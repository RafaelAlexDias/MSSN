package chaos_game;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;

public class Hexagon_ChaosGame implements IProcessingApp {

    private float x, y;
    private ArrayList<PVector> points;
    private PApplet p;

    @Override
    public void setup(PApplet p) {
        this.p = p;
        initializeAndDrawInitialRandomPoint();
        initializeAndDrawInitialShape(); // agora, um hexágono maior
    }

    @Override
    public void draw(PApplet p, float dt) {
        // Para cada método de desenho, desenha cem pontos (propósito: desenhar mais rápido)
        for (int i = 0; i < 100; i++) {
            int r = (int) Math.floor(p.random(points.size()));

            // Mover 2/3 da distância do ponto atual para o ponto escolhido
            x = PApplet.lerp(x, points.get(r).x, 2.0f / 3.0f);
            y = PApplet.lerp(y, points.get(r).y, 2.0f / 3.0f);

            p.point(x, y);
        }
    }

    private void initializeAndDrawInitialRandomPoint() {
        PVector randomInitialPoint = new PVector(p.random(p.width), p.random(p.height));
        p.point(randomInitialPoint.x, randomInitialPoint.y);
    }

    private void initializeAndDrawInitialShape() {
        points = new ArrayList<>();
        float angleOff = PApplet.TWO_PI / 6; // ângulo de compensação para hexágonos
        float radius = 200; // raio do hexágono (maior)

        // Criar pontos para um hexágono maior
        for (int i = 0; i < 6; i++) {
            float angle = i * angleOff;
            float hx = p.width / 2 + radius * PApplet.cos(angle);
            float hy = p.height / 2 + radius * PApplet.sin(angle);
            points.add(new PVector(hx, hy));
        }

        // Desenhar os pontos do hexágono
        for (PVector p1 : points) {
            p.point(p1.x, p1.y);
        }
    }

    @Override
    public void mousePressed(PApplet p) {
        throw new UnsupportedOperationException("mousePressed event is not implemented!");
    }

    @Override
    public void mouseDragged(PApplet parent) {

    }

    @Override
    public void keyPressed(PApplet p) {
        throw new UnsupportedOperationException("keyPressed event is not implemented!");
    }
}
