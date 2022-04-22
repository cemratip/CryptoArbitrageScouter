package com.cryptoarbitragescouter.cryptoarbitragescouter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.apache.http.NameValuePair;
import org.controlsfx.control.textfield.TextFields;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.util.List;

public class pairPage extends main{

    @FXML
    private TextField pairInput;
    @FXML
    private Label invalidPairLabel;

    private static HttpURLConnection connection;

    private static final String apiKey = "dd063792-575f-4afb-900f-df02d78e9d2f";

    private JSONArray pairs = null;

    public void initialize() {
        // fill the pairs input text field with all the pair suggestions
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("/Users/MrCem/Desktop/app/allFilteredPairs.json")) {
            Object obj = jsonParser.parse(reader);
            pairs = (JSONArray) obj;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        TextFields.bindAutoCompletion(pairInput, pairs);
    }

    public boolean pairExists() {
        String pair = pairInput.getText().toUpperCase();
        System.out.println(pair);
        return pairs.contains(pair);
    }

    public void hideInvalidPairLabel(){
        invalidPairLabel.setVisible(false);
    }

    public void goToExchangeSelectionPage() throws IOException {
        if (pairExists()){
            showExchangePage();
        }
        else {
            invalidPairLabel.setVisible(true);
        }
    }

    private static String makeAPICall(String uri, List<NameValuePair> parameters)
            throws URISyntaxException, IOException {
        /*String response_content = "";

        URIBuilder query = new URIBuilder(uri);
        query.addParameters(parameters);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(query.build());

        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        request.addHeader("X-CMC_PRO_API_KEY", apiKey);

        CloseableHttpResponse response = client.execute(request);

        try {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            response_content = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
        return  response_content;
        */

        return null; // delete line after uncommenting above block
    }


}
