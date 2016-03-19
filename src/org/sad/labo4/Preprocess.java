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

	public static void stringToWordVector() {

		stwv = new StringToWordVector();

		try {
			stwv.setInputFormat(DataHolder.getDatosTrain());
			stwv.setAttributeIndices("3");
			stwv.setWordsToKeep(99999);
			stwv.setLowerCaseTokens(true);// Aumenta algo el porcentaje pero ¿Ad-Hoc? 
			stwv.setStemmer(new LovinsStemmer());// Aumenta algo el porcentaje Stemmer buena idea 
			stwv.setIDFTransform(true);// ¿No hace nada? 
			
			//Guardar todos los nuevos datos con batch filtering
			DataHolder.setDatosTrain(Filter.useFilter(DataHolder.getDatosTrain(), stwv));
			DataHolder.setDatosTest(Filter.useFilter(DataHolder.getDatosTest(), stwv));
			DataHolder.setDatosBlind(Filter.useFilter(DataHolder.getDatosBlind(), stwv));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void infoGain() {

		try {
			weka.filters.supervised.attribute.AttributeSelection filter = new weka.filters.supervised.attribute.AttributeSelection();

			InfoGainAttributeEval infoGain = new InfoGainAttributeEval();
			Ranker search = new Ranker();
			search.setThreshold(0);

			filter.setEvaluator(infoGain);
			filter.setSearch(search);
			filter.setInputFormat(DataHolder.getDatosTrain());
			
			//Guardar todos los nuevos datos con batch filtering
			DataHolder.setDatosTrain(Filter.useFilter(DataHolder.getDatosTrain(), filter));
			DataHolder.setDatosTest(Filter.useFilter(DataHolder.getDatosTest(), filter));
			DataHolder.setDatosBlind(Filter.useFilter(DataHolder.getDatosBlind(), filter));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	

}
