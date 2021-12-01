package puzzles.lunarlanding.model;

import puzzles.clock.ClockConfig;
import solver.Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * DESCRIPTION
 * @author Andrew Le
 * November 2021
 */
public class LunarLandingConfig implements Configuration {

    //a board
    private String[][] board;
    //dimension of the board
    private static int rDim;
    private static int cDim;
    //lunar lander coordinates
    private static int rCor;
    private static int cCor;
    //cursor
    private int row;
    private int col;

    //an array list of characters
    private ArrayList<String> figureList;
    //
    int numberofCellsNeeded;
    private List<ClockConfig> successors;

    public LunarLandingConfig(String filename) {
        try (Scanner in = new Scanner(new File(filename))) {
            // creates the rows and columns
            String[] line = in.nextLine().split("\\s");
            rDim = Integer.parseInt(line[0]);
            cDim = Integer.parseInt(line[1]);
            rCor = Integer.parseInt(line[2]);
            cCor = Integer.parseInt(line[3]);
            figureList = new ArrayList<>();
            System.out.println(rDim + " " + cDim + " " + rCor + " " + cCor );
            numberofCellsNeeded = rDim * cDim;
            //creates the board and adds the explorer
            this.board = new String[rDim][cDim];
            for(int row = 0; row< rDim; row++) {
                for (int col = 0; col < cDim; col++) {
                 board[row][col] = "-";
                }
            }
            board[rCor][cCor] = "!";

            //goes through the second line and beyond and adds the figures to a list of figures
            String temp = in.nextLine();
            while(in.hasNext() && !temp.isEmpty()){
                System.out.println(temp);
                temp = (String) temp;
                figureList.add(temp);
                temp = in.nextLine();
            }
            String array[] = temp.split(" ");

            for(int i = 0; i < figureList.size(); i++){
                String fields[] = figureList.get(i).split(" ");
                int figureRow = Integer.parseInt(fields[1]);
                int figureCol = Integer.parseInt(fields[2]);
                board[figureRow][figureCol] = fields[0];
            }

        } // try-with-resources, the file is closed automatically
        catch(FileNotFoundException e){
            System.out.println("Error: Invalid File Destination");

        }
        catch(NumberFormatException e){
            System.out.println("Error: Invalid File Format");
        }
    }
    /**
     * The copy constructor takes a config, other, and makes a full "deep" copy
     * of its instance data.
     *
     * @param other the config to copy
     */
    protected LunarLandingConfig(LunarLandingConfig other) {
        //copies the other board so both this and other don't point to the same reference
        this.board = new String[rDim][cDim];
        for(int row = 0; row < rDim; row++){
            System.arraycopy(other.board[row], 0, this.board[row], 0, cDim);
        }

    }
    @Override
    public Collection<Configuration> getSuccessors() {
        //a new linked list is created which will hold its successors
        LinkedList<Configuration> successor = new LinkedList<>();

        //a for loop is created to go through the board
        for(int row = 0; row< rDim; row++){
            for(int col = 0; col < cDim; col++) {
                //checks if the cursor hits a figure, ignores "-"
                if(!this.board[row][col].equals("-")){
                    //if the cursor hits a figure then it will check for its cardinal directions to see if there is
                    //another figure in the board

                    if(this.board[row][col].equals("!")){
                        //do nothing if the cursor is equal to "!"
                    }
                    else {
                        //checks up
                        for (int i = row - 2; i > -1; i--) {
                            //checks if the cursor index ahead is not equal to - or equals to !
                            //if it does, then code will execute
                            if (!board[i][col].equals("-")) {

                                if(this.board[i][col].equals("!")){
                                    //do nothing if the cursor is equal to "!"
                                }
                                else {
                                    //a copy constructor of the config is created
                                    LunarLandingConfig other = new LunarLandingConfig(this);
                                    //saves the character as a temp variable
                                    String temp = other.board[row][col];
                                    //turns the cursor to "-" and then places the temp variable in front of
                                    //the figure
                                    other.board[row][col] = "-";
                                    //if the index in front of the figure, then they get stuck together ex:(!E)
                                    if (other.board[i + 1][col].equals("!")) {
                                        other.board[i + 1][col] = "!" + temp;
                                    } else {
                                        other.board[i + 1][col] = temp;
                                    }
                                    //the successor is then added to the list
                                    successor.add(other);
                                    break;
                                }
                            }
                        }

                        //check down
                        for (int i = row + 2; i < rDim - 1; i++) {
                            if (!board[i][col].equals("-")) {
                                if (this.board[i][col].equals("!")) {
                                    //do nothing if the cursor is equal to "!"
                                }
                                else {
                                    LunarLandingConfig other = new LunarLandingConfig(this);
                                    String temp = other.board[row][col];
                                    other.board[row][col] = "-";
                                    if (other.board[i - 1][col].equals("!")) {
                                        other.board[i - 1][col] = "!" + temp;
                                    } else {
                                        other.board[i - 1][col] = temp;
                                    }
                                    successor.add(other);
                                    break;
                                }
                            }
                        }

                        //check left
                        for (int i = col - 2; i > -1; i--) {
                            if (!board[row][i].equals("-")) {
                                if (this.board[i][col].equals("!")) {
                                    //do nothing if the cursor is equal to "!"
                                }
                                else {
                                    LunarLandingConfig other = new LunarLandingConfig(this);
                                    String temp = other.board[row][col];
                                    other.board[row][col] = "-";
                                    if (other.board[row][i + 1].equals("!")) {
                                        other.board[row][i + 1] = "!" + temp;
                                    } else {
                                        other.board[row][i + 1] = temp;
                                    }
                                    successor.add(other);
                                    break;
                                }
                            }
                        }

                        //check right
                        for (int i = col + 2; i < cDim - 1; i++) {
                            if (!board[row][i].equals("-")) {
                                if (this.board[i][col].equals("!")) {
                                    //do nothing if the cursor is equal to "!"
                                }
                                else {
                                    LunarLandingConfig other = new LunarLandingConfig(this);
                                    String temp = other.board[row][col];
                                    other.board[row][col] = "-";
                                    if (other.board[row][i - 1].equals("!")) {
                                        other.board[row][i - 1] = "!" + temp;
                                    } else {
                                        other.board[row][i - 1] = temp;
                                    }
                                    successor.add(other);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        //Successors are returned
        return successor;
    }

    /**
     *  Two LunarLandingConfigs are equal if they have the same board.
     *
     *  @param other The other object to check equality with
     *  @return true if equal; false otherwise
     */
    @Override
    public boolean equals(Object other){
        boolean result = false;
        if (other instanceof LunarLandingConfig) {
            LunarLandingConfig o = (LunarLandingConfig) other;
            //boolean check = Arrays.equals(this.board, o.board);
            //System.out.println(Arrays.deepEquals(this.board, o.board));
            result = Arrays.deepEquals(this.board, o.board);
        }
        return result;
    }

    @Override
    public boolean isGoal() {
        return board[rCor][cCor].equals("!E");
    }

    /**
     * @return a hashcode which consists of using the hashcode method on the board
     */
    @Override
    public int hashCode(){
        //System.out.println(board.hashCode());
        return Arrays.deepHashCode(board);
    }

    @Override
    public String toString() {
        String s = "";
        s += "\n";
        s += "   " ;
        for(int col = 0; col < cDim; col++){
            s += " " + col;
        }
        s += "\n";
        s += "   " ;
        for(int col = 0; col < cDim; col++){
            s += "--";
        }
        for(int row = 0; row< rDim; row++) {
            s += "\n";
            s += row + " | ";
            for (int col = 0; col < cDim; col++) {
                if(this.board[row][col] == null){
                    s += "-" + "  ";
                }
                else
                    s += this.board[row][col] + " ";
            }
        }
        return s;
    }

}
