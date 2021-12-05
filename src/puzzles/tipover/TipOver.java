package puzzles.tipover;

import puzzles.lunarlanding.model.LunarLandingConfig;
import puzzles.tipover.model.TipOverConfig;
import solver.Configuration;
import solver.Solver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * DESCRIPTION
 * @author Maddan Eisenberg
 * November 2021
 */
public class TipOver {
    /*
     * code to read the file name from the command line and
     * run the solver on the puzzle
     */

    public static void main(String[] args ) {
        if ( args.length < 1 ) {
            System.out.println(
                    ( "Usage: text file ..." )
            );
        }
        else{
            TipOverConfig config = new TipOverConfig(args[0]);
            List<Configuration> list = Solver.solve(config);
            if (list.isEmpty())
                System.out.println("No Solution");
            else
                for (int i = 0; i < list.size(); i++)
                    System.out.println("Step " + i + ":" + list.get(i));
        }
    }
}
