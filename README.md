# Spring Boot Macaroon Demo

Small app that plugs in Macaroon validation in Spring Security Config.

## Example Macaroon

    $ curl -v -H "Authorization: macaroon MDAyYmxvY2F0aW9uIGh0dHA6Ly9yYWluYm93LXdhc3RlbGFuZHMuY29tCjAwMWJpZGVudGlmaWVyIHNlcnZpY2VfYXBpCjAwMmZzaWduYXR1cmUgK7r2_tsQE3d7tsVwfLXg2Fr3qNLtriCIRFnK1Re499cK" \
        http://localhost:8082/api/user

# Progress

* Filter and Filter Configurer has been created to intercept the `Authorization: macaroon {macaroon}` header
* Macaroon is validated with the known secret and if correct continues to filter

# Outstanding work 

* End to end validation and session management
  * The User still needs to be created and set up in the session to make it valid
  * Currently it just filters through to the standard `UsernamePasswordAuthenticationFilter` and expects to authenticate with BASIC

 