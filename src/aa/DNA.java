package aa;

public class DNA {
    public float maxSpeed;
    public float maxForce;
    public float visionDistance;
    public float visionSafeDistance;
    public float visionAngle;
    public float deltaTPursuit;
    public float radiusArrive;
    public float deltaTWander;
    public float radiusWander;
    public float deltaPhiWander;

    public DNA() {
        // Physics
        maxSpeed = random(3, 5);
        maxForce = random(4, 7);
        // Vision
        visionDistance = random(2, 2);
        visionSafeDistance = 0.25f * visionDistance;
        visionAngle = (float)Math.PI * 0.8f;
        // Pursuit
        deltaTPursuit = random(0.5f, 1f);
        // Arrive
        radiusArrive = random(3, 5);
        // Wander
        deltaTWander = random(1f, 1f);
        radiusWander = random(3f, 3f);
        deltaPhiWander = (float) Math.PI/8;
    }

    public DNA(DNA dna) {
        maxSpeed = dna.maxSpeed;
        maxForce = dna.maxForce;

        visionDistance = dna.visionDistance;
        visionSafeDistance = dna.visionSafeDistance;
        visionAngle = dna.visionAngle;

        deltaTPursuit = dna.deltaTPursuit;
        radiusArrive = dna.radiusArrive;

        deltaTWander = dna.deltaTWander;
        deltaPhiWander = dna.deltaPhiWander;
        radiusWander = dna.radiusWander;
    }

    public static float random(float min, float max) {
        return (float)(min +(max - min) * Math.random());
    }
}
