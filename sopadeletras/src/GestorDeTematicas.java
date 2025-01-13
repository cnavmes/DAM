import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestorDeTematicas {
  private List<Tematica> tematicas;
  private static final String ruta = "src/tematicas";

  public GestorDeTematicas() {
    this.tematicas = new ArrayList<>();
    cargarTematicas();
  }

  private void cargarTematicas() {
    File carpetaArchivos = new File(ruta);
    File[] archivos;
    String nombreTematica;
    List<String> palabras;

    if (!carpetaArchivos.exists()) {
      carpetaArchivos.mkdir();
    }
    archivos = carpetaArchivos.listFiles((dir, name) -> name.endsWith(".txt"));

    if (archivos != null) {
      for (File archivo : archivos) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
          nombreTematica = archivo.getName().replace(".txt", "");
          palabras = new ArrayList<>();
        } catch (IOException e) {
          System.out.println("Error al leer el archivo: " + archivo.getName());
        }
      }
    }

  }
}
