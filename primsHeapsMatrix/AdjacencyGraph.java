import java.util.ArrayList;

import javax.xml.transform.Source;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AdjacencyGraph {
    ArrayList<Vertex> vertices;
    ArrayList<MSTNode> mstNodes = new ArrayList<>();


    HashMap<Vertex, Integer> vertexMap= new HashMap<>();


    public AdjacencyGraph() {
        vertices = new ArrayList<Vertex>();
    }

    public void addVertex(Vertex v) {
        vertices.add(v);
    }

    public ArrayList<Vertex> getVertices() {
        return this.vertices;
    }


    public void MSTPrims(){
        MinHeap<Pair> minheap = new MinHeap<>();
        HashMap<Vertex,Pair> pairs = new HashMap<>();
        for (int i = 0; i < vertices.size(); i++){ //            V log V
            Vertex currentVertex = vertices.get(i);
            Pair pair = new Pair(currentVertex, currentVertex.getDist());
            pair.setVertex(currentVertex);
            pairs.put(currentVertex, pair);
            minheap.insert(pair);
        }
    
        Pair startNode = minheap.getMinHeap().get(0);
        startNode.setDistance(0);
        startNode.getVertex().setPred(startNode.getVertex());
        while(!minheap.isEmpty()){
            Pair extractedNode = minheap.extractMin();
            Vertex extractedVertex = extractedNode.getVertex();
            MSTNode mstNode = new MSTNode(extractedVertex, extractedVertex.getPred(), extractedNode.distance);
            mstNodes.add(mstNode);
            for(Edge edge : extractedVertex.OutEdges){
                if (edge.weight < edge.to.dist){
                    edge.to.setDist(edge.weight);
                    edge.to.setPred(edge.from);
                    Pair currentPair = pairs.get(edge.to);
                    currentPair.setDistance(edge.to.getDist());
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



    public void createFromFile(String file) {
        String line = "";
        String splitBy = ",";
        int counter = 0;
        try {
            // parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) // returns a Boolean value
            {
                
                String[] connection = line.split(splitBy); // use comma as separator
                
                Vertex tempA = null;
                Vertex tempB = null;
                boolean tA = true;
                boolean tB = true;
                for (Vertex vertex : vertices) {
                    if (vertex.name.equals(connection[0])){
                        tempA = vertex;
                        tA = false;
                    }
                    if (vertex.name.equals(connection[1])){
                        tempB = vertex;
                        tB = false;
                    }
                }
                if (tA){
                    tempA = new Vertex(connection[0]);
                    vertices.add(tempA);
                    vertexMap.put(tempA, counter);
                    counter++;
                }
                if (tB){
                    tempB = new Vertex(connection[1]);
                    vertices.add(tempB);
                    vertexMap.put(tempB, counter);
                    counter++;
                }
                if (tempA!=null && tempB!=null) {         
                    //System.out.println(vertices);
                    addEdge(tempA,tempB,Integer.valueOf(connection[2]));
                    addEdge(tempB,tempA,Integer.valueOf(connection[2]));
                }
                
                // connection[[town1, town2, dist],[town1, town2, dist] ]
                
            }

        } catch (IOException e) {
            e.printStackTrace();
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
        //this.distance = vertex.getDist();
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