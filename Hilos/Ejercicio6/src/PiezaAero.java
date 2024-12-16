import java.util.concurrent.ThreadLocalRandom;

public class PiezaAero {
  private final int id;
  private final String descripcion;

  public PiezaAero() {
    this.id = ThreadLocalRandom.current().nextInt(1, 9999);
    this.descripcion = "Pieza aeronautica";
  }

  public int getId() {
    return id;
  }

  public String getDescripcion() {
    return descripcion;
  }

  @Override
  public String toString() {
    return "id: " + id + " ,descripcion: " + descripcion;
  }
}
