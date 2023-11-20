package physics;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

public class SolarSystemApp implements IProcessingApp {

    // Distances from planet to Sun {Mercúrio, Vénus, Terra, Marte, Júpiter, Saturno, Urano, Neptuno}
    private float[] distancesToSun = {5.791e10f, 1.082e11f, 1.496e11f, 2.22794e11f, 7.7833e11f, 1.4294e12f, 2.87099e12f,
                                        4.5043e12f};

    // Planet's Orbit Speed {Mercúrio, Vénus, Terra, Marte, Júpiter, Saturno, Urano, Neptuno}
    private float[] planetSpeed = {4.787e4f, 3.502e4f, 3e4f, 2.413e4f, 1.307e4f, 9.67e3f, 6.81e3f, 5.45e3f};

    // Planet's Mass {Mercúrio, Vénus, Terra, Marte, Júpiter, Saturno, Urano, Neptuno}
    private float[] planetMass = {3.303e23f, 4.869e24f, 5.97e24f, 6.421e24f, 1.9e27f, 5.688e26f, 8.686e25f, 1.024e26f};


    // Dados para o Sol
    private float sunMass = 1.989e30f;

    private float distEarthSun = 1.496e11f;

    // Viewport e Window
    // Para o tamanho da window, usámos a maior distância de um planeta ao sol e multiplicámos por 1.2
    // Neste caso o valor utilizado, é a distânica de Neptuno ao Sol
    private float distWindow = 4.5043e12f;
    private float[] viewport = {0, 0, 1, 1};
    private double[] window = {-1.2 * distWindow, 1.2 * distWindow, -1.2 * distWindow, 1.2 * distWindow};

    private SubPlot plt;
    private CelestialBody sun, mercury, venus, earth, mars, jupiter, saturn, uranus, neptune;

    // Cada segundo corresponde a um mês na simulação
    private float speedUp = 60 * 60 * 24 * 30;

    @Override
    public void setup(PApplet parent) {
        plt = new SubPlot(window, viewport, parent.width, parent.height);
        // Setup dos Corpos Celestiais
        sun = new CelestialBody(new PVector(), new PVector(), sunMass, distEarthSun/10, parent.color(255, 128, 0));
        mercury = new CelestialBody(new PVector(0, distancesToSun[0]), new PVector(planetSpeed[0], 0),
                planetMass[0], distEarthSun/50, parent.color(119, 115, 114));
        venus = new CelestialBody(new PVector(0, distancesToSun[1]), new PVector(planetSpeed[1], 0),
                planetMass[1], distEarthSun/30, parent.color(194, 100, 0));
        earth = new CelestialBody(new PVector(0, distancesToSun[2]), new PVector(planetSpeed[2], 0),
                planetMass[2], distEarthSun/20, parent.color(0, 180, 120));
        mars = new CelestialBody(new PVector(0, distancesToSun[3]), new PVector(planetSpeed[3], 0),
                planetMass[3], distEarthSun/18, parent.color(253, 134, 94));
        jupiter = new CelestialBody(new PVector(0, distancesToSun[4]), new PVector(planetSpeed[4], 0),
                planetMass[4], distEarthSun*1.1f, parent.color(163, 117, 93));
        saturn = new CelestialBody(new PVector(0, distancesToSun[5]), new PVector(planetSpeed[5], 0),
                planetMass[5], distEarthSun*0.9f, parent.color(219, 188, 121));
        uranus = new CelestialBody(new PVector(0, distancesToSun[6]), new PVector(planetSpeed[6], 0),
                planetMass[6], distEarthSun*0.5f, parent.color(80, 175, 220));
        neptune = new CelestialBody(new PVector(0, distancesToSun[7]), new PVector(planetSpeed[7], 0),
                planetMass[7], distEarthSun*0.6f, parent.color(64, 101, 251));
    }

    @Override
    public void draw(PApplet parent, float dt) {
        float[] pp = plt.getBoundingBox();
        parent.fill(255, 32);
        parent.rect(pp[0], pp[1], pp[2], pp[3]);

        // Display Sol
        sun.display(parent, plt);

        // Display Mercúrio
        PVector fMercury = sun.attraction(mercury);
        mercury.applyForce(fMercury);
        mercury.move(dt * speedUp);
        mercury.display(parent, plt);

        // Display Vénus
        PVector fVenus = sun.attraction(venus);
        venus.applyForce(fVenus);
        venus.move(dt * speedUp);
        venus.display(parent, plt);

        // Display Terra
        PVector fEarth = sun.attraction(earth);
        earth.applyForce(fEarth);
        earth.move(dt * speedUp);
        earth.display(parent, plt);

        // Display Marte
        PVector fMars = sun.attraction(mars);
        mars.applyForce(fMars);
        mars.move(dt * speedUp);
        mars.display(parent, plt);

        // Display Jupiter
        PVector fJupiter = sun.attraction(jupiter);
        jupiter.applyForce(fJupiter);
        jupiter.move(dt * speedUp);
        jupiter.display(parent, plt);

        // Display Saturno
        PVector fSaturn = sun.attraction(saturn);
        saturn.applyForce(fSaturn);
        saturn.move(dt * speedUp);
        saturn.display(parent, plt);

        // Display Urano
        PVector fUranus = sun.attraction(uranus);
        uranus.applyForce(fUranus);
        uranus.move(dt * speedUp);
        uranus.display(parent, plt);

        // Display Neptune
        PVector fNeptune = sun.attraction(neptune);
        neptune.applyForce(fNeptune);
        neptune.move(dt * speedUp);
        neptune.display(parent, plt);
    }

    @Override
    public void keyPressed(PApplet parent) {

    }

    @Override
    public void mousePressed(PApplet parent) {

    }

    @Override
    public void mouseDragged(PApplet parent) {

    }
}
