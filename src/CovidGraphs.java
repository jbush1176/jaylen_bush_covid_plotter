import java.awt.Container;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;

import org.math.plot.Plot2DPanel;
import org.math.plot.plotObjects.*;

import java.awt.BorderLayout;
import java.awt.Color;

public class CovidGraphs {
	static void daily() {

        
            }
    public static double[] dailyDeaths(double[] data) {
    	double[] pizza = new double[data.length];
    	for(int i = 1; i < pizza.length; i++) {
    		pizza[i] = data[i] - data[i-1];
    	}
    	return pizza;
    }
	

	public static LinkedHashMap<String,double[]> readData(Scanner fsc) {
        // declare the data structure I'll return
        LinkedHashMap<String,double[]> result = new LinkedHashMap<String,double[]>();
        fsc.nextLine();  // grab the header line
        String[] parts;
        String line; 
        double[] values;   // the values I read from each line
        String country; // the country from the line
        while (fsc.hasNextLine()) {
            line = fsc.nextLine();
            parts = line.split("\t");
            country = parts[0];
            // the rest of the line are the values
            values = new double[parts.length-1];
            for (int i = 1; i < parts.length; i++) {
                values[i-1] = Double.parseDouble(parts[i]);
            }
            result.put(country,values);
        }
        return result;
    }
    public static double[] getDays(int howMany) {
        double[] result = new double[howMany];
        for (int i = 0; i < howMany; i++) {
            result[i] = i;
        }
        return result;
    }
    public static void setUpAndShowFrame(Plot2DPanel plot) {
    	Scanner sc = new Scanner(System.in);
        int countryChoice;
        
        
        //Terminal output
       

        //for loop that waits for 3 to exit

       
       
        JFrame frm = new JFrame();
        frm.setTitle("Country Values");
        frm.setBounds(100,100,500,500);
        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // just get rid of frame
        Container c = frm.getContentPane();
		// Be sure to put this in both sets of code so that it gives cumulative and daily
        BaseLabel title = new BaseLabel ("Cumulative Deaths", Color.RED,0.5,1.1); // 50% of the width, 110% of the height
        c.setLayout(new BorderLayout());
        c.add(plot,BorderLayout.CENTER);
        frm.setVisible(true);
    }
    public static void main(String[] args) {
        LinkedHashMap<String,double[]> stats = null;
        String countrys;
        String choice; // for c or d
        String[] parts;
        double[] data; // each person's invesment data retrieved from the stats map
        Scanner sc = new Scanner(System.in);
        try {
            Scanner fsc = new Scanner(new File("C:\\Users\\JBush\\Documents\\Object Oriented Programming\\countries_deaths.txt"));
            stats = readData(fsc);
            fsc.close();
        } catch (Exception ex) {
            stats = null;
        }
        if (stats == null) {
            System.out.println("Sorry. I couldn't read the data.");
        } else {
            // the data has been successfully loaded in. Let's work with it.
            do {
            	System.out.println("******************************************");
                System.out.println("* INTERNATIONAL COVID-19 MORTALITY RATES *");
                System.out.println("******************************************");
            	System.out.print("Enter names of countries separated by commas (or type 'exit'): ");
                countrys = sc.nextLine();
                System.out.println("[C]umulative or [D]aily?");
                choice = sc.nextLine();
                //System.out.println(choice);
                
                
                	
                
                	
                if (!countrys.equalsIgnoreCase("exit")) {
                    Plot2DPanel plot = new Plot2DPanel(); 
                    plot.addLegend("SOUTH");
                    parts = countrys.split(",");
                    // add line plots for each person the user country
                    for (String part : parts) {
                        part = part.trim();  // get rid of leading and trailing spaces
                        if (stats.containsKey(part) == false) {
                            System.out.printf("%s was not found.\n",part);
                        }
                        else {
                            data = stats.get(part); 
                            // now plot the data
                            //plot.addLinePlot(part,getDays(data.length),data);//C
                            if (choice.equalsIgnoreCase("d"))  {
                            	plot.addLinePlot(part,getDays(data.length),dailyDeaths(data));//D
                            	BaseLabel title = new BaseLabel("Daily", Color.RED, 0.5, 1.1);
                                plot.addPlotable(title);
                                
                            }
                            else if (choice.equalsIgnoreCase("c")) {
                                plot.addLinePlot(part,getDays(data.length),data);//C
                                BaseLabel title = new BaseLabel("Cumulative", Color.RED, 0.5, 1.1);
                                plot.addPlotable(title);
                            }
                        }
                    }
                    //      
                   plot.setAxisLabel(0,"Day");
                   plot.setAxisLabel(1, "Deaths");
                    setUpAndShowFrame(plot);
                } 
               
            } while (!countrys.equalsIgnoreCase("exit"));
            
            
        }// ask for c or d, (string name, x and y), the base label will show which graph you make. make an array of 242 
        // org.math.plot.plotObjects. 
    }
}
   
