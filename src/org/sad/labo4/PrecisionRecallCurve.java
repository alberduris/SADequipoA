package org.sad.labo4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import javax.swing.JFrame;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.ThresholdCurve;
import weka.core.Instances;
import weka.gui.visualize.JComponentWriter;
import weka.gui.visualize.JPEGWriter;
import weka.gui.visualize.PlotData2D;
import weka.gui.visualize.ThresholdVisualizePanel;

/**
 * Generates and saves a precision-recall curve. Uses a cross-validation with
 * NaiveBayes to make the curve.
 *
 * @author FracPete
 * @author Eibe Frank
 * @modified by Equipo A
 */
public class PrecisionRecallCurve {
	
	static int cont = 1;
	

	/**
	 * takes two arguments: dataset in ARFF format (expects class to be last
	 * attribute) and name of file with output
	 */

	public static void generarCurva(Evaluation pEval, Instances pData) {

		
			// generate curve
			ThresholdCurve tc = new ThresholdCurve();

			//Calculate classIndex
			int classIndex = 0;
			
			Instances result = tc.getCurve(pEval.predictions(),classIndex);

			// plot curve
			ThresholdVisualizePanel vmc = new ThresholdVisualizePanel();
			PlotData2D tempd = new PlotData2D(result);

			// specify which points are connected
			boolean[] cp = new boolean[result.numInstances()];
			for (int n = 1; n < cp.length; n++)
				cp[n] = true;
			try {
				tempd.setConnectPoints(cp);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// add plot
			try {
				vmc.addPlot(tempd);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// We want a precision-recall curve
			try {
				vmc.setXIndex(result.attribute("Recall").index());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				vmc.setYIndex(result.attribute("Precision").index());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// Make window with plot but don't show it
			JFrame jf = new JFrame();
			jf.setSize(500, 400);
			jf.getContentPane().add(vmc);
			jf.pack();

			// Save to file specified as second argument (can use any of
			// BMPWriter, JPEGWriter, PNGWriter, PostscriptWriter for different formats)
			JComponentWriter jcw = new JPEGWriter(vmc.getPlotPanel(), new File("PrecisionRecallCurve_"+(cont++)+".jpg"));
			try {
				jcw.toOutput();
				jf.dispose();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		

	}

	public static void main(String[] args) throws Exception {

		// load data
		Instances data = new Instances(new BufferedReader(new FileReader(args[0])));
		data.setClassIndex(data.numAttributes() - 1);

		// train classifier
		Classifier cl = new NaiveBayes();
		Evaluation eval = new Evaluation(data);
		eval.crossValidateModel(cl, data, 10, new Random(1));

		// generate curve
		ThresholdCurve tc = new ThresholdCurve();
		int classIndex = 0;
		Instances result = tc.getCurve(eval.predictions(), classIndex);

		// plot curve
		ThresholdVisualizePanel vmc = new ThresholdVisualizePanel();
		PlotData2D tempd = new PlotData2D(result);

		// specify which points are connected
		boolean[] cp = new boolean[result.numInstances()];
		for (int n = 1; n < cp.length; n++)
			cp[n] = true;
		tempd.setConnectPoints(cp);
		// add plot
		vmc.addPlot(tempd);

		// We want a precision-recall curve
		vmc.setXIndex(result.attribute("Recall").index());
		vmc.setYIndex(result.attribute("Precision").index());

		// Make window with plot but don't show it
		JFrame jf = new JFrame();
		jf.setSize(500, 400);
		jf.getContentPane().add(vmc);
		jf.pack();

		// Save to file specified as second argument (can use any of
		// BMPWriter, JPEGWriter, PNGWriter, PostscriptWriter for different formats)
		JComponentWriter jcw = new JPEGWriter(vmc.getPlotPanel(), new File(args[1]));
		jcw.toOutput();
		System.exit(1);
	}
}
