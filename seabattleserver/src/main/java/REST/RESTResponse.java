package REST;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rest.AccountDTO;
import rest.AccountResponse;

import java.util.ArrayList;
import java.util.List;

public class RESTResponse {
    private static final Logger log = LoggerFactory.getLogger(RESTService.class);
    private static final Gson gson = new Gson();

    static String getErrorResponseString()
    {
        AccountResponse response = new AccountResponse();
        response.setSuccess(false);
        String output = gson.toJson(response);
        log.info("[Server response] " + output);
        return output;
    }

    static String getAllPetsResponse(List<AccountDTO> allAccounts)
    {
        AccountResponse response = new AccountResponse();
        response.setSuccess(true);
        response.setAccounts(allAccounts);
        String output = gson.toJson(response);
        log.info("[Server response] " + output);
        return output;
    }

    static String getSingleAccountResponse(AccountDTO account)
    {
        AccountResponse response = new AccountResponse();
        response.setSuccess(true);
        List<AccountDTO> accounts = new ArrayList<>();
        accounts.add(account);
        response.setAccounts(accounts);
        String output = gson.toJson(response);
        log.info("[Server response] " + output);
        return output;
    }

    static String getSuccessResponse()
    {
        AccountResponse response = new AccountResponse();
        response.setSuccess(true);
        String output = gson.toJson(response);
        log.info("[Server response] " + output);
        return output;
    }
}
