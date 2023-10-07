package cr.ac.una.proyecto1_datos.controller;

import cr.ac.una.proyecto1_datos.model.Jugador;
import cr.ac.una.proyecto1_datos.util.AppContext;
import cr.ac.una.proyecto1_datos.util.FlowController;
import cr.ac.una.proyecto1_datos.util.Mensaje;
import cr.ac.una.proyecto1_datos.util.SoundUtil;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 * FXML Controller class
 *
 * @author Luvara
 */
public class P03_NuevaPartidaViewController extends Controller implements Initializable {

    @FXML
    private MFXButton btnIniciar;
    @FXML
    private ImageView imvPlayButton;
    @FXML
    private MediaView mdvFondoJugador;
    @FXML
    private AnchorPane root;
    @FXML
    private MFXRadioButton rdbAutomatico;
    @FXML
    private MFXRadioButton rdbManual;
    @FXML
    private MFXTextField txfNombre;
    @FXML
    private MFXButton btnSalir;
    @FXML
    private ToggleGroup tggModoJuego;

    Jugador jugador;

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

        rdbAutomatico.setUserData("Automatico");
        rdbManual.setUserData("Manual");
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
        if (txfNombre.getText().isBlank()) {
            SoundUtil.errorSound();
            new Mensaje().showModal(Alert.AlertType.ERROR, "Ingresar Jugador", getStage(), "Es necesario digitar un nombre para continuar");
        } else {
            jugador = new Jugador(txfNombre.getText(), (String) tggModoJuego.getSelectedToggle().getUserData(), 0, 0, 0, new Stack<>());
            AppContext.getInstance().set("Jugador", jugador);
            FlowController.getInstance().delete("P03_NuevaPartidaViewController");
            if (tggModoJuego.getSelectedToggle().getUserData().equals("Automatico")) {
                FlowController.getInstance().goView("P06_MesaJuegoView");
                txfNombre.clear();
            } else {
                FlowController.getInstance().goView("P05_ModoManualView");
                txfNombre.clear();
            }
        }
    }

    @FXML
    private void onActionBtnSalir(ActionEvent event) {
        SoundUtil.mouseEnterSound();
        FlowController.getInstance().delete("P03_NuevaPartidaViewController");
        FlowController.getInstance().goView("P02_MenuView");
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
            SoundUtil.mouseHoverSound();
        });

        btnIniciar.setOnMouseExited(event -> {
            if (!event.isPrimaryButtonDown()) {
                String imageFile = new File("src/main/resources/cr/ac/una/proyecto1_datos/resources/media/icons/playButton.png").getAbsolutePath();
                Image image = new Image(new File(imageFile).toURI().toString());
                imvPlayButton.setImage(image);
            }
        });

        btnSalir.setOnMouseEntered(event -> {
            SoundUtil.mouseHoverSound();
        });

        txfNombre.setOnKeyReleased(event -> {
            SoundUtil.keyTyping();
        });

        rdbAutomatico.setOnMouseEntered(event -> {
            SoundUtil.mouseHoverSound();
        });
        rdbManual.setOnMouseEntered(event -> {
            SoundUtil.mouseHoverSound();
        });
    }

}
