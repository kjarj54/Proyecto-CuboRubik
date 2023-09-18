package cr.ac.una.proyecto1_datos.controller;

import cr.ac.una.proyecto1_datos.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * FXML Controller class
 *
 * @author Luvara
 */
public class P01_PrincipalViewController extends Controller implements Initializable {

    @FXML
    private AnchorPane acPanePrincipal;
    @FXML
    private MediaView mdvVideoIntro;
    @FXML
    private MFXButton btnIngresar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnIngresar.setVisible(false);
        loadVideo();
    }

    @Override
    public void initialize() {
    }

    @FXML
    private void onActionBtnIngresar(ActionEvent event) {
        soundEnter();
        FlowController.getInstance().goView("P02_MenuView");
        
    }

    private void loadVideo() {
        String videoFile = new File("src/main/resources/cr/ac/una/proyecto1_datos/resources/media/IntroPrincipalView.mp4").getAbsolutePath();

        Media media = new Media(new File(videoFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        mdvVideoIntro.setMediaPlayer(mediaPlayer);

        // Configura el ciclo infinito
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        // Vincula el tamaño del MediaView al tamaño de la escena
        mdvVideoIntro.fitWidthProperty().bind(acPanePrincipal.widthProperty());
        mdvVideoIntro.fitHeightProperty().bind(acPanePrincipal.heightProperty());
        mdvVideoIntro.setPreserveRatio(false);
        mediaPlayer.setAutoPlay(true);

        // Crear un temporizador
        Timer timer = new Timer();

        // Definir la tarea que se ejecutará después de unos segundos
        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                // Coloca aquí la acción que deseas ejecutar después de unos segundos
                btnIngresar.setVisible(true);
            }
        };

        // Programar la tarea para que se ejecute después de 5000 milisegundos (5 segundos)
        timer.schedule(tarea, 5000);
    }

    public void soundEnter() {
        try {
            File soundFile = new File("src/main/resources/cr/ac/una/proyecto1_datos/resources/media/pressButton.wav");

            Clip sound = AudioSystem.getClip();
            sound.open(AudioSystem.getAudioInputStream(soundFile));
            sound.start();
        } catch (Exception ex) {

        }
    }

}
