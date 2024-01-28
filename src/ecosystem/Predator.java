package ecosystem;

import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

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

    @Override
    public void eat(Terrain terrain) {
        Patch patch = (Patch)terrain.world2Cell(pos.x, pos.y);
        if (patch.getState() == WorldConstants.PatchType.FOOD.ordinal()) {
            energy += WorldConstants.ENERGY_FROM_PLANT;
            patch.setFertile();
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
