package org.sad.labo4;

import java.io.File;
import java.util.Random;

import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.core.Instances;
import weka.core.stemmers.LovinsStemmer;
import weka.core.tokenizers.WordTokenizer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Discretize;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.filters.unsupervised.instance.Randomize;

public class Preprocess {

	static StringToWordVector stwv;
	static InfoGainAttributeEval infoGain;
	
	
	/*
	 * brief Aplica el filtro stringToWordVector de Weka mediante BatchFiltering y guarda los datos 
	 * 
	 * note Se aplica con las configuraciones del filtro decididas
	 * 
	 * return void 
	 */
	public static void stringToWordVector() {

		stwv = new StringToWordVector();

		try {

			stwv.setInputFormat(DataHolder.getDatosTrain());
			stwv.setAttributeIndices("3");
			stwv.setWordsToKeep(99999);
			stwv.setLowerCaseTokens(true);// Aumenta algo el porcentaje pero ¿Ad-Hoc? 
			stwv.setStemmer(new LovinsStemmer());// Aumenta algo el porcentaje Stemmer buena idea 
			stwv.setTokenizer(new WordTokenizer());
			stwv.setUseStoplist(true); //Mejor resultado
			stwv.setOutputWordCounts(true); //Es importante tenerlo para el TFIDF
			stwv.setIDFTransform(false);
			stwv.setStopwords(new File("/files/custom_stop.csv"));

			//Guardar todos los nuevos datos con batch filtering
			if(DataHolder.getDatosTrain()!=null)DataHolder.setDatosTrain(Filter.useFilter(DataHolder.getDatosTrain(), stwv));
			if(DataHolder.getDatosTest()!=null)DataHolder.setDatosTest(Filter.useFilter(DataHolder.getDatosTest(), stwv));
			if(DataHolder.getDatosBlind()!=null)DataHolder.setDatosBlind(Filter.useFilter(DataHolder.getDatosBlind(), stwv));
			if(DataHolder.getDatosTrainTest()!=null)DataHolder.setDatosTrainTest(Filter.useFilter(DataHolder.getDatosTrainTest(), stwv));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * brief Aplica la selección de atributos InfoGain y el filtro Discretize de Weka mediante BatchFiltering y guarda los datos 
	 * 
	 * note Se aplica con las configuraciones del filtro decididas
	 * 
	 * use Discretize
	 * 
	 * return void 
	 */
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
			if(DataHolder.getDatosTrain()!=null)DataHolder.setDatosTrain(Filter.useFilter(DataHolder.getDatosTrain(), filter));
			if(DataHolder.getDatosTest()!=null)DataHolder.setDatosTest(Filter.useFilter(DataHolder.getDatosTest(), filter));
			if(DataHolder.getDatosBlind()!=null)DataHolder.setDatosBlind(Filter.useFilter(DataHolder.getDatosBlind(), filter));
			if(DataHolder.getDatosTrainTest()!=null)DataHolder.setDatosTrainTest(Filter.useFilter(DataHolder.getDatosTrainTest(), filter));

			//Ya de paso se discretiza
			if(DataHolder.getDatosTrain()!=null)DataHolder.setDatosTrain(discretize(DataHolder.getDatosTrain()));
			if(DataHolder.getDatosTest()!=null)DataHolder.setDatosTest(discretize(DataHolder.getDatosTest()));
			if(DataHolder.getDatosBlind()!=null)DataHolder.setDatosBlind(discretize(DataHolder.getDatosBlind()));
			if(DataHolder.getDatosTrainTest()!=null)DataHolder.setDatosTrainTest(discretize(DataHolder.getDatosTrainTest()));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * brief Aplica el filtro randomize de Weka mediante BatchFiltering y guarda los datos 
	 * 
	 * note Se aplica con las configuraciones del filtro decididas
	 * 
	 * params pData Las instancias a las que se aplica el filtro
	 * 
	 * return void 
	 */
	public static void randomize(Instances pData) {

		Instances datosProcesados = null;
		Randomize randomizer = new Randomize();

		try {
			randomizer.setInputFormat(pData);

			Random rnd = new Random();
			int seed = rnd.nextInt(9999);
			randomizer.setRandomSeed(seed);

			datosProcesados = Filter.useFilter(pData, randomizer);
			DataHolder.setDatosTrainTest(datosProcesados); // los datos

		}

		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

	/*
	 * brief Aplica el filtro Discretize de Weka mediante BatchFiltering y guarda los datos 
	 * 
	 * note Se aplica con las configuraciones del filtro decididas
	 * 
	 * params pData Las instancias a las que se aplica el filtro
	 * 
	 * return void 
	 */
	public static Instances discretize(Instances pData) {

		Instances datosProcesados = null;
		Discretize discretizer = new Discretize();

		try {
			discretizer.setInputFormat(pData);
			datosProcesados = Filter.useFilter(pData, discretizer);
		}

		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return datosProcesados;

	}

}
