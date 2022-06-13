package co.edu.unbosque.bluemarketfinal3.dtos;

public class Wallet {

    private int id;
    private String userApp;
    private String type;
    private double fcoins;

    public Wallet() {
    }

    public Wallet(String userApp, String type, double fcoins) {
        this.id = id;
        this.userApp = userApp;
        this.type = type;
        this.fcoins = fcoins;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "idNumer=" + id +
                ", userApp='" + userApp + '\'' +
                ", type='" + type + '\'' +
                ", fcois=" + fcoins +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserApp() {
        return userApp;
    }

    public void setUserApp(String userApp) {
        this.userApp = userApp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getFcoins() {
        return fcoins;
    }

    public void setFcoins(double fcoins) {
        this.fcoins = fcoins;
    }

}


