//////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  FileSystemMain.java
// File:             SimpleFolder.java
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
 * The SimpleFolder class represents a single folder in the file system having 
 * a name (as a String), path (as a String), parent (as a SimpleFolder), 
 * owner (as a User), subFolders (as a list of SimpleFolder), 
 * files (as a list of SimpleFile) and accesses (as a list of Access). 
 * The SimpleFolder.java file contains the outline of the SimpleFolder class.
 * @author Ricki Xie
 *
 */
public class SimpleFolder {

	private String name;
	private String path;//absolute path of the folder.
	private SimpleFolder parent;
	private User owner;
	private ArrayList<SimpleFolder> subFolders;
	private ArrayList<SimpleFile> files;
	private ArrayList<Access> allowedUsers;

	/**
	 * Constructs a SimpleFolder with name, path, parent and owner. 
	 * It also initializes the lists appropriately.
	 * @param name the name of the folder
	 * @param path the path of the folder
	 * @param parent the parent folder of the folder
	 * @param owner the owner of the folder
	 */
	public SimpleFolder(String name, String path, SimpleFolder parent, User owner) {
		//TODO
		if(name==null||path==null||owner==null) throw new IllegalArgumentException();//Yup, you are allowed to pass null as parent.(because the parent of the parent is null)
		this.name = name;
		this.parent = parent;

		this.path = path;
		this.owner = owner;


		this.subFolders = new ArrayList<SimpleFolder>();
		this.files = new ArrayList<SimpleFile>();
		this.allowedUsers = new ArrayList<Access>();


		//1. add to parent folder
		if(this.getParent()!=null){//if it is not root
			this.parent.addSubFolder(this);

			//2. If it is not admin

			if(!owner.getName().equalsIgnoreCase("admin")){
				//create a new access for both the admin and owner
				Access nonadminOwner = new Access(owner, 'w');
				//add both to the allowed list
				this.addAllowedUser(nonadminOwner);	
			}
			Access adminOwner = new Access(FileSystemMain.admin, 'w');
			if(!this.containsAllowedUser("admin")){
				this.addAllowedUser(adminOwner);
			}
			//add the folder to owner's folder list
			this.owner.addFolder(this);




			// If the admin user is to become the owner of file/folder, 
			//then it is passed in constructor as owner. 
			//If you need to add it in allowedUsers list, you use the addAllowedUser() method. 
			//If you need to check whether the allowedUsers list contains the user admin, 
			//you can compare the name of the users in the allowedUsers list with "admin" string.


		}
	}

	//checks if user - "uname" is allowed to access this folder or not. 
	//If yes, return true, otherwise false.
	/**
	 * Checks if user identified by uname is in list of accesses of this folder.
	 * If yes, return true, otherwise false.
	 * 
	 * @param name of the user
	 * @return yes if the user is in the list
	 */
	public boolean containsAllowedUser(String uname){
		//TODO
		if(uname==null) throw new IllegalArgumentException();
		for(int i =0; i<this.allowedUsers.size();i++){
			if(this.allowedUsers.get(i).getUser().getName().equalsIgnoreCase(uname))
				return true;
		}
		return false;
	}

	//checks if this folder contains the child folder identified by 'fname'.
	// If it does contain then it returns the folder otherwise returns null.
	/**
	 * Checks if this folder contains the child folder identified by 'fname'. 
	 * If it contains subfolder, then return subfolder, otherwise, return null
	 * @param fname child folder name
	 * @return the folder if the path contains the child folder, else return null
	 */
	public SimpleFolder getSubFolder(String fname){
		//TODO
		if(fname==null) throw new IllegalArgumentException();
		for(int i=0; i<this.subFolders.size(); i++){
			if(this.subFolders.get(i).getName().equalsIgnoreCase(fname))
				return this.subFolders.get(i);
		}
		return null;
	}


	//checks if this folder contains the child file identified by "fname".
	// If it does contain, return File otherwise null.
	/**
	 * Checks if this folder contains the child file identified by "fname". 
	 * If it contains child file, then return child file, otherwise, return null.
	 * @param fname file name to exam
	 * @return the file if the current folder contains the file, else return null
	 */
	public SimpleFile getFile(String fname){
		//TODO

		if(fname==null)  throw new IllegalArgumentException();
		Object [] result = checkFileName(fname);
		if(result[0]==null||result[1]==null) return null;
		else{
			for(int i = 0; i<this.getFiles().size();i++){
				if(this.files.get(i).getName().equals(result[0])&&
						this.files.get(i).getExtension().equals(result[1]))
					return this.files.get(i);
					
			}
			return null;
		}
	}

	/**
	 * This helper method validate if the input fname has valid format.
	 * @param fname the fname to exam
	 * @return true if the fname has valid file name and extension, false if otherwise.
	 */
	private Object[] checkFileName(String fname){
		if(fname==null)  throw new IllegalArgumentException();

		String [] completefile = fname.split("\\.");
		String filename;
		Extension fileType;
		if(completefile.length==2){
			filename=completefile[0].toLowerCase();
			switch(completefile[1]){
			case "txt":
				fileType = Extension.txt;
				break;
			case "doc":
				fileType = Extension.doc;
				break;
			case "rtf":
				fileType = Extension.rtf;
				break;
			default:
				fileType = null;
				break;
			}		
			return new Object[]{filename, fileType};
		}
		else

			return new Object[]{null,null};		
	}

	//returns the owner of the folder.
	/**
	 * Returns the owner of the folder.
	 * @return  the owner of the folder.
	 */
	public User getOwner() {
		//TODO
		return this.owner;
	}

	//returns the name of the folder.
	/**
	 * Returns the name of the folder.
	 * @return the folder name
	 */
	public String getName() {
		//TODO
		return this.name;
	}

	//returns the path of this folder.
	/**
	 * Returns the path of the folder.
	 * @return the folder path
	 */
	public String getPath() {
		//TODO
		return this.path;
	}

	//returns the Parent folder of this folder.
	/**
	 * Returns the parent of the folder.
	 * @return the parent folder
	 */
	public SimpleFolder getParent() {
		//TODO
		return this.parent;
	}

	//returns the list of all folders contained in this folder.
	/**
	 * Returns the list of all folders contained in this folder.
	 * @return the list of sub-folder/child folder
	 */
	public ArrayList<SimpleFolder> getSubFolders() {
		//TODO
		return this.subFolders;
	}


	//adds a folder into this folder.
	/**
	 * Adds a subfolder into this folder.
	 * @param subFolder the folder to add
	 */
	public void addSubFolder(SimpleFolder subFolder) {
		//TODO
		if(subFolder==null) throw new IllegalArgumentException();
		this.subFolders.add(subFolder);

	}

	//returns the list of files contained in this folder.
	/**
	 * Returns the list of files contained in this folder.
	 * @return the list of file in the folder
	 */
	public ArrayList<SimpleFile> getFiles() {
		//TODO
		return this.files;
	}

	//add the file to the list of files contained in this folder.
	/**
	 * Add the file to the list of files contained in this folder.
	 * @param file the file to add into the folder
	 */
	public void addFile(SimpleFile file) {
		//TODO
		if(file==null) throw new IllegalArgumentException();
		this.files.add(file);
	}

	//returns the list of allowed user to this folder.
	/**
	 * Returns the list of accesses associated with this folder.
	 * @return the list of access
	 */
	public ArrayList<Access> getAllowedUsers() {
		//TODO
		return this.allowedUsers;
	}

	//adds another user to the list of allowed user of this folder.
	/**
	 * Adds another access to the list of accesses of this folder.
	 * @param allowedUser a new user to add
	 */
	public void addAllowedUser(Access allowedUser) {
		//TODO
		if(allowedUser==null) throw new IllegalArgumentException ();
		this.allowedUsers.add(allowedUser);
	}

	//adds a list of allowed user to this folder.
	/**
	 * Adds all elements of a list of access to the list of accesses of this folder.
	 * @param allowedUser a list of allow users
	 */
	public void addAllowedUser(ArrayList<Access> allowedUser) {
		//TODO
		if(allowedUser ==null) throw new IllegalArgumentException();
		for(int i=0; i<allowedUser.size();i++){
			if(this.containsAllowedUser(allowedUser.get(i).getUser().getName())==false)
				this.allowedUsers.add(allowedUser.get(i));
		}
	}

	//If the user is owner of this folder or the user is admin or the user has 'w' privilege
	//, then delete this folder along with all its content.
	/**
	 * If the user is owner of this folder or the user is admin or the user 
	 * has 'w' privilege, then delete this folder from its parent folder. 
	 * If the folder is deleted, then it and all its content must be deleted 
	 * from their corresponding owner's list of folders and files as well. 
	 * If deleted, return true, otherwise, return false.
	 * @param removeUsr
	 * @return
	 */
	public boolean removeFolder(User removeUsr){
		//TODO
		if (this.getPath().isEmpty()) return false; //root folder will not be remove
		//1. Removes the file from the parent folder if the user is owner/admin or has 'w' access
		else if(findUser(removeUsr)){
			//1. Delete the folder from its parent folder
			this.getParent().subFolders.remove(this);//what if the folder doesn't have parent?
			//2. remove its content from their owner's list of folders and files
			for(int i=0; i<this.files.size();i++){//list of file
				this.owner.removeFile(this.files.get(i));
			}
			//3. traverse through subfolder and delete its content from their list of owners
			for(int j=0; j<this.getSubFolders().size();j++){
				removeFolder(removeUsr, this.getSubFolders().get(j));
			}
			this.getOwner().removeFolder(this);//finally remove this
			return true;
		}
		else
			return false;
		//Now, you have to remove the deleted folder and all its content from their list of owners. 
		//So, here you can do BFS to remove the files and folders from their owner's list.
	}
	private void removeFolder(User removeUsr,SimpleFolder root){
		for(int i=0; i<root.getFiles().size();i++){
			root.getFiles().get(i).getOwner().removeFile(root.getFiles().get(i));
		}
		//if there is no subfolder
		if(root.getSubFolders().size()==0){
			root.getOwner().removeFolder(root);
		}
		else{//if there is still subfolder
			for(int j=0; j<root.getSubFolders().size();j++){
				removeFolder(removeUsr,root.getSubFolders().get(j));
			}
			return;//finish
		}
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
	//returns the string representation of the Folder object.
	@Override
	public String toString() {
		String retString = "";
		retString = path + "/" + name + "\t" + owner.getName() + "\t";
		for(Access preAccess: allowedUsers){
			retString = retString + preAccess + " ";
		}

		retString = retString + "\nFILES:\n";

		for(int i=0;i<files.size();i++){
			retString = retString + files.get(i);
			if(i != files.size()-1)
				retString = retString + "\n";

		}				
		return retString;
	}


}
