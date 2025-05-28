
import java.util.Arrays;

public class Regression{

    // var1 is 
    private final double[] x;
    private final double[] y;
    private String nameX;
    private String nameY;

    // summations for calculations
    private double xsum = 0;
    private double ysum = 0;
    private double xsqsum = 0;
    private double ysqsum = 0;
    private double xysum = 0;

    // constructor class
    public Regression(double[][] data, int varX, int varY) {
        x = new double[data.length];
        y = new double[data.length];
        
        for (int i = 0; i < data.length; i++) {
            this.x[i] = data[i][varX];
            this.y[i] = data[i][varY];
        }

        initSums();
    }

    public Regression(String[][] data, int varX, int varY) {
        this.x = new double[data.length - 1];
        this.y = new double[data.length - 1];
        this.nameX = data[0][varX];
        this.nameY = data[0][varY];
        
        for (int i = 1; i < data.length; i++) {
            this.x[i - 1] = Double.parseDouble(data[i][varX]);
            this.y[i - 1] = Double.parseDouble(data[i][varY]);
        }

        initSums();
    }

    private void initSums(){
        for (int i = 0; i < x.length; i++){
            xsum += x[i];
            ysum += y[i];
            xysum += x[i] * y[i];
            xsqsum += Math.pow(x[i], 2);
            ysqsum += Math.pow(y[i], 2);
        }
    }

    public double correl(){
        double cov = x.length * xysum - (xsum * ysum);
        double sampleStdev = 
            Math.sqrt((x.length*xsqsum - Math.pow(xsum, 2))*
            (x.length*ysqsum - Math.pow(ysum, 2)));
        double r = cov/sampleStdev;

        return r;
    }

    public double rSqrd(){
        double rsqr;
        double cov = x.length * xysum - (xsum * ysum);
        double sampleStdev = 
            Math.sqrt((x.length*xsqsum - Math.pow(xsum, 2))*
            (x.length*ysqsum - Math.pow(ysum, 2)));

        rsqr = Math.pow(cov/sampleStdev, 2);

        return rsqr;
    }
    
    public double interceptCalc(){
        // intercept calculations for numerator and denominator
        double num = (ysum * xsqsum) - (xsum * xysum);
        double denom = x.length * xsqsum - Math.pow(xsum, 2);
        double intercept = num / denom;

        return intercept;
    }

    public double slopeCalc(){
        double num = x.length * xysum - (xsum * ysum);
        double denom = x.length * xsqsum - Math.pow(xsum, 2);
        double slope = num / denom;

        return slope;
    }

    public double predictVal(double userin){
        double i = interceptCalc();
        double s = slopeCalc();

        double pred = i + s * userin;
        return pred;
    }

    public String getRegEquation(){
        String eq = String.format("Predicted %S = %f + %f x %S", 
            nameY, interceptCalc(), slopeCalc(), nameX);

        return eq;
    }

    public double[] getResiduals(){
        double[] predictedValues = new double[y.length];
        double[] residuals = new double[y.length];
        // create array with predicted values from all data points
        for (int i = 0; i < x.length; i++) {
            // use predict val function on all x var values, save into array
            predictedValues[i] = predictVal(x[i]);
        }
        // calculate difference between observed and predicted values
        for (int i = 0; i < predictedValues.length; i++) {
           residuals[i] = y[i] - predictedValues[i];
        }
        
        // return residuals
        return residuals;
    }

    public double getRMSE(){
        double[] predictedValues = new double[y.length];
        double rmseSums = 0;
        // create array with predicted values from all data points
        for (int i = 0; i < x.length; i++) {
            // use predict val function on all x var values, save into array
            predictedValues[i] = predictVal(x[i]);
        }
        // calculate difference between observed and predicted values
        for (int i = 0; i < predictedValues.length; i++) {
            rmseSums = Math.pow(y[i] - predictedValues[i], 2);
        }
        // calculate rmse
        double rmse = Math.sqrt(rmseSums/y.length);
        // return rmse
        return rmse;
    }

    public double getSE(){
        double[] predictedValues = new double[y.length];
        double seSums = 0;
        // create array with predicted values from all data points
        for (int i = 0; i < x.length; i++) {
            // use predict val function on all x var values, save into array
            predictedValues[i] = predictVal(x[i]);
        }
        // calculate difference between observed and predicted values
        for (int i = 0; i < predictedValues.length; i++) {
            seSums = Math.pow(y[i] - predictedValues[i], 2);
        }
        // calculate rmse
        double rmse = Math.sqrt(seSums/(y.length - 2));
        // return rmse
        return rmse;
    }

    public double residualOutliers(){
        double[] residuals = getResiduals();
        Arrays.sort(residuals);
        int n = residuals.length;

        int q1Pos = (n + 1) * 1/4;
        int q3Pos = (n + 1) * 3/4;

        double q1 = residuals[q1Pos];
        double q3 = residuals[q3Pos];
        double iqr = q3 - q1;

        double upperFence = q3 + 1.5 * iqr;
        double lowerFence = q1 - 1.5 * iqr;
        
        int count = 0;

        for (int i = 0; i < residuals.length; i++) {
            if (residuals[i] < lowerFence){
                count += 1;
            }
            else if (residuals[i] > upperFence){
                count += 1;
            }
        }

        return count;
    }

    public double[] getX(){
        return x;
    }

    public double[] getY(){
        return y;
    }
}