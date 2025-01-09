import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        // Read sequences from a FASTA file
        ArrayList<String[]> sequences = IO.readFasta("assets/sequences.fasta");
        System.out.println("Sequences:");
        for (String[] seq : sequences) {
            System.out.println(seq[0] + ": " + seq[1]);
        }
        System.out.println("\n");

        // Read similarity matrix as a nested hashmap
        HashMap<Character, HashMap<Character, Double>> similarityMatrix = IO.readSimilrityMatrix("assets/blosum62.csv");

        // Compare the first sequence to all other sequences
        for(int i = 1; i < sequences.size(); i++){

            NeedlemanWunsch nw = new NeedlemanWunsch(
                    sequences.get(0)[0],
                    sequences.get(i)[0],
                    sequences.get(0)[1],
                    sequences.get(i)[1],
                    similarityMatrix,
                    -9);

            // Initialize matrices
            nw.initMatrices();

            // Fill matrices
            nw.fillMatrices();

            // Traceback
            nw.traceback();

            // Print alignment
            System.out.println(nw.getAlignment());
        }





    }
}