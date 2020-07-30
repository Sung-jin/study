package socket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NIO_SocketClient extends Application {
    UI ui = new UI();
    ExecutorService executorService;
    ServerSocketChannel serverSocketChannel;
    List<Client> connections = new Vector();

    TextArea textArea;
    Button btnStartStop;

    public static void main(String[] args) {
//        blockingSocketClientStart();
    }

    private static void blockingSocketClientStart() {
        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(true);
            // blocking 방식으로 동작
            System.out.println("[연결 요청]");
            socketChannel.connect(new InetSocketAddress("localhost", 5001));
            System.out.println("[연결 성공]");

            ByteBuffer byteBuffer = null;
            Charset charset = Charset.forName("UTF-8");

            byteBuffer = charset.encode("Hello Server");
            socketChannel.write(byteBuffer);
            System.out.println("[데이터 보내기 성공]");

            byteBuffer = ByteBuffer.allocate(100);
            int byteCount = socketChannel.read(byteBuffer);
            byteBuffer.flip();
            String message = charset.decode(byteBuffer).toString();
            System.out.println("[데이터 받기 성공] : " + message);

        } catch (Exception e) {}

        if (socketChannel.isOpen()) {
            try {
                socketChannel.close();
            } catch (IOException e) {}
        }
    }

    void startServer() {
        executorService = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );

        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(true);
            serverSocketChannel.bind(new InetSocketAddress(5001));
        } catch (Exception e) {
            if (serverSocketChannel.isOpen()) stopServer();
        }

        executorService.submit(() -> {
            Platform.runLater(() -> {
                ui.displayText(textArea, "[서버 시작]");
                btnStartStop.setText("stop");
            });

            while(true) {
                try {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    String message = "[연결 수락 : " + socketChannel.getRemoteAddress() + " : " + Thread.currentThread().getName();
                    Platform.runLater(() -> ui.displayText(textArea, message));

                    Client client = new Client(socketChannel);
                    connections.add(client);

                    Platform.runLater(() -> ui.displayText(textArea, "[연결 개수 : " + connections.size() + " ]"));
                } catch (Exception e) {
                    if(serverSocketChannel.isOpen()) stopServer();
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
                client.socketChannel.close();
                iterator.remove();
            }

            if (serverSocketChannel != null && serverSocketChannel.isOpen()) serverSocketChannel.close();
            if (executorService != null && !executorService.isShutdown()) executorService.shutdown();

            Platform.runLater(() -> {
                ui.displayText(textArea, "[서버 멈춤]");
                btnStartStop.setText("start");
            });
        } catch (Exception e) {}
    }

    class Client {
        SocketChannel socketChannel;

        Client(SocketChannel socketChannel) {
            this.socketChannel = socketChannel;
            receive();
        }

        void receive() {
            executorService.submit(() -> {
                while(true) {
                    try {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(100);

                        // 클라이언트가 비정상 종료를 햇을 경우 IOException 발생
                        int readByteCount = socketChannel.read(byteBuffer);

                        // 클라이언트가 정상적으로 SocketChannel 의 close() 호출했을 경우
                        if (readByteCount == -1) {
                            throw new IOException();
                        }

                        String message = "[요청 처리 : " + socketChannel.getRemoteAddress() + " : " + Thread.currentThread().getName() + " ]";
                        Platform.runLater(() -> ui.displayText(textArea, message));

                        byteBuffer.flip();
                        Charset charset = Charset.forName("UTF-8");
                        String data = charset.decode(byteBuffer).toString();

                        for(Client client: connections) client.send(data);
                    } catch (Exception e) {
                        try {
                            connections.remove(Client.this);
                            String message = "[클라이언트 통신 안됨 : " + socketChannel.getRemoteAddress() + " : " + Thread.currentThread().getName() + " ]";
                            Platform.runLater(() -> ui.displayText(textArea, message));
                            socketChannel.close();
                        } catch (IOException e2) {}
                        break;
                    }
                }
            });
        }

        void send(String data) {
            executorService.submit(() -> {
                try {
                    Charset charset = Charset.forName("UTF-8");
                    ByteBuffer byteBuffedr = charset.encode(data);
                    socketChannel.write(byteBuffedr);
                } catch (Exception e) {
                    try {
                        String message = "[클라이언트 통신 안됨 : " + socketChannel.getRemoteAddress() + " : " + Thread.currentThread().getName() + " ]";
                        Platform.runLater(() -> ui.displayText(textArea, message));
                        connections.remove(Client.this);
                        socketChannel.close();
                    } catch (IOException e2) {}
                }
            });
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        textArea = new TextArea();
        btnStartStop = new Button("start");

        ui.startServerUI(primaryStage, textArea, btnStartStop);
        primaryStage.setOnCloseRequest(event -> stopServer());

        btnStartStop.setOnAction(e -> {
            if (btnStartStop.getText().equals("start")) {
                startServer();
            } else if (btnStartStop.getText().equals("stop")) {
                stopServer();
            }
        });
    }
}
