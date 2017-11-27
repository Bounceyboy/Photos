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
	
	private static final long serialVersionUID = -6425302359328772834L;
	private String username;
	private String password;
	private ArrayList<Album> albums;

	/**
	 * Constructor for user creation.
	 * 
	 * @param username
	 * @param password
	 */
	public User(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
		albums = new ArrayList<Album>(0);
	}

	/**
	 * Getter for a User's username.
	 * 
	 * @return		Username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Setter for a user's username.
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Getter for a user's password.
	 * 
	 * @return	Password
	 */
	public String getPassword() {
		return password;
	}

	
	/**
	 * Setter for a User's password.
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Getter for albums arraylist.
	 * 
	 * @return	Arraylist of all of the User's albums.
	 */
	public ArrayList<Album> getAlbums() {
		return albums;
	}

	/**
	 * Adds an album to the user's arraylist of albums and serializes the updated user.
	 * 
	 * @param album
	 */
	public void addAlbum(Album album) {
		this.albums.add(album);
		try {
			this.write();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Deletes an album from the user's arraylist of albums and serializes the updated user.
	 * 
	 * @param album
	 */
	public void deleteAlbum(Album album) {
		this.albums.remove(album);
		try {
			this.write();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Serializes the user into a .txt file.
	 * 
	 * @throws Exception
	 */
	public void write() throws Exception {
		FileOutputStream fos = new FileOutputStream("users/" + this.getUsername() + ".txt");
		ObjectOutputStream oos = new ObjectOutputStream(fos); 
		oos.writeObject(this);
		oos.close();
	}
}
