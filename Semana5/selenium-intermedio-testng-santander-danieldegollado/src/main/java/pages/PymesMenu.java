package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.BasePage;

public class PymesMenu extends BasePage {
	
	private static final By SANTANDER_PYME_LINK =
	        By.cssSelector("#firstLevel-mainItem-2-menu a[href$='/pyme/']");

	private static final By DIVISAS_Y_COBERTURAS_LINK =
	        By.cssSelector("#firstLevel-mainItem-2-menu a[href*='coberturas-y-cambios']");

	private static final By CUENTAS_LINK =
	        By.cssSelector("#firstLevel-mainItem-2-menu a[href*='cuentas']");

	private static final By NEGOCIO_INTERNACIONAL_LINK =
	        By.cssSelector("#firstLevel-mainItem-2-menu a[href*='negocio-internacional']");

	private static final By PAQUETES_PYMES_LINK =
	        By.cssSelector("#firstLevel-mainItem-2-menu a[href*='paquetes-pymes']");

	private static final By CREDITOS_LINK =
	        By.cssSelector("#firstLevel-mainItem-2-menu a[href*='creditos']");

	private static final By SEGUROS_LINK =
	        By.cssSelector("#firstLevel-mainItem-2-menu a[href*='seguros']");

	private static final By ALIANZAS_LINK =
	        By.cssSelector("#firstLevel-mainItem-2-menu a[href*='alianzas']");

	private static final By NEGOCIO_TRANSACCIONAL_LINK =
	        By.cssSelector("#firstLevel-mainItem-2-menu a[href*='negocio-transaccional']");

	private static final By ECOSISTEMA_NO_FINANCIERO_LINK =
	        By.cssSelector("#firstLevel-mainItem-2-menu a[href*='ecosistemas-pyme']");

	private static final By INVERSIONES_LINK =
	        By.cssSelector("#firstLevel-mainItem-2-menu a[href*='inversiones']");

	protected PymesMenu(WebDriver driver) {
		super(driver);
	}
	
	public boolean clickSantanderPyme() {
        return clickAndVerifyUrl(SANTANDER_PYME_LINK, "/pyme");
    }
	
	public boolean clickDivisasYCoberturas() {
	    return clickAndVerifyUrl(DIVISAS_Y_COBERTURAS_LINK, "coberturas-y-cambios");
	}

	public boolean clickCuentas() {
	    return clickAndVerifyUrl(CUENTAS_LINK, "cuentas");
	}

	public boolean clickNegocioInternacional() {
	    return clickAndVerifyUrl(NEGOCIO_INTERNACIONAL_LINK, "negocio-internacional");
	}

	public boolean clickPaquetesPymes() {
	    return clickAndVerifyUrl(PAQUETES_PYMES_LINK, "paquetes-pymes");
	}

	public boolean clickCreditos() {
	    return clickAndVerifyUrl(CREDITOS_LINK, "creditos");
	}

	public boolean clickSeguros() {
	    return clickAndVerifyUrl(SEGUROS_LINK, "seguros");
	}

	public boolean clickAlianzas() {
	    return clickAndVerifyUrl(ALIANZAS_LINK, "alianzas");
	}

	public boolean clickNegocioTransaccional() {
	    return clickAndVerifyUrl(NEGOCIO_TRANSACCIONAL_LINK, "negocio-transaccional");
	}

	public boolean clickEcosistemaNoFinanciero() {
	    return clickAndVerifyUrl(ECOSISTEMA_NO_FINANCIERO_LINK, "ecosistemas-pyme");
	}

	public boolean clickInversiones() {
	    return clickAndVerifyUrl(INVERSIONES_LINK, "inversiones");
	}

}
