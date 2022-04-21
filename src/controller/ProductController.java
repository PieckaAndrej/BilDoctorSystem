package controller;

import dal.ProductDB;
import dal.ProductDBIF;
import model.Product;

public class ProductController {
	
	private ProductDBIF productDB;
	
	public ProductController() {
		productDB = new ProductDB();
	}
	
	public Product searchProduct(int productId) {
		return productDB.searchProduct(productId);
	}
	
}
