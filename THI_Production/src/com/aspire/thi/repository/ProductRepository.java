package com.aspire.thi.repository;

import java.util.List;

import com.aspire.thi.domain.Product;

public interface ProductRepository {

    public List<Product> getProductList();

    public void saveProduct(Product prod);

}