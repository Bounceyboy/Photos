package model;

import java.io.*;
import java.util.*;
/**
 * 
 * @author Rituraj Kumar
 *
 */
public class Album implements Serializable{

	private String name;
	public ArrayList<image> images;	
	
	public Album(String name) {
		this.images = new ArrayList<image>();
		this.setName(name);
		
		
	}
	/**
	 * This constructor is for user search results make a album function
	 * @param url
	 * @param newalbum
	 * @throws Exception
	 */
	public Album(List<image> newalbum) throws Exception{
		this.images = new ArrayList<image>();
		this.images.addAll(newalbum);
		
		
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
}
