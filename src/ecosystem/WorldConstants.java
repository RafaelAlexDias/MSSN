package ecosystem;

public class WorldConstants {

    // World
    public final static double[] WINDOW = {-10, 10, -10 , 10};

    // Terrain
    public final static int NROWS = 20; //45
    public final static int NCOLS = 20; //45
    public static enum PatchType {
        EMPTY, OBSTACLE, FERTILE, FOOD
    }
    public final static double[] PATCH_TYPE_PROB = {0.1f, 0.2f, 0.1f, 0.6f};
    public static int NSTATES = PatchType.values().length;
    public static int[][] TERRAIN_COLORS = {
            {200+50, 200, 60}, {160, 30, 70}, {200, 200, 60}, {40, 200, 20}
    };
    public static String[] TERRAIN_ART = {
            "art\\Empty.png", "art\\Water.png", "art\\WithoutGrass.png", "art\\Grass.png"
    };
    public final static float[] REGENERATION_TIME = {10.f, 20.f}; // seconds

    // Prey Population
    public final static float PREY_SIZE = 0.2f;
    public final static float PREY_MASS = 1f;
    public final static int INI_PREY_POPULATION = 5;
    public final static int INI_PREDATOR_POPULATION = 1;
    public final static float INI_PREY_ENERGY = 10f;
    public final static float INI_PREDATOR_ENERGY = 15f;
    public final static float ENERGY_FROM_PLANT = 4f;
    public final static float PREY_ENERGY_TO_REPRODUCE = 25f;
    public final static float PREDATOR_ENERGY_TO_REPRODUCE = 35f;
    public static String PREY_ART = "art\\Deer.svg";
    public static String PREDATOR_ART = "art\\Wolf.svg";
}
