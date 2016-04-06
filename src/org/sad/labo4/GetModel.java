package org.sad.labo4;

import java.io.File;
import java.io.FileWriter;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.bayes.net.search.local.SimulatedAnnealing;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class GetModel {

	static BayesNet bayesNet;
	static SimulatedAnnealing sa;
	static int trainSize;
	static int testSize;
	private static FileWriter writer;

	/*
	 * brief Realiza evaluación mediante resustitución aplicando BayesNet 
	 * 
	 * note Se aplica con las configuraciones del clasificador tuneado
	 *  
	 * return void Imprime resultados "DetailedAccuracyByClass"
	 */
	public static void bayesNetTunedResubstitution() {
	
		try {
			
			System.out.println("** BAYES-NET - TUNED - RESUBSTITUTION**");

			sa = new SimulatedAnnealing();
			sa.setTStart(0);
			sa.setDelta(0.001);
			sa.setMarkovBlanketClassifier(true);

			bayesNet = new BayesNet();
			bayesNet.buildClassifier(DataHolder.getDatosTrainTest());

			Evaluation eval = new Evaluation(DataHolder.getDatosTrainTest());
			eval.evaluateModel(bayesNet, DataHolder.getDatosTrainTest());

			printDetailedAccuracyByClass(eval,"** BAYES-NET - TUNED - RESUBSTITUTION**");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * brief Realiza evaluación mediante CrossFold aplicando BayesNet 
	 * 
	 * note Se aplica con las configuraciones del clasificador tuneado
	 *  
	 * return void Imprime resultados "DetailedAccuracyByClass"
	 */
	public static void bayesNetTunedCrossFold() {

		try {

			System.out.println("** BAYES-NET - TUNED - 10 CROSS FOLD**");

			sa = new SimulatedAnnealing();
			sa.setTStart(0);
			sa.setDelta(0.001);
			sa.setMarkovBlanketClassifier(true);

			bayesNet = new BayesNet();
			bayesNet.buildClassifier(DataHolder.getDatosTrainTest());

			Evaluation eval = new Evaluation(DataHolder.getDatosTrainTest());
			eval.crossValidateModel(bayesNet, DataHolder.getDatosTrainTest(), 10, new Random(0));

			printDetailedAccuracyByClass(eval,"** BAYES-NET - TUNED - 10 CROSS FOLD**");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * brief Realiza evaluación mediante HoldOut aplicando BayesNet 
	 * 
	 * note Se aplica con las configuraciones del clasificador tuneado
	 *  
	 * return void Imprime resultados "DetailedAccuracyByClass"
	 */
	public static void bayesNetTunedHoldOut() {


		try {

			System.out.println("** BAYES-NET - TUNED - HOLD OUT**");

			sa = new SimulatedAnnealing();
			sa.setTStart(0);
			sa.setDelta(0.001);
			sa.setMarkovBlanketClassifier(true);

			bayesNet = new BayesNet();
			bayesNet.buildClassifier(DataHolder.getDatosTrain());

			Evaluation eval = new Evaluation(DataHolder.getDatosTest());
			eval.evaluateModel(bayesNet, DataHolder.getDatosTest());
			
			saveBinaryModel();

			printDetailedAccuracyByClass(eval,"** BAYES-NET - TUNED - HOLD OUT**");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/*
	 * brief Almacena el modelo binario del clasificador 
	 * 
	 * note Se genera con las configuraciones del clasificador tuneado
	 *  
	 * return void Se genera el archivo binario
	 */
	public static void saveBinaryModel(){
		try {
			SerializationHelper.write("modeloBinarioBayesNet", bayesNet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//Modelo resultante en fichero binario
	}
	
	/*
	 * brief Divide las instancias {Train U Test} en dos partes según el porcentaje establecido
	 *  
	 * params pPercentage El porcentaje para train
	 *  
	 * return void Asigna los tamaños de Train y Test
	 */
	public static void percentageSplit(int pPercentage) {
		trainSize = (int) Math.round(DataHolder.getDatosTrainTest().numInstances() * pPercentage / 100);
		testSize = DataHolder.getDatosTrainTest().numInstances() - trainSize;
	}
	

	/*
	 * brief Imprime resultados "DetailedAccuracyByClass"
	 *  
	 * params pEvaluator El evaluador a imprimir. pTitle El titulo para formatear el archivo y que sea inteligible
	 *  
	 * return void 
	 */
	public static void printDetailedAccuracyByClass(Evaluation pEval,String pTitle) {

		try {
			File file = new File("Resultados\\Infered_Model_Results.csv");
			file.getParentFile().mkdir();
			file.createNewFile();
			writer = new FileWriter(file,true);
			
			writer.write(pTitle+"\n");
			writer.write("--- Detailed Accuracy By Class ---\n\n");
			System.out.println("=== Detailed Accuracy By Class ===\n");
			
			writer.write("TP Rate,FP Rate,Precision,Recall,F-Measure,ROC Area,Class\n");
			System.out.println("TP Rate,FP Rate,Precision,Recall,F-Measure,ROC Area,Class");
			
			double TPR0 = pEval.truePositiveRate(0);
			double TPR1 = pEval.truePositiveRate(1);
			double TPR2 = pEval.truePositiveRate(2);
			double watpr = pEval.weightedTruePositiveRate();

			double FPR0 = pEval.falsePositiveRate(0);
			double FPR1 = pEval.falsePositiveRate(1);
			double FPR2 = pEval.falsePositiveRate(2);
			double wafpr = pEval.weightedFalsePositiveRate();

			double prec0 = pEval.precision(0);
			double prec1 = pEval.precision(1);
			double prec2 = pEval.precision(2);
			double waprec = pEval.weightedPrecision();

			double recall0 = pEval.recall(0);
			double recall1 = pEval.recall(1);
			double recall2 = pEval.recall(2);
			double warecall = pEval.weightedRecall();

			double fMeasure0 = pEval.fMeasure(0);
			double fMeasure1 = pEval.fMeasure(1);
			double fMeasure2 = pEval.fMeasure(2);
			double wafmeasure = pEval.weightedFMeasure();

			double rocArea0 = pEval.areaUnderROC(0);
			double rocArea1 = pEval.areaUnderROC(1);
			double rocArea2 = pEval.areaUnderROC(2);
			double waroc = pEval.weightedAreaUnderROC();
			
			writer.write(TPR0+","+FPR0+","+prec0+","+recall0+","+fMeasure0+","+rocArea0+",positive"+"\n");
			System.out.println(TPR0+","+FPR0+","+prec0+","+recall0+","+fMeasure0+","+rocArea0);
			
			writer.write(TPR1+","+FPR1+","+prec1+","+recall1+","+fMeasure1+","+rocArea1+",negative\n");
			System.out.println(TPR1+","+FPR1+","+prec1+","+recall1+","+fMeasure1+","+rocArea1);
			
			writer.write(TPR2+","+FPR2+","+prec2+","+recall2+","+fMeasure2+","+rocArea2+",neutral\n");
			System.out.println(TPR2+","+FPR2+","+prec2+","+recall2+","+fMeasure2+","+rocArea2);
			
			writer.write(watpr+","+wafpr+","+waprec+","+warecall+","+wafmeasure+","+waroc+", <- Weighted Avg.\n\n\n");
			System.out.println(watpr+","+wafpr+","+waprec+","+warecall+","+wafmeasure+","+waroc+" <- Weighted Avg.+\n\n");
			
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		
		System.out.println("Cargando datos...");
		//Cargar datos
		DataHolder.loadTrainData(args[0]);
		DataHolder.loadTestData(args[1]);
		DataHolder.loadTrainTestData();
		System.out.println("Datos cargados");

		System.out.println("Preprocesando datos...");
		//Preprocesado
		Preprocess.stringToWordVector();
		Preprocess.infoGain();
		System.out.println("Datos preprocesados");
		
		//Resubstitution
		Evaluate.evaluarNaiveResubstitution();
		bayesNetTunedResubstitution();
	
		//CrossFold
		Evaluate.evaluarNaiveCrossFold();
		bayesNetTunedCrossFold();
	
		//HoldOut
		Evaluate.evaluarNaiveHoldOut();
		bayesNetTunedHoldOut();

	}

}
