package org.sad.labo4;

import java.io.File;

import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;

public class FSSInfoGain {

	public static void main(String[] args) {
		
		try {
			System.out.println("Eliminando atributos redundantes e irrelevantes mediante InfoGain...");
			infoGain(DataHolder.loadData(args[0]), args[1]);
			System.out.println("Proceso completado");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Ops! Algo ha ido mal...");
		}

	}

	private static void infoGain(Instances pData, String pName) {
		try {
			weka.filters.supervised.attribute.AttributeSelection filter = new weka.filters.supervised.attribute.AttributeSelection();

			pData.setClassIndex(DataHolder.getClassIndex(pData));
			
			InfoGainAttributeEval infoGain = new InfoGainAttributeEval();
			infoGain.setMissingMerge(true);
			infoGain.setBinarizeNumericAttributes(false);
			Ranker search = new Ranker();
			search.setThreshold(0);
			search.setNumToSelect(-1);

			filter.setEvaluator(infoGain);
			filter.setSearch(search);
			filter.setInputFormat(pData);

			//Guardar todos los nuevos datos con batch filtering
			Instances datosInfoGained = Filter.useFilter(pData, filter);
			

			ArffSaver saver = new ArffSaver();
			saver.setInstances(datosInfoGained);
			saver.setFile(new File(pName));
			saver.writeBatch();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
