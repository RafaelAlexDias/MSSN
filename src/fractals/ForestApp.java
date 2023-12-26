package fractals;

import fractals.Mandelbrot.IProcessingApp;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class ForestApp implements IProcessingApp {

    private double[] window = {-15, 15, 0, 15};
    private float[] viewport = {0f, 0f, 1f, 1f};
    private SubPlot plt;
    private List<Tree> forest;

    //

    private String axiom;
    private Rule[] rules;
    private float angle;
    private int niter;


    @Override
    public void setup(PApplet parent) {
        plt = new SubPlot(window, viewport, parent.width, parent.height);
        forest = new ArrayList<Tree>();

        // Default rules
        rules = new Rule[1];
        rules[0] = new Rule('F', "FF+[+F-F-F]-[-F+F+F]");
        axiom = "F";
        angle = 22.5f;
        niter = 3;
    }

    @Override
    public void draw(PApplet parent, float dt) {
        float[] bb = plt.getBoundingBox();
        parent.rect(bb[0], bb[1], bb[2], bb[3]);
        for (Tree tree : forest) {
            tree.grow(dt);
            tree.display(parent, plt);
        }
    }

    @Override
    public void keyPressed(PApplet parent) {
        if (parent.key == '1') {
            rules = new Rule[1];
            rules[0] = new Rule('F', "FF+[+F-F-F]-[-F+F+F]");
            axiom = "F";
            angle = 22.5f;
            niter = 3;
        }
        if (parent.key == '2') {
            rules = new Rule[2];
            rules[0] = new Rule('X', "F+[[X]-X]-F[-FX]+X");
            rules[1] = new Rule('F', "FF");
            axiom = "X";
            angle = 22.5f;
            niter = 3;
        }
        if (parent.key == '3') {
            rules = new Rule[2];
            rules[0] = new Rule('X', "F[+X][-X]FX");
            rules[1] = new Rule('F', "FF");
            axiom = "X";
            angle = 35f;
            niter = 6;
        }
    }

    @Override
    public void mousePressed(PApplet parent) {
        double[] w = plt.getWorldCoord(parent.mouseX, parent.mouseY);
        PVector pos = new PVector((float)w[0], (float)w[1]);
        Tree tree;
        tree = new Tree(axiom, rules, pos, 0.4f, PApplet.radians(angle), niter,
                0.5f, 1f, parent);
        forest.add(tree);
    }

    @Override
    public void mouseDragged(PApplet parent) {}

    @Override
    public void mouseReleased(PApplet parent) {

    }
}
