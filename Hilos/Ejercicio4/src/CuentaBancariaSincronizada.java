public class CuentaBancariaSincronizada {
  private int cuenta = 0;

  public synchronized void procesoCuenta() {
    cuenta += 4;
    cuenta -= 5;
    cuenta += 6;
  }

  public synchronized int getCuenta() {
    return cuenta;
  }
}
