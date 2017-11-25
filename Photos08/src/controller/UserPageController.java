package controller;

import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class UserPageController {

	Stage stage = new Stage();
	
	@FXML private ListView<String> listView;			//change to album
	@FXML private Button deleteAlbumButton;
	@FXML private Button openAlbumButton;
	@FXML private Button renameAlbumButton;
	@FXML private Button createAlbumButton;
	@FXML private Button dateSearchButton;
	@FXML private Button tagSearchButton;
	@FXML private Button logoutButton;
	@FXML private TextField albumNameField;
	@FXML private TextField dateField;
	@FXML private TextField tagField;
	@FXML private Label deleteAlbumError;
	@FXML private Label dateSearchError1;
	@FXML private Label dateSearchError2;
	@FXML private Label tagSearchError;
	@FXML private Label renameAlbumError;
	@FXML private Label openAlbumError;
	@FXML private Label createAlbumError1;
	@FXML private Label createAlbumError2;
	
	@FXML public void handleLogoutButton (ActionEvent event) throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setContentText("Are you sure you want to log out?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/Login.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root, 300, 300);
			
			((Node) event.getSource()).getScene().getWindow().hide();
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Photos08");
			stage.show();
		}
	}
	
	@FXML public void handleTagSearchButton (ActionEvent event) {
		
	}
	
	@FXML public void handleDateSearchButton (ActionEvent event) {
		
	}
	
	@FXML public void handleOpenAlbumButton (ActionEvent event) {
		
	}
	
	@FXML public void handleRenameAlbumButton (ActionEvent event) {
		
	}
	
	@FXML public void handleDeleteAlbumButton (ActionEvent event) {
		
	}
	
	@FXML public void handleCreateAlbumButton (ActionEvent event) {
		
	}
	
}
