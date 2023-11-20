package physics;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;

public class ControlGUIApp implements IProcessingApp {

    private RigidBody rb;
    private MotionControl mc;
    private float mass = 1f;
    private RigidBody.ControlType ct = RigidBody.ControlType.POSITION;

    @Override
    public void setup(PApplet parent) {
        rb = new RigidBody(mass);
        mc = new MotionControl(ct, rb);
    }

    @Override
    public void draw(PApplet parent, float dt) {
        parent.background(255);
        parent.translate(parent.width/2, parent.height/2);
        rb.move(dt, ct);
        rb.display(parent);
        mc.display(parent);
    }

    @Override
    public void keyPressed(PApplet parent) {
        if (parent.key == 'p') {
            ct = RigidBody.ControlType.POSITION;
        }
        if (parent.key == 'v') {
            ct = RigidBody.ControlType.VELOCITY;
        }
        if (parent.key == 'f') {
            ct = RigidBody.ControlType.FORCE;
        }
        rb = new RigidBody(mass);
        mc = new MotionControl(ct, rb);
    }

    @Override
    public void mousePressed(PApplet parent) {
        float x = parent.mouseX - parent.width/2;
        float y = parent.mouseY - parent.height/2;
        mc.setVector(new PVector(x, y));
    }

    @Override
    public void mouseDragged(PApplet parent) {

    }
}
