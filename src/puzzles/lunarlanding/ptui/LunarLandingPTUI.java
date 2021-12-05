package puzzles.lunarlanding.ptui;

import puzzles.lunarlanding.model.LunarLandingConfig;
import puzzles.lunarlanding.model.LunarLandingModel;
import solver.Configuration;
import solver.Solver;
import util.Observer;

import java.util.List;
import java.util.Scanner;

/**
 * DESCRIPTION
 * @author YOUR NAME HERE
 * November 2021
 */
public class LunarLandingPTUI
        implements Observer<LunarLandingModel, Object> {

    /**
     * The model for the view and controller.
     */
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
                    this.model.show();

                } else if (fields[0].contains("choose")) {
                    String[][] board = model.returnBoard();
                    if(board[Integer.parseInt(fields[1])][Integer.parseInt(fields[2])].equals("-")||board[Integer.parseInt(fields[1])][Integer.parseInt(fields[2])].equals("!")) {
                        System.out.println("No figure at that position");
                    }
                    else{
                        System.out.println("good");
                        chosen = true;
                        figureRowCoor = Integer.parseInt(fields[1]);
                        figureColCoor = Integer.parseInt(fields[2]);
                    }
                } else if (fields[0].equals("go")) {
                    if(chosen == false){
                        System.out.println("Choose a character to move first");
                    }
                    else {
                        if (fields[1].equals("north") || fields[1].equals("south") ||
                                fields[1].equals("east") || fields[1].equals("west")) {
                            System.out.println("nice");
                            chosen = false;
                            System.out.print(figureRowCoor + "" + figureColCoor);
                            this.model.go(fields[1], figureRowCoor, figureColCoor);
                        } else
                            System.out.print("Directions are\n" +
                                    "[NORTH, EAST, SOUTH, WEST]");
                    }

                } else {
                    displayHelp();
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

    @Override
    public void update(LunarLandingModel lunarLandingModel, Object o) {
        if(o.equals("notwin")) {
            displayBoard(lunarLandingModel.returnConfig());
        }
        if(o.equals("hintwin")) {
            displayBoard(lunarLandingModel.returnConfig());
            System.out.println( "I WIN!" );
        }
        if(o.equals("hint")) {
            displayBoard(lunarLandingModel.returnConfig());
        }
        // display a win if all cards are face up (not cheating)
        if(o.equals("win")) {
            System.out.println( "YOU WIN!" );
        }
        if(o.equals("Illegal move")) {
            System.out.println( "Illegal move" );
        }
        if(o.equals("already solved")){
            System.out.println("Current board is already solved");
        }
        if(o.equals("show")){
            displayBoard(lunarLandingModel.returnConfig());
        }
        if(o.equals("quit")) {
            System.exit(0);
        }
        if(o.equals("help")) {
            displayHelp();
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