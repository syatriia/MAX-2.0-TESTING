/**
 * 
 */
package com.selenium.functional.max;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
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
@Test
public class Registration {

    FileProperties fp;
    String url,pathReport,typeDriver,webdriver;
    WebDriver driver;
    WebDriverWait wait;
    ExtentHtmlReporter htmlReporter;
    ExtentReports extent;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh.mm");
    ExtentTest logger;
    public Registration() {
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
    
    @BeforeMethod
    public void start() {
	driver.get(url+"register");
    }
    
    public void validRegistration() throws Exception {
	logger = extent.createTest("Test Valid Registration");
	String message = "";
	try {
	    WebElement email,username,password,retype,phones,firstname,lastname,address,organization,next,finish;
	    email = driver.findElement(By.id("email"));
	    username = driver.findElement(By.id("username"));
	    password = driver.findElement(By.id("password"));
	    retype =  driver.findElement(By.id("retype"));
	    phones =  driver.findElement(By.id("phones"));
	    next = driver.findElement(By.linkText("Next"));
	    
	    email.sendKeys("Syatriia11@gmail.com");
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
	    
	    firstname = driver.findElement(By.id("firstname"));
	    lastname = driver.findElement(By.id("lastname"));
	    address = driver.findElement(By.id("address"));
	    organization = driver.findElement(By.id("organization"));
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
	    finish = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Finish")));
	    logger.log(Status.INFO, "Button next is clicked");
//	    finish.click();
	    logger.log(Status.INFO, "Button Finish is clicked");
	    message =  driver.findElement(By.tagName("h2")).getText();
	    assertTrue(driver.getCurrentUrl().equals(url+"confirm?process=register") &&  message.equals("Thank You For Signing Up"));
	    assertEquals(message, "Thank You For Signing Up");
        }catch (AssertionError e) {
            logger.log(Status.FAIL, e.getMessage());
            Assert.fail(e.getMessage());
            takeScreenshot(Thread.currentThread().getStackTrace()[1].getMethodName());
        }catch (Exception e) {
            logger.log(Status.FAIL, e.getMessage());
            Assert.fail(e.getMessage());
//            takeScreenshot();
        }
    }
    
    @AfterMethod
    public void getResult(ITestResult result) throws Exception{
	if(result.getStatus() == ITestResult.FAILURE){
		logger.log(Status.FAIL, "Test Case Failed is "+result.getName());
		logger.log(Status.FAIL, "Test Case Failed is "+result.getThrowable());
		//To capture screenshot path and store the path of the screenshot in the string "screenshotPath"
                //We do pass the path captured by this mehtod in to the extent reports using "logger.addScreenCapture" method. 			
                String screenshotPath = takeScreenshot(result.getName());
		//To add it in the extent report 
		logger.addScreenCaptureFromPath(screenshotPath);
	}else if(result.getStatus() == ITestResult.SKIP){
		logger.log(Status.SKIP, "Test Case Skipped is "+result.getName());
	}
	// ending test
	//endTest(logger) : It ends the current test and prepares to create HTML report
    }
    
    @AfterTest
    public void finish() {
//	driver.close();
//	driver.quit();
//	extent.flush();
    }
    
    public String takeScreenshot(String method) throws Exception {
	String filePath = "D:\\SCREENSHOTS"+method;
	File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//	try {
//	    FileUtils.copyFile(scrFile, new File(filePath+".png"));
	    System.out.println("***Placed screen shot in "+filePath+" ***");
//	} catch (IOException e) {
//	   e.printStackTrace();
//	}
        return filePath;   
    }
}
