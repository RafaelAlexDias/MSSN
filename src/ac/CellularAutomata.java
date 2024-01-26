package ac;

import ecosystem.WorldConstants;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import tools.CustomRandomGenerator;
import tools.SubPlot;

import java.util.ArrayList;

public class CellularAutomata {

    protected int nrows;
    protected int ncols;
    protected int nStates;
    private int radiusNeigh;
    protected Cell[][] cells;
    private int[] colors;
    private String[] art;
    protected float cellWidth, cellHeight;
    protected float xmin, ymin;
    private SubPlot plt;
    protected PApplet p;

    public CellularAutomata(PApplet p, SubPlot plt, int nrows, int ncols, int nStates, int radiusNeigh) {
        this.p = p;
        this.nrows = nrows;
        this.ncols = ncols;
        this.nStates = nStates;
        this.radiusNeigh = radiusNeigh;
        cells = new Cell[nrows][ncols];
        colors = new int[nStates];
        art = new String[nStates];
        float[] bb = plt.getBoundingBox();
        xmin = bb[0];
        ymin = bb[1];
        cellWidth = bb[2]/ncols;
        cellHeight = bb[3]/nrows;
        this.plt = plt;
        createCells();
        setStateColors(p);
    }

    public void setStateColors(PApplet p) {
        for (int i=0; i<nStates; i++) {
            colors[i] = p.color(p.random(255), p.random(255), p.random(255));
        }
    }

    public void setStateColors(int[] colors) {
        this.colors = colors;
    }

    public int[] getStateColors() {
        return colors;
    }

    public void setTerrainArt(String[] art) {
        this.art = art;
    }

    public String[] getTerrainArt() {
        return art;
    }

    protected void createCells() {
        for (int i=0; i<nrows; i++) {
            for (int j=0; j<ncols; j++) {
                cells[i][j] = new Cell(this, i, j);
            }
        }
        setMooreNeighbors();
    }

    public void initRandom() {
        for (int i = 0; i<nrows; i++) {
            for (int j=0; j<ncols; j++) {
                cells[i][j].setState((int)(nStates*Math.random()));
            }
        }
    }

    public void initRandomCustom(double[] pmf) {
        CustomRandomGenerator crg = new CustomRandomGenerator(pmf);
        for (int i=0; i<nrows; i++) {
            for (int j=0; j<ncols; j++) {
                cells[i][j].setState(crg.getRandomClass());
            }
        }
    }

    public PVector getCenterCell(int row, int col) {
        float x = (col + 0.5f) * cellWidth;
        float y = (row + 0.5f) * cellHeight;
        double[] w = plt.getWorldCoord(x, y);
        return new PVector((float)w[0], (float)w[1]);
    }

    public Cell world2Cell(double x, double y) {
        float[] xy = plt.getPixelCoord(x, y);
        return pixel2Cell(xy[0], xy[1]);
    }

    public Cell pixel2Cell(float x, float y) {
        int row = (int)((y-ymin)/cellHeight);
        int col = (int)((x-xmin)/cellWidth);
        if (row >= nrows) row = nrows - 1;
        if (col >= ncols) col = ncols - 1;
        if (row < 0) row = 0;
        if (col < 0) col = 0;
        return cells[row][col];
    }

    protected void setMooreNeighbors() {
        int NN = (int) Math.pow(2*radiusNeigh+1, 2);
        for (int i=0; i<nrows; i++) {
            for (int j=0; j<ncols; j++) {
                Cell[] neigh = new Cell[NN];
                int n = 0;
                for(int ii=-radiusNeigh; ii<=radiusNeigh; ii++) {
                    int row = (i + ii + nrows) % nrows;
                    for(int jj=-radiusNeigh; jj<=radiusNeigh; jj++) {
                        int col = (j + jj + ncols) % ncols;
                        neigh[n++] = cells[row][col];
                    }
                }
                cells[i][j].setNeighbors(neigh);
            }
        }
    }

    public void display(PApplet p) {
        for (int i=0; i<nrows; i++) {
            for (int j=0; j<ncols; j++) {
                cells[i][j].display(p);
            }
        }
    }

}
