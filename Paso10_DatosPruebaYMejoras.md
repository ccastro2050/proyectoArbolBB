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
> **Que es:** Cambia a la rama `main`.
> **Para que:** Nos aseguramos de estar en la rama principal antes de crear una nueva.
> **Por que:** Siempre debemos partir de `main` para tener la version mas reciente del proyecto con todos los cambios de los companeros.

```powershell
git pull origin main
```
> **Que es:** Descarga los ultimos cambios desde GitHub (origin) a tu computadora.
> **Para que:** Traer todo el codigo que est1 y est2 agregaron en los pasos anteriores.
> **Por que:** Si no hacemos pull, no tendriamos los metodos nuevos y habra conflictos al hacer merge.

```powershell
git checkout -b feature/mejoras
```
> **Que es:** Crea una rama nueva llamada `feature/mejoras` y se cambia a ella.
> **Para que:** Trabajar en un espacio aislado sin afectar `main`.
> **Por que:** La rama `main` esta protegida, no se puede subir codigo directamente. Todo debe ir por Pull Request.

---

## 2. Que vamos a agregar

Este es el ultimo paso de codigo. Vamos a agregar tres mejoras:

| Mejora | Que hace |
|--------|----------|
| `cargarDatosDePrueba()` | Inserta 8 extensiones automaticamente para no tener que escribirlas cada vez |
| Validacion de arbol vacio | Antes de mostrar o buscar, verificar que el arbol tiene datos |
| `try-catch` | Atrapar errores cuando el usuario escribe letras en vez de numeros |

---

## 3. Modificar ArbolInventario.java

Abrir `src/ArbolInventario.java` en VS Code.

Agregar este metodo **despues** del constructor y **antes** del metodo `insertar`:

```java
    // =============================================
    // ===== INICIO DE LO NUEVO (est3) =============
    // =============================================

    // METODO NUEVO: CARGAR DATOS DE PRUEBA
    // Inserta extensiones predefinidas para probar el sistema rapidamente
    public void cargarDatosDePrueba() {
        insertar(50, "Gerencia");
        insertar(30, "Contabilidad");
        insertar(70, "Sistemas");
        insertar(20, "Archivo");
        insertar(40, "Recursos Humanos");
        insertar(60, "Ventas");
        insertar(80, "Soporte");
        insertar(10, "Recepcion");
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
        ArbolInventario miArbol = new ArbolInventario();
        Scanner sc = new Scanner(System.in);
        int opcion = -1;

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
            System.out.println("  10. Extension Minima");
            System.out.println("  11. Extension Maxima");
            System.out.println("  12. Buscar con Detalle");
            System.out.println("  13. Ver Nodos por Nivel");
            System.out.println("  0.  Salir");
            System.out.println("========================================");
            System.out.print("Seleccione una opcion: ");

            try {
                opcion = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Error: debe ingresar un numero.");
                sc.nextLine(); // Limpiar el buffer para que no entre en bucle infinito
                continue;      // Volver al inicio del while
            }

            switch (opcion) {
                case 1:
                    try {
                        System.out.print("Ingrese numero de extension: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Nombre de la oficina: ");
                        String nombre = sc.nextLine();
                        miArbol.insertar(id, nombre);
                        System.out.println("Registrado con exito.");
                    } catch (InputMismatchException e) {
                        System.out.println("Error: la extension debe ser un numero.");
                        sc.nextLine();
                    }
                    break;
                case 2:
                    if (miArbol.estaVacio()) {
                        System.out.println("El arbol esta vacio. Registre extensiones primero.");
                    } else {
                        System.out.println("\nLISTADO INORDEN (ordenado):");
                        miArbol.mostrarInorden(miArbol.raiz);
                    }
                    break;
                case 3:
                    if (miArbol.estaVacio()) {
                        System.out.println("El arbol esta vacio. Registre extensiones primero.");
                    } else {
                        System.out.print("ID a buscar: ");
                        int buscaId = sc.nextInt();
                        System.out.println(miArbol.buscar(buscaId));
                    }
                    break;
                case 4:
                    if (miArbol.estaVacio()) {
                        System.out.println("El arbol esta vacio. Registre extensiones primero.");
                    } else {
                        System.out.println("\nRECORRIDO PREORDEN:");
                        miArbol.mostrarPreorden(miArbol.raiz);
                    }
                    break;
                case 5:
                    if (miArbol.estaVacio()) {
                        System.out.println("El arbol esta vacio. Registre extensiones primero.");
                    } else {
                        System.out.println("\nRECORRIDO POSTORDEN:");
                        miArbol.mostrarPostorden(miArbol.raiz);
                    }
                    break;
                case 6:
                    if (miArbol.estaVacio()) {
                        System.out.println("El arbol esta vacio. No hay nada que eliminar.");
                    } else {
                        System.out.print("ID de la extension a eliminar: ");
                        int eliminarId = sc.nextInt();
                        miArbol.eliminar(eliminarId);
                    }
                    break;
                case 7:
                    System.out.println("Total de nodos: " + miArbol.contarNodos(miArbol.raiz));
                    break;
                case 8:
                    System.out.println("Altura del arbol: " + miArbol.calcularAltura(miArbol.raiz));
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
                        System.out.println("El arbol esta vacio. Registre extensiones primero.");
                    } else {
                        System.out.print("ID a buscar: ");
                        int detalleId = sc.nextInt();
                        System.out.println(miArbol.buscarConDetalle(detalleId));
                    }
                    break;
                case 13:
                    if (miArbol.estaVacio()) {
                        System.out.println("El arbol esta vacio. Registre extensiones primero.");
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
                    System.out.println("Opcion no valida.");
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
> **Que es:** Compila todos los archivos `.java` de la carpeta `src` y genera los `.class` en `out`.
> **Para que:** Convertir el codigo fuente en bytecode que Java pueda ejecutar.
> **Por que:** Si hay errores de sintaxis, el compilador los mostrara aqui antes de ejecutar.

```powershell
java -cp out Main
```
> **Que es:** Ejecuta la clase `Main` buscando los `.class` en la carpeta `out`.
> **Para que:** Probar que el programa funciona correctamente con los cambios nuevos.
> **Por que:** Siempre debemos verificar que el codigo funciona antes de subirlo a GitHub.

### Prueba paso a paso

1. **Probar datos de prueba:** Al iniciar, escribir `1` para cargar los datos
   - Debe decir: `Se cargaron 8 extensiones de prueba.`
   - Ver directorio (opcion 2): deben aparecer 8 extensiones ordenadas

2. **Probar validacion de arbol vacio:** Salir, volver a ejecutar, escribir `0` (no cargar datos)
   - Usar opcion 2 → debe decir: `El arbol esta vacio. Registre extensiones primero.`

3. **Probar try-catch:** En la seleccion de opcion, escribir una letra (por ejemplo `abc`)
   - Debe decir: `Error: debe ingresar un numero.`
   - El programa NO debe cerrarse, debe volver a mostrar el menu

4. **Probar menu completo:** Cargar datos de prueba y probar TODAS las opciones (1 al 13)

### El arbol con datos de prueba queda asi:

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
> **Que es:** Agrega los archivos modificados al area de preparacion (staging).
> **Para que:** Le dice a Git cuales archivos se incluiran en el proximo commit.
> **Por que:** Solo subimos los archivos `.java` que modificamos. Los `.class` compilados no se suben porque estan en el `.gitignore`.

```powershell
git commit -m "Agregar datos de prueba, validaciones y manejo de errores"
```
> **Que es:** Crea un punto de guardado (commit) con un mensaje descriptivo.
> **Para que:** Registrar los cambios en el historial de Git con una descripcion clara.
> **Por que:** El mensaje ayuda a los companeros a entender que cambios contiene este commit sin tener que leer todo el codigo.

```powershell
git push origin feature/mejoras
```
> **Que es:** Sube la rama `feature/mejoras` al repositorio remoto en GitHub.
> **Para que:** Que los companeros puedan ver el codigo y crear el Pull Request.
> **Por que:** Los cambios solo existen en tu computadora hasta que haces push. Sin push, nadie mas puede verlos.

### Crear el Pull Request en GitHub

est3 sube su rama, pero **est1 crea el Pull Request** porque es el dueno del repositorio:

1. est1 va al repositorio en GitHub
2. Aparecera un boton amarillo que dice **"Compare & pull request"**. Hacer clic
3. Titulo: `Agregar datos de prueba, validaciones y manejo de errores`
4. Descripcion:
   ```
   Se agregan mejoras finales:
   - cargarDatosDePrueba(): inserta 8 extensiones automaticamente
   - estaVacio(): valida antes de operar en un arbol sin datos
   - try-catch: manejo de errores cuando se ingresa texto en vez de numeros
   - Menu mejorado con formato y todas las 13 opciones
   ```
5. Hacer clic en **Create pull request**
6. Hacer clic en **Merge pull request** → **Confirm merge**
