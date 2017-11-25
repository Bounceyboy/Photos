package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.album;
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
		File img = this.filechoice.showOpenDialog(Main.getstage());
		return img;
	}
	/**
	 * loads tilepane with imageview and caption
	 * handles image selection and photo display
	 * @throws Exception
	 */
	public void tileload() throws Exception {
		this.tile.getChildren().clear();
		for (int i = 0; i < Main.curr.images.size(); i++) {
			// System.out.println(Main.curr.images.get(i).getfile().getName());
			Main.curr.images.get(i).initilize();
			ImageView imageView = new ImageView(Main.curr.images.get(i).getthumbnail());
			imageView.setFitWidth(150);
			Text caption = new Text(Main.curr.images.get(i).getCaption());
			imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {

					if (event.getButton().equals(MouseButton.PRIMARY)) {
						if (event.getClickCount() == 1) {

							for (int i = 0; i < Main.curr.images.size(); i++) {
								if (Main.curr.images.get(i).getthumbnail().equals(imageView.getImage())) {
									selected = Main.curr.images.get(i);
									
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
			this.tile.getChildren().addAll(thumbnail);
			

		}
		
		sp.setContent(this.tile);
		root.setLeft(this.sp);
	}
	/**
	 * serialization
	 * @throws Exception
	 */
	public void write() throws Exception {
		FileOutputStream fos = new FileOutputStream(Main.curr.getfile().getPath() + "\\" + "attribute.txt");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(Main.curr);
		oos.close();
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
				image img = new image(url, Main.curr.getfile().getPath());
				Main.curr.addimage(img);

				//System.out.println(Main.curr.images.size());
			}
		}
		write();
		tileload();

	}
	/**
	 * handles button="Remove selected photo"
	 * @throws Exception
	 */
	public void remove() throws Exception {
		if (selected != null) {
			Main.curr.removeimage(selected);
			this.warningtxt.setVisible(false);
			write();
			tileload();
			selected = null;
		} else {
			this.warningtxt.setVisible(true);
		}
	}
	/**
	 * TODO copy to another album
	 */
	public void copy() {
		if (selected != null) {
			selected = null;
			this.warningtxt.setVisible(false);
		} else {
			this.warningtxt.setVisible(true);
		}
	}
	/**
	 * TODO move to another album
	 */
	public void move() {
		if (selected != null) {
			selected = null;
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
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Slideshow.fxml"));
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
			write();
			tileload();
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
	 * TODO return to user
	 */
	public void menu() {

	}
}
