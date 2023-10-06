package cr.ac.una.proyecto1_datos.util;

import cr.ac.una.proyecto1_datos.model.Jugador;
import cr.ac.una.proyecto1_datos.model.Movimientos;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author kevin
 */
public class ManejoDatos {

    private static final String TXT_PATH = "src/main/java/cr/ac/una/proyecto1_datos/model/datos.txt";
    private static final String TXT_PATH_RECORDS = "src/main/java/cr/ac/una/proyecto1_datos/model/records.txt";

    // Método para buscar un Jugador por su nombre.
    public static Jugador buscarJugadorPorNombre(String nombre) {
        List<Jugador> jugadores = leerJugadores();

        for (Jugador jugador : jugadores) {
            if (jugador.getName().equals(nombre)) {
                return jugador;
            }
        }
        return null; // Si el jugador no se encuentra
    }

    public static List<Jugador> leerRecords() {
        List<Jugador> jugadores = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(TXT_PATH_RECORDS))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] datos = line.split(",");
                String name = datos[0];
                int moves = Integer.parseInt(datos[1]);
                int time = Integer.parseInt(datos[2]);
                int points = Integer.parseInt(datos[3]);

                Jugador jugador = new Jugador(name, null, points, moves, time, null);
                jugadores.add(jugador);
            }
        } catch (FileNotFoundException e) {
            System.err.println("El archivo no se encontró: " + e.getMessage());
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return jugadores;
    }

    public static void guardarJugador(Jugador jugador) {
        List<Jugador> jugadores = leerJugadores();
        // Verificar si el jugador ya existe
        boolean jugadorExiste = false;
        for (Jugador j : jugadores) {
            if (j.getName().equals(jugador.getName())) {
                j.setModoJuego(jugador.getModoJuego());
                j.setPoints(jugador.getPoints());
                j.setMovimientos(jugador.getMovimientos());
                j.setTime(jugador.getTime());
                jugadorExiste = true;
                break;
            }
        }

        if (!jugadorExiste) {
            jugadores.add(jugador);
        }

        escribirJugadores(jugadores);
    }

    // Este método lee información de jugadores desde un archivo de texto y la procesa para crear una lista de objetos Jugador.
    private static List<Jugador> leerJugadores() {
        // Se crea una lista para almacenar los objetos Jugador.
        List<Jugador> jugadores = new ArrayList<>();

        // Se intenta abrir y leer el archivo.
        try (BufferedReader br = new BufferedReader(new FileReader(TXT_PATH))) {
            String line;

            // Se lee cada línea del archivo.
            while ((line = br.readLine()) != null) {
                // Se separa la línea en datos individuales utilizando coma como separador.
                String[] datos = line.split(",");
                String name = datos[0]; // Se obtiene el nombre del jugador.
                String modoJuego = datos[1]; // Se obtiene el modo de juego.
                int points = Integer.parseInt(datos[2]); // Se convierte la puntuación a entero.
                int moves = Integer.parseInt(datos[3]); // Se convierte el número de movimientos a entero.
                int time = Integer.parseInt(datos[4]); // Se obtiene el tiempo de juego.

                // Se crea una pila para almacenar los movimientos del jugador.
                Stack<Movimientos> movimientos = new Stack<>();
                String[] movimientosData = datos[5].split(";"); // Se supone que los movimientos están separados por ';'.

                // Se procesa cada movimiento.
                for (String movimiento : movimientosData) {
                    String[] movimientoData = movimiento.split("#"); // Se separa el número del movimiento y la dirección.
                    int numero = Integer.parseInt(movimientoData[0]); // Se convierte el número del movimiento a entero.
                    String direccion = movimientoData[1]; // Se obtiene la dirección.

                    // Se crea un objeto Movimientos y se agrega a la pila de movimientos.
                    Movimientos mov = new Movimientos(numero, direccion);
                    movimientos.push(mov);
                }

                // Se crea un objeto Jugador con los datos procesados y se agrega a la lista de jugadores.
                Jugador jugador = new Jugador(name, modoJuego, points, moves, time, movimientos);
                jugadores.add(jugador);
            }
        } catch (FileNotFoundException e) {
            System.err.println("El archivo no se encontró: " + e.getMessage()); // Se maneja la excepción de archivo no encontrado.
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage()); // Se maneja la excepción de error al leer el archivo.
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir datos: " + e.getMessage()); // Se maneja la excepción de error al convertir datos.
        }

        // Se retorna la lista de jugadores.
        return jugadores;
    }

    // Este método escribe la información de los jugadores en un archivo de texto.
    private static void escribirJugadores(List<Jugador> jugadores) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TXT_PATH))) {
            // Se itera sobre la lista de jugadores.
            for (Jugador jugador : jugadores) {
                // Se escribe en el archivo el nombre, modo de juego, puntuación, movimientos y tiempo del jugador.
                bw.write(jugador.getName() + "," + jugador.getModoJuego() + "," + jugador.getPoints() + "," + jugador.getMoves() + "," + jugador.getTime() + ",");

                // Se obtiene la pila de movimientos del jugador.
                Stack<Movimientos> movimientos = jugador.getMovimientos();

                // Se itera sobre los movimientos y se escribe cada uno en el formato adecuado.
                for (Movimientos movimiento : movimientos) {
                    bw.write(movimiento.getNumero() + "#" + movimiento.getDireccion() + ";");
                }

                bw.newLine(); // Se agrega una nueva línea al final de cada jugador para separarlos en el archivo.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void guardarRecord(Jugador jugador) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TXT_PATH_RECORDS, true))) {
            // Se escribe en el archivo el nombre del jugador, cantidad de movimientos, tiempo y puntuación.
            bw.write(jugador.getName() + "," + jugador.getMoves() + "," + jugador.getTime() + "," + jugador.getPoints());
            bw.newLine(); // Se agrega una nueva línea para separar los registros.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
