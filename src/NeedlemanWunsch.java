import java.util.HashMap;

public class NeedlemanWunsch {

    // Sequence names
    private String name1;
    private String name2;

    // Sequences
    private String sequence1;
    private String sequence2;

    // Alignment strings
    private String alignment1;
    private String alignment2;

    private int gapPenalty;
    private HashMap<Character, HashMap<Character, Double>> similarityMatrix;
    private double[][] scoreMatrix;
    private char[][] tracebackMatrix;

    public NeedlemanWunsch(String name1, String name2, String sequence1, String sequence2, HashMap<Character, HashMap<Character, Double>> similarityMatrix, int gapPenalty) {
        this.name1 = name1;
        this.name2 = name2;
        this.sequence1 = sequence1;
        this.sequence2 = sequence2;
        this.similarityMatrix = similarityMatrix;
        this.gapPenalty = gapPenalty;
    }

    public double[][] getScoreMatrix() {
        return scoreMatrix;
    }

    public char[][] getTracebackMatrix() {
        return tracebackMatrix;
    }

    public String getAlignment1() {
        return alignment1;
    }

    public String getAlignment2() {
        return alignment2;
    }

    public String getAlignment(){
        StringBuilder alignment = new StringBuilder();
        alignment.append(this.name1).append(" vs ").append(this.name2).append("\n");
        alignment.append("Score: ").append(scoreMatrix[scoreMatrix.length - 1][scoreMatrix[0].length - 1]).append("\n");
        alignment.append(alignment1).append("\n");
        for (int i = 0; i < alignment1.length(); i++){
            if (alignment1.charAt(i) == alignment2.charAt(i)){
                alignment.append("|");
            } else {
                alignment.append(" ");
            }
        }
        alignment.append("\n");
        alignment.append(alignment2).append("\n\n");

        return alignment.toString();
    }


    public void initMatrices() {

        scoreMatrix = new double[sequence1.length() + 1][sequence2.length() + 1];
            int count1= 0;
        // 1a): initialize the first column and row of the scoreMatrix
        // first column(Spalte)
        for (int i = 0; i < scoreMatrix.length; i++) {
            // TODO: correctly assign values to  scoreMatrix[i][0]
            // Hint: The score for a gap is stored in the variable gapPenalty
            scoreMatrix[i][0]= count1;
            count1+=gapPenalty;
        }
        int count2=0;
        // first row
        for (int j = 0; j < scoreMatrix[0].length; j++) {
            // TODO: correctly assign values to scoreMatrix[0][j]
            // Hint: The score for a gap is stored in the variable gapPenalty
            scoreMatrix[0][j]= count2;
            count2+=gapPenalty;
        }


        tracebackMatrix = new char[sequence1.length() + 1][sequence2.length() + 1];

        // 1a): initialize the first column and row of the tracebackMatrix. Use 'd', 'u', 'l' to indicate "diagonal", "up", and "left
        // first column, loop starts at 1 to skip the top left corner
        for (int i = 1; i < tracebackMatrix.length; i++) {
            // TODO: correctly assign values to tracebackMatrix[i][0]
            tracebackMatrix[i][0]='u';
        }
        // first row, loop starts at 1 to skip the top left corner
        for (int j = 1; j < tracebackMatrix[0].length; j++) {
            // TODO: correctly assign values to tracebackMatrix[i][0]
            tracebackMatrix[0][j]='l';
        }

    }

    public void fillMatrices() {

        // 1b): Implement the dynamic programming algorithm to fill the scoreMatrix and the tracebackMatrix

        // Iterate over the scoreMatrix starting in the second row and column
        for (int i = 1; i < scoreMatrix.length; i++) {
            for (int j = 1; j < scoreMatrix[0].length; j++) {

                // TODO: calculate the scores coming from each possible direction, by changing 0 to the correct value
                // Hint: Use similarityMatrix.get(sequence1.charAt(i - 1)).get(sequence2.charAt(j - 1)) to access the correct similarity score
                // Hint: The score for a gap is stored in the variable gapPenalty
                double diagonal = scoreMatrix[i-1][j-1]+similarityMatrix.get(sequence1.charAt(i - 1)).get(sequence2.charAt(j - 1)); //?
                double up = scoreMatrix[i-1][j]+gapPenalty;
                double left = scoreMatrix[i][j-1]+gapPenalty;

                // Store the maximum value in the scoring matrix
                scoreMatrix[i][j] = Math.max(diagonal, Math.max(up, left));

                // TODO: store the direction of the maximum value in the traceback matrix
                // Hint: Use 'd', 'u', 'l' to indicate "diagonal", "up", and "left", check the directions in this order for the test case to pass
                // Hint: You can access the traceback matrix with tracebackMatrix[i][j] =;
                if(scoreMatrix[i][j]==diagonal){tracebackMatrix[i][j] = 'd';} else if(scoreMatrix[i][j]==up){
                    tracebackMatrix[i][j] = 'u';}else if(scoreMatrix[i][j]==left){tracebackMatrix[i][j] = 'l';}
            }
        }
    }

    public void traceback() {

        // 1c): Implement the traceback algorithm

        // Hint: StringBuilders are more efficient than concatenating strings, we use them to construct the alignment strings (sequences with gaps)
        // Hint: You can insert characters at the beginning of the string using alignmentX.insert(0, sequenceX.charAt(i-1))
        // Hint: Use sequence1.charAt(i-1) to access the right character in the sequence, since the first row/column in the matrices are for the initialisation only
        StringBuilder alignment1 = new StringBuilder();
        StringBuilder alignment2 = new StringBuilder();

        // Start at the bottom right corner of the tracebackMatrix
        int i = sequence1.length();
        int j = sequence2.length();

        // Traceback until you reach the top left corner of the tracebackMatrix
        while (i > 0 || j > 0) {
            if (i >= 0 && j >= 0 && tracebackMatrix[i][j] == 'd') {
                //&& i - 1 < sequence2.length()
                    // TODO: extend alignment1: Hint: alignment1.insert(0, sequence1.charAt(TODO));
                if (i>0){
                    alignment1.insert(0, sequence1.charAt(i-1 ));}
                    // TODO: extend alignment2: Hint: alignment2.insert(0, sequence2.charAt(TODO));
                if (j>0){
                    alignment2.insert(0, sequence2.charAt(j-1));}

                i--;
                j--;
            } else if (tracebackMatrix[i][j] == 'u') {

                // TODO: extend alignment strings, insert the character '-' to add a gap
                if (i>0){
                alignment1.insert(0, sequence1.charAt(i-1));}
                alignment2.insert(0, '-');

                i--;
            } else if (tracebackMatrix[i][j] == 'l') {

                // TODO: extend alignment strings, insert the character '-' to add a gap
                alignment1.insert(0,'-' );
                if (j>0){
                alignment2.insert(0, sequence2.charAt(j-1));}

                j--;
            } else {
                // Invalid character in traceback matrix
                // In reallife, you would throw an exception here, but we are just breaking the loop so the programm still terminates
                break;
            }

        }
        this.alignment1 = alignment1.toString();
        this.alignment2 = alignment2.toString();
    }


}
