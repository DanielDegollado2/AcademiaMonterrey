package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import base.BasePage;

public class PersonasMenu extends BasePage{

	private static final By TARJETAS_DE_CREDITO_LINK =
	        By.cssSelector("ul[aria-label='Crédito y financiamiento'] a[href*='tarjetas-de-credito']");
	
	private static final By CREDITO_PERSONAL_LINK =
	        By.cssSelector("ul[aria-label='Crédito y financiamiento'] a[href*='creditos-personales']");
	
	private static final By CREDITO_HIPOTECARIO_LINK =
	        By.cssSelector("ul[aria-label='Crédito y financiamiento'] a[href$='/creditos-hipotecarios/']");
	
	private static final By SIMULADOR_DE_HIPOTECA_LINK =
	        By.cssSelector("ul[aria-label='Crédito y financiamiento'] a[href*='simulador-hipotecario']");
	
	private static final By CREDITO_AUTOMOTRIZ_LINK =
	        By.cssSelector("ul[aria-label='Crédito y financiamiento'] a[href*='credito-automotriz']");
	
	private static final By BURO_DE_CREDITO_LINK =
	        By.cssSelector("ul[aria-label='Crédito y financiamiento'] a[href*='buro-de-credito']");
	
	private static final By SANTANDER_DIGITAL_LINK =
	        By.cssSelector("ul[aria-label='Canales digitales'] a[href$='/santander-digital/']");
	
	private static final By APP_SANTANDER_LINK =
	        By.cssSelector("ul[aria-label='Canales digitales'] a[href*='app-santander']");
	
	private static final By SANTANDER_WEB_LINK =
	        By.cssSelector("ul[aria-label='Canales digitales'] a[href*='santander-web']");
	
	private static final By LIMITE_POR_TRANSACCION_LINK =
	        By.cssSelector("ul[aria-label='Canales digitales'] a[href*='limite-por-transaccion']");
	
	private static final By CUENTAS_LINK =
	        By.cssSelector("ul[aria-label='Tipo de cuenta'] a[href$='/cuentas/']");
	
	private static final By BASICA_LINK =
	        By.cssSelector("ul[aria-label='Tipo de cuenta'] a[href$='/basica/']");
	
	private static final By NOMINA_LINK =
	        By.cssSelector("ul[aria-label='Tipo de cuenta'] a[href*='basica-nomina']");
	
	private static final By CHEQUES_LINK =
	        By.cssSelector("ul[aria-label='Tipo de cuenta'] a[href*='cheque-saldo-promedio']");
	
	private static final By PORTABILIDAD_DE_NOMINA_LINK =
	        By.cssSelector("ul[aria-label='Tipo de cuenta'] a[href*='portabilidad-de-nomina']");
	
	private static final By FONDOS_DE_INVERSION_LINK =
	        By.cssSelector("ul[aria-label='Ahorro e inversión'] a[href*='fondos-de-inversion']");
	
	private static final By INVERSIONES_A_PLAZO_LINK =
	        By.cssSelector("ul[aria-label='Ahorro e inversión'] a[href*='inversiones-a-plazo']");
	
	private static final By NOTAS_ESTRUCTURADAS_LINK =
	        By.cssSelector("ul[aria-label='Ahorro e inversión'] a[href*='notas-estructuradas']");
	
	private static final By AUTO_LINK =
	        By.cssSelector("ul[aria-label='Seguros'] a[href*='auto']");
	
	private static final By VIDA_LINK =
	        By.cssSelector("ul[aria-label='Seguros'] a[href*='vida']");
	
	private static final By HOGAR_LINK =
	        By.cssSelector("ul[aria-label='Seguros'] a[href*='hogar']");
	
	private static final By AHORRO_LINK =
	        By.cssSelector("ul[aria-label='Seguros'] a[href*='ahorro']");
	
	private static final By GASTOS_MEDICOS_LINK =
	        By.cssSelector("ul[aria-label='Seguros'] a[href*='gastos-medicos']");
	
	private static final By PERTENENCIAS_LINK =
	        By.cssSelector("ul[aria-label='Seguros'] a[href*='pertenencias']");
	
	private static final By SUPERLINEA_LINK =
	        By.cssSelector("ul[aria-label='Información y ayuda'] a[href*='superlinea']");
	
	private static final By SUCURSALES_LINK =
	        By.cssSelector("ul[aria-label='Información y ayuda'] a[href*='sucursales']");
	
	private static final By CAJEROS_AUTOMATICOS_LINK =
	        By.cssSelector("ul[aria-label='Información y ayuda'] a[href*='cajeros-automaticos']");
	
	private static final By CANALES_ALTERNOS_LINK =
	        By.cssSelector("ul[aria-label='Información y ayuda'] a[href*='operaciones-canales-alternos']");

	private static final By CENTRO_DE_AYUDA_LINK =
	        By.cssSelector("ul[aria-label='Información y ayuda'] a[href*='centro-de-ayuda']");
	
	private static final By CENTRO_DE_SEGURIDAD_LINK =
	        By.cssSelector("ul[aria-label='Información y ayuda'] a[href*='centro-de-seguridad']");
	
	private static final By TUTORIALES_LINK =
	        By.cssSelector("ul[aria-label='Información y ayuda'] a[href*='tutoriales']");
	
	private static final By TERMINOS_Y_CONDICIONES_LINK =
	        By.cssSelector("ul[aria-label='Información y ayuda'] a[href*='terminos-y-condiciones']");
	
	private static final By REGULACION_LINK =
	        By.cssSelector("ul[aria-label='Información y ayuda'] a[href*='tramite-por-defuncion']");
	
	private static final By SELECT_LINK =
	        By.cssSelector("ul[aria-label='Beneficios'] a[href*='select']");
	
	private static final By PROMOCIONES_SANTANDER_LINK =
	        By.cssSelector("ul[aria-label='Beneficios'] a[href*='promociones']");
	
	private static final By UNIQUE_REWARDS_LINK =
	        By.cssSelector("ul[aria-label='Beneficios'] a[href*='uniquerewards']");
	
	private static final By SOLUCIONES_PARA_COLECTIVOS_LINK =
	        By.cssSelector("ul[aria-label='Beneficios'] a[href*='colectivos']");
	
	private static final By MUNDO_HOGAR_LINK =
	        By.cssSelector("ul[aria-label='Beneficios'] a[href*='mundohogar']");
	
	private static final By CASHBACK_LINK =
	        By.cssSelector("ul[aria-label='Beneficios'] a[href*='cashback']");
	
	protected PersonasMenu(WebDriver driver) {
		super(driver);
	}	
	
	 // Crédito y financiamiento
    public boolean clickTarjetasDeCredito() {
        return clickAndVerifyUrl(TARJETAS_DE_CREDITO_LINK, "tarjetas-de-credito");
    }
 
    public boolean clickCreditoPersonal() {
        return clickAndVerifyUrl(CREDITO_PERSONAL_LINK, "creditos-personales");
    }
 
    public boolean clickCreditoHipotecario() {
        return clickAndVerifyUrl(CREDITO_HIPOTECARIO_LINK, "creditos-hipotecarios");
    }
 
    public boolean clickSimuladorDeHipoteca() {
        return clickAndVerifyUrl(SIMULADOR_DE_HIPOTECA_LINK, "simulador-hipotecario");
    }
 
    public boolean clickCreditoAutomotriz() {
        return clickAndVerifyUrl(CREDITO_AUTOMOTRIZ_LINK, "credito-automotriz");
    }
 
    public boolean clickBuroDeCredito() {
        return clickAndVerifyUrl(BURO_DE_CREDITO_LINK, "buro-de-credito");
    }
 
    // Canales digitales
    public boolean clickSantanderDigital() {
        return clickAndVerifyUrl(SANTANDER_DIGITAL_LINK, "santander-digital");
    }
 
    public boolean clickAppSantander() {
        return clickAndVerifyUrl(APP_SANTANDER_LINK, "app-santander");
    }
 
    public boolean clickSantanderWeb() {
        return clickAndVerifyUrl(SANTANDER_WEB_LINK, "santander-web");
    }
 
    public boolean clickLimitePorTransaccion() {
        return clickAndVerifyUrl(LIMITE_POR_TRANSACCION_LINK, "limite-por-transaccion");
    }
 
    // Tipo de cuenta
    public boolean clickCuentas() {
        return clickAndVerifyUrl(CUENTAS_LINK, "/cuentas/");
    }
 
    public boolean clickBasica() {
        return clickAndVerifyUrl(BASICA_LINK, "/basica/");
    }
 
    public boolean clickNomina() {
        return clickAndVerifyUrl(NOMINA_LINK, "basica-nomina");
    }
 
    public boolean clickCheques() {
        return clickAndVerifyUrl(CHEQUES_LINK, "cheque-saldo-promedio");
    }
 
    public boolean clickPortabilidadDeNomina() {
        return clickAndVerifyUrl(PORTABILIDAD_DE_NOMINA_LINK, "portabilidad-de-nomina");
    }
 
    // Ahorro e inversión
    public boolean clickFondosDeInversion() {
        return clickAndVerifyUrl(FONDOS_DE_INVERSION_LINK, "fondos-de-inversion");
    }
 
    public boolean clickInversionesAPlazo() {
        return clickAndVerifyUrl(INVERSIONES_A_PLAZO_LINK, "inversiones-a-plazo");
    }
 
    public boolean clickNotasEstructuradas() {
        return clickAndVerifyUrl(NOTAS_ESTRUCTURADAS_LINK, "notas-estructuradas");
    }
 
    // Seguros
    public boolean clickAuto() {
        return clickAndVerifyUrl(AUTO_LINK, "#auto");
    }
 
    public boolean clickVida() {
        return clickAndVerifyUrl(VIDA_LINK, "#vida");
    }
 
    public boolean clickHogar() {
        return clickAndVerifyUrl(HOGAR_LINK, "#hogar");
    }
 
    public boolean clickAhorro() {
        return clickAndVerifyUrl(AHORRO_LINK, "#ahorro");
    }
 
    public boolean clickGastosMedicos() {
        return clickAndVerifyUrl(GASTOS_MEDICOS_LINK, "gastos-medicos");
    }
 
    public boolean clickPertenencias() {
        return clickAndVerifyUrl(PERTENENCIAS_LINK, "pertenencias");
    }
 
    // Información y ayuda
    public boolean clickSuperlinea() {
        return clickAndVerifyUrl(SUPERLINEA_LINK, "superlinea");
    }
 
    public boolean clickSucursales() {
        return clickAndVerifyUrl(SUCURSALES_LINK, "sucursales");
    }
 
    public boolean clickCajerosAutomaticos() {
        return clickAndVerifyUrl(CAJEROS_AUTOMATICOS_LINK, "cajeros-automaticos");
    }
 
    public boolean clickCanalesAlternos() {
        return clickAndVerifyUrl(CANALES_ALTERNOS_LINK, "operaciones-canales-alternos");
    }
 
    public boolean clickCentroDeAyuda() {
        return clickAndVerifyUrl(CENTRO_DE_AYUDA_LINK, "centro-de-ayuda");
    }
 
    public boolean clickCentroDeSeguridad() {
        return clickAndVerifyUrl(CENTRO_DE_SEGURIDAD_LINK, "centro-de-seguridad");
    }
 
    public boolean clickTutoriales() {
        return clickAndVerifyUrl(TUTORIALES_LINK, "tutoriales");
    }
 
    public boolean clickTerminosYCondiciones() {
        return clickAndVerifyUrl(TERMINOS_Y_CONDICIONES_LINK, "tyc");
    }
 
    public boolean clickRegulacion() {
        return clickAndVerifyUrl(REGULACION_LINK, "tramite-por-defuncion");
    }
 
    // Beneficios
    public boolean clickSelect() {
    	wait.until(ExpectedConditions.visibilityOfElementLocated(SELECT_LINK));
        return clickAndVerifyUrl(SELECT_LINK, "select");
    }
 
    public boolean clickPromocionesSantander() {
        return clickAndVerifyUrl(PROMOCIONES_SANTANDER_LINK, "promociones");
    }
 
    public boolean clickUniqueRewards() {
        return clickAndVerifyUrl(UNIQUE_REWARDS_LINK, "uniquerewards");
    }
 
    public boolean clickSolucionesParaColectivos() {
        return clickAndVerifyUrl(SOLUCIONES_PARA_COLECTIVOS_LINK, "colectivos");
    }
 
    public boolean clickMundoHogar() {
        return clickAndVerifyUrl(MUNDO_HOGAR_LINK, "mundohogar");
    }
 
    public boolean clickCashback() {
        return clickAndVerifyUrl(CASHBACK_LINK, "cashback");
    }
}
