///////////////////////////////////////////////////////////////////////////////
//
//Main Class File:  VersionControlApp.java
//File:             SimpleStack.java
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
 * This class implements StackADT using a simple array.
 * @author rickixie
 *
 * @param <E> generic type.
 */
public class SimpleStack <E> implements StackADT<E>{

	private E items [];
	private int numItems;
	private static final int INITSIZE = 10;
	
	
	/**
	 * Default constructor
	 */
	@SuppressWarnings("unchecked")
	public SimpleStack() {
	    items = (E[])(new Object[INITSIZE]);
	   // items = new E[INITSIZE];
	    numItems = 0;
	}
	/**
     * Checks if the stack is empty.
     * @return true if stack is empty; otherwise false
     */
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return numItems==0;
	}

	/**
     * Returns (but does not remove) the top item of the stack.
     * @return the top item of the stack
     * @throws EmptyStackException if the stack is empty
     */
	public E peek() throws EmptyStackException {
		// TODO Auto-generated method stub
		if(numItems==0) throw new EmptyStackException();
		
		return items[numItems-1];
	}

	/**
     * Pops the top item off the stack and returns it. 
     * @return the top item of the stack
     * @throws EmptyStackException if the stack is empty
     */
	public E pop() throws EmptyStackException {
		// TODO Auto-generated method stub
		if(numItems==0) throw new EmptyStackException();
		else{
			numItems--;
			return items[numItems];
		}
	
	}
	/**
     * Pushes the given item onto the top of the stack.
     * @param item the item to push onto the stack
     * @throws IllegalArgumentException if item is null.
     */
	@SuppressWarnings("unchecked")
	public void push(E item) {
		// TODO Auto-generated method stub
		if(item==null) throw new IllegalArgumentException();
		if(items.length==numItems){
			E[] tmp = (E[])(new Object[items.length*2]);
			System.arraycopy(items, 0, tmp, 0, numItems);
			items = tmp;
		}
		//NEED TO EXPAND THE ARRAY IF FULL
		items[numItems] = item;
		numItems++;
	}

	/**
     * Returns the size of the stack.
     * @return the size of the stack
     */
	public int size() {
		// TODO Auto-generated method stub
		return numItems;
	}
    /**
     * Returns a string representation of the stack (for printing).
     * @return a string representation of the stack
     */
    public String toString(){
    	String toReturn = "";
    	for(int i = numItems-1; i>=0; i--){
    		toReturn += items[i]+"\n";
    	}
		return toReturn;
    	
}

}
