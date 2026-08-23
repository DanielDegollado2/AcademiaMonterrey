package despues;

public class Inyector {
	
	static Auto getAuto() {
		Motor motorElectrico = new MotorElectrico("Siemens", 75);
		Motor motorGasolina = new MotorGasolina("Ford", 134);
		
		return new Auto("Mustang", motorGasolina);
	}
}
