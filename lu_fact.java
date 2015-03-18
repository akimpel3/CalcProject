public class lu_fact {
    public Matrix a;
    public int dim;

    public lu_fact(int dim) {
        this.a = createHilbertMatrix(dim);
        this.dim = dim;
    }

    public Matrix createHilbertMatrix(int dim) {
        double values[][] = new double[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                values[i][j] = 1.0 / ((i + 1.0) + (j + 1.0) - 1);
            }
        }
        return new Matrix(values);
    }

    public Solution[] findLU() {
        int i = 0;
        int j = 0;
        int index = 0;
        int numLowerNums = (dim * (dim - 1)) / 2;
        double[] lowerNums = new double[numLowerNums];
        
        //U matrix
        Matrix l = new Matrix(a);
        while (i < l.getHeight() && j < l.getWidth()) {
            //row multiplication
            for (int k = j + 1; k < l.getHeight(); k++) {
                double scalar = (-1) * l.getValue(k, j) / l.getValue(i, j);
                lowerNums[index] = scalar * (-1);
                index ++;
                l.addRowMultiple(i, k, scalar);
            }

            i++;
            j++;
        }

        //L matrix
        index = 0;
        double[][] uValues = new double[dim][dim];
        for (i = 0; i < dim; i++) {
            for (j = i + 1; j < dim; j++) {
                uValues[j][i] = lowerNums[index];
                index++;
            }
        }

        for (i = 0; i < dim; i++) {
            uValues[i][i] = 1;
        }

        //Error part
        double error = 0;
        Solution[] sol = {new Solution(l, error), new Solution(new Matrix(uValues))};
        return sol;
    }

    public static void main(String[] args) {
        lu_fact lu = new lu_fact(4);
        Solution[] s = lu.findLU();
        Matrix u = s[0].getM();
        Matrix l = s[1].getM();

        System.out.println(lu.a);
        System.out.println("---LU---");
        System.out.println(l.multiplyBy(u));
    }
}