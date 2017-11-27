package model;
import javafx.scene.image.*;
import java.io.*;
import java.util.*;
/**
 * 
 * @author Rituraj Kumar
 *
 */
public class image implements Serializable{
	private transient Image pic;
	private transient Image thumbnail;
	private File img;
	private Date date;
	private String caption;
	private HashMap<String, ArrayList<String>> tags = new HashMap<String, ArrayList<String>>();
	
	
	//TODO I edited this to make it only take the location of the file (url)
	//I did this because we aren't supposed to move the files anyway, we'll just track which files are in which albums
	//and serialize that info.
	/**
	 * 
	 * @param url jpg or png location
	 * @param location where this file will be located
	 * @throws Exception
	 */
	public image(String url) throws Exception{
		this.img = new File(url);
		this.pic = new Image(this.img.toURI().toString());
		this.thumbnail = new Image(this.img.toURI().toString(),150,0,true,true);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MILLISECOND,0);
		cal.setTimeInMillis(this.img.lastModified());
		this.date = cal.getTime();
		this.caption = "new Photo";
		
		//this.file =  new File(location +"\\"+this.img.getName().substring(0,this.img.getName().lastIndexOf('.'))+".txt");
		
		
		
		
	}
	
	
	/**
	 * populates the transient Image objects: pic and thumbnail
	 * @throws Exception
	 */
	public void initilize() throws Exception{
		
		this.pic = new Image(this.img.toURI().toString());
		this.thumbnail = new Image(img.toURI().toString(),90,90,true,true);
		
	}
	
	
	public void setCaption(String newcaption) throws Exception{
		this.caption = newcaption;
	}
	
	public String getCaption(){
		return this.caption;
	}
	
	public Image getthumbnail(){
		return this.thumbnail;
	}
	
	public Image getpic(){
		return this.pic;
	}
	
	public Date getdate(){
		return this.date;
	}
	
	public File getfile(){
		return this.img;
	}
	
	/**
	 * 
	 * @param key tagname
	 * @param value tagvalue
	 */
	public void addtags(String key, String value){
		if(tags.get(key) != null){
			if(!tags.get(key).contains(value)){
				tags.get(key).add(value);
			}
		}
		else{
			tags.put(key, new ArrayList<String>());
			tags.get(key).add(value);
		}
		
	}
	/**
	 * 
	 * @param key tagname
	 * @param value tagvalue
	 */
	public void deletetags(String key, String value){
		if(tags.get(key).size()==1){
			tags.remove(key);
		}
		else{
		tags.get(key).remove(value);
		}
	}
	/**
	 * 
	 * @return Hashmap of tags
	 */
	public HashMap<String, ArrayList<String>> gettags(){
		return this.tags;
	}
}
