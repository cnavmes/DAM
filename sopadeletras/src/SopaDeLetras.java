import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class SopaDeLetras {
  private char[][] tablero;
  private Tematica tematica;
  private int filas;
  private int columnas;
  Scanner sc = new Scanner(System.in);

  public SopaDeLetras(int filas, int columnas, Tematica tematica) {
    this.filas = filas;
    this.columnas = columnas;
    this.tematica = tematica;
    tablero = new char[filas][columnas];
    iniciarTablero();
  }

  private void iniciarTablero() {
    for (int i = 0; i < filas; i++) {
      for (int j = 0; j < columnas; j++) {
        tablero[i][j] = ' '; // Inicia tablero con espacios en blanco
      }
    }
    insertarPalabras();
  }

  private void insertarPalabras() {
    Random random = new Random();
    String[] direcciones = { "HORIZONTAL", "VERTICAL", "DIAGONAL" };
    boolean colocada = false;
    int fila, columna;
    String direccion;
    boolean invertida;

    for (String palabra : tematica.getPalabras()) {
      while (!colocada) {
        fila = random.nextInt(filas);
        columna = random.nextInt(columnas);
        direccion = direcciones[random.nextInt(direcciones.length)];
        invertida = random.nextBoolean();

        if (invertida) {
          palabra = new StringBuilder(palabra).reverse().toString();
        }
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
      if (columna + longitud > columnas) {
        resultado = false;
      }
      for (int i = 0; i < longitud; i++) {
        if (tablero[fila][columna + i] != ' ' && tablero[fila][columna + i] != palabra.charAt(i)) {
          resultado = false;
        }
      }
    } else if (direccion.equals("VERTICAL")) {
      if (fila + longitud > filas) {
        resultado = false;
        ;
      }
      for (int i = 0; i < longitud; i++) {
        if (tablero[fila + i][columna] != ' ' && tablero[fila + i][columna] != palabra.charAt(i)) {
          resultado = false;
        }
      }
    } else if (direccion.equals("DIAGONAL")) {
      if (fila + longitud > filas || columna + longitud > columnas) {
        resultado = false;
        ;
      }
      for (int i = 0; i < longitud; i++) {
        if (tablero[fila + i][columna + i] != ' ' && tablero[fila + i][columna + i] != palabra.charAt(i)) {
          resultado = false;
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

  protected void jugar() {
    String palabra;
    boolean continuar = true;
    System.out.println("Palabras a encontrar: " + Arrays.toString(tematica.getPalabras()));

    while (continuar) {
      System.out.println("Introduce una palabra (o 'me rindo para salir)");
      palabra = sc.nextLine().trim().toUpperCase();

      if (palabra.equals("ME RINDO")) {
        System.out.println("Te has rendido");
        continuar = false;
      }
      if (Arrays.asList(tematica.getPalabras()).contains(palabra)) {
        System.out.println("Encontraste la palabra: " + palabra);
        continuar = false;
      } else {
        System.out.println("Palabra no encontrada");
      }
    }
  }
}
