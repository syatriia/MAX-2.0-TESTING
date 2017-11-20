/**
 * 
 */
package com.selenium.integrationtest.max;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.summit.util.file.FileProperties;

/**
 * @author THINKPAD
 *
 */
public class Dictionary {
    
    FileProperties fp;
    String url,pathReport,typeDriver,webdriver;
    WebDriver driver;
    ExtentHtmlReporter htmlReporter;
    ExtentReports extent;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh.mm");
    
    public Dictionary() {
	fp = new FileProperties("url.properties");
	url = fp.getProperties("cloudurl");
	pathReport = fp.getProperties("integrationpathlog");
	typeDriver =  fp.getProperties("chromedriver");
	webdriver = fp.getProperties("chromewebdriver");
    }
    
    @BeforeTest
    public void startTest() throws InterruptedException {
	File file = new File(pathReport);
	if(!file.exists()) {
	    file.mkdirs();
	}
	htmlReporter = new ExtentHtmlReporter(pathReport+this.getClass().getSimpleName()+"_log_"+df.format(new Date())+".html" );
        System.setProperty(webdriver,typeDriver);
	driver =  new ChromeDriver();
	driver.manage().window().maximize();
	extent = new ExtentReports();
	extent.attachReporter(htmlReporter);
	
	driver.get(url+"login");
	
	WebElement username,password,submit;
	username = driver.findElement(By.id("username"));
	password = driver.findElement(By.id("password"));
	username.sendKeys("syatriia");
	password.sendKeys("11syatriia");
	
	submit = driver.findElement(By.xpath("//button[@onclick='submitForm()']"));
	submit.click();
	
    }

    
}
