package cr.ac.una.proyecto1_datos.model;

import java.util.Stack;

/**
 *
 * @author Luvara
 */
public class Jugador {

    private String name;
    private String modoJuego;
    private int points;
    private Stack<String> moves;
    private String time;

    public Jugador(String name, String modoJuego, int points, Stack<String> moves, String time) {
        this.name = name;
        this.modoJuego = modoJuego;
        this.points = points;
        this.moves = moves;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModoJuego() {
        return modoJuego;
    }

    public void setModoJuego(String modoJuego) {
        this.modoJuego = modoJuego;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Stack<String> getRecordStack() {
        return moves;
    }

    public void setRecordStack(Stack<String> moves) {
        this.moves = moves;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Jugador{" + "name=" + name + ", modoJuego=" + modoJuego + ", points=" + points + ", moves=" + moves + ", time=" + time + '}';
    }

}
