package controllers.voter;

import database.DBServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import programming.voter.CastVote;
import stage.StageHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CastVoteController extends VoterLoginController implements Initializable {
    public ComboBox<String> comboBox;
    public Button castVote;
    public TableView<CastVote> table;
    public TableColumn<CastVote, Integer> id;
    public TableColumn<CastVote, String> name;
    public TableColumn<CastVote, String> position;
    public Text selectedName;
    public Text selectedPosition;

    ObservableList<CastVote> castVoteList = FXCollections.observableArrayList();
    ObservableList<String> positionList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        castVote.setDisable(true);
        try {
            ResultSet resultSet = DBServices.statement.executeQuery("select position from candidates");
            while (resultSet.next()) {
                if (!positionList.contains(resultSet.getString(1)))
                    positionList.add(resultSet.getString(1));
            }
            comboBox.setItems(positionList);
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable() throws SQLException {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        position.setCellValueFactory(new PropertyValueFactory<>("position"));
        table.setItems(castVoteList);
        initializeTableUsingDB("select * from candidates");
    }

    public void initializeTableUsingDB(String query) throws SQLException {
        castVoteList.clear();
        ResultSet rs = DBServices.statement.executeQuery(query);
        while (rs.next()) {
            castVoteList.add(new CastVote(rs.getInt(1), rs.getString(2), rs.getString(3)));
        }
    }

    public void removeOtherPositions() throws SQLException {
        initializeTableUsingDB("select * from candidates where position = '" + comboBox.getValue() + "'");
        castVote.setDisable(true);
    }

    public void castVoteEnabled(MouseEvent event) {
        CastVote voter = table.getSelectionModel().getSelectedItem();
        try {
            selectedName.setText(voter.getName());
            selectedPosition.setText(voter.getPosition());
            castVote.setDisable(false);
        } catch (NullPointerException ignored) {
        }
    }

    public void castVote(ActionEvent event) throws SQLException, IOException {
        CastVote selectedCandidate = table.getSelectionModel().getSelectedItem();
        Alert confirmVote = new Alert(Alert.AlertType.CONFIRMATION);
        confirmVote.setTitle("Casting Vote");
        confirmVote.setHeaderText("This can't be undone! You will be Logged Out!");
        confirmVote.setContentText("Sure to vote '" + selectedCandidate.getName() + "' at position '" + selectedCandidate.getPosition() + "'");
        confirmVote.showAndWait();
        if (confirmVote.getResult() == ButtonType.OK) {
            new StageHandler().logout(event);
            ResultSet rs = DBServices.statement.executeQuery("select votes from candidates where id = '" + selectedCandidate.getId() + "'");
            if (rs.next()) {
                int votes = rs.getInt(1);
                votes++;
                DBServices.statement.executeUpdate("update candidates set votes = '" + votes + "' where id = '" + selectedCandidate.getId() + "'");
                DBServices.statement.executeUpdate("update voters set status = 'true' where cnic = '" + log + "'");
            }
        }
    }
}
