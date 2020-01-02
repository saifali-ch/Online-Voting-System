package controllers.admin;

import com.jfoenix.controls.JFXTextField;
import database.DBServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import programming.admin.Admin;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


public class ManageAdminsControllers implements Initializable {
    //Observable Lists
    public static ObservableList<Admin> adminObservableList = FXCollections.observableArrayList();
    //Form Variables
    public Button updateButton;
    public Button addButton;
    public JFXTextField cnic;
    public JFXTextField name;
    public JFXTextField password;
    public Admin admin;
    //Table Variables
    public TableView<Admin> table;
    public TableColumn<Admin, String> tname = new TableColumn<>();
    public TableColumn<Admin, String> tpassword = new TableColumn<>();
    public TableColumn<Admin, String> tcnic = new TableColumn<>();
    public TableColumn<Admin, Hyperlink> hyperlink = new TableColumn<>();
    //Connecting to Database
    Statement statement = DBServices.statement;
    //Condition
    String condition;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Reset - Form Method
    public void reset(ActionEvent actionEvent) {
        name.clear();
        cnic.clear();
        password.clear();
    }

    //Add Data - Form Method
    public void add(ActionEvent actionEvent) throws SQLException {
        Admin admin = new Admin(name.getText(), cnic.getText(), password.getText());
        statement.executeUpdate("insert into admins values('" + admin.getName() + "', '" + admin.getCnic() + "', '" + password.getText() + "')");
        this.reset(actionEvent);
        adminObservableList.add(admin);
    }

    //Update - Form Method
    public void update(ActionEvent actionEvent) throws SQLException {
        String query = "update admins set name = '" + name.getText() + "', cnic = '" + cnic.getText() + "', password = '" + password.getText() + "' " + condition + "";
        statement.executeUpdate(query);
        admin.setName(name.getText());
        admin.setCnic(cnic.getText());
        admin.setPassword(password.getText());

        initializeTableUsingDB();
        updateButton.setDisable(true);
        addButton.setDisable(false);
    }

    //Get Focus in Table When Mouse Enters
    public void getFocus(MouseEvent event) {
        table.requestFocus();
    }

    //Get Focus in Name Column When Mouse Exits From Table
    public void leaveFocus(MouseEvent event) {
        name.requestFocus();
    }

    //Creating Table
    public void createTable() throws SQLException {
        tname.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcnic.setCellValueFactory(new PropertyValueFactory<>("cnic"));
        tpassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        hyperlink.setCellFactory(par -> new TableCell<>() {
            private final Hyperlink update = new Hyperlink("Update");
            private final Hyperlink remove = new Hyperlink("Remove");
            private final Label slash = new Label("/");
            private final HBox pane = new HBox(update, slash, remove);

            {
                update.setOnAction(e -> {
                    admin = getTableView().getItems().get(getIndex());
                    String pName = admin.getName();
                    String pCnic = admin.getCnic();
                    String pPass = admin.getPassword();
                    addButton.setDisable(true);

                    //Setting Values in From
                    name.setText(pName);
                    cnic.setText(pCnic);
                    password.setText(pPass);

                    //Condition for update in Database
                    condition = "where name = '" + pName + "' and cnic = '" + pCnic + "' and password = '" + pPass + "'";
                    updateButton.setDisable(false);

                });
                remove.setOnAction(e -> {
                    ObservableList<Admin> allAdmins;
                    allAdmins = table.getItems();
                    Admin adminSelected = getTableView().getItems().get(getIndex());
                    String query = "delete from admins where name = '" + adminSelected.getName() + "' and cnic = '" + adminSelected.getCnic() + "' and password = '" + adminSelected.getPassword() + "'";
                    allAdmins.remove(adminSelected);
                    try {
                        statement.executeUpdate(query);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                });
            }

            @Override
            protected void updateItem(Hyperlink item, boolean empty) {
                super.updateItem(item, empty);
                pane.setPadding(new Insets(10, 10, 10, 10));
                setGraphic(empty ? null : pane);
            }
        });
        table.setItems(adminObservableList);
        initializeTableUsingDB();
    }

    public void initializeTableUsingDB() throws SQLException {
        adminObservableList.clear();
        ResultSet initialLoading = statement.executeQuery("select * from admins");
        try {
            while (initialLoading.next()) {
                Admin admin = new Admin(initialLoading.getString(1),
                        initialLoading.getString(2),
                        initialLoading.getString(3));
                adminObservableList.add(admin);
            }
        } catch (SQLException e) {
            e.getMessage();
        }
    }
}

