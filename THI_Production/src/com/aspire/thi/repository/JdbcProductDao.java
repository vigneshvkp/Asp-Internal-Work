package com.aspire.thi.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.aspire.thi.domain.Product;

public class JdbcProductDao extends SimpleJdbcDaoSupport implements
		ProductRepository {

	/** Logger for this class and subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	@SuppressWarnings("deprecation")
	public List<Product> getProductList() {
		logger.info("Getting products!");
		// List<Product> products = getSimpleJdbcTemplate().query(
		// "select id, description, price from products",
		// new ProductMapper());
		List<Product> products = getSimpleJdbcTemplate()
				.query("Select id, line_item_text as description, " +
						"assesment_group_id as price from assesment_line_item",
						new ProductMapper());
		return products;
	}

	public void saveProduct(Product prod) {
		//getJdbcTemplate().
		//getSimpleJdbcTemplate().
		logger.info("Saving product: " + prod.getDescription());
		int count = getSimpleJdbcTemplate()
				.update("update products set description = :description, price = :price where id = :id",
						new MapSqlParameterSource()
								.addValue("description", prod.getDescription())
								.addValue("price", prod.getPrice())
								.addValue("id", prod.getId()));
		logger.info("Rows affected: " + count);
	}

	private static class ProductMapper implements
			ParameterizedRowMapper<Product> {

		public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
			Product prod = new Product();
			prod.setId(rs.getInt("id"));
			prod.setDescription(rs.getString("description"));
			prod.setPrice(new Double(rs.getDouble("price")));
			return prod;
		}

	}

}