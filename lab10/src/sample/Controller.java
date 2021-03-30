package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller {
    @FXML private TextField usernameField;
    @FXML private TextField messageField;

    @FXML private Button sendButton;
    @FXML private Button exitButton;

    public void initialize() {
        sendButton.setOnAction(actionEvent -> {
            sendMessageToServer();
        });

        exitButton.setOnAction(actionEvent -> {
            exit();
        });
    }

    public void sendMessageToServer() {
        String username = usernameField.getText();
        String message = messageField.getText();
        Socket clientSocket = null;
        PrintWriter out = null;

        StringBuilder stringToServer = new StringBuilder();
        try {
            clientSocket = new Socket("localhost", 8080);
            out = new PrintWriter(new BufferedOutputStream(clientSocket.getOutputStream()));
            stringToServer.append(username).append(" ").append(message);
            out.println(stringToServer);
            resetText(usernameField);
            resetText(messageField);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exit() {
        Stage currentStage = Main.getPrimaryStage();
        currentStage.close();
    }

    public void resetText(TextField textField) {
        textField.setText("");
    }
}
