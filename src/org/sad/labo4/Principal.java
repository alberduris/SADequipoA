package org.sad.labo4;

public class Principal {

	//La ejecucion del main
	// java -jar nombreJar.jar train.arff test.arff blind.arff
	
	public static void main(String[] args) {
		
		
		//Cargar datos
		DataHolder.loadTrainData(args[0]);
		//DataHolder.loadTestData(args[1]);
		//DataHolder.loadBlindData(args[2]);
				
		//Preprocesado
		Preprocess.stringToWordVector(DataHolder.getDatosTrain());
		Preprocess.selectAttributes();
		
		
		//Imprimir
		DataHolder.printAttributos(DataHolder.getDatosTrain());

	}

}
