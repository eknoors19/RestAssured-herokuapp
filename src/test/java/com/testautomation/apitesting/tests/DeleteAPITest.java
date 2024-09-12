package com.testautomation.apitesting.tests;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.testautomation.apitesting.utils.BaseTest;
import com.testautomation.apitesting.utils.FileNameConstants;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;

public class DeleteAPITest extends BaseTest{
	
	@Test
	public void deleteAPIRequest() {
		
		try {
			Logger log= LogManager.getLogger(this.getClass().getName());
			
			String postAPIRequestBody = FileUtils.readFileToString(new File(FileNameConstants.POST_API_REQUEST_BODY), "UTF-8");
			System.out.println(postAPIRequestBody);
			
			String tokenAPIRequestBody = FileUtils.readFileToString(new File(FileNameConstants.TOKEN_API_REQUEST_BODY), "UTF-8");
				
			//POST API Call -- Create Booking
			Response response= 
			RestAssured.given()
			.contentType(ContentType.JSON)
			.body(postAPIRequestBody)
			.baseUri("https://restful-booker.herokuapp.com/booking")
		.when()
		.post()
			.then()
			.assertThat()
			.statusCode(200)
			.statusLine("HTTP/1.1 200 OK")
			.header("Content-Type","application/json; charset=utf-8")
		.extract()
			.response();
			log.info("Create Booking Response: "+response);
		
			JSONArray jsonArray= JsonPath.read(response.body().asString(), "$.booking..firstname");
			System.out.println(jsonArray.get(0));
			String firstName = (String) jsonArray.get(0);
			Assert.assertEquals(firstName, "Jim");
			
			int bookingid= JsonPath.read(response.body().asString(), "$.bookingid");
			System.out.println("BookingId: "+bookingid);
			
			//Get API Call -- Get Booking
			/*
			 * RestAssured .given() .contentType(ContentType.JSON)
			 * .baseUri("https://restful-booker.herokuapp.com/booking") .when()
			 * .get("/{bookingId}", bookingid) .then() .assertThat() .statusCode(200);
			 * 
			 * //Get API Call -- Get Booking RestAssured .given()
			 * .contentType(ContentType.JSON)
			 * .baseUri("https://restful-booker.herokuapp.com/booking/") .when()
			 * .get("/{bookingId}", bookingid) .then() .assertThat() .statusCode(200)
			 * .extract() .response(); log.info("Get Booking Response: "+response);
			 */
		//Token Generation -- Create Auth Token 
			Response tokenAPIResponse=
			RestAssured
		.given()
			.contentType(ContentType.JSON)
			.body(tokenAPIRequestBody)
			.baseUri("https://restful-booker.herokuapp.com/auth")
		.when()
			.post()
		.then()
			.assertThat()
			.statusCode(200)
		.extract()
			.response();
			log.info("Create Token Response: "+response);
		String token= JsonPath.read(tokenAPIResponse.body().asString(), "$.token");
		//System.out.println("Auth Token: "+token);	
		log.info("Auth Token: "+token);
		//put API call - Update Booking
		/*
		 * RestAssured.given() .contentType(ContentType.JSON) .body(putAPIRequestBody)
		 * .header("Cookie", "token="+token)
		 * .baseUri("https://restful-booker.herokuapp.com/booking/") .when()
		 * .put("{bookingId}", bookingid) .then() .assertThat() .statusCode(200)
		 * .body("firstname", Matchers.equalTo("James")) .body("lastname",
		 * Matchers.equalTo("White")) .statusLine("HTTP/1.1 200 OK")
		 * .header("Content-Type","application/json; charset=utf-8") .extract()
		 * .response();
		 * 
		 * //patch API call Request Body -- Partial Update Booking RestAssured.given()
		 * .contentType(ContentType.JSON) .body(patchAPIRequestBody) .header("Cookie",
		 * "token="+token) .baseUri("https://restful-booker.herokuapp.com/booking/")
		 * .when() .patch("{bookingId}", bookingid) .then() .assertThat()
		 * .statusCode(200) .body("firstname", Matchers.equalTo("Jimmy"))
		 * .statusLine("HTTP/1.1 200 OK")
		 * .header("Content-Type","application/json; charset=utf-8") .extract()
		 * .response();
		 */
		
			//delete API call Request Body -- Delete Booking
			RestAssured.given()
			.contentType(ContentType.JSON)
			.header("Cookie", "token="+token)
			.baseUri("https://restful-booker.herokuapp.com/booking/")
		.when()
			.delete("{bookingId}", bookingid)
		.then()
			.assertThat()
			.statusCode(201);
			//System.out.println("Deleted Booking");		
			log.info("Deleted Booking "+token);
		}catch(IOException e)
		{
			
		}
		
	}

}
