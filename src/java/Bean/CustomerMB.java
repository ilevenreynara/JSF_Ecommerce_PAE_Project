/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Controller.CustomerSB;
import Encryption.MD5;
import Model.Customers;
import Model.ShippingDetails;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;


/**
 *
 * @author ileven
 */
@Named(value = "customerMB")
@SessionScoped
public class CustomerMB implements Serializable {

    /**
     * Creates a new instance of CustomerMB
     */
    @EJB
    private CustomerSB csb;
    private Customers customer;
    private ShippingDetails sd;
    private List<Customers> custData;
    public static List<ShippingDetails> sdData;
    private boolean editMode = false;
    private MD5 encryptor;
    public CustomerMB() {
        sd = new ShippingDetails();
        editMode = false;
        customer = new Customers();
        custData = new ArrayList<>();
        sdData = new ArrayList<>();
    }
    public void reset() {
        sd = new ShippingDetails();
        editMode = false;
        customer = new Customers();
        custData = new ArrayList<>();
        sdData = new ArrayList<>();
        
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public ShippingDetails getSd() {
        return sd;
    }

    public void setSd(ShippingDetails sd) {
        this.sd = sd;
    }
    
    
    public Customers getCustomer() {
        return customer;
    }
    public void setCustomer(Customers customer) {
        this.customer = customer;
    }

    
    public void setSelectedCustomer(Customers selectedCustomer) {
        editMode = true;
        this.customer = selectedCustomer;
    }

    
    
    public List<Customers> getCustData() {
        if(custData.isEmpty()){
            custData = csb.getAllCustomers();
        }
        return custData;
    }
    public void insertCustomer(){
        
        if(csb.insertCustomer(customer)){
            System.out.println("Worked");
        }else{
            System.out.println("Failed");
        }
        reset();
    }
    public void updateCustomer(){
        System.out.println(customer.toString());
        if(csb.updateCustomer(customer)){
            System.out.println("Worked");
        }else{
            System.out.println("Failed");
        }
        reset();
    }
    public void deleteCustomer(Customers c){
        csb.deleteCustomer(c);
        reset();
    }
    
//    public boolean activateCustomer(String email, String token){
//        if(csb.enableCustomer(email, token)){
//            return true;
//        }else{
//            return false;
//        }
//       
//    }

    public List<ShippingDetails> getSdData() {
        if(sdData.isEmpty()){
            sdData = csb.getAllShipping(LoginBean.loggedCustomer);
        }
        return sdData;
    }
    public void setSelectedSd(ShippingDetails s){
        this.sd = s;
        this.editMode = true;
    }
    public void insertShippingDetails(){
        sd.setCustomerId(LoginBean.loggedCustomer);
        csb.insertShippingDetails(sd);
        reset();
    }
    public void deleteShippingDetails(ShippingDetails det){
        csb.deleteShippingDetails(det);
        reset();
    }
    public void updateShippingDetails(){
        csb.updateShippingDetails(sd);
        reset();
    }
}
