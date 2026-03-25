# Ejercicio de Clase: Sistema de Directorio con Árboles Binarios (ABB)

**Asignatura:** Estructuras de Datos
**Profesor:** Carlos Arturo Castro Castro

---

## 1. Enunciado del Problema

Se requiere un sistema sencillo para gestionar un **Directorio de Extensiones Telefónicas**. El sistema debe permitir registrar una extensión (ID numérico) y el nombre de la oficina. Para garantizar búsquedas rápidas, los datos se organizarán en un **Árbol Binario de Búsqueda** bajo la regla: *valores menores a la izquierda, valores mayores a la derecha*.

---

## 2. Código Fuente en Java

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

---

## 3. Comandos Git para subir el proyecto a un repositorio público

### Paso 1: Inicializar el repositorio local

```bash
git init
```

Crea un repositorio Git vacío en la carpeta del proyecto. Esto genera una carpeta oculta `.git` donde se almacenará todo el historial de cambios.

### Paso 2: Agregar los archivos al área de preparación (staging)

```bash
git add .
```

Agrega **todos** los archivos del proyecto al área de preparación. Esto le dice a Git cuáles archivos serán incluidos en el próximo commit.

### Paso 3: Crear el primer commit

```bash
git commit -m "Primer commit: Sistema de Directorio con ABB"
```

Guarda una "fotografía" del estado actual de los archivos. El mensaje entre comillas describe los cambios realizados.

### Paso 4: Renombrar la rama principal a `main`

```bash
git branch -M main
```

Renombra la rama actual a `main`, que es el nombre estándar usado en GitHub para la rama principal.

### Paso 5: Conectar con el repositorio remoto en GitHub

```bash
git remote add origin https://github.com/TU_USUARIO/TU_REPOSITORIO.git
```

Vincula tu proyecto local con el repositorio que creaste en GitHub. Debes reemplazar `TU_USUARIO` y `TU_REPOSITORIO` con tus datos reales.

### Paso 6: Subir los archivos a GitHub

```bash
git push -u origin main
```

Sube todos los commits al repositorio remoto en GitHub. La bandera `-u` establece `origin/main` como la rama remota por defecto, de modo que en futuros push solo necesitas escribir `git push`.

---

### Resumen rápido (todos los comandos juntos)

```bash
git init
git add .
git commit -m "Primer commit: Sistema de Directorio con ABB"
git branch -M main
git remote add origin https://github.com/TU_USUARIO/TU_REPOSITORIO.git
git push -u origin main
```

> **Nota:** Antes de ejecutar estos comandos, debes crear el repositorio en GitHub (sin README ni .gitignore) desde tu cuenta en github.com/new.
