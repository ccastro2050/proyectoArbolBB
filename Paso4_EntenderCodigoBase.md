# Paso 4: Entender el Código Base

**Asignatura:** Estructuras de Datos
**Profesor:** Carlos Arturo Castro Castro

---

> **Este paso es para los 3 estudiantes.** Antes de agregar código nuevo, deben entender exactamente que hace cada línea del proyecto.

---

## 1. Producto.java (El Nodo)

Esta clase representa **un nodo** del árbol. Cada nodo guarda un dato y tiene dos referencias a sus hijos.

```java
public class Producto {
    int id;              // Extensión telefónica (clave para ordenar)
    String nombre;       // Nombre de la oficina
    Producto izquierdo;  // Referencia al hijo izquierdo (menor)
    Producto derecho;    // Referencia al hijo derecho (mayor)

    public Producto(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.izquierdo = null;  // Al crearse, no tiene hijos
        this.derecho = null;
    }
}
```

### Que significa cada línea

| Linea | Que hace |
|-------|----------|
| `int id` | Guarda el número de extensión. Este valor decide si el nodo va a la izquierda o derecha |
| `String nombre` | Guarda el nombre de la oficina asociada a esa extensión |
| `Producto izquierdo` | Apunta al hijo izquierdo. Es de tipo `Producto` porque un hijo es otro nodo |
| `Producto derecho` | Apunta al hijo derecho |
| `this.izquierdo = null` | Cuando se crea un nodo nuevo, no tiene hijos todavía |

### Representacion visual de un nodo

```
    ┌─────────────────────┐
    │  id: 50             │
    │  nombre: "Gerencia" │
    ├──────────┬──────────┤
    │ izquierdo│  derecho │
    │  (null)  │  (null)  │
    └──────────┴──────────┘
```

---

## 2. ArbolInventario.java (La Logica del Arbol)

### 2.1 El atributo raiz

```java
public class ArbolInventario {
    Producto raiz;

    public ArbolInventario() {
        this.raiz = null;  // El árbol empieza vacio
    }
```

`raiz` es el punto de entrada al árbol. Todos los recorridos y operaciones empiezan desdeaquí.

### 2.2 Método insertar

```java
    public void insertar(int id, String nombre) {
        raiz = insertarRecursivo(raiz, id, nombre);
    }

    private Producto insertarRecursivo(Producto actual, int id, String nombre) {
        if (actual == null) {
            return new Producto(id, nombre);
        }
        if (id < actual.id) {
            actual.izquierdo = insertarRecursivo(actual.izquierdo, id, nombre);
        } else if (id > actual.id) {
            actual.derecho = insertarRecursivo(actual.derecho, id, nombre);
        }
        return actual;
    }
```

**Ejemplo paso a paso: insertar 30 en un árbol con raiz 50:**

```
1. insertar(30, "Contabilidad") llama a insertarRecursivo(raiz, 30, ...)
2. actual = [50], y 30 < 50 → baja por la izquierda
3. insertarRecursivo(null, 30, ...) → actual es null
4. Se crea nuevo Producto(30, "Contabilidad") y se retorna
5. Ese nuevo nodo queda como hijo izquierdo de [50]

        [50]
        /
     [30]
```

### 2.3 Método mostrarInorden

```java
    public void mostrarInorden(Producto nodo) {
        if (nodo != null) {
            mostrarInorden(nodo.izquierdo);
            System.out.println("Extension: " + nodo.id + " | Oficina: " + nodo.nombre);
            mostrarInorden(nodo.derecho);
        }
    }
```

Recorre el árbol en orden **Izquierda → Raiz → Derecha**. Produce un listado ordenado de menor a mayor.

### 2.4 Método buscar

```java
    public String buscar(int id) {
        return buscarRecursivo(raiz, id) ? "ID encontrado en el sistema." : "El ID no existe.";
    }

    private boolean buscarRecursivo(Producto actual, int id) {
        if (actual == null) return false;
        if (id == actual.id) return true;
        return id < actual.id
            ? buscarRecursivo(actual.izquierdo, id)
            : buscarRecursivo(actual.derecho, id);
    }
```

**Ejemplo: buscar 40 en el árbol:**

```
        [50]
        /    \
     [30]   [70]
        \
       [40]

1. buscarRecursivo([50], 40): 40 < 50 → baja por izquierda
2. buscarRecursivo([30], 40): 40 > 30 → baja por derecha
3. buscarRecursivo([40], 40): 40 == 40 → retorna true
```

---

## 3. Main.java (El Menu)

```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArbolInventario miÁrbol = new ArbolInventario();
        Scanner sc = new Scanner(System.in);
        int opción = -1;

        while (opcion != 0) {
            // ... muestra el menú y lee la opción ...
            switch (opcion) {
                case 1: // Registrar: lee id y nombre, llama miArbol.insertar()
                case 2: // Ver directorio: llama miArbol.mostrarInorden(miArbol.raiz)
                case 3: // Buscar: lee id, llama miArbol.buscar()
                case 0: // Salir
            }
        }
        sc.close();
    }
}
```

### Partes clave del Main

| Linea | Que hace |
|-------|----------|
| `Scanner sc = new Scanner(System.in)` | Crea un objeto para leer lo que el usuario escribe |
| `while (opcion != 0)` | El menú se repite hasta que el usuario escriba 0 |
| `sc.nextInt()` | Lee un número entero del teclado |
| `sc.nextLine()` | Lee una línea de texto. Se usa después de `nextInt()` para limpiar el buffer |
| `switch (opcion)` | Ejecuta un bloque diferente según la opción elegida |

---

## 4. Ejercicio practico: probar el proyecto

### 4.1 Compilar y ejecutar

Abrir PowerShell en la carpeta del proyecto:

```powershell
javac src/*.java -d out
java -cp out Main
```

### 4.2 Registrar estos datos (en este orden)

| Orden | Extensión | Oficina |
|-------|-----------|---------|
| 1 | 50 | Gerencia |
| 2 | 30 | Contabilidad |
| 3 | 70 | Sistemas |
| 4 | 20 | Archivo |
| 5 | 40 | Recursos Humanos |
| 6 | 60 | Ventas |
| 7 | 80 | Soporte |

### 4.3 Verificar

1. Opción **2 (Ver Directorio)**: deben aparecer ordenados: 20, 30, 40, 50, 60, 70, 80
2. Opción **3 (Buscar)**: buscar 40 → "ID encontrado". Buscar 99 → "El ID no existe"

### 4.4 Dibujar el árbol en papel

Dibujar el árbol que se forma al insertar esos 7 datos en ese orden:

```
        [50]
        /    \
     [30]   [70]
     /  \   /  \
  [20] [40] [60] [80]
```

**Preguntas:**
1. Si insertamos la extensión 35, donde queda? (Respuesta: hijo izquierdo de 40)
2. Si insertamos la extensión 65, donde queda? (Respuesta: hijo derecho de 60)
3. Cuantas hojas tiene el árbol? (Respuesta: 4 → son 20, 40, 60, 80)
