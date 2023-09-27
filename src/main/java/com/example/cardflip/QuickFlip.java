package com.example.cardflip;

/*********
 * Viser et spillekort
 * Når man trykker på kortet vendes kortet med en serie af 2D-transitioner
 * EK sep. 2023
 **********/

import javafx.animation.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class QuickFlip extends Application {

    private boolean viserForside = true;   // Vælg hvilken side kortet vises med til start

    public void start(Stage stage) throws Exception {

        // Indlæsning af for- og bagsidebilleder
        ImageView forside = new ImageView(new Image(getClass().getResource("kortfront.png").toString()));
        ImageView bagside = new ImageView(new Image(getClass().getResource("kortbagside.png").toString()));

        // Vend kortet fra forside til bagside med transitioner.
        // Forside skaleres fra fuld visning (1) til ingen (0) og bagside fra 0 til 1 på x-aksen.
        // De to transitioner sættes til at udføres i sekvens.
        ScaleTransition gemForside = new ScaleTransition(Duration.millis(500), forside);
        gemForside.setFromX(1);
        gemForside.setToX(0);
        gemForside.setInterpolator(Interpolator.EASE_BOTH);

        bagside.setScaleX(0);
        ScaleTransition visBagside = new ScaleTransition(Duration.millis(500), bagside);
        visBagside.setFromX(0);
        visBagside.setToX(1);
        visBagside.setInterpolator(Interpolator.EASE_BOTH);


        SequentialTransition vendForside = new SequentialTransition(gemForside, visBagside);

        // Vend kortet fra bagside til forside: lav transition fra bagside til forside
        ScaleTransition gemBagside = new ScaleTransition(Duration.millis(500), bagside);
        gemBagside.setFromX(1);
        gemBagside.setToX(0);
        gemBagside.setInterpolator(Interpolator.EASE_BOTH);

        forside.setScaleX(0);
        ScaleTransition visForside = new ScaleTransition(Duration.millis(500), forside);
        visForside.setFromX(0);
        visForside.setToX(1);
        visForside.setInterpolator(Interpolator.EASE_BOTH);

        SequentialTransition vendBagside = new SequentialTransition(gemBagside, visForside);

        // Vis kortet i normal skalering før vi starter
        bagside.setScaleX(1);
        forside.setScaleX(1);

        // Lav museevent
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                // Check hvilken vej kortet skal vendes og udfør transition
                if (viserForside) {
                    vendForside.play();
                    viserForside = false;
                } else {
                    vendBagside.play();
                    viserForside = true;
                }
            }
        };
        // Tilknyt museeventet til de to billeder af kortet
        forside.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        bagside.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);

        // Sæt vinduet og sæt kortets forside og bagside afhængigt af hvilken vej kortet vender ved start
        StackPane root = new StackPane();
        if (viserForside)
            root.getChildren().addAll(bagside, forside);
        else
            root.getChildren().addAll(forside, bagside);

        Scene scene = new Scene(root, 600, 600);
        stage.setScene(scene);

        stage.show();

    }
}