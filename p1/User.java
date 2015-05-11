package p1;
///////////////////////////////////////////////////////////////////////////////
//ALL STUDENTS COMPLETE THESE SECTIONS
//Main Class File:  Reddit.java
//File:             User.java
//Semester:         CS302 Spring 2015
//
//Author:           sxie27@wisc.edu
//CS Login:         si
//Lecturer's Name:  Jim Skrentny
//Lecture Section:	Section 2
//
////////////////////STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//fully acknowledge and credit all sources of help,
//other than Instructors and TAs.
//
//Persons:          n/a
//
//Online sources:   n/a
////////////////////////////80 columns wide //////////////////////////////////
import java.util.List;
import java.util.ArrayList;
/**
 * The User class represents the data associated with a single user. 
 * The class will store the name of the user as a String, the karma as an 
 * object of type Karma, a List<String> denoting the subreddits the user is 
 * subscribed to and different List<String>s which store information regarding 
 * which posts were posted, liked or disliked by the user. 
 * @author rickixie
 *
 */
public class User {
	final private String name;
	final private Karma karma;
	private List<String> subscribed;
	private List<Post> posted;
	private List<Post> liked;
	private List<Post> disliked;

	/**
	 * Constructor: Creates an instance of the User class with the specified 
	 * name and a newly initialized instance of Karma. The Lists will be 
	 * initialized to new ArrayLists of the relevant types as applicable.
	 * @param name
	 */
	public User(String name) {
		//TODO
		this.name = name;
		this.karma = new Karma ();
		this.subscribed = new ArrayList <String> ();
		this.posted = new ArrayList <Post> ();
		this.liked = new ArrayList <Post> ();
		this.disliked = new ArrayList <Post> ();
	}

	/**
	 * Return the name of the current user.
	 * @return The name of user in string.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns the karma of the current user.
	 * @return The karam of the user.
	 */
	public Karma getKarma() {
		return this.karma;
	}

	/**
	 * Return a copy of the list of subreddits' name the users is subscribed to.
	 * @return a list of subscribed subreddits's name.
	 */
	public List<String> getSubscribed() {
		//TODO
		List<String> userSubscribed = this.subscribed; 
		return userSubscribed;
	}

	/**
	 * Return a copy of the list of posts by the user.
	 * @return a list of posts posted by the user.
	 */
	public List<Post> getPosted() {
		//TODO
		List<Post> userPosted = this.posted; 
		return userPosted;
	}

	/**
	 * Return a copy of the list of posts that is liked by the user.
	 * @return a list of posts liked by the user.
	 */
	public List<Post> getLiked() {
		//TODO
		List<Post> userLiked = this.liked; 
		return userLiked;
	}


	/**
	 * Return a copy of the lists of posts that is disliked by the user.
	 * @return a list of posts that is disliked by the user.
	 */
	public List<Post> getDisliked() {
		//TODO
		List<Post> userDisiked = this.disliked; 
		return userDisiked;
	}

	/**
	 * Add the specified subreddit to the List of subscribed subreddits if the 
	 * user is not already subscribed. If the user is already subscribed, 
	 * unsubscribe from the subreddit.
	 * @param subreddit the name of the subreddit that the user is going to
	 * subscribe to.
	 */
	public void subscribe(String subreddit) {
		//TODO
		if(this.subscribed.contains(subreddit))
			this.unsubscribe(subreddit);
		else
			this.subscribed.add(subreddit);
	}

	/**
	 * Remove the specified subreddit from the List of subscribed subreddits 
	 * if present; if not, do nothing.
	 * @param subreddit the name of the subreddit that the user is going to
	 * unsubscribe to.
	 */
	public void unsubscribe(String subreddit) {
		//TODO
		if(this.subscribed.contains(subreddit))
			this.subscribed.remove(subreddit);
	}


	/**
	 * Instantiate a new post with the appropriate parameters and add it to 
	 * the list of posts posted by the user.
	 * 
	 * @param subreddit The subreddit that the post is submitting to.
	 * @param type The type of post that the user is post as.
	 * @param title The title of the post.
	 * @return return a type of post.
	 */
	public Post addPost(String subreddit, PostType type, String title) {
		//TODO
		//This means the user, instantiate the posts by calling the constructor
		//from the post class.
		Post newPost = new Post (this, subreddit, type, title);
		//Add it to the list of post that is posted by the user
		this.posted.add(newPost);
		//According to the specifications: "A user should automatically 
		//upvote (like) his own post when it is created."
		this.like(newPost);
		return newPost;
	}

	/**
	 * Upvote the post and add it to the List of liked posts if not already 
	 * liked; else undo the like. If the post is currently disliked by the user,
	 * the dislike should be undone.
	 * 
	 * @param post the post parameter to like
	 */
	public void like(Post post) {
		//TODO
		if(this.liked.contains(post)){
			this.undoLike(post);
		}
		else{ 
			if(this.disliked.contains(post)){
				this.undoDislike(post);
			}		
			post.upvote();			
			this.liked.add(post);
		}
	}

	/**
	 * Remove the post from the list of liked posts and update its 
	 * karma appropriately.
	 * 
	 * @param post the post parameter to undolike
	 */
	public void undoLike(Post post) {
		//TODO
		//Since the upvote receive two karma, the code will downvote twice
		//in order to get zero balance.
		post.downvote();
		post.downvote();
		this.liked.remove(post);
		
	}
	
	/**
	 * Downvote the post and add it to the List of disliked posts if not 
	 * already disliked; else undo the dislike.If the post is currently liked 
	 * by the user, the like should be undone.
	 * 
	 * @param post A post that the user dislike
	 */
	public void dislike(Post post) {
		//TODO
		if(this.disliked.contains(post)){
			this.undoDislike(post);
		}
		else{ 
			if (this.liked.contains(post)){
				this.undoLike(post);
			}
				this.disliked.add(post);
				post.downvote();
		}
	}
	
	/**
	 * Remove the post from the list of disliked posts and update 
	 * its karma appropriately.
	 * @param post post the post parameter to undo dislike
	 */
	public void undoDislike(Post post) {
		//TODO
		//since the upvote will have two karma increase, so the code has to
		//decrease one in order to get zero balance.
		post.downvote();
		post.upvote();
		this.disliked.remove(post);
	}
}