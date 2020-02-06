package fr.epita.epitrello.tests;

import fr.epita.epitrello.dao.Configuration;

public class TestConfigurationDAO {

	public static void main(String[] args) {

		String value = Configuration.getValueFromKey("user");

		boolean success = value.equals("test");
		System.out.println("success? " + success);
		if (!success) {
			System.out.println("read value: " + value);
		}
	}

}
