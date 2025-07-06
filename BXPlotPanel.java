import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class BXPlotPanel extends JPanel{
    
    private Regression model;
    private double[] residuals;
    private double[] outliers;
    private double upperFence;
    private double lowerFence;
    private double scale;
    private double median;
    private double q1;
    private double q3;

    public BXPlotPanel(Regression model) {
        this.model = model;
        this.setPreferredSize(new Dimension(285, 400));
        init();
    }

    private void init(){
        this.residuals = model.getResiduals();
        this.outliers = model.Outliers();
        this.upperFence = model.getUpperFence();
        this.lowerFence = model.getLowerFence();
        this.scale = model.scaleRes(360);
        this.median = model.getMedian(residuals);
        this.q1 = model.getQ1();
        this.q3 = model.getQ3();
    }

    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D gd = (Graphics2D) g;

        // fences 
        gd.setColor(Color.cyan);
        gd.drawLine(100, getHeight() / 2 - (int) (upperFence * scale), 
                    185, getHeight() / 2 - (int) (upperFence * scale));


        gd.drawLine(100, getHeight() / 2 - (int) (lowerFence * scale),
                    185, getHeight() / 2 - (int) (lowerFence * scale));
        // left side 
        gd.drawLine(100, getHeight() / 2 - (int) (q3 * scale), 
                    100, getHeight() / 2 - (int) (q1 * scale));
        // right side 
        gd.drawLine(185, getHeight() / 2 - (int) (q3 * scale), 
                    185, getHeight() / 2 - (int) (q1 * scale));

        // q1 and 3
        gd.drawLine(100, getHeight() / 2 - (int) (q3 * scale), 
                    185, getHeight() / 2 - (int) (q3 * scale));


        gd.drawLine(100, getHeight() / 2 - (int) (q1 * scale),
                    185, getHeight() / 2 - (int) (q1 * scale));

        gd.drawLine(getWidth() / 2, getHeight() / 2 - (int) (upperFence * scale), 
                    getWidth() / 2, getHeight() / 2 - (int) (q3 * scale));

        gd.drawLine(getWidth() / 2, getHeight() / 2 - (int) (q1 * scale), 
                    getWidth() / 2, getHeight() / 2 - (int) (lowerFence * scale));

        gd.setColor(Color.green);
        // median line
        gd.drawLine(100, getHeight() / 2 - (int) (median * scale), 
                    185, getHeight() / 2 - (int) (median * scale));
        
        gd.setColor(Color.red);
        gd.setStroke(new BasicStroke(3));
        for (int i = 0; i < outliers.length; i++) {
            double spot = outliers[i] * scale; 
            gd.fillOval((int) (getWidth() / 2) , getHeight() / 2 - (int) spot, 5, 5);
        }
    }
}
