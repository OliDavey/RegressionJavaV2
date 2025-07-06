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

    public double slopeCalc(double[] scX, double[] scY){
        double scXsum = 0;
        double scYsum = 0;
        double scXsqsum = 0;
        double scXYsum = 0;
        for (int i = 0; i < scX.length; i++){
            scXsum += scX[i];
            scYsum += scY[i];
            scXYsum += scX[i] * scY[i];
            scXsqsum += Math.pow(scX[i], 2);
        }

        double num = scX.length * scXYsum - (scXsum * scYsum);
        double denom = scX.length * scXsqsum - Math.pow(scXsum, 2);
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

    public double[] Outliers(){
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
        
        double count = residualOutliers();
        double[] outliers = new double[(int) count];

        int count2 = 0;

        for (int i = 0; i < residuals.length; i++) {
            if (residuals[i] < lowerFence){
                outliers[count2] = residuals[i];
                count2 += 1;
            }
            else if (residuals[i] > upperFence){
                outliers[count2] = residuals[i];
                count2 += 1;
            }
        }

        return outliers;
    }

    public double getUpperFence(){
        double[] residuals = getResiduals();
        Arrays.sort(residuals);
        int n = residuals.length;

        int q1Pos = (n + 1) * 1/4;
        int q3Pos = (n + 1) * 3/4;

        double q1 = residuals[q1Pos];
        double q3 = residuals[q3Pos];
        double iqr = q3 - q1;

        return q3 + 1.5 * iqr;
    }

    public double getLowerFence(){
        double[] residuals = getResiduals();
        Arrays.sort(residuals);
        int n = residuals.length;

        int q1Pos = (n + 1) * 1/4;
        int q3Pos = (n + 1) * 3/4;

        double q1 = residuals[q1Pos];
        double q3 = residuals[q3Pos];
        double iqr = q3 - q1;

        return q1 - 1.5 * iqr;
    }

    public double[] interval(double gLenX, double gLenY){
        // number of data points, length of graph area x, y
        double scaleX = gLenX / Arrays.stream(x).max().getAsDouble();
        // Arrays.sort(y);
        double scaleY = gLenY / Arrays.stream(y).max().getAsDouble() ;
        double[] scales = {Math.ceil(scaleX), Math.ceil(scaleY)};

        return scales;
    }

    public double scaleRes(double gLenY){
        double scale = gLenY / 
            Arrays.stream(getResiduals()).max().getAsDouble() - 10;
        return Math.ceil(scale);
    }

    public double getMedian(double[] arr){
        Arrays.sort(arr);
        double median;
        int arrLength = arr.length;
        // check if even or odd to decide where median lies
        if (arr.length % 2 == 0){
            median = (arr[arrLength/2] + arr[arrLength - 1]) / 2;
        }
        else{
            median = arr[arrLength/2];
        }

        return median;
    }

    public double getQ1(){
        double[] residuals = getResiduals();
        Arrays.sort(residuals);
        int n = residuals.length;

        int q1Pos = (n + 1) * 1/4;
        return residuals[q1Pos];
    }

    public double getQ3(){
        double[] residuals = getResiduals();
        Arrays.sort(residuals);
        int n = residuals.length;
        
        int q3Pos = (n + 1) * 3/4;

        return residuals[q3Pos];
    }

    public double[] getX(){
        return x;
    }

    public double[] getY(){
        return y;
    }
}