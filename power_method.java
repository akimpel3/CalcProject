public class power_method {

    public String power_method() {
        double[][] aValues = {{3, 4, 1},
                              {7, 2, 3} ,
                              {2, 4,  5}};
        double[][] bValues = {{1},
                              {2},
                                {3}};
        Matrix a = new Matrix(aValues);
        Matrix initial = new Matrix(bValues);
        Matrix eigenvector = initial;
        double eig = 0;
        
        for (int i = 0; i < 1000;i++) {
            double sigma = .01;

            Matrix x = a.multiplyBy(eigenvector);

            eig = x.getValue(0, 0);

            eigenvector = x.multiplyBy( 1 / eig);

        }
        return "Estimated Eigenvalue = " + eig + ". " + "\n Estimated Eigenvector = " + eigenvector;

    }
}



    
