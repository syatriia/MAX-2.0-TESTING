/**
 * 
 */
package com.selenium.functional.max;

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
import org.openqa.selenium.support.ui.WebDriverWait;
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
    FileProperties fpurl,fpdriver,fppathreport;
    String url,pathReport,typeDriver,webDriver,expected;
    WebDriver driver;
    WebDriverWait wait;
    ExtentHtmlReporter htmlReporter;
    ExtentReports extent;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh.mm");
    
    public Home() {
	fpurl = new FileProperties("url.properties");
	fppathreport =  new FileProperties("pathreport.properties");
	fpdriver = new FileProperties("driver.properties");
	url = fpurl.getProperties("devurl");
	pathReport = fppathreport.getProperties("unitpathlog");
	System.out.println(url+pathReport);
//	if(browser.equalsIgnoreCase("chorme")) {
	    webDriver = fpdriver.getProperties("chromewebdriver");
	    typeDriver = fpdriver.getProperties("chromedriver");
	    System.setProperty(webDriver,typeDriver);
	    driver = new ChromeDriver();
//	}else if(browser.equalsIgnoreCase("firefox")){
//	    webDriver = fpdriver.getProperties("firefoxwebdriver");
//	    typeDriver = fpdriver.getProperties("firefoxdriver");
//	    System.setProperty(webDriver,typeDriver);
//	    driver = new FirefoxDriver();
//	}else if(browser.equalsIgnoreCase("ie")) {
//	    webDriver = fpdriver.getProperties("iewebdriver");
//	    typeDriver = fpdriver.getProperties("iedriver");
//	    System.setProperty(webDriver,typeDriver);
//	    driver = new InternetExplorerDriver();
//	}
    }
    
    @BeforeTest
    public void prastartTest() throws InterruptedException {
	File file = new File(pathReport);
	if(!file.exists()) {
	    file.mkdirs();
	}
	htmlReporter = new ExtentHtmlReporter(pathReport+this.getClass().getPackage()+this.getClass().getSimpleName()+"_log_"+df.format(new Date())+".html" );
        System.setProperty(webDriver,typeDriver);
	driver =  new ChromeDriver();
	driver.manage().window().maximize();
	wait = new WebDriverWait(driver, 10);
	Set<String> allTabs = driver.getWindowHandles();
	List<String> tabList = new ArrayList<String>(allTabs);
	String newTab = tabList.get(0);
	driver.switchTo().window(newTab);
	extent = new ExtentReports();
	extent.attachReporter(htmlReporter);

    }

    @Test
    public void checklinkRegister() {
	expected = url+"register?plan=FREE";
	ExtentTest logger = extent.createTest("Test verify link register");
	driver.get(url);
	try {
	    logger.log(Status.INFO, "Navigated to Home page");
	    WebElement register;
	    register = driver.findElement(By.id("free-trial"));
	    register.click();
	    logger.log(Status.INFO, "Button register is clicked");
       	    assertEquals(driver.getCurrentUrl(), expected);
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
    public void checklinkSignIn() {
	ExtentTest logger = extent.createTest("Test verify link sign in");
	expected = url+"login";
	driver.get(url);
	try {
	    logger.log(Status.INFO, "Navigated to Home page");
	    WebElement signin;
	    signin = driver.findElement(By.linkText("Sign In"));
	    signin.click();
	    logger.log(Status.INFO, "Button sign in is clicked	");
       	    assertEquals(driver.getCurrentUrl(), expected);
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