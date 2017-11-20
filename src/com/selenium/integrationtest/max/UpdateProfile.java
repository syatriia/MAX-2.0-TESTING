/**
 * 
 */
package com.selenium.integrationtest.max;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
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
public class UpdateProfile {
    
    FileProperties fp;
    String url,pathReport,typeDriver,webdriver;
    WebDriver driver;
    ExtentHtmlReporter htmlReporter;
    ExtentReports extent;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh.mm");

    public UpdateProfile() {
	fp = new FileProperties("url.properties");
	url = fp.getProperties("cloudurl");
	pathReport = fp.getProperties("integrationpathlog");
	typeDriver =  fp.getProperties("chromedriver");
	webdriver = fp.getProperties("chromewebdriver");
    }
    
    @BeforeTest
    public void startTest() throws InterruptedException {
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
	
	driver.get(url+"login");
	
	WebElement username,password,submit;
	username = driver.findElement(By.id("username"));
	password = driver.findElement(By.id("password"));
	username.sendKeys("syatriia");
	password.sendKeys("1234678");
	
	submit = driver.findElement(By.xpath("//button[@onclick='submitForm()']"));
	submit.click();
	
	Thread.sleep(Long.valueOf(1000));
	WebElement user,changeProfil;
	user = driver.findElement(By.xpath("//div[@id='navbar-collapse']/ul/li/a"));
	changeProfil = driver.findElement(By.xpath("//div[@id='navbar-collapse']/ul/li/ul/li[2]/a"));
	user.click();
	changeProfil.click();
    }
    
    
    @Test(priority=2)
    public void validUpdate() {
	ExtentTest logger = extent.createTest("Test valid update");
	logger.log(Status.INFO, "Browser Launched");
	driver.get(url+"userdetails");
	logger.log(Status.INFO, "Navigated to userdetails page");
   	try {
   	    WebElement submit,address;
	    submit = driver.findElement(By.linkText("Next"));
	    logger.log(Status.INFO, "Click Button Next");
	    submit.click();
   	    address = driver.findElement(By.id("address"));
   	    address.clear();
   	    address.sendKeys("Jakarta Utara");
   	    logger.log(Status.INFO, address.getAttribute("value")+" is insert in filed new address ");
   	    submit = driver.findElement(By.linkText("Finish"));
	    submit.click();
	    logger.log(Status.INFO, "Click Button Finish");
	    assertEquals(driver.getCurrentUrl(), url+"admin/");
	}catch (AssertionError e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	    Assert.fail(e.getMessage());
	}catch (Exception e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	    Assert.fail(e.getMessage());
	}
    }

    @Test(priority=1)
    public void existUpdateEmail() {
	ExtentTest logger = extent.createTest("Test invalid email");
	logger.log(Status.INFO, "Browser Launched");
	driver.get(url+"userdetails");
	logger.log(Status.INFO, "Navigated to userdetails page");
   	try {
   	    WebElement email,submit;
   	    email = driver.findElement(By.id("email"));
   	    email.sendKeys("");
   	    email.sendKeys("20okta123789@gmail.com");
   	    logger.log(Status.INFO, email.getAttribute("value")+" is insert in filed email ");
   	    submit = driver.findElement(By.linkText("Next"));
   	    submit.click();
   	    logger.log(Status.INFO, "Click Button next");
   	    String message = driver.findElement(By.id("invalid-email")).getText();
	    Thread.sleep(3000);
	    assertEquals(message, "email already used");
	    logger.log(Status.INFO, "Show message : email already used");
	}catch (AssertionError e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	    Assert.fail(e.getMessage());
	}catch (Exception e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	    Assert.fail(e.getMessage());
	}
    }
}
