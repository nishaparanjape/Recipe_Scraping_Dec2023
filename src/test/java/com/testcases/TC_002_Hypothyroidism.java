package com.testcases;

import java.io.IOException;

import org.testng.annotations.Test;

import pageObjects.HypothyroidismPage;

public class TC_002_Hypothyroidism extends BaseClass{
	//HypothyroidismPage hp=new HypothyroidismPage(driver);

	@Test(priority = 1)
	public void hypothyroidism() throws InterruptedException, IOException {
	
	HypothyroidismPage hp=new HypothyroidismPage(driver);
	hp.GetRecipes();
	}
	
	@Test(priority=2)
	public void CheckForHR() throws InterruptedException, IOException{
		HypothyroidismPage.CheckForHealthyRecipes();		
	}
	
	@Test(priority=3)
	public void CheckForA() throws InterruptedException, IOException{
		HypothyroidismPage.CheckForAllergies();
	}
}