package testClassCreatePaylink;

import java.io.FileInputStream;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class baseClass {

	public static WebDriver driver;
	FileInputStream fis;
	public Properties prop;
	String directorypath;

	
	public WebDriver getDriver() {
		return driver;
	}

	//public void NavigatetoURL = for run all the classes no need to add rerun 
	@Test
	public void NavigatetoURL() {

		try {

			directorypath = System.getProperty("user.dir");
			unReadPropertiesFile();

			System.out.println(directorypath);

			if (prop.getProperty("browser").equals("chrome")) {
				System.setProperty("webdriver.chrome.driver",
						directorypath + "//src/test/resources/Drivers/chromedriver.exe");
				driver = new ChromeDriver();

			} else if (prop.getProperty("browser").equals("FireFox")) {
				System.setProperty("webdriver.gecko.driver",
						directorypath + "//src//test//resources//Drivers//geckodriver.exe");
				driver = new FirefoxDriver();
			}

			driver.get(prop.getProperty("URL"));
			driver.manage().window().maximize();
			Thread.sleep(5000);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	
	}

	public void unReadPropertiesFile() {

		try {
			String filepath = directorypath + "/src/main/java/pageObjectCreatePaylink/data.properties";
			prop = new Properties();
			fis = new FileInputStream(filepath);
			prop.load(fis);

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}

}
