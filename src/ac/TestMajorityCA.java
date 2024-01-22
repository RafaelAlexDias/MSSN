package ac;

import processing.core.PApplet;
import setup.IProcessingApp;
import tools.SubPlot;

public class TestMajorityCA implements IProcessingApp {

    private double[] window= {0, 10, 0, 10};
    private float[] viewport = {0.3f, 0.3f, 0.5f, 0.6f};
    private SubPlot plt;
    private MajorityCA ca;

    @Override
    public void setup(PApplet parent) {
        plt = new SubPlot(window, viewport, parent.width, parent.height);
        ca = new MajorityCA(parent, plt, 30, 40, 4, 1);
        ca.initRandom();
    }

    @Override
    public void draw(PApplet parent, float dt) {
        ca.display(parent);
    }

    @Override
    public void keyPressed(PApplet parent) {

    }

    @Override
    public void mousePressed(PApplet parent) {
        ca.majorityRule();
    }

    @Override
    public void mouseDragged(PApplet parent) {

    }
}
