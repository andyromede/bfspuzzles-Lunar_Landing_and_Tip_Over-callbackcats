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

    /**
     * Constructor for the PTUI
     */
    public LunarLandingPTUI() {
        this.model = new LunarLandingModel();
        initializeView();
    }

    // CONTROLLER

    /**
     * Read a command and execute loop.
     */
    private void run() {
        Scanner in = new Scanner(System.in);
        for (; ; ) {
            System.out.print("> ");
            String line = in.nextLine();
            String[] fields = line.split("\\s+");
            if (fields.length > 0) {
                if (fields[0].contains("quit")) {
                    break;
                } else if (fields[0].contains("load")) {
                    this.model.load(fields[1]);
                } else if (fields[0].contains("reload")) {
                    this.model.reload();
                } else if (fields[0].contains("hint")) {
                    this.model.hint();
                } else if (fields[0].contains("show")) {
                    this.model.show();
                } else if (fields[0].contains("go")) {
                    this.model.go(fields[1]);
                } else if (fields[0].contains("choose")) {
                    this.model.choose(Integer.parseInt(fields[1]), Integer.parseInt(fields[2]));
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
        update(this.model, null);
    }

    private void displayBoard() {

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
        displayBoard();
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
        LunarLandingPTUI ptui = new LunarLandingPTUI();
        ptui.run();
    }
}