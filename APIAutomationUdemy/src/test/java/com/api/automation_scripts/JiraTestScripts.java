package com.api.automation_scripts;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class JiraTestScripts {
	
	
	@Test 
	public void createSessionAddComment() {
		
		
		RestAssured.baseURI="http://localhost:8080";
		
		SessionFilter session = new SessionFilter();
		
		//Create Session
		String response = given().log().all().header("Content-Type", "application/json")
		       .body("{ \"username\": \"singh.rakesh1231\", \"password\": \"Photon@1993\" }").filter(session)
		       .when().post("/rest/auth/1/session").then().log().all().extract().response().asString();
		
		JsonPath jsp = new JsonPath(response); 
		
		String sessionID = jsp.getString("session.value");
		String sessionname = jsp.getString("session.name");
		System.out.println("SessionID : "+sessionID);
		
		String cookieValue = sessionname+"="+sessionID;
		
		System.out.println("Complete Cookie Value : "+cookieValue);
		//.header("cookie",cookieValue)
		
		//Add Comment
		    
		       String addComentResponse= given().log().all().pathParam("key", "10107").header("Content-Type","application/json")
		               .body("{\r\n"
		               		+ "    \"body\": \"This is my first comments\",\r\n"
		               		+ "    \"visibility\": {\r\n"
		               		+ "        \"type\": \"role\",\r\n"
		               		+ "        \"value\": \"Administrators\"\r\n"
		               		+ "    }\r\n"
		               		+ "}").filter(session)
		                 .when().post("/rest/api/2/issue/{key}/comment")
		                 .then().log().all().assertThat().statusCode(201).extract().response().asString();
		        
		        JsonPath jsp1 = new JsonPath(addComentResponse); 
		        String commentID = jsp1.get("id");
		
		//Add Attachment the file in jira
		        
		        given().log().all().header("X-Atlassian-Token", "no-check").pathParam("key", "10107").header("Content-Type", "multipart/form-data")
		              .header("cookie",cookieValue)
		              .multiPart("file",new File("Jira.txt"))
		              .when().post("/rest/api/2/issue/{key}/attachments")
		              .then().log().all().assertThat().statusCode(200);
		        
		//Get Issue
		        
		      String getresponse =  given().log().all().pathParam("key", "10107").filter(session)
		    		  .queryParam("fields", "comment")
		              .when().get("/rest/api/2/issue/{key}")
		              .then().log().all().assertThat().statusCode(200).extract().response().asString();
		               
		      System.out.println(getresponse);
		      JsonPath jsp2 = new JsonPath(addComentResponse); 
		      int allComentID = jsp2.getInt("fields.comment.comments.size()");
		      
		      for(int i=0;i<allComentID;i++) {
		    	  
		    	 String commentIsue = jsp2.get("fields.comment.comments["+i+"]").toString();
		    	 
		    	 if(commentIsue.equalsIgnoreCase(commentID)) {
		    		 
		    		 String commeent = jsp2.get("fields.comment.comments["+i+"]");
		    		System.out.println(commeent);
		    	 }
		      }
	}

}
