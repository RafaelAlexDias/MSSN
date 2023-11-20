package Sketch_TP0;

import processing.core.PApplet;

public class SketchCartoon implements setup.IProcessingApp {
    @Override
    public void setup(PApplet parent) {
        parent.fill(127, 127, 127);
        parent.strokeWeight(6);
    }

    @Override
    public void draw(PApplet parent, float dt) {

        parent.ellipse(248, 250, 400, 400);
        //parent.fill(0, 0, 0);
        parent.noFill();

        parent.bezier(294, 360, 356, 336, 386, 296, 400, 236);
        parent.noFill();
        parent.bezier(394, 182, 368, 208, 378, 232, 410, 234);
        parent.fill(0, 0, 0);

        // Direita
        parent.line(246, 164, 328, 132);
        parent.line(328, 132, 348, 160);
        parent.bezier(348, 160, 354, 194, 348, 216, 342, 234);
        parent.bezier(342, 234, 320, 246, 288, 244, 266, 234);
        parent.bezier(266, 234, 270, 236, 256, 218, 248, 192);

        // Esquerda
        parent.line(246, 164, 148, 146);
        parent.line(148, 146, 134, 172);
        parent.bezier(134, 182, 134, 198, 136, 214, 144, 234);
        parent.bezier(144, 234, 170, 246, 206, 242, 226, 234);
        parent.bezier(226, 234, 236, 220, 236, 210, 248, 192);

        // Meio
        parent.line(246, 164, 248, 192);


        parent.ellipse(288, 170, 40, 40);
        parent.fill(0, 0, 0);

        parent.ellipse(206, 174, 40, 40);
        parent.fill(253, 77, 77);

        parent.ellipse(280, 164, 24, 20);
        parent.ellipse(208, 168, 24, 20);


    }

    @Override
    public void keyPressed(PApplet parent) {
        throw new UnsupportedOperationException("keyPressed");
    }

    @Override
    public void mousePressed(PApplet parent) {
        //parent.fill(163, 73, 164);

    }
    @Override
    public void mouseDragged(PApplet parent) {
        parent.fill(163, 73, 164);
    }
}
