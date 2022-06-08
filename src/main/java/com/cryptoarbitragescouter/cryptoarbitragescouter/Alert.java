package com.cryptoarbitragescouter.cryptoarbitragescouter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Callback;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;

public class Alert {
    String alertID = null;
    String alertName = null;
    String timestamp = null;
    String pair = null;
    double priceDisc;
    String priceInterval = null;
    boolean desktopNotif;
    String desktopSound = null;
    boolean emailNotif;
    boolean textNotif;
    boolean executeScript;
    String scriptPath = null;
    boolean active;
    boolean triggered;

    ImageView toggleOn;
    ImageView toggleOff;

    public Alert(int alertID, String alertName, String timestamp, String pair, double priceDisc, String priceInterval, boolean desktopNotif, String desktopSound, boolean emailNotif, boolean textNotif, boolean executeScript, String scriptPath, boolean active, boolean triggered) throws IOException {
        this.alertID = String.valueOf(alertID);
        this.alertName = alertName;
        this.timestamp = timestamp;
        this.pair = pair;
        this.priceDisc = priceDisc;
        this.priceInterval = priceInterval;
        this.desktopNotif = desktopNotif;
        this.desktopSound = desktopSound;
        this.emailNotif = emailNotif;
        this.textNotif = textNotif;
        this.executeScript = executeScript;
        this.scriptPath = scriptPath;
        this.active = active;
        this.triggered = triggered;
    }

    public BorderPane display() throws IOException {

        BorderPane alertBox = new BorderPane();
        alertBox.setId("alertBox");
        alertBox.setPrefSize(200, 200);

        VBox container = new VBox();
        container.setId("VBOXcontainer");
        container.setPrefSize(180,180);
        container.setMinSize(180,180);
        container.setMaxSize(180,180);


        Label alertNameLabel = new Label(alertName);
        alertNameLabel.setPrefSize(180, 45);
        alertNameLabel.setMinSize(180,45);
        alertNameLabel.setMaxSize(180,45);
        alertNameLabel.setPadding(new Insets(0,10,0,10));
        alertNameLabel.setFont(new Font("Arial", 19));
        alertNameLabel.setAlignment(Pos.CENTER);


        Label pairLabel = new Label(pair);
        pairLabel.setPrefSize(180, 45);
        pairLabel.setMinSize(180,45);
        pairLabel.setMaxSize(180,45);
        pairLabel.setPadding(new Insets(0,10,0,10));
        pairLabel.setFont(new Font("Arial", 19));
        pairLabel.setAlignment(Pos.CENTER);


        BorderPane activeContainer = new BorderPane();
        activeContainer.setPrefSize(180,45);
        activeContainer.setMinSize(180,45);
        activeContainer.setMaxSize(180,45);


        HBox toggleContainer = new HBox();
        toggleContainer.setPrefSize(154,24);
        toggleContainer.setMinSize(154,24);
        toggleContainer.setMaxSize(154,24);

        Label activeLabel = new Label("     Active");
        activeLabel.setPrefSize(81.5, 24);
        activeLabel.setMinSize(81.5, 24);
        activeLabel.setMaxSize(81.5, 24);
        activeLabel.setFont(new Font("Arial", 19));
        activeLabel.setAlignment(Pos.CENTER);

        StackPane toggleImageStack = new StackPane();
        toggleImageStack.setPrefSize(39,24);
        toggleImageStack.setMinSize(39,24);
        toggleImageStack.setMaxSize(39,24);
        toggleImageStack.setPadding(new Insets(0,0,0,20));
        toggleImageStack.setAlignment(Pos.CENTER);

        toggleOff = new ImageView(new Image(new FileInputStream("src/main/resources/com/cryptoarbitragescouter/cryptoarbitragescouter/images/off.png")));
        toggleOff.setFitHeight(24);
        toggleOff.setFitWidth(39);
        toggleOff.setLayoutX(10);
        toggleOff.setLayoutY(10);
        toggleOff.setOnMouseClicked(e -> {
            try {
                switchToggleOn();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        toggleOn = new ImageView(new Image(new FileInputStream("src/main/resources/com/cryptoarbitragescouter/cryptoarbitragescouter/images/on.png")));
        toggleOn.setFitHeight(24);
        toggleOn.setFitWidth(39);
        toggleOn.setOnMouseClicked(e -> {
            try {
                switchToggleOff();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        if (active) {
            toggleOff.setVisible(false);
            toggleImageStack.getChildren().addAll(toggleOff, toggleOn);
        }
        else {
            toggleOn.setVisible(false);
            toggleImageStack.getChildren().addAll(toggleOn, toggleOff);
        }

        toggleContainer.getChildren().addAll(activeLabel, toggleImageStack);
        toggleContainer.setAlignment(Pos.CENTER_LEFT);

        activeContainer.setCenter(toggleContainer);

        BorderPane editContainer = new BorderPane();
        editContainer.setPrefSize(180,45);
        editContainer.setMinSize(180,45);
        editContainer.setMaxSize(180,45);

        Button editButton = new Button("Edit");
        editButton.setPrefSize(38,26);
        editButton.setMinSize(38,26);
        editButton.setMaxSize(38,26);
        editButton.setAlignment(Pos.CENTER);
        editButton.setOnMouseClicked(e -> {
            try {
                openEditMenu();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        editContainer.setCenter(editButton);

        container.getChildren().addAll(alertNameLabel, pairLabel, activeContainer, editContainer);
        alertBox.setCenter(container);

        return alertBox;
    }

    public void switchToggleOff() throws SQLException {
        // update DB with active=false for this alert
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://cryptomonitordb.c2ausffpbbkh.eu-west-2.rds.amazonaws.com", "admin", "admin12345");

            PreparedStatement query = con.prepareStatement("UPDATE cryptomonitordb.alerts SET active=false WHERE alertID=?");
            query.setString(1, alertID);
            query.executeUpdate();

            // hide on image
            toggleOn.setVisible(false);
            toggleOff.setVisible(true);

        } catch (Exception e) {
            System.out.println(e);
            // TO DO: Print error message box to user with reason for failure e.g. Internet connection interrupted, no internet
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    public void switchToggleOn() throws SQLException {
        // update DB with active=true for this alert
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://cryptomonitordb.c2ausffpbbkh.eu-west-2.rds.amazonaws.com", "admin", "admin12345");

            PreparedStatement query = con.prepareStatement("UPDATE cryptomonitordb.alerts SET active=true WHERE alertID=?");
            query.setString(1, alertID);
            query.executeUpdate();

            // show on image
            toggleOn.setVisible(true);
            toggleOff.setVisible(false);

        } catch (Exception e) {
            System.out.println(e);
            // TO DO: Print error message box to user with reason for failure e.g. Internet connection interrupted, no internet
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    public void openEditMenu() throws SQLException {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://cryptomonitordb.c2ausffpbbkh.eu-west-2.rds.amazonaws.com", "admin", "admin12345");

            System.out.println("open edit menu");

        } catch (Exception e) {
            System.out.println(e);
            // TO DO: Print error message box to user with reason for failure e.g. Internet connection interrupted, no internet
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

}
