package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;

public class EmpresasTest extends BaseTest{
	
	private static final String BASE_URL = "https://www.santander.com.mx/";
	
	@Test
	public void shouldNavigateEmpresasLinks() {
		 Assert.assertTrue(dashboardPage.openEmpresasMenu().clickEmpresasYGobierno(),
		            "No se navegó correctamente a Empresas y Gobierno");

		 driver.get(BASE_URL);
		 Assert.assertTrue(dashboardPage.openEmpresasMenu().clickMultinacionales(),
		            "No se navegó correctamente a Multinacionales");
	}
}
