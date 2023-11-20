package physics;

import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class CelestialBody extends Mover{

    private int color;
    private static double G = 6.67e-11;

    protected CelestialBody(PVector pos, PVector acc, float mass, float radius, int color) {
        super(pos, acc, mass, radius);
        this.color = color;
    }

    public PVector attraction(Mover m) {
        PVector r = PVector.sub(pos, m.pos);
        float dist = r.mag();
        float strenght = (float) (G * mass * m.mass / Math.pow(dist, 2));

        return r.normalize().mult(strenght);
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
