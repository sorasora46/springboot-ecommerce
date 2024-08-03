# User Stories
- User
  - ~~a user shall be able to register to the system~~
  - ~~a user must have registered account~~
  - ~~a user shall be able to logged in to the system with registered account~~
  - ~~a user shall have a role of "USER"~~
  - a user shall be able to refresh token when access token is expired
- Admin
  - ~~an admin shall be able to add/remove product~~
  - an admin shall be able to edit product detail
    - name, price, amount, description, images, tag, category
  - an admin shall be able to add/remove promotion to the product
- Product
  - a user shall be able to find a product
    - from id/name/tag,category/image/price
  - a user shall be able to see list of products by pagination
- Cart
  - a user shall be able to add/remove products to/from their cart
  - a user shall be able to edit product in their cart
    - amount

# Tasks
- [ ] create cart service
- [ ] send accessToken, refreshToken when register/login
- [ ] create refresh-token endpoint
- [ ] create promotions tables
- [ ] reimplement update item in cart (set new quantity from request)
- [x] create carts, cart_items tables
- [x] join carts with products table