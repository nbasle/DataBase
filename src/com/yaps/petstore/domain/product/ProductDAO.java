package com.yaps.petstore.domain.product;

import com.yaps.petstore.domain.PersistentObject;
import com.yaps.petstore.domain.category.Category;
import com.yaps.petstore.exception.CheckException;
import com.yaps.petstore.exception.DataAccessException;
import com.yaps.petstore.exception.DuplicateKeyException;
import com.yaps.petstore.exception.FinderException;
import com.yaps.petstore.exception.ObjectNotFoundException;
import com.yaps.petstore.persistence.AbstractDataAccessObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * ProductDAO.java
 * this class manage database data
 */
final class ProductDAO extends AbstractDataAccessObject {
	//////////////////Private Attributes///////////////////
	private static final String TABLE ="T_PRODUCT";
	private static final String COLUMNS ="ID, NAME, DESCRIPTION, CATEGORY_FK";
	
	///////////////////Constructors///////////////////
	ProductDAO() {
		
	}
	//////////////////Public methods/////////////////
	public PersistentObject select (final String id) throws ObjectNotFoundException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		Product product;
		try {
			//Manage database conection
			connection = getConnection();
			statement = connection.createStatement();
			
			// Select a row
			final String sql ="SELECT " + COLUMNS + " FROM "+ TABLE + " WHERE ID = '" + id + "' ";
			resultSet = statement.executeQuery(sql);
			if (!resultSet.next())
				throw new ObjectNotFoundException();
			
			// Set data to current object
			final Category tempCat = new Category();
			
			
			
			tempCat.findByPrimaryKey(resultSet.getString(4));
			
				
			
			product = new Product(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),tempCat);			
			
		} catch (SQLException e) {
			//manage exception
			displaySqlException(e);
			throw new DataAccessException("Cannot get data from the database", e);
		} catch (FinderException e){
			throw new ObjectNotFoundException();
			
		} catch (CheckException e){
			throw new ObjectNotFoundException();
		} finally {
			//Close
			try {
				if (resultSet != null) resultSet.close();
				if (statement != null) statement.close();
				if (connection != null) connection.close();
				
			} catch (SQLException e) {
				displaySqlException("Cannot close connection" , e);
				throw new DataAccessException("Cannot close the database connection", e);
			}
		}
		return product;
	}
	public Collection selectAll() throws ObjectNotFoundException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		final Collection products = new ArrayList();
		
		try {
			// Manage a database connection
			connection = getConnection();
			statement = connection.createStatement();
			
			//Select a Row
			final String sql = "SELECT " + COLUMNS + " FROM "+ TABLE;
			resultSet = statement.executeQuery(sql);
			
			while(resultSet.next()) {
				final Category tempCat = new Category();
											
				tempCat.findByPrimaryKey(resultSet.getString(4));
				
				//add data to the collection 
				final Product product = new Product(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), tempCat);
				products.add(product);				
			}
			if (products.isEmpty())
				throw new ObjectNotFoundException();
			
		} catch (SQLException e) {
			displaySqlException(e);
			throw new DataAccessException("Cannot get data from the database", e);
			
		} catch (FinderException e){
			throw new ObjectNotFoundException();
			
		} catch (CheckException e){
			throw new ObjectNotFoundException();
		} finally {
		
			try {
				if(resultSet != null) resultSet.close();
				if(statement != null) statement.close();
				if (connection != null) connection.close();
				
			} catch (SQLException e) {
				displaySqlException("Cannot close connection",e);
				throw new DataAccessException("Cannot close the database connection" , e);
			}
			
		}
		return products;
	}
	public void insert(final PersistentObject object) throws DuplicateKeyException {
	 Connection connection = null;
	 Statement statement = null;
	 final Product product = (Product) object;
	 try {
	 	//Manage database
	 	connection = getConnection();
	 	statement = connection.createStatement();
	 	
	 	//Inserts a Row
	 	final String sql = "INSERT INTO " + TABLE + "(" + COLUMNS + ") VALUES ('" + product.getId() + "', '" + product.getName() + "', '" + product.getDescription() + "', '" +product.getCategory().getId() + "' )";
	 	statement.executeUpdate(sql);
	 	
	 } catch (SQLException e) {
	 	//if the data is already inserted
	 	if (e.getErrorCode() == DATA_ALREADY_EXIST) {
	 		throw new DuplicateKeyException();
	 	} else {
	 		// Another SQL exception
	 		displaySqlException(e);
	 		throw new DataAccessException("Cannot insert data into the database",e);	 		
	 	} 
	 } finally {
	 	try {
	 		//Close database connection
	 	if (statement != null) statement.close();
	 	if (connection != null) connection.close();
	 	} catch (SQLException e) {
	 		
	 	displaySqlException("Cannot close connection", e);
	 	throw new DataAccessException("Cannot close the database connection", e);
	 }
		
  }
}
	public void update (final PersistentObject object) throws ObjectNotFoundException {
		Connection connection = null;
		Statement statement = null;
		final Product product = (Product) object;
		try {
			// Manage database exception
			connection = getConnection();
			statement = connection.createStatement();
			
			//Update row
			final String sql = "UPDATE " + TABLE + " SET NAME = '" + product.getName() + " ', DESCRIPTION = '" + product.getDescription() + "', CATEGORY_FK = '" + product.getCategory().getId() + "' WHERE ID = '" + product.getId() + "' ";
			
			if (statement.executeUpdate(sql) == 0)
				throw new ObjectNotFoundException();
		} catch (SQLException e) {
			//An exception occurs
			displaySqlException(e);
			throw new DataAccessException("Cannot Update data into database", e);
		} finally {
			try {
				if (statement != null) statement.close();
				if (connection != null) connection.close();				
			} catch (SQLException e) {
				displaySqlException("Cannot close connection",e);
				throw new DataAccessException("Cannot close the database connection",e);
			}			
		}				
	}
	public void remove (final String id) throws ObjectNotFoundException {
		Connection connection = null;
		Statement statement = null;
		try {
			//Manage a database exception
			connection = getConnection();
			statement = connection.createStatement();
			
			//Delete a row
			final String sql = "DELETE FROM " + TABLE + " WHERE ID = '" + id + "'";
			if (statement.executeUpdate(sql) == 0)
				throw new ObjectNotFoundException();
			
		} catch (SQLException e) {
			// An exception occurs
			displaySqlException(e);
			throw new DataAccessException("Cannot remove data into the database",e);			
		} finally {
			//Close connection
			try {
				if (statement != null) statement.close();
				if(connection != null) connection.close();				
			} catch (SQLException e) {
				displaySqlException("Cannot close connection",e);
				throw new DataAccessException("Cannot close the database connection",e);
			}
		}						
	}
}





