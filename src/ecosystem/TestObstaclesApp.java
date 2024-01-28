package ecosystem;

import processing.core.PApplet;
import setup.IProcessingApp;
import tools.SubPlot;

public class TestObstaclesApp implements IProcessingApp {

    private float[] viewport = {0f, 0f, 1f, 1f};
    private SubPlot plt;
    private Terrain terrain;
    private Population population;

    @Override
    public void setup(PApplet parent) {
        plt = new SubPlot(WorldConstants.WINDOW, viewport, parent.width, parent.height);
        terrain = new Terrain(parent, plt);
        terrain.setStateColors(getColors(parent));
        terrain.initRandomCustom(WorldConstants.PATCH_TYPE_PROB);
        for(int i=0; i<2; i++) {
            terrain.majorityRule();
        }
        population = new Population(parent, plt, terrain);
    }

    @Override
    public void draw(PApplet parent, float dt) {
        terrain.regenerate();
        population.update(dt, terrain);

        terrain.display(parent);
        population.display(parent, plt);

        System.out.println("numAnimals = " + population.getNumPreys());
    }

    private int[] getColors(PApplet p) {
        int[] colors = new int[WorldConstants.NSTATES];
        for(int i=0; i<WorldConstants.NSTATES; i++) {
            colors[i] = p.color(WorldConstants.TERRAIN_COLORS[i][0],
                                WorldConstants.TERRAIN_COLORS[i][1],
                                WorldConstants.TERRAIN_COLORS[i][2]);
        }
        return colors;
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
