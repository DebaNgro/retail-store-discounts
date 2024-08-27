# Retail Store Discounts App.
Api applies discounts based on the User Type and other business logic.

## About the Service
Here is what this application demonstrates:

* Integration with **Spring Boot** Framework to develop this application.
* Writing a RESTful Controller using annotation: `@RestController`
* Integrated with `Spring Data Jpa` to leverage it's object-relational mapping capabilities for simplified data access and manipulation. 
* Used `H2` in-memory Database.
* Integrated with `Spring Validation` to simplify field-level data validation.
* Used `Lombok` to eliminate boilerplate code and to improve code readability.
* Utilized `Jacoco` for generating code Coverage reports, enabling thorough unit test analysis.
* Leveraged `Mockito` to facilitate efficient unit testing by mocking dependencies.
* Added `Swagger OpenAPI` for API Documentation.

### Database Design
I've created 3 tables:

*USERS* table contains User Related information (USER_TYPE defines the type of user, field can contain any of: CUSTOMER, AFFILIATE, EMPLOYEE;)
```
USERS (USER_ID, CREATED_DATE, EMAIL_ID, NAME, USER_TYPE)

Sample Record: 
insert into USERS (USER_ID, CREATED_DATE, EMAIL_ID, NAME, USER_TYPE) values (1005, '2018-11-06', 'mtrueman4@topsy.com', 'Mollee Trueman', 'CUSTOMER');
```

*PRODUCTS* table contains Products related information (PRODUCT_TYPE defines the Product Category, field can contain any of: GROCERY, DAIRY, MISC, ELECTRONICS, DECOR)
```
PRODUCTS (PRODUCT_ID, PRODUCT_NAME, PRODUCT_TYPE, PRICE)

Sample Record: 
insert into PRODUCTS (PRODUCT_ID, PRODUCT_NAME, PRODUCT_TYPE, PRICE) values (101, 'Mango', 'GROCERY', 45.9);
```

*DISCOUNT_RULES* table contains the Discount Rules
* DISCOUNT_AMT will contain the discountable amount on bill, e.g. on every 100$ bill, discount will be applied 5$ --> Here 5$ is the DISCOUNT_AMT
* DISCOUNT_PERCENTAGE contain the percentage of discount applicable on User Type
* IS_DISCOUNT tells whether this rule is discountable or nonDiscountable category. e.g. for GROCERY there should not be any discount --> so, IS_DISCOUNT is false. For others, it's true
* RETENTION_YEARS implies how long the user needs to be associated to be eligible for a certain discount. e.g. a CUSTOMER for over 2 years, gets a 5% discount --> So, *USERS.CREATED_DATE* must be before 2 years to get this discount.
* TYPE_KEY & TYPE_VALUE is a Key-value pair of a discount rule. e.g. USERTYPE -> EMPLOYEE is a Key-value pair. PRODUCTTYPE -> GROCERY is a Key-value pair.
```
DISCOUNT_RULES (DISCOUNT_RULE_ID, DISCOUNT_AMT, DISCOUNT_PERCENTAGE, IS_DISCOUNT, RETENTION_YEARS, TYPE_KEY, TYPE_VALUE )

Sample Records:
insert into DISCOUNT_RULES (DISCOUNT_RULE_ID, DISCOUNT_AMT, DISCOUNT_PERCENTAGE, IS_DISCOUNT, RETENTION_YEARS, TYPE_KEY, TYPE_VALUE )
					values (101, 0, 30, true, 0, 'USERTYPE', 'EMPLOYEE');
insert into DISCOUNT_RULES (DISCOUNT_RULE_ID, DISCOUNT_AMT, DISCOUNT_PERCENTAGE, IS_DISCOUNT, RETENTION_YEARS, TYPE_KEY, TYPE_VALUE )
					values (102, 0, 10, true, 0, 'USERTYPE', 'AFFILIATE');
insert into DISCOUNT_RULES (DISCOUNT_RULE_ID, DISCOUNT_AMT, DISCOUNT_PERCENTAGE, IS_DISCOUNT, RETENTION_YEARS, TYPE_KEY, TYPE_VALUE )
					values (103, 0, 5, true, 2, 'USERTYPE', 'CUSTOMER');
insert into DISCOUNT_RULES (DISCOUNT_RULE_ID, DISCOUNT_AMT, DISCOUNT_PERCENTAGE, IS_DISCOUNT, RETENTION_YEARS, TYPE_KEY, TYPE_VALUE )
					values (104, 0, 0, false, 0, 'PRODUCTTYPE', 'GROCERY');
insert into DISCOUNT_RULES (DISCOUNT_RULE_ID, DISCOUNT_AMT, DISCOUNT_PERCENTAGE, IS_DISCOUNT, RETENTION_YEARS, TYPE_KEY, TYPE_VALUE )
					values (105, 5, 0, true, 0, 'AMOUNT', '100');
```


### API Details
* The service applies discount based on the User Type, Customer Retention period, product type & total bill amount. It uses an in-memory database (H2) to store the data. You can call the REST endpoints defined in ```com.org.retailstore.controller.DiscountsController``` on **port 8085** to calculate the final bill by passing the UserId & List of Product Ids as Request Body. And it returns BillDto containing final Bill Amount. (see below)
* After Request comes to `DiscountsController`, the Request Body will be validated.
* Then, request will call `DiscountsService.calculateBill()` method to evaluate the Bill Amount.
* ```com.org.retailstore.service.impl.DiscountsServiceImpl``` implements ```DiscountsService``` interface.
* ```DiscountsServiceImpl``` calls ```UserRepository, ProductRepository, DiscountRulesRepository``` to fetch the respective data from Database & evaluate the business logic.
* After evaluation is done, it's prepare ```BillDto``` with final bill amount & return.

*Sample Request Url*
```
Method: POST
url: http://localhost:8085/calculate
```

*Sample Request Body*
```
{
    "userId": 1001,
    "productIds": [
        101,
        104,
        105,
        106,
        108
    ]
}
```

*Sample Response Body*
```
{
    "billAmount": 15282.0
}
```


Sample data is present at: ```\src\main\resources\data.sql``` file.


# Build Steps: 
```./gradlew clean build```

# Code Coverage Report
Once, you build the project with the above build command, Code Coverage report will be generated inside path: ```retail-store-discounts\build\reports\jacoco\test\html\index.html```

# Run Application:
Open RetailStoreDiscountsApplication class & run the main method.

# Curl cmd to test from Postman:
curl --location 'http://localhost:8085/calculate' \
--header 'Content-Type: application/json' \
--data '{
    "userId": 1001,
    "productIds": [101, 104,105,106,108]
}'


# Run Unit Test Case: 
Open DiscountsServiceTest class & execute the Test case.

# Swagger API Docs:
* Swagger UI Link: http://localhost:8085/swagger-ui/index.html
* Swagger API Docs Link: http://localhost:8085/v3/api-docs
