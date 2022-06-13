package co.edu.unbosque.bluemarketfinal3.dtos;

public class User {

    private String name;
    private String email;
    private String password;
    private String role;
    private double fcoins;

    public User() {
    }

    public User(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(String name, String email, String password, String role, double fcoins) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.fcoins = fcoins;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getFcoins() {
        return fcoins;
    }

    public void setFcoins(double fcoins) {
        this.fcoins = fcoins;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", mail='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", fcoins=" + fcoins +
                '}';
    }
}
