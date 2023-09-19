package cr.ac.una.proyecto1_datos.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.ImageIcon;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        onActionMouse();
    }

    @Override
    public void initialize() {
    }

    @FXML
    private void onActionBtnIniciar(ActionEvent event) {
    }

    private void onActionMouse() {

        btnIniciar.setOnMouseEntered(event -> {
            String imageFile = new File("src/main/resources/cr/ac/una/proyecto1_datos/resources/media/icons/playButton.gif").getAbsolutePath();
            Image image = new Image(new File(imageFile).toURI().toString());
            imvPlayButton.setImage(image);
        });

        btnIniciar.setOnMouseExited(event -> {
            if (!event.isPrimaryButtonDown()) {
                String imageFile = new File("src/main/resources/cr/ac/una/proyecto1_datos/resources/media/icons/playButton.png").getAbsolutePath();
                Image image = new Image(new File(imageFile).toURI().toString());
                imvPlayButton.setImage(image);
            }
        });
    }
}
