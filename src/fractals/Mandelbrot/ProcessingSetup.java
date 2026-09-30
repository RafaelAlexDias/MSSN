package fractals.Mandelbrot;

import fractals.ForestApp;
import fractals.FruitTreeApp;
import processing.core.PApplet;

public class ProcessingSetup extends PApplet{
    private static IProcessingApp app;
    private float lastUpdateTime;
    @Override
    public void settings(){
        size(800, 700);
    }
    @Override
    public void setup(){
        app.setup(this);
        lastUpdateTime = millis();
    }
    @Override
    public void draw(){
        float now = millis();
        float dt = (now - lastUpdateTime) / 1000f;

        lastUpdateTime = now;
        app.draw(this, dt);
    }
    @Override
    public void mousePressed() {
        app.mousePressed(this);
    }
    @Override
    public void keyPressed() {
        app.keyPressed(this);
    }
    @Override
    public void mouseDragged() {
        app.mouseDragged(this);
    }
    @Override
    public void mouseReleased() {
        app.mouseReleased(this);
    }
    public static void main(String[] args) {
        // app = new ForestApp(); // Descomentar para simular o ForestApp (Exercício C-1)
        // app = new FruitTreeApp(); // Descomentar para simular o FruitTreeApp (Exercício C-2)
        app = new MandelbrotApp(); // Descomentar para simular o MandelbrotApp (Exercício D-1)
        PApplet.main(ProcessingSetup.class);
    }
}