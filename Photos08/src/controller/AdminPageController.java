package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.User;

public class AdminPageController implements Initializable {

	Stage stage = new Stage();
	
	@FXML private Button deleteUserButton;
	@FXML private Button createUserButton;
	@FXML private Button logoutButton;
	@FXML private ListView<String> listView;
	@FXML private TextField usernameField;
	@FXML private TextField passwordField;
	@FXML private Label createEmptyFieldsMessage;
	@FXML private Label createUserExistsMessage;
	@FXML private Label deleteErrorMessage;
	
	@FXML public void handleDeleteButton (ActionEvent event) throws IOException {
		deleteErrorMessage.setOpacity(0);
		createUserExistsMessage.setOpacity(0);
		createEmptyFieldsMessage.setOpacity(0);
		
		if(listView.getSelectionModel().isEmpty()) {
			deleteErrorMessage.setOpacity(1);
		}
		
		else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Delete User");
			alert.setHeaderText("Are you sure?");
			alert.setContentText("Are you sure you want to delete this user and all of his or her albums? This cannot be undone.");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				Files.deleteIfExists(Paths.get("users/" + listView.getSelectionModel().getSelectedItem() + ".txt"));
				
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/AdminPage.fxml"));
				Parent root = loader.load();
				Scene scene = new Scene(root, 600, 400);
				
				((Node) event.getSource()).getScene().getWindow().hide();
				stage.setScene(scene);
				stage.setResizable(false);
				stage.setTitle("Photos08");
				stage.show();
			}
		}
	}
	
	@FXML public void handleCreateButton (ActionEvent event) throws Exception {
		createEmptyFieldsMessage.setOpacity(0);
		createUserExistsMessage.setOpacity(0);
		deleteErrorMessage.setOpacity(0);
		
		if((usernameField.getText().trim().isEmpty()) || passwordField.getText().trim().isEmpty()) {
			createEmptyFieldsMessage.setOpacity(1);
		}
		
		else {
			String username = usernameField.getText();
			String password = passwordField.getText();
			
			File temp = new File("users/" + username + ".txt");
			if(temp.exists() && !temp.isDirectory()) {
				createUserExistsMessage.setOpacity(1);
			}
			
/*			else if(userList.findUser(username)) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Password Change?");
				alert.setHeaderText("User exists.");
				alert.setContentText("This user already exists. Would you like to change their password to " + password + "?");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK){
					User temp = new User(username, password);
					userList.deleteUser(temp);
					userList.addUser(temp);
					
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/view/AdminPage.fxml"));
					Parent root = loader.load();
					Scene scene = new Scene(root, 600, 400);
					
					((Node) event.getSource()).getScene().getWindow().hide();
					stage.setScene(scene);
					stage.setResizable(false);
					stage.setTitle("Photos08");
					stage.show();
				}
			} */		//password change functionality?
			
			else {
				User toAdd = new User(username, password);
				toAdd.write();
				
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/AdminPage.fxml"));
				Parent root = loader.load();
				Scene scene = new Scene(root, 600, 400);
				
				((Node) event.getSource()).getScene().getWindow().hide();
				stage.setScene(scene);
				stage.setResizable(false);
				stage.setTitle("Photos08");
				stage.show();
			}
		}
	}
	
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


	@Override
	public void initialize(URL url, ResourceBundle rb) {
		ArrayList<String> names = new ArrayList<String>();
		File usersFolder = new File("users/");
		File[] allFiles = usersFolder.listFiles();
		for (File file : allFiles) {
			if(file.isFile()) {
				names.add(file.getName().substring(0, file.getName().lastIndexOf('.')));
			}
		}
		if(!names.isEmpty()) {
			listView.setItems(FXCollections.observableArrayList(names));	
		}
	}
}
