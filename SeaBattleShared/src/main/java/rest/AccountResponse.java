package rest;

import java.util.List;

public class AccountResponse {
    public boolean success;

    public List<AccountDTO> accounts;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success){
        this.success = success;
    }

    public List<AccountDTO> getAccounts(){
        return accounts;
    }

    public void setAccounts(List<AccountDTO> accounts){
        this.accounts = accounts;
    }
}

