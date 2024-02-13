package org.browserSetUp;

import java.io.IOException;

import org.base.BaseClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserSetup extends BaseClass {
	public static WebDriver startBrowser() throws IOException {
		if (getPropertyValue("BrowserName").equals("chrome")) {
			WebDriver driver = null;
			WebDriverManager.chromedriver().clearDriverCache();
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--disable-gpu", "incognito",
					"window-size=1920,1080", "--ignore-certificate-errors");
			driver = new ChromeDriver(options);
			driver.manage().deleteAllCookies();
			driver.manage().window().maximize();
			return driver;
		} else if (getPropertyValue("BrowserName").equals("edge")) {
			WebDriver driver = null;
			WebDriverManager.edgedriver().clearDriverCache();
			WebDriverManager.edgedriver().setup();
			EdgeOptions options = new EdgeOptions();
			driver = new EdgeDriver(options);
			driver.manage().deleteAllCookies();
			driver.manage().window().maximize();
			return driver;
		} else if (getPropertyValue("BrowserName").equals("firefox")) {
			WebDriver driver = null;
			WebDriverManager.firefoxdriver().clearDriverCache();
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions options = new FirefoxOptions();
			options.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--disable-gpu", "-private");
			driver = new FirefoxDriver(options);
			driver.manage().deleteAllCookies();
			driver.manage().window().maximize();
			return driver;
		}
		return null;
		// "--headless", "window-size=1920,1080"
	}

}
