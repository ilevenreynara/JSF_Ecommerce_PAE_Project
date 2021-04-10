/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Controller.CustomerSB;
import Mail.SendEmail;
import Messages.Notifications;
import Model.Customers;
import Validator.EmailVerification;
import java.io.Serializable;
import java.util.Random;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author ileven
 */
@Named(value = "signupMB")
@SessionScoped
public class SignupMB implements Serializable {

    /**
     * Creates a new instance of SignupMB
     */
    @EJB
    private CustomerSB ssb;
    private Customers newCustomer;
    private Notifications message = new Notifications();
    private String password1 = "";
    private String password2 = "";

    public SignupMB() {
        newCustomer = new Customers();
    }

    public void reset() {
        newCustomer = new Customers();
    }

    public Customers getNewCustomer() {
        return newCustomer;
    }

    public void setNewCustomer(Customers newCustomer) {
        this.newCustomer = newCustomer;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String resendActivation() {
        Random random = new Random();
        random.nextInt(999999);
        if (ssb.checkExistingEmail(newCustomer.getEmail())) {
            newCustomer = ssb.setLoggedCustomer(newCustomer.getEmail());
        }else {
            message.showError("Email Doesn't Exist!", newCustomer.getEmail() + " is not registered");
            return "resendActivation";
        }
        newCustomer.setToken(DigestUtils.md5Hex("" + random));
        if (ssb.updateCustomer(newCustomer)) {
            SendEmail register = new SendEmail(newCustomer.getEmail(), newCustomer.getToken());
            register.sendMail();
            message.showInfo("Activation Link Sent", "Check your email for account activation!");
            reset();
            return "login";
        }else{
            message.showError("Failed to send activation", newCustomer.getEmail());
            reset();
            return "resendActivation";
        } 
    }

    public String insertNewCust() throws Exception {

        if (password1.equals(password2)) {
            if (!ssb.checkExistingEmail(newCustomer.getEmail())) {
                Random random = new Random();
                random.nextInt(999999);
                newCustomer.setToken(DigestUtils.md5Hex("" + random));
                newCustomer.setPassword(password1);
                newCustomer.setEnabled(Boolean.FALSE);
                ssb.insertCustomer(newCustomer);
                SendEmail register = new SendEmail(newCustomer.getEmail(), newCustomer.getToken());
                register.sendMail();
                message.showInfo("Registration Complete", "Check your email for account verification!");
                reset();
                return "login";
            } else {
                message.showError("Email Exist Error!", "Email is Used!");
            }
        } else {
            message.showError("Password Mismatched!", "Password is not same!");
        }

        return "signup";
    }

}
