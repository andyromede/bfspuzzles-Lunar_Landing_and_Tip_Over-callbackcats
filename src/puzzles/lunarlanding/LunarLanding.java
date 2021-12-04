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
public class LunarLanding
        implements Observer<LunarLandingModel, Object> {

    /**
     * The model for the view and controller.
     */
    private LunarLandingModel model;

    /**
     * Constructor for the PTUI
     */
    public LunarLanding() {
        //this.model = new LunarLandingModel();
        initializeView();
    }

    // CONTROLLER

    /**
     * Read a command and execute loop.
     */
    private void run() {
        Scanner in = new Scanner( System.in );
        for ( ; ; ) {
            System.out.print("> ");
            String line = in.nextLine();
            String[] fields = line.split( "\\s+");
            if( fields.length > 0){
                if( fields[0].contains("quit")){
                    break;
                }
                else if ( fields[0].contains("load")){
                    this.model.load(fields[1]);
                }
                else if( fields[0].contains("reload")){
                    this.model.reload();
                }
                else if( fields[0].contains("hint")){
                    this.model.hint();
                }
                else if( fields[0].contains("show")){
                    this.model.show();
                }
                else if( fields[0].contains("go")){
                    this.model.go(fields[1]);
                }
                else if( fields[0].contains("choose")){
                    this.model.choose(Integer.parseInt(fields[1]), Integer.parseInt(fields[2]));
                }
                else{
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
        this.model.addObserver( this );
        update( this.model, null );
    }

    private void displayBoard(){

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
    public static void main(String[] args ) {
        if ( args.length < 1 ) {
            System.out.println(
                    ( "Usage: text file ..." )
            );
        }
        else{
            //when the correct arguments are provided, then the else code is executed
        }
        LunarLandingConfig init = new LunarLandingConfig(args[0]);
        List<Configuration> list = Solver.solve(init);
        if(list.isEmpty()){
            System.out.println("No solution");
        }
        for(int i = 0; i < list.size(); i++) {
            System.out.println("Step " + i + ": " + list.get(i));
        }

    }

    /**
     * Run an instance of the lunar buckets puzzle.
     * args [0]: a text file
     */
    public static class LunarLanding
            implements Observer<LunarLandingModel, Object> {

        /**
         * The model for the view and controller.
         */
        private LunarLandingModel model;
        private static LunarLandingConfig config;

        /**
         * Constructor for the PTUI
         */
        public LunarLanding() {
            this.model = new LunarLandingModel();
            initializeView();
        }

        // CONTROLLER

        /**
         * Read a command and execute loop.
         */
        private void run() {
            Scanner in = new Scanner( System.in );
            for ( ; ; ) {
                System.out.print("> ");
                String line = in.nextLine();
                String[] fields = line.split( "\\s+");
                if( fields.length > 0){
                    if( fields[0].contains("quit")){
                        break;
                    }
                    else if ( fields[0].contains("load")){
                        this.model.load(fields[1]);
                    }
                    else if( fields[0].contains("reload")){
                        this.model.reload();
                    }
                    else if( fields[0].contains("hint")){
                        this.model.hint();
                    }
                    else if( fields[0].contains("show")){
                        this.model.show();
                    }
                    else if( fields[0].contains("go")){
                        this.model.go(fields[1]);
                    }
                    else if( fields[0].contains("choose")){
                        this.model.choose(Integer.parseInt(fields[1]), Integer.parseInt(fields[2]));
                    }
                    else{
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
            this.model.addObserver( this );
            update( this.model, null );
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
            config.displayBoard();
        }

        /*
         * code to read the file name from the command line and
         * run the solver on the puzzle
         */
        public static void main(String[] args ) {
            if ( args.length < 1 ) {
                System.out.println(
                        ( "Usage: text file ..." )
                );
            }
            else{
                try (Scanner in = new Scanner(new File(args[0]))) {
                    // creates the rows and columns
                    String[] line = in.nextLine().split("\\s");
                    int rDim = Integer.parseInt(line[0]);
                    int cDim = Integer.parseInt(line[1]);
                    int rCor = Integer.parseInt(line[2]);
                    int cCor = Integer.parseInt(line[3]);
                    ArrayList<String> figureList = new ArrayList<>();
                    System.out.println(rDim + " " + cDim + " " + rCor + " " + cCor );
                    //goes through the second line and beyond and adds the figures to a list of figures
                    String temp = in.nextLine();
                    while(in.hasNext() && !temp.isEmpty()){
                        figureList.add(temp);
                        temp = in.nextLine();
                    }
                    config = new LunarLandingConfig(rDim, cDim, rCor, cCor, figureList);
                    List<Configuration> list = Solver.solve(config);
                    if(list.isEmpty()){
                        System.out.println("No solution");
                    }
                    for(int i = 0; i < list.size(); i++) {
                        System.out.println("Step " + i + ": " + list.get(i));
                    }
                } // try-with-resources, the file is closed automatically
                catch(FileNotFoundException e){
                    System.out.println("Error: Invalid File Destination");

                }
                catch(NumberFormatException e){
                    System.out.println("Error: Invalid File Format");
                }
            }
        }
    }
}
