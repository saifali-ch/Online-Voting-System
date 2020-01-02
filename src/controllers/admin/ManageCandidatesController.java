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
import programming.admin.Candidate;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


public class ManageCandidatesController implements Initializable {
    //Observable Lists//////////////////////////////////////////////////////////////////////////////////////
    public static ObservableList<Candidate> candidateObservableList = FXCollections.observableArrayList();
    public static ObservableList<String> comboList = FXCollections.observableArrayList();
    //Form Variables
    public Button addButton;
    public JFXTextField name;
    //    public JFXTextField positions;
    public JFXTextField id;
    public ComboBox<String> positions;
    //Table Variables////////////////////////////////////////////////////////
    public TableView<Candidate> table;
    public TableColumn<Candidate, String> tID = new TableColumn<>();
    public TableColumn<Candidate, String> tName = new TableColumn<>();
    public TableColumn<Candidate, String> tPosition = new TableColumn<>();
    public TableColumn<Candidate, Hyperlink> hyperlink = new TableColumn<>();

    //Getting Connection Statement From Database//
    Statement statement = DBServices.statement;


    //Initializing Whole Scene////////////////////////////////////////////
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initializeComboBox(); //Initialize Position List
            this.createTable(); //Initialize Table
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Reset - Form Method/////////////////////////////
    public void reset(ActionEvent actionEvent) {
        name.clear();
        id.clear();
        positions.setValue("Select");
    }

    //Add Data - Form Method///////////////////////////////////////////
    public void add(ActionEvent actionEvent) throws SQLException {
        Candidate candidate = new Candidate(Integer.parseInt(id.getText()), name.getText(), positions.getValue()); //positions.getText()
        statement.executeUpdate("insert into candidates(Id, Name, Position) values('" + id.getText() + "', '" + candidate.getName() + "', '" + candidate.getPosition() + "' )");
        this.reset(actionEvent);
        candidateObservableList.add(candidate);
    }

    //Get Focus in Table When Mouse Enters///////////////////////////
    public void getFocus(MouseEvent event) {
        table.requestFocus();
    }

    //Get Focus in Name Column When Mouse Exits From Table///////////
    public void leaveFocus(MouseEvent event) {
        name.requestFocus();
    }

    //Creating Table////////////////////////////////////
    public void createTable() throws SQLException {
        tID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        hyperlink.setCellFactory(par -> new TableCell<>() {
            private final Hyperlink remove = new Hyperlink("Remove");

            {
                remove.setOnAction(e -> {
                    ObservableList<Candidate> allCandidates;
                    allCandidates = table.getItems();
                    Candidate candidateSelected = getTableView().getItems().get(getIndex());
                    allCandidates.remove(candidateSelected);
                    String query = "delete from candidates where name = '" + candidateSelected.getName() + "'";
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
        table.setItems(candidateObservableList);
        initializeTableUsingDB();
    }

    //Initialize Table Using Database//////////////////////////////
    public void initializeTableUsingDB() throws SQLException {
        candidateObservableList.clear();
        ResultSet initialLoading = statement.executeQuery("select * from candidates");
        try {
            while (initialLoading.next()) {
                Candidate candidate = new Candidate(initialLoading.getInt(1), initialLoading.getString(2),
                        initialLoading.getString(3));
                candidateObservableList.add(candidate);
            }
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    //Initialize ComboBox Using Database///////////////////////
    public void initializeComboBox() throws SQLException {
        comboList.clear();
        ResultSet resultSet = DBServices.statement.executeQuery("select * from positions");
        while (resultSet.next()) {
            comboList.add(resultSet.getString(2));
            positions.setItems(comboList);
//            TextFields.bindAutoCompletion(positions, positionList);
        }
    }
}

