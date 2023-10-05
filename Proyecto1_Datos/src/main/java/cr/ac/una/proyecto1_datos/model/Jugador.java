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
    private int moves;
    private Stack<Movimientos> movimientos;
    private String time;//Se le tiene que mandar en segundo nada mas para evitar errores

    public Jugador(String name, String modoJuego, int points,int moves , String time,Stack<Movimientos> movimientos) {
        this.name = name;
        this.modoJuego = modoJuego;
        this.points = points;
        this.moves = moves;
        this.movimientos = movimientos;
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

    public Stack<Movimientos> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(Stack<Movimientos> movimientos) {
        this.movimientos = movimientos;
    }

    

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }
    
    @Override
    public String toString() {
        return "Jugador{" + "name=" + name + ", modoJuego=" + modoJuego + ", points=" + points + ", moves=" + moves + ", time=" + time + '}';
    }

}
