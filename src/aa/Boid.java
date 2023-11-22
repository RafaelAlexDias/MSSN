package aa;

import physics.Body;
import processing.core.PVector;

public class Boid extends Body {
    protected Boid(PVector pos, PVector acc, float mass, float radius, int color) {
        super(pos, acc, mass, radius, color);
    }

    public PVector seek(PVector target) {
        PVector vd = PVector.sub(target, pos);
        vd.normalize().mult(10);
        return  PVector.sub(vd, vel);
    }

}
