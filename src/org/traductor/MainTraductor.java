package org.traductor;

public class MainTraductor {

	public static void main(String[] args) {

		System.out.println("Generando archivo arff a partir de raw data...");


			GenericFilter.tweetSentimentFilter(args[0]);
			Traductor.traduceTweet(args[1]);
			ArffCleaner.principal(args[1]);
			
			
			System.out.println("Arff generado");


	
	}

}
