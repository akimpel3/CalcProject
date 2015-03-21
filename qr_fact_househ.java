public class qr_fact_househ {
    public Matrix a;
    public int dim;

    public qr_fact_househ(int dim) {
        this.a = HilbertMatrix.createHilbertMatrix(dim);
        this.dim = dim;
    }

    //Finds the qr decomposition
    //--NOTE TO NOAH--
    //A solution array will be returned.
    //Matrix Q is in solution[0].getM()
    //Matrix R is in solution[1].getM()
    //Error is in solution[0].getD()
    public Solution[] findQRHouseh() {
        Matrix r = new Matrix(a);
        Matrix q = new Matrix(dim);
        for (int i = 0; i < dim - 1; i++) {
            Matrix x = r.subMatrix(i, i, a.height, i + 1);
            double xMagnitude = x.toVector().magnitude();
            double[][] eValues = new double[dim - i][1];
            eValues[0][0] = xMagnitude;
            Matrix e = new Matrix(eValues);
            Matrix v = x.subtract(e);
            Matrix u = v.multiplyBy(1 / v.toVector().magnitude());
            Matrix uTranspose = u.transpose();

            Matrix identity = new Matrix(dim - i);
            Matrix h = householdertize(identity.subtract(u.multiplyBy(2).multiplyBy(uTranspose)), dim);
            r = h.multiplyBy(r);
            q = q.multiplyBy(h);
        }

        //Error Part
        Matrix errorMatrix = (q.multiplyBy(r)).subtract(a);
        double maxEV = errorMatrix.multiplyBy(errorMatrix.transpose()).maxEigenValue(20);
        double error = Math.sqrt(maxEV);
        Solution[] sol = {new Solution(q, error), new Solution(r)};
        return sol;
    }

    public static Matrix householdertize(Matrix m, int size) {
        double[][] values = new double[size][size];
        for (int i = 0; i < size - m.width; i++) {
            values[i][i] = 1;
        }

        for (int i = size - m.height; i < size; i++) {
            for (int j = size - m.width; j < size; j++) {
                values[i][j] = m.values[i - (size - m.height)][j - (size - m.width)];
            }
        }
        return new Matrix(values);
    }

    public static void main(String[] args) {
        qr_fact_househ qr = new qr_fact_househ(4);
        Solution[] s = qr.findQRHouseh();
        System.out.println(s[0].getM());
        System.out.println(s[1].getM());
        System.out.println(s[0].getD());
    }
}