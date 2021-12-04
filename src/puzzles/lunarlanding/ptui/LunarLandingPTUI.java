package puzzles.lunarlanding.ptui;

import puzzles.lunarlanding.model.LunarLandingConfig;

/**
 * DESCRIPTION
 * @author YOUR NAME HERE
 * November 2021
 */
public class LunarLandingPTUI {
    private String[][] board;
    public LunarLandingPTUI(LunarLandingConfig config) { this.board = config.getBoard(); }
    /*
     * Here you need to add a class that updates the GUI based on the board you have here.
     * Should be as easy as just taking each board coordinate and updating its corresponding
     * square in the GUI, probably two for loops and a little more code.
     */
}
