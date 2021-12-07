package puzzles.lunarlanding.model;

import puzzles.lunarlanding.LunarLanding;
import puzzles.lunarlanding.model.LunarLandingConfig;
import solver.Configuration;
import solver.Solver;
import util.Observer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.StreamSupport;

/**
 * The LunarLanding model contains the data and the logic of the entire game
 *
 * @author Andrew Le
 * November 2021
 */
public class LunarLandingModel {

    private LunarLandingConfig currentConfig;
    private String[][] board;
    private List<Observer<LunarLandingModel, Object>> observers;
    private String reloadFileName;

    /**
     * Constructor for the model of a LunarLanding game.
     */
    public LunarLandingModel(LunarLandingConfig config, String reloadFileName) {
        this.observers = new LinkedList<>();
        currentConfig = config;
        this.board = currentConfig.returnBoard();
        this.reloadFileName = reloadFileName;
    }

    public LunarLandingConfig returnConfig() {
        return currentConfig;
    }

    public String[][] returnBoard() {
        return currentConfig.returnBoard();
    }

    public void load(String txtFile) {
        LunarLandingConfig config = new LunarLandingConfig(txtFile);
        if (config.returnBoard() != null) {
            reloadFileName = txtFile;
            currentConfig = config;
            board = currentConfig.returnBoard();
            announce("newFile");
        }
    }

    public void reload() {
        load(reloadFileName);
    }

    public boolean choose(String data) {
        if (data.equals("-") || data.equals("!")) {
            announce("chooseAgain");
            return false;
        } else {
            return true;
        }
    }

    public void go(String direction, int row, int col) {
        int rDim = board.length;
        int cDim = board[0].length;
        boolean legal = false;
        if (direction.equals("north")) {
            //will first check to see if it is a legal
            //checks north
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

                            }
                        }
                        announce("notwin");
                        break;
                    }
                }
            }
            if (!legal) {
                announce("Illegal move");
            }

        } else if (direction.equals("south")) {
            //will first check to see if it is a legal
            //checks south
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
                            }
                        }
                        announce("notwin");
                        break;
                    }
                }
            }
            if (!legal) {
                announce("Illegal move");
            }

        } else if (direction.equals("east")) {
            //will first check to see if it is a legal
            //checks east
            for (int i = col + 1; i < cDim; i++) {
                if (!board[row][i].equals("-")) {
                    if (this.board[row][i].equals("!")) {
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
                            }

                        }
                        announce("notwin");
                        break;
                    }
                }
            }
            if (!legal) {
                announce("Illegal move");
            }

        } else if (direction.equals("west")) {
            //will first check to see if it is a legal
            //checks west
            for (int i = col - 1; i > -1; i--) {
                if (!board[row][i].equals("-")) {
                    if (this.board[row][i].equals("!")) {
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
                            }

                        }
                        announce("notwin");
                        break;
                    }
                }
            }
            if (!legal) {
                announce("Illegal move");
            }
        }
        if (board[rDim / 2][cDim / 2].equals("!E")) {
            announce("win");
        }
    }

    /**
     * This method creates a instance of a solver solves the entire configuration, it then takes the first
     * step from the solver list and makes that configuration the new board
     */
    public void hint() {
        int rDim = board.length;
        int cDim = board[0].length;
        //uses the solver to solve the entire config
        List<Configuration> list = Solver.solve(currentConfig.updateConfig(board));
        //if the list is empty then it shows that the config is unsolvable
        if (list.size() == 0) {
            announce("unsolvable");
        }
        //if the list is only one in size then it shows that the config is already solved
        else if (list.size() == 1) {
            announce("alreadySolved");
        } else {
            currentConfig = (LunarLandingConfig) list.get(1);
            board = currentConfig.returnBoard();
            if (board[rDim / 2][cDim / 2].equals("!E")) {
                announce("hintwin");
            } else {
                announce("hint");
            }
        }
    }
    //announces the PTUI to print help
    public void help() {
        announce("help");
    }

    //announces the PTUI to quit
    public void quit() {
        announce("quit");
    }

    //a list of methods that is used to communicate between the model and the GUI or PTUI for MVC purposes
    public String win() {
        return "YOU WIN!";
    }
    public String unsolvable(){
        return "Unsolvable board";
    }
    public String hintWin() {
        return "I WIN!";
    }
    public String illegalMove() {
        return "Illegal move";
    }
    public String alreadySolved() {
        return "Already solved";
    }
    public String chooseAgain() {
        return "No figure at that position";
    }
    public String fileLoaded() {
        return "File loaded";
    }
    public String displayHelp() {
        return "Legal commands are...\n" +
                "\t> help : Show all commands.\n" +
                "\t> reload filename: Load the most recent file again.\n" +
                "\t> load filename: Load a new game board file. (1 argument)\n" +
                "\t> hint : Make the next move for me.\n" +
                "\t> show : Display the board.\n" +
                "\t> go {north|south|east|west}: Tell chosen character where to go. (1 argument)\n" +
                "\t> choose row column: Choose which character moves next. (2 arguments)\n" +
                "\t> quit";
    }

    /**
     * Add a new observer to the list for this model
     * @param obs an object that wants an
     *            {@link Observer#update(Object, Object)}
     *            when something changes here
     */
    public void addObserver(Observer<LunarLandingModel, Object> obs) {
        this.observers.add(obs);
    }

    /**
     * Announce to observers the model has changed;
     */
    private void announce(String arg) {
        for (var obs : this.observers) {
            obs.update(this, arg);
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
