package ecosystem;

import processing.core.PApplet;
import processing.core.PImage;
import setup.IProcessingApp;
import tools.SubPlot;

public class EcosystemApp implements IProcessingApp {
    private float[] viewport = {0f, 0f, 0.7f, 1f};

    private SubPlot plt;

    private Terrain terrain;
    private Population population;
    private float timer;
    private boolean paused = false;

    private float buttonWidth = 162;
    private float buttonHeight = 64;
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

        buttonX = parent.width - buttonWidth - 65;
        buttonY = parent.height - buttonHeight - 40;
    }

    @Override
    public void draw(PApplet parent, float dt) {
        parent.background(34, 79, 36);
        parent.textSize(30);

        terrain.display(parent);
        population.display(parent, plt);

        PImage Title = parent.loadImage("art\\Title.png");
        parent.image(Title, buttonX-25, 30);

        PImage Pause = parent.loadImage("art\\ParaPausar.png");
        parent.image(Pause, buttonX-10, 170);

        PImage ResetButton = parent.loadImage("art\\ResetButton.png");
        parent.image(ResetButton, buttonX, buttonY);

        PImage preyImage = parent.loadImage("art\\Deer.png");
        parent.image(preyImage, buttonX - 5, buttonY - buttonHeight - 120);
        PImage predatorImage = parent.loadImage("art\\Wolf.png");
        parent.image(predatorImage, buttonX + 55, buttonY - buttonHeight - 120);

        parent.fill(0);
        parent.text(population.getNumPreys(), buttonX, buttonY - buttonHeight - 55);
        parent.fill(0);
        parent.text(population.getNumPredator(), buttonX + 60, buttonY - buttonHeight - 55);

        if (parent.mousePressed && parent.mouseX > buttonX && parent.mouseX < buttonX + buttonWidth &&
                parent.mouseY > buttonY && parent.mouseY < buttonY + buttonHeight) {
            setup(parent);
        }

        if(!paused) {
            timer += dt;
            terrain.regenerate();
            population.update(dt, terrain);
            population.update(dt, terrain);
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
