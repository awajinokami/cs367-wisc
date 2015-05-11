///////////////////////////////////////////////////////////////////////////////
//
//Main Class File:  VersionControlApp.java
//File:             SimpleQueue.java
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
//Online sources:   http://faculty.washington.edu/moishe/javademos/ch07%20Code/
//												jss2/CircularArrayQueue.java
////////////////////////////80 columns wide //////////////////////////////////
/**
 * This class implements QueueADT using a circular array.
 * @author rickixie
 *
 * @param <E> generic type.
 */
public class SimpleQueue <E> implements QueueADT<E> {

	private E items [];
	private int numItems;
	private int frontIndex;
	private int rearIndex;
	private static final int INITSIZE = 10;



	/**
	 * Default constructor
	 */
	@SuppressWarnings("unchecked")
	public SimpleQueue() {
		items = (E[])(new Object[INITSIZE]);
		numItems = 0;
		frontIndex = 0;
		rearIndex = 0;
	}
	/**
	 * Checks if the queue is empty.
	 * @return true if queue is empty; otherwise false.
	 */
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return numItems==0;
	}

	/**
	 * removes and returns the front item of the queue.
	 * @return the front item of the queue.
	 * @throws EmptyQueueException if the queue is empty.
	 */
	public E dequeue() throws EmptyQueueException {
		// TODO Auto-generated method stub
		if(numItems==0) throw new EmptyQueueException();
		//The dequeue method will also use method incrementIndex 
		//to add one to frontIndex (with wrap-around) before returning 
		//the value that was at the front of the queue.
		E tmp = items[frontIndex];
//		System.out.println("rearIndex: "+rearIndex);
		items[frontIndex]=null;
		frontIndex = incrementIndex(frontIndex);
		numItems--;
		return tmp;
	}

	/**
	 * Adds an item to the rear of the queue.
	 * @param item the item to add to the queue.
	 * @throws IllegalArgumentException if item is null.
	 */
	@SuppressWarnings("unchecked")
	public void enqueue(E item) {
		// TODO Auto-generated method stub
		if(item==null) throw new IllegalArgumentException();

		//FOLLOWING ARE REVISING FROM THE ONLINE NOTES ON STACK AND ARRAY.
		// check for full array and expand if necessary
		if (items.length == numItems) {
			E[] tmp = (E[])(new Object[items.length*2]);
			System.arraycopy(items, frontIndex, tmp, frontIndex,
					items.length-frontIndex);
			if (frontIndex != 0) {
				System.arraycopy(items, 0, tmp, items.length, frontIndex);
			}
			items = tmp;
			rearIndex = frontIndex + numItems - 1;
		
		}
		
		
		// insert new item at rear of queue	
		items[rearIndex] = item;
		// use auxiliary method to increment rear index with wraparound	
		rearIndex = incrementIndex(rearIndex);
		
		//rearIndex = (rearIndex + 1) % items.length;

			
		numItems++;
	}
	private int incrementIndex(int index) {
		if (index == items.length-1) 
			return 0;
		else 
			return index + 1;
	}

	/**
	 * Returns (but does not remove) the front item of the queue.
	 * @return the front item of the queue.
	 * @throws EmptyQueueException if the queue is empty.
	 */
	public E peek() throws EmptyQueueException {
		// TODO Auto-generated method stub
		if(isEmpty()) throw new EmptyQueueException();

		return items[frontIndex];
	}

	/**
	 * Returns the size of the queue.
	 * @return the size of the queue
	 */
	public int size() {
		// TODO Auto-generated method stub
		return numItems;
	}
	/**
	 * Returns a string representation of the queue (for printing).
	 * @return a string representation of the queue.
	 */
	public String toString(){
		String toReturn = "";
		int scan = 0;
		while(scan<numItems){   	
			if(items[scan]!=null){
				toReturn += items[scan].toString()+"\n";
			}
			scan++;
		}
		//print method: http://faculty.washington.edu/moishe/javademos/ch07%20Code/jss2/CircularArrayQueue.java
		return toReturn;
	}

}
