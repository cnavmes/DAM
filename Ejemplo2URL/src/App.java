import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class App {
    public static void main(String[] args) throws Exception {
        try {
            URI uri = new URI("http://www.elaltozano.es");
            URL url = uri.toURL();
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("GET");

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conexion.getInputStream()))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    System.out.println(linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al conectar " + e.getMessage());
        }
    }
}
