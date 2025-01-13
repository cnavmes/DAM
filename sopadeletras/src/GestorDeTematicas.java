import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestorDeTematicas {
  private List<Tematica> tematicas;
  private static final String ruta = "src/tematicas";
  Scanner sc = new Scanner(System.in);

  public GestorDeTematicas() {
    this.tematicas = new ArrayList<>();
    cargarTematicas();
  }

  private void cargarTematicas() {
    File carpetaArchivos = new File(ruta);
    File[] archivos;
    String nombreTematica;
    String linea;
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
          while ((linea = br.readLine()) != null) {
            palabras.add(linea.toUpperCase());
          }
          tematicas.add(new Tematica(nombreTematica, palabras.toArray(new String[0]))); // Esto crea un nuevo array con
                                                                                        // la longitud precisa
        } catch (IOException e) {
          System.out.println("Error al leer el archivo: " + archivo.getName());
        }
      }
    }
  }

  public void iniciarJuego() {
    int opcion;

    if (tematicas.isEmpty()) {
      System.out.println("No hay temáticas cargadas");

    } else {
      System.out.println("Tematicas disponibles:");
      for (int i = 0; i < tematicas.size(); i++) {
        System.out.println((i + 1) + ". " + tematicas.get(i).getNombre());
      }
      System.out.println("Selecciona una tematica");
      opcion = sc.nextInt();
      sc.nextLine();

      if (opcion < 1 || opcion > tematicas.size()) {
        System.out.println("Opcion no valida");
      } else {
        Tematica seleccionada = tematicas.get(opcion - 1);
        SopaDeLetras sopa = new SopaDeLetras(10, 10, seleccionada);
        // TODO
        sopa.mostrarTablero();
        // TODO
        sopa.jugar();
      }
    }
  }

  public void agregarTematica() {
    String nombre;
    List<String> palabras = new ArrayList<>();

    System.out.println("Introduce el nombre de la tematica");
    nombre = sc.nextLine().trim();

    for (int i = 0; i < 5; i++) {
      System.out.println("Introduce la palabra " + (i + 1) + ":");
      palabras.add(sc.nextLine().trim().toUpperCase());
    }
    guardarTematica(nombre, palabras);
    tematicas.add(new Tematica(nombre, palabras.toArray(new String[0])));
    System.out.println("Tematica guardada");
  }

  private void guardarTematica(String nombre, List<String> palabras) {
    File archivo = new File(ruta + nombre + ".txt");

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
      for (String palabra : palabras) {
        bw.write(palabra);
        bw.newLine();
      }
    } catch (IOException e) {
      System.out.println("Error al guardar la tematica");
    }

  }
}
