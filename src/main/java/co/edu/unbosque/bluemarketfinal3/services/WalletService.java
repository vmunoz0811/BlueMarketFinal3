package co.edu.unbosque.bluemarketfinal3.services;

import co.edu.unbosque.bluemarketfinal3.dtos.Art;
import co.edu.unbosque.bluemarketfinal3.dtos.User;
import co.edu.unbosque.bluemarketfinal3.dtos.Wallet;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class WalletService {
    public WalletService() {
    }

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String URL = "jdbc:postgresql://localhost/BlueMarketDB";
    static final String USER = "postgres";
    static final String PASSWORD = "Pacolindo.23";

    public Wallet walletlist(String email) {
        List<Wallet> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD); Statement statement = connection.createStatement()) {
            Class.forName(JDBC_DRIVER);
            String query = "SELECT  * FROM wallethistory";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                list.add(new Wallet(result.getString("type"),
                        result.getString("email"),
                        result.getDouble("fcoins")));
            }
            return list.stream()
                    .peek(x -> System.out.println(x))
                    .filter(x -> x.getUserApp().equals(email))
                    .findFirst()
                    .orElse(new Wallet());

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
        return new Wallet();
    }

    public Optional<Boolean> sell(String email, String fcoins, String nameart) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            List<User> users = new UserService().getUsers();
            User userBuyer = users.stream().filter(user -> email.equals(user.getName())).findFirst().orElse(null);
            String emailBuyer = userBuyer.getEmail();
            Wallet wallets = walletlist(emailBuyer);
            double Fcoins = wallets.getFcoins() - Double.parseDouble(fcoins);
            String type = "buy";
            Date date = new Date();
            long match = date.getTime();
            Timestamp time = new Timestamp(match);
            if (Fcoins > 0) {
                String query = "UPDATE wallethistory SET fcoins = '" + Fcoins + "' WHERE email = '" + emailBuyer + "'";
                stmt.execute(query);
                ArtService service = new ArtService(conn);
                Art arts = service.artList2(nameart);

            } else {
                return Optional.of(false);
            }

            stmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (ClassNotFoundException ce) {
            ce.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return Optional.of(true);

    }

    public void sold(String artname, String Fcoins) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // Registering the JDBC driver
            Class.forName(JDBC_DRIVER);

            // Opening database connection
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            List<Art> arts = new ArtService(conn).artList();
            Art user1 = arts.stream().filter(user -> artname.equals(user.getTitle())).findFirst().orElse(null);
            String User1 = user1.getCategory();
            List<User> user = new UserService().getUsers();
            User userSeller = user.stream().filter(userOwner -> User1.equals(userOwner.getEmail())).findFirst().orElse(null);
            String email = userSeller.getEmail();
            Wallet wallets = walletlist(email);
            double numFcoins = Double.parseDouble(Fcoins);
            String type = "sale";
            Date date = new Date();
            long match = date.getTime();
            Timestamp time = new Timestamp(match);
            double fcoins = numFcoins + wallets.getFcoins();
            if (fcoins >= 0) {
                String query = "UPDATE wallethistory SET fcoins = '" + fcoins + "' WHERE email = '" + email + "'";
                stmt.execute(query);
            } else {

                stmt.executeUpdate();

                stmt.close();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Cleaning-up environment
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}