/**
 * 
 */
package com.selenium.integrationtest.max;

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
import org.openqa.selenium.support.ui.Select;
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
public class Registration {


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
	driver.get(url+"register");
    }
    
    @Test(priority=1)
    public void validRegistration() {
	expected = "";
	ExtentTest logger = extent.createTest("Test Valid Registration");
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
	    actual =  driver.findElement(By.tagName("h2")).getText();
	    assertEquals(actual, expected);
        }catch (Exception e) {
            logger.log(Status.FAIL, e.getMessage());
            Assert.fail(e.getMessage());
        }
    }
    
    
    @Test(enabled=false)
    public void checkExisttingEmail() throws InterruptedException {
	expected = "";
	ExtentTest logger = extent.createTest("Test Verify Existing Email User");
	try {
	    WebElement email;
	    email = driver.findElement(By.id("email"));
	    email.sendKeys("syatriia11@gmail.com");
	    Thread.sleep(3000);
	    actual = driver.findElement(By.id("email-error")).getText();
	    assertEquals(actual, expected);
	}catch (Exception e) {
	    logger.log(Status.INFO, e.getMessage());
	    Assert.fail(e.getMessage());
	}
    }
    
    @Test(priority=2)
    public void checkExisttingUsername() throws InterruptedException {

	ExtentTest logger = extent.createTest("Test Verify Existing Username");
	expected = "";
	try {
	    WebElement username;
	    username = driver.findElement(By.id("username"));
	    username.sendKeys("syatriia11@gmail.com");
	    actual = driver.findElement(By.id("username-error")).getText();
	    assertEquals(actual, expected);
	}catch (AssertionError e) {
	    logger.log(Status.FAIL, e.getMessage());
	    Assert.fail(e.getMessage());
	}catch (Exception e) {
	    logger.log(Status.INFO, e.getMessage());
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
