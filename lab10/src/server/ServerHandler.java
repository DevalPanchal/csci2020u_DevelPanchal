//package server;
//
//import javafx.scene.control.TextArea;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.Scanner;
//
//public class ServerHandler {
//    private ServerSocket serverSocket = null;
//    private int port;
//
//    public ServerHandler(int port) throws IOException {
//        this.port = port;
//        serverSocket = new ServerSocket(port);
//    }
//
//    public void handleRequests() throws IOException {
//        System.out.printf("Server is listening on port: %d", this.port);
//        while(true) {
//            Socket acceptedSocket = serverSocket.accept();
//            ConnectionHandler handler = new ConnectionHandler(acceptedSocket);
//            Thread handlerThread = new Thread(handler);
//            handlerThread.start();
//        }
//    }
//}
//
//class ConnectionHandler implements Runnable{
//
//    private Socket socket = null;
//
//    public ConnectionHandler(Socket socket) {
//        this.socket = socket;
//    }
//
//    @Override
//    public void run() {
//        try {
//            Scanner inputStream = new Scanner(socket.getInputStream());
//            PrintWriter outputStream = new PrintWriter(socket.getOutputStream(), true);
//
//            String message = inputStream.nextLine();
//            TextArea area = new TextArea();
//            area.setText(message);
//
//            System.out.println(inputStream.nextLine());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
