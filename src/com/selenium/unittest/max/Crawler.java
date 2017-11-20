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
import org.testng.annotations.Test;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.summit.util.file.FileProperties;


/**
 * @author THINKPAD
 *
 */
public class Crawler {

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
    public void beforeTest() {
	driver.get("http://max.mataprima.com/dev/admin/source");
    }
    
    @Test()
    public void emptyNameSource(){
	expected = "This field is required.";
	ExtentTest logger = extent.createTest("Test empty name source");
	try {
	    logger.log(Status.INFO, "Go to page Source");
	    driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[2]/button")).click();
	    logger.log(Status.INFO, "Clicked Button Add Source");
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"form_create\"]/div[3]/button[2]"))).click();
	    logger.log(Status.INFO, "Clicked Button Submit");
	    actual = driver.findElement(By.id("crawler-name-error")).getText();
	    assertEquals(actual, expected);
	    logger.log(Status.PASS, "Test Case Success");
   	}catch (Exception e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
//	    Assert.fail(e.getMessage());
	}
    }
    
    @Test()
    public void emptySource() {
	expected="Seems like you didn't choose the source, please make your choice";
	ExtentTest logger = extent.createTest("Test empty source");
	try {
	    logger.log(Status.INFO, "Go to page Source");
	    WebElement nameCrawler,keyword;
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div[1]/div[2]/button"))).click();
	    logger.log(Status.INFO, "Clicked Button Add Source");
	    nameCrawler = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("crawler-name")));
	    nameCrawler.sendKeys("Crawler test");
	    logger.log(Status.INFO, "Value"+ nameCrawler.getAttribute("value")+" is insert in field name crawler ");
	    keyword = driver.findElement(By.id("crawler-keyword"));
	    keyword.sendKeys("Test Keyword");
	    logger.log(Status.INFO, "Value"+ keyword.getAttribute("value")+" is insert in field keyword ");	
    	    driver.findElement(By.xpath("//*[@id=\"form_create\"]/div[3]/button[2]")).click();
	    logger.log(Status.INFO, "Clicked Button Submit");
	    actual = driver.findElement(By.id("create_crawler-source-error")).getText();
	    assertEquals(actual, expected);
	    logger.log(Status.PASS, "Test Case Success");
   	}catch (Exception e) {
	     logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
//	     Assert.fail(e.getMessage());
	}
    }

    @Test()
    public void emptyKeyword() {
	expected="This field is required.";
	ExtentTest logger = extent.createTest("Test empty keyword crawler");
	try {
	    WebElement nameCrawler,source;
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div[1]/div[2]/button"))).click();
	    logger.log(Status.INFO, " Click Button Add");
	    nameCrawler = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("crawler-name")));
	    nameCrawler.sendKeys("Crawler Twitter");
	    logger.log(Status.INFO, nameCrawler.getAttribute("value")+" is insert in field name Source" );
	    source = driver.findElement(By.xpath("//*[@id=\"source-row\"]/div/div/div[1]/div[2]/div/label/input"));
	    source.click();
	    logger.log(Status.INFO, "Source" + source.getAttribute("value")+" is selected");
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"form_create\"]/div[3]/button[2]"))).click();
	    logger.log(Status.INFO, "Click Button Submit");
	    actual = driver.findElement(By.id("crawler-keyword-error")).getText();
	    logger.log(Status.PASS, "Test Case Success");
	}catch (Exception e) {
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
