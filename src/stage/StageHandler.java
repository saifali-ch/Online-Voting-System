package stage;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class StageHandler {

    public static Stage oldS;
    public static Stage newS;

    public static Stage getStage(Event event) {
        return ((Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    public void changeStage(Event event, String FXMLPath, boolean closeOldStage) throws IOException {
        //Creating New Stage
        Parent fxml = FXMLLoader.load(getClass().getResource(FXMLPath));
        Stage newStage = new Stage();
        newStage.setScene(new Scene(fxml));
        Image icon = new Image(getClass().getResourceAsStream("/media/app_icon.png"));
        newStage.getIcons().add(icon);
        newStage.show();
        //Handling Old Stage
        Stage oldStage = getStage(event);
        if (closeOldStage) {
            oldStage.close();
        } else {
            oldStage.hide();
        }
        newStage.setOnCloseRequest(e -> oldStage.show());
        oldS = oldStage;
        newS = newStage;
    }

    public void logout(Event event) throws IOException {
        //Closing All Other and Opening Home.fxml
        Parent fxml = FXMLLoader.load(getClass().getResource("/fxmls/home/Home.fxml"));
        Stage home = new Stage();
        home.setScene(new Scene(fxml));
        Image icon = new Image(getClass().getResourceAsStream("/media/app_icon.png"));
        home.getIcons().add(icon);
        home.show();
        Stage oldStage = getStage(event);
        oldStage.close();
    }

    public Stage createStage(String FXMLPath) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource(FXMLPath));
        Stage stage = new Stage();
        Image icon = new Image(getClass().getResourceAsStream("/media/voter/change_password_icon.png"));
        stage.getIcons().add(icon);
        stage.setScene(new Scene(fxml));
        return stage;
    }

}
