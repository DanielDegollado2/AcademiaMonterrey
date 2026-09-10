package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.BasePage;

public class DashboardPage extends BasePage{
	
	private static final By PERSONAS_BUTTON = By.id("firstLevel-mainItem-0-menu-button");
	private static final By EMPRESAS_BUTTON = By.id("firstLevel-mainItem-1-menu-button");
	private static final By PYMES_BUTTON = By.id("firstLevel-mainItem-2-menu-button");
	private static final By BANCA_PRIVADA_BUTTON = By.cssSelector("li a[href='https://www.santander.com.mx/bp/home/']");
	private static final By ACERCA_DEL_BANCO_BUTTON = By.id("firstLevel-mainItem-4-menu-button");

	public DashboardPage(WebDriver driver) {
		super(driver);
	}
	
	public PersonasMenu openPersonasMenu() {
        click(PERSONAS_BUTTON);
        return new PersonasMenu(driver);
    }
	
	public EmpresasMenu openEmpresasMenu() {
        click(EMPRESAS_BUTTON);
        return new EmpresasMenu(driver);
    }
	
	public PymesMenu openPymesMenu() {
        click(PYMES_BUTTON);
        return new PymesMenu(driver);
    }
	
	public boolean openBancaPrivada() {
		return clickAndVerifyUrl(BANCA_PRIVADA_BUTTON, "bp/home");
	}
	
	public AcercaDelBancoMenu openAcercaDelBancoMenu() {
        click(ACERCA_DEL_BANCO_BUTTON);
        return new AcercaDelBancoMenu(driver);
    }
}
