package ac;

import processing.core.PApplet;

import static java.lang.Math.random;
import static processing.core.PApplet.constrain;

public class GOL {

    // Array de células
    int[] cells;
    // Dimensão de cada célula
    int cellDimension = 20;
    // Tamanho da grid
    int size;
    // Chance para geração de células iniciais
    double chance = 0.5;

    public GOL(PApplet p) {
        size = p.width / cellDimension;
        cells = new int[size * size];
        // Aleatóriamente atribui-se valor 1 (viva) ou 0 (morta) a cada célula
        // Pode-se alterar o valor "chance" sendo que com um valor mais elevado,
        // serão geradas menos células iniciais, e vice-versa
        for (int i = 0; i < cells.length; ++i) {
            if (random() < chance) {
                cells[i] = 1;
            } else {
                cells[i] = 0;
            }
        }
    }

    public void regrasGOL() {
        int[] next = cells.clone();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int n = numN(i, j);
                if (cells[pos(i, j)] == 1) {
                    // Se uma célula tiver menos que 2 ou mais que 3 vizinhos, morre
                    if (n < 2 || n > 3) {
                        next[pos(i, j)] = 0;  // Dead
                    }
                } else {
                    // Se uma célula tiver exatamente 3 vizinhos, torna-se viva
                    if (n == 3) {
                        next[pos(i, j)] = 1;
                    }
                }
            }
        }
        cells = next.clone();
    }

    public void clearScreen() {
        // Apaga a grid toda, ou seja, mata todas as células
        for (int i = 0; i < cells.length; i++) {
            cells[i] = 0;
        }
    }

    public int numN(int i, int j) {
        // Conta os vizinhos de cada célula
        int num = 0;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0) continue;
                int ni = i + x;
                int nj = j + y;
                num += cells[pos(ni, nj)];
            }
        }
        return num;
    }

    public int pos(int i, int j) {
        i = constrain(i, 0, size - 1);
        j = constrain(j, 0, size - 1);
        return i + j * size;
    }

    void setCell(int i, int j, int v) {
        cells[pos(i, j)] = v;
    }

    public void display(PApplet p) {
        // Desenha as células
        p.noStroke();
        p.fill(0);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (cells[pos(i, j)] == 1) {
                    p.rect(i * cellDimension, j * cellDimension, cellDimension, cellDimension);
                }
            }
        }

        // Desenha a grid
        p.stroke(150);
        for (int i = 0; i < size; i++) {
            p.line(i * cellDimension, 0, i * cellDimension, p.height);
            p.line(0, i * cellDimension, p.width, i * cellDimension);
        }
    }
}
