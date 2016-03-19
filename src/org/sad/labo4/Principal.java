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
		DataHolder.loadTrainTestData();
				
		//Preprocesado
		Preprocess.stringToWordVector();
		Preprocess.infoGain();
		
		
		//Imprimir
		System.out.println(DataHolder.getDatosTrain().numAttributes());
		System.out.println(DataHolder.getDatosTest().numAttributes());
		System.out.println(DataHolder.getDatosBlind().numAttributes());
		//System.out.println(DataHolder.getDatosTrainTest().numAttributes());


	}

}
