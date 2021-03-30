import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        AdjacencyGraph ag = new AdjacencyGraph();
        ag.createFromFile("townsAndDists.csv");
        ag.MSTPrims();
        
    }
}
