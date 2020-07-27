package socket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SocketClient extends Application {
    Socket socket;
    TextArea textDisplay;
    TextField textInput;
    Button btnConn, btnSend;

    void startClient() {
        new Thread(() -> {
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress("192.168.1.41", 12345));
                Platform.runLater(() -> {
                    displayText("[연결 완료 : " + socket.getRemoteSocketAddress() + " ]");
                    btnConn.setText("stop");
                    btnSend.setDisable(false);
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    displayText("[서버 통신 에러]");
                    if (!socket.isClosed()) { stopClient(); }
                });
            }
            receive();
        }).start();
    }

    void stopClient() {
        try {
            Platform.runLater(() -> {
                displayText("[연결 끊음]");
                btnConn.setText("start");
                btnSend.setDisable(true);
            });
            if(socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {}
    }

    void receive() {
        while(true) {
            try {
                byte[] byteArr = new byte[100];
                InputStream inputStream = socket.getInputStream();
                int readByteCount = inputStream.read(byteArr);

                if (readByteCount == -1) { throw new IOException(); }

                String data = new String(byteArr, 0, readByteCount, StandardCharsets.UTF_8);

                Platform.runLater(() -> displayText("[수신 완료] " + data));
            } catch (Exception e) {
                Platform.runLater(() -> displayText("[서버 통신 에러]"));
                stopClient();
                break;
            }
        }
    }

    void send(String data) {
        new Thread(() -> {
            try {
                byte[] byteArray = data.getBytes(StandardCharsets.UTF_8);
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(byteArray);
                outputStream.flush();
                Platform.runLater(() -> displayText("[보내기 성공]"));
            } catch (Exception e) {
                Platform.runLater(() -> displayText("[서버 통신 에러]"));
                stopClient();
            }
        }).start();
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        root.setPrefSize(500, 300);

        textDisplay = new TextArea();
        textDisplay.setEditable(false);
        BorderPane.setMargin(textDisplay, new Insets(0, 0, 0, 2));
        root.setCenter(textDisplay);

        BorderPane bottom = new BorderPane();
        textInput = new TextField();
        textInput.setPrefSize(60, 30);
        BorderPane.setMargin(textInput, new Insets(0, 1, 1, 1));

        btnConn = new Button("start");
        btnConn.setOnAction(e -> {
            if(btnConn.getText().equals("start")) startClient();
            else stopClient();
        });

        btnSend = new Button("send");
        btnSend.setPrefSize(60, 30);
        btnSend.setDisable(true);
        btnSend.setOnAction(e -> {
            send(textInput.getText());
        });

        bottom.setCenter(textInput);
        bottom.setLeft(btnConn);
        bottom.setRight(btnSend);
        root.setBottom(bottom);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("app.css").toString());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Client");
        primaryStage.setOnCloseRequest(e -> stopClient());
        primaryStage.show();
    }

    void displayText(String text) {
        textDisplay.appendText(text + "\n");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
