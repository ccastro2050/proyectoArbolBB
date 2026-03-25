# Paso 9: Busqueda con Detalle y Mostrar por Nivel (est2)

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
> **Por que:** Siempre debemos partir de `main` para tener la version mas reciente del proyecto con todos los cambios de los companeros.

```powershell
git pull origin main
```
> **Que es:** Descarga los ultimos cambios desde GitHub (origin) a tu computadora.
> **Para que:** Traer el codigo que est1 agrego en el Paso 8 (minimo y maximo).
> **Por que:** Si no hacemos pull, no tendriamos los metodos nuevos y habra conflictos al hacer merge.

```powershell
git checkout -b feature/busqueda-detalle
```
> **Que es:** Crea una rama nueva llamada `feature/busqueda-detalle` y se cambia a ella.
> **Para que:** Trabajar en un espacio aislado sin afectar `main`.
> **Por que:** La rama `main` esta protegida, no se puede subir codigo directamente. Todo debe ir por Pull Request.

---

## 2. Que vamos a agregar

Dos metodos nuevos:

| Metodo | Que hace |
|--------|----------|
| `buscarConDetalle(int id)` | Busca una extension y muestra su nombre de oficina (no solo si existe o no) |
| `mostrarNivel(nodo, nivelActual, nivelObjetivo)` | Muestra solo los nodos que estan en un nivel especifico del arbol |

### 2.1 Busqueda con detalle

El metodo `buscar()` que ya existe solo dice "ID encontrado" o "El ID no existe". El nuevo metodo devuelve toda la informacion del nodo.

```
Buscar extension 70:
- Metodo viejo: "ID encontrado en el sistema."
- Metodo nuevo: "Extension: 70 | Oficina: Sistemas"
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

**Como funciona:** Recorremos el arbol completo, pero solo imprimimos cuando el nivel actual es igual al nivel que queremos ver. Cada vez que bajamos un nivel, sumamos 1 al nivel actual.

---

## 3. Modificar ArbolInventario.java

Abrir `src/ArbolInventario.java` en VS Code.

Agregar estos metodos **despues** del ultimo metodo existente (antes de la llave de cierre `}` de la clase):

```java
    // =============================================
    // ===== INICIO DE LO NUEVO (est2) =============
    // =============================================

    // METODO NUEVO: BUSCAR CON DETALLE
    // Busca una extension y retorna toda su informacion
    public String buscarConDetalle(int id) {
        Producto resultado = buscarNodo(raiz, id);
        if (resultado == null) {
            return "La extension " + id + " no existe en el directorio.";
        }
        return "Extension: " + resultado.id + " | Oficina: " + resultado.nombre;
    }

    // Metodo auxiliar: busca y retorna el nodo completo (no solo true/false)
    private Producto buscarNodo(Producto actual, int id) {
        if (actual == null) {
            return null; // No se encontro
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
    // Muestra solo los nodos que estan en un nivel especifico
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

### 4.1 Agregar las opciones 12 y 13 en el texto del menu

Buscar estas lineas:

```java
            System.out.println("11. Extension Maxima");
            System.out.println("0. Salir");
```

Cambiarlas por:

```java
            System.out.println("11. Extension Maxima");
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

2. **Probar opcion 12 (Buscar con Detalle):**
   - Buscar 70 → debe mostrar: `Extension: 70 | Oficina: Sistemas`
   - Buscar 99 → debe mostrar: `La extension 99 no existe en el directorio.`

3. **Probar opcion 13 (Ver Nodos por Nivel):**
   - Nivel 0 → debe mostrar solo: `Extension: 50 | Oficina: Gerencia`
   - Nivel 1 → debe mostrar: extensiones 30 y 70
   - Nivel 2 → debe mostrar: extensiones 20, 40, 60, 80
   - Nivel 5 → no debe mostrar nada (no existe ese nivel)

---

## 6. Subir los cambios y crear el Pull Request

```powershell
git add src/ArbolInventario.java src/Main.java
```
> **Que es:** Agrega los archivos modificados al area de preparacion (staging).
> **Para que:** Le dice a Git cuales archivos se incluiran en el proximo commit.
> **Por que:** Solo subimos los archivos `.java` que modificamos. Los `.class` compilados no se suben porque estan en el `.gitignore`.

```powershell
git commit -m "Agregar busqueda con detalle y mostrar nodos por nivel"
```
> **Que es:** Crea un punto de guardado (commit) con un mensaje descriptivo.
> **Para que:** Registrar los cambios en el historial de Git con una descripcion clara.
> **Por que:** El mensaje ayuda a los companeros a entender que cambios contiene este commit sin tener que leer todo el codigo.

```powershell
git push origin feature/busqueda-detalle
```
> **Que es:** Sube la rama `feature/busqueda-detalle` al repositorio remoto en GitHub.
> **Para que:** Que los companeros puedan ver el codigo y crear el Pull Request.
> **Por que:** Los cambios solo existen en tu computadora hasta que haces push. Sin push, nadie mas puede verlos.

### Crear el Pull Request en GitHub

est2 sube su rama. Luego **est1** va a GitHub, crea el Pull Request y hace merge:

1. est1 va al repositorio en GitHub
2. Aparecera un boton amarillo que dice **"Compare & pull request"**. Hacer clic
3. Titulo: `Agregar busqueda con detalle y mostrar nodos por nivel`
4. Descripcion:
   ```
   Se agregan dos metodos en ArbolInventario:
   - buscarConDetalle(): busca una extension y retorna toda su informacion
   - mostrarNivel(): muestra solo los nodos de un nivel especifico del arbol
   Se agregan las opciones 12 y 13 en el menu.
   ```
5. Hacer clic en **Create pull request**
6. Hacer clic en **Merge pull request** → **Confirm merge**
