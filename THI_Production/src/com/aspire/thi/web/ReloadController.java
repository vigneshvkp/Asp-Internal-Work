package com.aspire.thi.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.aspire.thi.utils.AppContext;


public class ReloadController implements Controller {

    protected final Log logger = LogFactory.getLog(getClass());

    private AppContext appContext;

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	appContext.reConnectDB();
    	return new ModelAndView("hello");

    }


    public void setAppContext(AppContext appContext) {
        this.appContext = appContext;
    }

}