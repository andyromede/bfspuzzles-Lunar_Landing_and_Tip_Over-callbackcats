package puzzles.lunarlanding.model;

import puzzles.lunarlanding.LunarLanding;
import puzzles.lunarlanding.model.LunarLandingConfig;
import util.Observer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * DESCRIPTION
 * @author Andrew Le
 * November 2021
 */
public class LunarLandingModel {

    private LunarLandingConfig currentConfig;
    private String[][] board;
    private List<Observer< LunarLandingModel, Object> > observers;


    public LunarLandingModel(LunarLandingConfig config) {
        this.observers = new LinkedList<>();
        currentConfig = config;
        this.board = currentConfig.returnBoard();
    }

    public LunarLandingConfig returnConfig(){
        return currentConfig;
    }
    public String[][] returnBoard(){
        return currentConfig.returnBoard();
    }
    public void load(String txtFile){
        System.out.println("New file loaded");
    }

    public void reload(){

    }

    public void choose(int row, int col){
        //if()
    }

    public void go(String direction, int row, int col){
        boolean legal = false;
        int rDim = board.length;
        int cDim = board[0].length;
        //System.out.println(rDim +" "+ cDim);
        System.out.println(board[row][col] +" "+ row + " "+ col);
        if(direction.equals("north")){
           //will first check to see if it is a legal
            //checks up
            System.out.println("works up");
            for (int i = row - 1; i > -1; i--) {
                if (!board[i][col].equals("-")) {
                    if (this.board[i][col].equals("!")) {
                        //do nothing if the cursor is equal to "!"
                    } else {
                        legal = true;
                        String temp = "";
                        //has a "!"
                        if (board[row][col].length() == 2) {
                            temp = board[row][col].substring(1);
                            board[row][col] = "!";
                            if (board[i + 1][col].equals("!")) {
                                board[i + 1][col] = "!" + temp;
                            } else {
                                board[i + 1][col] = temp;
                            }
                        }
                        //just the letter
                        else {
                            temp = board[row][col];
                            //turns the cursor to "-" and then places the temp variable in front of
                            //the figure
                            board[row][col] = "-";
                            //if the index in front of the figure has a lunar lander ("!"), then they get stuck together ex:(!E)
                            if (board[i + 1][col].equals("!")) {
                                board[i + 1][col] = "!" + temp;
                            } else {
                                board[i + 1][col] = temp;
                                System.out.println("works usadsap");
                            }

                        }
                        announce("notwin");
                    }
                }
            }
            if(!legal){
                System.out.println("Illegal move");
            }

        }
        else if(direction.equals("south")){
            //will first check to see if it is a legal
            //checks up
            System.out.println("works down");
            for (int i = row + 1; i < rDim; i++) {
                if (!board[i][col].equals("-")) {
                    if (this.board[i][col].equals("!")) {
                        //do nothing if the cursor is equal to "!"
                    } else {
                        legal = true;
                        String temp = "";
                        //has a "!"
                        if (board[row][col].length() == 2) {
                            temp = board[row][col].substring(1);
                            board[row][col] = "!";
                            if (board[i - 1][col].equals("!")) {
                                board[i - 1][col] = "!" + temp;
                            } else {
                                board[i - 1][col] = temp;
                            }
                        }
                        //just the letter
                        else {
                            temp = board[row][col];
                            //turns the cursor to "-" and then places the temp variable in front of
                            //the figure
                            board[row][col] = "-";
                            //if the index in front of the figure has a lunar lander ("!"), then they get stuck together ex:(!E)
                            if (board[i - 1][col].equals("!")) {
                                board[i - 1][col] = "!" + temp;
                            } else {
                                board[i - 1][col] = temp;
                                System.out.println("works usadsap");
                            }

                        }
                        announce("notwin");
                    }
                }
            }
            if(!legal) {
                System.out.println("Illegal move");
            }

        }
        else if(direction.equals("east")){
            //will first check to see if it is a legal
            //checks east
            System.out.println("works east");
            for (int i = col + 1; i < cDim; i++) {
                if (!board[i][col].equals("-")) {
                    if (this.board[i][col].equals("!")) {
                        //do nothing if the cursor is equal to "!"
                    } else {
                        legal = true;
                        String temp = "";
                        //has a "!"
                        if (board[row][col].length() == 2) {
                            temp = board[row][col].substring(1);
                            board[row][col] = "!";
                            if (board[row][i - 1].equals("!")) {
                                board[row][i - 1] = "!" + temp;
                            } else {
                                board[row][i - 1] = temp;
                            }
                        }
                        //just the letter
                        else {
                            temp = board[row][col];
                            //turns the cursor to "-" and then places the temp variable in front of
                            //the figure
                            board[row][col] = "-";
                            //if the index in front of the figure has a lunar lander ("!"), then they get stuck together ex:(!E)
                            if (board[row][i - 1].equals("!")) {
                                board[row][i - 1] = "!" + temp;
                            } else {
                                board[row][i - 1] = temp;
                                System.out.println("works usadsap");
                            }

                        }
                        announce("notwin");
                    }
                }
            }
            if(!legal) {
                System.out.println("Illegal move");
            }

        }
        else if(direction.equals("west")){
            //will first check to see if it is a legal
            //checks west
            System.out.println("works west");
            for (int i = col - 1; i > -1; i--) {
                if (!board[i][col].equals("-")) {
                    if (this.board[i][col].equals("!")) {
                        //do nothing if the cursor is equal to "!"
                    } else {
                        legal = true;
                        String temp = "";
                        //has a "!"
                        if (board[row][col].length() == 2) {
                            temp = board[row][col].substring(1);
                            board[row][col] = "!";
                            if (board[row][i + 1].equals("!")) {
                                board[row][i + 1] = "!" + temp;
                            } else {
                                board[row][i + 1] = temp;
                            }
                        }
                        //just the letter
                        else {
                            temp = board[row][col];
                            //turns the cursor to "-" and then places the temp variable in front of
                            //the figure
                            board[row][col] = "-";
                            //if the index in front of the figure has a lunar lander ("!"), then they get stuck together ex:(!E)
                            if (board[row][i + 1].equals("!")) {
                                board[row][i + 1] = "!" + temp;
                            } else {
                                board[row][i + 1] = temp;
                                System.out.println("works usadsap");
                            }

                        }
                        announce("notwin");
                    }
                }
            }
            if(!legal) {
                System.out.println("Illegal move");
            }

        }
        if(board[rDim/2][cDim/2].equals("!E")){
            announce("win");
        }
    }

    public void hint(){

    }

    public void show(){

    }

    public void help(){

    }

    public void quit(){

    }


    public void addObserver( Observer< LunarLandingModel, Object > obs ) {
        this.observers.add(obs);
    }
    private void announce( String arg){
        for ( var obs: this.observers ) {
            obs.update(this,arg);
        }
    }

    /*
     * Code here includes...
     * Additional data variables for anything needed beyond what is in
     *   the config object to describe the current state of the puzzle
     * Methods to support the controller part of the GUI, e.g., load, move
     * Methods and data to support the "subject" side of the Observer pattern
     *
     * WARNING: To support the hint command, you will likely have to do
     *   a cast of Config to LunarLandingConfig somewhere, since the solve
     *   method works with, and returns, objects of type Configuration.
     */

}
