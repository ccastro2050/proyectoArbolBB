/**
 * Árbol Binario de Búsqueda (ABB) que almacena las extensiones telefónicas.
 *
 * Regla de orden del ABB: las extensiones menores quedan en la rama izquierda
 * y las mayores en la rama derecha. Gracias a esa regla, cada comparación
 * descarta media rama y la búsqueda es rápida.
 *
 * PATRÓN QUE SE REPITE EN TODA LA CLASE (método envoltorio):
 * Cada operación se escribe con DOS métodos:
 *
 *   1. Uno PÚBLICO, sin el nodo como parámetro: es el que usa Main y define
 *      QUÉ se hace (insertar, buscar, eliminar, mostrar...). Arranca siempre
 *      desde la raíz.
 *   2. Uno PRIVADO y recursivo, que recibe el nodo actual: define CÓMO se
 *      hace, bajando por el árbol nodo por nodo.
 *
 * ¿Por qué dos? Porque la recursión necesita saber en qué nodo va, y ese
 * dato es interno del árbol. Si el método público pidiera el nodo, quien lo
 * usa tendría que conocer la raíz y la clase Oficina, que es justo lo que se
 * quiere ocultar. Main solo dice "inserta esto"; no sabe cómo está armado
 * el árbol por dentro.
 */
public class ArbolInventario {
    private Oficina raiz;  // Primer nodo del árbol; es privado, nadie fuera lo ve

    // Constructor: el árbol nace vacío (sin raíz)
    public ArbolInventario() {
        this.raiz = null;
    }

    // Indica si el árbol todavía no tiene ningún nodo.
    // Main lo usa para avisar que no hay nada que mostrar, sin necesidad
    // de pedir la raíz ni tocar los nodos por dentro.
    public boolean estaVacio() {
        return raiz == null;
    }

    // ---------------------------------------------------------------
    // INSERTAR
    // ---------------------------------------------------------------

    // Público: registra una extensión nueva. Empieza el trabajo desde la raíz.
    public void insertar(int id, String nombre) {
        // La asignación "raiz =" es imprescindible: cuando el árbol está
        // vacío, raiz vale null y no se puede modificar un null desde
        // adentro. La única forma de que la raíz apunte al nodo nuevo es
        // que la recursión lo devuelva y aquí se guarde.
        raiz = insertarRecursivo(raiz, id, nombre);
    }

    // Privado y recursivo: busca el sitio libre donde cuelga el nodo nuevo.
    // Devuelve el nodo que debe quedar en esta posición del árbol.
    private Oficina insertarRecursivo(Oficina actual, int id, String nombre) {
        // CASO BASE: llegó a un sitio vacío, así que aquí va el nodo nuevo
        if (actual == null) {
            return new Oficina(id, nombre);
        }

        // Si el id es menor, el sitio está en la rama izquierda.
        // El resultado se vuelve a enganchar con setIzquierdo, porque la
        // recursión devuelve la rama ya actualizada.
        if (id < actual.getId()) {
            actual.setIzquierdo(insertarRecursivo(actual.getIzquierdo(), id, nombre));
        }
        // Si el id es mayor, el sitio está en la rama derecha
        else if (id > actual.getId()) {
            actual.setDerecho(insertarRecursivo(actual.getDerecho(), id, nombre));
        }
        // Si es igual, no hace nada: el ABB no admite extensiones repetidas

        return actual;  // Devuelve este nodo, ya con su rama actualizada
    }

    // ---------------------------------------------------------------
    // RECORRIDOS
    // Los tres visitan todos los nodos; lo único que cambia es EN QUÉ
    // MOMENTO se imprime el nodo respecto a sus dos ramas.
    // ---------------------------------------------------------------

    // Público: recorrido INORDEN (Izquierda -> Raíz -> Derecha).
    // Es el más útil en un ABB porque saca los datos de menor a mayor.
    public void mostrarInorden() {
        mostrarInordenRecursivo(raiz);  // arranca desde la raíz
    }

    private void mostrarInordenRecursivo(Oficina nodo) {
        if (nodo != null) {                              // caso base: null, no hace nada
            mostrarInordenRecursivo(nodo.getIzquierdo());  // 1. toda la rama izquierda
            imprimir(nodo);                                // 2. el nodo (la raíz de este subárbol)
            mostrarInordenRecursivo(nodo.getDerecho());    // 3. toda la rama derecha
        }
    }

    // Público: recorrido PREORDEN (Raíz -> Izquierda -> Derecha).
    // Sirve para copiar o reconstruir el árbol, porque la raíz sale primero.
    public void mostrarPreorden() {
        mostrarPreordenRecursivo(raiz);
    }

    private void mostrarPreordenRecursivo(Oficina nodo) {
        if (nodo != null) {
            imprimir(nodo);                                 // 1. el nodo primero
            mostrarPreordenRecursivo(nodo.getIzquierdo());  // 2. rama izquierda
            mostrarPreordenRecursivo(nodo.getDerecho());    // 3. rama derecha
        }
    }

    // Público: recorrido POSTORDEN (Izquierda -> Derecha -> Raíz).
    // Sirve para liberar el árbol, porque visita los hijos antes que el padre.
    public void mostrarPostorden() {
        mostrarPostordenRecursivo(raiz);
    }

    private void mostrarPostordenRecursivo(Oficina nodo) {
        if (nodo != null) {
            mostrarPostordenRecursivo(nodo.getIzquierdo());  // 1. rama izquierda
            mostrarPostordenRecursivo(nodo.getDerecho());    // 2. rama derecha
            imprimir(nodo);                                  // 3. el nodo al final
        }
    }

    // Da formato a una línea del directorio. Está aparte para que los tres
    // recorridos impriman igual y no se repita el texto tres veces.
    private void imprimir(Oficina nodo) {
        System.out.println("Extensión: " + nodo.getId() + " | Oficina: " + nodo.getNombre());
    }

    // ---------------------------------------------------------------
    // BUSCAR
    // ---------------------------------------------------------------

    // Público: informa si la extensión existe. Devuelve el mensaje ya listo
    // para mostrar, así Main no tiene que interpretar un true o un false.
    public String buscar(int id) {
        return buscarRecursivo(raiz, id) ? "ID encontrado en el sistema." : "El ID no existe.";
    }

    // Privado y recursivo: baja por una sola rama, comparando en cada nodo
    private boolean buscarRecursivo(Oficina actual, int id) {
        if (actual == null) return false;           // Caso base: se acabó la rama, no está
        if (id == actual.getId()) return true;      // Caso base: ¡lo encontró!

        // Si el id buscado es menor sigue por la izquierda; si no, por la
        // derecha. Nunca revisa las dos ramas: por eso la búsqueda es rápida.
        return id < actual.getId()
            ? buscarRecursivo(actual.getIzquierdo(), id)
            : buscarRecursivo(actual.getDerecho(), id);
    }

    // ---------------------------------------------------------------
    // ELIMINAR
    // ---------------------------------------------------------------

    // Público: borra una extensión y devuelve el mensaje del resultado
    public String eliminar(int id) {
        // Se comprueba antes de borrar, para poder avisar si el ID no existe
        if (!buscarRecursivo(raiz, id)) {
            return "El ID no existe en el sistema.";
        }
        // Igual que en insertar, el resultado se reasigna a raiz, porque el
        // nodo eliminado puede ser precisamente la raíz
        raiz = eliminarRecursivo(raiz, id);
        return "Extensión eliminada.";
    }

    // Privado y recursivo: localiza el nodo y aplica el caso que corresponda.
    // Devuelve el nodo que debe quedar en esta posición (o null si se borró).
    private Oficina eliminarRecursivo(Oficina actual, int id) {
        // Caso base: se acabó la rama sin encontrarlo
        if (actual == null) {
            return null;
        }

        // Mientras no sea el nodo buscado, sigue bajando y vuelve a enganchar
        // la rama que devuelve la recursión
        if (id < actual.getId()) {
            actual.setIzquierdo(eliminarRecursivo(actual.getIzquierdo(), id));
        } else if (id > actual.getId()) {
            actual.setDerecho(eliminarRecursivo(actual.getDerecho(), id));
        } else {
            // Encontró el nodo a eliminar. Ahora decide cuál de los tres
            // casos aplica, según cuántos hijos tenga.

            // CASO 1: es una hoja (no tiene hijos).
            // Se devuelve null, y el padre deja de apuntarlo: desaparece.
            if (actual.getIzquierdo() == null && actual.getDerecho() == null) {
                return null;
            }

            // CASO 2: tiene un solo hijo.
            // Se devuelve ese hijo, que sube a ocupar el lugar del eliminado.
            if (actual.getIzquierdo() == null) {
                return actual.getDerecho();    // solo tiene hijo derecho
            }
            if (actual.getDerecho() == null) {
                return actual.getIzquierdo();  // solo tiene hijo izquierdo
            }

            // CASO 3: tiene dos hijos. No se puede borrar sin romper el árbol,
            // así que se reemplaza por su SUCESOR INORDEN: el menor valor de
            // la rama derecha. Al ser "el menor de los mayores", sigue siendo
            // mayor que toda la rama izquierda y menor que el resto de la
            // derecha, así que la regla de orden se mantiene intacta.
            Oficina sucesor = buscarSucesor(actual.getDerecho());

            // Se copian los datos del sucesor sobre este nodo
            actual.setId(sucesor.getId());
            actual.setNombre(sucesor.getNombre());

            // Y se borra el sucesor de la rama derecha, donde estaba duplicado.
            // Ese borrado siempre cae en el caso 1 o 2, nunca en el 3, porque
            // el sucesor no puede tener hijo izquierdo.
            actual.setDerecho(eliminarRecursivo(actual.getDerecho(), sucesor.getId()));
        }

        return actual;  // Devuelve este nodo, ya actualizado
    }

    // Busca el nodo menor de un subárbol: el que está más a la izquierda.
    // Aquí se usa un while y no recursión porque el recorrido es en línea
    // recta, sin ramificarse: no hay que volver atrás en ningún momento.
    private Oficina buscarSucesor(Oficina nodo) {
        Oficina actual = nodo;
        while (actual.getIzquierdo() != null) {
            actual = actual.getIzquierdo();
        }
        return actual;  // Ya no hay nada más a la izquierda: este es el menor
    }
}
