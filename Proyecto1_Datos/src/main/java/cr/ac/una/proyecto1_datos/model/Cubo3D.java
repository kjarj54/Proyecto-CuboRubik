package cr.ac.una.proyecto1_datos.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

/**
 *
 * @author Luvara
 */
public class Cubo3D {

    List<List<int[]>> listaListasCaras;
    List<Group> listGroup;
    int contador = 0;

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

    public Cubo3D() {
        listGroup = new ArrayList<>();
        listaListasCaras = new ArrayList<>();
        loadFaces();
        createGroup();
    }

    public List<Group> getListGroup() {
        return listGroup;
    }

    private void loadFaces() {
        listaListasCaras.add(Arrays.asList(leftFace, frontFace, upFace));//0
        listaListasCaras.add(Arrays.asList(leftFace, upFace));//1
        listaListasCaras.add(Arrays.asList(backFace, leftFace, upFace)); //2
        listaListasCaras.add(Arrays.asList(frontFace, upFace)); //3
        listaListasCaras.add(Arrays.asList(upFace)); //4
        listaListasCaras.add(Arrays.asList(backFace, upFace)); //5
        listaListasCaras.add(Arrays.asList(frontFace, rigthFace, upFace)); //6
        listaListasCaras.add(Arrays.asList(rigthFace, upFace)); //7
        listaListasCaras.add(Arrays.asList(rigthFace, backFace, upFace)); //8
        listaListasCaras.add(Arrays.asList(leftFace, frontFace)); //9
        listaListasCaras.add(Arrays.asList(leftFace)); //10
        listaListasCaras.add(Arrays.asList(leftFace, backFace)); //11
        listaListasCaras.add(Arrays.asList(frontFace)); //12
        listaListasCaras.add(Arrays.asList(upFace)); //13
        listaListasCaras.add(Arrays.asList(backFace)); //14
        listaListasCaras.add(Arrays.asList(rigthFace, frontFace)); //15
        listaListasCaras.add(Arrays.asList(rigthFace)); //16
        listaListasCaras.add(Arrays.asList(rigthFace, backFace)); //17
        listaListasCaras.add(Arrays.asList(leftFace, frontFace, downFace)); //18
        listaListasCaras.add(Arrays.asList(leftFace, downFace)); //19
        listaListasCaras.add(Arrays.asList(backFace, leftFace, downFace)); //20
        listaListasCaras.add(Arrays.asList(frontFace, downFace)); //21
        listaListasCaras.add(Arrays.asList(downFace)); //22
        listaListasCaras.add(Arrays.asList(backFace, downFace)); //23
        listaListasCaras.add(Arrays.asList(frontFace, rigthFace, downFace)); //24
        listaListasCaras.add(Arrays.asList(rigthFace, downFace)); //25
        listaListasCaras.add(Arrays.asList(rigthFace, backFace, downFace)); //26
    }

    private void createGroup() {
        int size = 0;
        List<int[]> list;
        for (int i = 0; i < listaListasCaras.size(); i++) {
            size = listaListasCaras.get(i).size();
            list = listaListasCaras.get(i);
            listGroup.add(cubo(size, list));
        }
    }

    private Group cubo(int size, List<int[]> list) {

        MeshView[] cuboMesh = new MeshView[size];

        for (int i = 0; i < size; i++) {
            cuboMesh[i] = createCubeMeshView(list.get(i));
            cuboMesh[i].setId(Integer.toString(i));
        }

        Group group = new Group(cuboMesh);
        group.setId(Integer.toString(contador));

        return group;
    }

    private MeshView createCubeMeshView(int[] face/*, String faceColor*/) {
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
//        PhongMaterial material = new PhongMaterial();
//        Image textureImage = new Image("cr/ac/una/proyecto1_datos/resources/media/colors/" + faceColor + ".png");
//        material.setDiffuseMap(textureImage);
        MeshView meshView = new MeshView(cubeMesh);
        meshView.setDrawMode(DrawMode.FILL); // Rellenar caras
        // meshView.setMaterial(material);

        return meshView;
    }
}
