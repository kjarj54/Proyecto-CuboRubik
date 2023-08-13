package cr.ac.una.proyecto1_datos.model;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;

/**
 *
 * @author Luvara
 */
public class Cubito {

    private PositionCube position;
    private Group cubito = new Group();
    private int id;
    private int sides;
    private List<String> colors;

    //Crea la triangulacion y caras de cada cubo
    // Cara frontal Verde
    int[] frontFace = {0, 2, 2, 1, 1, 3, /*|*/ 2, 1, 3, 2, 1, 3};
    // Cara lateral derecha Roja
    int[] rigthFace = {1, 2, 3, 1, 5, 3, /*|*/ 3, 1, 7, 2, 5, 3};
    // Cara trasera Azul
    int[] backFace = {5, 2, 7, 1, 4, 3, /*|*/ 7, 1, 6, 2, 4, 3};
    // Cara lateral izquierda Naranja
    int[] leftFace = {4, 2, 6, 1, 0, 3, /*|*/ 6, 1, 2, 2, 0, 3};
    // Cara superior Blanca
    int[] upFace = {0, 2, 1, 1, 4, 3, /*|*/ 1, 1, 5, 2, 4, 3};
    // Cara inferior Amarilla
    int[] downFace = {7, 2, 3, 1, 6, 3, /*|*/ 3, 1, 2, 2, 6, 3};

    public Cubito(int id, PositionCube position, List colors) {
        this.id = id;
        this.position = position;
        this.colors = new ArrayList<>();
        initializeDefaults();
    }

    public PositionCube getPosition() {
        return position;
    }

    public void setPosition(PositionCube position) {
        this.position = position;
    }

    public Group getCubito() {
        return cubito;
    }

    public void setCubito(Group cubito) {
        this.cubito = cubito;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSides() {
        return sides;
    }

    public void setSides(int sides) {
        this.sides = sides;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    private void initializeDefaults() {
        switch (id) {
            case 0 -> {
                sides = 3;
            }
            case 1 -> {
                sides = 2;
            }
            case 2 -> {
                sides = 1;
            }
            default -> {
            }
        }
    }

    public enum PositionCube {
        CORNER,
        BORDER,
        CENTER
    }
    
}


