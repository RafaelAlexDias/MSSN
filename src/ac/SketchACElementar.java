package ac;

import processing.core.PApplet;

public class SketchACElementar implements setup.IProcessingApp {

    ACElementar ac;

    @Override
    public void setup(PApplet p) {
        ac = new ACElementar(p);
        ac.display(p);
    }

    @Override
    public void draw(PApplet p, float dt) {
        ac.generate();
        ac.display(p);
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