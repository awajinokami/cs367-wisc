import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

///////////////////////////////////////////////////////////////////////////////
//Title:            CS 367 Programming Assignment 2
//Files:            AmazonStore.java 
//					DLinkedList.java
//					InsufficientCreditException.java
//					Product.java
//					User.java
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

public class AmazonStore {

	//Store record of users and products
	private static ListADT<Product> products = new DLinkedList<Product>();
	private static ListADT<User> users = new DLinkedList<User>();
	private static User currentUser = null;//current user logged in

	//scanner for console input
	public static final Scanner stdin= new Scanner(System.in);


	//main method
	public static void main(String args[]) {


		//Populate the two lists using the input files: Products.txt User1.txt User2.txt ... UserN.txt
		if (args.length < 2) {
			System.out.println("Usage: java AmazonStore [PRODUCT_FILE] [USER1_FILE] [USER2_FILE] ...");
			System.exit(0);
		}

		//load store products
		loadProducts(args[0]);

		//load users one file at a time
		for(int i=1; i<args.length; i++)
			loadUser(args[i]);

		//User Input for login
		boolean done = false;
		while (!done) 
		{
			System.out.print("Enter username : ");
			String username = stdin.nextLine();
			System.out.print("Enter password : ");
			String passwd = stdin.nextLine();

			if(login(username, passwd)!=null)
			{
				//generate random items in stock based on this user's wish list
				ListADT<Product> inStock=currentUser.generateStock();
				//show user menu
				userMenu(inStock);
			}
			else
				System.out.println("Incorrect username or password");

			System.out.println("Enter 'exit' to exit program or anything else to go back to login");
			if(stdin.nextLine().equals("exit"))
				done = true;
		}

	}

	/**
	 * Tries to login for the given credentials. Updates the currentUser 
	 * if successful login
	 * 
	 * @param username name of user
	 * @param passwd password of user
	 * @returns the currentUser 
	 */
	public static User login(String username, String passwd){
		for(int i=0; i<users.size();i++){
			if(users.get(i).checkLogin(username, passwd)){//if find match
				currentUser = users.get(i);//update the currentUser
			}
		}

		return currentUser;
	}

	/**
	 * Reads the specified file to create and load products into the store.
	 * Every line in the file has the format: <NAME>#<CATEGORY>#<PRICE>#<RATING>
	 * Create new products based on the attributes specified in each line and 
	 * insert them into the products list
	 * Order of products list should be the same as the products in the file
	 * For any problem in reading the file print: 'Error: Cannot access file'
	 * 
	 * @param fileName name of the file to read.
	 */
	public static void loadProducts(String fileName){
		Scanner scn = null;
		File productFile = new File (fileName);

		//the scanner will then scan the file to construct the database
		try{
			scn = new Scanner (productFile);
			while (scn.hasNextLine()){
				String [] productInfo = scn.nextLine().split("#");
				//construct the product
				Product newProduct = new Product(productInfo[0], productInfo[1], 
						Integer.parseInt(productInfo[2]), Float.parseFloat(productInfo[3]));
				products.add(newProduct);//add it to the list	
			}			
		}
		catch(FileNotFoundException e){
			System.out.println("Error:Cannot access file");
		}


	}

	/**
	 * Reads the specified file to create and load a user into the store.
	 * The first line in the file has the format:<NAME>#<PASSWORD>#<CREDIT>
	 * Every other line after that is a name of a product in the user's wishlist, format:<NAME>
	 * For any problem in reading the file print: 'Error: Cannot access file'
	 * 
	 * @param fileName name of the file to read
	 */
	public static void loadUser(String fileName){
		Scanner scn = null;
		File userFile = new File (fileName);

		//the scanner scan the file to construct the user and its wish list
		try {
			scn = new Scanner (userFile);
			while (scn.hasNextLine()){
				//take the first line argument and create the user
				String [] userInfo = scn.nextLine().split("#");
				//construct the user
				User newUser = new User(userInfo[0], userInfo[1], Integer.parseInt(userInfo[2]));
				users.add(newUser);//add to the database
				while(scn.hasNextLine()){//add the rest of wish list
					newUser.addToWishList(search(scn.nextLine(), products));
					//a search method find the product and return a product object
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error:Cannot access file");
		}
	}

	/**
	 * See sample outputs
	 * Prints the entire store inventory formatted by category
	 * The input text file for products is already grouped by category, 
	 * use the same order as given in the text file 
	 * format:
	 * <CATEGORY1>
	 * <NAME> [Price:$<PRICE> Rating:<RATING> stars]
	 * ...
	 * <NAME> [Price:$<PRICE> Rating:<RATING> stars]
	 * 
	 * <CATEGORY2>
	 * <NAME> [Price:$<PRICE> Rating:<RATING> stars]
	 * ...
	 * <NAME> [Price:$<PRICE> Rating:<RATING> stars]
	 */
	public static void printByCategory(){

		String category;//a string to store the current category to print
		System.out.println();//print a line to start
		for(int i =0; i<products.size(); i++){//iterate through the product list
			category = products.get(i).getCategory();//get the first product's category		
			System.out.println(category+":");//start printing the products
			while(i<products.size()&&products.get(i).getCategory().equals(category)){
				System.out.println(products.get(i).toString());
				i++;//advance the list
			}
			i--;//since the for loop will increment the position after one loop
			//decrease the i to avoid skipping
			System.out.println();//print a line to start printing next category
		}
	}


	/**
	 * This private method searches if a ListADT<Product> contains a product, and return
	 * the product if found, else it will return null.
	 * 
	 * @param productname The product's name to search for.
	 * @param toSearch	The product list to search the target product.
	 * @return return the product found, if not found, return null.
	 */
	private static Product search(String productname, ListADT<Product> toSearch){

		//iterate through the list and try to find a match
		for(int i=0; i<toSearch.size(); i++){
			if(toSearch.get(i).getName().equals(productname))
				return toSearch.get(i);//return found product object
		}	
		//if not found, return null. This will cause the user class 
		//throw the IllegalArguementException (again it is assume that this will
		//not happen since the file is format correct - as indicate in the piazza
		//posts.
		return null;		
	}

	/**
	 * Interacts with the user by processing commands
	 * 
	 * @param inStock list of products that are in stock
	 */
	public static void userMenu(ListADT<Product> inStock){

		boolean done = false;
		while (!done) 
		{
			System.out.print("Enter option : ");
			String input = stdin.nextLine();

			//only do something if the user enters at least one character
			if (input.length() > 0) 
			{
				String[] commands = input.split(":");//split on colon, 
											//because names have spaces in them
				if(commands[0].length()>1)
				{
					System.out.println("Invalid Command");
					continue;
				}
				switch(commands[0].charAt(0)){
				case 'v':
					if(commands.length!=2) 
						System.out.println("Invalid Command");
					else{
						//view all products
						if(commands[1].equals("all"))
							printByCategory();	
						//view a particular user's wish list
						else if(commands[1].equals("wishlist"))
							currentUser.printWishList(System.out);
						//view currently instock product list
						else if(commands[1].equals("instock")){
							//iterate through generated inStock list
							for(int i=0; i<inStock.size(); i++){
								System.out.println(inStock.get(i).toString());
							}						 
						}
						else
							System.out.println("Invalid Command");
					}
					break;
				case 's':
					if(commands.length!=2) 
						System.out.println("Invalid Command");
					else{					
						//Search for the String string in all of the product 
						//names in the store and print out all the matching products. 						
						for(int i=0; i<products.size(); i++){
							if(products.get(i).getName().contains((commands[1]))){
								System.out.println(products.get(i));
								//Print format for each product is the same as 
								//specified in the print method of Product 
								//For no matching products, print nothing.
							}
						}
					}
					break;

				case 'a':
					if(commands.length!=2) 
						System.out.println("Invalid Command");
					else{
						//Add the Product whose name is specified by productName 
						//to the user's wishlist. 
						if(search(commands[1], products)!=null){
							currentUser.addToWishList(search(commands[1], products));
							System.out.println("Added to wishlist");
						}
						else //Show "Product not found" if no such product.
							System.out.println("Product not found");
					}
					break;
				case 'r':
					if(commands.length!=2) 
						System.out.println("Invalid Command");
					else{
						try{
							//Remove the Product whose name is specified by productName 
							//from the user's wishlist. Show "Product not found" if no such product.
							if(currentUser.removeFromWishList(commands[1])!=null){
								currentUser.removeFromWishList(commands[1]);
								System.out.println("Removed from wishlist");
							}else
								System.out.println("Product not found");
						}catch(IndexOutOfBoundsException e){
							System.out.println("Product not found");
							//For the wishlist if the user tries to remove any product when 
							//its empty it would say Product not found.
							//catch IndexOutOfBoundsException and print
						}
					}
					break;
				case 'b':
					//Buy the Products shown to be in stock by the list inStock. 
					//This removes them from the user's wishlist and charges the user for it. 
					if(commands.length!=1) 
						System.out.println("Invalid Command");
					else{
						for(int i=0; i<inStock.size(); i++){
							try{
								boolean success = currentUser.buy(inStock.get(i).getName());
								if(success){
									System.out.println("Bought "+inStock.get(i).getName()); 
								}	
							}
							//If the user has insufficient credit, print error message
							//and proceed with processing the user input for the commands again.
							catch(InsufficientCreditException e){
								System.out.println("Insufficient funds for "+inStock.get(i).getName());
							}
						}
					}
				
					break;
				case 'c':
					//Shows the credit of the current user as $<CREDIT>.
					if(commands.length!=1) 
						System.out.println("Invalid Command");
					else
					System.out.println("$"+currentUser.getCredit());
					break;
				case 'l':
					//Logs out the current user and goes back to the login screen.
					done = true;
					currentUser=null;
					System.out.println("Logged Out");
					break;
				default:  //a command with no argument
					System.out.println("Invalid Command");
					break;
				}
			}
		}
	}

}
