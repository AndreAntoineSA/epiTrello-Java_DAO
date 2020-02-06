package fr.epita.epitrello.dao;

import java.io.FileInputStream;
import java.util.Properties;

public class Configuration {

	private static String FILENAME="/home/alvaro1553/eclipse-workspace/epiTrello/src/fr/epita/epitrello/dao/config";
	private static Properties confProperties = new Properties();
	private static boolean isInit = false;

	private static void init() {
		try {
			confProperties.load(new FileInputStream(FILENAME));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getValueFromKey(String key) {
		if (!isInit) {
			init();
			isInit = true;
		}
		return confProperties.getProperty(key);
	}

}