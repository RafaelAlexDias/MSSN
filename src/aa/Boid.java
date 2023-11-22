package aa;

import physics.Body;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;
import tools.SubPlot;

public class Boid extends Body {

    private SubPlot plt;
    private PShape shape;

    protected Boid(PVector pos, PVector acc, float mass, float radius,
                   int color, DNA dna, PApplet p, SubPlot plt) {
        super(pos, acc, mass, radius, color);
        this.plt = plt;
        setShape(p, plt);
    }

    public void setShape(PApplet p, SubPlot plt) {
        float[] rr = plt.getDimInPixel(radius, radius);
        shape = p.createShape();
        shape.beginShape();
        shape.vertex(-rr[0], rr[0]/2);
        shape.vertex(rr[0], 0);
        shape.vertex(-rr[0], -rr[0]/2);
        shape.vertex(-rr[0], 0);
        shape.endShape(PConstants.CLOSE);
    }

    public PVector seek(PVector target) {
        PVector vd = PVector.sub(target, pos);
        vd.normalize().mult(10);
        return  PVector.sub(vd, vel);
    }

    @Override
    public void display(PApplet p, SubPlot plt) {
        float[] pp = plt.getPixelCoord(pos.x, pos.y);
        p.translate(pp[0], pp[1]);
        p.rotate(-vel.heading());
        p.shape(shape);
    }
}
