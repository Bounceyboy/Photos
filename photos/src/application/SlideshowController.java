package application;




import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.image;
/**
 * 
 * @author Rituraj Kumar
 *
 */
public class SlideshowController {

	@FXML BorderPane root;
	@FXML ImageView currentimage;
	@FXML ScrollPane sp;
	public image imginfo;
	
	@FXML private void initialize(){
		sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		
		if(AlbumController.selected!=null){
			this.currentimage=new ImageView(AlbumController.selected.getpic());
			this.imginfo=AlbumController.selected;
			
		}
		else if(Main.curr.images.size()>0){
			this.currentimage=new ImageView(Main.curr.images.get(0).getpic());
		}
		currentimage.fitHeightProperty().bind(sp.heightProperty());
		currentimage.fitWidthProperty().bind(sp.widthProperty());
		sp.setContent(this.currentimage);
		root.setCenter(this.sp);
	}
	/**
	 * show next image in slideshow
	 */
	public void next(){
		int i=0;
		for(i=0;i<Main.curr.images.size();i++){
			if(Main.curr.images.get(i).equals(this.imginfo)){
				i++;
				break;
			}
		}
		if(i>=Main.curr.images.size()){
			i=0;
		}
		this.imginfo=Main.curr.images.get(i);
		this.currentimage= new ImageView(imginfo.getpic());
		
		this.currentimage.fitWidthProperty().bind(this.sp.heightProperty());
		this.currentimage.fitWidthProperty().bind(this.sp.widthProperty());
		this.sp.setContent(this.currentimage);
		this.root.setCenter(this.sp);
	}
	/**
	 * show last image in slideshow
	 */
	public void prev(){
		int i=Main.curr.images.size()-1;
		for(i=Main.curr.images.size()-1;i>=0;i--){
			if(Main.curr.images.get(i).equals(this.imginfo)){
				i--;
				break;
			}
		}
		if(i<0){
			i=Main.curr.images.size()-1;
		}
		this.imginfo=Main.curr.images.get(i);
		this.currentimage= new ImageView(imginfo.getpic());
		
		this.currentimage.fitWidthProperty().bind(this.sp.heightProperty());
		this.currentimage.fitWidthProperty().bind(this.sp.widthProperty());
		this.sp.setContent(this.currentimage);
		this.root.setCenter(this.sp);
	}
	/**
	 * handles button="Back"
	 * @param event
	 * return to album
	 * @throws Exception
	 */
	public void back(ActionEvent event) throws Exception{
		
		Stage album = (Stage) ((Node)event.getSource()).getScene().getWindow();
		FXMLLoader fxmlLoader =new FXMLLoader(getClass().getResource("album.fxml"));
		Parent root = (Parent)fxmlLoader.load();
		album.setScene(new Scene(root, 1224, 591));
		album.show();
	}
}
