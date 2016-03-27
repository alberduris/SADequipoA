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

	public static void evaluarNaiveResubstitution() {
		try {

			System.out.println("** NAÏVE BAYES - RESUBSTITUTION**");

			naiveBayes = new NaiveBayes();
			naiveBayes.buildClassifier(DataHolder.getDatosTrainTest());

			Evaluation eval = new Evaluation(DataHolder.getDatosTrainTest());
			eval.evaluateModel(naiveBayes, DataHolder.getDatosTrainTest());
			printResultSet(eval, DataHolder.getDatosTrainTest());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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

	public static void evaluarNaiveCrossFold() {
		try {

			System.out.println("** NAÏVE BAYES - 10CROSSFOLD**");

			naiveBayes = new NaiveBayes();
			naiveBayes.buildClassifier(DataHolder.getDatosTrainTest());

			Evaluation eval = new Evaluation(DataHolder.getDatosTrainTest());
			eval.crossValidateModel(naiveBayes, DataHolder.getDatosTrainTest(), 10, new Random(0));
			printResultSet(eval, DataHolder.getDatosTrainTest());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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

	public static void evaluarNaiveHoldOut() {

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

			writerK2 = new FileWriter("Barrido_K2.csv", true);
			writerK2.write("BARRIDO_K2\n");
			writerK2.write("maxNumberOfParents,F-Measure\n");
			System.out.println("BARRIDO_K2");
			System.out.println("maxNumberOfParents,F-Measure");
			writerK2.close();

			//Barrido numOfParents K2
			for (int i = 4; i <= 10; i++) {
				writerK2 = new FileWriter("Barrido_K2.csv", true);
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

			writerHill = new FileWriter("Barrido_HillClimber.csv");
			writerHill.write("BARRIDO_HillClimber\n");
			writerHill.write("maxNumberOfParents,F-Measure\n");
			System.out.println("BARRIDO_Barrido_HillClimber");
			System.out.println("maxNumberOfParents,F-Measure");

			//Barrido numOfParents HillClimber
			for (int i = 1; i <= 100; i++) {
				writerHill = new FileWriter("Barrido_HillClimbing.csv", true);
				hillClimber.setMaxNrOfParents(i);
				bayesNet = new BayesNet();
				bayesNet.setSearchAlgorithm(hillClimber);
				bayesNet.buildClassifier(trainInstances);

				Evaluation eval = new Evaluation(testInstances);
				eval.evaluateModel(bayesNet, testInstances);

				double fMea = eval.weightedFMeasure();
				writerHill.write(i + "," + fMea + "\n");
				System.out.println(i + "," + fMea);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

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
			writerTAN.write(tan.getMarkovBlanketClassifier() + "," + fMea + "\n");
			writerTAN.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

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

	public static void percentageSplit(int pPercentage) {
		trainSize = (int) Math.round(DataHolder.getDatosTrainTest().numInstances() * pPercentage / 100);
		testSize = DataHolder.getDatosTrainTest().numInstances() - trainSize;
	}

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
		PrecisionRecallCurve.generarCurva(pEvaluator, pData);
		printConfussionMatrix(confMatrix, pData);

	}

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
