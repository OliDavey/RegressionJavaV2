import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Arrays;
import javax.swing.JPanel;


public class SCPlotPanel extends JPanel{

    // x, y
    // slope
    // intercept,
    // size
    private Regression model;
    private double[] x;
    private double[] y;
    private int length;
    private double slope;
    private double intercept;


    public SCPlotPanel(Regression model) {
        this.model = model;
        this.setPreferredSize(new Dimension(600, 400));
        init();
    }

    private void init(){
        this.x = model.getX();
        this.y = model.getY();
        this.length = x.length;
        this.slope = model.slopeCalc();
        this.intercept = model.interceptCalc();
    }

    // panel size is 600 by 400
    // bottom right corner is 600,390
    // working dimensions start at 10, 10, end at 570, 370
    // gd.drawRect(10, 10, 560, 360);
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D gd = (Graphics2D) g;

        gd.setPaint(Color.WHITE);
        gd.setStroke(new BasicStroke(2));
        gd.drawLine(10, 370, 570,370);
        gd.drawLine(10, 370, 10, 10);

        int xAxis = 10;
        int yAxis = 10;

        for (int i = 0; i < 22; i++) {
            gd.drawLine(xAxis, 370, xAxis, 365);
            xAxis += 28;
            gd.drawLine(10, yAxis, 15, yAxis);
            yAxis += 18;
        }

        gd.setPaint(Color.GREEN);
        gd.setStroke(new BasicStroke(3f));


        // scaling data
        double[] scaleXY = model.interval(560, 360);
        double scaleX = scaleXY[0];
        double scaleY = scaleXY[1];
        double[] scaledX = new double[length];
        double[] scaledY = new double[length];
        double minX = Arrays.stream(x).min().getAsDouble();
        double minY = Arrays.stream(y).min().getAsDouble();

        for (int i = 0 ; i < length; i++){
            scaledX[i] = (x[i] * scaleX) - (minX * scaleX) + 10;
            scaledY[i] = (y[i] * scaleY) - (minY * scaleY) + 10;
        }

        // sorting data
        double[] sortedX = new double[length];
       
        for(int i = 0; i < length; i++) {
            sortedX[i] = scaledX[i];
        }

        Arrays.sort(sortedX);

        double[] sortedY = new double[length];
       
        for(int i = 0; i < length; i++) {
            sortedY[i] = scaledY[i];
        }

        Arrays.sort(sortedY);
        
        double nSlope = model.slopeCalc(scaledX, scaledY);
        

        // calcing points for line of best fit
        double start = intercept + nSlope * sortedX[0];
        double stop = intercept + nSlope * sortedX[length-1];

        // line of best fit
        gd.setStroke(new BasicStroke(2));
        gd.setPaint(Color.cyan);
        gd.drawLine(10 + (int) sortedX[0] , // x1 
                    370 - (int) start, // y1
                    10 + (int) sortedX[length-1], //x2
                    370 - (int) stop); // y2

        // takes in unsorted data
        Point[] points = new Point[length];
        for (int i = 0; i < length; i++) {
            points[i] = new Point((int) scaledX[i], 
                        (int) scaledY[i]);
        }

        gd.setStroke(new BasicStroke(3f));
        gd.setPaint(Color.green);
        for (int i = 0; i < length; i++) {
            gd.fillOval(10 + points[i].x, 370 - points[i].y, 5, 5);
        }
    }
}
