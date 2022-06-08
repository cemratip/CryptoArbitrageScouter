package com.cryptoarbitragescouter.cryptoarbitragescouter;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;

public class Page {

    private final FlowPane page;

    public Page(ArrayList<Alert> alerts) throws IOException {
        page = new FlowPane();
        page.setPrefSize(1040,500);

        page.getChildren().addAll(new LongPane().display(), new BufferPane().display());

        int alertCount = 0;
        for (Alert alert : alerts) {
            alertCount++;
            BorderPane alertBox = alert.display();
            if (alertCount == 5) {
                page.getChildren().addAll(new LongPane().display(), new BufferPane().display());
            }
            page.getChildren().addAll(alertBox, new BufferPane().display());
        }
    }

    public FlowPane display() throws IOException {
        return page;
    }

}
