package dbclasses;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;
import java.util.Map;

public class dbclass {
    final private String hostName = "mssql.fhict.local"; // update me
    final private String dbName = "dbi398785_zeeslag"; // update me
    final private String user = "dbi398785_zeeslag"; // update me
    final private String password = "12345"; // update me
    final private String url = String.format("jdbc:sqlserver://mssql.fhict.local;database=dbi398785_zeeslag");
    private Connection connection = null;

    public void executeNonQuery(String procedure, Map map){
        try{
            connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(procedure);

            for (int i = 1; i <= map.size(); i++) {
                statement.setObject( i, map.get(i));
            }

            statement.executeUpdate();
            connection.close();

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public CachedRowSet executeQuery(String procedure, Map map) throws SQLException {
        CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection(url);
            PreparedStatement statement = connection.prepareStatement(procedure);

            for (int i = 1; i <= map.size(); i++) {
                statement.setObject( i, map.get(i));
            }

            resultSet = statement.executeQuery();
            crs.populate(resultSet);
            connection.close();

        } catch(Exception e){
            e.printStackTrace();
        }
        return crs;
    }
}
