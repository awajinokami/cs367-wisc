
import java.util.Random;
import java.io.PrintStream;
///////////////////////////////////////////////////////////////////////////////
//ALL STUDENTS COMPLETE THESE SECTIONS
//Main Class File:  AmazonStore.java
//File:             User.java
//Semester:         CS367 Spring 2015
//
//Author:           Ricki (Si) Xie; sxie27@wisc.edu
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
//Online sources:  	1. Return two values through a method call, used in the
//						foundProduct() private method
//http://stackoverflow.com/questions/21591178/java-how-to-return-two-variables
////////////////////////////80 columns wide //////////////////////////////////
/**
 * The User class uses DLinkedList to build a price ordered list called 
 * 'wishlist' of products 
 * Products with higher Price fields should come earlier in the list.
 * @author Ricki
 */
public class User {
	//Random number generator, used for generateStock. DO NOT CHANGE
	private static Random randGen = new Random(1234);

	private String username;
	private String passwd;
	private int credit;
	private ListADT<Product> wishList;

	/**
	 * Constructs a User instance with a name, password, credit and an empty 
	 * wishlist. 
	 * 
	 * @param username name of user
	 * @param passwd password of user
	 * @param credit amount of credit the user had in $ 
	 */
	public User(String username, String passwd, int credit){
		this.username = username;
		this.passwd = passwd;
		this.credit = credit;
		this.wishList = new DLinkedList<Product>();
	}

	/**
	 * Checks if login for this user is correct
	 * @param username the name to check
	 * @param passwd the password to check
	 * @return true if credentials correct, false otherwise
	 * @throws IllegalArgumentException when username or passwd is null
	 */
	public boolean checkLogin(String username, String passwd){

		if(username==null||passwd==null) throw new IllegalArgumentException();
		return this.username.equals(username)&&this.passwd.equals(passwd);
	}

	/**
	 * Adds a product to the user's wishlist. 
	 * Maintain the order of the wishlist from highest priced to lowest priced 
	 * products.
	 * @param product the Product to add
	 * @IllegalArgumentException throws a IllegalArgumentException if the 
	 * 							 product to add is null.
	 */
	public void addToWishList(Product product){
		//throw exception when the product to add is null
		if(product==null) throw new IllegalArgumentException();
		//1) if the wishlist is empty
		if(this.wishList.size()==0)
			this.wishList.add(product);	//just add it to the end of the list
		//2) if the product to add is cheaper than the last one in the list
		else if(product.getPrice()<this.wishList.get(this.wishList.size()-1).getPrice()){
			this.wishList.add(product);//also add it to th end
		}else{
			boolean done = false;
			//find the node that has product with price that is equal or
			//less than the product ot add 
			for(int i=0; i<this.wishList.size()&&!done;i++){
				if(this.wishList.get(i).getPrice()<=product.getPrice()){
					this.wishList.add(i,product);//add it before it
					done = true;//finishing adding
				}
			}
		}
	}

	/**
	 * Removes a product from the user's wishlist. 
	 * Do not charge the user for the price of this product
	 * @param productName the name of the product to remove
	 * @return the product on success, null if no such product found
	 * @throws IllegalArgument Exception when productName is null
	 */
	public Product removeFromWishList(String productName){
		int [] result = findProduct(productName);
		if (productName== null) throw new IllegalArgumentException();
		//if it is not found
		if(result[0]==0)
			return null;
		else{
		//if found then remove it from the list at position getting from the 
			//findProduct method
			return this.wishList.remove(result[1]);		
		}

	}

	/**
	 * Print each product in the user's wishlist in its own line using the 
	 * PrintStream object passed in the argument
	 * @param printStream The printstream object on which to print out the wishlist
	 */
	public void printWishList(PrintStream printStream){
		for(int i=0; i<this.wishList.size(); i++){
			printStream.println(this.wishList.get(i).toString());//piazza
		}
	}

	/**
	 * Buys the specified product in the user's wishlist.
	 * Charge the user according to the price of the product by updating the credit
	 * Remove the product from the wishlist as well
	 * Throws an InsufficientCreditException if the price of the product is
	 * greater than the credit available.
	 * 
	 * @param productName name of the product
	 * @return true if successfully bought, false if product not found 
	 * @throws InsufficientCreditException if price > credit 
	 * @throws IllegalArgumentException when productName is null
	 */
	public boolean buy(String productName) throws InsufficientCreditException{
		if(productName==null) throw new IllegalArgumentException();
		
		//1) Find if the product to buy is in the user's wishlist
		int [] result = findProduct(productName);
		boolean sucess = false;
		if(result[0]==0)
			sucess = false;//if it is not in the list
		else{
		//2) If it is in the list, see if there is sufficient credit to
			//purchase the product
			if(this.wishList.get(result[1]).getPrice()>(this.credit)){
				sucess = false;
				throw new InsufficientCreditException();
			}
			else{
		//3) Or purchase the product and remove from the list
				this.credit = this.credit - this.wishList.get(result[1]).getPrice();
				this.removeFromWishList(productName);
				sucess = true;//change it to true
			}
		}
		return sucess;
	}

	/** 
	 * Returns the credit of the user
	 * @return the credit
	 */
	public int getCredit(){
		return credit;
	}

	/**
	 * This method is already implemented for you. Do not change.
	 * Declare the first N items in the currentUser's wishlist to be in stock
	 * N is generated randomly between 0 and size of the wishlist
	 * 
	 * @returns list of products in stock 
	 */
	public ListADT<Product> generateStock() {
		ListADT<Product> inStock= new DLinkedList<Product>();

		int size=wishList.size();
		if(size==0)
			return inStock;

		int n=randGen.nextInt(size+1);//N items in stock where n>=0 and n<size

		//pick first n items from wishList
		for(int ndx=0; ndx<n; ndx++)
			inStock.add(wishList.get(ndx));

		return inStock;
	}

	/**
	 * This method find if a product is in the user's wish list
	 * and return a integer array that contains both the result (1 means found)
	 * and position of the product in the list if found. 
	 * @param productName The product to find in the list
	 * @return a integer array contains the found result
	 */
	private int [] findProduct(String productName){
 		int pos = 0;
 		//A for loop to get the position of the product if found
		for(int i=0; i<this.wishList.size(); i++){
			//find the match
			if(this.wishList.get(i).getName().equals(productName)){
				return new int[]{1, pos};//return 1(found) and the position
				//or the found product.
			}
			pos++;//increment the pos if not found
		}
		//return 0 (not found) and -1(illegal position) if the product
		//to find is not in the user wish list
		return new int[]{0, -1};
	}


}
