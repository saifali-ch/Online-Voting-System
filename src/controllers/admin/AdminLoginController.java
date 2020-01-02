package controllers.admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import database.DBServices;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import stage.StageHandler;

import java.io.IOException;
import java.sql.SQLException;


public class AdminLoginController {

    public static String text; // Used in Next Controller
    public JFXTextField cnic;
    public JFXPasswordField password;
    public JFXButton loginBtn;

    public void adminLogin(ActionEvent event) throws SQLException, IOException {
        String query = "select * from admins where cnic = '" + cnic.getText() + "' and password = '" + password.getText() + "'";
        text = cnic.getText();
        if (DBServices.statement.executeQuery(query).next()) {
            new StageHandler().changeStage(event, "/fxmls/admin/AdminHome.fxml", true);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Admin Login");
            alert.setContentText("Wrong Username or Password");
            alert.setHeaderText("Please Enter Correct Details");
            alert.showAndWait();
            cnic.clear();
            password.clear();
        }

    }

    public void fill(MouseEvent event) {
        System.out.println("Auto Fill Activated");
        cnic.setText("3110243396671");
        password.setText("12345678");
        loginBtn.fire();
    }
}
