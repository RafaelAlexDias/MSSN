package fractals.Mandelbrot;

import processing.core.PApplet;
import tools.SubPlot;

public class MandelbrotApp implements IProcessingApp {

    private double[] window = {-2, 2, -2, 2};
    private float[] viewport = {0, 0, 1, 1};
    private SubPlot plt;
    private MandelbrotSet ms;
    private int mx0, my0, mx1, my1;

    @Override
    public void setup(PApplet parent) {
        plt = new SubPlot(window, viewport, parent.width, parent.height);
        ms = new MandelbrotSet(300, plt);

    }

    @Override
    public void draw(PApplet parent, float dt) {
        ms.display(parent, plt);
        displayNewWindow(parent);
    }

    private void displayNewWindow(PApplet p) {
        p.pushStyle();
        p.noFill();
        p.strokeWeight(3);
        p.stroke(255);
        p.rect(mx0, my0, mx1-mx0, my1-my0);
        p.popStyle();
    }

    @Override
    public void keyPressed(PApplet parent) {}
    @Override
    public void mouseReleased(PApplet parent) {
        double[] xy0 = plt.getWorldCoord(mx0, my0);
        double[] xy1 = plt.getWorldCoord(parent.mouseX, parent.mouseY);
        double xmin =  Math.min(xy0[0], xy1[0]);
        double xmax =  Math.max(xy0[0], xy1[0]);
        double ymin =  Math.min(xy0[1], xy1[1]);
        double ymax =  Math.max(xy0[1], xy1[1]);
        double[] window = {xmin, xmax, ymin, ymax};
        plt = new SubPlot(window, viewport, parent.width, parent.height);
        mx0 = my0 = mx1 = my1 = 0;
    }

    @Override
    public void mousePressed(PApplet parent) {
        mx0 = parent.mouseX;
        my0 = parent.mouseY;
    }
    @Override
    public void mouseDragged(PApplet parent) {
        mx1 = parent.mouseX;
        my1 = parent.mouseY;
    }
}
