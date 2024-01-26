package ecosystem;

import processing.core.PApplet;
import setup.IProcessingApp;
import tools.SubPlot;
import tools.TimeGraph;

public class TestEcosystemApp implements IProcessingApp {

    private float timeDuration = 60;
    private float refPopulation = 720;
    private float refMeanMaxSpeed = 1f;
    private float refStdMaxSpeed = 0.2f;

    private float[] viewport = {0f, 0f, 1f, 1f};

    private double[] winGraph1 = {0, timeDuration, 0, 2*refPopulation};
    private double[] winGraph2 = {0, timeDuration, 0, 2*refMeanMaxSpeed};
    private double[] winGraph3 = {0, timeDuration, 0, 2*refStdMaxSpeed};

    private float[] viewGraph1 = {.71f, .04f, 0.28f, 0.28f};
    private float[] viewGraph2 = {.71f, .37f, 0.28f, 0.28f};
    private float[] viewGraph3 = {.71f, .70f, 0.28f, 0.28f};

    private SubPlot plt, pltGraph1, pltGraph2, pltGraph3;
    private TimeGraph tg1, tg2, tg3;

    private Terrain terrain;
    private Population population;
    private float timer, updateGraphTime;
    private float intervalUpdate = 1;

    @Override
    public void setup(PApplet parent) {
        plt = new SubPlot(WorldConstants.WINDOW, viewport, parent.width, parent.height);
        /*
        pltGraph1 = new SubPlot(winGraph1, viewGraph1, parent.width, parent.height);
        pltGraph2 = new SubPlot(winGraph2, viewGraph2, parent.width, parent.height);
        pltGraph3 = new SubPlot(winGraph3, viewGraph3, parent.width, parent.height);

        tg1 = new TimeGraph(parent, pltGraph1, parent.color(255, 0, 0), refPopulation);
        tg2 = new TimeGraph(parent, pltGraph2, parent.color(255, 0, 0), refMeanMaxSpeed);
        tg3 = new TimeGraph(parent, pltGraph3, parent.color(255, 0, 0), refStdMaxSpeed);
         */

        terrain = new Terrain(parent, plt);
        // terrain.setStateColors(getColors(parent));
        terrain.setTerrainArt(getTerrainArt(parent));
        terrain.initRandomCustom(WorldConstants.PATCH_TYPE_PROB);
        for(int i=0; i<2; i++) {
            terrain.majorityRule();
        }
        population = new Population(parent, plt, terrain, WorldConstants.PREY_ART);
        timer = 0;
        updateGraphTime = timer + intervalUpdate;
    }

    @Override
    public void draw(PApplet parent, float dt) {
        timer += dt;

        terrain.regenerate();
        population.update(dt, terrain);

        terrain.display(parent);
        population.display(parent, plt);

        if(timer > updateGraphTime) {
            System.out.println(String.format("Time = %ds", (int)timer));
            System.out.println("numAnimals = " + population.getNumAnimals());
            System.out.println("MeanMaxSpeed = " + population.getMeanMaxSpeed());
            System.out.println("StdMaxSpeed = " + population.getStdMaxSpeed());
            System.out.println("meanWeightWander = " + population.getMeanWeights()[0] +
                    " meanWeightAvoid = " + population.getMeanWeights()[1]);
            System.out.println("");
            /*
            tg1.plot(timer, population.getNumAnimals());
            tg2.plot(timer, population.getMeanMaxSpeed());
            tg3.plot(timer, population.getStdMaxSpeed());

             */
            updateGraphTime = timer + intervalUpdate;
        }
    }

    private String[] getTerrainArt(PApplet p) {
        String[] art = new String[WorldConstants.NSTATES];

        for (int i=0; i<WorldConstants.NSTATES; i++) {
            art[i] = WorldConstants.TERRAIN_ART[i];
        }
        return art;
    }

    /*
    private int[] getColors(PApplet p) {
        int[] colors = new int[WorldConstants.NSTATES];
        for(int i=0; i<WorldConstants.NSTATES; i++) {
            colors[i] = p.color(WorldConstants.TERRAIN_COLORS[i][0],
                                WorldConstants.TERRAIN_COLORS[i][1],
                                WorldConstants.TERRAIN_COLORS[i][2]);
        }
        return colors;
    }
    */

    @Override
    public void keyPressed(PApplet parent) {

    }

    @Override
    public void mousePressed(PApplet parent) {
        /*
        winGraph1[0] = timer;
        winGraph1[1] = timer + timeDuration;
        winGraph1[3] = 2*population.getNumAnimals();
        pltGraph1 = new SubPlot(winGraph1, viewGraph1, parent.width, parent.height);
        tg1 = new TimeGraph(parent, pltGraph1, parent.color(255, 0, 0), population.getNumAnimals());

        winGraph2[0] = timer;
        winGraph2[1] = timer + timeDuration;
        tg2 = new TimeGraph(parent, pltGraph2, parent.color(255, 0, 0), refMeanMaxSpeed);

        winGraph3[0] = timer;
        winGraph3[1] = timer + timeDuration;
        tg3 = new TimeGraph(parent, pltGraph3, parent.color(255, 0, 0), refStdMaxSpeed);
         */
    }

    @Override
    public void mouseDragged(PApplet parent) {

    }
}
