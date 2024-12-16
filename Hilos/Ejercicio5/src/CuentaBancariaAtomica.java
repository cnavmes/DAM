import java.util.concurrent.atomic.LongAdder;

public class CuentaBancariaAtomica {
  private final LongAdder cuenta = new LongAdder();

  public void procesoCuenta() {
    cuenta.add(4);
    cuenta.add(-5);
    cuenta.add(6);
  }

  public long getCuenta() {
    return cuenta.sum();
  }
}
