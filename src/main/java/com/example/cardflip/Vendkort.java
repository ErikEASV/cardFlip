package com.example.cardflip;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

// VENDKORT viser et spillekort, som man ved at klikke på kan vende om.
// Version 2 med selvstændig klasse til spillekortet.

public class Vendkort extends Application {

    public void start(Stage stage) {

        // Definér nyt kort og sæt event på
        Spillekort kort = new Spillekort(false);
        kort.setOnMouseClicked(event -> kort.vend());
        kort.setLayoutX(100);
        kort.setLayoutY(100);

        // Sæt verden op
        Pane root = new Pane();
        root.getChildren().add(kort);

        Scene scene = new Scene(root, 600, 600);
        stage.setScene(scene);

        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
