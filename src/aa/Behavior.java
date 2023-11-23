package aa;

public abstract class Behavior implements IBehavior{
    private float weight;

    public Behavior(float weight) {
        this.weight = weight;
    }

    @Override
    public PVector getDesiredVelocity(Boid me) {
        
        return null;
    }

    @Override
    public void setWeight(float weight) {
        this.weight = weight;
    }

    @Override
    public float getWeight() {
        return weight;
    }
}
