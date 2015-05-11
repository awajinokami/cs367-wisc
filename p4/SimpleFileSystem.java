//////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  FileSystemMain.java
// File:             SimpleFileSystem.java
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The SimpleFileSystem class stores the root of file system along with the 
 * list of users. It also has a variables current user and current location. 
 * The SimpleFileSystem.java file contains the outline of the SimpleFileSystem class.
 * 
 *  <p>Bugs: movLoc(), addUser(), addFile(), mkdir() was incomplete.
 * @author Ricki Xie
 *
 */
public class SimpleFileSystem {

	private SimpleFolder root;
	private ArrayList<User> users;
	private SimpleFolder currLoc;
	private User currUser;
	//constructor
	/**
	 * Initializes the class variables appropriately. 
	 * (Sets the current location and root to _root, users to _users and current user to admin.)
	 * @param _root
	 * @param _users
	 */
	public SimpleFileSystem(SimpleFolder _root, ArrayList<User> _users) {
		//TODO
		if(_root==null||_users==null) throw new IllegalArgumentException();
		this.root = _root;
		this.users = _users;
		this.currLoc = _root;
		this.currUser = FileSystemMain.admin;
	}

	// resets current user to admin and current location to root
	/**
	 * Resets current location to root and current user to admin.
	 */
	public void reset(){
		//TODO
		this.currLoc=this.root;
		this.currUser = FileSystemMain.admin;
	}

	//gets currUser.
	/**
	 * Return the current user
	 * @return the current user
	 */
	public User getCurrUser() {
		//TODO
		return this.currUser;
	}

	//sets the current user to the user with name passed in the argument.
	/**
	 * Sets the current user to the user with name passed in the argument. 
	 * If no such user found, return false, otherwise return true. 
	 * Also, note that when a user is set, current location points to root.
	 * @param name
	 * @return
	 */
	public boolean setCurrentUser(String name){
		//TODO
		if(name==null) throw new IllegalArgumentException();
		if(this.containsUser(name)!=null){//if user is found
			this.currUser = this.containsUser(name);
			this.currLoc=this.root;
			return true;
		}
		else //no such user found
			return false;
	}

	//checks if the user is contained in the existing users list or not.
	/**
	 * Returns user if it exists, otherwise return null.
	 * @param name the name of the user
	 * @return the user the User in the list
	 */
	public User containsUser(String name){
		//TODO
		if(name==null) throw new IllegalArgumentException();
		for(int i =0; i<this.users.size();i++){
			if(this.users.get(i).getName().equals(name))
				return this.users.get(i);
		}
		return null;
	}

	//checks whether curr location contains any file/folder with name name = fname
	/**
	 * Checks whether current location contains any 
	 * file/folder with name = fname. 
	 * If yes, return true, otherwise return false.
	 * @param fname
	 * @return
	 */
	public boolean containsFileFolder(String fname){
		//TODO

		if(this.currLoc.getFile(fname)!=null||this.currLoc.getSubFolder(fname)!=null)
			return true;
		return false;
	}




	private Object [] checkPathFormat(String argument){

		String validaboslutePath = "(\\/[a-z](\\w)*\\/)*[a-z](\\w)*";//e.g: /root/cs367/h1
		String validrelativePath = "(..\\/)*";//e.g: ../../
		String validrelativeFolder = "([a-z](\\w)*\\/)*[a-z](\\w)*";	//e.g: cs367/h1
		if(argument.contains("/")){
			String [] argumentDetail = argument.split("/");
			String [] currLocDetail = this.currLoc.getPath().split("/");
			if(argument.matches(validaboslutePath)&&//if the format is correct
					argumentDetail[0].equalsIgnoreCase(this.root.getName())){
				//check path exist
				int counter = 1;//skip the root since it is checked
				int length = argumentDetail.length;
				SimpleFolder tmp = null;
				for(int i=0; i<argumentDetail.length;i++){
					while(counter <=length){
						tmp = this.root.getSubFolder(argumentDetail[i]);
						while(tmp!=null){
							i++;
							tmp = tmp.getSubFolder(argumentDetail[i]);
						}
					}
					if(i<counter){
						return new Object [] {-1,0, null};
					}	
				}		
				return new Object[] {1, argumentDetail.length, tmp};
			}
			else if(argument.matches(validrelativePath)){//if it is a relative path
				//http://stackoverflow.com/questions/767759/occurrences-of-substring-in-a-string
				Pattern p = Pattern.compile("../");
				Matcher m = p.matcher(argument);
				int count = 0;
				while (m.find()){
					count +=1;
				}
				if(currLocDetail.length==count){

					//int counter = 1;//skip the root since it is checked
					//int length = argumentDetail.length;
					SimpleFolder tmp = null;					
					for(int i=0; i<count;i++){
						tmp = this.currLoc.getParent();
					}

					return new Object[] {2, count, tmp};				
				}
				else
					return new Object [] {-1,0, null};
			}
			else if(argument.matches(validrelativeFolder)){//if it is a relative path with the folder
				if(this.currLoc.getSubFolder(argumentDetail[0])!=null){
					//if the subfolder is under the currLoc
					int counter = 1;//skip the currLoc since it is checked
					int length = argumentDetail.length;
					SimpleFolder tmp = null;	
					for(int i=0; i<argumentDetail.length;i++){
						while(counter <=length){
							tmp = this.currLoc.getSubFolder(argumentDetail[i]);
							while(tmp!=null){
								i++;
								tmp = tmp.getSubFolder(argumentDetail[i]);
							}
						}
						if(i<counter){
							return new Object [] {-1,0, null};
						}

					}

					return new Object[] {3, argumentDetail.length, tmp};

				}
				else
					return new Object [] {-1,0, null};
			}
			else
				return new Object [] {-1,0, null};
		}
		else
			return new Object [] {-1,0, null};
		//return null;
	}
	//?!!???????????
	//changes the current location. If successful returns true, false otherwise.
	/**
	 * Changes the current location as per the argument. 
	 * Argument can be absolute/relative path. 
	 * If successful, return true. 
	 * Otherwise, return false.
	 * @param argument
	 * @return
	 */
	public boolean moveLoc(String argument){
		//TODO
		if(argument==null) throw new IllegalArgumentException();

		Object [] result = checkPathFormat(argument);
		if(result[0].equals(-1)) return false;
		SimpleFolder tmp = (SimpleFolder)result[2];
		
		ArrayList<Access> tmpAccess = tmp.getAllowedUsers();
		
		for(int i=0; i<tmpAccess.size();i++){
			if(!tmpAccess.get(i).getUser().getName().equalsIgnoreCase(this.currUser.getName())){
				return false;
			}
		}
		this.currLoc = tmp;
		return true;
			//If you go up in the hierarchy using relative path, then you don't have folder name in path.
		/**
		 * The argument path can be absolute or relative. 
		 * Absolute path starts with a '/'. It can have a sequence of folders 
		 * ("/courses/cs367/p1"). Relative path can be of two types. 
		 * If it is of the format '../', it means go one level up. 
		 * It can have multiple '../' sequences. 
		 * If it starts with folder names, then it must go down to subfolder. 
		 * It should be able to handle a sequence of subfolders (e.g. - 'cs367/h6'). 
		 * If any of these path is invalid, display "Invalid location passed"
		 */
		//STEP 1: Check the format
		//TYPE 1: Absolute
		//TYPE 2: Relative
		// /root/cs367/h1
		// ../../

		//STEP 2: Check if the location exist


		//STEP 3: Check if the user is in the folder's allowedAccess


		//(1) format correct, names correct, but location doesn't exist
		//(2) first folder of absolute path isn't root
		//(3) when format is correct, name is correct, location does exists but 
		//user does not have access 'r' or 'w' to the the location.


		//The valid folder name:
		//i) starts with an alphabet and
		//ii) only contains alphabets and digits.

		//Now, the location argument in the moveLoc() method might have correct folder names but:
		//1) the folder in location argument might not exist.
		//2) the user might not have access to folders in the location argument.

		//3) In case of location argument being absolute path, 
		//if the first folder of absolute path is not root, then location argument is also invalid.

		//return false;
	}


	//returns the currentlocation.path + currentlocation.name.
	/**
	 * Returns the current location path concatenated with its name appropriately.
	 * @return
	 */
	public String getPWD(){
		return ((currLoc.getPath().isEmpty()?"":(currLoc.getPath()+"/"))+currLoc.getName());
	}//return of getPWD method


	//deletes the folder/file identified by the 'name'
	/**
	 * Deletes the immediate child folder/file in the current location 
	 * identified by the 'name'. If successful, return true, otherwise return false.
	 * @param name the name of the file or folder to remove
	 * @return true if successful delete
	 */
	public boolean remove(String name){
		//TODO
		if(name==null) throw new IllegalArgumentException();
		if(this.currLoc.getFile(name)!=null){
			if(this.currLoc.getFile(name).removeFile(this.currUser)==true)
				return true;
			else return false;
		}
		else if(this.currLoc.getSubFolder(name)!=null){
			if(this.currLoc.getSubFolder(name).removeFolder(this.currUser)==true)
				return true;
			else return false;
		}
		else
			return false;
	}


	//Gives the access 'permission' of the file/folder fname to the user if the 
	//current user is the owner of the fname. If succeed, return true, otherwise false.
	/**
	 * Gives the access 'permission' of the file/folder fname to the user 
	 * if the current user is the owner of the fname. 
	 * If permission to folder is being given, then the same permission 
	 * is given to all the immediate files in the folder. 
	 * If successful, return true, otherwise false.
	 * @param fname the file/folder name to add permission to
	 * @param username the user with the permission
	 * @param permission the type of acecss type that the user has
	 * @return true if successfully added
	 */
	public boolean addUser(String fname, String username, char permission){
		//TODO
		//addUser() method of SimpleFileSystem class can only give access to the 
		//files or folders which are present as immediate children of current folder. 
		//When we share a child file under current folder, only that file is shared. 
		//When we share a child folder under current folder, the files inside that 
		//child folder are also shared, however, any folders inside the child folder are not shared.
		if(fname==null||username==null||!
				(permission=='w'||permission=='r')//if the permission format is incorrect
				||!this.containsFileFolder(fname)//if the fname is not in the system
				||this.containsUser(username)==null)//if the user is not in the system
			throw new IllegalArgumentException();

		//1. If fname represent a file
		if(this.currLoc.getFile(fname)!=null){
			//1. if current user is the owner of the fname
			if(this.currLoc.getFile(fname).getOwner().equals(this.currUser)){
				Access newAccess = new Access (this.containsUser(username), permission);
				this.currLoc.getFile(fname).addAllowedUser(newAccess);
				return true;
			}
			else return false;//else no permission granted
		}//2. If fname represent a folder
		else if(this.currLoc.getSubFolder(fname)!=null){
			//1. if current user is the owner of the fname
			if(this.currLoc.getSubFolder(fname).getOwner().equals(this.currUser)){
				Access newAccess = new Access (this.containsUser(username), permission);
				this.currLoc.getSubFolder(fname).addAllowedUser(newAccess);
				//also grant access to all the files within the system.
				for(int i=0; i<this.currLoc.getSubFolder(fname).getFiles().size();i++){
					this.currLoc.getSubFolder(fname).getFiles().get(i).addAllowedUser(newAccess);
				}
				return true;
			}
			else return false;//else no permission granted
		}
		return false;
	}


	//displays the user info in the specified format.
	/**
	 * Displays the user info if the current user is admin. 
	 * Returns true if successful, otherwise false.
	 * @return the user information
	 */
	public boolean printUsersInfo(){
		//TODO
		if(this.getCurrUser().getName().equals("admin")){
			System.out.println(this.getCurrUser().toString());
			return true;
		}
		return false;
	}



	//???????
	//makes a new folder under the current folder with owner = current user.
	/**
	 * Makes a new folder under the current folder with owner = current user.
	 * @param name the name of the folder
	 */
	//6) You don't need to check for the user's permission when creating a folder or file in the current location.
	public void mkdir(String name){
		//TODO
		//String name, String path, SimpleFolder parent, User owner
		String validFolder = "[a-z](\\w)*";
		if(name==null||!name.matches(validFolder)) throw new IllegalArgumentException();

		String parentPath = this.currLoc.getParent().getPath();
		String newPath="";

		if(parentPath.length()>0){//If the parent's path is non empty, then it should be parent's path + "/" + parent's name.
			newPath += parentPath+"/"+this.currLoc.getParent().getName();
		}
		else{//If the parent's path is empty, then it should be parent's name only.
			newPath += this.currLoc.getParent().getName();
		}

		SimpleFolder newFolder = new SimpleFolder(name, newPath, this.currLoc, this.currUser);
		this.currLoc.addSubFolder(newFolder);
	}

	//makes a new file under the current folder with owner = current user.
	//1) Filename contains both the name and extension at all places other than the 'name' 
	//field in SimpleFile class and the 'name' parameter in constructor of SimpleFile 
	//class where it just has name without extension. Also, the getName() method of SimpleFile returns filename without extension.
	//6) You don't need to check for the user's permission when creating a folder or file in the current location.
	/**
	 * Makes a new file with name=filename and content=filecontent 
	 * under the current folder with owner = current user.
	 * @param filename filename to add
	 * @param fileContent filecontent to add
	 */
	public void addFile(String filename, String fileContent){
		//TODO
		//String name, Extension extension, String path, String content, SimpleFolder parent, User owner
		if(filename==null||fileContent==null)  throw new IllegalArgumentException();
		Object [] result = checkFileName(filename);
		//String fileN
		if(result[0]==null||result[1]==null) throw new IllegalArgumentException();
		String fileName = (String)result[0];
		Extension fileExtension = (Extension) result[1];

		String parentPath = this.currLoc.getParent().getPath();
		String newPath="";

		if(parentPath==""){//If the parent's path is non empty, then it should be parent's path + "/" + parent's name.
			newPath = parentPath+"/"+this.currLoc.getParent().getName();
		}
		else{//If the parent's path is empty, then it should be parent's name only.
			newPath = this.currLoc.getParent().getName();
		}

		//Step 1: Create a new file and set the owner = current user
		SimpleFile newFile = new SimpleFile(fileName, fileExtension, fileContent, newPath, currLoc, currUser);
		//this.currLoc.addFile(newFile);
		//Step 2: Set the owner = current user
		if(!currUser.getName().equals("admin")){
			newFile.addAllowedUser(new Access(FileSystemMain.admin, 'w'));
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


	//prints all the folders and files under the current user for which user has access.
	/**
	 * Prints all the folders and files under the current location for 
	 * which current user has access. It has been implemented for you.
	 */
	public void printAll(){
		for(SimpleFile f : currLoc.getFiles()){
			if(f.containsAllowedUser(currUser.getName()))
			{
				System.out.print(f.getName() + "." + f.getExtension().toString() + " : " + f.getOwner().getName() + " : ");
				for(int i =0; i<f.getAllowedUsers().size(); i++){
					Access a = f.getAllowedUsers().get(i);
					System.out.print("("+a.getUser().getName() + "," + a.getAccessType() + ")");
					if(i<f.getAllowedUsers().size()-1){
						System.out.print(",");
					}
				}
				System.out.println();
			}
		}
		for(SimpleFolder f: currLoc.getSubFolders()){
			if(f.containsAllowedUser(currUser.getName()))
			{
				System.out.print(f.getName() + " : " + f.getOwner().getName() + " : ");
				for(int i =0; i<f.getAllowedUsers().size(); i++){
					Access a = f.getAllowedUsers().get(i);
					System.out.print("("+a.getUser().getName() + "," + a.getAccessType() + ")");
					if(i<f.getAllowedUsers().size()-1){
						System.out.print(",");
					}
				}
				System.out.println();
			}
		}


	}

}
