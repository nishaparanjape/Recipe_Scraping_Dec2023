package pageObjects;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.testcases.BaseClass;

public class HypertensionPage extends BaseClass {
	WebDriver driver;

	public HypertensionPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
      this.driver = driver;
	}
	
	@FindBy(xpath="//*[@id=\"nav\"]/li[1]/a/div") WebElement recipe;
	@FindBy(xpath="//a[@title='Click here to see all recipes under High Blood Pressure']") WebElement hypertension;

	//@FindBys(@FindBy(xpath="//*[@class='rcc_recipethumbnail']/.."))static List<WebElement> recipeList;	
	@FindBys(@FindBy(xpath="//a[@class='respglink']")) static List<WebElement> listWEPages;
	
	public void GetRecipes() throws InterruptedException, IOException {
		recipe.click();
		hypertension.click();
		int i=1;
		//String strXPath;
		String strWebPageBaseLink = driver.getCurrentUrl() + "?pageindex=";
		int noOfPages = Integer.valueOf(listWEPages.get(listWEPages.size()-1).getText());
		System.out.println("No of pages : " + noOfPages);
		//RecipesPageWise(driver, listHypertension, "hypertension", 2);			
		//noOfPages=2;
		String strWebPageLink = "";
		for(i=1;i<=noOfPages;i++) {	
			System.out.println(i);
			strWebPageLink = strWebPageBaseLink + i;
			driver.navigate().to(strWebPageLink);
			Thread.sleep(2000);
			
			RecipesPageWise(driver, listHypertension, "hypertension", 2);
		}
	}
	
	

	
	public static void CheckForHealthyRecipes() throws InterruptedException, IOException {
		CheckForHealthyItems(listHHTension, 2);
	}
	
	public static void CheckForAllergies() throws InterruptedException, IOException {
		for(int i=0;i<listAllergies.size();i++) {
			CheckForAllergy(listAllergies.get(i), 2);
		}
	}
}

	       