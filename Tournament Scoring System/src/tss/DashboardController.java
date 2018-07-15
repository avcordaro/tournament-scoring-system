package tss;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.sf.jasperreports.engine.JRException;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private Button btnSaveEditArcher;
    @FXML
    private Button btnDeleteArcher;
    @FXML
    private Button btnSearchArcher;
    @FXML 
    private Button btnNewArcher;
    @FXML 
    private Button btnSearchTarget;
    @FXML 
    private Button btnEditTarget;
    @FXML
    private Button btnSaveEditTarget;
    @FXML 
    private Button btnAllocateTargets;
    @FXML 
    private Button btnGenerateTargetList;
    @FXML
    private Button btnSearchScore;
    @FXML
    private Button btnEditScore;
    @FXML
    private Button btnStartScores;
    @FXML
    private Button btnStopScores;
    @FXML
    private Button btnBackScore;
    @FXML
    private Button btnNextScore;
    @FXML
    private Button btnSaveEditScore;
    @FXML
    private StackPane stpEditSaveTournament;
    @FXML
    private StackPane stpTournamentDate;
    @FXML 
    private StackPane stpSaveArcher;
    @FXML
    private StackPane stpEditSaveTarget;
    @FXML
    private StackPane stpEditSaveScore;
    @FXML
    private StackPane stpStartStopScores;
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
    private TextField txtVenue;
    @FXML
    private TextField txtAssembly;
    @FXML
    private TextField txtSighters;
    @FXML
    private TextField txtScore;
    @FXML
    private TextField txtHits;
    @FXML
    private TextField txtGolds;
    @FXML
    private TextField txtXs;
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
    private Label lblVenue;
    @FXML
    private Label lblAssembly;
    @FXML
    private Label lblSighters;
    @FXML
    private Label lblScore;
    @FXML
    private Label lblHits;
    @FXML
    private Label lblGolds;
    @FXML
    private Label lblXs;
    @FXML
    private Label lblCurrentTarget;
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
    private ComboBox<String> cmbEditTarget;
    @FXML
    private VBox vboxTournament;
    @FXML
    private TableColumn<ScoreEntry, Integer> tbcXs;
    @FXML
    private TableView<ArcherEntry> tbvArchers;
    @FXML
    private TableView<TargetEntry> tbvTargetList;
    @FXML
    private TableView<ScoreEntry> tbvScores;
    @FXML
	private TitledPane tpNewTournament;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab tabArchers;
    @FXML
    private Tab tabTargetList;
    @FXML
    private Tab tabScores;
    @FXML
    private Tab tabResults;
    
    private static Connection conn;
    private static Tournaments tournaments;
    private static Archers archers;
    private static MarriedCouples marriedCouples;
    private static Targets targets;
    private static Scores scores;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    
    @FXML
    public void initialize() throws SQLException {
		conn = SQLiteConnection.getConnection();
		tournaments = new Tournaments(conn);
		archers = new Archers(conn);
		marriedCouples = new MarriedCouples(conn);
		targets = new Targets(conn);
		scores = new Scores(conn);
		tbvArchers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("ID"));
		tbvArchers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("firstName"));
		tbvArchers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("lastName"));
		tbvArchers.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("club"));
		tbvArchers.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("category"));
		tbvArchers.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("bowType"));
		tbvArchers.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("round"));
		tbvTargetList.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("ID"));
		tbvTargetList.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("firstName"));
		tbvTargetList.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("lastName"));
		tbvTargetList.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("club"));
		tbvTargetList.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("category"));
		tbvTargetList.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("round"));
		tbvTargetList.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("bowType"));
		tbvTargetList.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("target"));
		tbvScores.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("ID"));
		tbvScores.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("firstName"));
		tbvScores.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("lastName"));
		tbvScores.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("target"));
		tbvScores.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("score"));
		tbvScores.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("hits"));
		tbvScores.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("golds"));
		tbvScores.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("Xs"));
		fillTournamentComboBox();
    	fillArcherEditorComboBoxes();
		tbvArchers.getSelectionModel().selectedItemProperty().addListener((o, oldS, newS) -> {
	    	btnEditArcher.setDisable(false);
	    	btnDeleteArcher.setDisable(false);
		});
		tbvTargetList.getSelectionModel().selectedItemProperty().addListener((o, oldS, newS) -> {
			if(!tbvTargetList.getSelectionModel().isEmpty() && tbvTargetList.getSelectionModel().getSelectedItem()
					.getTarget() != null) {
				btnEditTarget.setDisable(false);
			}
		});
		tbvScores.getSelectionModel().selectedItemProperty().addListener((o, oldS, newS) -> {
	    	btnEditScore.setDisable(false);
		});
    }
    
	public void start(Stage primaryStage) {
		try {
			Pane root = (Pane)FXMLLoader.load(getClass().getResource("DashboardView.fxml"));
			Scene scene = new Scene(root, 1200, 850);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Tournament Scoring System");
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("resources/logo.png")));
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			    public void handle(WindowEvent t) {
			        System.exit(0);
			    }
			});
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws SQLException {
		launch(args);
	}
	
	public Optional<String> searchArcherDialog(String title) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Search Archers");
		dialog.setHeaderText(null);
		dialog.setContentText("Archer ID: ");
		DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("resources/DashboardStylesheet.css").toExternalForm());
		Stage dialogStage = (Stage)dialog.getDialogPane().getScene().getWindow();
		dialogStage.getIcons().add(new Image(getClass().getResourceAsStream("resources/logo.png")));
		Optional<String> input = dialog.showAndWait();
		return input;
	}
	
	public void archerNotFoundDialog(Optional<String> input) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Archer not found");
		alert.setHeaderText(null);
		alert.setContentText("Archer ID \"" + input.get() + "\" could not be found.");
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("resources/DashboardStylesheet.css").toExternalForm());
		Stage alertStage = (Stage)alert.getDialogPane().getScene().getWindow();
		alertStage.getIcons().add(new Image(getClass().getResourceAsStream("resources/logo.png")));
		alert.showAndWait();	
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
		if(!cmbTournament.getSelectionModel().isEmpty()) {
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
			tbvArchers.getItems().clear();
			tbvTargetList.getItems().clear();
			tbvScores.getItems().clear();
			txtFirstName.clear();
			txtLastName.clear();
			txtClub.clear();
			cmbCategory.getSelectionModel().clearSelection();
			cmbBowType.getSelectionModel().clearSelection();
			cmbRound.getSelectionModel().clearSelection();
			cmbMarriedCouple.getSelectionModel().clearSelection();
			btnSearchTarget.setDisable(true);
			btnEditTarget.setDisable(true);
			btnAllocateTargets.setDisable(true);
			btnGenerateTargetList.setDisable(true);
			lblVenue.setDisable(false);
			lblAssembly.setDisable(false);
			lblSighters.setDisable(false);
			txtVenue.setDisable(false);
			txtAssembly.setDisable(false);
			txtSighters.setDisable(false);
			txtVenue.clear();
			txtAssembly.clear();
			txtSighters.clear();
			fillArcherTableView(id);
			fillTargetListTableView(id);
			fillScoresTableView(id);
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
		btnDeleteTournament.setDisable(false);
		btnEditTournament.setDisable(false);
		tbvArchers.getItems().clear();
		tbvTargetList.getItems().clear();
		tbvScores.getItems().clear();
		txtFirstName.clear();
		txtLastName.clear();
		txtClub.clear();
		cmbCategory.getSelectionModel().clearSelection();
		cmbBowType.getSelectionModel().clearSelection();
		cmbRound.getSelectionModel().clearSelection();
		cmbMarriedCouple.getSelectionModel().clearSelection();
		btnSearchArcher.setDisable(true);
    	btnEditArcher.setDisable(true);
    	btnDeleteArcher.setDisable(true);
    	btnNewArcher.setDisable(false);
    	cmbMarriedCouple.setDisable(!chkMarriedCouples.isSelected());
		txtFirstName.clear();
		txtLastName.clear();
		txtClub.clear();
		cmbCategory.getSelectionModel().clearSelection();
		cmbBowType.getSelectionModel().clearSelection();
		cmbRound.getSelectionModel().clearSelection();
		cmbMarriedCouple.getSelectionModel().clearSelection();
		btnSearchTarget.setDisable(true);
		btnEditTarget.setDisable(true);
		btnAllocateTargets.setDisable(true);
		btnGenerateTargetList.setDisable(true);
		lblVenue.setDisable(true);
		lblAssembly.setDisable(true);
		lblSighters.setDisable(true);
		txtVenue.setDisable(true);
		txtAssembly.setDisable(true);
		txtSighters.setDisable(true);
		txtVenue.clear();
		txtAssembly.clear();
		txtSighters.clear();
		btnSearchScore.setDisable(true);
		btnEditScore.setDisable(true);
		btnStartScores.setDisable(true);
		btnBackScore.setDisable(true);
		btnNextScore.setDisable(true);
		txtScore.setDisable(true);
		txtHits.setDisable(true);
		txtGolds.setDisable(true);
		txtXs.setDisable(true);
		lblScore.setDisable(true);
		lblHits.setDisable(true);
		lblGolds.setDisable(true);
		lblXs.setDisable(true);
	}
	
	@FXML
	public void deleteTournament(ActionEvent event) throws SQLException {
		int id = cmbTournament.getSelectionModel().getSelectedItem().getID();
		cmbTournament.getItems().remove(cmbTournament.getSelectionModel().getSelectedItem());
		btnEditTournament.setDisable(true);
		btnDeleteTournament.setDisable(true);
		btnEditSaveTournament.setVisible(false);
		tournaments.deleteRecord(id, archers);
		txtTitle.clear();
		txtDate.clear();
		txtTotalArchers.clear();
		txtArchersPerTarget.clear();
		chkMetric.setSelected(false);
		chkTeams.setSelected(false);
		chkMarriedCouples.setSelected(false);
		tbvArchers.getItems().clear();
		tbvTargetList.getItems().clear();
		tbvScores.getItems().clear();
    	btnSearchArcher.setDisable(true);
    	btnEditArcher.setDisable(true);
    	btnDeleteArcher.setDisable(true);
    	btnNewArcher.setDisable(true);
		txtFirstName.clear();
		txtLastName.clear();
		txtClub.clear();
		cmbCategory.getSelectionModel().clearSelection();
		cmbBowType.getSelectionModel().clearSelection();
		cmbRound.getSelectionModel().clearSelection();
		cmbMarriedCouple.getSelectionModel().clearSelection();
		btnSearchTarget.setDisable(true);
		btnEditTarget.setDisable(true);
		txtFirstName.clear();
		txtLastName.clear();
		txtClub.clear();
		cmbCategory.getSelectionModel().clearSelection();
		cmbBowType.getSelectionModel().clearSelection();
		cmbRound.getSelectionModel().clearSelection();
		cmbMarriedCouple.getSelectionModel().clearSelection();
		btnAllocateTargets.setDisable(true);
		btnGenerateTargetList.setDisable(true);
		lblVenue.setDisable(true);
		lblAssembly.setDisable(true);
		lblSighters.setDisable(true);
		txtVenue.setDisable(true);
		txtAssembly.setDisable(true);
		txtSighters.setDisable(true);
		txtVenue.clear();
		txtAssembly.clear();
		txtSighters.clear();
		btnSearchScore.setDisable(true);
		btnEditScore.setDisable(true);
		btnStartScores.setDisable(true);
		btnBackScore.setDisable(true);
		btnNextScore.setDisable(true);
		txtScore.setDisable(true);
		txtHits.setDisable(true);
		txtGolds.setDisable(true);
		txtXs.setDisable(true);
		lblScore.setDisable(true);
		lblHits.setDisable(true);
		lblGolds.setDisable(true);
		lblXs.setDisable(true);
		loadTournament(event);
	}
	
	@FXML
	public void beginEditTournament(ActionEvent event) throws SQLException {
		tabPane.setDisable(true);
		tabPane.setOpacity(1.00);
		tpNewTournament.setDisable(true);
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
		tabPane.setDisable(false);
		tpNewTournament.setDisable(false);
		loadTournament(event);
	}
	
	public void fillArcherTableView(int tournamentID) throws SQLException {
		tbvArchers.getItems().clear();
		cmbMarriedCouple.getItems().clear();
		cmbMarriedCouple.getItems().add("None");
		ResultSet rs = archers.getAllRecords(tournamentID);
		int archerCount = 0;
		btnSearchArcher.setDisable(rs.isBeforeFirst() ? false : true);
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
			if(!cmbMarriedCouple.getSelectionModel().getSelectedItem().equals("None")) {
				String selection = cmbMarriedCouple.getSelectionModel().getSelectedItem();
				int spouseID = Integer.parseInt(selection.substring(0, selection.indexOf(" ")));
				marriedCouples.newRecord(newArcherID, spouseID);
			}
		}
		scores.newRecord(newArcherID);
		txtFirstName.clear();
		txtLastName.clear();
		txtClub.clear();
		cmbCategory.getSelectionModel().clearSelection();
		cmbBowType.getSelectionModel().clearSelection();
		cmbRound.getSelectionModel().clearSelection();
		cmbMarriedCouple.getSelectionModel().clearSelection();
		fillArcherTableView(tID);
		fillTargetListTableView(tID);
		fillScoresTableView(tID);
		tbvArchers.getSelectionModel().selectLast();
		tbvArchers.scrollTo(tbvArchers.getSelectionModel().getSelectedItem());
	}
	
	@FXML
	public void beginEditArcher(ActionEvent event) throws SQLException {
		cmbTournament.setDisable(true);
		tpNewTournament.setDisable(true);
		btnEditTournament.setDisable(true);
		btnDeleteTournament.setDisable(true);
		tabTargetList.setDisable(true);
		tabScores.setDisable(true);
		tabResults.setDisable(true);
		btnSearchArcher.setDisable(true);
		btnEditArcher.setDisable(true);
		btnDeleteArcher.setDisable(true);
		tbvArchers.setDisable(true);
		tbvArchers.setOpacity(1.00);
		ArcherEntry archer = tbvArchers.getSelectionModel().getSelectedItem();
		txtFirstName.setText(archer.getFirstName());
		txtLastName.setText(archer.getLastName());
		txtClub.setText(archer.getClub());
		cmbCategory.getSelectionModel().select(archer.getCategory());
		cmbBowType.getSelectionModel().select(archer.getBowType());
		cmbRound.getSelectionModel().select(archer.getRound());
		ResultSet rs = marriedCouples.getRecord(archer.getID());
		if(rs.isBeforeFirst()) {
			if(rs.getInt(1) == archer.getID()) {
				cmbMarriedCouple.getSelectionModel().select(rs.getString(2) + " - " + rs.getString(3)
				+ " " + rs.getString(4));
			} else {
				cmbMarriedCouple.getSelectionModel().select(rs.getString(1) + " - " + rs.getString(3)
						+ " " + rs.getString(4));
				cmbMarriedCouple.setDisable(true);
				cmbMarriedCouple.setOpacity(1.00);
			}
		}
		btnSaveEditArcher.setVisible(true);
		btnNewArcher.setVisible(false);
		stpSaveArcher.getChildren().get(0).toFront();
	}
	
	@FXML
	public void saveEditArcher(ActionEvent event) throws SQLException {
		btnSaveEditArcher.setVisible(false);
		btnNewArcher.setVisible(true);
		tbvArchers.setDisable(false);
		stpSaveArcher.getChildren().get(0).toFront();
		int id = tbvArchers.getSelectionModel().getSelectedItem().getID();
		String fname = txtFirstName.getText();
		String lname = txtLastName.getText();
		String club = txtClub.getText();
		String cat = cmbCategory.getSelectionModel().getSelectedItem();
		String bow = cmbBowType.getSelectionModel().getSelectedItem();
		String round = cmbRound.getSelectionModel().getSelectedItem();
		archers.updateRecord(id, fname, lname, club, cat, bow, round);
		if(!cmbMarriedCouple.getSelectionModel().isEmpty()) {
			String selection = cmbMarriedCouple.getSelectionModel().getSelectedItem();
			marriedCouples.updateRecord(id, selection);
		}
		int index = tbvArchers.getSelectionModel().getSelectedIndex();
		int tID = cmbTournament.getSelectionModel().getSelectedItem().getID();
		fillArcherTableView(tID);
		fillTargetListTableView(tID);
		fillScoresTableView(tID);
		tbvArchers.getSelectionModel().select(index);
		txtFirstName.clear();
		txtLastName.clear();
		txtClub.clear();
		cmbCategory.getSelectionModel().clearSelection();
		cmbBowType.getSelectionModel().clearSelection();
		cmbRound.getSelectionModel().clearSelection();
		cmbMarriedCouple.getSelectionModel().clearSelection();
		cmbTournament.setDisable(false);
		tpNewTournament.setDisable(false);
		btnEditTournament.setDisable(false);
		btnDeleteTournament.setDisable(false);
		tabTargetList.setDisable(false);
		tabScores.setDisable(false);
		tabResults.setDisable(false);
		btnSearchArcher.setDisable(false);
	}
	
	@FXML
	public void searchArcher(ActionEvent event) {
		Optional<String> input = searchArcherDialog("Search Archer");
		if(input.isPresent() && input.get().matches("\\d+")) {
			int id = Integer.parseInt(input.get());
			for(ArcherEntry archer: tbvArchers.getItems()) {
				if(id == archer.getID()) {
					tbvArchers.getSelectionModel().select(archer);
					tbvArchers.scrollTo(archer);
					return;
				}
			}
		}
		if(input.isPresent()) {
			archerNotFoundDialog(input);
		}
	}
	
	@FXML
	public void deleteArcher(ActionEvent event) throws SQLException {
		ArcherEntry selectedArcher = tbvArchers.getSelectionModel().getSelectedItem();
		archers.deleteRecord(selectedArcher.getID());
		scores.deletedRecord(selectedArcher.getID());
		int tID = cmbTournament.getSelectionModel().getSelectedItem().getID();
		fillArcherTableView(tID);
		fillTargetListTableView(tID);
		fillScoresTableView(tID);
		btnEditArcher.setDisable(true);
		btnDeleteArcher.setDisable(true);
	}

	public void fillTargetListTableView(int tournamentID) throws SQLException {
		tbvTargetList.getItems().clear();
		ArrayList<TargetEntry> data = targets.getDataForTable(tournamentID);
		btnSearchTarget.setDisable(data.isEmpty() ? true : false);
		btnAllocateTargets.setDisable(data.isEmpty() ? true : false);
		btnGenerateTargetList.setDisable(data.isEmpty() ? true : false);
		lblVenue.setDisable(data.isEmpty() ? true : false);
		lblAssembly.setDisable(data.isEmpty() ? true : false);
		lblSighters.setDisable(data.isEmpty() ? true : false);
		txtVenue.setDisable(data.isEmpty() ? true : false);
		txtAssembly.setDisable(data.isEmpty() ? true : false);
		txtSighters.setDisable(data.isEmpty() ? true : false);
    	if(!data.isEmpty()) {
    		for(TargetEntry entry : data) {
    			tbvTargetList.getItems().add(entry);
    		}
    	}
	}
	
	@FXML
	public void allocateTargetDetails(ActionEvent event) throws SQLException {
		int tID = cmbTournament.getSelectionModel().getSelectedItem().getID();
		targets.assignAllDetails(tID, Integer.parseInt(txtArchersPerTarget.getText()));
		fillTargetListTableView(tID);
		fillScoresTableView(tID);
	}
	
	@FXML
	public void searchArcherTarget(ActionEvent event) {
		Optional<String> input = searchArcherDialog("Search Archer's Target");
		if(input.isPresent() && input.get().matches("^\\d+$")) {
			int id = Integer.parseInt(input.get());
			for(TargetEntry entry: tbvTargetList.getItems()) {
				if(id == entry.getID()) {
					tbvTargetList.getSelectionModel().select(entry);
					tbvTargetList.scrollTo(entry);
					return;
				}
			}
		} 
		if(input.isPresent()) {
			archerNotFoundDialog(input);
		}
	}
	
	@FXML
	public void beginEditTargetDetail(ActionEvent event) throws SQLException {
		cmbTournament.setDisable(true);
		tpNewTournament.setDisable(true);
		btnEditTournament.setDisable(true);
		btnDeleteTournament.setDisable(true);
		tabArchers.setDisable(true);
		tabScores.setDisable(true);
		tabResults.setDisable(true);
		btnSearchTarget.setDisable(true);
		btnAllocateTargets.setDisable(true);
		btnGenerateTargetList.setDisable(true);
		btnSaveEditTarget.setVisible(true);
		btnEditTarget.setVisible(false);
		tbvTargetList.setDisable(true);
		tbvTargetList.setOpacity(1.00);
		stpEditSaveTarget.getChildren().get(0).toFront();
		fillSwapTargetComboBox();
		cmbEditTarget.setDisable(false);
	}
	
	@FXML
	public void saveEditTargetDetail(ActionEvent event) throws SQLException {
		int tID = cmbTournament.getSelectionModel().getSelectedItem().getID();
		if(!cmbEditTarget.getSelectionModel().isEmpty() && !cmbEditTarget.getSelectionModel().getSelectedItem().equals("None")) {
			String target1 = cmbEditTarget.getSelectionModel().getSelectedItem().split(" ")[0].trim();
			String target2 = tbvTargetList.getSelectionModel().getSelectedItem().getTarget();
			targets.swapTargetDetails(tID, target1, target2);
		}
		btnSaveEditTarget.setVisible(false);
		btnEditTarget.setVisible(true);
		tbvTargetList.setDisable(false);
		stpEditSaveTarget.getChildren().get(0).toFront();
		cmbEditTarget.getSelectionModel().clearSelection();
		cmbEditTarget.getItems().clear();
		fillTargetListTableView(tID);
		fillScoresTableView(tID);
		btnEditTarget.setDisable(true);
		cmbEditTarget.setDisable(true);
		cmbTournament.setDisable(false);
		tpNewTournament.setDisable(false);
		btnEditTournament.setDisable(false);
		btnDeleteTournament.setDisable(false);
		tabArchers.setDisable(false);
		tabScores.setDisable(false);
		tabResults.setDisable(false);
		btnSearchTarget.setDisable(false);
		btnAllocateTargets.setDisable(false);
		btnGenerateTargetList.setDisable(false);
	}
	
	public void fillSwapTargetComboBox() throws SQLException {
		int tID = cmbTournament.getSelectionModel().getSelectedItem().getID();
		TargetEntry selectedEntry = tbvTargetList.getSelectionModel().getSelectedItem();
		targets.fillSwapDetailsComboBox(tID, selectedEntry, cmbEditTarget);
	}
	
	@FXML 
	public void previewTargetList(ActionEvent event) throws SQLException, JRException, ParseException {
		int tID = cmbTournament.getSelectionModel().getSelectedItem().getID();
		String title = txtTitle.getText();
		SimpleDateFormat dateFormat = new SimpleDateFormat("d/MM/yyyy");
		Date dateUnformatted = dateFormat.parse(txtDate.getText());
		DateFormat formatter = DateFormat.getDateInstance(DateFormat.FULL);
		String date = formatter.format(dateUnformatted);
		String venue = txtVenue.getText();
		String assembly = txtAssembly.getText();
		String sighters = txtSighters.getText();
		targets.previewTargetList(tID, title, date, venue, assembly, sighters);
	}
	
	public void fillScoresTableView(int tournamentID) throws SQLException {
		tbvScores.getItems().clear();
		btnEditScore.setDisable(true);
		tbcXs.setVisible(chkMetric.isSelected() ? true : false);
		ArrayList<ScoreEntry> tableData = scores.getDataForTable(tournamentID);
		for(ScoreEntry entry : tableData) {
			tbvScores.getItems().add(entry);
		}
		btnSearchScore.setDisable(tableData.isEmpty() ? true : false);
		btnStartScores.setDisable(tableData.isEmpty() ? true : false);
	}
	
	@FXML
	public void startScoring(ActionEvent event) {
		cmbTournament.setDisable(true);
		tpNewTournament.setDisable(true);
		btnEditTournament.setDisable(true);
		btnDeleteTournament.setDisable(true);
		tabArchers.setDisable(true);
		tabTargetList.setDisable(true);
		tabResults.setDisable(true);
		btnStopScores.setVisible(true);
		btnStartScores.setVisible(false);
		btnNextScore.setDisable(false);
		stpStartStopScores.getChildren().get(0).toFront();
		btnSearchScore.setDisable(true);
		btnEditScore.setDisable(true);
		tbvScores.setDisable(true);
		tbvScores.setOpacity(1.00);
		txtScore.setDisable(false);
		txtHits.setDisable(false);
		txtGolds.setDisable(false);
		txtXs.setDisable(chkMetric.isSelected() ? false : true);
		lblScore.setDisable(false);
		lblHits.setDisable(false);
		lblGolds.setDisable(false);
		lblXs.setDisable(chkMetric.isSelected() ? false : true);
		tbvScores.getSelectionModel().select(0);
		ScoreEntry firstEntry = tbvScores.getSelectionModel().getSelectedItem();
		btnEditScore.setDisable(true);
		lblCurrentTarget.setVisible(true);
		lblCurrentTarget.setText("Target: " + firstEntry.getTarget());
		txtScore.setText(Integer.toString(firstEntry.getScore()));
		txtHits.setText(Integer.toString(firstEntry.getHits()));
		txtGolds.setText(Integer.toString(firstEntry.getGolds()));
		if(chkMetric.isSelected()) {
			txtXs.setText(Integer.toString(firstEntry.getXs()));
		}
		txtScore.requestFocus();
	}
	
	@FXML
	public void stopScoring(ActionEvent event) throws SQLException {
		cmbTournament.setDisable(false);
		tpNewTournament.setDisable(false);
		btnEditTournament.setDisable(false);
		btnDeleteTournament.setDisable(false);
		tabArchers.setDisable(false);
		tabTargetList.setDisable(false);
		tabResults.setDisable(false);
		btnStopScores.setVisible(false);
		btnStartScores.setVisible(true);
		btnNextScore.setDisable(true);
		btnBackScore.setDisable(true);
		stpStartStopScores.getChildren().get(0).toFront();
		btnSearchScore.setDisable(false);
		tbvScores.setDisable(false);
		txtScore.setDisable(true);
		txtHits.setDisable(true);
		txtGolds.setDisable(true);
		txtXs.setDisable(true);
		txtScore.clear();
		txtHits.clear();
		txtGolds.clear();
		txtXs.clear();
		lblScore.setDisable(true);
		lblHits.setDisable(true);
		lblGolds.setDisable(true);
		txtXs.setDisable(true);
		lblCurrentTarget.setVisible(false);
		fillScoresTableView(cmbTournament.getSelectionModel().getSelectedItem().getID());
	}
	
	@FXML
	public void nextScore(ActionEvent event) throws SQLException {
		ScoreEntry currentEntry = tbvScores.getSelectionModel().getSelectedItem();
		int score = Integer.parseInt(txtScore.getText());
		int hits = Integer.parseInt(txtHits.getText());
		int golds = Integer.parseInt(txtGolds.getText());
		currentEntry.setScore(score);
		currentEntry.setHits(hits);
		currentEntry.setGolds(golds);
		if(chkMetric.isSelected()) {
			int Xs = Integer.parseInt(txtXs.getText());
			currentEntry.setXs(Xs);
			scores.updateRecord(currentEntry.getID(), score, hits, golds, Xs);
		} else {
			scores.updateRecord(currentEntry.getID(), score, hits, golds);
		}
		tbvScores.refresh();
		int currentIndex = tbvScores.getSelectionModel().getSelectedIndex();
		if(currentIndex != tbvScores.getItems().size() - 1) {
			btnBackScore.setDisable(false);
			tbvScores.getSelectionModel().select(currentIndex + 1);
			tbvScores.scrollTo(currentIndex + 1);
			btnEditScore.setDisable(true);
			currentEntry = tbvScores.getSelectionModel().getSelectedItem();
			lblCurrentTarget.setText("Target: " + currentEntry.getTarget());
			txtScore.setText(Integer.toString(currentEntry.getScore()));
			txtHits.setText(Integer.toString(currentEntry.getHits()));
			txtGolds.setText(Integer.toString(currentEntry.getGolds()));
			if(chkMetric.isSelected()) {
				txtXs.setText(Integer.toString(currentEntry.getXs()));
			}
			txtScore.requestFocus();
		} else {
			stopScoring(event);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Score Inputs Finished");
			alert.setHeaderText(null);
			alert.setContentText("Thank you, all archer's score inputs have been completed.");
			DialogPane dialogPane = alert.getDialogPane();
			dialogPane = alert.getDialogPane();
			dialogPane.getStylesheets().add(getClass().getResource("resources/DashboardStylesheet.css").toExternalForm());
			Stage alertStage = (Stage)alert.getDialogPane().getScene().getWindow();
			alertStage.getIcons().add(new Image(getClass().getResourceAsStream("resources/logo.png")));
			alert.showAndWait();	
		}
	}
	
	@FXML
	public void backScore(ActionEvent event) {
		int currentIndex = tbvScores.getSelectionModel().getSelectedIndex();
		tbvScores.getSelectionModel().select(currentIndex - 1);
		if(currentIndex - 1 == 0) {
			btnBackScore.setDisable(true);
		}
		ScoreEntry currentEntry = tbvScores.getSelectionModel().getSelectedItem();
		btnEditScore.setDisable(true);
		lblCurrentTarget.setVisible(true);
		lblCurrentTarget.setText("Target: " + currentEntry.getTarget());
		txtScore.setText(Integer.toString(currentEntry.getScore()));
		txtHits.setText(Integer.toString(currentEntry.getHits()));
		txtGolds.setText(Integer.toString(currentEntry.getGolds()));
		if(chkMetric.isSelected()) {
			txtXs.setText(Integer.toString(currentEntry.getXs()));
		}
		txtScore.requestFocus();
	}
	
	@FXML
    public void enterPressedForScore(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER) && btnNextScore.isVisible()) {
             btnNextScore.fire();
             event.consume();
        }
    }
	
	@FXML
	public void searchArcherScore(ActionEvent event) {
		Optional<String> input = searchArcherDialog("Search Archer's Score");
		if(input.isPresent() && input.get().matches("\\d+")) {
			int id = Integer.parseInt(input.get());
			for(ScoreEntry score: tbvScores.getItems()) {
				if(id == score.getID()) {
					tbvScores.getSelectionModel().select(score);
					tbvScores.scrollTo(score);
					return;
				}
			}
		}
		if(input.isPresent()) {
			archerNotFoundDialog(input);
		}
	}
	
	@FXML
	public void beginEditArcherScore(ActionEvent event) {
		cmbTournament.setDisable(true);
		tpNewTournament.setDisable(true);
		btnEditTournament.setDisable(true);
		btnDeleteTournament.setDisable(true);
		tabArchers.setDisable(true);
		tabTargetList.setDisable(true);
		tabResults.setDisable(true);
		btnSearchScore.setDisable(true);
		btnEditScore.setDisable(true);
		btnStartScores.setDisable(true);
		btnNextScore.setVisible(false);
		btnBackScore.setVisible(false);
		btnSaveEditScore.setVisible(true);
		stpEditSaveScore.getChildren().get(0).toFront();
		ScoreEntry selectedEntry = tbvScores.getSelectionModel().getSelectedItem();
		tbvScores.setDisable(true);
		tbvScores.setOpacity(1.00);
		txtScore.setDisable(false);
		txtHits.setDisable(false);
		txtGolds.setDisable(false);
		lblScore.setDisable(false);
		lblHits.setDisable(false);
		lblGolds.setDisable(false);
		txtScore.setText(Integer.toString(selectedEntry.getScore()));
		txtHits.setText(Integer.toString(selectedEntry.getHits()));
		txtGolds.setText(Integer.toString(selectedEntry.getGolds()));
		if(chkMetric.isSelected()) {
			txtXs.setDisable(false);
			lblXs.setDisable(false);
			txtXs.setText(Integer.toString(selectedEntry.getXs()));
		}
	}
	
	@FXML
	public void saveEditArcherScore(ActionEvent event) throws SQLException {
		cmbTournament.setDisable(false);
		tpNewTournament.setDisable(false);
		btnEditTournament.setDisable(false);
		btnDeleteTournament.setDisable(false);
		tabArchers.setDisable(false);
		tabTargetList.setDisable(false);
		tabResults.setDisable(false);
		btnSearchScore.setDisable(false);
		btnEditScore.setDisable(false);
		btnStartScores.setDisable(false);
		btnNextScore.setVisible(true);
		btnBackScore.setVisible(true);
		btnSaveEditScore.setVisible(false);
		stpEditSaveScore.getChildren().get(0).toFront();
		ScoreEntry selectedEntry = tbvScores.getSelectionModel().getSelectedItem();
		int score = Integer.parseInt(txtScore.getText());
		int hits = Integer.parseInt(txtHits.getText());
		int golds = Integer.parseInt(txtGolds.getText());
		selectedEntry.setScore(score);
		selectedEntry.setHits(hits);
		selectedEntry.setGolds(golds);
		if(chkMetric.isSelected()) {
			int Xs = Integer.parseInt(txtXs.getText());
			selectedEntry.setXs(Xs);
			scores.updateRecord(selectedEntry.getID(), score, hits, golds, Xs);
			txtXs.clear();
			txtXs.setDisable(true);
			lblXs.setDisable(true);
		} else {
			scores.updateRecord(selectedEntry.getID(), score, hits, golds);
		}
		tbvScores.setDisable(false);
		tbvScores.refresh();
		txtScore.clear();
		txtHits.clear();
		txtGolds.clear();
		txtScore.setDisable(true);
		txtHits.setDisable(true);
		txtGolds.setDisable(true);
		lblScore.setDisable(true);
		lblHits.setDisable(true);
		lblGolds.setDisable(true);
	}
}
