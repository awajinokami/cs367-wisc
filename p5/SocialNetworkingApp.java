import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
///////////////////////////////////////////////////////////////////////////////
//ALL STUDENTS COMPLETE THESE SECTIONS
//Title:            p5
//Files:           	SocialNetworkingApp.java
//					SocialGraph.java
//					UndirectedGraph.java
//
//
//Semester:         CS367 Spring 2015
//						
//Author:           Si Xie
//Email:            sxie27@wisc.edu
//CS Login:         si
//Lecturer's Name:  Jim Skrentny
//
////////////////////STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//must fully acknowledge and credit those sources of help.
//Instructors and TAs do not have to be credited here,
//but tutors, roommates, relatives, strangers, etc do.
//
//Persons:          Piazza
//
//Online sources:   n/a
////////////////////////////80 columns wide //////////////////////////////////

/**
 * This class implements a simplified social networking app. 
 * Via a command line interface, users can login, create/destroy friendships, 
 * make queries about their social circle, and log out. 
 * 
 * Users are first greeted by the main menu when they enter the social 
 * networking application. They have an option to either log in, which brings 
 * them to the sub-menu, or exit the application. The commands for the main menu
 * and sub-menu are as follows.
 * 
 * NOTE 1: Methods in this class do not need to handle null arguments. 
 * That is, you can assume that methods in this class will never be called with null arguments.
 * NOTE 2: You may modify the code in this class outside of sections marked 
 * TODO, especially to do additional error checking of user input, 
 * but this isn't necessary to receive a full grade.
 * 
 * @author rickixie
 *
 */
public class SocialNetworkingApp {

    /**
     * Returns a social network as defined in the file 'filename'.
     * See assignment hand-out on the expected file format.
     * @param filename filename of file containing social connection data
     * @return the constructed social graph
     * @throws FileNotFoundException if file does not exist
     */
    public static SocialGraph loadConnections(String filename) throws FileNotFoundException {
        //TODO
    	//create file instance and scanner to read from
    	File networkfile = new File(filename);
    	Scanner scn = new Scanner(networkfile);
    	//Initialize a new SocialGraph instance to return
    	SocialGraph socialNetwork = new SocialGraph();

    	 //read the input file line by line as each line are a line of user info
    	while(scn.hasNextLine()){    		    		
    
    		String [] userInfo = scn.nextLine().split(" "); 
    		if(!socialNetwork.getAllVertices().contains(userInfo[0]))
    			socialNetwork.addVertex(userInfo[0]);
    		//create the vertex if it is not exists in the graph
    
    		    		
    		for(int i=1; i<userInfo.length;i++){
    			if(!socialNetwork.getAllVertices().contains(userInfo[i])){
    				socialNetwork.addVertex(userInfo[i]);//create vertex
    			}
    				socialNetwork.addEdge(userInfo[0], userInfo[i]);//create edge		
    		 }    		
    	}

    	scn.close();
    
    	
        return socialNetwork;
    }

    static Scanner stdin = new Scanner(System.in);
    static SocialGraph graph;
    static String prompt = ">> ";  // Command prompt

    /**
     * Access main menu options to login or exit the application.
     * 
     * THIS METHOD HAS BEEN IMPLEMENTED FOR YOU.
     */
    public static void enterMainMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.print(prompt);
            String[] tokens = stdin.nextLine().split(" ");
            String cmd = tokens[0];
            String person = (tokens.length > 1 ? tokens[1] : null);

            switch(cmd) {
            	//Enters submenu as P1. Prints �Logged in as <P1>� before 
            	//entering the submenu and �Logged out� after exiting the submenu.
                case "login":
                	/*
                	 * You can assume that P1 is one of the names in the input 
                	 * file given at runtime. In other words, you can assume 
                	 * that P2 exists in the social network if you implemented 
                	 * SocialGraph, UndirectedGraph and the loadConnections 
                	 * method correctly.
                	 */
                    System.out.println("Logged in as " + person);
                    enterSubMenu(person);
                    System.out.println("Logged out");
                    break;
                //Exits main menu   
                case "exit":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid command");
            }
        }
    }

    /**
     * Access submenu options to view the social network from the perspective
     * of currUser. Assumes currUser exists in the network.
     * 
     * NOTE 1: You can assume that all user input in the submenu will be valid, 
     * e.g., no spelling mistakes, not more than two words per command. 
     * Nevertheless, basic error checking has been implemented for you.
     * NOTE 2: You can assume that P2 is not the current user.
     * NOTE 3: You can assume that P2 is one of the names in the input 
     * file given at runtime. In other words, you can assume that P2 
     * exists in the social network if you implemented SocialGraph, 
     * UndirectedGraph and the loadConnections method correctly.
     * @param currUser
     */
    public static void enterSubMenu(String currUser) {

        // Define the set of commands that have no arguments or one argument
        String commands =
                "friends fof logout print\n" +
                "connection friend unfriend";
        Set<String> noArgCmds = new HashSet<String>
                (Arrays.asList(commands.split("\n")[0].split(" ")));
        Set<String> oneArgCmds = new HashSet<String>
                (Arrays.asList(commands.split("\n")[1].split(" ")));

        boolean logout = false;
        while (!logout) {
            System.out.print(prompt);

            // Read user commands
            String[] tokens = stdin.nextLine().split(" ");
            String cmd = tokens[0];
            String otherPerson = (tokens.length > 1 ? tokens[1] : null);

            // Reject erroneous commands
            // You are free to do additional error checking of user input, but
            // this isn't necessary to receive a full grade.
            if (tokens.length == 0) continue;
            if (!noArgCmds.contains(cmd) && !oneArgCmds.contains(cmd)) {
                System.out.println("Invalid command");
                continue;
            }
            if (oneArgCmds.contains(cmd) && otherPerson == null) {
                System.out.println("Did not specify person");
                continue;
            }

            switch(cmd) {
            
            /* <connection P2>
             * Prints the shortest path from the current user to 
            *  P2 if there is a path between the current user and P2. 
            *  Otherwise, prints �You are not connected to <P2>�.
            */
            case "connection": {
                //TODO
            	if(graph.getPathBetween(currUser, otherPerson)==null)
            		System.out.println("You are not connected to "+otherPerson);    
            	else
            		System.out.println(graph.getPathBetween(currUser, otherPerson));
            //	else
            		        	
                break;
            }
            /*
             * Prints all of the current user�s 1-degree friends in 
             * alphabetical order if this user has 1-degree friends. 
             * Otherwise, prints �You do not have any friends�.
             */
            case "friends": {
                //TODO
            	if(graph.getNeighbors(currUser).size()!=0){
            		//REMEMBER TO SORT THEM
            		List<String> neighborsInOrder = new ArrayList<String>(graph.getNeighbors(currUser));
            		Collections.sort(neighborsInOrder);
            		System.out.println(neighborsInOrder);           	
            	}else
            		System.out.println("You do not have any friends");
                break;
            }

            /*
             * Prints all of the current user�s 2-degree friends in 
             * alphabetical order if this user has 2-degree friends. 
             * Otherwise, prints �You do not have any friends of friends�.
             */
            case "fof": {
                //TODO
            	if(graph.friendsOfFriends(currUser).size()!=0){
            		List<String> fofInOrder = new ArrayList<String>(graph.friendsOfFriends(currUser));
            		Collections.sort(fofInOrder);
            		System.out.println(fofInOrder);
            	}            		
            	else
            		System.out.println("You do not have any friends of friends");
                break;
                //break;
            }
            /*
             * <friend P2>
             * Creates a friendship between the current user and P2, 
             * and prints �You are now friends with <P2>�, if they are not 
             * already friends. Otherwise, prints �You are already 
             * friends with <P2>�.
             */
            case "friend": {
                //TODO
            	if(!graph.getNeighbors(currUser).contains(otherPerson)){
            		graph.addEdge(currUser, otherPerson);
            		System.out.println("You are now friends with "+otherPerson);
            	}         		
            	else
            		System.out.println("You are already friends with "+otherPerson);           		
                break;
            }

            /*
             * <unfriend P2>
             * Removes a friendship between the current user and P2, and 
             * prints �You are no longer friends with <P2>�, if they are 
             * friends. Otherwise, prints �You are already not friends with <P2>�.
             */
            case "unfriend": {
                //TODO
            	if(graph.getNeighbors(currUser).contains(otherPerson)){
            		graph.removeEdge(currUser, otherPerson);
            		System.out.println("You are no longer friends with "+otherPerson);
            	}         		
            	else
            		System.out.println("You are already not friends with "+otherPerson);           		
                break;
               
            }

            case "print": {
                // This command is left here for your debugging needs.
                // You may want to call graph.toString() or graph.pprint() here
            	// You are free to modify this or remove this command entirely.
            	//
                // YOU DO NOT NEED TO COMPLETE THIS COMMAND
                // THIS COMMAND WILL NOT BE PART OF GRADING
                break;
            }

            case "logout":
                logout = true;
                break;
            }  // End switch
        }
    }

    /**
     * Commandline interface for a social networking application.
     *
     * THIS METHOD HAS BEEN IMPLEMENTED FOR YOU.
     *
     * @param args
     */
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: java SocialNetworkingApp <filename>");
            return;
        }
        try {
            graph = loadConnections(args[0]);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        enterMainMenu();

    }

}
