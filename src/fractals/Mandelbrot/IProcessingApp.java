package fractals.Mandelbrot;

import processing.core.PApplet;

public interface IProcessingApp
{
    void setup(PApplet parent);
    void draw(PApplet parent, float dt);
    void keyPressed(PApplet parent);
    void mousePressed(PApplet parent);
    void mouseDragged(PApplet parent);
    void mouseReleased(PApplet parent);
}