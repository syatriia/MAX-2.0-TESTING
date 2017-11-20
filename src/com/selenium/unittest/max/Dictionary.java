/**
 * 
 */
package com.selenium.unittest.max;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
public class Dictionary {
    
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
	fpurl = new FileProperties("url.properties");
	fpdriver = new FileProperties("driver.properties");
	fppathreport = new FileProperties("pathreport.properties");
	url = fpurl.getProperties("devurl");
	pathReport = fppathreport.getProperties("unitpathlog");
	pathDriver =  fpdriver.getProperties("chromedriver");
	typeDriver = fpdriver.getProperties("chromewebdriver");
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

	driver.get(url+"login");
		
	WebElement username,password,submit;
	username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
	password = driver.findElement(By.id("password"));
	username.sendKeys("test");
	password.sendKeys("12345678");
	submit = driver.findElement(By.xpath("//button[@onclick='submitForm()']"));
	submit.click();
	
    }
    
    @BeforeMethod
    public void beforeMethod() {
	driver.get("http://max.mataprima.com/dev/admin/dictionary");
    }
    
    @Test
    public void emptyNameDictionary() {
	expected ="This field is required";
	ExtentTest logger = extent.createTest("Test empty name dictionary");
	try {
	    
	WebElement btnDictionary,btnAdd,submit;
    	btnDictionary = driver.findElement(By.xpath("//form[@id='form_create']/div[3]/button[2]"));
    	btnDictionary.click();
    	logger.log(Status.INFO, "Got to dictionary");
    	btnAdd = driver.findElement(By.xpath("//div[2]/button"));
    	btnAdd.click();
    	logger.log(Status.INFO, "Click Button Add Dictionary");
    	logger.log(Status.INFO, "Name Dictionary is empty");
    	submit = driver.findElement(By.xpath("//form[@id='form_create']/div[3]/button[2]"));
    	submit.click();
    	logger.log(Status.INFO, "Click Button Submit");
    	actual = driver.findElement(By.id("create_dictName-error")).getText();
    	assertEquals(actual, expected);
    	logger.log(Status.PASS, "Show message : This field is required");
	}catch (Exception e) {
	    logger.log(Status.FAIL,"Test Failed  : " + e.getMessage());
	}
    }
    
    @Test
    public void nameDictionaryMoreThan255() {
	ExtentTest logger = extent.createTest("Test Name Dictionary More Than 255 Character");
	try {
	    
	WebElement btnDictionary,btnAdd,nameDictionary,submit;
    	btnDictionary = driver.findElement(By.xpath("//form[@id='form_create']/div[3]/button[2]"));
    	btnDictionary.click();
    	logger.log(Status.INFO, "Got to dictionary");
    	btnAdd = driver.findElement(By.xpath("//div[2]/button"));
    	btnAdd.click();
    	logger.log(Status.INFO, "Click Button Add Dictionary");
    	nameDictionary = driver.findElement(By.id("create_dictName"));
    	nameDictionary.sendKeys("");
    	logger.log(Status.INFO, nameDictionary.getAttribute("value")+" is insert in field name directory");
    	submit = driver.findElement(By.xpath("//form[@id='form_create']/div[3]/button[2]"));
    	submit.click();
    	logger.log(Status.INFO, "Click Button Submit");
    	int lenght = nameDictionary.getAttribute("value").length();
	assertTrue(lenght<255);
   	logger.log(Status.PASS, "Test Case Success : lenght name topics is "+lenght);
   	} catch (Exception e) {
   	    logger.log(Status.FAIL, e.getMessage());
   	}
    }
    
    @Test(enabled=false)
    public void createDictionary() throws InterruptedException {
	
	ExtentTest logger = extent.createTest("Test valid create dictionary");
	try {
	    WebElement btnAdd,nameDictionary,fielda,btna,btnalias,fieldalias,submitalias,submit;
//	    btnDictionary = driver.findElement(By.xpath(""));
//	    btnDictionary.click();
	    btnAdd = driver.findElement(By.xpath("//div[2]/button"));
	    btnAdd.click();
	    logger.log(Status.INFO, "Click Button Add Dictionary");
	    nameDictionary = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("create_dictName")));
	    nameDictionary.sendKeys("aaaa");
	    logger.log(Status.INFO, nameDictionary.getAttribute("value")+" is insert in field name directory");
	    fielda = driver.findElement(By.xpath("//div[@id='dict-manual']/div/div/div/input"));
	    fielda.sendKeys("bbbb");
	    btna = driver.findElement(By.xpath("//div[@id='dict-manual']/div/div/div/button"));
	    btna.click();
	    btnalias = driver.findElement(By.xpath("//div[@id='item_0']/div/span/span"));
	    btnalias.click();
	    
	    for(int i=0;i<5;i++) {
		fieldalias = driver.findElement(By.xpath("//input[@id='alias_0']"));
		fieldalias.sendKeys("ggg");
		submitalias = driver.findElement(By.xpath("//div[@id='collapse_0']/div/button"));
		submitalias.click();
	    }

	}catch (Exception e) {
	    logger.log(Status.FAIL, e.getMessage());
	}
    }
    
    @Test(enabled=false)
    public void deleteDictionary(){
	int sizeBefore,sizeAfter;
	ExtentTest logger = extent.createTest("Test valid create dictionary");
	try {
	    WebElement btnDelDic,validationDel,btnSubmit;
//	    sideMenu = driver.findElement(By.xpath("xpath=(//button[@type='button'])[2]"));
	    btnDelDic = driver.findElement(By.xpath("//div[2]/div/div/div/button"));
	    validationDel = driver.findElement(By.xpath("//div[@id='dictionary-details']/div/div/div[5]/button"));
	    btnSubmit = driver.findElement(By.xpath("//div[@id='delete-confirmation']/div/div/div[3]/form/button[2]"));
   
	    List<WebElement> listDic = driver.findElements(By.className("panel-title"));
	    sizeBefore = listDic.size();
	    
	    for(WebElement in : listDic) {
		System.out.println(in.getText());
	    }
//	    sideMenu.click();
	    btnDelDic.click();
	    
	    validationDel.click();
	    
	    btnSubmit.click();
	    
	    
	    listDic = driver.findElements(By.className("panel-title"));
	    sizeAfter = listDic.size();
	    assertTrue(sizeAfter<sizeBefore);
	    System.out.println(sizeAfter+" "+sizeBefore);
	}catch (Exception e) {
	    logger.log(Status.FAIL, e.getMessage());
	}
	
    }
    
    @Test
    public void emptyKeywordDic() {
	expected = "Add Dictionary first to continue";
	ExtentTest logger = extent.createTest("Test Empty Keyword Dictionary");
	try {
	    WebElement btnDictionary,btnAdd,submit;
	    driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[2]/button/span")).click();
	    logger.log(Status.INFO, "Click Button Add Dictionary");
	    driver.findElement(By.id("create_dictName")).sendKeys("Test Dic");;
	    submit = driver.findElement(By.xpath("//form[@id='form_create']/div[3]/button[2]"));
	    submit.click();
	    logger.log(Status.INFO, "Click Button Submit");
	    actual = driver.findElement(By.id("create_dictName-error")).getText();
	    assertEquals(actual, expected);
	    logger.log(Status.PASS, "Show message : This field is required");
	}catch (Exception e) {
	    logger.log(Status.FAIL, e.getMessage());
	}
    }
    
    @Test
    public void spaceKeywordDic() {
	expected = "can't use space character";
	ExtentTest logger = extent.createTest("Test Empty Keyword Dictionary");
	try {
	    WebElement btnDictionary,btnAdd,submit;
	    driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[2]/button/span")).click();
	    logger.log(Status.INFO, "Click Button Add Dictionary");
	    driver.findElement(By.id("create_dictName")).sendKeys("Test Dic");
	    driver.findElement(By.id("//*[@id=\"dict\"]")).sendKeys("Test Keyword");
	    actual = driver.findElement(By.id("dict-error")).getText();
	    assertEquals(actual, expected);
	    logger.log(Status.PASS, "Show message : This field is required");
	}catch (Exception e) {
	    logger.log(Status.FAIL, e.getMessage());
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
