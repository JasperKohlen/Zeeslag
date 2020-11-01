package REST;

import loginclasses.loginclass;
import rest.AccountDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/auth")
public class RESTService {
    private loginclass seaBattleLogin;

    public RESTService() {
        seaBattleLogin = new loginclass();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(AccountDTO accountDTO){
        if(seaBattleLogin.login(accountDTO.getUsername(), accountDTO.getPassword())){
            return Response.status(200).entity(RESTResponse.getSuccessResponse()).build();
        }
        return Response.status(400).entity(RESTResponse.getErrorResponseString()).build();
    }

    @GET
    @Path("/account/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allAccounts() {


        // Get all pets from the store
        List<AccountDTO> allAccounts = seaBattleLogin.getAll();

        // Define response
        return Response.status(200).entity(RESTResponse.getAllPetsResponse(allAccounts)).build();
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerPlayer(AccountDTO account){
        if(account == null || account.getUsername() == null || account.getPassword() == null){
            return Response.status(400).entity(RESTResponse.getErrorResponseString()).build();
        }
        else if(seaBattleLogin.doesAccountExist()){
            return Response.status(400).entity(RESTResponse.getErrorResponseString()).build();
        }
        else{
            seaBattleLogin.register(account.getUsername(), account.password);
            return Response.status(200).entity(RESTResponse.getSingleAccountResponse(account)).build();
        }
    }

}
