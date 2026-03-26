package stepDefinitions;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo_Payloads.CretaeOrderPayload;
import pojo_Payloads.LoginAPI_Payload;
import pojo_Payloads.OrderDetails;

public class StepDefinition {

	static RequestSpecification loginRequest;
	static RequestSpecification addProductRequest;
	static RequestSpecification createOrderRequest;
	static RequestSpecification deleteOrderRequest;
	static Response loginResponse;
	static Response addProductResponse;
	static Response CreateOrderResponse;
	static Response deleteOrderResponse;
	static JsonPath js;
	static String token;
	static String userId;
	static String productId;
	static String orderId;

	@Given("user sends userName and userPassword in LoginAPI")
	public void user_sends_user_name_and_user_password_in_login_api() {
		RequestSpecification loginReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType("application/json").setRelaxedHTTPSValidation().build();

		LoginAPI_Payload loginPay = new LoginAPI_Payload();
		loginPay.setUserEmail("Hitman264@gmail.com");
		loginPay.setUserPassword("Rohit@264");

		loginRequest = given().spec(loginReq).body(loginPay);

	}

	@When("user call the api with post request")
	public void user_call_the_api_with_post_request() {
		loginResponse = loginRequest.when().post("/api/ecom/auth/login").then().log().all().extract().response();

	}

	@Then("validate the status code and status message")
	public void user_validate_the_status_code_and_status_message() {

		int statusCode = loginResponse.getStatusCode();
		Assert.assertEquals(statusCode, 200, "statusCode is not matching as expected");

		String statusLine = loginResponse.getStatusLine().toString();
		System.out.println(statusLine);
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK", // HTTP/1.1 200 OK
				"statusLine is not matching as expected");
	}

	@Then("validate the token and userId from the response")
	public void user_validate_the_token_from_the_response() {
		String loginResponseString = loginResponse.asString();
		js = new JsonPath(loginResponseString);
		token = js.getString("token");
		System.out.println("Token: " + token);
		userId = js.getString("userId");
		System.out.println("User Id: " + userId);
	}

	@Then("validate the loginAPI response message as expected")
	public void validate_the_response_message_as_expected() {
		String loginResponseMessage = js.getString("message");
		Assert.assertEquals(loginResponseMessage, "Login Successfully", "statusMessage is not matching as expected");
	}

	@Given("user sends authorization token in header for AddProductAPI")
	public void user_sends_authorization_token_in_header_for_AddProductAPI() {
		RequestSpecification addProdReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).setRelaxedHTTPSValidation().build();

		addProductRequest = given().spec(addProdReq).param("productName", "Hitman").param("productAddedBy", userId)
				.param("productCategory", "fashion").param("productSubCategory", "shirts")
				.param("productPrice", "11500").param("productDescription", "Addias Originals")
				.param("productFor", "men")
				.multiPart("productImage", new File("C:\\Users\\dsenthi1\\Downloads\\img.jpg"));

	}

	@When("user sends post request in AddProductAPI")
	public void user_sends_post_request_in_add_product_api() {
		addProductResponse = addProductRequest.when().post("/api/ecom/product/add-product").then().log().all().extract()
				.response();
	}

	@Then("validate the productId from response")
	public void validate_the_product_id_from_response() {
		String addProdResponseString = addProductResponse.asString();
		js = new JsonPath(addProdResponseString);
		productId = js.getString("productId");
		System.out.println("ProductId: " + productId);
	}

	@Then("validate the addProductAPI response message as expected")
	public void validate_the_add_product_api_response_message_as_expected() {
		String addProdResponseMessage = js.getString("message");
		Assert.assertEquals(addProdResponseMessage, "Product Added Successfully",
				"statusMessage is not matching as expected");
	}

	@Given("user sends authorization token in header for CreateNewOrderAPI")
	public void user_sends_authorization_token_in_header_for_create_new_order_api() {
		RequestSpecification createOrderReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).setContentType("application/json").setRelaxedHTTPSValidation()
				.build();

		OrderDetails details = new OrderDetails();
		details.setCountry("India");
		details.setProductOrderedId(productId);
		List<OrderDetails> orderList = new ArrayList<OrderDetails>();
		orderList.add(details);
		CretaeOrderPayload order = new CretaeOrderPayload();
		order.setOrders(orderList);

		createOrderRequest = given().log().all().spec(createOrderReq).body(order);

	}

	@When("user sends post request in CreateNewOrderAPI")
	public void user_sends_post_request_in_create_new_order_api() {

		CreateOrderResponse = createOrderRequest.when().post("/api/ecom/order/create-order").then().log().all()
				.extract().response();

	}

	@Then("validate the orderId from response")
	public void validate_the_order_id_from_response() {
		String CreateOrderResString = CreateOrderResponse.asString();
		js = new JsonPath(CreateOrderResString);
		orderId = js.getString("orders[0]");
		System.out.println(orderId);

	}

	@Then("validate the CreateNewOrderAPI response message as expected")
	public void validate_the_create_new_order_api_response_message_as_expected() {
		String createOrdResponseMessage = js.getString("message");
		Assert.assertEquals(createOrdResponseMessage, "Order Placed Successfully",
				"statusMessage is not matching as expected");
	}

	@Given("user sends authorization token in header for DeleteProductAPI")
	public void user_sends_authorization_token_in_header_for_delete_product_api() {

		RequestSpecification deletereq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType("application/json").addHeader("Authorization", token).setRelaxedHTTPSValidation()
				.build();

		deleteOrderRequest = given().spec(deletereq).pathParam("productId", productId);

	}

	@When("user sends delete request in DeleteProductAPI")
	public void user_sends_delete_request_in_delete_product_api() {
		deleteOrderResponse = deleteOrderRequest.when().delete("/api/ecom/product/delete-product/{productId}").then()
				.log().all().extract().response();
	}

	@Then("validate the DeleteProductAPI response message as expected")
	public void validate_the_delete_product_api_response_message_as_expected() {

		String deleteOrdResString = deleteOrderResponse.asString();
		js = new JsonPath(deleteOrdResString);
		String deleteOrdResMessage = js.getString("message");
		Assert.assertEquals(deleteOrdResMessage, "Product Deleted Successfully",
				"statusMessage is not matching as expected");

	}

}
