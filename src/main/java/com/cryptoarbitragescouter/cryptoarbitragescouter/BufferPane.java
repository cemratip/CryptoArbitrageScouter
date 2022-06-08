package com.cryptoarbitragescouter.cryptoarbitragescouter;

import javafx.scene.layout.Pane;

public class BufferPane extends Pane{
    public Pane display() {
        Pane bufferPane = new Pane();
        bufferPane.setId("bufferPane");
        bufferPane.setPrefSize(48, 200);
        return bufferPane;
    }
}
