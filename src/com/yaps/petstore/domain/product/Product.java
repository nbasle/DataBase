package com.yaps.petstore.domain.product;

import com.yaps.petstore.domain.PersistentObject;
import com.yaps.petstore.exception.CheckException;
import com.yaps.petstore.exception.FinderException;
import com.yaps.petstore.logging.Trace;
import com.yaps.petstore.domain.category.Category;

/**
* Product.java 
* This class manage a product
*/
public final class Product extends PersistentObject {
	
	//////////////////Private Attributes//////////////////
	private String _productName;
	private String _productDescription;
	private Category _productCategory;
	
	/////////////////////Constructors//////////////////
	{
		_dao = new ProductDAO();
	}
	public Product () {
		
	}
	public Product(final String id){
		_id = id;
	}
	public Product (final String id, final String productName, final String productDescription,  Category productCategory) {
		_id = id;
		_productName = productName;
		_productDescription = productDescription;
		_productCategory = productCategory;
	}
	
	////////////////////Protected methods///////////////////
	protected void checkData() throws CheckException {
		checkId(_id);
		if (_productName == null || "".equals(_productName))
			throw new CheckException("Invalid product name");
		if (_productDescription == null || "".equals(_productDescription))
			throw new CheckException("Invalid product description");
		if (_productCategory == null || "".equals(_productCategory.getId()) || _productCategory.getId() == null)
			throw new CheckException("Invalid product category");
	}
	public String toString () {
		final StringBuffer buf = new StringBuffer();
		buf.append("\n\tProduct {");
		buf.append("\n\tId=").append(_id);
		buf.append("\n\tProduct Name").append(_productName);
		buf.append("\n\tProduct Description").append(_productDescription);
		buf.append("\n\t Product Category").append(_productCategory);		
		buf.append("\n\t}");
		return buf.toString();
	}
	////////////////////Public methods/////////////////////
	public void findByPrimaryKey(final String id) throws FinderException, CheckException {
		final String name = "finByPrimaryKey";
		Trace.entering(_cname, name, id);
		
		//Check integrity
		checkId(id);
		
		final Product temp = (Product) ((ProductDAO)_dao).select(id);
		_id = temp.getId();
		_productName = temp.getName();
		_productDescription = temp.getDescription();
		_productCategory = temp.getCategory();
		
		Trace.exiting(_productName, name);
	}
	/////////////////////Accessors/////////////////////////
	/**
	 * Accessor set
	 * @param productName
	 */
	public void setName(String productName) {
		_productName = productName;
	}
	/**
	 * Accessor get
	 * @return productName
	 */
	public String getName() {
		return _productName;
	}
	/**
	 * Accessor set
	 * @param description
	 */
	public void setDescription(String productDescription) {
		_productDescription = productDescription;
	}
	/**
	 * Accessor get 
	 * @return description
	 */
	public String getDescription() {
		return _productDescription;
	}
	/**
	 *  Accessor set
	 * @param category	 
	 */
	public void setCategory(Category productCategory) {
		_productCategory = productCategory;
	}
	/**
	 * Accessor get
	 * @return category	 
	 */
	public Category getCategory()
	{
		return _productCategory;
	}
	
}