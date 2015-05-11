//////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  FileSystemMain.java
// File:             User.java
// Semester:         CS367 Spring 2015
//
// Author:           Si Xie
// CS Login:         si
// Lecturer's Name:  Jim Skrentny
// Lab Section:      n/a
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   fully acknowledge and credit all sources of help,
//                   other than Instructors and TAs.
//
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
//
// Online sources:   Piazza
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.ArrayList;
/**
 * The User class represents a user having a name (as a String), folders 
 * (as a list of SimpleFolder), files (as a list of SimpleFile) 
 * owned by the user. The User.java file contains the outline of the User class.
 * @author Ricki Xie
 *
 */
public class User {
	
	private String name; //name of the user.
	private ArrayList<SimpleFile> files;//list of files owned/created by user
	private ArrayList<SimpleFolder> folders;//list of folder owned by user.

	/**
	 * Constructs a user with name. It also initializes the lists appropriately.
	 * @param name the name of the user.
	 */
	public User(String name) {
		//TODO
		if(name==null) throw new IllegalArgumentException();
		this.name = name;
		this.files = new ArrayList<SimpleFile>();
		this.folders = new ArrayList<SimpleFolder>();
	}
	
	
	//@Override
	/**
	 * Override the equals method of the object to compare two users on name 
	 * field. If names are same, return true, otherwise, return false.
	 * @param obj This object to compare
	 */
	public boolean equals(Object obj) {
		//TODO		
		if(obj==null) throw new IllegalArgumentException();
		if(this.name.equals(((User)obj).getName()))//object?
			return true;
		else return false;
	}

	//returns the name of the user.
	/**
	 * Returns the name of the user.
	 * @return the name of the user
	 */
	public String getName() {
		//TODO
		return this.name;
	}

	//returns the list of files owned by the user.
	/**
	 * Returns an arraylist of files owned by the user.
	 * @return a list of file owned by the user.
	 */
	public ArrayList<SimpleFile> getFiles() {
		//TODO
		return this.files;
	}

	//adds the file to the list of files owned by the user.
	/**
	 * Adds the file to the list of files owned by the user.
	 * @param newfile the file to add and owned by the user
	 */
	public void addFile(SimpleFile newfile) {
		//TODO
		if(newfile==null) throw new IllegalArgumentException(); 
		this.files.add(newfile);//what if the file is already exist?
	} 
	
	//removes the file from the list of owned files of the user.
	/**
	 * Removes the file from the list of owned files of the user. 
	 * If deleted, return true, otherwise return false.
	 * @param rmFile the file to remove
	 * @return true if deleted, else return false
	 */
	public boolean removeFile(SimpleFile rmFile){
		//TODO
		if(rmFile==null) throw new IllegalArgumentException();
		if (!this.files.contains(rmFile)) return false;
		else{
			this.files.remove(rmFile);
			return true;
		}
		
	}

	//returns the list of folders owned by the user.
	/**
	 * Returns the list of folders owned by the user.
	 * @return the list of folder owned by the user
	 */
	public ArrayList<SimpleFolder> getFolders() {
		//TODO
		return this.folders;
	}

	//adds the folder to the list of folders owned by the user.
	/**
	 * Adds the folder to the list of folders owned by the user.
	 * @param newFolder the folder to add to the user.
	 */
	public void addFolder(SimpleFolder newFolder) {
		//TODO
		if(newFolder==null) throw new IllegalArgumentException();//what if the folder is already exist?
				this.folders.add(newFolder);
		//2) This program assumes that admin will be the owner of 
		//the root folder and that the root folder is not added 
		//to the admin's list of owned folders because only admin can own the root folder. 
		//This will also ensure that your output matches with the sample output.
	}

	//removes the folder from the list of folders owned by the user.
	/**
	 * Removes the folder from the list of folders owned by the user. 
	 * If deleted, return true, otherwise return false.
	 * @param rmFolder the folder to remove
	 * @return true if deleted, else return false
	 */
	public boolean removeFolder(SimpleFolder rmFolder){
		//TODO
		if(rmFolder==null) throw new IllegalArgumentException();
		if (!this.folders.contains(rmFolder)) return false;
		else{
			this.folders.remove(rmFolder);
			return true;
		}
	}
	
	//returns the string representation of the user object.
	@Override
	public String toString() {
		String retType = name + "\n";
		retType = retType + "Folders owned :\n";
		for(SimpleFolder preFolder : folders){
			retType = retType + "\t" + preFolder.getPath() + "/" + preFolder.getName() + "\n";
		}
		retType = retType + "\nFiles owned :\n"; 
		for(SimpleFile preFile : files){
			retType = retType + "\t" + preFile.getPath() + "/" + preFile.getName() + "." + preFile.getExtension().toString() + "\n";
		}
		return retType;
	}
	
	
	
}
