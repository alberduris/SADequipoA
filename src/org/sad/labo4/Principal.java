package org.sad.labo4;

import java.util.Scanner;

public class Principal {

	static Scanner scanner = new Scanner(System.in);
	
	//La ejecucion del main
	// java -jar nombreJar.jar train.arff test.arff blind.arff
	
	public static void main(String[] args) {
		
		
		//Cargar datos
		DataHolder.loadTrainData(args[0]);
		DataHolder.loadTestData(args[1]);
		DataHolder.loadBlindData(args[2]);
				
		//Preprocesado
		Preprocess.stringToWordVector();
		Preprocess.infoGain();
		
		
		//Imprimir
		DataHolder.printAttributos(DataHolder.getDatosTrain());
		scanner.nextLine();
		DataHolder.printAttributos(DataHolder.getDatosTest());
		scanner.nextLine();
		DataHolder.printAttributos(DataHolder.getDatosBlind());

	}

}
