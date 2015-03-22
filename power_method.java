



    
public class power_method {

    public static String power_method(double sigma) {
        double[][] aValues = {{3, 4, 1},
                              {7, 2, 3} ,
                              {2, 4,  5}};
        double[][] bValues = {{1},
                              {2},
                                {3}};
        Matrix a = new Matrix(aValues);
        Matrix initial = new Matrix(bValues);
        Matrix eigenvector = initial;
        double currentEigenvalue = 1;
        double lastEigenvalue = 0;
        
        for (int i = 0; i < 1000; i++) {

            Matrix x = a.multiplyBy(eigenvector);

            currentEigenvalue = x.getValue(0, 0);

            if (Math.abs(currentEigenvalue - lastEigenvalue) < sigma){
                return "Estimated Maximum Eigenvalue = " + currentEigenvalue + "." 
                        + "\nEstimated Eigenvector = \n" + eigenvector;
            }

            eigenvector = x.multiplyBy( 1 / currentEigenvalue);

            lastEigenvalue = currentEigenvalue;

            System.out.println("Eigenvalue: " + currentEigenvalue);
            System.out.println("Eigenvector: \n" + eigenvector);


        }
        return "Estimated Maximum Eigenvalue = " + currentEigenvalue + "." 
                        + "\nEstimated Eigenvector = \n" + eigenvector;

    }
}



    
