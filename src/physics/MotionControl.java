package physics;

import processing.core.PApplet;
import processing.core.PVector;

public class MotionControl {
    private RigidBody rb;
    private RigidBody.ControlType ct;
    private PVector vector;

    // Construtor do "MotionControl"
    public MotionControl(RigidBody.ControlType ct, RigidBody rb) {
        this.ct = ct;
        this.rb = rb;
        vector = new PVector();
    }

    // Método que permite aplicar a força de um certo vetor
    public void setVector(PVector vector) {
        this.vector = vector;
        switch(ct) {
            case POSITION:
                rb.setPos(vector);
                break;
            case VELOCITY:
                rb.setVel(vector);
                break;
            case FORCE:
                rb.applyForce(vector);
                break;
        }
    }

    // Método "display" do "MotionControl"
    public void display(PApplet p) {
        p.strokeWeight(1);
        p.line(-p.width/2, 0, p.width/2, 0);
        p.line(0, -p.height/2, 0, p.height/2);
        p.textSize(24);
        p.fill(0);
        p.text("Control by " + ct.toString(), -140, -280);

        p.strokeWeight(3);
        p.line(0, 0, vector.x, vector.y);
    }
}
