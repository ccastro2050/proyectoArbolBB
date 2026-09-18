# Ejercicio de Clase: Sistema de Directorio con Árboles Binarios de Búsqueda (ABB)

**Asignatura:** Estructuras de Datos
**Profesor:** Carlos Arturo Castro Castro

---

## 1. Enunciado del Problema

Se requiere un sistema sencillo para gestionar un **Directorio de Extensiones Telefónicas**. El sistema debe permitir registrar una extensión (ID numérico) junto con el nombre de la oficina. Para garantizar búsquedas rápidas, los datos se organizan en un **Árbol Binario de Búsqueda (ABB)** bajo la regla: *valores menores a la izquierda, valores mayores a la derecha*.

---

## 2. Fundamentos de árboles

### 2.1 Qué es un árbol

Un **árbol** es una estructura de datos **jerárquica**: los datos no van uno detrás de otro, sino que se ramifican formando niveles de padres e hijos.

Las estructuras que ya conoces son **lineales**: en un arreglo o en una lista cada elemento tiene un anterior y un siguiente, y para buscar un dato hay que recorrerlos casi todos.

```
Estructura LINEAL (arreglo o lista):

  [20] -> [30] -> [40] -> [50] -> [60] -> [70] -> [80]

  Para hallar el 80 hay que pasar por los 7 elementos.


Estructura JERÁRQUICA (árbol):

            [50]
           /    \
       [30]      [70]
       /  \      /  \
   [20] [40]  [60]  [80]

  Para hallar el 80 basta con 3 comparaciones: 50 -> 70 -> 80.
```

Esa es la ventaja del árbol: en cada paso **descarta una parte completa** de los datos en lugar de revisarlos uno por uno.

Un árbol se define de manera **recursiva**: un árbol es un nodo (la raíz) del que cuelgan cero o más árboles más pequeños, llamados subárboles. Esta definición es la que explica por qué casi todo el código de árboles se escribe con recursión.

### 2.2 Partes de un árbol

```
                    [50]                <- Raíz (nivel 0)
                   /    \
                  /      \
              [30]        [70]          <- Nivel 1
              /  \        /  \
          [20]  [40]  [60]  [80]        <- Nivel 2 (todas hojas)

          |_______|
              ^
        30 es padre de 20 y 40
        20 y 40 son hermanos entre sí
```

| Término | Significado | En el ejemplo |
|---------|-------------|---------------|
| **Nodo** | Cada elemento que almacena un dato | `50`, `30`, `70`, `20`… |
| **Arista** | La conexión entre un padre y un hijo | La línea de `50` a `30` |
| **Raíz** | El nodo inicial, el único sin padre | `50` |
| **Padre** | Nodo del que cuelga otro | `30` es padre de `20` |
| **Hijo** | Nodo que cuelga de otro | `20` es hijo de `30` |
| **Hermanos** | Nodos que comparten el mismo padre | `20` y `40` |
| **Hoja** (o nodo terminal) | Nodo sin hijos | `20`, `40`, `60`, `80` |
| **Nodo interno** | Nodo que sí tiene hijos | `50`, `30`, `70` |
| **Ancestro** | Cualquier nodo por encima, en el camino a la raíz | `50` es ancestro de `20` |
| **Descendiente** | Cualquier nodo por debajo | `20` es descendiente de `50` |
| **Camino** | Secuencia de nodos de uno a otro | `50 → 30 → 20` |
| **Subárbol** | Un nodo junto con todos sus descendientes | `30` con `20` y `40` |
| **Bosque** | Un conjunto de varios árboles separados | — |

> **Regla clave:** un árbol con *n* nodos tiene exactamente *n − 1* aristas, y **desde la raíz hay un único camino hacia cada nodo**. Si hubiera dos caminos, o un ciclo, dejaría de ser un árbol.

### 2.3 Medidas de un árbol

| Medida | Definición | En el ejemplo |
|--------|------------|---------------|
| **Grado de un nodo** | Cuántos hijos tiene | `50` tiene grado 2; `20` tiene grado 0 |
| **Grado del árbol** | El mayor grado entre sus nodos | 2 (por eso es *binario*) |
| **Nivel** | Distancia desde la raíz; la raíz está en el nivel 0 | `30` está en el nivel 1 |
| **Profundidad de un nodo** | Número de aristas desde la raíz hasta él | La profundidad de `20` es 2 |
| **Altura de un nodo** | Número de aristas desde él hasta la hoja más lejana | La altura de `30` es 1 |
| **Altura del árbol** | La altura de la raíz | 2 (o 3 niveles) |
| **Tamaño** | Cantidad total de nodos | 7 |

> **Cuidado:** *profundidad* se mide hacia arriba (desde la raíz) y *altura* hacia abajo (hasta las hojas). Algunos libros cuentan la altura en niveles en vez de aristas; en este documento se usa la cuenta por aristas.

### 2.4 Tipos de árboles

```
ÁRBOL GENERAL (n-ario)          ÁRBOL BINARIO
cada nodo, cualquier            cada nodo, máximo
número de hijos                 dos hijos

        [A]                          [A]
      / | | \                       /   \
   [B][C][D][E]                  [B]     [C]
```

| Tipo | Regla que cumple |
|------|------------------|
| **Árbol general (n-ario)** | Cada nodo puede tener cualquier cantidad de hijos |
| **Árbol binario** | Cada nodo tiene **como máximo dos** hijos: izquierdo y derecho |
| **Árbol binario lleno** | Cada nodo tiene 0 o 2 hijos, nunca uno solo |
| **Árbol binario completo** | Todos los niveles están llenos, salvo quizá el último, que se llena de izquierda a derecha |
| **Árbol binario perfecto** | Todos los niveles completamente llenos (como el ejemplo de arriba) |
| **Árbol degenerado** | Cada nodo tiene un solo hijo: se comporta como una lista |
| **Árbol balanceado** | La diferencia de altura entre las ramas de cada nodo es como máximo 1 |
| **Árbol Binario de Búsqueda (ABB)** | Árbol binario **ordenado**: menores a la izquierda, mayores a la derecha ← *el de este proyecto* |

El caso degenerado importa porque es el peor escenario de un ABB:

```
ÁRBOL BALANCEADO              ÁRBOL DEGENERADO
(se insertó 50,30,70…)        (se insertó 20,30,40… ya ordenado)

        [50]                    [20]
       /    \                      \
   [30]      [70]                   [30]
                                       \
                                        [40]
                                           \
                                            [50]

Buscar: pocas comparaciones   Buscar: hay que recorrerlo entero
```

### 2.5 Para qué se usan los árboles

| Uso | Ejemplo cotidiano |
|-----|-------------------|
| Organizar jerarquías | Las carpetas y subcarpetas de tu computador |
| Búsquedas rápidas | Los índices de una base de datos |
| Representar documentos | El HTML de una página web (el DOM) |
| Tomar decisiones | Los árboles de decisión en inteligencia artificial |
| Comprimir datos | El algoritmo de Huffman |
| Procesar expresiones | Cómo un compilador entiende `(2 + 3) * 4` |

### 2.6 Qué es un Árbol Binario de Búsqueda (ABB)

Es el tipo de árbol que usa este proyecto: un árbol binario que además cumple una **regla de orden**:

> **Los valores menores van a la izquierda. Los valores mayores van a la derecha.**

Esa regla es la que hace rápida la búsqueda: funciona como buscar una palabra en un diccionario. No se revisan todas las páginas, sino que en cada comparación se descarta una mitad.

En este proyecto el valor que ordena el árbol es el **número de extensión** de cada oficina.

### 2.7 Construcción paso a paso

Así se arma el árbol de ejemplo insertando en este orden: **50, 30, 70, 20, 40, 60, 80**.

```
Paso 1: Insertar 50 (el árbol está vacío, así que es la raíz)
        [50]

Paso 2: Insertar 30 (30 < 50, va a la izquierda)
        [50]
        /
     [30]

Paso 3: Insertar 70 (70 > 50, va a la derecha)
        [50]
        /   \
     [30]  [70]

Paso 4: Insertar 20 (20 < 50, izquierda; 20 < 30, izquierda)
        [50]
        /   \
     [30]  [70]
     /
  [20]

Paso 5: Insertar 40 (40 < 50, izquierda; 40 > 30, derecha)
        [50]
        /   \
     [30]  [70]
     /  \
  [20] [40]

Paso 6: Insertar 60 (60 > 50, derecha; 60 < 70, izquierda)
        [50]
        /   \
     [30]  [70]
     /  \   /
  [20] [40][60]

Paso 7: Insertar 80 (80 > 50, derecha; 80 > 70, derecha)
        [50]
        /    \
     [30]    [70]
     /  \    /  \
  [20] [40][60] [80]
```

> **Ojo:** el orden de inserción cambia la forma del árbol. Si los mismos números se insertan ya ordenados (20, 30, 40, 50…), el árbol se degenera en una lista y pierde su ventaja de búsqueda.

### 2.8 Insertar

La idea, en palabras: se compara el ID nuevo con la raíz y se baja por una rama hasta encontrar un hueco.

- Si es **menor**, se baja por la rama izquierda.
- Si es **mayor**, se baja por la rama derecha.
- Cuando se llega a un espacio vacío (`null`), ahí se crea el nodo.
- Si el ID ya existe, no se hace nada (no se permiten duplicados).

#### 2.8.1 Cómo funciona `insertarRecursivo` por dentro

Este es el código:

```java
public void insertar(int id, String nombre) {
    raiz = insertarRecursivo(raiz, id, nombre);
}

private Oficina insertarRecursivo(Oficina actual, int id, String nombre) {
    if (actual == null) {                    // CASO BASE: hueco encontrado
        return new Oficina(id, nombre);      // aquí nace el nodo nuevo
    }

    if (id < actual.getId()) {               // menor -> rama izquierda
        actual.setIzquierdo(insertarRecursivo(actual.getIzquierdo(), id, nombre));
    }
    else if (id > actual.getId()) {          // mayor -> rama derecha
        actual.setDerecho(insertarRecursivo(actual.getDerecho(), id, nombre));
    }
    // si es igual, no hace nada: no se admiten duplicados

    return actual;                           // devuelve este nodo, ya actualizado
}
```

Lo que confunde de este método es que **hace dos cosas en momentos distintos**:

| Fase | Cuándo ocurre | Qué hace |
|------|---------------|----------|
| **Bajada** (ida) | Mientras las llamadas se van anidando | Compara y decide por cuál rama seguir, hasta llegar a un `null` |
| **Subida** (vuelta) | Cuando cada llamada termina y devuelve su nodo | Vuelve a enganchar cada rama con `setIzquierdo` / `setDerecho` |

La bajada **busca el sitio**. La subida **reconstruye el camino**. El `return actual` es lo que hace posible la segunda fase.

#### 2.8.2 Ejemplo trazado: insertar 40

Partimos de este árbol, que ya tiene tres nodos:

```
        [50]
        /   \
     [30]  [70]
```

Y ejecutamos `miArbol.insertar(40, "Ventas")`.

**Fase 1 — Bajada: cada llamada abre otra llamada dentro**

```
insertar(40, "Ventas")
│
└─ raiz = insertarRecursivo( [50], 40 )
   │   actual = [50].  ¿40 < 50?  Sí -> hay que ir a la izquierda
   │   [50].setIzquierdo( ... espera el resultado de la llamada de abajo ... )
   │
   └─ insertarRecursivo( [30], 40 )
      │   actual = [30].  ¿40 < 30?  No.  ¿40 > 30?  Sí -> a la derecha
      │   [30].setDerecho( ... espera el resultado de la llamada de abajo ... )
      │
      └─ insertarRecursivo( null, 40 )
             actual = null  -> CASO BASE
             crea [40] y lo devuelve
```

Fíjate en que las dos primeras llamadas quedaron **en pausa**: no pueden terminar hasta que la llamada de adentro les devuelva algo. Eso es lo que forma la *pila de llamadas*.

**Fase 2 — Subida: las llamadas terminan en orden inverso**

```
      └─ insertarRecursivo( null, 40 )  devuelve  [40]
                                                    │
      ┌─────────────────────────────────────────────┘
      │
      └─ [30].setDerecho( [40] )     <-- AQUÍ se engancha de verdad el nodo nuevo
         insertarRecursivo( [30], 40 )  devuelve  [30]
                                                    │
   ┌────────────────────────────────────────────────┘
   │
   └─ [50].setIzquierdo( [30] )      <-- reasigna lo que YA estaba (30 seguía siendo su hijo)
      insertarRecursivo( [50], 40 )  devuelve  [50]
                                                 │
┌────────────────────────────────────────────────┘
│
└─ raiz = [50]                       <-- reasigna la raíz (tampoco cambió, pero ver 2.8.3)
```

**Resultado:**

```
        [50]
        /   \
     [30]  [70]
        \
        [40]
```

> **La clave:** de los tres reenganches, **solo uno cambia algo de verdad** (`[30].setDerecho([40])`). Los otros dos reasignan el mismo valor que ya tenían. El método no necesita averiguar cuál es el importante: reasigna todo el camino de vuelta y así siempre acierta. Eso es lo que hace el código tan corto.

#### 2.8.3 El caso que lo explica todo: insertar en un árbol vacío

Aquí se ve por qué el método público hace `raiz = ...` y por qué el recursivo devuelve un `Oficina` en lugar de ser `void`.

Con el árbol vacío, `raiz` vale `null`:

```
insertar(50, "Gerencia")
│
└─ raiz = insertarRecursivo( null, 50 )
   │      actual = null -> CASO BASE de inmediato
   │      crea [50] y lo devuelve
   │
   └─ raiz = [50]        <-- sin esta línea, el nodo se perdería
```

En Java, `actual` es **una copia** de la referencia. Si dentro del método recursivo se escribiera `actual = new Oficina(...)`, se cambiaría la copia y `raiz` seguiría en `null`: el nodo se crearía y se perdería al terminar el método. **No se puede modificar un `null` desde adentro.** La única salida es devolver el nodo y que quien llamó lo guarde. Por eso:

- el método recursivo **devuelve** `Oficina` en vez de ser `void`;
- el método público **asigna** ese resultado a `raiz`;
- y en cada nivel intermedio el resultado se asigna con `setIzquierdo` / `setDerecho`.

#### 2.8.4 Resumen del algoritmo

```
insertarRecursivo(actual, id, nombre):

    1. Si actual es null:
           devolver un nodo nuevo con (id, nombre)      <- caso base

    2. Si id < actual.id:
           actual.izquierdo = insertarRecursivo(actual.izquierdo, id, nombre)

    3. Si id > actual.id:
           actual.derecho = insertarRecursivo(actual.derecho, id, nombre)

    4. Si id == actual.id:
           no hacer nada (duplicado)

    5. Devolver actual
```

`buscar` y `eliminar` siguen exactamente este mismo esquema: caso base, decidir una rama, recursión, y devolver. Lo único que cambia es qué se hace al llegar al nodo.

### 2.9 Buscar

Funciona igual que insertar, pero comparando:

- Si el ID es igual al del nodo actual → **encontrado**.
- Si es menor → se busca a la izquierda.
- Si es mayor → se busca a la derecha.
- Si se llega a `null` → **no existe**.

### 2.10 Los tres recorridos

Recorrer un árbol significa **visitar todos sus nodos** en un orden determinado. Hay tres recorridos, y lo único que cambia entre ellos es **en qué momento se visita la raíz** respecto a sus hijos.

Todos los ejemplos usan el árbol construido arriba:

```
        [50]
        /    \
     [30]    [70]
     /  \    /  \
  [20] [40][60] [80]
```

| Recorrido | Orden de visita | Resultado | Para qué sirve |
|-----------|-----------------|-----------|----------------|
| **Inorden** | Izquierda → Raíz → Derecha | 20, 30, 40, **50**, 60, 70, 80 | Ver los datos ordenados |
| **Preorden** | Raíz → Izquierda → Derecha | **50**, 30, 20, 40, 70, 60, 80 | Copiar o reconstruir el árbol |
| **Postorden** | Izquierda → Derecha → Raíz | 20, 40, 30, 60, 80, 70, **50** | Eliminar o liberar el árbol |

> **Truco para recordar:** el nombre indica **cuándo se visita la raíz**.
> **Pre**orden = raíz al principio · **In**orden = raíz intermedia · **Post**orden = raíz al final.

El **inorden** es el más útil en un ABB porque siempre devuelve los datos **ordenados de menor a mayor**.

Estos tres recorridos bajan por las ramas hasta el fondo antes de retroceder (recorrido **en profundidad**). Existe además el recorrido **por niveles** o *en anchura*, que visita el árbol fila por fila (`50, 30, 70, 20, 40, 60, 80`); no se usa en este proyecto porque necesita una cola en vez de recursión.

#### 2.10.1 Inorden paso a paso (Izquierda → Raíz → Derecha)

**Regla:** primero se visita todo lo de la izquierda, luego la raíz, luego todo lo de la derecha.

```
Empiezo en [50]:
  Antes de visitar 50, debo ir a la izquierda -> [30]
    Antes de visitar 30, debo ir a la izquierda -> [20]
      Izquierda de 20 -> null (no hay nada)
      Visito [20]   <- PRIMERO
      Derecha de 20 -> null
    Visito [30]     <- SEGUNDO
    Derecha de 30 -> [40]
      Izquierda de 40 -> null
      Visito [40]   <- TERCERO
      Derecha de 40 -> null
  Visito [50]       <- CUARTO
  Derecha de 50 -> [70]
    Izquierda de 70 -> [60]
      Izquierda de 60 -> null
      Visito [60]   <- QUINTO
      Derecha de 60 -> null
    Visito [70]     <- SEXTO
    Derecha de 70 -> [80]
      Izquierda de 80 -> null
      Visito [80]   <- SÉPTIMO
      Derecha de 80 -> null
```

**Resultado: 20, 30, 40, 50, 60, 70, 80** (ordenado)

```java
// Público: lo que llama Main. Arranca desde la raíz.
public void mostrarInorden() {
    mostrarInordenRecursivo(raiz);
}

// Privado y recursivo: el que baja por el árbol
private void mostrarInordenRecursivo(Oficina nodo) {
    if (nodo != null) {                              // caso base: null, no hace nada
        mostrarInordenRecursivo(nodo.getIzquierdo());  // 1. Izquierda
        imprimir(nodo);                                // 2. Raíz (visitar)
        mostrarInordenRecursivo(nodo.getDerecho());    // 3. Derecha
    }
}
```

#### 2.10.2 Preorden paso a paso (Raíz → Izquierda → Derecha)

**Regla:** primero se visita la raíz, luego todo lo de la izquierda, luego todo lo de la derecha.

```
Llego a [50]:
  Visito [50]       <- PRIMERO
  Izquierda -> [30]
    Visito [30]     <- SEGUNDO
    Izquierda -> [20]
      Visito [20]   <- TERCERO
      Izquierda de 20 -> null
      Derecha de 20 -> null
    Derecha de 30 -> [40]
      Visito [40]   <- CUARTO
      Izquierda de 40 -> null
      Derecha de 40 -> null
  Derecha de 50 -> [70]
    Visito [70]     <- QUINTO
    Izquierda -> [60]
      Visito [60]   <- SEXTO
      Izquierda de 60 -> null
      Derecha de 60 -> null
    Derecha de 70 -> [80]
      Visito [80]   <- SÉPTIMO
      Izquierda de 80 -> null
      Derecha de 80 -> null
```

**Resultado: 50, 30, 20, 40, 70, 60, 80** (la raíz primero)

```java
public void mostrarPreorden() {
    mostrarPreordenRecursivo(raiz);
}

private void mostrarPreordenRecursivo(Oficina nodo) {
    if (nodo != null) {
        imprimir(nodo);                                 // 1. Raíz (visitar)
        mostrarPreordenRecursivo(nodo.getIzquierdo());  // 2. Izquierda
        mostrarPreordenRecursivo(nodo.getDerecho());    // 3. Derecha
    }
}
```

#### 2.10.3 Postorden paso a paso (Izquierda → Derecha → Raíz)

**Regla:** primero se visita todo lo de la izquierda, luego todo lo de la derecha, y al final la raíz.

```
Llego a [50]:
  Izquierda -> [30]
    Izquierda -> [20]
      Izquierda de 20 -> null
      Derecha de 20 -> null
      Visito [20]   <- PRIMERO
    Derecha de 30 -> [40]
      Izquierda de 40 -> null
      Derecha de 40 -> null
      Visito [40]   <- SEGUNDO
    Visito [30]     <- TERCERO
  Derecha de 50 -> [70]
    Izquierda -> [60]
      Izquierda de 60 -> null
      Derecha de 60 -> null
      Visito [60]   <- CUARTO
    Derecha de 70 -> [80]
      Izquierda de 80 -> null
      Derecha de 80 -> null
      Visito [80]   <- QUINTO
    Visito [70]     <- SEXTO
  Visito [50]       <- SÉPTIMO (la raíz es la última)
```

**Resultado: 20, 40, 30, 60, 80, 70, 50** (la raíz al final)

```java
public void mostrarPostorden() {
    mostrarPostordenRecursivo(raiz);
}

private void mostrarPostordenRecursivo(Oficina nodo) {
    if (nodo != null) {
        mostrarPostordenRecursivo(nodo.getIzquierdo());  // 1. Izquierda
        mostrarPostordenRecursivo(nodo.getDerecho());    // 2. Derecha
        imprimir(nodo);                                  // 3. Raíz (visitar)
    }
}
```

### 2.11 Eliminar: los tres casos

Eliminar es la operación más delicada, porque el árbol debe seguir cumpliendo la regla de orden. Hay tres situaciones:

**Caso 1 — El nodo es una hoja (sin hijos).**
Se elimina directamente; basta con poner `null` en su lugar.

```
     30              30
    /  \     →      /
  20    40        20        (se elimina 40)
```

**Caso 2 — El nodo tiene un solo hijo.**
El hijo sube y ocupa el lugar del nodo eliminado.

```
     30              30
    /  \     →      /  \
  20    40        20    50   (se elimina 40, sube 50)
          \
           50
```

**Caso 3 — El nodo tiene dos hijos.**
No se puede simplemente borrar. Se busca el **sucesor inorden**: el valor **menor de la rama derecha** (es decir, se baja una vez a la derecha y luego siempre a la izquierda). Se copian sus datos al nodo que se quería eliminar y después se elimina el sucesor de la rama derecha (que siempre cae en el caso 1 o 2).

```
        50                    60
      /    \               /     \
    30      70     →     30       70      (se elimina 50)
           /  \                     \
         60    80                    80
```

El sucesor de `50` es `60`, porque es el menor valor de su rama derecha. Al ser el menor de los mayores, mantiene intacta la regla del ABB.

### 2.12 Por qué recursión

La recursión es cuando un método **se llama a sí mismo** para resolver una versión más pequeña del mismo problema.

Como se dijo en 2.1, los árboles son **recursivos por definición**: cada subárbol es a su vez un árbol, y cada nodo es la raíz de su propio subárbol. Por eso todos los métodos siguen el mismo esquema:

1. Si el nodo es `null`, no hacer nada (**caso base**, el que detiene la recursión).
2. Si el nodo existe, procesarlo y volver a llamar al mismo método sobre el hijo izquierdo y el derecho.

```java
// Ejemplo ilustrativo: contar todos los nodos del árbol
public int contarNodos(Oficina nodo) {
    if (nodo == null) {
        return 0;  // Caso base: no hay nodo, no se cuenta nada
    }
    // Cuenta 1 (este nodo) + los de la izquierda + los de la derecha
    return 1 + contarNodos(nodo.getIzquierdo()) + contarNodos(nodo.getDerecho());
}
```

> Este `contarNodos` es solo un ejemplo para entender el patrón; no forma parte del código del proyecto.

#### El patrón de dos métodos (envoltorio)

En `ArbolInventario` **cada operación se escribe con dos métodos**: uno público sin el nodo como parámetro, y uno privado y recursivo que sí lo recibe.

```java
public void mostrarInorden() {            // QUÉ se hace. Es el que llama Main.
    mostrarInordenRecursivo(raiz);        // arranca siempre desde la raíz
}

private void mostrarInordenRecursivo(Oficina nodo) {   // CÓMO se hace
    ...                                   // baja por el árbol nodo por nodo
}
```

Se hace así por tres motivos:

1. **La recursión necesita un parámetro que el usuario no debería pasar.** El método recursivo avanza cambiando el nodo actual. Si ese fuera el único método, `Main` tendría que escribir `miArbol.mostrarInorden(miArbol.getRaiz())`, es decir, conocer la raíz y la clase `Oficina` — justo lo que `private` busca ocultar.
2. **Hay que reasignar la raíz.** En `insertar` y `eliminar`, el método recursivo devuelve un nodo porque reconstruye los enlaces al regresar. Cuando el árbol está vacío, `raiz` vale `null` y en Java no se puede modificar un `null` desde adentro: la única forma de que la raíz apunte al nodo nuevo es que la recursión lo devuelva y el método público lo asigne con `raiz = ...`.
3. **Separa la interfaz de la implementación.** `Main` solo dice *"muestra el directorio"*; no sabe ni le importa cómo está armado el árbol por dentro.

Por eso `Main` nunca pide la raíz: para saber si hay datos usa `arbol.estaVacio()`, no `arbol.getRaiz() == null`.

### 2.13 Ejercicio en papel

Dado este orden de inserción: **45, 25, 65, 15, 35, 55, 75, 10, 30**

1. Dibuje el árbol resultante paso a paso.
2. Marque cuál es la raíz, cuáles son las hojas y cuáles los nodos internos.
3. Escriba el resultado de cada recorrido:
   - Inorden: _______________
   - Preorden: _______________
   - Postorden: _______________
4. ¿Cuál es la altura del árbol? ¿Cuántos niveles tiene?
5. ¿Cuántas hojas tiene? ¿Cuál es el grado del nodo 25?
6. Si busca el número 35, ¿por cuáles nodos pasa?
7. ¿El árbol resultante es balanceado, completo o degenerado?

---

## 3. Estructura del proyecto

```
proyectoArbolBB/
├── src/
│   ├── Oficina.java           (El nodo del árbol)
│   ├── ArbolInventario.java   (La lógica del árbol)
│   └── Main.java              (El menú de consola)
├── out/                       (Archivos compilados .class)
└── README.md                  (Este documento)
```

---

## 4. Código Fuente en Java

### Clase A: `Oficina.java` (El Nodo)

Cada nodo guarda la extensión (`id`), el nombre de la oficina y las referencias a sus dos hijos. Los atributos son `private` y se acceden mediante *getters* y *setters* (encapsulamiento).

```java
public class Oficina {
    private int id;              // Número de extensión (clave para ordenar el árbol)
    private String nombre;       // Nombre de la oficina
    private Oficina izquierdo;   // Hijo menor (números menores que id)
    private Oficina derecho;     // Hijo mayor (números mayores que id)

    // Constructor: crea un nuevo nodo con sus dos ramas vacías
    public Oficina(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.izquierdo = null;  // No tiene hijo menor todavía
        this.derecho = null;    // No tiene hijo mayor todavía
    }

    // Getters: permiten leer los datos desde otra clase
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public Oficina getIzquierdo() { return izquierdo; }
    public Oficina getDerecho() { return derecho; }

    // Setters: permiten modificar los datos desde otra clase
    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setIzquierdo(Oficina izquierdo) { this.izquierdo = izquierdo; }
    public void setDerecho(Oficina derecho) { this.derecho = derecho; }
}
```

### Clase B: `ArbolInventario.java` (La Lógica)

Contiene la raíz y todas las operaciones del árbol. Cada operación pública tiene un método privado recursivo que hace el trabajo real.

```java
/**
 * Árbol Binario de Búsqueda (ABB) que almacena las extensiones telefónicas.
 *
 * Regla de orden del ABB: las extensiones menores quedan en la rama izquierda
 * y las mayores en la rama derecha. Gracias a esa regla, cada comparación
 * descarta media rama y la búsqueda es rápida.
 *
 * PATRÓN QUE SE REPITE EN TODA LA CLASE (método envoltorio):
 * Cada operación se escribe con DOS métodos:
 *
 *   1. Uno PÚBLICO, sin el nodo como parámetro: es el que usa Main y define
 *      QUÉ se hace (insertar, buscar, eliminar, mostrar...). Arranca siempre
 *      desde la raíz.
 *   2. Uno PRIVADO y recursivo, que recibe el nodo actual: define CÓMO se
 *      hace, bajando por el árbol nodo por nodo.
 *
 * ¿Por qué dos? Porque la recursión necesita saber en qué nodo va, y ese
 * dato es interno del árbol. Si el método público pidiera el nodo, quien lo
 * usa tendría que conocer la raíz y la clase Oficina, que es justo lo que se
 * quiere ocultar. Main solo dice "inserta esto"; no sabe cómo está armado
 * el árbol por dentro.
 */
public class ArbolInventario {
    private Oficina raiz;  // Primer nodo del árbol; es privado, nadie fuera lo ve

    // Constructor: el árbol nace vacío (sin raíz)
    public ArbolInventario() {
        this.raiz = null;
    }

    // Indica si el árbol todavía no tiene ningún nodo.
    // Main lo usa para avisar que no hay nada que mostrar, sin necesidad
    // de pedir la raíz ni tocar los nodos por dentro.
    public boolean estaVacio() {
        return raiz == null;
    }

    // ---------------------------------------------------------------
    // INSERTAR
    // ---------------------------------------------------------------

    // Público: registra una extensión nueva. Empieza el trabajo desde la raíz.
    public void insertar(int id, String nombre) {
        // La asignación "raiz =" es imprescindible: cuando el árbol está
        // vacío, raiz vale null y no se puede modificar un null desde
        // adentro. La única forma de que la raíz apunte al nodo nuevo es
        // que la recursión lo devuelva y aquí se guarde.
        raiz = insertarRecursivo(raiz, id, nombre);
    }

    // Privado y recursivo: busca el sitio libre donde cuelga el nodo nuevo.
    // Devuelve el nodo que debe quedar en esta posición del árbol.
    private Oficina insertarRecursivo(Oficina actual, int id, String nombre) {
        // CASO BASE: llegó a un sitio vacío, así que aquí va el nodo nuevo
        if (actual == null) {
            return new Oficina(id, nombre);
        }

        // Si el id es menor, el sitio está en la rama izquierda.
        // El resultado se vuelve a enganchar con setIzquierdo, porque la
        // recursión devuelve la rama ya actualizada.
        if (id < actual.getId()) {
            actual.setIzquierdo(insertarRecursivo(actual.getIzquierdo(), id, nombre));
        }
        // Si el id es mayor, el sitio está en la rama derecha
        else if (id > actual.getId()) {
            actual.setDerecho(insertarRecursivo(actual.getDerecho(), id, nombre));
        }
        // Si es igual, no hace nada: el ABB no admite extensiones repetidas

        return actual;  // Devuelve este nodo, ya con su rama actualizada
    }

    // ---------------------------------------------------------------
    // RECORRIDOS
    // Los tres visitan todos los nodos; lo único que cambia es EN QUÉ
    // MOMENTO se imprime el nodo respecto a sus dos ramas.
    // ---------------------------------------------------------------

    // Público: recorrido INORDEN (Izquierda -> Raíz -> Derecha).
    // Es el más útil en un ABB porque saca los datos de menor a mayor.
    public void mostrarInorden() {
        mostrarInordenRecursivo(raiz);  // arranca desde la raíz
    }

    private void mostrarInordenRecursivo(Oficina nodo) {
        if (nodo != null) {                              // caso base: null, no hace nada
            mostrarInordenRecursivo(nodo.getIzquierdo());  // 1. toda la rama izquierda
            imprimir(nodo);                                // 2. el nodo (la raíz de este subárbol)
            mostrarInordenRecursivo(nodo.getDerecho());    // 3. toda la rama derecha
        }
    }

    // Público: recorrido PREORDEN (Raíz -> Izquierda -> Derecha).
    // Sirve para copiar o reconstruir el árbol, porque la raíz sale primero.
    public void mostrarPreorden() {
        mostrarPreordenRecursivo(raiz);
    }

    private void mostrarPreordenRecursivo(Oficina nodo) {
        if (nodo != null) {
            imprimir(nodo);                                 // 1. el nodo primero
            mostrarPreordenRecursivo(nodo.getIzquierdo());  // 2. rama izquierda
            mostrarPreordenRecursivo(nodo.getDerecho());    // 3. rama derecha
        }
    }

    // Público: recorrido POSTORDEN (Izquierda -> Derecha -> Raíz).
    // Sirve para liberar el árbol, porque visita los hijos antes que el padre.
    public void mostrarPostorden() {
        mostrarPostordenRecursivo(raiz);
    }

    private void mostrarPostordenRecursivo(Oficina nodo) {
        if (nodo != null) {
            mostrarPostordenRecursivo(nodo.getIzquierdo());  // 1. rama izquierda
            mostrarPostordenRecursivo(nodo.getDerecho());    // 2. rama derecha
            imprimir(nodo);                                  // 3. el nodo al final
        }
    }

    // Da formato a una línea del directorio. Está aparte para que los tres
    // recorridos impriman igual y no se repita el texto tres veces.
    private void imprimir(Oficina nodo) {
        System.out.println("Extensión: " + nodo.getId() + " | Oficina: " + nodo.getNombre());
    }

    // ---------------------------------------------------------------
    // BUSCAR
    // ---------------------------------------------------------------

    // Público: informa si la extensión existe. Devuelve el mensaje ya listo
    // para mostrar, así Main no tiene que interpretar un true o un false.
    public String buscar(int id) {
        return buscarRecursivo(raiz, id) ? "ID encontrado en el sistema." : "El ID no existe.";
    }

    // Privado y recursivo: baja por una sola rama, comparando en cada nodo
    private boolean buscarRecursivo(Oficina actual, int id) {
        if (actual == null) return false;           // Caso base: se acabó la rama, no está
        if (id == actual.getId()) return true;      // Caso base: ¡lo encontró!

        // Si el id buscado es menor sigue por la izquierda; si no, por la
        // derecha. Nunca revisa las dos ramas: por eso la búsqueda es rápida.
        return id < actual.getId()
            ? buscarRecursivo(actual.getIzquierdo(), id)
            : buscarRecursivo(actual.getDerecho(), id);
    }

    // ---------------------------------------------------------------
    // ELIMINAR
    // ---------------------------------------------------------------

    // Público: borra una extensión y devuelve el mensaje del resultado
    public String eliminar(int id) {
        // Se comprueba antes de borrar, para poder avisar si el ID no existe
        if (!buscarRecursivo(raiz, id)) {
            return "El ID no existe en el sistema.";
        }
        // Igual que en insertar, el resultado se reasigna a raiz, porque el
        // nodo eliminado puede ser precisamente la raíz
        raiz = eliminarRecursivo(raiz, id);
        return "Extensión eliminada.";
    }

    // Privado y recursivo: localiza el nodo y aplica el caso que corresponda.
    // Devuelve el nodo que debe quedar en esta posición (o null si se borró).
    private Oficina eliminarRecursivo(Oficina actual, int id) {
        // Caso base: se acabó la rama sin encontrarlo
        if (actual == null) {
            return null;
        }

        // Mientras no sea el nodo buscado, sigue bajando y vuelve a enganchar
        // la rama que devuelve la recursión
        if (id < actual.getId()) {
            actual.setIzquierdo(eliminarRecursivo(actual.getIzquierdo(), id));
        } else if (id > actual.getId()) {
            actual.setDerecho(eliminarRecursivo(actual.getDerecho(), id));
        } else {
            // Encontró el nodo a eliminar. Ahora decide cuál de los tres
            // casos aplica, según cuántos hijos tenga.

            // CASO 1: es una hoja (no tiene hijos).
            // Se devuelve null, y el padre deja de apuntarlo: desaparece.
            if (actual.getIzquierdo() == null && actual.getDerecho() == null) {
                return null;
            }

            // CASO 2: tiene un solo hijo.
            // Se devuelve ese hijo, que sube a ocupar el lugar del eliminado.
            if (actual.getIzquierdo() == null) {
                return actual.getDerecho();    // solo tiene hijo derecho
            }
            if (actual.getDerecho() == null) {
                return actual.getIzquierdo();  // solo tiene hijo izquierdo
            }

            // CASO 3: tiene dos hijos. No se puede borrar sin romper el árbol,
            // así que se reemplaza por su SUCESOR INORDEN: el menor valor de
            // la rama derecha. Al ser "el menor de los mayores", sigue siendo
            // mayor que toda la rama izquierda y menor que el resto de la
            // derecha, así que la regla de orden se mantiene intacta.
            Oficina sucesor = buscarSucesor(actual.getDerecho());

            // Se copian los datos del sucesor sobre este nodo
            actual.setId(sucesor.getId());
            actual.setNombre(sucesor.getNombre());

            // Y se borra el sucesor de la rama derecha, donde estaba duplicado.
            // Ese borrado siempre cae en el caso 1 o 2, nunca en el 3, porque
            // el sucesor no puede tener hijo izquierdo.
            actual.setDerecho(eliminarRecursivo(actual.getDerecho(), sucesor.getId()));
        }

        return actual;  // Devuelve este nodo, ya actualizado
    }

    // Busca el nodo menor de un subárbol: el que está más a la izquierda.
    // Aquí se usa un while y no recursión porque el recorrido es en línea
    // recta, sin ramificarse: no hay que volver atrás en ningún momento.
    private Oficina buscarSucesor(Oficina nodo) {
        Oficina actual = nodo;
        while (actual.getIzquierdo() != null) {
            actual = actual.getIzquierdo();
        }
        return actual;  // Ya no hay nada más a la izquierda: este es el menor
    }
}
```

### Clase C: `Main.java` (Interfaz de Usuario)

Muestra el menú en consola y llama a los métodos del árbol. Usa `try-catch` para que el programa no se caiga si el usuario escribe algo que no es un número.

El menú dibuja el **árbol de ejemplo** de la sección 2 y, en cada opción de recorrido, indica su orden de visita y el resultado que daría sobre ese ejemplo:

```
--- DIRECTORIO DE EXTENSIONES ---
Árbol de ejemplo:
            50
          /    \
        30      70
       /  \    /  \
     20   40  60   80

1. Registrar Extensión
2. Ver Directorio Inorden   (ejemplo: Izquierda -> Raíz -> Derecha = 20, 30, 40, 50, 60, 70, 80)
3. Ver Directorio Preorden  (ejemplo: Raíz -> Izquierda -> Derecha = 50, 30, 20, 40, 70, 60, 80)
4. Ver Directorio Postorden (ejemplo: Izquierda -> Derecha -> Raíz = 20, 40, 30, 60, 80, 70, 50)
5. Buscar Extensión
6. Eliminar Extensión
0. Salir
Seleccione una opción:
```

Así el estudiante compara el resultado teórico del ejemplo con el de su propio árbol. Al elegir un recorrido, el programa recuerda el orden de visita y lista el directorio real:

```
INORDEN | Izquierda -> Raíz -> Derecha
Su directorio:
Extensión: 30 | Oficina: Contabilidad
Extensión: 50 | Oficina: Gerencia
Extensión: 70 | Oficina: Sistemas
```

```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Crea una instancia del árbol para guardar las extensiones
        ArbolInventario miArbol = new ArbolInventario();
        Scanner sc = new Scanner(System.in);  // Objeto para leer datos del usuario
        int opcion = -1;  // Guardará la opción que elija el usuario

        // Bucle que muestra el menú hasta que el usuario elija salir (opción 0)
        while (opcion != 0) {
            System.out.println("\n--- DIRECTORIO DE EXTENSIONES ---");
            mostrarArbolEjemplo();  // Dibuja el árbol de referencia
            System.out.println("1. Registrar Extensión");
            System.out.println("2. Ver Directorio Inorden   (ejemplo: Izquierda -> Raíz -> Derecha = 20, 30, 40, 50, 60, 70, 80)");
            System.out.println("3. Ver Directorio Preorden  (ejemplo: Raíz -> Izquierda -> Derecha = 50, 30, 20, 40, 70, 60, 80)");
            System.out.println("4. Ver Directorio Postorden (ejemplo: Izquierda -> Derecha -> Raíz = 20, 40, 30, 60, 80, 70, 50)");
            System.out.println("5. Buscar Extensión");
            System.out.println("6. Eliminar Extensión");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            
            // Manejo de errores: si el usuario ingresa algo que no es un número
            try {
                opcion = sc.nextInt();  // Lee la opción del usuario
            } catch (Exception e) {
                System.out.println("Entrada inválida. Debe ingresar un número.");
                sc.next();  // Limpia el buffer del Scanner
                continue;   // Vuelve a mostrar el menú
            }

            // Según la opción elegida, hace una cosa u otra
            switch (opcion) {
                case 1:  // Registrar nueva extensión
                    System.out.print("Ingrese número de extensión: ");
                    try {
                        int id = sc.nextInt();
                        sc.nextLine();  // Limpia el buffer después de leer número
                        System.out.print("Nombre de la oficina: ");
                        String nombre = sc.nextLine();  // Lee el nombre
                        miArbol.insertar(id, nombre);   // Inserta en el árbol
                        System.out.println("Registrado con éxito.");
                    } catch (Exception e) {
                        System.out.println("ID debe ser un número válido.");
                        sc.next();
                    }
                    break;
                    
                case 2:  // Recorrido inorden (menor a mayor)
                    mostrarEncabezadoRecorrido("INORDEN", "Izquierda -> Raíz -> Derecha");
                    mostrarDirectorio(miArbol, 2);
                    break;

                case 3:  // Recorrido preorden (raíz primero)
                    mostrarEncabezadoRecorrido("PREORDEN", "Raíz -> Izquierda -> Derecha");
                    mostrarDirectorio(miArbol, 3);
                    break;

                case 4:  // Recorrido postorden (raíz al final)
                    mostrarEncabezadoRecorrido("POSTORDEN", "Izquierda -> Derecha -> Raíz");
                    mostrarDirectorio(miArbol, 4);
                    break;

                case 5:  // Buscar una extensión por su ID
                    System.out.print("ID a buscar: ");
                    try {
                        int buscaId = sc.nextInt();
                        System.out.println(miArbol.buscar(buscaId));
                    } catch (Exception e) {
                        System.out.println("ID debe ser un número válido.");
                        sc.next();
                    }
                    break;

                case 6:  // Eliminar una extensión por su ID
                    System.out.print("ID a eliminar: ");
                    try {
                        int eliminaId = sc.nextInt();
                        System.out.println(miArbol.eliminar(eliminaId));
                    } catch (Exception e) {
                        System.out.println("ID debe ser un número válido.");
                        sc.next();
                    }
                    break;
                    
                case 0:  // Salir del programa
                    System.out.println("Saliendo del sistema...");
                    break;
                    
                default:  // Opción no válida
                    System.out.println("Opción no válida.");
            }
        }
        sc.close();  // Cierra el Scanner cuando termina el programa
    }

    // Dibuja el árbol de ejemplo que sirve de referencia en el menú.
    // Corresponde a insertar en este orden: 50, 30, 70, 20, 40, 60, 80
    private static void mostrarArbolEjemplo() {
        System.out.println();
        System.out.println();
        System.out.println("Árbol de ejemplo:");
        System.out.println("            50");
        System.out.println("          /    \\");
        System.out.println("        30      70");
        System.out.println("       /  \\    /  \\");
        System.out.println("     20   40  60   80");
        System.out.println();
    }

    // Imprime el nombre del recorrido y su orden de visita.
    // El resultado de ejemplo ya se ve en el menú, por eso no se repite aquí.
    private static void mostrarEncabezadoRecorrido(String nombre, String orden) {
        System.out.println("\n" + nombre + " | " + orden);
    }

    // Lanza sobre el árbol del usuario el recorrido que eligió en el menú.
    // Main no conoce la raíz ni la clase Oficina: solo le pide al árbol que
    // se muestre. Todo el detalle de cómo se recorre queda dentro de
    // ArbolInventario, que es el que sabe cómo está armado por dentro.
    private static void mostrarDirectorio(ArbolInventario arbol, int recorrido) {
        // Si todavía no hay extensiones, avisa en vez de no imprimir nada
        if (arbol.estaVacio()) {
            System.out.println("Su directorio: (vacío, aún no ha registrado extensiones)");
            return;
        }

        System.out.println("Su directorio:");
        switch (recorrido) {
            case 2:
                arbol.mostrarInorden();    // menor a mayor
                break;
            case 3:
                arbol.mostrarPreorden();   // la raíz primero
                break;
            case 4:
                arbol.mostrarPostorden();  // la raíz al final
                break;
        }
    }
}
```

---

## 5. Compilar y ejecutar

Desde PowerShell, ubicado en la carpeta del proyecto:

```powershell
# Compila los .java y deja los .class en la carpeta out
javac src/*.java -d out

# Ejecuta la clase que tiene el main
java -cp out Main
```

| Comando | Qué hace |
|---------|----------|
| `javac` | **Compila:** convierte archivos `.java` en archivos `.class` (bytecode) |
| `-d out` | Coloca los `.class` en la carpeta `out` |
| `java` | **Ejecuta:** corre el programa compilado |
| `-cp out` | Le dice a Java que busque las clases en la carpeta `out` |
| `Main` | Nombre de la clase que tiene el método `main` |

---

## 6. Prueba sugerida

1. Registrar en este orden: `50 Gerencia`, `30 Contabilidad`, `70 Sistemas`, `20 Recepción`, `40 Ventas`, `60 Soporte`, `80 Bodega`.
2. Opción 2 (inorden) → debe listar: 20, 30, 40, 50, 60, 70, 80.
3. Opción 3 (preorden) → debe listar: 50, 30, 20, 40, 70, 60, 80.
4. Opción 4 (postorden) → debe listar: 20, 40, 30, 60, 80, 70, 50.
5. Opción 5 buscando `40` → "ID encontrado en el sistema."; buscando `99` → "El ID no existe."
6. Opción 6 eliminando `20` (caso 1: hoja), luego `70` (caso 2: un hijo tras la eliminación previa) y finalmente `50` (caso 3: dos hijos). Después de cada eliminación, verificar con la opción 2 que el inorden sigue ordenado.
