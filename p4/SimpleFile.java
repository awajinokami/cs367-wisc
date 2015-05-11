//////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  FileSystemMain.java
// File:             SimpleFile.java
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
 * The SimpleFile class represents a single file in the file system having a 
 * name (as a String), extension (as an Extension), content (as a String), 
 * path (as a String), owner (as a User), 
 * parent (as a SimpleFolder), and a list of allowed users (as a list of Access). 
 * The SimpleFile.java file contains the outline of the SimpleFile class.
 * @author Ricki Xie
 *
 */
public class SimpleFile {
	private String name;
	private Extension extension;
	private String content;
	private User owner;
	private ArrayList<Access> allowedUsers;
	private String path;
	private SimpleFolder parent;
	/**
	 * Constructor to initialize a file object with: name, extension, path, 
	 * content, parent, owner. It also initializes lists appropriately.
	 * @param name of the file 
	 * @param extension  of the file
	 * @param path of the file
	 * @param content of the file
	 * @param parent folder of the file
	 * @param owner user of the file
	 */
	public SimpleFile(String name, Extension extension, String path, String content, SimpleFolder parent, User owner) {
		//TODO
		if(name ==null||extension==null||path==null||content==null||parent==null||owner==null) 
			throw new IllegalArgumentException();
		this.name = name;
		this.extension = extension;
	
		this.path = path;
		this.content = content;
		this.parent = parent;
		this.owner = owner;
		this.allowedUsers = new ArrayList<Access>();
		//add the file to the parent list or if it's parent is empty, add to root	
		//	this.parent.addFile(this);

		//2. If it is not admin

		if(!owner.getName().equalsIgnoreCase("admin")){
			//create a new access for both the admin and owner
			Access nonadminOwner = new Access(owner, 'w');
			//add both to the allowed list
			this.addAllowedUser(nonadminOwner);	
		}

		Access adminOwner = new Access(FileSystemMain.admin, 'w');//change this
		if(!this.containsAllowedUser("admin")){
			this.addAllowedUser(adminOwner);
		}
		//add the file to owner's file list
		this.owner.addFile(this);


		this.parent.addFile(this);
	}


	/**
	 * Return the path of this file
	 * @return the path variable.
	 */
	public String getPath(){
		//TODO
		return this.path;
	}


	/**
	 * Return the parent folder of this file
	 * @return the parent folder of this file.
	 */
	public SimpleFolder getParent() {
		//TODO
		return this.parent;
	}

	/**
	 * Return the name of the file.
	 * @return the name of the file.
	 */
	public String getName() {
		//TODO
		return this.name;
	}

	/**
	 *  Return the extension of the file
	 * @return the extension of the file.
	 */
	public Extension getExtension() {
		//TODO
		return this.extension;
	}

	/**
	 * Return the content of this file
	 * @return the content of the file.
	 */
	public String getContent() {
		//TODO
		return this.content;
	}

	/**
	 * Return the owner of this file
	 * @return the owner user of this file.
	 */
	public User getOwner() {
		//TODO
		return this.owner;
	}

	/**
	 * Return the arraylist of accesses which are associated with this file.
	 * @return the list of allowed user of this file.
	 */
	public ArrayList<Access> getAllowedUsers() {
		//TODO
		return this.allowedUsers;
	}


	/**
	 * Adds a new access(user+accesstype pair) to the list of allowed user.
	 * @param newAllowedUser the new access user to add
	 */
	public void addAllowedUser(Access newAllowedUser) {
		//TODO
		if(newAllowedUser == null) throw new IllegalArgumentException();
		this.allowedUsers.add(newAllowedUser);
	}

	//adds a list of the accesses to the list of allowed users.
	/**
	 * Adds all elements of a list of the accesses to the list of allowed users of this file.
	 * @param newAllowedUser list of access to add
	 */
	public void addAllowedUsers(ArrayList<Access> newAllowedUser) {
		//TODO
		if(newAllowedUser == null) throw new IllegalArgumentException();
		for(int i=0; i<newAllowedUser.size();i++){
			if(this.containsAllowedUser(newAllowedUser.get(i).getUser().getName())==false)
			this.allowedUsers.add(newAllowedUser.get(i));
		}
	}


	// returns true if the user name is in allowedUsers.
	// Otherwise return false.
	/**
	 * Check if the user is in the allowed users list.
	 * @param name of the user input
	 * @return true if the name is in list of accesses. Otherwise return false.
	 */
	public boolean containsAllowedUser(String name){
		//TODO
		if (name==null) throw new IllegalArgumentException ();
		for(int i=0; i<this.allowedUsers.size();i++){
			if(this.allowedUsers.get(i).getUser().getName().equalsIgnoreCase(name))
				return true;
		}	
		return false;
	}



	//Removes file for all users.
	//If the user is owner of the file or the admin or the user has 'w' access,
	//then it is removed for everybody. 
	/**
	 * Removes this file from the parent folder if the user is owner/admin 
	 * or has 'w' access. It also removes this file from the owner user's list of files. 
	 * If deleted, then return true otherwise return false.
	 * Removes the file for all users.
	 * @param removeUsr user to remove
	 * @return true if successfully delete a user. Otherwise return false.
	 */
	public boolean removeFile(User removeUsr){
		//TODO	

		//1. Removes the file from the parent folder if the user is owner/admin or has 'w' access

		if(findUser(removeUsr)){
			this.getParent().getFiles().remove(this);//hmm?
			//2. Also removes the file from the owner user's list of files
			this.getOwner().removeFile(this);
			//3. Return true if success
			return true;
		}
		else
			return false;

	}
	/**
	 * This helper method validate if a user is either a owner/admin of the file, or has 'w' access.
	 * @param input the user to validate
	 * @return true if it satisfied the criteria, false if not.
	 */
	private boolean findUser(User input){

		if(input.getName().equals("admin")||input.equals(this.getOwner()))
			return true;
		else {
			for(int i=0; i<this.allowedUsers.size();i++){
				if(this.allowedUsers.get(i).getUser().equals(input)&&this.allowedUsers.get(i).getAccessType()=='w')
					return true;
			}
		}
		return false;
	}

	//returns the string representation of the file.
	@Override
	public String toString() {
		String retString = "";
		retString = name + "." + extension.name() + "\t" + owner.getName() + "\t" ;
		for(Access preAccess : allowedUsers){
			retString = retString + preAccess + " ";
		}
		retString = retString + "\t\"" + content + "\"";
		return retString;
	}

}
