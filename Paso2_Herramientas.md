# Paso 2: Herramientas Necesarias

**Asignatura:** Estructuras de Datos
**Profesor:** Carlos Arturo Castro Castro

---

## 1. JDK Temurin (Eclipse Adoptium)

El JDK es el kit de desarrollo de Java. Temurin es una versión gratuita y de código abierto.

### Instalacion

1. Ir a https://adoptium.net/
2. Descargar el instalador para **Windows x64** (version LTS más reciente)
3. Ejecutar el instalador
4. **Importante:** Marcar la opción **"Add to PATH"** y **"Set JAVA_HOME"** durante la instalacion

### Verificar en PowerShell

Abrir PowerShell y escribir:

```powershell
java -version
```

Debe mostrar algo como:

```
openjdk versión "21.0.x" 2024-xx-xx LTS
Eclipse Adoptium
```

Tambien verificar el compilador:

```powershell
javac -version
```

---

## 2. Visual Studio Code

### Instalacion

1. Ir a https://code.visualstudio.com/
2. Descargar e instalar para Windows
3. Abrir VS Code

### Extensión necesaria

1. Ir a la pestaña de **Extensiones** (icono de cuadrados en la barra lateral izquierda, o `Ctrl+Shift+X`)
2. Buscar **"Extensión Pack for Java"** de Microsoft
3. Hacer clic en **Install**

Esto instala todo lo necesario para trabajar con Java en VS Code: resaltado de sintaxis, autocompletado, depuracion y ejecucion.

---

## 3. Git

### Instalacion

1. Ir a https://git-scm.com/downloads/win
2. Descargar e instalar para Windows
3. Durante la instalacion, dejar las opciones por defecto

### Verificar en PowerShell

```powershell
git --version
```

### Configuracion inicial (una sola vez)

```powershell
git config --global user.name "Tu Nombre"
git config --global user.email "tu@correo.com"
```

---

## 4. Cuenta en GitHub

1. Ir a https://github.com y crear una cuenta gratuita
2. Usar el mismo correo que configuraste en Git

---

## 5. Verificacion final

Abrir PowerShell y ejecutar estos tres comandos. Los tres deben responder sin error:

```powershell
java -version
javac -version
git --version
```

Si alguno falla, revisar que la instalacion se haya completado y que este en el PATH del sistema.

---

## 6. Compilar y ejecutar un programa Java desde PowerShell

Este es el flujo que usaremos en todo el tutorial:

```powershell
# Ir a la carpeta del proyecto
cd C:\ruta\al\proyectoArbolBB

# Compilar todos los archivos .java y poner los .class en la carpeta out
javac src/*.java -d out

# Ejecutar el programa (la clase que tiene el main)
java -cp out Main
```

| Comando | Que hace |
|---------|----------|
| `javac` | **Compila**: convierte archivos `.java` en archivos `.class` (bytecode) |
| `-d out` | Coloca los `.class` en la carpeta `out` |
| `java` | **Ejecuta**: corre el programa compilado |
| `-cp out` | Le dice a Java que busque las clases en la carpeta `out` |
| `Main` | Nombre de la clase que tiene el método `main` |
