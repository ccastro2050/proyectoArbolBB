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
> **Que es:** Cambia a la rama `main`.
> **Para que:** Nos aseguramos de estar en la rama principal antes de crear una nueva.
> **Por que:** Si creamos la rama desde otra rama vieja, no tendriamos los cambios de est1 y est2.

```powershell
git pull origin main
```
> **Que es:** Descarga los ultimos cambios desde GitHub (origin) a tu computadora.
> **Para que:** Traer el codigo que est1 (recorridos) y est2 (eliminar) ya agregaron.
> **Por que:** Si no hacemos pull, nuestro codigo local no tendra los metodos nuevos y habra conflictos al hacer merge.

```powershell
git checkout -b feature/estadisticas
```
> **Que es:** Crea una rama nueva llamada `feature/estadisticas` y se cambia a ella.
> **Para que:** Trabajar en un espacio aislado sin afectar `main`.
> **Por que:** La rama `main` esta protegida, no se puede subir codigo directamente. Todo debe ir por Pull Request.

---

## 2. Que vamos a agregar

Tres metodos que nos dan informacion sobre la estructura del arbol:

| Metodo | Que hace | Ejemplo con el arbol de 7 nodos |
|--------|----------|--------------------------------|
| `contarNodos()` | Cuenta cuantos nodos tiene el arbol en total | 7 |
| `calcularAltura()` | Calcula cuantos niveles tiene el arbol | 3 |
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

## 3. Entender la logica antes de escribir el codigo

### 3.1 Contar nodos (recursion)

```
Para contar los nodos del arbol:
- Si el nodo es null → devuelvo 0 (no hay nada que contar)
- Si el nodo existe → cuento 1 (este nodo) + los de la izquierda + los de la derecha
```

### 3.2 Calcular altura (recursion)

```
Para calcular la altura:
- Si el nodo es null → devuelvo 0 (no hay niveles)
- Si el nodo existe → devuelvo 1 + el maximo entre la altura izquierda y la altura derecha
```

Ejemplo con [50]:
```
Altura izquierda de [50] = altura de [30] = 2
Altura derecha de [50] = altura de [70] = 2
Altura de [50] = 1 + max(2, 2) = 3
```

### 3.3 Contar hojas (recursion)

```
Para contar las hojas:
- Si el nodo es null → devuelvo 0
- Si el nodo NO tiene hijo izquierdo NI derecho → es una hoja, devuelvo 1
- Si tiene hijos → devuelvo las hojas de la izquierda + las hojas de la derecha
```

---

## 4. Modificar ArbolInventario.java

Abrir `src/ArbolInventario.java` en VS Code.

Agregar estos metodos **despues** del ultimo metodo existente (antes de la llave de cierre `}` de la clase):

```java
    // =============================================
    // ===== INICIO DE LO NUEVO (est3) =============
    // =============================================

    // METODO NUEVO: CONTAR NODOS
    // Cuenta cuantos nodos tiene el arbol en total
    public int contarNodos(Producto nodo) {
        if (nodo == null) {
            return 0; // Caso base: no hay nodo, no cuento nada
        }
        // Cuento 1 (este nodo) + los de la izquierda + los de la derecha
        return 1 + contarNodos(nodo.izquierdo) + contarNodos(nodo.derecho);
    }

    // METODO NUEVO: CALCULAR ALTURA
    // Calcula cuantos niveles tiene el arbol
    public int calcularAltura(Producto nodo) {
        if (nodo == null) {
            return 0; // Caso base: no hay nodo, altura es 0
        }
        // La altura es 1 + el maximo entre la altura izquierda y la derecha
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

### 5.1 Agregar las opciones 7, 8 y 9 en el texto del menu

Buscar estas lineas:

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
                    System.out.println("Altura del arbol: " + miArbol.calcularAltura(miArbol.raiz));
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

2. **Probar opcion 7 (Contar Nodos):**
   - Debe mostrar: `Total de nodos: 7`

3. **Probar opcion 8 (Altura):**
   - Debe mostrar: `Altura del arbol: 3`

4. **Probar opcion 9 (Contar Hojas):**
   - Debe mostrar: `Total de hojas: 4`

5. **Probar despues de eliminar:** Eliminar la extension 20 (opcion 6), luego:
   - Contar nodos: debe ser 6
   - Contar hojas: debe ser 3 (ahora 40, 60 y 80)

---

## 7. Subir los cambios y crear el Pull Request

```powershell
git add src/ArbolInventario.java src/Main.java
```
> **Que es:** Agrega los archivos modificados al area de preparacion (staging).
> **Para que:** Le dice a Git cuales archivos se incluiran en el proximo commit.
> **Por que:** Solo subimos los archivos `.java` que modificamos. Los `.class` compilados no se suben porque estan en el `.gitignore`.

```powershell
git commit -m "Agregar conteo de nodos, altura y conteo de hojas"
```
> **Que es:** Crea un punto de guardado (commit) con un mensaje descriptivo.
> **Para que:** Registrar los cambios en el historial de Git con una descripcion clara.
> **Por que:** El mensaje ayuda a los companeros a entender que cambios contiene este commit sin tener que leer todo el codigo.

```powershell
git push origin feature/estadisticas
```
> **Que es:** Sube la rama `feature/estadisticas` al repositorio remoto en GitHub.
> **Para que:** Que los companeros puedan ver el codigo y crear el Pull Request.
> **Por que:** Los cambios solo existen en tu computadora hasta que haces push. Sin push, nadie mas puede verlos.

### Crear el Pull Request en GitHub

est3 sube su rama. Luego **est1** va a GitHub, crea el Pull Request y hace merge:

1. est1 va al repositorio en GitHub
2. Aparecera un boton amarillo que dice **"Compare & pull request"**. Hacer clic
3. Titulo: `Agregar conteo de nodos, altura y conteo de hojas`
4. Descripcion:
   ```
   Se agregan tres metodos en ArbolInventario:
   - contarNodos(): cuenta el total de nodos del arbol
   - calcularAltura(): calcula cuantos niveles tiene el arbol
   - contarHojas(): cuenta los nodos sin hijos
   Se agregan las opciones 7, 8 y 9 en el menu.
   ```
5. Hacer clic en **Create pull request**
6. Hacer clic en **Merge pull request** → **Confirm merge**
