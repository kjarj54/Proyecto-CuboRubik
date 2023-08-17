package cr.ac.una.proyecto1_datos.controller;

import cr.ac.una.proyecto1_datos.model.Cubito;
import cr.ac.una.proyecto1_datos.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Camera;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.A;
import static javafx.scene.input.KeyCode.D;
import static javafx.scene.input.KeyCode.E;
import static javafx.scene.input.KeyCode.Q;
import static javafx.scene.input.KeyCode.S;
import static javafx.scene.input.KeyCode.W;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Luvara
 */
public class A01_PruebasViewController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private ImageView imvBackground;
    @FXML
    private SubScene subScene;
    @FXML
    private MFXButton btnDerecha;
    @FXML
    private MFXButton btnArriba;

    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);

    int[] corners = {0, 2, 6, 8, 18, 20, 24, 26};
    int[] centers = {4, 12, 14, 22};

    Cubito[][][] matriz3D = new Cubito[3][3][3];
    int[][][] matrizInt = new int[3][3][3];
    Cubito cubito;

    SmartGroup principalGroup = new SmartGroup();
    @FXML
    private MFXButton btnIzquierda;
    @FXML
    private MFXButton btnIzquierda2;
    @FXML
    private MFXButton btnDerecha2;
    @FXML
    private MFXButton btnArriba1;
    @FXML
    private MFXButton btnAbajo;
    @FXML
    private MFXButton btnAbajo1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        int contador = 0, ejeX = -100, ejeY = -100, ejeZ = -100;
        // Inicializar la matriz con algunos valores
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    //compara las posiciones de la matriz para asignar el tipo
                    if (contains(corners, contador)) {
                        cubito = new Cubito(Cubito.PositionCube.CORNER);
                        matriz3D[i][j][k] = cubito;
                    } else if (contains(centers, contador)) {
                        cubito = new Cubito(Cubito.PositionCube.CENTER);
                        matriz3D[i][j][k] = cubito;
                    } else {
                        cubito = new Cubito(Cubito.PositionCube.BORDER);
                        matriz3D[i][j][k] = cubito;
                    }
                    cubito.getCubito().setTranslateX(ejeX);
                    cubito.getCubito().setTranslateY(ejeY);
                    cubito.getCubito().setTranslateZ(ejeZ);
                    cubito.getCubito().setId(String.valueOf(contador));
                    ejeZ += 100;

                    principalGroup.getChildren().add(cubito.getCubito());
                    matrizInt[i][j][k] = contador;
                    contador++;
                }
                ejeZ = -100;
                ejeX += 100;
            }
            ejeX = -100;
            ejeY += 100;
        }

        Camera camera = new PerspectiveCamera();

        subScene.setRoot(principalGroup);
        subScene.setCamera(camera);

        //pocision centro en pantalla del grupo principal o del cubo en si
        principalGroup.translateXProperty().set(540);
        principalGroup.translateYProperty().set(260);
        principalGroup.translateZProperty().set(200);

        punterosMouse(principalGroup);
        initMouseControl(principalGroup, subScene);

        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 3; k++) {
                System.out.print(matriz3D[j][0][k].getCubito().getId() + " ");
            }
            System.out.println();
        }
        crearLineasEje(principalGroup);
//        System.out.println(group.getChildren().size());
//        manejadores(groupCen);
    }

    @Override
    public void initialize() {
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

    // Metodo que grega líneas que representan los ejes X, Y y Z
    private void crearLineasEje(SmartGroup group) {

        Line xAxis = new Line(0, 0, 300, 0);
        Line yAxis = new Line(0, 0, 0, 300);
        Line zAxis = new Line(0, 0, 0, -300);

        // Colorea las lineas
        xAxis.setStroke(Color.RED);
        yAxis.setStroke(Color.GREEN);
        zAxis.setStroke(Color.BLUE);

        // Aumentar el grosor de las líneas
        xAxis.setStrokeWidth(3);
        yAxis.setStrokeWidth(3);
        zAxis.setStrokeWidth(3);

        group.getChildren().addAll(xAxis, yAxis, zAxis);
    }

    // metodo que habilita funciones del teclado
    private void manejadores(SmartGroup group) {
        FlowController flowController = FlowController.getInstance();
        Stage stage = flowController.getMainStage();
        //Controlar con el teclado
        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case W ->
                    group.setTranslateZ(group.getTranslateZ() + 50);
                case S ->
                    group.setTranslateZ(group.getTranslateZ() - 50);
                case Q ->
                    group.rotateByX(10);
                case E ->
                    group.rotateByX(-10);
                case A ->
                    group.rotateByY(10);
                case D ->
                    group.rotateByY(-10);
            }
        });
    }

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
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
        });

        scene.setOnMouseDragged(event -> {
            angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
            angleY.set(anchorAngleY + anchorX - event.getSceneX());
        });

        scene.addEventHandler(ScrollEvent.SCROLL, event -> {
            //Get how much scroll was done in Y axis.
            double delta = event.getDeltaY();
            //Add it to the Z-axis location.
            group.translateZProperty().set(group.getTranslateZ() + delta);
        });
    }

    List<Group> listGroupStatic = new ArrayList<>();
    List<Group> listGroupRotatory = new ArrayList<>();
    SmartGroup groupFila1 = new SmartGroup();
    SmartGroup groupFila2 = new SmartGroup();
    SmartGroup groupColumna1 = new SmartGroup();
    SmartGroup groupColumna2 = new SmartGroup();

    @FXML
    private void onAntionBtnDerecha(ActionEvent event) {

        groupGiroHorizontal(0, -90, groupFila1);

        rotarMatrizHorizontal(0, 'D');

        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 3; k++) {
                System.out.print(matriz3D[0][j][k].getCubito().getId() + " ");
            }
            System.out.println();
        }
    }

    @FXML
    private void onAntionBtnDerecha2(ActionEvent event) {
        groupGiroHorizontal(2, -90, groupFila2);

        rotarMatrizHorizontal(2, 'D');

        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 3; k++) {
                System.out.print(matriz3D[0][j][k].getCubito().getId() + " ");
            }
            System.out.println();
        }
    }

    @FXML
    private void onAntionBtnIzquierda(ActionEvent event) {
        groupGiroHorizontal(0, 90, groupFila1);

        rotarMatrizHorizontal(0, 'I');

        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 3; k++) {
                System.out.print(matriz3D[0][j][k].getCubito().getId() + " ");
            }
            System.out.println();
        }
    }

    @FXML
    private void onAntionBtnIzquierda2(ActionEvent event) {
        groupGiroHorizontal(2, 90, groupFila2);

        rotarMatrizHorizontal(2, 'I');

        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 3; k++) {
                System.out.print(matriz3D[0][j][k].getCubito().getId() + " ");
            }
            System.out.println();
        }
    }

    @FXML
    private void onAntionBtnArriba(ActionEvent event) {
        groupGiroVertical(0, -90, groupColumna1);

        rotarMatrizVertical(0, 'A');

        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 3; k++) {
                System.out.print(matriz3D[j][0][k].getCubito().getId() + " ");
            }
            System.out.println();
        }
    }

    @FXML
    private void onAntionBtnArriba1(ActionEvent event) {
        groupGiroVertical(2, -90, groupColumna2);
    }

    @FXML
    private void onAntionBtnAbajo(ActionEvent event) {
        groupGiroVertical(0, 90, groupColumna1);

        rotarMatrizVertical(0, 'D');

        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 3; k++) {
                System.out.print(matriz3D[j][0][k].getCubito().getId() + " ");
            }
            System.out.println();
        }
    }

    @FXML
    private void onAntionBtnAbajo1(ActionEvent event) {
        groupGiroVertical(2, 90, groupColumna2);
    }

    private void groupGiroVertical(int columna, int giro, SmartGroup group) {

        int giroHorizontal = 0;

        principalGroup.getChildren().remove(group);
        listGroupStatic.clear();
        listGroupRotatory.clear();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    if (j == columna) {
                        listGroupRotatory.add(matriz3D[i][j][k].getCubito());
                    }
                }
            }
        }
        group.getChildren().clear();
        group.getChildren().addAll(listGroupRotatory);

        giroHorizontal += giro;

        group.rotateByX(giroHorizontal);

        principalGroup.getChildren().add(group);
        System.out.println(principalGroup.getChildren().size());
    }

    List<Group> aux1 = new ArrayList<>();
    List<Group> aux2 = new ArrayList<>();

    private void groupGiroHorizontal(int fila, int giro, SmartGroup group) {

        int giroHorizontal = 0;

        principalGroup.getChildren().remove(group);
        listGroupRotatory.clear();
        aux1.clear();
        aux2.clear();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    if (i == fila) {
                        listGroupRotatory.add(matriz3D[i][j][k].getCubito());
                        // groupColumna1.getChildren().remove(matriz3D[i][j][k].getCubito());
                        //groupColumna2.getChildren().remove(matriz3D[i][j][k].getCubito());
                        //comprobarNodosRepetidosFilas(matriz3D[i][j][k]);
                    }
//                    if (j == 0) {
//                        aux1.add(matriz3D[i][j][k].getCubito());
//                    }
//                    if (j == 2) {
//                        aux2.add(matriz3D[i][j][k].getCubito());
//                    }
                }
            }
        }
//        groupColumna1.getChildren().clear();
//        groupColumna2.getChildren().clear();
//
//        groupColumna1.getChildren().addAll(aux1);
//        groupColumna2.getChildren().addAll(aux2);
        group.getChildren().clear();
        group.getChildren().addAll(listGroupRotatory);
        giroHorizontal += giro;

        group.rotateByY(giroHorizontal);

        principalGroup.getChildren().add(group);
        System.out.println(principalGroup.getChildren().size());
    }

    private void comprobarNodosRepetidosFilas(Cubito cubo) {
        for (int i = 0; i < groupColumna1.getChildren().size(); i++) {
            if (groupColumna1.getChildren().get(i).getId().equals(cubo.getCubito().getId())) {
                groupColumna1.getChildren().remove(i);
            }
        }
        for (int i = 0; i < groupColumna2.getChildren().size(); i++) {
            if (groupColumna2.getChildren().get(i).getId().equals(cubo.getCubito().getId())) {
                groupColumna2.getChildren().remove(i);
            }
        }

    }

    private void rotarMatrizHorizontal(int fila, char opcion) {
        int n = 3;
        switch (opcion) {

            case 'D' -> {
                // rotar matriz sentido antihorario
                for (int i = 0; i < n / 2; i++) {
                    for (int j = i; j < n - i - 1; j++) {
                        Cubito temp = matriz3D[fila][i][j];
                        matriz3D[fila][i][j] = matriz3D[fila][j][n - i - 1];
                        matriz3D[fila][j][n - i - 1] = matriz3D[fila][n - i - 1][n - j - 1];
                        matriz3D[fila][n - i - 1][n - j - 1] = matriz3D[fila][n - j - 1][i];
                        matriz3D[fila][n - j - 1][i] = temp;
                    }
                }
            }
            case 'I' -> {
                // rotar matriz sentido horario
                for (int i = 0; i < n / 2; i++) {
                    for (int j = i; j < n - i - 1; j++) {
                        Cubito temp = matriz3D[fila][i][j];
                        matriz3D[fila][i][j] = matriz3D[fila][n - j - 1][i];
                        matriz3D[fila][n - j - 1][i] = matriz3D[fila][n - i - 1][n - j - 1];
                        matriz3D[fila][n - i - 1][n - j - 1] = matriz3D[fila][j][n - i - 1];
                        matriz3D[fila][j][n - i - 1] = temp;
                    }
                }
            }
            default ->
                throw new AssertionError();
        }

    }

    private void rotarMatrizVertical(int columna, char opcion) {
        int n = 3;
        switch (opcion) {

            case 'D' -> {
                // rotar matriz sentido antihorario
                for (int i = 0; i < n / 2; i++) {
                    for (int j = i; j < n - i - 1; j++) {
                        Cubito temp = matriz3D[i][columna][j];
                        matriz3D[i][columna][j] = matriz3D[j][columna][n - i - 1];
                        matriz3D[j][columna][n - i - 1] = matriz3D[n - i - 1][columna][n - j - 1];
                        matriz3D[n - i - 1][columna][n - j - 1] = matriz3D[n - j - 1][columna][i];
                        matriz3D[n - j - 1][columna][i] = temp;
                    }
                }
            }
            case 'A' -> {
                // rotar matriz sentido horario
                for (int i = 0; i < n / 2; i++) {
                    for (int j = i; j < n - i - 1; j++) {
                        Cubito temp = matriz3D[i][columna][j];
                        matriz3D[i][columna][j] = matriz3D[n - j - 1][columna][i];
                        matriz3D[n - j - 1][columna][i] = matriz3D[n - i - 1][columna][n - j - 1];
                        matriz3D[n - i - 1][columna][n - j - 1] = matriz3D[j][columna][n - i - 1];
                        matriz3D[j][columna][n - i - 1] = temp;
                    }
                }
            }
            default ->
                throw new AssertionError();
        }
        groupGiroVertical(0, 0, groupColumna1);

    }

    // metodo que cambia los punteros del Mouse
    private void punterosMouse(SmartGroup group) {
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
            group.setCursor(Cursor.OPEN_HAND);
            //centrarCamara();
            //System.out.println(angleY);
            //System.out.println(group.x1);
            System.out.println(group.y1);

        });
    }

    // Clase que extiende a group para aplicar los giros y rotaciones
    class SmartGroup extends Group {

        Rotate r;
        Transform t = new Rotate();
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
