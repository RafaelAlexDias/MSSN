package ac;

import processing.core.PApplet;
import setup.IProcessingApp;
import tools.SubPlot;

public class TestCA implements IProcessingApp {

    private int nrows = 15;
    private int ncols = 20;
    private int nStates = 4;
    private int radiusNeigh = 2;
    private CellularAutomata ca;
    private SubPlot plt;
    private double[] window = {0, 10, 0, 10};
    private float[] viewport = {0.3f, 0.3f, 0.5f, 0.6f};

    @Override
    public void setup(PApplet parent) {
        plt = new SubPlot(window, viewport, parent.width, parent.height);
        ca = new CellularAutomata(parent, plt, nrows, ncols, nStates, radiusNeigh);
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
        Cell cell = ca.pixel2Cell(parent.mouseX, parent.mouseY);

        Cell[] neigh = cell.getNeighbors();
        for (int i=0; i<neigh.length; i++) {
            neigh[i].setState(nStates-1);
        }
    }

    @Override
    public void mouseDragged(PApplet parent) {

    }
}
