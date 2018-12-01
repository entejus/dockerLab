import java.sql.*;
import java.util.Scanner;
import java.io.Console;

public class DockerMySQL {
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   
   //adres na jaki kontener łączy się z bazą
   static final String DB_URL = "jdbc:mysql://10.0.10.3:3306/database";
   //nazwa użytkownika
   static final String USER = "jmielniczuk";
   //hasło użytkownika
   static final String PASS = "haslo";
   
   public static void main(String[] args) {
   Connection conn = null;
   Statement stmt = null;
   try{
      Class.forName("com.mysql.jdbc.Driver");

      System.out.println("Connecting to database...");
      Boolean connect = false;
    
      //Pętla while wykonująca połączenie z bazą do momentu jego ustanowienia
      while(!connect)
      {
         try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            connect = true;
         }
         catch(Exception e) {          
            System.out.println("Connecting to database...");
            Thread.sleep(1000);
         }
      }
      
      stmt = conn.createStatement();
      String dropTable,createTable,initInsert,insert,sql;
      
      //Usunięcie tabeli jeśli istnieje
      System.out.println("Droping table...");
      dropTable = "DROP TABLE IF EXISTS Osoby";
      stmt.executeUpdate(dropTable);
      
      //Tworzenie tabeli Osoby z 3 polami
      System.out.println("Creating table...");
      createTable = "CREATE TABLE Osoby (IdOsoby int, imie varchar(255), nazwisko varchar(255));";
      stmt.executeUpdate(createTable);
     
      //Wypełnianie tabeli 3 rekordami
      System.out.println("Filling table...");
      initInsert = "INSERT INTO Osoby (IdOsoby, imie, nazwisko) VALUES (1,'Jan','Kowalski')"+
                    ",(2,'Izabela','Kowal'),(3,'Krzysztof','Jarzyna');";
      stmt.executeUpdate(initInsert);
     
      //Zapytanie SQL do wyszukania wszystkich rekordów w tabeli Osoby
      sql = "SELECT * FROM Osoby";
      //Pierwsza część zapytania INSERT
      insert = "INSERT INTO Osoby (IdOsoby, imie, nazwisko) VALUES";
      
      Scanner input = new Scanner(System.in);
      
      int id;
      String imie,nazwisko;
           
      Boolean exit = false;
    
      while(!exit) { 
      System.out.println("Wybierz jedną z opcji(wprowadź odpowiednią cyfrę):");
      System.out.println("[1] Wyświetl zawartość bazy");
      System.out.println("[2] Dodaj encję");
      System.out.println("[3] Wyjdź");
      
      int option = input.nextInt();
      
      switch(option) {
         case 1:   
            //Wykonanie zapytania SELECT znajdującego się w zmiennej sql
            ResultSet rs = stmt.executeQuery(sql);
               while(rs.next()){
                  System.out.println("ID: " + rs.getInt(1)+", Imie: " + rs.getString(2)+", Nazwisko: " + rs.getString(3));
               }
            rs.close();
            break;
         case 2:            
            System.out.println("Podaj IdOsoby:");
            id = input.nextInt();
            System.out.println("Podaj imie:");
            imie = input.next();
            System.out.println("Podaj nazwisko:");
            nazwisko = input.next();
            
            //Uzupełnienie zapytania na podstawie wprowadzonych danych
            insert+=" ("+id+",'"+imie+"','"+nazwisko+"');";
            //Wprowadzenie danych do bazy
            stmt.executeUpdate(insert);
            break;
         case 3:
            //Wyjście z pętli while
            exit = true;
            break;
         default:
            System.out.println("Nie ma takiej opcji");
            }
      }
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
