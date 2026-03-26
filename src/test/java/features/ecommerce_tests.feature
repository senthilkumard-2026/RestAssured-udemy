Feature: Ecommerce End-to-End Test Scenarios

@LoginTest
Scenario: Login into Ecommerce website using valid credentials
Given user sends userName and userPassword in LoginAPI
When  user call the api with post request
Then  validate the status code and status message
And   validate the token and userId from the response
And   validate the loginAPI response message as expected

@AddProductTest
Scenario: Add new products into the Ecommerce website
Given user sends authorization token in header for AddProductAPI
When  user sends post request in AddProductAPI
Then  validate the productId from response
And   validate the addProductAPI response message as expected

@CreateNewOrderTest
Scenario: Create a new order in the Ecommerce website 
Given user sends authorization token in header for CreateNewOrderAPI
When  user sends post request in CreateNewOrderAPI
Then  validate the orderId from response
And   validate the CreateNewOrderAPI response message as expected

@DeleteProductTest
Scenario:Delete the product in the Ecommerce website
Given user sends authorization token in header for DeleteProductAPI
When  user sends delete request in DeleteProductAPI
Then  validate the DeleteProductAPI response message as expected