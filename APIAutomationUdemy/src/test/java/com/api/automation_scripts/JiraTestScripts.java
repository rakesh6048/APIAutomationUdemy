package com.api.automation_scripts;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.api.automation.Payload;

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
		
		//Create new issues in jira
		 String createIssueResponse = given().log().all().header("Content-Type", "application/json").filter(session)
		        .body(Payload.createNewIssue())
		        .when().post("/rest/api/2/issue")
		        .then().log().all().assertThat().statusCode(201).extract().response().asString();
		 
		 System.out.println(createIssueResponse);
		 
		 JsonPath jsp3 = new JsonPath(createIssueResponse);
		 String isueid = jsp3.get("id");
		 System.out.println(isueid);
		
		//Add Comment
		    
		     String expectedMsg="Jira issues verified successfully";
		
		       String addComentResponse= given().log().all().pathParam("isueid", isueid).header("Content-Type","application/json")
		               .body("{\r\n"
		               		+ "    \"body\": \""+expectedMsg+"\",\r\n"
		               		+ "    \"visibility\": {\r\n"
		               		+ "        \"type\": \"role\",\r\n"
		               		+ "        \"value\": \"Administrators\"\r\n"
		               		+ "    }\r\n"
		               		+ "}").filter(session)
		                 .when().post("/rest/api/2/issue/{isueid}/comment")
		                 .then().log().all().assertThat().statusCode(201).extract().response().asString();
		        
		        JsonPath jsp1 = new JsonPath(addComentResponse); 
		        String commentID = jsp1.get("id");
		        System.out.println(commentID);
		
		//Add Attachment the file in jira
		        
		        given().log().all().header("X-Atlassian-Token", "no-check").pathParam("isueid", isueid).header("Content-Type", "multipart/form-data")
		              .header("cookie",cookieValue)
		              .multiPart("file",new File("Jira.txt"))
		              .when().post("/rest/api/2/issue/{isueid}/attachments")
		              .then().log().all().assertThat().statusCode(200);
		        
		//Get Issue
		        
		      String getresponse =  given().log().all().pathParam("isueid", isueid).filter(session)
		    		  .queryParam("fields", "comment")
		              .when().get("/rest/api/2/issue/{isueid}")
		              .then().log().all().assertThat().statusCode(200).extract().response().asString();
		              
		              //assertThat().statusCode(200).extract().response().asString();
		               
		      System.out.println(getresponse);
		      JsonPath jsp2 = new JsonPath(getresponse); 
		      int commentcounts = jsp2.getInt("fields.comment.comments.size()");
		      
		      for(int i=0;i<commentcounts;i++) {
		    	  
		    	  String commentIsuues = jsp2.get("fields.comment.comments["+i+"].id").toString();
		    	  
		    	  if(commentIsuues.equalsIgnoreCase(commentID)) {
		    		  
		    		  String commentMsg =jsp2.get("fields.comment.comments["+i+"].body").toString();
		    		  System.out.println(commentMsg);
		    		  Assert.assertEquals(commentMsg, expectedMsg);
		    	  }
		      }
	}

}
