package com.aspire.thi.service;

import java.util.List;

import com.aspire.thi.domain.Product;
import com.aspire.thi.repository.ProductRepository;


@SuppressWarnings("serial")
public class SimpleProductManager implements ProductManager {

//    private List<Product> products;
	private ProductRepository productDao;
	
    public List<Product> getProducts() {
        //return products;
    	return productDao.getProductList();
    }

    public void increasePrice(int percentage) {
    	List<Product> products = productDao.getProductList();
    	if (products != null) {
            for (Product product : products) {
                double newPrice = product.getPrice().doubleValue() *
                                    (100 + percentage)/100;
                product.setPrice(newPrice);
                productDao.saveProduct(product);
            }
        }
    }

//    public void setProducts(List<Product> products) {
//        this.products = products;
//    }

    public void setProductDao(ProductRepository productDao) {
        this.productDao = productDao;
    }

}