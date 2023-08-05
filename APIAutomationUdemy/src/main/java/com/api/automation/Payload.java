package com.api.automation;

public class Payload {

	public static String addPlace() {

		return "{\r\n" + 
				"  \"location\": {\r\n" + 
				"    \"lat\": -38.383494,\r\n" + 
				"    \"lng\": 33.427362\r\n" + 
				"  },\r\n" + 
				"  \"accuracy\": 50,\r\n" + 
				"  \"name\": \"Rajputana House\",\r\n" + 
				"  \"phone_number\": \"(+91) 7305686048\",\r\n" + 
				"  \"address\": \"29, side layout, cohen 09\",\r\n" + 
				"  \"types\": [\r\n" + 
				"    \"shoe park\",\r\n" + 
				"    \"shop\"\r\n" + 
				"  ],\r\n" + 
				"  \"website\": \"http://google.com\",\r\n" + 
				"  \"language\": \"French-IN\"\r\n" + 
				"}";
	}

	public static String updatePlace() {

		return "validateAddPlaceAPI";

	}
	
	public static String CoursePrice() {
		
		return "{\r\n" + 
				"\"dashboard\": {\r\n" + 
				"\"purchaseAmount\": 1210,\r\n" + 
				"\"website\": \"rahulshettyacademy.com\"\r\n" + 
				"},\r\n" + 
				"\"courses\": [\r\n" + 
				"{\r\n" + 
				"\"title\": \"Selenium Python\",\r\n" + 
				"\"price\": 50,\r\n" + 
				"\"copies\": 6\r\n" + 
				"},\r\n" + 
				"{\r\n" + 
				"\"title\": \"Cypress\",\r\n" + 
				"\"price\": 40,\r\n" + 
				"\"copies\": 4\r\n" + 
				"},\r\n" + 
				"{\r\n" + 
				"\"title\": \"RPA\",\r\n" + 
				"\"price\": 45,\r\n" + 
				"\"copies\": 10\r\n" + 
				"}, \r\n" + 
				"{\r\n" + 
				"\"title\": \"API\",\r\n" + 
				"\"price\": 60,\r\n" + 
				"\"copies\": 5\r\n" + 
				"}\r\n" + 
				"]\r\n" + 
				"}\r\n" + 
				"";
	}

  public static String Addbook(String isbn, String aisle) {
	  
	  //String addBookPayload ="{\r\n" + "\r\n" + "\"name\":\"Learn Appium Automation with Java\",\r\n" + "\"isbn\":\""+isbn+"\",\r\n" + "\"aisle\":\""+aisle+"\",\r\n" + "\"author\":\"John foe\"\r\n" + "} \r\n" + "";
	  String addBookPayload ="{\r\n" + 
	  		"\"name\":\"Learn Appium Automation with Java\",\r\n" + 
	  		"\"isbn\":\""+isbn+"\",\r\n" + 
	  		"\"aisle\":\""+aisle+"\",\r\n" + 
	  		"\"author\":\"John foer\"\r\n" + 
	  		"}";
	  
	  return addBookPayload;
  }




}
