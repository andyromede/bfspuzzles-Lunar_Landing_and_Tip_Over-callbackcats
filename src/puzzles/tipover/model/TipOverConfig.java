package puzzles.tipover.model;

import puzzles.lunarlanding.model.LunarLandingConfig;
import puzzles.tipover.TipOver;
import solver.Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * DESCRIPTION
 * @author Maddan Eisenberg
 * November 2021
 */
public class TipOverConfig implements Configuration {
    private int[][] board;
    private int[] currCoords;
    private int[] goalCoords;
    private int[] lastVisited;
    public TipOverConfig(String filename)
    {
        try (Scanner in = new Scanner(new File(filename))) {
            String[] line = in.nextLine().split("\\s");
            int rows = Integer.parseInt(line[0]);
            int cols = Integer.parseInt(line[1]);
            currCoords = new int[2];
            currCoords[0] = Integer.parseInt(line[2]);
            currCoords[1] = Integer.parseInt(line[3]);
            goalCoords = new int[2];
            goalCoords[0] = Integer.parseInt(line[4]);
            goalCoords[1] = Integer.parseInt(line[5]);
            board = new int[rows][cols];
            for (int i = 0; i < rows; i++)
            {
                line = in.nextLine().split("\\s");
                for (int j = 0; j < cols; j++) {
                    board[i][j] = Integer.parseInt(line[j]);
                }
            }
            lastVisited = new int[2];
        }
        catch(FileNotFoundException e){
            System.out.println("Error: Invalid File Destination");
        }
        catch(NumberFormatException e){
            System.out.println("Error: Invalid File Format");
        }
    }
    protected TipOverConfig(TipOverConfig other)
    {
        this.board = new int[other.board.length][other.board[0].length];
        this.goalCoords = new int[2];
        this.currCoords = new int[2];
        this.lastVisited = new int[2];
        this.goalCoords[0] = other.goalCoords[0];
        this.goalCoords[1] = other.goalCoords[1];
        this.currCoords[0] = other.currCoords[0];
        this.currCoords[1] = other.currCoords[1];
        this.lastVisited[0] = other.lastVisited[0];
        this.lastVisited[1] = other.lastVisited[1];
        for (int row = 0; row < board.length; row++)
            System.arraycopy(other.board[row], 0, this.board[row], 0, board[0].length);
    }
    public boolean equals(Object other) {
        if (other instanceof TipOverConfig o)
            for (int i = 0; i < board.length; i++)
                for (int j = 0; j < board[0].length; j++)
                    if (this.board[i][j] != o.board[i][j] || this.currCoords != o.currCoords || this.goalCoords != o.goalCoords)
                        return false;
        return true;
    }
    @Override
    public boolean isGoal() {
        return Arrays.equals(currCoords, goalCoords);
    }
    @Override
    public Collection<Configuration> getSuccessors() {
        LinkedList<Configuration> successor = new LinkedList<>();
        //up
        if (currCoords[0] != 0) {
            boolean run = currCoords[0] - 1 != lastVisited[0] || currCoords[1] != lastVisited[1];
            if (run) {
                lastVisited = currCoords;
                if (board[currCoords[0] - 1][currCoords[1]] != 0) {
                    TipOverConfig other = new TipOverConfig(this);
                    other.currCoords[0] -= 1;
                    successor.add(other);
                }
                if (board[currCoords[0]][currCoords[1]] != 1) {
                    boolean possible = true;
                    for (int i = 1; i <= board[currCoords[0]][currCoords[1]]; i++) {
                        if (currCoords[0] - i < 0 || board[currCoords[0] - i][currCoords[1]] != 0) {
                            possible = false;
                            break;
                        }
                    }
                    if (possible) {
                        TipOverConfig other = new TipOverConfig(this);
                        for (int i = 1; i <= board[currCoords[0]][currCoords[1]]; i++)
                            other.board[currCoords[0] - i][currCoords[1]] = 1;
                        other.board[currCoords[0]][currCoords[1]] = 0;
                        other.currCoords[0] -= 1;
                        successor.add(other);
                    }
                }
            }
        }
        //down
        if (currCoords[0] != board.length - 1) {
            boolean run = currCoords[0] + 1 != lastVisited[0] || currCoords[1] != lastVisited[1];
            if (run) {
                lastVisited = currCoords;
                if (board[currCoords[0] + 1][currCoords[1]] != 0) {
                    TipOverConfig other = new TipOverConfig(this);
                    other.currCoords[0] += 1;
                    successor.add(other);
                }
                if (board[currCoords[0]][currCoords[1]] != 1) {
                    boolean possible = true;
                    for (int i = 1; i <= board[currCoords[0]][currCoords[1]]; i++) {
                        if (currCoords[0] + i >= board.length || board[currCoords[0] + i][currCoords[1]] != 0) {
                            possible = false;
                            break;
                        }
                    }
                    if (possible) {
                        TipOverConfig other = new TipOverConfig(this);
                        for (int i = 1; i <= board[currCoords[0]][currCoords[1]]; i++)
                            other.board[currCoords[0] + i][currCoords[1]] = 1;
                        other.board[currCoords[0]][currCoords[1]] = 0;
                        other.currCoords[0] += 1;
                        successor.add(other);
                    }
                }
            }
        }
        //left
        if (currCoords[1] != 0) {
            boolean run = currCoords[0] - 1 != lastVisited[0] || currCoords[1] != lastVisited[1];
            if (run) {
                lastVisited = currCoords;
                if (board[currCoords[0]][currCoords[1] - 1] != 0) {
                    TipOverConfig other = new TipOverConfig(this);
                    other.currCoords[1] -= 1;
                    successor.add(other);
                }
                if (board[currCoords[0]][currCoords[1]] != 1) {
                    boolean possible = true;
                    for (int i = 1; i <= board[currCoords[0]][currCoords[1]]; i++) {
                        if (currCoords[1] - i < 0 || board[currCoords[0]][currCoords[1] - i] != 0) {
                            possible = false;
                            break;
                        }
                    }
                    if (possible) {
                        TipOverConfig other = new TipOverConfig(this);
                        for (int i = 1; i <= board[currCoords[0]][currCoords[1]]; i++)
                            other.board[currCoords[0]][currCoords[1] - i] = 1;
                        other.board[currCoords[0]][currCoords[1]] = 0;
                        other.currCoords[1] -= 1;
                        successor.add(other);
                    }
                }
            }
        }
        //right
        if (currCoords[1] != board[0].length - 1) {
            boolean run = currCoords[0] - 1 != lastVisited[0] || currCoords[1] != lastVisited[1];
            if (run) {
                lastVisited = currCoords;
                if (board[currCoords[0]][currCoords[1] + 1] != 0) {
                    TipOverConfig other = new TipOverConfig(this);
                    other.currCoords[1] += 1;
                    successor.add(other);
                }
                if (board[currCoords[0]][currCoords[1]] != 1) {
                    boolean possible = true;
                    for (int i = 1; i <= board[currCoords[0]][currCoords[1]]; i++) {
                        if (currCoords[1] + i >= board[0].length || board[currCoords[0]][currCoords[1] + i] != 0) {
                            possible = false;
                            break;
                        }
                    }
                    if (possible) {
                        TipOverConfig other = new TipOverConfig(this);
                        for (int i = 1; i <= board[currCoords[0]][currCoords[1]]; i++)
                            other.board[currCoords[0]][currCoords[1] + i] = 1;
                        other.board[currCoords[0]][currCoords[1]] = 0;
                        other.currCoords[1] += 1;
                        successor.add(other);
                    }
                }
            }
        }
        return successor;
    }
    public int[][] getBoard()
    {
        return board;
    }
    @Override
    public int hashCode() { return Arrays.deepHashCode(board); }
    @Override
    public String toString()
    {
        String out = "";
        for (int[] a : board) {
            out += "\n";
            for (int i : a)
                out += i + " ";
        }
        return out;
    }
}
