import java.util.Set;
/**
 * This is a generic interface for a graph. 
 * It supports only a subset of common graph 
 * methods that are necessary to build the 
 * social network application. 
 * @author patron
 *
 * @param <V>
 */
public interface GraphADT<V> {
	/**
	 * Adds the specified vertex to this graph if not already present. 
	 * More formally, adds the specified vertex v to this graph if this graph 
	 * contains no vertex u such that u.equals(v). If this graph already 
	 * contains such vertex, the call leaves this graph unchanged
	 * and returns false.
	 * @param vertex
	 * @return
	 */
   boolean addVertex(V vertex);
   
   /**
    * Creates a new edge between vertices v1 and v2 and returns true, 
    * if v1.equals(v2) evaluates to false and an edge does not already exist 
    * between v1 and v2. Returns false otherwise. 
    * Vertices v1 and v2 must already exist in this graph. If they are not 
    * found in the graph IllegalArgumentException is thrown.
    * @param v1
    * @param v2
    * @return
    */
   boolean addEdge(V v1, V v2);
   
   /**
    * If both v1 and v2 exist in the graph, and an edge exists between v1 
    * and v2, remove the edge from this graph. Otherwise, do nothing
    * @param v1
    * @param v2
    */
   void removeEdge(V v1, V v2);
   
   /**
    * Returns a set of all vertices adjacent to v. 
    * Vertice v must already exist in this graph. If it is not 
    * found in the graph IllegalArgumentException is thrown.
    * @param vertex
    * @return
    */
   Set<V> getNeighbors(V vertex);
   
   /**
    * Returns a set of all the vertices in the graph.
    * @return
    */
   Set<V> getAllVertices();

}
