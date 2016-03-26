package org.sad.labo4;

import java.io.File;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.stemmers.LovinsStemmer;
import weka.core.tokenizers.WordTokenizer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class FSSTDIDF {

	public static void main(String[] args) {
		
		String minTermFreq = "";
		
		try {
			System.out.println("Eliminando atributos redundantes e irrelevantes mediante InfoGain...");
			if(args.length == 3) minTermFreq = args[2];
			tfidf(DataHolder.loadData(args[0]), args[1], minTermFreq);
			System.out.println("Proceso completado");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Ops! Algo ha ido mal...");
		}

	}

	private static void tfidf(Instances pData, String pName, String pMinTermFreq) {
		int minTermFreq = 2;
		if(pMinTermFreq.length() != 0){
			minTermFreq = Integer.valueOf(pMinTermFreq);
		}
		
		 
		StringToWordVector stwv = new StringToWordVector();
		Instances datosTFIDF = null;

		try {

			stwv.setInputFormat(pData);
			stwv.setAttributeIndices("3-last");
			stwv.setWordsToKeep(99999);
			stwv.setLowerCaseTokens(true);// Aumenta algo el porcentaje pero ¿Ad-Hoc? 
			stwv.setStemmer(new LovinsStemmer());// Aumenta algo el porcentaje Stemmer buena idea 
			stwv.setTokenizer(new WordTokenizer());
			stwv.setUseStoplist(false); //Mejor resultado
			stwv.setOutputWordCounts(true); //Es importante tenerlo para el InfoGain
			stwv.setStopwords(new File("/files/custom_stop.csv"));
			
			//TFIDF
			stwv.setIDFTransform(true);
			stwv.setMinTermFreq(minTermFreq);

			//Guardar todos los nuevos datos con batch filtering
			datosTFIDF = Filter.useFilter(pData, stwv);

			ArffSaver saver = new ArffSaver();
			saver.setInstances(datosTFIDF);
			saver.setFile(new File(pName));
			saver.writeBatch();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
