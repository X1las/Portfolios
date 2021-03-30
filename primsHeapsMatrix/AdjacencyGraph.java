import java.util.ArrayList;

import javax.xml.transform.Source;

import java.io.BufferedReader;
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

    public ArrayList<Vertex> getVertices() {
        return this.vertices;
    }

    public void MSTPrims(){
        MinHeap<Pair> minheap = new MinHeap<>();
        
        for (Vertex vertex : vertices){
            Pair pair = new Pair(vertex.dist, vertex);
            minheap.insert(pair);
        }
        
        minheap.printHeap();
        
        

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
                end if
            end for
        End while
        */
        /*
        int[] Distance =new int[matrixegdegraph.length];    // edge weight?
        int[] predecessor = new int[matrixegdegraph.length];
        boolean[] visited =  new boolean[matrixegdegraph.length];
        MinHeap<Pair> Q =new MinHeap<>();
        PriorityQueue<Pair> PQ = new PriorityQueue<>(); // offer (add) poll (extactmin)

        ArrayList<Pair> VertexPairs=new ArrayList<>();
        Arrays.fill(Distance, Integer.MAX_VALUE);
        Arrays.fill(predecessor,-1);
        Arrays.fill(visited,false);
        if (matrixegdegraph.length>0)
            Distance[0]=0;
        for(int i=0;i<matrixegdegraph.length;i++) {
            VertexPairs.add(new Pair(Distance[i], i));
            Q.Insert(VertexPairs.get(i));
        }
        int MST=0;
        while(!Q.isEmpty()){
            Pair u=Q.extractMin();
            for(int v=0;v<matrixegdegraph.length;v++){
                if(matrixegdegraph[u.index][v]==1 && matrixweightgraph[u.index][v]<Distance[v])
                {
                    if(!visited[v]) {
                        Distance[v] = matrixweightgraph[u.index][v];
                        predecessor[v] = u.index;
                        int pos = Q.getPosition(VertexPairs.get(v));
                        VertexPairs.get(v).distance = matrixweightgraph[u.index][v];
                        Q.decreasekey(pos);
                    }
                }
            }
            MST+=Distance[u.index];
        }
        System.out.println("Minimum spanning tree Dsitance: " +MST);
        for(int i=0; i< matrixegdegraph.length;i++)
        {
            System.out.println(" parent "+ predecessor[i] + " to " + i + " EdgeWeight: " + Distance[i]);
        }*/
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
                for (Vertex vertex : vertices) 
                {
                    if (vertex.name.equals(connection[0]))
                    {
                        tempA = vertex;
                        tA = false;
                    }
                    if (vertex.name.equals(connection[1]))
                    {
                        tempB = vertex;
                        tB = false;
                    }
                }
                if (tA)
                {
                    tempA = new Vertex(connection[0]);
                    vertices.add(tempA);
                }
                if (tB)
                {
                    tempB = new Vertex(connection[1]);
                    vertices.add(tempB);
                }

                if (tempA!=null && tempB!=null) 
                {         
                    //System.out.println(vertices);
                    addEdge(tempA,tempB,Integer.valueOf(connection[2]));
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
    Integer dist = Integer.MAX_VALUE;

    public Vertex(String id) {
        this.name = id;
        OutEdges = new ArrayList<Edge>();
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
    }
}

class Pair implements Comparable<Pair>{
    Integer distance;
    Vertex vertex;
 
    public Pair(Integer distance, Vertex vertex){
        this.distance = distance;
        this.vertex = vertex;
    }
 
    @Override
    public int compareTo(Pair p){
        return this.distance.compareTo(p.distance);
    }

    
    public String toString(){
        //System.out.println("(" + distance + ", " + vertex.getName() + ")");
        return ("(" + distance + ", " + vertex.getName() + ")");
    }
}