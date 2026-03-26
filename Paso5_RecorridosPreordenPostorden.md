# Paso 5: Recorridos Preorden y Postorden (est1)

**Asignatura:** Estructuras de Datos
**Profesor:** Carlos Arturo Castro Castro

---

> **Este paso lo realiza est1.**

---

## 1. Crear la rama

Abrir PowerShell en la carpeta del proyecto:

```powershell
git checkout main
```
> Cambia a la rama `main`. Siempre debemos partir de `main` antes de crear una rama nueva, para asegurarnos de tener la versión más reciente del proyecto.

```powershell
git pull origin main
```
> Descarga los últimos cambios desde GitHub a tu computadora. Si no hacemos pull, podríamos estar trabajando con código desactualizado que ya fue modificado por otro compañero.

```powershell
git checkout -b feature/recorridos
```
> Crea una rama nueva llamada `feature/recorridos` y se cambia a ella. Trabajar en ramas separadas es la buena práctica: cada quien hace sus cambios sin afectar el código de los demás en `main`.

---

## 2. Que vamos a agregar

El proyecto base solo tiene el recorrido **Inorden** (Izquierda-Raiz-Derecha). Ahora agregaremos los otros dos recorridos que vimos en el Paso 1:

| Recorrido | Orden de visita | Ya existe? |
|-----------|----------------|------------|
| Inorden | Izquierda → Raiz → Derecha | Si (código base) |
| **Preorden** | **Raiz → Izquierda → Derecha** | **No (lo agregaremos)** |
| **Postorden** | **Izquierda → Derecha → Raiz** | **No (lo agregaremos)** |

---

## 3. Modificar ArbolInventario.java

Abrir el archivo `src/ArbolInventario.java` en VS Code.

Agregar estos dos métodos **después** del método `mostrarInorden` y **antes** del método `buscar`:

```java
    // METODO NUEVO: MOSTRAR PREORDEN (Raiz - Izquierda - Derecha)
    public void mostrarPreorden(Producto nodo) {
        if (nodo != null) {
            System.out.println("Extension: " + nodo.id + " | Oficina: " + nodo.nombre);
            mostrarPreorden(nodo.izquierdo);
            mostrarPreorden(nodo.derecho);
        }
    }

    // METODO NUEVO: MOSTRAR POSTORDEN (Izquierda - Derecha - Raiz)
    public void mostrarPostorden(Producto nodo) {
        if (nodo != null) {
            mostrarPostorden(nodo.izquierdo);
            mostrarPostorden(nodo.derecho);
            System.out.println("Extension: " + nodo.id + " | Oficina: " + nodo.nombre);
        }
    }
```

### Donde queda exactamente

El archivo `ArbolInventario.java` debe quedar así (las partes nuevas están marcadas):

```java
public class ArbolInventario {
    Producto raiz;

    public ArbolInventario() {
        this.raiz = null;
    }

    // METODO 1: INSERTAR
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

    // METODO 2: MOSTRAR INORDEN (Izquierda - Raiz - Derecha)
    public void mostrarInorden(Producto nodo) {
        if (nodo != null) {
            mostrarInorden(nodo.izquierdo);
            System.out.println("Extension: " + nodo.id + " | Oficina: " + nodo.nombre);
            mostrarInorden(nodo.derecho);
        }
    }

    // =============================================
    // ===== INICIO DE LO NUEVO (est1) =============
    // =============================================

    // METODO 3: MOSTRAR PREORDEN (Raiz - Izquierda - Derecha)
    public void mostrarPreorden(Producto nodo) {
        if (nodo != null) {
            System.out.println("Extension: " + nodo.id + " | Oficina: " + nodo.nombre);
            mostrarPreorden(nodo.izquierdo);
            mostrarPreorden(nodo.derecho);
        }
    }

    // METODO 4: MOSTRAR POSTORDEN (Izquierda - Derecha - Raiz)
    public void mostrarPostorden(Producto nodo) {
        if (nodo != null) {
            mostrarPostorden(nodo.izquierdo);
            mostrarPostorden(nodo.derecho);
            System.out.println("Extension: " + nodo.id + " | Oficina: " + nodo.nombre);
        }
    }

    // =============================================
    // ===== FIN DE LO NUEVO (est1) ================
    // =============================================

    // METODO 5: BUSCAR
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
}
```

---

## 4. Modificar Main.java

Abrir `src/Main.java` y agregar dos opciones nuevas al menú.

### 4.1 Agregar las opciones 4 y 5 en el texto del menú

Buscar estás líneas:

```java
            System.out.println("3. Buscar Extension");
            System.out.println("0. Salir");
```

Cambiarlas por:

```java
            System.out.println("3. Buscar Extension");
            System.out.println("4. Ver Preorden");
            System.out.println("5. Ver Postorden");
            System.out.println("0. Salir");
```

### 4.2 Agregar los case 4 y 5 en el switch

Buscar está parte del switch:

```java
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
```

Agregar **antes** de `case 0` estos dos casos nuevos:

```java
                case 4:
                    System.out.println("\nRECORRIDO PREORDEN:");
                    miArbol.mostrarPreorden(miArbol.raiz);
                    break;
                case 5:
                    System.out.println("\nRECORRIDO POSTORDEN:");
                    miArbol.mostrarPostorden(miArbol.raiz);
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

1. Registrar estás extensiones en este orden: **50, 30, 70, 20, 40, 60, 80**
2. Usar opción **2 (Inorden)** → debe mostrar: 20, 30, 40, 50, 60, 70, 80
3. Usar opción **4 (Preorden)** → debe mostrar: 50, 30, 20, 40, 70, 60, 80
4. Usar opción **5 (Postorden)** → debe mostrar: 20, 40, 30, 60, 80, 70, 50

Si los resultados coinciden, el código está correcto.

---

## 6. Subir los cambios y crear el Pull Request

### 6.1 Agregar y hacer commit

```powershell
git add src/ArbolInventario.java src/Main.java
```
> Agrega los archivos modificados al área de preparación. Solo subimos los `.java` que modificamos, los `.class` y la carpeta `out` se ignoran automáticamente por el `.gitignore`.

```powershell
git commit -m "Agregar recorridos preorden y postorden"
```
> Crea un punto de guardado (commit) con un mensaje descriptivo. El mensaje ayuda a los compañeros a entender que cambios contiene este commit sin tener que leer todo el código.

### 6.2 Subir la rama

```powershell
git push origin feature/recorridos
```
> Sube la rama `feature/recorridos` a GitHub. Los cambios solo existen en tu computadora hasta que haces push. Después de esto, se puede crear el Pull Request en GitHub.

### 6.3 Si algo sale mal con Git (no te preocupes)

Esta es la primera vez que trabajas con ramas. Es normal que algo falle. Aqui las soluciones:

**Error: "nothing to commit, working tree clean"**
> Significa que no hay cambios para guardar. Puede pasar si olvidaste guardar los archivos en VS Code (`Ctrl + S`). Solución:
> 1. Ir a VS Code y guardar todos los archivos (`Ctrl + S` en cada uno)
> 2. Volver a ejecutar `git add src/ArbolInventario.java src/Main.java`
> 3. Volver a ejecutar el `git commit`

**Error: "fatal: The current branch feature/recorridos has no upstream branch"**
> Significa que la rama no existe todavía en GitHub. Solución:
> ```powershell
> git push -u origin feature/recorridos
> ```
> Esto la crea en GitHub y la sube al mismo tiempo.

**Error al compilar (javac muestra errores)**
> Revisar que el código este bien copiado. Los errores más comunes son:
> - Falta una llave `}` o un punto y coma `;`
> - El método se pego en el lugar equivocado (debe estar dentro de la clase)
>
> Corregir el error, guardar, y luego:
> ```powershell
> javac src/*.java -d out
> git add src/ArbolInventario.java src/Main.java
> git commit -m "Corregir error en recorridos"
> git push origin feature/recorridos
> ```

**"Me equivoque en todo y quiero empezar este paso de cero"**
> No hay problema. Puedes descartar todos los cambios y volver al estado limpio:
> ```powershell
> git checkout main
> git branch -D feature/recorridos
> git pull origin main
> ```
> Esto borra la rama con tus cambios y te deja en `main` limpio. Luego vuelves a empezar desde el paso 1 de este archivo (crear la rama).
>
> **Tranquilo:** borrar una rama local no afecta a nadie más. Solo estás borrando tu trabajo local que no funcionó. El repositorio en GitHub sigue intacto.

### 6.4 Crear el Pull Request en GitHub

> **Que es un Pull Request (PR)?** Es una solicitud para integrar los cambios de una rama a `main`. En vez de meter código directo a `main`, el PR permite:
> - Ver exactamente que archivos cambiaron (línea por línea)
> - Que el dueño del repositorio (est1) revise el código antes de integrarlo
> - Tener un historial de que se integró, cuando y quien lo hizo
>
> **Quien crea el PR?** Lo crea quien hizo el push, porque GitHub le muestra un botón amarillo solo a esa persona. Si est1 hizo push, est1 crea el PR. Si est2 o est3 hicieron push, ellos crean el PR.
>
> **Quien hace merge?** Solo est1, porque es el dueño del repositorio y de la rama `main`.
>
> **¿Qué pasa si est1 no acepta el PR?** El código se queda en la rama pero no entra a `main`. El PR queda abierto en GitHub esperando. Est1 puede escribir un comentario explicando qué hay que corregir, y el estudiante que hizo el push puede hacer más commits en la misma rama para arreglar el problema. Cuando est1 esté conforme, hace merge.
>
> **¿Qué pasa si el estudiante no crea el PR después de hacer push?** La rama queda subida en GitHub pero nadie la revisa. El código no entra a `main` y los demás compañeros no pueden usar esos cambios. Sin PR, es como si el trabajo no existiera para el equipo.

Como este paso lo hizo est1, est1 crea el PR y hace merge.

**Paso a paso en GitHub:**

1. Ir al repositorio en GitHub

2. GitHub muestra un banner amarillo que dice algo como: **"feature/recorridos had recent pushes"** con un botón **Compare & pull request**.

> Ese banner aparece porque GitHub detectó que alguien acaba de subir una rama nueva. "Recent pushes" significa "subidas recientes". El botón **Compare & pull request** significa: "comparar los cambios de esa rama contra `main` y crear una solicitud para integrarlos". Hacer clic en ese botón.

3. Si no aparece el banner (puede pasar si paso mucho tiempo desde el push): ir a la pestaña **Pull requests** (arriba en el repositorio) → **New pull request** → en el dropdown "compare" seleccionar `feature/recorridos`. Esto hace lo mismo que el botón amarillo pero de forma manual.

4. GitHub muestra un formulario para describir el PR. Llenar:
   - Título: `Agregar recorridos preorden y postorden`
   - Descripción: `Se agregan los métodos mostrarPreorden y mostrarPostorden en ArbolInventario y las opciones 4 y 5 en el menú`

5. Hacer clic en **Create pull request**.

> Esto NO integra los cambios todavía. Solo crea la solicitud. GitHub ahora muestra una página con el PR abierto donde se puede ver exactamente que archivos cambiaron y que líneas se agregaron o modificaron. En un equipo real, aquí es donde un compañero revisaría el código antes de aprobarlo.

6. Revisar los cambios en la pestaña **Files changed**. Ahi se ven las líneas nuevas en verde y las eliminadas en rojo. Verificar que todo se ve bien.

7. Hacer clic en **Merge pull request**.

> **Que es merge?** Merge significa "fusionar". Al hacer clic, le estamos diciendo a GitHub: "toma todos los cambios de la rama `feature/recorridos` e integralos a `main`". Después de esto, `main` tendrá el código nuevo.

8. Hacer clic en **Confirm merge** para confirmar.

> Listo. Los cambios de la rama `feature/recorridos` ahora están en `main`. La rama ya cumplió su propósito.

9. GitHub muestra un botón **Delete branch**. Hacer clic.

> Esto solo borra la rama en GitHub, no borra el código (el código ya está seguro en `main`). Es para no acumular ramas viejas que ya se integraron. Si no se borra, no pasa nada malo, solo queda una rama huérfana que ya no se usa y ensucia la lista de ramas.

10. **¿Y si después quiero ver qué hizo cada estudiante?** Aunque la rama se borró, el Pull Request queda guardado para siempre en GitHub. Ir a la pestaña **Pull requests** → **Closed** y ahí aparecen todos los PRs anteriores con el nombre de quien lo creó, la fecha, y todos los archivos que se cambiaron línea por línea. Esa es la forma de ver el historial de contribuciones de cada estudiante.
