package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.BasePage;

public class AcercaDelBancoMenu extends BasePage {
	
	private static final By FUNDACION_SANTANDER_LINK =
	        By.cssSelector("#firstLevel-mainItem-4-menu a[href*='fundacion-santander']");
	
	private static final By BLOG_LINK =
	        By.cssSelector("#firstLevel-mainItem-4-menu a[href*='blog']");
	
	private static final By SOSTENIBILIDAD_LINK =
	        By.cssSelector("#firstLevel-mainItem-4-menu a[href*='responsabilidad-social']");

	private static final By EDUCACION_FINANCIERA_LINK =
	        By.cssSelector("#firstLevel-mainItem-4-menu a[href*='educacion-financiera']");

	private static final By INVERSIONISTAS_LINK =
	        By.cssSelector("#firstLevel-mainItem-4-menu a[href*='ir/home']");

	private static final By SALA_DE_COMUNICACION_LINK =
	        By.cssSelector("#firstLevel-mainItem-4-menu a[href*='sala_prensa']");

	private static final By BOLSA_DE_TRABAJO_LINK =
	        By.cssSelector("#firstLevel-mainItem-4-menu a[href*='bolsa-de-trabajo']");

	protected AcercaDelBancoMenu(WebDriver driver) {
		super(driver);
	}
	
	public boolean clickFundacionSantander() {
        return clickAndVerifyUrl(FUNDACION_SANTANDER_LINK, "fundacion-santander");
    }
	
	public boolean clickBlog() {
	    return clickAndVerifyUrl(BLOG_LINK, "blog");
	}

	public boolean clickSostenibilidad() {
	    return clickAndVerifyUrl(SOSTENIBILIDAD_LINK, "responsabilidad-social");
	}

	public boolean clickEducacionFinanciera() {
	    return clickAndVerifyUrl(EDUCACION_FINANCIERA_LINK, "educacion-financiera");
	}

	public boolean clickInversionistas() {
	    return clickAndVerifyUrl(INVERSIONISTAS_LINK, "ir/home");
	}

	public boolean clickSalaDeComunicacion() {
	    return clickAndVerifyUrl(SALA_DE_COMUNICACION_LINK, "sala_prensa");
	}

	public boolean clickBolsaDeTrabajo() {
	    return clickAndVerifyUrl(BOLSA_DE_TRABAJO_LINK, "bolsa-de-trabajo");
	}

}
