package org.example.uichat;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.java_websocket.client.WebSocketClient;

public class HelloController {
    public TextArea textArea;
    public TextField textEntry;
    public static String username;
    public static String room;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        if (!textArea.getText().isEmpty()) {
            textArea.setText("");
            ClientWebSocket.send(new Message(username, textEntry.getText(), room));
            textEntry.setText("");
        }
    }
}
