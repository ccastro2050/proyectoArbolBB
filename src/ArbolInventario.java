/**
 * Clase ArbolInventario - Contiene toda la lógica del Árbol Binario de Búsqueda (ABB).
 *
 * Gestiona la estructura del árbol a través de tres operaciones principales:
 * 1. Insertar: agrega un nuevo nodo (extensión) en la posición correcta
 *    del árbol, respetando la regla del ABB (menores a la izquierda,
 *    mayores a la derecha). Utiliza recursión para recorrer el árbol
 *    hasta encontrar el lugar vacío donde debe ir el nuevo nodo.
 * 2. Mostrar Inorden: recorre el árbol en orden izquierda-raíz-derecha,
 *    lo que produce un listado ordenado de menor a mayor de todas
 *    las extensiones registradas.
 * 3. Buscar: recorre el árbol comparando el ID buscado con cada nodo;
 *    si es menor baja por la izquierda, si es mayor por la derecha,
 *    hasta encontrarlo o llegar a un nodo vacío (no existe).
 *
 * La raíz (raiz) es el punto de entrada al árbol; todos los recorridos
 * y operaciones parten desde ella.
 */
public class ArbolInventario {
    Producto raiz; // Nodo principal del árbol

    public ArbolInventario() {
        this.raiz = null;
    }

    // MÉTODO 1: INSERTAR (Punto de entrada)
    public void insertar(int id, String nombre) {
        raiz = insertarRecursivo(raiz, id, nombre);
    }

    // Lógica recursiva para insertar
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

    // MÉTODO 2: MOSTRAR INORDEN (Muestra los datos ordenados de menor a mayor)
    public void mostrarInorden(Producto nodo) {
        if (nodo != null) {
            mostrarInorden(nodo.izquierdo); // Visita rama izquierda
            System.out.println("Extensión: " + nodo.id + " | Oficina: " + nodo.nombre);
            mostrarInorden(nodo.derecho);   // Visita rama derecha
        }
    }

    // MÉTODO 3: BUSCAR (Retorna un mensaje según la existencia del ID)
    public String buscar(int id) {
        return buscarRecursivo(raiz, id) ? "ID encontrado en el sistema." : "El ID no existe.";
    }

    private boolean buscarRecursivo(Producto actual, int id) {
        if (actual == null) return false; // No se encontró
        if (id == actual.id) return true; // ¡Encontrado!

        // Decidir hacia qué rama bajar
        return id < actual.id 
            ? buscarRecursivo(actual.izquierdo, id) 
            : buscarRecursivo(actual.derecho, id);
    }
}