package model;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * User class, stores the username, password and an ArrayList of the user's albums.
 * @author Jason Holley
 */
public class User implements Serializable {
	
	private String username;
	private String password;
	private ArrayList<Album> albums;

	public User(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
		albums = new ArrayList<Album>(0);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<Album> getAlbums() {
		return albums;
	}

	public void addAlbum(Album album) {
		this.albums.add(album);
		try {
			this.write();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteAlbum(Album album) {
		this.albums.remove(album);
		try {
			this.write();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void write() throws Exception {
		FileOutputStream fos = new FileOutputStream("users/" + this.getUsername() + ".txt");
		ObjectOutputStream oos = new ObjectOutputStream(fos); 
		oos.writeObject(this);
		oos.close();
	}
}
