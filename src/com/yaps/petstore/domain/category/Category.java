package com.yaps.petstore.domain.category;

import com.yaps.petstore.domain.PersistentObject;
import com.yaps.petstore.exception.CheckException;
import com.yaps.petstore.exception.FinderException;
import com.yaps.petstore.logging.Trace;

/**
 *  Category.java
 *  Manage category
 */
public final class Category extends PersistentObject {
	
	///////////////////Private attributes//////////////////	
	private String _categoryName;
	private String _categoryDescription;
	
	//////////////////Consructor////////////////////////
	{
		_dao = new CategoryDAO();
	}
	/**
	 * Constructor with no parameter
	 */
	public Category(){
		
	}
	/**
	 *  Constructor with one parameter
	 * @param categoryId
	 */
	public Category(final String category){
		_id = category;
	}
	/**
	 *  Constructor with three parameters
	 * @param categoryId
	 * @param categoryName
	 * @param categoryDescription
	 */
	public Category(final String categoryId, final String categoryName, final String categoryDescription){
	 _id = categoryId;
	 _categoryName = categoryName;
	 _categoryDescription = categoryDescription;
	}
	//////////////////Private methods///////////////////
	//////////////////Public methods////////////////////
	///////////Method to implement for interface///////
	/////Protected/////
	protected void checkData() throws CheckException {
		checkId(_id);
		if (_categoryName == null || "".equals(_categoryName))
			throw new CheckException("Invalid category name");
		if (_categoryDescription == null || "".equals(_categoryDescription))
			throw new CheckException("Invalid category description");
		
	}
	/////Public/////
	public String toString(){
		final StringBuffer buf = new StringBuffer();
		buf.append("\n\tCategory {");
		buf.append("\n\t\tId=").append(_id);
		buf.append("\n\t\tCategory Name").append(_categoryName);
		buf.append("\n\t\tCategory Description").append(_categoryDescription);
		buf.append("\n\t}");
		return buf.toString();
		
	}
	public void findByPrimaryKey(final String id) throws FinderException, CheckException {
		final String name = "findByPrimaryKey";
		Trace.entering(_cname, name, id);
		
		//Check id integrity
		checkId(id);
		
		// Use the DAO access
		final Category temp = (Category) _dao.select(id);
		_id = temp.getId();
		_categoryName = temp.getName();
		_categoryDescription = temp.getDescription();
		
		Trace.exiting(_categoryName, name);
		
	}
	//////////////////Accessors///////////////////
	/**
	 * Accessor set
	 * @param categoryName
	 */
	public void setName(final String categoryName){
		_categoryName = categoryName;
	}
	/**
	 * Accessor get
	 * @return categoryName
	 */
	public String getName() {
		return _categoryName;
	}
	/**
	 * Accessor set
	 * @param categoryDescription	 
	 */
	public void setDescription(String categoryDescription)
	{
		_categoryDescription = categoryDescription;
	}
	/**
	 * Accessor get
	 * @return categoryDescription
	 */
	public String getDescription () {
		return _categoryDescription;
	}
	
	
}

