package physics;

import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class Body extends Mover{

    protected int color;
    protected float radius;

    protected Body(PVector pos, PVector acc, float mass, float radius, int color) {
        super(pos, acc, mass);
        this.radius = radius;
        this.color = color;
    }

    public void display(PApplet p, SubPlot plt) {
        p.pushStyle();
        float[] pp = plt.getPixelCoord(pos.x, pos.y);
        float[] r = plt.getDimInPixel(radius, radius);
        p.noStroke();
        p.fill(color);
        p.circle(pp[0], pp[1], 2*r[0]);
        p.popStyle();
    }
}
