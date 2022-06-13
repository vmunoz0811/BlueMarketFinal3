package co.edu.unbosque.bluemarketfinal3.resources;

import co.edu.unbosque.bluemarketfinal3.dtos.Art;
import co.edu.unbosque.bluemarketfinal3.services.ArtService;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.plugins.providers.multipart.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

@Path("/users/{email}/images")
public class ArtResource {

    @Context
    ServletContext context;

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost/BlueMarketDB";
    static final String USER = "postgres";
    static final String PASS = "Pacolindo.23";

    Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
    private final String UPLOAD_DIRECTORY = "/arts/";

    public ArtResource() throws SQLException {
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)


    public Response uploadFile(@PathParam("email") String email,
                               @FormParam("name") String name,
                               @FormParam("category") String category,
                               @FormParam("price") String price, MultipartFormDataInput input) {
        String fileName = "";
        String filename2 = "";
        int colectioni = 0;
        int precio2 = 0;
        int artid = 0;
        String path = "";
        Art pieceArt = new Art();

        try {
            // Getting the file from form input
            Map<String, List<InputPart>> formParts = input.getFormDataMap();
            path = create() + ".jpg";
            // Extracting text by input name
            if (formParts.get("filename") != null) {
                fileName = formParts.get("filename").get(0).getBodyAsString();
            }
            if (formParts.get("name") != null) {
                filename2 = formParts.get("name").get(0).getBodyAsString() + ".jpg";
            }
            if (formParts.get("category") != null) {
                colectioni = Integer.parseInt(formParts.get("category").get(0).getBodyAsString());
            }
            if (formParts.get("price") != null) {
                precio2 = Integer.parseInt(formParts.get("price").get(0).getBodyAsString());
            }
            ArtService arts = new ArtService(conn);
            artid = arts.artList().size();

            // Extracting multipart by input name

            List<InputPart> inputParts = formParts.get("imagepath");
            for (InputPart inputPart : inputParts) {
                // If file name is not specified as input, use default file name
                if (fileName.equals("") || fileName == null) {
                    // Retrieving headers and reading the Content-Disposition header to file name
                    jakarta.ws.rs.core.MultivaluedMap<String, String> headers = inputPart.getHeaders();
                    fileName = parseFileName(headers);
                }

                // Handling the body of the part with an InputStream
                InputStream istream = inputPart.getBody(InputStream.class, null);

                // Saving the file on disk
                saveFile(istream, path, context);
            }
            pieceArt.setIdArt(artid);
            pieceArt.setTitle(filename2);
            pieceArt.setPrice(String.valueOf(precio2));
            pieceArt.setImagePath(path);
            arts.insertArt(pieceArt);

            return Response.status(201)
                    .entity("Avatar successfully uploaded for " + email)
                    .build();
        } catch (IOException e) {
            return Response.serverError().build();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Response.ok().entity(pieceArt).build();
    }

    // Parse Content-Disposition header to get the file name
    private String parseFileName(jakarta.ws.rs.core.MultivaluedMap<String, String> headers) {
        String[] contentDispositionHeader = headers.getFirst("Content-Disposition").split(";");

        for (String name : contentDispositionHeader) {
            if ((name.trim().startsWith("filename"))) {
                String[] tmp = name.split("=");
                String fileName = tmp[1].trim().replaceAll("\"", "");
                return fileName;
            }
        }

        return "unknown";
    }

    // Save uploaded file to a defined location on the server
    private void saveFile(InputStream uploadedInputStream, String fileName, ServletContext context) {
        int read = 0;
        byte[] bytes = new byte[1024];

        try {
            // Complementing servlet path with the relative path on the server
            String uploadPath = context.getRealPath("") + UPLOAD_DIRECTORY;

            // Creating the upload folder, if not exist
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();

            // Persisting the file by output stream
            OutputStream outpuStream = new FileOutputStream(uploadPath + fileName);
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                outpuStream.write(bytes, 0, read);
            }

            outpuStream.flush();
            outpuStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String create() {

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        System.out.println(generatedString);
        return generatedString;
    }

}