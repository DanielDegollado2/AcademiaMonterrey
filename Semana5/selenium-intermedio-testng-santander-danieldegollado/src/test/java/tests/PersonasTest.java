package tests;


import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;

public class PersonasTest extends BaseTest {
	
	private static final String BASE_URL = "https://www.santander.com.mx/";
	
	@Test
	public void shouldNavigateCreditoYFinanciamientoLinks() {
	    Assert.assertTrue(dashboardPage.openPersonasMenu().clickTarjetasDeCredito(),
	            "No se navegó correctamente a Tarjetas de crédito");

	    driver.get(BASE_URL);
	    Assert.assertTrue(dashboardPage.openPersonasMenu().clickCreditoPersonal(),
	            "No se navegó correctamente a Crédito personal");

	    driver.get(BASE_URL);
	    Assert.assertTrue(dashboardPage.openPersonasMenu().clickCreditoHipotecario(),
	            "No se navegó correctamente a Crédito hipotecario");

	    driver.get(BASE_URL);
	    Assert.assertTrue(dashboardPage.openPersonasMenu().clickSimuladorDeHipoteca(),
	            "No se navegó correctamente a Simulador de hipoteca");

	    driver.get(BASE_URL);
	    Assert.assertTrue(dashboardPage.openPersonasMenu().clickCreditoAutomotriz(),
	            "No se navegó correctamente a Crédito automotriz");

	    driver.get(BASE_URL);
	    Assert.assertTrue(dashboardPage.openPersonasMenu().clickBuroDeCredito(),
	            "No se navegó correctamente a Buró de crédito");
	}

	@Test
	public void shouldNavigateAhorroEInversionLinks() {
	    Assert.assertTrue(dashboardPage.openPersonasMenu().clickFondosDeInversion(),
	            "No se navegó correctamente a Fondos de inversión");

	    driver.get(BASE_URL);
	    Assert.assertTrue(dashboardPage.openPersonasMenu().clickInversionesAPlazo(),
	            "No se navegó correctamente a Inversiones a plazo");

	    driver.get(BASE_URL);
	    Assert.assertTrue(dashboardPage.openPersonasMenu().clickNotasEstructuradas(),
	            "No se navegó correctamente a Notas estructuradas");
	}

	@Test
	public void shouldNavigateSegurosLinks() {
	    Assert.assertTrue(dashboardPage.openPersonasMenu().clickAuto(),
	            "No se navegó correctamente a Auto");

	    driver.get(BASE_URL);
	    Assert.assertTrue(dashboardPage.openPersonasMenu().clickVida(),
	            "No se navegó correctamente a Vida");

	    driver.get(BASE_URL);
	    Assert.assertTrue(dashboardPage.openPersonasMenu().clickHogar(),
	            "No se navegó correctamente a Hogar");

	    driver.get(BASE_URL);
	    Assert.assertTrue(dashboardPage.openPersonasMenu().clickAhorro(),
	            "No se navegó correctamente a Ahorro");

	    driver.get(BASE_URL);
	    Assert.assertTrue(dashboardPage.openPersonasMenu().clickGastosMedicos(),
	            "No se navegó correctamente a Gastos médicos");

	    driver.get(BASE_URL);
	    Assert.assertTrue(dashboardPage.openPersonasMenu().clickPertenencias(),
	            "No se navegó correctamente a Pertenencias");
	}

	@Test
	public void shouldNavigateInformacionYAyudaLinks() {
	    Assert.assertTrue(dashboardPage.openPersonasMenu().clickSuperlinea(),
	            "No se navegó correctamente a SuperLínea");

	    driver.get(BASE_URL);
	    Assert.assertTrue(dashboardPage.openPersonasMenu().clickSucursales(),
	            "No se navegó correctamente a Sucursales");

	    driver.get(BASE_URL);
	    Assert.assertTrue(dashboardPage.openPersonasMenu().clickCajerosAutomaticos(),
	            "No se navegó correctamente a Cajeros automáticos");

	    driver.get(BASE_URL);
	    Assert.assertTrue(dashboardPage.openPersonasMenu().clickCanalesAlternos(),
	            "No se navegó correctamente a Canales alternos");

	    driver.get(BASE_URL);
	    Assert.assertTrue(dashboardPage.openPersonasMenu().clickCentroDeAyuda(),
	            "No se navegó correctamente a Centro de ayuda");

	    driver.get(BASE_URL);
	    Assert.assertTrue(dashboardPage.openPersonasMenu().clickCentroDeSeguridad(),
	            "No se navegó correctamente a Centro de seguridad");

	    driver.get(BASE_URL);
	    Assert.assertTrue(dashboardPage.openPersonasMenu().clickTutoriales(),
	            "No se navegó correctamente a Tutoriales");

	    driver.get(BASE_URL);
	    Assert.assertTrue(dashboardPage.openPersonasMenu().clickTerminosYCondiciones(),
	            "No se navegó correctamente a Términos y condiciones");

	    driver.get(BASE_URL);
	    Assert.assertTrue(dashboardPage.openPersonasMenu().clickRegulacion(),
	            "No se navegó correctamente a Regulación");
	}

	@Test
	public void shouldNavigateBeneficiosLinks() {
	    Assert.assertTrue(dashboardPage.openPersonasMenu().clickSelect(),
	            "No se navegó correctamente a Select");

	    driver.get(BASE_URL);
	    Assert.assertTrue(dashboardPage.openPersonasMenu().clickPromocionesSantander(),
	            "No se navegó correctamente a Promociones Santander");

	    driver.get(BASE_URL);
	    Assert.assertTrue(dashboardPage.openPersonasMenu().clickUniqueRewards(),
	            "No se navegó correctamente a Unique Rewards");

	    driver.get(BASE_URL);
	    Assert.assertTrue(dashboardPage.openPersonasMenu().clickSolucionesParaColectivos(),
	            "No se navegó correctamente a Soluciones para colectivos");

	    driver.get(BASE_URL);
	    Assert.assertTrue(dashboardPage.openPersonasMenu().clickMundoHogar(),
	            "No se navegó correctamente a Mundo hogar");

	    driver.get(BASE_URL);
	    Assert.assertTrue(dashboardPage.openPersonasMenu().clickCashback(),
	            "No se navegó correctamente a Cashback");
	}
	
	@Test
	public void shouldNavigateCanalesDigitalesLinks() {
        Assert.assertTrue(dashboardPage.openPersonasMenu().clickSantanderDigital(),
                "No se navegó correctamente a Santander digital");

        driver.get(BASE_URL);
        Assert.assertTrue(dashboardPage.openPersonasMenu().clickAppSantander(),
                "No se navegó correctamente a App Santander");

        driver.get(BASE_URL);
        Assert.assertTrue(dashboardPage.openPersonasMenu().clickSantanderWeb(),
                "No se navegó correctamente a Santander Web");

        driver.get(BASE_URL);
        Assert.assertTrue(dashboardPage.openPersonasMenu().clickLimitePorTransaccion(),
                "No se navegó correctamente a Límite por transacción");
	}
	
	@Test
	public void shouldNavigateTipoDeCuentaLinks() {
        Assert.assertTrue(dashboardPage.openPersonasMenu().clickCuentas(),
                "No se navegó correctamente a Cuentas");

        driver.get(BASE_URL);
        Assert.assertTrue(dashboardPage.openPersonasMenu().clickBasica(),
                "No se navegó correctamente a Básica");

        driver.get(BASE_URL);
        Assert.assertTrue(dashboardPage.openPersonasMenu().clickNomina(),
                "No se navegó correctamente a Nómina");

        driver.get(BASE_URL);
        Assert.assertTrue(dashboardPage.openPersonasMenu().clickCheques(),
                "No se navegó correctamente a Cheques");

        driver.get(BASE_URL);
        Assert.assertTrue(dashboardPage.openPersonasMenu().clickPortabilidadDeNomina(),
                "No se navegó correctamente a Portabilidad de nómina");
	}
}
