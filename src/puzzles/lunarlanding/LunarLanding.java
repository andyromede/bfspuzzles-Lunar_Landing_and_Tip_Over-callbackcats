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
        LunarLandingConfig init = new LunarLandingConfig(args[0]);
        List<Configuration> list = Solver.solve(init);
        if(list.isEmpty()){
            System.out.println("No solution");
        }
        for(int i = 0; i < list.size(); i++) {
            System.out.println("Step " + i + ": " + list.get(i));
        }
    }
}
