# user-login

A RSA based login cycle demo

## Bootstrap

Install maven, add it to your path.

    mvn spring-boot:run

Access login page: http://127.0.0.1:8080/login

## Background

We have an issue of encrypt our form data when login, but the distributed environment don't allowed us using HTTPS.
So we have idea of using rsa encryption for form data.

## Cycle

Everytime the user perform a login action, we should acquire a public key, it would encrypt the form data and pass the secret text to the backend.
