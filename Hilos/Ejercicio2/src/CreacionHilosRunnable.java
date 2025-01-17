/*
 * En una clase tipo main llamada creacionHilosRunnable, se crearán o 5 hilos, pero esta
vez implementando la interfaz Runnable.
Los objetos de estas clases realizarán la misma operación que en el caso, anterior, es decir,
se calculará la multiplicación de dos números. También se les podrá asignar un número de
hilo.
El constructor de esta clase tendrá la forma public multiplicacionNumerosRunnable(long
num1,long num2,int numHilo)
Los hilos se arrancarán y se imprimirán los resultados, incluyendo el número de hilo que le
hemos asignado.
Además, se crearán otros 5 hilos de tipo Runnable, pero esta vez usando una funcion
lambda.
Se arrancarán también y se imprimirán los resultados.
Habrá un mensaje final de “Final de programa” en el hilo principal.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CreacionHilosRunnable {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        List<Runnable> operaciones = new ArrayList<>();

        operaciones.addAll(crearOperaciones());
        operaciones.addAll(crearOperacionesLambda());

        ejecutarHilos(operaciones);

        System.out.println("Fin del programa");

    }

    private static List<Runnable> crearTareas(int cantidad, boolean esLambda) {
        List<Runnable> tareas = new ArrayList<>();
        long num1, num2;
        int numHilo;

        for (int i = 1; i <= cantidad; i++) {
            System.out.println("Introduce primer numero");
            num1 = sc.nextLong();
            System.out.println("Introduce segundo numero");
            num2 = sc.nextLong();
            System.out.println("Introduce numero de hilo");
            numHilo = sc.nextInt();

            if (esLambda) {
                long num1Lambda = num1;
                long num2Lambda = num2;
                int numHiloLambda = numHilo;

                tareas.add(() -> {
                    long resultado = num1Lambda * num2Lambda;
                    System.out.println("Hilo Lambda: " + numHiloLambda + "//" + num1Lambda + " x " + num2Lambda + " = "
                            + resultado);
                }

                );
            } else {
                tareas.add(new MultiplicacionNumerosRunnable(num1, num2, numHilo));
            }
        }
        return tareas;
    }

    private static List<Runnable> crearOperaciones() {
        return crearTareas(5, false);
    }

    private static List<Runnable> crearOperacionesLambda() {
        return crearTareas(5, true);
    }

    private static void ejecutarHilos(List<Runnable> tareas) {
        ExecutorService eS = Executors.newFixedThreadPool(5);

        for (Runnable tarea : tareas) {
            eS.submit(tarea);
        }
        eS.shutdown();

        try {
            eS.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
