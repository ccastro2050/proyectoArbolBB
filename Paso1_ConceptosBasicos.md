# Paso 1: Conceptos Basicos de Arboles Binarios de Busqueda

**Asignatura:** Estructuras de Datos
**Profesor:** Carlos Arturo Castro Castro

---

## 1. Que es un Árbol Binario

Un árbol binario es una estructura de datos donde cada nodo tiene **como máximo dos hijos**: uno izquierdo y uno derecho.

```
        [50]           <-- Raiz
       /    \
    [30]    [70]       <-- Hijos de 50
    /  \    /  \
 [20] [40] [60] [80]  <-- Hojas (no tienen hijos)
```

### Terminologia basica

| Termino | Significado |
|---------|-------------|
| **Raiz** | El primer nodo del árbol (no tiene padre) |
| **Nodo** | Cada elemento del árbol que almacena un dato |
| **Hoja** | Un nodo que no tiene hijos (izquierdo ni derecho) |
| **Hijo** | Nodo que cuelga de otro nodo |
| **Padre** | Nodo del que cuelga otro nodo |
| **Nivel** | Distancia desde la raiz (la raiz está en nivel 0) |
| **Altura** | Cantidad de niveles del árbol |
| **Subarbol** | Cualquier nodo junto con todos sus descendientes |

---

## 2. Que es un Árbol Binario de Búsqueda (ABB)

Es un árbol binario con una **regla de orden**:

> **Valores menores van a la izquierda. Valores mayores van a la derecha.**

Esto permite buscar datos rápidamente, similar a buscar una palabra en un diccionario: no revisas todas las páginas, sino que vas descartando mitades.

### Ejemplo: insertar los números 50, 30, 70, 20, 40, 60, 80

```
Paso 1: Insertar 50 (es la raiz)
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
  [20] [40] [60]

Paso 7: Insertar 80 (80 > 50, derecha; 80 > 70, derecha)
        [50]
        /    \
     [30]   [70]
     /  \   /  \
  [20] [40] [60] [80]
```

---

## 3. Recorridos de un Árbol Binario

Recorrer un árbol significa **visitar todos sus nodos** en un orden especifico. Existen tres recorridos principales. La diferencia entre ellos es **cuando se visita la raiz** respecto a sus hijos.

Usaremos este árbol para todos los ejemplos:

```
        [50]
        /    \
     [30]   [70]
     /  \   /  \
  [20] [40] [60] [80]
```

---

### 3.1 Recorrido INORDEN (Izquierda - Raiz - Derecha)

**Regla:** Primero visito todo lo de la izquierda, luego la raiz, luego todo lo de la derecha.

**Resultado:** Los datos aparecen **ordenados de menor a mayor**. Por eso es el recorrido más util en un ABB.

#### Paso a paso detallado:

```
Empiezo en [50]:
  Antes de visitar 50, debo ir a la izquierda -> [30]
    Antes de visitar 30, debo ir a la izquierda -> [20]
      Antes de visitar 20, debo ir a la izquierda -> null (no hay nada)
      Visito [20]  ← PRIMER NUMERO
      Voy a la derecha de 20 -> null (no hay nada)
    Visito [30]  ← SEGUNDO NUMERO
    Voy a la derecha de 30 -> [40]
      Izquierda de 40 -> null
      Visito [40]  ← TERCER NUMERO
      Derecha de 40 -> null
  Visito [50]  ← CUARTO NUMERO
  Voy a la derecha de 50 -> [70]
    Izquierda de 70 -> [60]
      Izquierda de 60 -> null
      Visito [60]  ← QUINTO NUMERO
      Derecha de 60 -> null
    Visito [70]  ← SEXTO NUMERO
    Derecha de 70 -> [80]
      Izquierda de 80 -> null
      Visito [80]  ← SEPTIMO NUMERO
      Derecha de 80 -> null
```

**Resultado Inorden: 20, 30, 40, 50, 60, 70, 80** (ordenado)

#### Codigo:

```java
public void mostrarInorden(Producto nodo) {
    if (nodo != null) {
        mostrarInorden(nodo.izquierdo);  // 1. Izquierda
        System.out.println(nodo.id);      // 2. Raiz (visitar)
        mostrarInorden(nodo.derecho);     // 3. Derecha
    }
}
```

---

### 3.2 Recorrido PREORDEN (Raiz - Izquierda - Derecha)

**Regla:** Primero visito la raiz, luego todo lo de la izquierda, luego todo lo de la derecha.

**Utilidad:** Sirve para **copiar o reconstruir** un árbol exactamente igual, porque el primer nodo que visita es la raiz.

#### Paso a paso detallado:

```
Llego a [50]:
  Visito [50]  ← PRIMER NUMERO
  Voy a la izquierda -> [30]
    Visito [30]  ← SEGUNDO NUMERO
    Voy a la izquierda -> [20]
      Visito [20]  ← TERCER NUMERO
      Izquierda de 20 -> null
      Derecha de 20 -> null
    Voy a la derecha de 30 -> [40]
      Visito [40]  ← CUARTO NUMERO
      Izquierda de 40 -> null
      Derecha de 40 -> null
  Voy a la derecha de 50 -> [70]
    Visito [70]  ← QUINTO NUMERO
    Voy a la izquierda -> [60]
      Visito [60]  ← SEXTO NUMERO
      Izquierda de 60 -> null
      Derecha de 60 -> null
    Voy a la derecha de 70 -> [80]
      Visito [80]  ← SEPTIMO NUMERO
      Izquierda de 80 -> null
      Derecha de 80 -> null
```

**Resultado Preorden: 50, 30, 20, 40, 70, 60, 80** (raiz primero)

#### Codigo:

```java
public void mostrarPreorden(Producto nodo) {
    if (nodo != null) {
        System.out.println(nodo.id);       // 1. Raiz (visitar)
        mostrarPreorden(nodo.izquierdo);   // 2. Izquierda
        mostrarPreorden(nodo.derecho);     // 3. Derecha
    }
}
```

---

### 3.3 Recorrido POSTORDEN (Izquierda - Derecha - Raiz)

**Regla:** Primero visito todo lo de la izquierda, luego todo lo de la derecha, y al final la raiz.

**Utilidad:** Sirve para **eliminar o liberar** un árbol completo, porque visita los hijos antes que el padre (asi no se pierden referencias).

#### Paso a paso detallado:

```
Llego a [50]:
  Voy a la izquierda -> [30]
    Voy a la izquierda -> [20]
      Izquierda de 20 -> null
      Derecha de 20 -> null
      Visito [20]  ← PRIMER NUMERO
    Voy a la derecha de 30 -> [40]
      Izquierda de 40 -> null
      Derecha de 40 -> null
      Visito [40]  ← SEGUNDO NUMERO
    Visito [30]  ← TERCER NUMERO
  Voy a la derecha de 50 -> [70]
    Voy a la izquierda -> [60]
      Izquierda de 60 -> null
      Derecha de 60 -> null
      Visito [60]  ← CUARTO NUMERO
    Voy a la derecha de 70 -> [80]
      Izquierda de 80 -> null
      Derecha de 80 -> null
      Visito [80]  ← QUINTO NUMERO
    Visito [70]  ← SEXTO NUMERO
  Visito [50]  ← SEPTIMO NUMERO (la raiz es la ultima)
```

**Resultado Postorden: 20, 40, 30, 60, 80, 70, 50** (raiz al final)

#### Codigo:

```java
public void mostrarPostorden(Producto nodo) {
    if (nodo != null) {
        mostrarPostorden(nodo.izquierdo);  // 1. Izquierda
        mostrarPostorden(nodo.derecho);    // 2. Derecha
        System.out.println(nodo.id);        // 3. Raiz (visitar)
    }
}
```

---

### 3.4 Resumen comparativo de los tres recorridos

```
Arbol:
        [50]
        /    \
     [30]   [70]
     /  \   /  \
  [20] [40] [60] [80]
```

| Recorrido | Orden de visita | Resultado | Para que sirve |
|-----------|----------------|-----------|----------------|
| **Inorden** | Izq → Raiz → Der | 20, 30, 40, **50**, 60, 70, 80 | Ver datos ordenados |
| **Preorden** | Raiz → Izq → Der | **50**, 30, 20, 40, 70, 60, 80 | Copiar/reconstruir el árbol |
| **Postorden** | Izq → Der → Raiz | 20, 40, 30, 60, 80, 70, **50** | Eliminar/liberar el árbol |

> **Truco para recordar:** El nombre del recorrido indica **cuando se visita la raiz**:
> - **Pre**orden = raiz al **pre**inicio
> - **In**orden = raiz **in**termedio
> - **Post**orden = raiz al **post**final

---

## 4. Recursion: por que los arboles la necesitan

La recursión es cuando un método **se llama a si mismo** para resolver un problema más pequeno.

Los arboles son **naturalmente recursivos**: cada nodo es la raiz de su propio subarbol. Por eso, para recorrer un árbol, le decimos al metodo:

1. Si el nodo es `null`, no hagas nada (caso base)
2. Si el nodo existe, procesalo y luego llama al mismo método para el hijo izquierdo y el derecho

```java
// Ejemplo: contar todos los nodos del árbol
public int contarNodos(Producto nodo) {
    if (nodo == null) {
        return 0;  // Caso base: no hay nodo, no cuento nada
    }
    // Cuento 1 (este nodo) + los de la izquierda + los de la derecha
    return 1 + contarNodos(nodo.izquierdo) + contarNodos(nodo.derecho);
}
```

---

## 5. Ejercicio en papel

Dado el siguiente orden de insercion: **45, 25, 65, 15, 35, 55, 75, 10, 30**

1. Dibuje el árbol resultante paso a paso
2. Escriba el resultado de cada recorrido:
   - Inorden: _______________
   - Preorden: _______________
   - Postorden: _______________
3. Cual es la altura del árbol?
4. Cuantas hojas tiene?
5. Si busco el número 35, por cuales nodos paso?
