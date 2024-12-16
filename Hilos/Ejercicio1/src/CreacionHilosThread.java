
/*
 * En una clase tipo main llamada creacionHilosThread, crear 5 hilos heredando de la clase
Thread.
Estos hilos calcularán la multiplicación de dos números que se les pasarán como parámetros
a los objetos y además se les podrá asignar un número de hilo que también se les pasará
como parámetro.
El constructor de esta clase tendrá la forma public multiplicacionNumeros(long num1,long
num2,int numHilo)
Los hilos se arrancarán y se imprimirán los resultados, incluyendo el número de hilo que le
hemos asignado.
Habrá un mensaje final de “Final de programa” en el hilo principal.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CreacionHilosThread {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        ExecutorService eS = Executors.newFixedThreadPool(5);
        long num1, num2;
        int numHilos;
        List<MultiplicacionNumeros> operaciones = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            System.out.println("Introduce primer numero");
            num1 = sc.nextLong();
            System.out.println("Introduce segundo numero:");
            num2 = sc.nextLong();
            System.out.println("Introduce numero de hilo:");
            numHilos = sc.nextInt();

            operaciones.add(new MultiplicacionNumeros(num1, num2, numHilos));
        }
        for (MultiplicacionNumeros operacion : operaciones) {
            eS.execute(operacion);
        }
        eS.shutdown();

        try {
            eS.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Espera interrumpida");
        }
        System.out.println("Final del programa");
    }
}
