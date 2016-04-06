package org.sad.labo4;

import java.util.Scanner;

public class Principal {

	static Scanner scanner = new Scanner(System.in);
	
	
	//La ejecucion del main
	// java -jar nombreJar.jar train.arff test.arff blind.arff
	
	public static void main(String[] args) {
		
		
		System.out.println("PRINCIPAL PRUEBAS");
		
		System.out.println("Cargando datos...");
		//Cargar datos
		DataHolder.loadTrainData(args[0]);
		DataHolder.loadTestData(args[1]);
		DataHolder.loadBlindData(args[2]);
		DataHolder.loadTrainTestData();
		System.out.println("Datos cargados");

		System.out.println("Preprocesando datos...");
		//Preprocesado
		Preprocess.stringToWordVector();
		Preprocess.infoGain();
		System.out.println("Datos preprocesados");
		
		System.out.println("FIN PRINCIPAL PRUEBAS");


		
		

		
		
		



	}

}
