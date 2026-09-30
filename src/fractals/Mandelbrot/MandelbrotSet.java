package fractals.Mandelbrot;

import processing.core.PApplet;
import tools.Complex;
import tools.SubPlot;

public class MandelbrotSet {

    private int niter;
    private int x0, y0;
    private int dimx, dimy;
    private int[] palette;

    public MandelbrotSet(int niter, SubPlot plt) {
        this.niter = niter;
        float[] bb = plt.getBoundingBox();
        x0 = (int) bb[0];
        y0 = (int) bb[1];
        dimx = (int) bb[2];
        dimy = (int) bb[3];
        createPalette();
    }

    // Método para criar uma paleta de 8 cores
    private void createPalette() {
        palette = new int[8];
        palette[0] = 0xFF421E0F; // Castanho escuro
        palette[1] = 0xFF6A3403; // Castanho alaranjado
        palette[2] = 0xFF040449; // Azul médio
        palette[3] = 0xFF000764; // Azul claro
        palette[4] = 0xFF1852B1; // Azul escuro
        palette[5] = 0xFFF9C384; // Laranja claro
        palette[6] = 0xFFFFAA00; // Laranja escuro
        palette[7] = 0xFF19071A; // Roxo
    }

    public void display(PApplet p, SubPlot plt) {
        int tt = p.millis();
        p.loadPixels();
        for (int xx = x0; xx < x0 + dimx; xx++) {
            for (int yy = y0; yy < y0 + dimy; yy++) {
                double[] cc = plt.getWorldCoord(xx, yy);
                Complex c = new Complex(cc);
                Complex x = new Complex();
                int i;
                for (i = 0; i < niter; i++) {
                    x.mult(x).add(c);
                    if (x.norm() > 2)
                        break;
                }
                if (i == niter) {
                    p.pixels[yy * p.width + xx] = p.color(0);
                } else {
                    int colorIndex = i % palette.length;
                    p.pixels[yy * p.width + xx] = palette[colorIndex];
                }
            }
        }
        p.updatePixels();
    }
}
