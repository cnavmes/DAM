/*
 * En una clase tipo main llamada creacionHilosCallable, se crearán o 5 hilos, pero usando
ejecutores, Futures y Callables.
Implementaremos una clase tipo Callable que se va a encargar de multiplicar 2 números,
devolviendo el resultado en un tipo Long. También se podrá asignar un número de hilo.
El constructor de esta clase se llamará multiplicacionNumerosCallable (long num1,long
num2,int numHilo)


Usaremos 2 ejecutores de tipo ExecutorService, uno de ellos de tipo singleThread y otro
fixedThread.
En cada uno de los ejecutores realizaremos la multiplicación de 3 operaciones de
multiplicación de 2 números, usando la clase creada anteriormente.
 */

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CreacionHilosCallable {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        ExecutorService single = Executors.newSingleThreadExecutor();
        ExecutorService fixed = Executors.newFixedThreadPool(3);
        List<Future<Long>> operaciones = new ArrayList<>();

        try {
            operaciones.addAll(cearTareas(single, 3));
            operaciones.addAll(cearTareas(fixed, 3));
            imprimirResultados(operaciones);
        } catch (Exception e) {

        } finally {
            single.shutdown();
            fixed.shutdown();
        }

    }

    public static List<Future<Long>> cearTareas(ExecutorService executor, int cantidad) {

        List<Future<Long>> resultados = new ArrayList<>();
        long num1, num2;
        int numHilo;

        try {
            for (int i = 1; i <= cantidad; i++) {
                System.out.println("Introduce primer numero");
                num1 = sc.nextLong();
                System.out.println("Introduce segundo numero");
                num2 = sc.nextLong();
                System.out.println("Introduce numero de hilo");
                numHilo = sc.nextInt();
                sc.nextLine();

                MultiplicacionNumerosCallable operacion = new MultiplicacionNumerosCallable(num1, num2, numHilo);
                Future<Long> future = executor.submit(operacion);
                resultados.add(future);
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada no valida");

        }
        return resultados;
    }

    public static void imprimirResultados(List<Future<Long>> resultados) {
        for (Future<Long> future : resultados) {
            try {
                System.out.println("Resultado: " + future.get());
            } catch (Exception e) {
                System.out.println("No se pudo obtener el resultado");
            }
        }
    }
}
