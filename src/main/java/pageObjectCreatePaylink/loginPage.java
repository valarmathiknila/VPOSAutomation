package pageObjectCreatePaylink;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class loginPage {

	public WebDriver driver;

	@FindBy(how = How.XPATH, using = "//*[@id=\"UserName\"]")
	public WebElement UserName;

	@FindBy(how = How.XPATH, using = "//*[@id=\"Password\"]")
	public WebElement Password;

	@FindBy(how = How.XPATH, using = "//*[@id=\"loginForm\"]/form/input[4]")
	public WebElement Login;

	@FindBy(how = How.XPATH, using = "//*[@id=\"loginForm\"]/a[2]")
	public WebElement Usersetting;

	@FindBy(how = How.XPATH, using = "//*[@id=\"lnkHome\"]")
	public WebElement Merchant;

	@FindBy(how = How.XPATH, using = "//*[@id=\"btnVPOSLink\"]")
	public WebElement eCommerce;

	@FindBy(how = How.XPATH, using = "//a[contains(text(),'6280020000790881')]/parent::node()/following-sibling::td/a[@id='btnVPOSLink']")
	public WebElement Retail;

	@FindBy(how = How.XPATH, using = "//*[@id=\"btnVPOSLink\"]")
	public WebElement Moto;

	public loginPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		// this.driver = driver;
		PageFactory.initElements(driver, this);
	}

}
