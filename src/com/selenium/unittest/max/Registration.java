/**
 * 
 */
package com.selenium.unittest.max;


import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.summit.util.file.FileProperties;

/**
 * @author THINKPAD
 *
 */
public class Registration {

    FileProperties fpurl,fpdriver,fppathreport;
    String url,pathReport,typeDriver,pathDriver,expected,actual;
    WebDriver driver;
    WebDriverWait wait;
    ExtentHtmlReporter htmlReporter;
    ExtentReports extent;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh.mm");
    
    
    @Parameters("browser")
    @BeforeTest
    public void prastartTest(String browser) {
	
	fpurl = new FileProperties("url.properties");
	fpdriver = new FileProperties("driver.properties");
	fppathreport = new FileProperties("pathreport.properties");
	url = fpurl.getProperties("devurl");
	pathReport = fppathreport.getProperties("unitpathlog");
	pathDriver =  fpdriver.getProperties("chromedriver");
	typeDriver = fpdriver.getProperties("chromewebdriver");
	
	if(browser.equals("chrome")) {
	    typeDriver =  fpdriver.getProperties("chromewebdriver");
	    pathDriver = fpdriver.getProperties("pathchromedriver");
	    System.setProperty(typeDriver,pathDriver);
	    driver =  new ChromeDriver();
	    driver.manage().window().maximize();
	}else if(browser.equals("firefox")) {
	    typeDriver =  fpdriver.getProperties("firefoxwebdriver");
	    pathDriver = fpdriver.getProperties("pathfirefoxdriver");
	    System.setProperty(typeDriver,pathDriver);
	    driver = new FirefoxDriver();
	}else if(browser.equals("ie")) {
	    typeDriver =  fpdriver.getProperties("iewebdriver");
	    pathDriver = fpdriver.getProperties("pathiedriver");
	    System.setProperty(typeDriver,pathDriver);
	    driver = new InternetExplorerDriver();
	}
	
	File file = new File(pathReport);
	if(!file.exists()) {
	    file.mkdirs();
	}
	htmlReporter = new ExtentHtmlReporter(pathReport+this.getClass().getSimpleName()+"_log_"+df.format(new Date())+".html" );
	wait = new WebDriverWait(driver, 10);

	extent = new ExtentReports();
	extent.attachReporter(htmlReporter);
    }
    
    @BeforeMethod
    public void beforeMethod() {
	driver.get(url+"register");
    }
    
    @Test(priority=0)
    public void emptyEmail() {
	expected = "Please fill out this field.";
	ExtentTest logger = extent.createTest("Test empty email");
	logger.log(Status.INFO, "Browser Launched");
	try {
	    logger.log(Status.INFO, "Navigated to register page");
	    WebElement submit;
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
	    submit = driver.findElement(By.linkText("Next"));
	    logger.log(Status.INFO, "Click Button Next");
	    submit.click();
	    
	    expected = driver.findElement(By.id("email-error")).getText();
	    assertEquals(actual, expected);
	    logger.log(Status.PASS,  "Show message : This field is required");
	}catch (Exception e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	}
    }

    @Test(priority=1)
    public void inValidEmail() {
	expected = "Please enter a valid email address.";
   	ExtentTest logger = extent.createTest("Test invalid email");
   	try {
   	    WebElement email,submit;
   	    email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
   	    email.sendKeys("test.com");
   	    logger.log(Status.INFO, email.getAttribute("value")+" is insert in filed email ");
   	    submit = driver.findElement(By.linkText("Next"));
   	    submit.click();
   	    logger.log(Status.INFO, "Click Button Next");
   	    
   	    actual = driver.findElement(By.id("email-error")).getText();
   	    assertEquals(actual, expected);
   	    logger.log(Status.PASS, "Show message :  Please enter a valid email address");
   	}catch (Exception e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	}
    }
    
    @Test(priority=2)
    public void emptyNophones() {
	expected = "This field is required.";
	ExtentTest logger = extent.createTest("Test Empty No phones");
	logger.log(Status.INFO, "Navigated to register page");
	try {
	    WebElement email,submit;
	    email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
   	    email.sendKeys("test@test.com");
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("phones")));
	    submit = driver.findElement(By.linkText("Next"));
	    logger.log(Status.INFO, "Click Button Next");
	    submit.click();

	    actual = driver.findElement(By.id("phones-error")).getText();
	    assertEquals(actual,expected);
	    logger.log(Status.PASS, "Show message : This field is required");
	}catch (Exception e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	    Assert.fail(e.getMessage());
	}
    }
    
    
    @Test(priority=3)
    public void inValidNophones() {
	expected = "only numerical character allowed.";
	ExtentTest logger = extent.createTest("Test Invalid No phones");
	try {
	    WebElement nophones,submit;
	    nophones = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("phones")));
	    nophones.sendKeys("123abcd");
	    logger.log(Status.INFO, nophones.getAttribute("value")+" is insert in filed nophones ");
	    submit = driver.findElement(By.linkText("Next"));
	    submit.click();
    
	    actual = driver.findElement(By.id("invalid-phones")).getText();
	    assertEquals(actual,expected);
	    logger.log(Status.PASS, "Show message : only numerical character allowed.");
	}catch (Exception e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	    Assert.fail(e.getMessage());
	}
    }
//    
//    
    @Test(priority=4)
    public void emptyUsername() {
	expected = "This field is required.";
	ExtentTest logger = extent.createTest("Test Empty Username");
	logger.log(Status.INFO, "Navigated to register page");
	try {
	    WebElement submit;
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
	    submit = driver.findElement(By.linkText("Next"));
	    logger.log(Status.INFO, "Click Button Next");
	    submit.click();
	    actual = driver.findElement(By.id("username-error")).getText();
	    assertEquals(actual,expected);
	    logger.log(Status.PASS, "Show message : This field is required");
	}catch (Exception e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	    Assert.fail(e.getMessage());
	}
    }
//    
    @Test(priority=5)
    public void emptyPasword() {
	expected = "Please fill out this field.";
	ExtentTest logger = extent.createTest("Test Empty Password");
	logger.log(Status.INFO, "Browser Launched");
	try {
	    logger.log(Status.INFO, "Navigated to register page");
	    WebElement submit;;
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
	    submit = driver.findElement(By.linkText("Next"));
	    logger.log(Status.INFO, "Click Button Next");
	    submit.click();
	    actual = driver.findElement(By.id("password-error")).getText();
	    assertEquals(actual,expected);
	    logger.log(Status.PASS, "Show message : This field is required");
	}catch (Exception e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	    Assert.fail(e.getMessage());
	}
    }
    
    @Test(priority=6)
    public void paswordless8char() {
	expected = "Please enter at least 8 characters.";
	ExtentTest logger = extent.createTest("Test Password Less Than 8 Character");
	try {
	    WebElement password;
	    password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
	    password.sendKeys("abc1234");
	    logger.log(Status.INFO, password.getAttribute("value")+" is insert in filed password ");
	    actual = driver.findElement(By.id("password-error")).getText();
	    assertEquals(actual, expected);
	    logger.log(Status.PASS, "Show message : Please enter at least 8 characters");
	}catch (Exception e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	    Assert.fail(e.getMessage());
	}
    }
    
    @Test(priority=7)
    public void emptyRetypePassword() {
	expected = "Please fill out this field.";
	ExtentTest logger = extent.createTest("Test Empty Retype Password");
	try {
	    WebElement password,submit;
	    password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
	    password.sendKeys("abc12345");
	    logger.log(Status.INFO, password.getAttribute("value")+" is insert in filed password ");
	    driver.findElement(By.id("retype"));
	    submit = driver.findElement(By.linkText("Next"));
	    submit.click();
	    
	    actual = driver.findElement(By.id("retype-error")).getText();
	    assertEquals(actual,expected);
	    logger.log(Status.PASS, "Show message : This field is required");
	}catch (Exception e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	    Assert.fail(e.getMessage());
	}
    }
//    
    @Test(priority=8)
    public void verifyRetypePassword()  {
	expected = "Please enter the same value again.";
	ExtentTest logger = extent.createTest("Test Verify Retype Password");
	try {
	    WebElement password,retype,submit;
	    password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
	    password.sendKeys("abc12345");
	    logger.log(Status.INFO, password.getAttribute("value")+" is insert in filed password ");
	    retype =  driver.findElement(By.id("retype"));
	    retype.sendKeys("12345abc");
	    logger.log(Status.INFO, retype.getAttribute("value")+" is insert in filed retype-password ");
	    submit = driver.findElement(By.linkText("Next"));
	    logger.log(Status.INFO, "Clicked");
	    submit.click();
		
	    actual = driver.findElement(By.id("retype-error")).getText();
	    assertEquals(actual,expected);
	    logger.log(Status.PASS, "Show message : Password not match");
	}catch (Exception e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	    Assert.fail(e.getMessage());
	}
    }
//    
//    @Test(priority=9)
    @Test(enabled=false)
    public void selectPlan() {
	ExtentTest logger = extent.createTest("Test Select Plan");
	String message = "";
	try {
	    logger.log(Status.INFO, "Browser Launched");
	    
	    
	    logger.log(Status.INFO, "Navigated to register page");
//	    Select oSelect = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("plan"))));
////	    oSelect.selectByIndex(2);
//	    logger.log(Status.INFO, "Plan "+oSelect.getFirstSelectedOption().getText()+ " is selected");
	    
//	    WebElement plan = driver.findElement(By.id("plan"));
//	    List<WebElement> select = plan.findElements(By.tagName("option"));
//	    for (WebElement option : select) {
//		if (option.isSelected()) {
//		    message = oSelect.getFirstSelectedOption().getText();
//		    logger.log(Status.INFO, "Plan "+message+ "  is selected");
//		}
//	    }
	    assertEquals(message, "Professional");
	    logger.log(Status.PASS, "Value field plan is Changed ");
	}catch (Exception e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	    Assert.fail(e.getMessage());
	}
    }
//
    public void fillAccountInfo(ExtentTest logger) {

	WebElement email,username,password,retype,phones;
	
	email = driver.findElement(By.id("email"));
	username = driver.findElement(By.id("username"));
	password = driver.findElement(By.id("password"));
	retype =  driver.findElement(By.id("retype"));
	phones =  driver.findElement(By.id("phones"));
	
	email.sendKeys("20oktc123456789@gmail.com");
	logger.log(Status.INFO, email.getAttribute("value")+" is insert in filed email ");
	username.sendKeys("test125152");
	logger.log(Status.INFO, username.getAttribute("value")+" is insert in filed username ");
	password.sendKeys("12345678");
	logger.log(Status.INFO, password.getAttribute("value")+" is insert in filed password ");
	retype.sendKeys("12345678");
	logger.log(Status.INFO, retype.getAttribute("value")+" is insert in filed retype-password ");
	phones.sendKeys("08123456789");
	logger.log(Status.INFO, phones.getAttribute("value")+" is insert in filed no phone ");
	
////	Select oSelect = new Select(driver.findElement(By.id("plan")));
////	oSelect.selectByIndex(2);
////	logger.log(Status.INFO, oSelect.getFirstSelectedOption().getText()+ "is select in select Plan ");
    }
//
//

    @Test(priority=10)
    public void emptyFirstname() {
	expected = "This field is required.";
	ExtentTest logger = extent.createTest("Test Empty FirstName");
	WebElement next;
	fillAccountInfo(logger);
	try {
	    next = driver.findElement(By.linkText("Next"));
	    next.click();
	    logger.log(Status.INFO, "Navigate to Form Personal Info");
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstname")));
	    logger.log(Status.INFO, "Field firstname is empty");
	    next.click();
	    
	    actual = driver.findElement(By.id("firstname-error")).getText();
	    assertEquals(actual,expected);
	    logger.log(Status.PASS, "Show message : This field is required");
	}catch (Exception e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	    Assert.fail(e.getMessage());
	}
    }
    
    @Test(priority=11)
    public void emptyLastname() {
	expected = "This field is required.";
	ExtentTest logger = extent.createTest("Test Empty LastName");
	fillAccountInfo(logger);
	WebElement next;
	try {
	    next = driver.findElement(By.linkText("Next"));
	    next.click();
	    logger.log(Status.INFO, "Navigate to Form Personal Info");
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lastname")));
	    logger.log(Status.INFO, "Field lastname is empty");
	    next.click();
	    
	    logger.log(Status.INFO, "Click Next Button");
	    actual = driver.findElement(By.id("lastname-error")).getText();
	    assertEquals(actual,expected);
	    logger.log(Status.PASS, "Show message : This field is required");
	
	}catch (Exception e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	    Assert.fail(e.getMessage());
	}
    }
//    
    @Test(priority=12)
    public void emptyAddress() {
	expected = "This field is required.";
	ExtentTest logger = extent.createTest("Test Empty Address");
	fillAccountInfo(logger);
	WebElement next;
	try {
	    next = driver.findElement(By.linkText("Next"));
	    next.click();
	    logger.log(Status.INFO, "Navigate to Form Personal Info");
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("address")));
	    logger.log(Status.INFO, "Field Address is empty");
	    next.click();
	    
	    logger.log(Status.INFO, "Click Button Next");
	    actual = driver.findElement(By.id("address-error")).getText();
	    assertEquals(actual,expected);
	    logger.log(Status.PASS, "Show message : This field is required");
	}catch (Exception e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	    Assert.fail(e.getMessage());
	}
    }
//    
    @Test(priority=13)
    public void emptyOrganization() {
	expected = "This field is required.";
	ExtentTest logger = extent.createTest("Test Empty Organization");
	fillAccountInfo(logger);
	WebElement next;
	try {
	    next = driver.findElement(By.linkText("Next"));
	    next.click();
	    logger.log(Status.INFO, "Navigate to Form Personal Info");
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("organization")));
	    logger.log(Status.INFO, "Field Organization is empty");
	    next.click();
	    
	    logger.log(Status.INFO, "Click Button Next");
	    actual = driver.findElement(By.id("organization-error")).getText();
	    assertEquals(actual,expected);
	    logger.log(Status.PASS, "Show message : This field is required");
	
	}catch (Exception e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	    Assert.fail(e.getMessage());
	}
    }
//    
    @Test(priority=14)
    public void emptySelectCountry() {
	expected = "This field is required.";
	ExtentTest logger = extent.createTest("Test Empty Select Country");
	fillAccountInfo(logger);
	WebElement next;
	try {
	    logger.log(Status.INFO, "Browser Launched");
	    
	    logger.log(Status.INFO, "Navigated to register page");
	    next = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Next")));
	    driver.findElement(By.id("country"));
	    next.click();
	    
	    actual = driver.findElement(By.id("country-error")).getText();

//	    Select oSelect = new Select(driver.findElement(By.id("country")));
//	    oSelect.selectByValue("Indonesia");
//	    System.out.println(oSelect.getFirstSelectedOption().getText());
	    
	    assertEquals(actual,expected);
	    logger.log(Status.PASS, "Show message : This field is required");
	}catch (Exception e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	    Assert.fail(e.getMessage());
	}
    }
    
    @Parameters("browser")
    @AfterTest
    public void getResult(String browser) {
	if(browser.equals("chrome")) {
	    driver.close();
	    driver.quit();
	}else if(browser.equals("firefox")) {
	    driver.quit();
	}else if(browser.equals("ie")) {
	    try {
		Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
		Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
		} catch (IOException e) {
		e.printStackTrace();
		}
	}
	extent.flush();
    }
}
