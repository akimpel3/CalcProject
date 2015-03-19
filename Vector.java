public class Vector extends Matrix {
    boolean isVertical = true;

    public Vector(boolean isVertical, double ... values) {
        super(values);
        this.isVertical = isVertical;
    }

    public Vector(double ... values) {
        super(values);
    }

    @Override
    public String toString() {
        String s = "";
        if (this.isVertical) {
            for (int i = 0; i < this.width; i++) {
                s += "< " + values[0][i] + " >\n";
            }
        } else {
            s += "< ";
            for (int i = 0; i < this.width; i++) {
                s += values[0][i] + "\t";
            }
            s += ">";
        }
        return s;
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

    public double magnitude() {
        double total = 0;
        for (int i = 0; i < this.width; i++) {
            total += values[0][i] * values[0][i];
        }
        return Math.sqrt(total);
    }

    public Vector crossProduct(Vector other) {
        if (this.width != 3 || other.width != 3) {
            System.out.println("Cross product is not defined for these dimensions");
            System.exit(0);
        }
        double i = this.values[0][1] * other.values[0][2] - this.values[0][2] * other.values[0][1];
        double j = this.values[0][2] * other.values[0][0] - this.values[0][0] * other.values[0][2];
        double k = this.values[0][0] * other.values[0][1] - this.values[0][1] * other.values[0][0];
        return new Vector(i, j, k);
    }

    //Had to change the method name. Take note!!!
    public void vectorTranspose() {
        isVertical = !isVertical;
    }

    public static void main(String[] args) {
        Vector v = new Vector(5, 2, 5);
        Vector u = new Vector(-1, 4, -2.5);
        System.out.println(v);
        System.out.println(u);
        u.transpose();
        System.out.println(v.crossProduct(u));
    }
}