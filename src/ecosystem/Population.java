package ecosystem;

import aa.Wander;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class Population {

    private List<Animal> allAnimals;
    private double[] window;

    public Population(PApplet parent, SubPlot plt, Terrain terrain) {
        window = plt.getWindow();
        allAnimals = new ArrayList<Animal>();

        for(int i=0; i<WorldConstants.INI_PREY_POPULATION; i++) {
            PVector pos = new PVector(parent.random((float)window[0], (float)window[1]),
                    parent.random((float)window[2], (float)window[3]));
            int color = parent.color(
                    WorldConstants.PREY_COLOR[0],
                    WorldConstants.PREY_COLOR[1],
                    WorldConstants.PREY_COLOR[2]);
            Animal a = new Prey(pos, WorldConstants.PREY_MASS, WorldConstants.PREY_SIZE,
                    color, parent, plt);
            a.addBehavior(new Wander(1));
            allAnimals.add(a);
        }
    }

    public void update(float dt, Terrain terrain) {
        move(terrain, dt);
        eat(terrain);
        energy_consumption(dt, terrain);
        reproduce();
        die();
    }

    private void move(Terrain terrain, float dt) {
        for(Animal a : allAnimals) {
            a.applyBehaviors(dt);
        }
    }

    private void eat(Terrain terrain) {
        for(Animal a : allAnimals) {
            a.eat(terrain);
        }
    }

    private void energy_consumption(float dt, Terrain terrain) {
        for(Animal a : allAnimals) {
            a.energy_consumption(dt, terrain);
        }
    }

    private void die() {
        for(int i=allAnimals.size()-1; i>=0; i--) {
            Animal a = allAnimals.get(i);
            if (a.die()) {
                allAnimals.remove(a);
            }
        }
    }

    private void reproduce() {
        for(int i=allAnimals.size()-1; i>=0; i--) {
            Animal a = allAnimals.get(i);
            Animal child = a.reproduce();
            if (child != null) {
                allAnimals.add(child);
            }
        }
    }

    public void display(PApplet p, SubPlot plt) {
        for(Animal a : allAnimals) {
            a.display(p, plt);
        }
    }

    public int getNumAnimals() {
        return allAnimals.size();
    }
}
