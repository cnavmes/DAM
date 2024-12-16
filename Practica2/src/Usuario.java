public class Usuario {
    private int id;
    private String nombre;
    private int edad;
    private int nota; // Asegúrate de que este atributo exista y sea del tipo correcto

    // Constructor
    public Usuario(int id, String nombre, int edad, int nota) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.nota = nota;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getNota() { // Este getter es esencial
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }
}
