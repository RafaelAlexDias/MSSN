package aa;

import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class Flock {
    private List<Boid> boids;
    private int nboids;

    public Flock(int nboids, float mass, float radius, int color, float[] sacWeights,
                 PApplet p, SubPlot plt) {
        this.nboids = nboids;
        double[] w = plt.getWindow();
        boids = new ArrayList<Boid>();
        for (int i = 0; i < nboids; i++) {
            float x = p.random((float)w[0], (float)w[1]);
            float y = p.random((float)w[2], (float)w[3]);
            Boid b = new Boid(new PVector(x, y), mass, radius, color, p, plt);
            b.addBehavior(new Separate(sacWeights[0]));
            b.addBehavior(new Align(sacWeights[1]));
            b.addBehavior(new Cohesion(sacWeights[2]));
            b.addBehavior(new Flee(sacWeights[3]));
            boids.add(b);
        }

        List<Body> bodies = boidList2BodyList(boids);
        for (Boid b : boids) {
            b.setEye(new Eye(b, bodies));
        }

    }

    private List<Body> boidList2BodyList(List<Boid> boids) {
        List<Body> bodies = new ArrayList<Body>();
        for(Boid b : boids) {
            bodies.add(b);
        }
        return bodies;
    }

    public void applyBehavior(float dt) {
        for(Boid b : boids) {
            b.applyBehaviors(dt);
        }
    }

    public void followBoidControl(Boid boidControl, float velocity, float dt) {
        // Calcula a posição média dos boids
        PVector averagePosition = new PVector();
        for (Boid b : boids) {
            averagePosition.add(b.getPos());
        }
        averagePosition.div(boids.size());

        // Calcula a direção média dos boids
        PVector averageDirection = new PVector();
        for (Boid b : boids) {
            averageDirection.add(b.getVel());
        }
        averageDirection.div(boids.size());

        for (Boid b : boids) {
            // Calcula a direção para o boidControl
            PVector followDirection = PVector.sub(boidControl.getPos(), b.getPos());
            followDirection.normalize();

            // Aplica uma força na direção do boidControl
            followDirection.mult(velocity);  // Ajuste a velocidade conforme necessário
            b.applyForce(followDirection);

            // Adiciona comportamento de coesão (opcional)
            PVector cohesionForce = PVector.sub(averagePosition, b.getPos());
            cohesionForce.normalize();
            cohesionForce.mult(0.1f);  // Ajuste a intensidade conforme necessário
            b.applyForce(cohesionForce);

            // Adiciona comportamento de alinhamento (opcional)
            PVector alignmentForce = PVector.sub(averageDirection, b.getVel());
            alignmentForce.normalize();
            alignmentForce.mult(0.1f);  // Ajuste a intensidade conforme necessário
            b.applyForce(alignmentForce);
        }
    }

    public int getNBoids() {
        return this.nboids;
    }

    public Boid getBoid(int i) {
        return boids.get(i);
    }

    public void checkMouseClick(float mouseX, float mouseY, SubPlot plt) {
        for (Boid b : boids) {
            float[] pp = plt.getPixelCoord(b.getPos().x, b.getPos().y);
            if (mouseX >= pp[0] && mouseX <= pp[0] + b.getRadius() &&
                    mouseY >= pp[1] && mouseY <= pp[1] + b.getRadius()) {
                // Boid clicked! Implement explosion effect or any desired action here.
                boids.remove(b);
                break; // Assuming you want to explode only one boid on a click
            }
        }
    }


    public void display(PApplet p, SubPlot plt) {
        for (Boid b :boids) {
            b.display(p, plt);
        }
    }

}
