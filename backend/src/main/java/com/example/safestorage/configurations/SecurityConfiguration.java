package com.example.safestorage.configurations;

//import com.numericalanalysis.numericalalanalysisbackend.services.UserDetailsServiceImpl;
import com.example.safestorage.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
        import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;/*
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;*/
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.NestedServletException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@EnableCaching(proxyTargetClass=true)
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserDetailsServiceImpl userDetailsService;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public SecurityConfiguration(BCryptPasswordEncoder encoder, UserDetailsServiceImpl userDetailsService) {
        this.encoder = encoder;
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder( encoder );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/note/**", "/api/note/list", "/api/get-login" ).authenticated();

        http.authorizeRequests()
                .antMatchers( "/")
                .permitAll()//.authenticated()
                .and().httpBasic()
                .disable();
        http.headers()
                .frameOptions()
                .sameOrigin()
                .and()
                .csrf()
                .disable();
        http.addFilterBefore((servletRequest, servletResponse, filterChain) -> {
                    try {
                        filterChain.doFilter( servletRequest, servletResponse );
                    }
                    catch (AccessDeniedException e) {
                        var rs = (HttpServletResponse) servletResponse;
                        rs.sendError( HttpStatus.FORBIDDEN.value() );
                    }
                }, FilterSecurityInterceptor.class );
        http.formLogin()
                .successHandler( (httpServletRequest, httpServletResponse, authentication) ->
                        httpServletResponse.setStatus( 200 ) )
                .failureHandler( (httpServletRequest, httpServletResponse, e) ->
                        httpServletResponse.setStatus( 401 ) )

                .permitAll()
                .loginProcessingUrl("/api/login")
                .successForwardUrl( "/api/after-login" )
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll().and()

                .logout()
                // разрешаем делать логаут всем
                .permitAll()
                // указываем URL логаута
                .logoutUrl("/api/logout")
                .logoutSuccessHandler( (httpServletRequest, httpServletResponse, authentication) ->
                                               httpServletResponse.setStatus( 200 ) )

                // делаем не валидной текущую сессию
                .invalidateHttpSession(true).and()

                .rememberMe().key("uniqueAndSecret");//.tokenRepository(persistentTokenRepository());
    }

}