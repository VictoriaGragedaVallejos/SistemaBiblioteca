
// Importación de librerías necesarias
import java.util.*;

// === Patrón Observer: Interfaz de Observador ===
// Define un contrato para que cualquier clase que implemente esta interfaz
// pueda recibir notificaciones automáticas del sistema.
interface Observador {
    void actualizar(String mensaje);
}

// === Patrón Observer: Clase Observable ===
// Clase que mantiene una lista de observadores y les envía notificaciones.
class Notificador {
    private List<Observador> observadores = new ArrayList<>();

    public void agregarObservador(Observador o) {
        observadores.add(o);
    }

    public void notificarTodos(String mensaje) {
        for (Observador o : observadores) {
            o.actualizar(mensaje);
        }
    }
}

// === Implementación del Observer: Usuario ===
// Los usuarios implementan la interfaz Observador y reciben notificaciones.
class Usuario implements Observador {
    private String nombre;

    public Usuario(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void actualizar(String mensaje) {
        System.out.println(nombre + " recibió notificación: " + mensaje);
    }
}

// === Clase base abstracta para libros ===
// Aplica SRP y base para el patrón Factory Method
abstract class Libro {
    protected String titulo;
    protected String autor;

    public Libro(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
    }

    public abstract void mostrarInfo();
}

// === LibroFiccion extiende Libro ===
// Representa un libro de ficción
class LibroFiccion extends Libro {
    public LibroFiccion(String titulo, String autor) {
        super(titulo, autor);
    }

    @Override
    public void mostrarInfo() {
        System.out.println(" Libro de Ficción: " + titulo + " por " + autor);
    }
}

// === LibroTecnico extiende Libro ===
// Representa un libro técnico
class LibroTecnico extends Libro {
    public LibroTecnico(String titulo, String autor) {
        super(titulo, autor);
    }

    @Override
    public void mostrarInfo() {
        System.out.println(" Libro Técnico: " + titulo + " por " + autor);
    }
}

// === Patrón Factory Method ===
// Permite crear instancias sin depender de las clases concretas
class LibroFactory {
    public static Libro crearLibro(String tipo, String titulo, String autor) {
        if (tipo.equalsIgnoreCase("ficcion")) {
            return new LibroFiccion(titulo, autor);
        } else if (tipo.equalsIgnoreCase("tecnico")) {
            return new LibroTecnico(titulo, autor);
        } else {
            throw new IllegalArgumentException("Tipo de libro no válido");
        }
    }
}

// === Patrón Decorator ===
// Clase abstracta que extiende Libro y permite decorar sus funciones
abstract class LibroDecorator extends Libro {
    protected Libro libroDecorado;

    public LibroDecorator(Libro libro) {
        super(libro.titulo, libro.autor);
        this.libroDecorado = libro;
    }

    public abstract void mostrarInfo();
}

// === Decorador concreto: LibroReservado ===
// Añade funcionalidad de reserva a cualquier tipo de libro
class LibroReservado extends LibroDecorator {
    public LibroReservado(Libro libro) {
        super(libro);
    }

    @Override
    public void mostrarInfo() {
        libroDecorado.mostrarInfo();
        System.out.println(" Este libro está reservado.");
    }
}

// === Clase Principal que ejecuta el sistema ===
public class SistemaBiblioteca {
    public static void main(String[] args) {
        // Crear usuarios (observadores)
        Usuario u1 = new Usuario("Ana");
        Usuario u2 = new Usuario("Luis");

        // Crear notificador y agregar observadores
        Notificador notificador = new Notificador();
        notificador.agregarObservador(u1);
        notificador.agregarObservador(u2);

        // Crear libros usando Factory Method
        Libro libro1 = LibroFactory.crearLibro("ficcion", "Harry Potter", "J.K. Rowling");
        Libro libro2 = LibroFactory.crearLibro("tecnico", "Patrones de Diseño", "Gamma");

        // Mostrar información de libros
        libro1.mostrarInfo();
        libro2.mostrarInfo();

        // Decorar un libro como reservado
        Libro libroReservado = new LibroReservado(libro1);
        libroReservado.mostrarInfo();

        // Enviar notificación de préstamo
        notificador.notificarTodos("El libro '" + libro1.titulo + "' fue prestado.");
    }
}
