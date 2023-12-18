package com.testcases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.tarladala.utilities.Loggerload;

public class BaseClass {
	public static WebDriver driver;
	public static ResourceBundle rb;
	public static String strFilteredRecipes = System.getProperty("user.dir")
			+ "\\src\\test\\resources\\TestData\\Team23-RecipeScraping-Data.xlsx";
	public static String[] strHeading = { "Recipe ID", "Recipe Name: ", "Recipe Category", "Food Category",
			"Ingredients", "Preparation Time", "Cooking Time", "Preparation Method", "Nutrient Values",
			"Targetted Morbid Conditions", "Recipe URL", "Allergies : " };
	public static List<String> listHypothyroidism = new ArrayList<String>();
	public static List<String> listDiabetes = new ArrayList<String>();
	public static List<String> listHypertension = new ArrayList<String>();
	public static List<String> listPCOS = new ArrayList<String>();
	public static List<String> listHDiabetes = new ArrayList<String>();
	public static List<String> listHThyroidism = new ArrayList<String>();
	public static List<String> listHHTension = new ArrayList<String>();
	public static List<String> listHPCOS = new ArrayList<String>();
	public static List<String> listAllergies = new ArrayList<String>();
	
	@FindBys(@FindBy(xpath="//*[@class='rcc_recipethumbnail']/.."))static List<WebElement> recipeList;	

	@FindBys(@FindBy(xpath="//article[@class='rcc_recipecard']/div[3]/span[1]"))
				List<WebElement> listxPathNames;
	@FindBys(@FindBy(xpath="//article[@class='rcc_recipecard']/div[2]/span[1]"))
				List<WebElement> listxPathIDs;	
	@FindBys(@FindBy(xpath="//*[@id='rcpnutrients']")) List<WebElement> listWENValues;
	@FindBy(xpath="//*[@id='rcpinglist']") WebElement WEIngredients;
	@FindBy(xpath="//*[@id='recipe_small_steps']") WebElement WEPreparationMethod;
	@FindBy(xpath="//*[@id='rcpnutrients']") WebElement WENValues;	
	@FindBys(@FindBy(xpath="//*[@itemprop='prepTime']")) 
				List<WebElement> listWEPTime;
	@FindBys(@FindBy(xpath="//*[@itemprop='cookTime']")) 
				List<WebElement> listWECTime;
	@FindBys(@FindBy(xpath="//div[@class='tags']/a")) List<WebElement> listWETags;			
	

	@Parameters("browser")
	@BeforeClass
	public void setup(String br) throws Exception {
		if (driver==null) { 
			rb = ResourceBundle.getBundle("config");
			if (br.equalsIgnoreCase("chrome")) {
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--headless=new");
				driver = new ChromeDriver(options);
			} 
			else if (br.equalsIgnoreCase("edge")) {
				driver = new EdgeDriver();
			}
			else if (br.equalsIgnoreCase("firefox")) {
				driver = new FirefoxDriver();
			} 
			else {
				throw new Exception("Browser is not correct");
			}
		}		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(rb.getString("appurl"));

		driver.manage().window().maximize();
		ReadDataFromExcel();
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}

	public void captureScreeshot(WebDriver driver, String tname) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File target = new File(System.getProperty("user.dir") + "/Screenshots" + tname + ".png");
		FileUtils.copyFile(source, target);
		System.out.println("Screenshot taken");
	}

	public void ReadDataFromExcel() throws IOException {
		String path = System.getProperty("user.dir")
				+ "\\src\\test\\resources\\TestData\\IngredientsAndComorbidities-ScrapperHackathon.xlsx";
		File excelfile = new File(path);
		FileInputStream fis = new FileInputStream(excelfile);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(0);// ("EliminatedItems");
		int lastRow = sheet.getLastRowNum();

		int i = 0;
		XSSFRow row;
		XSSFCell cell;
		String strItem;

		// read all the data from xls and add to various lists
		for (i = 2; i < lastRow; i++) {
			row = sheet.getRow(i);
			cell = row.getCell(0);
			if (cell == null) {
				break;
			}
			strItem = cell.getStringCellValue();
			if (!strItem.isBlank()) {
				listDiabetes.add(strItem.strip());
			}
		}
		for (i=2;i<lastRow;i++) {
			row=sheet.getRow(i);
		    cell=row.getCell(1);

		    strItem = cell.getStringCellValue();
		    if (!strItem.isBlank()){
		    	listHDiabetes.add(strItem.strip());
		    }
		}
		
		sheet = workbook.getSheetAt(1);// ("EliminatedItems");
		lastRow = sheet.getLastRowNum();
		for (i = 2; i < lastRow; i++) {
			row = sheet.getRow(i);
			cell = row.getCell(0);

			strItem = cell.getStringCellValue();
			if (!strItem.isBlank()) {
				listHypothyroidism.add(strItem.strip());
			}
		}
		for (i=2;i<lastRow;i++) {
			row=sheet.getRow(i);
		    cell=row.getCell(1);

		    strItem = cell.getStringCellValue();
		    if (!strItem.isBlank()){
		    	listHThyroidism.add(strItem.strip());
		    }
		}
		
		sheet = workbook.getSheetAt(2);// ("EliminatedItems");
		lastRow = sheet.getLastRowNum();
		for (i = 2; i < lastRow; i++) {
			row = sheet.getRow(i);
			cell = row.getCell(0);

			strItem = cell.getStringCellValue();
			if (!strItem.isBlank()) {
				listHypertension.add(strItem.strip());
			}
		}
		for (i=2;i<lastRow;i++) {
			row=sheet.getRow(i);
		    cell=row.getCell(1);

		    strItem = cell.getStringCellValue();
		    if (!strItem.isBlank()){
		    	listHHTension.add(strItem.strip());
		    }
		}
		
		sheet = workbook.getSheetAt(3);// ("EliminatedItems");
		lastRow = sheet.getLastRowNum();
		for (i = 2; i < lastRow; i++) {
			row = sheet.getRow(i);
			cell = row.getCell(0);

			strItem = cell.getStringCellValue();
			if (!strItem.isBlank()) {
				listPCOS.add(strItem.strip());
			}
		}
		for (i=2;i<lastRow;i++) {
			row=sheet.getRow(i);
		    cell=row.getCell(1);

		    strItem = cell.getStringCellValue();
		    if (!strItem.isBlank()){
		    	listHPCOS.add(strItem.strip());
		    }
		}
		//allergies
		sheet = workbook.getSheetAt(4);// ("Allergies");
		lastRow = sheet.getLastRowNum();		
		for (i=2;i<lastRow;i++) {
			row=sheet.getRow(i);
		    cell=row.getCell(0);

		    strItem = cell.getStringCellValue();
		    if (!strItem.isBlank()){
		    	listAllergies.add(strItem.strip());
		    }
		}
		workbook.close();
	}
	
	
	public static void CheckForHealthyItems(List<String> healthyItems, int sheetNo) throws IOException {
		XSSFRow row;
		XSSFCell cell;		
		boolean bFound=false;
		int i;
		String ingredient="";
		Path p = Paths.get(BaseClass.strFilteredRecipes);
		boolean bFileExists = Files.exists(p);
		
		XSSFWorkbook wb;

		//if file already exists, check for the last row number
		if(bFileExists) {
			FileInputStream myxls = new FileInputStream(BaseClass.strFilteredRecipes);
			wb = new XSSFWorkbook(myxls);
		    XSSFSheet sheet = wb.getSheetAt(sheetNo);
			int lastRow=sheet.getLastRowNum();			
		    for(i=0;i<lastRow;i++){
		    	row = sheet.getRow(i);		    	
	    		cell = row.getCell(4);
	    		ingredient = cell.getStringCellValue().toLowerCase();

	    		//check for healthy ingredients 
	    		for (String item: healthyItems ) {
			        bFound = ingredient.contains(item.toLowerCase());
			        
			        //if healthy ingredient found, change the color of the row to green
			        if (bFound) {
			        	XSSFFont font = wb.createFont();
			        	CellStyle cs = wb.createCellStyle();
			        	font.setColor(IndexedColors.GREEN.getIndex());
						font.setBold(true);
						cs.setFont(font);
						for(int x=0;x<11;x++)
						{
							cell = row.getCell(x);
							cell.setCellStyle(cs);
						}
						System.out.println(row.getRowNum() + " : " + item);
			        	break;
			    	   	}
			        //and continue to the next healthy ingredient
		    		if(!bFound) {
		    			continue;
		    		}
		    		bFound = false;	
		    	}
		    }
		    //save the excel file
			FileOutputStream fileOut = new FileOutputStream(BaseClass.strFilteredRecipes);
			wb.write(fileOut);
			fileOut.close();
		    wb.close();
		}
	}
		
	public static void CheckForAllergy(String allergy, int sheetNo) throws IOException {
		System.out.println(allergy);
		XSSFRow row;
		XSSFCell cell;		
		boolean bFound=false;
		int i;
		String ingredient="";
		Path p = Paths.get(strFilteredRecipes);
		boolean bFileExists = Files.exists(p);
		
		XSSFWorkbook wb;

		//if file already exists, check for the last row number
		if(bFileExists) {
			FileInputStream myxls = new FileInputStream(strFilteredRecipes);
			wb = new XSSFWorkbook(myxls);
		    XSSFSheet sheet = wb.getSheetAt(sheetNo);
			int lastRow=sheet.getLastRowNum();	
			
			System.out.println("sheet no : " + sheetNo);
			System.out.println("last row : " + lastRow);
			
		    for(i=1;i<lastRow;i++) {
		    	row = sheet.getRow(i);		    	
	    		cell = row.getCell(4);
	    		ingredient = cell.getStringCellValue().toLowerCase();	
	    		
	    		//check for the allergy ingredient
		        bFound = ingredient.contains(allergy.toLowerCase());
		        
		        //if healthy ingredient found, add it at the end column of the row
		        if (bFound) {
		        	cell = row.getCell(11);
					String strPrevVal = cell.getStringCellValue();
					System.out.println("prev value : " + strPrevVal);

					cell.setCellValue(strPrevVal + ", " + allergy);
					System.out.println(row.getRowNum());
		        }
		    }
			FileOutputStream fileOut = new FileOutputStream(strFilteredRecipes);
			wb.write(fileOut);
			fileOut.close();
		    wb.close();
		}
	}
	
	
	public void RecipesPageWise( WebDriver driver, List<String> eliminatedList, 
									String strMorbidity, int sheetNo) 
									throws InterruptedException, IOException {
		//this.driver = driver1;
		//driver1=driver;
		Thread.sleep(2000);
			String strMainPage = driver.getCurrentUrl();
			int i=0;
			int nRecipes = recipeList.size();//a[contains(@href,"pageindex")]
			XSSFRow row;
			XSSFCell cell;		
			String  name="", pTime="", cTime="", id="";
			String ingredients="", nutrientValues="", preparationMethod="", url="";
			System.out.println("No. of recipes : " + nRecipes);
			String tagText = "";
			int z;
			String strRecipeCategory="";
			String strFoodCategory="";
			//nRecipes = 3;
			
			//Scan through all the recipes on the page
			for(i=0;i<nRecipes;i++) {
				//bFound is a flag to check if the recipe has any eliminated ingredient
				System.out.println("in the loop, i = " + i );
				boolean bFound = false;
				
				//get recipe ID
				id = listxPathIDs.get(i).getText();
				System.out.println("id : " + id);
				
				//get recipe name
				name = listxPathNames.get(i).getText();
				System.out.println("name : " + name);
				Loggerload.info("Recipe Name : " + name);
				
				//check if the name of the recipe has an eliminated ingredient
				//if yes, set the bFound to true
				for (String word: eliminatedList ) {
					bFound = name.toLowerCase().contains(word.toLowerCase());
					if (bFound) {
						break;
					}
				}
				
				//if eliminated ingredients found in the name, continue through the loop, 
				//don't go ahead for saving in the excel
				if (bFound){
					System.out.println("found in name..so navigating back to lists page..");
					continue;
				}
				bFound = false;
				
				//if eliminated ingredients not found in the name, open the recipe details page 
				//System.out.println("xpathname : " + xPathName);
				listxPathNames.get(i).click();		
				System.out.println("recipe opened");
				Thread.sleep(3000);
						
				System.out.println(driver.getCurrentUrl());
										
				ingredients = WEIngredients.getText();
				
				for (String word: eliminatedList ) {
				bFound = ingredients.toLowerCase().contains(word.toLowerCase());
				if (bFound) {
					break;
					}				
				}
				
				//if eliminated ingredients found in the ingredients of the recipe, continue through the loop
				//don't go ahead for saving in the excel
				if (bFound){
					System.out.println("found in ingredients..so navigating back to lists page..");
					driver.navigate().to(strMainPage);
					
					Thread.sleep(2000);
					continue;
				}
				bFound = false;
				
				//You have reached here, means the recipe does not contain any eliminated ingredients
				//scrape the recipe details
				
				//get preparation time			
				if(listWEPTime.size()>0){
					pTime = listWEPTime.get(0).getText();
				}
				//get cooking time
				if(listWECTime.size()>0){
					cTime = listWECTime.get(0).getText();
				}		
			
				//get preparation method
				preparationMethod = WEPreparationMethod.getText();

				//some recipes do not have nutrition value chart, so check if it's there
				if(listWENValues.size()>0) {
					nutrientValues = listWENValues.get(0).getText();
				}else {
					nutrientValues = "no data";
				}
				
				url = driver.getCurrentUrl();
				
				int iNoOfTags = listWETags.size();
				System.out.println("no of tags : " + iNoOfTags);
				strRecipeCategory = "";
				strFoodCategory = "";
				
				for(z=0;z<iNoOfTags;z++) {
					tagText = listWETags.get(z).getText();
					if (tagText.toLowerCase().contains("breakfast")){
						if(!strRecipeCategory.contains("breakfast")){
							strRecipeCategory = strRecipeCategory + "-breakfast-";
						}
					}
					if (tagText.toLowerCase().contains("lunch")){
						if(!strRecipeCategory.contains("lunch")){
							strRecipeCategory = strRecipeCategory + "-lunch-";
						}
					}
					if (tagText.toLowerCase().contains("snack")){
						if(!strRecipeCategory.contains("snack")){
							strRecipeCategory = strRecipeCategory + "-snack-";
						}
					}
					if (tagText.toLowerCase().contains("dinner")){
						if(!strRecipeCategory.contains("dinner")){
							strRecipeCategory = strRecipeCategory + "-dinner-";
						}	
					}
					if (tagText.toLowerCase().contains("jain")){
						strFoodCategory = strFoodCategory + "-jain-";
					}
					if (tagText.toLowerCase().contains("vegan")){
						strFoodCategory = strFoodCategory + "-vegan-";
					}
				}
				
				if (strFoodCategory.equals("")){
					strFoodCategory = "veg";
				}
				
				
				//Now it's time to save the recipe in the excel
				if(!bFound) {
					Path p = Paths.get(BaseClass.strFilteredRecipes);
					boolean bFileExists = Files.exists(p);
					XSSFWorkbook wb;
					
					//if file already exists, check for the last row number
					//and add a new row at the end
					if(bFileExists) {
					FileInputStream myxls = new FileInputStream(BaseClass.strFilteredRecipes);
					wb = new XSSFWorkbook(myxls);
					
					XSSFSheet sheet = wb.getSheetAt(sheetNo);
					int lastRow=sheet.getLastRowNum();
					row = sheet.createRow(++lastRow);
				}
				
				//if file doesn't exist, 
				//create a new file and add headers as the first row
				else {
					wb = new XSSFWorkbook();
					//CreationHelper ch = wb.getCreationHelper();
					
					if (wb.getNumberOfSheets()<=1) {
						wb.createSheet("Diabetes");
						wb.createSheet("Hypothyroidism");
						wb.createSheet("Hypertension");
						wb.createSheet("PCOS");					
					}
					
					for(z = 0; z<4; z++) {
						XSSFSheet sheet = wb.getSheetAt(z);
						XSSFRow header = sheet.createRow(0);			
						for(int k=0;k<BaseClass.strHeading.length;k++) {
							CellStyle cs = wb.createCellStyle();
							XSSFFont font = wb.createFont();
							font.setColor(IndexedColors.BLUE.getIndex());
							font.setBold(true);
							cs.setFont(font);
							cs.setWrapText(true);
							cell = header.createCell(k);
							cell.setCellStyle(cs);
							cell.setCellValue(BaseClass.strHeading[k]);
						}
					}
		
					XSSFSheet sheet = wb.getSheet(strMorbidity);
					row=sheet.createRow(1);
				}
				
				//add recipe details to the newly added row
				cell=row.createCell(0);
				cell.setCellValue(id);
				cell=row.createCell(1);
				cell.setCellValue(name);
				cell=row.createCell(2);
				cell.setCellValue(strFoodCategory);
				cell=row.createCell(3);
				cell.setCellValue(strRecipeCategory);
				cell=row.createCell(4);
				cell.setCellValue(ingredients);
				cell=row.createCell(5);
				cell.setCellValue(pTime);
				cell=row.createCell(6);
				cell.setCellValue(cTime);
				cell=row.createCell(7);
				cell.setCellValue(preparationMethod);
				cell=row.createCell(8);
				cell.setCellValue(nutrientValues);
				cell=row.createCell(9);
				cell.setCellValue(strMorbidity);
				cell=row.createCell(10);
				cell.setCellValue(url);
				cell=row.createCell(11);
				cell.setCellValue("");
				
				//commit the newly added row to the excel and save the excel file
				FileOutputStream fileOut = new FileOutputStream(BaseClass.strFilteredRecipes);
				wb.write(fileOut);
				fileOut.close();
				wb.close();
				}
				BaseClass.driver.navigate().to(strMainPage);
				Thread.sleep(5000);
			}
		}
	}
