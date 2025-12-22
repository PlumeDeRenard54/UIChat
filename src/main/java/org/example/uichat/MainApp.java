package org.example.uichat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.java_websocket.client.WebSocketClient;

import java.io.IOException;

public class MainApp extends Application {

    public static ChatController helloController;
    public static MainApp app;

    WebSocketClient client;

    Scene chatScene;
    Scene logScene;
    Stage stage;


    @Override
    public void start(Stage stage) throws IOException {
        app = this;
        this.stage = stage;
        //Loaders
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("chat.fxml"));
        FXMLLoader logIn = new FXMLLoader(MainApp.class.getResource("JoinRoom.fxml"));

        //Chargement des scenes
        chatScene = new Scene(fxmlLoader.load(), 400, 700);
        chatScene.getStylesheets().add("CSS.css");
        logScene = new Scene(logIn.load(),800,800);
        logScene.getStylesheets().add("CSS.css");
        stage.setTitle("IUTChat");
        stage.setScene(logScene);
        stage.show();
        stage.setOnCloseRequest((e)->ClientWebSocket.getLink().close());

        helloController = fxmlLoader.getController();
        Serverlink(helloController.messages);

        //lancement des checks reguliers
        ClientWebSocket.initUpdates();

    }

    public void setChat(){this.stage.setScene(chatScene);}
    public void setLog(){this.stage.setScene(logScene);}

    private void Serverlink(VBox vBox){
        ClientWebSocket.setMessages(vBox);
        client = ClientWebSocket.getLink();
        //TODO Ecran de loading

    }
}
