///////////////////////////////////////////////////////////////////////////////
//ALL STUDENTS COMPLETE THESE SECTIONS
//Main Class File:  AmazonStore.java
//File:             InsufficientCreditException.java
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
 * A checked exception class to be used by your implementation of User to 
 * signal when the user tries to buy an item but doesn't have enough credit. 
 * 
 * @author Ricki
 *
 */
public class InsufficientCreditException extends Exception {

	/**
	 * Default constructor.
	 */
	public InsufficientCreditException(){
		super();
	}
	/**
	 * Constructor that take a message to print when is called
	 * @param msg The error message to print.
	 */
	InsufficientCreditException (String msg){
		super(msg);
	}
}
