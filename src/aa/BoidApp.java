package aa;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

public class BoidApp implements IProcessingApp {

    private Boid b;
    private double[] window = {-10, 10, -10, 10};
    private float[] viewport = {0, 0, 1, 1};
    private SubPlot plt;
    private DNA dna;
    private float[] maxSpeed = {4, 4};
    private PVector target;

    @Override
    public void setup(PApplet parent) {
        plt = new SubPlot(window, viewport, parent.width, parent.height);
        dna = new DNA(maxSpeed);
        b = new Boid(new PVector(), new PVector(), 1, 0.5f, parent.color(0),
                dna, parent, plt);
        target = new PVector();
    }

    @Override
    public void draw(PApplet parent, float dt) {
        parent.background(255);

        PVector f = b.seek(target);
        b.applyForce(f);
        b.move(dt);

        b.display(parent, plt);
    }

    @Override
    public void keyPressed(PApplet parent) {

    }

    @Override
    public void mousePressed(PApplet parent) {
        double[] ww = plt.getWorldCoord(parent.mouseX, parent.mouseY);
        target.x = (float) ww[0];
        target.y = (float) ww[1];
    }

    @Override
    public void mouseDragged(PApplet parent) {

    }
}
