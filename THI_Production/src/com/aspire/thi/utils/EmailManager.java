package com.aspire.thi.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author gayatri.venkataraman
 * 
 * <p>
 * The major responsibility of the class includes the following <br>
 * <br>
 * <li>Connects to Mail server and sends mail to the recipient</li>
 * <li>Holding default mail properties from mail configuration file</li>
 * </p>
 */
public class EmailManager {
	private static final Log log = LogFactory.getLog(EmailManager.class);

	private static final EmailManager INSTANCE = new EmailManager();

	private static final List<String> EMPTY_LIST = new ArrayList<String>(1);

	private static final String MIME_TYPE_TEXT_HTML = "text/html";
	
	private Properties emailProperties = null;

	private EmailManager() {

	}

	public static final EmailManager getInstance() {
		return INSTANCE;
	}

	public void setEmailProperties(Properties properties) {
		this.emailProperties = properties;
	}

	/**
	 * Calls the overloaded sendMail() Api with empty cc address.
	 * 
	 * @param toAddress
	 *            Mail Address to which this email has to be sent
	 * @param subject
	 *            Subject for this email
	 * @param message
	 *            Body Content of this email
	 * 
	 * @return status of the mail, 0 - sent , -1 - send failed
	 */
	public Integer sendMail(String toAddress, String subject, String message) {
		return sendMail(toAddress, subject, message, EMPTY_LIST);
	}

	/**
	 * Calls the overloaded sendMail() Api with empty bcc address.
	 * 
	 * @param toAddress
	 *            Mail Address to which this email has to be sent
	 * @param subject
	 *            Subject for this email
	 * @param message
	 *            Body Content of this email
	 * @param ccAddress
	 *            Collection of cc email addresses to process
	 * @return status of the mail, 0 - sent , -1 - send failed
	 */
	public Integer sendMail(String toAddress, String subject, String message,
			List<String> ccAddresses) {
		return sendMail(toAddress, subject, message, ccAddresses, EMPTY_LIST);
	}

	/**
	 * Calls the overloaded sendMail() Api with MIME Type set to "text/html".
	 * 
	 * @param toAddress
	 *            Mail Address to which this email has to be sent
	 * @param subject
	 *            Subject for this email
	 * @param message
	 *            Body Content of this email
	 * @param ccAddress
	 *            Collection of cc email addresses to process
	 * @param bccAddress
	 *            Collection of bcc email addresses to process
	 * 
	 * @return status of the mail, 0 - sent , -1 - send failed
	 */
	public Integer sendMail(String toAddress, String subject, String message,
			List<String> ccAddresses, List<String> bccAddresses) {
		return sendMail(toAddress, subject, message, ccAddresses, bccAddresses,
				MIME_TYPE_TEXT_HTML);
	}

	/**
	 * Calls the overloaded sendMail() Api with empty attchments.
	 * 
	 * @param toAddress
	 *            Mail Address to which this email has to be sent
	 * @param subject
	 *            Subject for this email
	 * @param message
	 *            Body Content of this email
	 * @param ccAddress
	 *            Collection of cc email addresses to process
	 * @param bccAddress
	 *            Collection of bcc email addresses to process
	 * @param mimeType
	 *            Mime type for this email message
	 * 
	 * @return status of the mail, 0 - sent , -1 - send failed
	 */
	public Integer sendMail(String toAddress, String subject, String message,
			List<String> ccAddresses, List<String> bccAddresses, String mimetype) {
		// TODO Auto-generated method stub
		return sendMail(toAddress, subject, message, ccAddresses, bccAddresses,
				mimetype, EMPTY_LIST);
	}

	/**
	 * This method connects to the Mail server and sends mail.
	 * 
	 * @param toAddress
	 *            Mail address of the recipient
	 * @param subject
	 *            Subject of the message
	 * @param message
	 *            Mail body
	 * @param ccAddress
	 *            Collection of cc email addresses to process
	 * @param ccAddress
	 *            Collection of bcc email addresses to process
	 * @param mimeType
	 *            MIME Type
	 * @param attachments
	 *            List of filenames to be attached with this email message. At
	 *            max 5 will be processed in a single e-mail.
	 * @return status of the mail, 0 - sent , -1 - send failed
	 */

	public Integer sendMail(String toAddress, String subject, String message,
			List<String> ccAddress, List<String> bccAddress, String mimeType,
			List<String> attachments) {

		int status = -1;

		// If default mail properties in not set then return failure.
		if (this.emailProperties == null) {
			log.error("Default Email Properties is not available, "
					+ "set that by calling setEmailProperties() method and "
					+ "then call sendEmail()");
			return Integer.valueOf(status);
		}

		Authenticator authenticator = new Authenticator(emailProperties.getProperty("mail.smtp.username"),emailProperties.getProperty("mail.smtp.password"));

//		checkValidity(ccAddress, bccAddress, attachments);
		if (ccAddress == null) {
			ccAddress = EMPTY_LIST;
		}

		if (bccAddress == null) {
			bccAddress = EMPTY_LIST;
		}

		if (attachments == null) {
			attachments = EMPTY_LIST;
		}
		

		if (mimeType == null) {
			mimeType = MIME_TYPE_TEXT_HTML;
		}

		String fromAddress = emailProperties.getProperty("mail.default.from");

		if (toAddress == null || fromAddress == null || message == null)
			return Integer.valueOf(status);

		// create a new Session
		Session session = Session.getDefaultInstance(emailProperties,authenticator);

		// Create a New message
		MimeMessage msg = new MimeMessage(session);

		// Check if it is a valid email-id
		if (fromAddress.indexOf("@") != -1 && toAddress.indexOf("@") != -1
				&& fromAddress.indexOf(".") != -1
				&& toAddress.indexOf(".") != -1) {

			try {

				// Set the From address
				msg.setFrom(new InternetAddress(fromAddress));

				// Setting the "To recipients" addresses
				msg.setRecipients(Message.RecipientType.TO, InternetAddress
						.parse(toAddress, false));

				// Append CC Addresses
				appendAddresses(emailProperties, msg, ccAddress,
						RecipientType.CC);

				// Appemd BCC Addresses
				appendAddresses(emailProperties, msg, bccAddress,
						RecipientType.BCC);

				// Set mail subject
				msg.setSubject(subject);

				// Create and fill the first message part
				MimeBodyPart mbp = new MimeBodyPart();
				mbp.setContent(message, mimeType);

				// Create the Multipart and its parts to it
				Multipart mp = new MimeMultipart();
				mp.addBodyPart(mbp);

				// Add the attachments
				addAttachments(mp, attachments);

				// Add the Multipart to the message
				msg.setContent(mp);
				// Set the Date: header
				msg.setSentDate(new Date());

				// Send the message
				Transport.send(msg);

				status = 0;

			} catch (MessagingException msgEx) { // Messaging errors
				log.error("MailServiceBean.sendMail: Error sending mail : "
						+ msgEx.toString());

			} catch (Exception ex) {
				log.error("MailServiceBean.sendMail: Generic Error " + "sending mail : ", ex);
			}

		}

		return Integer.valueOf(status);
	}

	/*
	 * Assign Empty list to all null collections

	//Since Java uses pass by value, assigning a empty collection to a null value 
	//inside a local method doesn't carry that effect to outside method 
	
	 */
//	private void checkValidity(List<String> ccAddress, List<String> bccAddress,
//			List<String> attachments) {
//		if (ccAddress == null) {
//			ccAddress = EMPTY_LIST;
//		}
//
//		if (bccAddress == null) {
//			bccAddress = EMPTY_LIST;
//		}
//
//		if (attachments == null) {
//			attachments = EMPTY_LIST;
//		}
//	}

	/*
	 * Add recipients to cc or bcc list based on Recipient Type.
	 */
	private void appendAddresses(Properties prop, MimeMessage msg,
			List<String> addresses, RecipientType type)
			throws MessagingException {

		if (type.equals(RecipientType.CC)) {
			if (prop.getProperty("mail.default.cc") != null) {
				String defAddress = prop.getProperty("mail.default.cc");
				if (defAddress != null) {
					msg.setRecipients(type, InternetAddress.parse(defAddress));
				}
			}
		} else {
			if (prop.getProperty("mail.default.bcc") != null) {
				String defAddress = prop.getProperty("mail.default.bcc");
				if (defAddress != null) {
					msg.setRecipients(type, InternetAddress.parse(defAddress));
				}
			}
		}

		Address addr[] = msg.getRecipients(type);
		if(addr == null){
			addr = new Address[0];
		}

		if (addresses != null && addresses.size() > 0) {
			Address newAddr[] = new Address[addresses.size() + addr.length];
			int count = 0;
			for (String address : addresses) {
				InternetAddress internetAddress = new InternetAddress(address);
				internetAddress.validate();
				newAddr[count++] = internetAddress;
			}
			for (Address address2 : addr) {
				newAddr[count++] = address2;
			}
			msg.setRecipients(type, newAddr);
		}

	}

	/*
	 * Iterate over the attachment collection and check for the validity of file
	 * before adding to the messages.
	 */
	private void addAttachments(Multipart mp, List<String> attachments)
			throws MessagingException {
		for (String attachment : attachments) {
			if (new File(attachment).exists()) {
				MimeBodyPart mbp2 = new MimeBodyPart();
				FileDataSource fds = new FileDataSource(attachment);
				mbp2.setDataHandler(new DataHandler(fds));
				mbp2.setFileName(fds.getName());
				mp.addBodyPart(mbp2);
			} else {
				log.error("Attachment denoted by this abstract path name "
						+ "doesn't exist " + attachment);
			}
		}
	}

	private class Authenticator extends javax.mail.Authenticator {
		private PasswordAuthentication authentication;

		public Authenticator(String username,String password) {
			authentication = new PasswordAuthentication(username, password);
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return authentication;
		}
	}
}
