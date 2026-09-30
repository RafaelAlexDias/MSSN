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
