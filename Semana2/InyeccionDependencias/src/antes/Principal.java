package antes;

public class Principal {

	public static void main(String[] args) {
		//Version sin inyeccion de dependencia y alto acoplamiento, se utiliza un new dentro de la clase auto para generar el motor
		
		Auto auto = new Auto("Nissan");
		auto.arrancarAuto();

	}

}
