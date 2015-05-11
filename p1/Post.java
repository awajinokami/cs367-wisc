package p1;

///////////////////////////////////////////////////////////////////////////////
//ALL STUDENTS COMPLETE THESE SECTIONS
//Main Class File:  Reddit.java
//File:             Post.java
//Semester:         CS302 Spring 2015
//
//Author:           sxie27@wisc.edu
//CS Login:         si
//Lecturer's Name:  Jim Skrentny
//Lecture Section:  Section 2
//
//
////////////////////STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//fully acknowledge and credit all sources of help,
//other than Instructors and TAs.
//
//Persons:         	n/a
//
//Online sources:   n/a
////////////////////////////80 columns wide //////////////////////////////////

/***
 * The Post class represents a single post that keeps track of the user 
 * who posted it, the subreddit it was posted to, the type of the post 
 * (of type PostType, also provided), the title of the post and the karma
 * (points) received by it. 
 * 
 * @author rickixie
 *
 */
public class Post {
	final private User user;
	final private String subreddit;
	final private PostType type;
	final private String title;
	private int karma;

	/**
	 * Constructs a post with the specified attributes as applicable.
	 * A newly created post should be assigned a karma of zero.
	 * 
	 * @param user The user object who submit the post.
	 * @param subreddit The subreddit name that the post belongs to.
	 * @param type The type of post.
	 * @param title	The type of post in string format.
	 */
	public Post(User user, String subreddit, PostType type, String title) {
		//TODO
		this.user = user;
		this.subreddit = subreddit;
		this.type = type;
		this.title = title;
		this.karma = 0;
	}

	/**
	 * Increases the karma of this post and the relevant karma 
	 * of the user who created the post by two each.
	 */
	public void upvote() {
		//TODO
		this.karma = this.karma+2;
		this.user.getKarma().upvote(this.getType());//both increase by two
	}

	/**
	 * Decreases the karma of this post and the relevant karma of the 
	 * user who created the post by one each.
	 */
	public void downvote() {
		//TODO
		this.karma --;
		this.user.getKarma().downvote(this.getType());//both decrease by one
	}

	/**
	 * Returns the user who created this post.
	 * @return A user object who submit the post.
	 */
	public User getUser() {
		return this.user;
	}

	/**
	 * Returns the subreddit this was posted to.
	 * 
	 * @return A string contains the subreddit name.
	 */
	public String getSubreddit() {
		return this.subreddit;
	}
	
	/**
	 * Returns the type of the post.
	 * @return The PostType of the particular post object.
	 */
	public PostType getType() {
		return this.type;
	}

	/**
	 * Returns the title of the post.
	 * @return A string of the post title.
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Returns the karma aggregated by the post.
	 * @return The points of a particular post.
	 */
	public int getKarma() {
		return this.karma;
	}
}