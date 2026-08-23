package despues;

public class Auto {

	private String modelo;
	private Motor motor;
	
	public Auto(String modelo, Motor motor) {
		this.modelo = modelo;
		this.motor = motor;
	}
	
	public void arrancarAuto() {
		System.out.println("Encendiendo auto " + modelo);
		motor.arrancarMotor();
	}
}
