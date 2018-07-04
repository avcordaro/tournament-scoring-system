package tss.java;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;


public class DashboardController extends Application {

    @FXML
    private Button btnEditTournament;
    @FXML
    private Button btnEditSaveTournament;
    @FXML
    private Button btnDeleteTournament;
    @FXML
    private StackPane stpEditSaveTournament;
    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtDate;
    @FXML
    private TextField txtArchersPerTarget;
    @FXML
    private TextField txtTotalArchers;
    @FXML
    private CheckBox chkMetric;
    @FXML
    private CheckBox chkTeams;
    @FXML
    private CheckBox chkMarriedCouples;
    @FXML
    private CheckBox chkWorstWhite;
    @FXML
    private CheckBox chkBestGold;
    @FXML
    private ComboBox<TournamentMap> cmbTournament;

    
    
    private static Connection conn;
    private static Tournaments tournaments;
    
    @FXML
    public void initialize() throws SQLException {
		conn = SQLiteConnection.getConnection();
		tournaments = new Tournaments(conn);
    	ResultSet rs = tournaments.getAllRecords();
    	
    	while(rs.next()) {
    		TournamentMap map = new TournamentMap(rs.getString("Title"), rs.getInt("TournamentID"));
    		cmbTournament.getItems().add(map);
    	}
    }
    
	public void start(Stage primaryStage) {
		try {
			Pane root = (Pane)FXMLLoader.load(getClass().getResource("DashboardView.fxml"));
			Scene scene = new Scene(root, 1200, 910);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Tournament Scoring System");
			primaryStage.getIcons().add(new Image("file:src/tss/resources/logo.png"));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws SQLException {
		launch(args);
	}
	
	@FXML
	public void loadTournament(ActionEvent event) throws SQLException {
		int id = cmbTournament.getSelectionModel().getSelectedItem().getID();
		ResultSet rs = tournaments.getSpecificRecord(id);
		txtTitle.setText(rs.getString("Title"));
		txtDate.setText(rs.getString("Date"));
		txtArchersPerTarget.setText(Integer.toString(rs.getInt("ArchersPerTarget")));
		chkMetric.setSelected(Boolean.valueOf(rs.getString("Metric")));
		chkTeams.setSelected(Boolean.valueOf(rs.getString("Teams")));
		chkMarriedCouples.setSelected(Boolean.valueOf(rs.getString("MarriedCouples")));
		chkBestGold.setSelected(Boolean.valueOf(rs.getString("BestGold")));
		chkWorstWhite.setSelected(Boolean.valueOf(rs.getString("WorstWhite")));
	}
}
