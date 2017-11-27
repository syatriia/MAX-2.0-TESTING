/**
 * 
 */
package com.selenium.integrationtest.max;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
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
	String path = System.getProperty("user.dir")+"\\resources\\";
	fpurl = new FileProperties(path+"url.properties");
	fpdriver = new FileProperties(path+"driver.properties");
	fppathreport = new FileProperties(path+"pathreport.properties");
	url = fpurl.getProperties("finalurl");
	pathReport = fppathreport.getProperties("integrationpathlog");	
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
	
    }
    
    @BeforeMethod
    public void beforeTest() {
	
    }
    
    @Test(priority=1)
    public void createNewTopic() {
	ExtentTest logger = extent.createTest("Test Create New Topics");
   	try {
   	    WebElement buttonAddTopics, scheme,industry,next,nameTopics;
   	    buttonAddTopics = driver.findElement(By.xpath("//button[@type='button']"));
 	
   	    buttonAddTopics.click();
   	    logger.log(Status.INFO, "Click Button Add");
   	    Thread.sleep(1000);
 	
   	    scheme = driver.findElement(By.xpath("//input[@name='scheme-template-radio']"));
   	    industry =  driver.findElement(By.xpath("//input[@name='industry-template-radio']"));
   	    nameTopics = driver.findElement(By.id("topics-name"));
   	    next =  driver.findElement(By.xpath("//a[contains(text(),'Next')]"));
   	    scheme.click();
   	    industry.click();
   	    next.click();
 	
   	    nameTopics.sendKeys("Pilkada");
   	    next.click();
   	    assertEquals("", "");
   	} catch (AssertionError e) {
   	    logger.log(Status.FAIL, e.getMessage());
   	    Assert.fail(e.getMessage());
   	} catch (Exception e) {
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
