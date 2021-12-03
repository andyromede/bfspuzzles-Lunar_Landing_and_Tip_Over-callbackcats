package puzzles.lunarlanding.model;

import puzzles.lunarlanding.model.LunarLandingConfig;
import util.Observer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * DESCRIPTION
 * @author Andrew Le
 * November 2021
 */
public class LunarLandingModel {

    public static int BOARD_SIZE;
    private LunarLandingConfig currentConfig;

    private List<Observer< LunarLandingModel, Object> > observers = new LinkedList<>();


    public LunarLandingModel() {

    }
    public void load(String txtFile){

    }

    public void reload(){

    }

    public void choose(int row, int col){

    }

    public void go(String direction){

    }

    public void hint(){

    }

    public void show(){

    }

    public void help(){

    }

    public void quit(){

    }


    public void addObserver( Observer< LunarLandingModel, Object > obs ) {
        this.observers.add(obs);
    }
    private void announce( String arg){
        for ( var obs: this.observers ) {
            obs.update(this,arg);
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
