import java.sql.*;

public class DockerMySQL {
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://10.0.10.3:3306/database";

   static final String USER = "jmielniczuk";
   static final String PASS = "haslo";
   
   public static void main(String[] args) {
   Connection conn = null;
   Statement stmt = null;
   try{
      Class.forName("com.mysql.jdbc.Driver");

      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);

      stmt = conn.createStatement();
      String createTable,initInsert,sql;
      createTable = "CREATE TABLE Osoby (IdOsoby int, imie varchar(255), nazwisko varchar(255));"
      stmt.executeQuery(createTable);
     
      initInsert = "INSERT INTO Osoby (IdOsoby, imie, nazwisko) VALUES (1,'Jan','Kowalski')"+
                    ",(2,'Izabela','Kowal'),(3,'Krzysztof','Jarzyna');"
      stmt.executeQuery(initInsert);
     
      sql = "SELECT IdOsoby,imie,nazwisko FROM Osoby";
        
     
      ResultSet rs = stmt.executeQuery(sql);

      while(rs.next()){
         int id  = rs.getInt("IdOsoby");
         String imie = rs.getString("imie");
         String nazwisko = rs.getString("nazwisko")

         System.out.println("ID: " + id);
         System.out.println(", Imie: " + imie);
         System.out.println(", Nazwisko: " + nazwisko);
      }
      rs.close();
      stmt.close();
      conn.close();
   }catch(SQLException se){
      se.printStackTrace();
   }catch(Exception e){
      e.printStackTrace();
   }finally{
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
      }
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }
   }
 }
}
