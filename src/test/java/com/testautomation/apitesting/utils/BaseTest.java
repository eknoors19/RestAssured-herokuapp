package com.testautomation.apitesting.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;

import io.restassured.RestAssured;

public class BaseTest {
	
	@BeforeMethod
	public void beforeMethod()
	{
		
		//private Logger log= LoggingLogger.getLogger(this.getClass().getName());
		
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}

}
