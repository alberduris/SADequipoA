package org.traductor;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

import java.io.File;

public class MainTraductor {
	
	 /**
	   * takes 2 arguments:
	   * - CSV input file
	   * - ARFF output file
	   */
	  public static void main(String[] args) throws Exception {
	   
	   System.out.println("Comenzando");
		  
	    // load CSV 
	    CSVLoader loader = new CSVLoader();
	    loader.setSource(new File("tweetSentiment.train.csv"));
	    Instances data = loader.getDataSet();
	 
	    System.out.println("CSV Cargado");
	    
	    // save ARFF
	    ArffSaver saver = new ArffSaver();
	    saver.setInstances(data);
	    saver.setFile(new File(args[1]));
	    saver.setDestination(new File("output.arff"));
	    saver.writeBatch();
	    
	    System.out.println("Arff generado");

	  }

}
