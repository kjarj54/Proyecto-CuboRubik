package cr.ac.una.proyecto1_datos.controller;

import cr.ac.una.proyecto1_datos.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadVideo();
        onActionMouse();
    }

    @Override
    public void initialize() {

    }

    @FXML
    private void onActionBtnNuevaPartida(ActionEvent event) {
        mouseEnterSound();
        FlowController.getInstance().goView("P03_JugadorView");
    }

    @FXML
    private void onActionBtnContinuarPartida(ActionEvent event) {
        mouseEnterSound();
    }

    @FXML
    private void onActionBtnAcercaDe(ActionEvent event) {
        mouseEnterSound();
    }

    @FXML
    private void onActionBtnSalir(ActionEvent event) {
        mouseEnterSound();
        FlowController.getInstance().salir();
    }

    private void loadVideo() {
        String videoFile = new File("src/main/resources/cr/ac/una/proyecto1_datos/resources/media/menuVideo.mp4").getAbsolutePath();

        Media media = new Media(new File(videoFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        mdvMenu.setMediaPlayer(mediaPlayer);

        // Configura el ciclo infinito
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        // Vincula el tamaño del MediaView al tamaño de la escena
        mdvMenu.fitWidthProperty().bind(root.widthProperty());
        mdvMenu.fitHeightProperty().bind(root.heightProperty());
        mdvMenu.setPreserveRatio(false);
        mediaPlayer.setAutoPlay(true);
    }

    private void onActionMouse() {
        btnNuevaPartida.setOnMouseEntered(event -> {
            mouseHoverSound();
        });
        btnContinuarPartida.setOnMouseEntered(event -> {
            mouseHoverSound();
        });
        btnAcercaDe.setOnMouseEntered(event -> {
            mouseHoverSound();
        });
        btnSalir.setOnMouseEntered(event -> {
            mouseHoverSound();
        });
    }

    private void mouseHoverSound() {
        try {
            File soundFile = new File("src/main/resources/cr/ac/una/proyecto1_datos/resources/media/hoverMouse.wav");

            Clip sound = AudioSystem.getClip();
            sound.open(AudioSystem.getAudioInputStream(soundFile));
            sound.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
        }
    }

    private void mouseEnterSound() {
        try {
            File soundFile = new File("src/main/resources/cr/ac/una/proyecto1_datos/resources/media/enterSound.wav");

            Clip sound = AudioSystem.getClip();
            sound.open(AudioSystem.getAudioInputStream(soundFile));
            sound.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
        }
    }

}
