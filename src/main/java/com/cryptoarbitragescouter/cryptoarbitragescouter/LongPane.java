package com.cryptoarbitragescouter.cryptoarbitragescouter;

import javafx.scene.layout.Pane;

public class LongPane extends Pane {
    public Pane display () {
        Pane longPane = new Pane();
        longPane.setId("longPane");
        longPane.setPrefSize(1040, 50);
        return longPane;
    }
}
