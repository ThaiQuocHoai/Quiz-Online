/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoaitq.user;

import java.io.Serializable;

/**
 *
 * @author QH
 */
public class UserErrorDTO implements Serializable{
    private String usernameError;
    private String passwordError;
    private String confirmError;
    private String fullnameError;

    public UserErrorDTO() {
    }

    public UserErrorDTO(String usernameError, String passwordError, String confirmError, String fullnameError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.confirmError = confirmError;
        this.fullnameError = fullnameError;
    }

    public String getUsernameError() {
        return usernameError;
    }

    public void setUsernameError(String usernameError) {
        this.usernameError = usernameError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public String getConfirmError() {
        return confirmError;
    }

    public void setConfirmError(String confirmError) {
        this.confirmError = confirmError;
    }

    public String getFullnameError() {
        return fullnameError;
    }

    public void setFullnameError(String fullnameError) {
        this.fullnameError = fullnameError;
    }
    
    
}
