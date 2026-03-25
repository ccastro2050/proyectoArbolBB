import java.util.Scanner;

/**
 * Clase Main - Punto de entrada del programa e interfaz de usuario por consola.
 *
 * Presenta un menú interactivo con las siguientes opciones:
 * 1. Registrar Extensión: solicita al usuario un ID numérico y un nombre
 *    de oficina, y los inserta en el árbol binario de búsqueda.
 * 2. Ver Directorio: muestra todas las extensiones registradas ordenadas
 *    de menor a mayor, gracias al recorrido inorden del árbol.
 * 3. Buscar Extensión: permite verificar si un ID existe en el árbol,
 *    informando al usuario el resultado de la búsqueda.
 * 0. Salir: finaliza la ejecución del programa.
 *
 * Utiliza la clase Scanner para leer la entrada del usuario desde la consola
 * y la clase ArbolInventario para realizar todas las operaciones sobre el árbol.
 */
public class Main {
    public static void main(String[] args) {
        ArbolInventario miArbol = new ArbolInventario();
        Scanner sc = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n--- DIRECTORIO DE EXTENSIONES ---");
            System.out.println("1. Registrar Extensión");
            System.out.println("2. Ver Directorio (Ordenado)");
            System.out.println("3. Buscar Extensión");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese número de extensión: ");
                    int id = sc.nextInt();
                    sc.nextLine(); // Limpiar el buffer
                    System.out.print("Nombre de la oficina: ");
                    String nombre = sc.nextLine();
                    miArbol.insertar(id, nombre);
                    System.out.println("Registrado con éxito.");
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
                    System.out.println("Opción no válida.");
            }
        }
        sc.close();
    }
}