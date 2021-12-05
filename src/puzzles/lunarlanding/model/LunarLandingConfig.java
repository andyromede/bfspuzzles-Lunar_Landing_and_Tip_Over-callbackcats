package puzzles.lunarlanding.model;

import puzzles.lunarlanding.ptui.LunarLandingPTUI;
import solver.Configuration;
import solver.Solver;

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
    public LunarLandingConfig(String filename)
    {
        try (Scanner in = new Scanner(new File(filename))) {
            // creates the rows and columns
            String[] line = in.nextLine().split("\\s");
            rDim = Integer.parseInt(line[0]);
            cDim = Integer.parseInt(line[1]);
            rCor = Integer.parseInt(line[2]);
            cCor = Integer.parseInt(line[3]);
            ArrayList<String> figureList = new ArrayList<>();
            //goes through the second line and beyond and adds the figures to a list of figures
            String temp = in.nextLine();
            while(in.hasNext() && !temp.isEmpty()) {
                figureList.add(temp);
                temp = in.nextLine();
            }
            board = new String[rDim][cDim];
            for(int row = 0; row< rDim; row++) {
                for (int col = 0; col < cDim; col++) {
                    board[row][col] = "-";
                }
            }
            board[rCor][cCor] = "!";
            for (String s : figureList) {
                String[] fields = s.split(" ");
                int figureRow = Integer.parseInt(fields[1]);
                int figureCol = Integer.parseInt(fields[2]);
                if (board[figureRow][figureCol].equals("!")) {
                    board[figureRow][figureCol] = "!" + fields[0];
                } else {
                    board[figureRow][figureCol] = fields[0];
                }
            }
            //LunarLandingPTUI ptui = new LunarLandingPTUI();
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

    public LunarLandingConfig updateConfig(String[][] otherBoard){
        for(int row = 0; row< rDim; row++) {
            for (int col = 0; col < cDim; col++) {
                this.board[row][col] = otherBoard[row][col];
            }
        }
        return this;
    }
    public String[][] returnBoard(){
        return board;
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

                    //checks if the cursor contains an "!"
                    if(this.board[row][col].equals("!")){
                        //do nothing if the cursor is equal to "!"
                    }
                    else {
                        //checks up
                        for (int i = row - 1; i > -1; i--) {
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
                                    //if the length of the cursor is 2, then it will take a substring on the cursor with the first index
                                    //else, it will just take the letter
                                    String temp = "";
                                    //has a "!"
                                    if(other.board[row][col].length() == 2){
                                        temp = other.board[row][col].substring(1);
                                        other.board[row][col] = "!";
                                        if (other.board[i + 1][col].equals("!")) {
                                            other.board[i + 1][col] = "!" + temp;
                                        } else {
                                            other.board[i + 1][col] = temp;
                                        }
                                    }
                                    //just the letter
                                    else {
                                        temp = other.board[row][col];
                                        //turns the cursor to "-" and then places the temp variable in front of
                                        //the figure
                                        other.board[row][col] = "-";
                                        //if the index in front of the figure has a lunar lander ("!"), then they get stuck together ex:(!E)
                                        if (other.board[i + 1][col].equals("!")) {
                                            other.board[i + 1][col] = "!" + temp;
                                        } else {
                                            other.board[i + 1][col] = temp;
                                        }

                                    }
                                    //the successor is then added to the list
                                    successor.add(other);
                                    break;
                                }
                            }
                        }

                        //check down
                        for (int i = row + 1; i < rDim; i++) {
                            if (!board[i][col].equals("-")) {
                                if(this.board[i][col].equals("!")){
                                    //do nothing if the cursor is equal to "!"
//
                                }
                                else {
                                    //a copy constructor of the config is created
                                    LunarLandingConfig other = new LunarLandingConfig(this);
                                    //saves the character as a temp variable
                                    //if the length of the cursor is 2, then it will take a substring on the cursor with the first index
                                    //else, it will just take the letter
                                    String temp = "";
                                    //if original location has a letter and a "!"
                                    if(other.board[row][col].length() == 2){
                                        temp = other.board[row][col].substring(1);
                                        other.board[row][col] = "!";
                                        if (other.board[i - 1][col].equals("!")) {
                                            other.board[i - 1][col] = "!" + temp;
                                        } else {
                                            other.board[i - 1][col] = temp;
                                        }
                                    }
                                    //just the letter
                                    else {
                                        temp = other.board[row][col];
                                        //turns the cursor to "-" and then places the temp variable in front of
                                        //the figure
                                        other.board[row][col] = "-";
                                        //if the index in front of the figure has a lunar lander ("!"), then they get stuck together ex:(!E)
                                        if (other.board[i - 1][col].equals("!")) {
                                            other.board[i - 1][col] = "!" + temp;
                                        } else {
                                            other.board[i - 1][col] = temp;
                                        }

                                    }
                                    //the successor is then added to the list
                                    successor.add(other);
                                    break;
                                }
                            }
                        }

                        //check left
                        for (int i = col - 1; i > -1; i--) {
                            if (!board[row][i].equals("-")) {
                                if (this.board[row][i].equals("!")) {
                                    //do nothing if the cursor is equal to "!"
                                }
                                else {
                                    //a copy constructor of the config is created
                                    LunarLandingConfig other = new LunarLandingConfig(this);
                                    //saves the character as a temp variable
                                    //if the length of the original location is 2, then it will take a substring on the cursor with the first index
                                    //else, it will just take the letter
                                    String temp = "";
                                    //has a "!"
                                    if (other.board[row][col].length() == 2) {
                                        temp = other.board[row][col].substring(1);
                                        other.board[row][col] = "!";
                                        if (other.board[row][i + 1].equals("!")) {
                                            other.board[row][i + 1] = "!" + temp;
                                        } else {
                                            other.board[row][i + 1] = temp;
                                        }
                                    }
                                    //just the letter
                                    else {
                                        temp = other.board[row][col];
                                        //turns the cursor to "-" and then places the temp variable in front of
                                        //the figure
                                        other.board[row][col] = "-";
                                        //if the index in front of the figure has a lunar lander ("!"), then they get stuck together ex:(!E)
                                        if (other.board[row][i + 1].equals("!")) {
                                            other.board[row][i + 1] = "!" + temp;
                                        } else {
                                            other.board[row][i + 1] = temp;
                                        }

                                    }
                                    //the successor is then added to the list
                                    successor.add(other);
                                    break;
                                }
                            }
                        }

                        //check right
                        for (int i = col + 1; i < cDim; i++) {
                            if (!board[row][i].equals("-")) {
                                if (this.board[row][i].equals("!")) {
                                    //do nothing if the cursor is equal to "!"
                                }
                                else {
                                    //a copy constructor of the config is created
                                    LunarLandingConfig other = new LunarLandingConfig(this);
                                    //saves the character as a temp variable
                                    //if the length of the cursor is 2, then it will take a substring on the cursor with the first index
                                    //else, it will just take the letter
                                    String temp = "";
                                    //has a "!"
                                    if(other.board[row][col].length() == 2){
                                        temp = other.board[row][col].substring(1);
                                        other.board[row][col] = "!";
                                        if (other.board[row][i-1].equals("!")) {
                                            other.board[row][i-1] = "!" + temp;
                                        } else {
                                            other.board[row][i-1] = temp;
                                        }
                                    }
                                    //just the letter
                                    else {
                                        temp = other.board[row][col];
                                        //turns the cursor to "-" and then places the temp variable in front of
                                        //the figure
                                        other.board[row][col] = "-";
                                        //if the index in front of the figure has a lunar lander ("!"), then they get stuck together ex:(!E)
                                        if (other.board[row][i-1].equals("!")) {
                                            other.board[row][i-1] = "!" + temp;
                                        } else {
                                            other.board[row][i-1] = temp;
                                        }

                                    }
                                    //the successor is then added to the list
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
        boolean result = true;
        if (other instanceof LunarLandingConfig) {
            LunarLandingConfig o = (LunarLandingConfig) other;
            for(int row = 0; row< rDim; row++) {
                for (int col = 0; col < cDim; col++) {
                    if (!this.board[row][col].equals(o.board[row][col])) {
                        result = false;
                        break;
                    }
                }
            }
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
        s += "  " ;
        for(int col = 0; col < cDim; col++){
            s += "  " + col;
        }
        s += "\n";
        s += "   " ;
        for(int col = 0; col < cDim; col++){
            s += "---";
        }
        for(int row = 0; row< rDim; row++) {
            s += "\n";
            s += row + " | ";
            for (int col = 0; col < cDim; col++) {
                if(board[row][col] == null){
                    s += "-" + "   ";
                }
                else
                    s += board[row][col] + "  ";
            }
        }
        return s;
    }
}