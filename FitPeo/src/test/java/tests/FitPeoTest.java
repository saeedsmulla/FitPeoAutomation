package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FitPeoTest{
	//Initialzation
	WebDriver driver = new ChromeDriver();
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	Actions actions = new Actions(driver);
	JavascriptExecutor js = (JavascriptExecutor) driver;

    private ExtentReports extent;
    private static ExtentTest test;
    

    @BeforeAll
    void setUp() {
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("test-Reports/FitPeoReport.html");
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle("FitPeo Test Report");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        test = extent.createTest("FitPeo Revenue Calculator Test");
    }

    //Main Test Method
    @Test
    void testFitProWorkflow() throws Exception {
        try {
            navigateToHomePage();
            navigateToRevenueCalculator();
            scrollToSliderSection();
            adjustSlider(820);
            updateTextBoxValue("560");
            selectCPTCodes();
            validateTotalRecurringReimbursement("$110700");
        } catch (Exception e) {
            test.fail("Test failed: " + e.getMessage());
			Assertions.assertTrue(false,e.getMessage());
        }
    }

    //Method to navigate URL
    void navigateToHomePage() {
        try {
			driver.get("https://fitpeo.com/");
			driver.manage().window().maximize();
			if(driver.getCurrentUrl().contains("fitpeo.com")) {
				test.pass("Navigated to FitPeo Homepage.");
				System.out.println("Navigated to Fitpeo Homepage");

			}else {
				test.fail("failed to Navigate Fitpeo homepage");
				System.out.println("failed to Navigate to Fitpeo Homepage");
				Assertions.assertTrue(false,"Failed to navigate Homepage");
			}
		} catch (Exception e) {
            test.fail("Test failed: " + e.getMessage());
			Assertions.assertTrue(false,e.getMessage());
		}
    }

    //Method to navigate Revenue Calculator page
    void navigateToRevenueCalculator() throws InterruptedException {
        try {
			WebElement revenueCalculatorLink = wait.until(
			        ExpectedConditions.elementToBeClickable(By.linkText("Revenue Calculator"))
			);
			revenueCalculatorLink.click();
			Thread.sleep(4000);                                                            //Static wait to load revenue page
			if(driver.getCurrentUrl().contains("revenue-calculator")) {
				test.pass("Navigated to Revenue Calculator Page.");
				System.out.println("Navigated to Revenue Calculator Page.");
			}else {
				test.fail("failed to Navigate revenue calculator page");
				System.out.println("failed to Navigate to revenue calculator page");
				Assertions.assertTrue(false,"Failed to navigate revenue calculator page");
			}
			
		} catch (InterruptedException e) {
            test.fail("Test failed: " + e.getMessage());
			Assertions.assertTrue(false,e.getMessage());
		}

    }

    //Method to scroll 
    void scrollToSliderSection() {
        try {
			WebElement sliderSection = wait.until(
			        ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='MuiBox-root css-j7qwjs']"))
			);
			js.executeScript("arguments[0].scrollIntoView(true);", sliderSection);
			js.executeScript("scroll(0, 250);");
			test.pass("Scrolled to the Slider section.");
			System.out.println("Slider Section Visible");
		} catch (Exception e) {
            test.fail("Test failed: " + e.getMessage());
			Assertions.assertTrue(false,e.getMessage());

		}
    }

    //Method to move slider or Update slider
    //Here I can use moveToLocation() method by Action class but it will be not reusable because it uses X, Y co-ordinates
    void adjustSlider(int targetValue) throws InterruptedException {
        try {
			WebElement slider = driver.findElement(By.xpath("//input[@type='range']"));
			WebElement textBox = driver.findElement(By.xpath("//input[contains(@class, 'MuiInputBase-input')]"));
			js.executeScript("arguments[0].scrollIntoView(true);", slider);

			int currentValue = Integer.parseInt(slider.getAttribute("value"));
			int keyPressLength = targetValue - currentValue + 2;

			actions.click(slider).build().perform();
			Keys key = keyPressLength > 0 ? Keys.ARROW_RIGHT : Keys.ARROW_LEFT;
			keyPressLength = Math.abs(keyPressLength);
			js.executeScript("scroll(0, 250);");
			
			for (int i = 0; i <= keyPressLength; i++) {
			    actions.sendKeys(key).build().perform();
			}

			Thread.sleep(2000); // Wait for slider to settle
			if(String.valueOf(targetValue).equals(slider.getAttribute("value"))) {
				test.pass("Slider adjusted to " + targetValue);
				System.out.println("Slider adjusted to " + targetValue);
			}else {
				test.fail("failed to move slider value");
				System.out.println("failed to move slider value");
				Assertions.assertTrue(false,"failed to move slider value");
			}
			if(String.valueOf(targetValue).equals(textBox.getAttribute("value"))) {
				test.pass("Text box value updated with slider " + targetValue);
				System.out.println("Text box value updated with slider  " + targetValue);
			}else {
				test.fail("failed text box value not updated as per slider value" + textBox.getAttribute("value"));
				System.out.println("failed to move slider value" + textBox.getAttribute("value"));
				Assertions.assertTrue(false,"failed to move slider value" + textBox.getAttribute("value"));
			}
		} catch (NumberFormatException e) {
			test.fail("Test failed: " + e.getMessage());
			Assertions.assertTrue(false,e.getMessage());
		} catch (InterruptedException e) {
			test.fail("Test failed: " + e.getMessage());
			Assertions.assertTrue(false,e.getMessage());
		}
    }

    //Method to enter value in text box and verifying with slider
    void updateTextBoxValue(String newValue) {
		try {
			WebElement textBoxContainer = driver.findElement(By.cssSelector(".MuiTextField-root"));
			WebElement textBox = driver.findElement(By.xpath("//input[contains(@class, 'MuiInputBase-input')]"));
			WebElement slider = driver.findElement(By.xpath("//input[@type='range']"));
			js.executeScript("arguments[0].scrollIntoView(true);", textBoxContainer);
			js.executeScript("scroll(0, 250);");
			js.executeScript("var element = arguments[0];" + "element.focus();" + "element.select();" + "document.execCommand('delete');", textBox);
			textBox.sendKeys(newValue);
			
			if(newValue.equals(textBox.getAttribute("value"))) {
				test.pass("Slider adjusted to " + newValue);
				System.out.println("Slider adjusted to " + newValue);
			}else {
				test.fail("failed text box value not updated");
				System.out.println("failed text box value not updated");
				Assertions.assertTrue(false,"failed text box value not updated");
			}
			
			if(newValue.equals(slider.getAttribute("value"))) {
				test.pass("Slider updated to " + newValue);
				System.out.println("Slider updated to " + newValue);
			}else {
				test.fail("failed slider value not updated as per text box value");
				System.out.println("failed slider value not updated as per text box value");
				Assertions.assertTrue(false,"failed slider value not updated as per text box value");
			}
			
		} catch (Exception e) {
			test.fail("Test failed: " + e.getMessage());
			Assertions.assertTrue(false,e.getMessage());
		}
    }

    //method to add CPT codes
    void selectCPTCodes() throws InterruptedException {
        try {
			String[] cptCodes = {"CPT-99091", "CPT-99453", "CPT-99454", "CPT-99474"};
			for (String cptCode : cptCodes) {
			    WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(
			            By.xpath("//p[text()='" + cptCode + "']/following::input[@type='checkbox'][1]")));
				WebElement cbLable = driver.findElement(By.xpath("//p[text()='" + cptCode + "']"));
				js.executeScript("arguments[0].scrollIntoView(true);", cbLable);
			    if (!checkbox.isSelected()) {
			        js.executeScript("arguments[0].click();", checkbox);
			    }
			    Assertions.assertTrue(checkbox.isSelected(), "Failed to select CPT code: " + cptCode);
			}
			js.executeScript("scroll(0,500);");
			test.pass("Selected all required CPT codes.");
			System.out.println("Selected all required CPT codes.");
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			test.fail("Test failed: " + e.getMessage());
			Assertions.assertTrue(false,e.getMessage());
		}
    }

    //method to validate final total recurring reimbursement amount 
    void validateTotalRecurringReimbursement(String expectedValue) {
        try {
			WebElement reimbursementValue = wait.until(
			        ExpectedConditions.presenceOfElementLocated(By.cssSelector("p:nth-child(4) p:nth-child(1)"))
			);
			String currentValue = reimbursementValue.getText();
			if(currentValue.equals(expectedValue)) {
				test.pass("Total Recurring Reimbursement validated as Expected: " + expectedValue + "Current: " + currentValue);
				System.out.println("Total Recurring Reimbursement validated as Expected: " + expectedValue + "Current: " + currentValue);
			}else {
				test.fail("Failed to validate Total Recurring Reimbursement. Expected: " + expectedValue + "Current: " + currentValue);
				System.out.println("Failed to validate Total Recurring Reimbursement. Expected: " + expectedValue + "Current: " + currentValue);
				Assertions.assertTrue(false);
			}

		} catch (Exception e) {
			test.fail("Test failed: " + e.getMessage());
			Assertions.assertTrue(false, e.getMessage());
		}
    }
    
    @AfterAll
    void tearDown() {
        // Generate Report and Close Browser
        extent.flush();
        driver.quit();
    }
    
    
}
