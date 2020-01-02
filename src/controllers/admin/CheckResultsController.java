package controllers.admin;

import database.DBServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CheckResultsController implements Initializable {
    public PieChart numberChart;
    public ComboBox<String> selectPosition;
    public PieChart nameChart;
    public Label winner;
    ObservableList<String> comboList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ResultSet resultSet = DBServices.statement.executeQuery("select position from candidates");
            while (resultSet.next()) {
                if (!comboList.contains(resultSet.getString(1)))
                    comboList.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        selectPosition.setItems(comboList);
    }

    public void makePieChart(ActionEvent event) throws SQLException {
        ResultSet rs = DBServices.statement.executeQuery("select name, votes from candidates where position = '" + selectPosition.getValue() + "'");
        int highestVoteCount = 0;
        ObservableList<PieChart.Data> nameChartList = FXCollections.observableArrayList();
        ObservableList<PieChart.Data> numberChartList = FXCollections.observableArrayList();
        while (rs.next()) {
            numberChartList.add(new PieChart.Data(String.valueOf(rs.getString("votes")), rs.getInt("votes")));
            nameChartList.add(new PieChart.Data(String.valueOf(rs.getString("name")), rs.getInt("votes")));
            if (highestVoteCount < Integer.parseInt(rs.getString("votes"))) {
                highestVoteCount = Integer.parseInt(rs.getString("votes"));
            }
        }
        numberChart.setData(numberChartList);
        nameChart.setData(nameChartList);
        ResultSet winnerName = DBServices.statement.executeQuery("select name from candidates where votes = '" + highestVoteCount + "'");
        if (winnerName.next()) {
            winner.setText("Ms/Mr " + winnerName.getString(1) + " got elected securing " + highestVoteCount + " votes");
        }
    }
}
