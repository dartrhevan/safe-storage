FROM node:12 AS builder

COPY . /usr/src/app/frontend

WORKDIR /usr/src/app/frontend

RUN npm install && npm run build

FROM nginx

#RUN apk update 

#RUN apk add nodejs

#RUN apk add npm

WORKDIR /usr/share/nginx/html

COPY --from=builder /usr/src/app/frontend/build .

RUN rm /etc/nginx/conf.d/default.conf # <= This line solved my issue

COPY ./nginx.conf /etc/nginx/nginx.conf

#RUN cp -r ./build/* /usr/share/nginx/html

