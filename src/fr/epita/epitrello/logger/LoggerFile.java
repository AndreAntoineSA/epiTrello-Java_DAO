package fr.epita.epitrello.logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class LoggerFile extends Logger {
	
	final static String FILENAME="/home/alvaro1553/epiTrello.log";
	
	@Override
	protected void print(String formattedMessage) {
		File file = new File(FILENAME);
		
		try {
			FileOutputStream out = new FileOutputStream(file, true);
			PrintWriter writer = new PrintWriter(out);
			writer.println(formattedMessage);
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void clear() {
		File file = new File(FILENAME);

		try {
			FileOutputStream out = new FileOutputStream(file, false);
			PrintWriter writer = new PrintWriter(out);
			writer.print("");
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}
