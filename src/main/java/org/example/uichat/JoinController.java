package org.example.uichat;


import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class JoinController {
    public TextField textfield;
    public TextField username;

    public void logRoom(ActionEvent actionEvent) {
        try {
            //Checke si les information sont entrées
            if (!(textfield.getText().isEmpty() || username.getText().isEmpty())) {
                //Enregistrement des noms
                ChatController.username = username.getText();
                ChatController.room = textfield.getText();

                //Envoi d'un premier message de login
                ClientWebSocket.logInRoom(textfield.getText(),ChatController.username);

                //Update des affichages de la page de chat
                MainApp.helloController.initialize();

                //affichage de la page de chat
                MainApp.app.setChat();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
