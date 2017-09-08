package com.aspire.thi.web;

import junit.framework.Assert;

import org.junit.Test;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.web.servlet.ModelAndView;

/**
 * The class <code>HelloControllerTest</code> contains tests for the class <code>{@link HelloController}</code>.
 *
 * @generatedBy CodePro at 20/12/10 5:06 PM, using the Spring generator
 * @author muthu.velappan
 * @version $Revision: 1.0 $
 */
public class HelloControllerTests{

	/**
	 * Run the ModelAndView handleRequest(HttpServletRequest,HttpServletResponse) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 20/12/10 5:06 PM
	 */
	@Test
	public void handleRequestView()
		throws Exception {
		HelloController fixture = new HelloController();//.createHelloController();
		//MockHttpServletRequest request = new MockHttpServletRequest();
		//MockHttpServletResponse response = new MockHttpServletResponse();

		ModelAndView result = fixture.handleRequest(null, null);

		Assert.assertEquals("hello", result.getViewName());
		Assert.assertNotNull(result.getModel());
        String nowValue = (String) result.getModel().get("now");
        Assert.assertNotNull(nowValue);

		// add additional test code here
//		Assert.assertNotNull("ModelAndView should not be null", result);
//		ModelAndViewAssert.assertViewName(result, "WEB-INF/jsp/hello.jsp");
//		Assert.assertEquals(200, response.getStatus());
	}

}