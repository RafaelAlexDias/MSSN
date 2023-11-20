package Sketch_TP0;

import processing.core.PApplet;

import java.util.Random;

public class SketchLogo implements setup.IProcessingApp{

    int x = 240;
    int y = 300;

    Random rand = new Random();

    int intervalo = 2000;
    int ultimaAtualizacao;

    @Override
    public void setup(PApplet parent) {
        parent.noFill();
        ultimaAtualizacao = parent.millis();
    }
    @Override
    public void draw(PApplet parent, float dt) {
        parent.background(177, 177, 177);

        if (parent.millis() - ultimaAtualizacao > intervalo) {
            x = rand.nextInt(800 + 1);
            y = rand.nextInt(700 + 1);
            ultimaAtualizacao = parent.millis();
        }

        parent.beginShape();
        parent.vertex(x - 68, y - 61);
        parent.vertex(x + 60, y + 66);
        parent.vertex(x - 67, y + 194);
        parent.vertex(x - 194, y + 66);
        parent.vertex(x - 68, y - 61);
        parent.endShape();
        parent.fill(227, 24, 55);

        parent.beginShape();
        parent.vertex(x + 66, y - 196);
        parent.vertex(x + 194, y - 70);
        parent.vertex(x + 68, y + 58);
        parent.vertex(x - 59, y - 70);
        parent.vertex(x + 66, y - 196);
        parent.endShape();
        parent.fill(255, 255, 255);

        parent.ellipse(x + 67, y - 68, 62, 62);
        parent.fill(255, 255, 255);

        parent.ellipse(x - 114, y + 66, 62, 62);
        parent.fill(255, 255, 255);

        parent.ellipse(x - 21, y + 66, 62, 62);
        parent.fill(0, 100, 145);

    }

    @Override
    public void keyPressed(PApplet parent) {
        // throw new UnsupportedOperationException("keyPressed");
    }
    @Override
    public void mousePressed(PApplet parent) {
        /*
        double mouseX = MouseInfo.getPointerInfo().getLocation().getX();
        double mouseY = MouseInfo.getPointerInfo().getLocation().getY();
        System.out.println("X:" + mouseX);
        System.out.println("Y:" + mouseY);
        */
    }

    @Override
    public void mouseDragged(PApplet parent) {
        // throw new UnsupportedOperationException("mouseDragged");
    }
}