package ac;

import processing.core.PApplet;
import processing.core.PImage;

public class Cell {

    private int row, col;
    protected int state;
    private Cell[] neighbors;
    protected CellularAutomata ca;
    private PImage img;

    public Cell(CellularAutomata ca, int row, int col) {
        this.ca = ca;
        this.row = row;
        this.col = col;
        this.state = 0;
        this.neighbors = null;
    }

    public void setNeighbors(Cell[] neigh) {
        this.neighbors = neigh;
    }

    public Cell[] getNeighbors() {
        return neighbors;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return this.state;
    }

    public void setImg() {
        this.img = ca.p.loadImage(ca.getTerrainArt()[state]);
    }

    public void display(PApplet p) {
        p.pushStyle();
        p.noStroke();
        //p.fill(ca.getStateColors()[state]);
        if (img == null) {
            setImg();
        }
        p.image(img, ca.xmin+col*ca.cellWidth, ca.ymin+row*ca.cellHeight, ca.cellWidth, ca.cellHeight);
        //p.rect(ca.xmin+col*ca.cellWidth, ca.ymin+row*ca.cellHeight, ca.cellWidth, ca.cellHeight);
        p.popStyle();
    }

}
