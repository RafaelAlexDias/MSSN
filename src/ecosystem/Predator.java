package ecosystem;

import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

import java.util.List;

public class Predator extends Animal {
    private PApplet parent;
    private SubPlot plt;
    private float eatRange = 1f;

    public Predator(PVector pos, float mass, float radius, String shape, PApplet parent, SubPlot plt) {
        super(pos, mass, radius, shape, parent, plt);
        this.parent = parent;
        this.plt = plt;
        energy = WorldConstants.INI_PREDATOR_ENERGY;
    }

    public Predator(Predator predator, boolean mutate, PApplet parent, SubPlot plt) {
        super(predator, mutate, parent, plt);
        this.parent = parent;
        this.plt = plt;
        energy = WorldConstants.INI_PREDATOR_ENERGY;
    }

    @Override
    public void eat(Terrain terrain) {
        Body boid = eye.getClosestBoid();
        this.eye.setTarget(boid);

        if (boid != null) { // Verifica se boid não é nulo
            PVector boidPos = boid.getPos();
            if (PVector.dist(this.pos, boidPos) < eatRange) {
                this.energy += WorldConstants.ENERGY_FROM_PREY;
                List<Body> preyList = this.eye.getAllTrackingBodies();
                preyList.remove(boid);
                this.eye.setAllTrackingBodies(preyList);
            }
        }
    }


    @Override
    public Animal reproduce(boolean mutate) {
        Animal child = null;
        if (energy > WorldConstants.PREDATOR_ENERGY_TO_REPRODUCE) {
            energy -= WorldConstants.INI_PREDATOR_ENERGY;
            child = new Predator(this, mutate, parent, plt);
            if (mutate) {
                child.mutateBehaviors();
            }
        }
        return child;
    }
}
