package start;

import database.DBServices;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Start extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        new DBServices();
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/home/Home.fxml"));
        Scene login = new Scene(root);
        stage.setTitle("Online Voting System");
        stage.setScene(login);
        Image icon = new Image(getClass().getResourceAsStream("/media/app_icon.png"));
        stage.getIcons().add(icon);
        stage.show();
        stage.setOnCloseRequest(e -> stage.close());
    }
}
