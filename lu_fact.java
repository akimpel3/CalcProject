//No error term yet

public class lu_fact {
    public Matrix a;
    public int dim;

    public lu_fact(int dim) {
        this.a = HilbertMatrix.createHilbertMatrix(dim);
        this.dim = dim;
    }

    //Finds the lu decomposition
    //--NOTE TO NOAH--
    //A solution array will be returned.
    //Matrix L is in solution[0].getM()
    //Matrix U is in solution[1].getM()
    //Error is in solution[0].getD()
    public Solution[] findLU() {
        int i = 0;
        int j = 0;
        int index = 0;
        int numLowerNums = (dim * (dim - 1)) / 2;
        double[] lowerNums = new double[numLowerNums];
        
        //U matrix
        Matrix u = new Matrix(a);
        while (i < u.getHeight() && j < u.getWidth()) {
            //row multiplication
            for (int k = j + 1; k < u.getHeight(); k++) {
                double scalar = (-1) * u.getValue(k, j) / u.getValue(i, j);
                lowerNums[index] = scalar * (-1);
                index ++;
                u.addRowMultiple(i, k, scalar);
            }

            i++;
            j++;
        }

        //L matrix
        index = 0;
        double[][] lValues = new double[dim][dim];
        for (i = 0; i < dim; i++) {
            for (j = i + 1; j < dim; j++) {
                lValues[j][i] = lowerNums[index];
                index++;
            }
        }

        for (i = 0; i < dim; i++) {
            lValues[i][i] = 1;
        }

        //Error part
        double error = 0;
        Solution[] sol = {new Solution(new Matrix(lValues), error), new Solution(u)};
        return sol;
    }

    public static void main(String[] args) {
        lu_fact lu = new lu_fact(4);
        Solution[] s = lu.findLU();
        Matrix l = s[0].getM();
        Matrix u = s[1].getM();
        System.out.println(l);
        System.out.println(u);
    }
}