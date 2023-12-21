package fractals;

import processing.core.PApplet;
import tools.SubPlot;

public class Turtle {
    private float len;
    private float angle;

    public Turtle(float len, float angle) {
        this.len = len;
        this.angle = angle;
    }

    public void setPose(double[] position, float orientation, PApplet p, SubPlot plt) {
        float[] pp = plt.getPixelCoord(position);
        p.translate(pp[0], pp[1]);
        p.rotate(-orientation);
    }

    public void scaling(float s) {
        len *= s;
    }

    public void render(LSystem lsys, PApplet p, SubPlot plt) {
        p.stroke(0);
        float[] lenPix = plt.getVectorCoord(len, len);
        for (int i = 0; i < lsys.getSequence().length(); i++) {
            char c = lsys.getSequence().charAt(i);
            if (c == 'F' || c == 'G') {
                p.line(0, 0, lenPix[0], 0);
                p.translate(lenPix[0], 0);
            }
            else if (c == 'f') p.translate(lenPix[0], 0);
            else if (c == '+') p.rotate(angle);
            else if (c == '-') p.rotate(-angle);
            else if (c == '[') p.pushMatrix();
            else if (c == ']') p.popMatrix();
        }
    }

}
