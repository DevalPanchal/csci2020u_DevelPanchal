package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server{



    private ServerSocket serverSocket = null;
    private int port;

    public Server(int port) throws IOException {
        this.port = port;
        serverSocket = new ServerSocket(port);
    }

    public void handleRequests() throws IOException {
        System.out.println("Server is listening on port: " + this.port);
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        while(true) {
            Socket acceptedSocket = serverSocket.accept();
            threadPool.execute(new ConnectionHandler(acceptedSocket));
        }
    }

    public static void main(String[] args) {
        int port = 8080;
        try {

            Server server = new Server(port);
            server.handleRequests();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

class ConnectionHandler implements Runnable {
    private Socket socket = null;

    public ConnectionHandler(Socket socket) {
        this.socket = socket;
    }

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        try {
            Scanner inputStream = new Scanner(socket.getInputStream());
            PrintWriter outputStream = new PrintWriter(socket.getOutputStream(), true);
            System.out.println(inputStream.nextLine());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
