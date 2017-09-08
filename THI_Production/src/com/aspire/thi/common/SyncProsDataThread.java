package com.aspire.thi.common;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 *
 * A Simple thread Class to do the Pros Synchronization at pre-defined time.
 * 
 * @author muthu.velappan
 */
public class SyncProsDataThread  implements Runnable {

	private static final Logger LOGGER = Logger
			.getLogger(SyncProsDataThread.class);

	//MillSeconds * Seconds * Minutes * Hour * Day
	private static final long SLEEP_TIME = 1000 * 60 * 60 * 24 * 1; // 1 Day; 

	@Override
	public void run() {
		LOGGER.info("Time out period is " + SLEEP_TIME / (60 * 60 * 1000.0) 
				+ " hour");
		while (true) {
			try {
				Thread.sleep(SLEEP_TIME);
				boolean success = refreshProsData();
				if (success) {
					LOGGER.info("Projects & Customers are successfullt refreshed at " + new Date());
				}
			} catch (InterruptedException e) {
				LOGGER.info("Thread interruption", e);
			}
		}
	}

	private boolean refreshProsData() {
		try {
			URL url = new URL("http://localhost:8080/THI/syncProsDataThread.htm");
			URLConnection urlConnection = url.openConnection();
			InputStream ipStream = urlConnection.getInputStream();
			System.out.println(ipStream.read(new byte[1024]));
		} catch (MalformedURLException exe) {
			// TODO Auto-generated catch block
			exe.printStackTrace();
			LOGGER.info("URL Exception ", exe);
		} catch (IOException exe) {
			// TODO Auto-generated catch block
			exe.printStackTrace();
			LOGGER.info("IO Exception ", exe);
		}
		return true;
	}

}

