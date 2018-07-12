package tss;


import java.io.IOException;
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

import javax.swing.UnsupportedLookAndFeelException;

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
    private Button btnExportTargetList;
    @FXML 
    private Button btnPreviewTargetList;
    @FXML
    private StackPane stpEditSaveTournament;
    @FXML
    private StackPane stpTournamentDate;
    @FXML 
    private StackPane stpSaveArcher;
    @FXML
    private StackPane stpEditSaveTarget;
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
    private ComboBox<String> cmbEditTarget;
    @FXML
    private VBox vboxTournament;
    @FXML
    private TableView<ArcherEntry> tbvArchers;
    @FXML
    private TableView<TargetEntry> tbvTargetList;
    
    private static Connection conn;
    private static Tournaments tournaments;
    private static Archers archers;
    private static MarriedCouples marriedCouples;
    private static Targets targets;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    
    @FXML
    public void initialize() throws SQLException {
		conn = SQLiteConnection.getConnection();
		tournaments = new Tournaments(conn);
		archers = new Archers(conn);
		marriedCouples = new MarriedCouples(conn);
		targets = new Targets(conn);
		tbvArchers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("ID"));
		tbvArchers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("firstName"));
		tbvArchers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("lastName"));
		tbvArchers.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("club"));
		tbvArchers.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("category"));
		tbvArchers.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("bowType"));
		tbvArchers.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("round"));
		tbvTargetList.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("ID"));
		tbvTargetList.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("archer"));
		tbvTargetList.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("club"));
		tbvTargetList.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("round"));
		tbvTargetList.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("bowType"));
		tbvTargetList.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("target"));
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
			btnSearchTarget.setDisable(true);
			btnEditTarget.setDisable(true);
			btnAllocateTargets.setDisable(true);
			btnPreviewTargetList.setDisable(true);
			btnExportTargetList.setDisable(true);
			fillArcherTableView(id);
			fillTargetListTableView(id);
		} else {
			tbvArchers.getItems().clear();
			tbvTargetList.getItems().clear();
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
		btnSearchArcher.setDisable(true);
    	btnEditArcher.setDisable(true);
    	btnDeleteArcher.setDisable(true);
    	btnNewArcher.setDisable(false);
    	cmbMarriedCouple.setDisable(!chkMarriedCouples.isSelected());
		btnSearchTarget.setDisable(true);
		btnEditTarget.setDisable(true);
		btnAllocateTargets.setDisable(true);
		btnPreviewTargetList.setDisable(true);
		btnExportTargetList.setDisable(true);
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
		btnSearchTarget.setDisable(true);
		btnEditTarget.setDisable(true);
		btnAllocateTargets.setDisable(true);
		btnPreviewTargetList.setDisable(true);
		btnExportTargetList.setDisable(true);
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
		txtFirstName.clear();
		txtLastName.clear();
		txtClub.clear();
		cmbCategory.getSelectionModel().clearSelection();
		cmbBowType.getSelectionModel().clearSelection();
		cmbRound.getSelectionModel().clearSelection();
		cmbMarriedCouple.getSelectionModel().clearSelection();
		fillArcherTableView(tID);
		fillTargetListTableView(tID);
		tbvArchers.getSelectionModel().selectLast();
		tbvArchers.scrollTo(tbvArchers.getSelectionModel().getSelectedItem());
	}
	
	@FXML
	public void beginEditArcher(ActionEvent event) throws SQLException {
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
		fillArcherTableView(cmbTournament.getSelectionModel().getSelectedItem().getID());
		fillTargetListTableView(cmbTournament.getSelectionModel().getSelectedItem().getID());
		tbvArchers.getSelectionModel().select(index);
		txtFirstName.clear();
		txtLastName.clear();
		txtClub.clear();
		cmbCategory.getSelectionModel().clearSelection();
		cmbBowType.getSelectionModel().clearSelection();
		cmbRound.getSelectionModel().clearSelection();
		cmbMarriedCouple.getSelectionModel().clearSelection();
	}
	
	@FXML
	public void searchArcher(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Search Archers");
		dialog.setHeaderText(null);
		dialog.setContentText("Archer ID: ");
		DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("resources/DashboardStylesheet.css").toExternalForm());
		Stage dialogStage = (Stage)dialog.getDialogPane().getScene().getWindow();
		dialogStage.getIcons().add(new Image(getClass().getResourceAsStream("resources/logo.png")));
		Optional<String> input = dialog.showAndWait();
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
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Archer not found");
			alert.setHeaderText(null);
			alert.setContentText("Archer ID " + input.get() + " does not exist.");
			dialogPane = alert.getDialogPane();
			dialogPane.getStylesheets().add(getClass().getResource("resources/DashboardStylesheet.css").toExternalForm());
			Stage alertStage = (Stage)alert.getDialogPane().getScene().getWindow();
			alertStage.getIcons().add(new Image(getClass().getResourceAsStream("resources/logo.png")));
			alert.showAndWait();
		}
	}
	
	@FXML
	public void deleteArcher(ActionEvent event) throws SQLException {
		ArcherEntry selectedArcher = tbvArchers.getSelectionModel().getSelectedItem();
		archers.deleteRecord(selectedArcher.getID());
		fillArcherTableView(cmbTournament.getSelectionModel().getSelectedItem().getID());
		fillTargetListTableView(cmbTournament.getSelectionModel().getSelectedItem().getID());
		String newTotalArchers = Integer.toString(Integer.parseInt(txtTotalArchers.getText()) - 1);
		txtTotalArchers.setText(newTotalArchers);
		btnEditArcher.setDisable(true);
		btnDeleteArcher.setDisable(true);
	}

	public void fillTargetListTableView(int tournamentID) throws SQLException {
		tbvTargetList.getItems().clear();
		ArrayList<TargetEntry> data = targets.getDataForTable(tournamentID);
		btnSearchTarget.setDisable(data.isEmpty() ? true : false);
		btnAllocateTargets.setDisable(data.isEmpty() ? true : false);
		btnPreviewTargetList.setDisable(data.isEmpty() ? true : false);
		btnExportTargetList.setDisable(data.isEmpty() ? true : false);
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
	}
	
	@FXML
	public void searchArcherTarget(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Search Archer's Target");
		dialog.setHeaderText(null);
		dialog.setContentText("Archer ID: ");
		DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("resources/DashboardStylesheet.css").toExternalForm());
		Stage dialogStage = (Stage)dialog.getDialogPane().getScene().getWindow();
		dialogStage.getIcons().add(new Image(getClass().getResourceAsStream("resources/logo.png")));
		Optional<String> input = dialog.showAndWait();
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
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Archer not found");
			alert.setHeaderText(null);
			alert.setContentText("Archer ID " + input.get() + " does not exist.");
			dialogPane = alert.getDialogPane();
			dialogPane.getStylesheets().add(getClass().getResource("resources/DashboardStylesheet.css").toExternalForm());
			Stage alertStage = (Stage)alert.getDialogPane().getScene().getWindow();
			alertStage.getIcons().add(new Image(getClass().getResourceAsStream("resources/logo.png")));
			alert.showAndWait();
		}
	}
	
	@FXML
	public void beginEditTargetDetail(ActionEvent event) throws SQLException {
		btnSaveEditTarget.setVisible(true);
		btnEditTarget.setVisible(false);
		tbvTargetList.setDisable(true);
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
		btnEditTarget.setDisable(true);
		cmbEditTarget.setDisable(true);
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
		targets.previewTargetList(tID, title, date);
	}
	
	@FXML
	public void exportTargetList(ActionEvent event) throws SQLException, JRException, ParseException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, IOException {
		int tID = cmbTournament.getSelectionModel().getSelectedItem().getID();
		String title = txtTitle.getText();
		SimpleDateFormat dateFormat = new SimpleDateFormat("d/MM/yyyy");
		Date dateUnformatted = dateFormat.parse(txtDate.getText());
		DateFormat formatter = DateFormat.getDateInstance(DateFormat.FULL);
		String date = formatter.format(dateUnformatted);
		targets.exportTargetListPDF(tID, title, date);
	}
}
