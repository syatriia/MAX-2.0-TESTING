/**
 * 
 */
package com.selenium.unittest.max;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
public class Home {

    
    FileProperties fp;
    String pathReport,url,typeDriver,webDriver;
    WebDriver driver;
    ExtentHtmlReporter htmlReporter;
    ExtentReports extent;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd hh.mm");
    
    
    
    public Home() {
	fp = new FileProperties("url.properties");
	url = fp.getProperties("devurl");
	pathReport = fp.getProperties("unitpathlog");
	typeDriver =  fp.getProperties("chromedriver");
	webDriver = fp.getProperties("chromewebdriver");
    }
    
    @BeforeTest
    public void prastartTest() throws InterruptedException {
	File file = new File(pathReport);
	if(!file.exists()) {
	    file.mkdirs();
	}
	System.setProperty(webDriver,typeDriver);
	driver =  new ChromeDriver();
	driver.manage().window().maximize();
	Set<String> allTabs = driver.getWindowHandles();
	List<String> tabList = new ArrayList<String>(allTabs);
	String newTab = tabList.get(0);
	driver.switchTo().window(newTab);
	extent = new ExtentReports();
	htmlReporter = new ExtentHtmlReporter(pathReport+this.getClass().getSimpleName()+"_log_"+df.format(new Date())+".html" );
	extent.attachReporter(htmlReporter);
    }
    
    @Test
    public void linkRegister() {
	ExtentTest logger = extent.createTest("Test verify link register");
	logger.info("Browser Launched");
	driver.get(url);
	try {
	    logger.log(Status.INFO, "Navigated to Home page");
	    WebElement register;
	    register = driver.findElement(By.id("free-trial"));
	    register.click();
       	    assertEquals(driver.getCurrentUrl(), url+"register?plan=FREE");
       	    logger.log(Status.PASS, "Show message : Register page is shown");
       	}catch (AssertionError e) {
       	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
       	    Assert.fail();
       	} catch (Exception e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	    Assert.fail();
	}
    }
    
    @Test
    public void linkSignIn() {
	ExtentTest logger = extent.createTest("Test verify link sign in");
	logger.info("Browser Launched");
	driver.get(url);
	try {
	    logger.log(Status.INFO, "Navigated to Home page");
	    WebElement signin;
	    signin = driver.findElement(By.linkText("Sign In"));
	    signin.click();
       	    assertEquals(driver.getCurrentUrl(), url+"login");
       	    logger.log(Status.PASS, "Show message : Login page is shown");
       	}catch (AssertionError e) {
       	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
       	    Assert.fail();
       	} catch (Exception e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	    Assert.fail();
	}
    }
    
    @AfterTest
    public void finish() {
	driver.close();
	driver.quit();
	extent.flush();
    }
}
