package puzzles.tipover.ptui;

import puzzles.tipover.model.TipOverConfig;
import puzzles.tipover.model.TipOverModel;
import util.Observer;

import java.util.Scanner;

/**
 * DESCRIPTION
 * @author Maddan Eisenberg
 * November 2021
 */
public class TipOverPTUI implements Observer<TipOverModel, Object > {
    private TipOverModel model;
    private TipOverConfig config;
    public static void main(String[] args ) {
        TipOverPTUI ptui = new TipOverPTUI(args[0]);
        ptui.run();
    }
    public TipOverPTUI(String filename)
    {
        config = new TipOverConfig(filename);
        this.model = new TipOverModel(config);
        initializeView();
    }
    public void run()
    {
        Scanner in = new Scanner(System.in);
        for (; ; ) {
            System.out.print("> ");
            String line = in.nextLine();
            String[] fields = line.split("\\s+");
            if (fields.length > 0) {
                if (fields[0].contains("quit")) {
                    break;
                } else if (fields[0].contains("load")) {
                    this.model.load(fields[1]);
                } else if (fields[0].contains("reload")) {
                    this.model.reload();
                } else if (fields[0].contains("move")) {
                    this.model.move(fields[1]);
                } else if (fields[0].contains("hint")) {
                    this.model.hint();
                } else if (fields[0].contains("show")) {
                    this.model.show();
                } else if (fields[0].contains("help")) {
                    this.model.help();
                } else {
                    System.err.println("This is not a valid input.");
                }
            }

        }
    }
    public void initializeView()
    {
        this.model.addObserver(this);
        update(this.model, "notwin");
    }
    @Override
    public void update(TipOverModel tipOverModel, Object o) {

    }
}
