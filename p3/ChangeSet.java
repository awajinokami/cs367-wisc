///////////////////////////////////////////////////////////////////////////////
//
//Main Class File:  VersionControlApp.java
//File:             ChangeSet.java
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
 * Represents a set of change(s) made to a repository.
 * @author rickixie
 *
 */
public class ChangeSet {
	
	/* Queue of changes contained within the change set. */
	private final  QueueADT<Change> changes;
	
	/* The name of the repository to which the changes belongs. */
	private final String repoName;
	
	/**
	 * Constructs a change set object. 
	 * @param reponame The name of the repository.
	 * @throws IllegalArgumentException if any argument is null.
	 */
	public ChangeSet(String repoName) {
		// TODO: Implement this method. The following lines 
		// are just meant for the method to compile. You can 
		// remove or edit it in whatever way you like.
		if(repoName==null) throw new IllegalArgumentException();
		this.repoName = repoName;
		this.changes = new SimpleQueue<Change>();
	}
	
	/**
	 * Adds (queues) a new change to the change set.
	 * @param doc The doc to which the change was done.
	 * @param type The type of the change.
	 * @throws IllegalArgumentException if any argument is null.
	 */
	public void addChange(Document doc, Change.Type type) {
		// TODO: Implement this method.
		if(doc==null|type==null) throw new IllegalArgumentException ();
		Change newChange = new Change(doc, type);
		this.changes.enqueue(newChange);//should first check if there is duplication
	}
	
	/**
	 * Returns the repository's name to which this change list belongs.
	 * @return The repository's name.
	 */
	public String getReponame() {
		return this.repoName;
	}
	
	/**
	 * Returns and removes the next change from the change set.
	 * @return The next change if present, null otherwise.
	 * @throws EmptyQueueException if no changes is presented
	 */
	public Change getNextChange() throws EmptyQueueException {
		// TODO: Implement this method. The following lines 
		// are just meant for the method to compile. You can 
		// remove or edit it in whatever way you like.
		
		try{
			 return this.changes.dequeue();
		}
		catch(EmptyQueueException e){
			return null;
		}
	}
	
	/**
	* Returns the count of changes contained in the change set.
	* @return The count of changes.
	*/
	public int getChangeCount() {
		// TODO: Implement this method. The following lines 
		// are just meant for the method to compile. You can 
		// remove or edit it in whatever way you like.
    	return this.changes.size();
	}
	
	@Override
	public String toString() {
		return this.changes.toString();	
	}
}
