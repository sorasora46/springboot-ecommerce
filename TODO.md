# TODO
- [ ] [Setup] CI/CD
- [ ] [Setup] setup request validation
- [ ] handle user's credential in user creation
    - [ ] hash user's password

# DONE
- [x] [API endpoint] server should be able to do health check
- [x] [Setup] set up postgresql database
- [x] [Setup] response object (common response class)
- [x] [Setup] setup exception
- [x] [API endpoint] server should be able to send user information by id
    - [x] handle user's id not found
- [x] [API endpoint] server should have signup endpoint
    - [x] handle user already exist
    - signup endpoint should accept request body
        - request body should have User domain data:
          - username
          - user's first name
          - user's last name
          - user's email
          - user's password
    - [x] create User domain
    - [x] create User's service
        - [x] interface
        - [x] impl
    - [x] [Setup] repository for User