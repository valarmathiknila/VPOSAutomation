package testClassCreatePaylink;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class commonLibrary {

	public static WebDriver driver;
	public XSSFWorkbook wb;
	public static XSSFSheet sheet;
	public XSSFRow row;
	public static XSSFCell cell;
	public int rowNumb;
	public String celValue;
	static int rowNum = 0;
	static int colNum = 0;

	public void fnEnterValueInTextbox(WebElement ele, String text) {
		ele.sendKeys(text);
	}

	public void fnSelectdropdownbyVisibleText(WebElement ele, String text) {
		Select droppdown = new Select(ele);
		droppdown.selectByVisibleText(text);
	}

	public void fnSelectdropdownbyIndex(WebElement ele, int Index) {
		Select droppdown = new Select(ele);
		droppdown.selectByIndex(Index);
	}

	public void fnEnterDate(WebElement ele, Date dateinput) {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String strDate = formatter.format(date);
		ele.sendKeys(strDate);
	}

	public void fnreadSheet(String path, String sheetname) {
		try {
			FileInputStream fis = new FileInputStream(path);
			wb = new XSSFWorkbook(fis);
			sheet = wb.getSheet(sheetname);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public static int fnFindRow(String functionname) {
		System.out.println(sheet.getSheetName() + "sheetname");
		for (Row row : sheet) {
			if (row.getCell(0) != null) {
				if (row.getCell(0).getRichStringCellValue().getString().equalsIgnoreCase(functionname)) {
					rowNum = row.getRowNum();
					break;
				}
			}
		}
		return rowNum;
	}


	public String fnGetCelValue(int RowNum, int ColNum) {
		cell = sheet.getRow(RowNum).getCell(ColNum);

		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			Integer val = (int) cell.getNumericCellValue();
			celValue = Integer.toString(val);
		} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			celValue = cell.getStringCellValue();
		}
		return celValue;
	}

	public Date getDateCellData(int RowNum, int ColNum) throws Exception {

		try {

			cell = sheet.getRow(rowNum).getCell(ColNum);

			Date CellData = cell.getDateCellValue();

			return CellData;

		} catch (Exception e) {
			System.out.println("Incatch");
		}
		return null;

	}

	public static int fnFindColumnIndex(int rowIndicator, String columnname) {
		Row row = sheet.getRow(rowNum - 1);

		for (Cell cell : row) {

			if (cell.getRichStringCellValue().getString().equalsIgnoreCase(columnname)) {
				colNum = cell.getColumnIndex();
				break;
			}
		}
		return colNum;
	}

	public void fnGetCurrentwindow() {
		try {

			String CurrentWindow = driver.getWindowHandle();
			driver.switchTo().window(CurrentWindow);
			System.out.println(driver.getCurrentUrl());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void fnGetMultiplewindow() {
		try {
			String parent = driver.getWindowHandle();
			System.out.println(driver.getTitle() + "PArent Top");
			Set<String> s = driver.getWindowHandles();
			Iterator<String> I1 = s.iterator();

			while (I1.hasNext()) {
				String child_window = I1.next();
				if (!parent.equals(child_window)) {
					System.out.println(driver.getCurrentUrl() + "commonlibURL");
					if (!driver.getTitle().contains("FirstView")) {
						driver.close();
					}
					driver.switchTo().window(child_window);
					System.out.println(driver.getTitle() + "Child");
					break;

				} else {
					driver.switchTo().window(parent);
					System.out.println(driver.getTitle() + "Parent");

				}

			}

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}

	public void fnParentWindow() {
		try {

			String Parent_Window = driver.getWindowHandle();
			driver.switchTo().window(Parent_Window);

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}

	public static void handleAlert() {
		if (isAlertPresent()) {
			Alert alert = driver.switchTo().alert();
			System.out.println(alert.getText());
			alert.accept();
		}
	}

	public static boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException ex) {
			return false;
		}
	}

	public commonLibrary(WebDriver driver) {
		this.driver = driver;
	}

}
