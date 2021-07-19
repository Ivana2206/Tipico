package test;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import pages.*;

public class TestPage {

	public static WebDriver driver;
	public static WebDriverWait wait;

	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;

	Date date = new Date();
	SimpleDateFormat formatDate = new SimpleDateFormat("ddMMyy");

	private static String PAGE_URL = "https://sports.tipico.de/en/";

	public static SportsBettingPage sportsBettingPage;

	@BeforeSuite
	public void setExtent() throws Exception {

		htmlReporter = new ExtentHtmlReporter(
				System.getProperty("user.dir") + "./Report/" + formatDate.format(date) + "/Tipico.html");
		htmlReporter.config().setDocumentTitle("Tipico Sports Betting Report");// title of the report
		htmlReporter.config().setReportName("Tipico Sports Betting Report");// name of the report
		htmlReporter.config().setTheme(Theme.STANDARD);

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("environment", "test");
		extent.setSystemInfo("Browser", "Chrome");
	
	}

	@BeforeTest
	public void openBrowser() {

		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		driver = new ChromeDriver();
		driver.navigate().to(PAGE_URL);
		driver.manage().window().maximize();

		sportsBettingPage = new SportsBettingPage(driver);

	}

	@BeforeMethod
	public static void implicitWait() throws IOException, InterruptedException {

		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

	}

	@Test(priority = 0)
	public void testSportBettingPage() throws IOException, InterruptedException {
		
		sportsBettingPage.acceptAllCookies();
		sportsBettingPage.topSports();
		sportsBettingPage.selectSomeBetSlip();
		sportsBettingPage.eventSelected();
		sportsBettingPage.betslipSelectedCount();
		sportsBettingPage.deleteSelectedBetSlip();
		sportsBettingPage.betslipSelectedCount();
	}

	public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException {

		String dateName = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);

		String destination = System.getProperty("user.dir") + "\\Report\\ScreenshotFailed" + screenshotName + dateName
				+ ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);

		return destination;
	}

	@AfterMethod
	public void tearDown(ITestResult result) throws IOException, InterruptedException {

		if (result.getStatus() == result.FAILURE) {

			test.log(Status.FAIL, "TEST CASE FAILED IS " + result.getName());
			test.log(Status.FAIL, "TEST CASE FAILED IS " + result.getThrowable() + ExtentColor.RED);

			String screenPath = TestPage.getScreenshot(driver, result.getName());

			test.addScreenCaptureFromPath(screenPath);

		} else if (result.getStatus() == result.SKIP) {
			test.log(Status.SKIP, "Test case SKIPPED IS " + result.getName());

		}
	}

	@AfterSuite
	public void closeDriver() {

		extent.flush();
		driver.close();
		driver.quit();
	}

}