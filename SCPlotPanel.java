import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
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
        gd.setStroke(new BasicStroke(5));
        gd.drawLine(10, 370, 570,370);
        gd.drawLine(10, 370, 10, 10);

        gd.setPaint(Color.GREEN);
        gd.setStroke(new BasicStroke(3f));

        Point[] points = new Point[length];
        for (int i = 0; i < length; i++) {
            points[i] = new Point((int) x[i], (int) y[i]);
        }
        
        for (int i = 0; i < points.length; i++) {
            gd.fillOval(points[i].x, 300 - points[i].y, 5, 5);
        }

        double start = intercept + slope * x[0];
        double stop = intercept + slope * x[length-1];

        gd.setStroke(new BasicStroke(1));
        gd.setPaint(Color.cyan);
        gd.drawLine(points[0].x, 300 - (int) start, points[length-1].x, 300 - (int) stop);
        
    }
}
