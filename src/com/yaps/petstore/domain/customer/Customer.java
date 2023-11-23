package com.yaps.petstore.domain.customer;

import com.yaps.petstore.domain.PersistentObject;
import com.yaps.petstore.exception.CheckException;
import com.yaps.petstore.exception.FinderException;
import com.yaps.petstore.logging.Trace;

/**
 * This class represents a customer for the YAPS company.
 */
public final class Customer extends PersistentObject {

    // ======================================
    // =             Attributes             =
    // ======================================
    private String _firstname;
    private String _lastname;
    private String _telephone;
    private String _street1;
    private String _street2;
    private String _city;
    private String _state;
    private String _zipcode;
    private String _country;

    /** @link dependency 
     * @stereotype instantiate*/
    /*# CustomerDAO lnkCustomerDAO; */
    // ======================================
    // =            Constructors            =
    // ======================================
    {
        _dao = new CustomerDAO();
    }

    public Customer() {
    }

    public Customer(final String id) {
        _id = id;
    }

    public Customer(final String id, final String firstname, final String lastname) {
        _id = id;
        _firstname = firstname;
        _lastname = lastname;
    }

    // ======================================
    // =           Business methods         =
    // ======================================
    public void findByPrimaryKey(final String id) throws FinderException, CheckException {
        final String mname = "findByPrimaryKey";
        Trace.entering(_cname, mname, id);

        // Checks data integrity
        checkId(id);

        // Uses the DAO to access the persistent layer
        final Customer temp = (Customer) _dao.select(id);

        // Sets data to current object
        _id = temp.getId();
        _firstname = temp.getFirstname();
        _lastname = temp.getLastname();
        _telephone = temp.getTelephone();
        _street1 = temp.getStreet1();
        _street2 = temp.getStreet2();
        _city = temp.getCity();
        _state = temp.getState();
        _zipcode = temp.getZipcode();
        _country = temp.getCountry();

        Trace.exiting(_cname, mname);
    }

    protected void checkData() throws CheckException {
        checkId(_id);
        if (_firstname == null || "".equals(_firstname))
            throw new CheckException("Invalid first name");
        if (_lastname == null || "".equals(_lastname))
            throw new CheckException("Invalid last name");
    }

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    public String getFirstname() {
        return _firstname;
    }

    public void setFirstname(final String firstname) {
        _firstname = firstname;
    }

    public String getLastname() {
        return _lastname;
    }

    public void setLastname(final String lastname) {
        _lastname = lastname;
    }

    public String getTelephone() {
        return _telephone;
    }

    public void setTelephone(final String telephone) {
        _telephone = telephone;
    }

    public String getStreet1() {
        return _street1;
    }

    public void setStreet1(final String street1) {
        _street1 = street1;
    }

    public String getStreet2() {
        return _street2;
    }

    public void setStreet2(final String street2) {
        _street2 = street2;
    }

    public String getCity() {
        return _city;
    }

    public void setCity(final String city) {
        _city = city;
    }

    public String getState() {
        return _state;
    }

    public void setState(final String state) {
        _state = state;
    }

    public String getZipcode() {
        return _zipcode;
    }

    public void setZipcode(final String zipcode) {
        _zipcode = zipcode;
    }

    public String getCountry() {
        return _country;
    }

    public void setCountry(final String country) {
        _country = country;
    }

    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("\n\tCustomer {");
        buf.append("\n\t\tId=").append(_id);
        buf.append("\n\t\tFirst Name=").append(_firstname);
        buf.append("\n\t\tLast Name=").append(_lastname);
        buf.append("\n\t\tTelephone=").append(_telephone);
        buf.append("\n\t\tStreet 1=").append(_street1);
        buf.append("\n\t\tStreet 2=").append(_street2);
        buf.append("\n\t\tCity=").append(_city);
        buf.append("\n\t\tState=").append(_state);
        buf.append("\n\t\tZipcode=").append(_zipcode);
        buf.append("\n\t\tCountry=").append(_country);
        buf.append("\n\t}");
        return buf.toString();
    }
}
