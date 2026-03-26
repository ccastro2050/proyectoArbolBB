# Paso 3: Crear el Proyecto y Subirlo a GitHub

**Asignatura:** Estructuras de Datos
**Profesor:** Carlos Arturo Castro Castro

---

> **Este paso lo realiza est1.** Los otros dos estudiantes esperan hasta la seccion 3.

---

## 1. est1: Crear la carpeta del proyecto

Abrir PowerShell y ejecutar:

```powershell
# Ir al escritorio (o donde quieras guardar el proyecto)
cd C:\Users\TU_USUARIO\Desktop

# Crear la carpeta del proyecto
mkdir proyectoArbolBB

# Entrar a la carpeta
cd proyectoArbolBB

# Crear la carpeta para el código fuente
mkdir src

# Crear la carpeta para los archivos compilados
mkdir out
```

---

## 2. est1: Crear los 3 archivos Java

Abrir VS Code en la carpeta del proyecto:

```powershell
code .
```

### 2.1 Crear el archivo `src/Producto.java`

En VS Code, hacer clic derecho sobre la carpeta `src` → **New File** → escribir `Producto.java`.

Copiar este codigo:

```java
/**
 * Clase Producto - Representa un NODO dentro del Árbol Binario de Busqueda.
 *
 * Cada nodo almacena la información de una extensión telefónica:
 * - id: número de extensión, usado como clave para ordenar en el árbol.
 * - nombre: nombre de la oficina o servicio asociado a esa extensión.
 * - izquierdo/derecho: referencias a los nodos hijos, que permiten
 *   formar la estructura del árbol (menor a la izquierda, mayor a la derecha).
 */
public class Producto {
    int id;              // Extensión telefónica (Dato para ordenar)
    String nombre;       // Nombre de la oficina o servicio
    Producto izquierdo;  // Referencia al hijo menor (rama izquierda)
    Producto derecho;    // Referencia al hijo mayor (rama derecha)

    // Constructor: Inicializa el nodo con sus datos y ramas vacias
    public Producto(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.izquierdo = null;
        this.derecho = null;
    }
}
```

### 2.2 Crear el archivo `src/ArbolInventario.java`

Clic derecho sobre `src` → **New File** → escribir `ArbolInventario.java`.

Copiar este codigo:

```java
/**
 * Clase ArbolInventario - Contiene toda la lógica del Árbol Binario de Búsqueda (ABB).
 *
 * Gestiona la estructura del árbol a traves de tres operaciones principales:
 * 1. Insertar: agrega un nuevo nodo en la posición correcta del árbol.
 * 2. Mostrar Inorden: recorre el árbol izquierda-raiz-derecha (ordenado).
 * 3. Buscar: recorre el árbol hasta encontrar el ID o llegar a null.
 */
public class ArbolInventario {
    Producto raiz; // Nodo principal del árbol

    public ArbolInventario() {
        this.raiz = null;
    }

    // METODO 1: INSERTAR (Punto de entrada)
    public void insertar(int id, String nombre) {
        raiz = insertarRecursivo(raiz, id, nombre);
    }

    // Logica recursiva para insertar
    private Producto insertarRecursivo(Producto actual, int id, String nombre) {
        if (actual == null) {
            return new Producto(id, nombre); // Lugar encontrado, se crea el nodo
        }

        if (id < actual.id) {
            actual.izquierdo = insertarRecursivo(actual.izquierdo, id, nombre);
        } else if (id > actual.id) {
            actual.derecho = insertarRecursivo(actual.derecho, id, nombre);
        }
        return actual;
    }

    // METODO 2: MOSTRAR INORDEN (Muestra los datos ordenados de menor a mayor)
    public void mostrarInorden(Producto nodo) {
        if (nodo != null) {
            mostrarInorden(nodo.izquierdo); // Visita rama izquierda
            System.out.println("Extension: " + nodo.id + " | Oficina: " + nodo.nombre);
            mostrarInorden(nodo.derecho);   // Visita rama derecha
        }
    }

    // METODO 3: BUSCAR (Retorna un mensaje según la existencia del ID)
    public String buscar(int id) {
        return buscarRecursivo(raiz, id) ? "ID encontrado en el sistema." : "El ID no existe.";
    }

    private boolean buscarRecursivo(Producto actual, int id) {
        if (actual == null) return false; // No se encontró
        if (id == actual.id) return true; // Encontrado

        // Decidir hacia que rama bajar
        return id < actual.id
            ? buscarRecursivo(actual.izquierdo, id)
            : buscarRecursivo(actual.derecho, id);
    }
}
```

### 2.3 Crear el archivo `src/Main.java`

Clic derecho sobre `src` → **New File** → escribir `Main.java`.

Copiar este codigo:

```java
import java.util.Scanner;

/**
 * Clase Main - Punto de entrada del programa e interfaz de usuario por consola.
 */
public class Main {
    public static void main(String[] args) {
        ArbolInventario miÁrbol = new ArbolInventario();
        Scanner sc = new Scanner(System.in);
        int opción = -1;

        while (opcion != 0) {
            System.out.println("\n--- DIRECTORIO DE EXTENSIONES ---");
            System.out.println("1. Registrar Extension");
            System.out.println("2. Ver Directorio (Ordenado)");
            System.out.println("3. Buscar Extension");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");
            opción = sc.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese número de extension: ");
                    int id = sc.nextInt();
                    sc.nextLine(); // Limpiar el buffer
                    System.out.print("Nombre de la oficina: ");
                    String nombre = sc.nextLine();
                    miArbol.insertar(id, nombre);
                    System.out.println("Registrado con exito.");
                    break;
                case 2:
                    System.out.println("\nLISTADO ACTUAL:");
                    miArbol.mostrarInorden(miArbol.raiz);
                    break;
                case 3:
                    System.out.print("ID a buscar: ");
                    int buscaId = sc.nextInt();
                    System.out.println(miArbol.buscar(buscaId));
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no valida.");
            }
        }
        sc.close();
    }
}
```

---

## 3. est1: Crear el archivo .gitignore

En la raiz del proyecto (no dentro de `src`), crear un archivo llamado `.gitignore`:

Clic derecho en el explorador de VS Code (en la raiz) → **New File** → escribir `.gitignore`.

Copiar este contenido:

```
# Archivos compilados
out/
*.class

# Configuracion de VS Code
.vscode/
```

---

## 4. est1: Compilar y verificar que funciona

Antes de subir a GitHub, verificar que todo compila y ejecuta correctamente.

Abrir la terminal de VS Code (`Ctrl + Ñ`) o PowerShell:

```powershell
javac src/*.java -d out
java -cp out Main
```

Debe aparecer el menú. Probar registrando una extensión y luego viendola con la opción 2.

---

## 5. est1: Inicializar Git y subir a GitHub

### 5.1 Crear el repositorio en GitHub

1. Ir a https://github.com/new
2. Nombre del repositorio: `proyectoArbolBB`
3. Descripción: `Sistema de Directorio con Arboles Binarios de Busqueda`
4. Seleccionar **Public**
5. **NO** marcar "Add a README file"
6. Hacer clic en **Create repository**

### 5.2 Subir el proyecto desde PowerShell

Ejecutar estos comandos uno por uno en la carpeta del proyecto:

```powershell
git init
```
> Inicializa un repositorio Git en la carpeta actual. Crea una carpeta oculta `.git` donde Git guardara todo el historial de cambios. Sin este comando, Git no sabe que está carpeta es un proyecto que debe rastrear.

```powershell
git add .
```
> Agrega todos los archivos de la carpeta al área de preparación (staging). Git no guarda archivos automáticamente, hay que decirle cuales incluir en el próximo guardado.

```powershell
git commit -m "Código base: Sistema de Directorio con ABB"
```
> Crea un punto de guardado (commit) con un mensaje descriptivo. Es como una "fotografía" del estado actual de todos los archivos. Si algo sale mal después, podemos volver a este punto.

```powershell
git branch -M main
```
> Renombra la rama actual a `main`, que es el nombre estándar que GitHub espera para la rama principal. Algunas versiones de Git crean la rama con otro nombre por defecto, así que este comando asegura que se llame `main`.

```powershell
git remote add origin https://github.com/TU_USUARIO/proyectoArbolBB.git
```
> Conecta tu proyecto local con el repositorio que creaste en GitHub. Sin está conexión, Git no sabe a donde subir los archivos cuando hagas `push`. Reemplazar `TU_USUARIO` con tu nombre de usuario real de GitHub.

```powershell
git push -u origin main
```
> Sube todos los archivos al repositorio en GitHub. Los archivos solo existen en tu computadora hasta que haces push. Después de esto, est2 y est3 podrán descargarse el proyecto.

### 5.3 Si algo sale mal (no te preocupes)

**Es normal equivocarse con Git.** Si algo falla, aquí están las soluciones:

**Error: "fatal: not a git repository"**
> Significa que no estás en la carpeta correcta. Solución:
> ```powershell
> cd C:\Users\TU_USUARIO\Desktop\proyectoArbolBB
> ```

**Error: "remote origin already exists"**
> Significa que ya conectaste el repositorio antes. No pasa nada. Solución:
> ```powershell
> git remote remove origin
> git remote add origin https://github.com/TU_USUARIO/proyectoArbolBB.git
> ```

**Error: "failed to push" o "rejected"**
> Puede pasar si el repositorio en GitHub se creó con README. La solución más sencilla es borrar el repositorio en GitHub (Settings → Danger Zone → Delete this repository), volver a crearlo **sin** README, y repetir desde `git remote add origin`.

**Error al compilar (javac muestra errores)**
> Revisar que los 3 archivos `.java` esten dentro de la carpeta `src/` y que el código este copiado correctamente. Corregir el error, y luego:
> ```powershell
> git add .
> git commit -m "Corregir error en código base"
> git push origin main
> ```

**Si todo se complicó demasiado y no sabes que paso:**
> La opción más segura es empezar de cero. No tiene nada de malo:
> 1. Borrar el repositorio en GitHub (Settings → Danger Zone → Delete this repository)
> 2. Borrar la carpeta del proyecto en tu computadora
> 3. Volver a empezar desde el inicio de este Paso 3
>
> Git es una herramienta que se aprende con práctica. Equivocarse es parte del proceso.

### 5.3 Nota sobre protección de la rama main

> **Importante:** En proyectos profesionales se configura una "protección de rama" para que nadie pueda subir código directamente a `main` y todo pase por Pull Request.
>
> En este tutorial **no vamos a activar esa protección** para mantener las cosas simples y evitar bloqueos innecesarios. Igual vamos a trabajar con ramas y Pull Requests porque es la buena práctica, pero si alguien se equivoca y sube algo directo a `main`, no pasará nada grave.
>
> Cuando ya dominen el flujo de ramas y Pull Requests, pueden investigar como activarlo en **Settings → Branches → Add branch protection rule** en GitHub.

### 5.4 Agregar a est2 y est3 como colaboradores

1. En GitHub, ir al repositorio → **Settings** → **Collaborators**
2. Hacer clic en **Add people**
3. Buscar el nombre de usuario de GitHub de est2 y agregarlo
4. Buscar el nombre de usuario de GitHub de est3 y agregarlo
5. est2 y est3 recibiran un correo o notificacion y deben hacer clic en **Accept invitation**

---

## 6. est2 y est3: Clonar el repositorio

Una vez aceptada la invitacion, cada uno abre PowerShell:

```powershell
cd C:\Users\TU_USUARIO\Desktop
```
> Cambia la ubicación de PowerShell al escritorio. Necesitamos estar en la carpeta donde queremos guardar el proyecto antes de clonarlo.

```powershell
git clone https://github.com/USUARIO_EST1/proyectoArbolBB.git
```
> Descarga una copia completa del repositorio de GitHub a tu computadora, incluyendo todo el código y el historial de Git. Es la forma de obtener un proyecto que alguien más creó en GitHub.

```powershell
cd proyectoArbolBB
```
> Entra a la carpeta del proyecto que acabamos de descargar. Todos los comandos de Git deben ejecutarse dentro de está carpeta, si no, Git no sabe en que repositorio estás trabajando.

### Verificar que funciona

```powershell
javac src/*.java -d out
java -cp out Main
```

Debe aparecer el menú funcionando.

### Verificar la rama

```powershell
git branch
```
> Muestra en que rama estás trabajando actualmente. Debe salir `main` con un asterisco. Siempre hay que saber en que rama estamos antes de empezar a trabajar.

Debe mostrar:

```
* main
```

### Si algo sale mal al clonar (no te preocupes)

**Error: "repository not found"**
> Significa que la URL está mal o no aceptaste la invitacion. Solución:
> 1. Verificar que aceptaste la invitacion en GitHub (revisar correo o notificaciones)
> 2. Verificar que la URL sea exactamente la que te dio est1

**Error: "destination path already exists"**
> Significa que ya hay una carpeta llamada `proyectoArbolBB` en tu escritorio. Solución:
> 1. Borrar la carpeta existente
> 2. Volver a ejecutar `git clone`

**El programa no compila (javac muestra errores)**
> Puede ser que la carpeta `out` no exista. Solución:
> ```powershell
> mkdir out
> javac src/*.java -d out
> ```

**Si nada funciona:**
> Borrar la carpeta del proyecto y volver a ejecutar `git clone`. Clonar no puede danar nada, solo descarga una copia nueva.

---

## 7. Estructura del proyecto

Después de completar este paso, los 3 estudiantes tienen está estructura:

```
proyectoArbolBB/
├── src/
│   ├── Producto.java          (El nodo del árbol)
│   ├── ArbolInventario.java   (La lógica del árbol)
│   └── Main.java              (El menú de consola)
├── out/                       (Archivos compilados - ignorado por Git)
├── .gitignore                 (Archivos que Git debe ignorar)
└── README.md                  (Descripción del proyecto)
```

---

## 8. Flujo de trabajo que usaremos en los pasos 5 al 10

Cada vez que un estudiante necesite agregar código nuevo, seguira estos pasos:

```powershell
# 1. Ir a la rama principal y traer los últimos cambios
git checkout main
git pull origin main

# 2. Crear una rama nueva para su trabajo
git checkout -b feature/nombre-de-la-funcionalidad

# 3. (Escribir el código siguiendo el paso del tutorial)

# 4. Compilar y probar
javac src/*.java -d out
java -cp out Main

# 5. Preparar y guardar los cambios
git add src/
git commit -m "Mensaje descriptivo del cambio"

# 6. Subir la rama a GitHub
git push origin feature/nombre-de-la-funcionalidad

# 7. Quien hizo push crea el Pull Request en GitHub
# 8. est1 va a la pestaña Pull requests → revisa → Merge
```
