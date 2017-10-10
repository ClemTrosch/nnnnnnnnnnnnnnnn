package ch02_profbuild;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Besipiel zur Demonstration des Resourcenzugriffs aus einem JAR auf Daten in einem JAR
 * 
 * @author Michael Inden
 * 
 * Copyright 2014 by Michael Inden 
 */
public class ResourceAccessExample 
{
	public static void main(final String[] args) throws IOException 
	{
		final String filename = "images/Bad-Question-Message.png";
		
		// Variante mit ClassLoader (1) und Class (2)  
		final URL imageUrl1 = ResourceAccessExample.class.getClassLoader().
				                                          getResource(filename); // 1  
		final URL imageUrl2 = ResourceAccessExample.class.getResource(filename); // 2  
		System.out.println("imageUrl1: " + imageUrl1);
		System.out.println("imageUrl2: " + imageUrl2);

		// Variante mit InputStream  
		final InputStream is1 = ResourceAccessExample.class.getClassLoader().getResourceAsStream(filename);
		final InputStream is2 = ResourceAccessExample.class.getResourceAsStream(filename);
		System.out.println("is1: " + is1);
		System.out.println("is2: " + is2);	
	}		
}