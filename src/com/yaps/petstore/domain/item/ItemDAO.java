package com.yaps.petstore.domain.item;

import com.yaps.petstore.domain.PersistentObject;
import com.yaps.petstore.exception.CheckException;
import com.yaps.petstore.exception.DataAccessException;
import com.yaps.petstore.exception.DuplicateKeyException;
import com.yaps.petstore.exception.FinderException;
import com.yaps.petstore.exception.ObjectNotFoundException;
import com.yaps.petstore.persistence.AbstractDataAccessObject;
import com.yaps.petstore.domain.product.Product;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 *  ItemDAO.java
 * This class manages database
 */
final class ItemDAO extends AbstractDataAccessObject {
	
	//////////////////////Private Attributes////////////////
	private static final String TABLE = "T_ITEM";
	private static final String COLUMNS ="ID, NAME, UNITCOST, PRODUCT_FK";
	
	//////////////////////Constructor//////////////////////
	ItemDAO() {
		
	}
	
	/////////////////////Public methods////////////////////
	public PersistentObject select(final String id) throws ObjectNotFoundException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		Item item;
		
		try {
			// Manage a database connection
			connection = getConnection();
			statement = connection.createStatement();
			
			//Select  a row
			final String sql = "SELECT " + COLUMNS + " FROM " + TABLE + " WHERE ID = '" + id + "' ";
			resultSet = statement.executeQuery(sql);
			if (!resultSet.next())
				throw new ObjectNotFoundException();
			
			final Product tempProduct = new Product();
			tempProduct.findByPrimaryKey(resultSet.getString(4));
			// set current data
			item = new Item(resultSet.getString(1), resultSet.getString(2),  resultSet.getDouble(3), tempProduct);						
			
		} catch (SQLException e) {
			// An exception occurs
			displaySqlException(e);
			throw new DataAccessException("Cannot get data from the database", e);						
		} catch (FinderException e){
			throw new ObjectNotFoundException();
			
		} catch (CheckException e){
			throw new ObjectNotFoundException();
		}  finally {
		
			try {
				if (resultSet != null) resultSet.close();
				if (statement != null) statement.close();
				if (connection != null) connection.close();				
			} catch(SQLException e){
				displaySqlException("Cannot close connection",e);
				throw new DataAccessException("Cannot close the database connection",e);				
			}
		}
		return item;
	}
	public Collection selectAll() throws ObjectNotFoundException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		final Collection items = new ArrayList();
		try {
			// Manage a database connection
			connection = getConnection();
			statement = connection.createStatement();
			
			//Select a row
			final String sql = "SELECT " + COLUMNS + " FROM " + TABLE;
			resultSet = statement.executeQuery(sql);
			
			while(resultSet.next()) {
				//Add data to collection
				Product tempProduct = new Product();
				tempProduct.findByPrimaryKey(resultSet.getString(4));
				final Item item = new Item(resultSet.getString(1), resultSet.getString(2) , resultSet.getDouble(3),tempProduct);
				items.add(item);
				
			}
			if(items.isEmpty())
				throw new ObjectNotFoundException();
		} catch(SQLException e) {
			displaySqlException(e);
			throw new DataAccessException("Cannot get data from the database",e);
		} catch (FinderException e){
			throw new ObjectNotFoundException();
			
		} catch (CheckException e){
			throw new ObjectNotFoundException();
		}  finally {
			//Close connection
			try {
				if (resultSet != null) resultSet.close();
				if (statement != null) statement.close();
				if (connection != null) connection.close();
				
			} catch (SQLException e) {
				displaySqlException("Cannot close connection",e);
				throw new DataAccessException("Cannot close the database connection",e);
			}
		}
			return items;
		}
		public void insert(final PersistentObject object) throws DuplicateKeyException {
			Connection connection = null;
			Statement statement = null;
			ResultSet resultSet = null;
			
			final Item item = (Item) object;
			try {
				// get a data connection
				connection = getConnection();
				statement = connection.createStatement();
				
				//Inserts a Row
				final String sql = "INSERT INTO " + TABLE + "(" + COLUMNS + ") VALUES ('" + item.getId() + "', '" + item.getName() + "', '" + item.getUnitCost() + "', '" + item.getProduct().getId() + "' )";
				statement.executeUpdate(sql);
				
			} catch (SQLException e) {
				// data already in database
				if (e.getErrorCode() == DATA_ALREADY_EXIST) {
					throw new DuplicateKeyException();
				} else {
					
					//Another exception occurs
					displaySqlException(e);
					throw new DataAccessException("Cannot insert data into the database",e);
					
				}								
			} finally {
				//Close
				try {
					if (statement != null) statement.close();
					if (connection != null) connection.close();
				} catch (SQLException e) {
					displaySqlException("Cannot close connection", e);
					throw new DataAccessException("Cannot close the database connection",e);
					
				}
			}
		}
		
	public void update(final PersistentObject object) throws ObjectNotFoundException {
	 Connection connection = null;
	 Statement statement = null;
	 final Item item = (Item) object;
	 try {
	 	//Manage the database connection
	 	connection = getConnection();
	 	statement = connection.createStatement();
	 	
	 	//Update a row
	 	final String sql = "UPDATE " + TABLE + " SET NAME = '" + item.getName() +  "', UNITCOST = '" + item.getUnitCost() + "', PRODUCT_FK = '"  + item.getProduct().getId() + "' WHERE ID = '" + item.getId() + "' ";
	 	if (statement.executeUpdate(sql) == 0)
	 		throw new ObjectNotFoundException();	 
	 	
	 } catch (SQLException e) {
	 	// An exception occurs
	 	displaySqlException(e);
	 	throw new DataAccessException("Cannot update data into the database", e);
	 } finally {
	 	//Close
	 	try {
	 		if (statement != null ) statement.close();
	 		if (connection !=  null) connection.close();	 	
	 		
	 	} catch (SQLException e) {
	 		displaySqlException("Cannot close connection",e);
	 		throw new DataAccessException("Cannot close the database connection", e);
	 	}
	 }
	}
	public void remove (final String id) throws ObjectNotFoundException {
		Connection connection = null;
		Statement statement = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			
			//Delete a row
			final String sql = "DELETE FROM " + TABLE + " WHERE ID = '" + id + "'";
			if (statement.executeUpdate(sql) == 0)
				throw new ObjectNotFoundException();
			
		} catch (SQLException e) {
			//A error occurs
			displaySqlException(e);
			throw new DataAccessException("Cannot remove data into the database", e);
		} finally {			
			//Close
			try {
				if (statement != null) statement.close();
				if (connection != null) connection.close();
				
			} catch (SQLException e) {
				displaySqlException("Cannot close connection",e);
				throw new DataAccessException("Cannot close the database connection",e);
			}
		}
	
	}
}


