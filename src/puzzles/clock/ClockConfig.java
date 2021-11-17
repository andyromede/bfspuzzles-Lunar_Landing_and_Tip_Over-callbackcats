package puzzles.clock;

import solver.Configuration;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


public class ClockConfig implements Configuration{

    //private variable for the hours, start, and end
    private int hours, start, end;
    //a private list of clock configurations for its successors
    private List<ClockConfig> successors;

    /**
     * a constructor of ClockConfig which stores the hours, start, end, and the ClockConfig's successors
     */
    public ClockConfig(int hours, int start, int end) {
        this.hours = hours;
        this.start = start;
        this.end = end;
        this.successors = new LinkedList<>();
    }

    /**
     * @return a true of false value if its start hour is equal to its end hour
     */
    @Override
    public boolean isGoal() {
        return this.end == this.start;
    }

    /**
     * a method where the successors of a ClockConfiguration is returned. It does this by returning a linked list
     * of successors by just changing the start hours value
     *
     * @return a list of configuration that are successors to WaterConfiguration
     */
    @Override
    public Collection<Configuration> getSuccessors() {
        //Only two configuration will be created as successors
        ClockConfig Successor1;
        ClockConfig Successor2;

        //the first successor is created
        //if the start value is equal to one, then the start value will be set the hours value
        //else the start value is just subtract by one
        //this is because the start value can't be zero (its like a clock, it loops around from 1-12)
        if (start == 1) {
            Successor1 = new ClockConfig(hours, hours, end);
        } else {
            Successor1 = new ClockConfig(hours, start - 1, end);
        }

        //the second successor is created
        //if the start value greater than the hour value, then the start value will be set to one.
        //else the start value is just add by one
        //this is because the start value can't be greater than the hour value
        //(its like a clock, it loops around from 1-12)
        if (start > hours) {
            Successor2 = new ClockConfig(hours, 1, end);
        } else {
            Successor2 = new ClockConfig(hours, start + 1, end);
        }

        //Successors 1 and 2 are then added to the linked list and is the returned
        successors.add(Successor1);
        successors.add(Successor2);
        return new LinkedList<>(successors);
    }

    /**
     * Two ClockConfigs are equal if they have the same hours, start, and end values.
     *
     * @param other The other object to check equality with
     * @return true if equal; false otherwise
     */
    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof ClockConfig) {
            ClockConfig o = (ClockConfig) other;
            result = this.hours == o.hours && this.start == o.start && this.end == o.end;
        }
        return result;
    }


    /**
     * @return a String representation of the start hour value
     */
    @Override
    public String toString() {
        return String.valueOf(start);
    }


    /**
     * @return a hashcode which consists of the start hour being returned
     */
    @Override
    public int hashCode() {
        return start;
    }

}