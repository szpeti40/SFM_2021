package hu.unideb.inf;
import java.sql.*;

public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection() {
        String databaseName = "hotel_sfm";
        String databaseUser = "root";
        String databasePassword = "";
        String url = "jdbc:mysql://localhost:3306/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);

        } catch (Exception e) {
            System.out.println("gebasz");
            e.printStackTrace();
        }
        return databaseLink;
    }
    public Connection connectToDB() {
        DatabaseConnection connectionNow = new DatabaseConnection();

        Connection connectionDB = null;
        try {
            connectionDB = connectionNow.getConnection();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Nem sikerült csatlakozni az adatbazishoz");
        }
        return connectionDB;
}

    /*public boolean login(String user, String pwd,Connection connectionDB){
        String connectQuery = "SELECT id FROM `receptionist` WHERE username='user' AND password='pwd'";
        try {
            Statement statement = connectionDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);

            if(!queryOutput.next()){
                return false;
            }
            else
                return true;

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;

    }*/

}
