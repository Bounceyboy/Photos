package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

/**
 * Popup for changing an album name.
 * 
 * @author Jason Holley
 */
public class RenamePopupController {
	
	Stage stage = new Stage();

	@FXML private Button submitButton;
	@FXML private Button cancelButton;
	@FXML private TextField renameTextField;
	@FXML private Label renameErrorText;
	
	/**
	 * Changes album name if applicable.
	 * 
	 * @param event			Submit button is clicked.
	 * @throws IOException
	 */
	@FXML public void handleSubmitButtonAction (ActionEvent event) throws IOException {
		renameErrorText.setOpacity(0);
		if(renameTextField.getText().trim().isEmpty()) {
			renameErrorText.setOpacity(1);
		}
		else {
			for(int x = 0; x<LoginController.currentUser.getAlbums().size(); x++) {
				if (renameTextField.getText().trim().equalsIgnoreCase(LoginController.currentUser.getAlbums().get(x).getName())){
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Album could not be created. An album with that name already exists.");
					alert.showAndWait();
					return;
				}
			}
			LoginController.currentUser.deleteAlbum(UserPageController.selected);
			UserPageController.selected.setName(renameTextField.getText().trim());
			LoginController.currentUser.addAlbum(UserPageController.selected);
			
			((Node) event.getSource()).getScene().getWindow().hide();
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
	
	/**
	 * Goes back to main User page.
	 * 
	 * @param event				Cancel button is clicked.
	 * @throws IOException
	 */
	@FXML public void handleCancelButtonAction (ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
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
