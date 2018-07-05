package tss.java;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
    private Button btnNewTournament;
    @FXML
    private StackPane stpEditSaveTournament;
    @FXML
    private StackPane stpTournamentDate;
    @FXML
    private DatePicker datePicker;
    @FXML
    private DatePicker newDatePicker;
    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtDate;
    @FXML
    private TextField txtArchersPerTarget;
    @FXML
    private TextField txtTotalArchers;
    @FXML
    private TextField txtNewTitle;
    @FXML
    private TextField txtNewArchersPerTarget;
    @FXML
    private TextField txtNewTotalArchers;
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
    private CheckBox chkNewMetric;
    @FXML
    private CheckBox chkNewTeams;
    @FXML
    private CheckBox chkNewMarriedCouples;
    @FXML
    private CheckBox chkNewWorstWhite;
    @FXML
    private CheckBox chkNewBestGold;
    @FXML
    private Label lblTotalArchers;
    @FXML
    private ComboBox<TournamentMap> cmbTournament;
    @FXML
    private VBox vboxTournament;

    
    private static Connection conn;
    private static Tournaments tournaments;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    @FXML
    public void initialize() throws SQLException {
		conn = SQLiteConnection.getConnection();
		tournaments = new Tournaments(conn);
		fillTournamentComboBox();
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
	
	public void fillTournamentComboBox() throws SQLException {
    	ResultSet rs = tournaments.getAllRecords();
    	while(rs.next()) {
    		TournamentMap map = new TournamentMap(rs.getString("Title"), rs.getInt("TournamentID"));
    		cmbTournament.getItems().add(map);
    	}
	}
	@FXML
	public void loadTournament(ActionEvent event) throws SQLException {
		if(!cmbTournament.getItems().isEmpty()) {
			int id = cmbTournament.getSelectionModel().getSelectedItem().getID();
			ResultSet rs = tournaments.getRecord(id);
			txtTitle.setText(rs.getString("Title"));
			txtDate.setText(rs.getString("Date"));
			LocalDate localDate = LocalDate.parse(rs.getString("Date"), formatter);
			datePicker.setValue(localDate);
			txtArchersPerTarget.setText(Integer.toString(rs.getInt("ArchersPerTarget")));
			chkMetric.setSelected(Boolean.valueOf(rs.getString("Metric")));
			chkTeams.setSelected(Boolean.valueOf(rs.getString("Teams")));
			chkMarriedCouples.setSelected(Boolean.valueOf(rs.getString("MarriedCouples")));
			chkBestGold.setSelected(Boolean.valueOf(rs.getString("BestGold")));
			chkWorstWhite.setSelected(Boolean.valueOf(rs.getString("WorstWhite")));
			btnEditTournament.setDisable(false);
			btnDeleteTournament.setDisable(false);
		}
	}
	
	@FXML
	public void newTournament(ActionEvent event) throws SQLException {
		String title = txtNewTitle.getText();
		String date = newDatePicker.getValue().format(formatter);
		String apt = txtNewArchersPerTarget.getText();
		String metric = Boolean.toString(chkNewMetric.isSelected());
		String teams = Boolean.toString(chkNewTeams.isSelected());
		String couples = Boolean.toString(chkNewMarriedCouples.isSelected());
		String bGold = Boolean.toString(chkNewBestGold.isSelected());
		String wWhite = Boolean.toString(chkNewWorstWhite.isSelected());
		int newID = tournaments.newRecord(title, date, apt, metric, teams, couples, bGold, wWhite);
		TournamentMap newMap = new TournamentMap(title, newID);
		cmbTournament.getItems().add(newMap);
		cmbTournament.getSelectionModel().select(newMap);
		txtNewTitle.clear();
		newDatePicker.getEditor().clear();
		txtNewArchersPerTarget.clear();
		chkNewMetric.setSelected(false);
		chkNewTeams.setSelected(false);
		chkNewMarriedCouples.setSelected(false);
		chkNewBestGold.setSelected(false);
		chkNewWorstWhite.setSelected(false);
	}
	
	@FXML
	public void deleteTournament(ActionEvent event) throws SQLException {
		int id = cmbTournament.getSelectionModel().getSelectedItem().getID();
		cmbTournament.getItems().remove(cmbTournament.getSelectionModel().getSelectedItem());
		btnEditTournament.setDisable(true);
		btnDeleteTournament.setDisable(true);
		btnEditSaveTournament.setVisible(false);
		tournaments.deleteRecord(id);
		txtTitle.clear();
		txtDate.clear();
		txtTotalArchers.clear();
		txtArchersPerTarget.clear();
		chkMetric.setSelected(false);
		chkTeams.setSelected(false);
		chkMarriedCouples.setSelected(false);
		chkWorstWhite.setSelected(false);
		chkBestGold.setSelected(false);
		loadTournament(event);
	}
	
	@FXML
	public void beginEditTournament(ActionEvent event) throws SQLException {
		btnEditSaveTournament.setVisible(true);
		stpEditSaveTournament.getChildren().get(0).toFront();
		btnEditTournament.setVisible(false);
		btnDeleteTournament.setDisable(true);
		txtTitle.setEditable(true);
		stpTournamentDate.getChildren().get(0).toFront();
		txtTotalArchers.setDisable(true);
		lblTotalArchers.setDisable(true);
		txtArchersPerTarget.setEditable(true);
		chkMetric.setDisable(false);
		chkTeams.setDisable(false);
		chkMarriedCouples.setDisable(false);
		chkWorstWhite.setDisable(false);
		chkBestGold.setDisable(false);
	}
	
	@FXML
	public void saveEditTournament(ActionEvent event) throws SQLException {
		btnEditTournament.setVisible(true);
		stpEditSaveTournament.getChildren().get(0).toFront();
		btnEditSaveTournament.setVisible(false);
		txtTitle.setEditable(false);
		stpTournamentDate.getChildren().get(0).toFront();
		txtTotalArchers.setDisable(false);
		lblTotalArchers.setDisable(false);
		txtArchersPerTarget.setEditable(false);
		chkMetric.setDisable(true);
		chkTeams.setDisable(true);
		chkMarriedCouples.setDisable(true);
		chkWorstWhite.setDisable(true);
		chkBestGold.setDisable(true);
		int id = cmbTournament.getSelectionModel().getSelectedItem().getID();
		String title = txtTitle.getText();
		String date = datePicker.getValue().format(formatter);
		int apt = Integer.parseInt(txtArchersPerTarget.getText());
		String metric = Boolean.toString(chkMetric.isSelected());
		String teams = Boolean.toString(chkTeams.isSelected());
		String couples = Boolean.toString(chkMarriedCouples.isSelected());
		String bGold = Boolean.toString(chkBestGold.isSelected());
		String wWhite = Boolean.toString(chkWorstWhite.isSelected());
		tournaments.updateRecord(id, title, date, apt, metric, teams, couples, bGold, wWhite);
		loadTournament(event);
	}
}
