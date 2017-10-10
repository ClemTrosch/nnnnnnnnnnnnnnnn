package ch02_profbuild;

import java.io.File;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * Besipiel einer einfachen Untersuchung eines JARs
 * 
 * @author Michael Inden
 * 
 * Copyright 2014 by Michael Inden 
 */
public class InspectingJarExample 
{
	public static void main(final String[] args) throws IOException 
	{		
		final String jarFileName = "src/main/resources/ch02_profbuild/gradle-example.jar";
		final File file = new File(jarFileName);
		System.out.println(file.getAbsolutePath());
		System.out.println("jarFileName='" + jarFileName + "'");

		// Informationsobjekte ermitteln  
		final JarFile jarfile = new JarFile(jarFileName);
		final Manifest manifest = jarfile.getManifest();
		final Attributes attributes = manifest.getMainAttributes();

		// Informationen auslesen  
		final String mainClassName = attributes.getValue(Attributes.Name.MAIN_CLASS);
		System.out.println("mainClassName='" + mainClassName + "'");

		final String createdBy = attributes.getValue("Created-By");
		System.out.println("createdBy='" + createdBy + "'");

		final String manifestVersion = attributes.getValue(Attributes.Name.MANIFEST_VERSION);
		System.out.println("manifestVersion='" + manifestVersion + "'");
	}
}