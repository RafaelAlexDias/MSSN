package aa;

import physics.Body;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class Boid extends Body {

    private SubPlot plt;
    private PShape shape;
    protected DNA dna;
    protected Eye eye;
    private List<Behavior> behaviors;

    protected Boid(PVector pos, PVector acc, float mass, float radius,
                   int color, PApplet p, SubPlot plt) {
        super(pos, acc, mass, radius, color);
        dna = new DNA();
        behaviors = new ArrayList<Behavior>();
        this.plt = plt;
        setShape(p, plt);
    }

    public void setEye(Eye eye) {
        this.eye = eye;
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

    public void addBehavior(Behavior behavior) {
        behaviors.add(behavior);
    }

    public void removeBehavior(Behavior behavior) {
        if (behaviors.contains(behavior)) {
            behaviors.remove(behavior);
        }
    }

    public void applyBehaviors(float dt) {
        eye.look();

        PVector vd = new PVector();
        for (Behavior behavior : behaviors) {
            PVector vdd = behavior.getDesiredVelocity(this);
            vdd.mult(behavior.getWeight());
            vd.add(vdd);
        }
        move(dt, vd);
    }

    private void move(float dt, PVector vd) {
        vd.normalize().mult(dna.maxSpeed);
        PVector fs = PVector.sub(vd, vel);
        applyForce(fs.limit(dna.maxForce));
        super.move(dt);
    }

    @Override
    public void display(PApplet p, SubPlot plt) {
        float[] pp = plt.getPixelCoord(pos.x, pos.y);
        p.translate(pp[0], pp[1]);
        p.rotate(-vel.heading());
        p.shape(shape);
    }
}
