package com.cryptoarbitragescouter.cryptoarbitragescouter;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
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
    @FXML
    private VBox exchangeListLeft;
    @FXML
    private VBox exchangeListRight;
    @FXML
    private Button selectAllBtn;

    JSONArray pairs = null;
    JSONArray exchangesObj = null;
    ArrayList<CheckBox> exchangeCheckboxes = new ArrayList<CheckBox>();

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
            invalidPairLabel.setText("⚠ Select a pair");
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
            for (int i = 0; i < exchangesObj.size(); i++) {
                JSONArray element = (JSONArray) exchangesObj.get(i);
                if (element.get(1).equals(pairInput.getText().toUpperCase())) {
                    exchanges.add(element.get(0));
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
        double numberOfExchanges = set.size();
        int numberOfExchangesInLeftColumn = (int) Math.ceil(numberOfExchanges/2);
        int count=1;
        for (Object exchange : set) {
            CheckBox checkbox = new CheckBox("");
            exchangeCheckboxes.add(checkbox);
            HBox box = new HBox(new Label((String) exchange), checkbox);
            box.setAlignment(Pos.BASELINE_LEFT);
            if (count <= numberOfExchangesInLeftColumn) {
                exchangeListLeft.getChildren().add(box);
            }
            else {
                exchangeListRight.getChildren().add(box);
            }
            count++;
        }
    }

    public void selectAllExchanges() {
        if (selectAllBtn.getText().equals("Select All")) {
            for (CheckBox checkbox : exchangeCheckboxes) {
                checkbox.setSelected(true);
            }
            selectAllBtn.setText("Deselect All");
        }
        else {
            for (CheckBox checkbox : exchangeCheckboxes) {
                checkbox.setSelected(false);
            }
            selectAllBtn.setText("Select All");
        }
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