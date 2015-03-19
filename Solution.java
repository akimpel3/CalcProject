public class Solution {
    private Matrix m;
    private double d;

    public Solution(Matrix m, double d) {
        this.m = m;
        this.d = d;
    }

    public Solution(Matrix m) {
        this.m = m;
    }

    public Solution(double d) {
        this.d = d;
    }

    public Matrix getM() {
        return m;
    }

    public double getD() {
        return d;
    }
}