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
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
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
public class UpdateProfile {

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
	username.sendKeys("syatria");
	password.sendKeys("11syatriia");
	
	submit = driver.findElement(By.xpath("//button[@onclick='submitForm()']"));
	submit.click();
	
	WebElement user,changeProfil;
	user = driver.findElement(By.xpath("//*[@id=\"navbar-collapse\"]/ul/li[2]/a"));
	changeProfil = driver.findElement(By.xpath("//*[@id=\"navbar-collapse\"]/ul/li[2]/ul/li[1]/a"));
	user.click();
	changeProfil.click();
	currenturl = driver.getCurrentUrl();
    }
    
    
    public void beforeMethod() {
	driver.get(currenturl);
    }
    
    @Test(priority=1)
    public void emptyEmail() {
	expected = "This field is required.";
	ExtentTest logger = extent.createTest("Test empty email");
	try {
	    WebElement email,submit;
	    email = driver.findElement(By.id("email"));
	    email.clear();
	    submit = driver.findElement(By.linkText("Next"));
	    logger.log(Status.INFO, "Click Button Next");
	    submit.click();
	    actual = driver.findElement(By.id("email-error")).getText();
	    assertEquals(actual, expected);
	    logger.log(Status.PASS, "Show message : Please fill out this field. ");
	    
	}catch (NoSuchElementException e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	    Assert.fail(e.getMessage());
	}
    }
    
    @Test(priority=2)
    public void inValidEmail() {
	expected = "Please enter a valid email address.";
   	ExtentTest logger = extent.createTest("Test invalid email");
   	try {
   	    WebElement email;
   	    email = driver.findElement(By.id("email"));
   	    email.clear();
   	    email.sendKeys("test.com");
   	    logger.log(Status.INFO, email.getAttribute("value")+" is insert in filed email ");
   	    actual = driver.findElement(By.id("email-error")).getText();
   	    assertEquals(actual, expected);
	    logger.log(Status.PASS, "Show message :  Please enter a valid email address");
	}catch (Exception e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	    Assert.fail(e.getMessage());
	}
    }
    
    @Test(priority=3)
    public void emptyNophones() {
	expected = "This field is required.";
	ExtentTest logger = extent.createTest("Test Empty No phones");
	try {
	    WebElement nophones,submit;
	    nophones = driver.findElement(By.id("phone"));
	    nophones.clear();
	    submit = driver.findElement(By.linkText("Next"));
	    logger.log(Status.INFO, "Click Button Next");
	    submit.click();
	    actual = driver.findElement(By.id("phone-error")).getText();
	    assertEquals(actual, expected);
	    logger.log(Status.PASS, "Show message : Please fill out this field. ");
	}catch (NoSuchElementException e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	    Assert.fail(e.getMessage());
	}
    }
    
    
    @Test(priority=4)
    public void inValidNophones() {
	expected = "only numerical character allowed.";
	ExtentTest logger = extent.createTest("Test Invalid No phones");
	try {
	    WebElement nophones,submit;
	    nophones = driver.findElement(By.id("phone"));
	    nophones.clear();
	    nophones.sendKeys("123abcd");
	    logger.log(Status.INFO, nophones.getAttribute("value")+" is insert in filed nophones ");
	    submit = driver.findElement(By.linkText("Next"));
	    submit.click();
	    actual = driver.findElement(By.id("phone-error")).getText();
	    assertEquals(actual, expected);
	    logger.log(Status.PASS, "Show message : only numerical character allowed.");
	}catch (NoSuchElementException e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	    Assert.fail(e.getMessage());
	}
    }
    
    @Test(priority=5)
    public void emptyFirstname() {
	expected = "This field is required.";
	ExtentTest logger = extent.createTest("Test Empty FirstName");
	WebElement next;
	try {
	    next = driver.findElement(By.linkText("Next"));
	    next.click();
	    WebElement firstname = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstname")));
	    firstname.clear();
	    logger.log(Status.INFO, "Field firstname is empty");
	    driver.findElement(By.linkText("Finish")).click();
	    actual = driver.findElement(By.id("firstname-error")).getText();
	    assertEquals(actual, expected);
	    logger.log(Status.PASS, "Show message : This field is required.");
	}catch (NoSuchElementException e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	    Assert.fail(e.getMessage());
	}
    }
    
    @Test(priority=6)
    public void emptyLastname() {
	expected = "This field is required.";
	ExtentTest logger = extent.createTest("Test Empty LastName");
	WebElement next;
	try {
	    next = driver.findElement(By.linkText("Next"));
	    next.click();
	    logger.log(Status.INFO, "Navigate to Form Personal Info");
	    WebElement lastname = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lastname")));
	    lastname.clear();
	    logger.log(Status.INFO, "Field lastname is empty");
	    driver.findElement(By.linkText("Finish")).click();
	    logger.log(Status.INFO, "Click Next Button");
	    actual = driver.findElement(By.id("lastname-error")).getText();
	    assertEquals(actual, expected);
	    logger.log(Status.PASS, "Show message : Please fill out this field. ");
	}catch (NoSuchElementException e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	    Assert.fail(e.getMessage());
	}
    }
    
    @Test(priority=7)
    public void emptyAddress() {
	expected = "This field is required.";
	ExtentTest logger = extent.createTest("Test Empty Address");
	WebElement next;
	try {
	    next = driver.findElement(By.linkText("Next"));
	    next.click();
	    logger.log(Status.INFO, "Navigate to Form Personal Info");
	    WebElement address = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("address")));
	    address.clear();
	    logger.log(Status.INFO, "Field Address is empty");
	    driver.findElement(By.linkText("Finish")).click();
	    logger.log(Status.INFO, "Click Button Next");
	    actual = driver.findElement(By.id("address-error")).getText();
	    assertEquals(actual, expected);
	    logger.log(Status.PASS, "Show message : This field is required.");
   	}catch (AssertionError e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	    Assert.fail(e.getMessage());
	}catch (NoSuchElementException e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	    Assert.fail(e.getMessage());
	}
    }
    
    @Test(priority=8)
    public void emptyOrganization() {
	expected = "This field is required.";
	ExtentTest logger = extent.createTest("Test Empty Organization");
	WebElement next;
	try {
	    next = driver.findElement(By.linkText("Next"));
	    next.click();
	    logger.log(Status.INFO, "Navigate to Form Personal Info");
	    WebElement organization = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("organization")));
	    organization.clear();
	    logger.log(Status.INFO, "Field Organization is empty");
	    driver.findElement(By.linkText("Finish")).click();
	    logger.log(Status.INFO, "Click Button Next");
	    actual = driver.findElement(By.id("organization-error")).getText();
	    assertEquals(actual, expected);
	    logger.log(Status.PASS, "Show message : This field is required.");
	}catch (NoSuchElementException e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	    Assert.fail(e.getMessage());
	}
    }
    
    @Test(priority=9)
    public void emptySelectCountry() {
	expected = "This field is required.";
	ExtentTest logger = extent.createTest("Test Empty Select Country");
	WebElement next;
	try {
	    next = driver.findElement(By.linkText("Next"));
	    next.click();
	    logger.log(Status.INFO, "Navigate to Form Personal Info");
	    Select country = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("country"))));
	    country.selectByIndex(0);
	    logger.log(Status.INFO, "Select Country is Empty");
	    driver.findElement(By.linkText("Finish")).click();
	    logger.log(Status.INFO, "Click Button Next");
	    actual = driver.findElement(By.id("country-error")).getText();
	    assertEquals(actual, expected);
	    logger.log(Status.PASS, "Show message : This field is required.");
	}catch (NoSuchElementException e) {
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
