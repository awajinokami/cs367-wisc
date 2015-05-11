///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:           p4
// Files:           SimpleFile.java
//					SimpleFolder.java
//					User.java
//					Access.java
//					Extension.java
//					SimpleFileSystem.java
//					FileSystemMain.java
// Semester:         CS367 Spring 2015
//
// Author:           Si Xie
// Email:            sxie27@wisc.edu
// CS Login:         si
// Lecturer's Name:  Jim Skrentny
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   must fully acknowledge and credit those sources of help.
//                   Instructors and TAs do not have to be credited here,
//                   but tutors, roommates, relatives, strangers, etc do.
//
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
//
// Online sources:   Piazza
//////////////////////////// 80 columns wide //////////////////////////////////
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The application program, FileSystemMain, creates and uses a SimpleFileSystem to represent and process information about file system. The file system information is read from a text file (specified above) and then the program processes user commands.
 * The command line format for the program is:
 * 					java FileSystemMain FileName
 * where FileName is the name of the text file to be processed. See below for 
 * more information about command line arguments.
 * 
 * The main method of the FileSystemMain class does the following:
 * 	1. Check whether exactly one command-line argument is given; if not, display 
 * 		"Usage: java FileSystemMain FileName" and quit.
 * 2. Load the data from the input file and use it to construct a file system. 
 * 		Note: Folder and files are to be added to the structure in the order in 
 * which they appear in the text file. You may assume that input file exists and is readable.
 * 3. Prompt the user with "<username>@CS367$ " where username is replace with 
 * their name, and wait for them to enter a command. Process commands 
 * until the user types x for exit. The default username is "admin" 
 * and the default location is the root folder.
 * 
 * Case Insensitivity: All the commands, usernames, files and folder 
 * names are case insensitive and must be stored in lower case.
 * 
 * admin User: In addition to the users in the input file, you'll have to 
 * create a user named "admin", which won't be in that input file. The admin 
 * user will be the owner of all the folder and files in the input file and will also have 'w' access to all the folders and files created by anybody.
 * 
 * Commands: The command options are listed below. Display "invalid command" 
 * for any other commands and continue displaying the prompt.
 * 
 * 
 *  <p>Bugs: I wasn't able to figure out how to implement the movLoc() in the 
 *  SimpleFileSystem, this class is not successfully construct the database in order to test the system.
 * 
 * @author RickiXie
 *
 */
public class FileSystemMain {

	/* Scanner object on input stream. */
	private static final Scanner scnr = new Scanner(System.in);
	/**
	 * An enumeration of all possible commands for Version control system.
	 */
	private enum Cmd {
		reset, pwd,	ls, u, uinfo, cd, rm, mkdir, mkfile, sh, x
		,un}
	static User admin = new User ("admin");//Initialize the admin user ---don't i already have it in the simplefilesystem
	//private static User admin;
	private static SimpleFileSystem newFileSystem;
	public static void main(String[] args) {

		//TODO

		//1. Load commandLine
		//load users one file at a time
		if(args.length!=1)
			System.out.println("Usage: java FileSystemMain FileName");
		else
			loadFileSystem(args[0]); 

		//2. Load File
		//3. Process Menu
		processMenu(admin);

	}	


	/**	The first line contains the name of the root folder under which all 
	 * the folders are present. 
	 * In above example, it is "courses". Everything will be under root 
	 * folder and no user (including admin) can delete it.
	 * The second line contains the name of all users who can access the file system. 
	 * You may assume that all the users are uniquely identified by their name (i.e., no duplicates).
	 * The third and subsequent lines each contain a file system path for either a folder or file. 
	 * File names are identified by an extension of three characters separated by a dot. 
	 * Folders do not have any extension. 
	 * The files will be followed by content on the same line. 
	 * You may assume that in a particular folder, 
	 * you won't have multiple files or folders with same name.
	 * **/	


	private static void loadFileSystem(String fileName) {
		// TODO Auto-generated method stub
		Scanner scn = null;
		File userFile = new File (fileName);


		//the scanner scan the file to construct the user and file system
		//First create all the users (including admin) and the 
		//directory structure (under root folder) with appropriate access to users. 
		///Then pass these to the SimpleFileSystem when creating its object.

		//All the commands, usernames, files and folder names are case 
		//insensitive and must be stored in lower case. The file content is case sensitive.
		try {
			scn = new Scanner (userFile);///!!!!!HAS TO CHECK IF THE NAME FORMAT IS VALID
			//[STEP 1] Construct the root foler
			//String rootName = scn.nextLine();
			// Note: Admin is owner of the root folder. But we are not adding root to 
			//admin's list of owned folders because admin is the only user to own 
			//root and we are not interested in knowing this in uinfo command.
			SimpleFolder root = new SimpleFolder(scn.nextLine(), "", null, admin);
			//[STEP 2] Create all the users
			String [] userList = scn.nextLine().split(",");
			ArrayList<User> newUsers = new ArrayList<User>();
			for(int i=0; i<userList.length;i++){
				User newUser = new User (userList[i]);
				newUsers.add(newUser);
			}

			//[Step 3] Construct the directory structure under root folder
			//with appropriate access to users.
			newFileSystem = new SimpleFileSystem(root, newUsers);

			//1.Create file and add them to a file list
			//2. Create a folder and add them to a folder list
			//ArrayList<SimpleFolder> folders = new ArrayList<SimpleFolder>();
			SimpleFolder currFolder = root;
			while(scn.hasNextLine()){
				String [] fileArgs = scn.nextLine().split("/");


				for(int i=1; i<fileArgs.length; i++){
					if(fileArgs[i].contains(".")){//if it is a file
						String [] fileContent = fileArgs[i].split(" ");//split file arg with file content
						String [] fileNameP = fileContent[1].split(".");//split file name with file extension
						//construct the file
						SimpleFile newFile = new SimpleFile(fileNameP[0], Extension.valueOf(fileNameP[1]), currFolder.getPath(), fileContent[1], currFolder, admin);
						break;//it is the end of node
					}
					else{
						if(currFolder.getSubFolder(fileArgs[i])==null){
							String newPath ="";
							SimpleFolder newFolder = new SimpleFolder(fileArgs[i], currFolder.getPath(), currFolder,admin);

						}

					}
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("Error:Cannot access file");
		}

	}

	private static String getNewPath(SimpleFolder currFolder){
		//String [] fileNameP = fileContent[1].split(".");//split file name with file extension
		String parentPath ="";
		String newPath ="";
		if(currFolder.getParent()!=null && currFolder.getParent().getPath().length()>0){
			//if parent is not the root nor the parent's path is empty
			parentPath = currFolder.getParent().getPath();
			newPath += parentPath+"/"+currFolder.getParent().getName();
		}
		else{
			newPath += currFolder.getParent().getName();
		}
		return newPath;
	}




	/**
	 * Displays the user prompt for command.  
	 * @param prompt The prompt to be displayed.
	 * @return The user entered command (Max: 4 words).
	 */
	private static String[] prompt(String prompt) {
		System.out.print(prompt);
		String line = scnr.nextLine();
		String [] words = line.trim().split(" ", 4);//some are three
		return words;
	}
	/**
	 * Returns the Cmd equivalent for a string command. 
	 * @param strCmd The string command.
	 * @return The Cmd equivalent.
	 */
	private static Cmd stringToCmd(String strCmd) {
		try {
			return Cmd.valueOf(strCmd.toLowerCase().trim());
		}
		catch (IllegalArgumentException e){
			return Cmd.un;
		}
	}
	/**
	 * Validates if the input has exactly n elements. 
	 * @param input The user input.
	 * @return True, if the input is valid, false otherwise.
	 */
	private static boolean validateInputn(String[] input, int length) {
		if (input.length != length) {
			System.out.println("invalid command");
			return false;
		}
		return true;
	}
	/**
	 * Processes the main menu commands.
	 * 
	 */
	public static void processMenu(User currUser) {

		String mainPrompt = currUser.getName()+"@CS367$ ";
		boolean execute = true;
		while(execute){
			String[] words = prompt(mainPrompt);
			Cmd cmd = stringToCmd(words[0]);{
				switch (cmd) {
				case reset:
					if (validateInputn(words,1)) {
						newFileSystem.reset();
						System.out.println("Reset done");
					}else{
						System.out.println("No Argument Needed");
					}
					break;
				case pwd:
					if (validateInputn(words,1)) {
						System.out.println(newFileSystem.getPWD());
					}else{
						System.out.println("No Argument Needed");
					}
					break;
				case ls:
					if (validateInputn(words,1)) {
						newFileSystem.printAll();

					}else{
						System.out.println("No Argument Needed");
					}
					break;
				case uinfo:
					if (validateInputn(words,1)) {
						if(!newFileSystem.printUsersInfo())
							System.out.println("Insufficient privileges");
						else
							newFileSystem.printUsersInfo();				
					}else{
						System.out.println("No Argument Needed");
					}
					break;
				case x:

					execute = false;
					break;
					/**
					 * Changes the current user to username.
					 *  Also, note that when a user is set, 
					 *  current location points to root. 
					 *  If the user does not exist, print "user <username> does not exist".
					 */
				case u:
					if (validateInputn(words,2)) {
						String username = words[1].trim();
						if(!newFileSystem.setCurrentUser(username)){
							System.out.println("user "+username+ " does not exist");
						}
						else
							newFileSystem.setCurrentUser(username);
					}else{
						System.out.println("One Argument Needed");
					}

					break;
					/**
					 * The argument path can be absolute or relative. 
					 * Absolute path starts with a '/'. It can have a sequence of folders 
					 * ("/courses/cs367/p1"). Relative path can be of two types. 
					 * If it is of the fomat '../', it means go one level up. 
					 * It can have multiple '../' sequences. 
					 * If it starts with folder names, then it must go down to subfolder. 
					 * It should be able to handle a sequence of subfolders (e.g. - 'cs367/h6'). 
					 * If any of these path is invalid, display "Invalid location passed"
					 */
				case cd:
					if (validateInputn(words,2)) {
						//TODO
					}else{
						System.out.println("One Argument Needed");
					}
					break;
					/**
					 * Checks whether the name corresponds to any immediate child file/folder 
					 * in the present working directory. If not, print "Invalid name". 
					 * If it corresponds to some immediate child file/folder, 
					 * then remove that file/folder and print "<name> removed". 
					 * If the user does not privilege to delete the file/folder, print "Insufficient privilege".
					 */
				case rm:
					if (validateInputn(words,2)) {
						//TODO 
					}else{
						System.out.println("One Argument Needed");
					}
					break;
					/**
					 * Create a folder as immediate child of current location 
					 * with name = folderName and owner = current user. 
					 * If the operation is successful display "<folderName> added".
					 */
				case mkdir:
					if (validateInputn(words,2)) {
						//TODO
						
						/**
						 * If you get invalid names in the addfile() or mkdir() methods, please throw IllegalArgumentException. 
						 * For mkdir command, if folder name is invalid, then just reprompt without a prompt.
						 * For mkfile command, if the file name is invalid, print "Invalid filename".
						 */
						try{
							newFileSystem.mkdir(words[1]);
						}
						catch(IllegalArgumentException e){
							//do nothing, go back to prompt
						}						
					}else{
						System.out.println("One Argument Needed");
					}
					break;
					/**
					 * Creates a new file as immediate child of current 
					 * location with name = fileName and content = filecontent. 
					 * It should be able to create file with empty content when 
					 * the filecontent is not given.(Note: filecontent parameter is optional). 
					 * If the fileName is invalid, print "Invalid filename". 
					 * A fileName is invalid if it does not have any extension or 
					 * if its given extension does not match any of the values 
					 * in the Extension file. If the operation is successful, print "<fileName> added"
					 */
				case mkfile:
					if (validateInputn(words,3)) {
						//TODO
						try{
							newFileSystem.addFile(words[1], words[2]);
						}
						catch(IllegalArgumentException e){
							System.out.println("Invalid filename");
						}
					}else{
						System.out.println("One Argument Needed");
					}
					break;
					/**
					 * This command is used to give access of a particular immediate 
					 * child file/folder of current location identified by fname to the 
					 * user identified by username. If the access to a folder is being given, 
					 * then access to all the files are also given with same access type. 
					 * The access given to the user can either be 'r' or 'w'. 
					 * If any other access is being given, display "Invalid permission type". 
					 * If the username is invalid, i.e., does not correspond to any 
					 * existing user, print "Invalid user". 
					 * If the file/folder does not exist display "Invalid file/folder name". 
					 * Also note that the that privilege is granted if and 
					 * only if the current user owns the file/folder. 
					 * If the user doesn't own the file/folder, 
					 * then display "Insufficient privilege". 
					 * If the permission is granted, display "Privilege granted".
					 */
				case sh:
					if (validateInputn(words,4)) {
						 //TODO
	
					}else{
						System.out.println("Three Arguments Needed");
					}
					break;
				default:
					System.out.println("invalid command");
				}
			}


		}
	}



}
