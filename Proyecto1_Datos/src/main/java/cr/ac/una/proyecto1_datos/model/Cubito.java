package cr.ac.una.proyecto1_datos.model;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

/**
 *
 * @author Luvara
 */
public class Cubito {

    private PositionCube position;
    private Group cubito;
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

    public Cubito(PositionCube position) {
        //this.id = id;
        this.position = position;
        this.colors = new ArrayList<>();
        this.cubito = cubo();
        initializeDefaults();
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

    public Group getCubito() {
        return cubito;
    }
    
    public enum PositionCube {
        CORNER,
        BORDER,
        CENTER
    }

    private Group cubo() {
        
        //Arreglo para guardar las caras de ripo MeshView
        MeshView[] cuboMesh = new MeshView[6];
        // Cara frontal
        cuboMesh[0] = createCubeMeshView(frontFace, "Green");
        // Cara lateral derecha
        cuboMesh[1] = createCubeMeshView(rigthFace, "Red");
        // Cara trasera
        cuboMesh[2] = createCubeMeshView(backFace, "Blue");
        // Cara lateral izquierda
        cuboMesh[3] = createCubeMeshView(leftFace, "Orange");
        // Cara superior
        cuboMesh[4] = createCubeMeshView(upFace, "White");
        // Cara inferior
        cuboMesh[5] = createCubeMeshView(downFace, "Yellow");
        
        //Grupo que forma el cubo con todas las caras
        Group group = new Group(cuboMesh);

        return group;
    }

    private MeshView createCubeMeshView(int[] face, String faceColor) {
        TriangleMesh cubeMesh = new TriangleMesh();

        // Definir los vértices del cubo
        float sizeCube = 50; //medida de la mitad del cubo, tamano total de 100
        float[] points = {
            -sizeCube, -sizeCube, -sizeCube,
            sizeCube, -sizeCube, -sizeCube,
            -sizeCube, sizeCube, -sizeCube,
            sizeCube, sizeCube, -sizeCube,
            -sizeCube, -sizeCube, sizeCube,
            sizeCube, -sizeCube, sizeCube,
            -sizeCube, sizeCube, sizeCube,
            sizeCube, sizeCube, sizeCube
        };
        cubeMesh.getPoints().addAll(points);

        // Definir las coordenadas de textura (TexCoords)
        float[] texCoords = {
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f
        };
        cubeMesh.getTexCoords().addAll(texCoords);

        // Definir las caras del cubo
        cubeMesh.getFaces().addAll(face);

        // Asignar color a la cara específica
        PhongMaterial material = new PhongMaterial();

        Image textureImage = new Image("cr/ac/una/proyecto1_datos/resources/media/colors/" + faceColor + ".png");
        material.setDiffuseMap(textureImage);

        MeshView meshView = new MeshView(cubeMesh);
        meshView.setDrawMode(DrawMode.FILL); // Rellenar caras
        meshView.setMaterial(material);

        return meshView;
    }

}
