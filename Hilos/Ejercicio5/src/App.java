/*
 * Crear una clase cuentaBancariaAtomica que use una variable atomica de tipo
LongAdder para realizar el mismo ejercicio anterior, pero usando este tipo de variables.
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) throws Exception {
        int iteraciones = 50000;
        ExecutorService eS = Executors.newFixedThreadPool(4);
        CuentaBancariaAtomica cuenta = new CuentaBancariaAtomica();

        for (int i = 0; i < iteraciones; i++) {
            eS.execute(cuenta::procesoCuenta);
        }
        eS.shutdown();
        try {
            eS.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Cuenta atomica: " + cuenta.getCuenta());
    }
}
