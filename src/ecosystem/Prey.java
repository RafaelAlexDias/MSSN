package ecosystem;

import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class Prey extends Animal {

    private PApplet parent;
    private SubPlot plt;
    public Prey(PVector pos, float mass, float radius, int color, PApplet parent, SubPlot plt) {
        super(pos, mass, radius, color, parent, plt);
        this.parent = parent;
        this.plt = plt;
        energy = WorldConstants.INI_PREY_ENERGY;
    }

    public Prey(Prey prey, PApplet parent, SubPlot plt) {
        super(prey, parent, plt);
        this.parent = parent;
        this.plt = plt;
        energy = WorldConstants.INI_PREY_ENERGY;
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
    public Animal reproduce() {
        Animal child = null;
        if (energy > WorldConstants.PREY_ENERGY_TO_REPRODUCE) {
            energy -= WorldConstants.INI_PREY_ENERGY;
            child = new Prey(this, parent, plt);
        }
        return child;
    }
}
