package com.suncoastsoftware.estimateepro.model;

/**
 * Created by Command Center on 10/8/2017.
 */

public class Customer {

    public String companyName = "";
    public String customerID = "";
    public String contactName = "";
    public String phone = "";

    public Customer(String companyName, String customerID, String contactName, String phone) {
        this.companyName = companyName;
        this.customerID = customerID;
        this.contactName = contactName;
        this.phone = phone;
    }

    public Customer() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
