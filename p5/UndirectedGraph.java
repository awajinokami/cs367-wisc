import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
///////////////////////////////////////////////////////////////////////////////
//ALL STUDENTS COMPLETE THESE SECTIONS
//Main Class File:  SocialNetworkingApp.java
//File:             UndirectedGraph.java
//Semester:         CS367 Spring 2015
//
//Author:           Si Xie, sxie27@wisc.edu
//CS Login:         si
//Lecturer's Name:  Jim Skrentny
//
//
////////////////////STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//fully acknowledge and credit all sources of help,
//other than Instructors and TAs.
//
//Persons:          Piazza
//
//Online sources:   n/a
////////////////////////////80 columns wide //////////////////////////////////
/**
 * This class implements GraphADT with an adjacency lists representation. 
 * The graph has the following properties, as enforced by the addEdge() method:
 * 		The graph has no loops, i.e., no vertex has an edge to itself.
 * 		The graph is not a multigraph, i.e., two vertices will not have more 
 * 		than one edge connecting them.
 * To simplify the task of creating adjacency lists, 
 * you will use the Java implementation of HashMap and ArrayList. 
 * Specifically, the graph will be stored in an instance of 
 * HashMap<V, ArrayList<V>>. The Hashmap will map a vertex to 
 * its adjacency list, which in turn is an ArrayList<V>.
 * 
 * NOTE: All methods in this class should handle null arguments where 
 * appropriate. That is, a method should throw an 
 * IllegalArgumentException if any of its arguments is null.
 * 
 * 1. Use the Java ArrayList class for all instances of List. 
 * Hint: an ArrayList acts like a queue data structure if you call add(E item) 
 * and remove(0) to enqueue and dequeue respectively.
 * 
 * 2. Use the Java HashSet class for all instances of Set.
 * 3. Use Collections.sort to sort lists.
 * 
 * @author rickixie
 *
 * @param <V> Generic Type
 */
public class UndirectedGraph<V> implements GraphADT<V>{

	// Stores the vertices of this graph, and their adjacency lists.
	// It's protected rather than private so that subclasses can access it.
    protected HashMap<V, ArrayList<V>> hashmap;

    /**
     * Create a empty graph.
     */
    public UndirectedGraph() {
        this.hashmap = new HashMap<V, ArrayList<V>>();
    }

    /**
     *Creates a graph from a pre-constructed hashmap. 
     *This method will be used for testing your submissions. 
     *You will not find this in a regular ADT. Do not modify it.
     * @param hashmap adjacency lists representation of a graph that has no
     * loops and is not a multigraph.
     */
    public UndirectedGraph(HashMap<V, ArrayList<V>> hashmap) {
        if (hashmap == null) throw new IllegalArgumentException();
        this.hashmap = hashmap;
    }

    /**
	 * Adds the specified vertex to this graph if not already present. 
	 * More formally, adds the specified vertex v to this graph if this graph 
	 * contains no vertex u such that u.equals(v). If this graph already 
	 * contains such vertex, the call leaves this graph unchanged
	 * and returns false.
	 * @param vertex the vertex input to add to the graph
	 * @return true if it is added successfully
	 */
    @Override
    public boolean addVertex(V vertex) {
        //TODO
    	if (vertex==null) throw new IllegalArgumentException();
    	if(this.hashmap.keySet().contains(vertex)){
    		//compare with keySet since it doesn't allow duplicated
    		return false;
    	}else{   		
    		this.hashmap.put(vertex, new ArrayList<V>());
    		return true;
    	}       
    }

    /**
     * Creates a new edge between vertices v1 and v2 and returns true, 
     * if v1.equals(v2) evaluates to false and an edge does not already exist 
     * between v1 and v2. Returns false otherwise. 
     * Vertices v1 and v2 must already exist in this graph. If they are not 
     * found in the graph IllegalArgumentException is thrown.
     * @param v1 a vertex to connect with
     * @param v2 another vertex to link to v1
     * @return true if the edge is added successfully
     */
    @Override
    public boolean addEdge(V v1, V v2) {
        //TODO
    	if (v1==null||v2==null||!this.hashmap.containsKey(v1)||!this.hashmap.containsKey(v2)) 
    		throw new IllegalArgumentException();
    	//parameters are null + v1 or v2 does not exist in the graph
    	if(!v1.equals(v2)&&!this.hashmap.get(v1).contains(v2)){
    		//if v1 != v2 and no edges found between v2 and v1
    		this.hashmap.get(v1).add(v2);
    		this.hashmap.get(v2).add(v1);
    		return true;//1. return true if a new edge is created
    	}
    	else
    		return false;//2. return false if 
    			//v1.equals(v2) or an edge already exists between v1 and v2
    }
    /**
     * Returns a set of all vertices adjacent to v. 
     * Vertex v must already exist in this graph. If it is not 
     * found in the graph IllegalArgumentException is thrown.
     * @param vertex the vertex parameter to get neighbor vertices from.
     * @return
     */
    @Override
    public Set<V> getNeighbors(V vertex) {
        //TODO
    	if (vertex==null||!this.getAllVertices().contains(vertex)) 
    		throw new IllegalArgumentException();
        ArrayList<V> getSet = this.hashmap.get(vertex);//else prepare the list
    	Set<V> newSet = new HashSet <V>();//since it is to return a set, 
    							//not a array-list as constructor initialized
    	for(int i=0; i<getSet.size();i++){
    		newSet.add(getSet.get(i));
    	}
    	return newSet;
    }
    /**
     * If both v1 and v2 exist in the graph, and an edge exists between v1 
     * and v2, remove the edge from this graph. Otherwise, do nothing
     * @param v1 a vertex to remove edge from
     * @param v2 another vetex to remove edge from
     */
    @Override
    public void removeEdge(V v1, V v2) {
    	
    	/*<NOTES from Piazza>
    	 * Hello,
    	 * The description of the method removeEdge says 
    	 * "If both v1 and v2 exist in the graph, and an edge 
    	 * exists between v1 and v2, remove the edge from this graph.
    	 * Otherwise, do nothing." To keep symmetry with the addEdge, 
    	 * shouldn't it throw an exception if v1 and v2 are the same 
    	 * vertex? In addition, if the edge doesn't exist, 
    	 * I think it could return false (if it were a boolean method) 
    	 * likewise the addEdge does when the edge already exists.
    	 * 
    	 * Is there any strong motivation for it has a different approach than addEdge?
    	 */
 
    	/*
    	 * Oh great, we seldom get why questions on Piazza. As you've described, 
    	 * there are many choices to make when designing an ADT. In some cases, 
    	 * there are clearly good/bad choices, while in others it's a matter of taste.
    	 * In this assignment, (as written in the handout) the ADT was inspired 
    	 * by design choices made by JGraphT. The behaviors of addEdge and 
    	 * removeEdge are almost exactly the same except for one aspect - 
    	 * JGraphT's removeEdge returns an edge while our method is void. 
    	 * That (partially) explains why it's not Boolean. A void method 
    	 * would be backward compatible if in the future we decide to include 
    	 * arbitrary edges in our ADT, while a Boolean method would not.
    	 * As for why we don't throw IllegalArgumentException when the 
    	 * arguments are unexpected, obviously we're implying that 
    	 * such arguments are not illegal. Kind of "you can keep trying 
    	 * different vertices but the method will fail to remove edges 
    	 * silently". I'd say this choice is a matter of taste.
    	 */
        //TODO
    	if (v1==null||v2==null) throw new IllegalArgumentException();
    	if(!v1.equals(v2)&&this.hashmap.get(v1).contains(v2)){
    		
    	
    		this.hashmap.get(v1).remove(v2);
    		this.hashmap.get(v2).remove(v1);
    		
    	}
    
    }
    /**
     * Returns a set of all the vertices in the graph.
     * @return all of the vertices in the graph
     */
    @Override
    public Set<V> getAllVertices() {
        //TODO
        Set <V> setReturn = new HashSet<V>();
        ArrayList <V> getAllKeys = new ArrayList<V>(this.hashmap.keySet());

        for(int i =0; i<getAllKeys.size();i++){
        	setReturn.add(getAllKeys.get(i));
        }       
        return setReturn;
    }

    /* (non-Javadoc)
     * Returns a print of this graph in adjacency lists form.
     * 
     * This method has been written for your convenience (e.g., for debugging).
     * You are free to modify it or remove the method entirely.
     * THIS METHOD WILL NOT BE PART OF GRADING.
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringWriter writer = new StringWriter();
        for (V vertex: this.hashmap.keySet()) {
            writer.append(vertex + " -> " + hashmap.get(vertex) + "\n");
        }
        return writer.toString();
    }

}
