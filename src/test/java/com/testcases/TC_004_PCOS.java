package com.testcases;

import java.io.IOException;
import org.testng.annotations.Test;
import pageObjects.DiabetesPage;
import pageObjects.HypertensionPage;
import pageObjects.PCOSPage;

public class TC_004_PCOS extends BaseClass {
	
	@Test(priority = 1)
	public void pcosData() throws InterruptedException, IOException{
		PCOSPage pcosPage=new PCOSPage(driver);
		pcosPage.GetRecipes();
	}
	
	@Test(priority=2)
	public void CheckForHR() throws InterruptedException, IOException{
		PCOSPage.CheckForHealthyRecipes();		
	}
	
	@Test(priority=3)
	public void CheckForA() throws InterruptedException, IOException{
		PCOSPage.CheckForAllergies();
	}
}