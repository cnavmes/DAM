class MultiplicacionNumeros extends Thread {
  private long num1;
  private long num2;
  private int numHilo;

  public MultiplicacionNumeros(long num1, long num2, int numHilo) {
    this.num1 = num1;
    this.num2 = num2;
    this.numHilo = numHilo;
  }

  @Override
  public void run() {
    long resultado = num1 * num2;
    System.out.println("Hilo " + numHilo + ": El resultado de " + num1 + " x " + num2 + " es " + resultado);
  }
}