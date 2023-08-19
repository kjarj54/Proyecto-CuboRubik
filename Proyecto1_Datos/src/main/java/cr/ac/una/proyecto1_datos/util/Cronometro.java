/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyecto1_datos.util;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 *
 * @author ANTHONY
 */
public class Cronometro {

    private int time;
    private Timeline timeline;

    public Cronometro() {
        this.time = 0;
    }

    public void startCronometro() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            time++;
            System.out.println(time + " Segundos");
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void stopCronometro() {
        timeline.stop();
    }
}
