package com.qa.yaatra.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ElementUtil {
WebDriver driver;
	
	

	public ElementUtil(WebDriver driver)
	{
		this.driver=driver;
	}

	public String doGetTitle()
	{
		return driver.getTitle();
	}

	public WebElement getElement(By locator)
	{
		return driver.findElement(locator);
	}
	
	public void doGetClick(By locator)
	{
		getElement(locator).click();
	}
	
	public void doGetsendkeys(By locator,String value)
	{
		getElement(locator).sendKeys(value);;
	}
	
	public boolean doGetElementDisplayed(By locator)
	{
		return getElement(locator).isDisplayed();
	}
	public String doGetText(By locator)
	{
		return getElement(locator).getText();
	}
	
	
}
