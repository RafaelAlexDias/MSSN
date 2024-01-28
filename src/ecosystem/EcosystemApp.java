package ecosystem;

import processing.core.PApplet;
import processing.core.PImage;
import setup.IProcessingApp;
import tools.SubPlot;

public class EcosystemApp implements IProcessingApp {
    private float timeDuration = 60;
    private float refPopulation = 720;
    private float refMeanMaxSpeed = 1f;
    private float refStdMaxSpeed = 0.2f;

    private float[] viewport = {0f, 0f, 0.7f, 1f};

    private SubPlot plt;

    private Terrain terrain;
    private Population population;
    private float timer, updateGraphTime;
    private float intervalUpdate = 1;
    private boolean paused = false;

    private float buttonWidth = 180;
    private float buttonHeight = 90;
    private float buttonX, buttonY;


    @Override
    public void setup(PApplet parent) {
        plt = new SubPlot(WorldConstants.WINDOW, viewport, parent.width, parent.height);

        terrain = new Terrain(parent, plt);
        terrain.setTerrainArt(getTerrainArt(parent));
        terrain.initRandomCustom(WorldConstants.PATCH_TYPE_PROB);
        for(int i=0; i<2; i++) {
            terrain.majorityRule();
        }
        population = new Population(parent, plt, terrain);

        timer = 0;
        updateGraphTime = timer + intervalUpdate;

        buttonX = parent.width - buttonWidth - 70; // 20 pixels de margem
        buttonY = parent.height - buttonHeight - 40; // 20 pixels de margem
    }

    @Override
    public void draw(PApplet parent, float dt) {
        parent.background(34, 79, 36);

        terrain.display(parent);
        population.display(parent, plt);


        parent.fill(200);
        parent.rect(buttonX, buttonY, buttonWidth, buttonHeight);
        parent.fill(0);
        parent.text("Reset", buttonX + 60, buttonY + 50);
        parent.textSize(30);

        PImage preyImage = parent.loadImage("art\\Deer.png");
        parent.image(preyImage, buttonX - 5, buttonY - buttonHeight - 120);
        PImage predatorImage = parent.loadImage("art\\Wolf.png");
        parent.image(predatorImage, buttonX + 55, buttonY - buttonHeight - 120);

        parent.fill(0);
        parent.text(population.getNumPreys(), buttonX, buttonY - buttonHeight - 55);
        parent.fill(0);
        parent.text(population.getNumPredator(), buttonX + 60, buttonY - buttonHeight - 55);

        if(!paused) {
            timer += dt;

            terrain.regenerate();
            population.update(dt, terrain);
            population.update(dt, terrain);

            if(timer > updateGraphTime) {
                System.out.println(String.format("Time = %ds", (int)timer));
                System.out.println("numPreys = " + population.getNumPreys());
                System.out.println("numPredators = " + population.getNumPredator());
                System.out.println("MeanMaxSpeed = " + population.getPreyMeanMaxSpeed());
                System.out.println("StdMaxSpeed = " + population.getPreyStdMaxSpeed());
                System.out.println("meanWeightWander = " + population.getPreyMeanWeights()[0] +
                        " meanWeightAvoid = " + population.getPreyMeanWeights()[1]);
                System.out.println("");

                updateGraphTime = timer + intervalUpdate;
            }

            if (parent.mousePressed && parent.mouseX > buttonX && parent.mouseX < buttonX + buttonWidth &&
                    parent.mouseY > buttonY && parent.mouseY < buttonY + buttonHeight) {
                setup(parent);
            }
        }

    }

    private String[] getTerrainArt(PApplet p) {
        String[] art = new String[WorldConstants.NSTATES];

        for(int i=0; i<WorldConstants.NSTATES; i++) {
            art[i] = WorldConstants.TERRAIN_ART[i];
        }
        return art;
    }

    @Override
    public void keyPressed(PApplet parent) {
        if(parent.key == ' ') {
            paused = !paused;
        }
    }

    @Override
    public void mousePressed(PApplet parent) {}

    @Override
    public void mouseDragged(PApplet parent) {}
}
