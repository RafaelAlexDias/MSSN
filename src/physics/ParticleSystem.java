package physics;

import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class ParticleSystem extends Body{

    private List<Particle> particles;
    private float lifetime;
    private PSControl psc;

    protected ParticleSystem(PVector pos, PVector vel, float mass,
                             float radius, int color, float lifetime,
                             PSControl psc) {
        super(pos, vel, mass, radius, color);
        this.lifetime = lifetime;
        this.psc = psc;
        this.particles = new ArrayList<Particle>();
    }

    @Override
    public void move(float dt) {
        super.move(dt);
        addParticle();
        for(int i = particles.size()-1; i>=0; i--) {
            Particle p = particles.get(i);
            p.move(dt);
            if (p.isDead()) {
                particles.remove(i);
            }
        }
    }

    private void addParticle() {
        Particle particle = new Particle(pos, psc.getRndVel(), radius, color, lifetime);
        particles.add(particle);
    }

    @Override
    public void display(PApplet p, SubPlot plt) {
        for (Particle particle : particles) {
            particle.display(p, plt);
        }
    }

}
