package puzzles.tipover.gui;

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
import puzzles.tipover.model.TipOverConfig;
import puzzles.tipover.model.TipOverModel;
import util.Observer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * DESCRIPTION
 *
 * @author Maddan Eisenberg
 * November 2021
 */
public class TipOverGUI extends Application
        implements Observer<TipOverModel, Object> {

    private static Stage stage;
    //the config
    private static TipOverConfig config;
    //the model
    private static TipOverModel model;
    private static String filename;

    //an instantiation of the top label text, so it can be used throughout the entre class
    Label top = new Label("New file loaded.");

    /**
     * explorer image
     */
    private Button[][] buttonArr;
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
        int[][] board = config.getBoard();
        buttonArr = new Button[board.length][board[0].length];
        for (int row = 0; row < board.length; ++row) {
            for (int col = 0; col < board[0].length; ++col) {
                Button button = new Button();
                Font buttonFont = new Font(35);
                button.setFont(buttonFont);
                button.setUserData(board[row][col]);
                button.setMinSize(100, 100);
                button.setMinSize(100, 100);
                gridPane.add(button, col, row);
                int[] pos = new int[2];
                pos[0] = row;
                pos[1] = col;
                int[] curr = new int[2];
                curr[0] = config.getCurrCoords()[0];
                curr[1] = config.getCurrCoords()[1];
                int[] goal = config.getGoalCoords();
                if (Arrays.equals(pos, goal)) {
                    button.setStyle("-fx-background-color: Red");
                } else {
                    button.setStyle("-fx-background-color: White");
                }
                if (Arrays.equals(pos, curr)) {
                    button.setStyle("-fx-background-color: Pink");
                }
                button.setText(Integer.toString((int) button.getUserData()));
                buttonArr[row][col] = button;
            }
        }
        return gridPane;
    }

    @Override
    public void init() throws Exception {
        System.out.println("init: initialize and connect to model!");
        model.addObserver(this);
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
        Font topFont = new Font(20);
        top.setFont(topFont);

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
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setTitle("Concentration");
        stage.setWidth(900);
        stage.setHeight(900);
        stage.show();

        load.setOnAction((event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File temp = fileChooser.showOpenDialog(stage);
            if (temp != null) {
                try {
                    model.load(temp.getPath());
                    filename = temp.getPath();
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
        });
        //hint method triggers when hint button is pressed
        hint.setOnAction((event) -> {
            model.hint();
        });
        north.setOnAction((event) -> {
            model.move("north");
        });
        south.setOnAction((event) -> {
            model.move("south");
        });
        east.setOnAction((event) -> {
            model.move("east");
        });
        west.setOnAction((event) -> {
            model.move("west");
        });
    }
    @Override
    public void update(TipOverModel tipOverModel, Object o) {
        top.setText("");
        config = tipOverModel.returnConfig();
        int[][] board = config.getBoard();
        int[] goalCoords = config.getGoalCoords();
        int[] currCoords = config.getCurrCoords();
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[0].length; j++) {
                if (goalCoords[0] == i && goalCoords[1] == j)
                    buttonArr[i][j].setStyle("-fx-background-color: Red");
                else if (currCoords[0] == i && currCoords[1] == j)
                    buttonArr[i][j].setStyle("-fx-background-color: Pink");
                else
                    buttonArr[i][j].setStyle("-fx-background-color: White");
                buttonArr[i][j].setUserData(board[i][j]);
                buttonArr[i][j].setText(Integer.toString((int) buttonArr[i][j].getUserData()));
            }
        switch ((String) o)
        {
            case "towertopple" -> top.setText("A tower has been tipped over.");
            case "hint", "show", "invaliddir" -> {}
            default -> top.setText((String) o);
        }
    }

    public static void main(String[] args) {
        config = new TipOverConfig(args[0]);
        model = new TipOverModel(config, args[0]);
        filename = args[0];
        Application.launch(args[0]);
    }
}
