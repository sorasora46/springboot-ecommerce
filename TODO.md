# TODO
- [ ] [Setup] CI/CD
- [ ] [Setup] response object (common response class)
- [ ] [API endpoint] server should be able to send user information by id
    - [ ] handle user's id not found
- [ ] [API endpoint] server should have signup endpoint
    - [ ] handle user already exist
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

# DONE
- [x] [API endpoint] server should be able to do health check
- [x] [Setup] set up postgresql database