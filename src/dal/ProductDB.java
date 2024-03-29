package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Product;

public class ProductDB implements ProductDBIF {
	
	private static final String SELECT_PRODUCT_STATEMENT = "SELECT * FROM Product WHERE id = ?";
	private PreparedStatement selectProductStatement;
	
	private static final String UPDATE_STATEMENT = "UPDATE Product SET name = ?,"
			+ " currentStock = ?, price = ? WHERE id = ?";

	private static final String SELECT_PRODUCT_BY_NAME_STATEMENT = "SELECT * FROM Product";
	private PreparedStatement searchProductByNameStatement;
	
	private static final String INSERT_STATEMENT = "INSERT INTO "
			+ "Product(name, currentStock, price) VALUES(?, ?, ?)";
	
	

	public ProductDB() {
	}
	
	/**
	 * Search product by id
	 * @param productId The id of the product
	 * @return Product with the id
	 */
	public Product searchProduct(int productId) {
		
		Product product = null;

		try {
			try {
				selectProductStatement = DbConnection.getInstance().getConnection()
						.prepareStatement(SELECT_PRODUCT_STATEMENT);				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			selectProductStatement.setInt(1, productId);
			ResultSet rs = selectProductStatement.executeQuery();
			if (rs.next()) {
				product = buildObject(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return product;
	}
	
	/**
	 * Update existing product with the current one based on id
	 * @param product The product that is updated to
	 * @return True if the product was updated successfully
	 */
	public boolean updateProduct(Product product) {
		boolean retVal = false;
		
		try {
			PreparedStatement updateProductStatement = DbConnection.getInstance().getConnection()
					.prepareStatement(UPDATE_STATEMENT);
		

			// Product
			updateProductStatement.setString(1, product.getName());
			updateProductStatement.setInt(2, product.getCurrentStock());
			updateProductStatement.setBigDecimal(3, product.getPrice());
			
			// Where id
			updateProductStatement.setInt(4, product.getId());
	
			updateProductStatement.executeUpdate();
			
			retVal = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return retVal;
	}
	
	/**
	 * Insert product
	 * @param product The product that is inserted
	 * @return Product with id
	 */
	public Product insertProduct(Product product) {
		
		try {
			PreparedStatement insertProductStatement = DbConnection.getInstance().getConnection()
					.prepareStatement(INSERT_STATEMENT, Statement.RETURN_GENERATED_KEYS);
		

			// Product
			insertProductStatement.setString(1, product.getName());
			insertProductStatement.setInt(2, product.getCurrentStock());
			insertProductStatement.setBigDecimal(3, product.getPrice());
	
			product.setId(DbConnection.getInstance().executeSqlInsertWithIdentity(insertProductStatement));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return product;
	}
	
	/**
	 * Build object from the result set
	 * @param rs Result set that contains the object information
	 * @return Product built from the result set
	 * @throws SQLException
	 */
	private Product buildObject(ResultSet rs) throws SQLException {
		return new Product(rs.getInt("currentStock"), rs.getDouble("price"), rs.getInt("id"), rs.getString("name"));
	}
	
	/**
	 * Build list of object from the result set
	 * @param rs Result set that contains the object information
	 * @return List of the product that the result set contained
	 * @throws SQLException
	 */
	private List<Product> buildObjects(ResultSet rs) throws SQLException {
		List<Product> productList = new ArrayList<>();
		while(rs.next()) {
			productList.add(buildObject(rs));
		}
		return productList;
	}

	/**
	 * Get all products from the database
	 * @Return List of all products
	 */
	@Override
	public List<Product> getAllProducts() {
		List<Product> productList = new ArrayList<>();
		try {
			searchProductByNameStatement = DbConnection.getInstance().getConnection()
					.prepareStatement(SELECT_PRODUCT_BY_NAME_STATEMENT);
			ResultSet rs = searchProductByNameStatement.executeQuery();
			productList = buildObjects(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return productList;
	}
	
	/**
	 * @return the insertStatement
	 */
	public static String getInsertStatement() {
		return INSERT_STATEMENT;
	}
}
