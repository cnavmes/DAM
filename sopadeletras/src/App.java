import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        App app = new App();

        app.mostrarMenu();
    }

    public void mostrarMenu() {
        int opcion;
        GestorDeTematicas gestor = new GestorDeTematicas();
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println();
            System.out.println("""
                    ===SOPA DE LETRAS===
                    1. Iniciar juego
                    2. Agregar tematica
                    3. Salir
                    """);
            try {
                opcion = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Por favor, ingrese un número.");
                sc.nextLine();
                opcion = -1;
            }
            switch (opcion) {
                case 1 -> gestor.iniciarJuego();
                case 2 -> gestor.agregarTematica();
                case 3 -> System.out.println("Hasta luego");
                default -> System.out.println("Opcion no valida");
            }

        } while (opcion != 3);
        sc.close();
    }
}
