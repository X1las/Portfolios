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
        Vertex currentFrom = null;
        Vertex currentTo = null;
        ArrayList<Vertex> tempVert = new ArrayList<>();
        ArrayList<String[]> connections = new ArrayList<>();
        ArrayList<Vertex> connections2 = new ArrayList<>();
        boolean add1 = false;
        boolean add2 = false;
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
                
                /*tempVert.clear();
                tempVert.addAll(vertices);
                //connections.add(connection);
                if (this.vertices.isEmpty()) {
                    currentFrom = new Vertex(connection[0]);

                    vertices.add(currentFrom);
                    currentTo = new Vertex(connection[1]);
                    vertices.add(currentTo);
                } else {
                    
                    for (Vertex vertex : vertices) {
                        
                        if (vertex.getName().equals(connection[0])) {
                            System.out.println("test");
                            currentFrom = vertex;
                            add1 = false;
                        } else {
                            currentFrom = new Vertex(connection[0]);
                            add1 = true;
                            
                        }
                        if (vertex.getName().equals(connection[1])) {
                            currentTo = vertex;
                            add2 = false;

                        } else {
                            currentTo = new Vertex(connection[1]);
                            
                            add2 = true;
                            
                        }
                    } if (add1 == true){
                        System.out.println("KSJDHFKSJDFHKSJDFH");
                        vertices.add(currentFrom);
                    } if (add2 == true){
                        vertices.add(currentTo);
                    }
                    
                    
                }
                add1 = false; add2 = false;
                addEdge(currentFrom, currentTo, Integer.valueOf(connection[2]));
             */   
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