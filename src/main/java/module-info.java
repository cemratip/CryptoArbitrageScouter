module com.cryptoarbitragescouter.cryptoarbitragescouter {
    requires java.desktop;
    requires json.simple;
    requires java.scripting;
    requires javafx.web;
    requires io.github.bonigarcia.webdrivermanager;
    requires jakarta.activation;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.sql;
    requires mysql.connector.java;


    opens com.cryptoarbitragescouter.cryptoarbitragescouter to javafx.fxml;
    exports com.cryptoarbitragescouter.cryptoarbitragescouter;
}