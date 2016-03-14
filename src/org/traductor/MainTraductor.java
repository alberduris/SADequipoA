package org.traductor;

public class MainTraductor {

	public static void main(String[] args) {

		System.out.println("Generando archivo arff a partir de raw data...");

		if (args[0].trim().equalsIgnoreCase("t")) {// tweet

			GenericFilter.tweetSentimentFilter(args[1]);
			Traductor.traduceTweet(args[2]);
			System.out.println("Arff generado");

		} else if (args[0].trim().equalsIgnoreCase("s")) {// sms

			GenericFilter.smsSpamFilter(args[1]);
			Traductor.traduceSMS(args[2]);
			System.out.println("Arff generado");

		} else {
			System.out.println("Error en los parámetros");
		}

	}

}
