///////////////////////////////////////////////////////////////////////////////
//Title:            CS 367 Programming Assignment 3
//Main Class File:  "VersionControlApp.java"
//Files:            "SimpleQueue.java" 
//					"SimpleStack.java"					
//					"User.java"
//					"Repo.java"
//					"ChangeSet.java"
//Semester:         CS367 Spring 2015
//
//Author:           Ricki (Si) Xie
//Email:            sxie27@wisc.edu
//CS Login:         si
//Lecturer's Name:  Jim Skrentny
//
////////////////////STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//					must fully acknowledge and credit those sources of help.
//					Instructors and TAs do not have to be credited here,
//					but tutors, roommates, relatives, strangers, etc do.
//
//Persons:          Piazza
//
//Online sources:  	N/A 
////////////////////////////80 columns wide //////////////////////////////////
import java.util.Scanner;

/**
 * Version control application. Implements the command line utility
 * for Version control.
 * @author rickixie
 *
 */
public class VersionControlApp {

	/* Scanner object on input stream. */
	private static final Scanner scnr = new Scanner(System.in);

	/**
	 * An enumeration of all possible commands for Version control system.
	 */
	private enum Cmd {
		AU, DU,	LI, QU, AR, DR, OR, LR, LO, SU, CO, CI, RC, VH, RE, LD, AD,
		ED, DD, VD, HE, UN
	}

	/**
	 * Displays the main menu help. 
	 */
	private static void displayMainMenu() {
		System.out.println("\t Main Menu Help \n" 
				+ "====================================\n"
				+ "au <username> : Registers as a new user \n"
				+ "du <username> : De-registers a existing user \n"
				+ "li <username> : To login \n"
				+ "qu : To exit \n"
				+"====================================\n");
	}

	/**
	 * Displays the user menu help. 
	 */
	private static void displayUserMenu() {
		System.out.println("\t User Menu Help \n" 
				+ "====================================\n"
				+ "ar <reponame> : To add a new repo \n"
				+ "dr <reponame> : To delete a repo \n"
				+ "or <reponame> : To open repo \n"
				+ "lr : To list repo \n"
				+ "lo : To logout \n"
				+ "====================================\n");
	}

	/**
	 * Displays the repo menu help. 
	 */
	private static void displayRepoMenu() {
		System.out.println("\t Repo Menu Help \n" 
				+ "====================================\n"
				+ "su <username> : To subcribe users to repo \n"
				+ "ci: To check in changes \n"
				+ "co: To check out changes \n"
				+ "rc: To review change \n"
				+ "vh: To get revision history \n"
				+ "re: To revert to previous version \n"
				+ "ld : To list documents \n"
				+ "ed <docname>: To edit doc \n"
				+ "ad <docname>: To add doc \n"
				+ "dd <docname>: To delete doc \n"
				+ "vd <docname>: To view doc \n"
				+ "qu : To quit \n" 
				+ "====================================\n");
	}

	/**
	 * Displays the user prompt for command.  
	 * @param prompt The prompt to be displayed.
	 * @return The user entered command (Max: 2 words).
	 */
	private static String[] prompt(String prompt) {
		System.out.print(prompt);
		String line = scnr.nextLine();
		String []words = line.trim().split(" ", 2);
		return words;
	}

	/**
	 * Displays the prompt for file content.  
	 * @param prompt The prompt to be displayed.
	 * @return The user entered content.
	 */
	private static String promptFileContent(String prompt) {
		System.out.println(prompt);
		String line = null;
		String content = "";
		while (!(line = scnr.nextLine()).equals("q")) {
			content += line + "\n";
		}
		return content;
	}

	/**
	 * Validates if the input has exactly 2 elements. 
	 * @param input The user input.
	 * @return True, if the input is valid, false otherwise.
	 */
	private static boolean validateInput2(String[] input) {
		if (input.length != 2) {
			System.out.println(ErrorType.UNKNOWN_COMMAND);
			return false;
		}
		return true;
	}

	/**
	 * Validates if the input has exactly 1 element. 
	 * @param input The user input.
	 * @return True, if the input is valid, false otherwise.
	 */
	private static boolean validateInput1(String[] input) {
		if (input.length != 1) {
			System.out.println(ErrorType.UNKNOWN_COMMAND);
			return false;
		}
		return true;
	}

	/**
	 * Returns the Cmd equivalent for a string command. 
	 * @param strCmd The string command.
	 * @return The Cmd equivalent.
	 */
	private static Cmd stringToCmd(String strCmd) {
		try {
			return Cmd.valueOf(strCmd.toUpperCase().trim());
		}
		catch (IllegalArgumentException e){
			return Cmd.UN;
		}
	}

	/**
	 * Handles add user. Checks if a user with name "username" already exists; 
	 * if exists the user is not registered. 
	 * @param username The user name.
	 * @return USER_ALREADY_EXISTS if the user already exists, SUCCESS otherwise.
	 */
	private static ErrorType handleAddUser(String username) {
		if (VersionControlDb.addUser(username) != null) {
			return ErrorType.SUCCESS;
		}
		else {
			return ErrorType.USERNAME_ALREADY_EXISTS;
		}
	}

	/**
	 * Handles delete user. Checks if a user with name "username" exists; if 
	 * does not exist nothing is done. 
	 * @param username The user name.
	 * @return USER_NOT_FOUND if the user does not exists, SUCCESS otherwise.
	 */
	private static ErrorType handleDelUser(String username) {
		User user = VersionControlDb.findUser(username); 
		if (user == null) {
			return ErrorType.USER_NOT_FOUND;
		}
		else {
			VersionControlDb.delUser(user);
			return ErrorType.SUCCESS;
		}
	}

	/**
	 * Handles a user login. Checks if a user with name "username" exists; 
	 * if does not exist nothing is done; else the user is taken to the 
	 * user menu. 
	 * @param username The user name.
	 * @return USER_NOT_FOUND if the user does not exists, SUCCESS otherwise.
	 */
	private static ErrorType handleLogin(String username) {
		User currUser = VersionControlDb.findUser(username);
		if (currUser != null) {
			System.out.println(ErrorType.SUCCESS);
			processUserMenu(currUser);
			return ErrorType.SUCCESS;
		}
		else {
			return ErrorType.USER_NOT_FOUND;
		}
	}

	/**
	 * Processes the main menu commands.
	 * 
	 */
	public static void processMainMenu() {

		String mainPrompt = "[anon@root]: ";
		boolean execute = true;

		while (execute) {
			String[] words = prompt(mainPrompt);
			Cmd cmd = stringToCmd(words[0]);

			switch (cmd) {
			case AU:
				if (validateInput2(words)) {
					System.out.println(handleAddUser(words[1].trim()));
				}
				break;
			case DU:
				if (validateInput2(words)) {
					System.out.println(handleDelUser(words[1].trim())); 
				}
				break;
			case LI:
				if (validateInput2(words)) {
					System.out.println(handleLogin(words[1].trim()));
				}
				break;
			case HE:
				if (validateInput1(words)) {
					displayMainMenu();
				}
				break;
			case QU:
				if (validateInput1(words)) {
					execute = false;
					//System.out.println("Quitting the simulation.");
				}
				break;
			default:
				System.out.println(ErrorType.UNKNOWN_COMMAND);
			}

		}
	}

	/**
	 * Processes the user menu commands for a logged in user.
	 * @param logInUser The logged in user.
	 * @throws IllegalArgumentException in case any argument is null.
	 */
	public static void processUserMenu(User logInUser) {

		if (logInUser == null) {
			throw new IllegalArgumentException();
		}

		String userPrompt = "[" + logInUser.getName() + "@root" + "]: ";
		boolean execute = true;

		while (execute) {

			String[] words = prompt(userPrompt);
			Cmd cmd = stringToCmd(words[0]);

			switch (cmd) {
			/*
			 * Adds a new repository with the logged-in user as its admin to 
			 * the database and subscribes the logged-in user to the repository, 
			 * if there exists no repository with reponame. 
			 * Prints REPONAME_ALREADY_EXISTS if a repository with 
			 * reponame exists, else prints SUCCESS.
			 */
			case AR:
				if (validateInput2(words)) {
					// TODO: Implement logic to handle AR.
					if(VersionControlDb.findRepo(words[1])!=null)
						System.out.println(ErrorType.REPONAME_ALREADY_EXISTS);
					else{
						VersionControlDb.addRepo(words[1], logInUser);
						logInUser.subscribeRepo(words[1]);
						System.out.println(ErrorType.SUCCESS);
					}			
				}
				break;
				/*
				 * Deletes an existing repository from the database 
				 * if the logged-in user is the admin for the repository. 
				 * Prints REPO_NOT_FOUND if repository with reponame doesn't exists, 
				 * else if the logged-in user is not the admin for the repository prints ACCESS_DENIED, 
				 * else prints SUCCESS.
				 */
			case DR:
				if (validateInput2(words)) {
					// TODO: Implement logic to handle DR.
					if(VersionControlDb.findRepo(words[1])==null)
						System.out.println(ErrorType.REPO_NOT_FOUND);
					else if(logInUser!=VersionControlDb.findRepo(words[1]).getAdmin()){
						System.out.println(ErrorType.ACCESS_DENIED);
					}
					else{
						VersionControlDb.delRepo(VersionControlDb.findRepo(words[1]));
						System.out.println(ErrorType.SUCCESS);
					}
				}
				break;
				/*
				 * Lists the logged-in user's subscribed repositories.
				 */
			case LR:
				if (validateInput1(words)) {
					// TODO: Implement logic to handle LR.
					System.out.println(logInUser.toString());
				}
				break;
				/*
				 * Opens an existing repository if the logged-in user is subscribed 
				 * to the repository. Opening a repository involves checking out a 
				 * local working copy for the repository <b>if it doesn't already exist <b> 
				 * and taking the logged-in user to the Repo Menu prompt (described later) 
				 * so that the user can make changes to the repository and/or its working copy. 
				 * Prints REPO_NOT_FOUND if the repository with reponame doesn't exists, 
				 * else if the logged-in user is not subscribed to the repository prints REPO_NOT_SUBSCRIBED, 
				 * else prints SUCCESS.
				 * or<repoName>
				 */
			case OR: 
				if (validateInput2(words)) {
					// TODO: Implement logic to handle OR.
					if(VersionControlDb.findRepo(words[1])==null)
						System.out.println(ErrorType.REPO_NOT_FOUND);
					else if(!VersionControlDb.findUser(logInUser.getName()).getAllSubRepos().contains(words[1]))
						System.out.println(ErrorType.REPO_NOT_SUBSCRIBED);
					else{
						if(logInUser.getWorkingCopy(words[1])==null){
								logInUser.checkOut(words[1]);
						}
						System.out.println(ErrorType.SUCCESS);
						processRepoMenu(logInUser, words[1]);
						
					}			
				}
				break;
				/*
				 * Logs out the logged-in user and goes back to the main menu prompt.
				 */
			case LO:
				if (validateInput1(words)) {
					execute = false;
				}
				break;
				/*
				 * Prints the user menu help.
				 */
			case HE:
				if (validateInput1(words)) {
					displayUserMenu();
				}
				break;
			default:
				System.out.println(ErrorType.UNKNOWN_COMMAND);
			}

		}
	}

	/**
	 * Process the repo menu commands for a logged in user and current
	 * working repository.
	 * @param logInUser The logged in user. 
	 * @param currRepo The current working repo.
	 * @throws IllegalArgumentException in case any argument is null.
	 */
	public static void processRepoMenu(User logInUser, String currRepo) {

		if (logInUser  == null || currRepo == null) {
			throw new IllegalArgumentException();
		}

		String repoPrompt = "["+ logInUser.getName() + "@" + currRepo + "]: ";
		boolean execute = true;

		while (execute) {

			String[] words = prompt(repoPrompt);
			Cmd cmd = stringToCmd(words[0]);

			switch (cmd) {
			/*
			 * Subscribes an existing user (with <username> ) to the current 
			 * repository if the logged-in user is the admin of the current repository. 
			 * Prints USER_NOT_FOUND if the no user with <username> is found, 
			 * else if the logged-in user is not the admin for the current repository prints ACCESS_DENIED, 
			 * else prints SUCCESS.
			 * su <username>
			 */
			case SU:
				if (validateInput2(words)) {
					// TODO: Implement logic to handle SU.
					if(VersionControlDb.findUser(words[1])==null)
						System.out.println(ErrorType.USER_NOT_FOUND);
					else if(logInUser!=VersionControlDb.findRepo(currRepo).getAdmin()){
						System.out.println(ErrorType.ACCESS_DENIED);
					}
					else {
						VersionControlDb.findUser(words[1]).subscribeRepo(currRepo);
						System.out.println(ErrorType.SUCCESS);
					}
				}
				break;
				/*
				 * Lists all the documents in the local working set of the current repository.
				 */
			case LD:
				if (validateInput1(words)) {
					// TODO: Implement logic to handle LD.
					VersionControlDb.findRepo(currRepo);
					System.out.println(VersionControlDb.findUser(logInUser.getName()).
							getWorkingCopy(currRepo).toString());
					
				}
				break;
				/*
				 * Edits an existing document in the logged-in user's local working copy of the current repository. 
				 * Prints DOC_NOT_FOUND, if the document was not not found in the working copy, 
				 * else prompts the user to enter the new content for the document, 
				 * sets the document's content with the new content, 
				 * adds the change to the user's pending checkin for the current 
				 * repository and finally prints SUCCESS.
				 * ed <docname>
				 */
			case ED:
				if (validateInput2(words)) {
					// TODO: Implement logic to handle ED.
					if(VersionControlDb.findUser(logInUser.getName()).getWorkingCopy(currRepo).getDoc(words[1])==null){
						System.out.println(ErrorType.DOC_NOT_FOUND);
					}
					else{
						Document newEdit = VersionControlDb.findUser(logInUser.getName()).getWorkingCopy(currRepo).getDoc(words[1]);
						String content = promptFileContent("Enter the file content and press q to quit: ");
						newEdit.setContent(content);
						VersionControlDb.findUser(logInUser.getName()).addToPendingCheckIn(newEdit, Change.Type.EDIT, currRepo);
						System.out.println(ErrorType.SUCCESS);	
					}				
				}
				break;
				/*
				 * Adds a document to the logged-in user's local working copy of the current repository. 
				 * Prints DOCNAME_ALREADY_EXISTS, if the document with <docname> already exists 
				 * in the working copy 
				 * else prompts the user to enter the content for the document, 
				 * creates a new document, adds the document to the working copy, 
				 * adds the change to the user's pending checkin for the current 
				 * repository and finally prints SUCCESS.
				 * ad <docname>
				 */
			case AD:
				if (validateInput2(words)) {
					// TODO: Implement logic to handle AD.
					if(VersionControlDb.findUser(logInUser.getName()).getWorkingCopy(currRepo).getDoc(words[1])!=null){//EXCEPTION THAT THE UDUSER DIDN'T CHECK OUT
						System.out.println(ErrorType.DOCNAME_ALREADY_EXISTS);
					}
					else{				
						String content = promptFileContent("Enter the file content and press q to quit: ");
						Document newAdd = new Document(words[1],content,currRepo);

						VersionControlDb.findUser(logInUser.getName()).getWorkingCopy(currRepo).addDoc(newAdd);
						VersionControlDb.findUser(logInUser.getName()).addToPendingCheckIn(newAdd, Change.Type.ADD, currRepo);
						
						System.out.println(ErrorType.SUCCESS);
					}
				}
				break;
				/*
				 * Deletes an document from the local working set of the current repository. 
				 * Prints DOC_NOT_FOUND, if the document was not not found in the working copy, 
				 * else deletes the document from the working copy, adds the change to the user's 
				 * pending checkin for the current repository and finally prints SUCCESS.
				 * dd <docname>
				 */
			case DD:
				if (validateInput2(words)) {
					// TODO: Implement logic to handle DD.
					if(VersionControlDb.findUser(logInUser.getName()).getWorkingCopy(currRepo).getDoc(words[1])==null){
						System.out.println(ErrorType.DOC_NOT_FOUND);
					}
					else{
						Document newDelete = VersionControlDb.findUser(logInUser.getName()).getWorkingCopy(currRepo).getDoc(words[1]);
						VersionControlDb.findUser(logInUser.getName()).getWorkingCopy(currRepo).delDoc(newDelete);
						VersionControlDb.findUser(logInUser.getName()).addToPendingCheckIn(newDelete, Change.Type.DEL, currRepo);
						System.out.println(ErrorType.SUCCESS);
					}
				}
				break;
				/*
				 * Displays an existing document in the logged-in user's local working copy of the current repository. 
				 * Prints DOC_NOT_FOUND, if the document was not not found in the working copy, 
				 * else prints the document.
				 * vd <docname>
				 */
			case VD:
				if (validateInput2(words)) {
					// TODO: Implement logic to handle VD.
					if(VersionControlDb.findUser(logInUser.getName()).getWorkingCopy(currRepo).getDoc(words[1])==null){
						System.out.println(ErrorType.DOC_NOT_FOUND);
					}
					else{
						System.out.println(VersionControlDb.findUser(logInUser.getName()).getWorkingCopy(currRepo).getDoc(words[1]).toString());
					}
				}
				break;
				/*
				 * Check-in or queues the pending changes in the local working copy 
				 * to the current repository for admin approval. 
				 * Prints NO_LOCAL_CHANGES, if there are no pending changes in the working copy of the repository, 
				 * else prints SUCCESS.
				 */
			case CI:
				if (validateInput1(words)) {
					// TODO: Implement logic to handle CI.
					System.out.println(logInUser.checkIn(currRepo));
				}
				break;
				/*
				 * Check-out the latest version of the current repository into the 
				 * working copy of the logged in user and prints SUCCESS.
				 */
			case CO:
				if (validateInput1(words)) {
					// TODO: Implement logic to handle CO.
					System.out.println(logInUser.checkOut(currRepo));
				}
				break;
				/*
				 * Allows the admin to review the current repository's queued checkins 
				 * and approve/deny those checkins. 
				 * Prints NO_PENDING_CHECKINS, if there are no queued checkins in the current repository, 
				 * else if the logged-in user is not the admin of the current repository prints ACCESS_DENIED, 
				 * else removes and prints the queued checkins one by one, 
				 * prompts the logged-in user to approve the check-in by pressing "y" (or any other input for deny), 
				 * applies any checkin to the repository 
				 * if approved and finally prints SUCCESS after all queued checkins have been processed.
				 */
			case RC:
				if (validateInput1(words)) {
					// TODO: Implement logic to handle RC.
					if(VersionControlDb.findRepo(currRepo).getCheckInCount()==0)
						System.out.println(ErrorType.NO_PENDING_CHECKINS);
					else if(!VersionControlDb.findRepo(currRepo).getAdmin().equals(logInUser))
						System.out.println(ErrorType.ACCESS_DENIED);
					else{
						while(VersionControlDb.findRepo(currRepo).getCheckInCount()!=0){

							ChangeSet toReview = VersionControlDb.findRepo(currRepo).getNextCheckIn(logInUser);
							if(toReview!=null){
								System.out.println(toReview.toString());
								System.out.print("Approve changes? Press y to accept: ");
								if(scnr.nextLine().equals("y")){								
									VersionControlDb.findRepo(currRepo).approveCheckIn(logInUser, toReview);
									
								}
							}
							
						}
						System.out.println(ErrorType.SUCCESS);
					}
				}
				break;
				/*
				 * Displays the version history for the current repository.
				 */
			case VH:
				if (validateInput1(words)) {
					// TODO: Implement logic to handle VH.
					System.out.println(VersionControlDb.findRepo(currRepo).getVersionHistory());
				}
				break;
				/*
				 * Allows the admin of the current repository to revert the repository 
				 * to the previous version. 
				 * Prints ACCESS_DENIED if logged-in user is not the admin, 
				 * else if the current version of repository is the oldest version prints NO_OLDER_VERSION, 
				 * else prints SUCCESS.
				 */
			case RE:	
				if (validateInput1(words)) {
					// TODO: Implement logic to handle RE.
					System.out.println(VersionControlDb.findRepo(currRepo).revert(logInUser));		
				}
				break;
				/*
				 * Prints the repo menu help.
				 */
			case HE:
				if (validateInput1(words)) {
					displayRepoMenu();
				}
				break;
				/*
				 * Quits the repo menu prompt and takes the logged-in user to user menu prompt.
				 */
			case QU:
				if (validateInput1(words)) {
					execute = false;
					System.out.println(ErrorType.SUCCESS);//this is not here previously
				}
				break;
			default:
				System.out.println(ErrorType.UNKNOWN_COMMAND);
			}

		}
	}

	/**
	 * The main method. Simulation starts here.
	 * @param args Unused
	 */
	public static void main(String []args) {
		try {
			processMainMenu(); 
		}
		// Any exception thrown by the simulation is caught here.
		catch (Exception e) {
			System.out.println(ErrorType.INTERNAL_ERROR);
			// Uncomment this to print the stack trace for debugging purpose.
			//e.printStackTrace();
		}
		// Any clean up code goes here.
		finally {
			System.out.println("Quitting the simulation.");
		}
	}
}
