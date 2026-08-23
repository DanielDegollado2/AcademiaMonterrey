package despues;

public class Principal {

	public static void main(String[] args) {
		// Ejemplo con inyeccion de dependencias
		
		//Inyector le proporciona el motor al auto
		Auto auto = Inyector.getAuto();
		
		// Auto puede utilizar la dependencia proporcionada por el inyector
		auto.arrancarAuto();
		
		// inyector puede inyectar un motor electrico o de gasolina sin necesidad de tocar la clase auto

	}

}
