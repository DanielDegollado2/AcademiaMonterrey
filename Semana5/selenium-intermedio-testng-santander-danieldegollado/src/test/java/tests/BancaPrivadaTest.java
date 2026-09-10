package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;

public class BancaPrivadaTest extends BaseTest {
	
	@Test
	public void shouldNavigateToBancaPrivadaPage() {
		Assert.assertTrue(dashboardPage.openBancaPrivada(),
		            "No se navegó correctamente Banca Privada");
	}
}
