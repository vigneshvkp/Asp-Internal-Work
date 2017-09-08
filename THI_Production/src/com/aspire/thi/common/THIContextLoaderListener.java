package com.aspire.thi.common;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.web.context.ContextLoaderListener;

import com.aspire.thi.utils.EmailManager;

public class THIContextLoaderListener extends ContextLoaderListener {

	private static ServletContext servletContext;
	
	private static final Logger LOGGER = Logger.getLogger(THIContextLoaderListener.class);
	
	private static String qaLeadMail;

	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {

		super.contextInitialized(contextEvent);
		servletContext = contextEvent.getServletContext();
		// Set the email configuration
		try {
			//Configure Log4j 
			URL log4jFile = getURL("/WEB-INF/log4j.properties");
			PropertyConfigurator.configure(log4jFile);
			
			//Configure Email
			URL mailCfgFile = getURL("/WEB-INF/mail.properties");
			Properties emailProps = new Properties();
			emailProps.load(mailCfgFile.openStream());
			EmailManager.getInstance().setEmailProperties(emailProps);
			LOGGER.info("Initialized Mail configuration");
			
			qaLeadMail = (String)emailProps.getProperty("mail.qa.lead","QA@aspiresys.com" );

			//Thread to reconnect db at periodic intervals
			ReConnectDBThread reConnectThread = new ReConnectDBThread();
			Thread t2 = new Thread(reConnectThread);
			t2.setDaemon(true);
			t2.start();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the given properties/resource file location
	 * 
	 * @param filePath
	 * @return
	 * @throws MalformedURLException
	 */
	public static URL getURL(String filePath) throws MalformedURLException {
		URL url = servletContext.getResource(filePath);
		return url;
	}

	public static final String getQALead(){
		return qaLeadMail;
	}
}
