package com.cryptoarbitragescouter.cryptoarbitragescouter;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class main extends Application {

    @FXML
    private StackPane stackpane;

    @Override
    public void start(Stage stage) throws IOException {
        URL url = getClass().getResource("fxml/main.fxml");
        Pane root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setTitle("Crypto Arbitrage Scouter");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void createNewAlert() throws IOException {
        URL url = getClass().getResource("fxml/createNewAlert.fxml");
        Pane createNewAlertPane = FXMLLoader.load(url);
        stackpane.getChildren().add(createNewAlertPane);
    }

    public static void main(String[] args) {
        launch();
    }
}