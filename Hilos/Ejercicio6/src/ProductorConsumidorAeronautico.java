import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class ProductorConsumidorAeronautico {
    public static void main(String[] args) throws Exception {
        int capacidad = 20;
        BlockingQueue<PiezaAero> cola = new LinkedBlockingQueue<>(capacidad);
        ExecutorService eS = Executors.newFixedThreadPool(2);

        Runnable productor = () -> {
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
        Runnable consumidor = () -> {
            try {
                while (true) {
                    PiezaAero pieza = cola.take();
                    System.out.println("Consumidor: Retirada " + pieza + "Tamaño de la cola: " + cola.size());
                    Thread.sleep(1000);
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
