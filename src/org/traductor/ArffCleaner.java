package org.traductor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class ArffCleaner {

	static Instances datos;

	public static void loadData() {
		try {
			// load data
			BufferedReader reader = new BufferedReader(new FileReader("output_tweet.arff"));
			datos = new Instances(reader);
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void deleteAllMissingInstances() {
		datos.delete(0);
		datos.delete(0);
		datos.delete(0);
		datos.delete(0);
	}

	public static void selectUsefulAttributes() {
		try {
			Remove remove = new Remove();
			remove.setAttributeIndices("3,4");
			remove.setInputFormat(datos);
			datos = Filter.useFilter(datos, remove);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void printAllInstances() {
		for (int i = 0; i < datos.numInstances(); i++) {
			System.out.println("Instance " + i + ": " + datos.instance(i));
		}
	}

	public static void saveData() {
		try {
			datos.setRelationName("clean_tweet_output");
			ArffSaver saver = new ArffSaver();
			saver.setInstances(datos);
			saver.setFile(new File("clean_tweet_output.arff"));
			saver.writeBatch();
			new File("output_tweet.arff").delete();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void principal() {
		loadData();
		deleteAllMissingInstances();
		selectUsefulAttributes();
		saveData();

	}

	public static void main(String[] args) {

	

	}

}
