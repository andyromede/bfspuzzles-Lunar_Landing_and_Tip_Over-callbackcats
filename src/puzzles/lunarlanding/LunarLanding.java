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
            config = new LunarLandingConfig(args[0]);
            List<Configuration> list = Solver.solve(config);
            if(list.isEmpty()){
                System.out.println("No solution");
            }
            for(int i = 0; i < list.size(); i++) {
                System.out.println("Step " + i + ": " + list.get(i));
            }
        }
    }
}
