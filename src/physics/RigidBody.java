package physics;

import processing.core.PApplet;
import processing.core.PVector;

public class RigidBody {

    public enum ControlType {
        POSITION,
        VELOCITY,
        FORCE
    }

    private PVector pos;
    private PVector vel;
    private PVector acc;
    private float mass;

    public RigidBody(float mass) {
        pos = new PVector();
        vel = new PVector();
        acc = new PVector();
        this.mass = mass;
    }

    public void move(float dt, ControlType ct) {
        
    }

    public void display(PApplet p) {
        p.circle(pos.x, pos.y, 30);
    }


}
