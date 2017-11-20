/**
 * 
 */
package com.selenium.integrationtest.max;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
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
public class ForgotPassword {
    
    FileProperties fp;
    String url,pathReport,typeDriver,webdriver;
    WebDriver driver;
    ExtentHtmlReporter htmlReporter;
    ExtentReports extent;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh.mm");
    
    public ForgotPassword() {
	fp = new FileProperties("url.properties");
	url = fp.getProperties("finalurl");
	pathReport = fp.getProperties("integrationpathlog");
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
	extent = new ExtentReports();
	extent.attachReporter(htmlReporter);
	 
    }

    public void sendMailForgotPassword() {
	ExtentTest logger = extent.createTest("Test Send email forgot password");
	logger.log(Status.INFO, "Browser Launched");
	driver.get(url+"login");
	logger.log(Status.INFO, "Navigated to login page");
 	try {
 	  WebElement forgotPassword,form,submit;
 	  forgotPassword = driver.findElement(By.xpath("//form[@id='loginForm']/div[3]/div[2]/div/a"));
          forgotPassword.click();    
         
          form = driver.findElement(By.id("email-recovery"));
          form.sendKeys("testemail.com");
          logger.log(Status.INFO, form.getAttribute("value")+" is insert in filed email forgot password ");
          submit = driver.findElement(By.id("btnRecover"));
          submit.click();
          logger.log(Status.INFO, "Click button submit");
         
          String message = driver.findElement(By.xpath("//div[@id='recover-div']/label")).getText();
          assertEquals(message, "An email has been sent to reset your password.");
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
