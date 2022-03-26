module com.cryptoarbitragescouter.cryptoarbitragescouter {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.cryptoarbitragescouter.cryptoarbitragescouter to javafx.fxml;
    exports com.cryptoarbitragescouter.cryptoarbitragescouter;
}