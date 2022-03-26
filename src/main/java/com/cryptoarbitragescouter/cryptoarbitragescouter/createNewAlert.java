package com.cryptoarbitragescouter.cryptoarbitragescouter;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class createNewAlert {

    @FXML
    private BorderPane createNewAlertPane;

    @FXML
    public void returnToMain() {
        ((Pane) createNewAlertPane.getParent()).getChildren().remove(createNewAlertPane);
    }
}
