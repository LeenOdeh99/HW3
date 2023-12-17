package HW3;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

//import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;

public class HW3 {
	Row [] data;
	SoftAssert softAssertion = new SoftAssert();
	private WebDriver driver;
	
	public void test (int index) throws InterruptedException {
		String email, password;
		  Thread.sleep(5000);
		  WebElement emailField = driver.findElement(By.cssSelector("#gwt-sign-in-modal"));
		  WebElement passwordField = driver.findElement(By.id("passwordReset"));
		  WebElement loginButton = driver.findElement(By.id("logonButton"));
		  emailField.clear();
		  passwordField.clear();
		  //Row row = sheet.getRow(i);
		  email = data[index].getCell(0).getStringCellValue();
		  password = data[index].getCell(1) != null ? data[index].getCell(1).getStringCellValue() : "";
		  emailField.sendKeys(email);
	      passwordField.sendKeys(password);
	      loginButton.click();
	      Thread.sleep(3000);
	      boolean message = false;
	      try {
	    	  WebElement errorMessage = driver.findElement(By.cssSelector(".gwt-Label.errortxt"));
		      message = errorMessage.getText().isEmpty() ? false : true;
		      Assert.assertFalse(message, "Unabe to Login");
	      }catch (Exception e) {
	    	  Assert.assertFalse(message,"Unabe to Login");
	      }
	}
	  @Test
	  public void a() throws Exception {
		  test(0);
	  }
	  
	  @Test
	  public void b() throws Exception {
		  test(1);
	  }
	  
	  @Test
	  public void c() throws Exception {
		  test(2);		
	  }
	  private Row [] readExcel() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		  Row [] testData = new Row[3];
		  String sheetName = "inputData";
		  String filePath = System.getProperty("user.dir")+"\\src\\HW3\\inputData.xlsx";
		  File file = new File(filePath);
		  FileInputStream inputStream = new FileInputStream(file);
		  Workbook workbook = new XSSFWorkbook(inputStream);
		  org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheet(sheetName);
		  for (int i = 1; i < 4; i++) {
			  testData[i-1]= sheet.getRow(i);      
		  }
		  return testData;
		
	}
	@BeforeMethod
	  public void beforeMethod() throws IOException, InterruptedException {
		  System.setProperty("webdriver.chrome.driver", "C:\\New folder\\chromedriver.exe");
	      driver = new ChromeDriver();
	      driver.manage().window().maximize();
	      driver.get("https://devwcs.frontgate.com/ShoppingCartView");
		  WebElement advanced = driver.findElement(By.id("details-button"));
		  advanced.click();
		  
		  WebElement proceed = driver.findElement(By.id("proceed-link"));
		  proceed.click();
		  data = readExcel();
	  }

	  @AfterMethod
	  public void afterMethod() {
	  }
}
