package com.topics.map.properties;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

/**
 * The db.properties object contains key and value pair both as a string. It is the subclass of Hashtable.
 * 
 * It can be used to get property value based on the property key.
 * The Properties class provides methods to get data from db.properties file and store data into db.properties file.
 * Moreover, it can be used to get db.properties of system.
 */
public class PropertiesExample
{
	public static void main(String[] args) throws IOException  {
	    System.out.println("Properties from file : ");
		getPropertiesFromFile();
		System.out.println("System properties : ");
		getSystemProperties();
	}

	private static void getPropertiesFromFile() throws IOException {
		FileReader fileReader=new FileReader(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("jdbc/db.properties")).getFile());
	      
	    Properties properties=new Properties();  
	    properties.load(fileReader);  
	      
	    System.out.println(properties.getProperty("user"));  
	    System.out.println(properties.getProperty("password"));  
	}  
	
	private static void getSystemProperties() {
		Properties properties=System.getProperties();  
		Set<Entry<Object, Object>> set = properties.entrySet();

		for (Entry<Object, Object> entry : set) {
			System.out.println(entry.getKey() + " = " + entry.getValue());
		}  
	}
}
