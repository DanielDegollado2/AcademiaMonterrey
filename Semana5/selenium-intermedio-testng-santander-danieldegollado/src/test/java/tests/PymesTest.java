package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.DashboardPage;

public class PymesTest extends BaseTest {
	
	private static final String BASE_URL = "https://www.santander.com.mx/";
	
	@Test
	public void shouldNavigatePymesLinks() {
		 DashboardPage dashboardPage = new DashboardPage(driver);

		 Assert.assertTrue(dashboardPage.openPymesMenu().clickSantanderPyme(),
		            "No se navegó correctamente a Santander Pyme");

		 driver.get(BASE_URL);
		 Assert.assertTrue(dashboardPage.openPymesMenu().clickDivisasYCoberturas(),
		            "No se navegó correctamente a Divisas y coberturas");

		 driver.get(BASE_URL);
		 Assert.assertTrue(dashboardPage.openPymesMenu().clickCuentas(),
		            "No se navegó correctamente a Cuentas");

		 driver.get(BASE_URL);
		 Assert.assertTrue(dashboardPage.openPymesMenu().clickNegocioInternacional(),
		            "No se navegó correctamente a Negocio internacional");

		 driver.get(BASE_URL);
		 Assert.assertTrue(dashboardPage.openPymesMenu().clickPaquetesPymes(),
		            "No se navegó correctamente a Paquetes Pymes");

		 driver.get(BASE_URL);
		 Assert.assertTrue(dashboardPage.openPymesMenu().clickCreditos(),
		            "No se navegó correctamente a Créditos");

		 driver.get(BASE_URL);
		 Assert.assertTrue(dashboardPage.openPymesMenu().clickSeguros(),
		            "No se navegó correctamente a Seguros");

		 driver.get(BASE_URL);
		 Assert.assertTrue(dashboardPage.openPymesMenu().clickAlianzas(),
		            "No se navegó correctamente a Alianzas");

		 driver.get(BASE_URL);
		 Assert.assertTrue(dashboardPage.openPymesMenu().clickNegocioTransaccional(),
		            "No se navegó correctamente a Negocio transaccional");

		 driver.get(BASE_URL);
		 Assert.assertTrue(dashboardPage.openPymesMenu().clickEcosistemaNoFinanciero(),
		            "No se navegó correctamente a Ecosistema no financiero");

		 driver.get(BASE_URL);
		 Assert.assertTrue(dashboardPage.openPymesMenu().clickInversiones(),
		            "No se navegó correctamente a Inversiones");
		 
	}
}
