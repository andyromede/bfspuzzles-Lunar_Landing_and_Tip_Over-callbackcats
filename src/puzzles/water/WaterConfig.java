package puzzles.water;

import solver.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class WaterConfig {

    //the goal
    private int amountEnd;
    //an array list containing a list of the bucket's max volume
    private ArrayList<Integer> listBuckets;
    //a list of buckets with its current water level
    private ArrayList<Integer> currentBuckets;

    /**
     * a constructor of WaterConfig which stores the amount goal, a list of the buckets max volume,
     * and an array list of the current bucket water level
     */
    public WaterConfig(int amount, ArrayList<Integer> buckets, ArrayList<Integer> currentBucket ) {
        this.amountEnd = amount;
        this.listBuckets = buckets;
        this.currentBuckets = new ArrayList<>(currentBucket);
    }

    /**
     * a copy constructor of the WaterConfig
     */
    public WaterConfig(WaterConfig other){
        this.amountEnd =other.amountEnd;
        this.listBuckets = new ArrayList<>(other.listBuckets);
        this.currentBuckets = new ArrayList<>(other.currentBuckets);
    }

    /**
     * @return a true of false value if one of the buckets on the list of the bucket water
     * levels contains the goal amount
     */
    public boolean isGoal() {
        return currentBuckets.contains(amountEnd);
    }

    /**
     * a method where the successors of a WaterConfiguration is returned. It does this by returning every possible
     * combination of what could happen to the list of current buckets.
     *
     * There are three methods that create this combination of successors
     *  1. dump
     *  2. fill
     *  3. pour
     *
     * @return a list of configuration that are sucessors to WaterConfiguration
     */
    public Collection<Configuration> getSuccessors() {

        // a linked list of successors to the current configuration that will be returned
        LinkedList<Configuration> successor = new LinkedList<>();
        for(int i = 0; i < currentBuckets.size(); i++){

            //if the buckets are not empty then they are emptied
            if(currentBuckets.get(i) != 0){
                WaterConfig temp = dump(i, currentBuckets);
                successor.add(temp);
            }

            //if the buckets are emptied, then they are filled
            if(currentBuckets.get(i) == 0){
                WaterConfig temp = fill(i, currentBuckets, listBuckets);
                successor.add(temp);
            }
            // k i
            // 3 5
            //[0,5]
            //iterates through the list of buckets again and if this iteration index does not equal to the iteration
            //index above this one then one bucket will pour into another bucket
            for(int k = 0; k < currentBuckets.size(); k++){
                if(i!=k){
                    WaterConfig temp = pour(i, k, currentBuckets, listBuckets);
                    successor.add(temp);
                }
            }

        }
        //the list of successors is returned
        return successor;

    }

    /**
     * This method sets the current bucket water level to zero specified by its index
     * @return a WaterConfig with one of its current buckets set to zero specified by its index
     */
    public WaterConfig dump(int i, ArrayList<Integer> currentBuckets){
        WaterConfig other = new WaterConfig(this);
        other.currentBuckets.set(i, 0);
        return other;

    }


    /**
     * This method sets one of the current bucket's water level to its max_fill level specified by its index
     * @return a WaterConfig with one of its current buckets set to its full value specified by its index
     */
    public WaterConfig fill(int i, ArrayList<Integer> currentBuckets, ArrayList<Integer> listBuckets){
        int max_fill = listBuckets.get(i);
        WaterConfig other = new WaterConfig(this);
        other.currentBuckets.set(i,max_fill);
        return other;
    }

    /**
     * This method moves water from one bucket to another
     * @return a different instantiation of a WaterConfig in order to prevent having the same pointers
     */
    public WaterConfig pour(int i, int k, ArrayList<Integer> currentBuckets, ArrayList<Integer> listBuckets){

        //a different instantiation of a WaterConfig in order to prevent the configurations having the same pointers
        WaterConfig other = new WaterConfig(this);

        //an int variable storing the max volume of the bucket that is going to be poured onto
        int max_fill = listBuckets.get(k);

        //an int variable storing the amount of room the bucket has by subtracting the
        //bucket's max volume by the bucket's current water level
        int room = listBuckets.get(k) - currentBuckets.get(k);

        //if the amount of room of the buckets that is poured onto is less than or equal to the water level of the
        //bucket of that is going to be poured, then the bucket that is poured onto is going to be set by its
        //max volume while the current bucket that is being poured is subtracted by the room
        if(room <= currentBuckets.get(i)) {
            other.currentBuckets.set(k, max_fill);
            other.currentBuckets.set(i, currentBuckets.get(i)-room);
        }

        //else the bucket that is being poured has it's volume set to zero while the bucket that is being poured onto
        //has poured water added to its current bucket level
        else{
            other.currentBuckets.set(k, currentBuckets.get(i)+ currentBuckets.get(k));
            other.currentBuckets.set(i, 0);
        }
        return other;
    }

    /**
     * @return an ArrayList of the bucket water levels
     */
    public ArrayList<Integer> getCurrentBuckets(){
        return this.currentBuckets;
    }

    /**
     * @return a String representation of the currentBuckets
     */
    public String toString(){
        return String.valueOf(currentBuckets);
    }


    /**
     *  Two WaterConfigs are equal if they have the same current bucket water levels.
     *
     *  @param other The other object to check equality with
     *  @return true if equal; false otherwise
     */
    @Override
    public boolean equals(Object other){
        boolean result = false;
        if (other instanceof WaterConfig) {
            WaterConfig o = (WaterConfig) other;
            result = this.currentBuckets.equals(o.getCurrentBuckets());
        }
        return result;
    }

    /**
     * @return a hashcode which consists of the current bucket water levels added together which is returned
     */
    @Override
    public int hashCode(){
        int total = 0;
        for(int amount : currentBuckets) {
            total += amount;
        }
        return total;
    }


}
