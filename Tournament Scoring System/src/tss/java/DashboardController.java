package tss.java;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private Button btnEditArcher;
    @FXML
    private Button btnDeleteArcher;
    @FXML
    private Button btnSearchArcher;
    @FXML 
    private Button btnNewArcher;
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
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtClub;
    @FXML
    private CheckBox chkMetric;
    @FXML
    private CheckBox chkTeams;
    @FXML
    private CheckBox chkMarriedCouples;
    @FXML
    private CheckBox chkNewMetric;
    @FXML
    private CheckBox chkNewTeams;
    @FXML
    private CheckBox chkNewMarriedCouples;
    @FXML
    private Label lblTotalArchers;
    @FXML
    private ComboBox<TournamentMap> cmbTournament;
    @FXML
    private ComboBox<String> cmbCategory;
    @FXML
    private ComboBox<String> cmbBowType;
    @FXML
    private ComboBox<String> cmbRound;
    @FXML
    private ComboBox<String> cmbMarriedCouple;
    @FXML
    private VBox vboxTournament;
    @FXML
    private TableView<ArcherEntry> tbvArchers;
    
    private static Connection conn;
    private static Tournaments tournaments;
    private static Archers archers;
    private static MarriedCouples marriedCouples;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    
    @FXML
    public void initialize() throws SQLException {
		conn = SQLiteConnection.getConnection();
		tournaments = new Tournaments(conn);
		archers = new Archers(conn);
		marriedCouples = new MarriedCouples(conn);
		tbvArchers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("ID"));
		tbvArchers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("firstName"));
		tbvArchers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("lastName"));
		tbvArchers.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("club"));
		tbvArchers.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("category"));
		tbvArchers.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("bowType"));
		tbvArchers.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("round"));
		fillTournamentComboBox();
    	fillArcherEditorComboBoxes();
		tbvArchers.getSelectionModel().selectedItemProperty().addListener((o, oldS, newS) -> {
	    	btnEditArcher.setDisable(false);
	    	btnDeleteArcher.setDisable(false);
		});
    }
    
	public void start(Stage primaryStage) {
		try {
			Pane root = (Pane)FXMLLoader.load(getClass().getResource("DashboardView.fxml"));
			Scene scene = new Scene(root, 1200, 850);
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
			btnEditTournament.setDisable(false);
			btnDeleteTournament.setDisable(false);
			fillArcherTableView(id);
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
		int newID = tournaments.newRecord(title, date, apt, metric, teams, couples);
		TournamentMap newMap = new TournamentMap(title, newID);
		cmbTournament.getItems().add(newMap);
		cmbTournament.getSelectionModel().select(newMap);
		txtNewTitle.clear();
		newDatePicker.getEditor().clear();
		txtNewArchersPerTarget.clear();
		chkNewMetric.setSelected(false);
		chkNewTeams.setSelected(false);
		chkNewMarriedCouples.setSelected(false);
		btnSearchArcher.setDisable(false);
    	btnEditArcher.setDisable(true);
    	btnDeleteArcher.setDisable(true);
    	btnNewArcher.setDisable(false);
    	cmbMarriedCouple.setDisable(!chkMarriedCouples.isSelected());
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
    	btnSearchArcher.setDisable(true);
    	btnEditArcher.setDisable(true);
    	btnDeleteArcher.setDisable(true);
    	btnNewArcher.setDisable(true);
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
    	cmbMarriedCouple.setDisable(!chkMarriedCouples.isSelected());
		int id = cmbTournament.getSelectionModel().getSelectedItem().getID();
		String title = txtTitle.getText();
		String date = datePicker.getValue().format(formatter);
		int apt = Integer.parseInt(txtArchersPerTarget.getText());
		String metric = Boolean.toString(chkMetric.isSelected());
		String teams = Boolean.toString(chkTeams.isSelected());
		String couples = Boolean.toString(chkMarriedCouples.isSelected());
		tournaments.updateRecord(id, title, date, apt, metric, teams, couples);
		loadTournament(event);
	}
	
	public void fillArcherTableView(int tournamentID) throws SQLException {
		tbvArchers.getItems().clear();
		cmbMarriedCouple.getItems().clear();
		ResultSet rs = archers.getAllRecords(tournamentID);
		int archerCount = 0;
    	while(rs.next()) {
    		tbvArchers.getItems().add(new ArcherEntry(rs.getInt("ArcherID"), rs.getString("FirstName"), 
    				rs.getString("LastName"), rs.getString("Club"), rs.getString("Category"), 
    				rs.getString("BowType"), rs.getString("Round")));
    		cmbMarriedCouple.getItems().add(rs.getString("ArcherID") + " - "  + rs.getString("FirstName")
    				+ " " + rs.getString("LastName"));
    		archerCount++;
    	}
    	txtTotalArchers.setText(Integer.toString(archerCount));
    	cmbMarriedCouple.setDisable(!chkMarriedCouples.isSelected());
    	btnSearchArcher.setDisable(false);
    	btnNewArcher.setDisable(false);
	}
	
	public void fillArcherEditorComboBoxes() throws SQLException {
		ResultSet rs = archers.getCategories();
		while(rs.next()) {
			cmbCategory.getItems().add(rs.getString("Name"));
		}
		rs = archers.getBowTypes();
		while(rs.next()) {
			cmbBowType.getItems().add(rs.getString("Name"));
		}
		rs = archers.getRounds();
		while(rs.next()) {
			cmbRound.getItems().add(rs.getString("Name"));
		}
	}
	
	@FXML
	public void newArcher(ActionEvent event) throws SQLException {
		int tID = cmbTournament.getSelectionModel().getSelectedItem().getID();
		String fname = txtFirstName.getText();
		String lname = txtLastName.getText();
		String club = txtClub.getText();
		String cat = cmbCategory.getSelectionModel().getSelectedItem();
		String bow = cmbBowType.getSelectionModel().getSelectedItem();
		String round = cmbRound.getSelectionModel().getSelectedItem();
		int newArcherID = archers.newRecord(tID, fname, lname, club, cat, bow, round);
		if(!cmbMarriedCouple.getSelectionModel().isEmpty()) {
			String selection = cmbMarriedCouple.getSelectionModel().getSelectedItem();
			int spouseID = Integer.parseInt(selection.substring(0, selection.indexOf(" ")));
			marriedCouples.newRecord(newArcherID, spouseID);
		}
		txtFirstName.clear();
		txtLastName.clear();
		txtClub.clear();
		cmbCategory.getSelectionModel().clearSelection();
		cmbBowType.getSelectionModel().clearSelection();
		cmbRound.getSelectionModel().clearSelection();
		cmbMarriedCouple.getSelectionModel().clearSelection();
		fillArcherTableView(tID);
		tbvArchers.getSelectionModel().selectLast();
		tbvArchers.scrollTo(tbvArchers.getSelectionModel().getSelectedItem());
	}
	
	@FXML
	public void searchArcher(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Search Archers");
		dialog.setHeaderText(null);
		dialog.setContentText("Archer ID: ");
		DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("../resources/DashboardStylesheet.css").toExternalForm());
		Stage dialogStage = (Stage)dialog.getDialogPane().getScene().getWindow();
		dialogStage.getIcons().add(new Image("file:src/tss/resources/logo.png"));
		Optional<String> input = dialog.showAndWait();
		if(input.get().matches("\\d+")) {
			int id = Integer.parseInt(input.get());
			for(ArcherEntry archer: tbvArchers.getItems()) {
				if(id == archer.getID()) {
					tbvArchers.getSelectionModel().select(archer);
					tbvArchers.scrollTo(archer);
					return;
				}
			}
		}
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Archer not found");
		alert.setHeaderText(null);
		alert.setContentText("Archer for ID " + input.get() + " does not exist.");
		dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("../resources/DashboardStylesheet.css").toExternalForm());
		Stage alertStage = (Stage)alert.getDialogPane().getScene().getWindow();
		alertStage.getIcons().add(new Image("file:src/tss/resources/logo.png"));
		alert.showAndWait();
	}
	@FXML
	public void deleteArcher(ActionEvent event) throws SQLException {
		ArcherEntry selectedArcher = tbvArchers.getSelectionModel().getSelectedItem();
		archers.deleteRecord(selectedArcher.getID());
		fillArcherTableView(cmbTournament.getSelectionModel().getSelectedItem().getID());
		String newTotalArchers = Integer.toString(Integer.parseInt(txtTotalArchers.getText()) - 1);
		txtTotalArchers.setText(newTotalArchers);
		btnEditArcher.setDisable(true);
		btnDeleteArcher.setDisable(true);
	}
}
