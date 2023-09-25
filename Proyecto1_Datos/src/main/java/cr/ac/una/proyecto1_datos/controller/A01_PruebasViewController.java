package cr.ac.una.proyecto1_datos.controller;

import cr.ac.una.proyecto1_datos.model.Cubito;
import cr.ac.una.proyecto1_datos.model.Cubo3D;
import cr.ac.una.proyecto1_datos.model.Jugador;
import cr.ac.una.proyecto1_datos.util.AppContext;
import cr.ac.una.proyecto1_datos.util.Cronometro;
import cr.ac.una.proyecto1_datos.util.FlowController;
import cr.ac.una.proyecto1_datos.util.Mensaje;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.RotateTransition;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
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
import javafx.scene.shape.Line;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    @FXML
    private MFXButton btnDerechaFila1;
    @FXML
    private MFXButton btnIzquierdaFila1;
    @FXML
    private MFXButton btnIzquierdaFila3;
    @FXML
    private MFXButton btnDerechaFila3;
    @FXML
    private MFXButton btnArribaColumna1;
    @FXML
    private MFXButton btnArribaColumna3;
    @FXML
    private MFXButton btnAbajoColumna1;
    @FXML
    private MFXButton btnAbajoColumna3;
    @FXML
    private MFXButton btnRotarReloj;
    @FXML
    private MFXButton btnRotarAntireloj;
    @FXML
    private MFXButton btnSolucionar;
    @FXML
    private MFXButton btnIniciarPartida;
    @FXML
    private MFXButton btnGuardarSalir;
    @FXML
    private MFXButton btnDesarmarAleatorio;
    @FXML
    private MFXButton btnPruebas;
    @FXML
    private Label lblNombre;
    @FXML
    private Label lblTiempo;
    @FXML
    private Label lblMovimientos;
    @FXML
    private Label lblPuntos;

    // Variables Globales-------------------------------------------------------
    // Variables para los giros del cubo con el mouse
    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);

    // Vectores con las posiciones de las esquinas y centros
    int[] corners = {0, 2, 6, 8, 18, 20, 24, 26};
    int[] centers = {4, 10, 12, 14, 16, 22};

    // Vectores con las posiciones de las caras
    int[] face0 = {0, 3, 6, 9, 12, 15, 18, 21, 24}; //cara frontal
    int[] face1 = {6, 7, 8, 15, 16, 17, 24, 25, 26}; //cara derecha
    int[] face2 = {2, 5, 8, 11, 14, 17, 20, 23, 26}; //cara atras
    int[] face3 = {0, 1, 2, 9, 10, 11, 18, 19, 20}; //cara izquierda
    int[] face4 = {0, 1, 2, 3, 4, 5, 6, 7, 8}; //cara arriba
    int[] face5 = {18, 19, 20, 21, 22, 23, 24, 25, 26}; //cara abajo

    // Variables para controlar los ciclos for de la matriz principal
    int row = 3;
    int column = 3;
    int deep = 3;
    
    // Matriz de 3 dimensiones para almacenar objetos de tipo cubito
    Cubito[][][] matriz3D = new Cubito[3][3][3];
    
    int contadorArmar = 0; // Contador de animaciones completadas
    int contadorMovimientos;

    // Booleanos para el control de diversas funciones
    Boolean armar = false;

    // Grupo principal en pantalla para el cubo3D completo
    SmartGroup principalGroup = new SmartGroup();

    // Lista que guarda todas las caras de los cubos
    List<Group> loadCubesFaces3D;
    
    // Lista utilizada en vez de la pila para pruebas. Guarda la cara y el movimiento
    List<List<String>> movimientos = new ArrayList<>();

    // Declarar las diferentes clases y librerias
    Cubito cubito;
    Cubo3D cubo3D = new Cubo3D();
    Jugador jugador;
    Cronometro cronometro;
    Random rand = new Random();
    // FIN Variables Globales-------------------------------------------------------

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//        face4.addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
//        jugador = (Jugador) AppContext.getInstance().get("Jugador");
//        System.out.println( jugador.toString());
        iniciarScena();
        onActionsGiros();
        cronometro = new Cronometro(lblTiempo);// Inicializa el cronometro en 0

//        cronometro.startCronometro(); // Inicia o continua el cronometro o hilo
//        cronometro.stopCronometro();  // Pausa el cronometro o hilo
        btnPruebas.setOnAction(event -> {
            for (int i = 0; i < row; i++) {
                System.out.println("Capa " + (i + 1) + ":");
                for (int j = 0; j < column; j++) {
                    for (int k = 0; k < deep; k++) {
                        if (i == 1 && j == 1 && k == 1) {
                            continue;
                        }
                        System.out.print(matriz3D[i][j][k].getId() + " ");

                    }
                    System.out.println();
                }
                System.out.println();
            }
        });

    }

    public void imprimirPuntosMovimientos() {
        int puntos = cronometro.getTime() / contadorMovimientos * 100;

        String pointsStr = String.format("%04d", puntos); // Formatea los minutos con cuatro dígitos
        String movesStr = String.format("%02d", contadorMovimientos); // Formatea los minutos con cuatro dígitos

        lblMovimientos.setText("Movimientos:" + movesStr);
        lblPuntos.setText("Puntos: " + pointsStr);
    }

    @Override
    public void initialize() {
    }

    private void comprobarArmado() {
        int contador = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                for (int k = 0; k < deep; k++) {

                    if (i == 1 && j == 1 && k == 1) {
                        contador++;
                        continue;
                    }
                    if (matriz3D[i][j][k].getId() == contador) {
                        contador++;
                    }
                }
            }
        }
        if (contador == 27) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cubo completado", getStage(),
                    "Felicidades has completado el cubo.");
            manejoBotones(true);
        }
    }

    // Variables para el boton IniciarPartida
    Boolean partidaIniciada = false;

    @FXML
    private void onActionBtnIniciarPartida(ActionEvent event) {
        if (movimientos.size() < 15) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de inicio", getStage(),
                    "Es necesario hacer más movimientos de desarmar el cubo para iniciar la partida");
        } else {
            partidaIniciada = true;
            btnDesarmarAleatorio.setDisable(true);
            btnIniciarPartida.setDisable(true);
            cronometro.startCronometro();
            manejoBotones(true);
        }
    }

    @FXML
    private void onActionBtnGuardarSalir(ActionEvent event) {
    }

    // Variables para el boton desarmarAleatorio
    int movimientosAleatorios;// Control para los movimientos aleatorios
    int contadorDesarmar = 0; // Contador de animaciones completadas
    Boolean desarmar = false;

    @FXML
    private void onActionBtnDesarmarAleatorio(ActionEvent event) {
        movimientosAleatorios = rand.nextInt(11) + 15;
        desarmar = true;
        System.out.println(movimientosAleatorios);
        desarmarAleatorio();
        btnDesarmarAleatorio.setDisable(true);
        manejoBotones(true);
    }

    @FXML
    private void onActionBtnSolucionar(ActionEvent event) {
        armarCubo();
    }

    // Metodo que carga toda la pantalla principal
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

    // Metodo para el manejo de los botones del movimientos del cubo
    private void onActionsGiros() {
        btnDerechaFila1.setOnAction(event -> {
            ejecutarAccion(-1, "right0", 1);
        });
        btnIzquierdaFila1.setOnAction(event -> {
            ejecutarAccion(-1, "left0", 1);
        });
        btnDerechaFila3.setOnAction(event -> {
            ejecutarAccion(-1, "right2", 1);
        });
        btnIzquierdaFila3.setOnAction(event -> {
            ejecutarAccion(-1, "left2", 1);
        });
        btnArribaColumna1.setOnAction(event -> {
            ejecutarAccion(-1, "up0", 1);
        });
        btnAbajoColumna1.setOnAction(event -> {
            ejecutarAccion(-1, "down0", 1);
        });
        btnArribaColumna3.setOnAction(event -> {
            ejecutarAccion(-1, "up2", 1);
        });
        btnAbajoColumna3.setOnAction(event -> {
            ejecutarAccion(-1, "down2", 1);
        });
        btnRotarReloj.setOnAction(event -> {
            ejecutarAccion(-1, "hora", 1);
        });
        btnRotarAntireloj.setOnAction(event -> {
            ejecutarAccion(-1, "antihora", 1);
        });
    }

    // Metodo que vuelve a dibujar el cubo3D en pantalla con las posiciones actualizadas
    private void rellenarCubo() {

        principalGroup.getChildren().clear();
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
        principalGroup.getChildren().addAll(loadCubesFaces3D);
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
        Image textureImage = new Image("cr/ac/una/proyecto1_datos/resources/media/colors/" + color + ".png");
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

    // Metodo que habilita funciones del teclado/ NO IMPLEMENTADO AUN/
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

    // Metodo que hace cambios en las listas de colores al hacer giros verticales
    private void colorListSwapLaterales(int col, String direccion) {
        if (col == 0) {
            if (direccion.equals("up")) {
                // pos 2
                Collections.swap(matriz3D[0][0][2].getColorsList(), 0, 2);
                Collections.swap(matriz3D[0][0][2].getColorsList(), 1, 2);
                // pos 20
                Collections.swap(matriz3D[2][0][2].getColorsList(), 0, 2);
                // pos 18
                Collections.swap(matriz3D[2][0][0].getColorsList(), 0, 2);
                Collections.swap(matriz3D[2][0][0].getColorsList(), 0, 1);
                //pos 0
                Collections.swap(matriz3D[0][0][0].getColorsList(), 1, 2);
            } else {
                // pos 18
                Collections.swap(matriz3D[2][0][0].getColorsList(), 1, 2);
                // pos 20
                Collections.swap(matriz3D[2][0][2].getColorsList(), 0, 1);
                Collections.swap(matriz3D[2][0][2].getColorsList(), 0, 2);
                // pos 2
                Collections.swap(matriz3D[0][0][2].getColorsList(), 0, 2);
                //pos 0
                Collections.swap(matriz3D[0][0][0].getColorsList(), 1, 2);
                Collections.swap(matriz3D[0][0][0].getColorsList(), 0, 2);
            }
        } else {
            if (direccion.equals("up")) {
                // pos 8
                Collections.swap(matriz3D[0][2][2].getColorsList(), 1, 0);
                Collections.swap(matriz3D[0][2][2].getColorsList(), 1, 2);
                // pos 26
                Collections.swap(matriz3D[2][2][2].getColorsList(), 1, 2);
                // pos 24
                Collections.swap(matriz3D[2][2][0].getColorsList(), 0, 2);
                Collections.swap(matriz3D[2][2][0].getColorsList(), 1, 2);
                //pos 6
                Collections.swap(matriz3D[0][2][0].getColorsList(), 0, 2);
            } else {
                // pos 24
                Collections.swap(matriz3D[2][2][0].getColorsList(), 0, 2);
                // pos 26
                Collections.swap(matriz3D[2][2][2].getColorsList(), 0, 1);
                Collections.swap(matriz3D[2][2][2].getColorsList(), 1, 2);
                // pos 8
                Collections.swap(matriz3D[0][2][2].getColorsList(), 1, 2);
                //pos 6
                Collections.swap(matriz3D[0][2][0].getColorsList(), 0, 2);
                Collections.swap(matriz3D[0][2][0].getColorsList(), 1, 2);

            }
        }
    }

    // Metodo que hace cambios en las listas de colores al hacer giros horarios
    private void colorListSwapFrontales(int prof, String dir) {
        if (prof == 0) {
            if ("hora".equals(dir)) {
                //pos 0
                Collections.swap(matriz3D[0][0][0].getColorsList(), 0, 2);
                // pos 6
                Collections.swap(matriz3D[0][2][0].getColorsList(), 0, 1);
                Collections.swap(matriz3D[0][2][0].getColorsList(), 1, 2);
                // pos 24
                Collections.swap(matriz3D[2][2][0].getColorsList(), 1, 2);
                // pos 18
                Collections.swap(matriz3D[2][0][0].getColorsList(), 1, 2);
                Collections.swap(matriz3D[2][0][0].getColorsList(), 0, 1);
            } else {
                //pos 0
                Collections.swap(matriz3D[0][0][0].getColorsList(), 1, 2);
                Collections.swap(matriz3D[0][0][0].getColorsList(), 0, 1);
                // pos 6
                Collections.swap(matriz3D[0][2][0].getColorsList(), 1, 2);
                // pos 18
                Collections.swap(matriz3D[2][0][0].getColorsList(), 0, 2);
                // pos 24
                Collections.swap(matriz3D[2][2][0].getColorsList(), 1, 2);
                Collections.swap(matriz3D[2][2][0].getColorsList(), 0, 2);
            }
            //pos 3
            Collections.swap(matriz3D[0][1][0].getColorsList(), 0, 1);
            //pos 9
            Collections.swap(matriz3D[2][1][0].getColorsList(), 0, 1);
            //pos 15
            Collections.swap(matriz3D[1][0][0].getColorsList(), 0, 1);
            //pos 21
            Collections.swap(matriz3D[1][2][0].getColorsList(), 0, 1);
        } else {
            if ("hora".equals(dir)) {
                //pos 2
                Collections.swap(matriz3D[0][0][2].getColorsList(), 1, 2);
                // pos 8
                Collections.swap(matriz3D[0][2][2].getColorsList(), 0, 2);
                Collections.swap(matriz3D[0][2][2].getColorsList(), 1, 2);
                // pos 26
                Collections.swap(matriz3D[2][2][2].getColorsList(), 0, 2);
                // pos 20
                Collections.swap(matriz3D[2][0][2].getColorsList(), 0, 1);
                Collections.swap(matriz3D[2][0][2].getColorsList(), 1, 2);
            } else {
                // pos 20
                Collections.swap(matriz3D[2][0][2].getColorsList(), 1, 2);
                // pos 26
                Collections.swap(matriz3D[2][2][2].getColorsList(), 0, 1);
                Collections.swap(matriz3D[2][2][2].getColorsList(), 0, 2);
                // pos 8
                Collections.swap(matriz3D[0][2][2].getColorsList(), 0, 2);
                //pos 2
                Collections.swap(matriz3D[0][0][2].getColorsList(), 0, 1);
                Collections.swap(matriz3D[0][0][2].getColorsList(), 1, 2);
            }
            //pos 5
            Collections.swap(matriz3D[0][1][2].getColorsList(), 0, 1);
            //pos 11
            Collections.swap(matriz3D[1][0][2].getColorsList(), 0, 1);
            //pos 17
            Collections.swap(matriz3D[1][2][2].getColorsList(), 0, 1);
            //pos 23
            Collections.swap(matriz3D[2][1][2].getColorsList(), 0, 1);
        }
    }

    // Metodo que ejecuta giros dependiendo la cara que este en pantalla
    private void ejecutarAccion(int opcion, String direccion, float time) {

        if (opcion == -1) {
            opcion = comprobarCaraActual();
        }
        // giro derecha fila 0 por cara
        if (direccion.equals("right0")) {
            switch (opcion) {
                case 0, 1, 2, 3 -> {
                    rotarMatrizHorizontal(0, 'R');
                    animationRotate(face4, -90, 4, time);
                }
                case 40, 52 -> {
                    rotarMatrizGiro(2, 'H');
                    animationRotate(face2, 90, 2, time);
                }
                case 41, 53 -> {
                    rotarMatrizVertical(0, 'U');
                    animationRotate(face3, -90, 3, time);
                }
                case 42, 50 -> {
                    rotarMatrizGiro(0, 'A');
                    animationRotate(face0, -90, 0, time);
                }
                case 43, 51 -> {
                    rotarMatrizVertical(2, 'D');
                    animationRotate(face1, 90, 1, time);
                }

            }
        }
        // giro izquierda fila 0 por cara
        if (direccion.equals("left0")) {
            switch (opcion) {
                case 0, 1, 2, 3 -> {
                    rotarMatrizHorizontal(0, 'L');
                    animationRotate(face4, 90, 4, time);
                }
                case 40, 52 -> {
                    rotarMatrizGiro(2, 'A');
                    animationRotate(face2, -90, 2, time);
                }
                case 41, 53 -> {
                    rotarMatrizVertical(0, 'D');
                    animationRotate(face3, 90, 3, time);
                }
                case 42, 50 -> {
                    rotarMatrizGiro(0, 'H');
                    animationRotate(face0, 90, 0, time);
                }
                case 43, 51 -> {
                    rotarMatrizVertical(2, 'U');
                    animationRotate(face1, -90, 1, time);
                }

            }
        }
        // giro derecha fila 2 por cara
        if (direccion.equals("right2")) {
            switch (opcion) {
                case 0, 1, 2, 3 -> {
                    rotarMatrizHorizontal(2, 'R');
                    animationRotate(face5, -90, 5, time);
                }
                case 40, 52 -> {
                    rotarMatrizGiro(0, 'H');
                    animationRotate(face0, 90, 0, time);
                }
                case 41, 53 -> {
                    rotarMatrizVertical(2, 'U');
                    animationRotate(face1, -90, 1, time);
                }
                case 42, 50 -> {
                    rotarMatrizGiro(2, 'A');
                    animationRotate(face2, -90, 2, time);
                }
                case 43, 51 -> {
                    rotarMatrizVertical(0, 'D');
                    animationRotate(face3, 90, 3, time);
                }

            }
        }
        // giro izquierda fila 2 por cara
        if (direccion.equals("left2")) {
            switch (opcion) {
                case 0, 1, 2, 3 -> {
                    rotarMatrizHorizontal(2, 'L');
                    animationRotate(face5, 90, 5, time);
                }
                case 40, 52 -> {
                    rotarMatrizGiro(0, 'A');
                    animationRotate(face0, -90, 0, time);
                }
                case 41, 53 -> {
                    rotarMatrizVertical(2, 'D');
                    animationRotate(face1, 90, 1, time);
                }
                case 42, 50 -> {
                    rotarMatrizGiro(2, 'H');
                    animationRotate(face2, 90, 2, time);
                }
                case 43, 51 -> {
                    rotarMatrizVertical(0, 'U');
                    animationRotate(face3, -90, 3, time);
                }

            }
        }
        // giro arriba columna 0 por cara
        if (direccion.equals("up0")) {
            switch (opcion) {
                case 0, 40, 50 -> {
                    rotarMatrizVertical(0, 'U');
                    animationRotate(face3, -90, 3, time);
                }
                case 1, 41, 51 -> {
                    rotarMatrizGiro(0, 'A');
                    animationRotate(face0, -90, 0, time);
                }
                case 2, 42, 52 -> {
                    rotarMatrizVertical(2, 'D');
                    animationRotate(face1, 90, 1, time);
                }
                case 3, 43, 53 -> {
                    rotarMatrizGiro(2, 'H');
                    animationRotate(face2, 90, 2, time);
                }
            }
        }
        // giro abajo columna 0 por cara
        if (direccion.equals("down0")) {
            switch (opcion) {
                case 0, 40, 50 -> {
                    rotarMatrizVertical(0, 'D');
                    animationRotate(face3, 90, 3, time);
                }
                case 1, 41, 51 -> {
                    rotarMatrizGiro(0, 'H');
                    animationRotate(face0, 90, 0, time);
                }
                case 2, 42, 52 -> {
                    rotarMatrizVertical(2, 'U');
                    animationRotate(face1, -90, 1, time);
                }
                case 3, 43, 53 -> {
                    rotarMatrizGiro(2, 'A');
                    animationRotate(face2, -90, 2, time);
                }
            }
        }
        // giro arriba columna 2 por cara
        if (direccion.equals("up2")) {
            switch (opcion) {
                case 0, 40, 50 -> {
                    rotarMatrizVertical(2, 'U');
                    animationRotate(face1, -90, 1, time);
                }
                case 1, 41, 51 -> {
                    rotarMatrizGiro(2, 'A');
                    animationRotate(face2, -90, 2, time);
                }
                case 2, 42, 52 -> {
                    rotarMatrizVertical(0, 'D');
                    animationRotate(face3, 90, 3, time);
                }
                case 3, 43, 53 -> {
                    rotarMatrizGiro(0, 'H');
                    animationRotate(face0, 90, 0, time);
                }
            }
        }
        // giro abajo columna 2 por cara
        if (direccion.equals("down2")) {
            switch (opcion) {
                case 0, 40, 50 -> {
                    rotarMatrizVertical(2, 'D');
                    animationRotate(face1, 90, 1, time);
                }
                case 1, 41, 51 -> {
                    rotarMatrizGiro(2, 'H');
                    animationRotate(face2, 90, 2, time);
                }
                case 2, 42, 52 -> {
                    rotarMatrizVertical(0, 'U');
                    animationRotate(face3, -90, 3, time);
                }
                case 3, 43, 53 -> {
                    rotarMatrizGiro(0, 'A');
                    animationRotate(face0, -90, 0, time);
                }
            }
        }
        // giro horario por cara
        if (direccion.equals("hora")) {
            switch (opcion) {
                case 0 -> {
                    rotarMatrizGiro(0, 'H');
                    animationRotate(face0, 90, 0, time);
                }
                case 1 -> {
                    rotarMatrizVertical(2, 'U');
                    animationRotate(face1, -90, 1, time);
                }
                case 2 -> {
                    rotarMatrizGiro(2, 'A');
                    animationRotate(face2, -90, 2, time);
                }
                case 3 -> {
                    rotarMatrizVertical(0, 'D');
                    animationRotate(face3, 90, 3, time);
                }
                case 40, 41, 42, 43 -> {
                    rotarMatrizHorizontal(0, 'L');
                    animationRotate(face4, 90, 5, time);
                }
                case 50, 51, 52, 53 -> {
                    rotarMatrizHorizontal(2, 'R');
                    animationRotate(face5, -90, 5, time);
                }

            }
        }
        // giro antihorario por cara
        if (direccion.equals("antihora")) {
            switch (opcion) {
                case 0 -> {
                    rotarMatrizGiro(0, 'A');
                    animationRotate(face0, -90, 0, time);
                }
                case 1 -> {
                    rotarMatrizVertical(2, 'D');
                    animationRotate(face1, 90, 1, time);
                }
                case 2 -> {
                    rotarMatrizGiro(2, 'H');
                    animationRotate(face2, 90, 2, time);
                }
                case 3 -> {
                    rotarMatrizVertical(0, 'U');
                    animationRotate(face3, -90, 3, time);
                }
                case 40, 41, 42, 43 -> {
                    rotarMatrizHorizontal(0, 'R');
                    animationRotate(face4, -90, 5, time);
                }
                case 50, 51, 52, 53 -> {
                    rotarMatrizHorizontal(2, 'L');
                    animationRotate(face5, 90, 5, time);
                }

            }
        }
        if (!armar) {
            movimientos.add(Arrays.asList(Integer.toString(opcion), direccion)); //0,3,4
        }
        if (partidaIniciada) {
            contadorMovimientos++;
            imprimirPuntosMovimientos();
        }
        comprobarArmado();
//despues de hacer las respectivas rotaciones repinta el cubo3D
//        rellenarCubo();
    }

    // Metodo que devuelve la cara del cubo3D que este en pantalla
    private int comprobarCaraActual() {
        // cara 0 o frontal
        if ((anchorAngleY >= -45 && anchorAngleY <= 45) || anchorAngleY >= 315 || anchorAngleY <= -315) {
            if (anchorAngleX >= 45) {
                System.out.println("Cara blanca");
                return 40;
            } else if (anchorAngleX <= -45) {
                System.out.println("Cara amarilla");
                return 50;
            }
            System.out.println("Cara verde");
            return 0;
        }
        // cara 1 o derecha
        if ((anchorAngleY > 45 && anchorAngleY <= 135) || (anchorAngleY <= -225 && anchorAngleY > -315)) {
            if (anchorAngleX >= 45) {
                System.out.println("Cara blanca");
                return 41;
            } else if (anchorAngleX <= -45) {
                System.out.println("Cara amarilla");
                return 51;
            }
            System.out.println("Cara roja");
            return 1;
        }
        // cara 2 o trasera
        if ((anchorAngleY > 135 && anchorAngleY <= 225) || (anchorAngleY <= -135 && anchorAngleY > -225)) {
            if (anchorAngleX >= 45) {
                System.out.println("Cara blanca");
                return 42;
            } else if (anchorAngleX <= -45) {
                System.out.println("Cara amarilla");
                return 52;
            }
            System.out.println("Cara azul");
            return 2;
        }
        // cara 3 o izquierda
        if ((anchorAngleY > 225 && anchorAngleY <= 315) || (anchorAngleY <= -45 && anchorAngleY > -135)) {
            if (anchorAngleX >= 45) {
                System.out.println("Cara blanca");
                return 43;
            } else if (anchorAngleX <= -45) {
                System.out.println("Cara amarilla");
                return 53;
            }
            System.out.println("Cara naranja");
            return 3;
        }
        return 0;
    }

    // Rota las filas de la matriz izquierda-derecha
    private void rotarMatrizHorizontal(int fila, char opcion) {
        int n = 3;
        switch (opcion) {
            case 'R' -> {
                // rotar matriz giro derecha
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
            case 'L' -> {
                // rotar matriz giro izquierda
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
        }
    }

    // Rota las columnas de la matriz arriba-abajo
    private void rotarMatrizVertical(int columna, char opcion) {
        int n = 3;
        switch (opcion) {

            case 'D' -> {
                // rotar matriz giro abajo
                for (int i = 0; i < n / 2; i++) {
                    for (int j = i; j < n - i - 1; j++) {
                        Cubito temp = matriz3D[i][columna][j];
                        matriz3D[i][columna][j] = matriz3D[j][columna][n - i - 1];
                        matriz3D[j][columna][n - i - 1] = matriz3D[n - i - 1][columna][n - j - 1];
                        matriz3D[n - i - 1][columna][n - j - 1] = matriz3D[n - j - 1][columna][i];
                        matriz3D[n - j - 1][columna][i] = temp;
                    }
                }
                colorListSwapLaterales(columna, "");
            }
            case 'U' -> {
                // rotar matriz giro arriba
                for (int i = 0; i < n / 2; i++) {
                    for (int j = i; j < n - i - 1; j++) {
                        Cubito temp = matriz3D[i][columna][j];
                        matriz3D[i][columna][j] = matriz3D[n - j - 1][columna][i];
                        matriz3D[n - j - 1][columna][i] = matriz3D[n - i - 1][columna][n - j - 1];
                        matriz3D[n - i - 1][columna][n - j - 1] = matriz3D[j][columna][n - i - 1];
                        matriz3D[j][columna][n - i - 1] = temp;
                    }
                }
                colorListSwapLaterales(columna, "up");
            }
        }
    }

    // Rota las caras de la matriz horario-antihorario
    private void rotarMatrizGiro(int prof, char opcion) {
        int n = 3;
        switch (opcion) {

            case 'A' -> {
                // rotar matriz sentido antihorario
                for (int i = 0; i < n / 2; i++) {
                    for (int j = i; j < n - i - 1; j++) {
                        Cubito temp = matriz3D[i][j][prof];
                        matriz3D[i][j][prof] = matriz3D[j][n - i - 1][prof];
                        matriz3D[j][n - i - 1][prof] = matriz3D[n - i - 1][n - j - 1][prof];
                        matriz3D[n - i - 1][n - j - 1][prof] = matriz3D[n - j - 1][i][prof];
                        matriz3D[n - j - 1][i][prof] = temp;
                    }
                }
                colorListSwapFrontales(prof, "");
            }
            case 'H' -> {
                // rotar matriz sentido horario
                for (int i = 0; i < n / 2; i++) {
                    for (int j = i; j < n - i - 1; j++) {
                        Cubito temp = matriz3D[i][j][prof];
                        matriz3D[i][j][prof] = matriz3D[n - j - 1][i][prof];
                        matriz3D[n - j - 1][i][prof] = matriz3D[n - i - 1][n - j - 1][prof];
                        matriz3D[n - i - 1][n - j - 1][prof] = matriz3D[j][n - i - 1][prof];
                        matriz3D[j][n - i - 1][prof] = temp;
                    }
                }
                colorListSwapFrontales(prof, "hora");
            }
        }
    }

    // Metodo que cambia los punteros del Mouse
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
            manejoBotones(true);
        });

        group.setOnMouseReleased(event -> {
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
            group.setCursor(Cursor.OPEN_HAND);
            manejoBotones(false);
        });
    }

    private void armarCubo() {
        contadorArmar = movimientos.size() - 1;
        int cara = Integer.parseInt(movimientos.get(contadorArmar).get(0));
        String direccion = movimientos.get(contadorArmar).get(1);
        armar = true;

        if (direccion.equals("right0")) {
            ejecutarAccion(cara, "left0", (float) 0.5);
        }
        if (direccion.equals("right2")) {
            ejecutarAccion(cara, "left2", (float) 0.5);
        }
        if (direccion.equals("left0")) {
            ejecutarAccion(cara, "right0", (float) 0.5);
        }
        if (direccion.equals("left2")) {
            ejecutarAccion(cara, "right2", (float) 0.5);
        }
        if (direccion.equals("up0")) {
            ejecutarAccion(cara, "down0", (float) 0.5);
        }
        if (direccion.equals("up2")) {
            ejecutarAccion(cara, "down2", (float) 0.5);
        }
        if (direccion.equals("down0")) {
            ejecutarAccion(cara, "up0", (float) 0.5);
        }
        if (direccion.equals("down2")) {
            ejecutarAccion(cara, "up2", (float) 0.5);
        }
        if (direccion.equals("hora")) {
            ejecutarAccion(cara, "antihora", (float) 0.5);
        }
        if (direccion.equals("antihora")) {
            ejecutarAccion(cara, "hora", (float) 0.5);
        }

        System.out.println(movimientos.get(contadorArmar));
        System.out.println(movimientos.size());

        movimientos.remove(contadorArmar);
    }

    // Variables para el metodo de armar cubo
    int aux1;
    int aux2 = 0;
    int anterior = 0;

    private void desarmarAleatorio() {
        int[] caras = {0, 1, 2, 3, 40, 41, 42, 43, 50, 51, 52, 53};
        String[] direcciones = {"right0", "left0", "right2", "left2", "up0", "down0", "up2", "down2", "hora", "antihora"};

        if (anterior % 2 == 0) {
            anterior++;
        } else {
            anterior--;
        }
        // Ciclo para que no haga un moviento inverso al anterior moviento
        do {
            aux1 = rand.nextInt(caras.length);
            aux2 = rand.nextInt(direcciones.length);
        } while (aux2 == anterior);

        anterior = aux2;
        ejecutarAccion(caras[aux1], direcciones[aux2], (float) 0.5);
    }

    // Metodo para rotacion animada
    private void animationRotate(int[] vector, int rotation, int cara, float time) {

        Group auxGroupGiro = new Group();

        for (int i = 0; i < vector.length; i++) {
            principalGroup.getChildren().remove(loadCubesFaces3D.get(vector[i]));
            auxGroupGiro.getChildren().add(loadCubesFaces3D.get(vector[i]));
        }
        principalGroup.getChildren().add(auxGroupGiro);

        // Crear un evento de animacion rotación
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(time), auxGroupGiro);

        switch (cara) {
            case 0 -> {
                rotateTransition.setAxis(Rotate.Z_AXIS);
            }
            case 1 -> {
                rotateTransition.setAxis(Rotate.X_AXIS);
            }
            case 2 -> {
                rotateTransition.setAxis(Rotate.Z_AXIS);
            }
            case 3 -> {
                rotateTransition.setAxis(Rotate.X_AXIS);
            }
            case 4 -> {
                rotateTransition.setAxis(Rotate.Y_AXIS);
            }
            case 5 -> {
                rotateTransition.setAxis(Rotate.Y_AXIS);
            }
        }

        rotateTransition.setByAngle(rotation); // Grados de rotación

        // Configurar el ciclo de animación
        rotateTransition.setCycleCount(1);

        // EventHandler para ejecutar una tarea cuando la animación termine
        rotateTransition.setOnFinished((ActionEvent event) -> {
            rellenarCubo();
            manejoBotones(Boolean.FALSE);
            if (contadorDesarmar <= movimientosAleatorios && desarmar) {
                contadorDesarmar++;
                desarmarAleatorio();
            }

            if (contadorArmar > 0 && armar) {
                armarCubo();
            }

        });

        // Iniciar la animación
        rotateTransition.play();
        manejoBotones(Boolean.TRUE);
    }

    // Activar o desactivar botones
    private void manejoBotones(Boolean active) {
        btnAbajoColumna1.setDisable(active);
        btnDerechaFila1.setDisable(active);
        btnIzquierdaFila1.setDisable(active);
        btnIzquierdaFila3.setDisable(active);
        btnDerechaFila3.setDisable(active);
        btnArribaColumna1.setDisable(active);
        btnArribaColumna3.setDisable(active);
        btnAbajoColumna1.setDisable(active);
        btnAbajoColumna3.setDisable(active);
        btnRotarReloj.setDisable(active);
        btnRotarAntireloj.setDisable(active);
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
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
        });

        scene.setOnMouseDragged((var event) -> {
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
        });

        scene.setOnMouseReleased(event -> {
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
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

    // Metodo que agrega líneas que representan los ejes X, Y y Z
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
}
