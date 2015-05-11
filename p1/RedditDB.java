package p1;
///////////////////////////////////////////////////////////////////////////////
//ALL STUDENTS COMPLETE THESE SECTIONS
//Main Class File:  Reddit.java
//File:             RedditDB.java
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



import java.util.List;
import java.util.ArrayList;

/**
 * The RedditDB class stores a List of all the user information 
 * and has the constructor and methods below.
 * @author rickixie
 *
 */
public class RedditDB {
	private List<User> users;
	/**
	 * Constructs an empty database
	 */
	public RedditDB() {
		this.users = new ArrayList<User>();
	}
	/**
	 * Returns a copy of the list of users; the list itself should not 
	 * be exposed.
	 * 
	 * @return A list of user from the reddit database.
	 */
	public List<User> getUsers() {
		//TODO
		List<User> userList = this.users;
		return userList;
	}

	/**
	 * If a user with the given name does not already exist, add a user 
	 * with the given name to the database and return it; else simply 
	 * return null.
	 * 
	 * @param name The name for the user
	 * @return Constructed user object using the name parameter.
	 */
	public User addUser(String name) {
		//TODO
		if(!users.contains(name)){
			User newUser = new User(name);
			users.add(newUser);
			return newUser;	
		}
		else
			return null;
	}

	/**
	 * Search the database for a user with the given name and return the 
	 * information if found; else return null.
	 * 
	 * @param name The user name to search from the user list.
	 * @return Return the user that is found in the list. 
	 * If the user is not found, return null.
	 */
	public User findUser(String name) throws NullPointerException {
		//TODO
		//Initialize a user instance to copy the user information. 
		User userToReturn = null;
		
			for(int i=0; i<users.size(); i++){
				if(users.get(i).getName().equals(name)){
					//copy the information from that user and 
					//point to that reference
					userToReturn = users.get(i);
				}	
			}
		return userToReturn;
	}

	/**
	 * Search the database for a user with the given name and delete the 
	 * information and return true if found; else return false. Deletion also 
	 * involves undoing all information related to the user: (i) For each 
	 * posted post, remove it from the liked and disliked information of all 
	 * users. (ii) Undo all likes. (iii) Undo all dislikes.
	 * @param name The user name to search from the user list
	 * @return A boolean to indicate if deleting the user is successful.
	 */
	public boolean delUser(String name) {
		//TODO
		boolean userExist;

		if (users.contains(this.findUser(name))){	
			userExist = true;
		
			//undo like of this user
			for(int i = 0; i< this.findUser(name).getLiked().size(); i++){
				this.findUser(name).getLiked().get(i).downvote();
				this.findUser(name).getLiked().get(i).downvote();
			}
			//undo all the dislikes
			for(int j = 0; j< this.findUser(name).getDisliked().size(); j++){
				this.findUser(name).getDisliked().get(j).downvote();
				this.findUser(name).getDisliked().get(j).upvote();
				
			}
			// (i) For each posted post, remove it from the liked and disliked 
			//information of all users. 
			for(int i=0; i<this.findUser(name).getPosted().size(); i++){
				for(int j=0; j<this.getUsers().size(); j++){
					//undo like of all the user that like the delUser's post
					if(this.getUsers().get(j).getLiked().contains(this.findUser(name).getPosted().get(i))){
						this.getUsers().get(j).undoLike(this.findUser(name).getPosted().get(i));
					}
					//undo dislike of all the user that like the delUser's post
					if(this.getUsers().get(j).getDisliked().contains(this.findUser(name).getPosted().get(i))){
						this.getUsers().get(j).undoDislike(this.findUser(name).getPosted().get(i));
					}
				}
			}
			//finally delete the user	
			users.remove(this.findUser(name));
		}
		else
			userExist = false;
		return userExist;
	}

	/**
	 * Get all the posts to be displayed on the front page of a particular user 
	 * and return them. If the user is null this is simply all posts from all 
	 * users. If a user is specified, return all the posts from the user's 
	 * subscribed subreddits only; posts which have previously been liked or 
	 * disliked by the user should not be returned, except when the post was 
	 * created by the user themselves.
	 * 
	 * @param user The user who request for the front page.
	 * @return A List of post corresponding to that user.
	 */
	public List<Post> getFrontpage(User user) {
		//TODO
		List <Post> getAllUserPost = new ArrayList<Post>();
		List <Post> toReturn = new ArrayList<Post>();
		for(int i=0; i<users.size(); i++){
			for(int j = 0; j<users.get(i).getPosted().size(); j++)
				getAllUserPost.add(users.get(i).getPosted().get(j));
		}				
		
		//if the user is not specified
		if(user==null){
			toReturn = getAllUserPost;
		}	
		//the front page should only shows post by the users as well as 
		//belongs to the subscribed reddit
		else{
			for(int i=0; i<getAllUserPost.size(); i++){
				for(int j=0; j<user.getSubscribed().size(); j++){
					if(getAllUserPost.get(i).getSubreddit().equals(user.getSubscribed().get(j))){
						if(getAllUserPost.get(i).getUser()==user
								||(!user.getLiked().contains(getAllUserPost.get(i))
								&&!user.getDisliked().contains(getAllUserPost.get(i))))
							toReturn.add(getAllUserPost.get(i));
					}		
				}
			}
		}
		return toReturn;	
	}

	/**
	 * Get all the posts from the specified subreddit to be displayed on the 
	 * front page of a particular user and return them. If the user is null 
	 * this is simply all relevant posts from all users. If a user is 
	 * specified, return all the posts from the subreddit which have not 
	 * previously been liked or disliked by the user ,except when the post 
	 * was created by the user themselves.
	 * 
	 * @param user	The user who request for the front page
	 * @param subreddit The reddit page that is requested to display
	 * @return A List of post corresponding to that user and subreddt.
	 */
	public List<Post> getFrontpage(User user, String subreddit) {
		//TODO
		List <Post> getAllUserPost = new ArrayList<Post>();
		List <Post> toReturn = new ArrayList<Post>();

		//The reddit class will catch subreddit not exist
		if(user == null){
			getAllUserPost = getFrontpage(null);
		}
		else{
			getAllUserPost =getFrontpage(user);
		}
		for(int i = 0; i<getAllUserPost.size(); i++){
			if(getAllUserPost.get(i).getSubreddit().equalsIgnoreCase(subreddit))
				toReturn.add(getAllUserPost.get(i));
		}
		return toReturn;	

	}
}
