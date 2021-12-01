package puzzles.lunarlanding.model;

import puzzles.clock.ClockConfig;
import solver.Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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
            figureList = new ArrayList<>();
            System.out.println(rDim + " " + cDim + " " + rCor + " " + cCor );
            numberofCellsNeeded = rDim * cDim;
            //in.nextLine();
            //creates the board and adds the explorer
            this.board = new String[rDim][cDim];
            board[rCor][cCor] = "!";

            //goes through the second line and beyond and adds the figures to a list of figures
            String temp = in.nextLine();
            while(in.hasNext() && !temp.isEmpty()){
                System.out.println(temp);
                temp = (String) temp;
                figureList.add(temp);
                temp = in.nextLine();
//
//                 = temp.split();
//                String name = temp[0];
//                int row = parseInt(temp[1]);
//                int col = parseINt(xx[2]);
//                board[row][col] = name;
            }
            String array[] = temp.split(" ");

            for(int i = 0; i < figureList.size(); i++){
                String fields[] = figureList.get(i).split(" ");
                int figureRow = Integer.parseInt(fields[1]);
                int figureCol = Integer.parseInt(fields[2]);
                board[figureRow][figureCol] = fields[0];
                System.out.print(" 1. " + fields[0]);
                System.out.print(" 2. " + fields[1]);
                System.out.print(" 3. " + fields[2]);
            }
            String s = "";
            //s += "\t";
            s += "\n";
            s += "   " ;
            for(int col = 0; col < cDim; col++){
                s += " " + col + " ";
            }
            s += "\n";
            s += "   " ;
            for(int col = 0; col < cDim; col++){
                s += "---";
            }
            for(int row = 0; row< rDim; row++) {
                s += "\n";
                s += row + " | ";
                for (int col = 0; col < cDim; col++) {
                    if(this.board[row][col] == null){
                        s += "-" + "  ";
                    }
                    else
                    s += this.board[row][col] + " ";
                }
            }
            System.out.println(s);

        } // try-with-resources, the file is closed automatically
        catch(FileNotFoundException e){
            System.out.println("Error: Invalid File Destination");

        }
        catch(NumberFormatException e){
            System.out.println("Error: Invalid File Format");
        }
        //Setting the cursor
        this.row = -1;
        this.col = 0;
    }
    /**
     * The copy constructor takes a config, other, and makes a full "deep" copy
     * of its instance data.
     *
     * @param other the config to copy
     */
    protected LunarLandingConfig(LunarLandingConfig other) {
        // TODO
        this.row = other.row;
        this.col = other.col;

        //copies the other board so both this and other don't point to the same reference
        this.board = new String[rDim][cDim];
        for(int row = 0; row < rDim; row++){
            System.arraycopy(other.board[row], 0, this.board[row], 0, cDim);
        }

    }
    @Override
    public Collection<Configuration> getSuccessors() {
        LinkedList<Configuration> successor = new LinkedList<>();

        //if the cursor is at the end of the board, then it returns a blank successor
        if (row == rDim - 1 && col == cDim-1) {
            return successor;
        }
        //the cursor moves by one cell
        if(row == rDim-1) {
            row = 0;
            col++;
        }
        else {
            row++;
        }
        //if the cursor points to a non-empty cell, then a new successor will be
        // created
        if(!board[row][col].equals("-")){
            //Numbered island case
            LunarLandingConfig numIsland = new LunarLandingConfig(this);
            successor.add(numIsland);
            return successor;
        }
        //creates new configurations for the sea and island case
        //and puts either a @ or # into the empty cell specified

        //island case
        NurikabeConfig island = new NurikabeConfig(this);
        island.board[row][col] = "#";
        successor.add(island);

        //sea case
        NurikabeConfig sea = new NurikabeConfig(this);
        sea.board[row][col] = "@";
        successor.add(sea);

        //Successors are returned
        return successor;
    }


    @Override
    public boolean isGoal() {
        return false;
    }

    @Override
    public Collection<Configuration> getSuccessors() {
        return null;
    }

    @Override
    public String toString() {
        String s = "";
        //s += "\t";
        s += "\n";
        s += "   " ;
        for(int col = 0; col < cDim; col++){
            s += " " + col + " ";
        }
        s += "\n";
        s += "   " ;
        for(int col = 0; col < cDim; col++){
            s += "---";
        }
        for(int row = 0; row< rDim; row++) {
            s += "\n";
            s += row + " | ";
            for (int col = 0; col < cDim; col++) {
                if(this.board[row][col] == null){
                    s += "-" + "  ";
                }
                else
                    s += this.board[row][col] + " ";
            }
        }
        return s;
    }

}
