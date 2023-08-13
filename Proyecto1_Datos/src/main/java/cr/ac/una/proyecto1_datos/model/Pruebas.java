
package cr.ac.una.proyecto1_datos.model;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;



/**
 *
 * @author Luvara
 */
public class Pruebas {
    private MeshView createCubeMeshView(float size, int[] face, Color faceColor) {
        TriangleMesh cubeMesh = new TriangleMesh();

        // Definir los vértices del cubo
        float halfSize = size / 2.0f;
        float[] points = {
            -halfSize, -halfSize, -halfSize,
            halfSize, -halfSize, -halfSize,
            -halfSize, halfSize, -halfSize,
            halfSize, halfSize, -halfSize,
            -halfSize, -halfSize, halfSize,
            halfSize, -halfSize, halfSize,
            -halfSize, halfSize, halfSize,
            halfSize, halfSize, halfSize
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
//        int[] faces = {
//            // Cara frontal
//            0, 0, 2, 2, 1, 1,
//            2, 2, 3, 3, 1, 1,
//            // Cara lateral derecha
//            1, 0, 3, 3, 5, 2,
//            3, 3, 7, 1, 5, 2,
//            // Cara trasera
//            5, 2, 7, 1, 4, 3,
//            7, 1, 6, 2, 4, 3,
//            // Cara lateral izquierda
//            4, 3, 6, 2, 0, 0,
//            6, 2, 2, 2, 0, 0,
//            // Cara superior
//            0, 0, 1, 1, 4, 3,
//            1, 1, 5, 2, 4, 3,
//            // Cara inferior
//            7, 1, 3, 3, 6, 2,
//            3, 3, 2, 2, 6, 2
//        };
        cubeMesh.getFaces().addAll(face);

        // Asignar color a la cara específica
        PhongMaterial material = new PhongMaterial(faceColor);

        MeshView meshView = new MeshView(cubeMesh);
        meshView.setDrawMode(DrawMode.FILL); // Rellenar caras
        meshView.setMaterial(material);

//        MeshView meshView = new MeshView(cubeMesh);
//        meshView.setDrawMode(DrawMode.FILL); // Rellenar caras
//        meshView.setMaterial(new PhongMaterial(Color.RED)); // Material del cubo
        return meshView;
    }
}
