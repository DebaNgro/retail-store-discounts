# Retail Store Discounts App.
Api applies discounts based on the User Type and other bussiness logic.

# Build Steps: 
./gradlew clean build

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
