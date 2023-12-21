package chaos_game;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;

// MSSN: Chaos Game v1 (september 2019)
public class ChaosGame implements IProcessingApp {

    private float x, y;
    private ArrayList<PVector> points;
    private PApplet p;

    @Override
    public void setup(PApplet p) {
        this.p = p;
        initializeAndDrawInitialRandomPoint();
        initializeAndDrawInitialShape(); // e.g., a triangle for a Sierpinski triangle
    }

    @Override
    public void draw(PApplet p, float dt) {
        // foreach draw method, draws one hundred points (purpose: draw faster)
        for (int i = 0; i != 100; i++) {
            int r = (int) Math.floor(p.random(points.size()));
            x = PApplet.lerp(x, points.get(r).x, 0.5f);
            y = PApplet.lerp(y, points.get(r).y, 0.5f);
            p.point(x, y);
        }
    }

    private void initializeAndDrawInitialRandomPoint() {
        PVector randomInitialPoint = new PVector(p.random(p.width), p.random(p.height));
        p.point(randomInitialPoint.x, randomInitialPoint.y);
    }

    private void initializeAndDrawInitialShape() {
        points = new ArrayList<>();
        points.add(new PVector(p.width/2, 0));
        points.add(new PVector(0, p.height));
        points.add(new PVector(p.width, p.height));
        for(PVector p1 : points){
            p.point(p1.x, p1.y);
        }
    }

    @Override
    public void mousePressed(PApplet p) {
        throw new UnsupportedOperationException("mousePressed event is not implemented!");
    }

    @Override
    public void mouseDragged(PApplet parent) {

    }

    @Override
    public void keyPressed(PApplet p) {
        throw new UnsupportedOperationException("keyPressed event is not implemented!");
    }
}