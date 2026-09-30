package ac;

import processing.core.PApplet;
import processing.core.PVector;

import static java.lang.Math.random;
import static processing.core.PApplet.constrain;

public class GOL {

    // Array de células
    int[] cells;
    // Array de cores das células
    int[][] colors;
    // Dimensão de cada célula
    int cellDimension = 20;
    // Tamanho da grid
    int size;
    // Chance para geração de células iniciais
    double chance = 0.5;

    public GOL(PApplet p) {
        size = p.width / cellDimension;
        cells = new int[size * size];
        colors = new int[size * size][3];  // Array para armazenar RGB de cada célula
        // Aleatoriamente atribui-se valor 1 (viva) ou 0 (morta) a cada célula
        for (int i = 0; i < cells.length; ++i) {
            if (random() < chance) {
                cells[i] = 1;
                colors[i] = new int[]{(int) (random() * 255), (int) (random() * 255), (int) (random() * 255)};  // Cor aleatória
            } else {
                cells[i] = 0;
            }
        }
    }

    public void regrasGOL() {
        int[] next = cells.clone();
        int[][] nextColors = colors.clone();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int n = numN(i, j);

                if (cells[pos(i, j)] == 1) {
                    // Se uma célula tiver menos que 2 ou mais que 3 vizinhos, morre
                    if (n < 2 || n > 3) {
                        next[pos(i, j)] = 0;  // Morta
                    }
                } else {
                    // Se uma célula tiver exatamente 3 vizinhos, torna-se viva
                    if (n == 3) {
                        next[pos(i, j)] = 1;
                        // A nova célula herda a cor predominante dos vizinhos vivos
                        nextColors[pos(i, j)] = corPredominante(i, j);
                    }
                }
            }
        }
        cells = next.clone();
        colors = nextColors.clone();
    }

    public void clearScreen() {
        // Apaga a grid toda, ou seja, mata todas as células
        for (int i = 0; i < cells.length; i++) {
            cells[i] = 0;
            colors[i] = new int[]{0, 0, 0};  // Sem cor
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

    public int[] corPredominante(int i, int j) {
        // Conta as cores dos vizinhos vivos e retorna a cor predominante
        int[][] vizinhos = new int[8][3];  // Máximo de 8 vizinhos
        int contador = 0;

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0) continue;
                int ni = i + x;
                int nj = j + y;
                if (cells[pos(ni, nj)] == 1) {
                    vizinhos[contador++] = colors[pos(ni, nj)];
                }
            }
        }

        if (contador == 0) {
            return new int[]{255, 255, 255};  // Cor padrão branca se não houver vizinhos
        }

        // Calcula a cor média entre os vizinhos
        int[] corMedia = new int[3];
        for (int k = 0; k < contador; k++) {
            corMedia[0] += vizinhos[k][0];
            corMedia[1] += vizinhos[k][1];
            corMedia[2] += vizinhos[k][2];
        }

        corMedia[0] /= contador;
        corMedia[1] /= contador;
        corMedia[2] /= contador;

        return corMedia;
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
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (cells[pos(i, j)] == 1) {
                    p.fill(colors[pos(i, j)][0], colors[pos(i, j)][1], colors[pos(i, j)][2]);
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
