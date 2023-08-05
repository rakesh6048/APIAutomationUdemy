package com.api.automation_scripts;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.api.automation.Library;
import com.api.automation.Payload;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;


public class GoogleAPITestScrpts {

	//Add place API;
	//public static String place_ID;

	@Test(enabled = false)
	public void validateAddPlaceAPI() {
        
		//String place_ID;
		//Validate Add place API working as expected or not

		//given  - All inputs details
		//when  - Submit the API
		//then  - Validate the response
		try {		
			RestAssured.baseURI = "https://rahulshettyacademy.com";
			String response=given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
					.body(Payload.addPlace())
					.when().post("maps/api/place/add/json")
					.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP")).header("server", "Apache/2.4.52 (Ubuntu)")
					.extract().response().asString();

			System.out.println(response);
			JsonPath jsp = new JsonPath(response);  //For parsing the json

			String place_ID=jsp.getString("place_id");

			System.out.println("place_ID : "+place_ID);
			
			//Validate Update place API working as expected or not
			
			String newAddress ="Summer Walk, Africa";
			
			given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
			       .body("{\r\n" + 
			       		"\"place_id\":\""+place_ID+"\",\r\n" + 
			       		"\"address\":\""+newAddress+"\",\r\n" + 
			       		"\"key\":\"qaclick123\"\r\n" + 
			       		"}")
			        .when().put("maps/api/place/update/json")
			        .then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
			
			//Validate Get Pace API working as expected or not
			
			String getPlaceResponse =given().log().all().queryParam("key", "qaclick123").queryParams("place_id", place_ID).header("Content-Type", "application/json")
		        .when().get("maps/api/place/get/json")
		        .then().log().all().assertThat().statusCode(200).body("name", equalTo("Rajputana House")).extract().response().asString();
			
			 JsonPath js1=Library.rawToJson(getPlaceResponse);
			 String actualAddress = js1.getString("address");
			 System.out.println(actualAddress);
			 
			 Assert.assertEquals(actualAddress, newAddress);
			 
			 

		}catch(Exception e) {
			System.out.println(e);
		}
	}

	
	
	
}
