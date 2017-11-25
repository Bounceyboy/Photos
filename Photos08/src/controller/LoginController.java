package controller;

import java.io.IOException;

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
import model.UserList;

public class LoginController {
	
	Stage stage = new Stage();

	@FXML private Button Submit;
	@FXML private Label errorMessage;
	@FXML private TextField usernameField;
	@FXML private PasswordField passwordField;
	
 
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
			UserList userList = new UserList("users/loginInfo");
			if(userList.findUser(username, password)) {
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
	}
	
	public void setStage (Stage stage) {
		this.stage = stage;
	}
}
