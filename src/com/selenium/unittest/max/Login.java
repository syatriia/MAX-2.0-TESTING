/**
 * 
 */
package com.selenium.unittest.max;

import static org.testng.Assert.assertEquals;
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


/**
 * @author THINKPAD
 *
 */
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
    
    
    @BeforeMethod
    public void beforeMethod() {
	driver.get(url+"login");
    }
    
    @Test()
    public void emptyUsername() {
	expected = "This field is required.";
	ExtentTest logger = extent.createTest("Test empty field username");
	logger.info("Browser Launched");
	try {
	    
	    WebElement password,submit;
	    password = driver.findElement(By.id("password"));
	    password.sendKeys("test");
	    logger.log(Status.INFO, password.getAttribute("value")+" is insert in filed Password ");
	    logger.log(Status.INFO, "field password is blank"); 
	    submit = driver.findElement(By.xpath("//button[@onclick='submitForm()']"));
	    submit.click();
	    logger.log(Status.INFO, "Click button Login");
	    actual = driver.findElement(By.id("username-error")).getText();
       	    assertEquals(actual,expected);
       	    logger.log(Status.PASS, "Show message : This field is required");
       	} catch (Exception e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	    Assert.fail();
	}
    }

    @Test()
    public void emptyPassword() {
	expected = "This field is required.";
	ExtentTest logger = extent.createTest("Test empty field password");
   	try {
   	    WebElement username,submit;
            username = driver.findElement(By.id("username"));
            username.sendKeys("test");
            logger.log(Status.INFO, username.getAttribute("value")+" is insert in filed username ");
            logger.log(Status.INFO, "field password is blank"); 
            submit = driver.findElement(By.xpath("//button[@onclick='submitForm()']"));
            logger.log(Status.INFO, "Click button Login");
            submit.click();
            actual = driver.findElement(By.id("password-error")).getText();
            assertEquals(actual,expected);
            logger.log(Status.PASS, "Show message : This field is required");
   	}catch (Exception e) {
   	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
   	    Assert.fail();
	}
    } 
  
    @Test()
    public void showFormEmailForgotPassword() {
	expected = "";
	ExtentTest logger = extent.createTest("Test form email forgot password");
   	try {
   	    WebElement forgotPassword,form;
   	    forgotPassword = driver.findElement(By.xpath("//form[@id='loginForm']/div[3]/div[2]/div/a"));
            forgotPassword.click();    
//            Thread.sleep(2000);
            form = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email-recovery")));
            assertTrue(form.isDisplayed());
            logger.log(Status.PASS, "Form forgot email password is shown");
   	}catch (Exception e) {
   	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
   	    Assert.fail();
	}
    }
    
    @Test()
    public void emptyEmailForgotPass() {
	expected = "You must Enter your Email";
	ExtentTest logger = extent.createTest("Test empty email forgot password");
   	try {
   	    WebElement forgotPassword,submit;
   	    forgotPassword = driver.findElement(By.xpath("//form[@id='loginForm']/div[3]/div[2]/div/a"));
            forgotPassword.click();    
            logger.log(Status.INFO, "Cliked Forgot your password");
            submit = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnRecover")));
            submit.click();
            logger.log(Status.INFO, "Click button submit");
            actual  = driver.findElement(By.id("error-label-er")).getText();
            assertEquals(actual, expected);
            logger.log(Status.PASS, "Show message : This field is required");
   	}catch (Exception e) {
   	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
   	    Assert.fail(e.getMessage());
	}
    }
    
    @Test()
    public void invalidEmailForgotPass() {
	expected = "You must Enter valid email format";
	ExtentTest logger = extent.createTest("Test inValid email forgot password");
   	try {
   	    WebElement forgotPassword,form,submit;
   	    forgotPassword = driver.findElement(By.xpath("//form[@id='loginForm']/div[3]/div[2]/div/a"));
            forgotPassword.click();    
            form = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email-recovery")));
            form.sendKeys("testemail.com");
            logger.log(Status.INFO, form.getAttribute("value")+" is insert in filed email forgot password ");
            submit = driver.findElement(By.id("btnRecover"));
            submit.click();
            logger.log(Status.INFO, "Click button submit");
            actual = driver.findElement(By.id("error-label-er")).getText();
            assertEquals(actual, expected);
            logger.log(Status.PASS, "Show message :   Please enter a valid email address");
   	}catch (Exception e) {
   	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
   	    Assert.fail(e.getMessage());
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
