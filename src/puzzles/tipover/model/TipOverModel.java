package puzzles.tipover.model;

import puzzles.tipover.ptui.TipOverPTUI;
import util.Observer;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * DESCRIPTION
 * @author Maddan Eisenberg
 * November 2021
 */
public class TipOverModel {

    private TipOverConfig currentConfig;
    private int[][] board;
    private LinkedList<Observer<TipOverModel, Object>> observers;
    public TipOverModel(TipOverConfig config)
    {
        this.observers = new LinkedList<>();
        currentConfig = config;
        this.board = currentConfig.getBoard();
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
        currentConfig = new TipOverConfig(filename);
        board = currentConfig.getBoard();
        System.out.println("New file loaded");
        announce("show");
    }
    public void reload()
    {

    }
    public void move(String dir)
    {
        announce(currentConfig.movePlayer(dir));
    }
    public void hint()
    {

    }
    public void show()
    {

    }
    public void help()
    {
        System.out.println("Legal commands are...\n" +
                "\t> help : Show all commands.\n" +
                "\t> move {north|south|east|west}: Go in given direction, possibly tipping a tower. (1 argument)\n" +
                "\t> reload filename: Load the most recent file again.\n" +
                "\t> load {board-file-name}: Load a new game board file. (1 argument)\n" +
                "\t> hint Make the next move for me.\n" +
                "\t> show Display the board.\n" +
                "\t> quit");
    }
    public void quit()
    {

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
