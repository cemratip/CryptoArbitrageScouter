package com.cryptoarbitragescouter.cryptoarbitragescouter;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class main extends Application {

    @FXML
    private StackPane bufferPage;
    @FXML
    private StackPane pairPage;
    @FXML
    private VBox exchangePanel;
    @FXML
    private StackPane pricePage;
    @FXML
    private StackPane alertsPage;
    @FXML
    private TextField pairInput;
    @FXML
    private VBox exchangeListLeft;
    @FXML
    private VBox exchangeListRight;
    @FXML
    private Button selectAllBtn;
    @FXML
    private ComboBox<String> discCombobox;
    @FXML
    private ComboBox<String> soundsCombobox;
    @FXML
    private Label invalidNumberOfExchangesLabel;
    @FXML
    private TextField priceInput;
    @FXML
    private Label invalidPriceInputLabel;

    private JSONArray pairs = null;
    private JSONArray exchangesObj = null;
    private ArrayList<CheckBox> exchangeCheckboxes = new ArrayList<CheckBox>();
    private HashSet<String> exchangeSet = new HashSet<>();
    private double priceDisc;

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

    public void hideAlertsPage() {
        pricePage.setVisible(false);
    }

    @FXML
    public void doAction() {
        exchangePanel.setVisible(false);
        if (exchangesObj != null) {
            exchangesObj.clear();
        }
        exchangeSet.clear();
        exchangeCheckboxes.clear();
        exchangeListLeft.getChildren().clear();
        exchangeListRight.getChildren().clear();
        selectAllBtn.setText("Select All");
        showExchangePanel();
    }

    @FXML
    public void showExchangePanel() {
        if (pairExists()){
            exchangePanel.setVisible(true);
            getExchanges();
        }
    }

    private void getExchanges() {
        JSONParser jsonParser = new JSONParser();
        ArrayList<Object> exchanges = new ArrayList<>();
        try (FileReader reader = new FileReader("/Users/MrCem/Desktop/app/allFilteredExchangesAndPairs.json")) {
            Object obj = jsonParser.parse(reader);
            exchangesObj = (JSONArray) obj;
            exchangesObj.toArray();
            for (int i = 0; i < exchangesObj.size(); i++) {
                JSONArray element = (JSONArray) exchangesObj.get(i);
                if (element.get(1).equals(pairInput.getText().toUpperCase())) {
                    exchanges.add(element.get(0));
                }
            }
            for (Object exchange : exchanges) {
                exchangeSet.add((String) exchange);
            }
            showExchanges();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void showExchanges() {
        double numberOfExchanges = exchangeSet.size();
        int numberOfExchangesInLeftColumn = (int) Math.ceil(numberOfExchanges/2);
        int count=1;
        for (Object exchange : exchangeSet) {
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
        return pairs.contains(pair);
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







    private boolean isValidNumberOfExchanges() {
        int count=0;
        for (CheckBox checkbox : exchangeCheckboxes) {
            if (checkbox.isSelected()) {
                count++;
            }
            if (count >= 2) {
                return true;
            }
        }
        invalidNumberOfExchangesLabel.setVisible(true);
        return false;
    }

    @FXML
    public void showPricePage() {
        invalidNumberOfExchangesLabel.setVisible(false);
        if (isValidNumberOfExchanges()) {
            alertsPage.setVisible(false);
            if (discCombobox.getItems().size()!=2) {
                discCombobox.getItems().addAll("$", "%");
            }
            pricePage.setVisible(true);
        }
    }

    @FXML
    public void showAlertsPage() {
        try {
            if (discCombobox.getValue() != null) {
                if (discCombobox.getValue().equals("%") || discCombobox.getValue().equals("$")) {
                    priceDisc = Double.parseDouble(priceInput.getText());
                    soundsCombobox.getItems().clear();
                    soundsCombobox.getItems().addAll("Sound1", "Sound2");
                    alertsPage.setVisible(true);
                }
            }
            else {
                invalidPriceInputLabel.setText("⚠ Select an interval");
            }
        }
        catch (NumberFormatException e) {
            if (priceInput.getText().equals("")) {
                invalidPriceInputLabel.setText("⚠ Enter a number");
            }
            else {
                invalidPriceInputLabel.setText("⚠ Invalid character");
            }
        }
    }

    @FXML
    public void returnToMain() {
        bufferPage.setVisible(false);
        exchangePanel.setVisible(false);
        pairPage.setVisible(false);
        priceInput.clear();
        pricePage.setVisible(false);
        alertsPage.setVisible(false);
    }

    public void hideInvalidPriceLabel() {
        invalidPriceInputLabel.setText("");
    }

    public static void main(String[] args) {
        launch();
    }
}