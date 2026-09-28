package com.example.cardflip;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/*
Spillekort der kan vendes med museklik
EK Sep 2026
 */

public class Spillekort extends Group {

    private ImageView forside = new ImageView(new Image(getClass().getResource("kortfront.png").toString()));
    private ImageView bagside = new ImageView(new Image(getClass().getResource("kortbagside.png").toString()));
    private boolean viserForside;   // Vælg hvilken side kortet vises med til start
    private SequentialTransition vendForside, vendBagside;

    public Spillekort(boolean billedeside) {
        viserForside = billedeside;

        // ------------------
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

        vendForside = new SequentialTransition(gemForside, visBagside);

        // ------------------
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

        vendBagside = new SequentialTransition(gemBagside, visForside);

        // ------------------
        // Vis kortet i normal skalering før vi starter
        bagside.setScaleX(1);
        forside.setScaleX(1);

        // Sæt om kortet første gang skal vises med billedsiden opaf eller nedaf.
        if (viserForside)
            getChildren().addAll(bagside, forside);
        else
            getChildren().addAll(forside, bagside);

    }

    // Når kortet bliver bedt om at vende, så afspilles vendetransitionen afhængigt af hvilken side af kortet,
    // der vender opad.
    public void vend() {
        if (viserForside) {
            vendForside.play();
            viserForside = false;
        } else {
            vendBagside.play();
            viserForside = true;
        }
    }


}
