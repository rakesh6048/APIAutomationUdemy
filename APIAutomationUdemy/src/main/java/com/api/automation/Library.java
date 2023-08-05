package com.api.automation;

import io.restassured.path.json.JsonPath;

public class Library {

	    public static JsonPath rawToJson(String response) {
		
		JsonPath js1 = new JsonPath(response);
		return js1;
	}
}
