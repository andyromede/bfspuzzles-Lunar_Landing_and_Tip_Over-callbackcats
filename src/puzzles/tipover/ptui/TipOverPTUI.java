package puzzles.tipover.ptui;

import puzzles.tipover.model.TipOverConfig;
import puzzles.tipover.model.TipOverModel;
import util.Observer;

import java.util.Scanner;

/**
 * DESCRIPTION
 * @author Maddan Eisenberg
 * November 2021
 */
public class TipOverPTUI implements Observer<TipOverModel, Object > {
    private final TipOverModel model;

    public static void main(String[] args ) {
        TipOverPTUI ptui = new TipOverPTUI(args[0]);
        ptui.run();
    }
    public TipOverPTUI(String filename)
    {
        TipOverConfig config = new TipOverConfig(filename);
        this.model = new TipOverModel(config, filename);
        initializeView();
    }
    public void run()
    {
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String line = in.nextLine();
            String[] fields = line.split("\\s+");
            try {
                switch (fields[0].toLowerCase()) {
                    case "quit" -> {
                        this.model.quit();
                        return; //Will never happen but just in case
                    }
                    case "reload" -> this.model.reload();
                    case "load" -> this.model.load(fields[1]);
                    case "move" -> this.model.move(fields[1]);
                    case "hint" -> this.model.hint();
                    case "show" -> displayBoard(model.returnConfig());
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
            case "invaliddir" -> System.out.println("Legal directions are\n[NORTH, EAST, SOUTH, WEST]");
            case "towertopple" -> {
                System.out.println("A tower has been tipped over.");
                displayBoard(tipOverModel.returnConfig());
            }
            default -> System.out.println(o);
        }
    }
    private void displayBoard(TipOverConfig board)
    {
        int[] tempLoc = board.getCurrCoords();
        int[] tempGoal = board.getGoalCoords();
        int[][] tempBoard = board.getBoard();
        StringBuilder s = new StringBuilder();
        s.append("\n");
        s.append("      ");
        for(int col = 0; col < tempBoard[0].length; col++){
            s.append(col).append("  ");
        }
        s.append("\n");
        s.append("    ");
        s.append("___".repeat(tempBoard[0].length));
        for (int i = 0; i < tempBoard.length; i++)
        {
            s.append("\n ").append(i).append(" | ");
            for (int j = 0; j < tempBoard[0].length; j++) {
                if (tempBoard[i][j] == 0)
                    s.append(" _");
                else if (tempLoc[0] == i && tempLoc[1] == j)
                    s.append("*").append(tempBoard[i][j]);
                else if (tempGoal[0] == i && tempGoal[1] == j)
                    s.append("!").append(tempBoard[i][j]);
                else
                    s.append(" ").append(tempBoard[i][j]);
                s.append(" ");
            }
        }
        System.out.println(s + "\n");
    }
}
