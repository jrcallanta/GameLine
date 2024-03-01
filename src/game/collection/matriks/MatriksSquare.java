package game.collection.matriks;

import java.util.Random;

public class MatriksSquare {
    final private char[][] matrix;
    final private int matrixSize;
    static final char charTL = 'A';
    static final char charTR = 'B';
    static final char charBL = 'C';
    static final char charBR = 'D';


    public MatriksSquare (int n) {
        this.matrix = genMatrix(n);
        this.matrixSize = 2 * n;
    }

    private static char[][] genMatrix(int n) {
        char[][] matriks = new char[2 * n][2 * n];

        // Generate Solved Matrix
        for (int row = 0; row < 2 * n; row++) {
            for (int col = 0; col < 2 * n; col++) {
                if (row < n) {
                    matriks[row][col] = (col < n) ? charTL : charTR;
                } else {
                    matriks[row][col] = (col < n) ? charBL : charBR;
                }
            }
        }

        // Mix Matrix
        Random ran = new Random();
        int flips = ran.nextInt(20, 50);
        do {
            String bits = String.format(
                    "%" + (2 * n) + "s",
                    Integer.toBinaryString(
                            ran.nextInt(1, (int) Math.pow(2, (2 * n)))
                    )
            ).replace(" ", "0");

            for (int i = 0; i < bits.length(); i++) {
                if (bits.charAt(i) == '1') {
                    if (flips % 2 == 0)
                        matriks = flipRow(matriks, i);
                    else
                        matriks = flipCol(matriks, i);
                }
            }
        } while (flips-- > 0);

        return matriks;
    }

    public char[][] flipCol (int col) {
        return flipCol(this.matrix, col);
    }

    private static char[][] flipCol (char[][] matrix, int col) {
        int size = matrix.length;
        if (col > size) {
            System.out.println("ColOutOfBounds");
            return matrix;
        }

        for (int i = 0; i < size / 2; i++){
            int thisInd = i;
            int thatInd = size-1-i;
            char thisTemp = matrix[thisInd][col];
            char thatTemp = matrix[thatInd][col];
            matrix[thisInd][col] = thatTemp;
            matrix[thatInd][col] = thisTemp;
        }
        return matrix;
    }

    public char[][] flipRow (int row) {
        return flipRow(this.matrix, row);
    }

    private static char[][] flipRow (char[][] matrix, int row) {
        int size = matrix.length;
        if (row > size) {
            System.out.println("RowOutOfBounds");
            return matrix;
        }

        for (int j = 0; j < size / 2; j++){
            char temp = matrix[row][j];
            matrix[row][j] = matrix[row][size - 1 - j];
            matrix[row][size - 1 - j] = temp;
        }

        return matrix;
    }

    public boolean targetReached () {
        int size = this.matrix.length;
        int head = 0;
        int tail = matrix.length-1;

        // Check Corners Are Unique
        if (matrix[head][head] == matrix[head][tail]
        || matrix[head][tail] == matrix[tail][head]
        || matrix[tail][head] == matrix[tail][tail]
        ) return false;

        // Check Quadrants Match Respective Corners
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                char target = (row < size / 2)
                        ? (col < size / 2)
                            ? matrix[head][head] : matrix[head][tail]
                        : (col < size / 2)
                            ? matrix[tail][head] : matrix[tail][tail];

                if (matrix[row][col] != target) return false;
            }
        }

        return true;
    }

    public String toString() {
        int size = this.matrix.length;
        StringBuilder sb = new StringBuilder();

        sb.append("[\n");
        for(int i = 0; i < size; i++) {
            sb.append("\t");
            for(int j = 0; j < size; j++) {
                if (j != 0) {
                    sb.append("  ");
                }
                sb.append(this.matrix[i][j]);
            }
            sb.append("\n");
        }
        sb.append("]");

        return sb.toString();
    }


    public void print() {
        System.out.println(this);
    }

    public int getMatrixSize() {
        return this.matrixSize;
    }
}
