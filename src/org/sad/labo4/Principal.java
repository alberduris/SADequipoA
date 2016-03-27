package org.sad.labo4;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Principal {

	static Scanner scanner = new Scanner(System.in);
	private static FileWriter writerK2;
	
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
		

		//Clasificar-Evaluar
		
//		//Resubstitution
//		Evaluate.evaluarNaiveResubstitution();
//		Evaluate.evaluarBayesNetResubstitution();
//		
//		//CrossFold
//		Evaluate.evaluarNaiveCrossFold();
//		Evaluate.evaluarBayesNetCrossFold();
//		
//		//HoldOut
//		Evaluate.evaluarNaiveHoldOut();
//		Evaluate.evaluarBayesNetHoldOut();
		
		//BarridoParametros
		//Evaluate.barridoParametrosK2();
		Evaluate.barridoParametrosHillClimbing();

		
		
		



	}

}
