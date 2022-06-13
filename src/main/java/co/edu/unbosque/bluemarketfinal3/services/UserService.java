package co.edu.unbosque.bluemarketfinal3.services;

import co.edu.unbosque.bluemarketfinal3.dtos.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserService {

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String URL = "jdbc:postgresql://localhost/BlueMarketDB";
    static final String USER = "postgres";
    static final String PASSWORD = "Pacolindo.23";

    public UserService() {
    }

    public User addElement(String name, String email, String password, String role) {
        User response = new User(name, email, password, role, 0);
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD); Statement statement = connection.createStatement()) {
            Class.forName(JDBC_DRIVER);
            System.out.println("name = " + name + ", email = " + email + ", password = " + password + ", role = " + role);
            String query = "INSERT INTO userApp(name, email, password, role) VALUES ('" + name + "', '" + email + "', '" + password + "', '" + role + "')";
            String type = "created";
            Date date = new Date();
            long mili = date.getTime();
            Timestamp time = new Timestamp(mili);
            String query2 = "INSERT INTO walletHistory(registeredat,type,email,fcoins ) VALUES ('" + time + "', '" + type + "', '" + email + "', '" + 0 + "')";
            statement.execute(query);
            statement.executeQuery(query2);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
        return response;
    }


    public User login(String email, String password) {
        List<User> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD); Statement statement = connection.createStatement()) {
            Class.forName(JDBC_DRIVER);
            String query = "SELECT  * FROM userApp";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                list.add(new User(result.getString("name"),
                        result.getString("email"),
                        result.getString("password"),
                        result.getString("role")));
            }
            return list.stream()
                    .peek(x -> System.out.println(x))
                    .filter(x -> x.getEmail().equals(email) && x.getPassword().equals(password))
                    .findFirst()
                    .orElse(new User());

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
        return new User();
    }

    public List<User> getUsers() {
        List<User> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD); Statement statement = connection.createStatement()) {
            Class.forName(JDBC_DRIVER);
            String query = "SELECT  * FROM userApp";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                list.add(new User(result.getString("name"),
                        result.getString("email"),
                        result.getString("password"),
                        result.getString("role")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return list;
    }


}
