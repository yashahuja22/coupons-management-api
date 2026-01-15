# Coupons Management API – Monk Commerce Assignment

### Overview

This project is a backend service built using Java and Spring Boot to manage and apply discount coupons for an e-commerce platform.

The system currently supports:

•	Cart-wise coupons

•	Product-wise coupons

•	Buy X Get Y (BxGy) coupons

The design allows new coupon types to be added with minimal changes.

### Design Approach

The application uses a strategy-based design for handling different coupon types.

Each coupon type has its own class responsible for:

•	Checking whether the coupon is applicable

•	Calculating the discount

### Key Components

•	CouponStrategy – common interface for all coupon types

•	CartWiseCouponStrategy

•	ProductWiseCouponStrategy

•	BxGyCouponStrategy

•	CouponStrategyFactory – selects the correct strategy based on coupon type

An in-memory store is used to keep the implementation simple and focused on business logic.

### Implemented Coupon Types

#### 1. Cart-wise Coupon

Applies a percentage discount on the entire cart when the cart total crosses a certain amount.

**Example**

•	Cart total ≥ ₹100

•	10% discount on the full cart value


##### 2. Product-wise Coupon

Applies a discount to a specific product if it is present in the cart.

**Example**

•	Product ID 1 is present in the cart

•	20% discount applied to that product (for all quantities)

##### 3. Buy X Get Y (BxGy) Coupon

Provides free products when certain products are purchased.

**Example**

•	Buy 2 units of Product X

•	Get 1 unit of Product Z free

•	A repetition limit controls how many times the offer can be applied

This implementation is intentionally kept simple and its limitations are documented below.

### API Endpoints

##### Coupon Management

•	`POST /coupons` – Create a new coupon

•	`GET /coupons` – Fetch all coupons

•	`GET /coupons/{id}` – Fetch a coupon by ID

•	`DELETE /coupons/{id}` – Delete a coupon

##### Coupon Application

•	`POST /coupons/applicable` – Get all coupons applicable to a cart

•	`POST /coupons/apply/{id}` – Apply a specific coupon to the cart

### Scenarios Covered

•	Cart total meets threshold for cart-wise coupons

•	Product exists in cart for product-wise coupons

•	Required quantity is met for BxGy coupons

•	Multiple coupons evaluated independently

•	Invalid coupon ID handling

•	Coupon conditions not satisfied handling

### Scenarios Considered but Not Implemented

Due to time constraints, the following scenarios were thought through but not implemented:

### Coupon Rules

•	Applying multiple coupons together

•	Selecting the best coupon among many

•	Priority-based coupon execution

•	Category or user-specific coupons

•	Discount calculation with tax and shipping

### BxGy Edge Cases

•	Choosing the cheapest product as the free item

•	Multiple buy and get groups

•	Partial repetition handling

•	Overlapping buy and get products

•	Complex multi-level BxGy rules

### Assumptions

•	Only one coupon can be applied at a time

•	Product prices provided in the cart are final

•	Coupons do not expire

•	Cart does not include tax or shipping charges

•	Coupon validation is assumed to be handled upstream

•	In-memory storage is sufficient for this exercise

### Limitations
•	No database persistence (data resets on restart)

•	No concurrency handling

•	Simplified BxGy logic

•	No coupon usage limits

•	No authentication or authorization

### Handling Coupon Details

Coupon details are stored as a generic object to keep the system flexible.

When a coupon is created via API, the details are deserialized as a map.
Each coupon strategy converts this data into its own detail object using Jackson’s ObjectMapper.

### Testing

•	APIs were tested manually using Postman

•	Both valid and invalid cases were verified

•	Focus was on correctness of discount calculations

Unit tests can be added as a next step.