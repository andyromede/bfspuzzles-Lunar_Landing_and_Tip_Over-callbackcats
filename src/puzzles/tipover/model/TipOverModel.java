package puzzles.tipover.model;

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

    }
    public void reload()
    {

    }
    public void move(String dir)
    {

    }
    public void hint()
    {

    }
    public void show()
    {

    }
    public void help()
    {

    }
    public void quit()
    {

    }
    public void addObserver( Observer<TipOverModel, Object> obs )
    {
        this.observers.add(obs);
    }
    private void announce(String arg)
    {
        for (var obs: this.observers)
            obs.update(this, arg);
    }
}
