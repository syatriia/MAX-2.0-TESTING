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
public class ForgotPassword {
    
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
	htmlReporter = new ExtentHtmlReporter(pathReport+this.getClass().getSimpleName()+"_log_"+df.format(new Date())+".html" );
	wait = new WebDriverWait(driver, 10);

	extent = new ExtentReports();
	extent.attachReporter(htmlReporter);

    }
    
    
    @BeforeMethod
    public void beforeMethod() {
	driver.get(url+"login");
    }

    @Test
    public void sendMailForgotPassword() {
	ExtentTest logger = extent.createTest("Test Send email forgot password");
 	expected = "An email has been sent to reset your password.";
	try {
 	  WebElement form;
 	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form[@id='loginForm']/div[3]/div[2]/div/a"))).click();      
          form = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email-recovery")));
          form.sendKeys("20okta123789@gmail.com");
          logger.log(Status.INFO, form.getAttribute("value")+" is insert in filed email forgot password ");
          driver.findElement(By.id("btnRecover")).click();
          logger.log(Status.INFO, "Click button submit");
          actual = driver.findElement(By.xpath("//div[@id='recover-div']/label")).getText();
          assertEquals(actual, expected);        
 	}catch (Exception e) {
 	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
 	    Assert.fail(e.getMessage());
	}
    }
    
//    @AfterTest
//    public void finish() {
//	driver.close();
//	driver.quit();
//	extent.flush();
//    }
//    
}
