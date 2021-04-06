package server;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class ServerController {
    @FXML private static TextArea inputTextArea;

    public static void setTextArea(TextArea textArea) {
        ServerController.inputTextArea = textArea;
    }

    public static TextArea getTextArea() {
        return ServerController.inputTextArea;
    }

    public void initialize() throws IOException {
        setTextArea(inputTextArea);
        ServerHandler server = new ServerHandler(8080);
        server.handleRequests();
    }
}
