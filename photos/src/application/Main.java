package application;
import model.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.*;
/**
 * 
 * @author Rituraj Kumar
 *
 */

public class Main extends Application {
	private static Stage stage;
	public static album curr;
	@Override
	public void start(Stage stage) throws Exception {
		/**
		 * the following lines are how i tested making or opening an album
		 * type album name
		 * followed by 0 if creating an album
		 * or 1 if opening an already existing album
		 * 
		 * Change the System.getproperty to user instead of project directory("user.dir")
		 */
		Scanner scan = new Scanner(System.in);
		String albumname = scan.next();
		album made;
		if(scan.nextInt()==0){
		made = new album(System.getProperty("user.dir")+"\\"+albumname);
		}
		else{
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\"+albumname+"\\"+"attribute.txt");
			ObjectInputStream ois = new ObjectInputStream(fis);
			made = (album) ois.readObject();
			ois.close();
			fis.close();
		}
		scan.close();
		Main.curr=made;
		//System.out.println(Main.curr.images.size());
		
		Parent root = FXMLLoader.load(getClass().getResource("album.fxml"));
		
        Scene scene = new Scene(root, 1224, 591);
        
        Main.stage=stage;
        stage.setTitle(System.getProperty("user.dir").substring(System.getProperty("user.dir").lastIndexOf("\\")+1));
        stage.setScene(scene);
        stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static Stage getstage(){
		return stage;
	}
	
	
}
