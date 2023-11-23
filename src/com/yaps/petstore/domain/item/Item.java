package com.yaps.petstore.domain.item;

import com.yaps.petstore.domain.PersistentObject;
import com.yaps.petstore.domain.product.Product;
import com.yaps.petstore.exception.CheckException;
import com.yaps.petstore.exception.FinderException;
import com.yaps.petstore.logging.Trace;

/**
* Item.java
*  This class manages item
*/
public final class Item extends PersistentObject {
	
	///////////////////Private attributes/////////////////
	private String _itemName;	
	private double _itemPrice;
	private Product _itemProduct;
	
	///////////////////Public constructors////////////////
	
	{
		_dao = new ItemDAO();
	}
	
	public Item() {
		
	}
	
	public Item (final String id) {
		_id = id;
		
	}
	 public Item(final String id, final String itemName, final double itemPrice,final Product itemProduct){
		_id = id;
		_itemName = itemName;		
		_itemPrice = itemPrice;
		_itemProduct = itemProduct;
		
	}
	 ////////////////////Protected methods////////////////
	 protected void checkData() throws CheckException {
	 	checkId(_id);
	 	if (_itemName== null || "".equals(_itemName))
	 		throw new CheckException("Invalid item name");	 	
	 	if ( _itemPrice == 0)
	 		throw new CheckException("Invalid item price");
	 	if (_itemProduct == null || "".equals(_itemProduct.getId()) || _itemProduct.getId() == null)
	 		throw new CheckException("Invalid item product");
	 }
	///////////////////Public methods///////////////
	 
	 
	 public void findByPrimaryKey(final String id) throws FinderException, CheckException {
	 	final String mname = "findByPrimaryKey";
	 	Trace.entering(_cname, mname, id);
	 	
	 	//Check integrity
	 	checkId(id);
	 	
	 	// Use DAO
	 	final Item temp = (Item) _dao.select(id);
	 	
	 	// Sets data to current object
	 	_id = temp.getId();
	 	_itemName = temp.getName();	 
	 	_itemPrice = temp.getUnitCost();
	 	_itemProduct = temp.getProduct();
	 	
	 	Trace.exiting(_cname, mname);
	 }
	 /////////////////Public methods//////////////////
	 public String toString() {
	 	final StringBuffer buf = new StringBuffer();
	 	buf.append("\n\tItem {");
	 	buf.append("\n\tId=").append(_id);
	 	buf.append("\n\tName =").append(_itemName);	 
	 	buf.append("\n\tPrice =").append(_itemPrice);
	 	buf.append("\n\tProduct =").append(_itemProduct);
	 	buf.append("\n\t}");
	 	return buf.toString();
	 	
	 	
	 }
	 ///////////////Accessors////////////////////////
	 /**
	  * Accessor set
	  * @param name
	  */
	 public void setName(String itemName) {
	 	_itemName =itemName;
	 }
	 /** 
	  * Acccessor get
	  * @return name
	  */
	 public String getName()
	 {
	 	return _itemName;
	 }
	
	 /**
	  * Accessor set
	  * @param price
	  */
	 public void setUnitCost(double itemPrice) {
	 	_itemPrice = itemPrice;
	 }
	 /**
	  * Accessor
	  *@return price
	  */
	 public double getUnitCost() {
	 	return _itemPrice;
	 }
	 /**
	  * Accessor set 
	  * @param product
	  */
	 public void setProduct(Product itemProduct){
	 	_itemProduct = itemProduct;
	 }
	 /**
	  * Accessor get
	  * @return itemProduct
	  */
	 public Product getProduct() {
	 	return _itemProduct;
	 }
	 
	
	
}