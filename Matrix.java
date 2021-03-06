import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;

/*
 * Brian Woodbury
 * Version 1.1
 *
 * Matrix class handles matrices and some common operations.
 * Current abilities:
 * Adding, multiplying, augmenting, determinants, row reduction,
 * and row operations, inverse, Ax=b (square matrices)
 *
 * todo: decomposition/factoring (QR & LU), other
 * "showStep()" capabilities, eigenvalues
 */

public class Matrix {

    protected int width;
    protected int height;
    protected double values[][];
    private boolean showSteps = false;

    //Syntax: new Matrix(row1, row2, row3)
    //where the row1, row2, row3 are arrays of doubles
    public Matrix(double[] ... values) {
        height = values.length;
        check(values);
        width = values[0].length;
        this.values = values;
    }

    //Allows the user to input an arraylist of double arrays
    public Matrix(ArrayList<double[]> someValues) {
        double[][] newValues = new double[someValues.size()][];
        newValues = someValues.toArray(newValues);
        height = newValues.length;
        check(newValues);
        width = newValues[0].length;
        this.values = newValues;
    }

    //Pretty much the same as a deep copy
    public Matrix(Matrix m) {
        this.values = new double[m.height][m.width];
        for (int i = 0; i < m.height; i++) {
            for (int j = 0; j < m.width; j++) {
                this.values[i][j] = m.values[i][j];
            }
        }
        this.width = m.width;
        this.height = m.height;
    }

    //Syntax: new Matrix(3)
    //creates an identity of size 3
    public Matrix(int dim) {
        values = new double[dim][dim];
        for (int i = 0; i < dim; i++) {
            values[i][i] = 1;
        }
        this.height = dim;
        this.width = dim;

    }

    //Syntax: new Matrix(2, 3)
    //creates an empty 2x3 matrix
    public Matrix(int height, int width) {
        double[][] values = new double[height][width];
        this.width = width;
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public double[][] getValues() {
        return values;
    }

    private void check(double[][] values) {
        if (height >= 1) {
            for (int i = 0; i < height; i++) {
                if (values[i].length != values[0].length) {
                    System.out.print("Invalid input: rows must ");
                    System.out.println("have equal length");
                    System.exit(0);
                }
            }
        } else if (height == 0) {
            System.out.println("Invalid input: Matrix is empty");
            System.exit(0);
        }
    }

    public String toString() {
        String s = "";
        BigDecimal smallNum = new BigDecimal(10E-15);
        BigDecimal negSmallNum = new BigDecimal(-10E-15);

        for (int h = 0; h < height; h++) {
            s += "[";
            for (int w = 0; w < width; w++) {
                BigDecimal bd = new BigDecimal(values[h][w]);
                bd = bd.round(new MathContext(6));
                if (bd.compareTo(smallNum) == -1 && bd.compareTo(negSmallNum) == 1) {
                    bd = new BigDecimal(0);
                }
                s += String.format("% 6.6f", bd) + "  ";
            }
            s += "]\n";
        }

        return s;
    }

    public double getValue(int row, int column) {
        double returnValue = 0;
        if (!(row >= height || column >= width || column < 0 || row < 0)) {
            returnValue = values[row][column];
        } else {
            System.out.println("Invalid input");
            System.exit(0);
        }
        return returnValue;
    }

    public Matrix transpose() {
        double newValues[][] = new double[this.width][this.height];
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                newValues[j][i] = this.getValue(i, j);
            }
        }
        return new Matrix(newValues);
    }

    public Matrix getDiag() {
        double[][] d = new double[this.getHeight()][this.getWidth()];
        for (int y = 0; y < this.getHeight(); ++y) {
            for (int x = 0; x < this.getWidth(); ++x) {
                if (y == x) {
                    d[y][x] = this.getValue(y, x);
                }
            }
        }
        return new Matrix(d);
    }

    public Matrix getU() {
        double[][] u = new double[this.getHeight()][this.getWidth()];
        for (int y = 0; y < this.getHeight(); ++ y) {
            for (int x = 0; x < this.getWidth(); ++ x) {
                if (y < x) {
                    u[y][x] = this.getValue(y, x);
                }
            }
        }
        return new Matrix(u);
    }

    public Matrix getL() {
        double[][] l = new double[this.getHeight()][this.getWidth()];
        for (int y = 0; y < this.getHeight(); ++ y) {
            for (int x = 0; x < this.getWidth(); ++ x) {
                if (y > x) {
                    l[y][x] = this.getValue(y, x);
                }
            }
        }
        return new Matrix(l);
    }


    public Matrix multiplyBy(Matrix v) {
        double[][] newValues = new double[this.height][v.width];
        if (this.width == v.height) {
            for (int column = 0; column < v.width; column++) {
                for (int row = 0; row < height; row++) {
                    for (int i = 0; i < width; i++) {
                        newValues[row][column] += values[row][i] * v.getValue(i, column);
                    }
                }
            }
        } else {
            System.out.println("Invalid input: Cannot multiply the matrices");
            System.exit(0);
        }

        Matrix newMatrix = new Matrix(newValues);
        return newMatrix;
    }

    public Matrix multiplyBy(double e) {
        double[][] newValues = new double[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                newValues[i][j] = values[i][j] * e;
            }
        }

        return new Matrix(newValues);
    }

    public Matrix addTo(Matrix other) {
        double[][] newValues = new double[height][width];
        if (other.getWidth() == width && other.getHeight() == height) {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    newValues[i][j] = this.values[i][j] + other.getValue(i, j);
                }
            }
        } else {
            System.out.println("Matrices must be of equal dimensions");
            System.exit(0);
        }
        return new Matrix(newValues);
    }

    public Matrix subtract(Matrix other) {
        return this.addTo(other.multiplyBy(-1));
    }

    public double determinant() {
        double determinant = 0;
        if (isSquare()) {
            if (width > 1) {
                for (int i = 0; i < width; i++) {
                    double multiplier = 1;
                    if (i % 2 == 1) {
                        multiplier *= -1;
                    }
                    determinant += values[0][i] * removeRowColumn(0, i).determinant() * multiplier;  //over 80 characters, whoops.
                }
            } else {
                return values[0][0];
            }
        } else {
            System.out.println("The matrix has no determinant");
            System.exit(0);
        }
        return determinant;
    }

    //Makes a sub-matrix from (rA, cA) inclusive to (rB, cB) exclusive
    public Matrix subMatrix(int rowA, int colA, int rowB, int colB) {
        double[][] newValues = new double[rowB - rowA][colB - colA];
        for (int i = rowA; i < rowB; i++) {
            for (int j = colA; j < colB; j++) {
                newValues[i - rowA][j - colA] = values[i][j];
            }
        }

        return new Matrix(newValues);
    }

    //Makes a matrix out of everything except the row and column specified
    //I'm aware this can be smaller, but still O(n^2) so idgaf
    public Matrix removeRowColumn(int row, int column) {
        double[][] newValues = new double[height - 1][width - 1];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                newValues[i][j] = values[i][j];
            }

            for (int j = column + 1; j < width; j++) {
                newValues[i][j - 1] = values[i][j];
            }
        }

        for (int i = row + 1; i < height; i++) {
            for (int j = 0; j < column; j++) {
                newValues[i - 1][j] = values[i][j];
            }

            for (int j = column + 1; j < width; j++) {
                newValues[i - 1][j - 1] = values[i][j];
            }
        }

        return new Matrix(newValues);
    }

    //Adds the paramter matrix to the right side
    public Matrix augment(Matrix m) {
        double[][] newValues = new double[height][width + m.getWidth()];
        if (m.height == height) {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    newValues[i][j] = values[i][j];
                }

                for (int j = 0; j < m.getWidth(); j++) {
                    newValues[i][j + width] = m.getValue(i, j);
                }
            }
        } else {
            System.out.println("Matrices must be of equal height");
        }

        return new Matrix(newValues);
    }

    //Row reduction, self explanatory
    public Matrix rowReductionForwards() {
        int i = 0;
        int j = 0;
        Matrix m = new Matrix(this);
        while (i < m.height && j < m.width) {

            //get a non-zero in the pivot
            while (m.values[i][j] == 0) {
                for (int k = i; k < m.height; k++) {
                    if (m.values[k][j] != 0) {
                        showStep("Swapping rows " + i + " and " + k + "\n");
                        m.rowSwap(i, k);
                        showStep(m.toString());
                        break;
                    }
                }

                if (m.values[i][j] == 0) {
                    if (j == m.width - 1) {
                        showStep("Done. Returning");
                        return m;
                    } else {
                        j++;
                    }
                }
            }

            //row multiplication
            for (int k = j + 1; k < m.height; k++) {
                double scalar = (-1) * m.values[k][j] / m.values[i][j];
                showStep("Adding row multiple (" + scalar + " * (row " + i + ") + (row " + k + "))\n");
                m.addRowMultiple(i, k, scalar);
                showStep(m.toString());
            }

            i++;
            j++;
        }
        showStep("Row Reduction finished!");
        showStep("Final result: \n\n" + m);
        return m;
    }

    public Matrix findReducedEchelon() {
        int i = 0;
        int j = 0;
        Matrix m = new Matrix(this);
        while (i < m.height && j < m.width) {

            //get a non-zero in the pivot
            while (m.values[i][j] == 0) {
                for (int k = i; k < m.height; k++) {
                    if (m.values[k][j] != 0) {
                        showStep("Swapping rows " + i + " and " + k + "\n");
                        m.rowSwap(i, k);
                        showStep(m.toString());
                        break;
                    }
                }

                if (m.values[i][j] == 0) {
                    if (j == m.width - 1) {
                        showStep("Done. Returning");
                        return m;
                    } else {
                        j++;
                    }
                }
            }

            //row multiplication above pivot
            for (int k = 0; k < j; k++) {
                double scalar = (-1) * values[k][j] / values[i][j];
                showStep("Adding row multiple (" + scalar + " * (row " + i + ") + (row " + k + "))\n");
                m.addRowMultiple(i, k, scalar);
                showStep(m.toString());
            }

            //row multiplication below pivot
            for (int k = j + 1; k < m.height; k++) {
                double scalar = (-1) * values[k][j] / values[i][j];
                showStep("Adding row multiple (" + scalar + " * (row " + i + ") + (row " + k + "))\n");
                m.addRowMultiple(i, k, scalar);
                showStep(m.toString());
            }

            //get pivot to equal 1
            double n = m.values[i][j];
            showStep("Divide row " + i + " by " + n + "\n");
            for (int k = j; k < width; k++) {
                m.values[i][k] = m.values[i][k] / n;
            }
            showStep(m.toString());

            i++;
            j++;
        }
        return m;
    }

    public Matrix invert() {
        if ((this.determinant() != 0) && this.isSquare()) {
            Matrix identity = new Matrix(this.height);
            Matrix augmentedMatrix = this.augment(identity);
            augmentedMatrix = augmentedMatrix.findReducedEchelon();
            Matrix inverse = augmentedMatrix.subMatrix(0, identity.width,
                    augmentedMatrix.height, augmentedMatrix.width);
            return inverse;
        } else {
            System.out.println("Matrix is not invertible");
            System.exit(0);
        }
        return new Matrix(2);
    }

    //Ax=b
    public Matrix solveForX(Matrix b) {
        Matrix x = this.invert().multiplyBy(b);
        return x;
    }

    //Will change your matrix !!!!
    public void rowSwap(int a, int b) {
        double[] rowB = values[b];
        values[b] = values[a];
        values[a] = rowB;
    }

    //in the format rowB = rowB + (rowA * scalar)
    //Will also change your matrix !!!!
    public void addRowMultiple(int a, int b, double scalar) {
        for (int i = 0; i < values[a].length; i++) {
            values[b][i] = values[b][i] + values[a][i] * scalar;
        }
    }

    public Vector toVector() {
        double newValues[];
        boolean isVertical = true;
        if (this.width == 1) {
            newValues = new double[this.height];
            for (int i = 0; i < this.height; i++) {
                newValues[i] = this.values[i][0];
            }
        } else if (this.height == 1) {
            newValues = new double[this.width];
            isVertical = false;
            for (int i = 0; i < this.width; i++) {
                newValues[i] = this.values[0][i];
            }
        } else {
            System.out.println("This matrix cannot be converted to a vector");
            System.exit(0);
            newValues = new double[0];
        }
        return new Vector(isVertical, newValues);
    }

    //pass in the number of iterations
    public double maxEigenValue(int numIterations) {
        if (!this.isSquare()) {
            System.out.println("Matrix must be square");
            System.exit(0);
        }

        double[][] firstGuess = new double[this.width][1];
        for (int i = 0; i < this.width; i++) {
            firstGuess[i][0] = 1;
        }
        Matrix guess = new Matrix(firstGuess);

        for (int i = 0; i < numIterations; i++) {
            guess = this.multiplyBy(guess);
            if (guess.values[0][0] != 0) {
                guess = guess.multiplyBy(1 / guess.values[0][0]);
            }
        }

        Matrix ax = this.multiplyBy(guess);
        double topDotProduct = ax.toVector().dotProduct(guess.toVector());
        double bottomDotProduct = guess.toVector().dotProduct(guess.toVector());
        if (bottomDotProduct != 0) {
            return topDotProduct / bottomDotProduct;
        } else {
            return 0;
        }
    }

    public boolean isSquare() {
        return (height == width);
    }

    public void showStep(String instruction) {
        if (showSteps)
            System.out.println(instruction);
    }

    public static void main(String[] args) {
        //Example functionality

        //Initializing values
        double[][] aValues = {{4,     1,   3,  0},
                              {-6,    2,   1,  4},
                              {2,  -0.9,   2,  1},
                              {1,     0,   7,  2}};
        double[][] bValues = {{1,     3,   4,  6},
                              {0,     5,  -3, -1},
                              {-2,    2, 1.5,  0},
                              {7,     4,  -2,  2}};
        double[][] cValues = {{ 1, -2, -2},
                              {-2,  4,  4},
                              {-2,  4,  4}};

        //Creating the matrices from the 2d arrays
        Matrix a = new Matrix(aValues);
        Matrix b = new Matrix(bValues);
        Matrix c = new Matrix(cValues);

        System.out.println(c.maxEigenValue(50));
    }
}