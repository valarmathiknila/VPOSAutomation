package testClassCreatePaylink;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.internal.ExpectedExceptionsHolder;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pageObjectCreatePaylink.createPaylinkPage;
import pageObjectCreatePaylink.loginPage;
import pageObjectCreatePaylink.paylinkPaymentPage;

@Listeners({ testListener.class })

public class eCommerceMakePayment {

	// Instance Creation of the Class
	public WebDriver driver;
	baseClass base;
	loginPage poLogin;
	createPaylinkPage poCreatePaylink;
	paylinkPaymentPage poPaylinkPayment;
	testListener lis;
	commonLibrary comlib;
	WebDriverWait wait;
	JavascriptExecutor js;

	// Globalvariable declaration
	public String datadirectorypath;
	int rowindex = 0;
	public String parenturl = "";
	public static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	public String s;
	public int rowInTable;
	public String uniquename;
	public String urlAdmin;
	public String localClipboardData;
	public static org.apache.logging.log4j.Logger log = null;

	@BeforeTest
	public void NavigatetoURL() throws InterruptedException {
		driver = baseClass.driver;
		comlib = new commonLibrary(driver);
		wait = new WebDriverWait(driver, 100);
		poLogin = new loginPage(driver);
		poCreatePaylink = new createPaylinkPage(driver);
		poPaylinkPayment = new paylinkPaymentPage(driver);

		datadirectorypath = System.getProperty("user.dir");
		datadirectorypath = datadirectorypath + "/src/main/java/testData/createPaylinkTestData.xlsx";
		comlib.fnreadSheet(datadirectorypath, "Values");
		js = (JavascriptExecutor) driver;

		LoggerContext context = (LoggerContext) LogManager.getContext(false);
		File file = new File(System.getProperty("user.dir") + "\\src\\main\\java\\Log.xml");
		context.setConfigLocation(file.toURI());
		log = LogManager.getLogger(eCommerceMakePayment.class);

	}

	@Epic("Verify Login is working correctly")
	@Description("<p>Step 1: Navigate to application URL </br> Step 2:Login with valid Credentials</br>"
			+ "Step 3: Check for navigation DashboardScreen</p>")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 1)
	public void fnLogin() {
		try {

			String fnname = Thread.currentThread().getStackTrace()[1].getMethodName();
			rowindex = commonLibrary.fnFindRow(fnname);

			log.info("URL Navigatied and Going to log into application");
			log.info("********************************************************************************");
			comlib.fnEnterValueInTextbox(poLogin.UserName,
					comlib.fnGetCelValue(rowindex, comlib.fnFindColumnIndex(rowindex, "username")));
			comlib.fnEnterValueInTextbox(poLogin.Password,
					comlib.fnGetCelValue(rowindex, comlib.fnFindColumnIndex(rowindex, "password")));
			poLogin.Login.click();
			log.info("********************************************************************************");
			log.info("User successfully logged into application");
			Assert.assertTrue(driver.getCurrentUrl().contains("SendCode"));

		} catch (Exception e) {
			log.info(e.getMessage());
			Assert.fail(e.getMessage());
		}
	}

	@Epic("1stPayVPOS Application Launched Successfully and User able to select eCommerce Account")
	@Description("<p>Step 1: Click on Usersettings </br> Step 2:Verify is Merchant Home Page is displayed</br>"
			+ "Step 3: Verify eCommerce account type successfuly selected</p>")
	@Test(priority = 2)
	public void fn1stVPOS() {
		try {

			log.info("User cab able to see the dashboard");
			log.info("********************************************************************************");
			poLogin.Usersetting.click();
			poLogin.Merchant.click();
			poLogin.eCommerce.click();
			log.info("User has select eCommerce Account");
			log.info("********************************************************************************");
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			comlib.fnGetMultiplewindow();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='transcenter-modal-progress'][contains(@style, 'display: block')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='transcenter-modal-progress'][contains(@style, 'display: none')]")));
			wait.until(ExpectedConditions.elementToBeClickable(poCreatePaylink.RetailAdmin));
			Assert.assertTrue(poCreatePaylink.RetailAdmin.isDisplayed());
			poCreatePaylink.RetailAdmin.click();
			urlAdmin = driver.getCurrentUrl();
			wait.until(ExpectedConditions.elementToBeClickable(poCreatePaylink.Paylink));
			Assert.assertTrue(poCreatePaylink.Paylink.isDisplayed());
			log.info("User able to view the PaybyLink Option");
			log.info("********************************************************************************");
			poCreatePaylink.Paylink.click();
			//fnDeleteRow();
			

		} catch (Exception e) {
			log.info(e.getMessage());
			Assert.fail(e.getMessage());
		}
	}

	@Epic("Payment Link successfully created for eCommerce Account")
	@Description("<p>Step 1: Click On Create Paylink Button </br> Step 2:Enter Valid details</br>"
			+ "Step 3: Create Paylink Successfully Created</p>")
	@Test(priority = 3)
	public void fncreatelink() {
		try {
			String fnname = Thread.currentThread().getStackTrace()[1].getMethodName();
			rowindex = commonLibrary.fnFindRow(fnname);
			fnCreateLink(rowindex);
		} catch (Exception e) {
			log.info(e.getMessage());
			Assert.fail(e.getMessage());
		}
	}

	@Epic("Payment Link copied successfully for eCommerce type")
	@Description("<p>Step 1: Click On paylink copy Button")
	@Test(priority = 4)
	public void fncopiedlink() {
		try {
			localClipboardData="";
			fnCopyLink();
		} catch (Exception e) {
			log.info(e.getMessage());
			Assert.fail(e.getMessage());
		}
	}

	@Epic("Payment done successfully by using copied paylink for eCommerce account type with Visa Card")
	@Description("<p>Step 1: Launch the Copied Paymentlink </br> Step 2:Enter Visa Card number</br>,Step:3 Enter other required field data's,Step4:Cick on submit button</br>"
			+ "Step 3: Verify the payment done message")
	@Test(priority = 5)
	public void fnmakepaymentVisa() {
		try {

			String fnname = Thread.currentThread().getStackTrace()[1].getMethodName();
			rowindex = commonLibrary.fnFindRow(fnname);
			fnMakePayment(rowindex);
		} catch (Exception e) {
			log.info(e.getMessage());
			Assert.fail(e.getMessage());
		}
	}

	@Epic("Visa Card Transaction Deleted successfully and User able to log out the application Successfully")
	@Description("<p>Step 1: Verify delete button is appeared </br>  Step2: Delete the transaction</br> step3: Click on Logout button</br>")
	@Test(priority = 6)
	public void fnDelete() {
		try {
			fnDeleteRow();
		} catch (Exception e) {
			log.info(e.getMessage());
			Assert.fail(e.getMessage());
		}
	}

	// ******************************************************************************************

	@Epic("Payment Link successfully created for eCommerce Account for MasterCard")
	@Description("<p>Step 1: Click On Create Paylink Button </br> Step 2:Enter Valid details</br>"
			+ "Step 3: Create Paylink Successfully Created</p>")
	@Test(priority = 7)
	public void fncreatelinkMasterCard() {
		try {
			String fnname = "fncreatelink";
			rowindex = commonLibrary.fnFindRow(fnname);
			fnCreateLink(rowindex);
		} catch (Exception e) {
			log.info(e.getMessage());
			Assert.fail(e.getMessage());
		}
	}

	@Epic("Payment Link copied successfully for eCommerce type")
	@Description("<p>Step 1: Click On paylink copy Button")
	@Test(priority = 8)
	public void fncopiedlinkMasterCard() {
		try {

			fnCopyLink();
		} catch (Exception e) {
			log.info(e.getMessage());
			Assert.fail(e.getMessage());
		}
	}

	@Epic("Payment done successfully by using copied paylink for eCommerce account type with Master Card")
	@Description("<p>Step 1: Launch the Copied Paymentlink </br> Step 2:Enter Master Card number</br>,Step:3 Enter other required field data's,Step4:Cick on submit button</br>"
			+ "Step 3: Verify the payment done message")
	@Test(priority = 9)
	public void fnmakepaymentMasterCard() {
		try {

			String fnname = Thread.currentThread().getStackTrace()[1].getMethodName();
			rowindex = commonLibrary.fnFindRow(fnname);
			fnMakePayment(rowindex);

		} catch (Exception e) {
			log.info(e.getMessage());
			Assert.fail(e.getMessage());
		}
	}

	@Epic("Transaction Deleted successfully and User able log out the application Successfully")
	@Description("<p>Step 1: Verify delete button is appeared </br>  Step2: Delete the transaction</br> step3: Click on Logout button</br>")
	@Test(priority = 10)
	public void fnDeleteMasterCard() {
		try {
			fnDeleteRow();
		} catch (Exception e) {
			log.info(e.getMessage());
			Assert.fail(e.getMessage());
		}
	}

	@Epic("Payment Link successfully created for eCommerce Account for AmexCard")
	@Description("<p>Step 1: Click On Create Paylink Button </br> Step 2:Enter Valid details</br>"
			+ "Step 3: Create Paylink Successfully Created</p>")
	@Test(priority = 11)
	public void fncreatelinkAmexCard() {
		try {

			String fnname = "fncreatelink";
			rowindex = commonLibrary.fnFindRow(fnname);
			fnCreateLink(rowindex);

		} catch (Exception e) {
			log.info(e.getMessage());
			Assert.fail(e.getMessage());
		}
	}

	@Epic("Payment Link copied successfully for eCommerce type")
	@Description("<p>Step 1: Click On paylink copy Button")
	@Test(priority = 12)
	public void fncopiedlinkAmexCard() {
		try {

			fnCopyLink();

		} catch (Exception e) {
			log.info(e.getMessage());
			Assert.fail(e.getMessage());
		}
	}

	@Epic("Payment done successfully by using copied paylink for eCommerce account type with AmexCard")
	@Description("<p>Step 1: Launch the Copied Paymentlink </br> Step 2:Enter AmexCard number</br>,Step:3 Enter other required field data's,Step4:Cick on submit button</br>"
			+ "Step 3: Verify the payment done message")
	@Test(priority = 13)
	public void fnmakepaymentAmexCard() {
		try {

			String fnname = Thread.currentThread().getStackTrace()[1].getMethodName();
			rowindex = commonLibrary.fnFindRow(fnname);
			fnMakePayment(rowindex);

		} catch (Exception e) {
			log.info(e.getMessage());
			Assert.fail(e.getMessage());
		}
	}

	@Epic("Transaction Deleted successfully and User able log out the application Successfully")
	@Description("<p>Step 1: Verify delete button is appeared </br>  Step2: Delete the transaction</br> step3: Click on Logout button</br>")
	@Test(priority = 14)
	public void fnDeleteAmexCard() {
		try {

			fnDeleteRow();

		} catch (Exception e) {
			log.info(e.getMessage());
			Assert.fail(e.getMessage());
		}
	}

	// *************************************************************************************************************

	@Epic("Payment Link successfully created for eCommerce Account for DisCoverCard")
	@Description("<p>Step 1: Click On Create Paylink Button </br> Step 2:Enter Valid details</br>"
			+ "Step 3: Create Paylink Successfully Created</p>")
	@Test(priority = 15)
	public void fncreatelinkDiscoverCard() {
		try {
			String fnname = "fncreatelink";
			rowindex = commonLibrary.fnFindRow(fnname);

			fnCreateLink(rowindex);

		} catch (Exception e) {
			log.info(e.getMessage());
			Assert.fail(e.getMessage());
		}
	}

	@Epic("Payment Link copied successfully for eCommerce type")
	@Description("<p>Step 1: Click On paylink copy Button")
	@Test(priority = 16)
	public void fncopiedlinkDiscoverCard() {
		try {

			fnCopyLink();

		} catch (Exception e) {
			log.info(e.getMessage());
			Assert.fail(e.getMessage());
		}
	}

	@Epic("Payment done successfully by using copied paylink for eCommerce account type with AmexCard")
	@Description("<p>Step 1: Launch the Copied Paymentlink </br> Step 2:Enter Discover number</br>,Step:3 Enter other required field data's,Step4:Cick on submit button</br>"
			+ "Step 3: Verify the payment done message")
	@Test(priority = 17)
	public void fnmakepaymentDiscoverCard() {
		try {
			String fnname = Thread.currentThread().getStackTrace()[1].getMethodName();
			rowindex = commonLibrary.fnFindRow(fnname);
			fnMakePayment(rowindex);

		} catch (Exception e) {
			log.info(e.getMessage());
			Assert.fail(e.getMessage());
		}
	}

	@Epic("Transaction Deleted successfully and User able log out the application Successfully")
	@Description("<p>Step 1: Verify delete button is appeared </br>  Step2: Delete the transaction</br> step3: Click on Logout button</br>")
	@Test(priority = 18)
	public void fnDeleteDiscoverCard() {
		try {

			fnDeleteRow();

		} catch (Exception e) {
			log.info(e.getMessage());
			Assert.fail(e.getMessage());
		}
	}

	@Epic("Logout the Application ")
	@Description("<p>Step 1: Page Getting Navigate to Dashboard Page </br>  Step2: Click On Logu Button</br>")
	@Test(priority = 19)
	public void fnLogout() {

		try {

			comlib.fnGetMultiplewindow();
			poCreatePaylink.LogOut.click();
			wait.until(ExpectedConditions.visibilityOf(poLogin.UserName));
			Assert.assertTrue(poLogin.UserName.isDisplayed());
			log.info("User able to logout the application successfully");
			log.info("********************************************************************************");

		} catch (Exception e) {

			log.info(e.getMessage());
			Assert.fail(e.getMessage());

		}

	}

	public void fnCreateLink(int rowindex) {
		try {
			/*poCreatePaylink.RetailAdmin.click();
			log.info("User able to click the Admin Module");
			log.info("********************************************************************************");
			urlAdmin = driver.getCurrentUrl();
			wait.until(ExpectedConditions.elementToBeClickable(poCreatePaylink.Paylink));
			Assert.assertTrue(poCreatePaylink.Paylink.isDisplayed());
			log.info("User able to view the PaybyLink Option");
			log.info("********************************************************************************");
			poCreatePaylink.Paylink.click();*/
			wait.until(ExpectedConditions.elementToBeClickable(poCreatePaylink.CreatePayLink));
			poCreatePaylink.CreatePayLink.click();
			wait.until(ExpectedConditions.elementToBeClickable(poCreatePaylink.Name));
			log.info("User able to Click on Create Paylink");
			log.info("********************************************************************************");
			JavascriptExecutor js1 = (JavascriptExecutor) driver;
			js1.executeScript("window.scrollBy(0,1000)");
			uniquename = comlib.fnGetCelValue(rowindex, comlib.fnFindColumnIndex(rowindex, "Name"))
					+ randomAlphaNumeric(5);
			comlib.fnEnterValueInTextbox(poCreatePaylink.Name, uniquename);
			System.out.println(uniquename);

			/*
			 * comlib.fnEnterValueInTextbox(poCreatePaylink.SecretKey,
			 * comlib.fnGetCelValue(rowindex, comlib.fnFindColumnIndex(rowindex,
			 * "SecretKey")));
			 */
			comlib.fnEnterValueInTextbox(poCreatePaylink.Amount,
					comlib.fnGetCelValue(rowindex, comlib.fnFindColumnIndex(rowindex, "Amount")));
			comlib.fnSelectdropdownbyVisibleText(poCreatePaylink.Expiresin,
					comlib.fnGetCelValue(rowindex, comlib.fnFindColumnIndex(rowindex, "Expires")));
			comlib.fnSelectdropdownbyVisibleText(poCreatePaylink.Type,
					comlib.fnGetCelValue(rowindex, comlib.fnFindColumnIndex(rowindex, "Type")));
			comlib.fnEnterValueInTextbox(poCreatePaylink.Payment,
					comlib.fnGetCelValue(rowindex, comlib.fnFindColumnIndex(rowindex, "PaymentMessage")));
			comlib.fnEnterValueInTextbox(poCreatePaylink.Header,
					comlib.fnGetCelValue(rowindex, comlib.fnFindColumnIndex(rowindex, "Header")));
			comlib.fnEnterValueInTextbox(poCreatePaylink.Footer,
					comlib.fnGetCelValue(rowindex, comlib.fnFindColumnIndex(rowindex, "Footer")));
			poCreatePaylink.Save.click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='transcenter-modal-progress'][contains(@style, 'display: block')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='transcenter-modal-progress'][contains(@style, 'display: none')]")));
			log.info("User able to Create Paylink");
			log.info("********************************************************************************");
			wait.until(ExpectedConditions.elementToBeClickable(poCreatePaylink.Refresh));
			poCreatePaylink.Refresh.click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='transcenter-modal-progress'][contains(@style, 'display: block')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='transcenter-modal-progress'][contains(@style, 'display: none')]")));
			/*wait.until(ExpectedConditions.elementToBeClickable(poCreatePaylink.Refresh));
			poCreatePaylink.Refresh.click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='transcenter-modal-progress'][contains(@style, 'display: block')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='transcenter-modal-progress'][contains(@style, 'display: none')]")));*/
			
			for (int i = 1; i <= poCreatePaylink.NameinTable.size(); i++) {
				String text = driver.findElement(By.xpath("//*[@id='transactionTable']//tr[" + i + "]/td[1]"))
						.getText();
				if (text.equalsIgnoreCase(uniquename)) {
					System.out.println(i);
					rowInTable = i;
					break;
				}
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			Assert.fail(e.getMessage());
		}
	}

	public void fnCopyLink() {
		try {
			WebElement copy = driver.findElement(By.xpath("//*[@id='transactionTable']//tr/td[contains(text(),'"+uniquename+"')]/parent::node()/td/i[contains(@class,'clipboard')]"));
			wait.until(ExpectedConditions.elementToBeClickable(copy));
			System.out.println(copy.isDisplayed());
			log.info("User able to Copy the Link");
			log.info("********************************************************************************");
			copy.click();
			localClipboardData = (String) Toolkit.getDefaultToolkit().getSystemClipboard()
					.getData(DataFlavor.stringFlavor);
			System.out.println(localClipboardData);
			Assert.assertTrue(localClipboardData != "");
		} catch (Exception e) {
			log.info(e.getMessage());
			
			Assert.fail(e.getMessage());
		}
	}

	public void fnMakePayment(int rowindex) {
		try {
			/*localClipboardData = (String) Toolkit.getDefaultToolkit().getSystemClipboard()
					.getData(DataFlavor.stringFlavor);
			log.info("User can able to Lauch the Copied URL");*/
			log.info("********************************************************************************");
			driver.get(localClipboardData);

			System.out.println(parenturl);

			System.out.println(poPaylinkPayment.Cardnumber.isDisplayed());

			wait.until(ExpectedConditions.elementToBeClickable(poPaylinkPayment.Cardnumber));

			comlib.fnEnterValueInTextbox(poPaylinkPayment.Cardnumber,
					comlib.fnGetCelValue(rowindex, comlib.fnFindColumnIndex(rowindex, "Card Number")));
			comlib.fnSelectdropdownbyVisibleText(poPaylinkPayment.Expmonth,
					comlib.fnGetCelValue(rowindex, comlib.fnFindColumnIndex(rowindex, "Month")));
			comlib.fnSelectdropdownbyVisibleText(poPaylinkPayment.Expyear,
					comlib.fnGetCelValue(rowindex, comlib.fnFindColumnIndex(rowindex, "Year")));
			comlib.fnEnterValueInTextbox(poPaylinkPayment.Contactname,
					comlib.fnGetCelValue(rowindex, comlib.fnFindColumnIndex(rowindex, "CustomerName")));
			comlib.fnEnterValueInTextbox(poPaylinkPayment.PostalCode,
					comlib.fnGetCelValue(rowindex, comlib.fnFindColumnIndex(rowindex, "Pin")));
			comlib.fnEnterValueInTextbox(poPaylinkPayment.Address,
					comlib.fnGetCelValue(rowindex, comlib.fnFindColumnIndex(rowindex, "Address1")));
			comlib.fnEnterValueInTextbox(poPaylinkPayment.Address2,
					comlib.fnGetCelValue(rowindex, comlib.fnFindColumnIndex(rowindex, "Address22")));
			comlib.fnEnterValueInTextbox(poPaylinkPayment.City,
					comlib.fnGetCelValue(rowindex, comlib.fnFindColumnIndex(rowindex, "City1")));
			comlib.fnSelectdropdownbyVisibleText(poPaylinkPayment.State,
					comlib.fnGetCelValue(rowindex, comlib.fnFindColumnIndex(rowindex, "State1")));
			comlib.fnEnterValueInTextbox(poPaylinkPayment.Email,
					comlib.fnGetCelValue(rowindex, comlib.fnFindColumnIndex(rowindex, "Email")));
			comlib.fnEnterValueInTextbox(poPaylinkPayment.Phonenumber,
					comlib.fnGetCelValue(rowindex, comlib.fnFindColumnIndex(rowindex, "PhoneNumbers")));
			poPaylinkPayment.Submitbutton.click();
			Assert.assertTrue(driver.getCurrentUrl().contains("ThankYou"));
			log.info("User able Make Payment Successfully by using VISA CARD ");
			log.info("********************************************************************************");
		} catch (Exception e) {
			log.info(e.getMessage());
			Assert.fail(e.getMessage());
		}
	}

	public void fnDeleteRow() {
		try {
			driver.navigate().to(urlAdmin);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='transcenter-modal-progress'][contains(@style, 'display: block')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='transcenter-modal-progress'][contains(@style, 'display: none')]")));
			wait.until(ExpectedConditions.elementToBeClickable(poCreatePaylink.Admin));
			poCreatePaylink.Admin.click();
			poCreatePaylink.Paylink.click();
			
			JavascriptExecutor js2 = (JavascriptExecutor) driver;
			js2.executeScript("window.scrollBy(0,1000)");
			WebElement ele = driver.findElement(By.xpath("//*[@id='transactionTable']//tr/td[contains(text(),'"+uniquename+"')]/parent::node()/td/i[contains(@class,'trash')]"));
			js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", ele);
			ele.click();
			
			Thread.sleep(2000);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h5[contains(text(),'delete')]")));
			WebElement e = driver.findElement(By.xpath("//h5[contains(text(),'delete')]/parent::node()/div/button[@id='Yes']"));
			js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", e);
			e.click();
			
			/*Thread.sleep(2000);
			wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//div[@id='transcenter-modal-option']//button[@class='close']"))));
			driver.findElement(By.xpath("//div[@id='transcenter-modal-option']//button[@class='close']")).click();*/
			//wait.until(ExpectedConditions.elementToBeClickable(poCreatePaylink.Refresh));
			
			Thread.sleep(3000);
			js.executeScript("window.scrollBy(0,-1000)");
			poCreatePaylink.Refresh.click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='transcenter-modal-progress'][contains(@style, 'display: block')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='transcenter-modal-progress'][contains(@style, 'display: none')]")));
			
		/*	driver.switchTo().activeElement();
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.TAB);
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.TAB);
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.SPACE);
			js.executeScript("document.getElementById('transcenter-modal-option').style='display: none;'");
			js.executeScript("document.getElementsByClassName('modal-backdrop fade show').class=''");*/

			log.info("User able to Delete the Transactions ");
			log.info("********************************************************************************");

			System.out.println(poCreatePaylink.NameinTable.size()-2+"   Size");
			for (int i = 1; i <= poCreatePaylink.NameinTable.size()-2; i++) {
				String text = driver.findElement(By.xpath("//*[@id='transactionTable']//tr["+i+"]/td[1]"))
						.getText();
				if (text.equalsIgnoreCase(uniquename)) {
					Assert.fail();
				}

			}
			/*System.out.println(driver.getCurrentUrl());
			driver.navigate().refresh();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='transcenter-modal-progress'][contains(@style, 'display: block')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='transcenter-modal-progress'][contains(@style, 'display: none')]")));
			wait.until(ExpectedConditions.elementToBeClickable(poCreatePaylink.Admin));*/

		} catch (Exception e) {
			log.info(e.getMessage());
			Assert.fail(e.getMessage());
		}
	}

	public String randomAlphaNumeric(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		s = builder.toString();
		return s;
	}

	@AfterTest
	public void fnClose() {
		driver.quit();
	}

}
