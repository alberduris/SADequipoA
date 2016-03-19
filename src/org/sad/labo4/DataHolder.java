package org.sad.labo4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public class DataHolder {

	private static BufferedReader reader;

	private static Instances datosTrain;
	private static Instances datosTest;
	private static Instances datosBlind;
	private static Instances datosTrainTest;

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
	 * @brief Carga los datos de un archivo arff
	 * 
	 * @param String pPath El path completo del archivo arff
	 * 
	 * @return void Se guardan los datos en el objeto datosTrainTest de tipo Instances
	 */
	public static void loadTrainTestData() {
		
		datosTrainTest = datosTrain;
		Instance instancia;
		
		for(int i = 0; i < datosTest.numInstances(); i++){
			instancia = datosTest.instance(i);
			datosTrainTest.add(instancia);
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
	 * brief Devuelve los datosTrainTest almacenados
	 * 
	 * return datos Los datosTrainTest almacenados
	 */
	public static Instances getDatosTrainTest() {
		return datosTrainTest;
	}
	

	/*
	 * brief Asigna los datos pasados por parámetro a los datosTrain
	 * 
	 * param pData Los datos a asignar
	 * 
	 * return void Los datos estan asignados a datosTrain
	 */
	public static void setDatosTrain(Instances pData) {
		datosTrain = pData;
		String atributo = "";
		int attIndex = -1;
		boolean fin = false;
		for(int i = 0; i < datosTrain.numAttributes() && !fin; i++){
			atributo = datosTrain.instance(0).attribute(i).name();
			if(atributo.equals("classs")){
				attIndex = i;
				fin = true;
			}
		}
		datosTrain.setClassIndex(attIndex);
	
		}
	
	/*
	 * brief Asigna los datos pasados por parámetro a los datosTest
	 * 
	 * param pData Los datos a asignar
	 * 
	 * return void Los datos estan asignados a datosTest
	 */
	public static void setDatosTest(Instances pData) {
		datosTest = pData;
	}
	
	/*
	 * brief Asigna los datos pasados por parámetro a los datosBlind
	 * 
	 * param pData Los datos a asignar
	 * 
	 * return void Los datos estan asignados a datosBlind
	 */
	public static void setDatosBlind(Instances pData) {
		datosBlind = pData;
	}
	
	
	
	/*
	 * brief Asigna los datos pasados por parámetro a los datosTrainTest
	 * 
	 * param pData Los datos a asignar
	 * 
	 * return void Los datos estan asignados a datosTrainTest
	 */
	public static void setDatosTrainTest(Instances pData) {
		datosTrainTest = pData;
		String atributo = "";
		int attIndex = -1;
		boolean fin = false;
		for(int i = 0; i < datosTrainTest.numAttributes() && !fin; i++){
			atributo = datosTrainTest.instance(0).attribute(i).name();
			if(atributo.equals("classs")){
				attIndex = i;
				fin = true;
			}
		}
		datosTrainTest.setClassIndex(attIndex);
	
		}
	
	public static void printDatos(Instances pData){
		int cont = 0;
		for(int i = 0; i < pData.numInstances(); i++){
			System.out.println(pData.instance(i));
			cont++;
		}
		System.out.println("El set de datos tiene "+cont+" instancias.");
	}
	
	public static void printAttributos(Instances pData){
		int cont = 0;
		for(int i = 0; i < pData.numAttributes(); i++){
			System.out.println(pData.attribute(i));
			cont++;
		}
		System.out.println("El set de datos tiene "+cont+" atributos.");

		
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
