package puzzles.water;

import solver.Configuration;
import solver.Solver;

import java.util.ArrayList;
import java.util.List;

/**
 * Main class for the water buckets puzzle.
 *
 * @author Andrew Le
 */
public class Water{

    /**
     * Run an instance of the water buckets puzzle.
     * @param args [0]: desired amount of water to be collected;
     *             [1..N]: the capacities of the N available buckets.
     */
    public static void main( String[] args ) {
        if ( args.length < 2 ) {
            System.out.println(
                    ( "Usage: java Water amount bucket1 bucket2 ..." )
            );
        }
        else {
            //when the correct arguments are provided, then the else code is executed
            int amount = Integer.parseInt(args[0]);
            ArrayList<Integer> currentBuckets = new ArrayList<>();
            ArrayList<Integer> buckets = new ArrayList<>();
            for( int i = 1; i < args.length ; i++){
                int bucket = Integer.parseInt(args[i]);
                buckets.add(bucket);
                currentBuckets.add(0);
            }
            System.out.println("java Water " + amount + " " + buckets + "\n" +
                                "Amount: " + amount + ", Buckets: " + buckets);
            WaterConfig water = new WaterConfig(amount, buckets, currentBuckets);
            List<Configuration> list = Solver.solve(water);
            if(list.isEmpty()){
                System.out.println("No solution");
            }
            for(int i = 0; i < list.size(); i++) {
                System.out.println("Step " + i + ": " + list.get(i));
            }
        }


    }
}
