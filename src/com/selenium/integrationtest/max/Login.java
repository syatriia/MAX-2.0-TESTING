/**
 * 
 */
package com.selenium.integrationtest.max;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

public class Login {
    
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
	String pathreport = System.getProperty("user.dir")+"\\Report Testing\\";
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
	htmlReporter = new ExtentHtmlReporter(pathReport+browser+"_"+this.getClass().getSimpleName()+"_log_"+df.format(new Date())+".html" );
	wait = new WebDriverWait(driver, 10);

	extent = new ExtentReports();
	extent.attachReporter(htmlReporter);

    }
    
    
    @BeforeMethod
    public void beforeMethod() {
	driver.get(url+"login");
    }
    
    @Test(priority=1)
    public void invalidLogin() {
	expected = url+"login";
	ExtentTest logger = extent.createTest("Test inValid Login");
	try {
	    WebElement username,password;
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).sendKeys("test");
	    driver.findElement(By.id("password")).sendKeys("test1234");;
//	    logger.log(Status.INFO, username.getAttribute("value")+" is insert in filed username ");
//	    logger.log(Status.INFO, password.getAttribute("value")+" is insert in filed password ");
	    driver.findElement(By.xpath("//button[@onclick='submitForm()']")).click();
//	    logger.log(Status.INFO, "Click button Login");
       	    assertTrue(driver.getCurrentUrl().equals(url+"login"));
      	    logger.log(Status.PASS, "Test Pass: User is not login");
       	} catch (Exception e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	    Assert.fail();
	}
    }

    @Test(priority=2)
    public void validLogin() {
	expected = url+"admin";
	ExtentTest logger = extent.createTest("Test valid Login");
//	logger.info("Browser Launched");
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
//	    logger.log(Status.PASS, "Test Pass: Sucess Login");
       	} catch (Exception e) {
//	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
//	    Assert.fail();
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
