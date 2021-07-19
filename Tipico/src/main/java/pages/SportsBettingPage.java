package pages;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.aventstack.extentreports.Status;

import test.TestPage;

public class SportsBettingPage {

	public static WebDriver driver;
	
	static Random random;
	static int randomValueOfBetSelecting;
	static int betSlipTotalCount;

	@FindBy(id = "_evidon-accept-button")
	WebElement acceptCookies;

	@FindBy(xpath = "//*[text()='Quick']")
	WebElement quickLink;

	@FindBy(xpath = "//img[contains(@class, 'NavigationItemSimple-styles-icon')]/..//span/a")
	List<WebElement> topSportsGetName;

	@FindBy(xpath = "//*/button[contains (@class,'EventOddButton-styles-odd-button')][not(@class='EventOddButton-styles-odd-button EventOddButton-styles-stopped')][not(@class='EventOddButton-styles-odd-button EventOddButton-styles-selected')]")
	List<WebElement> selectSomeQuota;

	@FindBy(xpath = "//*[@class='EventOddButton-styles-selected ']")
	List<WebElement> betSelected;

	@FindBy(xpath = "//*/button[contains(@class,'EventOddButton-styles-selected')]")
	List<WebElement> quoteSelected;

	@FindBy(xpath = "//*[text()='Betslip']//..//div[not( text()='Betslip')]")
	WebElement betslipSelectedCount;

	
	public SportsBettingPage(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
	
	}

	
	public void acceptAllCookies() {
		
	TestPage.test = TestPage.extent.createTest("Verify that pop-up window 'Privacy' in displayed");
		
		if(acceptCookies.isDisplayed()) {
			acceptCookies.click();
			
	TestPage.test.log(Status.PASS,
			"The 'Privacy' pop-up window is displayed");

		} else  {
			
			TestPage.test.log(Status.ERROR,
					"The 'Privacy' pop-up window is NOT displayed");
			
		}

	}

	public void topSports() {
		
		TestPage.test = TestPage.extent.createTest("Verify if the list of sports is displayed");
		
		try {
			quickLink.click();

			System.out.print("Sports present are : ");
			
			TestPage.test.log(Status.PASS,
					"The sports are displayed");

			for (int i = 0; i < topSportsGetName.size(); i++) {

				System.out.print(topSportsGetName.get(i).getText() + ", ");
			}

		} catch (Exception e) {
			
			TestPage.test.log(Status.ERROR,
					"Sports are not present some problem with the system");
			System.out.println("ERROR : Sports are not present some problem with the system");
		}

	}

	public void selectSomeBetSlip() {
		
		TestPage.test =	TestPage.extent.createTest("Choose a random quota");

		int min = 5;
		int max = 10;

		random = new Random();

		randomValueOfBetSelecting = random.nextInt(max - min) + min;

		System.out.println("\nThe number of random value of the bet selection " + randomValueOfBetSelecting);

		for (int i = 0; i < randomValueOfBetSelecting; i++) {
			int randomValue = random.nextInt(selectSomeQuota.size());
			try {
				
				selectSomeQuota.get(randomValue).click();
				
				TestPage.test.log(Status.PASS,
						"Selected quota is " + selectSomeQuota.get(randomValue).getAttribute("id"));
			} catch (Exception e) {
				
				TestPage.test.log(Status.WARNING,
						"Quota is not currently able to be choose");
				System.out.println("Quota " + selectSomeQuota.get(randomValue).getAttribute("id") + " is not currently able to be choose");
			}
		}

	}

	public void eventSelected() {
		
		
		for (WebElement e : quoteSelected) {
			TestPage.test.log(Status.PASS,
					"Quota of event selected is " + e.getText() + "  and id is " + e.getAttribute("id"));
			System.out.println("Quota of event selected is " + e.getText() + "  and id is " + e.getAttribute("id"));
		}

	}


	public void betslipSelectedCount() {

		TestPage.test = TestPage.extent.createTest("Verify if selected quotas are the same as total count of betslip");

		betSlipTotalCount = Integer.valueOf(betslipSelectedCount.getText());
		int randomValueOfBetSelecting = Integer.valueOf(betSelected.size());
		System.out.println("Total count of selected betslips is  : " + betSlipTotalCount);

		if (randomValueOfBetSelecting == betSlipTotalCount) {
			System.out.println("CORRECT : Total count of selected betslip is : " + betSlipTotalCount
					+ "  and number of Quota selected is the same : " + randomValueOfBetSelecting);
			
			TestPage.test.log(Status.PASS,
					"Total count of selected betslip is : " + betSlipTotalCount 
						+ "  and number of Quota selected is the same : " + randomValueOfBetSelecting);
			
		} else {
			System.out.println("ERROR : Total count of selected betslip is : " + betSlipTotalCount
					+ "  and number of Quota selected is NOT the same : " + randomValueOfBetSelecting);
			
			TestPage.test.log(Status.ERROR,
					"Total count of selected betslip is : " + betSlipTotalCount  
										+ "  and number of Quota selected is NOT the same : " + randomValueOfBetSelecting);
			

		}

	}

	public void deleteSelectedBetSlip() {
		
		TestPage.test = TestPage.extent.createTest("Delete total number " + betSlipTotalCount + " of selected Bet Slip ");
		
		System.out.println("Delete total number " + betSlipTotalCount + " of selected Bet Slip ");
		for (WebElement qS : quoteSelected) {
			try {
				qS.click();
				
				TestPage.test.log(Status.PASS,
						"Quota " + qS.getAttribute("id") + " is deleted");

			} catch (Exception e) {
				
				TestPage.test.log(Status.WARNING,
						"Quota " + qS.getAttribute("id") + " is not currently able for deleting");

				System.out.println("Quota " + qS.getAttribute("id") + " is not currently able for deleting");
			}
		}

	}

}