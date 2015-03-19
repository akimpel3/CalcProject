public class qr_fact_househ {
    public Matrix a;
    public int dim;

    public qr_fact_househ(int dim) {
        this.a = HilbertMatrix.createHilbertMatrix(dim);
        this.dim = dim;
    }

    public Solution[] findQRHouseh() {
        return null;
    }
}