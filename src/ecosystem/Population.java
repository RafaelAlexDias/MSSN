package ecosystem;

import aa.*;
import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class Population {

    private List<Prey> allPreys;
    private List<Predator> allPredators;
    private double[] window;
    private boolean mutate = true;
    private Animal target;
    private Animal targetAux;
    private List<Body> allTrackingPreys;

    public Population(PApplet parent, SubPlot plt, Terrain terrain) {
        window = plt.getWindow();
        allPreys = new ArrayList<Prey>();
        allPredators = new ArrayList<Predator>();

        List<Body> obstacles = terrain.getObstacles();

        for(int i=0; i<WorldConstants.INI_PREY_POPULATION; i++) {
            PVector pos = new PVector(parent.random((float)window[0], (float)window[1]),
                    parent.random((float)window[2], (float)window[3]));
            Prey prey = new Prey(pos, WorldConstants.PREY_MASS, WorldConstants.PREY_SIZE,
                    WorldConstants.PREY_ART, parent, plt);
            prey.addBehavior(new Wander(4));
            prey.addBehavior(new AvoidObstacle(0));
            prey.addBehavior(new Flee(0.1f));
            Eye eye = new Eye(prey, obstacles);
            allTrackingPreys = new ArrayList<Body>();
            allTrackingPreys.add(prey);
            prey.setEye(eye);
            allPreys.add(prey);
        }

        for(int i=0; i<WorldConstants.INI_PREDATOR_POPULATION; i++) {
            PVector pos = new PVector(parent.random((float)window[0], (float)window[1]),
                    parent.random((float)window[2], (float)window[3]));
            Predator predator = new Predator(pos, WorldConstants.PREY_MASS, WorldConstants.PREY_SIZE,
                    WorldConstants.PREDATOR_ART, parent, plt);
            predator.addBehavior(new Wander(1));
            predator.addBehavior(new Pursuit(6));
            Eye eye = new Eye(predator, allTrackingPreys);
            predator.setEye(eye);
            target = (Animal) predator.getEye().nextTarget();
            predator.setTarget(target);
            allPredators.add(predator);
        }
    }

    public void newTarget(Animal child) {
        List<Body> allTrackingBodies = new ArrayList<Body>();
        for (Prey a : allPreys){
            allTrackingBodies.add(a);
        }
        Eye eye = new Eye(child, allTrackingBodies);
        child.setEye(eye);
        target = (Animal) child.getEye().nextTarget();
        child.setTarget(target);
    }

    private void nextTargetPredator(Animal predator) {
        // Verifica se o predador já tem um target válido e se ele está morto
        if (predator.getTarget() != null && predator.getTarget().isDead()) {
            List<Body> allTrackingBodies = new ArrayList<Body>();
            for (Prey a : allPreys) {
                if (!a.isDead()) { // Adiciona apenas presas vivas
                    allTrackingBodies.add(a);
                }
            }
            Eye eye = new Eye(predator, allTrackingBodies);
            predator.setEye(eye);
            target = (Animal) predator.getEye().nextTarget();
            predator.setTarget(target);
        }

        // Se não houver target válido ou estiver fora de alcance
        if (predator.getTarget() == null ||
                PVector.sub(predator.getPos(), predator.getTarget().getPos()).mag() > 15f) {
            target = (Animal) predator.getEye().nextTarget();
            predator.setTarget(target);
        }
    }


    public void update(float dt, Terrain terrain, Animal target) {
        move(terrain, dt);
        for (Predator predator : allPredators) {
            if (getNumPreys() > 0) {
                nextTargetPredator(predator);
            } else {
                // Se não houver presas, apenas vagueia
                predator.applyBehavior(0, dt);
            }
        }
        eat(terrain, target);
        energy_consumption(dt, terrain);
        reproduce(mutate);
        die();
    }

    private void move(Terrain terrain, float dt) {
        for(Prey prey : allPreys) {
            prey.applyBehaviors(dt);
        }
        for(Predator predator : allPredators) {
            predator.applyBehaviors(dt);
        }
    }

    private void eat(Terrain terrain, Animal target) {
        for(Prey prey : allPreys) {
            prey.eat(terrain);
        }
        for(Predator predator : allPredators) {
            predator.eat(terrain, predator.getTarget());
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
            Prey prey = allPreys.get(i);
            Prey child = (Prey) prey.reproduce(mutate);
            if (child != null) {
                allPreys.add(child);
            }
        }
        for(int i=allPredators.size()-1; i>=0; i--) {
            Predator predator = allPredators.get(i);
            Predator child = (Predator) predator.reproduce(mutate);
            if (child != null) {
                allPredators.add(child);
                newTarget(child);
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

    public Animal getTarget() {
        return this.target;
    }

    public int getNumPreys() {
        return this.allPreys.size();
    }

    public int getNumPredator() {
        return this.allPredators.size();
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

    public void addPrey(PApplet parent, SubPlot plt, Terrain terrain) {
        PVector pos = new PVector(
                parent.random((float) window[0], (float) window[1]),
                parent.random((float) window[2], (float) window[3])
        );
        Prey prey = new Prey(pos, WorldConstants.PREY_MASS, WorldConstants.PREY_SIZE,
                WorldConstants.PREY_ART, parent, plt);
        prey.addBehavior(new Wander(2));
        prey.addBehavior(new AvoidObstacle(0));
        Eye eye = new Eye(prey, terrain.getObstacles());
        prey.setEye(eye);
        allPreys.add(prey);
    }


    public void addPredator(PApplet parent, SubPlot plt) {
        PVector pos = new PVector(
                parent.random((float) window[0], (float) window[1]),
                parent.random((float) window[2], (float) window[3])
        );
        Predator predator = new Predator(pos, WorldConstants.PREY_MASS, WorldConstants.PREY_SIZE,
                WorldConstants.PREDATOR_ART, parent, plt);
        predator.addBehavior(new Wander(1));
        predator.addBehavior(new AvoidObstacle(1));
        predator.addBehavior(new Seek(2));
        Eye eye = new Eye(predator, allTrackingPreys);
        predator.setEye(eye);
        allPredators.add(predator);
    }

}
