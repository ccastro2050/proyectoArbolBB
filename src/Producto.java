/**
 * Clase Producto - Representa un NODO dentro del Árbol Binario de Búsqueda.
 *
 * Cada nodo almacena la información de una extensión telefónica:
 * - id: número de extensión, usado como clave para ordenar en el árbol.
 * - nombre: nombre de la oficina o servicio asociado a esa extensión.
 * - izquierdo/derecho: referencias a los nodos hijos, que permiten
 *   formar la estructura del árbol (menor a la izquierda, mayor a la derecha).
 *
 * Esta clase es la unidad básica del árbol; sin ella no existirían
 * los nodos que ArbolInventario organiza y recorre.
 */
public class Producto {
    int id;              // Extensión telefónica (Dato para ordenar)
    String nombre;       // Nombre de la oficina o servicio
    Producto izquierdo;  // Referencia al hijo menor (rama izquierda)
    Producto derecho;    // Referencia al hijo mayor (rama derecha)

    // Constructor: Inicializa el nodo con sus datos y ramas vacías
    public Producto(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.izquierdo = null;
        this.derecho = null;
    }
}