package org.sad.labo4;

import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class Evaluate {
	
	static NaiveBayes naiveBayes;
	static BayesNet bayesNet;
	
	static int trainSize;
	static int testSize;
	
	
	
	
	public static void evaluarNaiveResubstitution(){
		try {
			
			System.out.println("** NAÏVE BAYES - RESUBSTITUTION**");

			naiveBayes = new NaiveBayes();
			naiveBayes.buildClassifier(DataHolder.getDatosTrainTest());

			Evaluation eval = new Evaluation(DataHolder.getDatosTrainTest());
			eval.evaluateModel(naiveBayes, DataHolder.getDatosTrainTest());		
			printResultSet(eval,DataHolder.getDatosTrainTest());


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void evaluarBayesNetResubstitution(){
		try {
			
			System.out.println("** BAYES-NET - RESUBSTITUTION**");

			bayesNet = new BayesNet();
			bayesNet.buildClassifier(DataHolder.getDatosTrainTest());

			Evaluation eval = new Evaluation(DataHolder.getDatosTrainTest());
			eval.evaluateModel(bayesNet, DataHolder.getDatosTrainTest());		
			printResultSet(eval,DataHolder.getDatosTrainTest());
			
			


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void evaluarNaiveCrossFold(){
		try {
			
			System.out.println("** NAÏVE BAYES - 10CROSSFOLD**");

			naiveBayes = new NaiveBayes();
			naiveBayes.buildClassifier(DataHolder.getDatosTrainTest());

			Evaluation eval = new Evaluation(DataHolder.getDatosTrainTest());
			eval.crossValidateModel(naiveBayes, DataHolder.getDatosTrainTest(), 10, new Random(0));		
			printResultSet(eval,DataHolder.getDatosTrainTest());


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void evaluarBayesNetCrossFold(){
		try {
			
			System.out.println("** BAYES-NET - 10CROSSFOLD**");

			bayesNet = new BayesNet();
			bayesNet.buildClassifier(DataHolder.getDatosTrainTest());

			Evaluation eval = new Evaluation(DataHolder.getDatosTrainTest());
			eval.crossValidateModel(bayesNet, DataHolder.getDatosTrainTest(), 10, new Random(0));		
			printResultSet(eval,DataHolder.getDatosTrainTest());


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void evaluarNaiveHoldOut(){
		
		Preprocess.randomize(DataHolder.getDatosTrainTest());
		
		percentageSplit(70);//Viene especificado en la guia
		
		
		Instances trainInstances = new Instances(DataHolder.getDatosTrainTest(), 0, trainSize);
		
		Instances testInstances = new Instances(DataHolder.getDatosTrainTest(), trainSize, testSize);
		
		
		try {
			
			System.out.println("** NAÏVE BAYES - HoldOut**");

			naiveBayes = new NaiveBayes();
			naiveBayes.buildClassifier(trainInstances);

			Evaluation eval = new Evaluation(testInstances);
			eval.evaluateModel(naiveBayes, testInstances);

			printResultSet(eval, DataHolder.getDatosTrainTest());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
public static void evaluarBayesNetHoldOut(){
		
		Preprocess.randomize(DataHolder.getDatosTrainTest());
		
		percentageSplit(70);//Viene especificado en la guia
		
		
		Instances trainInstances = new Instances(DataHolder.getDatosTrainTest(), 0, trainSize);
		
		Instances testInstances = new Instances(DataHolder.getDatosTrainTest(), trainSize, testSize);
		
		
		try {
			
			System.out.println("** BAYES-NET - HoldOut**");

			bayesNet = new BayesNet();
			bayesNet.buildClassifier(trainInstances);

			Evaluation eval = new Evaluation(testInstances);
			eval.evaluateModel(bayesNet, testInstances);
			
			SerializationHelper.write("modelPath", bayesNet);

			printResultSet(eval, DataHolder.getDatosTrainTest());//Modelo resultante en fichero binario

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void percentageSplit(int pPercentage) {
		trainSize = (int) Math.round(DataHolder.getDatosTrainTest().numInstances() * pPercentage / 100);
		testSize = DataHolder.getDatosTrainTest().numInstances() - trainSize;
	}

	
	
	public static void printResultSet(Evaluation pEvaluator,Instances pData) throws Exception {
		
		double acc = pEvaluator.pctCorrect();
		double inc = pEvaluator.pctIncorrect();
		double confMatrix[][] = pEvaluator.confusionMatrix();
		double fMea = pEvaluator.weightedFMeasure();
		double prec = pEvaluator.precision(0);
		double rec = pEvaluator.recall(0);

		System.out.println("*****************************************");
		System.out.println("Correctly Classified Instances  " + acc);
		System.out.println("Incorrectly Classified Instances  " + inc);
		System.out.println("F-Measure  " + fMea);
		System.out.println("Precision  " + prec);
		System.out.println("Recall  " + rec);

		System.out.println("************CONFUSSION MATRIX**************");
		PrecisionRecallCurve.generarCurva(pEvaluator, pData);
		printConfussionMatrix(confMatrix,pData);

	}

	private static void printConfussionMatrix(double confMatrix[][],Instances pData) {

		System.out.print("\n");
		System.out.println("Confusion Matrix");
		for (int i = 0; i < pData.numClasses(); i++) {
			for (int j = 0; j < pData.numClasses(); j++) {
				System.out.print(confMatrix[i][j] + " \t");
			}
			System.out.print("\n");
		}

	}

}
