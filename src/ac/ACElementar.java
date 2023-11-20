package ac;

import processing.core.PApplet;

public class ACElementar {

    int[] cells;
    int cellDimension = 20;
    int generation = 0;

    // Rule 90
    // int[] ruleset = {0,1,0,1,1,0,1,0};

    // Rule 30
    // int[] ruleset = {0,0,0,1,1,1,1,0};

    // Rule 110
    int[] ruleset = {0,1,1,0,1,1,1,0};

    public ACElementar(PApplet p) {
        cells = new int[p.width / cellDimension];
        for(int i = 0; i != cells.length; ++i) {
            cells[i] = 0;
        }
        cells[cells.length / 2] = 1;
    }

    public void generate() {
        int[] nextGen = new int[cells.length];
        for(int i = 1; i != cells.length-1; ++i) {
            int left = cells[i-1];
            int me = cells[i];
            int right = cells[i+1];
            nextGen[i] = rules(left, me, right); // consultar a tabela
        }
        cells = nextGen;
        ++generation;
    }

    private int rules(int left, int me, int right) {
        if(left == 1 && me == 1 && right == 1) return ruleset[0];
        if(left == 1 && me == 1 && right == 0) return ruleset[1];
        if(left == 1 && me == 0 && right == 1) return ruleset[2];
        if(left == 1 && me == 0 && right == 0) return ruleset[3];
        if(left == 0 && me == 1 && right == 1) return ruleset[4];
        if(left == 0 && me == 1 && right == 0) return ruleset[5];
        if(left == 0 && me == 0 && right == 1) return ruleset[6];
        if(left == 0 && me == 0 && right == 0) return ruleset[7];
        return 0;
    }


    public void display(PApplet p) {
        for(int i = 0; i != cells.length; ++i) {
            if(cells[i] == 0) p.fill(255);
            else p.fill(0);
            p.stroke(0);
            p.rect(i * cellDimension, generation * cellDimension, cellDimension, cellDimension);
        }
    }
}