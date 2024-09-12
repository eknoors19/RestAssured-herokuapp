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

public class GetApiRequest extends BaseTest {
	
	@Test
	public void getAllBookings() throws IOException
	{		
		Logger log= LogManager.getLogger(this.getClass().getName());
		
		String postAPIRequestBody = FileUtils.readFileToString(new File(FileNameConstants.POST_API_REQUEST_BODY), "UTF-8");
		System.out.println(postAPIRequestBody);
		
		String tokenAPIRequestBody = FileUtils.readFileToString(new File(FileNameConstants.TOKEN_API_REQUEST_BODY), "UTF-8");
		
		String putAPIRequestBody = FileUtils.readFileToString(new File(FileNameConstants.PUT_API_REQUEST_BODY), "UTF-8");
		
		String patchAPIRequestBody = FileUtils.readFileToString(new File(FileNameConstants.PATCH_API_REQUEST_BODY), "UTF-8");
		
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
		
		Response getresponse=
		RestAssured.given()
			.contentType(ContentType.JSON)
			.baseUri("https://restful-booker.herokuapp.com/booking")
		.when()
			.get("/{bookingId}", bookingid)
		.then()
			.assertThat()
			.statusCode(200)
			.statusLine("HTTP/1.1 200 OK")
			.header("Content-Type","application/json; charset=utf-8")
		.extract()
			.response();
		log.info("Get Booking Response: "+getresponse);
		Assert.assertTrue(getresponse.getBody().asString().contains("Jim"));
		System.out.println(getresponse.getBody().prettyPrint());
		
	}
	
	@Test
	public void getAllBookingIds()
	{		
		Logger log= LogManager.getLogger(this.getClass().getName());
		
		Response response=
		RestAssured.given()
			.contentType(ContentType.JSON)
			.baseUri("https://restful-booker.herokuapp.com/booking")
		.when()
			.get()
		.then()
			.assertThat()
			.statusCode(200)
			.statusLine("HTTP/1.1 200 OK")
			.header("Content-Type","application/json; charset=utf-8")
		.extract()
			.response();
		log.info("Get Booking Response: "+response);
		Assert.assertTrue(response.getBody().asString().contains("bookingid"));
		System.out.println(response.getBody().prettyPrint());
	}

}
