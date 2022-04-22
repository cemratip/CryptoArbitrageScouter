package com.cryptoarbitragescouter.cryptoarbitragescouter;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    JSONArray exchangesObj = null;

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
        getPairs();
        bufferPage.setVisible(true);
        pairPage.setVisible(true);
        pairInput.clear();
    }

    @FXML
    public void showExchangePage() {
        if (pairExists()){
            exchangePage.setVisible(true);
            getExchanges();
        }
        else if (pairInput.getText().equals("")) {
            invalidPairLabel.setText("Select a pair");
        }
        else {
            invalidPairLabel.setText("⚠ Invalid pair");
        }
    }

    @FXML
    public void returnToMain() {
        bufferPage.setVisible(false);
        pairPage.setVisible(false);
        exchangePage.setVisible(false);
    }

    private void getPairs() {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("/Users/MrCem/Desktop/app/allFilteredPairs.json")) {
            Object obj = jsonParser.parse(reader);
            pairs = (JSONArray) obj;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        TextFields.bindAutoCompletion(pairInput, pairs);
    }

    private void getExchanges() {
        JSONParser jsonParser = new JSONParser();
        ArrayList<Object> exchanges = new ArrayList<>();
        try (FileReader reader = new FileReader("/Users/MrCem/Desktop/app/pairs1.json")) {
            Object obj = jsonParser.parse(reader);
            exchangesObj = (JSONArray) obj;
            exchangesObj.toArray();
            int numberOfExchanges = 0;
            for (int i = 0; i < exchangesObj.size(); i++) {
                JSONArray element = (JSONArray) exchangesObj.get(i);
                if (element.get(1).equals(pairInput.getText())) {
                    exchanges.add(element.get(0));
                    numberOfExchanges++;
                }
            }
            HashSet<String> set = new HashSet<String>();
            for (Object exchange : exchanges) {
                set.add((String) exchange);
            }
            showExchanges(set);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void showExchanges(HashSet set) {
        System.out.println(set);
    }

    private boolean pairExists() {
        String pair = pairInput.getText().toUpperCase();
        System.out.println(pair);
        return pairs.contains(pair);
    }

    public void hideInvalidPairLabel(){
        invalidPairLabel.setText("");
    }

    public static void main(String[] args) {
        launch();
    }
}