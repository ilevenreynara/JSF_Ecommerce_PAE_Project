/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mail;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author ileven
 */
public class SendEmail {
    private String userMail;
    private String userToken;

    public SendEmail(String userMail, String userToken) {
        this.userMail = userMail;
        this.userToken = userToken;
    }
    
    public void sendMail(){
        String email = "ryzeonlinestore@gmail.com";
        String pwd = "ukm12345*";
        Properties properties = new Properties();
        
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.user", email);
        
        Session session = Session.getInstance(properties);
        
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(userMail));
            message.setSubject("Account Activation");
            message.setText("Verification Link...");
            message.setText("Activate your account by clicking on this link :: "+"http://localhost:8080/Ecommerce-1773019/ActivateAccount?key1="+userMail+"&key2="+userToken);
            Transport t = session.getTransport("smtp");
            t.connect(email, pwd);
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (Exception e) {
            System.out.println("Sending Email....." + e);
        }
    }
}
