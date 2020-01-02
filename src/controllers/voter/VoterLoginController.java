package controllers.voter;

import com.jfoenix.controls.JFXButton;
import database.DBServices;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import stage.StageHandler;

import java.io.IOException;
import java.sql.SQLException;

public class VoterLoginController {

    public static String log; // Stores CNIC Used in Next Controller
    public TextField cnic;
    public PasswordField password;
    public JFXButton btnLogin;

    public void login(ActionEvent event) throws SQLException, IOException {
        log = cnic.getText();
        String query = "select * from voters where cnic = '" + cnic.getText() + "' and password = '" + password.getText() + "'";
        if (DBServices.statement.executeQuery(query).next()) {
            new StageHandler().changeStage(event, "/fxmls/voter/VoterHome.fxml", true);
        }
    }

    public void registerVoter(ActionEvent event) throws IOException {
        new StageHandler().changeStage(event, "/fxmls/voter/VoterRegister.fxml", false);
    }

    public void fill(MouseEvent event) {
        cnic.setText("4");
        password.setText("4");
        btnLogin.fire();
    }
}
