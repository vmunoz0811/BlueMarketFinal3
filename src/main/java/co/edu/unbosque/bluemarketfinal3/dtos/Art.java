package co.edu.unbosque.bluemarketfinal3.dtos;

public class Art {

    private int idArt;
    private String title;
    private String price;
    private String description;
    private String category;
    private String imagePath;
    private boolean forsale;


    public Art() {
    }

    public Art(int idArt, String title, String price, String description, String category, String imagePath, boolean forsale) {
        this.idArt = idArt;
        this.title = title;
        this.price = price;
        this.description = description;
        this.category = category;
        this.imagePath = imagePath;
        this.forsale = forsale;
    }

    public int getIdArt() {
        return idArt;
    }

    public void setIdArt(int idArt) {
        this.idArt = idArt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isForsale() {
        return forsale;
    }

    public void setForsale(boolean forsale) {
        this.forsale = forsale;
    }
}
