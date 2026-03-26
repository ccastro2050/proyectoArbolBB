# Paso 10: Datos de Prueba y Mejoras Finales (est3)

**Asignatura:** Estructuras de Datos
**Profesor:** Carlos Arturo Castro Castro

---

> **Este paso lo realiza est3.**

---

## 1. Crear la rama

Abrir PowerShell en la carpeta del proyecto:

```powershell
git checkout main
```
> Cambia a la rama `main`. Siempre debemos partir de `main` antes de crear una rama nueva, para tener la versión más reciente con todos los cambios de los compañeros.

```powershell
git pull origin main
```
> Descarga los últimos cambios desde GitHub. Esto trae todo el código que est1 y est2 agregaron en los pasos anteriores. Si no hacemos pull, no tendríamos los métodos nuevos y habrá conflictos.

```powershell
git checkout -b feature/mejoras
```
> Crea una rama nueva llamada `feature/mejoras` y se cambia a ella. Trabajar en ramas separadas es la buena práctica: cada quien hace sus cambios sin afectar el código de los demás en `main`.

---

## 2. Que vamos a agregar

Este es el último paso de código. Vamos a agregar tres mejoras:

| Mejora | Que hace |
|--------|----------|
| `cargarDatosDePrueba()` | Inserta 8 extensiones automáticamente para no tener que escribirlas cada vez |
| Validación de árbol vacio | Antes de mostrar o buscar, verificar que el árbol tiene datos |
| `try-catch` | Atrapar errores cuando el usuario escribe letras en vez de números |

---

## 3. Modificar ArbolInventario.java

Abrir `src/ArbolInventario.java` en VS Code.

Agregar este método **después** del constructor y **antes** del método `insertar`:

```java
    // =============================================
    // ===== INICIO DE LO NUEVO (est3) =============
    // =============================================

    // METODO NUEVO: CARGAR DATOS DE PRUEBA
    // Inserta extensiones predefinidas para probar el sistema rápidamente
    public void cargarDatosDePrueba() {
        insertar(50, "Gerencia");
        insertar(30, "Contabilidad");
        insertar(70, "Sistemas");
        insertar(20, "Archivo");
        insertar(40, "Recursos Humanos");
        insertar(60, "Ventas");
        insertar(80, "Soporte");
        insertar(10, "Recepción");
        System.out.println("Se cargaron 8 extensiones de prueba.");
    }

    // METODO NUEVO: VERIFICAR SI EL ARBOL ESTA VACIO
    public boolean estaVacio() {
        return raiz == null;
    }

    // =============================================
    // ===== FIN DE LO NUEVO (est3) ================
    // =============================================
```

### Donde queda exactamente

```java
public class ArbolInventario {
    Producto raiz;

    public ArbolInventario() {
        this.raiz = null;
    }

    // ← AQUI VAN LOS METODOS NUEVOS (cargarDatosDePrueba y estaVacio)

    // METODO 1: INSERTAR
    public void insertar(int id, String nombre) {
        ...
```

---

## 4. Modificar Main.java (reescritura completa)

Este paso modifica Main.java significativamente. Para evitar errores, **reemplazar todo el contenido** del archivo `src/Main.java` por el siguiente codigo:

```java
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * Clase Main - Punto de entrada del programa e interfaz de usuario por consola.
 * Incluye datos de prueba, validaciones y manejo de errores.
 */
public class Main {
    public static void main(String[] args) {
        ArbolInventario miÁrbol = new ArbolInventario();
        Scanner sc = new Scanner(System.in);
        int opción = -1;

        // Preguntar si desea cargar datos de prueba al iniciar
        System.out.println("Desea cargar datos de prueba? (1 = Si, 0 = No): ");
        try {
            int respuesta = sc.nextInt();
            if (respuesta == 1) {
                miArbol.cargarDatosDePrueba();
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada no valida. Se continua sin datos de prueba.");
            sc.nextLine(); // Limpiar el buffer
        }

        while (opcion != 0) {
            System.out.println("\n========================================");
            System.out.println("    DIRECTORIO DE EXTENSIONES (ABB)");
            System.out.println("========================================");
            System.out.println("  1.  Registrar Extension");
            System.out.println("  2.  Ver Directorio (Inorden)");
            System.out.println("  3.  Buscar Extension");
            System.out.println("  4.  Ver Preorden");
            System.out.println("  5.  Ver Postorden");
            System.out.println("  6.  Eliminar Extension");
            System.out.println("  7.  Contar Nodos");
            System.out.println("  8.  Altura del Arbol");
            System.out.println("  9.  Contar Hojas");
            System.out.println("  10. Extensión Minima");
            System.out.println("  11. Extensión Maxima");
            System.out.println("  12. Buscar con Detalle");
            System.out.println("  13. Ver Nodos por Nivel");
            System.out.println("  0.  Salir");
            System.out.println("========================================");
            System.out.print("Seleccione una opcion: ");

            try {
                opción = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Error: debe ingresar un número.");
                sc.nextLine(); // Limpiar el buffer para que no entre en bucle infinito
                continue;      // Volver al inicio del while
            }

            switch (opcion) {
                case 1:
                    try {
                        System.out.print("Ingrese número de extension: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Nombre de la oficina: ");
                        String nombre = sc.nextLine();
                        miArbol.insertar(id, nombre);
                        System.out.println("Registrado con exito.");
                    } catch (InputMismatchException e) {
                        System.out.println("Error: la extensión debe ser un número.");
                        sc.nextLine();
                    }
                    break;
                case 2:
                    if (miArbol.estaVacio()) {
                        System.out.println("El árbol está vacio. Registre extensiones primero.");
                    } else {
                        System.out.println("\nLISTADO INORDEN (ordenado):");
                        miArbol.mostrarInorden(miArbol.raiz);
                    }
                    break;
                case 3:
                    if (miArbol.estaVacio()) {
                        System.out.println("El árbol está vacio. Registre extensiones primero.");
                    } else {
                        System.out.print("ID a buscar: ");
                        int buscaId = sc.nextInt();
                        System.out.println(miArbol.buscar(buscaId));
                    }
                    break;
                case 4:
                    if (miArbol.estaVacio()) {
                        System.out.println("El árbol está vacio. Registre extensiones primero.");
                    } else {
                        System.out.println("\nRECORRIDO PREORDEN:");
                        miArbol.mostrarPreorden(miArbol.raiz);
                    }
                    break;
                case 5:
                    if (miArbol.estaVacio()) {
                        System.out.println("El árbol está vacio. Registre extensiones primero.");
                    } else {
                        System.out.println("\nRECORRIDO POSTORDEN:");
                        miArbol.mostrarPostorden(miArbol.raiz);
                    }
                    break;
                case 6:
                    if (miArbol.estaVacio()) {
                        System.out.println("El árbol está vacio. No hay nada que eliminar.");
                    } else {
                        System.out.print("ID de la extensión a eliminar: ");
                        int eliminarId = sc.nextInt();
                        miArbol.eliminar(eliminarId);
                    }
                    break;
                case 7:
                    System.out.println("Total de nodos: " + miArbol.contarNodos(miArbol.raiz));
                    break;
                case 8:
                    System.out.println("Altura del árbol: " + miArbol.calcularAltura(miArbol.raiz));
                    break;
                case 9:
                    System.out.println("Total de hojas: " + miArbol.contarHojas(miArbol.raiz));
                    break;
                case 10:
                    System.out.println(miArbol.encontrarMinimoPublico());
                    break;
                case 11:
                    System.out.println(miArbol.encontrarMaximo());
                    break;
                case 12:
                    if (miArbol.estaVacio()) {
                        System.out.println("El árbol está vacio. Registre extensiones primero.");
                    } else {
                        System.out.print("ID a buscar: ");
                        int detalleId = sc.nextInt();
                        System.out.println(miArbol.buscarConDetalle(detalleId));
                    }
                    break;
                case 13:
                    if (miArbol.estaVacio()) {
                        System.out.println("El árbol está vacio. Registre extensiones primero.");
                    } else {
                        System.out.print("Nivel a mostrar (0 = raiz): ");
                        int nivel = sc.nextInt();
                        System.out.println("Nodos en el nivel " + nivel + ":");
                        miArbol.mostrarNivel(miArbol.raiz, 0, nivel);
                    }
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no valida.");
            }
        }
        sc.close();
    }
}
```

---

## 5. Compilar y probar

```powershell
javac src/*.java -d out
```
> Compila todos los archivos `.java` de la carpeta `src` y genera los `.class` en `out`. Si hay errores de sintaxis, el compilador los mostrara aquí antes de ejecutar.

```powershell
java -cp out Main
```
> Ejecuta el programa. Siempre debemos verificar que el código funciona antes de subirlo a GitHub.

### Prueba paso a paso

1. **Probar datos de prueba:** Al iniciar, escribir `1` para cargar los datos
   - Debe decir: `Se cargaron 8 extensiones de prueba.`
   - Ver directorio (opcion 2): deben aparecer 8 extensiones ordenadas

2. **Probar validacion de árbol vacio:** Salir, volver a ejecutar, escribir `0` (no cargar datos)
   - Usar opción 2 → debe decir: `El árbol está vacio. Registre extensiones primero.`

3. **Probar try-catch:** En la seleccion de opcion, escribir una letra (por ejemplo `abc`)
   - Debe decir: `Error: debe ingresar un número.`
   - El programa NO debe cerrarse, debe volver a mostrar el menú

4. **Probar menú completo:** Cargar datos de prueba y probar TODAS las opciones (1 al 13)

### El árbol con datos de prueba queda asi:

```
            [50]
           /    \
        [30]   [70]
        /  \   /  \
     [20] [40] [60] [80]
     /
  [10]
```

---

## 6. Subir los cambios y crear el Pull Request

```powershell
git add src/ArbolInventario.java src/Main.java
```
> Agrega los archivos modificados al área de preparación. Solo subimos los `.java` que modificamos, los `.class` compilados se ignoran automáticamente por el `.gitignore`.

```powershell
git commit -m "Agregar datos de prueba, validaciones y manejo de errores"
```
> Crea un punto de guardado (commit) con un mensaje descriptivo. El mensaje ayuda a los compañeros a entender que cambios contiene este commit sin tener que leer todo el código.

```powershell
git push origin feature/mejoras
```
> Sube la rama `feature/mejoras` a GitHub. Después de esto, est3 puede crear el Pull Request y est1 puede revisar y hacer merge.
> **Por que:** Los cambios solo existen en tu computadora hasta que haces push. Sin push, nadie más puede verlos.

### Crear el Pull Request en GitHub

> **Recordar:** El botón amarillo solo le aparece a quien hizo el push. est3 crea el PR, est1 revisa y hace merge.

**est3 (quien hizo push) crea el PR:**

1. Ir al repositorio en GitHub
2. GitHub muestra un banner amarillo: **"feature/mejoras had recent pushes"** → Hacer clic en **Compare & pull request**
3. Si no aparece el banner: ir a la pestaña **Pull requests** → **New pull request** → en "compare" seleccionar `feature/mejoras`
4. Título: `Agregar datos de prueba, validaciones y manejo de errores`
5. Descripción:
   ```
   Se agregan mejoras finales:
   - cargarDatosDePrueba(): inserta 8 extensiones automáticamente
   - estaVacio(): valida antes de operar en un árbol sin datos
   - try-catch: manejo de errores cuando se ingresa texto en vez de números
   - Menú mejorado con formato y todas las 13 opciones
   ```
6. Hacer clic en **Create pull request**
7. **Avisarle a est1** que el PR está listo

**est1 (dueño del repositorio) revisa y hace merge:**

1. Ir a la pestaña **Pull requests** en GitHub
2. Abrir el PR que est3 acaba de crear
3. Revisar los cambios en la pestaña **Files changed**
4. Hacer clic en **Merge pull request** → **Confirm merge**
5. Hacer clic en **Delete branch** para limpiar la rama remota
