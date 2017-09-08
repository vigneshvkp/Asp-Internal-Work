package com.aspire.thi.web;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class HelloController implements Controller {

    protected static final Log LOGGER = LogFactory.getLog(HelloController.class);

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	//AppContext.populateCustomer();

//    	ITWebService itWsBean = AppContext.getITServiceBean();
//    	LOGGER.debug(itWsBean.GetTenantTypes());

    	
//    	IDMWebService idmWsBean = AppContext.getIDMServiceBean();
//    	String date = new DateFormatter().getFormat().format(Calendar.getInstance().getTime());

/*    	LOGGER.debug("*****AllDeliveryUnit********");
    	LOGGER.debug(idmWsBean.GetAllDeliveryUnit());
    	LOGGER.debug("*****AllDepartment********");
    	LOGGER.debug(idmWsBean.GetAllDepartment());
    	LOGGER.debug("*****AllEmployeeType********");
    	LOGGER.debug(idmWsBean.GetAllEmployeeType());
    	LOGGER.debug("*****AllDepartmentDesignation********");
    	LOGGER.debug(idmWsBean.GetAllDepartmentDesignation());
    	LOGGER.debug("*****AllDesignation********");
    	LOGGER.debug(idmWsBean.GetAllDesignation());
    	LOGGER.debug("*****AllReportingRelation********");
    	LOGGER.debug(idmWsBean.GetAllReportingRelation());
    	LOGGER.debug("*****AllLevel********");
    	LOGGER.debug(idmWsBean.GetAllLevel());
    	LOGGER.debug("*****EmployeeDetailByUserName********");
    	LOGGER.debug(idmWsBean.GetEmployeeDetailByUserName(date, "gokul.seenivasan"));
*/
    	
//    	LOGGER.debug("*****EmployeeDetailByUserName********");
//    	LOGGER.debug(idmWsBean.GetEmployeeDetailByUserStringID(date, "USER0045"));
//    	LOGGER.debug("*****Pros webservice details********");
//    	ProsWebService prosWsBean = AppContext.getProsServiceBean();
//    	LOGGER.debug("*****AllActiveCustomer********");
//    	LOGGER.debug(prosWsBean.GetAllActiveCustomer());
//    	LOGGER.debug("*****AllActiveProjects********");
//    	LOGGER.debug(prosWsBean.GetAllActiveProjects());
//    	LOGGER.debug("*****AllDeactivateCustomer********");
//    	LOGGER.debug(prosWsBean.GetAllDeactivateCustomer());
//    	LOGGER.debug("*****AllProjects********");
//    	LOGGER.debug(prosWsBean.GetAllProjects());

//    	LOGGER.debug("*****ProjectResourceAllocationAndProjectDetailsByAceNumber********");
//    	LOGGER.debug(prosWsBean.GetProjectResourceAllocationAndProjectDetailsByAceNumber1());
//    	LOGGER.debug(prosWsBean.GetProjectResourceAllocationAndProjectDetailsByAceNumber(
//    			"ACE0106"));
    	
//    	LOGGER.debug("*****ProjectResourceAllocationAndProjectDetailsByUserStringID********");
//    	LOGGER.debug(prosWsBean.GetProjectResourceAllocationAndProjectDetailsByUserStringID(
//    			"USER0349"));

//    	LOGGER.debug("*****ProjectResourceByProjectName********");
//    	LOGGER.debug(prosWsBean.GetProjectResourceByProjectName("Apus"));

//    	LOGGER.debug("*****ProjectsByDeliveryUnitstringID********");
//    	LOGGER.debug(prosWsBean.GetProjectsByDeliveryUnitstringID("DEP019"));
//
//    	prosWsBean.GetResourceByCustomerName(String customerName);
//    	LOGGER.debug("*****ResourceLentDetailByACENumber********");
//    	LOGGER.debug(prosWsBean.GetResourceLentDetailByACENumber("ACE0106"));
//    	LOGGER.debug("*****ResourceLentDetailByUserStringID********");
//    	LOGGER.debug(prosWsBean.GetResourceLentDetailByUserStringID("USER0045"));
    	
    	

    	String now = (new Date()).toString();
    	LOGGER.info("Returning hello view with " + now);

        return new ModelAndView("hello", "now", now);
    }

}
