///////////////////////////////////////////////////////////////////////////////
//ALL STUDENTS COMPLETE THESE SECTIONS
//Main Class File:  AmazonStore.java
//File:             Product.java
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
//Online sources:  	n/a
//
////////////////////////////80 columns wide //////////////////////////////////
/**
 * Stores the name, category, price and rating of a product
 * @author Ricki
 */
public class Product {
	
	private String name;
	private String category;
	private int price;
	private float rating;
	
	/**
     * Constructs a Product with a name, category, price and rating. 
     * 
     * @param name name of product
     * @param category category of product
     * @param price price of product in $ 
     * @param rating rating of product out of 5
     */
	public Product(String name, String category, int price, float rating){	
		this.name = name;
		this.category = category;
		this.price = price;
		this.rating = rating;			
	}
	
	/** 
     * Returns the name of the product
     * @return the name
     */
	public String getName(){
		
		return this.name;
	}
	
	/** 
     * Returns the category of the product
     * @return the category
     */
	public String getCategory(){
		return this.category;
	}
	
	/** 
     * Returns the price of the product
     * @return the price
     */
	public int getPrice(){
		return this.price;
	}
	
	/** 
     * Returns the rating of the product
     * @return the rating
     */
	public float getRating(){
		return this.rating;
	}
	
	/** 
     * Returns the Product's information in the following format: <NAME> [Price:$<PRICE> Rating:<RATING> stars]
     */
	public String toString(){
		return this.getName() + " [Price:$"+this.getPrice()+ " Rating:"+this.getRating()+" stars]";
	}
}