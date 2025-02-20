import java.net.*;

public class ej2 {
  public static void main(String[] args) {
    try {
      // Constructor simple con URL
      URL url1 = new URL("http://docs.oracle.com");
      Visualizar(url1);

      // Constructor simple con URL con parámetros
      URL url2 = new URL("http://localhost/pfc/gest/cli_gestion.php?S=3");
      Visualizar(url2);

      // Constructor con protocolo, host y directorio
      URL url3 = new URL("http", "docs.oracle.com", "/javase/9");
      Visualizar(url3);

      // Constructor con protocolo, host, puerto y directorio
      URL url4 = new URL("http", "localhost", 8084, "/WebApp/Controlador?accion=modificar");
      Visualizar(url4);

      // Constructor con URL base y especificación
      URL base = new URL("https://docs.oracle.com");
      URL url5 = new URL(base, "/javase/9/docs/api/java/net/URL.html");
      Visualizar(url5);

    } catch (MalformedURLException e) {
      System.err.println("Error al crear la URL: " + e.getMessage());
    }
  }

  private static void Visualizar(URL url) {
    System.out.println("URL completa: " + url.toString());
    System.out.println("Protocolo: " + url.getProtocol());
    System.out.println("Host: " + url.getHost());
    System.out.println("Puerto: " + url.getPort());
    System.out.println("Archivo: " + url.getFile());
    System.out.println("Información de usuario: " + url.getUserInfo());
    System.out.println("Ruta: " + url.getPath());
    System.out.println("Autoridad: " + url.getAuthority());
    System.out.println("Consulta: " + url.getQuery());
    System.out.println("Puerto por defecto: " + url.getDefaultPort());
    System.out.println("-------------------------------------");
  }
}
