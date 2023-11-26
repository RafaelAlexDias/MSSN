package aa;

import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

import static aa.DNA.random;

public class FlockWASDApp implements IProcessingApp {

    private Flock flock;
    private int numBoidControl = 0;
    private Boid boidControl;
    private float velocity = 3f;
    private float[] sacWeights  = {1f, 1f, 1f};
    private double[] window = {-10, 10, -10, 10};
    private float[] viewport = {0, 0, 1, 1};
    private SubPlot plt;

    @Override
    public void setup(PApplet parent) {
        plt = new SubPlot(window, viewport, parent.width, parent.height);
        flock = new Flock(10, 0.1f, 0.3f, parent.color(0, 100, 200), sacWeights, parent, plt);
        boidControl = flock.getBoid(numBoidControl);
    }

    @Override
    public void draw(PApplet parent, float dt) {
        // parent.background(255);
        float[] bb = plt.getBoundingBox();
        parent.fill(255, 64);
        parent.rect(bb[0], bb[1], bb[2], bb[3]);

        flock.followBoidControl(boidControl, velocity, dt);
        flock.display(parent, plt);
    }

    @Override
    public void keyPressed(PApplet parent) {
        if (parent.key == 'w' || parent.key == 'W') {
            boidControl.applyForce(new PVector(0, velocity));
        }
        if (parent.key == 'a' || parent.key == 'A') {
            boidControl.applyForce(new PVector(-velocity, 0));
        }
        if (parent.key == 's' || parent.key == 'S') {
            boidControl.applyForce(new PVector(0, -velocity));
        }
        if (parent.key == 'd' || parent.key == 'D') {
            boidControl.applyForce(new PVector(velocity, 0));
        }
    }

    @Override
    public void mousePressed(PApplet parent) {

    }

    @Override
    public void mouseDragged(PApplet parent) {

    }
}
