package com.testautomation.apitesting.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.testautomation.apitesting.utils.BaseTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.minidev.json.JSONObject;

public class PostApiRequest extends BaseTest{
	
	@Test
	public void createBooking()
	{
		Logger log= LogManager.getLogger(this.getClass().getName());
		//RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		
		//prepare request body
		JSONObject booking = new JSONObject();
		JSONObject bookingDates = new JSONObject();
		
		booking.put("firstname", "Eknoor");
		booking.put("lastname", "Singh");
		booking.put("totalprice", "1000");
		booking.put("depositpaid", "true");
		booking.put("additionalneeds", "breakfast");
		booking.put("bookingdates", bookingDates);
		
		bookingDates.put("checkin", "2024-06-18");
		bookingDates.put("checkout", "2024-06-24");
		
		Response response=
				RestAssured.given()
					.contentType(ContentType.JSON)
					.body(booking.toString())
					.baseUri("https://restful-booker.herokuapp.com/booking")
					//.log().all()
				.when()
					.post()
				.then()
					.assertThat()
					//.log().all()
					//.log().ifValidationFails()
					.statusCode(200)
					.statusLine("HTTP/1.1 200 OK")
					.header("Content-Type","application/json; charset=utf-8")
					.body("booking.firstname", Matchers.equalTo("Eknoor"))
					.body("booking.totalprice", Matchers.equalTo(1000))
					.body("booking.bookingdates.checkin", Matchers.equalTo("2024-06-18"))
				.extract()
					.response();
		log.info("Create Booking Response: "+response);		
		
		Assert.assertTrue(response.getBody().asString().contains("bookingid"));
		int bookingId= response.path("bookingid");
		
		RestAssured.given()
		.contentType(ContentType.JSON)
		.pathParam("bookingID", bookingId)
		.baseUri("https://restful-booker.herokuapp.com/booking")
		//.log().all()
		.when()
			.get("{bookingID}")
		.then()
			.assertThat()
			.statusCode(200)
			.body("firstname", Matchers.equalTo("Eknoor"))
			.body("lastname", Matchers.equalTo("Singh"));
	}
	
}
