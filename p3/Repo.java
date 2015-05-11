///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
//
//Main Class File:  VersionControlApp.java
//File:             Repo.java
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
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a repository which stores and tracks changes to a collection of 
 * documents.
 * @author rickixie
 *
 */
public class Repo {

	/* The current version of the repo. Initial version is 0 for a empty repo. */
	private int version;

	/* The name of the repo. It's a unique identifier for a repository. */
	private final String repoName;

	/* The user who is the administrator of the repo. */
	private final User admin;

	/* The collection(list) of documents in the repo. */
	private final List<Document> docs;

	/* The check-ins queued by different users for admin approval. */

	private final QueueADT<ChangeSet> checkIns; 

	/* The stack of copies of the repo at points when any check-in was applied. */
	/* The copies of different versions of repo at points when any check-in was applied. 
	The top of this stack should always contain a copy of current version of the 
	repo and the bottom of the stack should contain a copy of oldest version of 
	the repo (i.e. version 0).*/
	private final StackADT<RepoCopy> versionRecords; 

	/**
	 * Constructs a repo object.
	 * @param admin The administrator for the repo.
	 * @param reponame The name of the repo.
	 * @throws IllegalArgumentException if any argument is null. 
	 */
	public Repo(User admin, String repoName) {
		// TODO: Implement this contructor. The following lines 
		// are just meant for the method to compile. You should 
		// remove or edit it in whatever way you like.
		this.admin = admin;
		this.repoName =  repoName;
		this.docs =  new ArrayList<Document>(); 
		//this.docs.add(new Document("","",""));
		this.checkIns =  new SimpleQueue<ChangeSet>();
		this.versionRecords =  new SimpleStack<RepoCopy>();
		this.version = 0;
		this.versionRecords.push(new RepoCopy(repoName, version, docs));	
	}

	/**
	 * Return the name of the repo.
	 * @return The name of the repository.
	 */
	public String getName() {
		return this.repoName;
	}

	/**
	 * Returns the user who is administrator for this repository.
	 * @return The admin user.
	 */
	public User getAdmin() {
		return this.admin;
	}

	/**
	 * Returns a copy of list of all documents in the repository.
	 * @return A list of documents.
	 */
	public List<Document> getDocuments() {
		return new ArrayList<Document>(this.docs);
	}

	/**
	 * Returns a document with a particular name within the repository.
	 * @param searchName The name of document to be searched.
	 * @return The document if found, null otherwise.
	 * @throws IllegalArgumentException if any argument is null.
	 */
	public Document getDocument(String searchName) {
		if (searchName == null) {
			throw new IllegalArgumentException();
		}

		for (Document d : this.docs) {
			if (d.getName().equals(searchName)) {
				return d;
			}
		}

		return null;
	}

	/**
	 * Returns the current version of the repository.
	 * @return The version of the repository.
	 */
	public int getVersion() {
		return this.version;
	}

	/**
	 * Returns the number of versions (or changes made) for this repository.
	 * @return The version count.
	 */
	public int getVersionCount() {
		//Returns the number of records stored in the versionRecords stack. 
		// TODO: Implement this method. The following lines 
		// are just meant for the method to compile. You can 
		// remove or edit it in whatever way you like.
		return this.versionRecords.size();
	}

	/**
	 * Returns the history of changes made to the repository. 
	 * @return The string containing the history of changes.
	 */
	public String getVersionHistory() {
		// TODO: Implement this method. The following lines 
		// are just meant for the method to compile. You can 
		// remove or edit it in whatever way you like.

			/*
			 * Each RepoCopy object in the versionRecords stack denotes a version of the Repo. 
			 * A RepoCopy stores a version number and the entire set of documents 
			 * that were there in the Repo when the Repo had that version number. 
			 * This information is enough to represent a version of Repo.
			 * getVersionHistory() should return the elements(i.e. RepoCopy objects) 
			 * in the versionRecords stack in string format. 
			 * For this you can simply return the string representation of your versionRecords stack. 
			 * Also, "vh" command in the VersionControlApp uses this method. 
			 * You can refer to its output in the sample runs to see exactly what this method returns.
			 * 
			 */
			/*
			 * Current version should be at the top and Version 0 should be at bottom.
			 * Hint: You can use the toString() method of SimpleStack to get the string 
			 * representation for versionRecords instead of re-implementing the logic again.
			 */
		//}
		
		return this.versionRecords.toString();
	}

	/**
	 * Returns the number of pending check-ins queued for approval.
	 * @return The count of changes.
	 */
	public int getCheckInCount() {
		// TODO: Implement this method. The following lines 
		// are just meant for the method to compile. You can 
		// remove or edit it in whatever way you like.
		return this.checkIns.size();
	}


	/**
	 * Queue a new check-in for admin approval.
	 * @param checkIn The check-in to be queued.
	 * @throws IllegalArgumentException if any argument is null. 
	 */
	public void queueCheckIn(ChangeSet checkIn) {
		// TODO: Implement this method. 
		if (checkIn==null) throw new IllegalArgumentException ();
		this.checkIns.enqueue(checkIn);
	}

	/**
	 * Returns and removes the next check-in in the queue 
	 * if the requesting user is the administrator.
	 * @param requestingUser The user requesting for the change set.
	 * @return The checkin if the requestingUser is the admin and a checkin
	 * exists, null otherwise.
	 * @throws IllegalArgumentException if any argument is null. 
	 */
	public ChangeSet getNextCheckIn(User requestingUser) {
		// TODO: Implement this method. The following lines 
		// are just meant for the method to compile. You can 
		// remove or edit it in whatever way you like.
		if (requestingUser==null) throw new IllegalArgumentException ();

		//If requestingUser is not admin, or there is no pending check-in, retunn null.
		if(requestingUser!=this.getAdmin()) return null;
		//A user who creates a repository is the administrator of that repository and can delete it.
		else{
			try {
				return this.checkIns.dequeue();

				//return next;
			} catch (EmptyQueueException e) {
				return null;//no pending checkin
			}
		}
	}

	/**
	 * Applies the changes contained in a particular checkIn and adds
	 * it to the repository if the requesting user is the administrator.
	 * Also saves a copy of changed repository in the versionRecords.
	 * @param requestingUser The user requesting the approval.
	 * @param checkIn The checkIn to approve.
	 * @return ACCESS_DENIED if requestingUser is not the admin, SUCCESS 
	 * otherwise.
	 * @throws IllegalArgumentException if any argument is null. 
	 */
	public ErrorType approveCheckIn(User requestingUser, ChangeSet checkIn) {
		// TODO: Implement this method. The following lines 
		// are just meant for the method to compile. You can 
		// remove or edit it whatever way you like.
		if (requestingUser==null||checkIn==null) throw new IllegalArgumentException ();
		if(requestingUser!=this.getAdmin()) return ErrorType.ACCESS_DENIED;
		else{			
				while(checkIn.getChangeCount()!=0){
					try {
						Change toApply = checkIn.getNextChange();
						switch (toApply.getType()){
						case ADD:
							this.docs.add(toApply.getDoc());		
							break;
						case EDIT:
							this.getDocument(toApply.getDoc().getName()).setContent(toApply.getDoc().getContent());;
							break;
						case DEL:
							//this.docs.remove(toApply.getDoc());
							for(int i=0; i<this.docs.size(); i++){
								if(this.docs.get(i).getName().equals(toApply.getDoc().getName())){
									this.docs.remove(i);
								}
							}
							break;			
						}						
					} catch (EmptyQueueException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace(); DO NOTHING?
					}
				}
				this.version++;
				versionRecords.push(new RepoCopy(repoName, this.version, docs));
				return ErrorType.SUCCESS;	
		}		
	}

	/**
	 * Reverts the repository to the previous version if present version is
	 * not the oldest version and the requesting user is the administrator.
	 * @param requestingUser The user requesting the revert.
	 * @return ACCESS_DENIED if requestingUser is not the admin, 
	 * NO_OLDER_VERSION if the present version is the oldest version, SUCCESS 
	 * otherwise.
	 * @throws IllegalArgumentException if any argument is null. 
	 */
	public ErrorType revert(User requestingUser) {
		// TODO: Implement this method. The following lines 
		// are just meant for the method to compile. You can 
		// remove or edit it whatever way you like.
		if (requestingUser==null) throw new IllegalArgumentException ();
		if(requestingUser!=this.getAdmin()) return ErrorType.ACCESS_DENIED;
		if(this.getVersion()==0) return ErrorType.NO_OLDER_VERSION;
		else{
			this.docs.clear();
			try {
				this.versionRecords.pop(); 
				List<Document> old = new ArrayList<Document> (this.versionRecords.peek().getDocuments());
				for(int i=0; i<old.size();i++){
					this.docs.add(old.get(i));
				}
				this.version--;
			} catch (EmptyStackException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace(); DO NOTHING. This is the same with 
				//NO_OLDER_VERSION if it is caught. 
				//at the beginning.
			}
			return ErrorType.SUCCESS;
		}
		//return null;
	}
}
