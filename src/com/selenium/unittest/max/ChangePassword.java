/**
 * 
 */
package com.selenium.unittest.max;



import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
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
public class ChangePassword {
    
    FileProperties fpurl,fppathreport,fpdriver;
    String pathReport,url,typeDriver,pathDriver,actual,expected,currenturl;
    WebDriver driver;
    WebDriverWait wait;
    ExtentHtmlReporter htmlReporter;
    ExtentReports extent;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd hh.mm");
    
    @Parameters("browser")
    @BeforeTest
    public void beforeTest(String browser) {
	fpurl = new FileProperties("url.properties");
	fpdriver = new FileProperties("driver.properties");
	fppathreport = new FileProperties("pathreport.properties");
	url = fpurl.getProperties("finalurl");
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
	
	driver.get(url+"login");
	
	WebElement username,password,submit;
	username = driver.findElement(By.id("username"));
	password = driver.findElement(By.id("password"));
	username.sendKeys("syatriia");
	password.sendKeys("11syatriia");
	
	submit = driver.findElement(By.xpath("//button[@onclick='submitForm()']"));
	submit.click();
	
	WebElement user,changePass;
	user = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='navbar-collapse']/ul/li/a")));
	changePass = driver.findElement(By.xpath("//div[@id='navbar-collapse']/ul/li/ul/li[2]/a"));
	user.click();
	changePass.click();
	currenturl = driver.getCurrentUrl();
    }
        
    @BeforeTest
    public void beforeTest() {
	driver.get(currenturl);
    }
    
    @Test(priority=1)
    public void emptyNewPassword() {
	expected="Please fill out this field.";
	ExtentTest logger = extent.createTest("Test empty new password");
	try {
	    
	    logger.log(Status.INFO, "Navigate to Page Change Password");
	    WebElement submit;
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-password")));
	    logger.log(Status.INFO, "Empty Field new Password ");
	    submit = driver.findElement(By.id("submit-new-password"));
	    submit.click();
	    logger.log(Status.INFO, "Click Submit");
	    actual = driver.findElement(By.id("err-span-newpass")).getText();
	    assertEquals(actual, expected);
	    logger.log(Status.PASS, "Show message :  Please fill out this field.");
	   
	}catch (NoSuchElementException e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	   
	}
    }
    
    @Test(priority=3)
    public void emptyRetypePassword() {
	expected = "Please fill out this field.";
	ExtentTest logger = extent.createTest("Test new Password is empty");
	try {
	    logger.log(Status.INFO, "Navigate to Page Change Password");
	    WebElement password,submit;
	    password =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-password")));
	    password.sendKeys("12345678");
	    logger.log(Status.INFO, password.getText() + " is insert in field new password ");
	    logger.log(Status.INFO, "Field Retpe Password is Empty");
	    submit = driver.findElement(By.id("submit-new-password"));
	    submit.click();
	    logger.log(Status.INFO, "Click Submit");
	    actual = driver.findElement(By.id("err-span-retnewpass")).getText();
	    assertEquals(actual, expected);
	    logger.log(Status.PASS, "Show message :  Please fill out this field.");
	}catch (AssertionError e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	   
	}catch (NoSuchElementException e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	   
	}
    }	

    @Test(priority=2)
    public void paswordless8char() {
	ExtentTest logger = extent.createTest("Test new Password Less Than 8 Character");
	try {
	    logger.log(Status.INFO, "Navigated to register page");
	    WebElement password,submit;
	    password =  driver.findElement(By.id("new-password"));
	    password.sendKeys("abc1234");
	    logger.log(Status.INFO, password.getText()+" is insert in filed password ");
	    submit = driver.findElement(By.id("submit-new-password"));
	    submit.click();
	    logger.log(Status.INFO, "Click Button Submit");
	    String message = password.getAttribute("validationMessage");
	    assertTrue(message.contains("Please lengthen this text to 8 characters or more"));
	    logger.log(Status.PASS, "Show message : Please enter at least 8 characters");
	}catch (NoSuchElementException e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	}
    }
    
    @Test(priority=4)
    public void notMatchNewPassword() {
	expected="* password not match";
	ExtentTest logger = extent.createTest("Test new Password is Not Match");
	try {
	    WebElement password,retype,submit;
	    password =  driver.findElement(By.id("new-password"));
	    password.sendKeys("abcd1234");
	    logger.log(Status.INFO, password.getAttribute("value")+" is insert in filed password ");
	    retype =  driver.findElement(By.id("retype-new-password"));
	    retype.sendKeys("1234abcd");
	    logger.log(Status.INFO, retype.getAttribute("value")+" is insert in filed password ");
	    submit = driver.findElement(By.id("submit-new-password"));
	    submit.click();
	    logger.log(Status.INFO, "Click Button Submit");
	    actual = driver.findElement(By.id("span-notmatch")).getText();
	    assertEquals(actual, expected);
	    logger.log(Status.PASS, "Show message : Password not match");
	}catch (NoSuchElementException e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	   
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
