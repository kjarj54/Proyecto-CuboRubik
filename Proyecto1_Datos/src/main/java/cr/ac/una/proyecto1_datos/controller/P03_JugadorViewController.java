package cr.ac.una.proyecto1_datos.controller;

import cr.ac.una.proyecto1_datos.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * FXML Controller class
 *
 * @author Luvara
 */
public class P03_JugadorViewController extends Controller implements Initializable {

    @FXML
    private MFXButton btnIniciar;
    @FXML
    private ImageView imvPlayButton;
    @FXML
    private MediaView mdvFondoJugador;
    @FXML
    private ToggleGroup tggModoJugo;
    @FXML
    private AnchorPane root;
    @FXML
    private MFXRadioButton rdbAutomatico;
    @FXML
    private MFXRadioButton rdbManual;
    @FXML
    private MFXTextField txfNombre = new MFXTextField();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        rdbAutomatico.setUserData("Automatico");
        rdbManual.setUserData("Manual");
        onActionMouse();
        loadVideo();
    }

    @Override
    public void initialize() {
    }

    @FXML
    private void onActionBtnIniciar(ActionEvent event) {
        soundEnter();
        FlowController.getInstance().goView("A01_PruebasView");

    }

    private void loadVideo() {
        String videoFile = new File("src/main/resources/cr/ac/una/proyecto1_datos/resources/media/seleccJugador.mp4").getAbsolutePath();

        Media media = new Media(new File(videoFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        mdvFondoJugador.setMediaPlayer(mediaPlayer);

        // Configura el ciclo infinito
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        // Vincula el tamaño del MediaView al tamaño de la escena
        mdvFondoJugador.fitWidthProperty().bind(root.widthProperty());
        mdvFondoJugador.fitHeightProperty().bind(root.heightProperty());
        mdvFondoJugador.setPreserveRatio(false);
        mediaPlayer.setAutoPlay(true);
    }

    private void onActionMouse() {

        btnIniciar.setOnMouseEntered(event -> {
            String imageFile = new File("src/main/resources/cr/ac/una/proyecto1_datos/resources/media/icons/playButton.gif").getAbsolutePath();
            Image image = new Image(new File(imageFile).toURI().toString());
            imvPlayButton.setImage(image);
            mouseSound();
        });

        btnIniciar.setOnMouseExited(event -> {
            if (!event.isPrimaryButtonDown()) {
                String imageFile = new File("src/main/resources/cr/ac/una/proyecto1_datos/resources/media/icons/playButton.png").getAbsolutePath();
                Image image = new Image(new File(imageFile).toURI().toString());
                imvPlayButton.setImage(image);
            }
        });

        rdbAutomatico.setOnMouseEntered(event -> {
            mouseSound();
        });
        rdbManual.setOnMouseEntered(event -> {
            mouseSound();
        });

        Stage stage1 = FlowController.getInstance().getMainStage();
        stage1.setOnShown(event -> {
            // Luego de que la escena se muestre, solicitar el enfoque a otro nodo
            rdbAutomatico.requestFocus();
        });
    }

    private void mouseSound() {
        try {
            File soundFile = new File("src/main/resources/cr/ac/una/proyecto1_datos/resources/media/hoverMouse.wav");

            Clip sound = AudioSystem.getClip();
            sound.open(AudioSystem.getAudioInputStream(soundFile));
            sound.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
        }
    }
    
    public void soundEnter() {
        try {
            File soundFile = new File("src/main/resources/cr/ac/una/proyecto1_datos/resources/media/pressButton.wav");

            Clip sound = AudioSystem.getClip();
            sound.open(AudioSystem.getAudioInputStream(soundFile));
            sound.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
        }
    }
}
