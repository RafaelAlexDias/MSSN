package setup;

import ecosystem.EcosystemApp;
import processing.core.PApplet;

public class ProcessingSetup extends PApplet{
    private static IProcessingApp app;
    private float lastUpdateTime;
    @Override
    public void settings(){
        // Para o EcosystemApp
        size(1028, 720);
        // Para tudo o resto:
        // size(800, 700);
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
    public static void main(String[] args) {
        app = new EcosystemApp();
        PApplet.main(ProcessingSetup.class);
    }
}