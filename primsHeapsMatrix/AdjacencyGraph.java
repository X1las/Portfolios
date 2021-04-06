import java.util.ArrayList;

import javax.xml.transform.Source;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AdjacencyGraph {
    ArrayList<Vertex> vertices;



    public AdjacencyGraph() {
        vertices = new ArrayList<Vertex>();
    }

    public void addVertex(Vertex v) {
        vertices.add(v);
    }
    // cats r cute that's true tho
    
    public void MSTPrims(){
        ArrayList<MSTNode>   mstNodes = new ArrayList<>();      // 1
        MinHeap<Pair>        minheap  = new MinHeap<>();        // 1
        HashMap<Vertex,Pair> pairs    = new HashMap<>();        // 1
        for (int i = 0; i < vertices.size(); i++){ //            V log V
            Vertex currentVertex = vertices.get(i);
            Pair pair = new Pair(currentVertex, currentVertex.dist);
            pair.setVertex(currentVertex);
            pairs.put(currentVertex, pair);
            minheap.insert(pair);
        }
    
        Pair startNode = minheap.getMinHeap().get(0);
        startNode.setDistance(0);
        startNode.vertex.setPred(startNode.vertex);
        while(!minheap.isEmpty()){ 
            Pair    extractedNode = minheap.extractMin();   // O(v + v²)
            Vertex  extractedVertex = extractedNode.vertex; // O(1)
            MSTNode mstNode = new MSTNode(extractedVertex, extractedVertex.pred, extractedNode.distance); // O(1)
            mstNodes.add(mstNode); // O(v)
            for(Edge edge : extractedVertex.OutEdges){  // O(e)
                if (edge.weight < edge.to.dist){
                    edge.to.setDist(edge.weight);   // O(1)
                    edge.to.setPred(edge.from);     // O(1)
                    Pair currentPair = pairs.get(edge.to); 
                    currentPair.setDistance(edge.to.dist);
                    int pos = minheap.getPosition(currentPair);
                    minheap.decreasekey(pos);
                }
            }
        }
               
        int total = 0;
        for (MSTNode node : mstNodes){
            System.out.println(node);
            total += node.distance;
        }
        System.out.println("total distance: " + total + " km");
        System.out.printf(Locale.US, "total price:    %,d kroner %n", 100000*total);
    }


    public void addEdge(Vertex f, Vertex t, Integer w) {
        if (!(vertices.contains(f) && vertices.contains(t))) {
            System.out.println(" Vertex not in graph");
            return;
        }
        Edge e = new Edge(f, t, w);
    }

    public void createFromFile(String file) {
        String line    = "";
        String splitBy = ",";
        try{
            // parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) // returns a Boolean value
            {
                
                String[] connection = line.split(splitBy); // use comma as separator
                
                Vertex fromVertex = null;
                Vertex toVertex   = null;
                boolean from_alreadyInList = true;
                boolean to_alreadyInList   = true;
                for (Vertex vertex : vertices) {
                    if (vertex.name.equals(connection[0])){
                        fromVertex = vertex;
                        from_alreadyInList = false;
                    }
                    if (vertex.name.equals(connection[1])){
                        toVertex = vertex;
                        to_alreadyInList = false;
                    }
                }
                if (from_alreadyInList){
                    fromVertex = new Vertex(connection[0]);
                    vertices.add(fromVertex);
                }
                if (to_alreadyInList){
                    toVertex = new Vertex(connection[1]);
                    vertices.add(toVertex);
                }
                if (fromVertex!=null && toVertex!=null) {         
                    addEdge(fromVertex,toVertex,  Integer.valueOf(connection[2]));
                    addEdge(toVertex,  fromVertex,Integer.valueOf(connection[2]));
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();;
        }
    }

    public void PrintGraph() {
        for (int i = 0; i < vertices.size(); i++) {
            System.out.println(" From Vertex: " + vertices.get(i).name);
            Vertex currentfrom = vertices.get(i);
            for (int j = 0; j < currentfrom.OutEdges.size(); j++) {
                Edge currentEdge = currentfrom.OutEdges.get(j);
                System.out.println(" To: " + currentEdge.to.name + " weight: " + currentEdge.weight);
            }
            System.out.println(" ");
        }
    }
}

class MSTNode {
    Vertex self;
    Vertex parent;
    Integer distance;

    public MSTNode(Vertex self, Vertex parent, Integer distance){
        this.self = self;
        this.parent = parent;
        this.distance = distance;
    }

    public String toString(){
        return String.format("From: %-15s to: %-15s distance: %-2s km" , parent.getName(), self.getName(), Integer.toString(distance));
    }

}


class Vertex implements Comparable<Vertex> {
    String name;
    ArrayList<Edge> OutEdges;
    Vertex pred = null;
    Integer dist = Integer.MAX_VALUE;

    public Vertex(String id) {
        this.name = id;
        OutEdges = new ArrayList<Edge>();
    }

    public void setPred(Vertex pred){
        this.pred = pred;
    }

    public void setDist(Integer dist){
        this.dist = dist;
    }
    public void setName(String name){
        this.name = name;
    }

    public Vertex getPred(){
        return this.pred;
    }

    public Integer getDist(){
        return dist;
    }

    public String getName() {
        return name;
        
    }

    public void addOutEdge(Edge e) {
        OutEdges.add(e);
    }

    public boolean compareByName(String name) {
        if (this.name.equals(name))
            return true;
        return false;
    }

    @Override
    public boolean equals(Object vertex) {
        if (this == vertex)
            return true;

        return false;
    }

    @Override
    public int compareTo(Vertex o) {
        if (this.dist < o.dist)
            return -1;
        if (this.dist > o.dist)
            return 1;
        return 0;
    }
}





class Edge {
    Integer weight;
    Vertex from;
    Vertex to;

    public Edge(Vertex from, Vertex to, Integer cost) {
        this.from = from;
        this.to = to;
        this.weight = cost;
        this.from.addOutEdge(this);
        this.to.addOutEdge(this);
    }
}

class Pair implements Comparable<Pair>{
    Integer distance = Integer.MAX_VALUE;
    Vertex vertex;
    Integer key;
 
    public Pair(Vertex vertex, Integer distance){
        //this.disance = vertex.getDist();
        this.distance = distance;
        this.vertex = vertex;
    }

    public void setVertex(Vertex vertex){
        this.vertex = vertex;
    }
    
    public Vertex getVertex(){
        return vertex;
    }

    public void setDistance(Integer distance){
        //this.vertex.setDist(distance);
        this.distance = distance;
    }

    //public Pair getPair(Vertex vertex){
      //  if (this.vertex == vertex) return this;
       // return null;
   // }


    @Override
    public int compareTo(Pair p){
        return this.distance.compareTo(p.distance);
    }

    
    public String toString(){
        //System.out.println("(" + distance + ", " + vertex.getName() + ")");
        return ("(" + distance + ", " + vertex.getName() + ")");
    }
}