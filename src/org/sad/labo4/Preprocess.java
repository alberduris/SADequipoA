package org.sad.labo4;

import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.core.Instances;
import weka.core.stemmers.LovinsStemmer;
import weka.core.stemmers.Stemmer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class Preprocess {

	static StringToWordVector stwv;
	static InfoGainAttributeEval infoGain;

	public static void stringToWordVector(Instances pDatos) {

		stwv = new StringToWordVector();

		try {
			stwv.setInputFormat(pDatos);
			stwv.setAttributeIndices("3");
			stwv.setWordsToKeep(99999);
			stwv.setLowerCaseTokens(true);// Aumenta algo el porcentaje pero
											// �Ad-Hoc?
			stwv.setStemmer(new LovinsStemmer());// Aumenta algo el porcentaje
													// --> Stemmer buena idea
			stwv.setIDFTransform(true);// �No hace nada?
			Instances nuevosDatos = Filter.useFilter(pDatos, stwv);
			DataHolder.setDatosTrain(nuevosDatos);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void infoGain() {

		try{
		    weka.filters.supervised.attribute.AttributeSelection filter = new weka.filters.supervised.attribute.AttributeSelection();

		    InfoGainAttributeEval infoGain = new InfoGainAttributeEval();
		    Ranker search = new Ranker();
		    search.setThreshold(0);
		    
		    filter.setEvaluator(infoGain);
		    filter.setSearch(search);
		    filter.setInputFormat(DataHolder.getDatosTrain());
		    Instances newData = Filter.useFilter(DataHolder.getDatosTrain(), filter);
		    DataHolder.setDatosTrain(newData);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}

	public static void selectAttributes() {
		
		
	}

	public static void applyFilter() {

		// TODO
		// Filter.useFilter(filter, DataHolder.getDatos());

	}

}
