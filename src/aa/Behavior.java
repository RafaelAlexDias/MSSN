package aa;

import processing.core.PVector;

public abstract class Behavior {
    private float weight;

    public Behavior(float weight) {
        this.weight = weight;
    }

    public PVector getDesiredVelocity(Boid me) {
        return null;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getWeight() {
        return weight;
    }
}
