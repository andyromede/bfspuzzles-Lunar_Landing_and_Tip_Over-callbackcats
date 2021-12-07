package puzzles.tipover;

import puzzles.tipover.model.TipOverConfig;
import solver.Configuration;
import solver.Solver;

import java.util.List;

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
