package aa;

import processing.core.PApplet;
import setup.IProcessingApp;
import tools.SubPlot;

public class FlockTestApp implements IProcessingApp {

    private Flock flock;
    private float[] sacWeights  = {1f, 1f, 1f};
    private double[] window = {-10, 10, -10, 10};
    private float[] viewport = {0, 0, 1, 1};
    private SubPlot plt;

    @Override
    public void setup(PApplet parent) {
        plt = new SubPlot(window, viewport, parent.width, parent.height);
        flock = new Flock(200, 0.1f, 0.3f, parent.color(0, 100, 200), sacWeights, parent, plt);
    }

    @Override
    public void draw(PApplet parent, float dt) {
        // parent.background(255);
        float[] bb = plt.getBoundingBox();
        parent.fill(255, 64);
        parent.rect(bb[0], bb[1], bb[2], bb[3]);

        flock.applyBehavior(dt);
        flock.display(parent, plt);
    }

    @Override
    public void keyPressed(PApplet parent) {

    }

    @Override
    public void mousePressed(PApplet parent) {

    }

    @Override
    public void mouseDragged(PApplet parent) {

    }
}
