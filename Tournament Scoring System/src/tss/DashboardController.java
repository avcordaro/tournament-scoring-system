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
import java.util.HashSet;
import java.util.Optional;

import javax.swing.ImageIcon;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
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
    private Button btnGenerateIndividualResults;
    @FXML
    private Button btnGenerateCoupleResults;
    @FXML
    private Button btnGenerateTeamResults;
    @FXML
    private Button btnExportFullResults;
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
    private TextField txtArchersPerTeam;
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
    private CheckBox chkTeamCompound;
    @FXML
    private CheckBox chkTeamRecurve;
    @FXML
    private CheckBox chkTeamBarebow;
    @FXML
    private CheckBox chkTeamLongbow;
    @FXML
    private RadioButton rdbGenderMixed;
    @FXML
    private RadioButton rdbGenderSeparate;
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
    private Label lblBestGold;
    @FXML
    private Label lblWorstWhite;
    @FXML
    private Label lblTeamRound;
    @FXML
    private Label lblArchersPerTeam;
    @FXML
    private Label lblTeamCompound;
    @FXML
    private Label lblTeamRecurve;
    @FXML
    private Label lblTeamBarebow;
    @FXML
    private Label lblTeamLongbow;
    @FXML
    private Label lblGenderMixed;
    @FXML
    private Label lblGenderSeparate;
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
    private ComboBox<String> cmbBestGold;
    @FXML
    private ComboBox<String> cmbWorstWhite;
    @FXML
    private ComboBox<String> cmbTeamRound;
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
    private static Results results;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    
    @FXML
    public void initialize() {
		conn = SQLiteConnection.getConnection();
		tournaments = new Tournaments(conn);
		archers = new Archers(conn);
		marriedCouples = new MarriedCouples(conn);
		targets = new Targets(conn);
		scores = new Scores(conn);
		results = new Results(conn);
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
	
	public static void main(String[] args) {
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
	
	public void informationDialog(String title, String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("resources/DashboardStylesheet.css").toExternalForm());
		Stage alertStage = (Stage)alert.getDialogPane().getScene().getWindow();
		alertStage.getIcons().add(new Image(getClass().getResourceAsStream("resources/logo.png")));
		alert.showAndWait();	
	}
	
	public void fillTournamentComboBox() {
    	ResultSet rs = tournaments.getAllRecords();
    	try {
	    	while(rs.next()) {
	    		TournamentMap map = new TournamentMap(rs.getString("Title"), rs.getInt("TournamentID"));
	    		cmbTournament.getItems().add(map);
	    	}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void loadTournament(ActionEvent event) {
		if(!cmbTournament.getSelectionModel().isEmpty()) {
			int id = cmbTournament.getSelectionModel().getSelectedItem().getID();
			try {
				ResultSet rs = tournaments.getRecord(id);
				txtTitle.setText(rs.getString("Title"));
				txtDate.setText(rs.getString("Date"));
				LocalDate localDate = LocalDate.parse(rs.getString("Date"), formatter);
				datePicker.setValue(localDate);
				txtArchersPerTarget.setText(Integer.toString(rs.getInt("ArchersPerTarget")));
				chkMetric.setSelected(Boolean.valueOf(rs.getString("Metric")));
				chkTeams.setSelected(Boolean.valueOf(rs.getString("Teams")));
				chkMarriedCouples.setSelected(Boolean.valueOf(rs.getString("MarriedCouples")));
			} catch(SQLException e) {
				e.printStackTrace();
			}
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
			cmbTeamRound.getSelectionModel().clearSelection();
			txtArchersPerTeam.clear();
			chkTeamCompound.setSelected(false);
			chkTeamRecurve.setSelected(false);
			chkTeamBarebow.setSelected(false);
			chkTeamLongbow.setSelected(false);
			rdbGenderMixed.setSelected(true);
			rdbGenderSeparate.setSelected(false);
			cmbBestGold.getSelectionModel().clearSelection();
			cmbWorstWhite.getSelectionModel().clearSelection();
			fillArcherTableView(id);
			fillTargetListTableView(id);
			fillScoresTableView(id);
		}
	}
	
	@FXML
	public void newTournament(ActionEvent event) {
		String title = txtNewTitle.getText();
		String date = newDatePicker.getValue().format(formatter);
		String apt = txtNewArchersPerTarget.getText();
		String metric = Boolean.toString(chkNewMetric.isSelected());
		String teams = Boolean.toString(chkNewTeams.isSelected());
		String couples = Boolean.toString(chkNewMarriedCouples.isSelected());
		if(!Validation.validatePresence(title) || !Validation.validatePresence(apt) || !Validation.validatePresence(date)) {
			informationDialog("Missing Details", "You have not filled in all textfields.");
			return;
		} 
		if(!Validation.validateAsName(title) || !Validation.validateAsInteger(apt)) {
			informationDialog("Invalid Details", "You have entered invalid details into one or more of the textfields.");
			return;
		}
		int aptNumeric = Integer.parseInt(apt);
		int newID = tournaments.newRecord(title, date, aptNumeric, metric, teams, couples);
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
		lblBestGold.setDisable(true);
		lblWorstWhite.setDisable(true);
		cmbBestGold.setDisable(true);
		cmbWorstWhite.setDisable(true);
		btnGenerateIndividualResults.setDisable(true);
		btnGenerateCoupleResults.setDisable(true);
		cmbTeamRound.setDisable(true);
		lblTeamRound.setDisable(true);
		txtArchersPerTeam.setDisable(true);
		lblArchersPerTeam.setDisable(true);
		chkTeamCompound.setDisable(true);
		lblTeamCompound.setDisable(true);
		chkTeamRecurve.setDisable(true);
		lblTeamRecurve.setDisable(true);
		chkTeamBarebow.setDisable(true);
		lblTeamBarebow.setDisable(true);
		chkTeamLongbow.setDisable(true);
		lblTeamLongbow.setDisable(true);
		rdbGenderMixed.setDisable(true);
		lblGenderMixed.setDisable(true);
		rdbGenderSeparate.setDisable(true);
		lblGenderSeparate.setDisable(true);
		btnGenerateTeamResults.setDisable(true);
		btnExportFullResults.setDisable(true);
	}
	@FXML
	public void deleteTournament(ActionEvent event) {
		int id = cmbTournament.getSelectionModel().getSelectedItem().getID();
		cmbTournament.getItems().remove(cmbTournament.getSelectionModel().getSelectedItem());
		btnEditTournament.setDisable(true);
		btnDeleteTournament.setDisable(true);
		btnEditSaveTournament.setVisible(false);
		tournaments.deleteRecord(id, archers, scores, marriedCouples);
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
		lblBestGold.setDisable(true);
		lblWorstWhite.setDisable(true);
		cmbBestGold.setDisable(true);
		cmbWorstWhite.setDisable(true);
		btnGenerateIndividualResults.setDisable(true);
		btnGenerateCoupleResults.setDisable(true);
		cmbTeamRound.setDisable(true);
		lblTeamRound.setDisable(true);
		txtArchersPerTeam.setDisable(true);
		lblArchersPerTeam.setDisable(true);
		chkTeamCompound.setDisable(true);
		lblTeamCompound.setDisable(true);
		chkTeamRecurve.setDisable(true);
		lblTeamRecurve.setDisable(true);
		chkTeamBarebow.setDisable(true);
		lblTeamBarebow.setDisable(true);
		chkTeamLongbow.setDisable(true);
		lblTeamLongbow.setDisable(true);
		rdbGenderMixed.setDisable(true);
		lblGenderMixed.setDisable(true);
		rdbGenderSeparate.setDisable(true);
		lblGenderSeparate.setDisable(true);
		btnGenerateTeamResults.setDisable(true);
		btnExportFullResults.setDisable(true);
		loadTournament(event);
	}
	
	@FXML
	public void beginEditTournament(ActionEvent event) {
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
	public void saveEditTournament(ActionEvent event) {
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
		String apt = txtArchersPerTarget.getText();
		String metric = Boolean.toString(chkMetric.isSelected());
		String teams = Boolean.toString(chkTeams.isSelected());
		String couples = Boolean.toString(chkMarriedCouples.isSelected());
		if(!Validation.validatePresence(title) || !Validation.validatePresence(apt) || !Validation.validatePresence(date)) {
			informationDialog("Missing Details", "You have not filled in all textfields.");
			return;
		} 
		if(!Validation.validateAsName(title) || !Validation.validateAsInteger(apt)) {
			informationDialog("Invalid Details", "You have entered invalid details into one or more of the textfields.");
			return;
		}
		int aptNumeric = Integer.parseInt(apt);
		tournaments.updateRecord(id, title, date, aptNumeric, metric, teams, couples);
		tabPane.setDisable(false);
		tpNewTournament.setDisable(false);
		loadTournament(event);
	}
	
	public void fillArcherTableView(int tournamentID) {
		tbvArchers.getItems().clear();
		cmbMarriedCouple.getItems().clear();
		cmbMarriedCouple.getItems().add("None");
		cmbBestGold.getItems().clear();
		cmbBestGold.getItems().add("None");
		cmbWorstWhite.getItems().clear();
		cmbWorstWhite.getItems().add("None");
		ResultSet rs = archers.getAllRecords(tournamentID);
		int archerCount = 0;
		try {
			btnSearchArcher.setDisable(rs.isBeforeFirst() ? false : true);
			while(rs.next()) {
	    		tbvArchers.getItems().add(new ArcherEntry(rs.getInt("ArcherID"), rs.getString("FirstName"), 
	    				rs.getString("LastName"), rs.getString("Club"), rs.getString("Category"), 
	    				rs.getString("BowType"), rs.getString("Round")));
	    		String comboBoxString = rs.getString("ArcherID") + " - "  + rs.getString("FirstName") + " "
	    				+ rs.getString("LastName");
	    		cmbMarriedCouple.getItems().add(comboBoxString);
	    		cmbBestGold.getItems().add(comboBoxString);
	    		cmbWorstWhite.getItems().add(comboBoxString);
	    		archerCount++;
	    	}
		} catch(SQLException e) {
			e.printStackTrace();
		}
    	txtTotalArchers.setText(Integer.toString(archerCount));
    	cmbMarriedCouple.setDisable(!chkMarriedCouples.isSelected());
    	btnNewArcher.setDisable(false);
	}
	
	public void fillArcherEditorComboBoxes() {
		ResultSet rs = archers.getCategories();
		try {
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
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void newArcher(ActionEvent event) {
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
	public void beginEditArcher(ActionEvent event) {
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
		try {
			if(rs.isBeforeFirst()) {
				if(rs.getInt(1) == archer.getID()) {
					cmbMarriedCouple.getSelectionModel().select(rs.getString(2) + " - " + rs.getString(3)
					+ " " + rs.getString(4));
				} else {
					cmbMarriedCouple.getSelectionModel().select(rs.getString(1) + " - " + rs.getString(3)
							+ " " + rs.getString(4));
					cmbMarriedCouple.setDisable(true);
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		btnSaveEditArcher.setVisible(true);
		btnNewArcher.setVisible(false);
		stpSaveArcher.getChildren().get(0).toFront();
	}
	
	@FXML
	public void saveEditArcher(ActionEvent event) {
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
			informationDialog("Archer Not Found", "Archer ID " + input.get() + "does not exist.");
		}
	}
	
	@FXML
	public void deleteArcher(ActionEvent event) {
		ArcherEntry selectedArcher = tbvArchers.getSelectionModel().getSelectedItem();
		archers.deleteRecord(selectedArcher.getID());
		scores.deletedRecord(selectedArcher.getID());
		marriedCouples.deleteRecord(selectedArcher.getID());
		int tID = cmbTournament.getSelectionModel().getSelectedItem().getID();
		fillArcherTableView(tID);
		fillTargetListTableView(tID);
		fillScoresTableView(tID);
		btnEditArcher.setDisable(true);
		btnDeleteArcher.setDisable(true);
	}

	public void fillTargetListTableView(int tournamentID) {
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
		HashSet<String> roundsSet = new HashSet<String>();
    	if(!data.isEmpty()) {
    		for(TargetEntry entry : data) {
    			tbvTargetList.getItems().add(entry);
    			roundsSet.add(entry.getRound());
    		}
    	}
    	cmbTeamRound.getItems().clear();
    	cmbTeamRound.getItems().addAll(roundsSet);
	}
	
	@FXML
	public void allocateTargetDetails(ActionEvent event) {
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
			informationDialog("Archer Not Found", "Archer ID " + input.get() + "does not exist.");
		}
	}
	
	@FXML
	public void beginEditTargetDetail(ActionEvent event) {
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
	public void saveEditTargetDetail(ActionEvent event) {
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
	
	public void fillSwapTargetComboBox() {
		int tID = cmbTournament.getSelectionModel().getSelectedItem().getID();
		TargetEntry selectedEntry = tbvTargetList.getSelectionModel().getSelectedItem();
		targets.fillSwapDetailsComboBox(tID, selectedEntry, cmbEditTarget);
	}
	
	@FXML 
	public void previewTargetList(ActionEvent event) {
		int tID = cmbTournament.getSelectionModel().getSelectedItem().getID();
		String title = txtTitle.getText();
		SimpleDateFormat dateFormat = new SimpleDateFormat("d/MM/yyyy");
		String date = null;
		try {
			Date dateUnformatted = dateFormat.parse(txtDate.getText());
			DateFormat formatter = DateFormat.getDateInstance(DateFormat.FULL);
			date = formatter.format(dateUnformatted);
		} catch(ParseException e) {
			e.printStackTrace();
		}
		String venue = txtVenue.getText();
		String assembly = txtAssembly.getText();
		String sighters = txtSighters.getText();
		targets.previewTargetList(tID, title, date, venue, assembly, sighters);
	}
	
	public void fillScoresTableView(int tournamentID) {
		tbvScores.getItems().clear();
		btnEditScore.setDisable(true);
		tbcXs.setVisible(chkMetric.isSelected() ? true : false);
		ArrayList<ScoreEntry> tableData = scores.getDataForTable(tournamentID);
		for(ScoreEntry entry : tableData) {
			tbvScores.getItems().add(entry);
		}
		btnSearchScore.setDisable(tableData.isEmpty() ? true : false);
		btnStartScores.setDisable(tableData.isEmpty() ? true : false);
		lblBestGold.setDisable(tableData.isEmpty() ? true : false);
		lblWorstWhite.setDisable(tableData.isEmpty() ? true : false);
		cmbBestGold.setDisable(tableData.isEmpty() ? true : false);
		cmbWorstWhite.setDisable(tableData.isEmpty() ? true : false);
		btnGenerateIndividualResults.setDisable(tableData.isEmpty() ? true : false);
		btnGenerateCoupleResults.setDisable(!chkMarriedCouples.isSelected() || tableData.isEmpty() ? true : false);
		cmbTeamRound.setDisable(!chkTeams.isSelected() || tableData.isEmpty() ? true : false);
		lblTeamRound.setDisable(!chkTeams.isSelected() || tableData.isEmpty() ? true : false);
		txtArchersPerTeam.setDisable(!chkTeams.isSelected() || tableData.isEmpty() ? true : false);
		lblArchersPerTeam.setDisable(!chkTeams.isSelected() || tableData.isEmpty() ? true : false);
		chkTeamCompound.setDisable(!chkTeams.isSelected() || tableData.isEmpty() ? true : false);
		lblTeamCompound.setDisable(!chkTeams.isSelected() || tableData.isEmpty() ? true : false);
		chkTeamRecurve.setDisable(!chkTeams.isSelected() || tableData.isEmpty() ? true : false);
		lblTeamRecurve.setDisable(!chkTeams.isSelected() || tableData.isEmpty() ? true : false);
		chkTeamBarebow.setDisable(!chkTeams.isSelected() || tableData.isEmpty() ? true : false);
		lblTeamBarebow.setDisable(!chkTeams.isSelected() || tableData.isEmpty() ? true : false);
		chkTeamLongbow.setDisable(!chkTeams.isSelected() || tableData.isEmpty() ? true : false);
		lblTeamLongbow.setDisable(!chkTeams.isSelected() || tableData.isEmpty() ? true : false);
		rdbGenderMixed.setDisable(!chkTeams.isSelected() || tableData.isEmpty() ? true : false);
		lblGenderMixed.setDisable(!chkTeams.isSelected() || tableData.isEmpty() ? true : false);
		rdbGenderSeparate.setDisable(!chkTeams.isSelected() || tableData.isEmpty() ? true : false);
		lblGenderSeparate.setDisable(!chkTeams.isSelected() || tableData.isEmpty() ? true : false);
		btnGenerateTeamResults.setDisable(!chkTeams.isSelected() || tableData.isEmpty() ? true : false);
		btnExportFullResults.setDisable(tableData.isEmpty() ? true : false);
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
	public void stopScoring(ActionEvent event) {
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
	public void nextScore(ActionEvent event) {
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
			informationDialog("Archer Not Found", "Archer ID " + input.get() + "does not exist.");
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
	public void saveEditArcherScore(ActionEvent event) {
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
	
	@FXML
	public void previewIndividualResults(ActionEvent event) {
		int tID = cmbTournament.getSelectionModel().getSelectedItem().getID();
		String title = txtTitle.getText();
		String date = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("d/MM/yyyy");
			Date dateUnformatted = dateFormat.parse(txtDate.getText());
			DateFormat formatter = DateFormat.getDateInstance(DateFormat.FULL);
			date = formatter.format(dateUnformatted);
		} catch(ParseException e) {
			e.printStackTrace();
		}
		String bestGold = "", worstWhite = "";
		if(!cmbBestGold.getSelectionModel().isEmpty() && !cmbBestGold.getSelectionModel().getSelectedItem().equals("None")) {
			bestGold = cmbBestGold.getSelectionModel().getSelectedItem().split("-")[1];
		}
		if(!cmbWorstWhite.getSelectionModel().isEmpty() && !cmbWorstWhite.getSelectionModel().getSelectedItem().equals("None")) {
			worstWhite = cmbWorstWhite.getSelectionModel().getSelectedItem().split("-")[1];
		}
		boolean metric = chkMetric.isSelected();
		JasperPrint jPrint = results.generateIndividualResults(tID, title, date, metric, bestGold, worstWhite);
		if(jPrint.getPages().isEmpty()) { 
			informationDialog("No Results", "No results were yielded for Individual Results."); 
		} else {
			JasperViewer jViewer = new JasperViewer(jPrint, false);
			jViewer.setTitle("Full Results Preview");
			ImageIcon img = new ImageIcon(getClass().getResource("resources/list.png"));
			jViewer.setIconImage(img.getImage());
			jViewer.setVisible(true);
		}
	}
	
	@FXML
	public void previewMarriedCoupleResults(ActionEvent event) {
		int tID = cmbTournament.getSelectionModel().getSelectedItem().getID();
		boolean metric = chkMetric.isSelected();
		JasperPrint jPrint = results.generateMarriedCoupleResults(tID, metric);
		if(jPrint.getPages().isEmpty()) { 
			informationDialog("No Results", "No results were yielded for Married Couples."); 
		} else {
			JasperViewer jViewer = new JasperViewer(jPrint, false);
			jViewer.setTitle("Married Couple Results Preview");
			ImageIcon img = new ImageIcon(getClass().getResource("resources/list.png"));
			jViewer.setIconImage(img.getImage());
			jViewer.setVisible(true);
		}
	}
	
	@FXML
	public void previewTeamResults(ActionEvent event) {
		int tID = cmbTournament.getSelectionModel().getSelectedItem().getID();
		boolean metric = chkMetric.isSelected();
		String round = cmbTeamRound.getSelectionModel().getSelectedItem();
		int apt = Integer.parseInt(txtArchersPerTeam.getText());
		ArrayList<String> bowTypes = new ArrayList<String>();
		if(chkTeamCompound.isSelected()) { bowTypes.add("Compound"); }
		if(chkTeamRecurve.isSelected()) { bowTypes.add("Recurve"); }
		if(chkTeamBarebow.isSelected()) { bowTypes.add("Barebow"); }
		if(chkTeamLongbow.isSelected()) { bowTypes.add("Longbow"); }
		if(bowTypes.isEmpty()) {
			informationDialog("No Results", "Your team properties selection yielded no results."); 
		} else {
			boolean mixed = rdbGenderMixed.isSelected();
			JasperPrint jPrint = results.generateTeamResults(tID, round, apt, bowTypes, mixed, metric);
			if(jPrint.getPages().isEmpty()) { 
				informationDialog("No Results", "Your team properties selection yielded no results."); 
			} else {
				JasperViewer jViewer = new JasperViewer(jPrint, false);
				jViewer.setTitle("Team Results Preview");
				ImageIcon img = new ImageIcon(getClass().getResource("resources/list.png"));
				jViewer.setIconImage(img.getImage());
				jViewer.setVisible(true);
			}
		}
	}
	
	@FXML
	public void previewFullResults(ActionEvent event) {
		int tID = cmbTournament.getSelectionModel().getSelectedItem().getID();
		String title = txtTitle.getText();
		String date = null;
		try {
		SimpleDateFormat dateFormat = new SimpleDateFormat("d/MM/yyyy");
		Date dateUnformatted = dateFormat.parse(txtDate.getText());
		DateFormat formatter = DateFormat.getDateInstance(DateFormat.FULL);
		date = formatter.format(dateUnformatted);
		} catch(ParseException e) {
			e.printStackTrace();
		}
		String bestGold = "", worstWhite = "";
		if(!cmbBestGold.getSelectionModel().isEmpty() && !cmbBestGold.getSelectionModel().getSelectedItem().equals("None")) {
			bestGold = cmbBestGold.getSelectionModel().getSelectedItem().split("-")[1];
		}
		if(!cmbWorstWhite.getSelectionModel().isEmpty() && !cmbWorstWhite.getSelectionModel().getSelectedItem().equals("None")) {
			worstWhite = cmbWorstWhite.getSelectionModel().getSelectedItem().split("-")[1];
		}
		boolean metric = chkMetric.isSelected();
		String round = cmbTeamRound.getSelectionModel().getSelectedItem();
		int apt = 0;
		if(txtArchersPerTeam.getText().length() != 0) {
			apt = Integer.parseInt(txtArchersPerTeam.getText());
		}
		ArrayList<String> bowTypes = new ArrayList<String>();
		if(chkTeamCompound.isSelected()) { bowTypes.add("Compound"); }
		if(chkTeamRecurve.isSelected()) { bowTypes.add("Recurve"); }
		if(chkTeamBarebow.isSelected()) { bowTypes.add("Barebow"); }
		if(chkTeamLongbow.isSelected()) { bowTypes.add("Longbow"); }
		boolean mixed = rdbGenderMixed.isSelected();
		JasperPrint individual = results.generateIndividualResults(tID, title, date, metric, bestGold, worstWhite);
		JasperPrint couples = null;
		JasperPrint teams = null;
		if(chkMarriedCouples.isSelected()) {
			couples = results.generateMarriedCoupleResults(tID, metric);
		}
		if(chkTeams.isSelected() && !bowTypes.isEmpty()) {
			teams = results.generateTeamResults(tID, round, apt, bowTypes, mixed, metric);
		}
		JasperPrint fullResults = individual;
		if(!individual.getPages().isEmpty()) {
			if(teams != null && !teams.getPages().isEmpty()) {
				for(JRPrintPage page : teams.getPages()) {
					fullResults.getPages().add(page);
				}
			}
			if(couples != null && !couples.getPages().isEmpty()) {
				for(JRPrintPage page : couples.getPages()) {
					fullResults.getPages().add(page);
				}
			}
			JasperViewer jViewer = new JasperViewer(fullResults, false);
			jViewer.setTitle("Full Results Preview");
			ImageIcon img = new ImageIcon(getClass().getResource("resources/list.png"));
			jViewer.setIconImage(img.getImage());
			jViewer.setVisible(true);
		} else {
			informationDialog("No Results", "Full results could be created as no results for Individuals were yielded.");
		}
	}
}