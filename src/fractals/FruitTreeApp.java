package fractals;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

public class FruitTreeApp implements IProcessingApp {
    private LSystem lsys;
    private double[] window = {-15, 15, 0, 15};
    private float[] viewport = {0.1f, 0.1f, 0.8f, 0.8f};
    private PVector startingPosition = new PVector();
    private SubPlot plt;
    private Turtle turtle;

    @Override
    public void setup(PApplet parent) {
        plt = new SubPlot(window, viewport, parent.width, parent.height);
        Rule[] rules = new Rule[2];
        rules[0] = new Rule('F', " G[+F]-F");
        rules[1] = new Rule('G', "GG");

        lsys = new LSystem("F", rules);
        turtle = new Turtle(5,PApplet.radians(25));
    }

    @Override
    public void draw(PApplet parent, float dt) {
        float[] bb = plt.getBoundingBox();
        parent.rect(bb[0], bb[1], bb[2], bb[3]);
        turtle.setPose(startingPosition, PApplet.radians(90), parent, plt);
        turtle.render(lsys, parent, plt);
    }

    @Override
    public void keyPressed(PApplet parent) {

    }

    @Override
    public void mousePressed(PApplet parent) {
        System.out.println(lsys.getSequence());
        lsys.nextGeneration();
        turtle.scaling(0.55f);
    }

    @Override
    public void mouseDragged(PApplet parent) {

    }
}
