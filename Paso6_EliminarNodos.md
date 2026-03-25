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
> **Que es:** Cambia a la rama `main`.
> **Para que:** Nos aseguramos de estar en la rama principal antes de crear una nueva.
> **Por que:** Si creamos la rama nueva desde otra rama vieja, podriamos no tener los ultimos cambios.

```powershell
git pull origin main
```
> **Que es:** Descarga los ultimos cambios desde GitHub (origin) a tu computadora.
> **Para que:** Traer el codigo que est1 agrego en el Paso 5 (recorridos preorden y postorden).
> **Por que:** Si no hacemos pull, estariamos trabajando con codigo desactualizado y habra conflictos.

```powershell
git checkout -b feature/eliminar
```
> **Que es:** Crea una rama nueva llamada `feature/eliminar` y se cambia a ella.
> **Para que:** Trabajar en un espacio aislado sin afectar `main`.
> **Por que:** La rama `main` esta protegida, no se puede subir codigo directamente. Todo debe ir por Pull Request.

---

## 2. Que vamos a agregar

Eliminar un nodo de un ABB es la operacion mas compleja porque hay **3 casos diferentes** segun la cantidad de hijos que tenga el nodo:

```
Caso 1: El nodo es una HOJA (no tiene hijos)
→ Se elimina directamente

Caso 2: El nodo tiene UN solo hijo
→ Se reemplaza por su unico hijo

Caso 3: El nodo tiene DOS hijos
→ Se reemplaza por el SUCESOR INORDEN (el menor del subarbol derecho)
```

### Ejemplo visual de cada caso

Arbol de partida:

```
        [50]
        /    \
     [30]   [70]
     /  \   /  \
  [20] [40] [60] [80]
```

**Caso 1: Eliminar 20 (hoja, sin hijos)**

```
Antes:          Despues:
     [30]            [30]
     /  \               \
  [20] [40]            [40]

Se quita [20] y listo. No hay nada mas que hacer.
```

**Caso 2: Eliminar 30, suponiendo que solo tiene hijo derecho [40]**

```
Antes:          Despues:
     [50]            [50]
     /                /
  [30]             [40]
     \
    [40]

Se reemplaza [30] por su unico hijo [40].
```

**Caso 3: Eliminar 50 (tiene dos hijos: 30 y 70)**

```
Antes:                   Despues:
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

Agregar estos metodos **despues** del ultimo metodo existente (antes de la llave de cierre `}` de la clase):

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
        // Si el nodo es null, el ID no existe en el arbol
        if (actual == null) {
            System.out.println("La extension " + id + " no existe en el directorio.");
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
                System.out.println("Extension " + id + " eliminada (era una hoja).");
                return null;
            }

            // CASO 2a: Solo tiene hijo derecho
            if (actual.izquierdo == null) {
                System.out.println("Extension " + id + " eliminada (tenia un hijo).");
                return actual.derecho;
            }

            // CASO 2b: Solo tiene hijo izquierdo
            if (actual.derecho == null) {
                System.out.println("Extension " + id + " eliminada (tenia un hijo).");
                return actual.izquierdo;
            }

            // CASO 3: Tiene dos hijos
            // Buscar el sucesor inorden (el menor del subarbol derecho)
            Producto sucesor = encontrarMinimo(actual.derecho);

            // Copiar los datos del sucesor en el nodo actual
            actual.id = sucesor.id;
            actual.nombre = sucesor.nombre;

            // Eliminar el sucesor de su posicion original
            actual.derecho = eliminarRecursivo(actual.derecho, sucesor.id);
            System.out.println("Extension eliminada (tenia dos hijos, reemplazada por " + sucesor.id + ").");
        }
        return actual;
    }

    // Metodo auxiliar: encontrar el nodo con el ID mas pequeno de un subarbol
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

### 4.1 Agregar la opcion 6 en el texto del menu

Buscar estas lineas:

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
                    System.out.print("ID de la extension a eliminar: ");
                    int eliminarId = sc.nextInt();
                    miArbol.eliminar(eliminarId);
                    break;
```

---

## 5. Compilar y probar

```powershell
javac src/*.java -d out
```
> **Que es:** Compila todos los archivos `.java` de la carpeta `src` y genera los `.class` en `out`.
> **Para que:** Convertir el codigo fuente en bytecode que Java pueda ejecutar.
> **Por que:** Si hay errores de sintaxis, el compilador los mostrara aqui.

```powershell
java -cp out Main
```
> **Que es:** Ejecuta la clase `Main` buscando los `.class` en la carpeta `out`.
> **Para que:** Probar que el programa funciona correctamente con los cambios nuevos.
> **Por que:** Siempre debemos verificar que el codigo funciona antes de subirlo a GitHub.

### Prueba paso a paso

1. Registrar estas extensiones: **50, 30, 70, 20, 40, 60, 80**

2. **Probar Caso 1 (hoja):** Eliminar la extension 20
   - Usar opcion 6, ingresar 20
   - Debe decir: "Extension 20 eliminada (era una hoja)."
   - Ver directorio (opcion 2): ya no debe aparecer el 20

3. **Probar Caso 2 (un hijo):** Eliminar la extension 30 (ahora solo tiene hijo derecho 40)
   - Usar opcion 6, ingresar 30
   - Debe decir: "Extension 30 eliminada (tenia un hijo)."
   - Ver directorio: ya no debe aparecer el 30, pero el 40 si

4. **Probar Caso 3 (dos hijos):** Registrar de nuevo 20 y 30, luego eliminar 50
   - Usar opcion 6, ingresar 50
   - Debe decir: "Extension eliminada (tenia dos hijos, reemplazada por 60)."
   - Ver directorio: el 50 ya no aparece, pero todos los demas si

5. **Probar ID inexistente:** Eliminar la extension 999
   - Debe decir: "La extension 999 no existe en el directorio."

---

## 6. Subir los cambios y crear el Pull Request

```powershell
git add src/ArbolInventario.java src/Main.java
```
> **Que es:** Agrega los archivos modificados al area de preparacion (staging).
> **Para que:** Le dice a Git cuales archivos se incluiran en el proximo commit.
> **Por que:** Solo subimos los archivos `.java` que modificamos. No subimos los `.class` ni la carpeta `out` porque estan en el `.gitignore`.

```powershell
git commit -m "Agregar eliminacion de nodos con los 3 casos"
```
> **Que es:** Crea un punto de guardado (commit) con un mensaje descriptivo.
> **Para que:** Registrar los cambios en el historial de Git con una descripcion clara de lo que se hizo.
> **Por que:** El mensaje ayuda a los companeros a entender que cambios contiene este commit sin tener que leer todo el codigo.

```powershell
git push origin feature/eliminar
```
> **Que es:** Sube la rama `feature/eliminar` al repositorio remoto en GitHub.
> **Para que:** Que los companeros puedan ver el codigo y crear el Pull Request.
> **Por que:** Los cambios solo existen en tu computadora hasta que haces push. Sin push, nadie mas puede verlos.

### Crear el Pull Request en GitHub

est2 sube su rama. Luego **est1** va a GitHub, crea el Pull Request y hace merge:

1. est1 va al repositorio en GitHub
2. Aparecera un boton amarillo que dice **"Compare & pull request"**. Hacer clic
3. Titulo: `Agregar eliminacion de nodos con los 3 casos`
4. Descripcion:
   ```
   Se agrega el metodo eliminar en ArbolInventario con los 3 casos:
   - Caso 1: nodo hoja (sin hijos)
   - Caso 2: nodo con un solo hijo
   - Caso 3: nodo con dos hijos (sucesor inorden)
   Se agrega la opcion 6 en el menu.
   ```
5. Hacer clic en **Create pull request**
6. Hacer clic en **Merge pull request** → **Confirm merge**
