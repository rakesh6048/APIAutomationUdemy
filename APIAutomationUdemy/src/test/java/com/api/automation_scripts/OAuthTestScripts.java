package com.api.automation_scripts;

import static io.restassured.RestAssured.given;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.path.json.JsonPath;

public class OAuthTestScripts {
	
	
	@Test(enabled = true)
	public void  oAuthorization_Code_TestVerification() {
		try {
			
		//Hit the url in browser and get the url as string
			WebDriverManager.chromedriver().setup();
			WebDriver driver = new ChromeDriver();
			
			driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			
			//driver.findElement(By.xpath("//div[contains(text(),'Use another account')]")).click();
			
			driver.findElement(By.cssSelector("input[type='email']")).sendKeys("rakesh.s6048@gmail.com");
			driver.findElement(By.cssSelector("input[type='password']")).sendKeys("Photon@1993");
			
			driver.findElement(By.xpath("(//div[@class='VfPpkd-Jh9lGc'])[2]")).click();
			
			String url = driver.getCurrentUrl();
			
			//String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0Adeu5BXKUvVi4N135WCnolcZndiAbMmt2AX_GF9SfJ8ag4L02Ik8zhaZ2f0edMEplFnG3Q&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=consent";
			
		    String partialCode = url.split("code=")[1];
		    String code = partialCode.split("&scope")[0];
		    System.out.println(code);
			
			
		//Get the access token and supply in next request to get response as all course price
		String tokenResponse= given().log().all().urlEncodingEnabled(false)
							 .queryParam("code", code)
		                     .queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		                     .queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		                     .queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		                     .queryParam("grant_type", "authorization_code")
		                     .when()
		                     .post("https://www.googleapis.com/oauth2/v4/token").asString();
		System.out.println(tokenResponse);
		
		JsonPath jsp = new JsonPath(tokenResponse);
		String accesstoken = jsp.get("access_token");
		System.out.println(accesstoken);
			
			
		//Get the actual response as all course price
	     String getResponse=given().log().all().queryParam("access_token", accesstoken)
	    		 				  .when()
	    		 				  .get("https://rahulshettyacademy.com/getCourse.php").asString();
	     System.out.println(getResponse);
		
		}catch(Exception e) {
			System.out.println(e);
		}	
	}
	
	@Test(enabled = false)
	public void  oAuth_Client_Credential_TestVerification() {
		
		 try {
			 
		 //Get the access token and supply in next request to get response as all course price
			 
			String tokenResponse= given().log().all()
			        			 .queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
			        			 .queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
			        			 .queryParam("grant_type", "authorization_code")
			        			 .queryParam("scope", "https://www.googleapis.com/auth/userinfo.emai")
			        			 .when()
			        			 .post("https://www.googleapis.com/oauth2/v4/token").asString();
			
			System.out.println(tokenResponse);
			JsonPath jsp = new JsonPath(tokenResponse);
			String accesstoken = jsp.get("access_token");
			
			System.out.println(accesstoken);
			
			//Get all course price from actual response 
			String getResponse = given().log().all().queryParam("access_token", accesstoken)
			                     .when()
			                     .get("https://rahulshettyacademy.com/getCourse.php").asString();
			
			System.out.println(getResponse);
			
			JsonPath jsp1 = new JsonPath(getResponse);
			
			//start extract data and applies assetion to verify
			
			 
		 }catch(Exception e) {
			 System.out.println(e);
	}
	}

}
