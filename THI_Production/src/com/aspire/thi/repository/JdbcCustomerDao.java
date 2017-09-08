package com.aspire.thi.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.aspire.thi.domain.Customer;

public class JdbcCustomerDao extends SimpleJdbcDaoSupport implements
		CustomerRepository {

	/** Logger for this class and subclasses */
	private static final Logger LOGGER = Logger.getLogger(JdbcCustomerDao.class);

	private static final String GET_CUSTOMER_LIST = "SELECT id, customer_name, active, created_by, " +
			"created_on, last_modified_by, last_modified_on FROM customer c ORDER BY customer_name";

	private static final String GET_CUSTOMER_WITH_ID = "SELECT id, customer_name, active, created_by, " +
			"created_on, last_modified_by, last_modified_on FROM customer c WHERE id = :id";

	private static final String IS_CUSTOMER_AVBL_FOR_ID = "SELECT COUNT(id) FROM customer c" 
		+ " WHERE id = :id";
	
	private static final String UPDATE_CUSTOMER_BY_ID = "UPDATE customer SET active = :active, " +
		" customer_name = :customer_name, " +
		"last_modified_by = :last_modified_by, last_modified_on = :last_modified_on " +
		"WHERE id = :id";

	@Override
	public List<Customer> getAllCustomers() {
		LOGGER.debug("Getting ALL Customers!");
		List<Customer> customers = getSimpleJdbcTemplate().query(
				GET_CUSTOMER_LIST, new CustomerMapper());
		return customers;
	}

	public Customer getCustomer(int id) {
		LOGGER.debug("Getting Customers for id = " + id);

		Customer customer = getSimpleJdbcTemplate().queryForObject(
				GET_CUSTOMER_WITH_ID, new CustomerMapper(),
				new MapSqlParameterSource().addValue("id", id));

		return customer;

	}

	
	@Override
	public void saveCustomer(Customer customer) {
		LOGGER.debug("Updating Customer back to database " + customer);
		
		if (customer.getId() > 0) {
			SimpleJdbcInsert insertCustomer = new SimpleJdbcInsert(getDataSource()).withTableName("customer");
			
			Map<String, Object> parameters = new HashMap<String, Object>(5);
			parameters.put("id", customer.getId());
			parameters.put("customer_name", customer.getCustomerName());
		    parameters.put("active", customer.isActive());
		    parameters.put("created_by", customer.getCreatedBy());
		    parameters.put("created_on", customer.getCreatedOn());
		    parameters.put("last_modified_by", customer.getLastModifiedBy());
		    parameters.put("last_modified_on", customer.getLastModifiedOn());
		    int value = insertCustomer.execute(parameters);
		    LOGGER.debug("*******************************Customer got executed with value "+value +"****************************");
		} else {
			//
		}
		LOGGER.debug("Updating Customer Completed for customer with id " + customer.getId());
	}
	
	@Override
	public void syncCustomer(Customer customer){
		LOGGER.info("Synchronization  of Customer into database " + customer);
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", customer.getId());
		int count = getSimpleJdbcTemplate().queryForInt(IS_CUSTOMER_AVBL_FOR_ID, params);
		if(count == 0){
			saveCustomer(customer);
		} else {
			int updRows = getSimpleJdbcTemplate().update(
					UPDATE_CUSTOMER_BY_ID,
					new MapSqlParameterSource()
							.addValue("active", customer.isActive())
							.addValue("customer_name", customer.getCustomerName())
							.addValue("last_modified_by",
									customer.getLastModifiedBy())
							.addValue("last_modified_on",
									customer.getLastModifiedOn())
							.addValue("id",
									customer.getId()));
			
			LOGGER.info(customer + " already avbl in Database, so updating record " + updRows +".");
		}
	}
	
	private static class CustomerMapper implements ParameterizedRowMapper<Customer> {

		@Override
		public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
			Customer customer = new Customer();

			customer.setId(rs.getInt("id"));
			customer.setCustomerName(rs.getString("customer_name"));
			customer.setActive(rs.getBoolean("active"));
			customer.setCreatedBy(rs.getString("created_by"));
			customer.setCreatedOn(rs.getDate("created_on"));
			customer.setLastModifiedBy(rs.getString("last_modified_by"));
			customer.setLastModifiedOn(rs.getDate("last_modified_on"));

			return customer;
		}

	}

}