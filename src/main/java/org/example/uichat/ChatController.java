package org.example.uichat;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ChatController {
    public TextField textEntry;
    public static String username;
    public static String room;
    public TextField UserName;
    public ComboBox<String> Rooms;
    public VBox messages;
    @FXML
    private Label welcomeText;


    /**
     * Initialisation de la vue
     */
    public void initialize(){
        UserName.setText(username);
        Rooms.setValue(room);

        Rooms.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ClientWebSocket.logInRoom(Rooms.getValue(),username);
                room = Rooms.getValue();
            }
        });

        welcomeText.setText("You are " + username + " and currently in : " + room);
    }

    public void updateRooms(){
        Rooms.getItems().clear();
        for (String pr : ClientWebSocket.publicRooms){
            Rooms.getItems().add(pr);
        }
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
        if (Rooms.getValue() != null && Rooms.getValue().equals(room)){
            room = Rooms.getValue();
        }

        //Envoie le message
        if (!messages.getChildren().isEmpty()) {
            messages.getChildren().clear();
            ClientWebSocket.send(new Message(username, textEntry.getText(), room));
            textEntry.setText("");
        }
    }

    @FXML
    protected void onBackButtonClick(){
        MainApp.app.setLog();
    }
}
