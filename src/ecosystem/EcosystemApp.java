package ecosystem;

import processing.core.PApplet;
import processing.core.PImage;
import setup.IProcessingApp;
import tools.SubPlot;

public class EcosystemApp implements IProcessingApp {
    private float[] viewport = {0f, 0f, 0.7f, 1f};

    private SubPlot plt;
    private boolean start;
    private Terrain terrain;
    private Population population;
    private boolean paused = false;

    private float buttonWidth = 162;
    private float buttonHeight = 64;
    private float buttonX, buttonY;
    private Animal target;


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


        buttonX = parent.width - buttonWidth - 65;
        buttonY = parent.height - buttonHeight - 40;
    }

    @Override
    public void draw(PApplet parent, float dt) {
        if (!start) {
            // Menu inicial
            parent.background(34, 79, 36);
            parent.textAlign(PApplet.CENTER);

            // Título
            PImage Title = parent.loadImage("art\\Title.png");
            parent.image(Title, parent.width / 2 - 100, 50, 200, 100);

            // Texto de introdução
            parent.fill(255);
            parent.textSize(18);
            parent.text("Bem-vindo à simulação de um ecossistema\n\n" +
                            "Nesta simulação, foi recriado um ecossistema simples\n" +
                            "com presas e predadores.\n\n" +
                            "Teclas:\n'A' para adicionar presas\n'P' para adicionar predadores\n" +
                            "'espaço' para pausar",
                    parent.width / 2, parent.height / 2);

            // Instrução para começar
            parent.textSize(20);
            parent.fill(200, 50, 50);
            parent.text("Clique para iniciar", parent.width / 2, parent.height - 100);

            // Verifica clique do mouse para iniciar
            if (parent.mousePressed) {
                start = true;
            }
        } else {
            // Simulação em andamento
            parent.background(34, 79, 36);

            parent.textSize(30);

            terrain.display(parent);
            population.display(parent, plt);

            PImage Title = parent.loadImage("art\\Title.png");
            parent.image(Title, buttonX, 30);

            PImage Pause = parent.loadImage("art\\ParaPausar.png");
            parent.image(Pause, buttonX + 15, 170);

            PImage ResetButton = parent.loadImage("art\\ResetButton.png");
            parent.image(ResetButton, buttonX + 25, buttonY);

            PImage preyImage = parent.loadImage("art\\Deer.png");
            parent.image(preyImage, buttonX + 20, buttonY - buttonHeight - 120);
            PImage predatorImage = parent.loadImage("art\\Wolf.png");
            parent.image(predatorImage, buttonX + 80, buttonY - buttonHeight - 120);

            parent.fill(0);
            parent.text(population.getNumPreys(), buttonX + 35, buttonY - buttonHeight - 55);
            parent.text(population.getNumPredator(), buttonX + 95, buttonY - buttonHeight - 55);

            if (parent.mousePressed && parent.mouseX > buttonX && parent.mouseX < buttonX + buttonWidth &&
                    parent.mouseY > buttonY && parent.mouseY < buttonY + buttonHeight) {
                setup(parent);
            }

            if (!paused) {
                terrain.regenerate();
                target = population.getTarget();
                population.update(dt, terrain, target);
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
        if (parent.key == 'a' || parent.key == 'A'){
            population.addPrey(parent, plt, terrain);
        }
        if (parent.key == 'p' || parent.key == 'P'){
            population.addPredator(parent, plt);
        }

    }

    @Override
    public void mousePressed(PApplet parent) {}

    @Override
    public void mouseDragged(PApplet parent) {}
}
