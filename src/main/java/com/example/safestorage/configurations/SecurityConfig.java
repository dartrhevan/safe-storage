package com.example.safestorage.configurations;

//import com.numericalanalysis.numericalalanalysisbackend.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
        import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;/*
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;*/


@EnableCaching(proxyTargetClass=true)
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
/**
    @Autowired
    private DataSource dataSource;

    @Autowired

    @Autowired
    private UserDetailsServiceImpl userDetailsService;*/
    // регистрируем нашу реализацию UserDetailsService
    // а также PasswordEncoder для приведения пароля в формат SHA1


    private final BCryptPasswordEncoder encoder;

    @Autowired
    public SecurityConfig(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(userDetailsService).passwordEncoder( encoder );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                //.anyRequest()
                .antMatchers( "/**")
                .permitAll();//.authenticated()
                //.and().httpBasic();
        http.headers().frameOptions().sameOrigin().and();
        http.authorizeRequests()
                .antMatchers("").authenticated()
                .and().httpBasic();

        http.csrf().disable();
        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/j_spring_security_check")
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll();
        http.logout()
                // разрешаем делать логаут всем
                .permitAll()
                // указываем URL логаута
                .logoutUrl("/logout")
                // указываем URL при удачном логауте
                .logoutSuccessUrl("/login?logout")
                // делаем не валидной текущую сессию
                .invalidateHttpSession(true);

        http.rememberMe().key("uniqueAndSecret");//.tokenRepository(persistentTokenRepository());
    }

}