# Ejercicio de Clase: Sistema de Directorio con Árboles Binarios de Búsqueda (ABB)

**Asignatura:** Estructuras de Datos
**Profesor:** Carlos Arturo Castro Castro

---

## 1. Enunciado del Problema

Se requiere un sistema sencillo para gestionar un **Directorio de Extensiones Telefónicas**. El sistema debe permitir registrar una extensión (ID numérico) junto con el nombre de la oficina. Para garantizar búsquedas rápidas, los datos se organizan en un **Árbol Binario de Búsqueda (ABB)** bajo la regla: *valores menores a la izquierda, valores mayores a la derecha*.

---

## 2. Conceptos del ABB

### 2.1 Qué es un Árbol Binario de Búsqueda

Un ABB es una estructura donde cada elemento se llama **nodo**, y cada nodo puede tener como máximo dos hijos:

- **Hijo izquierdo:** contiene un valor **menor** que el del nodo.
- **Hijo derecho:** contiene un valor **mayor** que el del nodo.

El primer nodo se llama **raíz**. Un nodo sin hijos se llama **hoja**.

Si se insertan las extensiones `50, 30, 70, 20, 40, 60, 80`, el árbol queda así:

```
            50
          /    \
        30      70
       /  \    /  \
     20   40  60   80
```

Esta regla es la que hace rápida la búsqueda: en cada comparación se descarta la mitad del árbol.

### 2.2 Insertar

Se compara el ID nuevo con la raíz:

- Si es **menor**, se baja por la rama izquierda.
- Si es **mayor**, se baja por la rama derecha.
- Cuando se llega a un espacio vacío (`null`), ahí se crea el nodo.
- Si el ID ya existe, no se hace nada (no se permiten duplicados).

### 2.3 Buscar

Funciona igual que insertar, pero comparando:

- Si el ID es igual al del nodo actual → **encontrado**.
- Si es menor → se busca a la izquierda.
- Si es mayor → se busca a la derecha.
- Si se llega a `null` → **no existe**.

### 2.4 Los tres recorridos

Un recorrido es la forma de visitar todos los nodos del árbol. Cambia únicamente el **momento** en que se imprime el nodo.

| Recorrido | Orden de visita | Resultado con el árbol de ejemplo |
|-----------|-----------------|-----------------------------------|
| **Inorden** | Izquierda → Raíz → Derecha | 20, 30, 40, 50, 60, 70, 80 |
| **Preorden** | Raíz → Izquierda → Derecha | 50, 30, 20, 40, 70, 60, 80 |
| **Postorden** | Izquierda → Derecha → Raíz | 20, 40, 30, 60, 80, 70, 50 |

El **inorden** es el más útil en un ABB porque siempre devuelve los datos **ordenados de menor a mayor**.

### 2.5 Eliminar: los tres casos

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

### 2.6 Por qué recursión

Cada subárbol de un ABB es a su vez un ABB. Por eso los métodos se llaman a sí mismos con el hijo izquierdo o derecho: el problema grande se resuelve con el mismo procedimiento sobre un problema más pequeño. El caso base siempre es el mismo: `if (nodo == null) return;`.

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
public class ArbolInventario {
    private Oficina raiz;  // Primer nodo del árbol (punto de entrada)

    // Constructor: árbol vacío al inicio
    public ArbolInventario() {
        this.raiz = null;
    }

    // Getter: permite obtener la raíz desde Main
    public Oficina getRaiz() {
        return raiz;
    }

    // Inserta un nuevo nodo en el árbol
    public void insertar(int id, String nombre) {
        raiz = insertarRecursivo(raiz, id, nombre);
    }

    // Método recursivo que busca dónde colocar el nuevo nodo
    private Oficina insertarRecursivo(Oficina actual, int id, String nombre) {
        // Si llegó a un lugar vacío, crea el nuevo nodo aquí
        if (actual == null) {
            return new Oficina(id, nombre);
        }

        // Si el id es menor, va a la rama izquierda
        if (id < actual.getId()) {
            actual.setIzquierdo(insertarRecursivo(actual.getIzquierdo(), id, nombre));
        }
        // Si el id es mayor, va a la rama derecha
        else if (id > actual.getId()) {
            actual.setDerecho(insertarRecursivo(actual.getDerecho(), id, nombre));
        }
        // Si son iguales, no hace nada (evita duplicados)

        return actual;  // Devuelve el nodo actualizado
    }

    // Recorrido INORDEN: Izquierda -> Raíz -> Derecha (menor a mayor)
    public void mostrarInorden(Oficina nodo) {
        if (nodo != null) {
            mostrarInorden(nodo.getIzquierdo());
            System.out.println("Extensión: " + nodo.getId() + " | Oficina: " + nodo.getNombre());
            mostrarInorden(nodo.getDerecho());
        }
    }

    // Recorrido PREORDEN: Raíz -> Izquierda -> Derecha
    public void mostrarPreorden(Oficina nodo) {
        if (nodo != null) {
            System.out.println("Extensión: " + nodo.getId() + " | Oficina: " + nodo.getNombre());
            mostrarPreorden(nodo.getIzquierdo());
            mostrarPreorden(nodo.getDerecho());
        }
    }

    // Recorrido POSTORDEN: Izquierda -> Derecha -> Raíz
    public void mostrarPostorden(Oficina nodo) {
        if (nodo != null) {
            mostrarPostorden(nodo.getIzquierdo());
            mostrarPostorden(nodo.getDerecho());
            System.out.println("Extensión: " + nodo.getId() + " | Oficina: " + nodo.getNombre());
        }
    }

    // Busca un ID en el árbol y devuelve un mensaje
    public String buscar(int id) {
        return buscarRecursivo(raiz, id) ? "ID encontrado en el sistema." : "El ID no existe.";
    }

    // Método recursivo para buscar
    private boolean buscarRecursivo(Oficina actual, int id) {
        if (actual == null) return false;           // No lo encontró
        if (id == actual.getId()) return true;      // ¡Lo encontró!

        // Decide si buscar a la izquierda o derecha
        return id < actual.getId()
            ? buscarRecursivo(actual.getIzquierdo(), id)
            : buscarRecursivo(actual.getDerecho(), id);
    }

    // Elimina un nodo del árbol según su ID
    public String eliminar(int id) {
        // Primero verifica si el ID existe en el árbol
        if (!buscarRecursivo(raiz, id)) {
            return "El ID no existe en el sistema.";
        }
        raiz = eliminarRecursivo(raiz, id);
        return "Extensión eliminada.";
    }

    // Método recursivo que busca el nodo a eliminar
    private Oficina eliminarRecursivo(Oficina actual, int id) {
        // Caso: no encontró el nodo
        if (actual == null) {
            return null;
        }

        // Busca el nodo por la rama correspondiente
        if (id < actual.getId()) {
            // El nodo está en la rama izquierda
            actual.setIzquierdo(eliminarRecursivo(actual.getIzquierdo(), id));
        } else if (id > actual.getId()) {
            // El nodo está en la rama derecha
            actual.setDerecho(eliminarRecursivo(actual.getDerecho(), id));
        } else {
            // ¡Encontró el nodo a eliminar! Ahora decide qué caso aplica

            // CASO 1: Nodo hoja (sin hijos)
            // Solo devuelve null para eliminarlo
            if (actual.getIzquierdo() == null && actual.getDerecho() == null) {
                return null;
            }

            // CASO 2: Nodo con un solo hijo
            // Devuelve el hijo para reemplazar el nodo eliminado
            if (actual.getIzquierdo() == null) {
                return actual.getDerecho();  // Tiene solo hijo derecho
            }
            if (actual.getDerecho() == null) {
                return actual.getIzquierdo();  // Tiene solo hijo izquierdo
            }

            // CASO 3: Nodo con dos hijos
            // Busca el sucesor inorden (el menor de la rama derecha)
            Oficina sucesor = buscarSucesor(actual.getDerecho());

            // Copia los datos del sucesor al nodo actual
            actual.setId(sucesor.getId());
            actual.setNombre(sucesor.getNombre());

            // Elimina el sucesor de la rama derecha
            actual.setDerecho(eliminarRecursivo(actual.getDerecho(), sucesor.getId()));
        }

        return actual;  // Devuelve el nodo actualizado
    }

    // Busca el nodo con el valor mínimo (más a la izquierda)
    private Oficina buscarSucesor(Oficina nodo) {
        Oficina actual = nodo;
        // Mientras haya nodos a la izquierda, sigue avanzando
        while (actual.getIzquierdo() != null) {
            actual = actual.getIzquierdo();
        }
        return actual;  // Este es el nodo menor
    }
}
```

### Clase C: `Main.java` (Interfaz de Usuario)

Muestra el menú en consola y llama a los métodos del árbol. Usa `try-catch` para que el programa no se caiga si el usuario escribe algo que no es un número.

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
            System.out.println("1. Registrar Extensión");
            System.out.println("2. Ver Directorio Inorden (menor a mayor)");
            System.out.println("3. Ver Directorio Preorden (raíz primero)");
            System.out.println("4. Ver Directorio Postorden (raíz al final)");
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

                case 2:
                    System.out.println("\nINORDEN (menor a mayor):");
                    miArbol.mostrarInorden(miArbol.getRaiz());
                    break;

                case 3:
                    System.out.println("\nPREORDEN (raíz primero):");
                    miArbol.mostrarPreorden(miArbol.getRaiz());
                    break;

                case 4:
                    System.out.println("\nPOSTORDEN (raíz al final):");
                    miArbol.mostrarPostorden(miArbol.getRaiz());
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

---

## 7. Anexo: código base del enunciado original

Esta es la versión **de partida** entregada en el enunciado del ejercicio. Se conserva aquí como referencia: el proyecto actual (secciones 4 y 5) parte de este código y lo amplía.

### Diferencias entre el enunciado y el proyecto actual

| | Enunciado original | Proyecto actual |
|---|---|---|
| Clase del nodo | `Producto.java` | `Oficina.java` |
| Atributos | públicos, acceso directo (`actual.id`, `miArbol.raiz`) | `private` con getters y setters |
| Operaciones | insertar, mostrar inorden, buscar | + preorden, postorden y eliminar (3 casos) |
| Menú | 3 opciones | 6 opciones |
| Validación de entrada | ninguna | `try-catch` en cada lectura del Scanner |

### Clase A: `Producto.java` (El Nodo)

```java
/**
 * Clase que representa un nodo del árbol.
 * En este caso, cada nodo es un 'Producto' o servicio del directorio.
 */
public class Producto {
    int id;              // Extensión telefónica (Dato para ordenar)
    String nombre;       // Nombre de la oficina o servicio
    Producto izquierdo;  // Referencia al hijo menor (rama izquierda)
    Producto derecho;    // Referencia al hijo mayor (rama derecha)

    // Constructor: Inicializa el nodo con sus datos y ramas vacías
    public Producto(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.izquierdo = null;
        this.derecho = null;
    }
}
```

### Clase B: `ArbolInventario.java` (La Lógica)

```java
/**
 * Contiene la lógica de manipulación del Árbol Binario de Búsqueda.
 */
public class ArbolInventario {
    Producto raiz; // Nodo principal del árbol

    public ArbolInventario() {
        this.raiz = null;
    }

    // MÉTODO 1: INSERTAR (Punto de entrada)
    public void insertar(int id, String nombre) {
        raiz = insertarRecursivo(raiz, id, nombre);
    }

    // Lógica recursiva para insertar
    private Producto insertarRecursivo(Producto actual, int id, String nombre) {
        if (actual == null) {
            return new Producto(id, nombre); // Lugar encontrado, se crea el nodo
        }

        if (id < actual.id) {
            actual.izquierdo = insertarRecursivo(actual.izquierdo, id, nombre);
        } else if (id > actual.id) {
            actual.derecho = insertarRecursivo(actual.derecho, id, nombre);
        }
        return actual;
    }

    // MÉTODO 2: MOSTRAR INORDEN (Muestra los datos ordenados de menor a mayor)
    public void mostrarInorden(Producto nodo) {
        if (nodo != null) {
            mostrarInorden(nodo.izquierdo); // Visita rama izquierda
            System.out.println("Extensión: " + nodo.id + " | Oficina: " + nodo.nombre);
            mostrarInorden(nodo.derecho);   // Visita rama derecha
        }
    }

    // MÉTODO 3: BUSCAR (Retorna un mensaje según la existencia del ID)
    public String buscar(int id) {
        return buscarRecursivo(raiz, id) ? "ID encontrado en el sistema." : "El ID no existe.";
    }

    private boolean buscarRecursivo(Producto actual, int id) {
        if (actual == null) return false; // No se encontró
        if (id == actual.id) return true; // ¡Encontrado!

        // Decidir hacia qué rama bajar
        return id < actual.id
            ? buscarRecursivo(actual.izquierdo, id)
            : buscarRecursivo(actual.derecho, id);
    }
}
```

### Clase C: `Main.java` (Interfaz de Usuario)

```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArbolInventario miArbol = new ArbolInventario();
        Scanner sc = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n--- DIRECTORIO DE EXTENSIONES ---");
            System.out.println("1. Registrar Extensión");
            System.out.println("2. Ver Directorio (Ordenado)");
            System.out.println("3. Buscar Extensión");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese número de extensión: ");
                    int id = sc.nextInt();
                    sc.nextLine(); // Limpiar el buffer
                    System.out.print("Nombre de la oficina: ");
                    String nombre = sc.nextLine();
                    miArbol.insertar(id, nombre);
                    System.out.println("Registrado con éxito.");
                    break;
                case 2:
                    System.out.println("\nLISTADO ACTUAL:");
                    miArbol.mostrarInorden(miArbol.raiz);
                    break;
                case 3:
                    System.out.print("ID a buscar: ");
                    int buscaId = sc.nextInt();
                    System.out.println(miArbol.buscar(buscaId));
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
        sc.close();
    }
}
```
