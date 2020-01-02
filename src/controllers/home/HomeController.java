package controllers.home;


import animatefx.animation.RubberBand;
import animatefx.animation.Tada;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import stage.StageHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    public ImageView admin;
    public ImageView voter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void loginAsAdmin(Event event) throws IOException {
        new StageHandler().changeStage(event, "/fxmls/admin/AdminLogin.fxml", true);
    }

    public void loginAsVoter(Event event) throws IOException {
        new StageHandler().changeStage(event, "/fxmls/voter/VoterLogin.fxml", true);
    }

    public void mouseEntered(MouseEvent event) {
        if (event.getSource() == admin) {
            new Tada(admin).play();
        } else {
            new RubberBand(voter).play();
        }
    }
}
