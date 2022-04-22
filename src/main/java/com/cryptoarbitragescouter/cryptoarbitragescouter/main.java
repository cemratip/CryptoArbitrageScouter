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
import java.util.Objects;

public class main extends Application {

    @FXML
    private StackPane mainPage;
    @FXML
    private StackPane bufferPage;
    @FXML
    private StackPane pairPage;
    @FXML
    private StackPane exchangePage;

    @Override
    public void start(Stage stage) throws IOException {
        Pane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/main.fxml")));
        Scene scene = new Scene(root);
        stage.setTitle("Crypto Arbitrage Monitor");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void showPairPage() throws IOException {
        bufferPage.setVisible(true);
        pairPage.setVisible(true);
    }

    @FXML
    public void showExchangePage() throws IOException {
        exchangePage.setVisible(true);
    }

    @FXML
    public void returnToMain() {
        bufferPage.setVisible(false);
        pairPage.setVisible(false);
        exchangePage.setVisible(false);
    }

    public static void main(String[] args) {
        launch();
    }
}