package org.example.uichat;


import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class JoinController {
    public TextField textfield;
    public TextField username;
    public static MainApp app;

    public void logRoom(ActionEvent actionEvent) {
        try {
            //Checke si les information sont entrées
            if (!(textfield.getText().isEmpty() || username.getText().isEmpty())) {
                //Enregistrement des noms
                HelloController.username = username.getText();
                HelloController.room = textfield.getText();

                //Envoi d'un premier message
                ClientWebSocket.send(new Message(HelloController.username, "Join", textfield.getText()));

                //Update des affichages de la page de chat
                MainApp.helloController.initialize();

                //affichage de la page de chat
                app.setScene();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
