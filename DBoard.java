import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

public class DBoard extends JFrame{

    private final Regression model;
    private JTextField inpField;
    private JTextField outField;
    private JButton predButton;
    private double predictedVal = 0;
    
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
        // equation and r square value on scatter plot
        eqLabel.setText(
            String.format("<html>%S<br/>R Squared: %f</html>", 
            model.getRegEquation(), model.rSqrd()));
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

        // label for residual word
        JLabel resLabel = new JLabel();
        resLabel.setText("Residuals");
        resLabel.setBounds(0,10, 275, 375);
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
        // RMSE, Rsqr, R

        JLabel corrLabel = new JLabel();
        corrLabel.setText(
            String.format(
            "<html>Correlation: %f<br/>R Squared: %f<br/>RMSE: %f<br/>SE: %f</html>",
                model.correl(), model.rSqrd(),model.getRMSE(), model.getSE()));
        corrLabel.setBounds(20,410,200, 200);
        corrLabel.setVerticalAlignment(JLabel.TOP);
        corrLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        corrLabel.setForeground(Color.white);

        JLabel corrMeaningLabel = new JLabel();
        corrMeaningLabel.setText(
            String.format(
            "<html>%.2f%% of variability in the response variable " +
            "is accounted for by the variability in the explanatory variable. "+
            "%s</html>",
                model.rSqrd() * 100, model.getCorrDesc()));
        corrMeaningLabel.setBounds(220,410,400, 200);
        corrMeaningLabel.setVerticalAlignment(JLabel.TOP);
        corrMeaningLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        corrMeaningLabel.setForeground(Color.white);

        // input for predicting value
        inpField = new JTextField();
        inpField.setBounds(660,420,150,30);
        inpField.setFont(new Font("SansSerif", Font.PLAIN, 18));

        outField = new JTextField();
        outField.setBounds(660,500,150,30);
        outField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        outField.setEditable(false);

        predButton = new JButton("PREDICT Y");
        predButton.setBounds(670,460,130,30);
        predButton.setFont(new Font("SansSerif", Font.PLAIN, 18));
        predButton.setFocusable(false);
        predButton.setBackground(secondB);
        predButton.setForeground(Color.green);
        predButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if (inpField.getText().length() < 10){
                    double predictVal = model.predictVal(Double.valueOf(inpField.getText()));
                    outField.setText(String.format("%f", predictVal));
                }
                else{
                    JOptionPane.showMessageDialog(null,
                     "Invalid Input", 
                     "Validation Result", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel predPanel = new JPanel();
        predPanel.setBackground(secondB);
        predPanel.setPreferredSize(new Dimension(500, 200));
        predPanel.setBounds(400,400,500,200);
        predPanel.setLayout(new BorderLayout());
    
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

        upperMet.add(corrLabel);
        upperMet.add(corrMeaningLabel);
        upperMet.add(inpField);
        upperMet.add(predButton);
        upperMet.add(outField);
        // upperMet.add(corrPanel);
        upperMet.add(predPanel);
        add(upperMet);

        pack();
        setVisible(true); // make frame visible
    }
}
