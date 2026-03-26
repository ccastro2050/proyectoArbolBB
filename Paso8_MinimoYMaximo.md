# Paso 8: Extensión Minima y Maxima (est1)

**Asignatura:** Estructuras de Datos
**Profesor:** Carlos Arturo Castro Castro

---

> **Este paso lo realiza est1.**
>
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
> Descarga los últimos cambios desde GitHub. Esto trae el código que est2 (eliminar) y est3 (estadisticas) ya agregaron. Si no hacemos pull, no tendríamos los métodos nuevos y habrá conflictos.

```powershell
git checkout -b feature/min-max
```
> Crea una rama nueva llamada `feature/min-max` y se cambia a ella. Trabajar en ramas separadas es la buena práctica: cada quien hace sus cambios sin afectar el código de los demás en `main`.

---

## 2. Que vamos a agregar

Dos métodos que encuentran la extensión más pequeña y la más grande del árbol:

| Método | Que hace | Como funciona |
|--------|----------|---------------|
| `encontrarMinimoPublico()` | Encuentra la extensión más pequeña | Baja siempre por la izquierda hasta no poder más |
| `encontrarMaximo()` | Encuentra la extensión más grande | Baja siempre por la derecha hasta no poder más |

### Por que funciona asi

En un ABB, los valores menores siempre están a la **izquierda**. Entonces el nodo más pequeño de todo el árbol es el que está **mas a la izquierda de todo**.

Lo mismo al reves: el nodo más grande está **mas a la derecha de todo**.

```
        [50]
        /    \
     [30]   [70]
     /  \   /  \
  [20] [40] [60] [80]

Minimo: bajar siempre por la izquierda → 50 → 30 → 20 (no tiene izquierdo, es el minimo)
Maximo: bajar siempre por la derecha → 50 → 70 → 80 (no tiene derecho, es el maximo)
```

---

## 3. Modificar ArbolInventario.java

Abrir `src/ArbolInventario.java` en VS Code.

Agregar estos métodos **después** del último método existente (antes de la llave de cierre `}` de la clase):

```java
    // =============================================
    // ===== INICIO DE LO NUEVO (est1) =============
    // =============================================

    // METODO NUEVO: ENCONTRAR MINIMO (publico)
    // Encuentra la extensión más pequeña del árbol
    public String encontrarMinimoPublico() {
        if (raiz == null) {
            return "El árbol está vacio.";
        }
        Producto nodo = raiz;
        // Bajar siempre por la izquierda hasta llegar al final
        while (nodo.izquierdo != null) {
            nodo = nodo.izquierdo;
        }
        return "Extensión minima: " + nodo.id + " | Oficina: " + nodo.nombre;
    }

    // METODO NUEVO: ENCONTRAR MAXIMO
    // Encuentra la extensión más grande del árbol
    public String encontrarMaximo() {
        if (raiz == null) {
            return "El árbol está vacio.";
        }
        Producto nodo = raiz;
        // Bajar siempre por la derecha hasta llegar al final
        while (nodo.derecho != null) {
            nodo = nodo.derecho;
        }
        return "Extensión maxima: " + nodo.id + " | Oficina: " + nodo.nombre;
    }

    // =============================================
    // ===== FIN DE LO NUEVO (est1) ================
    // =============================================
```

### Por que usamos `while` y no recursión?

En los recorridos usamos recursión porque visitamos **todos** los nodos. Pero aquí solo necesitamos bajar por **un solo camino** (siempre izquierda o siempre derecha), así que un `while` es más sencillo y eficiente.

---

## 4. Modificar Main.java

### 4.1 Agregar las opciones 10 y 11 en el texto del menú

Buscar estás líneas:

```java
            System.out.println("9. Contar Hojas");
            System.out.println("0. Salir");
```

Cambiarlas por:

```java
            System.out.println("9. Contar Hojas");
            System.out.println("10. Extensión Minima");
            System.out.println("11. Extensión Maxima");
            System.out.println("0. Salir");
```

### 4.2 Agregar los case 10 y 11 en el switch

Buscar `case 0:` y agregar **antes** de el:

```java
                case 10:
                    System.out.println(miArbol.encontrarMinimoPublico());
                    break;
                case 11:
                    System.out.println(miArbol.encontrarMaximo());
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

2. **Probar opción 10 (Extensión Minima):**
   - Debe mostrar: `Extensión minima: 20 | Oficina: Archivo`

3. **Probar opción 11 (Extensión Maxima):**
   - Debe mostrar: `Extensión maxima: 80 | Oficina: Soporte`

4. **Probar con árbol vacio:** Salir del programa, volver a ejecutar (sin registrar nada), y probar opciones 10 y 11:
   - Ambas deben decir: `El árbol está vacio.`

---

## 6. Subir los cambios y crear el Pull Request

```powershell
git add src/ArbolInventario.java src/Main.java
```
> Agrega los archivos modificados al área de preparación. Solo subimos los `.java` que modificamos, los `.class` compilados se ignoran automáticamente por el `.gitignore`.

```powershell
git commit -m "Agregar búsqueda de extensión mínima y maxima"
```
> Crea un punto de guardado (commit) con un mensaje descriptivo. El mensaje ayuda a los compañeros a entender que cambios contiene este commit sin tener que leer todo el código.

```powershell
git push origin feature/min-max
```
> Sube la rama `feature/min-max` a GitHub. Después de esto, se puede crear el Pull Request.
> **Por que:** Los cambios solo existen en tu computadora hasta que haces push. Sin push, nadie más puede verlos.

### Crear el Pull Request en GitHub

Como este paso lo hizo est1, est1 crea el PR y hace merge:

1. Ir al repositorio en GitHub
2. GitHub muestra un banner amarillo: **"feature/min-max had recent pushes"** → Hacer clic en **Compare & pull request**
3. Si no aparece el banner: ir a la pestaña **Pull requests** → **New pull request** → en "compare" seleccionar `feature/min-max`
4. Título: `Agregar búsqueda de extensión mínima y maxima`
5. Descripción:
   ```
   Se agregan dos métodos en ArbolInventario:
   - encontrarMinimoPublico(): encuentra la extensión más pequeña bajando por la izquierda
   - encontrarMaximo(): encuentra la extensión más grande bajando por la derecha
   Se agregan las opciones 10 y 11 en el menú.
   ```
6. Hacer clic en **Create pull request**
7. Revisar los cambios en la pestaña **Files changed**
8. Hacer clic en **Merge pull request** → **Confirm merge**
9. Hacer clic en **Delete branch** para limpiar la rama remota
