package puzzles.lunarlanding;

import puzzles.lunarlanding.model.LunarLandingConfig;
import solver.Configuration;
import solver.Solver;

import java.util.List;

/**
 * DESCRIPTION
 * @author YOUR NAME HERE
 * November 2021
 */

/**
 * Run an instance of the lunar buckets puzzle.
 * args [0]: a text file
 */
public class LunarLanding {

    /*
     * code to read the file name from the command line and
     * run the solver on the puzzle
     */

    public static void main( String[] args ) {
        if ( args.length < 1 ) {
            System.out.println(
                    ( "Usage: text file ..." )
            );
        }
        else{
            //when the correct arguments are provided, then the else code is executed
        }
        System.err.println( "REPLACE THIS METHOD!" );
        LunarLandingConfig init = new LunarLandingConfig(args[0]);
        List<Configuration> list = Solver.solve(water);
    }
}
