package physics;

public class Water extends Fluid {

    private float waterHeight;
    private int color;

    protected Water(float waterHeight, int color) {
        super(1000.0f);
        this.waterHeight = waterHeight;
        this.color = color;
    }
}
