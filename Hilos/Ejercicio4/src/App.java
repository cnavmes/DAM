
/*
 * Crear dos clases, llamadas cuentaBancaria y cuentaBancariaSincronizada, donde la
primera de ellas va a ser no sincronizada y la segunda sincronizada.
Estas clases van a tener como único atributo int cuenta, que almacenará el valor actual de la
cuenta.
Tendrán también un método llamado procesoCuenta(), que en el caso de
cuentaBancariaSincronizada estará sincronizada, donde se van a realizar las siguientes
operaciones en la cuenta:

✓ Se suman 4 a la cuenta
✓ Se resta 5
✓ Se suman 6

Se creará un objeto de cada tipo, inicializando la cuenta a 0 en ambos casos.
Para cada una de las cuentas, se llamará al metodo procesoCuenta 50000 veces, mostrando
por pantalla el resultado final de la cuenta bancaria en cada caso.
Se usará un pool de hilos tipo fixedThreadPool de 4 hilos.
*/

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) throws Exception {
        int iteraciones = 50000;
        ExecutorService eS = Executors.newFixedThreadPool(4);
        CuentaBancaria cuenta1 = new CuentaBancaria();
        CuentaBancariaSincronizada cuenta2 = new CuentaBancariaSincronizada();

        for (int i = 0; i < iteraciones; i++) {
            eS.execute(cuenta1::procesoCuenta);
            eS.execute(cuenta2::procesoCuenta);
        }
        eS.shutdown();
        try {
            eS.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Cuenta no sincronizada");
        System.out.println(cuenta1.getCuenta());
        System.out.println("Cuenta sincronizada");
        System.out.println(cuenta2.getCuenta());
    }
}
