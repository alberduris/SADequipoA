package org.traductor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Filtro {
	
	public Filtro(){
	}
	
	public void filtrar(String pathEntrada, String pathSalida){

		try {
			FileReader fileR = new FileReader(pathEntrada);
			BufferedReader reader = new BufferedReader(fileR);
			FileWriter fileW = new FileWriter(pathSalida);
			BufferedWriter writer = new BufferedWriter(fileW);
			String line = reader.readLine();
			while(line != null){
				line = comprobarDatos(line);
				
				
				
				line = reader.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private String comprobarDatos(String datos){
		/*
		while(itr.hasNext()){
			datosAux.add(corregir(itr.next()));
		}*/
		return null;
	}
	
	private String[] corregir(String[] datos){
		String[] aux;
		return datos;
	}
}
