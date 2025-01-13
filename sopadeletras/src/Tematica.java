public class Tematica {
  private String nombre;
  private String[] palabras;

  public Tematica(String nombre, String[] palabras) {
    this.nombre = nombre;
    this.palabras = palabras;
  }

  public String getNombre() {
    return nombre;
  }

  public String[] getPalabras() {
    return palabras;
  }
}
