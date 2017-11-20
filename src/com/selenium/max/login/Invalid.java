/**
 * 
 */
package com.selenium.max.login;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

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
import org.openqa.selenium.support.ui.ExpectedConditions;
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
public class Invalid {

    FileProperties fp;
    String url,pathReport,typeDriver,webdriver;
    WebDriver driver;
    WebDriverWait wait;
    ExtentHtmlReporter htmlReporter;
    ExtentReports extent;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh.mm");
    
    public Invalid() {
	fp = new FileProperties("url.properties");
	url = fp.getProperties("finalurl");
	pathReport = fp.getProperties("funtionalpathlog");
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
	wait = new WebDriverWait(driver, 10);
	Set<String> allTabs = driver.getWindowHandles();
	List<String> tabList = new ArrayList<String>(allTabs);
	String newTab = tabList.get(0);
	driver.switchTo().window(newTab);
	extent = new ExtentReports();
	extent.attachReporter(htmlReporter);

    }
    
    @Test
    public void invalidLogin() {
	String message = "",show="Username and/or password incorrect.";
	ExtentTest logger = extent.createTest("Test inValid Login");
	logger.info("Browser Launched");
	driver.get(url+"login");
	try {
	    logger.log(Status.INFO, "Navigated to login page");
	    WebElement username,password,submit;
	    username = driver.findElement(By.id("username"));
	    password = driver.findElement(By.id("password"));
	    username.sendKeys("test");
	    password.sendKeys("test1234");
	    logger.log(Status.INFO, username.getAttribute("value")+" is insert in filed username ");
	    logger.log(Status.INFO, password.getAttribute("value")+" is insert in filed password ");
	    submit = driver.findElement(By.xpath("//button[@onclick='submitForm()']"));
	    submit.click();
	    logger.log(Status.INFO, "Click button Login");
	    message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"loginForm\"]/div[1]/label/i"))).getText();
	    System.out.println(message);
       	    assertEquals(driver.getCurrentUrl(), url+"login");
       	    logger.log(Status.PASS, "Test Pass: User is not login"); 
       	    assertEquals(message, show);
       	    logger.log(Status.PASS, "Test Pass: Messagge warning is shown");
	}catch (Exception e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	    Assert.fail();
	}
    }
    
    @Test
    public void validLogin(){
	
    }
    
    @AfterTest
    public void finish(){
	driver.close();
	driver.quit();
	extent.flush();
    }
}
