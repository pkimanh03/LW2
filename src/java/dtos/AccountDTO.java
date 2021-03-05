/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;

/**
 *
 * @author pkimanh03
 */
public class AccountDTO implements Serializable {
    public static final String NEW_STATUS = "NEW";
    public static final String DELETED_STATUS = "DELETED";
    public static final String ADMIN_ROLE = "ADMIN";
    public static final String STUDENT_ROLE = "STUDENT";
    
    private String accountEmail, accountPassword, accountName, accountRole, accountStatus;

    public AccountDTO(String accountEmail, String accountName, String accountRole) {
        this.accountEmail = accountEmail;
        this.accountRole = accountRole;
        this.accountName = accountName;
    }
    
    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountRole() {
        return accountRole;
    }

    public void setAccountRole(String accountRole) {
        this.accountRole = accountRole;
    }


    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }
    
}
