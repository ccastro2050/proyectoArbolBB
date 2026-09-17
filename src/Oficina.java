public class Oficina {
    private int id;              // Número de extensión (clave para ordenar el árbol)
    private String nombre;       // Nombre de la oficina
    private Oficina izquierdo;  // Hijo menor (números menores que id)
    private Oficina derecho;    // Hijo mayor (números mayores que id)

    // Constructor: crea un nuevo nodo con sus dos ramas vacías
    public Oficina(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.izquierdo = null;  // No tiene hijo menor todavía
        this.derecho = null;    // No tiene hijo mayor todavía
    }

    // Getters: permiten leer los datos desde otra clase
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public Oficina getIzquierdo() { return izquierdo; }
    public Oficina getDerecho() { return derecho; }

    // Setters: permiten modificar los datos desde otra clase
    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setIzquierdo(Oficina izquierdo) { this.izquierdo = izquierdo; }
    public void setDerecho(Oficina derecho) { this.derecho = derecho; }
}
