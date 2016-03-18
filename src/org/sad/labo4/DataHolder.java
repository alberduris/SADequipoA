package org.sad.labo4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import weka.core.Instances;

public class DataHolder {

	private static BufferedReader reader;

	private static Instances datosTrain;
	private static Instances datosTest;
	private static Instances datosBlind;

	/*
	 * @brief Carga los datos de un archivo arff
	 * 
	 * @param String pPath El path completo del archivo arff
	 * 
	 * @return void Se guardan los datos en el objeto datosTrain de tipo Instances
	 */
	public static void loadTrainData(String pPath) {
		try {

			reader = new BufferedReader(new FileReader(pPath));
			datosTrain = new Instances(reader);
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/*
	 * @brief Carga los datos de un archivo arff
	 * 
	 * @param String pPath El path completo del archivo arff
	 * 
	 * @return void Se guardan los datos en el objeto datosTest de tipo Instances
	 */
	public static void loadTestData(String pPath) {
		try {

			reader = new BufferedReader(new FileReader(pPath));
			datosTest = new Instances(reader);
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/*
	 * @brief Carga los datos de un archivo arff
	 * 
	 * @param String pPath El path completo del archivo arff
	 * 
	 * @return void Se guardan los datos en el objeto datosBlind de tipo Instances
	 */
	public static void loadBlindData(String pPath) {
		try {

			reader = new BufferedReader(new FileReader(pPath));
			datosBlind = new Instances(reader);
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * brief Devuelve los datosTrain almacenados
	 * 
	 * return datos Los datosTrain almacenados
	 */
	public static Instances getDatosTrain() {
		return datosTrain;
	}
	
	/*
	 * brief Devuelve los datosTest almacenados
	 * 
	 * return datos Los datosTest almacenados
	 */
	public static Instances getDatosTest() {
		return datosTest;
	}
	
	/*
	 * brief Devuelve los datosBlind almacenados
	 * 
	 * return datos Los datosBlind almacenados
	 */
	public static Instances getDatosBlind() {
		return datosBlind;
	}

	/*
	 * brief Asigna los datos pasados por parámetro a los datosTrain
	 * 
	 * param pData Los datos a asignar
	 * 
	 * return void Los datos estan asignados a datosTrain
	 */
	public static void setDatosTrain(Instances pData) {
		pData = datosTrain;
	}
	
	/*
	 * brief Asigna los datos pasados por parámetro a los datosTest
	 * 
	 * param pData Los datos a asignar
	 * 
	 * return void Los datos estan asignados a datosTest
	 */
	public static void setDatosTest(Instances pData) {
		pData = datosTest;
	}
	
	/*
	 * brief Asigna los datos pasados por parámetro a los datosBlind
	 * 
	 * param pData Los datos a asignar
	 * 
	 * return void Los datos estan asignados a datosBlind
	 */
	public static void setDatosBlind(Instances pData) {
		pData = datosBlind;
	}

	/*
	 * @brief Descripcion resumida del metodo Puedes seguir en otra linea
	 * 
	 * Descripcion detallada
	 * @param X
	 * 
	 * @return Lo que devuelve
	 * 
	 * @note Una anotacion
	 * 
	 * @see Referencia a otra funcion
	 */
}
