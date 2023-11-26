package aa;

import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class ReynoldsTestApp implements IProcessingApp {

    private Boid wander, seeker, pursuiter, boid;
    private Flock flock;
    private float[] sacWeights  = {1f, 1f, 1f};
    private double[] window = {-10, 10, -10, 10};
    private float[] view1 = {0.02f, 0.51f, 0.96f, 0.47f};
    private float[] view2 = {0.02f, 0.02f, 0.47f, 0.47f};
    private float[] view3 = {0.51f, 0.02f, 0.47f, 0.47f};
    private SubPlot plt1, plt2, plt3;
    private Body target;
    private int ix;

    @Override
    public void setup(PApplet parent) {
        plt1 = new SubPlot(window, view1, parent.width, parent.height);
        plt2 = new SubPlot(window, view2, parent.width, parent.height);
        plt3 = new SubPlot(window, view3, parent.width, parent.height);
        flock = new Flock(200, 0.1f, 0.3f, parent.color(0, 100, 200), sacWeights, parent, plt1);
        boid = flock.getBoid(4);

        wander = new Boid(new PVector(parent.random((float)window[0], (float)window[1]),
                parent.random((float)window[2], (float)window[3])), 0.5f,
                0.5f, parent.color(255, 0, 0), parent, plt2);
        wander.addBehavior(new Wander(1f));

        pursuiter = new Boid(new PVector(parent.random((float)window[0], (float)window[1]),
                parent.random((float)window[2], (float)window[3])), 0.5f,
                0.5f, parent.color(0, 255, 0), parent, plt2);
        pursuiter.addBehavior(new Pursuit(1f));
        List<Body> allTrackingBodies = new ArrayList<Body>();
        allTrackingBodies.add(wander);
        pursuiter.setEye(new Eye(pursuiter, allTrackingBodies));

        target = new Body(new PVector(), new PVector(), 1f, 0.3f, parent.color(0));
        seeker = new Boid(new PVector(parent.random((float)window[0], (float)window[1]),
                parent.random((float)window[2], (float)window[3])), 0.5f,
                0.5f, parent.color(0, 0, 255), parent, plt3);
        seeker.addBehavior(new Seek(1f));
        //seeker.addBehavior(new Wander(1f));
        seeker.addBehavior(new Flee(1f));
        allTrackingBodies = new ArrayList<Body>();
        allTrackingBodies.add(target);
        seeker.setEye(new Eye(seeker, allTrackingBodies));
    }

    @Override
    public void draw(PApplet parent, float dt) {
        parent.background(255);
        float[] bb = plt1.getBoundingBox();
        parent.fill(255, 64);
        parent.rect(bb[0], bb[1], bb[2], bb[3]);

        bb = plt2.getBoundingBox();
        parent.fill(190, 170, 45, 64);
        parent.rect(bb[0], bb[1], bb[2], bb[3]);

        bb = plt3.getBoundingBox();
        parent.fill(120, 170, 150, 64);
        parent.rect(bb[0], bb[1], bb[2], bb[3]);

        flock.applyBehavior(dt);
        wander.applyBehaviors(dt);
        pursuiter.applyBehaviors(dt);
        seeker.applyBehavior(ix, dt);

        flock.display(parent, plt1);
        // boid.getEye().display(parent, plt1);
        wander.display(parent, plt2);
        pursuiter.display(parent, plt2);
        seeker.display(parent, plt3);
        // seeker.getEye().display(parent, plt3);
        target.display(parent, plt3);
    }

    @Override
    public void keyPressed(PApplet parent) {
        if (parent.key == 't') {
            ix = (ix + 1) % 2;
        }
    }

    @Override
    public void mousePressed(PApplet parent) {
        if (plt3.isInside(parent.mouseX, parent.mouseY)) {
            double[] w = plt3.getWorldCoord(parent.mouseX, parent.mouseY);
            target.setPos(new PVector((float)w[0], (float)w[1]));
        }
    }

    @Override
    public void mouseDragged(PApplet parent) {

    }
}
