package ecosystem;

import aa.*;
import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class Population {

    private List<Animal> allPreys, allPredators;
    private double[] window;
    private boolean mutate = true;
    private List<Body> allTrackingPreys;

    public Population(PApplet parent, SubPlot plt, Terrain terrain) {
        window = plt.getWindow();
        allPreys = new ArrayList<Animal>();
        allPredators = new ArrayList<Animal>();

        List<Body> obstacles = terrain.getObstacles();

        for(int i=0; i<WorldConstants.INI_PREY_POPULATION; i++) {
            PVector pos = new PVector(parent.random((float)window[0], (float)window[1]),
                    parent.random((float)window[2], (float)window[3]));
            Animal prey = new Prey(pos, WorldConstants.PREY_MASS, WorldConstants.PREY_SIZE,
                    WorldConstants.PREY_ART, parent, plt);
            prey.addBehavior(new Wander(1));
            prey.addBehavior(new AvoidObstacle(0));
            Eye eye = new Eye(prey, obstacles);
            allTrackingPreys = new ArrayList<Body>();
            allTrackingPreys.add(prey);
            prey.setEye(eye);
            allPreys.add(prey);
        }

        for(int i=0; i<WorldConstants.INI_PREDATOR_POPULATION; i++) {
            PVector pos = new PVector(parent.random((float)window[0], (float)window[1]),
                    parent.random((float)window[2], (float)window[3]));
            Animal predator = new Predator(pos, WorldConstants.PREY_MASS, WorldConstants.PREY_SIZE,
                    WorldConstants.PREDATOR_ART, parent, plt);
            predator.addBehavior(new Wander(1));
            //predator.addBehavior(new AvoidObstacle(0));
            predator.addBehavior(new Pursuit(1));
            Eye eye = new Eye(predator, allTrackingPreys);
            predator.setEye(eye);
            allPredators.add(predator);
        }
    }

    public void update(float dt, Terrain terrain) {
        move(terrain, dt);
        eat(terrain);
        energy_consumption(dt, terrain);
        reproduce(mutate);
        die();
    }

    private void move(Terrain terrain, float dt) {
        for(Animal prey : allPreys) {
            prey.applyBehaviors(dt);
        }
        for(Animal predator : allPredators) {
            predator.applyBehaviors(dt);
        }
    }

    private void eat(Terrain terrain) {
        for(Animal prey : allPreys) {
            prey.eat(terrain);
        }
        for(Animal predator : allPredators) {
            predator.eat(terrain);
        }
    }

    private void energy_consumption(float dt, Terrain terrain) {
        for(Animal prey : allPreys) {
            prey.energy_consumption(dt, terrain);
        }
        for(Animal predator : allPredators) {
            predator.energy_consumption(dt, terrain);
        }
    }

    private void die() {
        for(int i=allPreys.size()-1; i>=0; i--) {
            Animal prey = allPreys.get(i);
            if (prey.die()) {
                allPreys.remove(prey);
            }
        }
        for(int i=allPredators.size()-1; i>=0; i--) {
            Animal predator = allPredators.get(i);
            if (predator.die()) {
                allPredators.remove(predator);
            }
        }
    }

    private void reproduce(boolean mutate) {
        for(int i=allPreys.size()-1; i>=0; i--) {
            Animal prey = allPreys.get(i);
            Animal child = prey.reproduce(mutate);
            if (child != null) {
                allPreys.add(child);
            }
        }
        for(int i=allPredators.size()-1; i>=0; i--) {
            Animal predator = allPredators.get(i);
            Animal child = predator.reproduce(mutate);
            if (child != null) {
                allPredators.add(child);
            }
        }
    }

    public void display(PApplet p, SubPlot plt) {
        for(Animal prey : allPreys) {
            prey.display(p, plt);
        }
        for(Animal predator : allPredators) {
            predator.display(p, plt);
        }
    }

    public int getNumPreys() {
        return allPreys.size();
    }

    public int getNumPredator() {
        return allPredators.size();
    }

    public float getPreyMeanMaxSpeed() {
        float sum = 0;
        for(Animal a : allPreys) {
            sum += a.getDNA().maxSpeed;
        }
        return sum/allPreys.size();
    }

    public float getPreyStdMaxSpeed() {
        float mean = getPreyMeanMaxSpeed();
        float sum = 0;
        for(Animal a : allPreys) {
            sum += Math.pow(a.getDNA().maxSpeed - mean, 2);
        }
        return (float)Math.sqrt(sum/allPreys.size());
    }

    public float[] getPreyMeanWeights() {
        float[] sums = new float[2];
        for(Animal a : allPreys) {
            sums[0] += a.getBehaviors().get(0).getWeight();
            sums[1] += a.getBehaviors().get(1).getWeight();
        }
        sums[0] /= allPreys.size();
        sums[1] /= allPreys.size();
        return sums;
    }
}
