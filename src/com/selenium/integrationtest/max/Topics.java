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
public class Topics {

    FileProperties fp;
    String url,pathReport,typeDriver,webdriver;
    WebDriver driver;
    ExtentHtmlReporter htmlReporter;
    ExtentReports extent;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
    
    public Topics() {
	fp = new FileProperties("url.properties");
	url = fp.getProperties("cloudurl");
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

	
	driver.get(url+"login");
		
	WebElement username,password,submit;
	username = driver.findElement(By.id("username"));
	password = driver.findElement(By.id("password"));
	username.sendKeys("syatriia");
	password.sendKeys("11syatriia");
	submit = driver.findElement(By.xpath("//button[@onclick='submitForm()']"));
	submit.click();
	Thread.sleep(Long.valueOf(1000));
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
}
