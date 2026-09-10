package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;

public class AcercaDelBancoTest extends BaseTest {
	
	private static final String BASE_URL = "https://www.santander.com.mx/";
	
	@Test
	public void shouldNavigateAcercaDelBancoLinks() {

		Assert.assertTrue(dashboardPage.openAcercaDelBancoMenu().clickFundacionSantander(),
		            "No se navegó correctamente a Fundacion Santander");
		 
		driver.get(BASE_URL);
		Assert.assertTrue(dashboardPage.openAcercaDelBancoMenu().clickBlog(),
	            "No se navegó correctamente a Blog");

	    driver.get(BASE_URL);
	    Assert.assertTrue(dashboardPage.openAcercaDelBancoMenu().clickSostenibilidad(),
	            "No se navegó correctamente a Sostenibilidad");

	    driver.get(BASE_URL);
	    Assert.assertTrue(dashboardPage.openAcercaDelBancoMenu().clickEducacionFinanciera(),
	            "No se navegó correctamente a Educación Financiera");

	    driver.get(BASE_URL);
	    Assert.assertTrue(dashboardPage.openAcercaDelBancoMenu().clickInversionistas(),
	            "No se navegó correctamente a Inversionistas");

	    driver.get(BASE_URL);
	    Assert.assertTrue(dashboardPage.openAcercaDelBancoMenu().clickSalaDeComunicacion(),
	            "No se navegó correctamente a Sala de comunicación");

	    driver.get(BASE_URL);
	    Assert.assertTrue(dashboardPage.openAcercaDelBancoMenu().clickBolsaDeTrabajo(),
	            "No se navegó correctamente a Bolsa de trabajo");
	}
}
