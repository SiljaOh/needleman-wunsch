public class Tests {

    public static boolean matricesEqual(double[][] matrix1, double[][] matrix2) {
        if (matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length) {
            return false;
        }

        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[0].length; j++) {
                if (matrix1[i][j] != matrix2[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean matricesEqual(char[][] matrix1, char[][] matrix2) {
        if (matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length) {
            return false;
        }

        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[0].length; j++) {
                if (matrix1[i][j] != matrix2[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean testScoreMatrixInit(NeedlemanWunsch nw, double[][] expectedScoreMatrix) {
        nw.initMatrices();
        double[][] scoreMatrix = nw.getScoreMatrix();
        return matricesEqual(scoreMatrix, expectedScoreMatrix);
    }

    public static boolean testTracebackMatrixInit(NeedlemanWunsch nw, char[][] expectedTracebackMatrix) {
        nw.initMatrices();
        char[][] tracebackMatrix = nw.getTracebackMatrix();
        return matricesEqual(tracebackMatrix, expectedTracebackMatrix);
    }

    public static boolean testScoreMatrixFill(NeedlemanWunsch nw, double[][] expectedScoreMatrix) {
        nw.initMatrices();
        nw.fillMatrices();
        double[][] scoreMatrix = nw.getScoreMatrix();
        return matricesEqual(scoreMatrix, expectedScoreMatrix);
    }

    public static boolean testTracebackMatrixFill(NeedlemanWunsch nw, char[][] expectedTracebackMatrix) {
        nw.initMatrices();
        nw.fillMatrices();
        char[][] tracebackMatrix = nw.getTracebackMatrix();
        return matricesEqual(tracebackMatrix, expectedTracebackMatrix);
    }

    public static boolean testTraceback(NeedlemanWunsch nw, String expectedAlignment1, String expectedAlignment2) {
        nw.initMatrices();
        nw.fillMatrices();
        nw.traceback();
        //System.out.println("ausgabe:"+nw.getAlignment1()+"  erwartet:"+expectedAlignment1);
        //System.out.println("ausgabe:"+nw.getAlignment2()+"  erwartet:"+expectedAlignment2);
        return nw.getAlignment1().equals(expectedAlignment1) && nw.getAlignment2().equals(expectedAlignment2);
    }

    public static void main(String[] args) {
        NeedlemanWunsch nw = new NeedlemanWunsch(
                "sequence1",
                "sequence2",
                "GATTA",
                "GTCG",
                IO.readSimilrityMatrix("assets/test_scoring_matrix.csv"),
                -2);

        // Test score matrix initialization
        double[][] expectedScoreMatrixInit = {
                {0.0, -2.0, -4.0, -6.0, -8.0},
                {-2.0, 0.0, 0.0, 0.0, 0.0},
                {-4.0, 0.0, 0.0, 0.0, 0.0},
                {-6.0, 0.0, 0.0, 0.0, 0.0},
                {-8.0, 0.0, 0.0, 0.0, 0.0},
                {-10.0, 0.0, 0.0, 0.0, 0.0}
        };
        System.out.println("Passed score matrix init:\t\t" + testScoreMatrixInit(nw, expectedScoreMatrixInit));

        // Test traceback matrix initialization
        char[][] expectedTracebackMatrixInit = {
                {0, 'l', 'l', 'l', 'l'},
                {'u', 0, 0, 0, 0},
                {'u', 0, 0, 0, 0},
                {'u', 0, 0, 0, 0},
                {'u', 0, 0, 0, 0},
                {'u', 0, 0, 0, 0}
        };
        System.out.println("Passed traceback matrix init:\t" + testTracebackMatrixInit(nw, expectedTracebackMatrixInit));

        // Test score matrix fill
        double[][] expectedScoreMatrixFill = {
                {0.0, -2.0, -4.0, -6.0, -8.0},
                {-2.0, 1.0, -1.0, -3.0, -5.0},
                {-4.0, -1.0, 0.0, -2.0, -4.0},
                {-6.0, -3.0, 0.0, -1.0, -3.0},
                {-8.0, -5.0, -2.0, -1.0, -2.0},
                {-10.0, -7.0, -4.0, -3.0, -2.0}
        };
        System.out.println("Passed score matrix fill:\t\t" + testScoreMatrixFill(nw, expectedScoreMatrixFill));

        // Test traceback matrix fill
        char[][] expectedTracebackMatrixFill = {
            {0, 'l', 'l', 'l', 'l'},
            {'u', 'd', 'l', 'l', 'd'},
            {'u', 'u', 'd', 'd', 'd'},
            {'u', 'u', 'd', 'd', 'd'},
            {'u', 'u', 'd', 'd', 'd'},
            {'u', 'u', 'u', 'd', 'd'}
        };
        System.out.println("Passed traceback matrix fill:\t" + testTracebackMatrixFill(nw, expectedTracebackMatrixFill));

        // Test traceback
        String expectedAlignment1 = "GATTA";
        String expectedAlignment2 = "G-TCG";
        System.out.println("Passed traceback:\t\t\t\t" + testTraceback(nw, expectedAlignment1, expectedAlignment2));

    }

}
