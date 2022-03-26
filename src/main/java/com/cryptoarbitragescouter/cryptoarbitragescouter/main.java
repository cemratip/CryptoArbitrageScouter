package com.cryptoarbitragescouter.cryptoarbitragescouter;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class main extends Application {

    @FXML
    private StackPane stackpane;

    @Override
    public void start(Stage stage) throws IOException {
        Pane root = FXMLLoader.load(getClass().getResource("fxml/main.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Crypto Arbitrage Scouter");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void createNewAlert() throws IOException {
        Pane createNewAlertPane = FXMLLoader.load(getClass().getResource("fxml/createNewAlert.fxml"));
        stackpane.getChildren().add(createNewAlertPane);
    }

    public static void main(String[] args) {
        launch();
    }
}