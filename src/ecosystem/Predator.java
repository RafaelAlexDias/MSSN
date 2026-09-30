package ecosystem;

import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

import java.util.List;

public class Predator extends Animal {
    private PApplet parent;
    private SubPlot plt;

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

    public void eat(Terrain terrain, Animal target){
        if (target != null) {
            if (!(target.isDead())) {
                float dist = PVector.dist(pos, target.getPos());
                if (dist < 3 * target.getRadius()) {
                    energy += WorldConstants.ENERGY_FROM_PREY;
                    target.setDead(true);
                }
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