package org.traductor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class GenericFilter {

	public static void tweetSentimentFilter(String pPath) {

		String line = null;
		int cont = 0;

		try {

			FileReader fileR = new FileReader(pPath);
			BufferedReader br = new BufferedReader(fileR);
			FileWriter fileW = new FileWriter("output_auxiliar_tweet.csv");
			BufferedWriter writer = new BufferedWriter(fileW);

			writer.write("\"topicazo\",\"classs\",\"TweetId\",\"TweetDate\",\"TweetText\"\n");
			writer.write("\"?\",\"positive\",\"?\",\"?\",\"?\"\n");
			writer.write("\"?\",\"negative\",\"?\",\"?\",\"?\"\n");
			writer.write("\"?\",\"neutral\",\"?\",\"?\",\"?\"\n");

			
			line = br.readLine();// Skip primera linea
			line = br.readLine();

			while (line != null) {
				cont++;
				
				if (line.equalsIgnoreCase("")) {// Si es una linea vacia
												// saltamos
					line = br.readLine();
				}

				else if (line.indexOf(',') == -1 || line.indexOf(',') == 1) { // Si es una linea no valida saltamos
					line = br.readLine();
				}
				else if (line.indexOf('"') != 0) {// Si una linea no empieza por
												// comillas dobles saltamos
					line = br.readLine();

				}
				else if(line.contains("\"\"\"\"\"\"")){ //Linea sin info saltamos
					line = br.readLine();
				}
				else {
					
					String clase = line.substring(line.indexOf(',')+2, line.length());
					clase = clase.substring(0, clase.indexOf(',')-1);
					
					if (clase.equalsIgnoreCase("irrelevant")){
						line = line.replace("irrelevant", "neutral");
					}

					String mensaje = line.substring(line.lastIndexOf("\",\"") + 1, line.lastIndexOf('"') + 1); // Mensaje
					// con
					// comillas
					// dobles
					// de
					// apertura
					// y
					// cierre

					if (mensaje.length() > 2) {
						mensaje = mensaje.substring(2, mensaje.length() - 1);// Mensaje

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

						mensaje = mensaje.replace(',', ' ');// Reemplazamos
															// comas por
															// espacios

						// Caracteres basura (No impiden la generacion pero son
						// basura)
						mensaje = mensaje.replace('&', ' ');
						mensaje = mensaje.replace(';', ' ');
						mensaje = mensaje.replace('/', ' ');
						mensaje = mensaje.replace(':', ' ');
						mensaje = mensaje.replaceAll("[^a-zA-Z0-9]"," ");
						mensaje = mensaje.trim().replaceAll(" +", " ");
						if(mensaje.startsWith(" ")) mensaje = mensaje.substring(1,mensaje.length());
						if(mensaje.endsWith(" ")) mensaje = mensaje.substring(0,mensaje.length()-1);

						mensaje = "\"" + mensaje + "\""; // Reconstruimos las
						// comillas dobles
						if (line.substring(0, line.lastIndexOf("\",\"") + 1).length() > 0)
							line = line.replace("UNKNOWN", "?");
						writer.write(line.substring(0, line.lastIndexOf("\",\"") + 1) + "," + mensaje + "\n");// Si
						// hay
						// algo
						// ademas
						// de
						// mensaje
						line = line.substring(line.indexOf(',') + 1, line.length());
					}

					line = br.readLine();
				}

			}

			br.close();
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void smsSpamFilter(String pPath) {

		String line = null;
		//int cont = 0;
		int posicionPrimerEspacio = -1;
		boolean setNominalValues = false;

		try {

			FileReader fileR = new FileReader(pPath);
			BufferedReader br = new BufferedReader(fileR);
			FileWriter fileW = new FileWriter("output_auxiliar_sms.csv");
			BufferedWriter writer = new BufferedWriter(fileW);

			writer.write("\"class\",\"message\"\n");
			
			line = br.readLine();
			while (line != null) {

				line = line.replace(',', ' ');
				line = line.replace('"', ' ');
				posicionPrimerEspacio = line.indexOf('	');
				if (posicionPrimerEspacio == -1) {// Unknown class
					if(!setNominalValues){
						writer.write("\"ham\",\"?\"\n");
						writer.write("\"spam\",\"?\"\n");
						setNominalValues = true;
					}
					
					line = line.replace('\'', ' ');
					writer.write("?,\""+line+"\"\n");
					line = br.readLine();

				} else {

					
					line = line.substring(0, posicionPrimerEspacio) + ",\""
							+ line.substring(posicionPrimerEspacio + 1, line.length()) + "\"";
										
					writer.write(line + "\n");
					line = br.readLine();
				}

				//cont++;
			}

			br.close();
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

	}

}
