package org.example.uichat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.java_websocket.client.WebSocketClient;

import java.io.IOException;

public class MainApp extends Application {

    WebSocketClient client;

    Scene scene;
    Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        //
        JoinController.app = this;
        this.stage = stage;
        //
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("chat.fxml"));
        FXMLLoader logIn = new FXMLLoader(MainApp.class.getResource("JoinRoom.fxml"));
        Scene MainScene = new Scene(fxmlLoader.load(), 400, 700);
        MainScene.getStylesheets().add("CSS.css");
        Scene loginScene = new Scene(logIn.load(),800,800);
        this.scene = MainScene;
        loginScene.getStylesheets().add("CSS.css");
        stage.setTitle("IUTChat");
        stage.setScene(loginScene);
        stage.show();

        HelloController controller = fxmlLoader.getController();
        Serverlink(controller.textArea);

    }

    public void setScene(){this.stage.setScene(scene);}

    private void Serverlink(TextArea textArea){
        ClientWebSocket.setTextArea(textArea);
        client = ClientWebSocket.getLink();
    }
}
