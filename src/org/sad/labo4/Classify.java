package org.sad.labo4;

import java.io.File;
import java.io.FileWriter;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class Classify {

	static BayesNet bayesNet;
	static double[] predictions;
	static Instance[] instancias;
	private static int trainSize;
	private static int testSize;
	private static NaiveBayes naiveBayes;
	private static FileWriter writer;

	public static void classifiyBayesNet() {

		Preprocess.randomize(DataHolder.getDatosTrainTest());
		percentageSplit(70);//Viene especificado en la guia
		Instances testInstances = new Instances(DataHolder.getDatosTrainTest(), trainSize, testSize);

		predictions = new double[DataHolder.getDatosBlind().numInstances()];
		instancias = new Instance[DataHolder.getDatosBlind().numInstances()];

		try {

			bayesNet = (BayesNet) SerializationHelper.read("modeloBinarioBayesNet");
			Evaluation eval = new Evaluation(testInstances);
			eval.evaluateModel(bayesNet, testInstances);

			for (int i = 0; i < predictions.length; i++) {
				instancias[i] = DataHolder.getDatosBlind().instance(i);
				predictions[i] = eval.evaluateModelOnceAndRecordPrediction(bayesNet,
						DataHolder.getDatosBlind().instance(i));

			}

			printPredictions("Predicciones BAYES-NET");
			instancias = null;
			predictions = null;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void classifiyNaiveBayes() {

		Preprocess.randomize(DataHolder.getDatosTrainTest());
		percentageSplit(70);//Viene especificado en la guia
		Instances testInstances = new Instances(DataHolder.getDatosTrainTest(), trainSize, testSize);

		predictions = new double[DataHolder.getDatosBlind().numInstances()];
		instancias = new Instance[DataHolder.getDatosBlind().numInstances()];

		try {

			naiveBayes = new NaiveBayes();
			naiveBayes.buildClassifier(testInstances);
			Evaluation eval = new Evaluation(testInstances);
			eval.evaluateModel(naiveBayes, testInstances);

			for (int i = 0; i < predictions.length; i++) {
				instancias[i] = DataHolder.getDatosBlind().instance(i);
				predictions[i] = eval.evaluateModelOnceAndRecordPrediction(naiveBayes,
						instancias[i]);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		printPredictions("CLASIFICACION NAIVE-BAYES");
		instancias = null;
		predictions = null;

	}

	public static void printPredictions(String pTitulo) {

		System.out.println(pTitulo);
		
		try {
			File file = new File("Clasificacion\\Clasificacion_Naive_Bayes_Bayes_Net.txt");
			file.getParentFile().mkdir();
			file.createNewFile();
			writer = new FileWriter(file,true);
			writer.write(pTitulo+"\n");

			String prediccion = "";

			for (int i = 0; i < predictions.length; i++) {

				switch ((int) predictions[i]) {
				case 0:
					prediccion = "Positive";
				case 1:
					prediccion = "Negative";
				case 2:
					prediccion = "Neutral";
				}
				System.out.println(instancias[i] + " --> Clase predecida: " + prediccion);
				writer.write(instancias[i] + " --> Clase predecida: " + prediccion+"\n");
			}
			
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		

	}

	public static void percentageSplit(int pPercentage) {
		trainSize = (int) Math.round(DataHolder.getDatosTrainTest().numInstances() * pPercentage / 100);
		testSize = DataHolder.getDatosTrainTest().numInstances() - trainSize;
	}

	public static void main(String[] args) {

		System.out.println("Cargando datos...");
		//Cargar datos
		DataHolder.loadTrainData(args[0]);
		DataHolder.loadTestData(args[1]);
		DataHolder.loadBlindData(args[2]);
		DataHolder.loadTrainTestData();
		System.out.println("Datos cargados");

		System.out.println("Preprocesando datos...");
		//Preprocesado
		Preprocess.stringToWordVector();
		Preprocess.infoGain();
		System.out.println("Datos preprocesados");

		classifiyNaiveBayes();
		classifiyBayesNet();

	}

}
