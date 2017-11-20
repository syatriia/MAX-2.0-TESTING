/**
 * 
 */
package com.selenium.functional.max;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
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
public class Login {
    FileProperties fpurl,fppathreport,fpdriver;
    String pathReport,url,typeDriver,webDriver;
    WebDriver driver;
    WebDriverWait wait;
    ExtentHtmlReporter htmlReporter;
    ExtentReports extent;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh.mm");
    ExtentTest logger;
    
    @Parameters("browser")
    @BeforeClass
    public void beforeTest(String browser) {
	fpurl = new FileProperties("url.properties");
	fppathreport =  new FileProperties("pathreport.properties");
	fpdriver = new FileProperties("driver.properties");
	url = fpurl.getProperties("finalurl");
	pathReport = fppathreport.getProperties("unitpathlog");
	System.out.println(browser);
	if(browser.equalsIgnoreCase("chrome")) {
	    webDriver = fpdriver.getProperties("chromewebdriver");
	    typeDriver = fpdriver.getProperties("chromedriver");
	    System.setProperty(webDriver,typeDriver);
	    driver = new ChromeDriver();
	}else if(browser.equalsIgnoreCase("firefox")){
	    webDriver = fpdriver.getProperties("firefoxwebdriver");
	    typeDriver = fpdriver.getProperties("firefoxdriver");
	    System.setProperty(webDriver,typeDriver);
	    driver = new FirefoxDriver();
	}else if(browser.equalsIgnoreCase("ie")) {
	    webDriver = fpdriver.getProperties("iewebdriver");
	    typeDriver = fpdriver.getProperties("iedriver");
	    System.setProperty(webDriver,typeDriver);
	    driver = new InternetExplorerDriver();
	}
	htmlReporter = new ExtentHtmlReporter(pathReport+this.getClass().getSimpleName()+"_log_"+df.format(new Date())+".html" );
	extent = new ExtentReports();
	extent.attachReporter(htmlReporter);
	
	Set<String> allTabs = driver.getWindowHandles();
	List<String> tabList = new ArrayList<String>(allTabs);
	String newTab = tabList.get(0);
	driver.switchTo().window(newTab);
//	wait = new WebDriverWait(driver, 10);
    }
    
//    @BeforeTest
//    public void prastartTest() throws InterruptedException {
//	File file = new File(pathReport);
//	if(!file.exists()) {
//	    file.mkdirs();
//	}
//	htmlReporter = new ExtentHtmlReporter(pathReport+this.getClass().getSimpleName()+"_log_"+df.format(new Date())+".html" );
//	driver =  new ChromeDriver();
//	driver.manage().window().maximize();
//	wait = new WebDriverWait(driver, 10);
//	Set<String> allTabs = driver.getWindowHandles();
//	List<String> tabList = new ArrayList<String>(allTabs);
//	String newTab = tabList.get(0);
//	driver.switchTo().window(newTab);
//	extent = new ExtentReports();
//	extent.attachReporter(htmlReporter);
//    }
    
    @BeforeMethod
    public void start() {
	driver.get(url+"login");
//	 driver.get("http://www.store.demoqa.com"); 
    }
    
    @Test(enabled=false)
    public void validLogin() {
	logger = extent.createTest("Test valid Login");
	logger.info("Browser Launched");
	try {
	    logger.log(Status.INFO, "Navigated to login page");
	    WebElement username,password,submit;
	    username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
	    password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
	    username.sendKeys("syatria");
	    password.sendKeys("11syatriia");
	    logger.log(Status.INFO, username.getAttribute("value")+" is insert in filed username ");
	    logger.log(Status.INFO, password.getAttribute("value")+" is insert in filed password ");
	    submit = driver.findElement(By.xpath("//button[@onclick='submitForm()']"));
	    submit.click();
	    logger.log(Status.INFO, "Click button Login");
	    assertTrue(driver.getCurrentUrl().equals(url+"admin/"));
       	    logger.log(Status.PASS, "Test Pass: Sucess Login");
       	}catch (AssertionError e) {
       	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
       	    Assert.fail();
       	} catch (Exception e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	    Assert.fail();
	}
    }
    
    @Test(enabled=false)
    public void invalidLogin(){
	String actual = "",expected="Username and/or password incorrect.";
	ExtentTest logger = extent.createTest("Test inValid Login");
	try {
	    WebElement username,password,submit;
	    username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
	    password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
	    username.sendKeys("test");
	    password.sendKeys("test1234");
	    logger.log(Status.INFO, username.getAttribute("value")+" is insert in field username ");
	    logger.log(Status.INFO, password.getAttribute("value")+" is insert in field password ");
	    submit = driver.findElement(By.xpath("//button[@onclick='submitForm()']"));
	    submit.click();
	    logger.log(Status.INFO, "Click button Login");
	    actual = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"loginForm\"]/div[1]/label/i"))).getText();
       	    assertEquals(driver.getCurrentUrl(), url+"login");
       	    logger.log(Status.PASS, "Test Pass: User is not login"); 
//       	    assertEquals(actual, expected);
//       	    logger.log(Status.PASS, "Test Pass: Messagge warning is expectedn");
       	} catch (Exception e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	}
    }
    
    @Test()
    public void emptyEmailForgotPassword() throws InterruptedException {
	driver.findElement(By.xpath("//a[contains(text(),'Forgot your password ?')]")).click();
	driver.findElement(By.id("btnRecover"));
	Thread.sleep(2000);
	System.out.println(driver.findElement(By.id("error-label-er")).getText());
//	Thread.sleep(2000);
//	driver.findElement(By.id("log")).sendKeys("testuser_1");
//	driver.findElement(By.id("pwd")).sendKeys("Test@123");
//	driver.findElement(By.id("login")).click();
//	String actual = "",expected="You must Enter your Email";
//	ExtentTest logger = extent.createTest("Test Empty Email Forgot Password");
//	logger.info("Browser Launched");
//	
////	try {
//	    
//	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Forgot your password ?')]"))).click();;
//	    System.out.println("expected: "+expected);
//	    logger.log(Status.INFO, "Button Forgot Password is clicked");
//	    logger.log(Status.INFO, "Form forgot password is expectedn");
//	    logger.log(Status.INFO, "Field email is empty");
//	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnRecover"))).click();;
//
//	    logger.log(Status.INFO, "Button submit is clicked");
//	    actual = driver.findElement(By.id("error-label-er")).getText();
//	    System.out.println("actual: "+actual+"expected: "+expected);
//	    assertEquals(actual, expected);
//	    logger.log(Status.PASS, "Test Succes: ");
//	}catch (Exception e) {
//	    logger.log(Status.FAIL, "Test Failed : "+e.getMessage());
//	}
    }
    
    @Test(enabled=false)
    public void invalidEmailForgotPassword() {
	String actual = "",expected="You must Enter valid email format";
	ExtentTest logger = extent.createTest("Test Invalid Email Forgot Password");
	logger.info("Browser Launched");
	try {
	    WebElement btnForgotPass,email,btnSubmit;
	    btnForgotPass = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form[@id='loginForm']/div[3]/div[2]/div/a")));
	    btnForgotPass.click();
	    logger.log(Status.INFO, "Button Forgot Password is clicked");
	    logger.log(Status.INFO, "Form forgot password is expectedn");
	    email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email-recovery")));
	    email.sendKeys("test@mail");
	    logger.log(Status.INFO, email.getAttribute("value")+"is insert in field email");
	    btnSubmit = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnRecover")));
	    btnSubmit.click();
	    logger.log(Status.INFO, "Button submit is clicked");
	    actual = driver.findElement(By.id("error-label-er")).getText();
	    assertEquals(actual, expected);
	    logger.log(Status.PASS, "Test Succes: ");
	}catch (Exception e) {
	    logger.log(Status.FAIL, "Test Failed : "+e.getMessage());
	    Assert.fail();
	}
    }
    
    @AfterTest
    public void finish() {
//	driver.close();
	driver.quit();
//	extent.flush();
    }
    
//    public String takeScreenshot(String method) throws Exception {
//	String filePath = pathReport+method+df.format(new Date());
//	File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//	try {
//	    FileUtils.copyFile(scrFile, new File(filePath+".png"));
//	} catch (IOException e) {
//	    e.printStackTrace();
//	}
//        return filePath;   
//    }
}