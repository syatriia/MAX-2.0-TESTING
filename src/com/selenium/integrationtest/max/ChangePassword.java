/**
 * 
 */
package com.selenium.integrationtest.max;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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
public class ChangePassword {
    
    FileProperties fp;
    String url,pathReport,typeDriver,webdriver;
    WebDriver driver;
    ExtentHtmlReporter htmlReporter;
    ExtentReports extent;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh.mm");
    
    public ChangePassword() {
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
	password.sendKeys("12345678");
	
	submit = driver.findElement(By.xpath("//button[@onclick='submitForm()']"));
	submit.click();
	
	Thread.sleep(Long.valueOf(1000));
	WebElement user,changePass;
	user = driver.findElement(By.xpath("//div[@id='navbar-collapse']/ul/li/a"));
	changePass = driver.findElement(By.xpath("//div[@id='navbar-collapse']/ul/li/ul/li[2]/a"));
	user.click();
	changePass.click();
    }
    
    @Test
    public void validChangePass() {
	ExtentTest logger = extent.createTest("Test empty new password");
	try {
	    driver.get(url+"admin/changepass");
	    logger.log(Status.INFO, "Navigate to Page Change Password");
	    WebElement password,retype,submit;
	    password =  driver.findElement(By.id("new-password"));
	    password.sendKeys("87654321");
	    logger.log(Status.INFO, password.getAttribute("value")+" is insert in filed password ");
	    retype =  driver.findElement(By.id("retype-new-password"));
	    retype.sendKeys("87654321");
	    logger.log(Status.INFO, retype.getAttribute("value")+" is insert in filed password ");
	    submit = driver.findElement(By.id("submit-new-password"));
	    submit.click();
	    logger.log(Status.INFO, "Click Button Submit");
	    
	    assertEquals(driver.getCurrentUrl(), url+"admin/");
	    logger.log(Status.PASS, "Succes update password");
	}catch (AssertionError e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	}catch (NoSuchElementException e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	}
    }
    
    @AfterTest
    public void getResult() {
	driver.close();
	driver.quit();
	extent.flush();
    }

}
