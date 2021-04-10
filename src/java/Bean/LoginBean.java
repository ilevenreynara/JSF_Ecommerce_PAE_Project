/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Controller.CustomerSB;
import Messages.Notifications;
import Model.Administrators;
import Model.Customers;
import Validator.EmailVerification;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ileven
 */
@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    /**
     * Creates a new instance of LoginBean
     */
    @EJB
    private CustomerSB lsb;
    protected static Customers loggedCustomer;
    private Administrators loggedAdmin;
    private List<Customers> cusData;
    private Notifications message = new Notifications();
    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    String dir = ec.getRealPath("/");

    public String login() {
        if (lsb.customerLogin(loggedCustomer.getEmail(), loggedCustomer.getPassword())) {
            setLoggedCustomer(lsb.setLoggedCustomer(loggedCustomer.getEmail()));
            if (lsb.isEnabled(loggedCustomer.getEmail())) {
                return "customerHome";
            } else {
                reset();
                message.showError("Login Failed", "Account is not verified yet! Please check your email");
                return "login";
            }
        }
        message.showError("Login Failed!", "Username / Password Incorrect!");
        return "login";
    }

    public String logout() {
        loggedCustomer = new Customers();
        loggedAdmin = new Administrators();
        if (OrderMB.cartData != null) {
            OrderMB.cartData.clear();
            CustomerMB.sdData.clear();
        }
        return "login";
    }

    public String loginAdmin() {
        if (lsb.adminLogin(loggedAdmin.getUsername(), loggedAdmin.getPassword())) {
            System.out.println(dir);
            return "index";
        }
        message.showError("Admin Login Failed!", "Admin Username / Password Incorrect!");

        return "login";
    }

    public LoginBean() {
        loggedAdmin = new Administrators();
        loggedCustomer = new Customers();
        cusData = new ArrayList<>();
    }

    public void reset() {

        loggedAdmin = new Administrators();
        loggedCustomer = new Customers();
        cusData = new ArrayList<>();
    }

    public Administrators getLoggedAdmin() {
        return loggedAdmin;
    }

    public void setLoggedAdmin(Administrators loggedAdmin) {
        this.loggedAdmin = loggedAdmin;
    }

    public Customers getLoggedCustomer() {
        return loggedCustomer;
    }

    public void setLoggedCustomer(Customers loggedCustomer) {
        this.loggedCustomer = loggedCustomer;
    }

    public List<Customers> getCusData() {
        if (cusData.isEmpty()) {
            cusData = lsb.getAllCustomers();
        }
        return cusData;
    }

}
/*
Named Query Backups
 */
//@NamedQuery(name = "Customers.login", query = "SELECT c FROM Customers c WHERE c.email = :email and c.password = :password")
//@NamedQuery(name = "Administrators.login", query = "SELECT a FROM Administrators a WHERE a.username = :username and a.password = :password")
