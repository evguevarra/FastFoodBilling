package repositories;

//import java.sql.Connection;
////import java.sql.DriverManager;
////
////public class DatabaseConnection {
////
////    public Connection databaseLink;
////
////    public Connection getConnection(){
////        String databaseName = "fastfood";
////        String databaseUser = "root";
////        String databasePassword = "edisonvg0508";
////        String url = "jdbc:mysql://localhost/"+databaseName;
////
////        try{
////            Class.forName("com.mysql.cj.jdbc.Driver");
////            databaseLink = DriverManager.getConnection(url,databaseUser,databasePassword);
////        }catch (Exception e){
////            e.printStackTrace();
////        }
////        return databaseLink;
////    }
////
////}
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DatabaseConnection {
    public static Connection connect() {

        Connection con  = null;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:menuDB.db"); //connecting to database
            System.out.println("Connected");
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e+"");
        }

        return con;
    }
}
