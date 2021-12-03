package puzzles.lunarlanding.ptui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * DESCRIPTION
 * @author YOUR NAME HERE
 * November 2021
 */
public class LunarLandingPTUI {
    private static String[][] board;
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
    private static ArrayList<String> figureList;
    //
    public static void main(String[] args) {
        try (
                Scanner in = new Scanner(new File(filename))) {
            // creates the rows and columns
            String[] line = in.nextLine().split("\\s");
            rDim = Integer.parseInt(line[0]);
            cDim = Integer.parseInt(line[1]);
            rCor = Integer.parseInt(line[2]);
            cCor = Integer.parseInt(line[3]);
            figureList = new ArrayList<>();
            System.out.println(rDim + " " + cDim + " " + rCor + " " + cCor);
            //creates the board and adds the explorer
            this.board = new String[rDim][cDim];
            for (int row = 0; row < rDim; row++) {
                for (int col = 0; col < cDim; col++) {
                    board[row][col] = "-";
                }
            }
            board[rCor][cCor] = "!";

            //goes through the second line and beyond and adds the figures to a list of figures
            String temp = in.nextLine();
            while (in.hasNext() && !temp.isEmpty()) {
                System.out.println(temp);
                temp = (String) temp;
                figureList.add(temp);
                temp = in.nextLine();
            }
            String array[] = temp.split(" ");

            for (int i = 0; i < figureList.size(); i++) {
                String fields[] = figureList.get(i).split(" ");
                int figureRow = Integer.parseInt(fields[1]);
                int figureCol = Integer.parseInt(fields[2]);
                if (board[figureRow][figureCol].equals("!")) {
                    board[figureRow][figureCol] = "!" + fields[0];
                } else {
                    board[figureRow][figureCol] = fields[0];
                }
            }
        } // try-with-resources, the file is closed automatically
        catch (
                FileNotFoundException e) {
            System.out.println("Error: Invalid File Destination");

        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid File Format");
        }
        public void updateBoard (String[][]board)
        {
            this.board = board;
        }
    }
    @Override
    public String toString() {
        String s = "";
        s += "\n";
        s += "   " ;
        for(int col = 0; col < board[0].length; col++){
            s += " " + col;
        }
        s += "\n";
        s += "   " ;
        for(int col = 0; col < board[0].length; col++){
            s += "--";
        }
        for(int row = 0; row< board.length; row++) {
            s += "\n";
            s += row + " | ";
            for (int col = 0; col < board[0].length; col++) {
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
