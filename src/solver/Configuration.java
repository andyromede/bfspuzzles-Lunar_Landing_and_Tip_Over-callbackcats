package solver;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Configuration abstraction for the solver algorithm
 *
 * @author Andrew Le
 */
public interface Configuration {


    /*
     * List here the methods that the configurations of all the
     * puzzles must implement.
     * The project writeup explains that there are other acceptable designs,
     * so use of this interface is not required. However, for full design
     * credit, use of a shared solver that requires the implementation of
     * a certain abstraction from all puzzles is required.
     */

    /**
     * @return a true of false statement if a goal has been met
     */
    public boolean isGoal();

    /**
     * @return all successors connected to the configuration
     */
    public Collection<Configuration> getSuccessors();
}