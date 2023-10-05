/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyecto1_datos.util;

import cr.ac.una.proyecto1_datos.model.Jugador;
import cr.ac.una.proyecto1_datos.model.Movimientos;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author kevin
 */
public class ManejoDatos {

    private static final String TXT_PATH = "src/main/java/cr/ac/una/proyecto1_datos/model/datos.txt";
    private static final String TXT_PATH_RECORDS = "src/main/java/cr/ac/una/proyecto1_datos/model/records.txt";

    public static Jugador buscarJugadorPorNombre(String nombre) {
        List<Jugador> jugadores = leerJugadores();

        for (Jugador jugador : jugadores) {
            if (jugador.getName().equals(nombre)) {
                return jugador;
            }
        }
        return null; // Si el jugador no se encuentra
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
        // Guardar record
        guardarRecord(jugador);
    }

    private static List<Jugador> leerJugadores() {
        List<Jugador> jugadores = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(TXT_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] datos = line.split(",");
                String name = datos[0];
                String modoJuego = datos[1];
                int points = Integer.parseInt(datos[2]);
                int moves = Integer.parseInt(datos[3]);
                String time = datos[4];
                Stack<Movimientos> movimientos = new Stack<>();
                String[] movimientosData = datos[5].split(";"); // Suponiendo que los movimientos están separados por ';'

                for (String movimiento : movimientosData) {
                    String[] movimientoData = movimiento.split("#");
                    int numero = Integer.parseInt(movimientoData[0]);
                    String direccion = movimientoData[1];

                    Movimientos mov = new Movimientos(numero, direccion);
                    movimientos.push(mov);
                }

                Jugador jugador = new Jugador(name, modoJuego, points, moves, time, movimientos);
                jugadores.add(jugador);
            }
        } catch (FileNotFoundException e) {
            System.err.println("El archivo no se encontró: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir datos: " + e.getMessage());
        }
        return jugadores;
    }

    private static void escribirJugadores(List<Jugador> jugadores) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TXT_PATH))) {
            for (Jugador jugador : jugadores) {
                bw.write(jugador.getName() + "," + jugador.getModoJuego() + "," + jugador.getPoints() + "," + jugador.getMoves() + "," + jugador.getTime() + ",");

                Stack<Movimientos> movimientos = jugador.getMovimientos();
                for (Movimientos movimiento : movimientos) {
                    bw.write(movimiento.getNumero() + "#" + movimiento.getDireccion() + ";");
                }
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void guardarRecord(Jugador jugador) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TXT_PATH_RECORDS, true))) {
            bw.write(jugador.getName() + "," + jugador.getMoves() + "," + jugador.getTime() + "," + jugador.getPoints());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
