package org.sad.labo4;

import java.io.File;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.stemmers.LovinsStemmer;
import weka.core.tokenizers.WordTokenizer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class ArffToBOW {

	public static void main(String[] args) {
		try {
			System.out.println("Transformando instancias a representación BOW...");
			stringToWordVector(DataHolder.loadData(args[0]), args[1]);
			System.out.println("Transformación completada");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Ops! Algo ha ido mal...");
		}
	}

	/*
	 * brief Aplica el filtro stringToWordVector de Weka y guarda los datos 
	 * 
	 * note Se aplica con las configuraciones del filtro decididas
	 * 
	 * return void Genera un archivo arff con el resultado de aplicar el filtro
	 */
	public static void stringToWordVector(Instances pData, String pName) {
		StringToWordVector stwv = new StringToWordVector();
		Instances datosBOW = null;

		try {

			stwv.setInputFormat(pData);
			stwv.setAttributeIndices("3");
			stwv.setWordsToKeep(99999);
			stwv.setLowerCaseTokens(true);// Aumenta algo el porcentaje pero ¿Ad-Hoc? 
			stwv.setStemmer(new LovinsStemmer());// Aumenta algo el porcentaje Stemmer buena idea 
			stwv.setTokenizer(new WordTokenizer());
			stwv.setUseStoplist(false); //Mejor resultado
			stwv.setOutputWordCounts(true); //Es importante tenerlo para el InfoGain
			stwv.setIDFTransform(false);
			stwv.setStopwords(new File("/files/custom_stop.csv"));

			//Guardar todos los nuevos datos con batch filtering
			datosBOW = Filter.useFilter(pData, stwv);

			ArffSaver saver = new ArffSaver();
			saver.setInstances(datosBOW);
			saver.setFile(new File(pName));
			saver.writeBatch();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
