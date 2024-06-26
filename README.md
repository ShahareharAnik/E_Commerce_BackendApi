All APIs:                                                                                

http://localhost:8080/

### User Endpoints

- **Get all users:**
  - `GET /users`

- **Search user by name:**
  - `GET /users/search?name={Name}`

- **Search users by type:**
  - `GET /users/search?type={type}`

- **Get user by ID:**
  - `GET /users/{id}`

- **Add a new user:**
  - `POST /users`

- **Delete user by ID:**
  - `DELETE /users/{id}`

- **Update user details:**
  - `PUT /users/{id}`

- **Search user by ID using query parameter:**
  - `GET /users/search?id=1`

### Product Endpoints

- **Get all products:**
  - `GET /products`

- **Search product by name:**
  - `GET /products/search?name={name}`

- **Search products by price:**
  - `GET /products/search?price={price}`

- **Get product by ID:**
  - `GET /products/{id}`

- **Add a new product:**
  - `POST /products`

- **Delete product by ID:**
  - `DELETE /products/{id}`

- **Update product details:**
  - `PUT /products/{id}`

- **Search product by name using path parameter:**
  - `GET /products/name/{name}`

- **Search product by ID using path parameter:**
  - `GET /products/id/{id}`

### Cart Endpoints

- **Add product to cart by ID:**
  - `POST /cart/customer/{customerId}/add-product-by-id/{productId}`

- **Add product to cart by name:**
  - `POST /cart/customer/{customerId}/add-product-by-name/{productName}`

- **Get all cart items for a customer:**
  - `GET /cart/customer/{customerId}`

- **Remove product from cart:**
  - `DELETE /cart/customer/{customerId}/remove-product/{productId}`

### Order Endpoints

- **Place an order for a customer:**
  - `POST /orders/place-order/{customerId}`

- **Get all orders:**
  - `GET /orders/all-orders`

- **Approve an order by ID:**
  - `PUT /orders/approve-order/{orderId}`
