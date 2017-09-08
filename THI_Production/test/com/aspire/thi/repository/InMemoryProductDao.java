package com.aspire.thi.repository;

import java.util.List;

import com.aspire.thi.domain.Product;

public class InMemoryProductDao implements ProductRepository {

    private List<Product> productList;

    public InMemoryProductDao(List<Product> productList) {
        this.productList = productList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void saveProduct(Product prod) {
    }

}