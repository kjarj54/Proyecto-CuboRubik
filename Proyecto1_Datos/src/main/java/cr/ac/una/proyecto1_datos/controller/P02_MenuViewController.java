package cr.ac.una.proyecto1_datos.controller;

import cr.ac.una.proyecto1_datos.util.FlowController;
import cr.ac.una.proyecto1_datos.util.SoundUtil;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 * FXML Controller class
 *
 * @author Luvara
 */
public class P02_MenuViewController extends Controller implements Initializable {

    @FXML
    private MFXButton btnNuevaPartida;
    @FXML
    private MFXButton btnContinuarPartida;
    @FXML
    private MFXButton btnAcercaDe;
    @FXML
    private MFXButton btnSalir;
    @FXML
    private MediaView mdvMenu;
    @FXML
    private AnchorPane root;

    MediaPlayer mediaPlayer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadVideo();
        onMouseEntered();
    }

    @Override
    public void initialize() {
        loadVideo();
    }

    @FXML
    private void onActionBtnNuevaPartida(ActionEvent event) {
        SoundUtil.mouseEnterSound();
        mediaPlayer.setCycleCount(0);
        mediaPlayer.stop();
        FlowController.getInstance().goView("P03_RegistroJugadorView");
    }

    @FXML
    private void onActionBtnContinuarPartida(ActionEvent event) {
        mediaPlayer.setCycleCount(0);
        mediaPlayer.stop();
        SoundUtil.mouseEnterSound();
    }

    @FXML
    private void onActionBtnAcercaDe(ActionEvent event) {
        SoundUtil.mouseEnterSound();
    }

    @FXML
    private void onActionBtnSalir(ActionEvent event) {
        SoundUtil.mouseEnterSound();
        FlowController.getInstance().salir();
    }

    private void loadVideo() {
        String videoFile = new File("src/main/resources/cr/ac/una/proyecto1_datos/resources/media/menuVideo.mp4").getAbsolutePath();

        Media media = new Media(new File(videoFile).toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        mdvMenu.setMediaPlayer(mediaPlayer);

        // Configura el ciclo infinito
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        // Vincula el tamaño del MediaView al tamaño de la escena
        mdvMenu.fitWidthProperty().bind(root.widthProperty());
        mdvMenu.fitHeightProperty().bind(root.heightProperty());
        mdvMenu.setPreserveRatio(false);
        mediaPlayer.setAutoPlay(true);
    }

    private void onMouseEntered() {
        btnNuevaPartida.setOnMouseEntered(event -> {
            SoundUtil.mouseHoverSound();
        });
        btnContinuarPartida.setOnMouseEntered(event -> {
            SoundUtil.mouseHoverSound();
        });
        btnAcercaDe.setOnMouseEntered(event -> {
            SoundUtil.mouseHoverSound();
        });
        btnSalir.setOnMouseEntered(event -> {
            SoundUtil.mouseHoverSound();
        });
    }

}
