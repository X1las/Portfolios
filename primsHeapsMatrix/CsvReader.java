import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CsvReader {
    public static void main(String[] args) {
        String line = "";
        String splitBy = ",";
        ArrayList<String[]> connections = new ArrayList<>();
        try {
            // parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader("townsAndDists.csv"));
            while ((line = br.readLine()) != null) // returns a Boolean value
            {
                String[] connection = line.split(splitBy); // use comma as separator
                //connection[[town1, town2, dist],[town1, town2, dist] ]
                connections.add(connection);
                
                /*
                System.out.println("Employee [First Name=" + employee[0] + ", Last Name=" + employee[1]
                        + ", Designation=" + employee[2] + ", Contact=" + employee[3] + ", Salary= " + employee[4]
                        + ", City= " + employee[5] + "]");
            */}

        } catch (IOException e) {
            e.printStackTrace();
        }
        for(String[] connection : connections){
            System.out.print(Arrays.toString(connection));
            System.out.println();
        }
        //System.out.println(connections);
    }
}
