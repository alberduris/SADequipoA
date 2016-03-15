package org.traductor;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Add;
import weka.filters.unsupervised.attribute.Remove;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Traductor {
	
	

	/**vcxftghj
	 * takes 2 arguments: - CSV input file - ARFF output file
	 */

	public static void traduceTweet(String pPath) {
		
		
		

		try {

			CSVLoader loader = new CSVLoader();
			loader.setSource(new File("output_auxiliar_tweet.csv"));
		
			String [] listaOpciones = new String[6];
			listaOpciones[0] = "-N";
			listaOpciones[1] = "1,2";
			listaOpciones[2] = "-S";
			listaOpciones[3] = "4,5";
			listaOpciones[4] = "-E";
			listaOpciones[5] = "\"";
			loader.setOptions(listaOpciones);
			
			Instances data = loader.getDataSet();
			
			// save ARFF
			ArffSaver saver = new ArffSaver();
			saver.setInstances(data);
			File aux = new File(System.getenv("APPDATA")+"617578696c696172");
			saver.setFile(aux);
			File output = new File(pPath);
			saver.setDestination(output);
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
