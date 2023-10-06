package cr.ac.una.proyecto1_datos.controller;

import cr.ac.una.proyecto1_datos.model.Jugador;
import cr.ac.una.proyecto1_datos.util.FlowController;
import static cr.ac.una.proyecto1_datos.util.ManejoDatos.leerRecords;
import cr.ac.una.proyecto1_datos.util.SoundUtil;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.File;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML
    private TableView<Jugador> tbvMejoresTiempos;

    MediaPlayer mediaPlayer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadVideo();
        onMouseEntered();
        ObservableList<Jugador> jugadores = FXCollections.observableArrayList(leerRecords().stream()
                .sorted(Comparator.comparing(Jugador::getTime)) // Ordena por el campo "time"
                .collect(Collectors.toList()));
        // Asocia la lista de jugadores a la TableView
        tbvMejoresTiempos.setItems(jugadores);

        // Crea las columnas y asocia sus propiedades
        TableColumn<Jugador, String> nameColumn = new TableColumn<>("Nombre");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Jugador, Integer> timeColumn = new TableColumn<>("Tiempo");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<Jugador, Integer> movesColumn = new TableColumn<>("Movimientos");
        movesColumn.setCellValueFactory(new PropertyValueFactory<>("moves"));

        TableColumn<Jugador, Integer> pointsColumn = new TableColumn<>("Puntos");
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));

        // Agrega las columnas a la TableView
        tbvMejoresTiempos.getColumns().addAll(nameColumn, timeColumn, movesColumn, pointsColumn);
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
        FlowController.getInstance().goView("P03_NuevaPartidaView");
    }

    @FXML
    private void onActionBtnContinuarPartida(ActionEvent event) {
        SoundUtil.mouseEnterSound();
        mediaPlayer.setCycleCount(0);
        mediaPlayer.stop();
        FlowController.getInstance().goView("P04_ContinuarPartidaView");
    }

    @FXML
    private void onActionBtnAcercaDe(ActionEvent event) {
        SoundUtil.mouseEnterSound();
        FlowController.getInstance().goViewInWindowModal("P07_AcercaDeView", stage, Boolean.FALSE);
    }

    @FXML
    private void onActionBtnSalir(ActionEvent event) {
        SoundUtil.mouseEnterSound();
        FlowController.getInstance().salir();
    }

    private void cargarMejoresTiempos() {

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
