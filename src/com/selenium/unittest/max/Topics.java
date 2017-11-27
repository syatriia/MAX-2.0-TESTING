/**
 * 
 */
package com.selenium.unittest.max;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.io.File;
import java.io.IOException;

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
public class Topics {

    FileProperties fpurl,fppathreport,fpdriver;
    String pathReport,url,typeDriver,pathDriver,actual,expected;
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
	username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
	password = driver.findElement(By.id("password"));
	username.sendKeys("test");
	password.sendKeys("12345678");
	submit = driver.findElement(By.xpath("//button[@onclick='submitForm()']"));
	submit.click();
	

    }
    
    @BeforeMethod
    public void beforeMethod() {
	driver.get(url+"admin/");
    }
    
    @Test(enabled=false)
    public void notSelectSchemaTemplate(){
   	ExtentTest logger = extent.createTest("Test not select schema template");
   	try {
   	    WebElement buttonAddTopics,buttonNext;
   	    buttonAddTopics = driver.findElement(By.xpath("//button[@type='button']"));
   	    buttonAddTopics.click();
   	    logger.log(Status.INFO, "Cliked Button Add Topics");
   	    Thread.sleep(1000);
   	    logger.log(Status.INFO, "Schema Template is not Selected");
   	    buttonNext = driver.findElement(By.xpath("//form[@id='topic-template']/div[3]/ul/li[2]/a"));
   	    buttonNext.click();
   	    logger.log(Status.INFO, "Cliked Button Add Topics");
   	    String message = driver.findElement(By.name("scheme-template-radio")).getAttribute("validationMessage");
   	    Assert.assertEquals(message, "Please select one of these options.");
   	    logger.log(Status.PASS, "Show Messagge Error : Please select one of these options.");
   	} catch (Exception e) {
   	    logger.log(Status.FAIL, e.getMessage());
   	    Assert.fail(e.getMessage());
   	}
    }
    
    @Test(enabled=false)
    public void notSelectIndustryTemplate(){
   	
   	ExtentTest logger = extent.createTest("Test not select industry template");
   	try {
   	    WebElement buttonAddTopics,buttonNext;
   	    buttonAddTopics = driver.findElement(By.xpath("//button[@type='button']"));
   	    buttonAddTopics.click();
   	    logger.log(Status.INFO, "Cliked Button Add Topics");
   	    Thread.sleep(1000);
   	    logger.log(Status.INFO, "Schema Template is not Selected");
   	    buttonNext = driver.findElement(By.xpath("//form[@id='topic-template']/div[3]/ul/li[2]/a"));
   	    buttonNext.click();
   	    logger.log(Status.INFO, "Cliked Button Add Topics");
   	    String message = driver.findElement(By.name("industry-template-radio")).getAttribute("validationMessage");
   	    Assert.assertEquals(message, "Please select one of these options.");
   	    logger.log(Status.PASS, "Test Case Success : Show message \"This field is required.\"");
   	}catch (Exception e) {
   	    logger.log(Status.FAIL, e.getMessage());
   	    Assert.fail(e.getMessage());
   	}
    }
    
    
    @Test(priority=2) 
    public void emptyNameTopics() {
	expected = "This field is required.";
	ExtentTest logger = extent.createTest("Test empty name topics");
	try {
	    
	WebElement buttonAddTopics, scheme,industry,next;
	buttonAddTopics = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[2]/button/span"));
	buttonAddTopics.click();
	logger.log(Status.INFO, "Click Button Add Topics");
	
	scheme = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='scheme-template-radio']")));
//	industry =  driver.findElement(By.xpath("//input[@name='industry-template-radio']"));
	next =  driver.findElement(By.linkText("Next"));
	scheme.click();
	logger.log(Status.INFO, "Selected Schema Template");
//	industry.click();
//	logger.log(Status.INFO, "Selected Industry Template");
	next.click();
	logger.log(Status.INFO, "Click Button Next");

	logger.log(Status.INFO, "Name Topics Is Empty");
	logger.log(Status.INFO, "Click Button Next");
	next.click();
	actual = driver.findElement(By.id("topic-name-err")).getText();
	assertEquals(actual, expected);
	logger.log(Status.PASS, "Test Case Success : Show message This field is required.");
   	} catch (Exception e) {
   	    logger.log(Status.FAIL, e.getMessage());
   	    Assert.fail(e.getMessage());
   	}
    }
    
    @Test(priority=3)
    public void nameTopicsMoreThan255() throws InterruptedException {
	ExtentTest logger = extent.createTest("Test name topics more than 255 character");
	try {
	    WebElement buttonAddTopics, scheme,industry,next,nameTopics;
	    buttonAddTopics = driver.findElement(By.xpath("//button[@type='button']"));
	    buttonAddTopics.click();
	    logger.log(Status.INFO, "Click Button Add");
	    scheme = wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//input[@name='scheme-template-radio']"))));
//	    industry =  driver.findElement(By.xpath("//input[@name='industry-template-radio']"));
	    nameTopics = driver.findElement(By.id("topics-name"));
	    next =  driver.findElement(By.xpath("//a[contains(text(),'Next')]"));
	    scheme.click();
//	    industry.click();
	    next.click();
    	
	    nameTopics.sendKeys("There is an easy [and easily-overlooked] way around this limitation, though: set your field's data type to Memo. There is an easy [and easily-overlooked] way around this limitation,There is an easy [and easily-overlooked] way around this limitation, though: set your field's data type to Memo. There is an easy [and easily-overlooked] way around this limitation,There is an easy [and easily-overlooked] way around this limitation, though: set your field's data type to Memo. There is an easy [and easily-overlooked] way around this limitation,There is an easy [and easily-overlooked] way around this limitation, though: set your field's data type to Memo. There is an easy [and easily-overlooked] way around this limitation,");
	    logger.log(Status.INFO, nameTopics.getAttribute("value")+" is insert in name topics");
	    next.click();
	    logger.log(Status.INFO, "Cliked button next");
	    int length = nameTopics.getAttribute("value").length();
	    assertTrue(length<255);
	    logger.log(Status.PASS, "Test Case Success : lenght name topics is "+length);
   	} catch (Exception e) {
   	    logger.log(Status.FAIL, e.getMessage());
   	    Assert.fail(e.getMessage());
   	}
    }
    
    @Test
    public void emptySourceTopicsProductReview() {
	expected = "Seems like you didn't choose the source, please make your choice";
	ExtentTest logger = extent.createTest("Test Empty data source when create topics product review");
	try {
	    WebElement buttonAddTopics, scheme,industry,next,nameTopics;
	    buttonAddTopics = driver.findElement(By.xpath("//button[@type='button']"));
	    buttonAddTopics.click();
	    logger.log(Status.INFO, "Click Button Add");
//	    scheme = wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//input[@name='scheme-template-radio']"))));
	    scheme = wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//*[@id=\"scheme-template-row\"]/div/div/div/div[1]/div/label/input"))));
//	    industry =  driver.findElement(By.xpath("//input[@name='industry-template-radio']"));
//	    industry =  driver.findElement(By.xpath("//*[@id=\"industry-template-row\"]/div/div/div/div[2]/div/label/input"));
	    next =  driver.findElement(By.xpath("//a[contains(text(),'Next')]"));
	    scheme.click();
//	    industry.click();
	    next.click();
//    	
	    nameTopics = driver.findElement(By.id("topics-name"));
	    nameTopics.sendKeys("test topics product review");
	    
	    next.click();
	    logger.log(Status.INFO, "Cliked button next");
	    next.click();
	    actual = driver.findElement(By.id("crawler-source-error")).getText();
	    assertEquals(actual, expected);
   	} catch (Exception e) {
   	    logger.log(Status.FAIL, e.getMessage());
   	    Assert.fail(e.getMessage());
   	}
    }
    
    @Test
    public void emptyKeywordTopicsProductReview() {
	expected = "This field is required.";
	ExtentTest logger = extent.createTest("Test Empty data source when create topics product review");
	try {
	    WebElement buttonAddTopics,nameTopics,scheme,industry,next,source;
	    buttonAddTopics = driver.findElement(By.xpath("//button[@type='button']"));
	    buttonAddTopics.click();
	    logger.log(Status.INFO, "Click Button Add");
//	    scheme = wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//input[@name='scheme-template-radio']"))));
	    scheme = wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//*[@id=\"scheme-template-row\"]/div/div/div/div[1]/div/label/input"))));
//	    industry =  driver.findElement(By.xpath("//input[@name='industry-template-radio']"));
//	    industry =  driver.findElement(By.xpath("//*[@id=\"industry-template-row\"]/div/div/div/div[2]/div/label/input"));
	    next =  driver.findElement(By.xpath("//a[contains(text(),'Next')]"));
	    scheme.click();
//	    industry.click();
	    next.click();
//    	
	    nameTopics = driver.findElement(By.id("topics-name"));
	    nameTopics.sendKeys("test topics product review");
	    next.click();
	    source = driver.findElement(By.id("source-twitter"));
	    source.click();
	    logger.log(Status.INFO, "Source Twitter is selected");
	    
	    next.click();
	    logger.log(Status.INFO, "Cliked button next");
	    actual = driver.findElement(By.id("label-keyword-err")).getText();
	    assertEquals(actual, expected);
	}catch (Exception e) {
	    logger.log(Status.FAIL, e.getMessage());
   	    Assert.fail(e.getMessage());
	}
	
    }
    
    @Test
    public void emptyBrandKeywordTopicsProductReview() {
	expected = "This field is required.";
	ExtentTest logger = extent.createTest("Test Empty brand keyword when create topics product review");
	try {
	    WebElement buttonAddTopics,nameTopics,scheme,industry,next,source,keyword;
	    buttonAddTopics = driver.findElement(By.xpath("//button[@type='button']"));
	    buttonAddTopics.click();
	    logger.log(Status.INFO, "Click Button Add");
//	    scheme = wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//input[@name='scheme-template-radio']"))));
	    scheme = wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//*[@id=\"scheme-template-row\"]/div/div/div/div[1]/div/label/input"))));
//	    industry =  driver.findElement(By.xpath("//input[@name='industry-template-radio']"));
//	    industry =  driver.findElement(By.xpath("//*[@id=\"industry-template-row\"]/div/div/div/div[2]/div/label/input"));
	    next =  driver.findElement(By.xpath("//a[contains(text(),'Next')]"));
	    scheme.click();
//	    industry.click();
	    next.click();
//    	
	    nameTopics = driver.findElement(By.id("topics-name"));
	    nameTopics.sendKeys("test topics product review");
	    next.click();	
	    source = driver.findElement(By.id("source-twitter"));
	    source.click();
	    logger.log(Status.INFO, "Source Twitter is selected");
	    next.click();
	    keyword = driver.findElement(By.id("main-keyword"));
	    keyword.sendKeys("test keyword");
	    logger.log(Status.INFO, "");
	    next.click();
	    logger.log(Status.INFO, "Cliked button next");
	    next.click();
	    
	    actual = driver.findElement(By.id("brandName-error")).getText();
	    assertEquals(actual, expected);
	}catch (Exception e) {
	    logger.log(Status.FAIL, e.getMessage());
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
