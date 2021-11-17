package puzzles.clock;

import solver.Solver;

import java.util.List;

/**
 * Main class for the "clock" puzzle.
 *
 * @author Andrew Le
 */
public class Clock{

    /**
     * Run an instance of the clock puzzle.
     * @param args [0]: number of hours on the clock;
     *             [1]: starting time on the clock;
     *             [2]: goal time to which the clock should be set.
     */
    public static void main( String[] args ) {
        if ( args.length != 3 ) {
            System.out.println( "Usage: java Clock hours start end" );
        }
        else {
            //when the correct arguments are provided, then the else code is executed
            int hours = Integer.parseInt(args[0]);
            int start = Integer.parseInt(args[1]);
            int end = Integer.parseInt(args[2]);
            System.out.println("java Clock " + hours + " " + start + " " + end + "\n"
                    + "Hours: " + hours + ", Start: " + start + ", End: " + end);
            ClockConfig clock = new ClockConfig(hours, start, end);
            List<Configuration> list = Solver.solve(clock);
            if(list.isEmpty()){
                System.out.println("No solution");
            }
            for(int i = 0; i < list.size(); i++) {
                System.out.println("Step " + i + ": " + list.get(i));
            }

        }

    }
}