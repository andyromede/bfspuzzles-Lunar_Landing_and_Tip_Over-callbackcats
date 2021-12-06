package puzzles.lunarlanding.gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import puzzles.lunarlanding.model.LunarLandingConfig;
import puzzles.lunarlanding.model.LunarLandingModel;
import puzzles.lunarlanding.ptui.LunarLandingPTUI;
import util.Observer;

import java.io.File;
import java.util.ArrayList;

/**
 * DESCRIPTION
 * @author Andrew Le
 * November 2021
 */
public class LunarLandingGUI extends Application
        implements Observer< LunarLandingModel, Object > {

    //the config
    private LunarLandingConfig config = new LunarLandingConfig("data/lunarlanding/lula-5.txt");
    //the model
    private LunarLandingModel model = new LunarLandingModel(config, "data/lunarlanding/lula-5.txt");
    //a button list for Figure button
    ArrayList<FigureButton> buttonList = new ArrayList();


    //an instantiation of the top label text, so it can be used throughout the entre class
    private String topPrompt = "Select the first card";
    Label top = new Label(topPrompt);

    /** explorer image*/
    private final Image explorer = new Image(getClass().getResourceAsStream("resources/explorer.png"));

    /** robot-blue image*/
    private final Image robotBlue = new Image(getClass().getResourceAsStream("resources/robot-blue.png"));

    Button north = new Button("North");
    Button south = new Button("South");
    Button east = new Button("East");
    Button west = new Button("West");
    /**
     * A helper method that creates a GridPane for the
     * PokemonButtons and returns it for organization purposes
     *
     * @return a grid pane
     */
    private GridPane createGridPane(){
        GridPane gridPane = new GridPane();
        String[][] board = model.returnBoard();
        int rDim = board.length;
        int cDim = board[0].length;
        for (int row = 0; row < rDim; ++row) {
            for (int col = 0; col < cDim; ++col) {
                //figure button is created with info regarding its contents and coordinates
                FigureButton button = new FigureButton(board[row][col], row, col);
                //sets the button min size
                button.setMinSize(100,100);
                button.setMinSize(100,100);
                //button is then added to grid pane
                gridPane.add(button, col, row);
                //sets the background colors
                if(!board[row][col].equals("!")){
                    button.setStyle("-fx-background-color: LightGrey");
                }
                else{
                    button.setStyle("-fx-background-color: Tomato");
                }
                //sets the images
                if(!board[row][col].equals("-")){
                    if(board[row][col].contains("!")){

                    }
                    else if(board[row][col].contains("E")){
                        button.setGraphic(new ImageView(explorer));
                    }
                    else
                        button.setGraphic(new ImageView(robotBlue));
                }
                //removes lettering from the button
                button.setText("");
                buttonList.add(button);
                //when button is pressed, this happens
                button.setOnAction((event) -> {
                    boolean figureInPosition;
                    int pressedRow = button.getRow();
                    int pressedCol = button.getCol();
                    String data = button.getData();
                    figureInPosition = model.choose(data, pressedRow, pressedCol);
                    while(figureInPosition) {
                        directionButton(data, pressedRow, pressedCol);
                        figureInPosition = false;
                    }
                });
            }
        }
        //gridPane is returned
        return gridPane;
    }

    @Override
    public void init() throws Exception{
        System.out.println("init: initialize and connect to model!");
        model.addObserver(this);
//        }
    }

    private class FigureButton extends Button{

        //the position of the button
        private int row;
        private int col;
        private String data;
        private Image figure;

        /**
         * A constructor for the figure button.
         *
         * @param data the contents of the button(could contain a figure, robot or be empty)
         * @param row the row position of the button
         * @param col the col position of the button
         */
        public FigureButton(String data, int row, int col) {
            this.data = data;
            this.row = row;
            this.col = col;
            if(!data.equals("-")){
                if(data.equals("!")){

                }
                else if(data.contains("E")){
                    figure = explorer;
                }
                else{
                    figure = robotBlue;
                }
            }

        }
        public FigureButton returnButton(int row, int col){
            return this;
        }
        //returns the row
        public int getRow(){
            return row;
        }
        //returns the col
        public int getCol(){
            return col;
        }
        //returns the data
        public String getData(){
            return data;
        }
        //replaces the old data with new information
        public void setData(String newData){
            data = newData;
        }
    }
    @Override
    public void start( Stage stage ) {
//        stage.setTitle( "Lunar Landing" );
//        Image spaceship = new Image(
//                //"resources" + File.separator + "lander.png"
//                LunarLandingGUI.class.getResourceAsStream(
//                        "resources/lander.png"
//                )
//        );
//        Button temp = new Button();
//        temp.setGraphic( new ImageView( spaceship ) );
//        Scene scene = new Scene( temp, 640, 480 );
//        stage.setScene( scene );
//        stage.show();

        //the main border pane is created
        BorderPane borderPane = new BorderPane();

        //a grid pane is created
        GridPane gridPane = createGridPane();
        borderPane.setCenter(gridPane);

        //top label is set to the top of the border
        borderPane.setTop(top);
        BorderPane.setAlignment(top, Pos.TOP_CENTER);

        //the hbox contains load, reload, hint, and the directional buttons which is set to the top right
        VBox vbox = new VBox();
//        Button north = new Button("North");
//        Button south = new Button("South");
//        Button east = new Button("East");
//        Button west = new Button("West");
        Button load = new Button("Load");
        Button reload = new Button("Relaod");
        Button hint = new Button("Hint");
        vbox.getChildren().addAll(north, south, east, west, load, reload, hint);
        borderPane.setRight(vbox);
        vbox.setMinHeight(75);
        vbox.setMaxHeight(75);
        BorderPane.setAlignment(vbox,Pos.CENTER_LEFT);

        north.setOnAction((event) -> {
            model.choose("north",1 , 1);
        });
        south.setOnAction((event) -> {
            model.choose("south", 1, 1);
        });
        east.setOnAction((event) -> {
            model.choose("east",1, 1);
        });
        west.setOnAction((event) -> {
            model.choose("west",1, 1);
        });
        //load method triggers when reload button is pressed
        load.setOnAction((event) -> {
            model.load("data/lunarlanding/lula-4.txt");
        });
        //reload method triggers when reload button is pressed
        reload.setOnAction((event) -> {
            model.reload();
        });
        //hint method triggers when hint button is pressed
        hint.setOnAction((event) -> {
            model.hint();
        });

        // add the main pane to the scene and set some properties before showing it
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setTitle("Concentration");
        stage.setWidth(640);
        stage.setHeight(550);
        stage.show();
    }

    public void directionButton(String data, int row, int col) {
        north.setOnAction((event) -> {
            System.out.println("works n");
            model.go("north", row , col);
            return;
        });
        south.setOnAction((event) -> {
            System.out.println("works s");
            model.go("south", row , col);
            return;
        });
        east.setOnAction((event) -> {
            System.out.println("works e ");
            model.go("east", row , col);
            return;
        });
        west.setOnAction((event) -> {
            System.out.println("works w");
            model.go("west", row , col);
            return;
        });
    }

    @Override
    public void update( LunarLandingModel lunarLandingModel, Object o ) {
        System.out.println( "My model has changed! (DELETE THIS LINE)");
        String[][] board = lunarLandingModel.returnBoard();
        int rDim = board.length;
        int cDim = board[0].length;
        for(int i = 0; i < buttonList.size(); i++){
            FigureButton temp = buttonList.get(i);
            if(!board[temp.getRow()][temp.getCol()].equals("-")){
                if(temp.getData().equals(("!"))){

                }
                else if(board[temp.getRow()][temp.getCol()].contains("E")){
                    temp.setGraphic(new ImageView(explorer));
                    System.out.println("ex works");
                }
                else
                    temp.setGraphic(new ImageView(robotBlue));
            }
            else{
                temp.setGraphic(null);
            }
            temp.setData(board[temp.getRow()][temp.getCol()]);
        }
        if(o.equals("chooseAgain")) {
            top.setText("No figure at that position");
        }
        if(o.equals("Illegal move")) {
            top.setText("Illegal move");
        }
        if(o.equals("notwin")) {
            top.setText("Illegal move");
        }
    }

    public static void main( String[] args ) {
        //LunarLandingPTUI ptui = new LunarLandingPTUI(args[0]);
        Application.launch( args );
    }
}
