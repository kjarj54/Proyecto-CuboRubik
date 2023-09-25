/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyecto1_datos.util;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 *
 * @author ANTHONY
 */
public class Cronometro {

    private int time;
    private Timeline timeline;
    private Label label;

    public Cronometro(Label label) {
        this.time = 0;
        this.label = label;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
        updateLabel();
    }

    public void startCronometro() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), (var event) -> {
            time++;
            updateLabel(); // Llamamos al método para actualizar el Label
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void stopCronometro() {
        timeline.stop();
        label.setText("Tiempo: " + time);
    }

    // Método para actualizar el Label con el tiempo actual
    private void updateLabel() {
        int minutes = time / 60;
        int seconds = time % 60;

        String minutesStr = String.format("%02d", minutes); // Formatea los minutos con dos dígitos
        String secondsStr = String.format("%02d", seconds); // Formatea los segundos con dos dígitos

        label.setText("Tiempo: " + minutesStr + ":" + secondsStr + ".");
    }
}
