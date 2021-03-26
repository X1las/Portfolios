import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
       AdjacencyGraph adjacencyGraph = new AdjacencyGraph();
        ArrayList<String[]> connections = csvReader("townsAndDists.csv");
        for (String[] connection : connections){
            adjacencyGraph.addEdge(new Vertex(connection[0]), new Vertex(connection[1]), Integer.parseInt(connection[2]));
        }
    }
        public static ArrayList<String[]> csvReader(String file){

                
                String line = "";
        String splitBy = ",";
        ArrayList<String[]> connections = new ArrayList<>();
        try {
            // parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) // returns a Boolean value
            {
                String[] connection = line.split(splitBy); // use comma as separator
                //connection[[town1, town2, dist],[town1, town2, dist] ]
                connections.add(connection);
                
            

        } catch (IOException e) {
            e.printStackTrace();
        }
        for(String[] connection : connections){
            System.out.print(Arrays.toString(connection));
            System.out.println();
        }
        return connections;
        //System.out.println(connections);
    }
}
