package antes;

public class Auto {
	private String modelo;
	MotorElectrico motor;
	
	public Auto(String modelo) {
		this.modelo = modelo;
	}
	
	public void arrancarAuto() {
		System.out.println("Encendiendo auto " + modelo);
		motor = new MotorElectrico("Siemens", 15);
		motor.arrancarMotor();
	}
	
	
}
