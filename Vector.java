public class Vector extends Matrix {
    boolean isVertical = true;

    public Vector(boolean isVertical, double ... values) {
        super(values);
        this.isVertical = isVertical;
    }

    public Vector(double ... values) {
        super(values);
    }

    public Vector deepCopy() {
        double[] newValues = new double[this.width];
        for (int i = 0; i < this.width; i++) {
            newValues[i] = this.getValue(0, i);
        }
        return new Vector(newValues);
    }

    public Matrix toMatrix() {
        double newValues[][];
        if (isVertical) {
            newValues = new double[this.width][1];
            for (int i = 0; i < this.width; i++) {
                newValues[i][0] = this.values[0][i];
            }
        } else {
            newValues = new double[1][this.width];
            for (int i = 0; i < this.width; i++) {
                newValues[0][i] = this.values[0][i];
            }
        }
        return new Matrix(newValues);
    }

    public Matrix multiplyBy(Vector other) {
        return this.toMatrix().multiplyBy(other.toMatrix());
    }

    public double dotProduct(Vector other) {
        double product = 0;
        if (other.width == this.width) {
            for (int i = 0; i < this.width; i++) {
                product += this.values[0][i] * other.values[0][i];
            }
        } else {
            System.out.println("Input mismatch: vectors of different length");
            System.exit(0);
        }
        return product;
    }

    public void transpose() {
        isVertical = !isVertical;
    }

    public static void main(String[] args) {
        Vector v = new Vector(5, 2, 5);
        Vector u = new Vector(-1, 4, -2.5);
        System.out.println(v);
        System.out.println(u);
        u.transpose();
        System.out.println(v.multiplyBy(u));
    }
}