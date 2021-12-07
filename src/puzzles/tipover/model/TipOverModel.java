package puzzles.tipover.model;

import solver.Configuration;
import solver.Solver;
import util.Observer;

import java.util.LinkedList;
import java.util.List;

/**
 * DESCRIPTION
 * @author Maddan Eisenberg
 * November 2021
 */
public class TipOverModel {

    private TipOverConfig currentConfig;
    private final LinkedList<Observer<TipOverModel, Object>> observers;
    private String filename;
    public TipOverModel(TipOverConfig config, String filename)
    {
        this.observers = new LinkedList<>();
        currentConfig = config;
        this.filename = filename;
    }

    /*
     * Code here includes...
     * Additional data variables for anything needed beyond what is in
     *   the config object to describe the current state of the puzzle
     * Methods to support the controller part of the GUI, e.g., load, move
     * Methods and data to support the "subject" side of the Observer pattern
     *
     * WARNING: To support the hint command, you will likely have to do
     *   a cast of Config to TipOverConfig somewhere, since the solve
     *   method works with, and returns, objects of type Configuration.
     */
    public void load(String filename)
    {
        TipOverConfig tempConfig = new TipOverConfig(filename);
        if (tempConfig.getBoard() != null) {
            currentConfig = tempConfig;
            this.filename = filename;
            System.out.println("New file loaded");
            announce("show");
            if (currentConfig.isGoal())
                announce("win");
        }
    }
    public void reload()
    {
        load(filename);
    }
    public void move(String dir)
    {
        announce(currentConfig.movePlayer(dir));
    }
    public void hint()
    {
        List<Configuration> list = Solver.solve(currentConfig);
        if(list.size() == 0){
            announce("unsolvable");
        }
        else if(list.size() == 1){
            announce("alreadywon");
        }
        else {
            currentConfig = (TipOverConfig) list.get(1);
            if (currentConfig.isGoal()) {
                announce("hintwin");
            } else {
                announce("hint");
            }
        }
    }
    public void show()
    {
        announce("show");
    }
    public void help()
    {
        System.out.println("""
                Legal commands are...
                \t> help : Show all commands.
                \t> move {north|south|east|west}: Go in given direction, possibly tipping a tower. (1 argument)
                \t> reload filename: Load the most recent file again.
                \t> load {board-file-name}: Load a new game board file. (1 argument)
                \t> hint Make the next move for me.
                \t> show Display the board.
                \t> quit""");
    }
    public void quit()
    {
        System.exit(0);
    }
    public void addObserver( Observer<TipOverModel, Object> obs )
    {
        this.observers.add(obs);
    }
    public TipOverConfig returnConfig()
    {
        return currentConfig;
    }
    private void announce(String arg)
    {
        for (var obs: this.observers)
            obs.update(this, arg);
    }
}
