package com.api.automation_scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.api.automation.Payload;

import io.restassured.path.json.JsonPath;

public class ComplextJSONParseScripts {

	@Test(enabled = false)
	public void complexJsonParse() {

		JsonPath js = new JsonPath(Payload.CoursePrice());

		//Print No of courses 
		int numberOfCourses = js.getInt("courses.size()");
		System.out.println("NumberOfCourses : "+numberOfCourses);

		//Print Purchase Amount
		int totalPurchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("TotalPurchaseAmount : "+totalPurchaseAmount);

		//Print Title of the first course
		String firstCourseTitle = js.get("courses[0].title");
		System.out.println("FirstCourseTitle : "+firstCourseTitle);

		//Print Title of the second course
		String secondCourseTitle = js.get("courses[1].title");
		System.out.println("SecondCourseTitle : "+secondCourseTitle);

		//Print Title of the third course
		String thirdCourseTitle = js.get("courses[2].title");
		System.out.println("ThirdCourseTitle : "+thirdCourseTitle);

		//Print All course titles and their respective Prices
		System.out.println("Print All course titles and their respective Prices");

		for(int i=0;i<numberOfCourses;i++) {
			String courseTitles = js.get("courses["+i+"].title");
			System.out.println(courseTitles);
			System.out.println(js.get("courses["+i+"].price").toString());
		}
		System.out.println("Print no of copies sold by RPA Course");

		for(int i=0;i<numberOfCourses;i++) {

			String courseTitles = js.get("courses["+i+"].title");
			if(courseTitles.equalsIgnoreCase("RPA")) {
				
				int copies = js.get("courses["+i+"].copies");
				System.out.println("RPA Number Of Copies Sold : "+copies);
				break;
			}	
		}
	}

	@Test(enabled = true)
	public void sumOfCourses() {
        int sumOfAllCoursePrice=0;
		JsonPath js = new JsonPath(Payload.CoursePrice());

		//Print No of courses 
		int numberOfCourses = js.getInt("courses.size()");
		//System.out.println("NumberOfCourses : "+numberOfCourses);
		
		//System.out.println("Print no of copies sold by RPA Course");
		for(int i=0;i<numberOfCourses;i++) {
			
			int price = js.getInt("courses["+i+"].price");
		    int copies = js.getInt("courses["+i+"].copies");
		    int amount = price * copies;
		    System.out.println(amount);
		    sumOfAllCoursePrice = sumOfAllCoursePrice+amount;
		}
		System.out.println("Sum of All Course Price : "+sumOfAllCoursePrice);
		
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("TotalPurchaseAmount : "+purchaseAmount);
		
		Assert.assertEquals(sumOfAllCoursePrice, purchaseAmount);
	}
}
