package controllers.voter;

import animatefx.animation.Pulse;
import animatefx.animation.Shake;
import animatefx.animation.ZoomIn;
import database.DBServices;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import stage.StageHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class VoterHomeController extends VoterLoginController implements Initializable {
    static ResultSet resultSet;
    public Label welcome;
    public String name;
    public VBox vb1;
    public VBox vb2;
    public VBox vb3;
    public HBox hb;
    public ImageView img1;
    public ImageView img2;
    public ImageView img3;

    public void castVote(MouseEvent event) throws IOException, SQLException {
        ResultSet resultSet = DBServices.statement.executeQuery("select status, name from voters where cnic = '" + log + "'");
        resultSet.next();
        if (resultSet.getString("status").equals("false")) {
            new StageHandler().changeStage(event, "/fxmls/voter/CastVote.fxml", true);
        } else {
            Alert sorry = new Alert(Alert.AlertType.ERROR);
            sorry.setTitle("Vote Casting Error");
            sorry.setHeaderText("Vote Can't Be Casted Reason:");
            sorry.setContentText("Mr." + resultSet.getString("name") + "! You have already casted your vote!");
            sorry.showAndWait();
        }
    }

    public void changePassword(MouseEvent event) throws IOException {
        Stage stage = new StageHandler().createStage("/fxmls/voter/ChangePassword.fxml");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void logout(MouseEvent event) throws IOException {
        new StageHandler().logout(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            resultSet = DBServices.statement.executeQuery("select status, name from voters where cnic = '" + log + "'");
            resultSet.next();
            welcome.setText("Welcome Mr. " + resultSet.getString("name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        new ZoomIn(vb1).play();
        new ZoomIn(vb2).play();
        new ZoomIn(vb3).play();
        new ZoomIn(welcome).play();
    }

    public void onMouse(MouseEvent event) {
        if (event.getSource() == img1) {
            new Pulse(img1).setResetOnFinished(true).play();
        }
        if (event.getSource() == img2) {
            new Shake(img2).setResetOnFinished(true).play();
        }
        if (event.getSource() == img3) {
            new Pulse(img3).setResetOnFinished(true).play();
        }
    }
}
