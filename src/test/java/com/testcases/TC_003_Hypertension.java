package com.testcases;

import java.io.IOException;

import org.testng.annotations.Test;

import pageObjects.DiabetesPage;
import pageObjects.HypertensionPage;

public class TC_003_Hypertension extends BaseClass{

	@Test(priority = 1)
	public void HypertensionData() throws InterruptedException, IOException{
		HypertensionPage hypertensionPage=new HypertensionPage(driver);
		hypertensionPage.GetRecipes();
	}
	
	@Test(priority=2)
	public void CheckForHR() throws InterruptedException, IOException{
		HypertensionPage.CheckForHealthyRecipes();		
	}
	
	@Test(priority=3)
	public void CheckForA() throws InterruptedException, IOException{
		HypertensionPage.CheckForAllergies();
	}
	
	
}
