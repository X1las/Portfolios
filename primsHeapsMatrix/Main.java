import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        AdjacencyGraph ag = new AdjacencyGraph();
        ag.createFromFile("townsAndDists.csv");
        ag.PrintGraph();
        //ag.MSTPrims();
        ArrayList<Vertex> likj = ag.getVertices();
        for (Vertex name : likj){
            System.out.println(name.getName());
        }
        System.out.println(likj.size());

}
