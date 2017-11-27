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
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
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
public class Home {

    
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
