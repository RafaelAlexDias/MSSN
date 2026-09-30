# MSSN — Modelação e Simulação de Sistemas Naturais

**ISEL — 3.º Semestre, 2023/2024 — Trabalho 3**

Simulações interativas em Java + [Processing](https://processing.org/).
João Ramos (50730) · Rafael Dias (50773) — Turma 31, Paulo Vieira.

---

## Requisitos

- **JDK 11+** (o projeto está configurado para `openjdk-27`)
- **Processing 4.3** — apenas a biblioteca `core.jar`

Não há `pom.xml` nem `gradle`; compila-se directamente no IntelliJ.

**Nota importante:** o `core.jar` não está no repositório e
`.idea/libraries/core.xml` aponta para um caminho que não existe neste
checkout. Depois de descarregar o Processing, adiciona a biblioteca em
*File → Project Structure → Libraries → + → Java* e aponta para
`<processing-4.3>/core/library/core.jar`.

---

## Como correr

Há dois pontos de entrada. Em ambos, escolhes a simulação editando a linha
`app = new ...` no método `main`:

| Ponto de entrada | Simulação por omissão |
|---|---|
| `setup.ProcessingSetup` | `ecosystem.EcosystemApp` |
| `fractals.Mandelbrot.ProcessingSetup` | `fractals.Mandelbrot.MandelbrotApp` |

O primeiro cobre `physics`, `aa`, `ac`, `ecosystem`, `chaos_game` e `dla`.
O segundo é só para `fractals`, porque essas classes implementam uma versão
da interface `IProcessingApp` **com** `mouseReleased` (de que o *zoom* do
Mandelbrot precisa).

A janela é fixada em `size(800, 700)` dentro de `settings()`.

---

## Módulos

| Pacote | O que é |
|---|---|
| `physics` | Motor 2D: `Mover` → `Body` → `RigidBody`/`Particle`; fluidos `Air`/`Water` |
| `tools` | `SubPlot` (mapeia coordenadas do mundo para painéis), `Histogram`, `Complex` |
| `ac` | Autómatos celulares: Jogo da Vida, regra 110, regra da maioria |
| `aa` | Autonomia: `Boid` + 11 comportamentos de *steering* (seek, flee, wander, pursue…) |
| `ecosystem` | **Trabalho principal.** Predadores e presas sobre um terreno que é um autómato celular |
| `fractals` | L-Systems (floresta, árvore com frutos) e o conjunto de Mandelbrot |
| `chaos_game` | Jogo do caos: triângulo, polígono de N lados, pentágono |
| `dla` | Agregação de tamanho limitado |
| `art` | Imagens usadas pelo ecossistema |

O `ecosystem` reutiliza quase tudo o que veio antes — `Terrain` estende
`MajorityCA`, `Patch` estende `MajorityCell` e `Animal` estende `Boid` — e
centraliza os parâmetros em `ecosystem/WorldConstants.java`.

---

## Controlos

| Simulação | Teclas |
|---|---|
| `EcosystemApp` | clique inicia · **espaço** pausa · **A** cria presa · **P** cria predador · botão reinicia |
| `SolarSystemApp` | **1** / **2** / **3** aproximam a vista |
| `ReynoldsTestApp` | **T** alterna *seek*/*flee* · clique (no painel inferior direito) move o alvo |
| `FlockWASDApp` | **W** / **A** / **S** / **D** |
| `BoidAceleradorTravaoApp` | **+** / **−** mudam a velocidade |
| `ControlGUIApp` | **P** / **V** / **F** escolhem controlo por posição, velocidade ou força |
| `SketchGOL` | **espaço** pausa · **D** alterna apagar/pintar · **C** limpa |
| `DLA` | **espaço** pausa · **W** / **S** mudam a velocidade |
| `ForestApp` | **1** / **2** / **3** trocam as regras · clique planta uma árvore |
| `MandelbrotApp` | arrastar e soltar faz *zoom* |
| `XSides_ChaosGame` | **5**–**9** mudam o número de lados |

As restantes não têm controlos.

---

## Problemas conhecidos

Notas da revisão do código, para referência:

- `ecosystem/Population.java:38` — a lista `allTrackingPreys` é criada dentro
  do ciclo de criação de presas, pelo que só guarda a última. Predadores
  adicionados à mão (tecla `P`) só perseguem uma presa.
- `TestCA` e `TestMajorityCA` lançam `NullPointerException` no primeiro frame,
  porque a arte do terreno nunca é definida fora do `EcosystemApp`.
- `ac/CellularAutomata.java:3` importa `ecosystem.WorldConstants` sem usar,
  invertendo a dependência entre camadas.
- Existem três cópias de `IProcessingApp`; `dla/IProcessingApp.java` não é
  usada.
- `MSSN.plantuml` está desatualizado face ao código actual.
