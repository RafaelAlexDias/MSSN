package ac;

import processing.core.PApplet;

import java.awt.MouseInfo;
import java.awt.Point;

public class SketchGOL implements setup.IProcessingApp {

    private boolean paused = false;
    private boolean delete = false;
    private int NUM_STEPS_PER_FRAME = 20;
    private GOL gol;

    @Override
    public void setup(PApplet parent) {
        gol = new GOL(parent);
    }

    @Override
    public void draw(PApplet parent, float dt) {
        parent.background(255);
        gol.display(parent);
        if (parent.frameCount % NUM_STEPS_PER_FRAME == 0 && !paused) {
            gol.regrasGOL();
        }
    }

    @Override
    public void keyPressed(PApplet parent) {
        if (parent.key == ' ') {
            paused = !paused;
        }
        if (parent.key == 'd') {
            delete = !delete;
        }
        if (parent.key == 'c') {
            gol.clearScreen();
        }
    }

    @Override
    public void mousePressed(PApplet parent) {

    }

    @Override
    public void mouseDragged(PApplet parent) {
        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
        int mouseX = (int) mouseLocation.getX() - 375;
        int mouseY = (int) mouseLocation.getY() - 110;
        if (delete) {
            gol.setCell(mouseX / gol.cellDimension, mouseY / gol.cellDimension, 0);
        } else {
            gol.setCell(mouseX / gol.cellDimension, mouseY / gol.cellDimension, 1);
        }
    }
}
