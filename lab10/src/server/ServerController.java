package server;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ServerController {
    @FXML private TextArea inputTextArea;
    @FXML private Button ExitButton;

    private ThreadPoolExecutor pool;

    @FXML
    public void addMessage(String message) {
        inputTextArea.appendText(message);
    }

    @FXML
    public void initialize() throws IOException {
        pool = (ThreadPoolExecutor)Executors.newFixedThreadPool(5);
        pool.execute(new ServerHandler(8080));

        ExitButton.setOnAction((e) -> exit());
    }

    public void exit() {
        Stage currentStage = Server.getPrimaryStage();
        currentStage.close();

    }

    class ServerHandler implements Runnable {
        private ServerSocket serverSocket = null;
        private int port;
        private ConnectionHandler handler;
        ServerHandler(int port) throws IOException {
            this.port = port;
            serverSocket = new ServerSocket(8080);
        }

        @Override
        public void run() {
            System.out.printf("Server is listening on port %d\n", this.port);
            try {
                ThreadPoolExecutor pool = (ThreadPoolExecutor)Executors.newFixedThreadPool(5);
                while (true) {
//                    Socket acceptedSocket = serverSocket.accept();
                    handler = new ConnectionHandler(serverSocket.accept());
//                    Thread handlerThread = new Thread(handler);
//                    handlerThread.start();
                    pool.execute(handler);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class ConnectionHandler implements Runnable {
        private Socket socket;

        ConnectionHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println("Connected: " + socket);
            try {
                Scanner in = new Scanner(socket.getInputStream());
                String message = in.nextLine();
                addMessage("\n" + message);
                System.out.println(message);
                in.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void terminate() {
            try {
                socket.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}