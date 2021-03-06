package org.sad.labo4;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.bayes.net.search.local.HillClimber;
import weka.classifiers.bayes.net.search.local.K2;
import weka.classifiers.bayes.net.search.local.SimulatedAnnealing;
import weka.classifiers.bayes.net.search.local.TAN;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.SerializationHelper;

public class Evaluate {

	static NaiveBayes naiveBayes;
	static BayesNet bayesNet;

	static int trainSize;
	static int testSize;
	private static FileWriter writerK2;
	private static FileWriter writerHill;
	private static FileWriter writerTAN;
	private static FileWriter writerSA;

	/*
	 * brief Realiza evaluaci�n mediante resustituci�n aplicando NaiveBayes 
	 * 
	 * note Se aplica con las configuraciones del clasificador standar
	 *  
	 * return void Imprime resultados "DetailedAccuracyByClass"
	 */
	public static void evaluarNaiveResubstitution() {
		try {

			System.out.println("** NA�VE BAYES - RESUBSTITUTION**");

			naiveBayes = new NaiveBayes();
			naiveBayes.buildClassifier(DataHolder.getDatosTrainTest());

			Evaluation eval = new Evaluation(DataHolder.getDatosTrainTest());
			eval.evaluateModel(naiveBayes, DataHolder.getDatosTrainTest());
			
			GetModel.printDetailedAccuracyByClass(eval,"** NA�VE BAYES - RESUBSTITUTION**");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * brief Realiza evaluaci�n mediante resustitucion aplicando BayesNet 
	 * 
	 * note Se aplica con las configuraciones del clasificador standar
	 *  
	 * return void Imprime resultados figuras de m�rito principales
	 */
	public static void evaluarBayesNetResubstitution() {
		try {

			System.out.println("** BAYES-NET - RESUBSTITUTION**");

			bayesNet = new BayesNet();
			bayesNet.buildClassifier(DataHolder.getDatosTrainTest());

			Evaluation eval = new Evaluation(DataHolder.getDatosTrainTest());
			eval.evaluateModel(bayesNet, DataHolder.getDatosTrainTest());
			printResultSet(eval, DataHolder.getDatosTrainTest());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * brief Realiza evaluaci�n mediante CrossFold aplicando NaiveBayes 
	 * 
	 * note Se aplica con las configuraciones del clasificador standar
	 *  
	 * return void Imprime resultados "DetailedAccuracyByClass"
	 */
	public static void evaluarNaiveCrossFold() {
		try {

			System.out.println("** NA�VE BAYES - 10CROSSFOLD**");

			naiveBayes = new NaiveBayes();
			naiveBayes.buildClassifier(DataHolder.getDatosTrainTest());

			Evaluation eval = new Evaluation(DataHolder.getDatosTrainTest());
			eval.crossValidateModel(naiveBayes, DataHolder.getDatosTrainTest(), 10, new Random(0));
			GetModel.printDetailedAccuracyByClass(eval,"** NA�VE BAYES - 10CROSSFOLD**");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * brief Realiza evaluaci�n mediante CrossFold aplicando BayesNet 
	 * 
	 * note Se aplica con las configuraciones del clasificador standar
	 *  
	 * return void Imprime resultados figuras de m�rito principales
	 */
	public static void evaluarBayesNetCrossFold() {
		try {

			System.out.println("** BAYES-NET - 10CROSSFOLD**");

			bayesNet = new BayesNet();
			bayesNet.buildClassifier(DataHolder.getDatosTrainTest());

			Evaluation eval = new Evaluation(DataHolder.getDatosTrainTest());
			eval.crossValidateModel(bayesNet, DataHolder.getDatosTrainTest(), 10, new Random(0));
			printResultSet(eval, DataHolder.getDatosTrainTest());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * brief Realiza evaluaci�n mediante HoldOut aplicando NaiveBayes 
	 * 
	 * note Se aplica con las configuraciones del clasificador standar
	 *  
	 * return void Imprime resultados "DetailedAccuracyByClass"
	 */
	public static void evaluarNaiveHoldOut() {

		Preprocess.randomize(DataHolder.getDatosTrainTest());

		percentageSplit(70);//Viene especificado en la guia

		Instances trainInstances = new Instances(DataHolder.getDatosTrainTest(), 0, trainSize);

		Instances testInstances = new Instances(DataHolder.getDatosTrainTest(), trainSize, testSize);

		try {

			System.out.println("** NA�VE BAYES - HoldOut**");

			naiveBayes = new NaiveBayes();
			naiveBayes.buildClassifier(trainInstances);

			Evaluation eval = new Evaluation(testInstances);
			eval.evaluateModel(naiveBayes, testInstances);

			GetModel.printDetailedAccuracyByClass(eval,"** NA�VE BAYES - HoldOut**");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * brief Realiza evaluaci�n mediante HoldOut aplicando BayesNet 
	 * 
	 * note Se aplica con las configuraciones del clasificador standar
	 *  
	 * return void Imprime resultados figuras de m�rito principales
	 */
	public static void evaluarBayesNetHoldOut() {

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

			SerializationHelper.write("modelPath", bayesNet);//Modelo resultante en fichero binario

			printResultSet(eval, DataHolder.getDatosTrainTest());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * brief Realiza barrido de par�metros principales de K2 para BayesNet
	 *  
	 * return void Genera un fichero con los resultados
	 */
	public static void barridoParametrosK2() {

		System.out.println("BARRIDO PARAMETROS K2 BAYES-NET");

		//Preparacion HoldOut
		Preprocess.randomize(DataHolder.getDatosTrainTest());
		percentageSplit(70);//Viene especificado en la guia
		Instances trainInstances = new Instances(DataHolder.getDatosTrainTest(), 0, trainSize);
		Instances testInstances = new Instances(DataHolder.getDatosTrainTest(), trainSize, testSize);
		trainInstances.setClassIndex(DataHolder.getClassIndex(trainInstances));
		testInstances.setClassIndex(DataHolder.getClassIndex(testInstances));

		//K2 params binarios o nominales maximizados --> Barrido en params num
		K2 k2 = new K2();
		k2.setInitAsNaiveBayes(false);
		k2.setMarkovBlanketClassifier(true);
		k2.setRandomOrder(true);
		k2.setScoreType(new SelectedTag(0, K2.TAGS_SCORE_TYPE));

		//HillClimber params binarios o nominales maximizados --> Barrido en params num
		HillClimber hillClimber = new HillClimber();
		hillClimber.setInitAsNaiveBayes(false);
		hillClimber.setMarkovBlanketClassifier(true);

		try {

			writerK2 = new FileWriter("/Barridos/Barrido_K2.csv", true);
			writerK2.write("BARRIDO_K2\n");
			writerK2.write("maxNumberOfParents,F-Measure\n");
			System.out.println("BARRIDO_K2");
			System.out.println("maxNumberOfParents,F-Measure");
			writerK2.close();

			//Barrido numOfParents K2
			for (int i = 4; i <= 10; i++) {
				writerK2 = new FileWriter("/Barridos/Barrido_K2.csv", true);
				k2.setMaxNrOfParents(i);
				bayesNet = new BayesNet();
				bayesNet.setSearchAlgorithm(k2);
				bayesNet.buildClassifier(trainInstances);

				Evaluation eval = new Evaluation(testInstances);
				eval.evaluateModel(bayesNet, testInstances);

				double fMea = eval.weightedFMeasure();
				writerK2.write(i + "," + fMea + "\n");
				System.out.println(i + "," + fMea);
				writerK2.close();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * brief Realiza barrido de par�metros principales de HillClimbing para BayesNet
	 *  
	 * return void Genera un fichero con los resultados
	 */
	public static void barridoParametrosHillClimbing() {

		System.out.println("BARRIDO PARAMETROS HILLCLIMBING BAYES-NET");

		//Preparacion HoldOut
		Preprocess.randomize(DataHolder.getDatosTrainTest());
		percentageSplit(70);//Viene especificado en la guia
		Instances trainInstances = new Instances(DataHolder.getDatosTrainTest(), 0, trainSize);
		Instances testInstances = new Instances(DataHolder.getDatosTrainTest(), trainSize, testSize);
		trainInstances.setClassIndex(DataHolder.getClassIndex(trainInstances));
		testInstances.setClassIndex(DataHolder.getClassIndex(testInstances));

		//HillClimber params binarios o nominales maximizados --> Barrido en params num
		HillClimber hillClimber = new HillClimber();
		hillClimber.setInitAsNaiveBayes(false);
		hillClimber.setMarkovBlanketClassifier(true);

		try {

			writerHill = new FileWriter("/Barridos/Barrido_HillClimber.csv");
			writerHill.write("BARRIDO_HillClimber\n");
			writerHill.write("maxNumberOfParents,F-Measure\n");
			System.out.println("BARRIDO_Barrido_HillClimber");
			System.out.println("maxNumberOfParents,F-Measure");

			//Barrido numOfParents HillClimber
			for (int i = 1; i <= 100; i++) {
				writerHill = new FileWriter("/Barridos/Barrido_HillClimbing.csv", true);
				hillClimber.setMaxNrOfParents(i);
				bayesNet = new BayesNet();
				bayesNet.setSearchAlgorithm(hillClimber);
				bayesNet.buildClassifier(trainInstances);

				Evaluation eval = new Evaluation(testInstances);
				eval.evaluateModel(bayesNet, testInstances);

				double fMea = eval.weightedFMeasure();
				writerHill.write(i + "," + fMea + "\n");
				System.out.println(i + "," + fMea);
				writerHill.close();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * brief Realiza barrido de par�metros principales de TAN para BayesNet
	 *  
	 * return void Genera un fichero con los resultados
	 */
	public static void barridoParametrosTAN() {

		System.out.println("BARRIDO PARAMETROS TAN BAYES-NET");

		//Preparacion HoldOut
		Preprocess.randomize(DataHolder.getDatosTrainTest());
		percentageSplit(70);//Viene especificado en la guia
		Instances trainInstances = new Instances(DataHolder.getDatosTrainTest(), 0, trainSize);
		Instances testInstances = new Instances(DataHolder.getDatosTrainTest(), trainSize, testSize);
		trainInstances.setClassIndex(DataHolder.getClassIndex(trainInstances));
		testInstances.setClassIndex(DataHolder.getClassIndex(testInstances));

		//TAN
		TAN tan = new TAN();

		try {

			writerTAN = new FileWriter("Barrido_TAN.csv");
			writerTAN.write("BARRIDO_TAN\n");
			writerTAN.write("markovBlanketClassifier,F-Measure\n");
			System.out.println("BARRIDO_TAN");
			System.out.println("markovBlanketClassifier,F-Measure");

			//Markov false
			tan.setMarkovBlanketClassifier(false);
			bayesNet = new BayesNet();
			bayesNet.setSearchAlgorithm(tan);
			bayesNet.buildClassifier(trainInstances);

			Evaluation eval = new Evaluation(testInstances);
			eval.evaluateModel(bayesNet, testInstances);

			double fMea = eval.weightedFMeasure();
			writerTAN.write(tan.getMarkovBlanketClassifier() + "," + fMea + "\n");

			//Markov false
			tan.setMarkovBlanketClassifier(true);
			bayesNet = new BayesNet();
			bayesNet.setSearchAlgorithm(tan);
			bayesNet.buildClassifier(trainInstances);

			eval = new Evaluation(testInstances);
			eval.evaluateModel(bayesNet, testInstances);

			fMea = eval.weightedFMeasure();
			System.out.println(tan.getMarkovBlanketClassifier() + "," + fMea);
			writerTAN.write(tan.getMarkovBlanketClassifier() + "," + fMea + "\n");
			writerTAN.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * brief Realiza barrido de par�metros principales de SimulatedAnnealing para BayesNet
	 *  
	 * return void Genera un fichero con los resultados
	 */
	public static void barridoParametrosSimulatedAnnealing() {

		System.out.println("BARRIDO PARAMETROS SIMULATED ANNEALING BAYES-NET");

		//Preparacion HoldOut
		Preprocess.randomize(DataHolder.getDatosTrainTest());
		percentageSplit(70);//Viene especificado en la guia
		Instances trainInstances = new Instances(DataHolder.getDatosTrainTest(), 0, trainSize);
		Instances testInstances = new Instances(DataHolder.getDatosTrainTest(), trainSize, testSize);
		trainInstances.setClassIndex(DataHolder.getClassIndex(trainInstances));
		testInstances.setClassIndex(DataHolder.getClassIndex(testInstances));

		//SA
		SimulatedAnnealing sa = new SimulatedAnnealing();

		try {

			writerSA = new FileWriter("Barrido_SimulatedAnnealing.csv");
			writerSA.write("BARRIDO_SIMULATED_ANNEALING\n");
			writerSA.write("runs,F-Measure\n");
			System.out.println("BARRIDO_SIMULATED_ANNEALING");
			System.out.println("runs,F-Measure");
			writerSA.close();

			//SA --> Barrido en runs
			sa.setTStart(0);
			sa.setDelta(0.0001);
			sa.setMarkovBlanketClassifier(true);
			

			for (int i = 1; i <= 10000; i++) {
				writerSA = new FileWriter("Barrido_SimulatedAnnealing.csv", true);
				sa.setRuns(i);
				bayesNet = new BayesNet();
				bayesNet.setSearchAlgorithm(sa);
				bayesNet.buildClassifier(trainInstances);

				Evaluation eval = new Evaluation(testInstances);
				eval.evaluateModel(bayesNet, testInstances);

				double fMea = eval.weightedFMeasure();
				writerSA.write(i + "," + fMea + "\n");
				System.out.println(i + "," + fMea);
				writerSA.close();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/*
	 * brief Divide las instancias {Train U Test} en dos partes seg�n el porcentaje establecido
	 *  
	 * params pPercentage El porcentaje para train
	 *  
	 * return void Asigna los tama�os de Train y Test
	 */
	public static void percentageSplit(int pPercentage) {
		trainSize = (int) Math.round(DataHolder.getDatosTrainTest().numInstances() * pPercentage / 100);
		testSize = DataHolder.getDatosTrainTest().numInstances() - trainSize;
	}

	/*
	 * brief Imprime resultados con varias figuras de merito
	 *  
	 * params pEvaluator El evaluador a imprimir. pData Las instancias evaluadas
	 * 
	 * use printConfussionMatrix
	 *  
	 * return void 
	 */
	public static void printResultSet(Evaluation pEvaluator, Instances pData) throws Exception {

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
		printConfussionMatrix(confMatrix, pData);

	}

	/*
	 * brief Imprime la matriz de confusi�n
	 *  
	 * params confMatrix[][] Matriz de double representando la matriz de confusi�n. pData Las instancias evaluadas
	 *  
	 * return void 
	 */
	private static void printConfussionMatrix(double confMatrix[][], Instances pData) {

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
