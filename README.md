# MSSN — Modelação e Simulação de Sistemas Naturais

**ISEL — 3.º Semestre, ano letivo 2023/2024 — Trabalho 3**

Coleção de simulações interativas escritas em Java com [Processing](https://processing.org/),
desenvolvidas no âmbito da unidade curricular de MSSN.

| | |
|---|---|
| **Turma** | 31 — Paulo Vieira |
| **Autores** | João Ramos (n.º 50730), Rafael Dias (n.º 50773) |
| **Linguagem** | Java + Processing 4.3 |
| **IDE** | IntelliJ IDEA |

---

## Índice

- [Visão geral](#visão-geral)
- [Requisitos](#requisitos)
- [Configuração](#configuração)
- [Como executar](#como-executar)
- [Controlos](#controlos)
- [Arquitetura](#arquitetura)
- [Módulos](#módulos)
- [Problemas conhecidos](#problemas-conhecidos)

---

## Visão geral

O repositório está organizado em módulos independentes que partilham a mesma
base: um motor de física 2D (`physics`), um sistema de coordenadas de visualização
(`tools.SubPlot`) e um contrato de aplicações (`setup.IProcessingApp`).

Os exercícios desenvolvidos durante o trabalho vão de fractais e jogos do caos até
simulações multi-agente (boids, autonomia) e um ecossistema predador-presa
construído por cima de autómatos celulares.

Existem dois pontos de entrada — `setup.ProcessingSetup` e
`fractals.Mandelbrot.ProcessingSetup` — porque existem duas variantes da
interface `IProcessingApp` (ver [Arquitetura](#arquitetura)). Em ambos os
casos, a simulação a correr é escolhida editando a atribuição a `app` no
método `main`.

---

## Requisitos

| Requisito | Versão | Notas |
|---|---|---|
| JDK | `openjdk-27` (o que consta em `.idea/misc.xml`) | Qualquer JDK 11+ deve servir |
| Processing | **4.3** | Apenas a biblioteca `core.jar` é necessária |

Não existe `pom.xml`, `build.gradle` nem pasta `lib/`. O projeto é compilado
diretamente pelo IntelliJ e depende exclusivamente de `core.jar`.

---

## Configuração

O projeto depende de uma biblioteca externa (o `core.jar` do Processing) que
**não está versionada**. É preciso registá-la manualmente:

1. Descarregar o [Processing 4.3](https://processing.org/download).
2. No IntelliJ: `File` → `Project Structure…` → `Libraries` → `+` → `Java`.
3. Selecionar `<processing-4.3>/core/library/core.jar`.
4. Confirmar que a biblioteca está marcada como disponível no módulo `MSSN`
   (separador `Modules` → `Dependencies`).

> **Atenção:** `.idea/libraries/core.xml` contém um caminho absoluto fixo
> (`…/Desktop/LEIM/3º Semestre/MSSN/processing-4.3/core/library/core.jar`)
> que não corresponde a este checkout. É expectável ter de reapontar a
> biblioteca à instalação local do Processing. Ver
> [Problemas conhecidos](#problemas-conhecidos).

---

## Como executar

Existem **dois pontos de entrada**, porque existem duas variantes da interface
`IProcessingApp` (ver [Arquitetura](#arquitetura)):

### 1. `setup.ProcessingSetup` — aplicações principais

Cobre `physics`, `aa`, `ac`, `ecosystem`, `chaos_game` e `dla`.

Por omissão executa o ecossistema (`ecosystem.EcosystemApp`). Para correr
outra simulação, editar `src/setup/ProcessingSetup.java:39`:

```java
public static void main(String[] args) {
    app = new EcosystemApp();        // <-- mudar aqui
    PApplet.main(ProcessingSetup.class);
}
```

Depois: *Run* sobre `setup.ProcessingSetup`.

### 2. `fractals.Mandelbrot.ProcessingSetup` — fractais e L-Systems

As classes de `fractals` implementam a variante de `IProcessingApp` **com
`mouseReleased`**, que é a que o `MandelbrotApp` precisa para o *zoom* por
arrasto. Só podem ser executadas a partir deste `ProcessingSetup`.

Editar `src/fractals/Mandelbrot/ProcessingSetup.java:46`:

```java
public static void main(String[] args) {
    // app = new ForestApp();     // Exercício C-1
    // app = new FruitTreeApp();  // Exercício C-2
    app = new MandelbrotApp();   // Exercício D-1
    PApplet.main(ProcessingSetup.class);
}
```

> `fractals.LSystemApp` é a exceção: implementa `setup.IProcessingApp`, logo
> pode ser corrida a partir do ponto de entrada principal.

### Janela

Ambos os `ProcessingSetup` definem `size(800, 700)` em `settings()`. Para alterar
o tamanho, editar esse método.

---

## Controlos

### `ecosystem`

| App | Controlos |
|---|---|
| `EcosystemApp` | **Clique** para iniciar · **Espaço** pausa · **A** adiciona presa · **P** adiciona predador · **botão** no canto inferior direito reinicia |

### `aa` — autonomia / steering behaviours

| App | Controlos |
|---|---|
| `BoidApp` | **Clique** move o target. Aplica apenas o comportamento de índice `2` (`Wander`) |
| `BoidAceleradorTravaoApp` | **+** / **−** aumentam/diminuem `maxSpeed` · **Clique** move o target |
| `BoidWanderSeekApp` | Sem controlos; o target salta de posição quando o boid se aproxima |
| `FlockWASDApp` | **W** / **A** / **S** / **D** aplicam força ao boid nº 0 do flock |
| `FlockPredatorApp` | Sem controlos; o predador persegue o boid mais próximo |
| `DebuggingApp` | Sem controlos; realça a vermelho os boids no campo de visão do boid nº 4 |
| `ReynoldsTestApp` | **T** alterna `Seek`↔`Flee` no subplot 3 · **Clique** dentro do subplot 3 move o target |

### `ac` — autómatos celulares

| App | Controlos |
|---|---|
| `SketchGOL` | **Espaço** pausa · **D** alterna modo apagar/pintar · **C** limpa a grelha · **arrastar** pinta/apaga células |
| `SketchACElementar` | Sem controlos; regra 110 avança uma geração por frame |
| `TestCA` | **Clique** força os vizinhos da célula para o estado máximo |
| `TestMajorityCA` | **Clique** aplica a regra da maioria |

### `physics`

| App | Controlos |
|---|---|
| `FallingBodyApp` | Sem controlos; imprime no terminal o tempo de queda e pára a sketch |
| `ControlGUIApp` | **P** / **V** / **F** escolhem controlo por posição, velocidade ou força · **Clique** define o vetor |
| `ParticleSystemApp` | **Clique** cria um novo sistema de partículas · **X do rato** define o ângulo de emissão |
| `SolarSystemApp` | **1** / **2** / **3** aproximam a vista (órbita interna / Júpiter / sistema inteiro) |

### `fractals`

| App | Controlos |
|---|---|
| `MandelbrotApp` | **Arrastar** define um retângulo · **soltar** faz *zoom* para essa região |
| `ForestApp` | **1** / **2** / **3** trocam o conjunto de regras L-System · **Clique** planta uma árvore |
| `FruitTreeApp` | **Clique** avança uma geração |
| `LSystemApp` | **Clique** avança uma geração (imprime a sequência no terminal) |

### `chaos_game` e `dla`

| App | Controlos |
|---|---|
| `ChaosGame` | Sem controlos; triângulo de Sierpinski |
| `XSides_ChaosGame` | **5**–**9** mudam o número de lados e o fator de contração `R` |
| `Pentagon_ChaosGame` | Sem controlos; pentágono de Sierpinski (vértice anterior excluído) |
| `DLA` | **Espaço** pausa · **W** / **S** ajustam os passos por frame · **Clique** regenera os walkers |

> ⚠️ `SketchGOL.mouseDragged` usa deslocamentos de ecrã fixos
> (`-375`, `-110`) para compensar a barra de título da janela do Processing
> (`src/ac/SketchGOL.java:50`). A pintura fica desalinhada noutras
> resoluções ou em outro sistema operativo.

---

## Arquitetura

```
                    ┌────────────────────────┐
                    │   ProcessingSetup      │  extends PApplet
                    │   calcula o dt         │
                    └───────────┬────────────┘
                                │ delega eventos
                    ┌───────────▼────────────┐
                    │     IProcessingApp     │
                    └───────────┬────────────┘
                                │
        ┌───────────────────────┼───────────────────────┐
        │                       │                       │
┌───────▼────────┐   ┌──────────▼─────────┐   ┌─────────▼──────────┐
│    physics     │   │       tools        │   │         ac         │
│  Mover         │   │  SubPlot           │   │  CellularAutomata  │
│   └ Body       │   │  Histogram         │   │   └ Cell           │
│     ├ RigidBody│   │  TimeGraph         │   │  GOL               │
│     └ Particle │   │  Complex           │   │  MajorityCA        │
└───────┬────────┘   │  CustomRandomGen.  │   └─────────┬──────────┘
        │            └────────────────────┘             │
        │                       ▲                        │
        │            ┌──────────┴─────────┐              │
        └───────────►│        aa          │              │
                     │  Behavior → 11 ×   │              │
                     │  Boid, Flock, DNA, │              │
                     │  Eye               │              │
                     └──────────┬─────────┘              │
                                │                        │
                     ┌──────────▼────────────────────────▼───┐
                     │             ecosystem                  │
                     │  Animal → Prey / Predator             │
                     │  Population · Terrain · Patch         │
                     └────────────────────────────────────────┘
```

### O contrato `IProcessingApp`

Cada simulação implementa uma interface com cinco (ou seis) métodos e fica
totalmente desacoplada do `PApplet`. O `ProcessingSetup` calcula o `dt` entre
frames e encaminha os eventos — assim a mesma simulação corre sobre qualquer
sketch.

Existem **três** cópias desta interface no repositório:

| Ficheiro | Métodos | Usada por |
|---|---|---|
| `setup/IProcessingApp.java` | 5 (sem `mouseReleased`) | `physics`, `aa`, `ac`, `ecosystem`, `chaos_game`, `dla` |
| `fractals/Mandelbrot/IProcessingApp.java` | 6 (com `mouseReleased`) | `fractals` |
| `dla/IProcessingApp.java` | 5 (duplicata exacta) | `dla.DLA` (importa `setup.IProcessingApp`, esta cópia está sem uso) |

### `physics` — o motor

`Mover` é a base: `pos`, `vel`, `acc`, `mass`, com `applyForce`, `move(dt)` e
`attraction` (gravitação newtoniana, `G = 6.67e-11`). `Body` acrescenta `radius`,
`color` e `display`. `Boid` (em `aa`) herda de `Body` e acrescenta behaviours,
`DNA` e wrap-around da janela.

`Fluid`/`Air`/`Water` modelam arrasto quadrático; `RigidBody` é uma
implementação separada, mais simples, com controlo por **posição**, **velocidade**
ou **força** — usada para ilustrar a diferença entre os três modelos de
movimento.

### `tools.SubPlot` — coordenadas

É a peça que permite desenhar vários painéis na mesma janela. Mapeia uma
`window` do mundo (`{xmin, xmax, ymin, ymax}`) para um `viewport` normalizado
da janela (`{x0, y0, w, h}`):

```java
plt = new SubPlot(new double[]{-10, 10, -10, 10},
                  new float[]{0.02f, 0.51f, 0.96f, 0.47f},
                  parent.width, parent.height);
```

`ReynoldsTestApp` usa três `SubPlot` para mostrar três simulações em paralelo.
`getPixelCoord`/`getWorldCoord` convertem entre os dois espaços;
`isInside` permite restringir o rato a um painel.

---

## Módulos

| Pacote | Conteúdo |
|---|---|
| `setup` | `IProcessingApp` e `ProcessingSetup` — o ponto de entrada principal |
| `physics` | Motor 2D: `Mover`, `Body`, `RigidBody`, `Fluid`/`Air`/`Water`, `Particle`/`ParticleSystem`, `MotionControl`, `PSControl` + 4 apps |
| `tools` | `SubPlot` (mapeamento de coordenadas), `Histogram` (base da regra da maioria), `TimeGraph`, `Complex` (para o Mandelbrot), `CustomRandomGenerator` (amostragem por PMF) |
| `ac` | Autómatos celulares: `CellularAutomata`, `Cell`, `MajorityCA`/`MajorityCell`, `GOL` (Jogo da Vida com cor herdada dos vizinhos), `ACElementar` (regras 30/90/110) + 4 apps |
| `aa` | Autonomia: `Behavior` (base) e 11 comportamentos — `Seek`, `Flee`, `Wander`, `Pursuit`, `Arrive`, `Evade`, `Align`, `Cohesion`, `Separate`, `Brake`, `AvoidObstacle`; `Boid`, `Flock`, `DNA`, `Eye` + 7 apps |
| `ecosystem` | **Trabalho principal.** `Animal` (abstrata), `Prey`, `Predator`, `Patch`, `Terrain`, `Population`, `WorldConstants`, `EcosystemApp` |
| `fractals` | L-Systems (`LSystem`, `Rule`, `Turtle`, `TurtleFruit`, `Tree`) e o conjunto de Mandelbrot (`MandelbrotSet`) + 4 apps |
| `chaos_game` | Jogo do caos: triângulo, polígono de N lados, pentágono |
| `dla` | Agregação de tamanho limitado (`DLA`, `Walker`) |
| `art` | Imagens usadas pelo ecossistema (PNG e SVG) |

### Como o ecossistema reutiliza os restantes módulos

Este é o exercício mais completo e demonstra a arquitetura do trabalho:

```
Terrain  extends  MajorityCA   (autómato celular — o terreno é uma grelha)
Patch    extends  MajorityCell (cada célula sabe regenerar-se)
Animal   extends  Boid         (cada animal é um agente com comportamentos)
IAnimal  —  contrato: reproduce / energy_consumption / die
```

`EcosystemApp` cria o terreno com `PATCH_TYPE_PROB` (distribuição inicial),
aplica a regra da maioria duas vezes para suavizar, e depois gera a população.
Em cada frame: regenera o terreno, atualiza a população, e as presas comem
nas células `FOOD` enquanto os predadores perseguem e comem presas.

O terreno é também a fonte de obstáculos: `Terrain.getObstacles()` converte
cada célula `OBSTACLE` num `Body`, que passa a ser o `allTrackingBodies` do
`Eye` das presas — é assim que `AvoidObstacle` sabe o que evitar.

As crianças herdam o `DNA` e a lista de comportamentos do progenitor, sofrendo
mutação (`DNA.mutate` em `maxSpeed`; `Boid.mutateBehaviors` no peso de
`AvoidObstacle`). Os pesos de cada comportamento são somados e normalizados em
`Boid.applyBehaviors`, pelo que a seleção natural acaba por actuar
directamente sobre eles.

### `WorldConstants`

Todos os parâmetros da simulação estão centralizados em
`src/ecosystem/WorldConstants.java`:

| Grupo | Constantes |
|---|---|
| Mundo | `WINDOW` = `{-10, 10, -10, 10}` |
| Terreno | `NROWS`, `NCOLS`, enum `PatchType` (`EMPTY`, `OBSTACLE`, `FERTILE`, `FOOD`), `PATCH_TYPE_PROB`, `TERRAIN_COLORS`, `TERRAIN_ART`, `REGENERATION_TIME` |
| População | `INI_PREY_POPULATION` (5), `INI_PREDATOR_POPULATION` (1), `PREY_SIZE`, `PREY_MASS` |
| Energia | `INI_PREY_ENERGY` (10), `INI_PREDATOR_ENERGY` (15), `ENERGY_FROM_PLANT` (4), `ENERGY_FROM_PREY` (10), `PREY_ENERGY_TO_REPRODUCE` (25), `PREDATOR_ENERGY_TO_REPRODUCE` (35) |

Os caminhos em `TERRAIN_ART`, `PREY_ART` e `PREDATOR_ART` usam separadores
`\` do Windows e só funcionam quando a sketch é executada a partir da raiz do
projeto.

---

## Problemas conhecidos

Encontrados durante a revisão do código. Nenhum foi corrigido — estão
documentados para referência.

**Configuração**

- `.idea/libraries/core.xml` aponta para um caminho absoluto que não existe
  neste checkout. Quem clonar o repositório tem de reconfigurar a biblioteca.
- `out/` (compilados) está versionado no Git. Não há `.gitignore` na raiz.

**Código**

- `ac/CellularAutomata.java:3` importa `ecosystem.WorldConstants` sem o usar —
  e cria uma dependência inversa: o pacote `ac` (base) passa a depender do
  `ecosystem` (camada superior).
- `ac/Cell.display` chama `setImg()` quando `img == null`, e `setImg()` faz
  `ca.loadImage(ca.getTerrainArt()[state])`. Em `TestCA` e `TestMajorityCA` o
  array `art` nunca é preenchido, pelo que o acesso a `loadImage(null)` lança
  `NullPointerException` no primeiro frame. A arte do terreno só está definida
  no `EcosystemApp`.
- `ecosystem/Population.java:38` — `allTrackingPreys` é criado **dentro** do
  ciclo de criação de presas, pelo que termina a guardar apenas a última
  presa. O `Eye` do predador inicial fica limitado a essa presa. A lista é
  reconstruída corretamente em `nextTargetPredator`, mas `addPredator` (tecla
  `P`) continua a usar a lista obsoleta, pelo que um predador adicionado
  manualmente só persegue uma presa. `addPrey` também não regista a presa na
  lista.
- Existem três definições de `IProcessingApp` (ver [Arquitetura](#arquitetura));
  `dla/IProcessingApp.java` é uma duplicata sem uso.
- `EcosystemApp.draw` carrega `Title.png` em **todos** os frames, incluindo o
  loop da simulação. Deveria ser carregado uma vez em `setup()`.
- `ac/SketchGOL.java:50-51` — deslocamentos de ecrã fixos para a posição da
  janela (§ Controlos).
- `EcosystemApp.draw` usa o campo `parent.mousePressed` diretamente em vez do
  evento `mousePressed`, e o botão de reset não distingue o clique inicial do
  reinício.
- `physics/FallingBodyApp` chama `parent.noLoop()` quando a bola chega ao
  fundo — a sketch não pode ser retomada sem reiniciar.
- `EcosystemApp.setup()` é reutilizado como reset, mas não reinicia o estado
  `start`, o que funciona por acidente e não por desenho.

**Documentação**

- `MSSN.plantuml` está desatualizado: ainda lista `Sketch_TP0`, `chaos_game`,
  `dla` e `fractals` com relações e atributos que já não correspondem ao código.
  Foi gerado pelo [SketchIt!](https://bitbucket.org/pmesmeur/sketch.it).

---

## Autores

- **João Ramos** — n.º 50730
- **Rafael Dias** — n.º 50773

Turma 31 — Paulo Vieira
