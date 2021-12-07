package puzzles.lunarlanding.ptui;

import puzzles.lunarlanding.model.LunarLandingConfig;
import puzzles.lunarlanding.model.LunarLandingModel;
import util.Observer;

import java.util.Scanner;

/**
 * The plain text user interface for the LunarLanding model
 * @author Andrew Le
 * November 2021
 */
public class LunarLandingPTUI
        implements Observer<LunarLandingModel, Object> {

    //the model and configuration of LunarLanding
    private LunarLandingModel model;
    private LunarLandingConfig config;

    /**
     * Constructor for the PTUI
     */
    public LunarLandingPTUI(String filename) {
        config = new LunarLandingConfig(filename);
        this.model = new LunarLandingModel(config, filename);
        initializeView();
    }

    // CONTROLLER

    /**
     * Read a command and execute loop.
     */
    private void run() {
        Scanner in = new Scanner(System.in);
        boolean chosen = false;
        int figureRowCoor = 0;
        int figureColCoor = 0;
        for (; ; ) {
            System.out.print("> ");
            String line = in.nextLine();
            String[] fields = line.split("\\s+");
            if (fields.length > 0) {
                try {
                    if (fields[0].contains("quit")) {
                        this.model.quit();
                    } else if (fields[0].equals("load")) {
                        this.model.load(fields[1]);
                    } else if (fields[0].equals("reload")) {
                        this.model.reload();
                    } else if (fields[0].equals("hint")) {
                        this.model.hint();
                    } else if (fields[0].equals("help")) {
                        this.model.help();
                    } else if (fields[0].equals("show")) {
                        displayBoard(model.returnConfig());
                    } else if (fields[0].contains("choose")) {
                        String[][] board = model.returnBoard();
                        if (board[Integer.parseInt(fields[1])][Integer.parseInt(fields[2])].equals("-") || board[Integer.parseInt(fields[1])][Integer.parseInt(fields[2])].equals("!")) {
                            System.out.println("No figure at that position");
                        } else {

                            chosen = true;
                            figureRowCoor = Integer.parseInt(fields[1]);
                            figureColCoor = Integer.parseInt(fields[2]);
                        }
                    } else if (fields[0].equals("go")) {
                        if (chosen == false) {
                            System.out.println("Choose a character to move first");
                        } else {
                            if (fields[1].equals("north") || fields[1].equals("south") ||
                                    fields[1].equals("east") || fields[1].equals("west")) {
                                chosen = false;
                                this.model.go(fields[1], figureRowCoor, figureColCoor);
                            } else
                                System.out.print("Directions are\n" +
                                        "[NORTH, EAST, SOUTH, WEST]");
                        }

                    } else {
                        displayHelp();
                    }
                }
                catch (ArrayIndexOutOfBoundsException e)
                {
                    System.out.println("Error: you need to finish the statement.");
                }
            }

        }
    }
    // VIEW

    /**
     * Initialize the view
     */
    public void initializeView() {
        this.model.addObserver(this);
        update(this.model, "notwin");
    }

    //prints out the board
    private void displayBoard(LunarLandingConfig board) {
        System.out.println(board);
    }

    /**
     * Print on standard out help for the game.
     */
    private void displayHelp() {
        System.out.println("Legal commands are...\n" +
                "\t> help : Show all commands.\n" +
                "\t> reload filename: Load the most recent file again.\n" +
                "\t> load filename: Load a new game board file. (1 argument)\n" +
                "\t> hint : Make the next move for me.\n" +
                "\t> show : Display the board.\n" +
                "\t> go {north|south|east|west}: Tell chosen character where to go. (1 argument)\n" +
                "\t> choose row column: Choose which character moves next. (2 arguments)\n" +
                "\t> quit");
    }

    /**
     * a method which updates the view after every press of any button. it does this
     * by receiving the current model as the parameter and the controller updates the view
     * with the data it has received from the model
     *
     * @param lunarLandingModel a model which manages data, logic, and rules
     * @param o an argument which tells the view what should change
     */
    @Override
    public void update(LunarLandingModel lunarLandingModel, Object o) {
        if(o.equals("notwin")) {
            displayBoard(lunarLandingModel.returnConfig());
        }
        if(o.equals("hintwin")) {
            displayBoard(lunarLandingModel.returnConfig());
            System.out.println(model.hintWin());
        }
        if(o.equals("hint")) {
            displayBoard(lunarLandingModel.returnConfig());
        }
        // display a win if all cards are face up (not cheating)
        if(o.equals("win")) {
            System.out.println(model.win());
        }
        if(o.equals("Illegal move")) {
            System.out.println(model.illegalMove());
        }
        if(o.equals("alreadySolved")){
            System.out.println(model.alreadySolved());
        }
        if(o.equals("show")){
            displayBoard(lunarLandingModel.returnConfig());
        }
        if(o.equals("quit")) {
            System.exit(0);
        }
        if(o.equals("help")) {
           System.out.println(model.displayHelp());
        }
        if(o.equals("newFile")) {
            System.out.println("File loaded");
        }
        if(o.equals("unsolvable")) {
            System.out.println("Unsolvable board");
        }
    }

    /*
     * code to read the file name from the command line and
     * run the solver on the puzzle
     */
    /**
     * The main method used to play a game.
     *
     * @param args Command line arguments -- unused
     */
    public static void main( String[] args ) {
        System.out.println("New file loaded.");
        LunarLandingPTUI ptui = new LunarLandingPTUI(args[0]);
        ptui.run();
    }
}