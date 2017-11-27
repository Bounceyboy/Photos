package controller;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;
/**
 * 
 * @author Rituraj Kumar
 *
 */
public class PhotoController {

	@FXML private Text date;
	@FXML private Text caption;
	@FXML private ImageView photo;
	@FXML private TextField submittedtag;
	@FXML private BorderPane root;
	@FXML private ScrollPane sp;
	@FXML private ListView<String> tags;
	@FXML private HBox listhbox;
	@FXML private VBox listvbox;
	private ObservableList<String> collected;
	
	@FXML private void initialize(){
		
		sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		
		
		inittags();
		
		this.date.setText(AlbumController.selected.getdate().toString());
		this.caption.setText(AlbumController.selected.getCaption());
		
		this.photo = new ImageView(AlbumController.selected.getpic());
		/*this.photo.fitHeightProperty().bind(sp.heightProperty());
		this.photo.fitWidthProperty().bind(sp.widthProperty());*/
		this.sp.setContent(this.photo);
		this.root.setLeft(this.sp);
	}

	/**
	 * add tag to image
	 * @throws Exception
	 */
	public void addtag() throws Exception{
		if(!submittedtag.getText().isEmpty()){
			if(submittedtag.getText().lastIndexOf("=")!=-1){
				String key=submittedtag.getText().substring(0, submittedtag.getText().lastIndexOf("="));
				String value = submittedtag.getText().substring(submittedtag.getText().lastIndexOf("=")+1);
				AlbumController.selected.addtags(key, value);
				LoginController.currentUser.write();
				inittags();
			}
			
		}
	}
	/**
	 * delete tag from image
	 * @throws Exception
	 */
	public void deletetag() throws Exception{
		if(tags.getSelectionModel().getSelectedItem()!=null){
			String key = tags.getSelectionModel().getSelectedItem().substring(0, tags.getSelectionModel().getSelectedItem().lastIndexOf("="));
			String value = tags.getSelectionModel().getSelectedItem().substring(tags.getSelectionModel().getSelectedItem().lastIndexOf("=")+1);
			AlbumController.selected.deletetags(key, value);
			LoginController.currentUser.write();
			inittags();
		}
	}
	/**
	 * handles button="Back"
	 * @param event
	 * return to album
	 * @throws Exception
	 */
	public void back(ActionEvent event) throws Exception{
		
		Stage album = (Stage) ((Node)event.getSource()).getScene().getWindow();
		FXMLLoader fxmlLoader =new FXMLLoader(getClass().getResource("/view/Album.fxml"));
		Parent root = (Parent)fxmlLoader.load();
		album.setScene(new Scene(root, 1224, 591));
		album.show();
	}
	/**
	 * populate tags into listview
	 */
	public void inittags(){
		this.collected = FXCollections.observableArrayList();
		for(String key:AlbumController.selected.gettags().keySet()){
			for(int i=0;i<AlbumController.selected.gettags().get(key).size();i++){
				this.collected.add(key+"="+AlbumController.selected.gettags().get(key).get(i));
			}
		}
		this.tags.setItems(collected);
	}
}
