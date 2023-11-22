package physics;

import processing.core.PVector;

public abstract class Mover {
    protected PVector pos;
    protected PVector acc;
    protected PVector vel;
    protected float mass;
    private static double G = 6.67e-11;

    protected Mover(PVector pos, PVector vel, float mass) {
        this.pos = pos.copy();
        this.vel = vel;
        this.mass = mass;
        acc = new PVector();
    }

    public PVector attraction(Mover m) {
        PVector r = PVector.sub(pos, m.pos);
        float dist = r.mag();
        float strenght = (float) (G * mass * m.mass / Math.pow(dist, 2));

        return r.normalize().mult(strenght);
    }

    public void applyForce(PVector force) {
        acc.add(PVector.div(force, mass));
    }

    public void move(float dt) {
        vel.add(acc.mult(dt));
        pos.add(PVector.mult(vel, dt));
        acc.mult(0);
    }

    public void setPos(PVector pos) {
        this.pos = pos;
    }

    public void setVel(PVector vel) {
        this.vel = vel;
    }

    public PVector getPos() {
        return pos;
    }

    public PVector getVel() {
        return vel;
    }
}
