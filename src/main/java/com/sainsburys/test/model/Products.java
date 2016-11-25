package com.sainsburys.test.model;

import java.util.List;

public class Products {
	
	private List<ProductItem> productItems;
	private double total;

	public List<ProductItem> getProductItems() {
		return productItems;
	}
	public void setProductItems(List<ProductItem> productItems) {
		this.productItems = productItems;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
}
