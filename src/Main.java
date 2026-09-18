import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Crea una instancia del árbol para guardar las extensiones
        ArbolInventario miArbol = new ArbolInventario();
        Scanner sc = new Scanner(System.in);  // Objeto para leer datos del usuario
        int opcion = -1;  // Guardará la opción que elija el usuario

        // Bucle que muestra el menú hasta que el usuario elija salir (opción 0)
        while (opcion != 0) {
            System.out.println("\n--- DIRECTORIO DE EXTENSIONES ---");
            mostrarArbolEjemplo();  // Dibuja el árbol de referencia
            System.out.println("1. Registrar Extensión");
            System.out.println("2. Ver Directorio Inorden   (ejemplo: Izquierda -> Raíz -> Derecha = 20, 30, 40, 50, 60, 70, 80)");
            System.out.println("3. Ver Directorio Preorden  (ejemplo: Raíz -> Izquierda -> Derecha = 50, 30, 20, 40, 70, 60, 80)");
            System.out.println("4. Ver Directorio Postorden (ejemplo: Izquierda -> Derecha -> Raíz = 20, 40, 30, 60, 80, 70, 50)");
            System.out.println("5. Buscar Extensión");
            System.out.println("6. Eliminar Extensión");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            
            // Manejo de errores: si el usuario ingresa algo que no es un número
            try {
                opcion = sc.nextInt();  // Lee la opción del usuario
            } catch (Exception e) {
                System.out.println("Entrada inválida. Debe ingresar un número.");
                sc.next();  // Limpia el buffer del Scanner
                continue;   // Vuelve a mostrar el menú
            }

            // Según la opción elegida, hace una cosa u otra
            switch (opcion) {
                case 1:  // Registrar nueva extensión
                    System.out.print("Ingrese número de extensión: ");
                    try {
                        int id = sc.nextInt();
                        sc.nextLine();  // Limpia el buffer después de leer número
                        System.out.print("Nombre de la oficina: ");
                        String nombre = sc.nextLine();  // Lee el nombre
                        miArbol.insertar(id, nombre);   // Inserta en el árbol
                        System.out.println("Registrado con éxito.");
                    } catch (Exception e) {
                        System.out.println("ID debe ser un número válido.");
                        sc.next();
                    }
                    break;
                    
                case 2:  // Recorrido inorden (menor a mayor)
                    mostrarEncabezadoRecorrido("INORDEN", "Izquierda -> Raíz -> Derecha");
                    mostrarDirectorio(miArbol, 2);
                    break;

                case 3:  // Recorrido preorden (raíz primero)
                    mostrarEncabezadoRecorrido("PREORDEN", "Raíz -> Izquierda -> Derecha");
                    mostrarDirectorio(miArbol, 3);
                    break;

                case 4:  // Recorrido postorden (raíz al final)
                    mostrarEncabezadoRecorrido("POSTORDEN", "Izquierda -> Derecha -> Raíz");
                    mostrarDirectorio(miArbol, 4);
                    break;

                case 5:  // Buscar una extensión por su ID
                    System.out.print("ID a buscar: ");
                    try {
                        int buscaId = sc.nextInt();
                        System.out.println(miArbol.buscar(buscaId));
                    } catch (Exception e) {
                        System.out.println("ID debe ser un número válido.");
                        sc.next();
                    }
                    break;

                case 6:  // Eliminar una extensión por su ID
                    System.out.print("ID a eliminar: ");
                    try {
                        int eliminaId = sc.nextInt();
                        System.out.println(miArbol.eliminar(eliminaId));
                    } catch (Exception e) {
                        System.out.println("ID debe ser un número válido.");
                        sc.next();
                    }
                    break;
                    
                case 0:  // Salir del programa
                    System.out.println("Saliendo del sistema...");
                    break;
                    
                default:  // Opción no válida
                    System.out.println("Opción no válida.");
            }
        }
        sc.close();  // Cierra el Scanner cuando termina el programa
    }

    // Dibuja el árbol de ejemplo que sirve de referencia en el menú.
    // Corresponde a insertar en este orden: 50, 30, 70, 20, 40, 60, 80
    private static void mostrarArbolEjemplo() {
        System.out.println();
        System.out.println();
        System.out.println("Árbol de ejemplo:");
        System.out.println("            50");
        System.out.println("          /    \\");
        System.out.println("        30      70");
        System.out.println("       /  \\    /  \\");
        System.out.println("     20   40  60   80");
        System.out.println();
    }

    // Imprime el nombre del recorrido y su orden de visita.
    // El resultado de ejemplo ya se ve en el menú, por eso no se repite aquí.
    private static void mostrarEncabezadoRecorrido(String nombre, String orden) {
        System.out.println("\n" + nombre + " | " + orden);
    }

    // Lanza sobre el árbol del usuario el recorrido que eligió en el menú.
    // Main no conoce la raíz ni la clase Oficina: solo le pide al árbol que
    // se muestre. Todo el detalle de cómo se recorre queda dentro de
    // ArbolInventario, que es el que sabe cómo está armado por dentro.
    private static void mostrarDirectorio(ArbolInventario arbol, int recorrido) {
        // Si todavía no hay extensiones, avisa en vez de no imprimir nada
        if (arbol.estaVacio()) {
            System.out.println("Su directorio: (vacío, aún no ha registrado extensiones)");
            return;
        }

        System.out.println("Su directorio:");
        switch (recorrido) {
            case 2:
                arbol.mostrarInorden();    // menor a mayor
                break;
            case 3:
                arbol.mostrarPreorden();   // la raíz primero
                break;
            case 4:
                arbol.mostrarPostorden();  // la raíz al final
                break;
        }
    }
}