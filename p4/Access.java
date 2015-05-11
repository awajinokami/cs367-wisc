//////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  FileSystemMain.java
// File:             Access.java
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
/**
 * The Access class represents an access (user with access - r/w) 
 * having a user (as a User) and accessType (as a char). 
 * The Access.java file contains the outline of the Access class.
 * @author Ricki
 *
 */
public class Access {
	
	private User user;
	private char accessType;
	/**
	 * Constructs an access with user and valid accesstype ('w' or 'r').
	 * @param user the user with a access
	 * @param accessType the type of the access
	 */
	public Access(User user, char accessType) {
		//TODO
		if(user==null||!((accessType=='w')||(accessType=='r'))) throw new IllegalArgumentException();	
		this.user = user;
		this.accessType = accessType;
	}

	/**
	 * Returns user
	 * @return the user of this access
	 */
	public User getUser() {
		//TODO
		return this.user;
	}

	/**
	 * Returns access type
	 * @return the access type of this access
	 */
	public char getAccessType() {
		//TODO
		return this.accessType;
	}

	/**
	 * Sets access type.
	 * @param accessType the access type to set.
	 */
	public void setAccessType(char accessType) {
		//TODO
		if(!((accessType=='w')||(accessType=='r'))) throw new IllegalArgumentException();
		this.accessType=accessType;
	}
	
	@Override
	public String toString() {
		return (user.getName() + ":" + accessType);
	}
	
}
