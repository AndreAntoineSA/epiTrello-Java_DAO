package fr.epita.epitrello.logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

	public void log(String message) {

		String timestamp = getTimeStamp();

		String formattedMessage = format(timestamp, message);

		print(formattedMessage);

	}

	protected void print(String formattedMessage) {
		System.out.println(formattedMessage);
	}

	private String format(String timestamp, String message) {
		return timestamp + " -- " + message;
	}

	private String getTimeStamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS");
		String timestamp = sdf.format(new Date());
		return timestamp;
	}

}
