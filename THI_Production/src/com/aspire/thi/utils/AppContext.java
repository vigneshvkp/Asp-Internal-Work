package com.aspire.thi.utils;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.aspire.thi.domain.Auditor;
import com.aspire.thi.domain.Customer;
import com.aspire.thi.domain.Department;
import com.aspire.thi.domain.ProsProject;
import com.aspire.thi.service.ICustomerManager;
import com.aspire.thi.service.IDepartmentManager;
import com.aspire.thi.service.IProjectManager;
import com.aspire.thi.ws.client.IDMWebService;
import com.aspire.thi.ws.client.ProsWebService;

public class AppContext {

	private static final Log LOGGER = LogFactory.getLog(AppContext.class);
	
	private final static ApplicationContext context = new ClassPathXmlApplicationContext("config-ws.xml");
	
	private ICustomerManager customerManager;
	private IDepartmentManager departmentManager;
	private IProjectManager projectManager;
	
	public static IDMWebService getIDMServiceBean() {
		return (IDMWebService) context.getBean("idmService");
	}

	public static ProsWebService getProsServiceBean() {
		return (ProsWebService) context.getBean("prosService");
	}
	
	public AppContext(){
		//
	}
	
	public void setCustomerManager(ICustomerManager customerManager){
		this.customerManager = customerManager;
	}
	
	public void setDepartmentManager(IDepartmentManager departmentManager){
		this.departmentManager = departmentManager;
	}

	public void setProjectManager(IProjectManager projectManager){
		this.projectManager = projectManager;
	}
	
	//Don't remove this method, this has specific purpose.
	public void reConnectDB(){
		LOGGER.info("Reconnecting to DB for maintaing Database Session Start");
		LOGGER.info(departmentManager.getDepartment(1));// Management
		LOGGER.info("Reconnecting to DB for maintaing Database Session End");
	}

	public void populateCustomer(){
		List<Customer> customers = loadCustomers();
		customerManager.saveAllCustomers(customers);
	}
	
	private List<Customer> loadCustomers(){
		List<Customer> customers = null;
	    try {
			String xml = getProsServiceBean().GetAllActiveCustomer();
		    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder builder = factory.newDocumentBuilder();
		    InputSource is = new InputSource( new StringReader( xml ) );
		    Document activeCustomer = builder.parse( is );
		    NodeList nodes = activeCustomer.getElementsByTagName("Customer");
		    int nodeLength = nodes.getLength();
		    customers = new ArrayList<Customer>(nodeLength);
		    for (int i = 0; i < nodeLength; i++) {
				Element node = (Element) nodes.item(i);
				String id = node.getAttribute("Identifier");
				String customerName = node.getChildNodes().item(0).getTextContent();
				Customer customer = new Customer();
				customer.setId(Integer.parseInt(id));
				customer.setCustomerName(customerName);
				customer.setActive(true);
				customer.setCreatedBy("ACE0106");
				Date now = Calendar.getInstance().getTime();
				customer.setCreatedOn(now);
				customer.setLastModifiedBy("ACE0106");
				customer.setLastModifiedOn(now);
				LOGGER.debug("Current Customer is --> " + customer);
				customers.add(customer);
			}
	    } catch(Exception e){
	    	//@TODO do meaningful handling
	    }
	    LOGGER.info("Call customer manager to save values into db with size" + customers.size());
	    return customers;
	  }

	public void populateDepartment(){
		List<Department> departments = loadDepartments();
	    departmentManager.saveAllDepartments(departments);
	}
	
	public static List<Auditor> loadAllEmployeeByRole(){
		List<Auditor> auditors = null;
	    try {
			String xml = getIDMServiceBean().GetEmployeeListByUserStringID("", "USER0002", 1);
		    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder builder = factory.newDocumentBuilder();
		    InputSource is = new InputSource( new StringReader( xml ) );
		    Document activeEmployee = builder.parse( is );
		    NodeList nodes = activeEmployee.getElementsByTagName("Employee");
		    int nodeLength = nodes.getLength();
		    auditors = new ArrayList<Auditor>(nodeLength);
		    for (int i = 1; i < nodeLength; i++) {
		    	Auditor auditor = new Auditor();
				Element node = (Element) nodes.item(i);
				auditor.setAceNo(node.getAttribute("ACEID"));
				auditor.setName(((Element)node.getChildNodes().item(0)).getAttribute("UserName"));
				auditor.setEmail(node.getChildNodes().item(1).getTextContent());
				auditor.setDeptID(((Element)node.getChildNodes().item(7)).getAttribute("Identifier"));
				auditor.setDeptName(node.getChildNodes().item(7).getTextContent());
				auditor.setCreatedBy("ACE0002");
				Date now = Calendar.getInstance().getTime();
				auditor.setCreatedOn(now);
				auditor.setLastModifiedBy("ACE0002");
				auditor.setLastModifiedOn(now);
				auditors.add(auditor);
			}
	    } catch(Exception e){
	    	e.printStackTrace();
	    	//@TODO do meaningful handling
	    }
	    LOGGER.info("Auditor size" + auditors.size());
	    return auditors;
	}


	private List<Department> loadDepartments(){
		List<Department> departments = null;
	    try {
			String xml = getIDMServiceBean().GetAllDepartment();
		    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder builder = factory.newDocumentBuilder();
		    InputSource is = new InputSource( new StringReader( xml ) );
		    Document activedepartment = builder.parse( is );
		    NodeList nodes = activedepartment.getElementsByTagName("Department");
		    int nodeLength = nodes.getLength();
		    departments = new ArrayList<Department>(nodeLength);
		    for (int i = 1; i < nodeLength; i++) {
				Element node = (Element) nodes.item(i);
				String prosDeptId = node.getAttribute("Identifier");
				int id = Integer.parseInt(prosDeptId.substring(3),10); //All  Dept will start with prefix "DEP"
				String deptName = node.getChildNodes().item(0).getTextContent();
				String deptHeadAceNo = ((Element)node.getChildNodes().item(2)).getAttribute("Identifier");
				Department department = new Department();
				department.setId(id);
				department.setProsDeptId(prosDeptId);
				department.setName(deptName);
				department.setDeptHeadAceNo(deptHeadAceNo);
				department.setCreatedBy("ACE0002");
				Date now = Calendar.getInstance().getTime();
				department.setCreatedOn(now);
				department.setLastModifiedBy("ACE0002");
				department.setLastModifiedOn(now);
				LOGGER.debug("Current Department is --> " + department);
				departments.add(department);
			}
	    } catch(Exception e){
	    	e.printStackTrace();
	    	//@TODO do meaningful handling
	    }
	    LOGGER.info("Call Department manager to save values into db with size" + departments.size());
	    return departments;
	  }

	public void populateProjectFromPros(){
		List<ProsProject> prosProjects = loadProjectsFromPros();
	    projectManager.saveAllProsProjects(prosProjects);
	}

	@SuppressWarnings("deprecation")
	private List<ProsProject> loadProjectsFromPros() {
		List<ProsProject> prosProjects = null;
	    try {
			String xml = getProsServiceBean().GetAllActiveProjects();
		    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder builder = factory.newDocumentBuilder();
		    InputSource is = new InputSource( new StringReader( xml ) );
		    Document activeProjects = builder.parse( is );
		    NodeList nodes = activeProjects.getElementsByTagName("Project");
		    int nodeLength = nodes.getLength();
		    prosProjects = new ArrayList<ProsProject>(nodeLength);
		    for (int i = 0; i < nodeLength; i++) {
				Element node = (Element) nodes.item(i);
				int prosProjectId = Integer.parseInt(node.getAttribute("Identifier"));
				NodeList projectNodes = node.getChildNodes();
				String projectName = projectNodes.item(0).getTextContent();
				int customerId = Integer.parseInt(projectNodes.item(1).getTextContent());
				int deptId = Integer.parseInt(projectNodes.item(3).getTextContent().substring(3), 10);
				String startDateStr = projectNodes.item(4).getTextContent();
				LOGGER.debug(startDateStr);
				
				Date startDate = new Date(startDateStr);
				String endDateStr = projectNodes.item(5).getTextContent();
				Date endDate = null; 
				if(endDateStr != null && endDateStr.trim().length()>0)
					endDate = new Date(endDateStr);
				String ownerUserId = projectNodes.item(8).getTextContent();
				
				ProsProject prosProject = new ProsProject();
				prosProject.setProsProjectId(prosProjectId);
				prosProject.setProjectName(projectName);
				prosProject.setCustomerId(customerId);
				prosProject.setDeptId(deptId);
				prosProject.setActive(true);
				prosProject.setStartDate(startDate);
				prosProject.setEndDate(endDate);
				prosProject.setOwnerUserId(ownerUserId);
				prosProject.setCreatedBy("ACE0002");
				Date now = Calendar.getInstance().getTime();
				prosProject.setCreatedOn(now);
				prosProject.setLastModifiedBy("ACE0002");
				prosProject.setLastModifiedOn(now);
				LOGGER.debug("Current ProsProject is --> " + prosProject);
				prosProjects.add(prosProject);
			}
	    } catch(Exception e){
	    	//@TODO do meaningful handling
	    }
	    LOGGER.info("Call Project manager to save values into db with size" + prosProjects.size());
	    return prosProjects;
	  }

	public boolean syncCustomers(){
		List<Customer> customers = loadCustomers();
		customerManager.syncCustomers(customers);
		return true;
	}

	public boolean syncDepartments(){
		List<Department> departments = loadDepartments();
	    departmentManager.saveAllDepartments(departments);
		return true;
	}

	public boolean syncProsProjects(){
		List<ProsProject> prosProjects = loadProjectsFromPros();
		projectManager.sycnProsProjects(prosProjects);
		return true;
		
	}
}
