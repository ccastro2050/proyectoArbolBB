# Paso 9: Búsqueda con Detalle y Mostrar por Nivel (est2)

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
> Cambia a la rama `main`. Siempre debemos partir de `main` antes de crear una rama nueva, para tener la versión más reciente con todos los cambios de los compañeros.

```powershell
git pull origin main
```
> Descarga los últimos cambios desde GitHub. Esto trae el código que est1 agregó en el Paso 8 (minimo y maximo). Si no hacemos pull, no tendríamos los métodos nuevos y habrá conflictos.

```powershell
git checkout -b feature/busqueda-detalle
```
> Crea una rama nueva llamada `feature/busqueda-detalle` y se cambia a ella. Trabajar en ramas separadas es la buena práctica: cada quien hace sus cambios sin afectar el código de los demás en `main`.

---

## 2. Que vamos a agregar

Dos métodos nuevos:

| Método | Que hace |
|--------|----------|
| `buscarConDetalle(int id)` | Busca una extensión y muestra su nombre de oficina (no solo si existe o no) |
| `mostrarNivel(nodo, nivelActual, nivelObjetivo)` | Muestra solo los nodos que están en un nivel específico del árbol |

### 2.1 Búsqueda con detalle

El método `buscar()` que ya existe solo dice "ID encontrado" o "El ID no existe". El nuevo método devuelve toda la información del nodo.

```
Buscar extensión 70:
- Método viejo: "ID encontrado en el sistema."
- Método nuevo: "Extension: 70 | Oficina: Sistemas"
```

### 2.2 Mostrar por nivel

Permite ver solo los nodos de un nivel especifico:

```
        [50]           ← Nivel 0
        /    \
     [30]   [70]       ← Nivel 1
     /  \   /  \
  [20] [40] [60] [80]  ← Nivel 2

Mostrar nivel 0: solo [50]
Mostrar nivel 1: [30] y [70]
Mostrar nivel 2: [20], [40], [60] y [80]
```

**Como funciona:** Recorremos el árbol completo, pero solo imprimimos cuando el nivel actual es igual al nivel que queremos ver. Cada vez que bajamos un nivel, sumamos 1 al nivel actual.

---

## 3. Modificar ArbolInventario.java

Abrir `src/ArbolInventario.java` en VS Code.

Agregar estos métodos **después** del último método existente (antes de la llave de cierre `}` de la clase):

```java
    // =============================================
    // ===== INICIO DE LO NUEVO (est2) =============
    // =============================================

    // METODO NUEVO: BUSCAR CON DETALLE
    // Busca una extensión y retorna toda su información
    public String buscarConDetalle(int id) {
        Producto resultado = buscarNodo(raiz, id);
        if (resultado == null) {
            return "La extensión " + id + " no existe en el directorio.";
        }
        return "Extension: " + resultado.id + " | Oficina: " + resultado.nombre;
    }

    // Método auxiliar: busca y retorna el nodo completo (no solo true/false)
    private Producto buscarNodo(Producto actual, int id) {
        if (actual == null) {
            return null; // No se encontró
        }
        if (id == actual.id) {
            return actual; // Encontrado, retorna el nodo completo
        }
        // Decidir hacia que rama bajar
        if (id < actual.id) {
            return buscarNodo(actual.izquierdo, id);
        } else {
            return buscarNodo(actual.derecho, id);
        }
    }

    // METODO NUEVO: MOSTRAR NODOS POR NIVEL
    // Muestra solo los nodos que están en un nivel especifico
    public void mostrarNivel(Producto nodo, int nivelActual, int nivelObjetivo) {
        if (nodo == null) {
            return; // No hay nodo, no hacer nada
        }
        // Si estamos en el nivel que queremos, imprimir este nodo
        if (nivelActual == nivelObjetivo) {
            System.out.println("Extension: " + nodo.id + " | Oficina: " + nodo.nombre);
            return;
        }
        // Si no, bajar al siguiente nivel por ambos lados
        mostrarNivel(nodo.izquierdo, nivelActual + 1, nivelObjetivo);
        mostrarNivel(nodo.derecho, nivelActual + 1, nivelObjetivo);
    }

    // =============================================
    // ===== FIN DE LO NUEVO (est2) ================
    // =============================================
```

---

## 4. Modificar Main.java

### 4.1 Agregar las opciones 12 y 13 en el texto del menú

Buscar estás líneas:

```java
            System.out.println("11. Extensión Maxima");
            System.out.println("0. Salir");
```

Cambiarlas por:

```java
            System.out.println("11. Extensión Maxima");
            System.out.println("12. Buscar con Detalle");
            System.out.println("13. Ver Nodos por Nivel");
            System.out.println("0. Salir");
```

### 4.2 Agregar los case 12 y 13 en el switch

Buscar `case 0:` y agregar **antes** de el:

```java
                case 12:
                    System.out.print("ID a buscar: ");
                    int detalleId = sc.nextInt();
                    System.out.println(miArbol.buscarConDetalle(detalleId));
                    break;
                case 13:
                    System.out.print("Nivel a mostrar (0 = raiz): ");
                    int nivel = sc.nextInt();
                    System.out.println("Nodos en el nivel " + nivel + ":");
                    miArbol.mostrarNivel(miArbol.raiz, 0, nivel);
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

2. **Probar opción 12 (Buscar con Detalle):**
   - Buscar 70 → debe mostrar: `Extension: 70 | Oficina: Sistemas`
   - Buscar 99 → debe mostrar: `La extensión 99 no existe en el directorio.`

3. **Probar opción 13 (Ver Nodos por Nivel):**
   - Nivel 0 → debe mostrar solo: `Extension: 50 | Oficina: Gerencia`
   - Nivel 1 → debe mostrar: extensiones 30 y 70
   - Nivel 2 → debe mostrar: extensiones 20, 40, 60, 80
   - Nivel 5 → no debe mostrar nada (no existe ese nivel)

---

## 6. Subir los cambios y crear el Pull Request

```powershell
git add src/ArbolInventario.java src/Main.java
```
> Agrega los archivos modificados al área de preparación. Solo subimos los `.java` que modificamos, los `.class` compilados se ignoran automáticamente por el `.gitignore`.

```powershell
git commit -m "Agregar búsqueda con detalle y mostrar nodos por nivel"
```
> Crea un punto de guardado (commit) con un mensaje descriptivo. El mensaje ayuda a los compañeros a entender que cambios contiene este commit sin tener que leer todo el código.

```powershell
git push origin feature/busqueda-detalle
```
> Sube la rama `feature/busqueda-detalle` a GitHub. Después de esto, est2 puede crear el Pull Request y est1 puede revisar y hacer merge.
> **Por que:** Los cambios solo existen en tu computadora hasta que haces push. Sin push, nadie más puede verlos.

### Crear el Pull Request en GitHub

> **Recordar:** El botón amarillo solo le aparece a quien hizo el push. est2 crea el PR, est1 revisa y hace merge.

**est2 (quien hizo push) crea el PR:**

1. Ir al repositorio en GitHub
2. GitHub muestra un banner amarillo: **"feature/busqueda-detalle had recent pushes"** → Hacer clic en **Compare & pull request**
3. Si no aparece el banner: ir a la pestaña **Pull requests** → **New pull request** → en "compare" seleccionar `feature/busqueda-detalle`
4. Título: `Agregar búsqueda con detalle y mostrar nodos por nivel`
5. Descripción:
   ```
   Se agregan dos métodos en ArbolInventario:
   - buscarConDetalle(): busca una extensión y retorna toda su información
   - mostrarNivel(): muestra solo los nodos de un nivel específico del árbol
   Se agregan las opciones 12 y 13 en el menú.
   ```
6. Hacer clic en **Create pull request**
7. **Avisarle a est1** que el PR está listo

**est1 (dueño del repositorio) revisa y hace merge:**

1. Ir a la pestaña **Pull requests** en GitHub
2. Abrir el PR que est2 acaba de crear
3. Revisar los cambios en la pestaña **Files changed**
4. Hacer clic en **Merge pull request** → **Confirm merge**
5. Hacer clic en **Delete branch** para limpiar la rama remota
