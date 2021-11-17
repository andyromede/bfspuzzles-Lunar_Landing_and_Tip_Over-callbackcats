package puzzles.lunarlanding.model;

import puzzles.clock.ClockConfig;
import solver.Configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

/**
 * DESCRIPTION
 * @author YOUR NAME HERE
 * November 2021
 */
public class LunarLandingConfig implements Configuration {

    //a board
    private String[][] board;
    //dimension of the board
    private static int rDim;
    private static int cDim;
    //lunar lander coordinates
    private static int rCor;
    private static int cCor;
    //cursor
    private int row;
    private int col;

    //an array list of characters
    private ArrayList<String> figureList;
    //
    int numberofCellsNeeded;
    private List<ClockConfig> successors;

    public LunarLandingConfig(String filename) {
        try (Scanner in = new Scanner(new File(filename))) {
            // creates the rows and columns
            String[] line = in.nextLine().split("\\s");
            rDim = Integer.parseInt(line[0]);
            cDim = Integer.parseInt(line[1]);
            rCor = Integer.parseInt(line[2]);
            cCor = Integer.parseInt(line[3]);
            numberofCellsNeeded = rDim * cDim;
            in.nextLine();
            //creates the board
            this.board = new String[rDim][cDim];

            //goes through the second line and beyond and adds the figures to a list of figures
            String temp = in.nextLine();
            while(in.hasNext() && !temp.isEmpty()){
                System.out.println(temp);
                figureList.add(temp);
                temp = in.nextLine();
            }

        } // try-with-resources, the file is closed automatically
        catch(Exception e){
            System.out.println(e);
        }
        //Setting the cursor
        this.row = -1;
        this.col = 0;
    }

    @Override
    public boolean isGoal() {
        return false;
    }

    @Override
    public Collection<Configuration> getSuccessors() {
        return null;
    }

}
