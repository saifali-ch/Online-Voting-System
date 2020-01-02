package controllers.voter;

import com.jfoenix.controls.JFXTextField;
import database.DBServices;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.sql.SQLException;

public class ChangePasswordController extends VoterLoginController {

    public JFXTextField old;
    public JFXTextField newPass;
    public JFXTextField confirmNewPass;
    Notifications notification;

    public void changePassword(ActionEvent event) throws SQLException {
        if (DBServices.statement.executeQuery("select * from voters where cnic = '" + log + "' and password = '" + old.getText() + "'").next()) {
            if (newPass.getText().equals(confirmNewPass.getText()) || newPass.getText().equals("")) {
                DBServices.statement.executeUpdate("update voters set password = '" + newPass.getText() + "' where cnic = '" + log + "'");
                notification = createNotification("Password Changed Successfully");
                notification.showConfirm();
                old.clear();
                newPass.clear();
                confirmNewPass.clear();
            } else {
                notification = createNotification("New Password and Confirm Password Does't Match!");
                notification.showWarning();
            }
        } else {
            System.out.println("work");
            notification = createNotification("Your Current Password is Incorrect");
            notification.showWarning();
        }

    }

    public void close(ActionEvent event) {
        Stage s = (Stage) old.getScene().getWindow();
        s.close();

    }

    public Notifications createNotification(String text) {
        notification = Notifications.create()
                .title("Change Password")
                .text(text)
                .position(Pos.TOP_CENTER)
                .hideAfter(Duration.seconds(3));
        return notification;
    }
}
