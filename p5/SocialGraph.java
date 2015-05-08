import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
///////////////////////////////////////////////////////////////////////////////
//ALL STUDENTS COMPLETE THESE SECTIONS
//Main Class File:  SocialNetworkingApp.java
//File:             SocialGraph.java
//Semester:         CS367 Spring 2015
//
//Author:           Si Xie, sxie27@wisc.edu
//CS Login:         si
//Lecturer's Name:  Jim Skrentny
//
//
////////////////////STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//					fully acknowledge and credit all sources of help,
//					other than Instructors and TAs.
//
//Persons:          Piazza
//
//Online sources:   n/a
////////////////////////////80 columns wide //////////////////////////////////
/**
 * This class represents a simplified social network. 
 * It extends UndirectedGraph<String> with methods specific 
 * to social networking. See the FAIAQ below to find out what 
 * "extends" means.
 * 
 * NOTE: All methods in this class should handle null arguments where 
 * appropriate. That is, a method should throw an IllegalArgumentException 
 * if any of its arguments is null. 
 * @author rickixie
 *
 */
public class SocialGraph extends UndirectedGraph<String> {

    /**
     * Creates an empty social graph.
     * 
     * DO NOT MODIFY THIS CONSTRUCTOR.
     */
    public SocialGraph() {
        super();
    }

    /**
     * Creates a graph from a preconstructed hashmap.
     * This method will be used to test your submissions. You will not find this
     * in a regular ADT.
     * 
     * DO NOT MODIFY THIS CONSTRUCTOR.
     * DO NOT CALL THIS CONSTRUCTOR ANYWHERE IN YOUR SUBMISSION.
     * 
     * @param hashmap adjacency lists representation of a graph that has no
     * loops and is not a multigraph.
     */
    public SocialGraph(HashMap<String, ArrayList<String>> hashmap) {
        super(hashmap);
    }
    
    /**
     * Returns a set of 2-degree friends of person, if person exists in 
     * this graph. Otherwise, throws IllegalArgumentException.
     * (See note below this table)
     * @param person the person to search friend from
     * @return a list of 2-degree friends of the input person parameter
     */
    public Set<String> friendsOfFriends(String person) {
        //TODO
    	return getVerticesAtDepthN(person, 2);
    }


    /**
     * A revised version of breath-first search to add vertices in N degree from
     * the inputed vertex. It returns all vertices in the graph that have a 
     * shortest path of length N from start 
     * @param start The start point of the BFS
     * @param N Degree of breath to return
     * @return A set of of vertices at degree N
     */
    private Set<String> getVerticesAtDepthN(String start, int N){

    
    	//Create new queue with start as the only element
    	
    	ArrayList<String> frontier = new ArrayList<String>();
    	
		//As an alternative to marking vertices as visited/unvisited,
        // 'explored' maintains the set of vertices that have been visited.
       //Create new empty set
    
    	ArrayList<String> explored = new ArrayList<String>();
    
    	//Map every vertex in frontier and explored to its depth
        //Create new empty hashmap    
    	HashMap<String, Integer> depths = new HashMap<String, Integer>();
    	
    	depths.put(start, 0);
    
    	frontier.add(start);
    	while(!frontier.isEmpty()){
    		String vertex = frontier.remove(0);
    		
    		if(depths.get(vertex)>N) break;//base case 
  	
    		explored.add(vertex);//record visited    
    		for(int i=0; i<this.hashmap.get(vertex).size();i++){
    			String neighbor = this.hashmap.get(vertex).get(i);
    			if(!frontier.contains(neighbor)&&
    					!explored.contains(neighbor)){
    				depths.put(neighbor, depths.get(vertex)+1);
    				frontier.add(neighbor);
    			}
    		}
    	}
    	   	
    	//prepare the list of vertices to return
    	Set <String> verticesAtDepthN = new HashSet<String>();
    	Set <String> allKeys = depths.keySet();//get all the keys in the hashmap
    	Iterator<String> itr = allKeys.iterator();//by using a iterator
    	while (itr.hasNext()) {
    		String currentVertex = itr.next();
    		if(depths.get(currentVertex)==N)//only added those in degree of N
    			verticesAtDepthN.add(currentVertex);
    	}   	
    	return verticesAtDepthN;
    	
    }
    /**
     * Returns the shortest path between two people or null if there is 
     * no path between them, if both people exist in this graph and they are 
     * not the same person. Otherwise, throws IllegalArgumentException. The 
     * returned list should begin with personFrom and end with personTo. 
     * Only returns one path if multiple solutions exist.
     * (See note below this table)
     * @param pFrom the person to start search from
     * @param pTo the person to start end at
     * @return the list of nodes between the path
     */
    //Note 1: The pathBetween method solves the classic Shortest Path Search 
    //problem. You may use any algorithm you like, as long as the method meets 
    //the specification stated above. During grading, we will only test cases 
    //where there is at most one shortest path between personFrom and personTo.
    
    //Note 2: One possible algorithm is a modified Breadth First Search, 
    //for which the pseudocode is written below.

    public List<String> getPathBetween(String pFrom, String pTo) {
        //TODO
    	
    	if(pFrom==null||pTo==null||(pFrom.equals(pTo))) 
    		throw new IllegalArgumentException();
  
       	ArrayList<String> frontier = new ArrayList<String>();       
        ArrayList<String> explored = new ArrayList<String>();
        //create a hashmap to "remember" the prev node
    	HashMap<String, String> depthsNodes = new HashMap<String, String>();
    	depthsNodes.put(pFrom, "");//for the start node, 
    							//set its prev node is a empty string 
    							//(though it doesn't really matter)
    	frontier.add(pFrom);
    	
    	while(!frontier.isEmpty()){//do BFS from the pFrom
    		String vertexNode = frontier.remove(0);
			if (depthsNodes.containsKey(pTo)) break; 
					//base case where it stops when found the pTo
			explored.add(vertexNode);
			for(int j=0; j<this.hashmap.get(vertexNode).size();j++){
				String neighborNode = this.hashmap.get(vertexNode).get(j);
				if(!frontier.contains(neighborNode)&&
						!explored.contains(neighborNode)){
					depthsNodes.put(neighborNode, vertexNode);
					frontier.add(neighborNode);
				}
			}			
    	}

    	if(depthsNodes.containsKey(pTo)==false)
    	//if pTo doesnt exist in any valid path start from pFrom
    		return null;//return null
    	//else prepare the path
    	List<String> path = new ArrayList<String>();
    	String prev = pTo;//iterate back to the start point using the hashmap
   	
    	while(!prev.equals(pFrom)){
    		
    		path.add(prev);
    		prev = depthsNodes.get(prev);
    	}
    	path.add(pFrom);//don't forget to add back the start point
    	Collections.reverse(path);//reverse the path since it is backwards
   
        return path;

    }

    /**
     * Returns a pretty-print of this graph in adjacency matrix form.
     * People are sorted in alphabetical order, "X" denotes friendship.
     * 
     * This method has been written for your convenience (e.g., for debugging).
     * You are free to modify it or remove the method entirely.
     * THIS METHOD WILL NOT BE PART OF GRADING.
     *
     * NOTE: this method assumes that the internal hashmap is valid (e.g., no
     * loop, graph is not a multigraph). USE IT AT YOUR OWN RISK.
     *
     * @return pretty-print of this graph
     */
    public String pprint() {
        // Get alphabetical list of people, for prettiness
        List<String> people = new ArrayList<String>(this.hashmap.keySet());
        Collections.sort(people);

        // String writer is easier than appending tons of strings
        StringWriter writer = new StringWriter();

        // Print labels for matrix columns
        writer.append("   ");
        for (String person: people)
            writer.append(" " + person);
        writer.append("\n");

        // Print one line of social connections for each person
        for (String source: people) {
            writer.append(source);
            for (String target: people) {
                if (this.getNeighbors(source).contains(target))
                    writer.append("  X ");
                else
                    writer.append("    ");
            }
            writer.append("\n");

        }

        // Remove last newline so that multiple printlns don't have empty
        // lines in between
        String string = writer.toString();
        return string.substring(0, string.length() - 1);
    }

}
