
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class SopaDeLetras {
  private char[][] tablero;
  private Tematica tematica;
  private int filas;
  private int columnas;
  private List<List<int[]>> palabrasEncontradas = new ArrayList<>();
  Scanner sc = new Scanner(System.in);

  public SopaDeLetras(int filas, int columnas, Tematica tematica) {
    this.filas = filas;
    this.columnas = columnas;
    this.tematica = tematica;
    tablero = new char[filas][columnas];
    iniciarTablero();
  }

  private void iniciarTablero() { // Inicia el tablero con espacios en blanco
    for (int i = 0; i < filas; i++) {
      for (int j = 0; j < columnas; j++) {
        tablero[i][j] = ' ';
      }
    }
    insertarPalabras();
  }

  private void insertarPalabras() {

    int fila, columna;
    String direccion;
    boolean invertida;
    Random random = new Random();
    String[] direcciones = { "HORIZONTAL", "VERTICAL", "DIAGONAL", "DIAGONAL_INVERSA" };

    for (String palabra : tematica.getPalabras()) {
      boolean colocada = false;

      while (!colocada) { // Colocación aleatoria de las palabras
        fila = random.nextInt(filas);
        columna = random.nextInt(columnas);
        direccion = direcciones[random.nextInt(direcciones.length)];
        invertida = random.nextBoolean();

        // Si sale invertida uso stringbuilder para invertir la palabra
        if (invertida) {
          palabra = new StringBuilder(palabra).reverse().toString();
        }

        // Intentar colocar la palabra
        if (sePuedeColocarPalabra(fila, columna, palabra, direccion)) {
          colocarPalabra(fila, columna, palabra, direccion);
          colocada = true;
        }
      }
    }
  }

  private boolean sePuedeColocarPalabra(int fila, int columna, String palabra, String direccion) {
    int longitud = palabra.length();
    boolean resultado = true;

    if (direccion.equals("HORIZONTAL")) {
      // Validar si la palabra cabe horizontalmente
      if (columna + longitud > columnas) {
        resultado = false;
      } else {
        for (int i = 0; i < longitud && resultado; i++) {
          if (tablero[fila][columna + i] != ' ' && tablero[fila][columna + i] != palabra.charAt(i)) {
            resultado = false;
          }
        }
      }
    } else if (direccion.equals("VERTICAL")) {
      // Validar si la palabra cabe verticalmente
      if (fila + longitud > filas) {
        resultado = false;
      } else {
        for (int i = 0; i < longitud && resultado; i++) {
          if (tablero[fila + i][columna] != ' ' && tablero[fila + i][columna] != palabra.charAt(i)) {
            resultado = false;
          }
        }
      }
    } else if (direccion.equals("DIAGONAL")) {
      // Validar si la palabra cabe diagonalmente
      if (fila + longitud > filas || columna + longitud > columnas) {
        resultado = false;
      } else {
        for (int i = 0; i < longitud && resultado; i++) {
          if (tablero[fila + i][columna + i] != ' ' && tablero[fila + i][columna + i] != palabra.charAt(i)) {
            resultado = false;
          }
        }
      }
    } else if (direccion.equals("DIAGONAL_INVERSA")) {
      // Validar si la palabra cabe en la diagonal inversa
      if (fila + longitud > filas || columna - longitud < -1) {
        resultado = false;
      } else {
        for (int i = 0; i < longitud && resultado; i++) {
          if (tablero[fila + i][columna - i] != ' ' && tablero[fila + i][columna - i] != palabra.charAt(i)) {
            resultado = false;
          }
        }
      }
    }

    return resultado;
  }

  private void colocarPalabra(int fila, int columna, String palabra, String direccion) {

    int longitud = palabra.length();

    if (direccion.equals("HORIZONTAL")) {
      for (int i = 0; i < longitud; i++) {
        tablero[fila][columna + i] = palabra.charAt(i);
      }
    } else if (direccion.equals("VERTICAL")) {
      for (int i = 0; i < longitud; i++) {
        tablero[fila + i][columna] = palabra.charAt(i);
      }
    } else if (direccion.equals("DIAGONAL")) {
      for (int i = 0; i < longitud; i++) {
        tablero[fila + i][columna + i] = palabra.charAt(i);
      }
    }
  }

  protected void mostrarTablero() {
    Random random = new Random();

    // Rellena los espacios vacíos con letras aleatorias
    for (int i = 0; i < filas; i++) {
      for (int j = 0; j < columnas; j++) {
        if (tablero[i][j] == ' ') {
          tablero[i][j] = (char) (random.nextInt(26) + 'A');
        }
      }
    }

    // Encabezado con números de las columnas
    System.out.print("   ");
    for (int j = 0; j < columnas; j++) {
      System.out.print((j + 1) + " ");
    }
    System.out.println();

    // Separador
    System.out.print("   ");
    for (int j = 0; j < columnas; j++) {
      System.out.print("--");
    }
    System.out.println();

    for (int i = 0; i < filas; i++) {

      System.out.print((char) ('A' + i) + " | ");
      for (int j = 0; j < columnas; j++) {
        char letra = tablero[i][j];

        // Comprobar si esta coordenada está en las palabras encontradas

        boolean esParteDePalabra = false;
        for (List<int[]> coordenadasPalabra : palabrasEncontradas) {
          for (int[] coordenada : coordenadasPalabra) {
            if (coordenada[0] == i && coordenada[1] == j) {
              esParteDePalabra = true;
              break;
            }
          }
          if (esParteDePalabra)
            break;
        }

        // Si es parte de una palabra encontrada, la mostramos en verde
        if (esParteDePalabra) {
          System.out.print("\033[32m" + letra + "\033[0m ");
        } else {
          System.out.print(letra + " "); // Letra normal
        }
      }
      System.out.println();
    }
  }

  protected void jugar() {
    List<String> palabrasRestantes = new ArrayList<>(Arrays.asList(tematica.getPalabras()));
    boolean continuar = true;

    System.out.println("=== SOPA DE LETRAS ===");
    System.out.println("Palabras a encontrar: " + palabrasRestantes);
    mostrarTablero();

    while (continuar) {
      System.out.println("\nIntroduce la palabra encontrada (o escribe 'ME RINDO' para salir):");
      String palabra = sc.nextLine().trim().toUpperCase();

      if (palabra.equals("ME RINDO")) {
        System.out.println("Te has rendido. Las palabras que quedaban por encontrar eran: " + palabrasRestantes);
        continuar = false;
        break;
      }

      if (!palabrasRestantes.contains(palabra)) {
        System.out.println("La palabra no está en la lista de palabras restantes. Inténtalo de nuevo.");
        continue;
      }

      System.out.println("Introduce las coordenadas de inicio y fin de la palabra (formato: A1 C1):");
      String[] coordenadas = sc.nextLine().trim().toUpperCase().split(" ");

      if (coordenadas.length != 2 || !validarFormatoCoordenadas(coordenadas)) {
        System.out.println("Coordenadas inválidas. Usa el formato correcto (por ejemplo: A1 C1).");
        continue;
      }
      // Convierto coordenadas a posiciones en la matriz
      int[] inicio = convertirCoordenada(coordenadas[0]);
      int[] fin = convertirCoordenada(coordenadas[1]);

      if (validarCoordenadas(palabra, inicio, fin)) {
        System.out.println("¡Correcto! Encontraste la palabra: " + palabra);
        palabrasRestantes.remove(palabra);
        mostrarTablero();
      } else {
        System.out.println("Las coordenadas no coinciden con la ubicación de la palabra. Inténtalo de nuevo.");
      }

      if (palabrasRestantes.isEmpty()) {
        System.out.println("¡Felicidades! Encontraste todas las palabras.");
        continuar = false;
      } else {
        System.out.println("Palabras restantes: " + palabrasRestantes);
      }
    }
  }

  private boolean validarFormatoCoordenadas(String[] coordenadas) {
    boolean resultado = true;

    for (String coord : coordenadas) {
      if (coord.length() < 2 || coord.charAt(0) < 'A' || coord.charAt(0) >= 'A' + filas ||
          !Character.isDigit(coord.charAt(1)) || Integer.parseInt(coord.substring(1)) < 1 ||
          Integer.parseInt(coord.substring(1)) > columnas) {
        resultado = false;
      }
    }
    return resultado;
  }

  private int[] convertirCoordenada(String coordenada) {
    int fila = coordenada.charAt(0) - 'A';
    int columna = Integer.parseInt(coordenada.substring(1)) - 1;
    return new int[] { fila, columna };
  }

  private boolean validarCoordenadas(String palabra, int[] inicio, int[] fin) {
    boolean resultado = true;
    int longitud = palabra.length();
    int filaInicio = inicio[0];
    int colInicio = inicio[1];
    int filaFin = fin[0];
    int colFin = fin[1];
    int pasoFila;
    int pasoColumna;
    // Calcular las diferencias de fila y columna
    int deltaFila = filaFin - filaInicio;
    int deltaColumna = colFin - colInicio;

    // Verificar que las diferencias sean válidas según la longitud de la palabra
    if (Math.abs(deltaFila) != 0 && Math.abs(deltaFila) != longitud - 1 &&
        Math.abs(deltaColumna) != 0 && Math.abs(deltaColumna) != longitud - 1) {
      resultado = false;
    }

    pasoFila = Integer.compare(deltaFila, 0);// Para saber si la palabra va hacia arriba o abajo
    pasoColumna = Integer.compare(deltaColumna, 0);// Para saber si la palabra va hacia la izquierda o derecha

    // Verificar que la palabra encaje en esa dirección
    for (int i = 0; i < longitud; i++) {
      int filaActual = filaInicio + i * pasoFila;
      int colActual = colInicio + i * pasoColumna;

      if (filaActual < 0 || filaActual >= filas || colActual < 0 || colActual >= columnas) {
        resultado = false; // Fuera de límites
      }

      if (tablero[filaActual][colActual] != palabra.charAt(i)) {
        resultado = false; // No coincide la letra
      }
    }

    // Si la palabra es correcta, guardamos las coordenadas
    List<int[]> coordenadasPalabra = new ArrayList<>();
    for (int i = 0; i < longitud; i++) {
      int filaActual = filaInicio + i * pasoFila;
      int colActual = colInicio + i * pasoColumna;
      coordenadasPalabra.add(new int[] { filaActual, colActual });
    }
    palabrasEncontradas.add(coordenadasPalabra);

    return resultado;
  }
}