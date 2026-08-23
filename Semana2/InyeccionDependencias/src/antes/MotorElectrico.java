package antes;

public class MotorElectrico {
	private String fabricante;
	private Integer potenciaKW;
	
	public MotorElectrico(String fabricante, Integer potenciaKW) {
		this.fabricante = fabricante;
		this.potenciaKW = potenciaKW;
	}
	
	public void arrancarMotor() {
		System.out.println("Arrancando motor electrico fabricado por: " + fabricante + " y con potencia de: " + potenciaKW + "kw");
	}
	
}
