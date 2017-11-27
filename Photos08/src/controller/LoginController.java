package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

/**
 * Controller for the Login page. Handles deserialization of User objects.
 * 
 * @author Jason Holley
 *
 */
public class LoginController {
	
	public static User currentUser;
	Stage stage = new Stage();
	
	@FXML private Button Submit;
	@FXML private Label errorMessage;
	@FXML private TextField usernameField;
	@FXML private PasswordField passwordField;
	
 
	/**
	 * Handles logging in.
	 * 
	 * @param event						Submit button is clicked.
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@FXML public void handleSubmitButtonAction (ActionEvent event) throws ClassNotFoundException, IOException{
		
		errorMessage.setOpacity(0);
		String username = usernameField.getText();
		String password = passwordField.getText();
		
		if (username.equals("admin") && password.equals("admin")) {
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
		else {
			File temp = new File("users/" + username + ".txt");
			if(temp.exists() && !temp.isDirectory()) {
				FileInputStream file = new FileInputStream("users/" + username + ".txt");
				ObjectInputStream object = new ObjectInputStream(file);
				
				currentUser = (User) object.readObject();
				
				file.close();
				object.close();
				
				if(password.equals(currentUser.getPassword())) {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/view/UserPage.fxml"));
					Parent root = loader.load();
					Scene scene = new Scene(root, 800, 400);
					
					((Node) event.getSource()).getScene().getWindow().hide();
					stage.setScene(scene);
					stage.setTitle("Photos08");
					stage.show();
				}
				else {
					errorMessage.setOpacity(1);
				}
			}
			else {
				errorMessage.setOpacity(1);
			}
		}
	}
	
	/**
	 * Setter for LoginController.stage
	 * 
	 * @param stage
	 */
	public void setStage (Stage stage) {
		this.stage = stage;
	}
	
}
