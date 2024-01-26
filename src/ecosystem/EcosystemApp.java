package ecosystem;

import processing.core.PApplet;
import processing.core.PImage;
import setup.IProcessingApp;
import tools.SubPlot;
import tools.TimeGraph;

public class EcosystemApp implements IProcessingApp {
    private float timeDuration = 60;
    private float refPopulation = 720;
    private float refMeanMaxSpeed = 1f;
    private float refStdMaxSpeed = 0.2f;

    private float[] viewport = {0f, 0f, 0.7f, 1f};

    private SubPlot plt;

    private Terrain terrain;
    private Population preys, predators;
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
        preys = new Population(parent, plt, terrain, WorldConstants.PREY_ART);
        predators = new Population(parent, plt, terrain, WorldConstants.PREDATOR_ART);
        timer = 0;
        updateGraphTime = timer + intervalUpdate;

        buttonX = parent.width - buttonWidth - 70; // 20 pixels de margem
        buttonY = parent.height - buttonHeight - 40; // 20 pixels de margem
    }

    @Override
    public void draw(PApplet parent, float dt) {
        parent.background(34, 79, 36); // 255 é branco, você pode ajustar a cor de fundo conforme necessário
        if(!paused) {
            timer += dt;

            terrain.regenerate();
            preys.update(dt, terrain);
            predators.update(dt, terrain);

            terrain.display(parent);
            preys.display(parent, plt);
            predators.display(parent, plt);

            parent.fill(200);
            parent.rect(buttonX, buttonY, buttonWidth, buttonHeight);
            parent.fill(0);
            parent.text("Reset", buttonX + 60, buttonY + 50);
            parent.textSize(30);

            PImage preyImage = parent.loadImage("art\\Deer.png");
            parent.image(preyImage, buttonX, buttonY - buttonHeight - 100);

            PImage predatorImage = parent.loadImage("art\\Deer.png");
            parent.image(predatorImage, buttonX + 60, buttonY - buttonHeight - 100);

            // Exibir preys.getNumAnimals() abaixo do botão
            parent.fill(0);
            parent.text(preys.getNumAnimals(), buttonX, buttonY - buttonHeight - 55);
            parent.fill(0);
            parent.text(predators.getNumAnimals(), buttonX + 60, buttonY - buttonHeight - 55);


            if(timer > updateGraphTime) {
                System.out.println(String.format("Time = %ds", (int)timer));
                System.out.println("numAnimals = " + preys.getNumAnimals());
                System.out.println("MeanMaxSpeed = " + preys.getMeanMaxSpeed());
                System.out.println("StdMaxSpeed = " + preys.getStdMaxSpeed());
                System.out.println("meanWeightWander = " + preys.getMeanWeights()[0] +
                        " meanWeightAvoid = " + preys.getMeanWeights()[1]);
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

        for (int i=0; i<WorldConstants.NSTATES; i++) {
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
    public void mousePressed(PApplet parent) {

    }

    @Override
    public void mouseDragged(PApplet parent) {

    }
}
