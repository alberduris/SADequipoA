package org.traductor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class GenericFilter {

	public static void tweetSentimentFilter(String pPath) {

		String line = null;
		int cont = 0;

		try {

			FileReader fileR = new FileReader(pPath);
			BufferedReader br = new BufferedReader(fileR);
			FileWriter fileW = new FileWriter("output_firstStep_tweet.csv");
			BufferedWriter writer = new BufferedWriter(fileW);

			line = br.readLine();// Skip primera linea
			writer.write(line + "\n");
			line = br.readLine();

			while (line != null) {
				cont++;

				if (line.equalsIgnoreCase("")) {// Si es una linea vacia
												// saltamos
					line = br.readLine();
				}

				if (line.indexOf(',') == -1) { // Si es una linea no valida
												// saltamos
					line = br.readLine();
				} else {

					String mensaje = line.substring(line.lastIndexOf(",\"") + 1, line.lastIndexOf('"') + 1); // Mensaje
																												// con
																												// comillas
																												// dobles
																												// de
																												// apertura
																												// y
																												// cierre

					if (mensaje.length() > 1) {
						mensaje = mensaje.substring(1, mensaje.length() - 1);// Mensaje
																				// sin
																				// comillas
																				// dobles
																				// de
																				// apertura
																				// y
																				// cierre
						mensaje = mensaje.replace('"', ' '); // Eliminamos el
																// resto de
																// comillas
																// dobles
						mensaje = "\"" + mensaje + "\""; // Reconstruimos las
															// comillas dobles
						if (line.substring(0, line.lastIndexOf(",\"") + 1).length() > 0)
							writer.write(line.substring(0, line.lastIndexOf(",\"") + 1) + mensaje + "\n");// Si
																											// hay
																											// algo
																											// ademas
																											// de
																											// mensaje
						line = line.substring(line.indexOf(',') + 1, line.length());
					}

				}

				line = br.readLine();
			}

			br.close();
			writer.close();

			System.out.println("N: " + cont);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void smsSpamFilter(String pPath) {

		String line = null;
		int cont = 0;
		int posicionPrimerEspacio = -1;
		
		try {

			FileReader fileR = new FileReader(pPath);
			BufferedReader br = new BufferedReader(fileR);
			FileWriter fileW = new FileWriter("output_firstStep_sms.csv");
			BufferedWriter writer = new BufferedWriter(fileW);

			writer.write("\"class\",\"message\"\n");
			line = br.readLine();// Skip primera linea
			
			while (line != null) {

				line = line.replace(',', ' ');
				line = line.replace('"',' ');
				posicionPrimerEspacio = line.indexOf('	');
				line = line.substring(0, posicionPrimerEspacio)+",\""+line.substring(posicionPrimerEspacio+1,line.length())+"\"";
				
				writer.write(line+"\n");
				line = br.readLine();
				
				cont++;
			}

			br.close();
			writer.close();

			System.out.println("N: " + cont);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static ArrayList<Integer> indexesOf(String pStr, char pChar) {
		ArrayList<Integer> lista = new ArrayList<Integer>();
		int pos = -1;

		while (pStr.length() > 0) {
			pos = pStr.indexOf(pChar);
			pStr = pStr.substring(pos + 1, pStr.length());
			lista.add(pos);

		}

		return lista;
	}

	public static void main(String[] args) {

		//tweetSentimentFilter("tweetSentiment.train.csv");
		smsSpamFilter("SMS_SpamCollection.train.txt");
	}

}
