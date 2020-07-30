package socket;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

class UI {
    void startServerUI(Stage primaryStage, TextArea textArea, Button btnStartStop) {
        BorderPane root = new BorderPane();
        root.setPrefSize(500, 300);

        textArea.setEditable(false);
        BorderPane.setMargin(textArea, new Insets(0, 0, 2, 0));
        root.setCenter(textArea);

        btnStartStop.setPrefHeight(30);
        btnStartStop.setMaxWidth(Double.MAX_VALUE);

        root.setBottom(btnStartStop);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("app.css").toString());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Server");
        primaryStage.show();
    }

    void displayText(TextArea textArea, String text) {
        textArea.appendText(text + "\n");
    }
}
