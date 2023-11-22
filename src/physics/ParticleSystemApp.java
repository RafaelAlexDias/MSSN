package physics;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class ParticleSystemApp implements IProcessingApp {

    private List<ParticleSystem> pss;
    private double[] window = {-10, 10, -10, 10};
    private float[] viewport = {0, 0, 1, 1};
    private SubPlot plt;
    private float[] velControl = {PApplet.radians(90), PApplet.radians(20), 1, 3};

    @Override
    public void setup(PApplet parent) {
        plt = new SubPlot(window, viewport, parent.width, parent.height);
        pss = new ArrayList<ParticleSystem>();
    }

    @Override
    public void draw(PApplet parent, float dt) {
        parent.background(255);

        for (ParticleSystem ps : pss) {
            ps.applyForce(new PVector(0, 0));
        }

        for (ParticleSystem ps : pss) {
            ps.move(dt);
            ps.display(parent, plt);
        }

        velControl[0] = PApplet.map(parent.mouseX, 0, parent.width,
                PApplet.radians(0), PApplet.radians(360));

    }

    @Override
    public void keyPressed(PApplet parent) {
    }

    @Override
    public void mousePressed(PApplet parent) {
        System.out.println("u");
        double[] ww = plt.getWorldCoord(parent.mouseX, parent.mouseY);

        int cor = parent.color(parent.random(255), parent.random(255), parent.random(255));
        float lifespan = parent.random(1, 3);

        PSControl psc = new PSControl(velControl);
        ParticleSystem ps = new ParticleSystem(new PVector((float)ww[0], (float)ww[1]), new PVector() , 1f, 0.2f,
                cor, lifespan, psc);
        pss.add(ps);
        System.out.println("deu");
    }

    @Override
    public void mouseDragged(PApplet parent) {

    }
}
