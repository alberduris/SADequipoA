package org.traductor;

import java.io.File;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.CSVLoader;

public class Filtro {
	
	 
	public Filtro(){
		
	}
	
	public void filtrar(String path){
		 // load CSV 
	    CSVLoader loader = new CSVLoader();
	    try {
			loader.setSource(new File(path));
			//loader.
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] arg){
		Filtro f = new Filtro();
		f.filtrar("tweetSentiment.train.csv");
	}
}
