package programming.admin;


import javafx.beans.property.SimpleStringProperty;

public class Admin {
    private SimpleStringProperty name;
    private SimpleStringProperty cnic;
    private SimpleStringProperty password;

    public Admin(String name, String cnic, String password) {
        this.name = new SimpleStringProperty(name);
        this.cnic = new SimpleStringProperty(cnic);
        this.password = new SimpleStringProperty(password);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getCnic() {
        return cnic.get();
    }

    public void setCnic(String cnic) {
        this.cnic.set(cnic);
    }

    public SimpleStringProperty cnicProperty() {
        return cnic;
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }
}

