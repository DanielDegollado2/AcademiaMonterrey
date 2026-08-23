package despues;

public class MotorElectrico implements Motor {

	private String fabricante;
	private Integer potenciaKW;
	
	public MotorElectrico(String fabricante, Integer potenciaKW) {
		this.fabricante = fabricante;
		this.potenciaKW = potenciaKW;
	}
	
	@Override
	public void arrancarMotor() {
		System.out.println("Arrancando motor electrico fabricado por: " + fabricante + " y con potencia de: " + potenciaKW + "kw");
		
	}

}
