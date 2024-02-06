package matriks;

import java.util.Random;

public class MatriksSquare {
    private int[][] matrix;
    private int matrixSize;
    private int target;

    public MatriksSquare () {
        new MatriksSquare(2);
    }

    public MatriksSquare (int n) {
        this.matrix = genMatrix(n);
        this.target = calcTarget();
        this.matrixSize = 2 * n;
    }

    private static int[][] genMatrix(int n) {
        int size = 2 * n;
        Random ran = new Random();
        int[][] matrix = new int[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                matrix[row][col] = ran.nextInt(10);
            }
        }
        return matrix;
    }

    private static int[][] genMatrix() {
        return genMatrix(2);
    }

    public int[][] flipCol (int col) {
        int size = this.matrix.length;
        if (col > size) {
            System.out.println("ColOutOfBounds");
            return this.matrix;
        }

        for (int i = 0; i < size / 2; i++){
            int thisInd = i;
            int thatInd = size-1-i;
            int thisTemp = matrix[thisInd][col];
            int thatTemp = matrix[thatInd][col];
            matrix[thisInd][col] = thatTemp;
            matrix[thatInd][col] = thisTemp;
        }
        return matrix;
    }

    public int[][] flipRow (int row) {
        int size = this.matrix.length;
        if (row > size) {
            System.out.println("RowOutOfBounds");
            return matrix;
        }

        for (int j = 0; j < size / 2; j++){
            int temp = this.matrix[row][j];
            this.matrix[row][j] = this.matrix[row][size - 1 - j];
            this.matrix[row][size - 1 - j] = temp;
        }

        return matrix;
    }

    public int calcCorner() {
        int sum = 0;

        int size = this.matrix.length;
        for (int i = 0; i < size / 2; i++){
            for (int j = 0; j < size / 2; j++){
                sum += matrix[i][j];
            }
        }
        return sum;
    }

    private int calcTarget () {
        int size = this.matrix.length;
        int[][] maxes = new int[size/2][size/2];
        int maxMatrixSum = 0;

        for (int i = 0; i < size / 2; i++) {
            for (int j = 0; j < size / 2; j++) {
                maxes[i][j] = 0;
                int UL = matrix[i][j];
                int BL = matrix[size - 1 - i][j];
                int UR = matrix[i][size - 1 - j];
                int BR = matrix[size - 1 - i][size - 1 - j];
                maxes[i][j] = Math.max(Math.max(UL, BL), Math.max(UR, BR));
                maxMatrixSum += maxes[i][j];
            }
        }

        return maxMatrixSum;
    }

    public boolean targetReached () {
        return calcCorner() == this.target;
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

    public void printDetails () {
        //System.out.println(this);
        System.out.println("TARGET: " + this.target);
        System.out.println("CURRENT: " + calcCorner());
    }

    public void print() {
        System.out.println(this.toString());
    }

    public int getMatrixSize() {
        return this.matrixSize;
    }
}
