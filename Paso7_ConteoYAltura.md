# Paso 7: Conteo de Nodos, Altura y Hojas (est3)

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
> Cambia a la rama `main`. Siempre debemos partir de `main` antes de crear una rama nueva, para no quedarnos sin los cambios de est1 y est2.

```powershell
git pull origin main
```
> Descarga los últimos cambios desde GitHub. Esto trae el código que est1 (recorridos) y est2 (eliminar) ya agregaron. Si no hacemos pull, nuestro código local no tendrá los métodos nuevos y habrá conflictos.

```powershell
git checkout -b feature/estadisticas
```
> Crea una rama nueva llamada `feature/estadisticas` y se cambia a ella. Trabajar en ramas separadas es la buena práctica: cada quien hace sus cambios sin afectar el código de los demás en `main`.

---

## 2. Que vamos a agregar

Tres métodos que nos dan información sobre la estructura del árbol:

| Método | Que hace | Ejemplo con el árbol de 7 nodos |
|--------|----------|--------------------------------|
| `contarNodos()` | Cuenta cuantos nodos tiene el árbol en total | 7 |
| `calcularAltura()` | Calcula cuantos niveles tiene el árbol | 3 |
| `contarHojas()` | Cuenta los nodos que no tienen hijos | 4 |

### Ejemplo visual

```
        [50]           ← Nivel 0
        /    \
     [30]   [70]       ← Nivel 1
     /  \   /  \
  [20] [40] [60] [80]  ← Nivel 2

Total de nodos: 7
Altura: 3 (niveles 0, 1 y 2 = 3 niveles)
Hojas: 4 (son 20, 40, 60, 80 porque no tienen hijos)
```

---

## 3. Entender la lógica antes de escribir el código

### 3.1 Contar nodos (recursión)

```
Para contar los nodos del árbol:
- Si el nodo es null → devuelvo 0 (no hay nada que contar)
- Si el nodo existe → cuento 1 (este nodo) + los de la izquierda + los de la derecha
```

### 3.2 Calcular altura (recursión)

```
Para calcular la altura:
- Si el nodo es null → devuelvo 0 (no hay niveles)
- Si el nodo existe → devuelvo 1 + el máximo entre la altura izquierda y la altura derecha
```

Ejemplo con [50]:
```
Altura izquierda de [50] = altura de [30] = 2
Altura derecha de [50] = altura de [70] = 2
Altura de [50] = 1 + max(2, 2) = 3
```

### 3.3 Contar hojas (recursión)

```
Para contar las hojas:
- Si el nodo es null → devuelvo 0
- Si el nodo NO tiene hijo izquierdo NI derecho → es una hoja, devuelvo 1
- Si tiene hijos → devuelvo las hojas de la izquierda + las hojas de la derecha
```

---

## 4. Modificar ArbolInventario.java

Abrir `src/ArbolInventario.java` en VS Code.

Agregar estos métodos **después** del último método existente (antes de la llave de cierre `}` de la clase):

```java
    // =============================================
    // ===== INICIO DE LO NUEVO (est3) =============
    // =============================================

    // METODO NUEVO: CONTAR NODOS
    // Cuenta cuantos nodos tiene el árbol en total
    public int contarNodos(Producto nodo) {
        if (nodo == null) {
            return 0; // Caso base: no hay nodo, no cuento nada
        }
        // Cuento 1 (este nodo) + los de la izquierda + los de la derecha
        return 1 + contarNodos(nodo.izquierdo) + contarNodos(nodo.derecho);
    }

    // METODO NUEVO: CALCULAR ALTURA
    // Calcula cuantos niveles tiene el árbol
    public int calcularAltura(Producto nodo) {
        if (nodo == null) {
            return 0; // Caso base: no hay nodo, altura es 0
        }
        // La altura es 1 + el máximo entre la altura izquierda y la derecha
        int alturaIzquierda = calcularAltura(nodo.izquierdo);
        int alturaDerecha = calcularAltura(nodo.derecho);
        return 1 + Math.max(alturaIzquierda, alturaDerecha);
    }

    // METODO NUEVO: CONTAR HOJAS
    // Cuenta los nodos que NO tienen hijos (hojas)
    public int contarHojas(Producto nodo) {
        if (nodo == null) {
            return 0; // Caso base: no hay nodo
        }
        // Si no tiene hijo izquierdo ni derecho, es una hoja
        if (nodo.izquierdo == null && nodo.derecho == null) {
            return 1;
        }
        // Si tiene hijos, sumar las hojas de cada lado
        return contarHojas(nodo.izquierdo) + contarHojas(nodo.derecho);
    }

    // =============================================
    // ===== FIN DE LO NUEVO (est3) ================
    // =============================================
```

---

## 5. Modificar Main.java

### 5.1 Agregar las opciones 7, 8 y 9 en el texto del menú

Buscar estás líneas:

```java
            System.out.println("6. Eliminar Extension");
            System.out.println("0. Salir");
```

Cambiarlas por:

```java
            System.out.println("6. Eliminar Extension");
            System.out.println("7. Contar Nodos");
            System.out.println("8. Altura del Arbol");
            System.out.println("9. Contar Hojas");
            System.out.println("0. Salir");
```

### 5.2 Agregar los case 7, 8 y 9 en el switch

Buscar `case 0:` y agregar **antes** de el:

```java
                case 7:
                    System.out.println("Total de nodos: " + miArbol.contarNodos(miArbol.raiz));
                    break;
                case 8:
                    System.out.println("Altura del árbol: " + miArbol.calcularAltura(miArbol.raiz));
                    break;
                case 9:
                    System.out.println("Total de hojas: " + miArbol.contarHojas(miArbol.raiz));
                    break;
```

---

## 6. Compilar y probar

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

2. **Probar opción 7 (Contar Nodos):**
   - Debe mostrar: `Total de nodos: 7`

3. **Probar opción 8 (Altura):**
   - Debe mostrar: `Altura del árbol: 3`

4. **Probar opción 9 (Contar Hojas):**
   - Debe mostrar: `Total de hojas: 4`

5. **Probar después de eliminar:** Eliminar la extensión 20 (opcion 6), luego:
   - Contar nodos: debe ser 6
   - Contar hojas: debe ser 3 (ahora 40, 60 y 80)

---

## 7. Subir los cambios y crear el Pull Request

```powershell
git add src/ArbolInventario.java src/Main.java
```
> Agrega los archivos modificados al área de preparación. Solo subimos los `.java` que modificamos, los `.class` compilados se ignoran automáticamente por el `.gitignore`.

```powershell
git commit -m "Agregar conteo de nodos, altura y conteo de hojas"
```
> Crea un punto de guardado (commit) con un mensaje descriptivo. El mensaje ayuda a los compañeros a entender que cambios contiene este commit sin tener que leer todo el código.

```powershell
git push origin feature/estadisticas
```
> Sube la rama `feature/estadisticas` a GitHub. Después de esto, est3 puede crear el Pull Request y est1 puede revisar y hacer merge.

### Crear el Pull Request en GitHub

> **Recordar:** El botón amarillo solo le aparece a quien hizo el push. est3 crea el PR, est1 revisa y hace merge.

**est3 (quien hizo push) crea el PR:**

1. Ir al repositorio en GitHub
2. GitHub muestra un banner amarillo: **"feature/estadisticas had recent pushes"** → Hacer clic en **Compare & pull request**
3. Si no aparece el banner: ir a la pestaña **Pull requests** → **New pull request** → en "compare" seleccionar `feature/estadisticas`
4. Título: `Agregar conteo de nodos, altura y conteo de hojas`
5. Descripción:
   ```
   Se agregan tres métodos en ArbolInventario:
   - contarNodos(): cuenta el total de nodos del árbol
   - calcularAltura(): calcula cuantos niveles tiene el árbol
   - contarHojas(): cuenta los nodos sin hijos
   Se agregan las opciones 7, 8 y 9 en el menú.
   ```
6. Hacer clic en **Create pull request**
7. **Avisarle a est1** que el PR está listo

**est1 (dueño del repositorio) revisa y hace merge:**

1. Ir a la pestaña **Pull requests** en GitHub
2. Abrir el PR que est3 acaba de crear
3. Revisar los cambios en la pestaña **Files changed**
4. Hacer clic en **Merge pull request** → **Confirm merge**
5. Hacer clic en **Delete branch** para limpiar la rama remota
