///////////////////////////////////////////////////////////////////////////////
//ALL STUDENTS COMPLETE THESE SECTIONS
//Main Class File:  AmazonStore.java
//File:             DLinkedList.java
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
 * The DLinkedList class implements the ListADT interface using a 
 * doubly-linked chain of nodes. 
 * 
 * @author Ricki
 *
 * @param <E> The generic type.
 */
public class DLinkedList <E> implements ListADT<E> {


	private Listnode<E> head;//a head pointer    
	private int numItems;//number of items in the list

	/**
	 * Default constructor.
	 */
	public DLinkedList (){
		head = new Listnode<E>(null);//The DS use a dummy header node
		numItems = 0;//initialized the list
	}

	/**
	 * Adds item to the end of the List.
	 * 
	 * @param item the item to add
	 * @throws IllegalArgumentException if item is null 
	 */
	public void add(E item){
		if(item==null) throw new IllegalArgumentException();
		add(numItems, item);//use the add method below		
	}

	/**
	 * Adds item at position pos in the List, moving the items originally in 
	 * positions pos through size() - 1 one place to the right to make room.
	 * 
	 * @param pos the position at which to add the item
	 * @param item the item to add
	 * @throws IllegalArgumentException if item is null 
	 * @throws IndexOutOfBoundsException if pos is less than 0 or greater 
	 * than size()
	 */
	public void add(int pos, E item){

		if(item==null) throw new IllegalArgumentException();
		//if the position is out of range, throw index exception
		if(pos>numItems || pos<0) throw new IndexOutOfBoundsException();


		//General Case

		Listnode <E> curr = head;//initialize a temporary list node to traverse
		//to the item before the position to add, started with the head
		for(int i=0; i<pos; i++){
			curr = curr.getNext();//since there is a dummy header, no need to
								//skip it since it will go to the pos-1
		}
		//1) Intialized the new node and see next to the node currently at pos;
		//and set the prev to the node that curr is pointing to
		Listnode<E> newNode = new Listnode<E>(item, curr.getNext(),curr);
		//2) Make the item at pos-1 point to the new node
		curr.setNext(newNode);
		//Special Case: if the pos is not the last node, make the item at pos+1
		//(was at pos before the add) point to the new node.
		if(pos!=numItems)
			curr.getNext().getNext().setPrev(newNode);
		//increase the count of list size.
		numItems++;
	}

	/**
	 * Returns true if item is in the List (i.e., there is an item x in the List 
	 * such that x.equals(item))
	 * 
	 * @param item the item to check
	 * @return true if item is in the List, false otherwise
	 * @IllegalArgumentException throws a IllegalArgumentException if the item is null
	 */	
	public boolean contains(E item) {
		if(item==null) throw new IllegalArgumentException();
		Listnode <E> curr =this.head.getNext();//skip the dummy header node
		//Use a while loop to traverse the list
		while(curr!=null){
			if(curr.getData().equals(item))
				return true;//if found
			curr=curr.getNext();//else increment the curr
		}
		return false;//not found
	}


	/**
	 * Returns the item at position pos in the List.
	 * 
	 * @param pos the position of the item to return
	 * @return the item at position pos
	 * @throws IndexOutOfBoundsException if pos is less than 0 or greater than
	 * or equal to size()
	 */
	public E get(int pos) {
		//if the position is out of range, throw index exception
		if(pos>=numItems || pos<0) throw new IndexOutOfBoundsException();
		Listnode <E> curr =this.head;
		for(int i=0; i<pos; i++){
			curr = curr.getNext();
		}
		return curr.getNext().getData();

	}


	/**
	 * Returns true if the List is empty.
	 * 
	 * @return true if the List is empty, false otherwise
	 */
	public boolean isEmpty() {
		return numItems==0;

	}

	/**
	 * Removes and returns the item at position pos in the List, moving the items 
	 * originally in positions pos+1 through size() - 1 one place to the left to 
	 * fill in the gap.
	 * 
	 * @param pos the position at which to remove the item
	 * @return the item at position pos
	 * @throws IndexOutOfBoundsException if pos is less than 0 or greater than
	 * or equal to size()
	 */
	public E remove(int pos) {

		if(pos>=numItems || pos<0) throw new IndexOutOfBoundsException();
		//if the position is out of range, throw index exception
		Listnode <E> curr =this.head;

		//General Case
		for(int i=0; i<pos; i++){
			curr = curr.getNext();	
		}//get to the node before remove

		Listnode <E> toRemove =curr.getNext();//get the node to be removed
		curr.setNext(toRemove.getNext());//set the node before toRemove to the
										//next node; if there is no node after it
										//if will just set to null
		//Special Case: If the position is not the last node;
		if(pos!=numItems-1)
			toRemove.getNext().setPrev(curr);//link the node after it to the node
											//before it
		numItems--;		//decrease the counter
		return toRemove.getData();//return data

	}


	/**
	 * Returns the number of items in the List.
	 * 
	 * @return the number of items in the List
	 */
	public int size() {

		return numItems;
	}
}
