import java.net.*;

public class ej1 {
  public static void main(String[] args) {
    StringBuilder sb = new StringBuilder();
    String separador = "==========================================" + "\n";
    try {
      sb.append(separador);
      InetAddress dir = InetAddress.getLocalHost();
      sb.append("Direccion de la maquina local: " + dir + "\n");

      sb.append(pruebaMetodos(dir) + "\n");
      sb.append(separador);
      dir = InetAddress.getByName("www.google.es");
      sb.append("Dirección de www.google.es:" + "\n");
      sb.append(pruebaMetodos(dir) + "\n");

      InetAddress[] direcciones = InetAddress.getAllByName("www.google.es");
      sb.append("Direcciones IP de www.google.es:" + "\n");
      for (InetAddress direccion : direcciones) {
        sb.append(direccion.getHostAddress());
      }
      sb.append("\n");
      sb.append(separador);
      System.out.println(sb.toString());

    } catch (UnknownHostException e) {
      System.err.println("Error al obtener la dirección: " + e.getMessage());
    }
  }

  private static String pruebaMetodos(InetAddress dir) {
    StringBuilder sb = new StringBuilder();
    try {
      sb.append("getByName(): " + InetAddress.getByName(dir.getHostName()) + "\n");
      sb.append("getLocalHost(): " + InetAddress.getLocalHost() + "\n");
      sb.append("getHostName(): " + dir.getHostName() + "\n");
      sb.append("getHostAddress(): " + dir.getHostAddress() + "\n");
      sb.append("toString(): " + dir.toString() + "\n");
      sb.append("getCanonicalHostName(): " + dir.getCanonicalHostName() + "\n");
    } catch (UnknownHostException e) {
      System.err.println("Error en pruebaMetodos: " + e.getMessage());
    }
    return sb.toString()
  }

}
