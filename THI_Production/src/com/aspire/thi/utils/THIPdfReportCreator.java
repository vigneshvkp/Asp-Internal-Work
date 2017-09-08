package com.aspire.thi.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.aspire.thi.domain.AssesmentGroupScore;
import com.aspire.thi.domain.LineItemLog;
import com.aspire.thi.domain.ThiScore;
import com.itextpdf.text.Document;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class THIPdfReportCreator {

	private final ThiScore score;

	public THIPdfReportCreator(ThiScore score) throws Exception {

		this.score = score;
	}

	public static void main(String[] args) {
		try {
			@SuppressWarnings("unused")
			THIPdfReportCreator pdfTable = new THIPdfReportCreator(new ThiScore());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendMail(List<String> emailAddresses) {
		try {
			String fileName = createReport();
			sendMail(fileName, emailAddresses);
			System.out.println("File Send :" + fileName);
			deleteFile(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file != null && file.exists()) {
			return file.delete();
		}
		return false;
	}

	private void sendMail(String fileName, List<String> emailAddresses) {
		if (emailAddresses != null && emailAddresses.size() > 0) {
			String subject = "THI Report_" + score.getProjectName() + "_"
					+ new SimpleDateFormat("MMM_yyyy").format(score.getAuditCycleDate());
			String message = "Auto Generated Messeage. No Reply.";
			List<String> attachments = new ArrayList<String>();
			attachments.add(fileName);
			String toAddress = emailAddresses.get(0);
			emailAddresses.remove(0);
			EmailManager.getInstance().sendMail(toAddress, subject, message, emailAddresses, null, null, attachments);
		}
	}

	private String createReport() throws Exception {
		String fileName = System.getProperty("java.io.tmpdir") + "ThiReport_" + this.score.getProjectName() + "_"
				+ new SimpleDateFormat("MMM_yyyy").format(this.score.getAuditCycleDate()) + ".pdf";
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(fileName));
			document.open();

			HTMLWorker htmlWorker = new HTMLWorker(document);

			StringBuilder builder = new StringBuilder();
			builder.append("<html><head></head><body><br><br>");
			builder.append("<table cellspacing='1' cellpadding='2' border='0' width='100%' style='font-size:9px;'>");
			builder.append("<tbody>");
			builder.append("<tr>");
			builder.append("<td nowrap='nowrap' bgcolor='#eceff1' valign='middle' style='padding: 5px 10px;'>Project Name :</td>");
			builder.append("<td bgcolor='#f1f1f1' valign='middle' class='content'>");
			builder.append(score.getProjectName());
			builder.append("</td>");
			builder.append("</tr>");
			builder.append("<tr>");

			builder.append("<tr>");
			builder.append("<td nowrap='nowrap' bgcolor='#eceff1' valign='middle'  style='padding: 5px 10px;'>Audit Cycle :</td>");
			builder.append("<td bgcolor='#f1f1f1' valign='middle' class='content'>");
			builder.append(new SimpleDateFormat("MMM/yyyy").format(score.getAuditCycleDate()));
			builder.append("</td>");
			builder.append("</tr>");

			builder.append("<tr>");
			builder.append("<td nowrap='nowrap' height='28' bgcolor='#eceff1' valign='middle'  style='padding: 5px 10px;'>Auditor's Name</td>");
			builder.append("<td bgcolor='#f1f1f1' valign='middle' class='content'>");
			builder.append(score.getAuditorName());
			builder.append("</td>");
			builder.append("</tr>");
			
			List<String> auditeeNames = score.getAuditeeNames();
			if((auditeeNames!=null)&&(auditeeNames.size()>0)){
				StringBuffer sbAuditee = new StringBuffer(3200);
				int k=0;
				for(;k<auditeeNames.size()-1;++k){
					if((k+1)%2==0){
						sbAuditee.append(auditeeNames.get(k)+",<br/>");
					}else{
						sbAuditee.append(auditeeNames.get(k)+",");
					}
				}
				sbAuditee.append(auditeeNames.get(k));
				builder.append("<tr>");
				builder.append("<td nowrap='nowrap' height='28' bgcolor='#eceff1' valign='middle'  style='padding: 5px 10px;'>Auditee Names</td>");
				builder.append("<td bgcolor='#f1f1f1' valign='middle' class='content'>");
				builder.append(sbAuditee.toString());
				builder.append("</td>");
				builder.append("</tr>");
			}

			builder.append("<tr>");
			builder.append("<td nowrap='nowrap' height='28' bgcolor='#eceff1' valign='middle'  style='padding: 5px 10px;'>Auditor Date</td>");
			builder.append("<td bgcolor='#f1f1f1' valign='middle' class='content'>");
			builder.append(new SimpleDateFormat("dd/MMM/yyyy").format(score.getAuditDate()));
			builder.append("</td>");
			builder.append("</tr>");

			builder.append("</tbody>");
			builder.append("</table>");
			builder.append("<br>");

			builder.append("<table width='100%' border='0.5' cellpadding='3' >");
			// Code 5
			@SuppressWarnings("unused")
			PdfPTable table = new PdfPTable(1); // Code 1

			builder.append("<tr bgcolor='#0069B5' style='color:#FFFFFF;font-size:10px;'><th>Category</th>");
			builder.append("<th align='right' style='border-left:#0069B5;'>Score</th></tr>");

			List<AssesmentGroupScore> groupScores = score.getAssesmentGroupScores();

			for (AssesmentGroupScore assesmentGroupScore : groupScores) {
				// PdfPTable tableInner = new PdfPTable(3);
				List<LineItemLog> lineItemLogs = assesmentGroupScore.getLineItemLogs();
				builder.append("<tr bgcolor='#E5E6FB' style='color:#0275AF;font-size:10px;'>");
				builder.append("<td >");
				builder.append(assesmentGroupScore.getName());
				builder.append("</td>");
				builder.append("<td align='right' style='border-left:0px;'>");
				if (assesmentGroupScore.getScore() != -1) {
					builder.append(assesmentGroupScore.getScore());
				} else {
					builder.append("N/A");
				}

				builder.append("</td>");
				builder.append("</tr>");

				builder.append("<tr colspan=2>");
				builder.append("<td width='20%'>");
				builder.append("<table width='100%' border='0.5' cellpadding='5' style='font-size:8px;'>");
				builder.append("<tr bgcolor='#E7E7E7' style='font-size:9px;'><th align='center'>Criteria</th>");
				builder.append("<th align='center'>Description</th>");
				builder.append("<th align='center'>Remarks</th></tr>");
				for (LineItemLog lineItemLog : lineItemLogs) {
					builder.append("<tr>");
					builder.append("<td width='20%'>");
					builder.append(lineItemLog.getText());
					builder.append("</td>");
					builder.append("<td width='40%'>");
					builder.append(lineItemLog.getDescription());
					builder.append("</td>");
					builder.append("<td width='40%'>");
					builder.append(lineItemLog.getComments());
					builder.append("</td>");
					builder.append("</tr>");
				}
				builder.append("</td>");
				builder.append("</tr>");
				builder.append("</table>");
				// table.addCell(tableInner);
			}

			builder.append("<tr bgcolor='#0069B5' style='color:#FFFFFF;font-size:10px;'><th>Overall Score</th>");
			builder.append("<th align='right' style='border-left:#0069B5;'>" + score.getOverallScore() + "</th></tr>");
			builder.append("</table>");
			builder.append("<div style='font-size:8px; border:1px;padding:10px;'>");
			builder.append("<table cellspacing='1' cellpadding='5' border='0' width='100%'>");
			builder.append("<tbody>");
			builder.append("<tr>");
			builder.append("<td nowrap='nowrap' bgcolor='#eceff1' valign='middle'  style='padding: 5px 10px;'>Overall Comments</td>");
			builder.append("<td bgcolor='#f1f1f1' valign='middle' class='content'>");
			builder.append(score.getComments());
			builder.append("</td>");
			builder.append("</tr>");
			builder.append("<tr>");
			builder.append("<td nowrap='nowrap' height='28' bgcolor='#eceff1' valign='middle'  style='padding: 5px 10px;'>Recommendations for the Project</td>");
			builder.append("<td bgcolor='#f1f1f1' valign='middle' class='content'>");
			builder.append(score.getRecommendations());
			builder.append("</td>");
			builder.append("</tr>");
			builder.append("</tbody>");
			builder.append("</table>");
			builder.append("</div>");
			builder.append("</body></html>");
			htmlWorker.parse(new StringReader(builder.toString()));
			// document.add(table);
		} catch (Exception e) {
			fileName = null;
			throw e;
		} finally {
			document.close();
		}
		return fileName;
	}

	public String generatePdf() {
		String fileName = null;
		try {
			fileName = createReport();
			System.out.println("File Send :" + fileName);
		} catch (Exception e) {
			fileName = null;
			e.printStackTrace();
		}
		return fileName;
	}

}
