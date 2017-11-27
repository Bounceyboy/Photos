package controller;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

/**
 * Controller for the main page that shows up after a regular user logs in.
 * 
 * @author Jason Holley
 *
 */
public class UserPageController implements Initializable {

	public static Stage stage = new Stage();
	
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
	
	/**
	 * Takes the user back to the login page.
	 * 
	 * @param event				Logout button is pressed.
	 * @throws IOException
	 */
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
	
	/**
	 * Handles searching based on tags.
	 * 
	 * @param event				Tag search button is clicked.
	 * @throws Exception
	 */
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
				searched = searched.substring(currentKey.length()+1).trim();
				if(searched.contains(",")) {
					currentValue = searched.substring(0, searched.indexOf(','));
					searched = searched.substring(currentValue.length()+1).trim();
				}
				else {
					currentValue = searched.trim();
					searched = "";
				}
				
				ArrayList<String> temp;
				
				currentKey = currentKey.trim();
				currentValue = currentValue.trim();
				
				for(int x = 0; x < LoginController.currentUser.getAlbums().size(); x++) {
					for (int y = 0; y < LoginController.currentUser.getAlbums().get(x).images.size(); y++) {
						currentTags = LoginController.currentUser.getAlbums().get(x).images.get(y).gettags();
						temp = currentTags.get(currentKey);
						if(currentTags.containsKey(currentKey) && temp.contains(currentValue)){
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
					
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/view/SearchResults.fxml"));
					Parent root = loader.load();
					Scene scene = new Scene(root, 600, 400);
					
					((Node) event.getSource()).getScene().getWindow().hide();
					stage.setScene(scene);
					stage.setTitle(tagField.getText());
					stage.showAndWait();
				}
			}
		}
	}
	
	/**
	 * Handles searching based on tags.
	 * 
	 * @param event			Date search button is clicked.
	 * @throws Exception
	 */
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
			if(!dateField.getText().contains("-")) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("The date search was formatted incorrectly. Required format is:\nMM/DD/YY-MM/DD/YY or YYYY-YYYY.");
				alert.showAndWait();
				return;
			}
			String searched, startDateString, endDateString;
			searched = dateField.getText().trim();
			ArrayList<image> found = new ArrayList<image>();
			
				startDateString = searched.substring(0, searched.indexOf('-'));
				searched = searched.substring(startDateString.length()+1).trim();
				endDateString = searched.trim();
				startDateString = startDateString.trim();
				image tempImage;
				
				Date startDate,endDate;
				
				if (startDateString.length()==4) {
					if (endDateString.length()==4) {
						DateFormat df = new SimpleDateFormat("YYYY");
						startDate = df.parse(startDateString);
						endDate = df.parse(endDateString);
					}
					else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("The date search was formatted incorrectly. Required format is:\nMM/DD/YYYY-MM/DD/YYYY or YYYY-YYYY.");
						alert.showAndWait();
						return;
					}
				}
				else if (startDateString.contains("/")) {
					if (startDateString.length()==10 && startDateString.charAt(2)=='/' && startDateString.charAt(5)=='/') {
						if (endDateString.length()==10 && endDateString.charAt(2)=='/' && endDateString.charAt(5)=='/') {
							DateFormat df = new SimpleDateFormat("MM/DD/YYYY");
							startDate = df.parse(startDateString);
							endDate = df.parse(endDateString);
						}
						else {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setContentText("The date search was formatted incorrectly. Required format is:\nMM/DD/YY-MM/DD/YY or YYYY-YYYY.");
							alert.showAndWait();
							return;
						}
					}
					else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("The date search was formatted incorrectly. Required format is:\nMM/DD/YY-MM/DD/YY or YYYY-YYYY.");
						alert.showAndWait();
						return;
					}
				}
				else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("The date search was formatted incorrectly. Required format is:\nMM/DD/YY-MM/DD/YY or YYYY-YYYY.");
					alert.showAndWait();
					return;
				}
				
				if(startDate.after(endDate)) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("The start date can't come after end date!");
					alert.showAndWait();
					return;
				}
				for(int x = 0; x < LoginController.currentUser.getAlbums().size(); x++) {
					for (int y = 0; y < LoginController.currentUser.getAlbums().get(x).images.size(); y++) {
							tempImage = LoginController.currentUser.getAlbums().get(x).images.get(y);
							if(tempImage.getdate().compareTo(startDate)>=0 && tempImage.getdate().compareTo(endDate) <= 0) {
								found.add(tempImage);
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
					
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/view/SearchResults.fxml"));
					Parent root = loader.load();
					Scene scene = new Scene(root, 600, 400);
					
					((Node) event.getSource()).getScene().getWindow().hide();
					stage.setScene(scene);
					stage.setTitle(dateField.getText());
					stage.showAndWait();
				}
		}
	}
	
	/**
	 * Handles the opening of a selected album.
	 * 
	 * @param event				Open album button is clicked.
	 * @throws IOException
	 */
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
			Scene scene = new Scene(root, 1224, 591);
			
			((Node) event.getSource()).getScene().getWindow().hide();
			stage.setScene(scene);
			stage.setTitle(selected.getName());
			stage.showAndWait();
		}
	}
	
	/**
	 * Handles renaming of albums.
	 * 
	 * @param event			Rename album button is clicked.
	 * @throws IOException
	 */
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
	
	/**
	 * Handles deletion of albums.
	 * 
	 * @param event			Delete album button is clicked.
	 * @throws IOException
	 */
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
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Delete User");
			alert.setHeaderText("Are you sure?");
			alert.setContentText("Are you sure you want to delete this album? This cannot be undone.");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
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
	}
	
	/**
	 * Handles creation of albums.
	 * 
	 * @param event				Create album button is clicked.
	 * @throws IOException
	 */
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
