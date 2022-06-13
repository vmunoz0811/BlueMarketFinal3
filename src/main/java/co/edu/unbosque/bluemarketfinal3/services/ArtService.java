package co.edu.unbosque.bluemarketfinal3.services;

import co.edu.unbosque.bluemarketfinal3.dtos.Art;
import java.sql.*;
import java.util.*;

public class ArtService {

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String URL = "jdbc:postgresql://localhost/BlueMarketDB";
    static final String USER = "postgres";
    static final String PASSWORD = "Pacolindo.23";

    public ArtService(Connection conn) throws SQLException {
        conn = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public long insertArt(Art art) {

        String SQL = "INSERT INTO art (id_art, name, price, description, category, imagepath,forsale)" + "VALUES(?,?,?,?,?,?,?)";
        long id = 0;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, art.getIdArt());
            pstmt.setString(2, art.getTitle());
            pstmt.setDouble(3, Double.parseDouble(art.getPrice()));
            pstmt.setString(4, art.getDescription());
            pstmt.setString(5, art.getCategory());
            pstmt.setString(6, art.getImagePath());
            pstmt.setBoolean(7, art.isForsale());

            int affectedRows = pstmt.executeUpdate();
            // check the affected rows
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;

    }

    public List<Art> artList() {
        Statement stmt = null;
        List<Art> art = new ArrayList<Art>();

        try {
            System.out.println("=> Listing arts...");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM art";
            ResultSet rs = stmt.executeQuery(sql);

            // Reading data from result set row by row
            while (rs.next()) {
                int id_art = rs.getInt("id_art");
                String name = rs.getString("name");
                String price = rs.getString("price");
                String imagenpath = rs.getString("imagenpath");

                // Creating a new UserApp class instance and adding it to the array list
                Art pieceArt = new Art();

                pieceArt.setTitle(name);
                pieceArt.setPrice(price);
                pieceArt.setImagePath(imagenpath);
                art.add(pieceArt);

            }
            // Printing results
            System.out.println("idArt | title | precio | imagePath");
            for (Art pieceOfArt : art) {
                System.out.println( + pieceOfArt.getIdArt() + " | " + pieceOfArt.getTitle() + " | " + pieceOfArt.getPrice()+ " | " + pieceOfArt.getImagePath());
            }

            // Printing total rows
            System.out.println("Total of users retrieved: " + art.size() + "\n");

            // Closing resources
            rs.close();
            stmt.close();
        } catch (SQLException se) {
            se.printStackTrace(); // Handling errors from database
        } finally {
            // Cleaning-up environment
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return art;
    }

    public void updatePrice(Art art) {
        // Object for handling SQL statement
        PreparedStatement stmt = null;

        try {

            // Executing a SQL query
            System.out.println("=> Updating art...");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.prepareStatement("UPDATE art SET price = ? WHERE id_art = ?");

            stmt.setDouble(2, Double.parseDouble(art.getPrice()));
            stmt.setString(1, art.getTitle());

            int rowsUpdated = stmt.executeUpdate(); // executeUpdate is also used for inserting records

            // Printing results
            System.out.println("Rows updated: " + rowsUpdated + "\n");

            // Closing resources
            stmt.close();
        } catch (SQLException se) {
            se.printStackTrace(); // Handling errors from database
        } finally {
            // Cleaning-up environment
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public Art artList2(String email) throws SQLException {
        Statement stmt = null;
        List<Art> art = new ArrayList<Art>();
        ResultSet rs = null;
        try {
            // Executing a SQL query
            System.out.println("=> Listing obra...");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM art";
            rs = stmt.executeQuery(sql);

            // Reading data from result set row by row
            while (rs.next()) {
                // Extracting row values by column name
                int id_art = rs.getInt("id_art");
                String imagenpath = rs.getString("imagenpath");
                String name = rs.getString("name");
                String price = rs.getString("price");

                // Creating a new UserApp class instance and adding it to the array list
                Art pieceArt = new Art();
                pieceArt.setPrice(price);
                pieceArt.setImagePath(imagenpath);
                pieceArt.setTitle(name);
                pieceArt.setIdArt(id_art);
                art.add(pieceArt);

            }
            return art.stream()
                    .peek(x -> System.out.println(x))
                    .filter(x -> x.getTitle().equals(email))
                    .findFirst()
                    .orElse(new Art());

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            // Cleaning-up environment
            try {
                if (stmt != null) stmt.close();
                rs.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return new Art();
    }

}
