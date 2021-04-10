/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Model.Customers;
import java.util.ArrayList;

/**
 *
 * @author Ileven
 */
public class TestMethodObject {
    
    private Customers customer;
    private ArrayList<Customers> customerList= new ArrayList<>();
    
    public static void main(String[] args) {
        
    }

    public Customers getCustomer() {
        return customer;
    }
    public Customers getIleven(){
        for (Customers customers : customerList) {
            if(customers.getFirstName().equalsIgnoreCase("Ileven")){
                return customers;
            }
        }
        return null;
    }
    public Customers getCustomerYangNomorTeleponnyaLebihPanjang(Customers a, Customers b){
        if(a.getPhone().length() > b.getPhone().length()){
            return a;
        }
        return b;
    }
    public ArrayList<Customers> getMarriedCustomers(){
        ArrayList<Customers> marriedCust = new ArrayList<>();
        for (Customers customers : customerList) {
            if(customers.getMaritalStatus().equalsIgnoreCase("Married")){
                marriedCust.add(customer);
            }
        }
        return marriedCust;
    }
    public void setCustomer(Customers customer) {
        this.customer = customer;
    }
    
}
