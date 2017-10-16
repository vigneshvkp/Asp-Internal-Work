/*
 * Created on Jan 10, 2008
 * 
 * Copyright 2007-2008 Aspire Systems Pvt. Ltd. All rights reserved.
 */

package com.aspire.thi.common;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


/**
 * A utility class to load the resources from classpath and web path.
 * 
 * @author venkat.sadasivam
 */
public final class ResourceUtility {

	private static final Logger logger = Logger.getLogger(ResourceUtility.class);

	private static DocumentBuilder builder = null;

	private ResourceUtility() {
		throw new Error("Contains only static methods");
	}

	static {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException exp) {
			logger.error("Error while creating DocumentBuilder", exp);
		}
	}

	/**
	 * Loads the properties file from the class path, i.e. the properties file
	 * either available in WEB-INF/classes folder or in one of the jar file.
	 * 
	 * @param filePath
	 * @return
	 */
	public static Properties loadPropertiesFromClassPath(final String filePath) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	/**
	 * Loads the xml file from the class path, i.e. the xml file either
	 * available in WEB-INF/classes folder or in one of the jar file.
	 * 
	 * @param filePath
	 * @return
	 */
	public static Document loadXmlFromClassPath(final String filePath) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	/**
	 * Loads the given properties file from the webapp path<br>
	 * For example if the filePath is /WEB-INF/security.properties it finds the
	 * file from WEB-INF folder
	 * 
	 * @param filePath
	 * @return
	 */
	public static Properties loadPropertiesFromWebPath(final String filePath) {
		try {
			URL url = THIContextLoaderListener.getURL(filePath);
			Properties properties = new Properties();
			properties.load(url.openStream());
			return properties;
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Loads the given xml file from the webapp path<br>
	 * For example if the filePath is /WEB-INF/menu.xml it finds the file from
	 * WEB-INF folder
	 * 
	 * @param filePath
	 * @return
	 */
	public static Document loadXmlFromWebPath(final String filePath) {
		try {
			URL url = THIContextLoaderListener.getURL(filePath);
			return builder.parse(url.openStream());
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
