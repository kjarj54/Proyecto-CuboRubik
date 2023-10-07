package cr.ac.una.proyecto1_datos.controller;

import cr.ac.una.proyecto1_datos.model.Jugador;
import cr.ac.una.proyecto1_datos.util.AppContext;
import cr.ac.una.proyecto1_datos.util.FlowController;
import cr.ac.una.proyecto1_datos.util.ManejoDatos;
import cr.ac.una.proyecto1_datos.util.Mensaje;
import cr.ac.una.proyecto1_datos.util.SoundUtil;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author Luvara
 */
public class P04_ContinuarPartidaViewController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private MediaView mdvCargarPartida;
    @FXML
    private MFXButton btnSalir;
    @FXML
    private MFXTextField txfNombre;
    @FXML
    private MFXButton btnCargarPartida;
    
    MediaPlayer mediaPlayer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        onActionMouse();
        loadVideo();
    }

    @Override
    public void initialize() {
        loadVideo();
    }

    @FXML
    private void onActionBtnIniciar(ActionEvent event) {
        SoundUtil.mouseEnterSound();
        if (txfNombre.getText().isBlank() || txfNombre.getText().isEmpty()) {
            SoundUtil.errorSound();
            new Mensaje().showModal(Alert.AlertType.ERROR, "Ingresar Jugador", getStage(), "Es necesario digitar un nombre para cargar partida");
        } else if (ManejoDatos.buscarJugadorPorNombre(txfNombre.getText()) == null) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error cargando partida", getStage(), "No hay partidas guardadas con el nombre");
        } else {
            mediaPlayer.setCycleCount(0);
            mediaPlayer.stop();
            Jugador jugador = ManejoDatos.buscarJugadorPorNombre(txfNombre.getText());
            AppContext.getInstance().set("Jugador", jugador);
            AppContext.getInstance().set("Pantalla", "continuar");
            FlowController.getInstance().goView("P06_MesaJuegoView");
        }
    }

    @FXML
    private void onActionBtnSalir(ActionEvent event) {
        FlowController.getInstance().goView("P02_MenuView");
    }

    private void onActionMouse() {

        btnSalir.setOnMouseEntered(event -> {
            SoundUtil.mouseHoverSound();
        });
        btnCargarPartida.setOnMouseEntered(event -> {
            SoundUtil.mouseHoverSound();
        });
        txfNombre.setOnKeyReleased(event -> {
            SoundUtil.keyTyping();
        });

    }

    private void loadVideo() {
        String videoFile = new File("src/main/resources/cr/ac/una/proyecto1_datos/resources/media/cargarPartidaVideo.mp4").getAbsolutePath();

        Media media = new Media(new File(videoFile).toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        mdvCargarPartida.setMediaPlayer(mediaPlayer);

        // Configura el ciclo infinito
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        // Vincula el tamaño del MediaView al tamaño de la escena
        mdvCargarPartida.fitWidthProperty().bind(root.widthProperty());
        mdvCargarPartida.fitHeightProperty().bind(root.heightProperty());
        mdvCargarPartida.setPreserveRatio(false);
        mediaPlayer.setAutoPlay(true);
    }
}
