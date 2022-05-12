package controller;

import java.util.List;

import dal.ProductDB;
import dal.ProductDBIF;
import model.Product;

public class ProductController {
	
	private ProductDBIF productDB;
	private List<Product> currentProducts;
	

	public ProductController() {
		productDB = new ProductDB();
	}
	
	/**
	 * Search product in the database by id
	 * @param productId Id of the product
	 * @return Product with the id
	 */
	public Product searchProduct(int productId) {
		return productDB.searchProduct(productId);
	}

	/**
	 * Get all products from the database
	 * @return List of products
	 */
	public List<Product> getAllProducts() {
		return currentProducts = productDB.getAllProducts();
	}
	
	/**
	 * @return the products
	 */
	public List<Product> getCurrentProducts() {
		return currentProducts;
	}
	
}
