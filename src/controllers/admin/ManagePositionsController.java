package controllers.admin;

import com.jfoenix.controls.JFXTextField;
import database.DBServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import programming.admin.Position;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ManagePositionsController implements Initializable {
    //Observable Lists
    public static ObservableList<Position> positionObservableList = FXCollections.observableArrayList();
    //Form Variables
    public Button addButton;
    public JFXTextField positionID;
    public JFXTextField positionName;
    //Table Variables
    public TableView<Position> table;
    public TableColumn<Position, Integer> id;
    public TableColumn<Position, String> name;
    public TableColumn<Position, Hyperlink> hyperlink;
    //Connecting to Database
    Statement statement = DBServices.statement;
    ResultSet initialLoading;

    public ManagePositionsController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Reset - Form Method
    public void reset(ActionEvent actionEvent) {
        positionName.clear();
        positionID.clear();
    }

    //Add Data - Form Method
    public void add(ActionEvent actionEvent) throws SQLException {
        Position position = new Position(Integer.parseInt(positionID.getText()), positionName.getText());
        statement.executeUpdate("insert into positions values('" + position.getId() + "','" + position.getName() + "')");
        this.reset(actionEvent);
        positionObservableList.add(position);
    }

    //Get Focus in Table When Mouse Enters
    public void getFocus(MouseEvent event) {
        table.requestFocus();
    }

    //Get Focus in Name Column When Mouse Exits From Table
    public void leaveFocus(MouseEvent event) {
        positionName.requestFocus();
    }

    //Creating Table
    public void createTable() throws SQLException {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        hyperlink.setCellFactory(e -> new TableCell<>() {
            private final Hyperlink remove = new Hyperlink("Remove");

            {
                remove.setOnAction(e -> {
                    ObservableList<Position> allPositions;
                    allPositions = table.getItems();
                    Position positionSelected = getTableView().getItems().get(getIndex());
                    allPositions.remove(positionSelected);
                    String query = "delete from positions where id = '" + positionSelected.getId() + "' ";
                    try {
                        statement.executeUpdate(query);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                });
            }

            @Override
            protected void updateItem(Hyperlink item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : remove);
            }
        });
        table.setItems(positionObservableList);
        initializeTableUsingDB();
    }

    public void initializeTableUsingDB() throws SQLException {
        positionObservableList.clear();
        initialLoading = statement.executeQuery("select * from positions");
        try {
            while (initialLoading.next()) {
                Position p = new Position(initialLoading.getInt(1), initialLoading.getString(2));
                positionObservableList.add(p);
            }
        } catch (SQLException e) {
            e.getMessage();
        }
    }
}
