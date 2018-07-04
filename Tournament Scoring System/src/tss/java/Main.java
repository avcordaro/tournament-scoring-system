package tss.java;
	
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {

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
		Connection conn = SQLiteConnection.getConnection();
		Statement stmt = conn.createStatement();
		String query = "SELECT * FROM Archer;";
		ResultSet rs = stmt.executeQuery(query);
		System.out.println(rs.getString("FirstName") + " " + rs.getString("LastName"));
	}
}
