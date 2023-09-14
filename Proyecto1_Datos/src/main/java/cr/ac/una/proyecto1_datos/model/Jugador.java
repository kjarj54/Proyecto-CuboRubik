package cr.ac.una.proyecto1_datos.model;

import java.time.LocalDate;

/**
 *
 * @author Luvara
 */
public class Jugador {

    String name;
    int points;
    int moves;
    String time;

    public Jugador(String name, int points, String time) {
        this.name = name;
        this.points = points;
        this.time = time;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
    
    
    

}
