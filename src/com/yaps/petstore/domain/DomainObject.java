package com.yaps.petstore.domain;

/**
 * Every domain object should extend this abstract class.
 */
public abstract class DomainObject {

    // ======================================
    // =             Attributes             =
    // ======================================
    // Used for logging
    protected final transient String _cname = this.getClass().getName();

}
