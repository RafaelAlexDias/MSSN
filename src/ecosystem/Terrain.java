package ecosystem;

import ac.MajorityCA;
import processing.core.PApplet;
import tools.SubPlot;

public class Terrain extends MajorityCA {
    public Terrain(PApplet p, SubPlot plt) {
        super(p, plt, WorldConstants.NROWS, WorldConstants.NCOLS, WorldConstants.NSTATES, 1);
    }

    @Override
    protected void createCells() {
        int minRT = (int) (WorldConstants.REGENERATION_TIME[0]*1000);
        int maxRT = (int) (WorldConstants.REGENERATION_TIME[1]*1000);
        for (int i=0; i<nrows; i++) {
            for (int j=0; j<ncols; j++) {
                int timeToGrow = (int) (minRT + (maxRT-minRT)*Math.random());
                cells[i][j] = new Patch(this, i, j, timeToGrow);
            }
        }
        setMooreNeighbors();
    }

    public void regenerate() {
        for (int i=0; i<nrows; i++) {
            for (int j=0; j<ncols; j++) {
                ((Patch)cells[i][j]).regenerate();
            }
        }
    }
}
