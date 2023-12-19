package pageObjects;

import java.io.IOException;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import com.testcases.BaseClass;

public class HypothyroidismPage extends BaseClass {
	WebDriver driver;
	@FindBy(xpath="//*[@id=\"nav\"]/li[1]/a") WebElement lnkRecipes;
	@FindBy(xpath="//*[@id=\"ctl00_cntleftpanel_ttlhealthtree_tvTtlHealtht211\"]") WebElement lnkHypo;
	@FindBy(xpath="//img[@src=\"images/recipe/more.jpg\"]") static List<WebElement> recipesPerPage;
	@FindBy(xpath="//a[@class='respglink']") static List<WebElement> pageList;
	@FindBys(@FindBy(xpath="//a[@class='respglink']")) List<WebElement> listWEPages;
	
	public HypothyroidismPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}
	
	public void GetRecipes() throws InterruptedException, IOException
	{
		lnkRecipes.click();
		lnkHypo.click();
		int i;
		String strXPath;
		int noOfPages=pageList.size();
		System.out.println("No of pages other than main page:"+ noOfPages );
		RecipesPageWise(driver, listHypothyroidism, "Hypothyroidism", 1);
		for(i=2;i<=noOfPages+1;i++) {						
			strXPath = "//*[@id='pagination']/a[" + i + "]";
			driver.findElement(By.xpath(strXPath)).click();
			System.out.println("Current Page Number:"+ i);
			Thread.sleep(2000);
			RecipesPageWise(driver,listHypothyroidism, "Hypothyroidism", 1);
		}	
	}

	public static void CheckForHealthyRecipes() throws InterruptedException, IOException {
		CheckForHealthyItems(listHThyroidism, 1);
	}
	public static void CheckForAllergies() throws InterruptedException, IOException {
		for(int i=0;i<listAllergies.size();i++) {
			CheckForAllergy(listAllergies.get(i), 1);
		}
	}
}