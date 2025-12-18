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
    public TextField UserName;
    @FXML
    private Label welcomeText;

    /**
     * Initialisation de la vue
     */
    public void initialize(){
        UserName.setText(username);
        welcomeText.setText("You are " + username + " and currently in : " + room);
    }


    /**
     * Fonction à l'activation du bouton d'envoi
     */
    @FXML
    protected void onHelloButtonClick() {
        //Mets a jour ne nom d'util
        if (!UserName.getText().isEmpty()) {
            username = UserName.getText();
        }

        //Envoie le message
        if (!textArea.getText().isEmpty()) {
            textArea.setText("");
            ClientWebSocket.send(new Message(username, textEntry.getText(), room));
            textEntry.setText("");
        }
    }
}
