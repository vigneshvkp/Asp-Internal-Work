/*
 * Created on Jan 7, 2008
 * 
 * Copyright 2007-2008 Aspire Systems Pvt. Ltd. All rights reserved.
 */

package com.aspire.thi.web.common;


import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.aspire.thi.common.ResourceUtility;
import com.aspire.thi.common.ServletUtility;

/**
 * Render menu for the application
 * 
 * @author venkat.sadasivam
 */
public class MenuTag extends TagSupport {

	/**
	 * default serial version id
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = Logger.getLogger(MenuTag.class);

	/**
	 * Path of the menu.xml relative to the web app folder<br>
	 * For example if your menu.xml under WEB-INF folder then the filepath
	 * should be /WEB-INF/menu.xml
	 */
	private String filePath;

	private String menutype;

	private transient String baseUrl;
	
	private boolean ismainmenu=true;
	
	boolean isFirstLevel=true;
	
	private String aceNo;

	private boolean auditor;

	/**
	 * Call this method in doEndTag to clear attribute before leaving from this
	 * tag
	 */
	private void reset() {
		filePath = null;
		menutype = null;
		baseUrl = null;
		aceNo = null;
		auditor = false;
	}

	@Override
	public int doEndTag() throws JspException {
		baseUrl = ServletUtility.getBaseUrl((HttpServletRequest) pageContext.getRequest());
		//LOGGER.debug("filePath---->"+ filePath);
		Document document = ResourceUtility.loadXmlFromWebPath(filePath);
		Properties aceNoProps = ResourceUtility.loadPropertiesFromWebPath("/WEB-INF/qa.properties");
		try {
			pageContext.getOut().print(renderMenu(document, aceNoProps));
		} catch (Exception e) {
			throw new JspException(e);
		} finally {
			reset();
		}
		return super.doEndTag();
	}

	private String renderMenu(final Document document, final Properties aceNoProps) throws Exception {
//		String cssMenu = null;
//		String functionName = null;
//		if (menutype.equalsIgnoreCase("horizontal")) {
//			cssMenu = "horizontalmenu";
//			functionName = "createhorizontalmenu";
//		} else if (menutype.equalsIgnoreCase("vertical")) {
//			cssMenu = "verticalmenu";
//			functionName = "createverticalmenu";
//		} else {
//			throw new JspException("Invalid menutype: " + menutype);
//		}
		StringBuffer sb = new StringBuffer(4 * 1024);
		
		/*
		 * Commented to apply new CSS. as per PropelJ implementation, we used java script functions to render 
		 * menu, but as per new CSS we no need to use java script function.
		 */
		 
		/*
		sb.append("\n\r");
		sb.append("<script>");
		sb.append("\n\r");
		sb.append("if (window.addEventListener) window.addEventListener(\"load\", " + functionName + ", false)");
		sb.append("\n\r");
		sb.append("else if (window.attachEvent) window.attachEvent(\"onload\", " + functionName + ");");
		sb.append("\n\r");
		sb.append("</script>");
		sb.append("\n\r");
		sb.append("<ul id=\"navigationmenu\" class=\"");
		sb.append(cssMenu);
		sb.append("\">");
		*/
		// added for new CSS
		if (menutype.equalsIgnoreCase("vertical"))
		{
			//LOGGER.debug("Building Vertical menu");
			sb.append("<div class=\"glossymenu\">");
			// 	Get the root tag children from the document
			NodeList nodeList = document.getElementsByTagName("menus").item(0).getChildNodes();
			int childNodes = nodeList.getLength();
			
			if (childNodes > 0) {
				for (int i = 0; i < childNodes; i++) {
					ismainmenu=true;
					buildMenu(nodeList.item(i), sb, aceNoProps);
				}
			}
			sb.append("</div>");
		}
		else{
			//LOGGER.debug("Building Horizontal Menu");
			NodeList nodeList = document.getElementsByTagName("menus").item(0).getChildNodes();
			int childNodes = nodeList.getLength();
			
			if (childNodes > 0) {
				for (int i = 0; i < childNodes; i++) {
					ismainmenu=true;
					buildHorizontalMenu(nodeList.item(i), sb, aceNoProps);
				}
			}
			
		}
		LOGGER.debug("Final Menu String--->" + sb.toString());
		return sb.toString();

	}

	private void buildMenu(final Node node, final StringBuffer sb, final Properties aceNoProps) throws Exception {
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			String url = null;
			String title = null;
			String permissionExp = null;
			Element elem = (Element) node;
			Attr attr = null;
			if ((attr = elem.getAttributeNode("permission")) != null) {
				permissionExp = attr.getValue();
			}
			// if the expression fails, return from this node
//			if (permissionExp != null && !PermissionTag.parseExp(pageContext, permissionExp)) {
//				return;
//			}
			
			if(permissionExp != null){
				if(permissionExp.contains("QA") && (aceNoProps.containsKey(aceNo) == false) ){
					return;
				}
				if(permissionExp.contains("Auditor") && auditor == false){
					return;
				}
			}
			
			if ((attr = elem.getAttributeNode("title")) != null) {
				title = attr.getValue();
			}
			if ((attr = elem.getAttributeNode("url")) != null) {
				url = attr.getValue();
				if (url != null && url.startsWith("/")) {
					url = baseUrl + url;
				}
			}
			
			String css="menuitem";
			
			int childNodes = node.getChildNodes().getLength();
			if (childNodes > 0) {
				
				if(isFirstLevel)
					css="menuitem submenuheader";
				else if(!isFirstLevel)
					css="menuitem1 submenuheader";
				sb.append("<a class=\"");
				sb.append(css);
				sb.append("\" href=\"");
				sb.append(url);
				sb.append("\">");
				sb.append(title);
				sb.append(" </a>");
				sb.append("<div class=\"submenu\">");
//				sb.append("<div class=\"submenu\"><ul>");
				isFirstLevel=false;
				for (int i = 0; i < childNodes; i++) {
					ismainmenu=false;
					buildMenu(node.getChildNodes().item(i), sb, aceNoProps);
				}
				isFirstLevel=true;
//				sb.append("</ul></div>");
				sb.append("</div>");
			}
			else{
				if(ismainmenu){
					sb.append("<a ");
					sb.append(" class=\"");
					sb.append(css);
					sb.append("\" ");
					sb.append(" href=\"");
					sb.append(url);
					sb.append("\">");
					sb.append(title);
					sb.append("</a>");
				}
				else{
//					sb.append("<li><a ");
					sb.append("<a ");
					sb.append(" href=\"");
					sb.append(url);
					sb.append("\">");
//					if(!isFirstLevel){
//						//sb.append("&nbsp;&nbsp;"+title);
//						sb.append(title);
//					}
//					else
						sb.append(title);
						sb.append("</a>");
//						sb.append("</a></li>");
				}
			}
			
			//LOGGER.debug("Menu Title ---->" + title);
			
		}
	}
	
	/**
	 * new function to build horizontal menu.
	 * to apply CSS perfectly, we have created a new function.
	 * @return
	 */
	private void buildHorizontalMenu(final Node node, final StringBuffer sb, final Properties aceNoProps) throws Exception {
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			String url = null;
			String title = null;
			String permissionExp = null;
			Element elem = (Element) node;
			Attr attr = null;
			if ((attr = elem.getAttributeNode("permission")) != null) {
				permissionExp = attr.getValue();
			}
			// if the expression fails, return from this node
//			if (permissionExp != null && !PermissionTag.parseExp(pageContext, permissionExp)) {
//				return;
//			}
			if(permissionExp != null){
				if(permissionExp.contains("QA") && (aceNoProps.containsKey(aceNo) == false) ){
					return;
				}
				if(permissionExp.contains("AUDITOR") && auditor == false){
					return;
				}
			}
			
			if ((attr = elem.getAttributeNode("title")) != null) {
				title = attr.getValue();
			}
			if ((attr = elem.getAttributeNode("url")) != null) {
				url = attr.getValue();
				if (url != null && url.startsWith("/")) {
					url = baseUrl + url;
				}
			}
			
			int childNodes = node.getChildNodes().getLength();
			if (childNodes > 0) {
				
				//sb.append("<ul>");
				
				sb.append("<li><a ");
				sb.append(" href=\"");
				sb.append(url);
				sb.append("\">");
				sb.append(title);
				sb.append(" </a>");
				sb.append("<ul>");
				
				for (int i = 0; i < childNodes; i++) {
					ismainmenu=false;
					buildHorizontalMenu(node.getChildNodes().item(i), sb, aceNoProps);
				}
				sb.append("</ul></li>");
			}
			else{
//				if(ismainmenu){
//					sb.append("<li><a ");
//					sb.append(" href=\"");
//					sb.append(url);
//					sb.append("\">");
//					sb.append(title);
//					sb.append("</a></li>");
//				}
//				else{
					sb.append("<li><a ");
					sb.append(" href=\"");
					sb.append(url);
					sb.append("\">");
					sb.append(title);
					sb.append("</a></li>");
//				}
			}
		}
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(final String filePath) {
		this.filePath = filePath;
	}

	public String getMenutype() {
		return menutype;
	}

	public void setMenutype(final String menutype) {
		this.menutype = menutype;
	}
	
	public void setAceNo(String aceNo) {
		this.aceNo = aceNo;
	}
	
	public String getAceNo() {
		return aceNo;
	}
	
	public void setAuditor(boolean auditor) {
		this.auditor = auditor;
	}
	
	public boolean isAuditor() {
		return auditor;
	}
}
