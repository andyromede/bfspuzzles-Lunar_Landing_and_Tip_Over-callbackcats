package puzzles.lunarlanding;

import puzzles.lunarlanding.model.LunarLandingConfig;
import puzzles.lunarlanding.model.LunarLandingModel;
import solver.Configuration;
import solver.Solver;
import util.Observer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

import java.util.List;

/**
 * DESCRIPTION
 * @author Andrew Le
 * November 2021
 */

/**
 * Run an instance of the lunar buckets puzzle.
 * args [0]: a text file
 */
public class LunarLanding {
    /**
     * Run an instance of the lunar buckets puzzle.
     * args [0]: a text file
     */

    /**
     * The model for the view and controller.
     */
    private static LunarLandingConfig config;


    public static void main(String[] args ) {
        if ( args.length < 1 ) {
            System.out.println(
                    ( "Usage: text file ..." )
            );
        }
        else{
            try (Scanner in = new Scanner(new File(args[0]))) {
                // creates the rows and columns
                String[] line = in.nextLine().split("\\s");
                int rDim = Integer.parseInt(line[0]);
                int cDim = Integer.parseInt(line[1]);
                int rCor = Integer.parseInt(line[2]);
                int cCor = Integer.parseInt(line[3]);
                ArrayList<String> figureList = new ArrayList<>();
                System.out.println(rDim + " " + cDim + " " + rCor + " " + cCor );
                //goes through the second line and beyond and adds the figures to a list of figures
                String temp = in.nextLine();
                while(in.hasNext() && !temp.isEmpty()){
                    figureList.add(temp);
                    temp = in.nextLine();
                }
                config = new LunarLandingConfig(rDim, cDim, rCor, cCor, figureList);
                List<Configuration> list = Solver.solve(config);
                if(list.isEmpty()){
                    System.out.println("No solution");
                }
                for(int i = 0; i < list.size(); i++) {
                    System.out.println("Step " + i + ": " + list.get(i));
                }
            } // try-with-resources, the file is closed automatically
            catch(FileNotFoundException e){
                System.out.println("Error: Invalid File Destination");

            }
            catch(NumberFormatException e){
                System.out.println("Error: Invalid File Format");
            }
        }
    }
}
