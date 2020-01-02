package controllers.voter;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import database.DBServices;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import stage.StageHandler;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


public class VoterRegisterController implements Initializable {


    public JFXTextField name;
    public JFXTextField cnic;
    public JFXPasswordField pass1;
    public JFXPasswordField pass2;

    Statement statement = DBServices.statement;

    public void register() throws SQLException {
        boolean ok = true;
        if (!pass1.getText().equals(pass2.getText()) || pass1.getText().equals("")) {
            ok = false;
            createAlert(Alert.AlertType.WARNING, "Register", "Password", "Password Doesn't Match!");
        }
        if (name.getText().equals("")) {
            createAlert(Alert.AlertType.WARNING, "Register", "Username", "Name Field Can't be Empty!");
        }
        if (ok) {
            String query = "insert into voters(Name, CNIC, Password) values('" + name.getText() + "', '" + cnic.getText() + "', '" + pass1.getText() + "')";
            ResultSet rs = statement.executeQuery("select * from voters where cnic = '" + cnic.getText() + "'");
            if (rs.next()) {
                createAlert(Alert.AlertType.WARNING, "Register", "CNIC", "CNIC Already Exists!");
            } else {
                statement.executeUpdate(query);
                createAlert(Alert.AlertType.CONFIRMATION, "Register", "Registration Successful", "You Are Successfully Registered!");
            }
        }
        StageHandler.oldS.show();
        StageHandler.newS.hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void createAlert(Alert.AlertType alertType, String winTitle, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(winTitle);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
