package org.sad.labo4;

import java.util.Random;

import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.core.Instances;
import weka.core.stemmers.LovinsStemmer;
import weka.core.tokenizers.WordTokenizer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.filters.unsupervised.instance.Randomize;

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
			stwv.setTokenizer(new WordTokenizer());
			stwv.setUseStoplist(false); //Mejor resultado
			stwv.setOutputWordCounts(true); //Es importante tenerlo para el InfoGain
			stwv.setIDFTransform(true);// ¿No hace nada? 
			
			//Guardar todos los nuevos datos con batch filtering
			DataHolder.setDatosTrain(Filter.useFilter(DataHolder.getDatosTrain(), stwv));
			DataHolder.setDatosTest(Filter.useFilter(DataHolder.getDatosTest(), stwv));
			DataHolder.setDatosBlind(Filter.useFilter(DataHolder.getDatosBlind(), stwv));
			DataHolder.setDatosTrainTest(Filter.useFilter(DataHolder.getDatosTrainTest(), stwv));
			
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void infoGain() {

		try {
			weka.filters.supervised.attribute.AttributeSelection filter = new weka.filters.supervised.attribute.AttributeSelection();

			InfoGainAttributeEval infoGain = new InfoGainAttributeEval();
			infoGain.setMissingMerge(true);
			infoGain.setBinarizeNumericAttributes(false);
			Ranker search = new Ranker();
			search.setThreshold(0);
			search.setNumToSelect(-1);

			filter.setEvaluator(infoGain);
			filter.setSearch(search);
			filter.setInputFormat(DataHolder.getDatosTrain());
			
			
			
			
		
			//Guardar todos los nuevos datos con batch filtering
			DataHolder.setDatosTrain(Filter.useFilter(DataHolder.getDatosTrain(), filter));
			DataHolder.setDatosTest(Filter.useFilter(DataHolder.getDatosTest(), filter));
			DataHolder.setDatosBlind(Filter.useFilter(DataHolder.getDatosBlind(), filter));
			DataHolder.setDatosTrainTest(Filter.useFilter(DataHolder.getDatosTrainTest(), filter));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void randomize(Instances pData) {
		
		Instances datosProcesados = null;
		Randomize randomizer = new Randomize(); 
		
		try {
			randomizer.setInputFormat(pData);
		
			Random rnd = new Random();
			int seed = rnd.nextInt(9999);
			randomizer.setRandomSeed(seed); 
											

			datosProcesados = Filter.useFilter(pData, randomizer); 
			DataHolder.setDatosTrainTest(datosProcesados);												// los datos
																	
		}

		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	

}
