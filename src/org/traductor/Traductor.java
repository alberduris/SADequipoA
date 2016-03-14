package org.traductor;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

import java.io.File;

public class Traductor {

	/**vcxftghj
	 * takes 2 arguments: - CSV input file - ARFF output file
	 */

	public static void traduceTweet(String pPath) {

		try {

			// load CSV
			CSVLoader loader = new CSVLoader();
			loader.setSource(new File("output_auxiliar_tweet.csv"));
			
			loader.setNominalAttributes("1,2");
			loader.setStringAttributes("4,5");
			
			
			Instances data = loader.getDataSet();
			

			// save ARFF
			ArffSaver saver = new ArffSaver();
			saver.setInstances(data);
			File aux = new File(System.getenv("APPDATA")+"617578696c696172");
			saver.setFile(aux);
			saver.setDestination(new File(pPath));
			saver.writeBatch();


			
			aux.delete();
			new File("output_auxiliar_tweet.csv").delete();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void traduceSMS(String pPath){
		try {

			// load CSV
			CSVLoader loader = new CSVLoader();
			loader.setSource(new File("output_auxiliar_sms.csv"));
			
			loader.setNominalAttributes("1");
			
			
			loader.setStringAttributes("2");
			
			
			Instances data = loader.getDataSet();
			

			// save ARFF
			ArffSaver saver = new ArffSaver();
			saver.setInstances(data);
			File aux = new File(System.getenv("APPDATA")+"617578696c696172");
			saver.setFile(aux);
			saver.setDestination(new File(pPath));
			saver.writeBatch();

		
			
			aux.delete();
			new File("output_auxiliar_sms.csv").delete();
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) throws Exception {

		
	}

}
