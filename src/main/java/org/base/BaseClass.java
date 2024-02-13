package org.base;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.javafaker.Faker;

public class BaseClass {
	public WebDriverWait wait;
	public static WebDriver driver;
	static WebElement element = null;
	static WebElement targetelement = null;
	List<WebElement> listElement = null;
	static String dummy;
	public static String invalid;

	public String inputText(Object elementOrLocator, String value, int num) {
		wait = new WebDriverWait(driver, num);
		if (elementOrLocator instanceof WebElement) {
			element = wait.until(ExpectedConditions.visibilityOf((WebElement) elementOrLocator));
		} else if (elementOrLocator instanceof By) {
			element = wait.until(ExpectedConditions.visibilityOfElementLocated((By) elementOrLocator));
		}
		element.sendKeys(value);
		return value;
	}

	public String tabInput(Object elementOrLocator, String value, int num) {
		wait = new WebDriverWait(driver, num);
		if (elementOrLocator instanceof WebElement) {
			element = wait.until(ExpectedConditions.visibilityOf((WebElement) elementOrLocator));
		} else if (elementOrLocator instanceof By) {
			element = wait.until(ExpectedConditions.visibilityOfElementLocated((By) elementOrLocator));
		}
		element.sendKeys(value, Keys.TAB);
		return value;
	}

	public void jsInputText(Object elementOrLocator, String value) {
		((JavascriptExecutor) driver)
				.executeScript("document.getElementById('" + elementOrLocator + "').value='" + value + "';");
	}

	public void mouseActionClicks(Object elementOrLocator, int value) {
		wait = new WebDriverWait(driver, value);
		if (elementOrLocator instanceof WebElement) {
			element = wait.until(ExpectedConditions.visibilityOf((WebElement) elementOrLocator));
		} else if (elementOrLocator instanceof By) {
			element = wait.until(ExpectedConditions.visibilityOfElementLocated((By) elementOrLocator));
		}
		Actions actions = new Actions(driver);
		actions.moveToElement(element).click().build().perform();
	}

	static String text;

	public String checkErrorMessage(Object element, Object submitButton) {
		int i = 0;
		if (!this.conditionChecking(element, 3)) {
			do {
				this.clickButton(submitButton, 5, "JS Click");
				i++;
			} while ((!this.conditionChecking(element, 3)) && i < 5);
		}
		if (i == 5) {
			text = "null";
		} else {
			text = this.getText(element, 10);
		}
		return text;
	}

	public void dragAndDrop(WebElement elementOrLocator, WebElement tragetLocator, int value) {
		wait = new WebDriverWait(driver, value);
		element = wait.until(ExpectedConditions.elementToBeClickable(elementOrLocator));
		targetelement = wait.until(ExpectedConditions.elementToBeClickable(tragetLocator));
		Actions actions = new Actions(driver);
		actions.dragAndDrop(element, targetelement).build().perform();
	}

	public void mouseAction(Object elementOrLocator, int value) {
		wait = new WebDriverWait(driver, value);
		if (elementOrLocator instanceof WebElement) {
			element = wait.until(ExpectedConditions.visibilityOf((WebElement) elementOrLocator));
		} else if (elementOrLocator instanceof By) {
			element = wait.until(ExpectedConditions.visibilityOfElementLocated((By) elementOrLocator));
		}
		Actions actions = new Actions(driver);
		actions.moveToElement(element).perform();
	}

	public Result listWebElement(Object elementOrLocator, int value) {
		wait = new WebDriverWait(driver, value);
		if (elementOrLocator instanceof WebElement) {
			listElement = wait.until(ExpectedConditions.visibilityOfAllElements((WebElement) elementOrLocator));
		} else if (elementOrLocator instanceof By) {
			listElement = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy((By) elementOrLocator));
		}
		return new Result(listElement.size(), listElement);
	}

	public static class Result {
		private int size;
		private List<WebElement> elements;

		public Result(int size, List<WebElement> elements) {
			this.size = size;
			this.elements = elements;
		}

		public int getSize() {
			return size;
		}

		public List<WebElement> getElements() {
			return elements;
		}
	}

	public void clickButton(Object elementOrLocator, int value, String event) {
		wait = new WebDriverWait(driver, value);
		if (elementOrLocator instanceof WebElement) {
			element = wait.until(ExpectedConditions.visibilityOf((WebElement) elementOrLocator));
		} else if (elementOrLocator instanceof By) {
			element = wait.until(ExpectedConditions.visibilityOfElementLocated((By) elementOrLocator));
		}
		if (event.equals("Click")) {
			element.click();
		} else if (event.equals("JS Click")) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
		}
	}

	public void clearField(Object elementOrLocator, int value) {
		wait = new WebDriverWait(driver, value);
		if (elementOrLocator instanceof WebElement) {
			element = wait.until(ExpectedConditions.visibilityOf((WebElement) elementOrLocator));
		} else if (elementOrLocator instanceof By) {
			element = wait.until(ExpectedConditions.visibilityOfElementLocated((By) elementOrLocator));
		}
		element.clear();
	}

	public String getText(Object elementOrLocator, int value) {
		wait = new WebDriverWait(driver, value);
		if (elementOrLocator instanceof WebElement) {
			element = wait.until(ExpectedConditions.visibilityOf((WebElement) elementOrLocator));
		} else if (elementOrLocator instanceof By) {
			element = wait.until(ExpectedConditions.visibilityOfElementLocated((By) elementOrLocator));
		}
		return element.getText();
	}

	public String getTextAttribute(Object elementOrLocator, int value) {
		wait = new WebDriverWait(driver, value);
		if (elementOrLocator instanceof WebElement) {
			element = wait.until(ExpectedConditions.visibilityOf((WebElement) elementOrLocator));
		} else if (elementOrLocator instanceof By) {
			element = wait.until(ExpectedConditions.visibilityOfElementLocated((By) elementOrLocator));
		}
		return element.getAttribute("value");
	}

	public void invisible(Object elementOrLocator, int value) {
		wait = new WebDriverWait(driver, value);
		if (elementOrLocator instanceof WebElement) {
			wait.until(ExpectedConditions.invisibilityOf((WebElement) elementOrLocator));
		} else if (elementOrLocator instanceof By) {
			wait.until(ExpectedConditions.invisibilityOfElementLocated((By) elementOrLocator));
		}
	}

	public void newWindowWait(String value, int num, int tab) {
		((JavascriptExecutor) driver).executeScript("window.open('" + value + "','_blank');");
		wait = new WebDriverWait(driver, num);
		wait.until(ExpectedConditions.numberOfWindowsToBe(tab));
	}

	public int checkResponseCode() throws IOException {
		String url = driver.getCurrentUrl();
		CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier((host, session) -> true).build();
		HttpGet request = new HttpGet(url);
		HttpResponse response = httpClient.execute(request);
		int responseCode = response.getStatusLine().getStatusCode();
		return responseCode;
	}

	public void windowsHandle(int value) {
		Set<String> windowHandles = driver.getWindowHandles();
		ArrayList<String> list = new ArrayList<String>(windowHandles);
		driver.switchTo().window(list.get(value));
	}

	public Boolean conditionChecking(Object elementOrLocator, int value) {
		Boolean text = false;
		wait = new WebDriverWait(driver, value);
		try {
			if (elementOrLocator instanceof WebElement) {
				element = wait.until(ExpectedConditions.visibilityOf((WebElement) elementOrLocator));
			} else if (elementOrLocator instanceof By) {
				element = wait.until(ExpectedConditions.visibilityOfElementLocated((By) elementOrLocator));
			}
			text = element.isEnabled();
		} catch (Exception e) {
			return text;
		}
		return text;
	}

	public Boolean invisibleConditionChecking(Object elementOrLocator, int value) {
		Boolean text = false;
		wait = new WebDriverWait(driver, value);
		try {
			if (elementOrLocator instanceof WebElement) {
				text = wait.until(ExpectedConditions.invisibilityOf((WebElement) elementOrLocator));
			} else if (elementOrLocator instanceof By) {
				text = wait.until(ExpectedConditions.invisibilityOfElementLocated((By) elementOrLocator));
			}
		} catch (Exception e) {
			return text;
		}
		return text;
	}

	public Boolean invisibleCheckListElement(Object elementOrLocator, int value) {
		Boolean text = false;
		wait = new WebDriverWait(driver, value);
		try {
			if (elementOrLocator instanceof WebElement) {
				text = wait.until(ExpectedConditions.invisibilityOfAllElements((WebElement) elementOrLocator));
			}
		} catch (Exception e) {
			return text;
		}
		return text;
	}

	public Boolean valuePresentCondition(Object elementOrLocator, int value, String data) {
		Boolean text = false;
		wait = new WebDriverWait(driver, value);
		try {
			if (elementOrLocator instanceof WebElement) {
				text = wait
						.until(ExpectedConditions.textToBePresentInElementValue(((WebElement) elementOrLocator), data));
			} else if (elementOrLocator instanceof By) {
				text = wait.until(ExpectedConditions.textToBePresentInElementValue(((By) elementOrLocator), data));
			}
		} catch (Exception e) {
			return text;
		}
		return text;
	}

	public void dropDownByIndex(Object elementOrLocator, int value, int num) {
		wait = new WebDriverWait(driver, value);
		if (elementOrLocator instanceof WebElement) {
			element = wait.until(ExpectedConditions.visibilityOf((WebElement) elementOrLocator));
		} else if (elementOrLocator instanceof By) {
			element = wait.until(ExpectedConditions.visibilityOfElementLocated((By) elementOrLocator));
		}
		Select select = new Select(element);
		select.selectByIndex(num);
	}

	public String dummyData(String value) throws IOException {
		Faker faker = new Faker();
		switch (value) {
		case "First Name":
			dummy = faker.name().firstName().replaceAll("[^a-zA-Z0-9 ]", "");
			break;
		case "Last Name":
			dummy = faker.name().lastName().replaceAll("[^a-zA-Z0-9 ]", "");
			break;
		case "Email":
			dummy = faker.internet().safeEmailAddress();
			break;
		case "Phone Number":
			dummy = faker.phoneNumber().phoneNumber();
			break;
		case "Address1":
			dummy = faker.address().buildingNumber();
			break;
		case "Address2":
			dummy = faker.address().streetName();
			break;
		case "City":
			dummy = faker.address().city();
			break;
		case "State":
			dummy = faker.address().state();
			break;
		case "Zipcode":
			dummy = faker.address().zipCode();
			break;
		case "Job Tittle":
			dummy = faker.name().title();
			break;
		case "category Name":
			dummy = faker.book().publisher().replaceAll("[^a-zA-Z0-9 ]", "");
			dummy = dummy + " " + RandomStringUtils.randomAlphabetic(6);
			break;
		case "Website URL":
			dummy = faker.internet().url();
			dummy = dummy.replace("-", "");
			break;
		case "Heading":
			dummy = faker.book().title();
			break;
		case "Business Name":
			dummy = faker.company().name();
			break;
		default:
			break;
		}
		return dummy;
	}

	public static String getPropertyValue(String key) throws IOException {
		Properties properties = new Properties();
		FileInputStream stream = new FileInputStream(
				System.getProperty("user.dir") + "\\Reusable Records\\config.properties");
		properties.load(stream);
		String value = (String) properties.get(key);
		return value;
	}

	static String upper_Case = RandomStringUtils.randomAlphabetic(7).toUpperCase();

	public String randomCharacters(String value) throws IOException {
		switch (value) {
		case "Max 256 Characters":
			dummy = RandomStringUtils.randomAlphabetic(257);
			break;
		case "Max 1000 Characters":
			dummy = RandomStringUtils.randomAlphabetic(1000);
			break;
		case "Max 512 Characters":
			dummy = RandomStringUtils.randomAlphabetic(512);
			break;
		case "Max 2048 Characters":
			dummy = RandomStringUtils.randomAlphabetic(2048);
			break;
		case "Max 10 Characters":
			dummy = RandomStringUtils.randomNumeric(10);
			break;
		case "Max 69 Characters":
			dummy = RandomStringUtils.randomAlphabetic(69);
			break;
		case "Max 16 Characters":
			dummy = RandomStringUtils.randomNumeric(16);
			break;
		case "Max 4 Characters":
			dummy = RandomStringUtils.randomNumeric(4);
			break;
		case "Max 3 Characters":
			dummy = RandomStringUtils.randomNumeric(3);
			break;
		case "Dummy Description":
			int numberOfWords = 10;
			dummy = generateDummyDescription(numberOfWords);
			break;
		case "Dummy Roles":
			dummy = generateRandomRole();
			dummy = dummy + " " + upper_Case;
			break;
		case "Color Code":
			dummy = colorCode();
			break;
		case "Dummy Store":
			dummy = storeName();
			dummy = dummy + " " + upper_Case;
			break;
		case "Dummy Address":
			dummy = address();
			break;
		case "Dummy IP Address":
			dummy = generateRandomIPv4Address();
			break;
		case "Dummy Printer Name":
			dummy = generateRandomPrinterName();
			dummy = dummy + " " + upper_Case;
			break;
		case "Dummy Hub Name":
			dummy = generateRandomHubName();
			dummy = dummy + " " + upper_Case;
			break;
		case "1 Hour Timing":
			dummy = timeFormat("1 Hour Timing");
			break;
		case "1 Hour Custome Timing":
			dummy = timeFormat24Hour("1 Hour Timing");
			break;
		case "3 Hour Timing":
			dummy = timeFormat("4 Hour Timing");
			break;
		case "3 Hour Custome Timing":
			dummy = timeFormat24Hour("4 Hour Timing");
			break;
		case "Invalid Timing":
			dummy = timeFormat("Invalid Time");
			break;
		case "Past Timing":
			dummy = timeFormat("Past Timing");
			break;
		case "Dummy Delivery Schedule Name":
			dummy = deliveryScheduleName();
			break;
		case "Dummy Pickup Schedule Name":
			dummy = pickupScheduleName();
			break;
		case "Dummy Route Name":
			dummy = routeName() + " " + upper_Case;
			break;
		case "Dummy Lead Source":
			dummy = generateRandomLeadSource() + " " + upper_Case;
			break;
		case "Dummy 8 Characters":
			dummy = RandomStringUtils.randomAlphabetic(8).toUpperCase();
			break;
		}
		return dummy;

	}

	public static void attachmentFile(String location) throws AWTException {
		StringSelection selection = new StringSelection(location);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

	}

	static int i = 0;
	static String randomNumeric;

	public static String validationCharacterSize() {
		Random r = new Random();
		int u = 4;
		int nextInt;
		do {
			nextInt = r.nextInt(u);
		} while (nextInt == 0);
		do {
			randomNumeric = RandomStringUtils.randomNumeric(nextInt);
		} while (Integer.parseInt(randomNumeric) >= 10 || randomNumeric.equals("0") || randomNumeric.equals("00")
				|| randomNumeric.equals("000"));
		return randomNumeric;
	}

	public static void ScreenShots(String path) throws IOException {
		TakesScreenshot screenshot = (TakesScreenshot) driver;
		File screenshotAs = screenshot.getScreenshotAs(OutputType.FILE);
		File file = new File(path);
		FileHandler.copy(screenshotAs, file);
	}

	public WebElement visible(Object elementOrLocator, int value) {
		wait = new WebDriverWait(driver, value);
		if (elementOrLocator instanceof WebElement) {
			element = wait.until(ExpectedConditions.visibilityOf((WebElement) elementOrLocator));
		} else if (elementOrLocator instanceof By) {
			element = wait.until(ExpectedConditions.visibilityOfElementLocated((By) elementOrLocator));
		}
		return element;
	}

	public static String generateDummyDescription(int numberOfWords) {
		String[] loremIpsum = { "Lorem", "ipsum", "dolor", "sit", "amet", "consectetur", "adipiscing", "elit", "sed",
				"do", "eiusmod", "tempor", "incididunt", "ut", "labore", "et", "dolore", "magna", "aliqua", "Ut",
				"enim", "ad", "minim", "veniam", "quis", "nostrud", "exercitation", "ullamco", "laboris", "nisi", "ut",
				"aliquip", "ex", "ea", "commodo", "consequat", "Duis", "aute", "irure", "dolor", "in", "reprehenderit",
				"in", "voluptate", "velit", "esse", "cillum", "dolore", "eu", "fugiat", "nulla", "pariatur" };
		StringBuilder description = new StringBuilder();
		for (int i = 0; i < numberOfWords; i++) {
			int randomIndex = (int) (Math.random() * loremIpsum.length);
			description.append(loremIpsum[randomIndex]).append(" ");
		}
		return description.toString();
	}

	public static String generateRandomRole() {
		String[] roles = { "Administrator", "Manager", "User", "Guest", "Developer", "Supervisor", "Analyst", "Tester",
				"Support", "Operator" };
		int randomIndex = (int) (Math.random() * roles.length);
		return roles[randomIndex];
	}

	public String colorCode() {
		Random random = new Random();
		int red = random.nextInt(256);
		int green = random.nextInt(256);
		int blue = random.nextInt(256);
		return String.format("#%02X%02X%02X", red, green, blue);
	}

	public String storeName() {
		String[] adjectives = { "Vintage", "Groovy", "Eclectic", "Harmonious", "Charming" };
		String[] nouns = { "Vinyl", "Records", "Tunes", "Melodies", "Beats" };
		Random random = new Random();
		String randomAdjective = adjectives[random.nextInt(adjectives.length)];
		String randomNoun = nouns[random.nextInt(nouns.length)];
		return randomAdjective + " " + randomNoun;

	}

	public String address() throws IOException {
		String[] streetNames = { dummyData("Address1"), dummyData("Address2") };
		String[] cities = { dummyData("City") };
		String[] states = { dummyData("State"), };
		String[] postalCodes = { dummyData("Zipcode") };
		Random random = new Random();
		String randomStreet = streetNames[random.nextInt(streetNames.length)];
		int randomStreetNumber = random.nextInt(1000) + 1; // Generate a number between 1 and 1000
		String randomCity = cities[random.nextInt(cities.length)];
		String randomState = states[random.nextInt(states.length)];
		String randomPostalCode = postalCodes[random.nextInt(postalCodes.length)];
		return randomStreetNumber + " " + randomStreet + ", " + randomCity + ", " + randomState + " "
				+ randomPostalCode;
	}

	static FileOutputStream fo;

// Please use this format to store the string value
//	propertyInputValue("function",setName1,name1,setName2,name2)
	@SuppressWarnings("unchecked")
	public void propertyInputValue(String function, Object... input) throws IOException {
		Properties properties = new Properties();
		if (input.length == 1 && input[0] instanceof Map) {
			Map<String, String> mapInput = (Map<String, String>) input[0];
			for (Map.Entry<String, String> entry : mapInput.entrySet()) {
				properties.put(entry.getKey(), entry.getValue());
			}
		} else if (input.length % 2 == 0) {
			for (int i = 0; i < input.length; i += 2) {
				if (input[i] instanceof String && input[i + 1] instanceof String) {
					properties.put((String) input[i], (String) input[i + 1]);
				}
			}
		}
		fo = new FileOutputStream(System.getProperty("user.dir"));
		properties.store(fo, "Update Value");
	}

	static FileInputStream stream;

	public static String getUpdatedPropertyFile(String value, String key) throws IOException {
		Properties properties = new Properties();
		if (value.equals("Registration Page")) {
			stream = new FileInputStream(
					System.getProperty("user.dir") + "\\Reusable Records\\Registration.properties");
		}
		properties.load(stream);
		dummy = (String) properties.get(key);
		return dummy;
	}

	public String generateRandomIPv4Address() {
		Random rand = new Random();
		StringBuilder ipAddress = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			ipAddress.append(rand.nextInt(256)); // Generate a random number between 0 and 255
			if (i < 3) {
				ipAddress.append(".");
			}
		}
		return ipAddress.toString();
	}

	public static String generateRandomPrinterName() {
		String[] adjectives = { "Red", "Blue", "Green", "Yellow", "Fast", "Silent", "Smart" };
		String[] nouns = { "Printer", "Inkjet", "LaserJet", "Pro", "OfficeJet", "Pixma", "Epson" };

		Random rand = new Random();
		String adjective = adjectives[rand.nextInt(adjectives.length)];
		String noun = nouns[rand.nextInt(nouns.length)];

		return adjective + " " + noun;
	}

	public static String generateRandomHubName() {
		String[] adjectives = { "Tech", "Innovative", "Digital", "Smart", "Creative", "Connected" };
		String[] nouns = { "Hub", "Center", "Workspace", "Lab", "Zone", "Hubbub" };

		Random rand = new Random();
		String adjective = adjectives[rand.nextInt(adjectives.length)];
		String noun = nouns[rand.nextInt(nouns.length)];

		return adjective + " " + noun;
	}

	public static String timeZone() throws IOException {
		if (getUpdatedPropertyFile("City Town Managment", "StateName").toLowerCase().contains("florida")) {
			return "EST";
		} else if (getUpdatedPropertyFile("City Town Managment", "StateName").toLowerCase().contains("utah")) {
			return "MST";
		}
		return null;
	}

	public String timeFormat(String value) throws IOException {
		SimpleDateFormat sdfStopTime = new SimpleDateFormat("hh:mma", Locale.ENGLISH);
		sdfStopTime.setTimeZone(TimeZone.getTimeZone(timeZone()));
		Calendar c = Calendar.getInstance();
		if (value.equals("1 Hour Timing")) {
			c.add(Calendar.HOUR, 2);
		} else if (value.equals("4 Hour Timing")) {
			c.add(Calendar.HOUR, 3);
		} else if (value.equals("Invalid Time")) {
			c.add(Calendar.MINUTE, (int) 7.5);
		} else if (value.equals("Past Timing")) {
			c.add(Calendar.HOUR, 1);
		}
		Date d = c.getTime();
		return sdfStopTime.format(d);
	}

	public String timeFormat24Hour(String value) throws IOException {
		String formattedTime12Hour = timeFormat(value);
		try {
			SimpleDateFormat sdfStopTime24Hour = new SimpleDateFormat("HH:mm");
			Date date = new SimpleDateFormat("hh:mma", Locale.ENGLISH).parse(formattedTime12Hour);
			Calendar c = Calendar.getInstance();
			c.setTime(date);

			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minutes = c.get(Calendar.MINUTE);

			if ((hour == 2 && minutes >= 0 && minutes <= 30) || (hour == 2 && minutes > 30 && minutes <= 59)) {
				// If time is between 02:00 and 02:30, set it to 02:30
				c.set(Calendar.HOUR_OF_DAY, 2);
				c.set(Calendar.MINUTE, 30);
			} else {
				// Otherwise, round to the nearest 30 minutes
				int roundedMinutes = Math.round((float) minutes / 30) * 30;
				c.set(Calendar.MINUTE, roundedMinutes);
			}

			return sdfStopTime24Hour.format(c.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return formattedTime12Hour;
		}
	}

	public String deliveryScheduleName() {
		String[] scheduleNames = { "Morning Delivery", "Afternoon Delivery", "Evening Delivery", "Express Delivery",
				"Weekend Delivery", "Standard Delivery", "Next-Day Delivery" };
		// Create a Random object
		Random random = new Random();
		// Generate a random index to select a name from the array
		int randomIndex = random.nextInt(scheduleNames.length);
		// Get the random delivery schedule name
		String randomScheduleName = scheduleNames[randomIndex];
		String value = randomScheduleName + " " + RandomStringUtils.randomAlphabetic(7).toUpperCase();
		return value;
	}

	public String pickupScheduleName() {
		String[] scheduleNames = { "Morning Pickup", "Afternoon Pickup", "Evening Pickup", "Express Pickup",
				"Weekend Pickup", "Standard Pickup", "Next-Day Pickup" };
		// Create a Random object
		Random random = new Random();
		// Generate a random index to select a name from the array
		int randomIndex = random.nextInt(scheduleNames.length);
		// Get the random delivery schedule name
		String randomScheduleName = scheduleNames[randomIndex];
		String value = randomScheduleName + " " + RandomStringUtils.randomAlphabetic(7).toUpperCase();
		return value;
	}

	public static String dateFormat(int num) throws IOException {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, num);
		TimeZone utcTimeZone = TimeZone.getTimeZone(timeZone());
		formatter.setTimeZone(utcTimeZone);
		return formatter.format(cal.getTime());
	}

	public static String excelRead(int sheetName, int row, int cell) throws IOException {
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\Reusable Records\\Zippy_Form_Excel_Sheet.xlsx");
		Workbook workbook = WorkbookFactory.create(fis);
		Sheet sheet = workbook.getSheetAt(sheetName);
		Row r = sheet.getRow(row);
		Cell c = r.getCell(cell);
		String cellValue;
		if (c.getCellType() == CellType.NUMERIC) {
			double numericValue = c.getNumericCellValue();
			cellValue = String.valueOf(numericValue);
		} else if (c.getCellType() == CellType.STRING) {
			String stringValue = c.getStringCellValue();
			cellValue = stringValue;
		} else {
			cellValue = "Cell type not supported";
		}
		return cellValue;
	}

	public static String timeExcel(int sheetIndex, int rowIndex, int columnIndex) throws IOException, ParseException {
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\Reusable Records\\Zippy_Form_Excel_Sheet.xlsx");
		Workbook workbook = WorkbookFactory.create(fis);
		Sheet sheet = workbook.getSheetAt(sheetIndex);
		Row row = sheet.getRow(rowIndex);
		Cell timeCell = row.getCell(columnIndex);
		Date numericValue;
		if (timeCell.getCellType() == CellType.STRING) {
			String timeString = timeCell.getStringCellValue();
			numericValue = parseTime(timeString.replace(".", ":"));
		} else {
			numericValue = timeCell.getDateCellValue();
		}
		SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mma", Locale.ENGLISH);
		return outputFormat.format(numericValue);
	}

	private static Date parseTime(String timeString) throws ParseException {
		SimpleDateFormat inputFormats[] = { new SimpleDateFormat("hh:mma", Locale.ENGLISH),
				new SimpleDateFormat("h:mma", Locale.ENGLISH), new SimpleDateFormat("HH:mm", Locale.ENGLISH) };
		for (SimpleDateFormat format : inputFormats) {
			try {
				return format.parse(timeString);
			} catch (ParseException e) {
				// Try the next format
			}
		}

		throw new ParseException("Unable to parse the input time: " + timeString, 0);
	}

	public static String variableXpath;
	public static String[] textValues;
	public static Map<String, String> xpathMap = new HashMap<>();
	public static Map<String, String> checkMap = new HashMap<>();

	public void permissionClick(int sheetName, int rowValue, int celValue, String firstPath, String lastPath)
			throws IOException, InterruptedException {
		FileInputStream excelFile = new FileInputStream(
				System.getProperty("user.dir") + "\\Reusable Records\\Zippy_Form_Excel_Sheet.xlsx");
		Workbook workbook = new XSSFWorkbook(excelFile);

		Sheet sheet = workbook.getSheetAt(sheetName);
		Row row = sheet.getRow(rowValue);
		Cell cell = row.getCell(celValue);

		String cellValue = cell.getStringCellValue();

		if (cellValue.contains(", ")) {
			textValues = cellValue.split(", ");
		} else {
			textValues = cellValue.split(",");
		}

		for (String text : textValues) {
			String capitalizeEachWord = capitalizeEachWord(text.trim());
			String xpath = firstPath + capitalizeEachWord + lastPath;
			xpathMap.put(text, xpath);
			checkMap.put(text, capitalizeEachWord);
		}
		workbook.close();
		for (Map.Entry<String, String> entry : xpathMap.entrySet()) {
			if (conditionChecking(By.xpath(entry.getValue().replaceAll("Viop", "VOIP")), 5)) {
				this.mouseActionClicks(By.xpath(entry.getValue().replaceAll("Viop", "VOIP")), 10);
			}
		}
	}

	public static String capitalizeEachWord(String input) {
		StringBuilder result = new StringBuilder();
		for (String word : input.split(" ")) {
			if (!word.isEmpty()) {
				result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1).toLowerCase())
						.append(" ");
			}
		}
		return result.toString().trim();
	}

	public String currentDay() throws IOException {
		TimeZone timeZone = TimeZone.getTimeZone(timeZone());
		ZoneId zoneId = timeZone.toZoneId();
		ZonedDateTime currentDateTime = ZonedDateTime.now(zoneId);
		DayOfWeek dayOfWeek = currentDateTime.getDayOfWeek();
		String dayOfWeekString = dayOfWeek.toString();
		String firstThreeChars = dayOfWeekString.substring(0, 3);
		return firstThreeChars + " (";
	}

	public static String currentDayCamelNotation() throws IOException {
		TimeZone timeZone = TimeZone.getTimeZone(timeZone());
		ZoneId zoneId = timeZone.toZoneId();
		ZonedDateTime currentDateTime = ZonedDateTime.now(zoneId);
		DayOfWeek dayOfWeek = currentDateTime.getDayOfWeek();
		return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault());
	}

	public String routeName() {
		String[] routeNames = { "Mountain Loop", "Cityscape Circuit", "Lakeview Run", "Alpine Trail", "Sunset Drive" };
		Random random = new Random();
		int index = random.nextInt(routeNames.length);
		return routeNames[index];
	}

	public String generateRandomLeadSource() {
		String[] leadSources = { "Advertisement", "Referral", "Online Search", "Social Media", "Event", "Cold Call" };
		Random random = new Random();
		int index = random.nextInt(leadSources.length);
		return leadSources[index];
	}

	public String dataInputRecordCheck(String value, String data) {
		switch (value) {
		case "URL":
			String cleanedUrl = data.replaceAll("[^a-zA-Z0-9-._~:/?#@!$&'()*+,;=]", "");
			if (!cleanedUrl.matches("^(http|https)://.*")) {
				cleanedUrl = "https://" + cleanedUrl;
			}
			return cleanedUrl;
		case "Phone Number":
			String digitsOnly = data.replaceAll("[^0-9]", "");
			digitsOnly = digitsOnly.substring(0, Math.min(digitsOnly.length(), 10));
			if (digitsOnly.length() < 10) {
				digitsOnly = String.format("%s%0" + (10 - digitsOnly.length()) + "d", digitsOnly, 0);
			}
			return digitsOnly.substring(0, 3) + "-" + digitsOnly.substring(3, 6) + "-" + digitsOnly.substring(6);
		case "IP Address":
			String numericString = data.replaceAll("\\D", "");
			String[] groups = numericString.split("(?<=\\G\\d{3})");
			return String.join(".", groups);
		default:
			break;
		}
		return value;
	}

	public static List<Integer> getRandomIndices(int numberOfIndices, int maxIndex) {
		List<Integer> indices = new ArrayList<>();
		for (int i = 0; i < maxIndex; i++) {
			indices.add(i);
		}
		Collections.shuffle(indices, new Random());
		return indices.subList(0, numberOfIndices);
	}

	public void scrollDown() throws InterruptedException {
		Thread.sleep(5000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
		Thread.sleep(3000);
	}

	public void scrollUp() throws InterruptedException {
		Thread.sleep(5000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, -document.body.scrollHeight);");
		Thread.sleep(3000);
	}

	public WebElement shadowRootPath(String value) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return (WebElement) js.executeScript(value);
	}

}
