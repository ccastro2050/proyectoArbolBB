# Paso 8: Extension Minima y Maxima (est1)

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
> **Que es:** Cambia a la rama `main`.
> **Para que:** Nos aseguramos de estar en la rama principal antes de crear una nueva.
> **Por que:** Siempre debemos partir de `main` para tener la version mas reciente del proyecto con todos los cambios de los companeros.

```powershell
git pull origin main
```
> **Que es:** Descarga los ultimos cambios desde GitHub (origin) a tu computadora.
> **Para que:** Traer el codigo que est2 (eliminar) y est3 (estadisticas) ya agregaron.
> **Por que:** Si no hacemos pull, no tendriamos los metodos nuevos y habra conflictos al hacer merge.

```powershell
git checkout -b feature/min-max
```
> **Que es:** Crea una rama nueva llamada `feature/min-max` y se cambia a ella.
> **Para que:** Trabajar en un espacio aislado sin afectar `main`.
> **Por que:** La rama `main` esta protegida, no se puede subir codigo directamente. Todo debe ir por Pull Request.

---

## 2. Que vamos a agregar

Dos metodos que encuentran la extension mas pequena y la mas grande del arbol:

| Metodo | Que hace | Como funciona |
|--------|----------|---------------|
| `encontrarMinimoPublico()` | Encuentra la extension mas pequena | Baja siempre por la izquierda hasta no poder mas |
| `encontrarMaximo()` | Encuentra la extension mas grande | Baja siempre por la derecha hasta no poder mas |

### Por que funciona asi

En un ABB, los valores menores siempre estan a la **izquierda**. Entonces el nodo mas pequeno de todo el arbol es el que esta **mas a la izquierda de todo**.

Lo mismo al reves: el nodo mas grande esta **mas a la derecha de todo**.

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

Agregar estos metodos **despues** del ultimo metodo existente (antes de la llave de cierre `}` de la clase):

```java
    // =============================================
    // ===== INICIO DE LO NUEVO (est1) =============
    // =============================================

    // METODO NUEVO: ENCONTRAR MINIMO (publico)
    // Encuentra la extension mas pequena del arbol
    public String encontrarMinimoPublico() {
        if (raiz == null) {
            return "El arbol esta vacio.";
        }
        Producto nodo = raiz;
        // Bajar siempre por la izquierda hasta llegar al final
        while (nodo.izquierdo != null) {
            nodo = nodo.izquierdo;
        }
        return "Extension minima: " + nodo.id + " | Oficina: " + nodo.nombre;
    }

    // METODO NUEVO: ENCONTRAR MAXIMO
    // Encuentra la extension mas grande del arbol
    public String encontrarMaximo() {
        if (raiz == null) {
            return "El arbol esta vacio.";
        }
        Producto nodo = raiz;
        // Bajar siempre por la derecha hasta llegar al final
        while (nodo.derecho != null) {
            nodo = nodo.derecho;
        }
        return "Extension maxima: " + nodo.id + " | Oficina: " + nodo.nombre;
    }

    // =============================================
    // ===== FIN DE LO NUEVO (est1) ================
    // =============================================
```

### Por que usamos `while` y no recursion?

En los recorridos usamos recursion porque visitamos **todos** los nodos. Pero aqui solo necesitamos bajar por **un solo camino** (siempre izquierda o siempre derecha), asi que un `while` es mas sencillo y eficiente.

---

## 4. Modificar Main.java

### 4.1 Agregar las opciones 10 y 11 en el texto del menu

Buscar estas lineas:

```java
            System.out.println("9. Contar Hojas");
            System.out.println("0. Salir");
```

Cambiarlas por:

```java
            System.out.println("9. Contar Hojas");
            System.out.println("10. Extension Minima");
            System.out.println("11. Extension Maxima");
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

1. Registrar estas extensiones: **50, 30, 70, 20, 40, 60, 80**

2. **Probar opcion 10 (Extension Minima):**
   - Debe mostrar: `Extension minima: 20 | Oficina: Archivo`

3. **Probar opcion 11 (Extension Maxima):**
   - Debe mostrar: `Extension maxima: 80 | Oficina: Soporte`

4. **Probar con arbol vacio:** Salir del programa, volver a ejecutar (sin registrar nada), y probar opciones 10 y 11:
   - Ambas deben decir: `El arbol esta vacio.`

---

## 6. Subir los cambios y crear el Pull Request

```powershell
git add src/ArbolInventario.java src/Main.java
```
> **Que es:** Agrega los archivos modificados al area de preparacion (staging).
> **Para que:** Le dice a Git cuales archivos se incluiran en el proximo commit.
> **Por que:** Solo subimos los archivos `.java` que modificamos. Los `.class` compilados no se suben porque estan en el `.gitignore`.

```powershell
git commit -m "Agregar busqueda de extension minima y maxima"
```
> **Que es:** Crea un punto de guardado (commit) con un mensaje descriptivo.
> **Para que:** Registrar los cambios en el historial de Git con una descripcion clara.
> **Por que:** El mensaje ayuda a los companeros a entender que cambios contiene este commit sin tener que leer todo el codigo.

```powershell
git push origin feature/min-max
```
> **Que es:** Sube la rama `feature/min-max` al repositorio remoto en GitHub.
> **Para que:** Que los companeros puedan ver el codigo y crear el Pull Request.
> **Por que:** Los cambios solo existen en tu computadora hasta que haces push. Sin push, nadie mas puede verlos.

### Crear el Pull Request en GitHub

1. Ir al repositorio en GitHub
2. Aparecera un boton amarillo que dice **"Compare & pull request"**. Hacer clic
3. Titulo: `Agregar busqueda de extension minima y maxima`
4. Descripcion:
   ```
   Se agregan dos metodos en ArbolInventario:
   - encontrarMinimoPublico(): encuentra la extension mas pequena bajando por la izquierda
   - encontrarMaximo(): encuentra la extension mas grande bajando por la derecha
   Se agregan las opciones 10 y 11 en el menu.
   ```
5. Hacer clic en **Create pull request**

est1 crea el Pull Request y hace merge:

6. Hacer clic en **Merge pull request** → **Confirm merge**
