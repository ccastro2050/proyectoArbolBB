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

# Crear la carpeta para el codigo fuente
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
 * Clase Producto - Representa un NODO dentro del Arbol Binario de Busqueda.
 *
 * Cada nodo almacena la informacion de una extension telefonica:
 * - id: numero de extension, usado como clave para ordenar en el arbol.
 * - nombre: nombre de la oficina o servicio asociado a esa extension.
 * - izquierdo/derecho: referencias a los nodos hijos, que permiten
 *   formar la estructura del arbol (menor a la izquierda, mayor a la derecha).
 */
public class Producto {
    int id;              // Extension telefonica (Dato para ordenar)
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
 * Clase ArbolInventario - Contiene toda la logica del Arbol Binario de Busqueda (ABB).
 *
 * Gestiona la estructura del arbol a traves de tres operaciones principales:
 * 1. Insertar: agrega un nuevo nodo en la posicion correcta del arbol.
 * 2. Mostrar Inorden: recorre el arbol izquierda-raiz-derecha (ordenado).
 * 3. Buscar: recorre el arbol hasta encontrar el ID o llegar a null.
 */
public class ArbolInventario {
    Producto raiz; // Nodo principal del arbol

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

    // METODO 3: BUSCAR (Retorna un mensaje segun la existencia del ID)
    public String buscar(int id) {
        return buscarRecursivo(raiz, id) ? "ID encontrado en el sistema." : "El ID no existe.";
    }

    private boolean buscarRecursivo(Producto actual, int id) {
        if (actual == null) return false; // No se encontro
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
        ArbolInventario miArbol = new ArbolInventario();
        Scanner sc = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n--- DIRECTORIO DE EXTENSIONES ---");
            System.out.println("1. Registrar Extension");
            System.out.println("2. Ver Directorio (Ordenado)");
            System.out.println("3. Buscar Extension");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese numero de extension: ");
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
                    System.out.println("Opcion no valida.");
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

Debe aparecer el menu. Probar registrando una extension y luego viendola con la opcion 2.

---

## 5. est1: Inicializar Git y subir a GitHub

### 5.1 Crear el repositorio en GitHub

1. Ir a https://github.com/new
2. Nombre del repositorio: `proyectoArbolBB`
3. Descripcion: `Sistema de Directorio con Arboles Binarios de Busqueda`
4. Seleccionar **Public**
5. **NO** marcar "Add a README file"
6. Hacer clic en **Create repository**

### 5.2 Subir el proyecto desde PowerShell

Ejecutar estos comandos uno por uno en la carpeta del proyecto:

```powershell
git init
```
> **Que es:** Inicializa un repositorio Git en la carpeta actual.
> **Para que:** Crea una carpeta oculta `.git` donde Git guardara todo el historial de cambios.
> **Por que:** Sin esto, Git no sabe que esta carpeta es un proyecto que debe rastrear.

```powershell
git add .
```
> **Que es:** Agrega TODOS los archivos de la carpeta al area de preparacion.
> **Para que:** Le dice a Git cuales archivos incluir en el proximo guardado (commit).
> **Por que:** Git no guarda archivos automaticamente. Hay que decirle cuales incluir.

```powershell
git commit -m "Codigo base: Sistema de Directorio con ABB"
```
> **Que es:** Crea un punto de guardado (commit) con un mensaje descriptivo.
> **Para que:** Guarda una "fotografia" del estado actual de todos los archivos.
> **Por que:** Cada commit es un punto al que podemos volver si algo sale mal.

```powershell
git branch -M main
```
> **Que es:** Renombra la rama actual a `main`.
> **Para que:** Usar el nombre estandar que GitHub espera para la rama principal.
> **Por que:** Algunas versiones de Git crean la rama con otro nombre por defecto.

```powershell
git remote add origin https://github.com/TU_USUARIO/proyectoArbolBB.git
```
> **Que es:** Conecta tu proyecto local con el repositorio que creaste en GitHub.
> **Para que:** Le dice a Git a donde debe subir los archivos cuando hagas `push`.
> **Por que:** Sin esta conexion, Git no sabe que existe un repositorio en internet.

```powershell
git push -u origin main
```
> **Que es:** Sube todos los archivos al repositorio en GitHub.
> **Para que:** Que el codigo este disponible en internet para que est2 y est3 lo descarguen.
> **Por que:** Los archivos solo existen en tu computadora hasta que haces push.

> **Nota:** Reemplazar `TU_USUARIO` con tu nombre de usuario real de GitHub.

### 5.3 Si algo sale mal (no te preocupes)

**Es normal equivocarse con Git.** Si algo falla, aqui estan las soluciones:

**Error: "fatal: not a git repository"**
> Significa que no estas en la carpeta correcta. Solucion:
> ```powershell
> cd C:\Users\TU_USUARIO\Desktop\proyectoArbolBB
> ```

**Error: "remote origin already exists"**
> Significa que ya conectaste el repositorio antes. No pasa nada. Solucion:
> ```powershell
> git remote remove origin
> git remote add origin https://github.com/TU_USUARIO/proyectoArbolBB.git
> ```

**Error: "failed to push" o "rejected"**
> Puede pasar si el repositorio en GitHub se creo con README. La solucion mas sencilla es borrar el repositorio en GitHub (Settings → Danger Zone → Delete this repository), volver a crearlo **sin** README, y repetir desde `git remote add origin`.

**Error al compilar (javac muestra errores)**
> Revisar que los 3 archivos `.java` esten dentro de la carpeta `src/` y que el codigo este copiado correctamente. Corregir el error, y luego:
> ```powershell
> git add .
> git commit -m "Corregir error en codigo base"
> git push origin main
> ```

**Si todo se complico demasiado y no sabes que paso:**
> La opcion mas segura es empezar de cero. No tiene nada de malo:
> 1. Borrar el repositorio en GitHub (Settings → Danger Zone → Delete this repository)
> 2. Borrar la carpeta del proyecto en tu computadora
> 3. Volver a empezar desde el inicio de este Paso 3
>
> Git es una herramienta que se aprende con practica. Equivocarse es parte del proceso.

### 5.3 Proteger la rama main

Esto obliga a que todos los cambios entren por Pull Request con revision de un companero.

1. En GitHub, ir al repositorio → **Settings** → **Branches**
2. Hacer clic en **Add branch protection rule**
3. En "Branch name pattern" escribir: `main`
4. Marcar: **Require a pull request before merging**
5. Hacer clic en **Create**

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
> **Que es:** Cambia la ubicacion de PowerShell al escritorio.
> **Para que:** Que el proyecto se descargue en el escritorio (o donde quieras tenerlo).
> **Por que:** Necesitamos estar en la carpeta donde queremos guardar el proyecto antes de clonarlo.

```powershell
git clone https://github.com/USUARIO_EST1/proyectoArbolBB.git
```
> **Que es:** Descarga una copia completa del repositorio de GitHub a tu computadora.
> **Para que:** Tener el proyecto con todo el codigo y el historial de Git en tu maquina.
> **Por que:** Es la forma de obtener un proyecto que alguien mas creo en GitHub.

```powershell
cd proyectoArbolBB
```
> **Que es:** Entra a la carpeta del proyecto que acabamos de descargar.
> **Para que:** Todos los comandos siguientes deben ejecutarse dentro de la carpeta del proyecto.
> **Por que:** Git solo funciona cuando estas dentro de una carpeta que tiene un repositorio.

### Verificar que funciona

```powershell
javac src/*.java -d out
java -cp out Main
```

Debe aparecer el menu funcionando.

### Verificar la rama

```powershell
git branch
```
> **Que es:** Muestra en que rama estas trabajando actualmente.
> **Para que:** Confirmar que estas en `main`.
> **Por que:** Siempre debemos saber en que rama estamos antes de empezar a trabajar.

Debe mostrar:

```
* main
```

### Si algo sale mal al clonar (no te preocupes)

**Error: "repository not found"**
> Significa que la URL esta mal o no aceptaste la invitacion. Solucion:
> 1. Verificar que aceptaste la invitacion en GitHub (revisar correo o notificaciones)
> 2. Verificar que la URL sea exactamente la que te dio est1

**Error: "destination path already exists"**
> Significa que ya hay una carpeta llamada `proyectoArbolBB` en tu escritorio. Solucion:
> 1. Borrar la carpeta existente
> 2. Volver a ejecutar `git clone`

**El programa no compila (javac muestra errores)**
> Puede ser que la carpeta `out` no exista. Solucion:
> ```powershell
> mkdir out
> javac src/*.java -d out
> ```

**Si nada funciona:**
> Borrar la carpeta del proyecto y volver a ejecutar `git clone`. Clonar no puede danar nada, solo descarga una copia nueva.

---

## 7. Estructura del proyecto

Despues de completar este paso, los 3 estudiantes tienen esta estructura:

```
proyectoArbolBB/
├── src/
│   ├── Producto.java          (El nodo del arbol)
│   ├── ArbolInventario.java   (La logica del arbol)
│   └── Main.java              (El menu de consola)
├── out/                       (Archivos compilados - ignorado por Git)
├── .gitignore                 (Archivos que Git debe ignorar)
└── README.md                  (Descripcion del proyecto)
```

---

## 8. Flujo de trabajo que usaremos en los pasos 5 al 10

Cada vez que un estudiante necesite agregar codigo nuevo, seguira estos pasos:

```powershell
# 1. Ir a la rama principal y traer los ultimos cambios
git checkout main
git pull origin main

# 2. Crear una rama nueva para su trabajo
git checkout -b feature/nombre-de-la-funcionalidad

# 3. (Escribir el codigo siguiendo el paso del tutorial)

# 4. Compilar y probar
javac src/*.java -d out
java -cp out Main

# 5. Preparar y guardar los cambios
git add src/
git commit -m "Mensaje descriptivo del cambio"

# 6. Subir la rama a GitHub
git push origin feature/nombre-de-la-funcionalidad

# 7. est1 va a GitHub → crea el Pull Request → Merge
```
