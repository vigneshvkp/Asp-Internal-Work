package com.aspire.thi.service;

import java.io.Serializable;
import java.util.List;

import com.aspire.thi.domain.Product;

public interface ProductManager extends Serializable{

    public void increasePrice(int percentage);

    public List<Product> getProducts();

}