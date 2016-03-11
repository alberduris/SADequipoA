package org.traductor;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

import java.io.File;

public class MainTraductor {

	/**
	 * takes 2 arguments: - CSV input file - ARFF output file
	 */

	public static void traduceTweet() {

		try {

			System.out.println("Comenzando");

			// load CSV
			CSVLoader loader = new CSVLoader();
			loader.setSource(new File("output_firstStep_tweet.csv"));
			
			loader.setNominalAttributes("1,2");
			loader.setStringAttributes("4,5");
			
			
			Instances data = loader.getDataSet();
			
			System.out.println("CSV Cargado");

			// save ARFF
			ArffSaver saver = new ArffSaver();
			saver.setInstances(data);
			saver.setFile(new File("auxiliar"));
			saver.setDestination(new File("output_tweet.arff"));
			saver.writeBatch();

			System.out.println("Arff generado");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void traduceSMS(){
		try {

			System.out.println("Comenzando");

			// load CSV
			CSVLoader loader = new CSVLoader();
			loader.setSource(new File("output_firstStep_sms.csv"));
			
			loader.setNominalAttributes("1");
			loader.setStringAttributes("2");
			
			
			Instances data = loader.getDataSet();
			
			System.out.println("CSV Cargado");

			// save ARFF
			ArffSaver saver = new ArffSaver();
			saver.setInstances(data);
			saver.setFile(new File("auxiliar2"));
			saver.setDestination(new File("output_sms.arff"));
			saver.writeBatch();

			System.out.println("Arff generado");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) throws Exception {

		traduceTweet();
		traduceSMS();
	}

}
