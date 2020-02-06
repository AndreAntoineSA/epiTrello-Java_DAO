package fr.epita.epitrello.tests;

import fr.epita.epitrello.logger.LoggerFile;

public class TestLogger {

	public static void main(String[] args) {
		
		LoggerFile fileLogger = new LoggerFile();
		fileLogger.log("beginning of the program");

	}

}