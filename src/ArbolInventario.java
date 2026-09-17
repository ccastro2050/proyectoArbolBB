public class ArbolInventario {
    private Oficina raiz;  // Primer nodo del árbol (punto de entrada)

    // Constructor: árbol vacío al inicio
    public ArbolInventario() {
        this.raiz = null;
    }

    // Getter: permite obtener la raíz desde Main
    public Oficina getRaiz() {
        return raiz;
    }

    // Inserta un nuevo nodo en el árbol
    public void insertar(int id, String nombre) {
        raiz = insertarRecursivo(raiz, id, nombre);
    }

    // Método recursivo que busca dónde colocar el nuevo nodo
    private Oficina insertarRecursivo(Oficina actual, int id, String nombre) {
        // Si llegó a un lugar vacío, crea el nuevo nodo aquí
        if (actual == null) {
            return new Oficina(id, nombre);
        }

        // Si el id es menor, va a la rama izquierda
        if (id < actual.getId()) {
            actual.setIzquierdo(insertarRecursivo(actual.getIzquierdo(), id, nombre));
        } 
        // Si el id es mayor, va a la rama derecha
        else if (id > actual.getId()) {
            actual.setDerecho(insertarRecursivo(actual.getDerecho(), id, nombre));
        }
        // Si son iguales, no hace nada (evita duplicados)
        
        return actual;  // Devuelve el nodo actualizado
    }

    // Recorrido INORDEN: Izquierda -> Raíz -> Derecha (menor a mayor)
    public void mostrarInorden(Oficina nodo) {
        if (nodo != null) {
            mostrarInorden(nodo.getIzquierdo());
            System.out.println("Extensión: " + nodo.getId() + " | Oficina: " + nodo.getNombre());
            mostrarInorden(nodo.getDerecho());
        }
    }

    // Recorrido PREORDEN: Raíz -> Izquierda -> Derecha
    public void mostrarPreorden(Oficina nodo) {
        if (nodo != null) {
            System.out.println("Extensión: " + nodo.getId() + " | Oficina: " + nodo.getNombre());
            mostrarPreorden(nodo.getIzquierdo());
            mostrarPreorden(nodo.getDerecho());
        }
    }

    // Recorrido POSTORDEN: Izquierda -> Derecha -> Raíz
    public void mostrarPostorden(Oficina nodo) {
        if (nodo != null) {
            mostrarPostorden(nodo.getIzquierdo());
            mostrarPostorden(nodo.getDerecho());
            System.out.println("Extensión: " + nodo.getId() + " | Oficina: " + nodo.getNombre());
        }
    }

    // Busca un ID en el árbol y devuelve un mensaje
    public String buscar(int id) {
        return buscarRecursivo(raiz, id) ? "ID encontrado en el sistema." : "El ID no existe.";
    }

    // Método recursivo para buscar
    private boolean buscarRecursivo(Oficina actual, int id) {
        if (actual == null) return false;           // No lo encontró
        if (id == actual.getId()) return true;      // ¡Lo encontró!

        // Decide si buscar a la izquierda o derecha
        return id < actual.getId() 
            ? buscarRecursivo(actual.getIzquierdo(), id) 
            : buscarRecursivo(actual.getDerecho(), id);
    }

    // Elimina un nodo del árbol según su ID
    public String eliminar(int id) {
        // Primero verifica si el ID existe en el árbol
        if (!buscarRecursivo(raiz, id)) {
            return "El ID no existe en el sistema.";
        }
        raiz = eliminarRecursivo(raiz, id);
        return "Extensión eliminada.";
    }

    // Método recursivo que busca el nodo a eliminar
    private Oficina eliminarRecursivo(Oficina actual, int id) {
        // Caso: no encontró el nodo
        if (actual == null) {
            return null;
        }

        // Busca el nodo por la rama correspondiente
        if (id < actual.getId()) {
            // El nodo está en la rama izquierda
            actual.setIzquierdo(eliminarRecursivo(actual.getIzquierdo(), id));
        } else if (id > actual.getId()) {
            // El nodo está en la rama derecha
            actual.setDerecho(eliminarRecursivo(actual.getDerecho(), id));
        } else {
            // ¡Encontró el nodo a eliminar! Ahora decide qué caso aplica

            // CASO 1: Nodo hoja (sin hijos)
            // Solo devuelve null para eliminarlo
            if (actual.getIzquierdo() == null && actual.getDerecho() == null) {
                return null;
            }

            // CASO 2: Nodo con un solo hijo
            // Devuelve el hijo para reemplazar el nodo eliminado
            if (actual.getIzquierdo() == null) {
                return actual.getDerecho();  // Tiene solo hijo derecho
            }
            if (actual.getDerecho() == null) {
                return actual.getIzquierdo();  // Tiene solo hijo izquierdo
            }

            // CASO 3: Nodo con dos hijos
            // Busca el sucesor inorden (el menor de la rama derecha)
            Oficina sucesor = buscarSucesor(actual.getDerecho());
            
            // Copia los datos del sucesor al nodo actual
            actual.setId(sucesor.getId());
            actual.setNombre(sucesor.getNombre());
            
            // Elimina el sucesor de la rama derecha
            actual.setDerecho(eliminarRecursivo(actual.getDerecho(), sucesor.getId()));
        }

        return actual;  // Devuelve el nodo actualizado
    }

    // Busca el nodo con el valor mínimo (más a la izquierda)
    private Oficina buscarSucesor(Oficina nodo) {
        Oficina actual = nodo;
        // Mientras haya nodos a la izquierda, sigue avanzando
        while (actual.getIzquierdo() != null) {
            actual = actual.getIzquierdo();
        }
        return actual;  // Este es el nodo menor
    }
}