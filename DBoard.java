import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

public class DBoard extends JFrame{

    private final Regression model;
    
    public DBoard(Regression model){
        this.model = model;

        setTitle("Regression Dashboard"); //sets title of frame
        setPreferredSize(new Dimension(900,600)); // sets x and y dim of frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit when x button clicked
        setResizable(false);
        Color mainB = new Color(0x141A2D);
        Color secondB = new Color(0x252c40);
        
        ImageIcon image = new ImageIcon("resc\\regModelv2logo.png"); // create image icon
        setIconImage(image.getImage()); // change icon of frame
        getContentPane().setBackground(mainB); // change background colour
    
        // reg equation label
        JLabel eqLabel = new JLabel();
        eqLabel.setText(model.getRegEquation());
        eqLabel.setBounds(10,10, 580, 80);
        // eqLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        eqLabel.setHorizontalAlignment(JLabel.CENTER);
        eqLabel.setVerticalAlignment(JLabel.TOP);
        eqLabel.setHorizontalTextPosition(JLabel.CENTER);
        eqLabel.setVerticalTextPosition(JLabel.CENTER);
        eqLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        eqLabel.setForeground(Color.white);

        // scatter graph panel 1
        SCPlotPanel scpp = new SCPlotPanel(model);
        scpp.setBackground(secondB);
        //scpp.setBorder(new MatteBorder(5,5,5,5,Color.BLUE));

        JLabel resLabel = new JLabel();
        resLabel.setText("Residuals");
        resLabel.setBounds(0,10, 275, 380);
        resLabel.setHorizontalAlignment(JLabel.CENTER);
        resLabel.setVerticalAlignment(JLabel.BOTTOM);
        resLabel.setHorizontalTextPosition(JLabel.CENTER);
        resLabel.setVerticalTextPosition(JLabel.CENTER);
        resLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        resLabel.setForeground(Color.white);

        // box plot panel 
        BXPlotPanel bxpp = new BXPlotPanel(model);
        bxpp.setBackground(secondB);

        // left side 
        JPanel regPanel = new JPanel();
        regPanel.setBackground(Color.WHITE);
        regPanel.setBounds(0,0, 600, 400);
        regPanel.setLayout(new BorderLayout());
        regPanel.setBorder(new MatteBorder(10,10,10,5, mainB));

        // right side
        JPanel infPanel = new JPanel();
        infPanel.setBackground(secondB);
        infPanel.setBounds(600,0, 285, 400);
        infPanel.setLayout(new BorderLayout());
        infPanel.setBorder(new MatteBorder(10,5,10,10, mainB));

        // stats for the model
        // RMSE, Rsqr, R, CI 95%
        JLabel RLabel = new JLabel();
        RLabel.setText("Reyyy");
        RLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        RLabel.setBounds(10,10, 300, 200);
        RLabel.setHorizontalAlignment(JLabel.CENTER);
        RLabel.setVerticalAlignment(JLabel.CENTER);
        RLabel.setHorizontalTextPosition(JLabel.CENTER);
        RLabel.setVerticalTextPosition(JLabel.BOTTOM);
        RLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        RLabel.setForeground(Color.white);

        // bottom
        JPanel upperMet = new JPanel();
        upperMet.setBackground(secondB);
        upperMet.setBounds(0,400,900,200);
        upperMet.setLayout(new BorderLayout());
        upperMet.setBorder(new MatteBorder(10,10,10,10, mainB));

        scpp.add(eqLabel);
        regPanel.add(scpp);
        add(regPanel);
        
        
        infPanel.add(resLabel);
        infPanel.add(bxpp);
        add(infPanel);

        upperMet.add(RLabel);
        add(upperMet);

        pack();
        setVisible(true); // make frame visible
    }
}
