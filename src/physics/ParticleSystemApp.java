package physics;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

public class ParticleSystemApp implements IProcessingApp {

    private ParticleSystem ps;
    private double[] window = {-10, 10, -10, 10};
    private float[] viewport = {0, 0, 1, 1};
    private SubPlot plt;
    private PVector particleSpeed;

    @Override
    public void setup(PApplet parent) {
        plt = new SubPlot(window, viewport, parent.width, parent.height);
        ps = new ParticleSystem(new PVector(), new PVector(), 1f, 0.2f, parent.color(255, 0, 0), 1f,
                new PVector(-5, 5));
    }

    @Override
    public void draw(PApplet parent, float dt) {
        parent.background(255);
        ps.move(dt);
        ps.display(parent, plt);
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
