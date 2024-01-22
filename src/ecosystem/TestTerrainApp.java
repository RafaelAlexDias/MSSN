package ecosystem;

import processing.core.PApplet;
import setup.IProcessingApp;
import tools.SubPlot;

public class TestTerrainApp implements IProcessingApp {

    private float[] viewport = {0f, 0f, 1f, 1f};
    private SubPlot plt;
    private Terrain terrain;

    @Override
    public void setup(PApplet parent) {
        plt = new SubPlot(WorldConstants.WINDOW, viewport, parent.width, parent.height);
        terrain = new Terrain(parent, plt);
        terrain.setStateColors(getColors(parent));
        terrain.initRandom();
        for (int i=0; i<5; i++) {
            terrain.majorityRule();
        }
    }

    @Override
    public void draw(PApplet parent, float dt) {
        terrain.regenerate();
        terrain.display(parent);
    }

    private int[] getColors(PApplet p) {
        int[] colors = new int[WorldConstants.NSTATES];
        for (int i=0; i<WorldConstants.NSTATES; i++) {
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
