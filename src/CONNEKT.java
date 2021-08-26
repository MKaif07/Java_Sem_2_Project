import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CONNEKT {

    Connection kon;
    public Connection KON(String database){
        String url = "jdbc:mysql://localhost:3306/"+database;
        String user = "root";
        String pass = "root";
        try {
            kon = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e){
            System.out.println(e);
            e.printStackTrace();
        }

        return kon;
    }
}
