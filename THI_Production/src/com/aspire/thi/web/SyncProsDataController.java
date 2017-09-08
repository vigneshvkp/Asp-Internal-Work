package com.aspire.thi.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.aspire.thi.utils.AppContext;

public class SyncProsDataController implements Controller {

    private final Log LOGGER = LogFactory.getLog(this.getClass());

    private AppContext appContext;
    
    private boolean fromThread;
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		

        String now = (new java.util.Date()).toString();
        LOGGER.info("returning hello view with " + now);

		this.appContext.syncCustomers();
		
		this.appContext.syncDepartments();
		
		this.appContext.syncProsProjects();

		Map<String, Object> myModel = new HashMap<String, Object>();
        myModel.put("now", now);
        
		if(fromThread){
			return new ModelAndView("syncProsDataThread", "model", myModel);
		} else {
			return new ModelAndView("syncProsData", "model", myModel);
		}
		
	}
	
    public void setAppContext(AppContext appContext) {
        this.appContext = appContext;
    }

    public void setFromThread(boolean fromThread) {
        this.fromThread = fromThread;
    }


}
