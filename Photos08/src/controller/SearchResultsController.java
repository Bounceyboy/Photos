package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.image;

/**
 * Popup for search results and potential album creation.
 * 
 * @author Jason Holley
 *
 */
public class SearchResultsController {
	
	Stage stage = new Stage();
	
	@FXML private Button createAlbumButton;
	@FXML private Button cancelButton;
	@FXML private TextField albumNameField;
	@FXML private TilePane tilePane;
	@FXML private Label createAlbumError;
	@FXML private ScrollPane sp;
	@FXML private Pane root;
	public static image selected;
	
	/**
	 * Handles album creation.
	 * 
	 * @param event				Create album button is clicked.
	 * @throws IOException
	 */
	@FXML public void handleCreateAlbumButton (ActionEvent event) throws IOException {
		createAlbumError.setOpacity(0);
		if(albumNameField.getText().trim().isEmpty()){
			createAlbumError.setOpacity(1);
		}
		else {
			for(int x = 0; x<LoginController.currentUser.getAlbums().size(); x++){
				if (albumNameField.getText().trim().equalsIgnoreCase(LoginController.currentUser.getAlbums().get(x).getName())) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Album could not be created. An album with that name already exists.");
					alert.showAndWait();
					return;
				}	
			}
			
			UserPageController.selected.setName(albumNameField.getText().trim());
			LoginController.currentUser.addAlbum(UserPageController.selected);
			
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
	 * Goes back to main user page.
	 * 
	 * @param event				Cancel button is clicked.
	 * @throws IOException
	 */
	@FXML public void handleCancelButton (ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/UserPage.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root, 800, 400);
		
		((Node) event.getSource()).getScene().getWindow().hide();
		stage.setScene(scene);
		stage.setTitle("Photos08");
		stage.show();
	}
	
	@FXML
	private void initialize() throws Exception {
		sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		this.tilePane.getChildren().clear();
		for (int i = 0; i < UserPageController.selected.images.size(); i++) {
			// System.out.println(UserPageController.selected.images.get(i).getfile().getName());
			UserPageController.selected.images.get(i).initilize();
			ImageView imageView = new ImageView(UserPageController.selected.images.get(i).getthumbnail());
			imageView.setFitWidth(150);
			Text caption = new Text(UserPageController.selected.images.get(i).getCaption());
			imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {

					if (event.getButton().equals(MouseButton.PRIMARY)) {
						if (event.getClickCount() == 1) {

							for (int i = 0; i < UserPageController.selected.images.size(); i++) {
								if (UserPageController.selected.images.get(i).getthumbnail().equals(imageView.getImage())) {
									selected = UserPageController.selected.images.get(i);
									
								}
							}
						} else if (event.getClickCount() > 1) {
							try{
							Stage slideshow = (Stage) ((Node) event.getSource()).getScene().getWindow();
							FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Photoview.fxml"));
							Parent root = (Parent) fxmlLoader.load();
							slideshow.setScene(new Scene(root, 1224, 591));
							slideshow.show();
							}
							catch(IOException e){
								
							}
						}
					}

				}

			});
			VBox thumbnail = new VBox();
			thumbnail.setMaxHeight(120);
			//thumbnail.setMinHeight(120);
			imageView.fitWidthProperty().bind(thumbnail.widthProperty());
			imageView.fitHeightProperty().bind(thumbnail.heightProperty());
			thumbnail.setPrefSize(150, 150);
			thumbnail.getChildren().addAll(imageView, caption);
			this.tilePane.getChildren().addAll(thumbnail);
			

		}
		
		sp.setContent(this.tilePane);
		
	}
}
