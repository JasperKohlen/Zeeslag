package loginclasses;

import dbclasses.*;
import rest.AccountDTO;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class loginclass {
    dbclass dbClass = new dbclass();

    public void register(String userName, String password){
        String hashedPassword = hashclass.hashPassword(password);
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, userName);
        map.put(2, hashedPassword);
        String procedure = "INSERT INTO [dbo].[User] (userName, password) VALUES(?,?)";

        dbClass.executeNonQuery(procedure, map);
    }

    public boolean login(String userName, String inputPassword){
        String procedure = "SELECT userName, password FROM [dbo].[User] WHERE userName = ?";
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, userName);
        try{
            CachedRowSet cachedRowSet = dbClass.executeQuery(procedure, map);
            if(cachedRowSet.next()){
                return hashclass.checkPassword(inputPassword,cachedRowSet.getString("password"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public List<AccountDTO> getAll(){
        String procedure = "SELECT * FROM [dbo].[User]";
        List<AccountDTO> accounts = new ArrayList<>();
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        try{
            CachedRowSet cachedRowSet = dbClass.executeQuery(procedure, map);
            while (cachedRowSet.next()){
                accounts.add(new AccountDTO(cachedRowSet.getInt("userId"),
                        cachedRowSet.getString("userName"),
                        cachedRowSet.getString("password")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return accounts;
    }

    public boolean doesAccountExist() {
        //TODO
        return false;
    }
}
