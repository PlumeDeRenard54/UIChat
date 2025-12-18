package org.example.uichat;


import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class JoinController {
    public TextField textfield;
    public TextField username;
    public static MainApp app;

    public void logRoom(ActionEvent actionEvent) {
        try {
            HelloController.username = username.getText();
            HelloController.room = textfield.getText();
            ClientWebSocket.send(new Message(HelloController.username,"LogMessage",textfield.getText()));
            app.setScene();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
