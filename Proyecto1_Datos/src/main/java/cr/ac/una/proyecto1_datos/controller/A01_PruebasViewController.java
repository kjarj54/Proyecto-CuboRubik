package cr.ac.una.proyecto1_datos.controller;

import cr.ac.una.proyecto1_datos.util.FlowController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Camera;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
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
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Line;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
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
    private SubScene subScene;
    
    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private DoubleProperty angleX = new SimpleDoubleProperty(0);
    private DoubleProperty angleY = new SimpleDoubleProperty(0);

    int[] frontFace = {
        // Cara frontal Verde
        0, 2, 2, 1, 1, 3, 2, 1, 3, 2, 1, 3
    };
    int[] rigthFace = {
        // Cara lateral derecha Roja
        1, 2, 3, 1, 5, 3,
        3, 1, 7, 2, 5, 3
    };
    int[] backFace = {
        // Cara trasera Azul
        5, 2, 7, 1, 4, 3,
        7, 1, 6, 2, 4, 3
    };
    int[] leftFace = {
        // Cara lateral izquierda Naranja
        4, 2, 6, 1, 0, 3,
        6, 1, 2, 2, 0, 3
    };
    int[] upFace = {
        // Cara superior Blanca
        0, 2, 1, 1, 4, 3,
        1, 1, 5, 2, 4, 3
    };
    int[] downFace = {
        // Cara inferior Amarilla
        7, 2, 3, 1, 6, 3,
        3, 1, 2, 2, 6, 3
    };  

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        SmartGroup groupCen = createGroupWithBoxes();
        SmartGroup groupUp = createGroupWithBoxes();
        SmartGroup groupDown = createGroupWithBoxes();

        groupUp.setTranslateY(-102);
        groupDown.setTranslateY(+102);

        SmartGroup groupCen2 = createGroupWithBoxes();
        SmartGroup groupUp2 = createGroupWithBoxes();
        SmartGroup groupDown2 = createGroupWithBoxes();

        groupCen2.setTranslateZ(-102);
        groupUp2.setTranslateZ(-102);
        groupDown2.setTranslateZ(-102);
        groupUp2.setTranslateY(-102);
        groupDown2.setTranslateY(+102);

        SmartGroup groupCen3 = createGroupWithBoxes();
        SmartGroup groupUp3 = createGroupWithBoxes();
        SmartGroup groupDown3 = createGroupWithBoxes();

        groupCen3.setTranslateZ(+102);
        groupUp3.setTranslateZ(+102);
        groupDown3.setTranslateZ(+102);
        groupUp3.setTranslateY(-102);
        groupDown3.setTranslateY(+102);

        SmartGroup group = new SmartGroup();
        group.getChildren().addAll(groupUp, groupCen, groupDown, groupUp2, groupCen2, groupDown2, groupUp3, groupCen3, groupDown3);

        crearLineasEje(group);

        Camera camera = new PerspectiveCamera();

        subScene.setRoot(group);
        subScene.setCamera(camera);

        //pocision en pantalla grupo principal
        group.translateXProperty().set(540);
        group.translateYProperty().set(260);
        group.translateZProperty().set(500);

        punterosMouse(group);
        initMouseControl(group, subScene);
//        manejadores(groupCen);

    }

    private void centrarCamara() {
        // Reajustar la cámara para que la cara rote hacia la pantalla se convierta en la principal
        Camera camera = new PerspectiveCamera(true);
        camera.getTransforms().addAll(
                new Rotate(-30, Rotate.X_AXIS), // Rotar hacia arriba
                new Rotate(45, Rotate.Y_AXIS), // Rotar hacia la derecha
                new Translate(0, 0, 500) // Alejar la cámara
        );
        subScene.setCamera(camera);
    }

    private SmartGroup createGroupWithBoxes() {
        SmartGroup group = new SmartGroup();

        MeshView[] cuboMeshCenter = new MeshView[6];
        // Cara frontal
        cuboMeshCenter[0] = createCubeMeshView(100, frontFace, "Green");
        // Cara lateral derecha
        cuboMeshCenter[1] = createCubeMeshView(100, rigthFace, "Red");
        // Cara trasera
        cuboMeshCenter[2] = createCubeMeshView(100, backFace, "Blue");
        // Cara lateral izquierda
        cuboMeshCenter[3] = createCubeMeshView(100, leftFace, "Orange");
        // Cara superior
        cuboMeshCenter[4] = createCubeMeshView(100, upFace, "White");
        // Cara inferior
        cuboMeshCenter[5] = createCubeMeshView(100, downFace, "Yellow");

        MeshView[] cuboMeshleft = new MeshView[6];
        // Cara frontal
        cuboMeshleft[0] = createCubeMeshView(100, frontFace, "Green");
        // Cara lateral derecha
        cuboMeshleft[1] = createCubeMeshView(100, rigthFace, "Red");
        // Cara trasera
        cuboMeshleft[2] = createCubeMeshView(100, backFace, "Blue");
        // Cara lateral izquierda
        cuboMeshleft[3] = createCubeMeshView(100, leftFace, "Orange");
        // Cara superior
        cuboMeshleft[4] = createCubeMeshView(100, upFace, "White");
        // Cara inferior
        cuboMeshleft[5] = createCubeMeshView(100, downFace, "Yellow");

        MeshView[] cuboMeshRight = new MeshView[6];
        // Cara frontal
        cuboMeshRight[0] = createCubeMeshView(100, frontFace, "Green");
        // Cara lateral derecha
        cuboMeshRight[1] = createCubeMeshView(100, rigthFace, "Red");
        // Cara trasera
        cuboMeshRight[2] = createCubeMeshView(100, backFace, "Blue");
        // Cara lateral izquierda
        cuboMeshRight[3] = createCubeMeshView(100, leftFace, "Orange");
        // Cara superior
        cuboMeshRight[4] = createCubeMeshView(100, upFace, "White");
        // Cara inferior
        cuboMeshRight[5] = createCubeMeshView(100, downFace, "Yellow");

        Group cubo1 = new Group(cuboMeshCenter);
        Group cubo2 = new Group(cuboMeshRight);
        Group cubo3 = new Group(cuboMeshleft);
        cubo2.setTranslateX(+102);
        cubo3.setTranslateX(-102);
        
//        MeshView cubo = (MeshView) cubo3.getChildren().get(0);
//        cubo.setMaterial(new PhongMaterial(Color.BLACK));

        group.getChildren().addAll(cubo1, cubo2, cubo3);

        return group;

    }

    private MeshView createCubeMeshView(float size, int[] face, String faceColor) {
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

    private void crearLineasEje(SmartGroup group) {
        // Agregar líneas que representan los ejes X, Y y Z
        Line xAxis = new Line(0, 0, 300, 0);
        Line yAxis = new Line(0, 0, 0, 300);
        Line zAxis = new Line(0, 0, 0, -300);

        xAxis.setStroke(Color.RED);
        yAxis.setStroke(Color.GREEN);
        zAxis.setStroke(Color.BLUE);

        // Aumentar el grosor de las líneas
        xAxis.setStrokeWidth(3);
        yAxis.setStrokeWidth(3);
        zAxis.setStrokeWidth(3);

        group.getChildren().addAll(xAxis, yAxis, zAxis);
    }

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

    class SmartGroup extends Group {

        Rotate r;
        Transform t = new Rotate();
        int x1, y1;

        void rotateByX(int ang) {
            r = new Rotate(ang, Rotate.X_AXIS);
            t = t.createConcatenation(r);
            this.getTransforms().clear();
            this.getTransforms().addAll(t);
            x1=ang;
        }

        void rotateByY(int ang) {
            r = new Rotate(ang, Rotate.Y_AXIS);
            t = t.createConcatenation(r);
            this.getTransforms().clear();
            this.getTransforms().addAll(t);
            y1=ang;
        }
    }
    
double x;
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
            System.out.println(group.x1);
            System.out.println(group.y1);
            //x=angleY.doubleValue();
//            if (angleY.equals(360)){
//                angleY.setValue(0);
//            }
//            double x = group.getRotate();
//            if (x<90)
//                group.rotateByX((int) (90-x));
//            if (x<180)
//                group.rotateByX((int) (180-x));
//            if (x<270)
//                group.rotateByX((int) (270-x));
//            if (x<360)
//                group.rotateByX((int) (360-x));
//            group.translateXProperty().set(540);
//            group.translateYProperty().set(260);
//            group.translateZProperty().set(500);
        });
    }

    @Override
    public void initialize() {
    }

    @FXML
    private void onKeyPressRoot(KeyEvent event) {

    }

}
