package aa;

import processing.core.PVector;

public class IBehavior {
    public PVector getDesiredVelocity(Boid me);
    public void setWeight(float weight);
    public float getWeight();
}
