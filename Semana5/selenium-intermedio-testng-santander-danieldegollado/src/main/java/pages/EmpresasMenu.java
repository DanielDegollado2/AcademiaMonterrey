package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.BasePage;

public class EmpresasMenu extends BasePage{

	private static final By EMPRESAS_Y_GOBIERNO_LINK =
	        By.cssSelector("a[href*='bei/home']");

	private static final By MULTINACIONALES_LINK =
	        By.cssSelector("a[href*='multinacionales']");
	
	protected EmpresasMenu(WebDriver driver) {
		super(driver);
	}
	
	public boolean clickEmpresasYGobierno() {
        return clickAndVerifyUrl(EMPRESAS_Y_GOBIERNO_LINK, "bei/home");
    }
	
	public boolean clickMultinacionales() {
        return clickAndVerifyUrl(MULTINACIONALES_LINK, "multinacionales");
    }

}
