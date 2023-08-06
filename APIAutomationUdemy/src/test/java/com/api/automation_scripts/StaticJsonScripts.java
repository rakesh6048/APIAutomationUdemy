package com.api.automation_scripts;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import com.api.automation.Library;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class StaticJsonScripts {

	@Test(enabled = false)
	public void addBook() throws IOException {

		RestAssured.baseURI = "http://216.10.245.166";

		String response1 = given().log().all().header("Content-Type", "application/json")
				.body(new String(Files.readAllBytes(Paths.get("E:\\API Automation\\API Autmation Using Rest Assured\\JSON PayLoad Files\\AddBook.json"))))
				.when()
				.post("/Library/Addbook.php")
				.then().log().all().assertThat().statusCode(200)
				.extract().response().asString();

		JsonPath js = Library.rawToJson(response1);
		String id = js.get("ID");
		System.out.println("ID : "+id);	    
	}

	@Test(enabled = true)
	public void validateAddPlaceAPIInStatic() {

		//Content of the file to String > content of file can convert into Byte > Byte data to String
		//Content of the file to String > content of file can convert into Byte > Byte data to String


		try {		
			RestAssured.baseURI = "https://rahulshettyacademy.com";
			String response=given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
					.body(new String(Files.readAllBytes(Paths.get("E:\\API Automation\\API Autmation Using Rest Assured\\JSON PayLoad Files\\AddPlace.json"))))
					.when().post("maps/api/place/add/json")
					.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP")).header("server", "Apache/2.4.52 (Ubuntu)")
					.extract().response().asString();

			System.out.println(response);
			JsonPath jsp = new JsonPath(response);  //For parsing the json

			String place_ID=jsp.getString("place_id");

			System.out.println("place_ID : "+place_ID);

		}catch(Exception e) {
			System.out.println(e);
		}
	}


}
