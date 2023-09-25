package cr.ac.una.proyecto1_datos.controller;

import cr.ac.una.proyecto1_datos.model.Cubito;
import cr.ac.una.proyecto1_datos.model.Cubo3D;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Camera;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.PickResult;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

/**
 * FXML Controller class
 *
 * @author Luvara
 */
public class P05_ModoManualViewController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private Label lblNombre;
    @FXML
    private Label lblTiempo;
    @FXML
    private Label lblMovimientos;
    @FXML
    private Label lblPuntos;
    @FXML
    private MFXButton btnIniciarPartida;
    @FXML
    private SubScene subScene;
    @FXML
    private MFXButton btnPruebas;
    @FXML
    private MFXButton btnBlue;
    @FXML
    private MFXButton btnGreen;
    @FXML
    private MFXButton btnOrange;
    @FXML
    private MFXButton btnRed;
    @FXML
    private MFXButton btnWhite;
    @FXML
    private MFXButton btnYellow;

    // Variables para los giros del cubo con el mouse
    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);

    // Vectores con las posiciones de las esquinas y centros
    int[] corners = {0, 2, 6, 8, 18, 20, 24, 26};
    int[] centers = {4, 10, 12, 14, 16, 22};
    int[] sides = {1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25};

    // Variables para controlar los ciclos for de la matriz principal
    int row = 3;
    int column = 3;
    int deep = 3;

    // Matriz de 3 dimensiones para almacenar objetos de tipo cubito
    Cubito[][][] matriz3D = new Cubito[3][3][3];

    // Grupo principal en pantalla para el cubo3D completo
    SmartGroup principalGroup = new SmartGroup();

    // Lista que guarda todas las caras de los cubos
    List<Group> loadCubesFaces3D;

    // Declarar las diferentes clases y librerias
    Cubito cubito;
    Cubo3D cubo3D = new Cubo3D();

    List<MFXButton> buttonList = new ArrayList<>();

    List<Group> centrosGroup = new ArrayList<>();
    List<Group> ladosGroup = new ArrayList<>();
    List<Group> cornersGroup = new ArrayList<>();
    List<List<String>> colorsList = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnBlue.setUserData("Blue");
        btnGreen.setUserData("Green");
        btnOrange.setUserData("Orange");
        btnRed.setUserData("Red");
        btnWhite.setUserData("White");
        btnYellow.setUserData("Yellow");
        manejoBotones(Boolean.TRUE);
        buttonList.addAll(Arrays.asList(btnBlue, btnGreen, btnOrange, btnRed, btnWhite, btnYellow));
        iniciarScena();
        manejoAccionBotones();

//        for (int i = 0; i < loadCubesFaces3D.size(); i++) {
//            switch (i) {
//                // centros
//                case 4, 10, 12, 14, 16, 22 -> {
//                    centrosGroup.add(loadCubesFaces3D.get(i));
//                }
//                // lados
//                case 1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25 -> {
//                    ladosGroup.add(loadCubesFaces3D.get(i));
//                }
//                // ezquinas
//                case 0, 2, 6, 8, 18, 20, 24, 26 -> {
//                    cornersGroup.add(loadCubesFaces3D.get(i));
//                }
//                default -> {
//                }
//            }
//        }
//        System.out.println("Grupo centro size: " + centrosGroup.size());
//        System.out.println("Grupo lados size: " + ladosGroup.size());
//        System.out.println("Grupo cornes size: " + cornersGroup.size());
//
//        System.out.println("Lista colores size: " + colorsList.size());
    }

    @Override
    public void initialize() {
    }

    @FXML
    private void onActionBtnIniciarPartida(ActionEvent event) {
    }

    private void iniciarScena() {
        int contador = 0, ejeX = -100, ejeY = -100, ejeZ = -100;
        // Ciclo principal que llena la matriz y crea el cubo3D en pantalla
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                for (int k = 0; k < deep; k++) {

                    //compara las posiciones de la matriz para asignar el tipo
                    if (contador == 13) {
                        ejeZ += 100;
                        contador++;
                        colorsList.add(Arrays.asList("vacio"));
                        continue;
                    } else if (contains(corners, contador)) {
                        cubito = new Cubito(contador, Cubito.PositionCube.CORNER);
                        matriz3D[i][j][k] = cubito;
                    } else if (contains(centers, contador)) {
                        cubito = new Cubito(contador, Cubito.PositionCube.CENTER);
                        matriz3D[i][j][k] = cubito;
                    } else {
                        cubito = new Cubito(contador, Cubito.PositionCube.BORDER);
                        matriz3D[i][j][k] = cubito;
                    }

                    colorsList.add(matriz3D[i][j][k].getColorsList());

                    cubo3D.getListGroup().get(contador).setTranslateX(ejeX);
                    cubo3D.getListGroup().get(contador).setTranslateY(ejeY);
                    cubo3D.getListGroup().get(contador).setTranslateZ(ejeZ);

                    ejeZ += 100;
                    contador++;
                }
                ejeZ = -100;
                ejeX += 100;
            }
            ejeX = -100;
            ejeY += 100;
        }

        for (int i = 0; i < colorsList.size(); i++) {
            List<String> aux = colorsList.get(i);
            for (int j = 0; j < aux.size(); j++) {
                aux.set(j, "vacio");
            }
        }
//        for (int i = 0; i < colorsList.size(); i++) {
//            List<String> aux = colorsList.get(i);
//            for (int j = 0; j < aux.size(); j++) {
//                System.out.print(aux.get(j) + " ");
//            }
//            System.out.println("");
//        }

        loadCubesFaces3D = new ArrayList<>(cubo3D.getListGroup());

        principalGroup.getChildren().addAll(loadCubesFaces3D);
        principalGroup.setId("Cube");

        Camera camera = new PerspectiveCamera();

        subScene.setRoot(principalGroup);
        subScene.setCamera(camera);

        //pocision centro en pantalla del grupo principal o del cubo3D en si
        principalGroup.translateXProperty().set(250);
        principalGroup.translateYProperty().set(260);
        principalGroup.translateZProperty().set(200);
        angleX.set(20);
        angleY.set(-20);

        punterosMouse(principalGroup);
        initMouseControl(principalGroup, subScene);
        rellenarCubo();
//      manejadores(groupCen);
    }

    // Metodo que vuelve a dibujar el cubo3D en pantalla con las posiciones actualizadas
    private void rellenarCubo() {

        for (int i = 0; i < loadCubesFaces3D.size(); i++) {
            Group group = loadCubesFaces3D.get(i);
            for (int j = 0; j < group.getChildren().size(); j++) {
                MeshView meshView = (MeshView) group.getChildren().get(j);
                PhongMaterial material = new PhongMaterial();
                Image textureImage = new Image("cr/ac/una/proyecto1_datos/resources/media/colors/A01.png");
                material.setDiffuseMap(textureImage);
                meshView.setMaterial(material);
            }
        }
    }

    // Metodo que comprueba si un valor esta dentro de un vector
    private boolean contains(int[] array, int value) {
        for (int element : array) {
            if (element == value) {
                return true;
            }
        }
        return false;
    }

    private void centros() {
        manejoBotones(false);
        for (int i = 0; i < centers.length; i++) {
            List<String> aux2 = colorsList.get(centers[i]);
            if (!"vacio".equals(aux2.get(0))) {
                for (int j = 0; j < buttonList.size(); j++) {
                    if (aux2.get(0) == buttonList.get(j).getUserData()) {
                        buttonList.get(j).setDisable(true);
                    }
                }
            }
        }

//        for (int i = 0; i < colorsList.size(); i++) {
//            List<String> aux = colorsList.get(i);
//            for (int j = 0; j < aux.size(); j++) {
//                System.out.print(aux.get(j) + " ");
//            }
//            System.out.println("");
//        }
    }

    private void lados() {
        manejoBotones(false);
        List<String> aux =  colorsList.get(groupId);
        // Si ya tiene un color asiganado no hace nada
        if (!"vacio".equals(aux.get(meshId))){
            return;
        }
        
        if (!"vacio".equals(aux.get(0)) && !"vacio".equals(aux.get(1))){
            return;
        }
        
        
        
        for (int i = 0; i < sides.length; i++) {
            List<String> colors = colorsList.get(sides[i]);
            
            for (int j = 0; j < colors.size(); j++) {
                if (!"vacio".equals(colors.get(j))) {
                    
                    for (int k = 0; k < buttonList.size(); k++) {
                        if (colors.get(j) == buttonList.get(k).getUserData()) {
                            buttonList.get(k).setDisable(true);
                        }
                    }
                }
            }
        }

//        for (int i = 0; i < colorsList.size(); i++) {
//            List<String> aux = colorsList.get(i);
//            for (int j = 0; j < aux.size(); j++) {
//                System.out.print(aux.get(j) + " ");
//            }
//            System.out.println("");
//        }
    }

    private void manejoAccionBotones() {
        // Crear un manejador de eventos común
        EventHandler<ActionEvent> commonHandler = (ActionEvent event) -> {
            // El código que se ejecutará cuando se haga clic en cualquiera de los botones
            List<String> aux = colorsList.get(groupId);
//        aux.set(meshId, "vacio");

            PhongMaterial material = new PhongMaterial();
            String color = (String) ((MFXButton) event.getSource()).getUserData();
            Image textureImage = new Image("cr/ac/una/proyecto1_datos/resources/media/colors/" + color + ".png");

            material.setDiffuseMap(textureImage);
            aux.set(meshId, color);

            meshView.setMaterial(material);

            manejoBotones(Boolean.TRUE);

//            PhongMaterial phongMaterial = (PhongMaterial) meshView.getMaterial();
//            System.out.println(phongMaterial.getDiffuseMap().getUrl());
//
//            MeshView meshView1 = (MeshView) loadCubesFaces3D.get(4).getChildren().get(0);
//
//            PhongMaterial phongMaterial1 = (PhongMaterial) meshView1.getMaterial();
//            System.out.println(phongMaterial1.getDiffuseMap().getUrl());
//
//            System.out.println("Botón presionado: " + ((MFXButton) event.getSource()).getUserData());
        };

        //asignarles el mismo manejador de eventos
        btnBlue.setOnAction(commonHandler);
        btnGreen.setOnAction(commonHandler);
        btnOrange.setOnAction(commonHandler);
        btnRed.setOnAction(commonHandler);
        btnWhite.setOnAction(commonHandler);
        btnYellow.setOnAction(commonHandler);
    }

    // Metodo que cambia los punteros del Mouse
    MeshView meshView;
    int meshId;
    int groupId;

    private void punterosMouse(SmartGroup group) {

        group.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                PickResult pickResult = event.getPickResult();
                Node pickedNode = pickResult.getIntersectedNode();

                if (pickedNode != null) {
                    System.out.println("Nodo seleccionado: " + pickedNode);

                    meshView = (MeshView) pickedNode;
                    Group group1 = (Group) meshView.getParent();

                    meshId = Integer.parseInt(meshView.getId());
                    groupId = Integer.parseInt(group1.getId());
                    int groupChildren = group1.getChildren().size();

                    System.out.println("meshview id: " + meshView.getId());
                    System.out.println("group id: " + group1.getId());
                    System.out.println("Tamano del group" + group1.getChildren().size());

                    if (groupChildren == 1) {
                        centros();
                    }
                    if (groupChildren == 2) {
                        lados();
                    }

                    // Puedes realizar acciones adicionales con el nodo seleccionado aquí}
                }
            }
        });
        group.setOnMouseEntered(event -> {
            if (!event.isPrimaryButtonDown()) {
                group.setCursor(Cursor.OPEN_HAND);
            }
        });

        group.setOnMouseExited(event -> {
            if (!event.isPrimaryButtonDown()) {
                group.setCursor(Cursor.DEFAULT);
            }
        });

        group.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown()) {
                group.setCursor(Cursor.CLOSED_HAND);
            }
        });

        group.setOnMouseReleased(event -> {
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
            group.setCursor(Cursor.OPEN_HAND);
        });
    }

    // Metodo para el manejo del mouse del cubo3D, angulos, giros y rotaciones
    private void initMouseControl(SmartGroup group, SubScene scene) {
        Rotate xRotate;
        Rotate yRotate;
        group.getTransforms().addAll(
                xRotate = new Rotate(0, Rotate.X_AXIS),
                yRotate = new Rotate(0, Rotate.Y_AXIS)
        );
        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);

        scene.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                anchorX = event.getSceneX();
                anchorY = event.getSceneY();
                anchorAngleX = angleX.get();
                anchorAngleY = angleY.get();
            }
        });

        scene.setOnMouseDragged(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                double newAngleX = anchorAngleX - (anchorY - event.getSceneY());
                double newAngleY = anchorAngleY + anchorX - event.getSceneX();

                // Reiniciar los ángulos a 0 cuando exceden 360 grados
                if (newAngleY >= 360) {
                    newAngleY -= 360;
                }
                if (newAngleY <= -360) {
                    newAngleY += 360;
                }
                // limita la rotacion del eje x, arriba, abajo
                newAngleX = Math.max(-90, Math.min(90, newAngleX));

                angleX.set(newAngleX);
                angleY.set(newAngleY);
            }
        });

        scene.setOnMouseReleased(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                anchorAngleX = angleX.get();
                anchorAngleY = angleY.get();
            }
        });

        scene.addEventHandler(ScrollEvent.SCROLL, event -> {
            // Define los límites de zoom
            double minZoom = -100;  // Zoom mínimo
            double maxZoom = 500;   // Zoom máximo

            // Obtén el cambio de desplazamiento en el eje Y
            double delta = event.getDeltaY();

            // Actualiza la propiedad de traslación Z con limitación
            double newZoom = group.getTranslateZ() + delta;
            newZoom = Math.max(minZoom, Math.min(maxZoom, newZoom));
            group.translateZProperty().set(newZoom);
        });

    }

    private void manejoBotones(Boolean bool) {
        btnBlue.setDisable(bool);
        btnGreen.setDisable(bool);
        btnOrange.setDisable(bool);
        btnRed.setDisable(bool);
        btnWhite.setDisable(bool);
        btnYellow.setDisable(bool);
    }

    // Clase que extiende a group para aplicar los giros y rotaciones
    class SmartGroup extends Group {

        Rotate r;
        Transform t = new Affine();
        int x1, y1;

        void rotateByX(int ang) {
            r = new Rotate(ang, Rotate.X_AXIS);
            t = t.createConcatenation(r);
            this.getTransforms().clear();
            this.getTransforms().addAll(t);
            x1 = ang;
        }

        void rotateByY(int ang) {
            r = new Rotate(ang, Rotate.Y_AXIS);
            t = t.createConcatenation(r);
            this.getTransforms().clear();
            this.getTransforms().addAll(t);
            y1 = ang;
        }
    }
}
