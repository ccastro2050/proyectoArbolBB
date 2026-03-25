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
> **Que es:** Cambia a la rama `main`.
> **Para que:** Nos aseguramos de estar en la rama principal antes de crear una nueva.
> **Por que:** Siempre debemos partir de `main` para tener la version mas reciente del proyecto.

```powershell
git pull origin main
```
> **Que es:** Descarga los ultimos cambios desde GitHub (origin) a tu computadora.
> **Para que:** Traer cualquier cambio que se haya hecho en `main` desde la ultima vez.
> **Por que:** Si no hacemos pull, podriamos estar trabajando con codigo desactualizado.

```powershell
git checkout -b feature/recorridos
```
> **Que es:** Crea una rama nueva llamada `feature/recorridos` y se cambia a ella.
> **Para que:** Trabajar en un espacio aislado sin afectar `main`.
> **Por que:** La rama `main` esta protegida, no se puede subir codigo directamente. Todo debe ir por Pull Request.

---

## 2. Que vamos a agregar

El proyecto base solo tiene el recorrido **Inorden** (Izquierda-Raiz-Derecha). Ahora agregaremos los otros dos recorridos que vimos en el Paso 1:

| Recorrido | Orden de visita | Ya existe? |
|-----------|----------------|------------|
| Inorden | Izquierda → Raiz → Derecha | Si (codigo base) |
| **Preorden** | **Raiz → Izquierda → Derecha** | **No (lo agregaremos)** |
| **Postorden** | **Izquierda → Derecha → Raiz** | **No (lo agregaremos)** |

---

## 3. Modificar ArbolInventario.java

Abrir el archivo `src/ArbolInventario.java` en VS Code.

Agregar estos dos metodos **despues** del metodo `mostrarInorden` y **antes** del metodo `buscar`:

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

El archivo `ArbolInventario.java` debe quedar asi (las partes nuevas estan marcadas):

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

Abrir `src/Main.java` y agregar dos opciones nuevas al menu.

### 4.1 Agregar las opciones 4 y 5 en el texto del menu

Buscar estas lineas:

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

Buscar esta parte del switch:

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

1. Registrar estas extensiones en este orden: **50, 30, 70, 20, 40, 60, 80**
2. Usar opcion **2 (Inorden)** → debe mostrar: 20, 30, 40, 50, 60, 70, 80
3. Usar opcion **4 (Preorden)** → debe mostrar: 50, 30, 20, 40, 70, 60, 80
4. Usar opcion **5 (Postorden)** → debe mostrar: 20, 40, 30, 60, 80, 70, 50

Si los resultados coinciden, el codigo esta correcto.

---

## 6. Subir los cambios y crear el Pull Request

### 6.1 Agregar y hacer commit

```powershell
git add src/ArbolInventario.java src/Main.java
```
> **Que es:** Agrega los archivos modificados al area de preparacion (staging).
> **Para que:** Le dice a Git cuales archivos se incluiran en el proximo commit.
> **Por que:** Solo subimos los archivos `.java` que modificamos. No subimos los `.class` ni la carpeta `out` porque estan en el `.gitignore`.

```powershell
git commit -m "Agregar recorridos preorden y postorden"
```
> **Que es:** Crea un punto de guardado (commit) con un mensaje descriptivo.
> **Para que:** Registrar los cambios en el historial de Git con una descripcion clara.
> **Por que:** El mensaje ayuda a los companeros a entender que cambios contiene este commit.

### 6.2 Subir la rama

```powershell
git push origin feature/recorridos
```
> **Que es:** Sube la rama `feature/recorridos` al repositorio remoto en GitHub.
> **Para que:** Que los companeros puedan ver el codigo y crear el Pull Request.
> **Por que:** Los cambios solo existen en tu computadora hasta que haces push. Sin push, nadie mas puede verlos.

### 6.3 Si algo sale mal con Git (no te preocupes)

Esta es la primera vez que trabajas con ramas. Es normal que algo falle. Aqui las soluciones:

**Error: "nothing to commit, working tree clean"**
> Significa que no hay cambios para guardar. Puede pasar si olvidaste guardar los archivos en VS Code (`Ctrl + S`). Solucion:
> 1. Ir a VS Code y guardar todos los archivos (`Ctrl + S` en cada uno)
> 2. Volver a ejecutar `git add src/ArbolInventario.java src/Main.java`
> 3. Volver a ejecutar el `git commit`

**Error: "fatal: The current branch feature/recorridos has no upstream branch"**
> Significa que la rama no existe todavia en GitHub. Solucion:
> ```powershell
> git push -u origin feature/recorridos
> ```
> Esto la crea en GitHub y la sube al mismo tiempo.

**Error al compilar (javac muestra errores)**
> Revisar que el codigo este bien copiado. Los errores mas comunes son:
> - Falta una llave `}` o un punto y coma `;`
> - El metodo se pego en el lugar equivocado (debe estar dentro de la clase)
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
> **Tranquilo:** borrar una rama local no afecta a nadie mas. Solo estas borrando tu trabajo local que no funciono. El repositorio en GitHub sigue intacto.

### 6.4 Crear el Pull Request en GitHub

est1 crea el Pull Request y hace merge:

1. Ir al repositorio en GitHub
2. Aparecera un boton amarillo que dice **"Compare & pull request"**. Hacer clic
3. Titulo: `Agregar recorridos preorden y postorden`
4. Descripcion: `Se agregan los metodos mostrarPreorden y mostrarPostorden en ArbolInventario y las opciones 4 y 5 en el menu`
5. Hacer clic en **Create pull request**
6. Hacer clic en **Merge pull request** → **Confirm merge**
