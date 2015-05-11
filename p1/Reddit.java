package p1;
///////////////////////////////////////////////////////////////////////////////
//ALL STUDENTS COMPLETE THESE SECTIONS
//Title:           	Program 1: Reddit Simulator
//Files:            Karma.java; Post.java; RedditDB.java; Reddit.java; User.java
//Semester:         CS302 Spring 2015
//
//Author:           sxie27@wisc.edu
//CS Login:         si
//Lecturer's Name:  Jim Skrentny
//Lecture Section:  Section 2
//
////////////////////STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//must fully acknowledge and credit those sources of help.
//Instructors and TAs do not have to be credited here,
//but tutors, roommates, relatives, strangers, etc do.
//
//Persons: 			n/a
//
//Online sources: 	n/a
////////////////////////////80 columns wide //////////////////////////////////

import java.io.File;
import java.io.FileNotFoundException;
//import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
/**
 * The application program, Reddit, creates and uses an RedditDB to represent 
 * and process information. The user and post information is read from a text 
 * files processed through the command line argument and then the 
 * program processes user commands. The application must make use of 
 * Java Iterators where appropriate.
 * 
 * @author rickixie
 *
 */
public class Reddit {

	//Initialize a database to store all the information
	private static RedditDB newDB = new RedditDB();
	/**
	 * The main method for executing the user input and constructing the 
	 * reddit database.
	 * @param args The command-line argument in String Array.
	 */
	public static void main(String[] args){
		// TODO Auto-generated method stub
		//add default user "admin"
		newDB.addUser("admin");
		try{
			//catch command-line exception
			if(args.length==0||args==null)
				throw new IllegalArgumentException (); 
			//evaluate the user file
			for (int i = 0; i<args.length; i++)
				constructRedditDB(args[i]);
			//execute the program by printing out command prompt
			runCommand();
			//Checks whether at least one command-line argument is given; 
			//if not, displays "Usage: java Reddit <FileNames>" and quit.
		}catch (IllegalArgumentException e){
			System.out.println("Usage: java Reddit <FileNames>");	
		}
		//handle the file not found exception from the constructRedditDB method.
		catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}
	}

	/**
	 * The method construct a reddit database for particular user session.
	 * @param commandLine The command line input that represent a file name.
	 * @throws FileNotFoundException A exception will throw if the file doesn't
	 * exist or readable.
	 */
	private static void constructRedditDB (String commandLine) throws FileNotFoundException{
		String FileName = commandLine;
		Scanner scn = null;
		File userFile = new File (FileName);
		//Checks whether the input files exists and is readable; 
		//if not, displays: "File <FileName> not found." and quit.
		if(!userFile.exists() || !userFile.canRead())
			throw new FileNotFoundException ("File "+ FileName+ " not found.");
		else {
			//the scanner will then scan the file to construct the database
			scn = new Scanner (userFile);
			//it will first take the file name as the user name and add it to
			// the reddit database
			String userName = FileName.substring(0, FileName.indexOf(".txt")).toLowerCase();
			User newUser = newDB.addUser(userName);
			if (scn.hasNextLine()){
				//take the first line argument as subscribed reddits names.
				String [] newSubscribed = scn.nextLine().split(", ");
				for (int j = 0; j<newSubscribed.length; j++){
					newUser.subscribe(newSubscribed[j].toLowerCase());
				}
				//then they scanner will take the rest of file 
				while(scn.hasNextLine()){
					String postP = scn.nextLine();
					int first = postP.indexOf(", ");
					//find the second index of "," - in case there are more
					//commas in the post title.
					int second = postP.indexOf(", ", first+1);
					String postSubredditP = postP.substring(0, first);
					String postTypeP = postP.substring(first+2, second);
					String postContentP = postP.substring(second+2);
					newUser.addPost(postSubredditP, PostType.valueOf(postTypeP), postContentP);
				}
			}
		}
		scn.close();
	}

	/**
	 * This method handles user input and print information regarding 
	 * different command options.
	 */
	private static void runCommand(){
		//a boolean to print the command prompt
		boolean shouldKeepGoing = true;
		//a boolean to switch to submenu method
		boolean submenu = false;
		String userLogin = "anon";
		//Separate array list to token and check size
		Scanner userInput;
		while(shouldKeepGoing){
			userInput = new Scanner (System.in);
			System.out.print("["+userLogin+"@reddit]$ ");

			//try and catch illegal argument exception
			String test = userInput.nextLine();
			String[] toExam = test.split("\\s+");
			if(toExam.length==1){
				switch(toExam[0]){			
				 //Show summary of all users when the current user is "admin"; 
				 //not a valid command else. Summaries will be in the following 
				 //format: <userName><horizontalTab><linkKarma><horizontalTab>
				 //<commentKarma>		 
				case "s":
					if(!userLogin.equalsIgnoreCase("admin"))
						System.out.println("Invalid command!");
					else{
						for(int i = 0; i<newDB.getUsers().size(); i++){
							System.out.println(newDB.getUsers().get(i).getName()
									+"\t"+newDB.getUsers().get(i).getKarma().getLinkKarma()
									+"\t"+newDB.getUsers().get(i).getKarma().getCommentKarma());
						}
					}break;				
				//Logout the user and display the message "User <userName> 
				//logged out." when done. If no user was already logged in, 
				//display "No user logged in."
				case "l":
					if(userLogin.equals("anon")){
						System.out.println("No user logged in.");	
					}else{
						System.out.println("User " + userLogin + " logged out.");	
						userLogin = "anon";
					}
					break;
				 //Display "Displaying the front page..." and prompt for 
				 //sub-menu options (given in another table below) for each 
				 //frontPage post one-by-one till the sub-menu exits. Posts are 
				 //to be displayed as <postKarma><horizontalTab><Title>
				case "f":
					try {
						List <Post> toPrintFront = newDB.getFrontpage(newDB.findUser(userLogin));
						submenu = true;
						System.out.println("Displaying the front page...");
						//enter the submenu as long as there is content to operate
						while (submenu)
							submenu = submenuOperations(toPrintFront, userLogin);
					}
					 //Display "No posts left to display." when all posts have 
					 //displayed and exit the sub-menu.
					 //Display "Exiting to the main menu..." 
					 //when leaving the sub-menu.	
					catch (NoSuchElementException e){
						System.out.println("No posts left to display.");
						System.out.println("Exiting to the main menu...");
						submenu = false;
					}
					break;
				case "x":
					if(submenu){
						System.out.println("Exiting to the main menu...");
						submenu = false;
					}else{
						System.out.println("Exiting to the real world...");
						shouldKeepGoing = false;
					}
					break;
				default:
					System.out.println("Invalid command!");
				}
			}
			//evaluate another sets of command options
			else if(toExam.length==2){
				//get the first element of the command line
				switch(toExam[0]){
				//Delete the specified user and display "User <userName> 
				//deleted." if found and when the current user is "admin"; 
				//not a valid command else.
				//If the user was not found, display "User <userName> not found."
				case "d":
					String userToDelete = toExam[1];
					if(userLogin.equals("admin")){

						if(newDB.findUser(userToDelete) !=null){
							newDB.delUser(userToDelete);
							System.out.println("User "+userToDelete+" deleted.");
						}else{
							System.out.println("User "+userToDelete+" not found.");
						}
					}
					else{
						System.out.println("Invalid command!");
					}
					break;
					//Login the user with the given userName and display the message "User <userName> logged in." when done.
					//	If another user was already logged in, display "User <OtherUserName> already logged in."
					//	If no user was already logged in, but the current user was not found, display "User <userName> not found."
				case "l":
					String userToLogin = toExam[1].toLowerCase();
					//if the user is not exist
					if(userLogin.equals("anon")){
						if(newDB.findUser(userToLogin)==null){
							System.out.println("User " + userToLogin + " not found.");
						}
						else{
							userLogin = userToLogin;
							System.out.println("User " + userLogin + " logged in.");
						}
					}
					else{
						System.out.println("User " + userLogin + " already logged in.");
					}
					break;
				//Display "Displaying /r/<subredditName>..." and prompt for 
				//sub-menu options (given in another table below) for each 
				//subreddit frontPage post one-by-one till the sub-menu exits.
				//Display "No posts left to display." when all posts have displayed and exit the sub-menu.
				//Display "Exiting to the main menu..." when leaving the sub-menu.
				case "r":				
					String subRedditName = toExam[1].toLowerCase();
					System.out.println("Displaying /r/" +subRedditName+"...");
					try{
						List <Post> toPrintFrontbyReddit = newDB.getFrontpage(newDB.findUser(userLogin), subRedditName);
						submenu = true;
						while (submenu)
							submenu = submenuOperations(toPrintFrontbyReddit, userLogin);
					}
					catch (NoSuchElementException e){
						System.out.println("No posts left to display.");
						System.out.println("Exiting to the main menu...");
						submenu = false;
					}
					break;
				//Display "Displaying /u/<userName>..." and prompt for 
				//sub-menu options (given in another table below) for each 
				//post of the specified user one-by-one till the sub-menu exits.
				//Display "No posts left to display." when all posts have displayed
				//and exit the sub-menu.
				//Display "Exiting to the main menu..." when leaving the sub-menu.
				case "u":
					String userName = toExam[1];
					try {
						List <Post> toPrintFrontbyUser = newDB.findUser(userName).getPosted();
						System.out.println("Displaying /u/" +userName+"...");
						submenu = true;
						while (submenu)
							submenu = submenuOperations(toPrintFrontbyUser, userLogin);
					}
					catch (NullPointerException e){
						System.out.println("Invalid Command!");
						submenu = false;
					}
					break;
				default: System.out.println("Invalid command!");
				}
			}
			else
				System.out.println("Invalid Command!");
		}	
	}



	/**
	 * This method operations all the submenu options and return a boolean when
	 * the operation is finished.
	 * @param toPrintFront The runCommand method past a post parameter for the 
	 * method to iterate from.
	 * @param userLogin	The user who is logging in and using the command.
	 * @return A boolean indicates the status of submenu. True means still in
	 * operations, False means finished the operations.
	 */
	private static boolean submenuOperations (List<Post> toPrintFront, String userLogin){

		//Initialize a iterator to access all the post from the front page, or 
		//subreddit thread, or a user's posts.
		Iterator <Post> itr = toPrintFront.iterator();
		boolean submenu = true;
		//A temporary memory to store the iterator content.
		Post temp = itr.next();
		Scanner userInput;
		System.out.println(temp.getKarma() +"\t" + temp.getTitle());
		try{
			while(submenu){	
				userInput = new Scanner (System.in);
				System.out.print("["+userLogin+"@reddit]$ ");
				String usersubInput = userInput.nextLine();
				switch(usersubInput){
				//Upvote the given post and move to the next one if a user 
				//is logged in.
				// If not logged in, display "Login to like post."
				case "a":
					if(userLogin.equals("anon")){
						System.out.println("Login to like post.");
						System.out.println(temp.getKarma() +"\t" + temp.getTitle());
					}
					else{
						newDB.findUser(userLogin).like(temp);
						temp = itr.next();
						System.out.println(temp.getKarma() +"\t" + temp.getTitle());
					}			
					break;
				//Downvote the given post and move to the next one if a user 
				//is logged in.
				//If not logged in, display "Login to dislike post."
				case "z":
					if(userLogin.equals("anon")){
						System.out.println("Login to dislike post.");
						System.out.println(temp.getKarma() +"\t" + temp.getTitle());
					}
					else{
						newDB.findUser(userLogin).dislike(temp);
						temp = itr.next();
						System.out.println(temp.getKarma() +"\t" + temp.getTitle());
					}
					break;
				//Move to the next one.
				case "j" :
					temp = itr.next();
					System.out.println(temp.getKarma() +"\t" + temp.getTitle());
					break;
				//Display "Exiting to the main menu..." and exit the sub-menu.
				case "x":
					System.out.println("Exiting to the main menu...");
					submenu = false;
					break;
				default:
					System.out.println("Invalid command!");
					System.out.println(temp.getKarma() +"\t" + temp.getTitle());
				}
			}
			//userInput.close();
			
		}
		//similar to the run command method, this will catch the exception
		// when there is no more element for the iterator to read from.
		catch (NoSuchElementException e){
			System.out.println("No posts left to display.");
			System.out.println("Exiting to the main menu...");
			submenu = false;
		}
		//return false when it finished iterating the list.
		//userInput.close();
		return submenu;
	}
}
