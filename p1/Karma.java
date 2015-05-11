package p1;
///////////////////////////////////////////////////////////////////////////////
//ALL STUDENTS COMPLETE THESE SECTIONS
//Main Class File:  Reddit.java
//File:             Karma.java
//Semester:         CS302 Spring 2015
//
//Author:           sxie27@wisc.edu
//CS Login:         si
//Lecturer's Name:  Jim Skrentny
//Lecture Section:  Section 2
//
////////////////////STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//fully acknowledge and credit all sources of help,
//other than Instructors and TAs.
//
//Persons:          n/a
//
//Online sources:   n/a
////////////////////////////80 columns wide //////////////////////////////////
/**
 * The Karma class represents the points accrued by a single user.
 *  Karma is of two subtypes, linkKarma and commentKarma, relating to 
 *  LINK and COMMENT PostTypes respectively; SELF posts do not affect the 
 *  Karma of the creating user. A new user will have karmas of both types 
 *  initialised to zero.
 * @author rickixie
 *
 */
public class Karma {
	private int linkKarma;
	private int commentKarma;

	/**
	 * Default constructor: Creates an instance of the Karma class with 
	 * link and comment karmas initialized to zero
	 * 
	 */
	public Karma() {
		this.linkKarma = 0;
		this.commentKarma = 0;
	}

	/**
	 * Increases the karma of this type by two for the current instance.
	 * @param type A type of post that will get corresponding karma point.
	 */
	public void upvote(PostType type) {
		//TODO
		try{
			if(type == PostType.LINK)
				this.linkKarma = this.linkKarma+2;
			if(type == PostType.COMMENT)
				this.commentKarma = this.commentKarma+2;
		}
		catch (IllegalArgumentException e){
			System.out.println("Your file is not formatted correctly.");
		}
	}

	/**
	 * Decreases the karma of this type by one for the current instance.
	 * @param type A type of post that will get corresponding karma point.
	 */
	public void downvote(PostType type) {
		//TODO
		try{
			if(type == PostType.LINK)
				this.linkKarma--;
			if(type == PostType.COMMENT)
				this.commentKarma--;	
		}
		catch (IllegalArgumentException e){
			System.out.println("Your file is not formatted correctly.");
		}

	}

	/***
	 * Returns the linkKarma associated with the current instance.
	 * 
	 * @return A integer indicating the current value of LinkType karma.
	 */
	public int getLinkKarma() {
		return this.linkKarma;
	}

	/**
	 * Returns the commentKarma associated with the current instance.
	 * @return	A integer indicating the current value of LinkType karma.
	 */
	public int getCommentKarma() {
		return this.commentKarma;
	}
}