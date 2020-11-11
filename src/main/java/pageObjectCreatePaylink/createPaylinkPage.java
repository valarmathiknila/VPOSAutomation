package pageObjectCreatePaylink;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class createPaylinkPage {

	@FindBy(how = How.XPATH, using = "	//*[@id=\"select-active-midtid\"]")
	public WebElement AccountType;

	@FindBy(how = How.XPATH, using = "//li[@id='t-Admin']/a")
	public WebElement Admin;
	

	@FindBy(how = How.XPATH, using = "//li[@id='t-Admin']/a/span")
	public WebElement RetailAdmin;

	@FindBy(how = How.XPATH, using = "//*[@id='transactionTable']//tr")
	public List<WebElement> NameinTable;

	@FindBy(how = How.ID, using = "Yes")
	public WebElement Yes;

	@FindBy(how = How.XPATH, using = "//*[@id=\"payLinks___BV_tab_button__\"]")
	public WebElement Paylink;

	@FindBy(how = How.XPATH, using = "	/html/body/div/div/div/div/div/div/div[1]/div[2]/div[2]/section[7]/div/div[2]/div/div[2]/div[6]/form/div/div[1]/div/div/div/button")
	public WebElement CreatePayLink;

	@FindBy(how = How.XPATH, using = "//*[@id=\"p_1_2_7_1_1_6_2__link_name\"]")
	public WebElement Name;

	@FindBy(how = How.XPATH, using = "//*[@id=\"p_1_2_7_1_1_6_2__link_amount\"]")
	public WebElement Amount;

	@FindBy(how = How.XPATH, using = "//*[@id=\"p_1_2_7_1_1_6_2__link_ttl\"]")
	public WebElement Expiresin;

	@FindBy(how = How.XPATH, using = "//*[@id=\"p_1_2_7_1_1_6_2__link_paymentMessage\"]")
	public WebElement Payment;

	@FindBy(how = How.XPATH, using = "//*[@id=\"p_1_2_7_1_1_6_2__link_header\"]")
	public WebElement Header;

	@FindBy(how = How.XPATH, using = "//*[@id=\"p_1_2_7_1_1_6_2__link_footer\"]")
	public WebElement Footer;

	@FindBy(how = How.XPATH, using = "//*[@id=\"p_1_2_7_1_1_6_2__link_transaction_type\"]")
	public WebElement Type;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'inner-wrapper')]//button[contains(text(),'Save') and contains(@class,'btn-primary')]")
	public WebElement Save;

	@FindBy(how = How.XPATH, using = "(//i[@class='fa fa-clipboard'])[1]")
	public WebElement Linkcopied;

	@FindBy(how = How.XPATH, using = "//*[@id=\"transactionTable\"]/tbody/tr[1]/td[8]/i[3]")
	public WebElement Delete;

	@FindBy(how = How.XPATH, using = "//*[@id=\"transactionTable\"]/tbody/tr[1]/td[8]/i[2]")
	public WebElement Mail;

	@FindBy(how = How.XPATH, using = "//*[@id=\"p_1_2_7_1_1_6_2__formId\"]/div/div[2]/div[1]/h5/i")
	public WebElement refresh;

	@FindBy(how = How.XPATH, using = "//*[@id=\"transactionTable\"]/tbody/tr[1]/td[1]")
	public WebElement grid;

	@FindBy(how = How.XPATH, using = "//i[contains(@class,'refresh')]")
	public WebElement Refresh;

	@FindBy(how = How.ID, using = "lnkLogOff")
	public WebElement LogOut;

	@FindBy(how = How.XPATH, using = "//*[@id=\"Yes\"]")
	public WebElement OKButton;

	@FindBy(how = How.XPATH, using = " //input[@id = 'p_1_2_7_1_1_6_2__secret_key']")
	public WebElement SecretKey;


	public createPaylinkPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		//this.driver = driver;
		PageFactory.initElements(driver, this);
	}

}
