package puzzles.tipover.ptui;

import puzzles.tipover.model.TipOverConfig;
import puzzles.tipover.model.TipOverModel;
import util.Observer;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

/**
 * DESCRIPTION
 * @author Maddan Eisenberg
 * November 2021
 */
public class TipOverPTUI implements Observer<TipOverModel, Object > {
    private TipOverModel model;
    private TipOverConfig config;
    public static void main(String[] args ) {
        TipOverPTUI ptui = new TipOverPTUI(args[0]);
        ptui.run();
    }
    public TipOverPTUI(String filename)
    {
        config = new TipOverConfig(filename);
        this.model = new TipOverModel(config, filename);
        initializeView();
    }
    public void run()
    {
        Scanner in = new Scanner(System.in);
        for (; ; ) {
            System.out.print("> ");
            String line = in.nextLine();
            String[] fields = line.split("\\s+");
            try {
                switch (fields[0].toLowerCase()) {
                    case "quit" -> this.model.quit();
                    case "reload" -> this.model.reload();
                    case "load" -> this.model.load(fields[1]);
                    case "move" -> this.model.move(fields[1]);
                    case "hint" -> this.model.hint();
                    case "show" -> this.model.show();
                    case "help" -> this.model.help();
                    default -> {
                        System.out.println("Illegal command");
                        this.model.help();
                    }
                }
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                System.out.println("Error: you need to finish the statement.");
            }
        }
    }
    public void initializeView()
    {
        this.model.addObserver(this);
        update(this.model, "show");
    }
    @Override
    public void update(TipOverModel tipOverModel, Object o) {
        switch ((String) o) {
            case "show", "hint" -> displayBoard(tipOverModel.returnConfig());
            case "illegal" -> System.out.println("That move's illegal.");
            case "towertopple" -> {
                System.out.println("A tower has been tipped over.");
                displayBoard(tipOverModel.returnConfig());
            }
            case "invaliddir" -> System.out.println("Legal directions are\n[NORTH, EAST, SOUTH, WEST]");
            case "win" -> System.out.println("YOU WON!");
            case "alreadywon" -> System.out.println("You arlready won!");
            case "newFile" -> System.out.println("New file loaded.");
            case "hintwin" -> System.out.println("I WON!");
            case "unsolvable" -> {
                System.out.println("Unsolvable board");
                displayBoard(tipOverModel.returnConfig());
            }
        }
    }
    private void displayBoard(TipOverConfig board)
    {
        int[] tempLoc = board.getCurrCoords();
        int[] tempGoal = board.getGoalCoords();
        int[][] tempBoard = board.getBoard();
        String s = "";
        s += "\n";
        s += "      " ;
        for(int col = 0; col < tempBoard[0].length; col++){
            s += col + "  ";
        }
        s += "\n";
        s += "    " ;
        for(int col = 0; col < tempBoard[0].length; col++){
            s += "___";
        }
        for (int i = 0; i < tempBoard.length; i++)
        {
            s += "\n " + i + " | ";
            for (int j = 0; j < tempBoard[0].length; j++) {
                if (tempBoard[i][j] == 0)
                    s += " _";
                else if (tempLoc[0] == i && tempLoc[1] == j)
                    s += "*" + tempBoard[i][j];
                else if (tempGoal[0] == i && tempGoal[1] == j)
                    s += "!" + tempBoard[i][j];
                else
                    s += " " + tempBoard[i][j];
                s += " ";
            }
        }
        System.out.println(s + "\n");
    }
}
