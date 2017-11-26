package model;

import java.io.*;
import java.util.*;
/**
 * 
 * @author Rituraj Kumar
 *
 */
public class album implements Serializable{

	public ArrayList<image> images;
	private File info;
	public boolean made;
	
	
	
	public album(String url) throws Exception{
		this.info = new File(url);
		this.made =this.info.mkdir();
		this.images = new ArrayList<image>();
		
		
	}
	/**
	 * This constructor is for user search results make a album function
	 * @param url
	 * @param newalbum
	 * @throws Exception
	 */
	public album(String url, List<image> newalbum) throws Exception{
		this.info = new File(url);
		this.made =this.info.mkdir();
		this.images = new ArrayList<image>();
		this.images.addAll(newalbum);
		
		
	}
	
	/**
	 * 
	 * @return if album is made return true
	 */
	public boolean ismade(){
		return this.made;
	}
	
	
	/**
	 * 
	 * @param m image being added to album list
	 * @throws IOException
	 */
	public void addimage(image m) throws IOException{
		for(int i=0; i<this.images.size();i++){
			if(this.images.get(i).getfile().getName().equals(m.getfile().getName())){
				this.images.remove(i);
				this.images.add(m);
				return;
			}
		}
		this.images.add(m);
		
	}
	/**
	 * 
	 * @param m image being removed from album list
	 * @throws IOException
	 */
	public void removeimage(image m) throws IOException{
		this.images.remove(m);
	}
	/**
	 * 
	 * @return album file
	 */
	public File getfile(){
		return this.info;
	}
	
}
