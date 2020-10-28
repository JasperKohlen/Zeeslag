package restfulclient;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import rest.AccountDTO;
import rest.AccountResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class SeaBattleREST {

    public SeaBattleREST() {

    }

    private final String url = "http://localhost:8090/auth";
    private final Gson gson = new Gson();
    private final int NOTDEFINED = -1;

    public boolean login(String user, String password){
        AccountDTO accountDTO = new AccountDTO(NOTDEFINED, user, password);
        String queryPost = "/login";
        AccountResponse response = executeQueryPost(accountDTO, queryPost);
        return response.isSuccess();
    }

    public boolean register(String user, String password){
        AccountDTO accountDTO = new AccountDTO(NOTDEFINED, user, password);
        String queryPost = "/register";
        AccountResponse response = executeQueryPost(accountDTO, queryPost);
        return response.isSuccess();
    }

    private AccountResponse executeQueryPost(AccountDTO account, String queryPost) {
        // Build the query for the REST service
        final String query = url + queryPost;

        // Execute the HTTP POST request
        HttpPost httpPost = new HttpPost(query);
        httpPost.addHeader("content-type", "application/json");
        StringEntity params;
        try {
            params = new StringEntity(gson.toJson(account));
            httpPost.setEntity(params);
        } catch (UnsupportedEncodingException ex) {
            System.out.println(ex);
        }
        return executeHttpUriRequest(httpPost);
    }

    private AccountResponse executeHttpUriRequest(HttpUriRequest httpUriRequest) {
        // Execute the HttpUriRequest
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpUriRequest)) {
            HttpEntity entity = response.getEntity();
            final String entityString = EntityUtils.toString(entity);
            AccountResponse accountResponse = gson.fromJson(entityString, AccountResponse.class);
            return accountResponse;
        } catch (IOException e) {
            System.out.println(e);
            AccountResponse accountResponse = new AccountResponse();
            accountResponse.setSuccess(false);
            return accountResponse;
        } catch (JsonSyntaxException e) {
            System.out.println(e);
            AccountResponse accountResponse = new AccountResponse();
            accountResponse.setSuccess(false);
            return accountResponse;
        }
    }
}
