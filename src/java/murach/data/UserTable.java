package murach.data;

import java.sql.*;
import java.io.*;
import java.util.*;
import shopProj.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class UserTable {
    
    static String url = "jdbc:mysql://localhost:3306/phrase3";
    static String username = "phase3";
    static String password = "123";
    
    static Connection connection = null;
    static PreparedStatement selectProduct = null;
    static ResultSet resultset = null;
	
	//Static initializer, it runs when the class is intialized (it is executed once)
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
    static {
        try {
            connection = DriverManager.getConnection (url, username, password);
        }
        catch (SQLException e) {
            for (Throwable t : e)
                t.printStackTrace();
        }}
    
    public static void addRecord(User user) throws IOException {
        
        try {
            
            String preparedSQL = "INSERT INTO users (firstName, lastName, email, password) VALUES ('" + user.getFirstName() + "', '" + user.getLastName() + "', '" + user.getEmail() + "', '" + user.getPassword() + "')";           
            
            PreparedStatement statement = connection.prepareStatement(preparedSQL);
            
            statement.executeUpdate();
           
        } catch (SQLException e){
            System.err.print("Exception in addRecord");
            System.err.println(e.getMessage());
        }
    }

    public static User getUser(String emailAddress) throws IOException {
               User user = new User(); 
        try {
            String preparedSQL = "Select * From users Where email = '" + emailAddress + "';";           
            
            PreparedStatement statement = connection.prepareStatement(preparedSQL);
            
            resultset = statement.executeQuery();
            
            resultset.next();
                   
            user.setFirstName(resultset.getString("firstName"));
            user.setLastName(resultset.getString("lastName"));
            user.setEmail(resultset.getString("email"));
            user.setPassword(resultset.getString("password"));
            
        } catch (SQLException e){
            System.err.print("Exception in addRecord");
            System.err.println(e.getMessage());
        }
        if(user.getEmail() == null){
            user = null;
        }
        return user;
    }

    public static ArrayList<User> getUsers() throws IOException {
		ArrayList<User> users = new ArrayList<User>();
                try {
                     String preparedSQL = "Select * From users";           
            
                        PreparedStatement statement = connection.prepareStatement(preparedSQL);

                        resultset = statement.executeQuery();

                    while(resultset.next()) {
                    User user = new User();
                    user.setFirstName(resultset.getString("firstName"));
                    user.setLastName(resultset.getString("lastName"));
                    user.setEmail(resultset.getString("email"));
                    user.setPassword(resultset.getString("password"));
                    users.add(user);
                }       
                } catch (SQLException e){
                    System.err.print("Exception in getUsers");
                            System.err.println(e.getMessage());
        }
                return users;
    }

    public static HashMap<String, User> getUsersMap() throws IOException {
			HashMap<String, User> users = new HashMap<String, User>();
                try {
                     String preparedSQL = "Select * From users";           
            
                        PreparedStatement statement = connection.prepareStatement(preparedSQL);

                        resultset = statement.executeQuery();

                    while(resultset.next()) {
                        User user = new User();
                        user.setFirstName(resultset.getString("firstName"));
                        user.setLastName(resultset.getString("lastName"));
                        user.setEmail(resultset.getString("email"));
                        user.setPassword(resultset.getString("password"));
                        users.put(resultset.getString("email"),user);
                }       
                } catch (SQLException e){
                    System.err.print("Exception in getUsers");
                            System.err.println(e.getMessage());
        }
                return users;
    }
  }

