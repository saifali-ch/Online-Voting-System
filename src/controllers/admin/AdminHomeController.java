package controllers.admin;

import animatefx.animation.ZoomIn;
import database.DBServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import stage.StageHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminHomeController extends AdminLoginController implements Initializable {
    public Label title;
    public Label adminName;
    public AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ResultSet name = DBServices.statement.executeQuery("select name from admins where cnic = '" + text + "'");
            name.next();
            adminName.setText(name.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void manageAdmins(ActionEvent event) throws IOException {
        title.setText("Manage Admins");
        new ZoomIn(title).play();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxmls/admin/ManageAdmins.fxml"));
        anchorPane.getChildren().setAll(pane);
        new ZoomIn(anchorPane).setResetOnFinished(true).play();
    }

    public void managePositions(ActionEvent event) throws IOException {
        title.setText("Manage Positions");
        new ZoomIn(title).play();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxmls/admin/ManagePositions.fxml"));
        anchorPane.getChildren().setAll(pane);
        new ZoomIn(anchorPane).play();
    }

    public void manageCandidates(ActionEvent event) throws IOException {
        title.setText("Manage Candidates");
        new ZoomIn(title).play();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxmls/admin/ManageCandidates.fxml"));
        anchorPane.getChildren().setAll(pane);
        new ZoomIn(anchorPane).play();
    }

    public void checkResults(ActionEvent event) throws IOException {
        title.setText("Check Results");
        new ZoomIn(title).play();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxmls/admin/CheckResults.fxml"));
        anchorPane.getChildren().setAll(pane);
        new ZoomIn(anchorPane).play();
    }

    public void logout(ActionEvent event) throws IOException {
        new StageHandler().logout(event);
    }
}
