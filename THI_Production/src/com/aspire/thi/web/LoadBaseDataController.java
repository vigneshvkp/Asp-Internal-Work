package com.aspire.thi.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.aspire.thi.utils.AppContext;


public class LoadBaseDataController implements Controller {

    protected final Log logger = LogFactory.getLog(getClass());

    private AppContext appContext;

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String now = (new java.util.Date()).toString();
        logger.info("returning hello view with " + now);
        
        this.appContext.hashCode();
        
        //this.appContext.populateCustomer();
        
        //this.appContext.populateDepartment();
        
        //this.appContext.populateProjectFromPros();

        Map<String, Object> myModel = new HashMap<String, Object>();
        myModel.put("now", now);
        //myModel.put("products", this.appLoader.get());

        return new ModelAndView("loadbasedata", "model", myModel);
    }


    public void setAppContext(AppContext appContext) {
        this.appContext = appContext;
    }

}