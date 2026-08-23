package despues;

public class MotorGasolina implements Motor {

	private String fabricante;
	private Integer potenciaKW;
	
	public MotorGasolina(String fabricante, Integer potenciaKW) {
		this.fabricante = fabricante;
		this.potenciaKW = potenciaKW;
	}
	
	@Override
	public void arrancarMotor() {
		System.out.println("Arrancando motor gasolina fabricado por: " + fabricante + " y con potencia de: " + potenciaKW + "kw");
	}

}
