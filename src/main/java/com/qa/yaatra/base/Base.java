package com.qa.yaatra.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Base {

	Properties prop;
	public static ThreadLocal<WebDriver> tldriver=new ThreadLocal<WebDriver>();
	 
	/*
	 * Thread Local will create a Local copy for driver. it will help to make
	 * multiple copies for threads
	 */
 
 
 public static synchronized WebDriver getDriver()
 {
	 return tldriver.get();
 }
 public WebDriver init(String browser)
 {
	 if(browser.equalsIgnoreCase("chrome"))
	 { 
		 WebDriverManager.chromedriver().setup();
		 
		 
		 if(prop.getProperty("headless").equals("yes"))
		 {
		 ChromeOptions co= new ChromeOptions();
		 co.addArguments("--headless");
		 tldriver.set(new ChromeDriver(co));
		 //driver=new ChromeDriver(co);
		 }
		 else
		 {
			 tldriver.set(new ChromeDriver());
		 }
	 }
		 
	 else if(browser.equalsIgnoreCase("firefox"))
	 {
		 WebDriverManager.firefoxdriver().setup();
		 //driver=new FirefoxDriver();
		 tldriver.set(new FirefoxDriver());
	 }
	 getDriver().manage().deleteAllCookies();
	 getDriver().manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
	 getDriver().manage().window().fullscreen();
	 
	return getDriver(); 
 }
 public Properties getProperties()
 {
	 prop=new Properties();
	 
	 String path=null;
	String env=null;
	
	try {
		 env=System.getProperty("env");
		 if(env.equals("qa"))
	     {
	    	  path="./src/main/java/com/qa"
	    	  		+ "/yaatra/properties/config.properties";
	     }
	     else if(env.equals("stg"))
	     {
	    	 path="./src/main/"
	    		 		+ "java/com/qa/hubspot/properties"
	    		 		+ "/stg.config.properties";
	     }
	}
	    catch(Exception e)
	     {
	    	 path="./src/main/java/com"
	    	 		+ "/qa/yaatra/properties/config.properties";
	     }
	
	    
	     
	 try {
		FileInputStream fis=new FileInputStream(path);
		prop.load(fis);
		
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 return prop;
 }
	
	public String getScreenshot()
	{
		File src=((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		
		String path=System.getProperty("user.dir") +"\\Screenshots\\" + System.currentTimeMillis() +".png";
        File destination=new File(path);
       try {
		FileUtils.copyFile(src, destination);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		System.out.println("Screenshot failed");
	}
       return path;
	}
}
