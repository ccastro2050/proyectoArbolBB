# Paso 0 — Plan de Desarrollo y Buenas Prácticas

**Asignatura:** Estructuras de Datos
**Profesor:** Carlos Arturo Castro Castro

**Este paso se hace ANTES de escribir una sola línea de código.**

Independiente de la metodología de desarrollo que se use (Scrum, Kanban, RUP, ágil, por prototipos o híbridos), todo proyecto debe comenzar con un **plan de desarrollo** que defina qué se va a hacer, quién lo hace, cómo se va a trabajar y cuándo se entrega.

---

## 1. Buenas prácticas para trabajo colaborativo en GitHub

### 1.1 Estrategia de ramas (Branching Strategy)

Para equipos pequeños (2-5 personas), se recomienda **GitHub Flow**:

```
main (siempre estable, código que compila y ejecuta)
  ├── feature/recorridos       (est1 trabaja aquí)
  ├── feature/eliminar         (est2 trabaja aquí)
  └── feature/estadisticas     (est3 trabaja aquí)
```

**Reglas:**
- `main` siempre debe funcionar (se puede compilar con `javac src/*.java -d out` sin errores)
- Nadie hace push directo a `main` — todo entra por Pull Request
- Cada tarea tiene su propia rama
- Las ramas se borran después del merge

### 1.2 Convenciones para nombres de ramas

Usar prefijos que indiquen el tipo de trabajo:

| Prefijo | Uso | Ejemplo |
|---------|-----|---------|
| `feature/` | Nueva funcionalidad | `feature/recorridos` |
| `fix/` | Corrección de errores | `fix/error-eliminar-raiz` |
| `refactor/` | Mejora de código sin cambiar funcionalidad | `refactor/metodo-buscar` |
| `docs/` | Documentación | `docs/actualizar-readme` |
| `hotfix/` | Corrección urgente | `hotfix/null-pointer` |

**Ejemplo práctico para este proyecto:**

```
feature/recorridos             ← est1 (agregar preorden y postorden)
feature/eliminar               ← est2 (agregar eliminación de nodos)
feature/estadisticas           ← est3 (agregar conteo, altura, hojas)
feature/min-max                ← est1 (agregar extensión mínima y máxima)
feature/busqueda-detalle       ← est2 (agregar búsqueda con detalle y nivel)
feature/mejoras                ← est3 (agregar datos de prueba y validaciones)
fix/error-eliminar-raiz        ← quien lo detecte (corrección de bug)
fix/menu-opcion-invalida       ← quien lo detecte (corrección de bug)
refactor/metodo-buscar         ← mejora de código sin cambiar funcionalidad
docs/actualizar-readme         ← documentación
```

> **Nota:** El prefijo de la rama coincide con el tipo del commit. Si la rama es `feature/recorridos`, los commits dentro de esa rama empiezan con `feat:`. Si la rama es `fix/error-eliminar`, los commits empiezan con `fix:`.

### 1.3 Convenciones para mensajes de commit

Usar mensajes claros que digan **qué** se hizo:

**Formato recomendado:**
```
tipo: descripción corta
```

**Tipos comunes:**
| Tipo | Significado | Ejemplo |
|------|-------------|---------|
| `feat` | Nueva funcionalidad | `feat: agregar recorridos preorden y postorden` |
| `fix` | Corrección de error | `fix: corregir eliminación cuando el nodo es la raíz` |
| `docs` | Documentación | `docs: agregar manual de uso en README` |
| `style` | Formato (no cambia lógica) | `style: corregir indentación en ArbolInventario.java` |
| `refactor` | Reestructuración de código | `refactor: simplificar método buscarRecursivo` |
| `test` | Agregar o modificar pruebas | `test: agregar datos de prueba automáticos` |

**Más ejemplos adaptados a este proyecto:**
```
feat: agregar eliminación de nodos con los 3 casos
feat: agregar conteo de nodos, altura y conteo de hojas
feat: agregar búsqueda de extensión mínima y máxima
feat: agregar búsqueda con detalle y mostrar por nivel
feat: agregar datos de prueba y validaciones
fix: corregir sucesor inorden cuando tiene hijo derecho
fix: corregir Scanner que no lee el nombre con espacios
refactor: extraer encontrarMinimo como método público
style: agregar comentarios a los 3 casos de eliminación
docs: actualizar README con instrucciones de compilación
```

### 1.4 Convenciones para Pull Requests

- **Título claro:** describir qué hace el PR en una oración
- **Descripción:** explicar qué se hizo, por qué y cómo probarlo
- **Un PR por tarea:** no mezclar varias funcionalidades en un PR
- **Revisar antes de aprobar:** al menos una persona revisa el código

**Ejemplo de PR bien hecho:**

```
Título: Agregar recorridos preorden y postorden

Descripción:
- Se agregan los métodos mostrarPreorden y mostrarPostorden en ArbolInventario
- Se agregan las opciones 4 y 5 en el menú de Main.java
- Ambos recorridos usan recursión igual que mostrarInorden

¿Cómo probarlo?
1. Compilar: javac src/*.java -d out
2. Ejecutar: java -cp out Main
3. Registrar extensiones: 50, 30, 70, 20, 40
4. Probar opción 4 (Preorden): debe mostrar 50, 30, 20, 40, 70
5. Probar opción 5 (Postorden): debe mostrar 20, 40, 30, 70, 50
```

### 1.5 Flujo de trabajo diario

```
1. git checkout main
2. git pull origin main              ← traer lo último
3. git checkout -b feature/nombre    ← crear rama
4. (escribir el código)
5. javac src/*.java -d out           ← compilar
6. java -cp out Main                 ← probar
7. git add src/
8. git commit -m "feat: descripción"
9. git push origin feature/nombre
10. Quien hizo push crea el PR en GitHub (botón amarillo "Compare & pull request")
11. est1 va a la pestaña Pull requests → revisa → Merge
```

### 1.6 Aclaraciones importantes sobre Pull Requests

> **¿Qué es un Pull Request (PR)?** Es una solicitud para integrar los cambios de una rama a `main`. En vez de meter código directo a `main`, el PR permite:
> - Ver exactamente qué archivos cambiaron (línea por línea)
> - Que el dueño del repositorio (est1) revise el código antes de integrarlo
> - Tener un historial de qué se integró, cuándo y quién lo hizo
>
> **¿Quién crea el PR?** Lo crea quien hizo el push, porque GitHub le muestra un botón amarillo solo a esa persona. Si est1 hizo push, est1 crea el PR. Si est2 o est3 hicieron push, ellos crean el PR.
>
> **¿Quién hace merge?** Solo est1, porque es el dueño del repositorio y de la rama `main`.
>
> **¿Qué pasa si est1 no acepta el PR?** El código se queda en la rama pero no entra a `main`. El PR queda abierto en GitHub esperando. Est1 puede escribir un comentario explicando qué hay que corregir, y el estudiante que hizo el push puede hacer más commits en la misma rama para arreglar el problema. Cuando est1 esté conforme, hace merge.
>
> **¿Qué pasa si el estudiante no crea el PR después de hacer push?** La rama queda subida en GitHub pero nadie la revisa. El código no entra a `main` y los demás compañeros no pueden usar esos cambios. Sin PR, es como si el trabajo no existiera para el equipo.

### 1.7 Paso a paso: crear un PR en GitHub

1. Ir al repositorio en GitHub

2. GitHub muestra un banner amarillo que dice algo como: **"feature/recorridos had recent pushes"** con un botón **Compare & pull request**.

> Ese banner aparece porque GitHub detectó que alguien acaba de subir una rama nueva. "Recent pushes" significa "subidas recientes". El botón **Compare & pull request** significa: "comparar los cambios de esa rama contra `main` y crear una solicitud para integrarlos". Hacer clic en ese botón.

3. Si no aparece el banner (puede pasar si pasó mucho tiempo desde el push): ir a la pestaña **Pull requests** (arriba en el repositorio) → **New pull request** → en el dropdown "compare" seleccionar la rama. Esto hace lo mismo que el botón amarillo pero de forma manual.

4. GitHub muestra un formulario. Llenar título y descripción del cambio.

5. Hacer clic en **Create pull request**.

> Esto NO integra los cambios todavía. Solo crea la solicitud. GitHub ahora muestra una página con el PR abierto donde se puede ver exactamente qué archivos cambiaron y qué líneas se agregaron o modificaron. Aquí es donde est1 revisaría el código antes de aprobarlo.

6. **est1** revisa los cambios en la pestaña **Files changed**. Ahí se ven las líneas nuevas en verde y las eliminadas en rojo.

7. Hacer clic en **Merge pull request**.

> **¿Qué es merge?** Merge significa "fusionar". Al hacer clic, le estamos diciendo a GitHub: "toma todos los cambios de esa rama e intégralos a `main`". Después de esto, `main` tendrá el código nuevo.

8. Hacer clic en **Confirm merge** para confirmar.

9. GitHub muestra un botón **Delete branch**. Hacer clic.

> Esto solo borra la rama en GitHub, no borra el código (el código ya está seguro en `main`). Es para no acumular ramas viejas que ya se integraron. Si no se borra, no pasa nada malo, solo queda una rama huérfana que ensucia la lista de ramas.

10. **¿Y si después quiero ver qué hizo cada estudiante?** Aunque la rama se borró, el Pull Request queda guardado para siempre en GitHub. Ir a la pestaña **Pull requests** → **Closed** y ahí aparecen todos los PRs anteriores con el nombre de quien lo creó, la fecha, y todos los archivos que se cambiaron línea por línea.

---

## 2. Plan de desarrollo

### 2.1 Descripción del problema

Se requiere un sistema sencillo para gestionar un **Directorio de Extensiones Telefónicas**. El sistema debe permitir registrar una extensión (ID numérico) y el nombre de la oficina. Para garantizar búsquedas rápidas, los datos se organizarán en un **Árbol Binario de Búsqueda (ABB)** bajo la regla: *valores menores a la izquierda, valores mayores a la derecha*.

### 2.2 Tecnologías

| Tecnología | Uso |
|-----------|-----|
| **Java** | Lenguaje de programación |
| **javac** | Compilador (convierte .java en .class) |
| **Scanner** | Lectura de datos por consola |
| **VS Code** | Editor de código |
| **Git / GitHub** | Control de versiones y colaboración |

### 2.3 Estructura del proyecto

```
proyectoArbolBB/
├── src/
│   ├── Producto.java          ← El nodo (id, nombre, izquierdo, derecho)
│   ├── ArbolInventario.java   ← La lógica del árbol (16 métodos)
│   └── Main.java              ← El menú de consola (13 opciones)
├── out/                       ← Archivos compilados (NO se sube a GitHub)
├── .gitignore                 ← Ignora out/ y .vscode/
└── README.md                  ← Descripción del proyecto
```

### 2.4 Alcance

- Sistema de consola en Java
- Árbol Binario de Búsqueda con 16 operaciones
- Menú interactivo con 13 opciones + datos de prueba
- Trabajo colaborativo entre 3 estudiantes usando Git y GitHub

**Fuera del alcance:**
- Interfaz gráfica
- Base de datos
- Persistencia de datos (se pierden al cerrar el programa)

### 2.5 Objetivos

1. Implementar un Árbol Binario de Búsqueda completo en Java
2. Entender recursión aplicada a estructuras de datos
3. Practicar trabajo colaborativo con GitHub (ramas, PRs, merge)
4. Aprender los 3 recorridos (inorden, preorden, postorden) y los 3 casos de eliminación

### 2.6 Descripción de entregables

| No. | Entregable | Descripción | Paso |
|-----|-----------|-------------|------|
| 1 | Código base | Producto.java + ArbolInventario.java + Main.java con insertar, inorden y buscar | 3 |
| 2 | Recorridos preorden y postorden | Dos métodos nuevos en ArbolInventario + opciones 4 y 5 en menú | 5 |
| 3 | Eliminación de nodos | Método eliminar con 3 casos + opción 6 en menú | 6 |
| 4 | Estadísticas del árbol | Contar nodos, calcular altura, contar hojas + opciones 7, 8, 9 | 7 |
| 5 | Extensión mínima y máxima | Dos métodos con while + opciones 10 y 11 | 8 |
| 6 | Búsqueda con detalle y nivel | Búsqueda que retorna info completa + mostrar por nivel + opciones 12, 13 | 9 |
| 7 | Datos de prueba y mejoras | Carga automática de datos + validaciones + try-catch | 10 |

### 2.7 Metodología

Se usa **Scrum adaptado** para un equipo de 3 personas:

| Elemento Scrum | Adaptación para este proyecto |
|----------------|-------------------------------|
| Sprint | Cada paso del tutorial es un sprint (1-2 horas) |
| Sprint Planning | Al inicio de cada paso, revisar qué hace cada estudiante |
| Daily Standup | Comunicación breve antes de empezar a codificar |
| Sprint Review | Verificar que compila (`javac src/*.java -d out`) después de cada merge |
| Product Backlog | Lista de historias de usuario (sección 3) |
| Sprint Backlog | Tareas asignadas por estudiante en cada sprint (sección 5) |

### 2.8 Roles

| Rol Scrum | Quién | Responsabilidades en GitHub |
|-----------|-------|----------------------------|
| Product Owner | Profesor | Define qué se construye, prioriza historias |
| Scrum Master | est1 | Administra el repo, revisa PRs, hace merge, resuelve conflictos |
| Desarrollador | est1 | Trabaja en sus ramas, crea PRs |
| Desarrollador | est2 | Trabaja en sus ramas, crea PRs |
| Desarrollador | est3 | Trabaja en sus ramas, crea PRs |

---

## 3. Historias de usuario

### HU-01: Código base del proyecto

| Campo | Valor |
|-------|-------|
| **Nombre** | Código base con insertar, inorden y buscar |
| **Prioridad** | Alta |
| **Horas estimadas** | 2 |
| **Iteración** | Sprint 1 (Paso 3) |
| **Responsable** | est1 |

**Descripción:**
Yo como profesor, quiero que el equipo tenga un proyecto Java con la estructura básica del ABB (insertar, mostrar inorden, buscar), subido a GitHub con los 3 estudiantes como colaboradores.

**Criterios de aceptación:**
- Compila con `javac src/*.java -d out` sin errores
- Ejecuta con `java -cp out Main` y muestra el menú con 3 opciones
- El repositorio está en GitHub con est2 y est3 como colaboradores

---

### HU-02: Recorridos preorden y postorden

| Campo | Valor |
|-------|-------|
| **Nombre** | Agregar recorridos preorden y postorden |
| **Prioridad** | Alta |
| **Horas estimadas** | 1 |
| **Iteración** | Sprint 2 (Paso 5) |
| **Responsable** | est1 |
| **Rama** | `feature/recorridos` |

**Descripción:**
Yo como profesor, quiero que el sistema muestre los datos en los 3 órdenes de recorrido para verificar que el árbol está bien construido.

**Criterios de aceptación:**
- Preorden muestra: Raíz → Izquierda → Derecha
- Postorden muestra: Izquierda → Derecha → Raíz
- Opciones 4 y 5 en el menú

---

### HU-03: Eliminación de nodos

| Campo | Valor |
|-------|-------|
| **Nombre** | Eliminar nodos con los 3 casos |
| **Prioridad** | Alta |
| **Horas estimadas** | 2 |
| **Iteración** | Sprint 3 (Paso 6) |
| **Responsable** | est2 |
| **Rama** | `feature/eliminar` |

**Descripción:**
Yo como profesor, quiero poder eliminar extensiones del directorio, manejando los 3 casos (hoja, un hijo, dos hijos).

**Criterios de aceptación:**
- Caso 1: eliminar nodo hoja
- Caso 2: eliminar nodo con un hijo
- Caso 3: eliminar nodo con dos hijos (sucesor inorden)
- Opción 6 en el menú

---

### HU-04: Estadísticas del árbol

| Campo | Valor |
|-------|-------|
| **Nombre** | Contar nodos, calcular altura y contar hojas |
| **Prioridad** | Media |
| **Horas estimadas** | 1 |
| **Iteración** | Sprint 3 (Paso 7) |
| **Responsable** | est3 |
| **Rama** | `feature/estadisticas` |

**Descripción:**
Yo como profesor, quiero poder ver estadísticas del árbol para verificar su estructura.

**Criterios de aceptación:**
- Contar nodos retorna el total
- Calcular altura retorna los niveles
- Contar hojas retorna nodos sin hijos
- Opciones 7, 8 y 9 en el menú

---

### HU-05: Extensión mínima y máxima

| Campo | Valor |
|-------|-------|
| **Nombre** | Encontrar extensión mínima y máxima |
| **Prioridad** | Media |
| **Horas estimadas** | 1 |
| **Iteración** | Sprint 4 (Paso 8) |
| **Responsable** | est1 |
| **Rama** | `feature/min-max` |

**Descripción:**
Yo como profesor, quiero encontrar la extensión más pequeña y más grande del directorio.

**Criterios de aceptación:**
- Mínimo baja siempre por la izquierda
- Máximo baja siempre por la derecha
- Opciones 10 y 11 en el menú

---

### HU-06: Búsqueda con detalle y mostrar por nivel

| Campo | Valor |
|-------|-------|
| **Nombre** | Búsqueda con información completa y mostrar nodos por nivel |
| **Prioridad** | Media |
| **Horas estimadas** | 2 |
| **Iteración** | Sprint 4 (Paso 9) |
| **Responsable** | est2 |
| **Rama** | `feature/busqueda-detalle` |

**Descripción:**
Yo como profesor, quiero buscar una extensión y ver su nombre de oficina, y poder ver los nodos de un nivel específico.

**Criterios de aceptación:**
- Buscar con detalle retorna "Extensión: X | Oficina: Y"
- Mostrar nivel muestra solo los nodos de ese nivel
- Opciones 12 y 13 en el menú

---

### HU-07: Datos de prueba y mejoras

| Campo | Valor |
|-------|-------|
| **Nombre** | Carga automática de datos, validaciones y manejo de errores |
| **Prioridad** | Baja |
| **Horas estimadas** | 2 |
| **Iteración** | Sprint 5 (Paso 10) |
| **Responsable** | est3 |
| **Rama** | `feature/mejoras` |

**Descripción:**
Yo como profesor, quiero que el sistema cargue datos de prueba automáticamente y maneje errores de entrada.

**Criterios de aceptación:**
- `cargarDatosDePrueba()` inserta 8 extensiones
- `estaVacio()` valida antes de operar en árbol sin datos
- try-catch atrapa errores cuando se ingresan letras en vez de números

---

## 4. Reunión inicial del equipo

Antes de empezar a codificar, el equipo debe reunirse para definir:

### 4.1 Checklist de la reunión

- [ ] **Clonar el repositorio** — verificar que los 3 pueden clonar, compilar (`javac src/*.java -d out`) y ejecutar (`java -cp out Main`)
- [ ] **Acordar convenciones de ramas** — usar `feature/` para funcionalidades nuevas
- [ ] **Acordar convenciones de commits** — usar el formato `tipo: descripción`
- [ ] **Revisar las historias de usuario** — entender qué hace cada uno
- [ ] **Definir flujo de PRs** — quien hace push crea el PR, est1 revisa y hace merge
- [ ] **Definir canal de comunicación** — WhatsApp, Discord, Teams, etc.

### 4.2 Preguntas que debe responder la reunión

| Pregunta | Respuesta |
|----------|-----------|
| ¿Quién administra el repo? | est1 |
| ¿Quién revisa los PRs? | est1 revisa los de est2 y est3. est2 o est3 revisan los de est1 |
| ¿Quién hace merge? | Solo est1 |
| ¿Cómo nos avisamos que un PR está listo? | Mensaje en el grupo de WhatsApp |
| ¿Cada cuánto hacemos pull de main? | Antes de crear cada rama nueva |
| ¿Qué hacemos si alguien se atrasa? | Se redistribuyen tareas |

---

## 5. Cronograma por sprints

| Sprint | Paso | Entregables | est1 | est2 | est3 | Horas |
|--------|------|-------------|------|------|------|-------|
| **Sprint 1** | 1-3 | Proyecto + GitHub | Crear repo, código base | Clonar, verificar | Clonar, verificar | 2 |
| **Sprint 2** | 5 | Recorridos | Preorden + Postorden + PR | Pull y verificar | Pull y verificar | 1 |
| **Sprint 3** | 6-7 | Eliminar + Estadísticas | Revisar PRs + merge | Eliminar nodos + PR | Conteo, altura, hojas + PR | 3 |
| **Sprint 4** | 8-9 | Min/Max + Búsqueda | Min/Max + PR | Búsqueda detalle + PR | Pull y verificar | 3 |
| **Sprint 5** | 10 | Datos prueba + Mejoras | Revisar PR + merge | Pull y verificar | Datos prueba + PR | 2 |
| | | | | | **Total estimado:** | **11** |

### Distribución de carga por estudiante

| Estudiante | Historias asignadas | Métodos | Horas estimadas |
|------------|-------------------|---------|-----------------|
| est1 | HU-01, HU-02, HU-05 | 7 métodos | 4 |
| est2 | HU-03, HU-06 | 5 métodos | 4 |
| est3 | HU-04, HU-07 | 4 métodos | 3 |

---

## 6. Resumen de convenciones acordadas

| Convención | Acuerdo |
|------------|---------|
| **Prefijo de ramas** | `feature/`, `fix/`, `docs/` |
| **Formato de commit** | `tipo: descripción` (feat, fix, docs, refactor) |
| **Quién hace merge** | est1 (Scrum Master) |
| **Quién revisa PRs** | est1 revisa los de est2 y est3. est2 o est3 revisan los de est1 |
| **Canal de comunicación** | Grupo de WhatsApp |
| **Cuándo hacer pull** | Siempre antes de crear una rama nueva |
| **Qué hacer si hay conflicto** | Resolverlo en GitHub o pedir ayuda |
| **Compilar antes de push** | Siempre: `javac src/*.java -d out` |

---

> **Siguiente paso:** Paso 1 — Conceptos Básicos de Árboles Binarios.
