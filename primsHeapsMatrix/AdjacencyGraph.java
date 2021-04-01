import java.util.ArrayList;

import javax.xml.transform.Source;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AdjacencyGraph {
    ArrayList<Vertex> vertices;
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
        ArrayList<Pair> pairs = new ArrayList<>();
        ArrayList<Vertex> vertexList = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++){
            Vertex currentVertex = vertices.get(i);
            Pair pair = new Pair(vertexMap.get(currentVertex), currentVertex.getDist());
            //pair.setVertex(currentVertex);
            pairs.add(pair);
            vertexList.add(currentVertex);
            minheap.insert(pair);
            //System.out.println(vertices.get(i).name);
        }
        //minheap.getMinHeap().get(0).getDist() = 0;
        Pair startNode = minheap.getMinHeap().get(0);
        startNode.setDistance(0);
        //startNode.getVertex().setPred(startNode.getVertex());
        int MSTdist = 0;
        while(!minheap.isEmpty()){
            
            Pair extractedNode = minheap.extractMin();
            //minheap.printHeap();
            Vertex extractedVertex = vertexMap.get()
            
            //extractedNode.getVertex();
            //System.out.println(smallestVertex.name);
            for(Edge edge : extractedVertex.OutEdges){
                // set the distance of pair that has the .to edge.
                //int index = vertexList.indexOf(edge.to);
                
            }


            for(Edge edge : extractedVertex.OutEdges){
                if (edge.weight < edge.to.dist){
                    edge.to.setDist(edge.weight);
                    edge.to.setPred(edge.from);
                    System.out.println(pairs.get(vertexList.indexOf(edge.to)));
                    System.out.println(minheap.minheap.get(minheap.getPosition(pairs.get(vertexList.indexOf(edge.to)))));
                    //System.out.println("pred" + edge.to.getPred().name);
                    minheap.decreasekey(minheap.getPosition(pairs.get(vertexList.indexOf(edge.to))));
                }
            }
            
            MSTdist += extractedVertex.dist;
        }
        System.out.println(MSTdist);

        
        //minheap.getMinHeap().get(0).dist = 0;
        minheap.printHeap();
        
        for (Pair pair : pairs){
            System.out.println("parent " + pair.getVertex().getPred().getName() + " to " + pair.getVertex().name + " Edgeweight: " + pair.getVertex().getDist());
        }

        /*



         https://algorithms.tutorialhorizon.com/prims-minimum-spanning-tree-mst-using-adjacency-list-and-min-heap/
    1. Create min Heap of size = no of vertices.
    2. Create a heapNode for each vertex which will store two information. a). vertex b). key
    3. Use minHeap[] to keep track of the vertices which are currently in min heap.
    4. Create key[] to keep track of key value for each vertex. (keep updating it as heapNode key for each vertex)
    5. For each heapNode, Initialize key as MAX_VAL except the heapNode for the first vertex for which key will 0. (Start from first vertex).
    6. Insert all the heapNodes into min heap. inHeap[v] = true for all vertices.
    7. while minHeap is not empty
        a) Extract the min node from the heap, say it vertex u and add it to the MST.
        b) Decrease key: Iterate through all the adjacent vertices of above vertex u and if adjacent vertex (say it’s v) is still part of inHeap[] (means not in MST) and key of vertex v> u-v weight then key of vertex v = u-v
    8. We will use Result object to store the result of each vertex. Result object will store 2 information’s.
        a) First the parent vertex, means from which vertex you can visit this vertex. Example if for vertex v, you have included edge u-v in mst[] then vertex u will be the parent vertex.
        b) Second weight of edge u-v. If you add all these weights for all the vertices in mst[]  then you will get Minimum spanning tree weight.
            
        */
        
        /*
        Prims(G):
        Q=Ø (empty min-heap/priorityQueue)
        For each vertex v in G
            dist[v]=infinity;
            pred[v]=null;
            Q.offer(D[v],v);    // adding (for some reason) -- graph.insert()
        dist[start]=0;
        Q.update(start);
        While Q is not empty
            u = Q.poll; // smallest D[u] ---  graph.extractMin()
            for each edge (u,v) (outedge)
            
                if weight (u,v) < D[v]
                    dist[v]= w(u,v);
                    pred[v]=u;
                    Q.update(v);//get to right position
                    minheap.decreasekey(minheap.getPosition())
                end if
            end for
        End while
        */
     
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
    Integer distance;
    Integer vertex;
    Integer key;
 
    public Pair(Integer vertex, Integer distance){
        //this.distance = vertex.getDist();
        this.distance = distance;
        this.vertex = vertex;
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

    /*
    public String toString(){
        //System.out.println("(" + distance + ", " + vertex.getName() + ")");
        return ("(" + distance + ", " + vertex.getName() + ")");
    }*/
}