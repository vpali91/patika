package patika;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DB {
    
    final String URL = "jdbc:derby:sampleDB;create=true";
    final String USERNAME = "";
    final String PASSWORD = "";
    
    //Létrehozzuk a kapcsolatot (hidat)
    Connection conn = null;
    Statement createStatement = null;
    DatabaseMetaData dbmd = null;
    
    
    public DB() {
        //Megpróbáljuk életre kelteni
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("A híd létrejött");
        } catch (SQLException ex) {
            System.out.println("Valami baj van a connection (híd) létrehozásakor.");
            System.out.println(""+ex);
        }
        
        //Ha életre kelt, csinálunk egy megpakolható teherautót
        if (conn != null){
            try {
                createStatement = conn.createStatement();
            } catch (SQLException ex) {
                System.out.println("Valami baj van van a createStatament létrehozásakor.");
                System.out.println(""+ex);
            }
        }
        
        //Megnézzük, hogy üres-e az adatbázis? Megnézzük, létezik-e az adott adattábla.
        try {           
            dbmd = conn.getMetaData();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a DatabaseMetaData (adatbázis leírása) létrehozásakor..");
            System.out.println(""+ex);
        }
        
        try {
            ResultSet rs = dbmd.getTables(null, "APP", "GYOGYSZEREK", null);
            if(!rs.next())
            { 
             createStatement.execute("create table gyogyszerek(id INT not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),gynev varchar(20), mennyiseg varchar(5), hasznalat varchar(50), ellenjav varchar(50), szavatossag varchar(12))");
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van az adattáblák létrehozásakor.");
            System.out.println(""+ex);
        }       
    }
    
    
    public ArrayList<Gyogyszer> getAllGyogyszer(){
        String sql = "select * from gyogyszerek";
        ArrayList<Gyogyszer> users = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            users = new ArrayList<>();
            
            while (rs.next()){
                Gyogyszer actualGyogyszer = new Gyogyszer(rs.getInt("id"),rs.getString("gynev"),rs.getString("mennyiseg"),rs.getString("hasznalat"),rs.getString("ellenjav"), rs.getString("szavatossag"));
                users.add(actualGyogyszer);
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a userek kiolvasásakor");
            System.out.println(""+ex);
        }
      return users;
    }
    
    public void addGyogyszer(Gyogyszer gyogyszer){
      try {
        String sql = "insert into gyogyszerek (gynev, mennyiseg, hasznalat, ellenjav, szavatossag) values (?,?,?,?,?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, gyogyszer.getGyogyszer());
        preparedStatement.setString(2, gyogyszer.getMennyiseg());
        preparedStatement.setString(3, gyogyszer.getHasznalat());
        preparedStatement.setString(4, gyogyszer.getEllenJav());
        preparedStatement.setString(5, gyogyszer.getSzavatossag());
        preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a contact hozzáadásakor");
            System.out.println(""+ex);
        }
    }
    
    public void updateGyogyszer(Gyogyszer gyogyszer){
      try {
            String sql = "update gyogyszerek set gynev = ?, mennyiseg = ? , hasznalat = ?, ellenjav = ?, szavatossag = ? where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, gyogyszer.getGyogyszer());
        preparedStatement.setString(2, gyogyszer.getMennyiseg());
        preparedStatement.setString(3, gyogyszer.getHasznalat());
        preparedStatement.setString(4, gyogyszer.getEllenJav());
        preparedStatement.setString(5, gyogyszer.getSzavatossag());
            preparedStatement.setInt(6, Integer.parseInt(gyogyszer.getId()));
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a contact hozzáadásakor");
            System.out.println(""+ex);
        }
    }
    
     public void removeGyogyszer(Gyogyszer gyogyszer){
      try {
            String sql = "delete from gyogyszerek where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(gyogyszer.getId()));
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a contact törlésekor");
            System.out.println(""+ex);
        }
    }
    
}