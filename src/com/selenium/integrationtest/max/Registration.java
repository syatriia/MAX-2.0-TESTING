/**
 * 
 */
package com.selenium.integrationtest.max;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
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
public class Registration {

    FileProperties fp;
    String url,pathReport,typeDriver,webdriver;
    WebDriver driver;
    ExtentHtmlReporter htmlReporter;
    ExtentReports extent;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh.mm");
    
    public Registration() {
	fp = new FileProperties("url.properties");
	url = fp.getProperties("cloudurl");
	pathReport = fp.getProperties("integrationpathlog");
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
        System.setProperty(typeDriver,webdriver);
	driver =  new ChromeDriver();
	extent = new ExtentReports();
	extent.attachReporter(htmlReporter);
	 
    }
    
    @Test(priority=1)
    public void validRegistration() {
	driver.get(url+"register");
	ExtentTest logger = extent.createTest("Test Valid Registration");
	String message = "";
	try {
	    WebElement email,username,password,retype,phones,firstname,lastname,address,organization,next,finish;
        	
	    email = driver.findElement(By.id("email"));
	    username = driver.findElement(By.id("username"));
	    password = driver.findElement(By.id("password"));
	    retype =  driver.findElement(By.id("retype"));
	    phones =  driver.findElement(By.id("phones"));
	    next = driver.findElement(By.linkText("Next"));

	    firstname = driver.findElement(By.id("firstname"));
	    lastname = driver.findElement(By.id("lastname"));
	    address = driver.findElement(By.id("address"));
	    organization = driver.findElement(By.id("organization"));
	    email.sendKeys("blackpanda@gmail.com");
	    logger.log(Status.INFO, email.getAttribute("value")+" is insert in filed email ");
	    username.sendKeys("syatriia");
	    logger.log(Status.INFO, username.getAttribute("value")+" is insert in filed username ");
	    password.sendKeys("12345678");
	    logger.log(Status.INFO, password.getAttribute("value")+" is insert in filed password ");
	    retype.sendKeys("12345678");
	    logger.log(Status.INFO, retype.getAttribute("value")+" is insert in filed retype-password ");
	    phones.sendKeys("081234567890");
	    logger.log(Status.INFO, phones.getAttribute("value")+" is insert in filed no phone ");
        	
	    Select plan = new Select(driver.findElement(By.id("plan")));
	    plan.selectByIndex(2);
	    logger.log(Status.INFO, "Plan "+plan.getFirstSelectedOption().getText() +" is selected");
	    
	    next.click();
	    logger.log(Status.INFO, "Button Next is clicked");
	    firstname.sendKeys("Syatria");
	    logger.log(Status.INFO, firstname.getAttribute("value")+" is insert in filed firstname ");
	    lastname.sendKeys("Babullah");
	    logger.log(Status.INFO, lastname.getAttribute("value")+" is insert in filed lastname ");
	    address.sendKeys("Jakarta");
	    logger.log(Status.INFO, address.getAttribute("value")+" is insert in filed address ");
	    Select country = new Select(driver.findElement(By.id("country")));
	    country.selectByIndex(32);
//	    country.selectByValue("Indonesia");
	    logger.log(Status.INFO, "Plan "+country.getFirstSelectedOption().getText() +" is selected");
	    
	    organization.sendKeys("Mata Prima");
	    logger.log(Status.INFO, organization.getAttribute("value")+" is insert in filed organization ");
	    next.click();
	    Thread.sleep(1000);
	    finish = driver.findElement(By.linkText("Finish"));
	    logger.log(Status.INFO, "Button next is clicked");
	    finish.click();
	    logger.log(Status.INFO, "Button Finish is clicked");
	    message =  driver.findElement(By.tagName("h2")).getText();
	    assertTrue(driver.getCurrentUrl().equals(url+"confirm?process=register") &&  message.equals("Thank You For Signing Up"));
	    assertEquals(message, "Thank You For Signing Up");
        }catch (AssertionError e) {
            logger.log(Status.FAIL, e.getMessage());
            Assert.fail(e.getMessage());
        }catch (Exception e) {
            logger.log(Status.FAIL, e.getMessage());
            Assert.fail(e.getMessage());
        }
    }
    
    
    @Test(enabled=false)
    public void checkExisttingEmail() throws InterruptedException {
	driver.get(url+"register");
	ExtentTest logger = extent.createTest("Test Verify Existing Email User");
	String message = "";
	try {
	    WebElement email;
	    email = driver.findElement(By.id("email"));
	    email.sendKeys("syatriia11@gmail.com");
	    Thread.sleep(3000);
	    message = driver.findElement(By.id("invalid-email")).getText();
	    Thread.sleep(3000);
	    assertEquals(message, "email already used");
	}catch (AssertionError e) {
	    logger.log(Status.FAIL, e.getMessage());
	    Assert.fail(e.getMessage());
	}catch (Exception e) {
	    logger.log(Status.INFO, e.getMessage());
	    Assert.fail(e.getMessage());
	}
    }
    
    @Test(priority=2)
    public void checkExisttingUsername() throws InterruptedException {
	driver.get(url+"register");
	ExtentTest logger = extent.createTest("Test Verify Existing Username");
	String message = "";
	try {
	    WebElement username;
	    username = driver.findElement(By.id("username"));
	    username.sendKeys("syatriia11@gmail.com");
	    Thread.sleep(3000);
	    message = driver.findElement(By.id("invalid-username")).getText();
	    Thread.sleep(3000);
	    assertEquals(message, "username already used");
	}catch (AssertionError e) {
	    logger.log(Status.FAIL, e.getMessage());
	    Assert.fail(e.getMessage());
	}catch (Exception e) {
	    logger.log(Status.INFO, e.getMessage());
	    Assert.fail(e.getMessage());
	}
    }
    
    @AfterTest
    public void finish() {
	driver.close();
//	driver.quit();
	extent.flush();
    }
}
