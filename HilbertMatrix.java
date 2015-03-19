public class HilbertMatrix {

    public static Matrix createHilbertMatrix(int dim) {
        double values[][] = new double[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                values[i][j] = 1.0 / ((i + 1.0) + (j + 1.0) - 1);
            }
        }
        return new Matrix(values);
    }
}