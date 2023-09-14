package cr.ac.una.proyecto1_datos;

import cr.ac.una.proyecto1_datos.util.FlowController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FlowController.getInstance().InitializeFlow(stage, null);
        //stage.getIcons().add(new Image(""));
        stage.setTitle("Rubik");
        FlowController.getInstance().goMain();
    }

    public static void main(String[] args) {
        launch();
    }
    
}