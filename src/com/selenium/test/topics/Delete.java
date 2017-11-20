/**
 * 
 */
package com.selenium.test.topics;

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
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.summit.util.file.FileProperties;

/**
 * @author THINKPAD
 *
 */
public class Delete {

    FileProperties fp;
    String url,pathReport,typeDriver,webdriver;
    WebDriver driver;
    WebDriverWait wait;
    ExtentHtmlReporter htmlReporter;
    ExtentReports extent;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh.mm");
    
    public Delete() {
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
	htmlReporter = new ExtentHtmlReporter(pathReport+this.getClass().getPackage()+"_"+this.getClass().getSimpleName()+"_log_"+df.format(new Date())+".html" );
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

	driver.get(url+"login");
	
	WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
	WebElement password = driver.findElement(By.id("password"));
	WebElement login = driver.findElement(By.xpath("//button[@onclick='submitForm()']"));
	username.sendKeys("test");
	password.sendKeys("12345678");
	login.click();;
    }
    
    @Test
    public void deleteTopics() {
   	try {
   	    WebElement topicsMenu,detail,delete,submit;
   	    topicsMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div/div/ul/li/a/i")));
   	    topicsMenu.click();
   	    detail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Details')]")));
   	    detail.click();
   	    delete =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='topics-details']/div/div/div[2]/button")));
   	    delete.click();
   	    submit =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@type='submit']")));
   	    submit.click();
   	    assertTrue(driver.findElements(By.xpath("html/body/div[2]/div[2]/div[1]/div[1]/div[1]/h3")).size() < 1);
   	} catch (AssertionError e) {
//   	    logger.log(Status.FAIL, e.getMessage());
   	    Assert.fail(e.getMessage());
   	} catch (Exception e) {
//   	    logger.log(Status.FAIL, e.getMessage());
   	    Assert.fail(e.getMessage());
   	}
    }
    
    @AfterTest
    public void finish() {
	driver.close();
	driver.quit();
    }
}
