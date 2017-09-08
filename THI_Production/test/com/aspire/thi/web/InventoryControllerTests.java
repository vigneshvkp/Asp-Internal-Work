package com.aspire.thi.web;

import java.util.ArrayList;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.aspire.thi.domain.Product;
import com.aspire.thi.repository.InMemoryProductDao;
import com.aspire.thi.service.SimpleProductManager;

public class InventoryControllerTests {

	@SuppressWarnings("rawtypes")
	@Test
    public void testHandleRequestView() throws Exception{
        InventoryController controller = new InventoryController();
        SimpleProductManager spm = new SimpleProductManager();
        spm.setProductDao(new InMemoryProductDao(new ArrayList<Product>()));
        controller.setProductManager(spm);
        //controller.setProductManager(new SimpleProductManager());
            ModelAndView modelAndView = controller.handleRequest(null, null);
        Assert.assertEquals("hello", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel());
        Map modelMap = (Map) modelAndView.getModel().get("model");
        String nowValue = (String) modelMap.get("now");
        Assert.assertNotNull(nowValue);
    }
}