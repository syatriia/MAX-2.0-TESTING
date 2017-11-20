/**
 * 
 */
package com.selenium.max.source;

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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
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
	login.click();
    }
    
    @Test
    public void create() {
	String expected,actually;
	try {
	    WebElement dashboardSource,buttonAdd,selectTopic,nameSource,keyword,submit,source;
	    dashboardSource = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"sidemenu\"]/li[2]/a")));
	    dashboardSource.click();
	    buttonAdd = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//html/body/div[2]/div[1]/div[2]/button/span")));
	    buttonAdd.click();
	    selectTopic = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"create_topic\"]")));
	    nameSource = driver.findElement(By.xpath("//*[@id=\"crawler-name\"]"));
	    nameSource.sendKeys("test twitter");
	    source = driver.findElement(By.xpath("//*[@id=\"form_create\"]/div[2]/div[3]/div/div/div[1]/div[2]/div/label/input"));
	    source.click();
	    keyword = driver.findElement(By.xpath("//*[@id=\"crawler-keyword\"]"));
	    keyword.sendKeys("test keyword");
	    expected = keyword.getAttribute("value");
	    submit = driver.findElement(By.id(""));
	    submit.click();
	    WebElement x = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//html/body/div[2]/div[2]/div[1]/div[1]/div[1]")));
	    actually = x.getText();
	    assertEquals(actually, expected);
	    
	}catch (AssertionError e) {
	    Assert.fail();
	}catch (Exception e) {
	    Assert.fail();
	}
    }
}
