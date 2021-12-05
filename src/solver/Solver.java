package solver;

import java.util.*;

/**
 * This class contains a universal algorithm to find a path from a starting
 * configuration to a solution, if one exists
 *
 * @author Andrew Le
 */
public class Solver {

    // queue: to order the node visitation
    //List<Configuration> queue = new LinkedList<>();
    // predecessor map: shows the shortest path
    //Map<Configuration,Configuration> predecessors = new HashMap<>();
    //get 12 2 11
    /**
     * This method uses a search technique called Breadth first search which guarantees the shortest path
     * to be returned from the start configuration to the end configuration
     *
     * Breadth First Search(BFS) uses 3 data structures
     *
     * - a queue to order the visitation of nodes
     * - a predecessor map which is a list of configurations showing the shortest path
     * - a graph of configurations (a bit confusing because you're actually making one instead of getting a pre-made one
     *                              from a text file)
     *
     * @param config a configuration that will be used the starting configuration
     * @return a list of configurations representing the shortest path
     */
    public static List<Configuration> solve(solver.Configuration config){
        //totalCounter is an int representing the total amount of predecessors possible
        //uniqueCounter represents the amount of configurations visited
        int totalCounter = 0;
        int uniqueCounter = 0;

        //a queue is created
        List<Configuration> queue = new LinkedList<>();
        // a predecessor map is created
        Map<solver.Configuration, solver.Configuration> predecessors = new HashMap<>();
        //the current node that is being visited and checked
        solver.Configuration current = null;
        //the first configuration is added to the queue and predecessor map
        queue.add(config);
        predecessors.put(config, null);
        uniqueCounter += 1;
        //System.out.println(config);
        totalCounter += 1;

        //while the queue is not empty, the front of the queue will become the current configuration that will
        //be checked for the end configuration or it's predecessors
        while(!queue.isEmpty()){
            current = queue.remove(0);
           // System.out.println(current);

            //if the current configuration is the target, then the method will break
            if(current.isGoal()){
                break;
            }

            //else, a new configuration will be taken from the queue until the target has been found
            for(Configuration nbr : current.getSuccessors()){
                totalCounter += 1;
                if(!predecessors.containsKey(nbr)){
                    predecessors.put(nbr, current);
                    //System.out.println(current);
                    uniqueCounter += 1;
                    queue.add(nbr);
                    //System.out.println("--------------------------------------");
                }

            }
        }

        //prints out the unique and total counter
        //System.out.println("Unique Counter: " + uniqueCounter);
        //System.out.println("Total Counter: " + totalCounter);

        //After the target has been found, the path is constructed and then returned
        return constructPath(predecessors, config, current);

    }

    /**
     * A helper method that returns a path representing the shortest path from
     * the start configuration to the end configuration by using the predecessor map
     *
     * @param startConfig a configuration that is the starting point of the path
     * @param finishConfig  a configuration that is the ending point of the path
     * @param predecessors  a predecessor map which shows a record all visitations from the search
     * @return a list of configurations representing the shortest path
     */
    public static List<Configuration> constructPath(Map<Configuration,Configuration> predecessors,
                                                    Configuration startConfig, Configuration finishConfig){
        //a list of configurations called path is created
        List<Configuration> path = new LinkedList<>();

        //uses the predecessor map to back track the configurations from finish to start
        if(finishConfig != null && finishConfig.isGoal()){
            Configuration current = finishConfig;
            while(current != startConfig){
                path.add(0,current);
                current = predecessors.get(current);
            }
            path.add(0, current);
        }

        //path is returned
        return path;
    }
}