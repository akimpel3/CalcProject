public class qr_fact_househ {
    public Matrix a;
    public int dim;

    public qr_fact_househ(int dim) {
        this.a = HilbertMatrix.createHilbertMatrix(dim);
        this.dim = dim;
    }

    public Solution[] findQRHouseh() {
        for (int i = 0; i < dim - 1; i++) {
            Vector x = a.subMatrix(i, i, a.height, i + 1).toVector();
            double[] eValues = new double[dim];
            eValues[0] = 1;
            Vector e = new Vector(eValues);
            Vector u = x.subtract(e.multiply)
        }
        return null;
    }

    public static void main(String[] args) {
        qr_fact_househ qr = new qr_fact_househ(4);
        qr.findQRHouseh();
    }
}