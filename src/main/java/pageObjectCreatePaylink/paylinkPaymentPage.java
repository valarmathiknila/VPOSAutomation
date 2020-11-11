package pageObjectCreatePaylink;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class paylinkPaymentPage {

	@FindBy(how = How.XPATH, using = "//*[@id=\'ccNum\']")
	public WebElement Cardnumber;

	@FindBy(how = How.XPATH, using = "//*[@id=\"expMonth\"]")
	public WebElement Expmonth;

	@FindBy(how = How.XPATH, using = "//*[@id=\"expYear\"]")
	public WebElement Expyear;

	@FindBy(how = How.XPATH, using = "//*[@id=\"zip\"]")
	public WebElement PostalCode;

	@FindBy(how = How.XPATH, using = "//*[@id=\"name\"]")
	public WebElement Contactname;

	@FindBy(how = How.XPATH, using = "//*[@id=\"line1\"]")
	public WebElement Address;

	@FindBy(how = How.XPATH, using = "//*[@id=\"line2\"]")
	public WebElement Address2;

	@FindBy(how = How.XPATH, using = "//*[@id=\"city\"]")
	public WebElement City;

	@FindBy(how = How.XPATH, using = "//*[@id=\"state\"]")
	public WebElement State;

	@FindBy(how = How.XPATH, using = "//*[@id=\"phone\"]")
	public WebElement Phonenumber;

	@FindBy(how = How.XPATH, using = "//*[@id=\"email\"]")
	public WebElement Email;

	@FindBy(how = How.XPATH, using = "//*[@id=\"btnSave\"]")
	public WebElement Submitbutton;

	@FindBy(how = How.XPATH, using = "//h5[contains(text(),'Approved')]")
	public WebElement PaymentApproved;

	@FindBy(how = How.XPATH, using = "	//*[@id=\"transactionTable\"]/tbody/tr[1]/td[8]/i[2]")
	public WebElement Mail;

	public paylinkPaymentPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		// this.driver = driver;
		PageFactory.initElements(driver, this);

	}
}
