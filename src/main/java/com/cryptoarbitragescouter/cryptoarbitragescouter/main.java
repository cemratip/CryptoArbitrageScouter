package com.cryptoarbitragescouter.cryptoarbitragescouter;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class main extends Application {

    @FXML
    private StackPane bufferPage;
    @FXML
    private StackPane pairPage;
    @FXML
    private StackPane exchangePage;
    @FXML
    private TextField pairInput;
    @FXML
    private Label invalidPairLabel;

    JSONArray pairs = null;

    @Override
    public void start(Stage stage) throws IOException {
        Pane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/main.fxml")));
        Scene scene = new Scene(root);
        stage.setTitle("Crypto Arbitrage Monitor");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void showPairPage() {
        initialisePairs();
        bufferPage.setVisible(true);
        pairPage.setVisible(true);
        pairInput.clear();
    }

    @FXML
    public void showExchangePage() {
        if (pairExists()){
            exchangePage.setVisible(true);
        }
        else {
            invalidPairLabel.setVisible(true);
        }
    }

    @FXML
    public void returnToMain() {
        bufferPage.setVisible(false);
        pairPage.setVisible(false);
        exchangePage.setVisible(false);
    }

    private void initialisePairs() {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("/Users/MrCem/Desktop/app/allFilteredPairs.json")) {
            Object obj = jsonParser.parse(reader);
            pairs = (JSONArray) obj;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        TextFields.bindAutoCompletion(pairInput, pairs);
    }

    private boolean pairExists() {
        String pair = pairInput.getText().toUpperCase();
        System.out.println(pair);
        return pairs.contains(pair);
    }

    public void hideInvalidPairLabel(){
        invalidPairLabel.setVisible(false);
    }

    public static void main(String[] args) {
        launch();
    }
}