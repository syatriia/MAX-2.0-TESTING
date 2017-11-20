/**
 * 
 */
package com.selenium.unittest.max;




import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.summit.util.file.FileProperties;


/**
 * @author THINKPAD
 *
 */
public class Test {
    
    FileProperties fp;
    String url,pathReport,typeDriver,webdriver;
    WebDriver driver;
    WebDriverWait wait;
    ExtentHtmlReporter htmlReporter;
    ExtentReports extent;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh.mm");
    
    public Test() {
	fp = new FileProperties("url.properties");
	url = fp.getProperties("finalurl");
	pathReport = fp.getProperties("unitpathlog");
	typeDriver =  fp.getProperties("chromedriver");
	webdriver = fp.getProperties("chromewebdriver");
    }

    @BeforeTest
    public void prastartTest() throws InterruptedException {
	File file = new File(pathReport);
	if(!file.exists()) {
	    file.mkdirs();
	}
	htmlReporter = new ExtentHtmlReporter(pathReport+this.getClass().getSimpleName()+"_log_"+df.format(new Date())+".html" );
	System.setProperty(webdriver,typeDriver);
	driver =  new ChromeDriver();
	driver.manage().window().maximize();
	wait = new WebDriverWait(driver, 10);
	
	extent = new ExtentReports();
	extent.attachReporter(htmlReporter);
	driver.get(url+"register");
    }

    @org.testng.annotations.Test
    public void showFormEmailForgotPassword() {
	ExtentTest logger = extent.createTest("Test empty email");
	try {
	    logger.log(Status.INFO, "Browser Launched");
	    driver.get(url+"register");
	    logger.log(Status.INFO, "Navigated to register page");
	    WebElement email,submit;
	    email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
	    submit = driver.findElement(By.linkText("Next"));
	    logger.log(Status.INFO, "Click Button Next");
	    submit.click();
	    String message = email.getAttribute("validationMessage");
	    assertEquals("Please fill out this field.", message);
	    logger.log(Status.PASS,  "Show message : This field is required");
	}catch (AssertionError e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	    Assert.fail(e.getMessage());
	}catch (Exception e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	    Assert.fail(e.getMessage());
	}
    }
    
    @org.testng.annotations.Test
    public void emptyEmailForgotPass() {
	ExtentTest logger = extent.createTest("Test empty email forgot password");
	logger.log(Status.INFO, "Browser Launched");
	driver.get(url+"login");
	logger.log(Status.INFO, "Navigated to login page");
   	try {
   	    WebElement forgotPassword,form,submit;
   	    forgotPassword = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form[@id='loginForm']/div[3]/div[2]/div/a")));
            forgotPassword.click();    
            logger.log(Status.INFO, "Cliked Forgot your password");
            form = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email-recovery")));
            submit = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnRecover")));
            submit.click();
            logger.log(Status.INFO, "Click button submit");
            String message = form.getAttribute("validationMessage");
            assertEquals("Please fill out this field.", message);
            logger.log(Status.PASS, "Show message : This field is required");
   	}catch (AssertionError e) {
   	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
   	    Assert.fail(e.getMessage());
   	}catch (Exception e) {
   	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
   	    Assert.fail(e.getMessage());
	}
    }
    
    @org.testng.annotations.Test
    public void invalidEmailForgotPass() {
	ExtentTest logger = extent.createTest("Test inValid email forgot password");
	logger.log(Status.INFO, "Browser Launched");
	driver.get(url+"login");
	logger.log(Status.INFO, "Navigated to login page");
   	try {
   	    WebElement forgotPassword,form,submit;
   	    forgotPassword = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form[@id='loginForm']/div[3]/div[2]/div/a")));
            forgotPassword.click();    
            form = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email-recovery")));
            form.sendKeys("testemail.com");
            logger.log(Status.INFO, form.getAttribute("value")+" is insert in filed email forgot password ");
            submit = driver.findElement(By.id("btnRecover"));
            submit.click();
            logger.log(Status.INFO, "Click button submit");
            String message = form.getAttribute("validationMessage");
            assertTrue(message.contains("Please include an '@' in the email address."));
            logger.log(Status.PASS, "Show message :   Please enter a valid email address");
   	}catch (AssertionError e) {
   	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
   	    Assert.fail(e.getMessage());
   	}catch (Exception e) {
   	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
   	    Assert.fail(e.getMessage());
	}
    }   
    
    @AfterTest
    public void finish() {
	driver.close();
	driver.quit();
	extent.flush();
    }
    
    
}
