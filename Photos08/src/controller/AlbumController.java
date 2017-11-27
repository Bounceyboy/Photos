package controller;

import java.io.*;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Album;
import model.image;
/**
 * 
 * @author Rituraj Kumar
 *
 */
public class AlbumController {

	@FXML
	private TilePane tile;
	@FXML
	private BorderPane root;
	@FXML
	private ScrollPane sp;
	@FXML
	private Text warningtxt;
	public static image selected = null;
	public FileChooser filechoice = new FileChooser();
	@FXML private TextField newcaption;
	private Parent selectedparent;
	Stage stage = new Stage();
	
	
	@FXML
	private void initialize() throws Exception {

		sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		tileload();
	}
	
	
	/**
	 * image browser
	 * @return image file
	 */
	public File browse() {
		filechoice.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*.*"),
				new FileChooser.ExtensionFilter("JPG", "*.jpg"), new FileChooser.ExtensionFilter("PNG", "*.png"));
		File img = this.filechoice.showOpenDialog(UserPageController.stage);
		return img;
	}
	/**
	 * loads tilepane with imageview and caption
	 * handles image selection and photo display
	 * @throws Exception
	 */
	public void tileload() throws Exception {
		this.tile.getChildren().clear();
		for (int i = 0; i < UserPageController.selected.images.size(); i++) {
			// System.out.println(UserPageController.selected.images.get(i).getfile().getName());
			UserPageController.selected.images.get(i).initilize();
			ImageView imageView = new ImageView(UserPageController.selected.images.get(i).getthumbnail());
			Text caption = new Text(UserPageController.selected.images.get(i).getCaption());
			VBox thumbnail = new VBox();
			thumbnail.setMaxHeight(90);
			thumbnail.setMaxWidth(90);
			thumbnail.setPrefSize(100, 100);
			imageView.fitWidthProperty().bind(thumbnail.widthProperty());
			//imageView.fitHeightProperty().bind(thumbnail.heightProperty());
			thumbnail.getChildren().addAll(imageView, caption);
			
			thumbnail.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {

					if (event.getButton().equals(MouseButton.PRIMARY)) {
						if (event.getClickCount() == 1) {

							for (int i = 0; i < UserPageController.selected.images.size(); i++) {
								if (UserPageController.selected.images.get(i).getthumbnail().equals(imageView.getImage())) {
									selected = UserPageController.selected.images.get(i);
									break;
								}
							}
							if(selectedparent != null){
								selectedparent.setStyle("-fx-background-color:white;");
							}
							selectedparent = imageView.getParent();
							selectedparent.setStyle("-fx-background-color:blue;");
						} else if (event.getClickCount() > 1) {
							try{
							Stage slideshow = (Stage) ((Node) event.getSource()).getScene().getWindow();
							FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Photoview.fxml"));
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
			
			this.tile.getChildren().addAll(thumbnail);
			

		}
		
		sp.setContent(this.tile);
		root.setLeft(this.sp);
	}

	/**
	 * handles button="browse to add new photo"
	 * @throws Exception
	 */
	public void add() throws Exception {
		File imagefile = browse();
		if (imagefile != null) {
			if ((imagefile.getName().lastIndexOf(".jpg") != -1) 
					|| (imagefile.getName().lastIndexOf(".png") != -1)
					|| (imagefile.getName().lastIndexOf(".JPG") != -1)
				|| (imagefile.getName().lastIndexOf(".PNG") != -1)) {

				String url = imagefile.getPath();
				image img = new image(url);
				UserPageController.selected.addimage(img);

				//System.out.println(UserPageController.selected.images.size());
			}
		}
		LoginController.currentUser.write();
		tileload();

	}
	/**
	 * handles button="Remove selected photo"
	 * @throws Exception
	 */
	public void remove() throws Exception {
		if (selected != null) {
			UserPageController.selected.removeimage(selected);
			this.warningtxt.setVisible(false);
			LoginController.currentUser.write();
			tileload();
			selected = null;
		} else {
			this.warningtxt.setVisible(true);
		}
	}
	/**
	 * copy photo to another album
	 */
	public void copy() {
		if (selected != null) {
			BorderPane bp = new BorderPane();
			ArrayList<Album> albums = LoginController.currentUser.getAlbums();
			ArrayList<String> names = new ArrayList<String>();
			for (int x = 0; x<albums.size(); x++){
				names.add(albums.get(x).getName());
			}
			ListView<String> albumlist = new ListView<String>(FXCollections.observableArrayList(names));
			bp.setCenter(albumlist);
			Button submit = new Button("Submit");
			bp.setBottom(submit);
			Scene albumchoice = new Scene(bp,400,400);
			Stage stage = new Stage();
			stage.setScene(albumchoice);
			stage.show();
			submit.setOnAction(new EventHandler<ActionEvent>(){

				@Override
				public void handle(ActionEvent event) {
					if(albumlist.getSelectionModel().getSelectedItem() != null){
						try {
							for(int x = 0; x<LoginController.currentUser.getAlbums().size(); x++){
								if (LoginController.currentUser.getAlbums().get(x).getName().equals(
										albumlist.getSelectionModel().getSelectedItem())) {
									LoginController.currentUser.getAlbums().get(x).addimage(selected);
									LoginController.currentUser.write();
									stage.close();
									return;
								}			
							}
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
				}
				
			});
			
			
			this.warningtxt.setVisible(false);
		} else {
			this.warningtxt.setVisible(true);
		}
	}
	/**
	 * move photo to another album
	 */
	public void move() {
		if (selected != null) {
			BorderPane bp = new BorderPane();
			ArrayList<Album> albums = LoginController.currentUser.getAlbums();
			ArrayList<String> names = new ArrayList<String>();
			for (int x = 0; x<albums.size(); x++){
				names.add(albums.get(x).getName());
			}
			ListView<String> albumlist = new ListView<String>(FXCollections.observableArrayList(names));
			bp.setCenter(albumlist);
			Button submit = new Button("Submit");
			bp.setBottom(submit);
			Scene albumchoice = new Scene(bp,400,400);
			Stage stage = new Stage();
			stage.setScene(albumchoice);
			stage.show();
			submit.setOnAction(new EventHandler<ActionEvent>(){

				@Override
				public void handle(ActionEvent event) {
					if(albumlist.getSelectionModel().getSelectedItem() != null){
						try {
							for(int x = 0; x<LoginController.currentUser.getAlbums().size(); x++){
								if (LoginController.currentUser.getAlbums().get(x).getName().equals(
										albumlist.getSelectionModel().getSelectedItem())) {
									LoginController.currentUser.getAlbums().get(x).addimage(selected);
									UserPageController.selected.removeimage(selected);
									LoginController.currentUser.write();
									tileload();
									stage.close();
									return;
								}			
							}
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
				}
				
			});
			
			
			this.warningtxt.setVisible(false);
		} else {
			this.warningtxt.setVisible(true);
		}
	}
	/**
	 * 
	 * @param event slideshow button 
	 * Starts at selected image or first image
	 * @throws Exception
	 */
	public void slideshow(ActionEvent event) throws Exception {

		Stage slideshow = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Slideshow.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		//SlideshowController slide = fxmlLoader.<SlideshowController>getController();
		slideshow.setScene(new Scene(root, 1224, 591));
		slideshow.show();
	}
	/**
	 * recaption image
	 * textfield must be filled
	 * handles button="Submit"
	 * @throws Exception
	 */
	public void recaption() throws Exception{
		
		if((!this.newcaption.getText().isEmpty()) && (selected!=null)){
			selected.setCaption(this.newcaption.getText());
			LoginController.currentUser.write();
			tileload();
			selected=null;
			this.warningtxt.setVisible(false);
		}
		else if (selected==null){
			this.warningtxt.setVisible(true);
		}
		else{
			this.newcaption.setPromptText("*fill field to caption");
			this.warningtxt.setVisible(false);
		}
	}
	
	/**
	 * return to user
	 * @throws IOException 
	 */
	public void menu(ActionEvent event) throws IOException {
		
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
