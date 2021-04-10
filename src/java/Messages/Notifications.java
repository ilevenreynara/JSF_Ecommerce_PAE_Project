/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Messages;

/**
 *
 * @author ileven
 */

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author ileven
 */
public class Notifications {

    public Notifications() {
    }

    public void showInfo(String info, String message) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, info, message);
        FacesContext.getCurrentInstance().addMessage("msgs", msg);
    }
    public void showError(String error, String message) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, error, message);
        FacesContext.getCurrentInstance().addMessage("msgs", msg);
    }
    public void showFailed(String fatal, String message) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, fatal, message);
        FacesContext.getCurrentInstance().addMessage("msgs", msg);
    }
    public void showWarning(String warning, String message) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, warning, message);
        FacesContext.getCurrentInstance().addMessage("msgs", msg);
    }
}
