import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class IO {

    public static ArrayList<String[]> readFasta(String path) {

        ArrayList<String[]> sequences = new ArrayList<>();
        boolean first = true;
        String name = "";
        String sequence = "";

        try (Scanner sc = new Scanner(new File(path))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.charAt(0) == '>') {
                    if (first) {
                        first = false;
                    } else {
                        sequences.add(new String[]{name, sequence});
                        sequence = "";
                    }
                    name = line.substring(1);
                } else {
                    sequence += line;
                }
            }
            sequences.add(new String[]{name, sequence});
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sequences;
    }

    public static HashMap<Character, HashMap<Character,Double>> readSimilrityMatrix(String path) {
        HashMap<Character, HashMap<Character, Double>> similarityMatrix = new HashMap<>();
        try (Scanner sc = new Scanner(new File(path))) {
            String[] header = sc.nextLine().split(",");
            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(",");
                HashMap<Character, Double> row = new HashMap<>();
                for (int i = 1; i < line.length; i++) {
                    row.put(header[i].charAt(0), Double.parseDouble(line[i]));
                }
                similarityMatrix.put(line[0].charAt(0), row);
            }
        } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
        return similarityMatrix;
    }
}
