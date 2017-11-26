package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserList {
	
	private ArrayList<User> list = new ArrayList<User>(0);
	
	public UserList(String file) throws FileNotFoundException {
		Scanner sc = new Scanner (new File(file));
		String username, password;
		
		while(sc.hasNextLine()) {
			sc.nextLine();
			username = sc.nextLine();
			password = sc.nextLine();
			
			User temp = new User(username, password);
			list.add(temp);
		}
		
		sc.close();
	}
	
	public ArrayList<String> getNames() {
		ArrayList<String> toReturn = new ArrayList<String>();
		for(int x = 0; x<list.size(); x++) {
			toReturn.add(list.get(x).getUsername());
		}
		return toReturn;
	}
	
	public void addUser(User toAdd) throws IOException {
		BufferedWriter writer = null;
		FileWriter fstream = new FileWriter ("users/loginInfo", true);
		writer = new BufferedWriter(fstream);
		writer.write("\n" + toAdd.getUsername() + "\n");
		writer.write(toAdd.getPassword() + "\n");
		writer.close();
		list.add(toAdd);
	}
	
	public void deleteUserNoFolder (User toDelete) throws IOException {
		File old = new File("users/loginInfo");
		String everythingElse = "\n";
		
		BufferedReader reader = new BufferedReader(new FileReader(old));
		
		String currentLine;
		
		currentLine = reader.readLine();
		while((currentLine = reader.readLine()) != null) {
			if(currentLine.equalsIgnoreCase(toDelete.getUsername())){
				currentLine = reader.readLine();
				currentLine = reader.readLine();
			}
			else {
				everythingElse = everythingElse.concat(currentLine + "\n");
				currentLine = reader.readLine();
				everythingElse = everythingElse.concat(currentLine + "\n");
				currentLine = reader.readLine();
				if(currentLine!=null)
					everythingElse = everythingElse.concat(currentLine + "\n");
				else
					everythingElse = everythingElse.concat("\n");
			}
		}
		
		everythingElse = everythingElse.substring(0, everythingElse.length()-1);
		
		reader.close();
		
		old.delete();
		
		BufferedWriter writer = null;
		try{
			writer = new BufferedWriter(new FileWriter("users/loginInfo"));
			writer.write(everythingElse);
		}
		finally{
			writer.close();
		}

		writer.close();
		
		list.remove(toDelete);
	}
	
	
	public void deleteUser (User toDelete) throws IOException {
		File old = new File("users/loginInfo");
		String everythingElse = "\n";
		
		BufferedReader reader = new BufferedReader(new FileReader(old));
		
		String currentLine;
		
		currentLine = reader.readLine();
		while((currentLine = reader.readLine()) != null) {
			if(currentLine.equalsIgnoreCase(toDelete.getUsername())){
				currentLine = reader.readLine();
				currentLine = reader.readLine();
			}
			else {
				everythingElse = everythingElse.concat(currentLine + "\n");
				currentLine = reader.readLine();
				everythingElse = everythingElse.concat(currentLine + "\n");
				currentLine = reader.readLine();
				if(currentLine!=null)
					everythingElse = everythingElse.concat(currentLine + "\n");
				else
					everythingElse = everythingElse.concat("\n");
			}
		}
		
		everythingElse = everythingElse.substring(0, everythingElse.length()-1);
		
		reader.close();
		
		old.delete();
		
		BufferedWriter writer = null;
		try{
			writer = new BufferedWriter(new FileWriter("users/loginInfo"));
			writer.write(everythingElse);
		}
		finally{
			writer.close();
		}

		writer.close();
		
		list.remove(toDelete);
	}
	
	
	public boolean findUser (String username) {
		User current;
		for (int x = 0; x < list.size(); x++) {
			current = list.get(x);
			if (current.getUsername().equalsIgnoreCase(username))
				return true;
		}
		return false;
	}
	
	
	public boolean findUser(String username, String password) {
		User current;
		for (int x = 0; x < list.size(); x++) {
			current = list.get(x);
			if (current.getUsername().equalsIgnoreCase(username) && current.getPassword().equals(password))
				return true;
		}
		return false;
	}
	
	
}
