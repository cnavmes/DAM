import java.util.concurrent.Callable;

public class MultiplicacionNumerosCallable implements Callable<Long> {
  private final long num1;
  private final long num2;
  private final int numHilo;

  public MultiplicacionNumerosCallable(long num1, long num2, int numHilo) {
    this.num1 = num1;
    this.num2 = num2;
    this.numHilo = numHilo;
  }

  @Override
  public Long call() throws Exception {
    System.out.println("Hilo " + numHilo + " : " + num1 + " x " + num2 + " = " + num1 * num2);
    return num1 * num2;
  }
}
