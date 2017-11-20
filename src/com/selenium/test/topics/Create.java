/**
 * 
 */
package com.selenium.test.topics;

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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.summit.util.file.FileProperties;

/**
 * @author THINKPAD
 *
 */
public class Create {
    FileProperties fp;
    String url,pathReport,typeDriver,webdriver;
    WebDriver driver;
    WebDriverWait wait;
    ExtentHtmlReporter htmlReporter;
    ExtentReports extent;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh.mm");
    
    public Create() {
	fp = new FileProperties("url.properties");
	url = fp.getProperties("devurl");
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

	driver.get(url+"login");
	
	WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
	WebElement password = driver.findElement(By.id("password"));
	WebElement login = driver.findElement(By.xpath("//button[@onclick='submitForm()']"));
	username.sendKeys("test");
	password.sendKeys("12345678");
	login.click();;
    }
    
    @Test
    public void CreateTopicsCustom() {
   	try {
   	    WebElement buttonAddTopics, schemecustom,industry,next,nameTopics,create;
   	    buttonAddTopics = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@type='button']")));
 	
   	    buttonAddTopics.click();
//   	    logger.log(Status.INFO, "Click Button Add");
 	
   	    
   	    schemecustom  = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"scheme-template-row\"]/div/div/div/div[2]/div/label/span")));
   	    industry =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='industry-template-radio']")));
   	    next =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Next')]")));
   	    schemecustom.click();
   	    industry.click();
   	    next.click();
   	    nameTopics = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("topics-name")));
   	    nameTopics.sendKeys("Pilkada");
   	    String name = nameTopics.getAttribute("value");
   	    next.click();
   	    create = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Create')]")));
   	    create.click();
   	    WebElement x = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("html/body/div[2]/div[2]/div[1]/div[1]/div[1]/h3")));
   	    String topics = x.getText();
   	    assertEquals(name, topics);
   	} catch (AssertionError e) {
//   	    logger.log(Status.FAIL, e.getMessage());
   	    Assert.fail(e.getMessage());
   	} catch (Exception e) {
//   	    logger.log(Status.FAIL, e.getMessage());
   	    Assert.fail(e.getMessage());
   	}
    }
    
    @Test
    public void CreateTopicsProduct() {
   	try {
   	    WebElement buttonAddTopics,schemeproduc,industry,next,nameTopics,source,keyword,brand,dicCompetitors,create;
   	    buttonAddTopics = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@type='button']")));
 	
   	    buttonAddTopics.click();
//   	    logger.log(Status.INFO, "Click Button Add");
   	    schemeproduc = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"scheme-template-row\"]/div/div/div/div[1]/div/label/span")));
   	    industry =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='industry-template-radio']")));
   	    next =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Next')]")));
   	    schemeproduc.click();
   	    industry.click();
   	    next.click();
   	    nameTopics = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("topics-name")));
   	    nameTopics.sendKeys("Pilkada");
   	    String name = nameTopics.getAttribute("value");
   	    next.click();
   	    source = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("source-twitter")));
   	    source.click();
   	    keyword = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("main-keyword")));
   	    keyword.sendKeys("pilkada 2019,pilkada");
   	    brand = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("brandName")));
   	    brand.sendKeys("Jawa Barat");
   	    dicCompetitors = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("brandCompetitor1")));
   	    dicCompetitors.sendKeys("Jawa Timur");
//   	    create = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Create')]")));
//   	    create.click();
//   	    WebElement x = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("html/body/div[2]/div[2]/div[1]/div[1]/div[1]/h3")));
//   	    String topics = x.getText();
//   	    assertEquals(name, topics);
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
