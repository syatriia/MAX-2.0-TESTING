/**
 * 
 */
package com.selenium.integrationtest.max;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.summit.util.file.FileProperties;

public class Login {
    
    
    FileProperties fpurl,fpdriver,fppathreport;
    String url,pathReport,typeDriver,webdriver;
    WebDriver driver;
    ExtentHtmlReporter htmlReporter;
    ExtentReports extent;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh.mm");
    
    public Login() {
	fpurl = new FileProperties("url.properties");
	fppathreport = new FileProperties("pathreport.properties");
	fpdriver =  new FileProperties("driver.properties");
	url = fpurl.getProperties("finalurl");
	pathReport = fppathreport.getProperties("integrationpathlog");
	typeDriver =  fpdriver.getProperties("firefoxdriver");
	webdriver = fpdriver.getProperties("firefoxwebdriver");
    }
    
    @BeforeTest
    public void prastartTest() throws InterruptedException {
//	File file = new File(pathReport);
//	if(!file.exists()) {
//	    file.mkdirs();
//	}
//	htmlReporter = new ExtentHtmlReporter(pathReport+this.getClass().getSimpleName()+"_log_"+df.format(new Date())+".html" );
        System.setProperty(webdriver,typeDriver);
	driver =  new FirefoxDriver();
//	driver.get(url+"login");
//	driver.manage().window().maximize();
//	extent = new ExtentReports();
//	extent.attachReporter(htmlReporter);
	 
    }
    
    @Test(priority=1)
    public void invalidLogin() {
	String message = "";
//	ExtentTest logger = extent.createTest("Test inValid Login");
//	logger.info("Browser Launched");
	driver.get(url+"login");
	try {
//	    logger.log(Status.INFO, "Navigated to login page");
	    WebElement username,password,submit;
	    username = driver.findElement(By.id("username"));
	    password = driver.findElement(By.id("password"));
	    username.sendKeys("test");
	    password.sendKeys("test1234");
//	    logger.log(Status.INFO, username.getAttribute("value")+" is insert in filed username ");
//	    logger.log(Status.INFO, password.getAttribute("value")+" is insert in filed password ");
	    Thread.sleep(1000);
	    submit = driver.findElement(By.xpath("//button[@onclick='submitForm()']"));
	    submit.click();
//	    logger.log(Status.INFO, "Click button Login");
	    Thread.sleep(1000);
       	    assertTrue(driver.getCurrentUrl().equals(url+"login"));
//       	    logger.log(Status.PASS, "Test Pass: User is not login");
       	}catch (AssertionError e) {
//       	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
//       	    Assert.fail();
       	} catch (Exception e) {
//	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
//	    Assert.fail();
	}
    }

//   
    
    @Test(priority=2)
    public void validLogin() {
//	String message = "";
//	ExtentTest logger = extent.createTest("Test valid Login");
//	logger.info("Browser Launched");
	driver.get(url+"login");
	try {
//	    logger.log(Status.INFO, "Navigated to login page");
	    WebElement username,password,submit;
	    username = driver.findElement(By.id("username"));
	    password = driver.findElement(By.id("password"));
	    username.sendKeys("syatriia");
	    password.sendKeys("11syatriia");
//	    logger.log(Status.INFO, username.getAttribute("value")+" is insert in filed username ");
//	    logger.log(Status.INFO, password.getAttribute("value")+" is insert in filed password ");
	    Thread.sleep(1000);
	    submit = driver.findElement(By.xpath("//button[@onclick='submitForm()']"));
	    submit.click();
//	    logger.log(Status.INFO, "Click button Login");
	    Thread.sleep(1000);
	    assertTrue(driver.getCurrentUrl().equals(url+"admin/"));
//       	    logger.log(Status.PASS, "Test Pass: Sucess Login");
       	}catch (AssertionError e) {
//       	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
//       	    Assert.fail();
       	} catch (Exception e) {
//	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
//	    Assert.fail();
	}
    }
    
    @AfterTest
    public void finish() {
//	driver.close();
	driver.quit();
//	extent.flush();
    }
    
}
