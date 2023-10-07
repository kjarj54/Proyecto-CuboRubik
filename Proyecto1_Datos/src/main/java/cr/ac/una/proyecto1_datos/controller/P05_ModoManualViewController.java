package cr.ac.una.proyecto1_datos.controller;

import cr.ac.una.proyecto1_datos.model.Cubito;
import cr.ac.una.proyecto1_datos.model.Cubo3D;
import cr.ac.una.proyecto1_datos.util.AppContext;
import cr.ac.una.proyecto1_datos.util.FlowController;
import cr.ac.una.proyecto1_datos.util.Mensaje;
import cr.ac.una.proyecto1_datos.util.SoundUtil;
import io.github.palexdev.materialfx.controls.MFXButton;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
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
    private Label lblBlue;
    @FXML
    private Label lblGreen;
    @FXML
    private Label lblOrange;
    @FXML
    private Label lblRed;
    @FXML
    private Label lblWhite;
    @FXML
    private Label lblYellow;
    @FXML
    private SubScene subScene;
    @FXML
    private MFXButton btnIniciarPartida;
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
    @FXML
    private MFXButton btnSalir;
    @FXML
    private MFXButton btnReiniciarLados;
    @FXML
    private MFXButton btnReiniciarEsquinas;

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

    List<List<String>> colorsList = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        AnchorPane.setTopAnchor(root, 0.0);
        AnchorPane.setLeftAnchor(root, 0.0);
        AnchorPane.setRightAnchor(root, 0.0);
        AnchorPane.setBottomAnchor(root, 0.0);

        btnBlue.setUserData("Blue");
        btnGreen.setUserData("Green");
        btnOrange.setUserData("Orange");
        btnRed.setUserData("Red");
        btnWhite.setUserData("White");
        btnYellow.setUserData("Yellow");
        manejoBotones(Boolean.TRUE);
        iniciarScena();
        manejoAccionBotones();
    }

    @Override
    public void initialize() {
    }

    @FXML
    private void onActionBtnIniciarPartida(ActionEvent event) {
        SoundUtil.mouseEnterSound();
        if (iniciarPartida == 0) {
            AppContext.getInstance().set("coloresManual", colorsList);
            FlowController.getInstance().goView("P06_MesaJuegoView");
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cubo incompleto", getStage(), "Es necesario llenar todo el cubo.");
        }
    }

    @FXML
    private void onActionBtnSalir(ActionEvent event) {
        SoundUtil.mouseEnterSound();
        FlowController.getInstance().delete("P05_ModoManualView");
        FlowController.getInstance().goView("P03_NuevaPartidaView");
    }

    @FXML
    private void onActionBtnReiniciarLados(ActionEvent event) {
        SoundUtil.mouseEnterSound();
        for (int i = 0; i < sides.length; i++) {
            List<String> color = colorsList.get(sides[i]);
            for (int j = 0; j < color.size(); j++) {
                color.set(j, "A01");
            }
        }
        blueSides = 4;
        greenSides = 4;
        orangeSides = 4;
        redSides = 4;
        whiteSides = 4;
        yellowSides = 4;
        rellenarCubo();
        manejoContadoresTotal();
    }

    @FXML
    private void onActionBtnReiniciarEsquinas(ActionEvent event) {
        SoundUtil.mouseEnterSound();
        for (int i = 0; i < corners.length; i++) {
            List<String> color = colorsList.get(corners[i]);
            for (int j = 0; j < color.size(); j++) {
                color.set(j, "A01");
            }
        }
        blueCorner = 4;
        greenCorner = 4;
        orangeCorner = 4;
        redCorner = 4;
        whiteCorner = 4;
        yellowCorner = 4;
        rellenarCubo();
        manejoContadoresTotal();
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
                if (aux.size() == 1) {

                } else {
                    aux.set(j, "A01");
                }
            }
        }

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
    }

    // Metodo que vuelve a dibujar el cubo3D en pantalla con las posiciones actualizadas
    private void rellenarCubo() {

        int contador = 0;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                for (int k = 0; k < deep; k++) {
                    if (contador == 13) {
                        contador++;
                        continue;
                    }
                    cambiarColores(loadCubesFaces3D.get(contador), matriz3D[i][j][k].getColorsList());
                    contador++;
                }
            }
        }
    }

    // Repinta las caras de cada posicion, metodo perteneciente a rellenarCubo()
    public void cambiarColores(Group group, List<String> color) {
        for (int i = 0; i < color.size(); i++) {
            group.getChildren().get(i);
            repintarCaras((MeshView) group.getChildren().get(i), color.get(i));
        }
    }

    // Repinta las caras de cada posicion, metodo perteneciente a cambiarColores()
    public void repintarCaras(MeshView mesh, String color) {
        PhongMaterial material = new PhongMaterial();
        Image textureImage;
        if (color.equals("A01")) {
            textureImage = new Image("cr/ac/una/proyecto1_datos/resources/media/colors/" + color + ".png");
        } else {
            textureImage = new Image("cr/ac/una/proyecto1_datos/resources/media/colors/" + color + "0.png");
        }
        material.setDiffuseMap(textureImage);
        mesh.setMaterial(material);
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

    // Metodo que cambia los punteros del Mouse
    MeshView meshView;
    int meshId;
    int groupId;
    int groupChildren;

    private void punterosMouse(SmartGroup group) {

        group.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                PickResult pickResult = event.getPickResult();
                Node pickedNode = pickResult.getIntersectedNode();

                if (pickedNode != null) {
//                    System.out.println("Nodo seleccionado: " + pickedNode);

                    meshView = (MeshView) pickedNode;
                    Group group1 = (Group) meshView.getParent();

                    meshId = Integer.parseInt(meshView.getId());
                    groupId = Integer.parseInt(group1.getId());
                    groupChildren = group1.getChildren().size();

//                    System.out.println("meshview id: " + meshView.getId());
//                    System.out.println("group id: " + group1.getId());
//                    System.out.println("Tamano del group" + group1.getChildren().size());
                    if (groupChildren == 1) {
                        manejoBotones(true);
                    }
                    if (groupChildren == 2) {
                        manejoBotones(true);
                        lados();
                    }

                    if (groupChildren == 3) {
                        manejoBotones(true);
                        esquinas();
                    }
                    // Puedes realizar acciones adicionales con el nodo seleccionado aquí}
                }
            }
        });
        group.setOnMouseEntered(event -> {
            if (!event.isSecondaryButtonDown()) {
                group.setCursor(Cursor.OPEN_HAND);
            }
        });

        group.setOnMouseExited(event -> {
            if (!event.isSecondaryButtonDown()) {
                group.setCursor(Cursor.DEFAULT);
            }
        });

        group.setOnMousePressed(event -> {
            if (event.isSecondaryButtonDown()) {
                group.setCursor(Cursor.CLOSED_HAND);
            }
        });

        group.setOnMouseReleased(event -> {
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
            group.setCursor(Cursor.OPEN_HAND);
        });
        btnIniciarPartida.setOnMouseEntered(event -> {
            SoundUtil.mouseHoverSound();
        });
        btnReiniciarEsquinas.setOnMouseEntered(event -> {
            SoundUtil.mouseHoverSound();
        });
        btnReiniciarLados.setOnMouseEntered(event -> {
            SoundUtil.mouseHoverSound();
        });
        btnSalir.setOnMouseEntered(event -> {
            SoundUtil.mouseHoverSound();
        });
    }

    // Metodo que hace una misma accion para todos los botones de color
    private void manejoAccionBotones() {

        // Crear un manejador de eventos común
        EventHandler<ActionEvent> commonHandler = (ActionEvent event) -> {

            // El código que se ejecutará cuando se haga clic en cualquiera de los botones
            List<String> aux = colorsList.get(groupId);

            PhongMaterial material = new PhongMaterial();
            String color = (String) ((MFXButton) event.getSource()).getUserData();
            Image textureImage = new Image("cr/ac/una/proyecto1_datos/resources/media/colors/" + color + "0.png");
            material.setDiffuseMap(textureImage);
            meshView.setMaterial(material);

            // setea el color en la lista que almacena los colores
            aux.set(meshId, color);

            if (groupChildren == 2) {
                manejoContadoresLados(color);
            }
            if (groupChildren == 3) {
                manejoContadoresEsquinas(color);
            }

            manejoContadoresTotal();
            manejoBotones(Boolean.TRUE);
        };

        //asignarles el mismo manejador de eventos
        btnBlue.setOnAction(commonHandler);
        btnGreen.setOnAction(commonHandler);
        btnOrange.setOnAction(commonHandler);
        btnRed.setOnAction(commonHandler);
        btnWhite.setOnAction(commonHandler);
        btnYellow.setOnAction(commonHandler);
    }

    private void esquinas() {
        List<String> aux = colorsList.get(groupId);

        // Si ya tiene un color asiganado no hace nada
        if (!"A01".equals(aux.get(meshId))) {
            return;
        }

        // Si los 3 lados estan vacios habilita los botones y sale del metodo
        if ("A01".equals(aux.get(0)) && "A01".equals(aux.get(1)) && "A01".equals(aux.get(2))) {
            manejoBotones(false);
            comprobarContadoresEsquinas("");
            return;
        }

        // Comprobar y deshabilitar los opuestos
        manejoBotones(false);
        for (int i = 0; i < aux.size(); i++) {
            if (!aux.get(i).equals("A01")) {
                comprobarColoresOpuestos(aux.get(i));
                comprobarContadoresEsquinas(aux.get(i));
            }
        }
        comprobarEsquinasIguales();
    }

    int blueCorner = 4;
    int greenCorner = 4;
    int orangeCorner = 4;
    int redCorner = 4;
    int whiteCorner = 4;
    int yellowCorner = 4;

    private void manejoContadoresEsquinas(String color) {
        switch (color) {
            case "Blue" ->
                blueCorner--;
            case "Green" ->
                greenCorner--;
            case "Orange" ->
                orangeCorner--;
            case "Red" ->
                redCorner--;
            case "White" ->
                whiteCorner--;
            case "Yellow" ->
                yellowCorner--;
        }
    }

    private void comprobarContadoresEsquinas(String color) {

        if (blueCorner <= 0 || color.equals("Blue")) {
            btnBlue.setDisable(true);
        }
        if (greenCorner <= 0 || color.equals("Green")) {
            btnGreen.setDisable(true);
        }
        if (orangeCorner <= 0 || color.equals("Orange")) {
            btnOrange.setDisable(true);
        }
        if (redCorner <= 0 || color.equals("Red")) {
            btnRed.setDisable(true);
        }
        if (whiteCorner <= 0 || color.equals("White")) {
            btnWhite.setDisable(true);
        }
        if (yellowCorner <= 0 || color.equals("Yellow")) {
            btnYellow.setDisable(true);
        }
    }

    private void comprobarEsquinasIguales() {
        List<String> aux = colorsList.get(groupId);

        for (int i = 0; i < corners.length; i++) {
            List<String> aux2 = colorsList.get(corners[i]);
            int contador = 0;
            for (int j = 0; j < aux.size(); j++) {
                for (int k = 0; k < aux2.size(); k++) {
                    if (aux.get(j).equals(aux2.get(k))) {
                        contador++;
                    }
                }
            }
            if (contador == 2) {
                for (int q = 0; q < aux2.size(); q++) {
                    if (!aux2.get(q).equals("A01")) {
                        comprobarContadoresEsquinas(aux2.get(q));
                    }
                }
            }
        }
    }

    private void lados() {

        List<String> aux = colorsList.get(groupId);

        // Si ya tiene un color asiganado no hace nada
        if (!"A01".equals(aux.get(meshId))) {
            return;
        }

        // Si los 2 lados estan vacios habilita los botones y sale del metodo
        if ("A01".equals(aux.get(0)) && "A01".equals(aux.get(1))) {
            manejoBotones(false);
            comprobarContadoresLados("");
            return;
        }

        manejoBotones(false);
        // Si solo un lado tiene color, deshabilita ese boton
        for (int i = 0; i < aux.size(); i++) {
            if (!aux.get(i).equals("A01")) {
                comprobarColoresOpuestos(aux.get(i));
                comprobarContadoresLados(aux.get(i));
                comprobarLadosIguales(aux.get(i));
            }
        }

    }

    private void comprobarColoresOpuestos(String color) {
        switch (color) {
            case "Blue" ->
                btnGreen.setDisable(true);
            case "Green" ->
                btnBlue.setDisable(true);
            case "Orange" ->
                btnRed.setDisable(true);
            case "Red" ->
                btnOrange.setDisable(true);
            case "White" ->
                btnYellow.setDisable(true);
            case "Yellow" ->
                btnWhite.setDisable(true);
        }
    }

    private void comprobarContadoresLados(String color) {

        if (blueSides <= 0 || color.equals("Blue")) {
            btnBlue.setDisable(true);
        }
        if (greenSides <= 0 || color.equals("Green")) {
            btnGreen.setDisable(true);
        }
        if (orangeSides <= 0 || color.equals("Orange")) {
            btnOrange.setDisable(true);
        }
        if (redSides <= 0 || color.equals("Red")) {
            btnRed.setDisable(true);
        }
        if (whiteSides <= 0 || color.equals("White")) {
            btnWhite.setDisable(true);
        }
        if (yellowSides <= 0 || color.equals("Yellow")) {
            btnYellow.setDisable(true);
        }
    }

    private void comprobarLadosIguales(String color) {

        for (int i = 0; i < sides.length; i++) {
            List<String> aux = colorsList.get(sides[i]);

            for (int j = 0; j < aux.size(); j++) {
                if (aux.get(j).equals(color)) {

                    for (int k = 0; k < aux.size(); k++) {
                        if (!aux.get(k).equals(color) || aux.get(k).equals("A01")) {
                            comprobarContadoresLados(aux.get(k));
                        }
                    }
                }
            }
        }
    }

    int blueSides = 4;
    int greenSides = 4;
    int orangeSides = 4;
    int redSides = 4;
    int whiteSides = 4;
    int yellowSides = 4;

    private void manejoContadoresLados(String color) {
        switch (color) {
            case "Blue" ->
                blueSides--;
            case "Green" ->
                greenSides--;
            case "Orange" ->
                orangeSides--;
            case "Red" ->
                redSides--;
            case "White" ->
                whiteSides--;
            case "Yellow" ->
                yellowSides--;
        }
    }

    int iniciarPartida = 0;

    private void manejoContadoresTotal() {

        int blueTotal = blueSides + blueCorner;
        int greenTotal = greenSides + greenCorner;
        int orangeTotal = orangeSides + orangeCorner;
        int redTotal = redSides + redCorner;
        int whiteTotal = whiteSides + whiteCorner;
        int yellowTotal = yellowSides + yellowCorner;

        iniciarPartida = blueTotal + greenTotal + orangeTotal + redTotal + whiteTotal + yellowTotal;

        lblBlue.setText(Integer.toString(blueTotal));
        lblGreen.setText(Integer.toString(greenTotal));
        lblOrange.setText(Integer.toString(orangeTotal));
        lblRed.setText(Integer.toString(redTotal));
        lblWhite.setText(Integer.toString(whiteTotal));
        lblYellow.setText(Integer.toString(yellowTotal));
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
