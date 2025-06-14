import java.awt.Graphics;
import java.awt.Graphics2D;
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
        init();
    }

    private void init(){
        this.x = model.getX();
        this.y = model.getY();
        this.length = x.length;
        this.slope = model.slopeCalc();
        this.intercept = model.interceptCalc();
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D gd = (Graphics2D) g;

    }
}
