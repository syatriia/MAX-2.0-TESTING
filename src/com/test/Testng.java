/**
 * 
 */
package com.test;



import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
public class Testng {

    
    FileProperties fpurl,fpdriver,fpreport;
    String url,pathReport,typeDriver,webDriver;
    WebDriver driver;
    WebDriverWait wait;
    ExtentHtmlReporter htmlReporter;
    ExtentReports extent;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh.mm");
    
    @Parameters("browser")
    @BeforeTest
    public void prastartTest(String browser) {
	fpurl = new FileProperties("url.properties");
	fpdriver =  new FileProperties("driver.properties");
	fpreport = new FileProperties("pathreport.properties");
	if(browser.equalsIgnoreCase("firefox")) {
	    typeDriver = fpdriver.getProperties("firefoxwebdriver");
	    webDriver = fpdriver.getProperties("pathfirefoxdriver");
	    System.out.println("ok"+typeDriver+webDriver);
	    System.setProperty(typeDriver,webDriver);
	    driver =  new FirefoxDriver();
	}else if(browser.equalsIgnoreCase("chrome")) {
	    System.out.println(browser);
	    System.out.println(fpdriver.getProperties("chromewebdriver"));
	    typeDriver = fpdriver.getProperties("chromewebdriver");
	    webDriver = fpdriver.getProperties("pathchromedriver");
	    System.setProperty(typeDriver,webDriver);
	    driver =  new ChromeDriver();
	    driver.manage().window().maximize();
	}else if(browser.equalsIgnoreCase("ie")) {
	    typeDriver = fpdriver.getProperties("iewebdriver");
	    webDriver = fpdriver.getProperties("pathiedriver");
	    System.setProperty(typeDriver,webDriver);
	    driver =  new InternetExplorerDriver();
	}
	wait = new WebDriverWait(driver, 10);
    }
    
    @BeforeMethod
    public void beforeMethod() {

    }
    
    @Test
    public void test1() throws InterruptedException{
	 driver.get("https://www.google.com/gmail/");
         wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("identifierId"))).sendKeys("20okta123789@gmail.com");
         driver.findElement(By.xpath("//*[@id=\"identifierNext\"]/content/span")).click();
         wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"password\"]/div[1]/div/div[1]/input"))).sendKeys("barkerz891993");
         driver.findElement(By.xpath("//*[@id=\"passwordNext\"]/content/span")).click();
         wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gbqfq"))).sendKeys("Mata no-reply");;
         List<WebElement> email = driver.findElements(By.cssSelector("div.yW>span"));
         for(WebElement emailsub : email){
             System.out.println(emailsub.getText());
             if(emailsub.getText().equals("MATA no-reply") == true){
        	 emailsub.click();
        	 driver.findElement(By.xpath("//*[@id=\":k8\"]/a"));
        	 System.out.println(driver.getCurrentUrl());
                 break;
             }
         }
    }

    @Parameters("browser")
    @AfterTest
    public void afterTest(String browser) {
	if(browser.equalsIgnoreCase("chrome")) {
	    driver.close();
	    driver.quit();
	}else if(browser.equalsIgnoreCase("firefox")) {
	    driver.close();
	}else if(browser.equalsIgnoreCase("ie")) {
	    try {
		    Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
		    Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
		} catch (IOException e) {
			    e.printStackTrace();
		     }
		 }    
	}     
}
