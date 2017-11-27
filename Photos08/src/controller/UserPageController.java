package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import controller.LoginController;
import model.Album;
import model.image;

public class UserPageController implements Initializable {

	Stage stage = new Stage();
	
	public static Album selected;
	
	@FXML private ListView<String> listView;
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
	
	@FXML public void handleTagSearchButton (ActionEvent event) throws Exception {
		deleteAlbumError.setOpacity(0);
		dateSearchError1.setOpacity(0);
		dateSearchError2.setOpacity(0);
		tagSearchError.setOpacity(0);
		renameAlbumError.setOpacity(0);
		openAlbumError.setOpacity(0);
		createAlbumError1.setOpacity(0);
		createAlbumError2.setOpacity(0);
		
		if(tagField.getText().trim().isEmpty()){
			tagSearchError.setOpacity(1);
		}
		else{
			String searched, currentKey, currentValue;
			searched = tagField.getText().trim();
			ArrayList<image> found = new ArrayList<image>();
			HashMap<String, ArrayList<String>> currentTags;
			
			while(searched.contains("=")){
				currentKey = searched.substring(0, searched.indexOf('='));
				searched = searched.substring(currentKey.length()).trim();
				currentValue = searched.substring(0, searched.indexOf(','));
				searched = searched.substring(currentValue.length()).trim();
				
				currentKey = currentKey.trim();
				currentValue = currentValue.trim();
				
				for(int x = 0; x < LoginController.currentUser.getAlbums().size(); x++) {
					for (int y = 0; y < LoginController.currentUser.getAlbums().get(x).images.size(); y++) {
						currentTags = LoginController.currentUser.getAlbums().get(x).images.get(y).gettags();
						if(currentTags.containsKey(currentKey) && currentTags.containsValue(currentValue)){
							found.add(LoginController.currentUser.getAlbums().get(x).images.get(y));
						}
					}
				}
				if (found.isEmpty()) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("No images with those tags found.");
					alert.showAndWait();
				}
				else {
					Album searchResults = new Album(found);
					UserPageController.selected = searchResults;
					
					selected = LoginController.currentUser.getAlbums().get(listView.getSelectionModel().getSelectedIndex());
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/view/SearchResults.fxml"));
					Parent root = loader.load();
					Scene scene = new Scene(root, 400, 600);
					
					((Node) event.getSource()).getScene().getWindow().hide();
					stage.setScene(scene);
					stage.setTitle(selected.getName());
					stage.showAndWait();
				}
			}
		}
	}
	
	@FXML public void handleDateSearchButton (ActionEvent event) throws Exception {
		deleteAlbumError.setOpacity(0);
		dateSearchError1.setOpacity(0);
		dateSearchError2.setOpacity(0);
		tagSearchError.setOpacity(0);
		renameAlbumError.setOpacity(0);
		openAlbumError.setOpacity(0);
		createAlbumError1.setOpacity(0);
		createAlbumError2.setOpacity(0);
		
		if(dateField.getText().trim().isEmpty()){
			dateSearchError1.setOpacity(1);
			dateSearchError2.setOpacity(1);
		}
		else{
			//TODO change to date search
			String searched, startDateString, endDateString;
			searched = tagField.getText().trim();
			ArrayList<image> found = new ArrayList<image>();
			
			while(searched.contains("=")){
				startDateString = searched.substring(0, searched.indexOf('-'));
				searched = searched.substring(startDateString.length()).trim();
				endDateString = searched.trim();
				startDateString = startDateString.trim();
				
				//TODO convert string to date
				
				for(int x = 0; x < LoginController.currentUser.getAlbums().size(); x++) {
					for (int y = 0; y < LoginController.currentUser.getAlbums().get(x).images.size(); y++) {
							//check for date, if good found.add
						}
					}
				}
				if (found.isEmpty()) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("No images between those dates found.");
					alert.showAndWait();
				}
				else {
					Album searchResults = new Album(found);
					UserPageController.selected = searchResults;
					
					selected = LoginController.currentUser.getAlbums().get(listView.getSelectionModel().getSelectedIndex());
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/view/SearchResults.fxml"));
					Parent root = loader.load();
					Scene scene = new Scene(root, 400, 600);
					
					((Node) event.getSource()).getScene().getWindow().hide();
					stage.setScene(scene);
					stage.setTitle(selected.getName());
					stage.showAndWait();
				}
			}
		}
	
	@FXML public void handleOpenAlbumButton (ActionEvent event) throws IOException {
		
		deleteAlbumError.setOpacity(0);
		dateSearchError1.setOpacity(0);
		dateSearchError2.setOpacity(0);
		tagSearchError.setOpacity(0);
		renameAlbumError.setOpacity(0);
		openAlbumError.setOpacity(0);
		createAlbumError1.setOpacity(0);
		createAlbumError2.setOpacity(0);
		
		if(listView.getSelectionModel().isEmpty()) {
			openAlbumError.setOpacity(1);
		}
		else {
			selected = LoginController.currentUser.getAlbums().get(listView.getSelectionModel().getSelectedIndex());
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/Album.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root, 591, 1224);
			
			((Node) event.getSource()).getScene().getWindow().hide();
			stage.setScene(scene);
			stage.setTitle(selected.getName());
			stage.showAndWait();
		}
	}
	
	@FXML public void handleRenameAlbumButton (ActionEvent event) throws IOException {
		
		deleteAlbumError.setOpacity(0);
		dateSearchError1.setOpacity(0);
		dateSearchError2.setOpacity(0);
		tagSearchError.setOpacity(0);
		renameAlbumError.setOpacity(0);
		openAlbumError.setOpacity(0);
		createAlbumError1.setOpacity(0);
		createAlbumError2.setOpacity(0);
		
		if(listView.getSelectionModel().isEmpty()) {
			renameAlbumError.setOpacity(1);
		}
		else {
			selected = LoginController.currentUser.getAlbums().get(listView.getSelectionModel().getSelectedIndex());
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/RenamePopup.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root, 300, 150);
			
			((Node) event.getSource()).getScene().getWindow().hide();
			stage.setScene(scene);
			stage.setTitle("Rename");
			stage.show();
		}
	}
	
	@FXML public void handleDeleteAlbumButton (ActionEvent event) throws IOException {
		
		deleteAlbumError.setOpacity(0);
		dateSearchError1.setOpacity(0);
		dateSearchError2.setOpacity(0);
		tagSearchError.setOpacity(0);
		renameAlbumError.setOpacity(0);
		openAlbumError.setOpacity(0);
		createAlbumError1.setOpacity(0);
		createAlbumError2.setOpacity(0);
		
		if(listView.getSelectionModel().isEmpty()) {
			deleteAlbumError.setOpacity(1);
		}
		else {
			LoginController.currentUser.deleteAlbum(LoginController.currentUser.getAlbums().get(listView.getSelectionModel().getSelectedIndex()));
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/UserPage.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root, 800, 400);
			
			((Node) event.getSource()).getScene().getWindow().hide();
			stage.setScene(scene);
			stage.setTitle("Photos08");
			stage.show();
		}
	}
	
	@FXML public void handleCreateAlbumButton (ActionEvent event) throws IOException {
		
		deleteAlbumError.setOpacity(0);
		dateSearchError1.setOpacity(0);
		dateSearchError2.setOpacity(0);
		tagSearchError.setOpacity(0);
		renameAlbumError.setOpacity(0);
		openAlbumError.setOpacity(0);
		createAlbumError1.setOpacity(0);
		createAlbumError2.setOpacity(0);
		
		if(albumNameField.getText().trim().isEmpty()) {
			createAlbumError1.setOpacity(1);
			createAlbumError2.setOpacity(1);
		}
		else{
			for(int x = 0; x<LoginController.currentUser.getAlbums().size(); x++){
				if (albumNameField.getText().trim().equalsIgnoreCase(LoginController.currentUser.getAlbums().get(x).getName())) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Album could not be created. An album with that name already exists.");
					alert.showAndWait();
					return;
				}			
			}
			
			Album album = new Album(albumNameField.getText());
			LoginController.currentUser.addAlbum(album);
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/UserPage.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root, 800, 400);
			
			((Node) event.getSource()).getScene().getWindow().hide();
			stage.setScene(scene);
			stage.setTitle("Photos08");
			stage.show();
		}
	}
	
	public void initialize(URL url, ResourceBundle rb) {
		ArrayList<Album> albums = LoginController.currentUser.getAlbums();
		ArrayList<String> names = new ArrayList<String>();
		for (int x = 0; x<albums.size(); x++){
			names.add(albums.get(x).getName());
		}
		listView.setItems(FXCollections.observableArrayList(names));
	}
}
