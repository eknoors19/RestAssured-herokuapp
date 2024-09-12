package com.testautomation.apitesting.tests;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.testautomation.apitesting.utils.BaseTest;
import com.testautomation.apitesting.utils.FileNameConstants;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;

public class PostApiRequestUsingFile extends BaseTest{
	
	@Test
	public void postAPIRequest() {
		
		try {
			Logger log= LogManager.getLogger(this.getClass().getName());
			
			String postAPIRequestBody = FileUtils.readFileToString(new File(FileNameConstants.POST_API_REQUEST_BODY), "UTF-8");
			System.out.println(postAPIRequestBody);
			
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
			
			JSONArray jsonArrayLastname= JsonPath.read(response.body().asString(), "$.booking..lastname");
			System.out.println(jsonArrayLastname.get(0));
			String lastName = (String) jsonArrayLastname.get(0);
			
			Assert.assertEquals(lastName, "Brown");
			
			JSONArray jsonArrayCheckin= JsonPath.read(response.body().asString(), "$.booking.bookingdates..checkin");
			System.out.println(jsonArrayCheckin.get(0));
			String checkin = (String) jsonArrayCheckin.get(0);
			Assert.assertEquals(checkin, "2018-01-01");
			
			int bookingid= JsonPath.read(response.body().asString(), "$.bookingid");
			System.out.println("Booking Id: "+bookingid);
			
			RestAssured
			.given()
			.contentType(ContentType.JSON)
			.baseUri("https://restful-booker.herokuapp.com/booking")
		.when()
			.get("/{bookingId}", bookingid)
		.then()
			.assertThat()
			.statusCode(200);
			
			
		}catch(IOException e)
		{
			
		}
		
	}

}
