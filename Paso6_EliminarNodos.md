# Paso 6: Eliminar Nodos (est2)

**Asignatura:** Estructuras de Datos
**Profesor:** Carlos Arturo Castro Castro

---

> **Este paso lo realiza est2.**

---

## 1. Crear la rama

Abrir PowerShell en la carpeta del proyecto:

```powershell
git checkout main
```
> Cambia a la rama `main`. Siempre debemos partir de `main` antes de crear una rama nueva, para no quedarnos con código viejo.

```powershell
git pull origin main
```
> Descarga los últimos cambios desde GitHub. Esto trae el código que est1 agregó en el Paso 5 (recorridos preorden y postorden). Si no hacemos pull, estaríamos trabajando con código desactualizado y habrá conflictos.

```powershell
git checkout -b feature/eliminar
```
> Crea una rama nueva llamada `feature/eliminar` y se cambia a ella. Trabajar en ramas separadas es la buena práctica: cada quien hace sus cambios sin afectar el código de los demás en `main`.

---

## 2. Que vamos a agregar

Eliminar un nodo de un ABB es la operación más compleja porque hay **3 casos diferentes** según la cantidad de hijos que tenga el nodo:

```
Caso 1: El nodo es una HOJA (no tiene hijos)
→ Se elimina directamente

Caso 2: El nodo tiene UN solo hijo
→ Se reemplaza por su único hijo

Caso 3: El nodo tiene DOS hijos
→ Se reemplaza por el SUCESOR INORDEN (el menor del subarbol derecho)
```

### Ejemplo visual de cada caso

Árbol de partida:

```
        [50]
        /    \
     [30]   [70]
     /  \   /  \
  [20] [40] [60] [80]
```

**Caso 1: Eliminar 20 (hoja, sin hijos)**

```
Antes:          Después:
     [30]            [30]
     /  \               \
  [20] [40]            [40]

Se quita [20] y listo. No hay nada más que hacer.
```

**Caso 2: Eliminar 30, suponiendo que solo tiene hijo derecho [40]**

```
Antes:          Después:
     [50]            [50]
     /                /
  [30]             [40]
     \
    [40]

Se reemplaza [30] por su único hijo [40].
```

**Caso 3: Eliminar 50 (tiene dos hijos: 30 y 70)**

```
Antes:                   Después:
        [50]                     [60]
        /    \                   /    \
     [30]   [70]             [30]   [70]
     /  \   /  \             /  \      \
  [20] [40] [60] [80]    [20] [40]   [80]

El sucesor inorden de 50 es el MENOR del subarbol derecho.
Subarbol derecho de 50: [70, 60, 80]. El menor es [60].
Se copia el 60 en lugar del 50, y luego se elimina el [60] original.
```

---

## 3. Modificar ArbolInventario.java

Abrir `src/ArbolInventario.java` en VS Code.

Agregar estos métodos **después** del último método existente (antes de la llave de cierre `}` de la clase):

```java
    // =============================================
    // ===== INICIO DE LO NUEVO (est2) =============
    // =============================================

    // METODO NUEVO: ELIMINAR (Punto de entrada)
    public void eliminar(int id) {
        raiz = eliminarRecursivo(raiz, id);
    }

    // Logica recursiva para eliminar
    private Producto eliminarRecursivo(Producto actual, int id) {
        // Si el nodo es null, el ID no existe en el árbol
        if (actual == null) {
            System.out.println("La extensión " + id + " no existe en el directorio.");
            return null;
        }

        // Buscar el nodo a eliminar
        if (id < actual.id) {
            actual.izquierdo = eliminarRecursivo(actual.izquierdo, id);
        } else if (id > actual.id) {
            actual.derecho = eliminarRecursivo(actual.derecho, id);
        } else {
            // ENCONTRAMOS EL NODO A ELIMINAR

            // CASO 1: Es una hoja (no tiene hijos)
            if (actual.izquierdo == null && actual.derecho == null) {
                System.out.println("Extensión " + id + " eliminada (era una hoja).");
                return null;
            }

            // CASO 2a: Solo tiene hijo derecho
            if (actual.izquierdo == null) {
                System.out.println("Extensión " + id + " eliminada (tenia un hijo).");
                return actual.derecho;
            }

            // CASO 2b: Solo tiene hijo izquierdo
            if (actual.derecho == null) {
                System.out.println("Extensión " + id + " eliminada (tenia un hijo).");
                return actual.izquierdo;
            }

            // CASO 3: Tiene dos hijos
            // Buscar el sucesor inorden (el menor del subarbol derecho)
            Producto sucesor = encontrarMinimo(actual.derecho);

            // Copiar los datos del sucesor en el nodo actual
            actual.id = sucesor.id;
            actual.nombre = sucesor.nombre;

            // Eliminar el sucesor de su posición original
            actual.derecho = eliminarRecursivo(actual.derecho, sucesor.id);
            System.out.println("Extensión eliminada (tenia dos hijos, reemplazada por " + sucesor.id + ").");
        }
        return actual;
    }

    // Método auxiliar: encontrar el nodo con el ID más pequeño de un subarbol
    private Producto encontrarMinimo(Producto nodo) {
        while (nodo.izquierdo != null) {
            nodo = nodo.izquierdo;
        }
        return nodo;
    }

    // =============================================
    // ===== FIN DE LO NUEVO (est2) ================
    // =============================================
```

---

## 4. Modificar Main.java

### 4.1 Agregar la opción 6 en el texto del menú

Buscar estás líneas:

```java
            System.out.println("5. Ver Postorden");
            System.out.println("0. Salir");
```

Cambiarlas por:

```java
            System.out.println("5. Ver Postorden");
            System.out.println("6. Eliminar Extension");
            System.out.println("0. Salir");
```

### 4.2 Agregar el case 6 en el switch

Buscar `case 0:` y agregar **antes** de el:

```java
                case 6:
                    System.out.print("ID de la extensión a eliminar: ");
                    int eliminarId = sc.nextInt();
                    miArbol.eliminar(eliminarId);
                    break;
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

1. Registrar estás extensiones: **50, 30, 70, 20, 40, 60, 80**

2. **Probar Caso 1 (hoja):** Eliminar la extensión 20
   - Usar opción 6, ingresar 20
   - Debe decir: "Extensión 20 eliminada (era una hoja)."
   - Ver directorio (opcion 2): ya no debe aparecer el 20

3. **Probar Caso 2 (un hijo):** Eliminar la extensión 30 (ahora solo tiene hijo derecho 40)
   - Usar opción 6, ingresar 30
   - Debe decir: "Extensión 30 eliminada (tenia un hijo)."
   - Ver directorio: ya no debe aparecer el 30, pero el 40 si

4. **Probar Caso 3 (dos hijos):** Registrar de nuevo 20 y 30, luego eliminar 50
   - Usar opción 6, ingresar 50
   - Debe decir: "Extensión eliminada (tenia dos hijos, reemplazada por 60)."
   - Ver directorio: el 50 ya no aparece, pero todos los demás si

5. **Probar ID inexistente:** Eliminar la extensión 999
   - Debe decir: "La extensión 999 no existe en el directorio."

---

## 6. Subir los cambios y crear el Pull Request

```powershell
git add src/ArbolInventario.java src/Main.java
```
> Agrega los archivos modificados al área de preparación. Solo subimos los `.java` que modificamos, los `.class` y la carpeta `out` se ignoran automáticamente por el `.gitignore`.

```powershell
git commit -m "Agregar eliminación de nodos con los 3 casos"
```
> Crea un punto de guardado (commit) con un mensaje descriptivo. El mensaje ayuda a los compañeros a entender que cambios contiene este commit sin tener que leer todo el código.

```powershell
git push origin feature/eliminar
```
> Sube la rama `feature/eliminar` a GitHub. Después de esto, est2 puede crear el Pull Request y est1 puede revisar y hacer merge.
> **Por que:** Los cambios solo existen en tu computadora hasta que haces push. Sin push, nadie más puede verlos.

### Crear el Pull Request en GitHub

> **Importante:** El botón amarillo "Compare & pull request" solo le aparece a quien hizo el push. Como est2 hizo el push, **est2 crea el PR**. Luego **est1 revisa y hace merge** porque es el dueño del repositorio.

**est2 (quien hizo push) crea el PR:**

1. Ir al repositorio en GitHub
2. GitHub muestra un banner amarillo: **"feature/eliminar had recent pushes"** → Hacer clic en **Compare & pull request**
3. Si no aparece el banner: ir a la pestaña **Pull requests** → **New pull request** → en "compare" seleccionar `feature/eliminar`
4. Título: `Agregar eliminación de nodos con los 3 casos`
5. Descripción:
   ```
   Se agrega el método eliminar en ArbolInventario con los 3 casos:
   - Caso 1: nodo hoja (sin hijos)
   - Caso 2: nodo con un solo hijo
   - Caso 3: nodo con dos hijos (sucesor inorden)
   Se agrega la opción 6 en el menú.
   ```
6. Hacer clic en **Create pull request**
7. **Avisarle a est1** que el PR está listo

**est1 (dueño del repositorio) revisa y hace merge:**

1. Ir a la pestaña **Pull requests** en GitHub
2. Abrir el PR que est2 acaba de crear
3. Revisar los cambios en la pestaña **Files changed**
4. Hacer clic en **Merge pull request** → **Confirm merge**
5. Hacer clic en **Delete branch** para limpiar la rama remota
