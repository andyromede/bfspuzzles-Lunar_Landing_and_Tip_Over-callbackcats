package puzzles.lunarlanding.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import puzzles.lunarlanding.model.LunarLandingConfig;
import puzzles.lunarlanding.model.LunarLandingModel;
import util.Observer;
import java.io.File;
import java.util.ArrayList;

/**
 * DESCRIPTION
 *
 * @author Andrew Le
 * November 2021
 */
public class LunarLandingGUI extends Application
        implements Observer<LunarLandingModel, Object> {

    private static Stage stage;
    //the config
    private static LunarLandingConfig config;
    //the model
    private static LunarLandingModel model;
    private static String filename;
    //a button list for Figure button
    ArrayList<FigureButton> buttonList = new ArrayList();


    //an instantiation of the top label text, so it can be used throughout the entre class
    private String topPrompt = "Select the first card";
    Label top = new Label(topPrompt);

    /**
     * explorer image
     */
    private final Image explorer = new Image(getClass().getResourceAsStream("resources/explorer.png"));

    /**
     * robot-blue image
     */
    private final Image robotBlue = new Image(getClass().getResourceAsStream("resources/robot-blue.png"));

    Button north = new Button("N");
    Button south = new Button("S");
    Button east = new Button("E");
    Button west = new Button("W");

    /**
     * A helper method that creates a GridPane for the
     * PokemonButtons and returns it for organization purposes
     *
     * @return a grid pane
     */
    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        String[][] board = model.returnBoard();
        int rDim = board.length;
        int cDim = board[0].length;
        for (int row = 0; row < rDim; ++row) {
            for (int col = 0; col < cDim; ++col) {
                //figure button is created with info regarding its contents and coordinates
                FigureButton button = new FigureButton(board[row][col], row, col);
                //sets the button min size
                button.setMinSize(100, 100);
                button.setMinSize(100, 100);
                //button is then added to grid pane
                gridPane.add(button, col, row);
                //sets the background colors
                if (!board[row][col].equals("!")) {
                    button.setStyle("-fx-background-color: LightGrey");
                } else {
                    button.setStyle("-fx-background-color: Tomato");
                }
                //sets the images
                if (!board[row][col].equals("-")) {
                    if (board[row][col].contains("!")) {

                    } else if (board[row][col].contains("E")) {
                        button.setGraphic(new ImageView(explorer));
                    } else
                        button.setGraphic(new ImageView(robotBlue));
                }
                //removes lettering from the button
                button.setText("");
                buttonList.add(button);
                button.setOnAction((event) -> {
                    top.setText("");
                    int pressedRow = button.getRow();
                    int pressedCol = button.getCol();
                    String data = button.getData();
                    if (model.choose(data))
                        directionButton(data, pressedRow, pressedCol);
                });
            }
        }
        //gridPane is returned
        return gridPane;
    }

    @Override
    public void init() throws Exception {
        System.out.println("init: initialize and connect to model!");
        model.addObserver(this);
    }

    private class FigureButton extends Button {

        //the position of the button
        private int row;
        private int col;
        private String data;
        private Image figure;

        /**
         * A constructor for the figure button.
         *
         * @param data the contents of the button(could contain a figure, robot or be empty)
         * @param row  the row position of the button
         * @param col  the col position of the button
         */
        public FigureButton(String data, int row, int col) {
            this.data = data;
            this.row = row;
            this.col = col;
            if (!data.equals("-")) {
                if (data.equals("!")) {

                } else if (data.contains("E")) {
                    figure = explorer;
                } else {
                    figure = robotBlue;
                }
            }

        }

        public FigureButton returnButton(int row, int col) {
            return this;
        }

        //returns the row
        public int getRow() {
            return row;
        }

        //returns the col
        public int getCol() {
            return col;
        }

        //returns the data
        public String getData() {
            return data;
        }

        //replaces the old data with new information
        public void setData(String newData) {
            data = newData;
        }
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        Font vboxFont = new Font(20);
        Font directionFont = new Font(15);
        //the main border pane is created
        BorderPane borderPane = new BorderPane();

        //a grid pane is created
        GridPane gridPane = createGridPane();
        borderPane.setCenter(gridPane);

        //top label is set to the top of the border
        borderPane.setTop(top);
        BorderPane.setAlignment(top, Pos.TOP_CENTER);

        //the vbox contains load, reload, hint, and the directional buttons which is set to the top right
        VBox vbox = new VBox();
        Button load = new Button("Load");
        Button reload = new Button("Reload");
        Button hint = new Button("Hint");
        vbox.getChildren().addAll(north, east, west, south, load, reload, hint);
        borderPane.setRight(vbox);
        load.setMinSize(87, 25);
        load.setFont(vboxFont);
        reload.setMinSize(75, 25);
        reload.setFont(vboxFont);
        hint.setMinSize(87,25 );
        hint.setFont(vboxFont);
        north.setFont(directionFont);
        east.setFont(directionFont);
        west.setFont(directionFont);
        south.setFont(directionFont);
        vbox.setMargin(north, new Insets(0,0,0,33));
        vbox.setMargin(east, new Insets(0,10,-32,62));
        vbox.setMargin(west, new Insets(0,25,0,0));
        vbox.setMargin(south, new Insets(0,0,0,34));
        BorderPane.setAlignment(vbox, Pos.CENTER_RIGHT);
        // add the main pane to the scene and set some properties before showing it
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setTitle("Concentration");
        stage.setWidth(606);
        stage.setHeight(550);
        stage.show();

        //load method triggers when reload button is pressed
        load.setOnAction((event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File temp = fileChooser.showOpenDialog(stage);
            if (temp != null) {
                try {
                    model.load(temp.getPath());
                    filename = temp.getPath();
                    top.setText(model.fileLoaded());
                } catch (Exception e) {
                    top.setText("This file isn't meant for this.");
                }
            } else {
                top.setText("Illegal file, try again");
            }
        });
        //reload method triggers when reload button is pressed
        reload.setOnAction((event) -> {
            model.load(filename);
            top.setText(model.fileLoaded());
        });
        //hint method triggers when hint button is pressed
        hint.setOnAction((event) -> {
            model.hint();
        });
    }

    public void directionButton(String data, int row, int col) {
        north.setOnAction((event) -> {
            System.out.println("works n");
            model.go("north", row, col);
            return;
        });
        south.setOnAction((event) -> {
            System.out.println("works s");
            model.go("south", row, col);
            return;
        });
        east.setOnAction((event) -> {
            System.out.println("works e ");
            model.go("east", row, col);
            return;
        });
        west.setOnAction((event) -> {
            System.out.println("works w");
            model.go("west", row, col);
            return;
        });
    }

    @Override
    public void update(LunarLandingModel lunarLandingModel, Object o) {
        String[][] board = lunarLandingModel.returnBoard();
        for (int i = 0; i < buttonList.size(); i++) {
            FigureButton temp = buttonList.get(i);
            if (!board[temp.getRow()][temp.getCol()].equals("-")) {
                if (board[temp.getRow()][temp.getCol()].contains("!")) {
                    temp.setGraphic(null);
                }
                if (board[temp.getRow()][temp.getCol()].contains("E")) {
                    temp.setGraphic(new ImageView(explorer));
                }
                if (!board[temp.getRow()][temp.getCol()].contains("E") && !board[temp.getRow()][temp.getCol()].equals("!"))
                    temp.setGraphic(new ImageView(robotBlue));
            } else {
                temp.setGraphic(null);
            }
            temp.setData(board[temp.getRow()][temp.getCol()]);
        }
        if (o.equals("chooseAgain")) {
            top.setText(model.chooseAgain());
        }
        if (o.equals("Illegal move")) {
            top.setText(model.illegalMove());
        }
        if (o.equals("hintwin")) {
            top.setText(model.hintWin());
        }
        if (o.equals("win")) {
            top.setText(model.win());
        }
        if (o.equals("alreadySolved")) {
            top.setText(model.alreadySolved());
        }
        if (o.equals("unsolvable")){
            top.setText(model.unsolvable());
        }
    }

    public static void main(String[] args) {
        config = new LunarLandingConfig(args[0]);
        model = new LunarLandingModel(config, args[0]);
        filename = args[0];
        Application.launch(args[0]);
    }
}
