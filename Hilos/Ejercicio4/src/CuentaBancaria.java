public class CuentaBancaria {
  private int cuenta = 0;

  public void procesoCuenta() {
    cuenta += 4;
    cuenta -= 5;
    cuenta += 6;
  }

  public int getCuenta() {
    return cuenta;
  }
}
