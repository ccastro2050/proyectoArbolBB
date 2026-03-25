# Paso 11: Resumen del Flujo Completo

**Asignatura:** Estructuras de Datos
**Profesor:** Carlos Arturo Castro Castro

---

## 1. El arbol con los datos de prueba

Al cargar los datos de prueba (extension 50, 30, 70, 20, 40, 60, 80, 10), el arbol queda asi:

```
              [50]
             /    \
          [30]    [70]
          /  \    /  \
       [20] [40] [60] [80]
       /
    [10]
```

### Niveles del arbol

```
Nivel 0:  [50]
Nivel 1:  [30], [70]
Nivel 2:  [20], [40], [60], [80]
Nivel 3:  [10]
```

### Recorridos con estos datos

| Recorrido | Orden | Resultado |
|-----------|-------|-----------|
| **Inorden** | Izq → Raiz → Der | 10, 20, 30, 40, 50, 60, 70, 80 |
| **Preorden** | Raiz → Izq → Der | 50, 30, 20, 10, 40, 70, 60, 80 |
| **Postorden** | Izq → Der → Raiz | 10, 20, 40, 30, 60, 80, 70, 50 |

### Estadisticas

| Dato | Valor |
|------|-------|
| Total de nodos | 8 |
| Altura | 4 |
| Hojas | 4 (son: 10, 40, 60, 80) |
| Extension minima | 10 (Recepcion) |
| Extension maxima | 80 (Soporte) |

---

## 2. Tabla de operaciones del proyecto

| Operacion | Metodo | Clase | Quien lo hizo |
|-----------|--------|-------|---------------|
| Insertar | `insertar()` / `insertarRecursivo()` | ArbolInventario | Codigo base (est1) |
| Mostrar Inorden | `mostrarInorden()` | ArbolInventario | Codigo base (est1) |
| Buscar | `buscar()` / `buscarRecursivo()` | ArbolInventario | Codigo base (est1) |
| Mostrar Preorden | `mostrarPreorden()` | ArbolInventario | est1 - Paso 5 |
| Mostrar Postorden | `mostrarPostorden()` | ArbolInventario | est1 - Paso 5 |
| Eliminar | `eliminar()` / `eliminarRecursivo()` | ArbolInventario | est2 - Paso 6 |
| Encontrar minimo (auxiliar) | `encontrarMinimo()` | ArbolInventario | est2 - Paso 6 |
| Contar nodos | `contarNodos()` | ArbolInventario | est3 - Paso 7 |
| Calcular altura | `calcularAltura()` | ArbolInventario | est3 - Paso 7 |
| Contar hojas | `contarHojas()` | ArbolInventario | est3 - Paso 7 |
| Extension minima | `encontrarMinimoPublico()` | ArbolInventario | est1 - Paso 8 |
| Extension maxima | `encontrarMaximo()` | ArbolInventario | est1 - Paso 8 |
| Buscar con detalle | `buscarConDetalle()` / `buscarNodo()` | ArbolInventario | est2 - Paso 9 |
| Mostrar por nivel | `mostrarNivel()` | ArbolInventario | est2 - Paso 9 |
| Cargar datos de prueba | `cargarDatosDePrueba()` | ArbolInventario | est3 - Paso 10 |
| Verificar arbol vacio | `estaVacio()` | ArbolInventario | est3 - Paso 10 |

---

## 3. Flujo completo: del teclado al nodo

Cuando el usuario registra una extension, esto es lo que pasa internamente:

```
1. El usuario escribe "50" y "Gerencia" en la consola
         ↓
2. Main.java lee los datos con Scanner (sc.nextInt() y sc.nextLine())
         ↓
3. Main.java llama a miArbol.insertar(50, "Gerencia")
         ↓
4. ArbolInventario.insertar() llama a insertarRecursivo(raiz, 50, "Gerencia")
         ↓
5. Como raiz es null (arbol vacio), se crea un nuevo Producto(50, "Gerencia")
         ↓
6. Ese nuevo Producto se asigna como raiz del arbol
         ↓
7. Main.java imprime "Registrado con exito."
```

Cuando se inserta un segundo dato (por ejemplo 30):

```
1. Main.java llama a miArbol.insertar(30, "Contabilidad")
         ↓
2. insertarRecursivo(raiz, 30, ...) → raiz es [50]
         ↓
3. 30 < 50, entonces baja por la izquierda
         ↓
4. insertarRecursivo(null, 30, ...) → es null, se crea Producto(30, "Contabilidad")
         ↓
5. Ese nodo queda como hijo izquierdo de [50]

        [50]
        /
     [30]
```

---

## 4. Flujo de trabajo con Git que usamos

Este fue el flujo que se repitio en cada paso del tutorial:

```
         est2 o est3                              est1
         (en su PC)                           (dueno del repo)
              |                                      |
   1. git checkout main                              |
   2. git pull origin main                           |
   3. git checkout -b feature/nombre                 |
   4. (escribe el codigo)                            |
   5. javac src/*.java -d out                        |
   6. java -cp out Main                              |
   7. git add src/                                   |
   8. git commit -m "mensaje"                        |
   9. git push origin feature/nombre                 |
              |                                      |
              └──── la rama aparece en GitHub ───────→|
                                                     |
                                          10. Crea el Pull Request
                                          11. Merge pull request
                                                     |
                                          Los cambios quedan en main
```

---

## 5. Estructura final del proyecto

```
proyectoArbolBB/
├── src/
│   ├── Producto.java          (El nodo: id, nombre, izquierdo, derecho)
│   ├── ArbolInventario.java   (16 metodos para gestionar el arbol)
│   └── Main.java              (Menu con 13 opciones + datos de prueba)
├── out/                       (Archivos compilados - ignorado por Git)
├── .gitignore                 (Ignora out/ y .vscode/)
└── README.md                  (Descripcion del proyecto)
```

---

## 6. Menu final del programa

```
========================================
    DIRECTORIO DE EXTENSIONES (ABB)
========================================
  1.  Registrar Extension
  2.  Ver Directorio (Inorden)
  3.  Buscar Extension
  4.  Ver Preorden
  5.  Ver Postorden
  6.  Eliminar Extension
  7.  Contar Nodos
  8.  Altura del Arbol
  9.  Contar Hojas
  10. Extension Minima
  11. Extension Maxima
  12. Buscar con Detalle
  13. Ver Nodos por Nivel
  0.  Salir
========================================
```

---

## 7. Distribucion del trabajo

| Paso | Estudiante | Que agrego |
|------|-----------|------------|
| 3 | est1 | Creo el repositorio, subio el codigo base, agrego a est2 y est3 |
| 5 | est1 | Recorridos preorden y postorden |
| 6 | est2 | Eliminacion de nodos (3 casos) |
| 7 | est3 | Conteo de nodos, altura, conteo de hojas |
| 8 | est1 | Extension minima y maxima |
| 9 | est2 | Busqueda con detalle y mostrar por nivel |
| 10 | est3 | Datos de prueba, validaciones, manejo de errores |

---

## 8. Que aprendimos

| Tema | Donde se aplico |
|------|----------------|
| Arbol Binario de Busqueda | Todo el proyecto |
| Recursion | Insertar, buscar, recorridos, contar, altura, hojas, eliminar, nivel |
| Recorrido Inorden | Mostrar datos ordenados (opcion 2) |
| Recorrido Preorden | Mostrar raiz primero (opcion 4) |
| Recorrido Postorden | Mostrar hojas primero (opcion 5) |
| Eliminacion con 3 casos | Opcion 6 |
| Busqueda eficiente | Opciones 3 y 12 |
| Git (ramas, commits, push) | Todos los pasos |
| Pull Requests | Todos los pasos |
| Trabajo en equipo | 3 estudiantes, cada uno con su rama |
