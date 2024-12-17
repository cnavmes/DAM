/*6. Construir una aplicación en una clase tipo main llamada
productorConsumidorAeronautico tipo Productor-Consumidor usando un
BlockingQueue de 20 elementos.
Se modelará un productor de tipo aeronaútico que produce piezas metálicas, que serán los
componentes que se vayan guardando en la cola bloqueante.
Los elementos metálicos se modelarán con una clase llamada PiezaAero, que tendrá como
atributos un ID, de tipo int, y que será alealtorio y una descripción tipo String que será
siempre “Pieza aeronautica”.
El productor producirá de manera continua piezas y al almanecerá en la cola, hasta que se
bloquee, y el consumidor las irá retirando.
Por pantalla nos irá mostrando mensajes por parte del productor y consumidor. El productor
informará de la pieza que introducido en la cola y del tamaño de la cola actualizado.
Lo mismo por parte del consumidor. */

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class ProductorConsumidorAeronautico {
    public static void main(String[] args) throws Exception {
        int capacidad = 20;
        BlockingQueue<PiezaAero> cola = new LinkedBlockingQueue<>(capacidad);
        ExecutorService eS = Executors.newFixedThreadPool(2);
        Runnable productor;
        Runnable consumidor;

        Runtime.getRuntime().addShutdownHook(new Thread(() -> { // ShutDownHook para cierre seguro de recursos
            System.out.println("Cerrando aplicacion");
            eS.shutdown();
            System.out.println("Hilos finalizados");
        }

        ));

        productor = () -> {
            try {
                while (true) {
                    PiezaAero pieza = new PiezaAero();
                    cola.put(pieza);
                    System.out.println("Productor: Añadida " + pieza + "Tamaño de la cola: " + cola.size());
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Productor interrumpido");
            }
        };
        consumidor = () -> {
            try {
                while (true) {
                    PiezaAero pieza = cola.take();
                    System.out.println("Consumidor: Retirada " + pieza + " Tamaño de la cola: " + cola.size());
                    Thread.sleep(4000);
                }
            } catch (InterruptedException e) {
                System.out.println("Consumidor interrumpido");
                Thread.currentThread().interrupt();
            }
        };
        eS.execute(productor);
        eS.execute(consumidor);
    }
}
