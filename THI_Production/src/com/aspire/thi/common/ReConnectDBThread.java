package com.aspire.thi.common;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import org.apache.log4j.Logger;

public class ReConnectDBThread implements Runnable {
	private static final Logger LOGGER = Logger
			.getLogger(ReConnectDBThread.class);

	// MillSeconds * Seconds * Minutes * Hour * Day
	private static final long SLEEP_TIME = 1000 * 60 * 60 * 1 * 1; // 1 Hour;

	@Override
	public void run() {
		LOGGER.info("Time out period is   " + SLEEP_TIME / (60 * 60 * 1000.0)
				+ " hour");
		while (true) {
			try {
				Thread.sleep(SLEEP_TIME);
				boolean success = refreshProsData();
				if (success) {
					LOGGER.info("DB Connection successfully reconnected"
							+ new Date());
				}
			} catch (InterruptedException e) {
				LOGGER.info("Thread interruption", e);
			}
		}
	}

	private boolean refreshProsData() {
		try {
			URL url = new URL(
					"http://localhost:8080/THI/reload.htm");
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
