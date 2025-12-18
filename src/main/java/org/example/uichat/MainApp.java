package org.example.uichat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
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

    //TODO close on ending

    @Override
    public void start(Stage stage) throws IOException {
        app = this;
        this.stage = stage;
        //
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("chat.fxml"));
        FXMLLoader logIn = new FXMLLoader(MainApp.class.getResource("JoinRoom.fxml"));
        chatScene = new Scene(fxmlLoader.load(), 400, 700);
        chatScene.getStylesheets().add("CSS.css");
        logScene = new Scene(logIn.load(),800,800);
        logScene.getStylesheets().add("CSS.css");
        stage.setTitle("IUTChat");
        stage.setScene(logScene);
        stage.show();

        helloController = fxmlLoader.getController();
        Serverlink(helloController.textArea);

    }

    public void setChat(){this.stage.setScene(chatScene);}
    public void setLog(){this.stage.setScene(logScene);}

    private void Serverlink(TextArea textArea){
        ClientWebSocket.setTextArea(textArea);
        client = ClientWebSocket.getLink();

    }
}
