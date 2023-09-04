package cr.ac.una.proyecto1_datos.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;

/**
 *
 * @author Luvara
 */
public class Cubito {

    private PositionCube position;
    private Group cubito;
    private int id;
    private int sides;
    private List<String> colorsList;

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

    public Cubito(int id, PositionCube position) {
        this.id = id;
        this.position = position;
        this.colorsList = new ArrayList<>();
        initializeDefaults();
    }

    private void initializeDefaults() {
        switch (position) {
            case CORNER -> {
                if (id == 0) {
                    colorsList.addAll(Arrays.asList("Orange", "Green", "White")); //0,3,4
                }
                if (id == 2) {
                    colorsList.addAll(Arrays.asList("Blue", "Orange", "White")); //2,3,4
                }
                if (id == 6) {
                    colorsList.addAll(Arrays.asList("Green", "Red", "White")); //0,1,4
                }
                if (id == 8) {
                    colorsList.addAll(Arrays.asList("Red", "Blue", "White"));//1,2,4
                }
                if (id == 18) {
                    colorsList.addAll(Arrays.asList("Orange", "Green", "Yellow"));//0,3,5
                }
                if (id == 20) {
                    colorsList.addAll(Arrays.asList("Blue", "Orange", "Yellow"));//2,3,5
                }
                if (id == 24) {
                    colorsList.addAll(Arrays.asList("Green", "Red", "Yellow"));//0,1,5
                }
                if (id == 26) {
                    colorsList.addAll(Arrays.asList("Red", "Blue", "Yellow"));//1,2,5
                }
                sides = 3;
            }
            case BORDER -> {
                if (id == 1) {
                    colorsList.addAll(Arrays.asList("Orange", "White"));//3,4
                }
                if (id == 3) {
                    colorsList.addAll(Arrays.asList("Green", "White"));//0,4
                }
                if (id == 5) {
                    colorsList.addAll(Arrays.asList("Blue", "White"));//2,4
                }
                if (id == 7) {
                    colorsList.addAll(Arrays.asList("Red", "White"));//1,4
                }
                if (id == 9) {
                    colorsList.addAll(Arrays.asList("Orange", "Green"));//0,3
                }
                if (id == 11) {
                    colorsList.addAll(Arrays.asList("Orange", "Blue"));//2,3
                }
                if (id == 15) {
                    colorsList.addAll(Arrays.asList("Red", "Green"));//0,1
                }
                if (id == 17) {
                    colorsList.addAll(Arrays.asList("Red", "Blue"));//1,2
                }
                if (id == 19) {
                    colorsList.addAll(Arrays.asList("Orange", "Yellow"));//3,5
                }
                if (id == 21) {
                    colorsList.addAll(Arrays.asList("Green", "Yellow"));//0,5
                }
                if (id == 23) {
                    colorsList.addAll(Arrays.asList("Blue", "Yellow"));//2,5
                }
                if (id == 25) {
                    colorsList.addAll(Arrays.asList("Red", "Yellow"));//1,5
                }
                sides = 2;
            }
            case CENTER -> {
                if (id == 4) {
                    colorsList.add("White");//4
                }
                if (id == 10) {
                    colorsList.add("Orange");//3
                }
                if (id == 12) {
                    colorsList.add("Green");//0
                }
                if (id == 14) {
                    colorsList.add("Blue");//2
                }
                if (id == 16) {
                    colorsList.add("Red");//1
                }
                if (id == 22) {
                    colorsList.add("Yellow");//5
                }
                sides = 1;
            }
        }
    }

    public PositionCube getPosition() {
        return position;
    }

    public int getId() {
        return id;
    }

    public int getSides() {
        return sides;
    }

    public enum PositionCube {
        CORNER,
        BORDER,
        CENTER
    }

    public List<String> getColorsList() {
        return colorsList;
    }

    @Override
    public String toString() {
        return "Cubito{" + "rotacionX=" + ", rotacionY=" + '}';
    }

    private MeshView createCubeMeshView(int[] face, String faceColor) {

        // Asignar color a la cara específica
        PhongMaterial material = new PhongMaterial();
        Image textureImage = new Image("cr/ac/una/proyecto1_datos/resources/media/colors/" + faceColor + ".png");
        material.setDiffuseMap(textureImage);

        MeshView meshView = new MeshView();
        meshView.setDrawMode(DrawMode.FILL); // Rellenar caras
        meshView.setMaterial(material);

        return meshView;
    }

}
