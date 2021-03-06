package socket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer extends Application {
    UI ui = new UI();
    ExecutorService executorService;
    ServerSocket serverSocket;
    List<Client> connections = new Vector<>();

    TextArea textArea;
    Button btnStartStop;

    void startServer() {
        executorService = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );

        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress("localhost", 5001));
        } catch (Exception e) {
            if (!serverSocket.isClosed()) { stopServer(); }
        }

        executorService.submit(() -> {
            Platform.runLater(() -> {
                ui.displayText(textArea, "[서버 시작]");
                btnStartStop.setText("stop");
            });

            while(true) {
                try {
                    Socket socket = serverSocket.accept();
                    String message = "[연결 수락 : " + socket.getRemoteSocketAddress() +
                            " : " + Thread.currentThread().getName() + " ]";
                    Platform.runLater(() -> {
                        ui.displayText(textArea, message);
                    });

                    Client client = new Client(socket);
                    connections.add(client);
                    Platform.runLater(() -> {
                        ui.displayText(textArea, "[연결 개수 : " + connections.size() + " ]");
                    });
                } catch (Exception e) {
                    if (!serverSocket.isClosed()) { stopServer(); }
                    break;
                }
            }
        });
    }

    void stopServer() {
        try {
            Iterator<Client> iterator = connections.iterator();
            while(iterator.hasNext()) {
                Client client = iterator.next();
                client.socket.close();
                iterator.remove();
            }

            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }

            if (executorService != null && !executorService.isShutdown()) {
                executorService.shutdown();
            }

            Platform.runLater(() -> {
                ui.displayText(textArea, "[서버 멈춤]");
                btnStartStop.setText("start");
            });
        } catch (Exception e) { }
    }

    class Client {
        Socket socket;

        Client(Socket socket) {
            this.socket = socket;
            receive();
        }

        void receive() {
            executorService.submit(() -> {
                try {
                    while(true) {
                        byte[] byteArr = new byte[100];
                        InputStream inputStream = socket.getInputStream();

                        int readByteCount = inputStream.read(byteArr);

                        if (readByteCount == -1) { throw new IOException(); }

                        String message = "[요청 처리 : " + socket.getRemoteSocketAddress()
                                + " : " + Thread.currentThread().getName() + " ]";
                        Platform.runLater(() -> ui.displayText(textArea, message));

                        String data = new String(byteArr, 0, readByteCount, StandardCharsets.UTF_8);

                        for(Client client : connections) client.send(data);
                    }
                } catch (Exception e) {
                    try {
                        connections.remove(Client.this);
                        String message = "[클라이언트 통신 오류 : " + socket.getRemoteSocketAddress()
                                + " : " + Thread.currentThread().getName() + " ]";
                        Platform.runLater(() -> ui.displayText(textArea, message));
                        socket.close();
                    } catch (Exception e2) { }
                }
            });
        }

        void send(String data) {
            executorService.submit(() -> {
                try {
                    byte[] byteArr = data.getBytes(StandardCharsets.UTF_8);
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write(byteArr);
                    outputStream.flush();
                } catch (Exception e) {
                    try {
                        String message = "[클라이언트 통신 오류 : " + socket.getRemoteSocketAddress()
                                + " : " + Thread.currentThread() + " ]";
                        Platform.runLater(() -> ui.displayText(textArea, message));
                        connections.remove(Client.this);
                        socket.close();
                    } catch (Exception e2) { }
                }
            });
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        UI ui = new UI();
        textArea = new TextArea();
        btnStartStop = new Button("start");

        ui.startServerUI(primaryStage, textArea, btnStartStop);

        btnStartStop.setOnAction(e -> {
            if (btnStartStop.getText().equals("start")) {
                startServer();
            } else if (btnStartStop.getText().equals("stop")) {
                stopServer();
            }
        });
        primaryStage.setOnCloseRequest(event -> stopServer());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
