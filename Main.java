import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    
    public static void main(String[] args) {
        // getting and formating data
        String filep = "exampleData\\Roller_CoastersData.csv"; // <-- add ability to input own csv through ui later
        String line;
        BufferedReader br;
        List<String[]> data = new ArrayList<>();
        String[][] dataTwo = {};
        
        try {
            br = new BufferedReader(new FileReader(filep));
            while ((line = br.readLine()) != null) { 
                // singular line e.g 1,2,3 in csv file
                data.add(line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1));
            }
            dataTwo = data.toArray(new String[0][]);
            br.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        // creating regression object
        Regression regModel = new Regression(dataTwo, 1, 2);
        // testing functions 
        System.out.println(regModel.getRegEquation());
        System.out.println(regModel.correl());
        System.out.println(regModel.rSqrd());
        System.out.println(regModel.getRMSE());
        System.out.println(regModel.residualOutliers());
    }
}
