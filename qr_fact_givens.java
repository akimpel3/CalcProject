public class qr_fact_givens {
    public Matrix a;
    public int dim;

    public qr_fact_givens(int dim) {
        this.a = HilbertMatrix.createHilbertMatrix(dim);
        this.dim = dim;
    }

    //Finds the qr decomposition
    //--NOTE TO NOAH--
    //A solution array will be returned.
    //Matrix Q is in solution[0].getM()
    //Matrix R is in solution[1].getM()
    //Error is in solution[0].getD()
    public Solution[] findQRGivens() {
        Matrix r = new Matrix(a);
        Matrix q = new Matrix(dim);
        for (int i = 0; i < dim; i++) {
            for (int j = i + 1; j < dim; j++) {
                double aValue = r.values[i][i];
                double bValue = r.values[j][i];
                double vecLen = Math.sqrt(aValue * aValue + bValue * bValue);
                double[][] rotValues = new double[2][2];
                rotValues[0][0] = aValue / vecLen;
                rotValues[1][0] = bValue / vecLen;
                rotValues[0][1] = -rotValues[1][0];
                rotValues[1][1] = rotValues[0][0];
                Matrix rotationMatrix = new Matrix(rotValues);
                Matrix g = givenstize(rotationMatrix, dim, j, i);
                
                r = g.multiplyBy(r);
                q = q.multiplyBy(g.transpose());
            }
        }

        Matrix errorMatrix = (q.multiplyBy(r)).subtract(a);
        double maxEV = errorMatrix.multiplyBy(errorMatrix.transpose()).maxEigenValue(20);
        double error = Math.sqrt(maxEV);
        Solution[] sol = {new Solution(q, error), new Solution(r)};
        return sol;
    }

    //Matrix m is the rotation matrix (2x2)
    //matrixSize is the size of A/G
    //locationRow is the row of the to-be-zero
    //locationColumn is the column of the to-be-zero
    public static Matrix givenstize(Matrix m, int matrixSize, int locationRow,
            int locationColumn) {
        double[][] newValues = new double[matrixSize][matrixSize];
        for (int i = 0; i < matrixSize; i++) {
            newValues[i][i] = 1;
        }
        newValues[locationRow][locationRow] = m.values[0][0];
        newValues[locationRow][locationColumn] = m.values[0][1];
        newValues[locationColumn][locationRow] = m.values[1][0];
        newValues[locationColumn][locationColumn] = m.values[1][1];
        return new Matrix(newValues);
    }

    public static void main(String[] args) {
        qr_fact_givens qr = new qr_fact_givens(4);
        Solution[] s = qr.findQRGivens();
        System.out.println(s[0].getM());
        System.out.println(s[1].getM());
        System.out.println(s[0].getD());
    }
}