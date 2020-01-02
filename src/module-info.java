module Online.Voting.System {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires mysql.connector;
    requires com.jfoenix;
    requires AnimateFX;
    requires org.controlsfx.controls;

    exports start;
    exports controllers.admin;
    exports controllers.voter;
    exports controllers.home;
    exports programming.admin;
    exports programming.voter;
//    --add-exports javafx.base/com.sun.javafx.event=org.controlsfx.controls
}