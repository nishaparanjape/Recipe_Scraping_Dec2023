package com.testcases;

import java.io.IOException;

import org.testng.annotations.Test;

import pageObjects.DiabetesPage;

public class TC_001_Diabetes extends BaseClass {
		
	@Test(priority = 1)
	public void diabetesData() throws InterruptedException, IOException{
		DiabetesPage diabetesPage=new DiabetesPage(driver);
		diabetesPage.GetRecipes();
	}
	
	@Test(priority=2)
	public void CheckForHR() throws InterruptedException, IOException{
		DiabetesPage.CheckForHealthyRecipes();		
	}
	
	@Test(priority=3)
	public void CheckForA() throws InterruptedException, IOException{
		DiabetesPage.CheckForAllergies();
	}
}
